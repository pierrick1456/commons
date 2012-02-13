/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *   * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.synapse.samples.framework.tests.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.samples.framework.PropertyLoader;
import org.apache.synapse.samples.framework.SampleClientResult;
import org.apache.synapse.samples.framework.SynapseTestCase;
import org.apache.synapse.samples.framework.clients.StockQuoteSampleClient;

public class Sample2 extends SynapseTestCase {

    private static final Log log = LogFactory.getLog(Sample2.class);

    private StockQuoteSampleClient client;

    public Sample2() {
        super(2);
        PropertyLoader.getProperties();
        client = getStockQuoteClient();
    }


    public void testCBR() {
        String addUrl = "http://localhost:9000/services/SimpleStockQuoteService";
        String trpUrl;
        if (PropertyLoader.CONTEXT_ROOT == null) {
            trpUrl = "http://" + PropertyLoader.HOST_NAME + ":" + PropertyLoader.NHTTP_PORT;
        } else {
            trpUrl = "http://" + PropertyLoader.HOST_NAME + ":" + PropertyLoader.NHTTP_PORT + "/" + PropertyLoader.CONTEXT_ROOT;
        }

        log.info("Running test: CBR with the Switch-case mediator, using message properties");
        SampleClientResult result = client.requestStandardQuote(addUrl, trpUrl, null, "IBM", null);
        assertResponseReceived(result);
        result = client.requestStandardQuote(addUrl, trpUrl, null, "MSFT", null);
        assertResponseReceived(result);
    }

}
