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

package com.bytechef.component.shopify.action;

import static com.bytechef.component.OpenApiComponentHandler.PropertyType;
import static com.bytechef.component.definition.ComponentDsl.action;
import static com.bytechef.component.definition.ComponentDsl.integer;

import com.bytechef.component.definition.ComponentDsl;
import com.bytechef.component.definition.OptionsDataSource;
import com.bytechef.component.shopify.util.ShopifyUtils;
import java.util.Map;

/**
 * Provides a list of the component actions.
 *
 * @generated
 */
public class ShopifyDeleteOrderAction {
    public static final ComponentDsl.ModifiableActionDefinition ACTION_DEFINITION = action("deleteOrder")
        .title("Delete Order")
        .description("Deletes an order. Orders that interact with an online gateway can't be deleted.")
        .metadata(
            Map.of(
                "method", "DELETE",
                "path", "/orders/{orderId}.json"

            ))
        .properties(integer("orderId").label("Order ID")
            .description("ID of the order to delete.")
            .required(true)
            .options((OptionsDataSource.ActionOptionsFunction<Long>) ShopifyUtils::getOrderIdOptions)
            .metadata(
                Map.of(
                    "type", PropertyType.PATH)));

    private ShopifyDeleteOrderAction() {
    }
}
