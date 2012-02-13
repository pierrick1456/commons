/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.siddhi.test.samples.complex;

import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.wso2.siddhi.api.QueryFactory;
import org.wso2.siddhi.api.eventstream.InputEventStream;
import org.wso2.siddhi.api.eventstream.query.Query;
import org.wso2.siddhi.api.eventstream.query.inputstream.property.WindowType;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.event.EventImpl;
import org.wso2.siddhi.core.exception.InvalidQueryException;
import org.wso2.siddhi.core.exception.ProcessorInitializationException;
import org.wso2.siddhi.core.exception.SiddhiException;
import org.wso2.siddhi.core.node.CallbackHandler;
import org.wso2.siddhi.core.node.InputHandler;

import static org.wso2.siddhi.api.condition.where.ConditionOperator.EQUAL;
import static org.wso2.siddhi.api.condition.where.ConditionOperator.GREATERTHAN_EQUAL;


public class JoinFilterAndFilterInParallelTestCase {


    private static final Logger log = Logger.getLogger(JoinFilterAndFilterInParallelTestCase.class);
    private volatile int i = 0;
    private volatile int j = 0;
    private volatile boolean event1Captured = false;
    private volatile boolean event2Captured = false;

    public static void main(String[] args)
            throws InvalidQueryException, ProcessorInitializationException, InterruptedException,
                   SiddhiException {
        // Test code
        JoinFilterAndFilterInParallelTestCase simSimpleFilterTestCase = new JoinFilterAndFilterInParallelTestCase();
        simSimpleFilterTestCase.testCase();
    }

    @Before
    public void info() {
            log.debug("-----Query processed: Running two Queries in parallel-----");
    }

    @Test
    public void testCase() throws SiddhiException, ProcessorInitializationException,
                                  InvalidQueryException, InterruptedException {

        //Instantiate SiddhiManager
        SiddhiManager siddhiManager = new SiddhiManager();
        QueryFactory qf = siddhiManager.getQueryFactory();
        InputEventStream reserveConfirmationEventStream = new InputEventStream(
                "confirmation",
                new String[]{"time", "memberId", "payMethod"},
                new Class[]{Long.class, Integer.class, String.class}
        );

        InputEventStream requestEventStream = new InputEventStream(
                "request",
                new String[]{"time", "memberId", "noOfRooms"},
                new Class[]{Long.class, Integer.class, Integer.class}
        );

        InputEventStream cseEventStream = new InputEventStream(
                "CSEStream",
                new String[]{"symbol", "price"},
                new Class[]{String.class, Integer.class}
        );

        siddhiManager.addInputEventStream(reserveConfirmationEventStream);
        siddhiManager.addInputEventStream(requestEventStream);
        siddhiManager.addInputEventStream(cseEventStream);

        Query query1 = qf.createQuery(
                "bought",
                qf.output("method=confirmation.payMethod", "rooms=request.noOfRooms"),
                qf.innerJoin(qf.from(reserveConfirmationEventStream).setWindow(WindowType.LENGTH,100),qf.from(requestEventStream).setWindow(WindowType.LENGTH,100)),
                qf.condition("confirmation.memberId", EQUAL, "request.memberId")
        );

        Query query2 = qf.createQuery(
                "bought2Rooms",
                qf.output("Method=bought.method", "Rooms=bought.rooms", "avdRooms=avg(bought.rooms)"),
                qf.from(query1),
                qf.condition("bought.rooms", GREATERTHAN_EQUAL, "2")
        );

        Query query = qf.createQuery(
                "StockQuote",
                qf.output("price=CSEStream.price"),
                qf.from(cseEventStream),
                qf.condition("CSEStream.symbol", EQUAL, "IBM")
        );

        siddhiManager.addQuery(query1);
        siddhiManager.addQuery(query2);
        siddhiManager.addQuery(query);

        Thread.sleep(1000);

        siddhiManager.addCallback(new CallbackHandler("bought2Rooms") {
            public void callBack(Event event) {
                log.debug("Event captured: " + event);

                if ((Integer) event.getNthAttribute(2) == 5 && j == 0) {
                    event2Captured = true;
                }
                if ((Integer) event.getNthAttribute(2) == 3 & j == 1) {
                    event2Captured = true;
                }
                j++;
            }
        }
        );

        siddhiManager.addCallback(new CallbackHandler("StockQuote") {
            public void callBack(Event event) {

                log.debug("Event captured: " + event);
                if ((Integer) event.getNthAttribute(0) == 400 && i == 0) {
                    event1Captured = true;
                }
                if ((Integer) event.getNthAttribute(0) == 450 & i == 1) {
                    event1Captured = true;
                }
                i++;
            }
        }
        );

        siddhiManager.update();

        HotelBooker hotelBooker = new HotelBooker(siddhiManager);
        Thread hotelBookerThread = new Thread(hotelBooker);
        hotelBookerThread.start();

        StockSender stockSender = new StockSender(siddhiManager);
        Thread stockSenderThread = new Thread(stockSender);
        stockSenderThread.start();

        hotelBookerThread.join();
        stockSenderThread.join();
        Thread.sleep(1000);
        Assert.assertTrue(event1Captured);
        Assert.assertTrue(event2Captured);
        Assert.assertTrue(i == 2);
        Assert.assertTrue(j == 2);
        siddhiManager.shutDownTask();

    }

