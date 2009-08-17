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

import java.lang.reflect.Method;

import org.jboss.logging.Logger;
import org.jboss.wsf.spi.deployment.Endpoint;
import org.jboss.wsf.spi.invocation.Invocation;
import org.jboss.wsf.spi.invocation.InvocationHandler;

/**
 * Base class for all Web Service invocation handlers inside AS.
 * 
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 * @author <a href="mailto:tdiesler@redhat.com">Thomas Diesler</a>
 */
abstract class AbstractInvocationHandler extends InvocationHandler
{

   /** Logger. */
   protected final Logger log = Logger.getLogger(this.getClass());

   /**
    * Constructor.
    */
   protected AbstractInvocationHandler()
   {
      super();
   }

   /**
    * Creates invocation.
    * 
    * @return invocation instance
    */
   public final Invocation createInvocation()
   {
      return new Invocation();
   }

   /**
    * Initialization method.
    * 
    * @param endpoint endpoint
    */
   public void init(final Endpoint endpoint)
   {
      // does nothing
   }

   /**
    * Returns implementation method that will be used for invocation.
    * 
    * @param implClass implementation endpoint class
    * @param seiMethod SEI interface method used for method finding algorithm
    * @return implementation method
    * @throws NoSuchMethodException if implementation method wasn't found
    */
   protected final Method getImplMethod(final Class<?> implClass, final Method seiMethod) throws NoSuchMethodException
   {
      final String methodName = seiMethod.getName();
      final Class<?>[] paramTypes = seiMethod.getParameterTypes();

      return implClass.getMethod(methodName, paramTypes);
   }

}
