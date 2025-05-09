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

package com.bytechef.component.xml.file.action;

import static com.bytechef.component.xml.file.constant.XmlFileConstants.FILENAME;
import static com.bytechef.component.xml.file.constant.XmlFileConstants.SOURCE;

import com.bytechef.component.definition.ActionContext;
import com.bytechef.component.definition.Parameters;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 * @author Ivica Cardic
 */
@Disabled
public class XmlFileWriteActionTest {

    private static final String TEST_XML = "test.xml";
    private static final String FILE_XML = "file.xml";

    @Test
    public void testPerformWrite() throws IOException {
        ActionContext context = Mockito.mock(ActionContext.class);
        Map<String, Object> source = Map.of(
            "Flower",
            new LinkedHashMap<>() {
                {

                    put("id", "45");
                    put("name", "Poppy");
                    put("color", "RED");
                    put("petals", "9");
                    put("Florists",
                        Map.of(
                            "Florist",
                            List.of(
                                new LinkedHashMap<>(Map.of("name", "Joe")),
                                new LinkedHashMap<>(Map.of("name", "Mark")))));
                }
            });
        Parameters parameters = Mockito.mock(Parameters.class);

        Mockito.when(parameters.getString(Mockito.eq(FILENAME), Mockito.eq("file.xml")))
            .thenReturn("file.xml");
        Mockito.when(parameters.getRequired(Mockito.eq(SOURCE)))
            .thenReturn(source);
        Mockito.when(context.xml(Mockito.any()))
            .thenReturn(
                "<root><Flower><id>45</id><name>Poppy</name><color>RED</color><petals>9</petals><Florists><Florist><name>Joe</name></Florist><Florist><name>Mark</name></Florist></Florists></Flower></root>");

        XmlFileWriteAction.perform(parameters, parameters, context);

        ArgumentCaptor<ByteArrayInputStream> inputStreamArgumentCaptor = ArgumentCaptor.forClass(
            ByteArrayInputStream.class);
        ArgumentCaptor<String> filenameArgumentCaptor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(context)
            .file(
                file1 -> file1.storeContent(filenameArgumentCaptor.capture(), inputStreamArgumentCaptor.capture()));

        ByteArrayInputStream byteArrayInputStream = inputStreamArgumentCaptor.getValue();

        Assertions.assertThat(new String(byteArrayInputStream.readAllBytes(), StandardCharsets.UTF_8).trim())
            .isEqualTo(
                "<root><Flower><id>45</id><name>Poppy</name><color>RED</color><petals>9</petals><Florists><Florist><name>Joe</name></Florist><Florist><name>Mark</name></Florist></Florists></Flower></root>");
        Assertions.assertThat(filenameArgumentCaptor.getValue())
            .isEqualTo(FILE_XML);

        Mockito.reset(context);
        Mockito.reset(parameters);

        Mockito.when(parameters.getString(Mockito.eq(FILENAME), Mockito.eq("file.xml")))
            .thenReturn(TEST_XML);
        Mockito.when(parameters.getRequired(Mockito.eq(SOURCE)))
            .thenReturn(source);
        Mockito.when(context.xml(Mockito.any()))
            .thenReturn(
                "<root><Flower><id>45</id><name>Poppy</name><color>RED</color><petals>9</petals><Florists><Florist><name>Joe</name></Florist><Florist><name>Mark</name></Florist></Florists></Flower></root>");

        XmlFileWriteAction.perform(parameters, parameters, context);

        Mockito.verify(context)
            .file(
                file1 -> file1.storeContent(filenameArgumentCaptor.capture(), Mockito.any(InputStream.class)));

        Assertions.assertThat(filenameArgumentCaptor.getValue())
            .isEqualTo(TEST_XML);
    }

    @Test
    public void testPerformWriteArray() throws IOException {
        ActionContext context = Mockito.mock(ActionContext.class);
        List<Map<String, Object>> source = List.of(
            new LinkedHashMap<>() {
                {
                    put("id", "45");
                    put("name", "Poppy");
                }
            },
            new LinkedHashMap<>() {
                {
                    put("id", "50");
                    put("name", "Rose");
                }
            });
        Parameters parameters = Mockito.mock(Parameters.class);

        Mockito.when(parameters.getString(Mockito.eq(FILENAME), Mockito.eq("file.xml")))
            .thenReturn("file.xml");
        Mockito.when(parameters.getRequired(Mockito.eq(SOURCE)))
            .thenReturn(source);
        Mockito.when(context.xml(Mockito.any()))
            .thenReturn(
                "<root><item><id>45</id><name>Poppy</name></item><item><id>50</id><name>Rose</name></item></root>");

        XmlFileWriteAction.perform(parameters, parameters, context);

        ArgumentCaptor<ByteArrayInputStream> inputStreamArgumentCaptor = ArgumentCaptor.forClass(
            ByteArrayInputStream.class);
        ArgumentCaptor<String> filenameArgumentCaptor = ArgumentCaptor.forClass(String.class);

        Mockito.verify(context)
            .file(
                file1 -> file1.storeContent(filenameArgumentCaptor.capture(), inputStreamArgumentCaptor.capture()));

        ByteArrayInputStream byteArrayInputStream = inputStreamArgumentCaptor.getValue();

        Assertions.assertThat(new String(byteArrayInputStream.readAllBytes(), StandardCharsets.UTF_8).trim())
            .isEqualTo(
                "<root><item><id>45</id><name>Poppy</name></item><item><id>50</id><name>Rose</name></item></root>");

        Assertions.assertThat(filenameArgumentCaptor.getValue())
            .isEqualTo(FILE_XML);

        Mockito.reset(context);
        Mockito.reset(parameters);

        Mockito.when(parameters.getString(Mockito.eq(FILENAME), Mockito.eq("file.xml")))
            .thenReturn(TEST_XML);
        Mockito.when(parameters.getRequired(Mockito.eq(SOURCE)))
            .thenReturn(source);
        Mockito.when(context.xml(Mockito.any()))
            .thenReturn(
                "<root><item><id>45</id><name>Poppy</name></item><item><id>50</id><name>Rose</name></item></root>");

        XmlFileWriteAction.perform(parameters, parameters, context);

        Mockito.verify(context)
            .file(
                file1 -> file1.storeContent(filenameArgumentCaptor.capture(), Mockito.any(InputStream.class)));

        Assertions.assertThat(filenameArgumentCaptor.getValue())
            .isEqualTo(TEST_XML);
    }
}
