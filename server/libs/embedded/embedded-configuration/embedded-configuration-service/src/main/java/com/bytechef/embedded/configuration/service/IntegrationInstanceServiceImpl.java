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

package com.bytechef.embedded.configuration.service;

import com.bytechef.embedded.configuration.domain.IntegrationInstance;
import com.bytechef.embedded.configuration.repository.IntegrationInstanceRepository;
import com.bytechef.platform.constant.Environment;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ivica Cardic
 */
@Service
@Transactional
public class IntegrationInstanceServiceImpl implements IntegrationInstanceService {

    private final IntegrationInstanceRepository integrationInstanceRepository;

    @SuppressFBWarnings("EI")
    public IntegrationInstanceServiceImpl(IntegrationInstanceRepository integrationInstanceRepository) {
        this.integrationInstanceRepository = integrationInstanceRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<IntegrationInstance> getConnectedUserIntegrationInstances(long connectedUserId) {
        return integrationInstanceRepository.findAllByConnectedUserId(connectedUserId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IntegrationInstance> getConnectedUserIntegrationInstances(long connectedUserId, boolean enabled) {
        return integrationInstanceRepository.findAllByConnectedUserIdAndEnabled(connectedUserId, enabled);
    }

    @Override
    public List<IntegrationInstance> getConnectedUserIntegrationInstances(
        long connectedUserId, Environment environment) {

        return integrationInstanceRepository.findAllByConnectedUserIdAndEnvironment(
            connectedUserId, environment.ordinal());
    }

    @Override
    @Transactional(readOnly = true)
    public List<IntegrationInstance> getConnectedUserIntegrationInstances(List<Long> connectedUserIds) {
        return integrationInstanceRepository.findAllByConnectedUserIdIn(connectedUserIds);
    }

    @Override
    public Optional<IntegrationInstance> fetchFirstIntegrationInstance(
        long connectedUserId, String componentName, Environment environment) {

        return integrationInstanceRepository.findFirstByConnectedUserIdIdAndComponentNameAndEnvironment(
            connectedUserId, componentName, environment.ordinal());
    }

    @Override
    public IntegrationInstance getIntegrationInstance(
        long connectedUserId, List<String> componentNames, Environment environment) {

        return integrationInstanceRepository.findFirstByConnectedUserIdIdAndComponentNamesAndEnvironment(
            connectedUserId, componentNames, environment.ordinal());
    }

    @Override
    @Transactional(readOnly = true)
    public IntegrationInstance getIntegrationInstance(long id) {
        return integrationInstanceRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Integration instance not found"));
    }

    @Override
    public IntegrationInstance getIntegrationInstance(
        long connectedUserId, String workflowId, Environment environment) {

        return integrationInstanceRepository
            .findByWorkflowIdAndEnvironment(connectedUserId, workflowId, environment.ordinal())
            .orElseThrow(() -> new IllegalArgumentException("Integration instance not found"));
    }

    @Override
    public List<IntegrationInstance> getIntegrationInstances(long integrationInstanceConfigurationId) {
        return integrationInstanceRepository.findAllByIntegrationInstanceConfigurationId(
            integrationInstanceConfigurationId);
    }

    @Override
    public void updateEnabled(long id, boolean enable) {
        IntegrationInstance integrationInstance = getIntegrationInstance(id);

        integrationInstance.setEnabled(enable);

        integrationInstanceRepository.save(integrationInstance);
    }
}
