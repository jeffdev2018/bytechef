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

package com.bytechef.component.teamwork;

import static com.bytechef.component.definition.ComponentDsl.component;

import com.bytechef.component.OpenApiComponentHandler;
import com.bytechef.component.definition.ComponentDefinition;
import com.bytechef.component.teamwork.action.TeamworkCreateCompanyAction;
import com.bytechef.component.teamwork.action.TeamworkCreateTaskAction;
import com.bytechef.component.teamwork.connection.TeamworkConnection;

/**
 * Provides the base implementation for the REST based component.
 *
 * @generated
 */
public abstract class AbstractTeamworkComponentHandler implements OpenApiComponentHandler {
    private final ComponentDefinition componentDefinition = modifyComponent(
        component("teamwork")
            .title("Teamwork")
            .description(
                "Teamwork is a project management software that helps teams collaborate, organize tasks, and track progress efficiently."))
                    .actions(modifyActions(TeamworkCreateCompanyAction.ACTION_DEFINITION,
                        TeamworkCreateTaskAction.ACTION_DEFINITION))
                    .connection(modifyConnection(TeamworkConnection.CONNECTION_DEFINITION))
                    .triggers(getTriggers());

    @Override
    public ComponentDefinition getDefinition() {
        return componentDefinition;
    }
}
