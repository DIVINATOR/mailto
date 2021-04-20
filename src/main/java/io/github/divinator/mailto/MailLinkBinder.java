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

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MailLinkBinder {

    private StringProperty to, cc, bcc, subject, body;

    private MailLink mailLink;

    public MailLinkBinder(MailLink mailLink) {
        this.mailLink = mailLink;
        this.to = new SimpleStringProperty();
        this.cc = new SimpleStringProperty();
        this.bcc = new SimpleStringProperty();
        this.subject = new SimpleStringProperty();
        this.body = new SimpleStringProperty();
    }

    public String getTo() {
        return to.get();
    }

    public StringProperty toProperty() {
        return to;
    }

    public void setTo(String to) {
        this.to.set(to);
    }

    public String getCc() {
        return cc.get();
    }

    public StringProperty ccProperty() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc.set(cc);
    }

    public String getBcc() {
        return bcc.get();
    }

    public StringProperty bccProperty() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc.set(bcc);
    }

    public String getSubject() {
        return subject.get();
    }

    public StringProperty subjectProperty() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }

    public String getBody() {
        return body.get();
    }

    public StringProperty bodyProperty() {
        return body;
    }

    public void setBody(String body) {
        this.body.set(body);
    }

    public String getUrl() {
        return mailLink
                .setTo(to.getValue())
                .setCc(cc.getValue())
                .setBcc(bcc.getValue())
                .setSubject(subject.getValue())
                .setBody(body.getValue())
                .build();
    }
}
