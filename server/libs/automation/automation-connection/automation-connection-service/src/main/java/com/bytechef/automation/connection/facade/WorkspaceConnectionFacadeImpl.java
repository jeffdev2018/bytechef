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

package com.bytechef.automation.connection.facade;

import com.bytechef.automation.connection.domain.WorkspaceConnection;
import com.bytechef.automation.connection.service.WorkspaceConnectionService;
import com.bytechef.commons.util.CollectionUtils;
import com.bytechef.platform.connection.domain.ConnectionEnvironment;
import com.bytechef.platform.connection.dto.ConnectionDTO;
import com.bytechef.platform.connection.facade.ConnectionFacade;
import com.bytechef.platform.constant.ModeType;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ivica Cardic
 */
@Service
@Transactional
public class WorkspaceConnectionFacadeImpl implements WorkspaceConnectionFacade {

    private final ConnectionFacade connectionFacade;
    private final WorkspaceConnectionService workspaceConnectionService;

    @SuppressFBWarnings("EI")
    public WorkspaceConnectionFacadeImpl(
        ConnectionFacade connectionFacade, WorkspaceConnectionService workspaceConnectionService) {

        this.connectionFacade = connectionFacade;
        this.workspaceConnectionService = workspaceConnectionService;
    }

    @Override
    public long create(long workspaceId, ConnectionDTO connectionDTO) {
        long connectionId = connectionFacade.create(connectionDTO, ModeType.AUTOMATION);

        workspaceConnectionService.create(connectionId, workspaceId);

        return connectionId;
    }

    @Override
    public void delete(long connectionId) {
        workspaceConnectionService.deleteWorkspaceConnection(connectionId);

        connectionFacade.delete(connectionId);
    }

    @Override
    public List<ConnectionDTO> getConnections(
        long workspaceId, String componentName, Integer connectionVersion, ConnectionEnvironment connectionEnvironment,
        Long tagId) {

        List<Long> connectionIds = CollectionUtils.map(
            workspaceConnectionService.getWorkspaceConnections(workspaceId), WorkspaceConnection::getConnectionId);

        return CollectionUtils.filter(
            connectionFacade.getConnections(
                componentName, connectionVersion, connectionEnvironment, tagId, ModeType.AUTOMATION),
            connectionDTO -> connectionIds.contains(connectionDTO.id()));
    }
}
