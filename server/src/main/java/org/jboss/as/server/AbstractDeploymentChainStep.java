/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.as.server;

import org.jboss.as.controller.NewOperationContext;
import org.jboss.as.controller.NewStepHandler;
import org.jboss.dmr.ModelNode;

/**
 * A step handler for a deployment chain step which adds a processor to the deployment chain.
 *
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
public abstract class AbstractDeploymentChainStep implements NewStepHandler {

    static final ThreadLocal<DeploymentProcessorTarget> PROCESSOR_TARGET_THREAD_LOCAL = new ThreadLocal<DeploymentProcessorTarget>();

    public final void execute(final NewOperationContext context, final ModelNode operation) {
        final DeploymentProcessorTarget target = PROCESSOR_TARGET_THREAD_LOCAL.get();
        if (target == null) {
            throw new IllegalStateException("Boot operation executing during runtime");
        }
        execute(target);
        context.completeStep();
    }

    /**
     * Execute the step, adding deployment processors.
     *
     * @param processorTarget the processor target
     */
    protected abstract void execute(DeploymentProcessorTarget processorTarget);
}
