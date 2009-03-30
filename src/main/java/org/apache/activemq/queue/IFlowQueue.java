/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.activemq.queue;

import org.apache.activemq.flow.IFlowRelay;

public interface IFlowQueue<E> extends IBlockingFlowSource<E>, IPollableFlowSource<E>, IAsynchronousFlowSource<E>, IFlowRelay<E> {

    public interface FlowQueueListener {
        
        /**
         * Called when there is a queue error
         * 
         * @param queue The queue triggering the exception
         * @param thrown The exception. 
         */
        public void onQueueException(IFlowQueue<?> queue, Throwable thrown);
    }

    public void setFlowQueueListener(FlowQueueListener listener);
    
    public void setDispatchPriority(int priority);

}
