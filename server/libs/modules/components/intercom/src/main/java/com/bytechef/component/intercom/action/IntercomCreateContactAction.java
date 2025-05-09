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

package com.bytechef.component.intercom.action;

import static com.bytechef.component.definition.ComponentDsl.ModifiableActionDefinition;
import static com.bytechef.component.definition.ComponentDsl.action;
import static com.bytechef.component.definition.ComponentDsl.option;
import static com.bytechef.component.definition.ComponentDsl.outputSchema;
import static com.bytechef.component.definition.ComponentDsl.string;
import static com.bytechef.component.definition.Context.Http.ResponseType;
import static com.bytechef.component.definition.Context.Http.responseType;
import static com.bytechef.component.intercom.constant.IntercomConstants.AVATAR;
import static com.bytechef.component.intercom.constant.IntercomConstants.CONTACT_OUTPUT_PROPERTY;
import static com.bytechef.component.intercom.constant.IntercomConstants.EMAIL;
import static com.bytechef.component.intercom.constant.IntercomConstants.LEAD;
import static com.bytechef.component.intercom.constant.IntercomConstants.NAME;
import static com.bytechef.component.intercom.constant.IntercomConstants.PHONE;
import static com.bytechef.component.intercom.constant.IntercomConstants.ROLE;
import static com.bytechef.component.intercom.constant.IntercomConstants.USER;

import com.bytechef.component.definition.Context;
import com.bytechef.component.definition.Context.ContextFunction;
import com.bytechef.component.definition.Context.Http;
import com.bytechef.component.definition.Context.Http.Body;
import com.bytechef.component.definition.Parameters;
import com.bytechef.component.definition.TypeReference;

/**
 * @author Luka Ljubić
 * @author Monika Kušter
 */
public class IntercomCreateContactAction {

    public static final ModifiableActionDefinition ACTION_DEFINITION = action("createContact")
        .title("Create Contact")
        .description("Create new contact")
        .properties(
            string(ROLE)
                .options(option("User", USER), option("Lead", LEAD))
                .label("Contact Role")
                .description("Role of the contact")
                .maxLength(100)
                .required(true),
            string(EMAIL)
                .label("Contact Email")
                .description("Email of the contact")
                .maxLength(100)
                .required(true),
            string(NAME)
                .label("Contact Name")
                .description("Name of the contact")
                .maxLength(360)
                .required(false),
            string(PHONE)
                .label("Contact Phone")
                .description("Phone of the contact must start with a \"+\" sign")
                .maxLength(200)
                .required(false),
            string(AVATAR)
                .label("Contact Image")
                .description("Image of the contact")
                .maxLength(500)
                .required(false))
        .output(outputSchema(CONTACT_OUTPUT_PROPERTY))
        .perform(IntercomCreateContactAction::perform);

    protected static final ContextFunction<Http, Http.Executor> POST_CONTACTS_CONTEXT_FUNCTION =
        http -> http.post("/contacts");

    public static Object perform(Parameters inputParameters, Parameters connectionParameters, Context context) {
        return context.http(POST_CONTACTS_CONTEXT_FUNCTION)
            .body(
                Body.of(
                    ROLE, inputParameters.getRequiredString(ROLE),
                    EMAIL, inputParameters.getRequiredString(EMAIL),
                    NAME, inputParameters.getString(NAME),
                    PHONE, inputParameters.getString(PHONE),
                    AVATAR, inputParameters.getString(AVATAR)))
            .configuration(responseType(ResponseType.JSON))
            .execute()
            .getBody(new TypeReference<>() {});
    }

    private IntercomCreateContactAction() {
    }
}
