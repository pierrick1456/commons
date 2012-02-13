/**
 *  Copyright (c) 2009, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.wso2.carbon.esb.samples;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.wso2.carbon.common.test.utils.client.Axis2Client;

import static org.wso2.carbon.authenticator.proxy.test.utils.FrameworkSettings.BACKENDSERVER_HTTP_PORT;
import static org.wso2.carbon.authenticator.proxy.test.utils.FrameworkSettings.HOST_NAME;
import static org.wso2.carbon.authenticator.proxy.test.utils.FrameworkSettings.HTTP_PORT;

//Message Mediation Sample

public class Sample12 extends SampleTestTemplate {
    private static final Log log = LogFactory.getLog(Sample12.class);


    @Override
    void setSampleNo() {
        sampleNo = 12;
    }

    @Override
    protected void invokeClient() {
        clientLog = Axis2Client.fireClient("ant stockquote -Daddurl=http://" + HOST_NAME + ":" + BACKENDSERVER_HTTP_PORT + "/services/SimpleStockQuoteService -Dtrpurl=http://" + HOST_NAME + ":" + HTTP_PORT + "/ -Dmode=placeorder");

    }

    @Override
    protected void runSuccessTest() {
        Assert.assertTrue(clientLog.contains("SimpleStockQuoteService :: Accepted order for"));
        log.info("running sample12 success case");
        log.debug("running sample12 success case");
    }

    @Override
    protected void runFailureTest() {
        Assert.assertFalse(clientLog.contains("SimpleStockQuoteService :: Accepted order for"));
        log.info("running sample12 failure case");
        log.debug("running sample12 failure case");
    }
}
