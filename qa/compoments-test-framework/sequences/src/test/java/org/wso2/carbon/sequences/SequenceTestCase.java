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
package org.wso2.carbon.sequences;

import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.junit.Before;
import org.wso2.carbon.sequences.ui.types.SequenceAdminServiceStub;
import org.wso2.carbon.test.framework.ComponentsTestCase;

import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: suho
 * Date: Jun 16, 2010
 * Time: 3:51:54 PM
 * Initializing SequenceAdminServiceStub
 */
public class SequenceTestCase extends ComponentsTestCase {
    private String serviceURL = "https://localhost:9443/services/SequenceAdminService";
         protected SequenceAdminServiceStub sequenceAdminServiceStub;
    
       @Before
       public void Init() throws RemoteException {

           System.out.println("Using Session Cookie: " + sessionCookie);

           sequenceAdminServiceStub = new SequenceAdminServiceStub(serviceURL);
           ServiceClient client = sequenceAdminServiceStub._getServiceClient();
           Options option = client.getOptions();
           option.setManageSession(true);
           option.setProperty(org.apache.axis2.transport.http.HTTPConstants.COOKIE_STRING, sessionCookie);

       }

}
