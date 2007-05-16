/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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
package org.jboss.ws.integration.jboss50;

// $Id$

import java.lang.reflect.Method;

import javax.xml.rpc.server.ServiceLifecycle;
import javax.xml.rpc.server.ServletEndpointContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceException;

import org.jboss.ws.integration.Endpoint;
import org.jboss.ws.integration.invocation.EndpointInvocation;
import org.jboss.ws.integration.invocation.InvocationContext;
import org.jboss.ws.integration.invocation.WebServiceContextInjector;

/**
 * Handles invocations on JSE endpoints.
 *
 * @author Thomas.Diesler@jboss.org
 * @since 25-Apr-2007
 */
public class InvocationHandlerJSE extends AbstractInvocationHandler
{
   private Object targetBean;

   public void create(Endpoint ep)
   {
      try
      {
         Class epImpl = ep.getTargetBean();
         targetBean = epImpl.newInstance();
      }
      catch (Exception ex)
      {
         throw new WebServiceException("Cannot load target bean");
      }
   }

   public void invoke(Endpoint ep, EndpointInvocation epInv) throws Exception
   {
      try
      {
         InvocationContext invContext = epInv.getInvocationContext();
         WebServiceContext wsContext = invContext.getAttachment(WebServiceContext.class);
         if (wsContext != null)
         {
            new WebServiceContextInjector().injectContext(targetBean, (WebServiceContext)wsContext);
         }

         if (targetBean instanceof ServiceLifecycle)
         {
            ServletEndpointContext sepContext = invContext.getAttachment(ServletEndpointContext.class);
            if (sepContext == null)
               throw new IllegalStateException("Cannot obtain ServletEndpointContext");
            
            ((ServiceLifecycle)targetBean).init(sepContext);
         }

         try
         {
            Method method = getImplMethod(ep.getTargetBean(), epInv.getJavaMethod());
            Object retObj = method.invoke(targetBean, epInv.getArgs());
            epInv.setReturnValue(retObj);
         }
         finally
         {
            if (targetBean instanceof ServiceLifecycle)
            {
               ((ServiceLifecycle)targetBean).destroy();
            }
         }
      }
      catch (Exception e)
      {
         handleInvocationException(e);
      }
   }
}
