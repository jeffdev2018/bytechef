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

package com.bytechef.platform.workflow.worker.event;

import com.bytechef.message.event.MessageEvent;
import com.bytechef.platform.configuration.domain.CancelControlTrigger;
import com.bytechef.platform.workflow.worker.message.route.TriggerWorkerMessageRoute;
import org.springframework.util.Assert;

/**
 * @author Ivica Cardic
 */
public class CancelControlTriggerEvent extends AbstractEvent implements MessageEvent<TriggerWorkerMessageRoute> {

    private CancelControlTrigger controlTrigger;

    private CancelControlTriggerEvent() {
    }

    public CancelControlTriggerEvent(CancelControlTrigger controlTrigger) {
        Assert.notNull(controlTrigger, "'controlTrigger' must not be null");

        this.controlTrigger = controlTrigger;
    }

    public CancelControlTrigger getControlTrigger() {
        return controlTrigger;
    }

    @Override
    public TriggerWorkerMessageRoute getRoute() {
        return TriggerWorkerMessageRoute.CONTROL_EVENTS;
    }

    @Override
    public String toString() {
        return "CancelControlTriggerEvent{" +
            "controlTrigger=" + controlTrigger +
            ", createDate=" + createDate +
            "} ";
    }
}
