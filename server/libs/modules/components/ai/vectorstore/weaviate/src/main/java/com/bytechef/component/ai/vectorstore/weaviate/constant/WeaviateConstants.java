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

package com.bytechef.component.ai.vectorstore.weaviate.constant;

import com.bytechef.component.ai.vectorstore.VectorStore;
import com.bytechef.component.exception.ProviderException;
import io.weaviate.client.Config;
import io.weaviate.client.WeaviateAuthClient;
import io.weaviate.client.WeaviateClient;
import io.weaviate.client.v1.auth.exception.AuthException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.ai.vectorstore.weaviate.WeaviateVectorStore;

/**
 * @author Monika Kušter
 */
public class WeaviateConstants {

    public static final String API_KEY = "apiKey";
    public static final String URL = "url";
    public static final String WEAVIATE = "weaviate";

    private WeaviateConstants() {
    }

    public static final VectorStore VECTOR_STORE = (connectionParameters, embeddingModel) -> {
        Pattern pattern = Pattern.compile("^(http|https)://([^/]+)");
        Matcher matcher = pattern.matcher(connectionParameters.getRequiredString(URL));

        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid URL");
        }

        WeaviateClient weaviateClient;

        try {
            weaviateClient = WeaviateAuthClient.apiKey(
                new Config(matcher.group(1), matcher.group(2)), connectionParameters.getRequiredString(API_KEY));
        } catch (AuthException authException) {
            throw new ProviderException("Authentication failed", authException);
        }

        return WeaviateVectorStore.builder(weaviateClient, embeddingModel)
            .build();
    };
}
