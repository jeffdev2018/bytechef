/*
 * Copyright 2025 ByteChef
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bytechef.component.whatsapp.action;

import static com.bytechef.component.definition.ComponentDsl.action;
import static com.bytechef.component.definition.ComponentDsl.object;
import static com.bytechef.component.definition.ComponentDsl.outputSchema;
import static com.bytechef.component.definition.ComponentDsl.string;
import static com.bytechef.component.definition.Context.Http.ResponseType;
import static com.bytechef.component.definition.Context.Http.responseType;
import static com.bytechef.component.whatsapp.constant.WhatsAppConstants.BODY;
import static com.bytechef.component.whatsapp.constant.WhatsAppConstants.CONTACTS;
import static com.bytechef.component.whatsapp.constant.WhatsAppConstants.ID;
import static com.bytechef.component.whatsapp.constant.WhatsAppConstants.INPUT;
import static com.bytechef.component.whatsapp.constant.WhatsAppConstants.MESSAGES;
import static com.bytechef.component.whatsapp.constant.WhatsAppConstants.MESSAGING_PRODUCT;
import static com.bytechef.component.whatsapp.constant.WhatsAppConstants.PHONE_NUMBER_ID;
import static com.bytechef.component.whatsapp.constant.WhatsAppConstants.RECEIVE_USER;
import static com.bytechef.component.whatsapp.constant.WhatsAppConstants.RECIPIENT_TYPE;
import static com.bytechef.component.whatsapp.constant.WhatsAppConstants.TEXT;
import static com.bytechef.component.whatsapp.constant.WhatsAppConstants.TYPE;

import com.bytechef.component.definition.ActionContext;
import com.bytechef.component.definition.ComponentDsl.ModifiableActionDefinition;
import com.bytechef.component.definition.Context.Http.Body;
import com.bytechef.component.definition.Parameters;
import com.bytechef.component.definition.TypeReference;
import java.util.Map;

/**
 * @author Luka Ljubić
 */
public class WhatsAppSendMessageAction {

    public static final ModifiableActionDefinition ACTION_DEFINITION = action("sendMessage")
        .title("Send Message")
        .description("Send a message via WhatsApp")
        .properties(
            string(BODY)
                .label("Message")
                .description("Message to send via WhatsApp")
                .maxLength(4096)
                .required(true),
            string(RECEIVE_USER)
                .label("Send Message To")
                .description("Phone number to send the message. It must start with \"+\" sign")
                .required(true))
        .output(
            outputSchema(
                object()
                    .properties(
                        string(MESSAGING_PRODUCT),
                        object(CONTACTS)
                            .properties(
                                string(INPUT),
                                string("wa_id")),
                        object(MESSAGES)
                            .properties(
                                string(ID)))))
        .perform(WhatsAppSendMessageAction::perform);

    private WhatsAppSendMessageAction() {
    }

    public static Object perform(
        Parameters inputParameters, Parameters connectionParameters, ActionContext actionContext) {

        return actionContext
            .http(http -> http.post("/" + connectionParameters.getString(PHONE_NUMBER_ID) + "/messages"))
            .body(
                Body.of(
                    MESSAGING_PRODUCT, "whatsapp",
                    RECIPIENT_TYPE, "individual",
                    RECEIVE_USER, inputParameters.getRequiredString(RECEIVE_USER),
                    TYPE, "text",
                    TEXT, Map.of(BODY, inputParameters.getRequiredString(BODY))))
            .configuration(responseType(ResponseType.JSON))
            .execute()
            .getBody(new TypeReference<>() {});
    }
}
