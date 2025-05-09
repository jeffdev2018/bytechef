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

package com.bytechef.component.microsoft.teams.constant;

import static com.bytechef.component.definition.ComponentDsl.option;
import static com.bytechef.component.definition.ComponentDsl.string;

import com.bytechef.component.definition.ComponentDsl.ModifiableStringProperty;
import com.bytechef.component.definition.Property.ControlType;

/**
 * @author Monika Domiter
 */
public class MicrosoftTeamsConstants {

    public static final String BODY = "body";
    public static final String CHAT_ID = "chatId";
    public static final String CHANNEL_ID = "channelId";
    public static final String CONTENT = "content";
    public static final String CONTENT_TYPE = "contentType";
    public static final String DISPLAY_NAME = "displayName";
    public static final String DESCRIPTION = "description";
    public static final String ID = "id";
    public static final String TEAM_ID = "teamId";
    public static final String VALUE = "value";

    public static final ModifiableStringProperty CONTENT_TYPE_PROPERTY = string(CONTENT_TYPE)
        .label("Message Text Format")
        .options(
            option("Text", "text"),
            option("HTML", "html"))
        .defaultValue("text")
        .required(true);

    public static final ModifiableStringProperty CONTENT_PROPERTY = string(CONTENT)
        .label("Message Text")
        .controlType(ControlType.TEXT_AREA)
        .required(true);

    private MicrosoftTeamsConstants() {
    }
}
