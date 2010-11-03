/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors as indicated
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
package org.jboss.metadata.annotation.creator.ejb.jboss;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collection;

import javax.ejb.Timeout;

import org.jboss.metadata.annotation.creator.AbstractFinderUser;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.common.ejb.ITimeoutTarget;
import org.jboss.metadata.ejb.spec.NamedMethodMetaData;

/**
 * Process @Timeout annotation.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 67275 $
 */
public class TimeoutProcessor extends AbstractFinderUser
   implements Processor<ITimeoutTarget, Method>
{
   public TimeoutProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }
   
   public void process(ITimeoutTarget bean, Method method)
   {
      Timeout timeout = finder.getAnnotation(method, Timeout.class);
      if(timeout == null)
         return;
      
      NamedMethodMetaData timeoutMethod = new NamedMethodMetaData();
      timeoutMethod.setMethodName(method.getName());
      timeoutMethod.setMethodParams(ProcessorUtils.getMethodParameters(method));
      bean.setTimeoutMethod(timeoutMethod);
   }
   
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(Timeout.class);
   }
}
