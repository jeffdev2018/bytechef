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

package com.bytechef.platform.ai.repository.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Map;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

/**
 * @author Marko Kriskovic
 */
@ReadingConverter
@SuppressFBWarnings("EI_EXPOSE_REP2")
public class PGobjectToMapConverter implements Converter<PGobject, Map<String, Object>> {

    private final ObjectMapper objectMapper;

    public PGobjectToMapConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Map<String, Object> convert(PGobject source) {
        try {
            return Map.copyOf(objectMapper.readValue(source.getValue(), Map.class));
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert PGobject to Map", e);
        }
    }
}
