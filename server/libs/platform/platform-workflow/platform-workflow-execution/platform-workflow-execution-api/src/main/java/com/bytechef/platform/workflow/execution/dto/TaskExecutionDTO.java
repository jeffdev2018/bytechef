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

package com.bytechef.platform.workflow.execution.dto;

import com.bytechef.atlas.configuration.domain.WorkflowTask;
import com.bytechef.atlas.execution.domain.TaskExecution;
import com.bytechef.error.ExecutionError;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.time.Instant;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
@SuppressFBWarnings("EI")
public record TaskExecutionDTO(
    String createdBy, Instant createdDate, Instant endDate, ExecutionError error, long executionTime,
    String icon, Long id, Map<String, ?> input, Long jobId, String lastModifiedBy, Instant lastModifiedDate,
    int maxRetries, Object output, Long parentId, int priority, int progress, int retryAttempts, String retryDelay,
    int retryDelayFactor, long retryDelayMillis, Instant startDate, TaskExecution.Status status, int taskNumber,
    String title, String type, WorkflowTask workflowTask) {

    public TaskExecutionDTO(
        TaskExecution taskExecution, String title, String icon, Map<String, ?> input, Object output) {

        this(
            taskExecution.getCreatedBy(), taskExecution.getCreatedDate(),
            taskExecution.getEndDate(), taskExecution.getError(), taskExecution.getExecutionTime(), icon,
            taskExecution.getId(), input, taskExecution.getJobId(), taskExecution.getLastModifiedBy(),
            taskExecution.getLastModifiedDate(), taskExecution.getMaxRetries(), output,
            taskExecution.getParentId(), taskExecution.getPriority(), taskExecution.getProgress(),
            taskExecution.getRetryAttempts(), taskExecution.getRetryDelay(), taskExecution.getRetryDelayFactor(),
            taskExecution.getRetryDelayMillis(), taskExecution.getStartDate(), taskExecution.getStatus(),
            taskExecution.getTaskNumber(), title, taskExecution.getType(), taskExecution.getWorkflowTask());
    }
}
