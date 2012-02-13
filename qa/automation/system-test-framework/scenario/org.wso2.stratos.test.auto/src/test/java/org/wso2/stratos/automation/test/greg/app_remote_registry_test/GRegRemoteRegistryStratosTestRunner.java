/*
* Copyright (c) 2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.stratos.automation.test.greg.app_remote_registry_test;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.wso2.carbon.admin.service.utils.FrameworkSettings;


public class GRegRemoteRegistryStratosTestRunner extends TestSuite {
    public static Test suite() {
        TestSuite testSuite = new TestSuite();

        if (!(FrameworkSettings.getStratosTestStatus())) {
            testSuite.addTestSuite(CommentTest.class);
            testSuite.addTestSuite(ContinuousOperations.class);
            testSuite.addTestSuite(PropertiesTest.class);
            testSuite.addTestSuite(QueryTest.class);
            testSuite.addTestSuite(RatingTest.class);
            testSuite.addTestSuite(RenameTest.class);
            testSuite.addTestSuite(ResourceHandling.class);
            testSuite.addTestSuite(TestAssociation.class);
            testSuite.addTestSuite(TestContentStream.class);
            testSuite.addTestSuite(TestCopy.class);
            testSuite.addTestSuite(TestMove.class);
            testSuite.addTestSuite(TestPaths.class);
            testSuite.addTestSuite(TestResources.class);
            testSuite.addTestSuite(TestTagging.class);
        }
        return testSuite;
    }
}
