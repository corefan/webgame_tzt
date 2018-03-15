/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License. 
 *  
 */
package com.snail.webgame.engine.gate.util;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.mina.common.IdleStatus;
import org.apache.mina.common.IoFilterAdapter;
import org.apache.mina.common.IoFilterChain;
import org.apache.mina.common.IoSession;
import org.apache.mina.common.IoFilter.WriteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A filter that forward events to {@link Executor} in
 * <a href="http://dcl.mathcs.emory.edu/util/backport-util-concurrent/">backport-util-concurrent</a>.
 * You can apply various thread model by inserting this filter to the {@link IoFilterChain}.
 * <p>
 * Please note that this filter doesn't manage the life cycle of the underlying
 * {@link Executor}.  You have to destroy or stop it by yourself.
 *
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev: 350169 $, $Date: 2005-12-01 00:17:41 -0500 (Thu, 01 Dec 2005) $
 */
public class ExecutorFilterSend extends IoFilterAdapter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Executor executor;

    /**
     * Creates a new instace with the default thread pool implementation
     * (<tt>new ThreadPoolExecutor(16, 16, 60, TimeUnit.SECONDS, new LinkedBlockingQueue() )</tt>).
     */
    public ExecutorFilterSend() {
        this(new ThreadPoolExecutor(16, 16, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()));
    }

    /**
     * Creates a new instance with the specified <tt>executor</tt>.
     */
    public ExecutorFilterSend(Executor executor) {
        if (executor == null) {
            throw new NullPointerException("executor");
        }

        this.executor = executor;
    }

    /**
     * Returns the underlying {@link Executor} instance this filter uses.
     */
    public Executor getExecutor() {
        return executor;
    }

    private void fireEvent(NextFilter nextFilter, IoSession session,
            EventType type, Object data) {
        Event event = new Event(type, nextFilter, data);
        SessionBuffer buf = SessionBuffer.getSessionBuffer(session);

        boolean execute;
        synchronized (buf.eventQueue) {
            buf.eventQueue.offer(event);
            if (buf.processingCompleted) {
                buf.processingCompleted = false;
                execute = true;
            } else {
                execute = false;
            }
        }

        if (execute) {
            if (logger.isDebugEnabled()) {
                logger.debug("Launching thread for "
                        + session.getRemoteAddress());
            }

            executor.execute(new ProcessEventsRunnable(buf));
        }
    }

    private static class SessionBuffer {
        private static final String KEY = SessionBuffer.class.getName()
                + ".KEY";

        private static SessionBuffer getSessionBuffer(IoSession session) {
            synchronized (session) {
                SessionBuffer buf = (SessionBuffer) session.getAttribute(KEY);
                if (buf == null) {
                    buf = new SessionBuffer(session);
                    session.setAttribute(KEY, buf);
                }
                return buf;
            }
        }

        private final IoSession session;

        private final Queue<Event> eventQueue = new LinkedList<Event>();

        private boolean processingCompleted = true;

        private SessionBuffer(IoSession session) {
            this.session = session;
        }
    }

    protected static class EventType {


        public static final EventType FILTWRITE = new EventType("FILTWRITE");
        
        private final String value;

        private EventType(String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }
    }

    protected static class Event {
        private final EventType type;

        private final NextFilter nextFilter;

        private final Object data;

        Event(EventType type, NextFilter nextFilter, Object data) {
            this.type = type;
            this.nextFilter = nextFilter;
            this.data = data;
        }

        public Object getData() {
            return data;
        }

        public NextFilter getNextFilter() {
            return nextFilter;
        }

        public EventType getType() {
            return type;
        }
    }

   

    protected void processEvent(NextFilter nextFilter, IoSession session,
            EventType type, Object data) {
        
        if(type == EventType.FILTWRITE)
        {
        	nextFilter.filterWrite(session, new WriteRequest(data));
        }
    }

    public void filterWrite(NextFilter nextFilter, IoSession session,
            WriteRequest writeRequest) {
       // nextFilter.filterWrite(session, writeRequest);
    	
    	 fireEvent(nextFilter, session, EventType.FILTWRITE, writeRequest.getMessage());
    }

    public void filterClose(NextFilter nextFilter, IoSession session)
            throws Exception {
        nextFilter.filterClose(session);
    }

    private class ProcessEventsRunnable implements Runnable {
        private final SessionBuffer buffer;

        ProcessEventsRunnable(SessionBuffer buffer) {
            this.buffer = buffer;
        }

        public void run() {
            while (true) {
                Event event;

                synchronized (buffer.eventQueue) {
                    event = buffer.eventQueue.poll();

                    if (event == null) {
                        buffer.processingCompleted = true;
                        break;
                    }
                }

                processEvent(event.getNextFilter(), buffer.session, event
                        .getType(), event.getData());
            }

            if (logger.isDebugEnabled()) {
                logger.debug("Exiting since queue is empty for "
                        + buffer.session.getRemoteAddress());
            }
        }
    }
}
