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

package com.bytechef.component.rabbitmq.action;

import static com.bytechef.component.definition.ComponentDsl.action;
import static com.bytechef.component.definition.ComponentDsl.object;
import static com.bytechef.component.definition.ComponentDsl.string;
import static com.bytechef.component.rabbitmq.constant.RabbitMqConstants.HOSTNAME;
import static com.bytechef.component.rabbitmq.constant.RabbitMqConstants.MESSAGE;
import static com.bytechef.component.rabbitmq.constant.RabbitMqConstants.PASSWORD;
import static com.bytechef.component.rabbitmq.constant.RabbitMqConstants.PORT;
import static com.bytechef.component.rabbitmq.constant.RabbitMqConstants.QUEUE;
import static com.bytechef.component.rabbitmq.constant.RabbitMqConstants.USERNAME;

import com.bytechef.component.definition.ActionContext;
import com.bytechef.component.definition.ComponentDsl.ModifiableActionDefinition;
import com.bytechef.component.definition.Parameters;
import com.bytechef.component.rabbitmq.util.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author Ivica Cardic
 */
public class RabbitMqSendMessageAction {

    public static final ModifiableActionDefinition ACTION_DEFINITION = action("sendMessage")
        .title("Send Message")
        .description("Send a new RabbitMQ message.")
        .properties(
            string(QUEUE)
                .description("The name of the queue to read from")
                .required(true),
            object(MESSAGE)
                .description("The name of the queue to read from")
                .required(true))
        .perform(RabbitMqSendMessageAction::perform);

    protected static Object perform(
        Parameters inputParameters, Parameters connectionParameters, ActionContext context)
        throws IOException, TimeoutException {

        try (com.rabbitmq.client.Connection rabbitMqConnection = RabbitMqUtils.getConnection(
            connectionParameters.getString(HOSTNAME),
            connectionParameters.getInteger(PORT, 5672),
            connectionParameters.getString(USERNAME),
            connectionParameters.getString(PASSWORD))) {

            Channel channel = rabbitMqConnection.createChannel();

            String queueName = inputParameters.getRequiredString(QUEUE);
            String message = context.json(json -> json.write(inputParameters.getRequired(MESSAGE)));

            channel.queueDeclare(queueName, true, false, false, null);
            channel.basicPublish("", queueName, null, message.getBytes(StandardCharsets.UTF_8));

            channel.close();
        }

        return null;
    }
}
