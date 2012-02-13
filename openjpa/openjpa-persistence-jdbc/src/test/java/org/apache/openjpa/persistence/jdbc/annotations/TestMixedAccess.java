/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.openjpa.persistence.jdbc.annotations;

import javax.persistence.PersistenceException;
import javax.persistence.EntityManagerFactory;

import org.apache.openjpa.persistence.test.AbstractCachedEMFTestCase;
import org.apache.openjpa.persistence.test.PersistenceTestCase;

public class TestMixedAccess extends AbstractCachedEMFTestCase {

    public void testMixedAccessEntityError() {
        try {
            EntityManagerFactory emf =
                createEMF(UnenhancedMixedAccess.class, "openjpa.RuntimeUnenhancedClasses", "supported");
            emf.createEntityManager().close();
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (!(msg.contains("UnenhancedMixedAccess.id") &&
                msg.contains("UnenhancedMixedAccess.getStringField")))
                throw e;
        }
    }

    public void testInappropriateTransientError() {
        try {
            EntityManagerFactory emf = createEMF(
                UnenhancedInappropriateTransient.class, "openjpa.RuntimeUnenhancedClasses", "supported");
            emf.createEntityManager().close();
         } catch (RuntimeException e) {
            String msg = e.getMessage();
            if (!(msg.contains("UnenhancedInappropriateTransient.id") &&
                msg.contains("UnenhancedInappropriateTransient.prePersist")))
                throw e;
        }
    }
}
