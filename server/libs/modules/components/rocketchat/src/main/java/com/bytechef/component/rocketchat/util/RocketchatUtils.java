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

package com.bytechef.component.rocketchat.util;

import static com.bytechef.component.definition.ComponentDsl.option;
import static com.bytechef.component.definition.Context.Http.responseType;
import static com.bytechef.component.rocketchat.constant.RocketchatConstants.NAME;
import static com.bytechef.component.rocketchat.constant.RocketchatConstants.ROOM_ID;
import static com.bytechef.component.rocketchat.constant.RocketchatConstants.TEXT;
import static com.bytechef.component.rocketchat.constant.RocketchatConstants.USERNAME;

import com.bytechef.component.definition.Context;
import com.bytechef.component.definition.Context.Http;
import com.bytechef.component.definition.Option;
import com.bytechef.component.definition.Parameters;
import com.bytechef.component.definition.TypeReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Marija Horvat
 */
public class RocketchatUtils {

    private RocketchatUtils() {
    }

    public static List<Option<String>> getUsersOptions(
        Parameters inputParameters, Parameters connectionParameters, Map<String, String> lookupDependsOnPaths,
        String searchText, Context context) {

        Map<String, Object> body = context.http(http -> http.get("/users.list"))
            .configuration(responseType(Http.ResponseType.JSON))
            .execute()
            .getBody(new TypeReference<>() {});

        List<Option<String>> options = new ArrayList<>();

        if (body.get("users") instanceof List<?> users) {
            for (Object user : users) {
                if (user instanceof Map<?, ?> map) {
                    String username = (String) map.get(USERNAME);

                    options.add(option(username, username));
                }
            }
        }

        return options;
    }

    public static List<Option<String>> getChannelsOptions(
        Parameters inputParameters, Parameters connectionParameters, Map<String, String> lookupDependsOnPaths,
        String searchText, Context context) {

        Map<String, Object> body = context.http(http -> http.get("/channels.list"))
            .configuration(responseType(Http.ResponseType.JSON))
            .execute()
            .getBody(new TypeReference<>() {});

        List<Option<String>> options = new ArrayList<>();

        if (body.get("channels") instanceof List<?> channels) {
            for (Object channel : channels) {
                if (channel instanceof Map<?, ?> map) {
                    String name = (String) map.get(NAME);

                    options.add(option(name, "#" + name));
                }
            }
        }

        return options;
    }

    public static Object sendMessage(String roomId, String text, Context context) {
        return context.http(http -> http.post("/chat.postMessage"))
            .body(
                Http.Body.of(
                    ROOM_ID, roomId,
                    TEXT, text))
            .configuration(responseType(Http.ResponseType.JSON))
            .execute()
            .getBody();
    }
}
