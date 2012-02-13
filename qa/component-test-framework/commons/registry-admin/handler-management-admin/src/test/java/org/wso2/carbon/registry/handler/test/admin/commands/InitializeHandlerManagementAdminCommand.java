/*
*Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*WSO2 Inc. licenses this file to you under the Apache License,
*Version 2.0 (the "License"); you may not use this file except
*in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing,
*software distributed under the License is distributed on an
*"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*KIND, either express or implied.  See the License for the
*specific language governing permissions and limitations
*under the License.
*/
package org.wso2.carbon.registry.handler.test.admin.commands;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.wso2.carbon.authenticator.proxy.test.utils.FrameworkSettings;
import org.wso2.carbon.registry.handler.ui.HandlerManagementServiceStub;

/**
 * HandlerManagementService initialization class: it returns handlerManagementServiceStub
 */
public class InitializeHandlerManagementAdminCommand {
    private static final Log log = LogFactory.getLog(InitializeHandlerManagementAdminCommand.class);

    public HandlerManagementServiceStub executeAdminStub(String sessionCookie) {
        log.debug("sessionCookie:" + sessionCookie);
        String serviceURL = null;
        FrameworkSettings.getProperty();
        serviceURL = FrameworkSettings.SERVICE_URL + "HandlerManagementService";
        HandlerManagementServiceStub handlerManagementServiceStub = null;
        try {
            handlerManagementServiceStub = new HandlerManagementServiceStub(serviceURL);
            ServiceClient client = handlerManagementServiceStub._getServiceClient();
            Options option = client.getOptions();
            option.setManageSession(true);
            option.setProperty(org.apache.axis2.transport.http.HTTPConstants.COOKIE_STRING, sessionCookie);
            handlerManagementServiceStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(600000);
        } catch (AxisFault axisFault) {
            Assert.fail("Unexpected exception thrown");
            axisFault.printStackTrace();
        }
        log.info("handlerManagementServiceStub created");
        return handlerManagementServiceStub;

    }
}
