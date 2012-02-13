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

package org.wso2.siddhi.test.samples.simple.complex_expression;

import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.wso2.siddhi.api.QueryFactory;
import org.wso2.siddhi.api.eventstream.InputEventStream;
import org.wso2.siddhi.api.eventstream.query.Query;
import org.wso2.siddhi.api.eventstream.query.inputstream.property.WindowType;
import org.wso2.siddhi.api.exception.SiddhiPraserException;
import org.wso2.siddhi.core.SiddhiManager;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.event.EventImpl;
import org.wso2.siddhi.core.exception.SiddhiException;
import org.wso2.siddhi.core.node.CallbackHandler;
import org.wso2.siddhi.core.node.InputHandler;

public class ComplexExpression1TestCase {

    private static final Logger log = Logger.getLogger(ComplexExpression1TestCase.class);
    private volatile int i;
    private volatile boolean eventCaptured;


    @Before
    public void info() {
        log.debug("-----Query processed: Testing equal through complex expressions-----");
        eventCaptured = false;
        i = 0;
    }

    @Test
    public void testAPI() throws SiddhiException, InterruptedException {
        //Instantiate SiddhiManager
        SiddhiManager siddhiManager = new SiddhiManager();
        QueryFactory qf = SiddhiManager.getQueryFactory();

        InputEventStream cseEventStream = new InputEventStream(
                "CSEStream",
                new String[]{"symbol", "price"},
                new Class[]{String.class, Integer.class}
        );
        InputHandler inputHandler = siddhiManager.addInputEventStream(cseEventStream);

        Query query = qf.createQuery(
                "StockQuote",
                qf.output("symbol=CSEStream.symbol", "avgPrice=avg(CSEStream.price)", "count=count(CSEStream.symbol)"),
                qf.from(cseEventStream).setWindow(WindowType.TIME, 500),
                qf.condition("(7+(8*2)<CSEStream.price) && (CSEStream.symbol=='IBM')")
        );

        siddhiManager.addQuery(query);

        siddhiManager.addCallback(assignCallback());
        siddhiManager.update();

        sendEvents(inputHandler);
        assertTest();
        siddhiManager.shutDownTask();
    }

    @Test
    public void testQuery() throws SiddhiException, InterruptedException, SiddhiPraserException {
        //Instantiate SiddhiManager
        SiddhiManager siddhiManager = new SiddhiManager();

        siddhiManager.addQueries("CSEStream:= symbol[string], price [int]; \n" +
                                 "" +
                                 "StockQuote:= select symbol, avgPrice=avg(price), symbolCount=count(symbol) " +
                                 "from CSEStream[win.time=500] " +
                                 "where  (7+(8*2)<CSEStream.price) and(CSEStream.symbol=='IBM') ;");

        siddhiManager.addCallback(assignCallback());
        siddhiManager.update();

        sendEvents(siddhiManager.getInputHandler("CSEStream"));
        assertTest();
        siddhiManager.shutDownTask();
    }

    private void assertTest() throws InterruptedException {
        Thread.sleep(1000);
        Assert.assertTrue(eventCaptured);
        Assert.assertTrue("required two event not arrived", i == 2);
    }

    private void sendEvents(InputHandler inputHandler) {
        inputHandler.sendEvent(new EventImpl("CSEStream", new Object[]{"IBM", 10}));
        inputHandler.sendEvent(new EventImpl("CSEStream", new Object[]{"IBM", 103}));
    }

    private CallbackHandler assignCallback() {
        return new CallbackHandler("StockQuote") {
            public void callBack(Event event) {
                log.debug("       Event captured  " + event + " ");
                if ((Integer) event.getNthAttribute(1) == 103) {
                    eventCaptured = true;
                }
                if ((Integer) event.getNthAttribute(1) == 0) {
                    eventCaptured = true;
                }
                i++;

            }
        };
    }

}
