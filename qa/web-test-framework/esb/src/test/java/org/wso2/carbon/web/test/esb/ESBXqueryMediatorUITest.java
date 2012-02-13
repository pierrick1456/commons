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

public class ESBXqueryMediatorUITest   extends TestCase {

    Selenium selenium;

    public ESBXqueryMediatorUITest(Selenium _browser){
		selenium = _browser;
    }

    /*
    This method will verify the Xquery mediator
     */
    public void addXSLTMediator(String level) throws Exception {
        selenium.click("//a[@id='mediator-"+level+"']");
        Thread.sleep(4000);        
        assertTrue(selenium.isTextPresent("XQuery Mediator"));
		assertTrue(selenium.isTextPresent("Key  *"));
		assertTrue(selenium.isElementPresent("link=Registry Keys"));
		assertTrue(selenium.isTextPresent("Target"));
		assertTrue(selenium.isElementPresent("link=NameSpaces"));
		assertTrue(selenium.isTextPresent("Variables of the XQuery mediator"));
		assertTrue(selenium.isElementPresent("link=Add Variable"));
		selenium.click("link=Add Variable");
		assertTrue(selenium.isTextPresent("Variable Type"));
		assertEquals("Select A ValueINTINTEGERBOOLEANBYTEDOUBLESHORTLONGFLOATSTRINGDOCUMENTDOCUMENT_ELEMENTELEMENT", selenium.getText("variableType0"));
		assertTrue(selenium.isTextPresent("Variable Name"));
		assertTrue(selenium.isTextPresent("Value Type"));
		assertEquals("ValueExpression", selenium.getText("variableTypeSelection0"));
		assertTrue(selenium.isTextPresent("Value / Expression"));
		assertTrue(selenium.isTextPresent("Action"));
		assertTrue(selenium.isElementPresent("//a[@onclick='deletevariable(0)']"));
		selenium.select("variableTypeSelection0", "label=Expression");
		assertTrue(selenium.isTextPresent("Registry Key"));
		assertTrue(selenium.isTextPresent("NS Editor"));
		assertTrue(selenium.isElementPresent("//a[@onclick=\"showNameSpaceEditor('variableValue0')\"]"));
		selenium.select("variableTypeSelection0", "label=Value");
		selenium.select("variableTypeSelection0", "label=Expression");
		assertTrue(selenium.isTextPresent("Registry Browser"));
		assertTrue(selenium.isElementPresent("//a[@onclick=\"showInLinedRegistryBrowser('registryKey0')\"]"));
		assertTrue(selenium.isElementPresent("//input[@value='Update']"));
    }
}
