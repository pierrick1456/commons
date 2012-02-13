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
package org.apache.openjpa.persistence.lockmgr;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

/**
 * Test EntityManager find/namedQuery deadlock exceptions.
 */
public class TestMixedLockManagerDeadlock extends SequencedActionsTest {
    private DBType dbType;
    
    public void setUp() {
        setSupportedDatabases(
                org.apache.openjpa.jdbc.sql.DerbyDictionary.class,
                org.apache.openjpa.jdbc.sql.OracleDictionary.class,
                org.apache.openjpa.jdbc.sql.DB2Dictionary.class);
        if (isTestsDisabled()) {
            return;
        }

        setUp(LockEmployee.class
            , "openjpa.LockManager", "mixed"
        );
        commonSetUp();
        EntityManager em = emf.createEntityManager();
        dbType = getDBType(em);
    }

    /* ======== Find dead lock exception test ============*/
    public void testFindDeadLockException() {
        commonFindTest("testFindDeadLockException", LockModeType.READ, null); 
        commonFindTest("testFindDeadLockException", LockModeType.WRITE, dbType == DBType.oracle ? null
                : ExpectingOptimisticLockExClass);
        commonFindTest("testFindDeadLockException", LockModeType.PESSIMISTIC_WRITE, ExpectingAnyLockExClass);
    }

    private void commonFindTest( String testName, 
        LockModeType t1Lock, Class<?>[] t1Exceptions ) {
        String[] parameters = new String[] { "Thread 1: lock= " + t1Lock + ", expectedEx= "
                + Arrays.toString(t1Exceptions) };
            
        Object[][] threadMain = {
            {Act.CreateEm},
            {Act.StartTx},
            
            {Act.FindWithLock, 1, t1Lock},
            {Act.Flush},            
            
            {Act.NewThread, 1 },
            {Act.StartThread, 1 },

            {Act.Wait, 0},
            {Act.FindWithLock, 2, t1Lock},                        
            
            {Act.WaitAllChildren},
            {Act.TestException, 1, t1Exceptions},
            {Act.RollbackTx}
        };
        Object[][] thread1 = {
            {Act.CreateEm},
            {Act.StartTx},
            {Act.FindWithLock, 2, t1Lock},            
            {Act.Flush},            

            {Act.Notify, 0},
            {Act.Sleep, 1000},
            {Act.FindWithLock, 1, t1Lock},            

            {Act.RollbackTx},
        };
        launchActionSequence(testName, parameters, threadMain, thread1);
    }
    
    /* ======== named query dead lock exception test ============*/
    public void testNamedQueryDeadLockException() {
        commonNamedQueryTest("testNamedQueryDeadLockException", LockModeType.READ, null);
        commonNamedQueryTest("testNamedQueryDeadLockException", LockModeType.WRITE, dbType == DBType.oracle ? null
                : ExpectingOptimisticLockExClass);
//      commonNamedQueryTest("testNamedQueryDeadLockException", LockModeType.PESSIMISTIC_FORCE_INCREMENT,
//      ExpectingAnyLockExClass);
    }

    private void commonNamedQueryTest( String testName, 
        LockModeType t1Lock, Class<?>[] t1Exceptions ) {
        String[] parameters = new String[] { "Thread 1: lock= " + t1Lock + ", expectedEx= "
                + Arrays.toString(t1Exceptions) };
            
        Object[][] threadMain = {
            {Act.CreateEm},
            {Act.StartTx},
            
            {Act.NamedQueryWithLock, "findEmployeeById", 1, t1Lock, "openjpa.hint.IgnorePreparedQuery", true},
            {Act.Flush},            
            
            {Act.NewThread, 1 },
            {Act.StartThread, 1 },

            {Act.Wait, 0},
            {Act.NamedQueryWithLock, "findEmployeeById", 2, t1Lock, "openjpa.hint.IgnorePreparedQuery", true},                        
            
            {Act.WaitAllChildren},
            {Act.TestException, 1, t1Exceptions},

            {Act.RollbackTx}
        };
        Object[][] thread1 = {
            {Act.CreateEm},
            {Act.StartTx},
            {Act.NamedQueryWithLock, "findEmployeeById", 2, t1Lock, "openjpa.hint.IgnorePreparedQuery", true},            
            {Act.Flush},            

            {Act.Notify, 0},
            {Act.Sleep, 1000},
            {Act.NamedQueryWithLock, "findEmployeeById", 1, t1Lock, "openjpa.hint.IgnorePreparedQuery", true},            

            {Act.RollbackTx},
        };
        launchActionSequence(testName, parameters, threadMain, thread1);
    }
}
