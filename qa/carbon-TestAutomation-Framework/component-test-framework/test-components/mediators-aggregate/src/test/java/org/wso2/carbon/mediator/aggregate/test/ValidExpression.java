package org.wso2.carbon.mediator.aggregate.test;

import junit.framework.Assert;
import org.apache.axiom.om.OMElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.authenticator.proxy.test.utils.FrameworkSettings;
import org.wso2.carbon.common.test.utils.ConfigHelper;
import org.wso2.carbon.common.test.utils.TestTemplate;
import org.wso2.carbon.common.test.utils.client.StockQuoteClient;
import org.wso2.carbon.mediation.configadmin.test.commands.ConfigServiceAdminStubCommand;
import org.wso2.carbon.mediation.configadmin.ui.ConfigServiceAdminStub;

import java.io.File;
/*
* Copyright (c) WSO2 Inc. (http://wso2.com) All Rights Reserved.
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*  http://www.apache.org/licenses/LICENSE-2.0
*  Unless required by applicable law or agreed to in writing,
*  software distributed under the License is distributed on an
*  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*  KIND, either express or implied.  See the License for the
*  specific language governing permissions and limitations
*  under the License.
*/

public class ValidExpression extends TestTemplate {

    private static final Log log = LogFactory.getLog(ValidExpression.class);

    @Override
    public void init() {
        log.info("Initializing Aggregate Mediator Test class ");
        log.debug("Aggregate Test Initialized");
    }


    @Override
    public void runSuccessCase() {
        log.debug("Running SuccessCase ");
        OMElement result = null;
        StockQuoteClient stockQuoteClient = new StockQuoteClient();

        try {

            ConfigServiceAdminStub configServiceAdminStub = new
                    ConfigServiceAdminStubCommand().initConfigServiceAdminStub(sessionCookie);

            String xmlPath = frameworkPath + File.separator + "mediators-aggregate"
                    + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "valid-expression.xml";
            OMElement omElement = ConfigHelper.createOMElement(xmlPath);

            new ConfigServiceAdminStubCommand(configServiceAdminStub).updateConfigurationExecuteSuccessCase(omElement);


            Thread.sleep(4000);                                    
            if(FrameworkSettings.STRATOS.equalsIgnoreCase("true")){
                result = stockQuoteClient.stockQuoteClientforProxy("http://" + FrameworkSettings.HOST_NAME + ":" + FrameworkSettings.HTTP_PORT + "/services/"+FrameworkSettings.TENANT_NAME + "/", null, "IBM");
            }
            else if(FrameworkSettings.STRATOS.equalsIgnoreCase("false")){
                result = stockQuoteClient.stockQuoteClientforProxy("http://" + FrameworkSettings.HOST_NAME + ":" + FrameworkSettings.HTTP_PORT, null, "IBM");
             }

            assert result != null;
            System.out.println(result);
            if (!result.toString().contains("IBM") || result== null) {
                log.error("Aggregate Mediator does not work");
                Assert.fail("Aggregate Mediator does not work");
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            log.error("Aggregate mediator doesn't work : " + e.getMessage());
            Assert.fail("Aggregate mediator doesn't work : " + e.getMessage());

        }

    }

    @Override
    public void runFailureCase() {


    }

    @Override
    public void cleanup() {
     loadDefaultConfig();
    }
}
