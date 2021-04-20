/*
 *  Copyright  2021 Sergey Divin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.github.divinator.mailto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    TextField to, cc, bcc, subject, out;

    @FXML
    TextArea body;

    private MailLinkBinder mailLinkBinder;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.mailLinkBinder = new MailLinkBinder(new MailLink());

        this.mailLinkBinder.toProperty().bind(to.textProperty());
        this.mailLinkBinder.ccProperty().bind(cc.textProperty());
        this.mailLinkBinder.bccProperty().bind(bcc.textProperty());
        this.mailLinkBinder.subjectProperty().bind(subject.textProperty());
        this.mailLinkBinder.bodyProperty().bind(body.textProperty());

        initializeChangeListener(to, cc, bcc, subject, body);
    }

    public void onCopy(ActionEvent actionEvent) {
        if (!mailLinkBinder.getUrl().isEmpty()) {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(mailLinkBinder.getUrl()), null);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успешно");
            //alert.setHeaderText(null);
            alert.setContentText("Ссылка скопирована в буфер обмена");
            alert.showAndWait();
        }
    }

    public void onCheck(ActionEvent actionEvent) throws URISyntaxException {
        if (!this.mailLinkBinder.getUrl().isEmpty()) {
            URI uri = new URI(this.mailLinkBinder.getUrl());
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.mail(uri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onClear(ActionEvent actionEvent) {
        clearTextInput(to, cc, bcc, subject, body);
        out.setText("");
    }

    private void initializeChangeListener(TextInputControl ... controls) {
        Arrays.stream(controls)
                .forEach(textInputControl -> textInputControl.textProperty()
                        .addListener((observable, oldValue, newValue) -> out.setText(mailLinkBinder.getUrl())));
    }

    private void clearTextInput(TextInputControl ... controls) {
        Arrays.stream(controls)
                .forEach(textInputControl -> textInputControl.textProperty().setValue(""));
    }
}
