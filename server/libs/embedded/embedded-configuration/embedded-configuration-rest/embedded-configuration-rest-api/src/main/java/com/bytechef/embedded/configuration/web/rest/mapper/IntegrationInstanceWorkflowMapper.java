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

package com.bytechef.embedded.configuration.web.rest.mapper;

import com.bytechef.embedded.configuration.domain.IntegrationInstanceWorkflow;
import com.bytechef.embedded.configuration.dto.IntegrationInstanceWorkflowDTO;
import com.bytechef.embedded.configuration.web.rest.mapper.config.EmbeddedConfigurationMapperSpringConfig;
import com.bytechef.embedded.configuration.web.rest.model.IntegrationInstanceWorkflowModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

/**
 * @author Ivica Cardic
 */
public class IntegrationInstanceWorkflowMapper {

    @Mapper(config = EmbeddedConfigurationMapperSpringConfig.class)
    public interface IntegrationInstanceWorkflowDToIntegrationInstanceWorkflowModelMapper
        extends Converter<IntegrationInstanceWorkflow, IntegrationInstanceWorkflowModel> {

        @Override
        @Mapping(target = "workflowId", ignore = true)
        IntegrationInstanceWorkflowModel convert(IntegrationInstanceWorkflow integrationInstanceWorkflow);
    }

    @Mapper(config = EmbeddedConfigurationMapperSpringConfig.class)
    public interface IntegrationInstanceWorkflowDTOToIntegrationInstanceWorkflowModelMapper
        extends Converter<IntegrationInstanceWorkflowDTO, IntegrationInstanceWorkflowModel> {

        @Override
        IntegrationInstanceWorkflowModel convert(IntegrationInstanceWorkflowDTO integrationInstanceWorkflow);
    }
}
