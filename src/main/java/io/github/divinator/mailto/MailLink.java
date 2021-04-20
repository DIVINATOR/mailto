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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MailLink {

    private final Map<String, String> params;

    public MailLink() {
        this.params = new LinkedHashMap<>();
    }

    public MailLink setTo(String to) {
        params.put("to", to);
        return this;
    }

    public MailLink setCc(String cc) {
        params.put("cc", cc);
        return this;
    }

    public MailLink setBcc(String bcc) {
        params.put("bcc", bcc);
        return this;
    }

    public MailLink setSubject(String subject) {
        params.put("subject", subject);
        return this;
    }

    public MailLink setBody(String body) {
        params.put("body", body);
        return this;
    }

    public String build() {
        String AMPERSAND = "&";
        String MAILTO = "mailto:?";

        final String collect = params.entrySet().stream()
                .filter(stringStringEntry -> !stringStringEntry.getValue().isEmpty())
                .map(stringStringEntry -> {
                    try {
                        return String.format("%s=%s",
                                stringStringEntry.getKey(),
                                URLEncoder.encode(stringStringEntry.getValue(), StandardCharsets.UTF_8.name()).replaceAll("\\+", "%20"));
                    } catch (UnsupportedEncodingException e) {
                        //e.printStackTrace();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.joining(AMPERSAND));

        final String out = String.format("%s%s", MAILTO, collect);

        return out.compareTo(MAILTO) == 0 ? "" : out;
    }
}
