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

package com.bytechef.server.config;

import com.bytechef.atlas.configuration.converter.StringToWorkflowTaskConverter;
import com.bytechef.atlas.configuration.converter.WorkflowTaskToStringConverter;
import com.bytechef.atlas.execution.repository.jdbc.converter.ExecutionErrorToStringConverter;
import com.bytechef.atlas.execution.repository.jdbc.converter.FileEntryToStringConverter;
import com.bytechef.atlas.execution.repository.jdbc.converter.StringToExecutionErrorConverter;
import com.bytechef.atlas.execution.repository.jdbc.converter.StringToFileEntryConverter;
import com.bytechef.atlas.execution.repository.jdbc.converter.StringToWebhooksConverter;
import com.bytechef.atlas.execution.repository.jdbc.converter.WebhooksToStringConverter;
import com.bytechef.commons.data.jdbc.converter.EncryptedMapWrapperToStringConverter;
import com.bytechef.commons.data.jdbc.converter.EncryptedStringToMapWrapperConverter;
import com.bytechef.commons.data.jdbc.converter.MapWrapperToStringConverter;
import com.bytechef.commons.data.jdbc.converter.StringToMapWrapperConverter;
import com.bytechef.encryption.Encryption;
import com.bytechef.platform.ai.repository.converter.ListDoubleToPGObjectConverter;
import com.bytechef.platform.ai.repository.converter.MapToPGObjectConverter;
import com.bytechef.platform.ai.repository.converter.PGObjectToListDoubleConverter;
import com.bytechef.platform.ai.repository.converter.PGobjectToMapConverter;
import com.bytechef.platform.data.storage.jdbc.repository.converter.DataEntryValueWrapperToStringConverter;
import com.bytechef.platform.data.storage.jdbc.repository.converter.StringToDataEntryValueWrapperConverter;
import com.bytechef.platform.workflow.execution.repository.converter.StringToTriggerStateValueConverter;
import com.bytechef.platform.workflow.execution.repository.converter.StringToWorkflowExecutionIdConverter;
import com.bytechef.platform.workflow.execution.repository.converter.StringToWorkflowTriggerConverter;
import com.bytechef.platform.workflow.execution.repository.converter.TriggerStateValueToStringConverter;
import com.bytechef.platform.workflow.execution.repository.converter.WorkflowExecutionIdToStringConverter;
import com.bytechef.platform.workflow.execution.repository.converter.WorkflowTriggerToStringConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

/**
 * @author Ivica Cardic
 */
@Configuration
public class JdbcConfiguration extends AbstractJdbcConfiguration {

    private final Encryption encryption;
    private final ObjectMapper objectMapper;

    @SuppressFBWarnings("EI2")
    public JdbcConfiguration(Encryption encryption, ObjectMapper objectMapper) {
        this.encryption = encryption;
        this.objectMapper = objectMapper;
    }

    @Override
    protected List<?> userConverters() {
        // TODO Use JsonUtils directly
        return Arrays.asList(
            new DataEntryValueWrapperToStringConverter(),
            new EncryptedMapWrapperToStringConverter(encryption, objectMapper),
            new EncryptedStringToMapWrapperConverter(encryption, objectMapper),
            new ExecutionErrorToStringConverter(objectMapper),
            new MapToPGObjectConverter(objectMapper),
            new PGobjectToMapConverter(objectMapper),
            new ListDoubleToPGObjectConverter(),
            new PGObjectToListDoubleConverter(),
            new FileEntryToStringConverter(objectMapper),
            new MapWrapperToStringConverter(objectMapper),
            new StringToDataEntryValueWrapperConverter(),
            new StringToExecutionErrorConverter(objectMapper),
            new StringToFileEntryConverter(objectMapper),
            new StringToMapWrapperConverter(objectMapper),
            new StringToWebhooksConverter(objectMapper),
            new StringToWorkflowExecutionIdConverter(),
            new StringToWorkflowTaskConverter(objectMapper),
            new StringToWorkflowTriggerConverter(objectMapper),
            new StringToTriggerStateValueConverter(objectMapper),
            new TriggerStateValueToStringConverter(objectMapper),
            new WebhooksToStringConverter(objectMapper),
            new WorkflowExecutionIdToStringConverter(),
            new WorkflowTaskToStringConverter(objectMapper),
            new WorkflowTriggerToStringConverter(objectMapper));
    }
}
