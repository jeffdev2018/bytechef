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

package com.bytechef.component.webflow.util;

import static com.bytechef.component.definition.ComponentDsl.option;
import static com.bytechef.component.webflow.constant.WebflowConstants.DISPLAY_NAME;
import static com.bytechef.component.webflow.constant.WebflowConstants.ID;
import static com.bytechef.component.webflow.constant.WebflowConstants.ORDER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.bytechef.component.definition.Context;
import com.bytechef.component.definition.Context.Http;
import com.bytechef.component.definition.Option;
import com.bytechef.component.definition.Parameters;
import com.bytechef.component.definition.TypeReference;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Monika Kušter
 */
class WebflowUtilsTest {

    private final List<Option<String>> expectedOptions = List.of(option("abc", "123"));
    private final Context mockedContext = mock(Context.class);
    private final Http.Executor mockedExecutor = mock(Http.Executor.class);
    private final Parameters mockedParameters = mock(Parameters.class);
    private final Http.Response mockedResponse = mock(Http.Response.class);

    @BeforeEach
    void beforeEach() {
        when(mockedContext.http(any()))
            .thenReturn(mockedExecutor);
        when(mockedExecutor.configuration(any()))
            .thenReturn(mockedExecutor);
        when(mockedExecutor.execute())
            .thenReturn(mockedResponse);
    }

    @Test
    void testGetItemIdOptions() {
        when(mockedResponse.getBody(any(TypeReference.class)))
            .thenReturn(Map.of("items", List.of(Map.of(ID, "123", "fieldData", Map.of("name", "abc")))));

        assertEquals(
            expectedOptions,
            WebflowUtils.getItemIdOptions(mockedParameters, mockedParameters, Map.of(), "",
                mockedContext));
    }

    @Test
    void testGetCollectionIdOptions() {
        when(mockedResponse.getBody(any(TypeReference.class)))
            .thenReturn(Map.of("collections", List.of(Map.of(ID, "123", DISPLAY_NAME, "abc"))));

        assertEquals(
            expectedOptions,
            WebflowUtils.getCollectionIdOptions(mockedParameters, mockedParameters, Map.of(), "", mockedContext));
    }

    @Test
    void testGetOrderIdOptions() {
        when(mockedResponse.getBody(any(TypeReference.class)))
            .thenReturn(Map.of("orders", List.of(Map.of(ORDER_ID, "123"))));

        assertEquals(
            List.of(option("123", "123")),
            WebflowUtils.getOrderIdOptions(mockedParameters, mockedParameters, Map.of(), "", mockedContext));
    }

    @Test
    void testGetSiteIdOptions() {
        when(mockedResponse.getBody(any(TypeReference.class)))
            .thenReturn(Map.of("sites", List.of(Map.of(ID, "123", DISPLAY_NAME, "abc"))));

        assertEquals(
            expectedOptions,
            WebflowUtils.getSiteIdOptions(mockedParameters, mockedParameters, Map.of(), "", mockedContext));
    }
}
