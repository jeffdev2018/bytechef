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

package com.bytechef.component.clickup.util;

import static com.bytechef.component.clickup.constant.ClickupConstants.FOLDER_ID;
import static com.bytechef.component.clickup.constant.ClickupConstants.ID;
import static com.bytechef.component.clickup.constant.ClickupConstants.NAME;
import static com.bytechef.component.clickup.constant.ClickupConstants.SPACE_ID;
import static com.bytechef.component.clickup.constant.ClickupConstants.WORKSPACE_ID;
import static com.bytechef.component.definition.ComponentDsl.option;

import com.bytechef.component.definition.Context;
import com.bytechef.component.definition.Context.Http;
import com.bytechef.component.definition.Option;
import com.bytechef.component.definition.Parameters;
import com.bytechef.component.definition.TriggerContext;
import com.bytechef.component.definition.TriggerDefinition.WebhookBody;
import com.bytechef.component.definition.TypeReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Monika Kušter
 */
public class ClickupUtils extends AbstractClickupUtils {

    private ClickupUtils() {
    }

    public static List<Option<String>> getListIdOptions(
        Parameters inputParameters, Parameters connectionParameters, Map<String, String> lookupDependsOnPaths,
        String searchText, Context context) {

        List<Option<String>> options = new ArrayList<>();
        String folderId = inputParameters.getString(FOLDER_ID);
        if (folderId != null) {
            options.addAll(getListsWithinFolder(context, folderId));
        }

        options.addAll(getFolderlessLists(context, inputParameters.getRequiredString(SPACE_ID)));

        return options;
    }

    private static List<Option<String>> getListsWithinFolder(Context context, String folderId) {

        return getOptions(fetchDataFromHttpEndpoint("/folder/" + folderId + "/list", context), "lists");
    }

    private static List<Option<String>> getFolderlessLists(Context context, String spaceId) {
        return getOptions(fetchDataFromHttpEndpoint("/space/" + spaceId + "/list", context), "lists");
    }

    public static List<Option<String>> getFolderIdOptions(
        Parameters inputParameters, Parameters connectionParameters, Map<String, String> lookupDependsOnPaths,
        String searchText, Context context) {

        return getOptions(
            fetchDataFromHttpEndpoint("/space/" + inputParameters.getRequiredString(SPACE_ID) + "/folder", context),
            "folders");
    }

    public static List<Option<String>> getSpaceIdOptions(
        Parameters inputParameters, Parameters connectionParameters, Map<String, String> lookupDependsOnPaths,
        String searchText, Context context) {

        return getOptions(
            fetchDataFromHttpEndpoint("/team/" + inputParameters.getRequiredString(WORKSPACE_ID) + "/space", context),
            "spaces");
    }

    public static List<Option<String>> getWorkspaceIdOptions(
        Parameters inputParameters, Parameters connectionParameters, Map<String, String> lookupDependsOnPaths,
        String searchText, Context context) {

        return getOptions(fetchDataFromHttpEndpoint("/team", context), "teams");
    }

    private static List<Option<String>> getOptions(Map<String, List<Map<String, Object>>> body, String resource) {
        List<Option<String>> options = new ArrayList<>();

        for (Map<String, Object> map : body.get(resource)) {
            options.add(option((String) map.get(NAME), (String) map.get(ID)));
        }

        return options;
    }

    private static Map<String, List<Map<String, Object>>> fetchDataFromHttpEndpoint(
        String path, Context context) {

        return context
            .http(http -> http.get(path))
            .configuration(Http.responseType(Http.ResponseType.JSON))
            .execute()
            .getBody(new TypeReference<>() {});
    }

    public static String subscribeWebhook(
        String webhookUrl, TriggerContext context, String workspaceId, String eventType) {

        Map<String, Object> body = context.http(http -> http.post("/team/" + workspaceId + "/webhook"))
            .body(
                Http.Body.of(
                    "endpoint", webhookUrl,
                    "events", List.of(eventType)))
            .configuration(Http.responseType(Http.ResponseType.JSON))
            .execute()
            .getBody(new TypeReference<>() {});

        return (String) body.get(ID);
    }

    public static void unsubscribeWebhook(TriggerContext context, String webhookId) {
        context.http(http -> http.delete("/webhook/" + webhookId))
            .configuration(Http.responseType(Http.ResponseType.JSON))
            .execute();
    }

    public static Map<String, Object> getCreatedObject(
        WebhookBody body, TriggerContext context, String id, String path) {

        Map<String, Object> content = body.getContent(new TypeReference<>() {});

        return context
            .http(http -> http.get(path + content.get(id)))
            .configuration(Http.responseType(Http.ResponseType.JSON))
            .execute()
            .getBody(new TypeReference<>() {});
    }
}
