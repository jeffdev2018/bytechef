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

package com.bytechef.component;

import com.bytechef.component.definition.ComponentDefinition;
import com.bytechef.component.definition.Context;
import java.util.Map;
import java.util.Optional;

/**
 * Default component handler marker interface.
 *
 * @author Ivica Cardic
 */
public interface ComponentHandler {

    /**
     * This can be useful if we still want to have only one method to handle all actions instead of defining
     * <code>performFunction</code> for each <code>ActionDefinition</code>.
     *
     * @return optional HandleActionFunction
     */
    default Optional<ActionHandlerFunction> getActionHandler() {
        return Optional.empty();
    }

    /**
     *
     * @return
     */
    ComponentDefinition getDefinition();

    default String getName() {
        ComponentDefinition componentDefinition = getDefinition();

        return componentDefinition.getName();
    }

    default int getVersion() {
        ComponentDefinition componentDefinition = getDefinition();

        return componentDefinition.getVersion();
    }

    /**
     *
     */
    @FunctionalInterface
    interface ActionHandlerFunction {

        /**
         *
         * @param actionName
         * @param inputParameters
         * @param context
         * @return
         */
        Map<String, ?> apply(String actionName, Map<String, ?> inputParameters, Context context) throws Exception;
    }
}
