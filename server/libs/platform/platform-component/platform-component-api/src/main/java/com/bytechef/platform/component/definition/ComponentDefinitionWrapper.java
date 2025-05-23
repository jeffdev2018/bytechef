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

package com.bytechef.platform.component.definition;

import com.bytechef.commons.util.OptionalUtils;
import com.bytechef.component.definition.ActionDefinition;
import com.bytechef.component.definition.ComponentDefinition;
import com.bytechef.platform.component.util.CustomActionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Ivica Cardic
 */
public class ComponentDefinitionWrapper extends AbstractComponentDefinitionWrapper implements ComponentDefinition {

    private final List<ActionDefinition> actions;

    public ComponentDefinitionWrapper(
        ComponentDefinition componentDefinition, List<ActionDefinition> actionDefinitions) {

        super(componentDefinition);

        // Custom Actions support

        if (OptionalUtils.orElse(componentDefinition.getCustomAction(), false)) {
            actionDefinitions = new ArrayList<>(actionDefinitions);

            actionDefinitions.add(CustomActionUtils.getCustomActionDefinition(componentDefinition));
        }

        this.actions = actionDefinitions;
    }

    @Override
    public Optional<List<? extends ActionDefinition>> getActions() {
        return Optional.ofNullable(actions == null ? null : new ArrayList<>(actions));
    }

    @Override
    public String toString() {
        return "ComponentDefinitionWrapper{" +
            "name='" + name + '\'' +
            ", version=" + version +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", connection=" + connection +
            ", categories='" + componentCategories + '\'' +
            ", clusterElements=" + clusterElements +
            ", customAction=" + customAction +
            ", customActionHelp=" + customActionHelp +
            ", actions=" + actions +
            ", triggers=" + triggers +
            ", resources=" + resources +
            ", tags=" + tags +
            ", metadata=" + metadata +
            ", icon='" + icon + '\'' +
            "} ";
    }
}
