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

package com.bytechef.component.ods.file.action;

import static com.bytechef.component.definition.ComponentDsl.action;
import static com.bytechef.component.definition.ComponentDsl.array;
import static com.bytechef.component.definition.ComponentDsl.bool;
import static com.bytechef.component.definition.ComponentDsl.date;
import static com.bytechef.component.definition.ComponentDsl.dateTime;
import static com.bytechef.component.definition.ComponentDsl.fileEntry;
import static com.bytechef.component.definition.ComponentDsl.integer;
import static com.bytechef.component.definition.ComponentDsl.nullable;
import static com.bytechef.component.definition.ComponentDsl.number;
import static com.bytechef.component.definition.ComponentDsl.object;
import static com.bytechef.component.definition.ComponentDsl.outputSchema;
import static com.bytechef.component.definition.ComponentDsl.string;
import static com.bytechef.component.definition.ComponentDsl.time;
import static com.bytechef.component.ods.file.constant.OdsFileConstants.FILENAME;
import static com.bytechef.component.ods.file.constant.OdsFileConstants.ROWS;
import static com.bytechef.component.ods.file.constant.OdsFileConstants.SHEET_NAME;

import com.bytechef.component.definition.ActionContext;
import com.bytechef.component.definition.ComponentDsl.ModifiableActionDefinition;
import com.bytechef.component.definition.FileEntry;
import com.bytechef.component.definition.Parameters;
import com.github.miachm.sods.Range;
import com.github.miachm.sods.Sheet;
import com.github.miachm.sods.SpreadSheet;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.Validate;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class OdsFileWriteAction {

    public static final ModifiableActionDefinition ACTION_DEFINITION = action("write")
        .title("Write to File")
        .description("Writes the data to a ODS file.")
        .properties(
            string(SHEET_NAME)
                .label("Sheet Name")
                .description("The name of the sheet to create in the spreadsheet.")
                .defaultValue("Sheet")
                .advancedOption(true),
            array(ROWS)
                .label("Rows")
                .description("The array of rows to write to the file.")
                .required(true)
                .placeholder("Add Row")
                .items(
                    object()
                        .placeholder("Add Column")
                        .additionalProperties(
                            bool(), date(), dateTime(), integer(), nullable(), number(), string(), time())),
            string(FILENAME)
                .label("Filename")
                .description(
                    "Filename to set for binary data. By default, \"file.ods\" will be used.")
                .required(true)
                .defaultValue("file.ods")
                .advancedOption(true))
        .output(outputSchema(fileEntry()))
        .perform(OdsFileWriteAction::perform);

    private static Object[] getHeaderValues(Set<String> names) {
        Validate.notNull(names, "'names' must not be null");

        if (names.isEmpty()) {
            throw new IllegalArgumentException("Unable to create header values with empty names collection");
        }

        Object[] values = new Object[names.size()];

        int idx = 0;

        for (Object value : names) {
            values[idx++] = value;
        }

        return values;
    }

    @SuppressWarnings({
        "rawtypes", "unchecked"
    })
    protected static FileEntry perform(
        Parameters inputParameters, Parameters connectionParameters, ActionContext context) {

        String fileName = inputParameters.getString(FILENAME, "file.ods");
        List<Map<String, ?>> rows = (List) inputParameters.getList(ROWS, List.of());
        String sheetName = inputParameters.getString(SHEET_NAME, "Sheet");

        return context.file(file -> file.storeContent(
            fileName, new ByteArrayInputStream(write(rows, new WriteConfiguration(fileName, sheetName)))));
    }

    private static byte[] write(List<Map<String, ?>> rows, WriteConfiguration configuration) throws IOException {
        Map<String, ?> rowMap = rows.get(0);

        Object[] headerValues = getHeaderValues(rowMap.keySet());
        Object[][] values = new Object[rows.size() + 1][headerValues.length];

        values[0] = headerValues;

        for (int i = 0; i < rows.size(); i++) {
            Map<String, ?> row = rows.get(i);

            for (int j = 0; j < headerValues.length; j++) {
                values[i + 1][j] = row.get(headerValues[j]);
            }
        }

        Sheet sheet = new Sheet(configuration.sheetName(), rows.size() + 1, headerValues.length);

        SpreadSheet spreadSheet = new SpreadSheet();

        spreadSheet.appendSheet(sheet);

        Range range = sheet.getDataRange();

        range.setValues(values);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        spreadSheet.save(byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }

    private record WriteConfiguration(String fileName, String sheetName) {
    }
}
