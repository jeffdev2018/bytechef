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

package com.bytechef.component.microsoft.share.point.action;

import static com.bytechef.component.microsoft.share.point.constant.MicrosoftSharePointConstants.COLUMNS;
import static com.bytechef.component.microsoft.share.point.constant.MicrosoftSharePointConstants.FIELDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.bytechef.component.definition.Context.Http;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * @author Monika Domiter
 */
class MicrosoftSharePointCreateListItemActionTest extends AbstractMicrosoftSharePointActionTest {

    @Test
    void testPerform() {
        when(mockedParameters.getMap(COLUMNS, Map.of()))
            .thenReturn(Map.of());

        Object result =
            MicrosoftSharePointCreateListItemAction.perform(mockedParameters, mockedParameters, mockedContext);

        assertEquals(responseMap, result);

        Http.Body body = bodyArgumentCaptor.getValue();

        assertEquals(Map.of(FIELDS, Map.of()), body.getContent());
    }

}
