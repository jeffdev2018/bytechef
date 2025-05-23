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

package com.bytechef.component.intercom;

import static com.bytechef.component.definition.ComponentDsl.component;
import static com.bytechef.component.definition.ComponentDsl.tool;

import com.bytechef.component.ComponentHandler;
import com.bytechef.component.definition.ComponentCategory;
import com.bytechef.component.definition.ComponentDefinition;
import com.bytechef.component.intercom.action.IntercomCreateContactAction;
import com.bytechef.component.intercom.action.IntercomGetContactAction;
import com.bytechef.component.intercom.action.IntercomSendMessageAction;
import com.bytechef.component.intercom.connection.IntercomConnection;
import com.google.auto.service.AutoService;

/**
 * @author Luka Ljubić
 */
@AutoService(ComponentHandler.class)
public class IntercomComponentHandler implements ComponentHandler {

    private static final ComponentDefinition COMPONENT_DEFINITION = component("intercom")
        .title("Intercom")
        .description(
            "Intercom is the complete AI-first customer service solution, giving exceptional experiences for support teams with AI agent, AI copilot, tickets, ...")
        .customAction(true)
        .icon("path:assets/intercom.svg")
        .categories(ComponentCategory.CUSTOMER_SUPPORT)
        .connection(IntercomConnection.CONNECTION_DEFINITION)
        .actions(
            IntercomCreateContactAction.ACTION_DEFINITION,
            IntercomGetContactAction.ACTION_DEFINITION,
            IntercomSendMessageAction.ACTION_DEFINITION)
        .clusterElements(
            tool(IntercomCreateContactAction.ACTION_DEFINITION),
            tool(IntercomGetContactAction.ACTION_DEFINITION),
            tool(IntercomSendMessageAction.ACTION_DEFINITION));

    @Override
    public ComponentDefinition getDefinition() {
        return COMPONENT_DEFINITION;
    }
}
