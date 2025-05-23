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

package com.bytechef.component.pipedrive.unified.adapter;

import com.bytechef.component.definition.Context;
import com.bytechef.component.definition.Parameters;
import com.bytechef.component.definition.unified.crm.adapter.ProviderContactAdapter;
import com.bytechef.component.pipedrive.unified.model.PipedriveContactInputModel;
import com.bytechef.component.pipedrive.unified.model.PipedriveContactOutputModel;

/**
 * @author Ivica Cardic
 */
public class PipedriveContactAdapter
    implements ProviderContactAdapter<PipedriveContactInputModel, PipedriveContactOutputModel> {

    @Override
    public String create(PipedriveContactInputModel inputModel, Parameters connectionParameters, Context context) {
        return null;
    }

    @Override
    public void delete(String id, Parameters connectionParameters, Context context) {

    }

    @Override
    public PipedriveContactOutputModel get(String id, Parameters connectionParameters, Context context) {
        return null;
    }

    @Override
    public Page<PipedriveContactOutputModel> getPage(
        Parameters connectionParameters, Parameters cursorParameters, Context context) {

        return null;
    }

    @Override
    public void update(
        String id, PipedriveContactInputModel inputModel, Parameters connectionParameters, Context context) {
    }
}
