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

package org.wso2.siddhi.core.eventstream.handler.query.window;

import org.wso2.siddhi.api.eventstream.query.inputstream.QueryInputStream;
import org.wso2.siddhi.core.eventstream.handler.query.AbstractQueryInputStreamHandler;
import org.wso2.siddhi.core.eventstream.queue.EventQueue;

public abstract class AbstractWindowQueryInputStreamHandler
        extends AbstractQueryInputStreamHandler {

    protected long toKeepValue;  //Time in milliseconds or Length


    public AbstractWindowQueryInputStreamHandler(QueryInputStream queryInputStream) {
        super(queryInputStream);
        toKeepValue = queryInputStream.getWindowValue();
    }

    public EventQueue getWindow() {
        return window;
    }
}
