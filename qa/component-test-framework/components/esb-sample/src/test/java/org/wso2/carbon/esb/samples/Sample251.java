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

import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.common.test.utils.client.Axis2Client;
import org.wso2.carbon.logging.view.LogViewerStub;
import org.wso2.carbon.logging.view.test.commands.InitializeLogViewerAdmin;
import org.wso2.carbon.logging.view.test.commands.LogViewerAdminCommand;
import org.wso2.carbon.logging.view.types.carbon.LogMessage;

import static org.wso2.carbon.authenticator.proxy.test.utils.FrameworkSettings.HOST_NAME;
import static org.wso2.carbon.authenticator.proxy.test.utils.FrameworkSettings.HTTP_PORT;

/*Switching from http/s to JMS*/
public class Sample251 extends SampleTestTemplate {
    private static final Log log = LogFactory.getLog(Sample251.class);
    String searchWord = "PropertyMediator Setting property : OUT_ONLY at scope : default to : true";

    @Override
    void setSampleNo() {
        sampleNo = 251;
    }

    @Override
    protected void invokeClient() {
        try {
            clientLog = Axis2Client.fireClient("ant stockquote -Daddurl=http://" + HOST_NAME + ":" + HTTP_PORT + "/services/StockQuoteProxy -Dmode=placeorder -Dsymbol=MSFT");
            LogViewerStub logViewerStub = new InitializeLogViewerAdmin().executeAdminStub();
            LogMessage[] logMessages = new LogViewerAdminCommand(logViewerStub).addEntrySuccessCase("PropertyMediator");
            if (!logMessages[logMessages.length - 1].getLogMessage().contains(searchWord)) {
                Assert.fail("Sample 251 doesn't work");
                log.error("Sample 251 doesn't work");
            }
        } catch (Exception e) {
            log.error("Sample 251 doesn't work : " + e.getMessage());
        }


    }

    @Override
    protected void runSuccessTest() {
        //      Assert.assertTrue(clientLog.contains("Standard :: Stock price ="));
    }

    @Override
    protected void runFailureTest() {
        //     Assert.assertFalse(clientLog.contains("Standard :: Stock price ="));
    }


}