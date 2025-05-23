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

package com.bytechef.embedded.configuration.dto;

import com.bytechef.embedded.configuration.domain.IntegrationInstanceWorkflow;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.time.Instant;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
@SuppressFBWarnings("EI")
public record IntegrationInstanceWorkflowDTO(
    String createdBy, Instant createdDate, Long id, long integrationInstanceConfigurationWorkflowId,
    Map<String, ?> inputs, String lastModifiedBy, Instant lastModifiedDate, boolean enabled, String workflowId) {
    public IntegrationInstanceWorkflowDTO(IntegrationInstanceWorkflow integrationInstanceWorkflow, String workflowId) {
        this(
            integrationInstanceWorkflow.getCreatedBy(), integrationInstanceWorkflow.getCreatedDate(),
            integrationInstanceWorkflow.getId(),
            integrationInstanceWorkflow.getIntegrationInstanceConfigurationWorkflowId(),
            integrationInstanceWorkflow.getInputs(), integrationInstanceWorkflow.getLastModifiedBy(),
            integrationInstanceWorkflow.getLastModifiedDate(), integrationInstanceWorkflow.isEnabled(), workflowId);
    }
}
