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

package com.bytechef.atlas.coordinator.event;

import com.bytechef.atlas.coordinator.message.route.TaskCoordinatorMessageRoute;
import com.bytechef.atlas.execution.domain.TaskExecution;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * @author Ivica Cardic
 */
public class TaskExecutionCompleteEvent extends AbstractEvent {

    private TaskExecution taskExecution;

    private TaskExecutionCompleteEvent() {
    }

    @SuppressFBWarnings("EI")
    public TaskExecutionCompleteEvent(TaskExecution taskExecution) {
        super(TaskCoordinatorMessageRoute.TASK_EXECUTION_COMPLETE_EVENTS);

        this.taskExecution = taskExecution;
    }

    @SuppressFBWarnings("EI")
    public TaskExecution getTaskExecution() {
        return taskExecution;
    }

    @Override
    public String toString() {
        return "TaskExecutionCompleteEvent{" +
            "taskExecution=" + taskExecution +
            ", createdDate=" + createDate +
            ", route=" + route +
            "} ";
    }
}
