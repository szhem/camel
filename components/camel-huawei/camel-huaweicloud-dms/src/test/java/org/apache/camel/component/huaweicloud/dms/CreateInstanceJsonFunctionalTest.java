/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.huaweicloud.dms;

import org.apache.camel.BindToRegistry;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.huaweicloud.common.models.ServiceKeys;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateInstanceJsonFunctionalTest extends CamelTestSupport {
    private static final String AUTHENTICATION_KEY = "replace_this_with_authentication_key";
    private static final String SECRET_KEY = "replace_this_with_secret_key";
    private static final String PROJECT_ID = "replace_this_with_project_id";
    private static final String REGION = "replace_this_with_region";

    @BindToRegistry("serviceKeys")
    ServiceKeys serviceKeys = new ServiceKeys(AUTHENTICATION_KEY, SECRET_KEY);

    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() throws Exception {
                from("direct:operation")
                        .to("hwcloud-dms:createInstance?" +
                            "serviceKeys=#serviceKeys" +
                            "&projectId=" + PROJECT_ID +
                            "&region=" + REGION +
                            "&ignoreSslVerification=true")
                        .log("Operation successful")
                        .to("log:LOG?showAll=true")
                        .to("mock:operation_result");
            }
        };
    }

    /**
     * The following test cases should be manually enabled to perform test against the actual HuaweiCloud DMS server
     * with real user credentials. To perform this test, manually comment out the @Ignore annotation and enter relevant
     * service parameters in the placeholders above (static variables of this test class)
     *
     * @throws Exception
     */
    @Disabled("Manually enable this once you configure the parameters in the placeholders above")
    @Test
    public void testOperation() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:operation_result");
        mock.expectedMinimumMessageCount(1);

        // new Kafka instance options: https://support.huaweicloud.com/en-us/api-kafka/kafka-api-180514002.html
        // new RabbitMQ instance options: https://support.huaweicloud.com/en-us/api-rabbitmq/rabbitmq-api-180514002.html
        String sampleBody = "{" +
                            "\"name\":\"" + "replace_with_instance_information" + "\"," +
                            "\"description\":\"" + "replace_with_instance_information" + "\"," +
                            "\"engine\":\"" + "replace_with_instance_information" + "\"," +
                            "\"engine_version\":\"" + "replace_with_instance_information" + "\"," +
                            "\"specification\":\"" + "replace_with_instance_information" + "\"," +
                            "\"storage_space\":" + "replace_with_instance_information" + "," +
                            "\"partition_num\":" + "replace_with_instance_information" + "," +
                            "\"access_user\":\"" + "replace_with_instance_information" + "\"," +
                            "\"password\":\"" + "replace_with_instance_information" + "\"," +
                            "\"vpc_id\":\"" + "replace_with_instance_information" + "\"," +
                            "\"security_group_id\":\"" + "replace_with_instance_information" + "\"," +
                            "\"subnet_id\":\"" + "replace_with_instance_information" + "\"," +
                            "\"available_zones\":[\"" + "replace_with_instance_information" + "\"]," +
                            "\"product_id\":\"" + "replace_with_instance_information" + "\"," +
                            "\"kafka_manager_user\":\"" + "replace_with_instance_information" + "\"," +
                            "\"kafka_manager_password\":\"" + "replace_with_instance_information" + "\"," +
                            "\"maintain_begin\":\"" + "replace_with_instance_information" + "\"," +
                            "\"maintain_end\":\"" + "replace_with_instance_information" + "\"," +
                            "\"enable_publicip\":" + "replace_with_instance_information" + "," +
                            "\"public_bandwidth\":\"" + "replace_with_instance_information" + "\"," +
                            "\"publicip_id\":\"" + "replace_with_instance_information" + "\"," +
                            "\"ssl_enable\":" + "replace_with_instance_information" + "," +
                            "\"retention_policy\":\"" + "replace_with_instance_information" + "\"," +
                            "\"connector_enable\":" + "replace_with_instance_information" + "," +
                            "\"enable_auto_topic\":" + "replace_with_instance_information" + "," +
                            "\"storage_spec_code\":\"" + "replace_with_instance_information" + "\"," +
                            "\"enterprise_project_id\":\"" + "replace_with_instance_information" + "\"" +
                            "}";

        template.sendBody("direct:operation", sampleBody);
        Exchange responseExchange = mock.getExchanges().get(0);

        mock.assertIsSatisfied();

        assertNotNull(responseExchange.getIn().getBody(String.class));
        assertTrue(responseExchange.getIn().getBody(String.class).length() > 0);
    }
}
