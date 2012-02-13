package org.wso2.carbon.web.test.is;


import com.thoughtworks.selenium.Selenium;
import org.wso2.carbon.web.test.common.StartUpTest;
import org.wso2.carbon.web.test.is.BrowserInitializer;
import junit.framework.TestCase;

/*
 * Copyright 2004,2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


public class ISStartUpTest extends TestCase {

    Selenium browser;
    StartUpTest startuptest;


    public ISStartUpTest(String text) {
        super(text);
    }

    public void setUp() throws Exception {
        browser = BrowserInitializer.getbrowser();
        startuptest = new StartUpTest(browser);
    }

    public void testRegistryBrowserTextResources() throws Exception {
        startuptest.ErorrsInStartUp("wso2carbon.log");
    }
}