/*
 * Copyright 2025 ByteChef
 *
 * Licensed under the ByteChef Enterprise license (the "Enterprise License");
 * you may not use this file except in compliance with the Enterprise License.
 */

package com.bytechef.ee.atlas.execution.remote.client.service;

import com.bytechef.atlas.execution.domain.Context.Classname;
import com.bytechef.atlas.execution.service.ContextService;
import com.bytechef.ee.remote.client.LoadBalancedRestClient;
import com.bytechef.file.storage.domain.FileEntry;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

/**
 * @version ee
 *
 * @author Ivica Cardic
 */
@Component
public class RemoteContextServiceClient implements ContextService {

    private static final String EXECUTION_APP = "execution-app";
    private static final String INTERNAL_CONTEXT_SERVICE = "/remote/context-service";

    private final LoadBalancedRestClient loadBalancedRestClient;

    @SuppressFBWarnings("EI")
    public RemoteContextServiceClient(LoadBalancedRestClient loadBalancedRestClient) {
        this.loadBalancedRestClient = loadBalancedRestClient;
    }

    @Override
    public FileEntry peek(long stackId, Classname classname) {
        return loadBalancedRestClient.get(
            uriBuilder -> uriBuilder
                .host(EXECUTION_APP)
                .path(INTERNAL_CONTEXT_SERVICE + "/peek/{stackId}/{classname}")
                .build(stackId, classname),
            FileEntry.class);
    }

    @Override
    public FileEntry peek(long stackId, int subStackId, Classname classname) {
        return loadBalancedRestClient.get(
            uriBuilder -> uriBuilder
                .host(EXECUTION_APP)
                .path(INTERNAL_CONTEXT_SERVICE + "/peek/{stackId}/{subStackId}/{classname}")
                .build(stackId, subStackId, classname),
            FileEntry.class);
    }

    @Override
    public void push(long stackId, Classname classname, FileEntry value) {
        loadBalancedRestClient.post(
            uriBuilder -> uriBuilder
                .host(EXECUTION_APP)
                .path(INTERNAL_CONTEXT_SERVICE + "/push/{stackId}/{classname}")
                .build(stackId, classname),
            value, FileEntry.class);
    }

    @Override
    public void push(long stackId, int subStackId, Classname classname, FileEntry value) {
        loadBalancedRestClient.post(
            uriBuilder -> uriBuilder
                .host(EXECUTION_APP)
                .path(INTERNAL_CONTEXT_SERVICE + "/push/{stackId}/{subStackId}/{classname}")
                .build(stackId, classname),
            value, new ParameterizedTypeReference<Map<String, Object>>() {});
    }
}
