/*
 * Copyright 2005-2007 WSO2, Inc. (http://wso2.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.wso2.eventing;

import org.wso2.eventing.Event;
import org.wso2.eventing.EventFilter;
import org.wso2.eventing.EventSink;
import org.wso2.eventing.exceptions.EventException;

/**
 * A FilteredSink is a "wrapper" EventSink which filters events and passes
 * matching ones to the "real" EventSink destination.
 */
public class FilteredSink implements EventSink {
    private EventFilter filter;
    private EventSink destination;

    public FilteredSink(EventFilter filter, EventSink destination) {
        this.filter = filter;
        this.destination = destination;
    }

    public void onEvent(Event event) throws EventException {
        if (filter.match(event)) {
            destination.onEvent(event);
        }
    }
}