    class HotelBooker implements Runnable {
        SiddhiManager siddhiManager;
        InputHandler inputHandlerRequest;
        InputHandler inputHandlerConfirmation;

        HotelBooker(SiddhiManager siddhiManager) throws SiddhiException {
            this.siddhiManager = siddhiManager;
            this.inputHandlerRequest = siddhiManager.getInputHandler("request");
            this.inputHandlerConfirmation = siddhiManager.getInputHandler("confirmation");
        }

        @Override
        public void run() {
            //pumping the Events
            inputHandlerRequest.sendEvent(new EventImpl("request", new Object[]{20, 99, 5}));
            inputHandlerRequest.sendEvent(new EventImpl("request", new Object[]{21, 97, 5}));
            inputHandlerConfirmation.sendEvent(new EventImpl("confirmation", new Object[]{20, 100, "card"}));
            inputHandlerConfirmation.sendEvent(new EventImpl("confirmation", new Object[]{21, 98, "card"}));

            inputHandlerRequest.sendEvent(new EventImpl("request", new Object[]{23, 101, 5}));
            inputHandlerConfirmation.sendEvent(new EventImpl("confirmation", new Object[]{24, 101, "card"}));
            inputHandlerRequest.sendEvent(new EventImpl("request", new Object[]{25, 102, 4}));
            inputHandlerRequest.sendEvent(new EventImpl("request", new Object[]{26, 103, 2}));
            inputHandlerRequest.sendEvent(new EventImpl("request", new Object[]{27, 104, 3}));
            inputHandlerConfirmation.sendEvent(new EventImpl("confirmation", new Object[]{28, 103, "cash"}));
            inputHandlerConfirmation.sendEvent(new EventImpl("confirmation", new Object[]{29, 105, "card"}));

//            log.debug("Thread 2 Done with work");
        }
    }

    class StockSender implements Runnable {
        SiddhiManager siddhiManager;
        InputHandler inputHandler;

        StockSender(SiddhiManager siddhiManager) throws SiddhiException {
            this.siddhiManager = siddhiManager;
            this.inputHandler = siddhiManager.getInputHandler("CSEStream");
        }

        @Override
        public void run() {
            inputHandler.sendEvent(new EventImpl("CSEStream", new Object[]{"IBM", 400}));
            inputHandler.sendEvent(new EventImpl("CSEStream", new Object[]{"WSO2", 900}));
            inputHandler.sendEvent(new EventImpl("CSEStream", new Object[]{"IBM", 450}));
            inputHandler.sendEvent(new EventImpl("CSEStream", new Object[]{"WSO2", 910}));

//            log.debug("Thread 1 Done with work");

        }
    }

}
