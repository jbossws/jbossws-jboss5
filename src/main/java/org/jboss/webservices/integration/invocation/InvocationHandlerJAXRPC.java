/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.webservices.integration.invocation;

import javax.xml.rpc.server.ServiceLifecycle;
import javax.xml.rpc.server.ServletEndpointContext;

import org.jboss.wsf.spi.invocation.Invocation;
import org.jboss.wsf.spi.invocation.InvocationContext;

/**
 * Handles invocations on JAXRPC endpoints.
 *
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 * @author <a href="mailto:tdiesler@redhat.com">Thomas Diesler</a>
 */
final class InvocationHandlerJAXRPC extends AbstractInvocationHandlerJSE
{

   /**
    * Constructor.
    */
   InvocationHandlerJAXRPC()
   {
      super();
   }

   /**
    * Calls {@link javax.xml.rpc.server.ServiceLifecycle#init(Object)}
    * method on target bean if this bean implements 
    * {@link javax.xml.rpc.server.ServiceLifecycle} interface.
    * 
    * @param invocation current invocation
    * @throws Exception if any error occurs
    */
   @Override
   protected void onBeforeInvocation(final Invocation invocation) throws Exception
   {
      final InvocationContext invocationContext = invocation.getInvocationContext();
      final Object targetBean = invocationContext.getTargetBean();
      final boolean isJaxrpcLifecycleBean = targetBean instanceof ServiceLifecycle;

      if (isJaxrpcLifecycleBean)
      {
         final ServletEndpointContext sepContext = invocationContext.getAttachment(ServletEndpointContext.class);
         ((ServiceLifecycle) targetBean).init(sepContext);
      }
   }

   /**
    * Calls {@link javax.xml.rpc.server.ServiceLifecycle#destroy()}
    * method on target bean if this bean implements 
    * {@link javax.xml.rpc.server.ServiceLifecycle} interface.
    * 
    * @param invocation current invocation
    * @throws Exception if any error occurs
    */
   @Override
   protected void onAfterInvocation(final Invocation invocation) throws Exception
   {
      final InvocationContext invocationContext = invocation.getInvocationContext();
      final Object targetBean = invocationContext.getTargetBean();
      final boolean isJaxrpcLifecycleBean = targetBean instanceof ServiceLifecycle;

      if (isJaxrpcLifecycleBean)
      {
         ((ServiceLifecycle) targetBean).destroy();
      }
   }

}
