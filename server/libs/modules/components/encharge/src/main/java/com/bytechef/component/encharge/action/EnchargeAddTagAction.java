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

package com.bytechef.component.encharge.action;

import static com.bytechef.component.OpenApiComponentHandler.PropertyType;
import static com.bytechef.component.definition.ComponentDsl.action;
import static com.bytechef.component.definition.ComponentDsl.string;
import static com.bytechef.component.definition.Context.Http.BodyContentType;

import com.bytechef.component.definition.ComponentDsl;
import java.util.Map;

/**
 * Provides a list of the component actions.
 *
 * @generated
 */
public class EnchargeAddTagAction {
    public static final ComponentDsl.ModifiableActionDefinition ACTION_DEFINITION = action("addTag")
        .title("Add Tag")
        .description("Add tag(s) to an existing user.")
        .metadata(
            Map.of(
                "method", "POST",
                "path", "/tags", "bodyContentType", BodyContentType.JSON, "mimeType", "application/json"

            ))
        .properties(string("tag").metadata(
            Map.of(
                "type", PropertyType.BODY))
            .label("Tag")
            .description("Tag(s) to add. To add multiple tags, use a comma-separated list, e.g. tag1,tag2")
            .required(true),
            string("email").metadata(
                Map.of(
                    "type", PropertyType.BODY))
                .label("Email")
                .description("Email of the person.")
                .required(true));

    private EnchargeAddTagAction() {
    }
}
