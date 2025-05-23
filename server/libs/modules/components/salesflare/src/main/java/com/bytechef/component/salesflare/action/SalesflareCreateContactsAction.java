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

package com.bytechef.component.salesflare.action;

import static com.bytechef.component.OpenApiComponentHandler.PropertyType;
import static com.bytechef.component.definition.ComponentDsl.action;
import static com.bytechef.component.definition.ComponentDsl.array;
import static com.bytechef.component.definition.ComponentDsl.integer;
import static com.bytechef.component.definition.ComponentDsl.object;
import static com.bytechef.component.definition.ComponentDsl.outputSchema;
import static com.bytechef.component.definition.ComponentDsl.string;
import static com.bytechef.component.definition.Context.Http.BodyContentType;
import static com.bytechef.component.definition.Context.Http.ResponseType;

import com.bytechef.component.definition.ComponentDsl;
import java.util.Map;

/**
 * Provides a list of the component actions.
 *
 * @generated
 */
public class SalesflareCreateContactsAction {
    public static final ComponentDsl.ModifiableActionDefinition ACTION_DEFINITION = action("createContacts")
        .title("Create Contacts")
        .description("Creates new contacts.")
        .metadata(
            Map.of(
                "method", "POST",
                "path", "/contacts", "bodyContentType", BodyContentType.JSON, "mimeType", "application/json"

            ))
        .properties(array("contacts").items(object().properties(string("email").maxLength(1000)
            .label("Email")
            .description("Email address of the contact.")
            .required(true),
            string("firstname").label("First Name")
                .description("The first name of the contact.")
                .required(false),
            string("lastname").label("Last Name")
                .description("The last name of the contact.")
                .required(false),
            string("phone_number").label("Work Phone Number")
                .required(false),
            string("mobile_phone_number").label("Mobile Phone Number")
                .required(false),
            string("home_phone_number").label("Home Phone Number")
                .required(false),
            string("fax_number").label("Fax Number")
                .required(false),
            array("social_profiles").items(string().description("Social profile URL"))
                .placeholder("Add to Social Profiles")
                .label("Social Profiles")
                .description("Social profile URL")
                .required(false)))
            .placeholder("Add to Contacts")
            .metadata(
                Map.of(
                    "type", PropertyType.BODY))
            .label("Contacts")
            .required(true))
        .output(outputSchema(array().items(object().properties(integer("id").description("ID of the contact.")
            .required(false))
            .description("List of created contacts."))
            .description("List of created contacts.")
            .metadata(
                Map.of(
                    "responseType", ResponseType.JSON))));

    private SalesflareCreateContactsAction() {
    }
}
