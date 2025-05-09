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

package com.bytechef.component.text.helper.action;

import static com.bytechef.component.definition.ComponentDsl.action;
import static com.bytechef.component.definition.ComponentDsl.option;
import static com.bytechef.component.definition.ComponentDsl.outputSchema;
import static com.bytechef.component.definition.ComponentDsl.string;
import static com.bytechef.component.text.helper.constant.TextHelperConstants.CONTENT;
import static com.bytechef.component.text.helper.constant.TextHelperConstants.ENCODING_SCHEMA;
import static com.bytechef.component.text.helper.constant.TextHelperConstants.ENCODING_SCHEMA_BASE64;
import static com.bytechef.component.text.helper.constant.TextHelperConstants.ENCODING_SCHEMA_BASE64URL;

import com.bytechef.component.definition.ActionContext;
import com.bytechef.component.definition.ComponentDsl.ModifiableActionDefinition;
import com.bytechef.component.definition.Parameters;
import com.bytechef.component.definition.Property.ControlType;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

/**
 * @author Igor Beslic
 */
public class TextHelperBase64DecodeAction {

    public static final ModifiableActionDefinition ACTION_DEFINITION = action("base64Decode")
        .title("Base64 Decode")
        .description("Decodes base64 encoded text into human readable plain text.")
        .properties(
            string(ENCODING_SCHEMA)
                .label("Encoding Scheme")
                .options(
                    option("Base64", ENCODING_SCHEMA_BASE64),
                    option("Base64 URL", ENCODING_SCHEMA_BASE64URL))
                .defaultValue(ENCODING_SCHEMA_BASE64)
                .required(true),
            string(CONTENT)
                .label("Base64 Content")
                .description("The Base64 encoded content that needs to be decoded.")
                .controlType(ControlType.TEXT_AREA)
                .required(true))
        .output(outputSchema(string().description("Decoded content.")))
        .perform(TextHelperBase64DecodeAction::perform);

    protected static String perform(
        Parameters inputParameters, Parameters connectionParameters, ActionContext context) {

        String base64Content = inputParameters.getRequiredString(CONTENT);

        if (base64Content.isEmpty()) {
            return base64Content;
        }

        Base64.Decoder decoder = of(inputParameters.getRequiredString(ENCODING_SCHEMA));

        return new String(decoder.decode(base64Content), StandardCharsets.UTF_8);
    }

    private static Base64.Decoder of(String schema) {
        if (Objects.equals(ENCODING_SCHEMA_BASE64, schema)) {
            return Base64.getDecoder();
        } else if (Objects.equals(ENCODING_SCHEMA_BASE64URL, schema)) {
            return Base64.getUrlDecoder();
        }

        throw new IllegalArgumentException("Unsupported schema: " + schema);
    }

}
