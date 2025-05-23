/*
 * Copyright 2025 ByteChef
 *
 * Licensed under the ByteChef Enterprise license (the "Enterprise License");
 * you may not use this file except in compliance with the Enterprise License.
 */

package com.bytechef.ee.remote.client;

import com.bytechef.tenant.TenantContext;
import com.bytechef.tenant.constant.TenantConstants;
import java.net.URI;
import java.util.function.Function;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

/**
 * @version ee
 *
 * @author Ivica Cardic
 */
public class AbstractRestClient {

    private final RestClient.Builder restClientBuilder;

    public AbstractRestClient(RestClient.Builder restClientBuilder) {
        this.restClientBuilder = restClientBuilder;
    }

    @Retryable
    public void delete(Function<UriBuilder, URI> uriFunction) {
        restClientBuilder
            .build()
            .delete()
            .uri(uriFunction)
            .header(TenantConstants.CURRENT_TENANT_ID, TenantContext.getCurrentTenantId())
            .retrieve()
            .toBodilessEntity();
    }

    @Retryable
    public void get(Function<UriBuilder, URI> uriFunction) {
        restClientBuilder
            .build()
            .get()
            .uri(uriFunction)
            .header(TenantConstants.CURRENT_TENANT_ID, TenantContext.getCurrentTenantId())
            .retrieve()
            .toBodilessEntity();
    }

    @Retryable
    public <T> T get(Function<UriBuilder, URI> uriFunction, Class<T> responseClass) {
        return restClientBuilder
            .build()
            .get()
            .uri(uriFunction)
            .header(TenantConstants.CURRENT_TENANT_ID, TenantContext.getCurrentTenantId())
            .retrieve()
            .body(responseClass);
    }

    @Retryable
    public <T> T get(Function<UriBuilder, URI> uriFunction, ParameterizedTypeReference<T> responseTypeRef) {
        return restClientBuilder
            .build()
            .get()
            .uri(uriFunction)
            .header(TenantConstants.CURRENT_TENANT_ID, TenantContext.getCurrentTenantId())
            .retrieve()
            .body(responseTypeRef);
    }

    @Retryable
    public void post(Function<UriBuilder, URI> uriFunction, Object bodyValue) {
        RestClient.RequestBodySpec requestBodySpec = restClientBuilder
            .build()
            .post()
            .uri(uriFunction)
            .header(TenantConstants.CURRENT_TENANT_ID, TenantContext.getCurrentTenantId());

        if (bodyValue != null) {
            requestBodySpec.body(bodyValue);
        }

        requestBodySpec.retrieve()
            .toBodilessEntity();
    }

    @Retryable
    public <T> T post(Function<UriBuilder, URI> uriFunction, Object bodyValue, Class<T> responseClass) {
        RestClient.RequestBodySpec requestBodySpec = restClientBuilder
            .build()
            .post()
            .uri(uriFunction)
            .header(TenantConstants.CURRENT_TENANT_ID, TenantContext.getCurrentTenantId());

        if (bodyValue != null) {
            requestBodySpec.body(bodyValue);
        }

        return requestBodySpec.retrieve()
            .body(responseClass);
    }

    @Retryable
    public <T> T post(
        Function<UriBuilder, URI> uriFunction, Object bodyValue, ParameterizedTypeReference<T> responseTypeRef) {

        RestClient.RequestBodySpec requestBodySpec = restClientBuilder
            .build()
            .post()
            .uri(uriFunction)
            .header(TenantConstants.CURRENT_TENANT_ID, TenantContext.getCurrentTenantId());

        if (bodyValue != null) {
            requestBodySpec.body(bodyValue);
        }

        return requestBodySpec.retrieve()
            .body(responseTypeRef);
    }

    @Retryable
    public void put(Function<UriBuilder, URI> uriFunction, Object bodyValue) {
        RestClient.RequestBodySpec requestBodySpec = restClientBuilder
            .build()
            .put()
            .uri(uriFunction)
            .header(TenantConstants.CURRENT_TENANT_ID, TenantContext.getCurrentTenantId());

        if (bodyValue != null) {
            requestBodySpec.body(bodyValue);
        }

        requestBodySpec.retrieve()
            .toBodilessEntity();
    }

    @Retryable
    public <T> T put(Function<UriBuilder, URI> uriFunction, Object bodyValue, Class<T> responseClass) {
        RestClient.RequestBodySpec requestBodySpec = restClientBuilder
            .build()
            .put()
            .uri(uriFunction)
            .header(TenantConstants.CURRENT_TENANT_ID, TenantContext.getCurrentTenantId());

        if (bodyValue != null) {
            requestBodySpec.body(bodyValue);
        }

        return requestBodySpec.retrieve()
            .body(responseClass);
    }

    @Retryable
    public <T> T put(
        Function<UriBuilder, URI> uriFunction, Object bodyValue, ParameterizedTypeReference<T> responseTypeRef) {

        RestClient.RequestBodySpec requestBodySpec = restClientBuilder
            .build()
            .put()
            .uri(uriFunction)
            .header(TenantConstants.CURRENT_TENANT_ID, TenantContext.getCurrentTenantId());

        if (bodyValue != null) {
            requestBodySpec.body(bodyValue);
        }

        return requestBodySpec.retrieve()
            .body(responseTypeRef);
    }
}
