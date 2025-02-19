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
import org.apache.camel.component.huaweicloud.dms.constants.DMSProperties;
import org.apache.camel.component.huaweicloud.dms.models.UpdateInstanceRequest;
import org.apache.camel.component.huaweicloud.dms.models.UpdateInstanceResponse;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpdateInstanceJsonTest extends CamelTestSupport {
    TestConfiguration testConfiguration = new TestConfiguration();

    @BindToRegistry("dmsClient")
    DmsClient mockClient = Mockito.mock(DmsClient.class);

    @BindToRegistry("serviceKeys")
    ServiceKeys serviceKeys = new ServiceKeys(
            testConfiguration.getProperty("authenticationKey"),
            testConfiguration.getProperty("secretKey"));

    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:operation")
                        .to("hwcloud-dms:updateInstance?" +
                            "serviceKeys=#serviceKeys" +
                            "&projectId=" + testConfiguration.getProperty("projectId") +
                            "&region=" + testConfiguration.getProperty("region") +
                            "&instanceId=" + testConfiguration.getProperty("instanceId") +
                            "&ignoreSslVerification=true" +
                            "&dmsClient=#dmsClient")
                        .log("Operation successful")
                        .to("mock:operation_result");
            }
        };
    }

    @Test
    public void testOperation() throws Exception {
        UpdateInstanceResponse response = new UpdateInstanceResponse();

        Mockito.when(mockClient.updateInstance(Mockito.any(UpdateInstanceRequest.class))).thenReturn(response);

        MockEndpoint mock = getMockEndpoint("mock:operation_result");
        mock.expectedMinimumMessageCount(1);

        String sampleBody = "{" +
                            "\"name\":\"" + testConfiguration.getProperty("name") + "\"," +
                            "\"description\":\"" + testConfiguration.getProperty("description") + "\"," +
                            "\"maintain_begin\":\"" + testConfiguration.getProperty("maintainBegin") + "\"," +
                            "\"maintain_end\":\"" + testConfiguration.getProperty("maintainEnd") + "\"," +
                            "\"security_group_id\":\"" + testConfiguration.getProperty("securityGroupId") + "\"," +
                            "\"enable_publicip\":\"" + testConfiguration.getProperty("enablePublicip") + "\"," +
                            "\"publicip_id\":\"" + testConfiguration.getProperty("publicipId") + "\"" +
                            "}";
        template.sendBody("direct:operation", sampleBody);
        Exchange responseExchange = mock.getExchanges().get(0);

        mock.assertIsSatisfied();

        assertTrue(responseExchange.getProperty(DMSProperties.INSTANCE_UPDATED, boolean.class));
    }
}
