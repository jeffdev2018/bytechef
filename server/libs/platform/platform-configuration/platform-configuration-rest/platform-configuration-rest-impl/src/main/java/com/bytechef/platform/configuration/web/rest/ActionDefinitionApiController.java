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

package com.bytechef.platform.configuration.web.rest;

import com.bytechef.atlas.coordinator.annotation.ConditionalOnCoordinator;
import com.bytechef.platform.component.service.ActionDefinitionService;
import com.bytechef.platform.configuration.web.rest.model.ActionDefinitionBasicModel;
import com.bytechef.platform.configuration.web.rest.model.ActionDefinitionModel;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ivica Cardic
 */
@RestController
@RequestMapping("${openapi.openAPIDefinition.base-path.platform:}/internal")
@ConditionalOnCoordinator
public class ActionDefinitionApiController implements ActionDefinitionApi {

    private final ActionDefinitionService actionDefinitionService;
    private final ConversionService conversionService;

    @SuppressFBWarnings("EI")
    public ActionDefinitionApiController(
        ActionDefinitionService actionDefinitionService, ConversionService conversionService) {

        this.actionDefinitionService = actionDefinitionService;
        this.conversionService = conversionService;
    }

    @Override
    public ResponseEntity<ActionDefinitionModel> getComponentActionDefinition(
        String componentName, Integer componentVersion, String actionName) {

        return ResponseEntity.ok(
            conversionService.convert(
                actionDefinitionService.getActionDefinition(componentName, componentVersion, actionName),
                ActionDefinitionModel.class));
    }

    @Override
    public ResponseEntity<List<ActionDefinitionBasicModel>> getComponentActionDefinitions(
        String componentName, Integer componentVersion) {

        return ResponseEntity.ok(
            actionDefinitionService.getActionDefinitions(componentName, componentVersion)
                .stream()
                .map(actionDefinition -> conversionService.convert(
                    actionDefinition, ActionDefinitionBasicModel.class))
                .toList());
    }

//    @Override
//    public ResponseEntity<String> getComponentActionEditorDescription(
//        String componentName, Integer componentVersion, String actionName,
//        ComponentOperationRequestModel componentOperationRequestModel) {
//
//        return ResponseEntity.ok(
//            actionDefinitionFacade.executeEditorDescription(
//                componentName, componentVersion, actionName, componentOperationRequestModel.getParameters(),
//                componentOperationRequestModel.getConnectionId()));
//    }
//
//    @Override
//    public ResponseEntity<ComponentOutputSchemaModel> getComponentActionOutputSchema(
//        String componentName, Integer componentVersion, String actionName,
//        ComponentOperationRequestModel componentOperationRequestModel) {
//
//        return ResponseEntity.ok(
//            conversionService.convert(
//                actionDefinitionFacade.executeOutputSchema(
//                    componentName, componentVersion, actionName, componentOperationRequestModel.getParameters(),
//                    componentOperationRequestModel.getConnectionId()),
//                ComponentOutputSchemaModel.class));
//    }
//
//    @Override
//    public ResponseEntity<List<PropertyModel>> getComponentActionPropertyDynamicProperties(
//        String componentName, Integer componentVersion, String actionName, String propertyName,
//        ComponentOperationRequestModel componentOperationRequestModel) {
//
//        return ResponseEntity.ok(
//            CollectionUtils.map(
//                actionDefinitionFacade.executeDynamicProperties(
//                    componentName, componentVersion, actionName, propertyName,
//                    componentOperationRequestModel.getParameters(),
//                    componentOperationRequestModel.getConnectionId()),
//                property -> conversionService.convert(property, PropertyModel.class)));
//    }
//
//    @Override
//    public ResponseEntity<List<OptionModel>> getComponentActionPropertyOptions(
//        String componentName, Integer componentVersion, String actionName, String propertyName, String searchText,
//        ComponentOperationRequestModel componentOperationRequestModel) {
//
//        return ResponseEntity.ok(
//            CollectionUtils.map(
//                actionDefinitionFacade.executeOptions(
//                    componentName, componentVersion, actionName, propertyName,
//                    componentOperationRequestModel.getParameters(), componentOperationRequestModel.getConnectionId(),
//                    searchText),
//                option -> conversionService.convert(option, OptionModel.class)));
//    }
}
