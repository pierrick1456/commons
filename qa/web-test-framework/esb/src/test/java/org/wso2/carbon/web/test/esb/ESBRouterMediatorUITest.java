package org.wso2.carbon.web.test.esb;

import junit.framework.TestCase;
import com.thoughtworks.selenium.Selenium;

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

public class ESBRouterMediatorUITest    extends TestCase {
    Selenium selenium;

    public ESBRouterMediatorUITest(Selenium _browser){
		selenium = _browser;
    }

    /*
	 * This method will verify the Router mediator properties
	 */
    public void verifyRouterMediator(String level) throws Exception{
        selenium.click("//a[@id='mediator-"+level+"']");
        Thread.sleep(2000);
        assertTrue(selenium.isTextPresent("Router Mediator"));
		assertTrue(selenium.isTextPresent("Continue after routing"));
		assertTrue(selenium.isTextPresent("Number of clones"));
		assertEquals("Yes No", selenium.getText("mediator.router.continue"));
		assertEquals("Update", selenium.getValue("//input[@value='Update']"));
        assertEquals("Help", selenium.getText("//div[@id='mediatorDesign']/div/div/a"));

//        ESBCommon esbCommon = new ESBCommon(selenium);
//        esbCommon.testMediatorHelp("Router");
    }

    /*
	 * This method will add a Router configuration
	 */
    public void testVerifyRouterConfiguration() throws Exception{
        selenium.click("link=Route");
        Thread.sleep(2000);
        assertTrue(selenium.isTextPresent("Route Configuration"));
		assertTrue(selenium.isTextPresent("Break after route"));
		assertEquals("Yes No", selenium.getText("mediator.route.break"));
		assertTrue(selenium.isTextPresent("Route Expression*"));
		assertEquals("Namespaces", selenium.getText("mediator.route.expression.xpath_nmsp_button"));
		assertTrue(selenium.isTextPresent("Route Pattern"));
		assertEquals("Update", selenium.getValue("//input[@value='Update']"));
		assertEquals("Help", selenium.getText("//div[@id='mediatorDesign']/div/div/a"));

//        ESBCommon esbCommon = new ESBCommon(selenium);
//        esbCommon.testMediatorHelp("Route");
    }

    /*
	 * This method will add a Target Configuration
	 */
    public void testVerifyTargetConfiguration() throws Exception{
        selenium.click("link=Target");
        Thread.sleep(2000);
        assertTrue(selenium.isTextPresent("Target Configuration"));
		assertTrue(selenium.isTextPresent("SOAP Action"));
		assertTrue(selenium.isTextPresent("To Address"));
		assertTrue(selenium.isTextPresent("Sequence"));
		assertTrue(selenium.isTextPresent("None"));
		assertTrue(selenium.isTextPresent("Anonymous"));
		assertTrue(selenium.isTextPresent("Pick From Registry"));
		assertTrue(selenium.isTextPresent("Endpoint"));
		assertTrue(selenium.isTextPresent("None"));
		assertTrue(selenium.isTextPresent("Anonymous"));
		assertTrue(selenium.isTextPresent("Pick From Registry"));
		assertEquals("Pick From Registry", selenium.getText("//form[@id='mediator-editor-form']/div/table/tbody/tr[2]/td/table/tbody/tr[10]/td[1]"));
		assertEquals("Anonymous", selenium.getText("//form[@id='mediator-editor-form']/div/table/tbody/tr[2]/td/table/tbody/tr[9]/td[1]"));
		assertEquals("None", selenium.getText("//form[@id='mediator-editor-form']/div/table/tbody/tr[2]/td/table/tbody/tr[8]/td"));
		assertEquals("Help", selenium.getText("//div[@id='mediatorDesign']/div/div/a"));
		assertEquals("Update", selenium.getValue("//input[@value='Update']"));

//        ESBCommon esbCommon = new ESBCommon(selenium);
//        esbCommon.testMediatorHelp("Target");
    }
}
