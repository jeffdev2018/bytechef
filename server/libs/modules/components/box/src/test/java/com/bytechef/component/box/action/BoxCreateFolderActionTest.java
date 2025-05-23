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

package com.bytechef.component.box.action;

import static com.bytechef.component.box.constant.BoxConstants.ID;
import static com.bytechef.component.box.constant.BoxConstants.NAME;
import static com.bytechef.component.box.constant.BoxConstants.PARENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.bytechef.component.definition.Context.Http;
import com.bytechef.component.definition.TypeReference;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * @author Monika Domiter
 */
class BoxCreateFolderActionTest extends AbstractBoxActionTest {

    @Test
    void testPerform() {
        when(mockedParameters.getRequiredString(NAME))
            .thenReturn("new folder");
        when(mockedParameters.getRequiredString(ID))
            .thenReturn("123id");

        when(mockedExecutor.body(bodyArgumentCaptor.capture()))
            .thenReturn(mockedExecutor);
        when(mockedExecutor.execute())
            .thenReturn(mockedResponse);
        when(mockedResponse.getBody(any(TypeReference.class)))
            .thenReturn(responseMap);

        Object result = BoxCreateFolderAction.perform(mockedParameters, mockedParameters, mockedContext);

        assertEquals(responseMap, result);

        Http.Body body = bodyArgumentCaptor.getValue();

        assertEquals(Map.of(NAME, "new folder", PARENT, Map.of(ID, "123id")), body.getContent());
    }

}
