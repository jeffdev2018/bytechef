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

package com.bytechef.platform.component.service;

import com.bytechef.component.definition.UnifiedApiDefinition;
import com.bytechef.component.definition.unified.base.adapter.ProviderModelAdapter;
import com.bytechef.component.definition.unified.base.mapper.ProviderModelMapper;
import com.bytechef.component.definition.unified.base.model.ProviderInputModel;
import com.bytechef.component.definition.unified.base.model.ProviderOutputModel;
import com.bytechef.component.definition.unified.base.model.UnifiedInputModel;
import com.bytechef.component.definition.unified.base.model.UnifiedOutputModel;
import com.bytechef.platform.component.domain.ComponentDefinition;
import java.util.List;

/**
 * @author Ivica Cardic
 */
public interface UnifiedApiDefinitionService {

    List<ComponentDefinition> getUnifiedApiComponentDefinitions(UnifiedApiDefinition.UnifiedApiCategory category);

    ProviderModelAdapter<? super ProviderInputModel, ? extends ProviderOutputModel> getUnifiedApiProviderModelAdapter(
        String componentName, UnifiedApiDefinition.UnifiedApiCategory category,
        UnifiedApiDefinition.ModelType modelType);

    ProviderModelMapper<? super UnifiedInputModel, ? extends UnifiedOutputModel, ? extends ProviderInputModel, ? super ProviderOutputModel>
        getUnifiedApiProviderModelMapper(
            String componentName, UnifiedApiDefinition.UnifiedApiCategory category,
            UnifiedApiDefinition.ModelType modelType);
}
