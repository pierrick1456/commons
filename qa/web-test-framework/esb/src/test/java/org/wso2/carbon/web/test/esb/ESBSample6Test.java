package org.wso2.carbon.web.test.esb;

import org.wso2.carbon.web.test.common.SeleniumTestBase;
import org.wso2.carbon.web.test.client.ESBSampleClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;/*
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

public class ESBSample6Test extends CommonSetup{

    public ESBSample6Test(String text) {
        super(text);
    }

//    <in>
//        <header name="To" value="http://localhost:9000/services/SimpleStockQuoteService"/>
//    </in>
//    <send/>

    public void createSequence(String seqName) throws Exception{
        ESBCommon esbCommon = new ESBCommon(selenium);
        ESBSequenceTreePopulatorTest esbSequenceTreePopulatorTest = new ESBSequenceTreePopulatorTest(selenium);
        ESBHeaderMediatorTest esbHeaderMediatorTest = new ESBHeaderMediatorTest(selenium);
        ESBSendMediatorTest esbSendMediatorTest = new ESBSendMediatorTest(selenium);

        //Creating sequence
        esbCommon.addSequence(seqName);

        //Adding the In mediator
        esbCommon.addRootLevelChildren("Add Child","Filter","In");
        esbSequenceTreePopulatorTest.clickMediator("0");

        //Adding a Header mediator
        esbCommon.addMediators("Add Child","0","Transform","Header");
        esbHeaderMediatorTest.addHeaderMediator("0.0","To");
        esbHeaderMediatorTest.setHeaderAction("value",esbCommon.getServiceAddUrl("SimpleStockQuoteService"));
        esbCommon.mediatorUpdate();

        //Adding a Send mediator
        esbCommon.addRootLevelChildren("Add Child","Core","Send");
        esbSendMediatorTest.addNormalSendMediator("1");
        esbCommon.mediatorUpdate();
        esbCommon.sequenceSave();
    }

    /*
    Executing client for proxy_inline_wsdl_anon_seq Proxy Service
     */
    public void invokeClient() throws Exception{
        ESBSampleClient esbSampleClient = new ESBSampleClient();
        ESBCommon esbCommon = new ESBCommon(selenium);
        boolean stockQuoteResponse = esbSampleClient.stockQuoteClient("http://"+esbCommon.getHostName()+":"+esbCommon.getNioHttpPort()+"/",null, "IBM");
        if (stockQuoteResponse){
            System.out.println("The response received!!!!");
        }else{
            throw new MyCheckedException("Client Failed!!!!");
        }
        Thread.sleep(5000);
       esbCommon.closeFiles();        
    }
    
    public void testSample5Config() throws Exception{
        SeleniumTestBase seleniumTestBase = new SeleniumTestBase(selenium);
        ESBCommon esbCommon = new ESBCommon(selenium);
        
         boolean login = selenium.isTextPresent("Sign-out");

         if (login){
             seleniumTestBase.logOutUI();
         }

        seleniumTestBase.loginToUI("admin","admin");
        createSequence("sample_6");
        //Setting the created sequence to the main sequence
        esbCommon.setSequenceToSequence("main","sample_6");
        seleniumTestBase.logOutUI();
        invokeClient();
    }
}
