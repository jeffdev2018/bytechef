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

package com.bytechef.platform.connection.service;

import com.bytechef.platform.connection.domain.Connection;
import com.bytechef.platform.connection.domain.ConnectionEnvironment;
import com.bytechef.platform.constant.ModeType;
import java.util.List;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public interface ConnectionService {

    Connection create(Connection connection);

    void delete(long id);

    Connection getConnection(long id);

    List<Connection> getConnections(ModeType type);

    List<Connection> getConnections(String componentName, int version, ModeType type);

    List<Connection> getConnections(
        String componentName, Integer connectionVersion, ConnectionEnvironment connectionEnvironment, Long tagId,
        ModeType type);

    Connection update(long id, List<Long> tagIds);

    Connection update(long id, String name, List<Long> tagIds, int version);

    Connection updateConnectionCredentialStatus(long connectionId, Connection.CredentialStatus status);

    Connection updateConnectionParameters(long connectionId, Map<String, ?> parameters);
}
