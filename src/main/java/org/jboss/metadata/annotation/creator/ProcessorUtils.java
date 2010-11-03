/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata.annotation.creator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.jboss.annotation.javaee.Descriptions;
import org.jboss.metadata.ejb.spec.MethodMetaData;
import org.jboss.metadata.ejb.spec.MethodParametersMetaData;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.DescriptionImpl;
import org.jboss.metadata.javaee.spec.DescriptionsImpl;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class ProcessorUtils
{
   public static <E extends AnnotatedElement> Set<ResourceInjectionTargetMetaData> getInjectionTargets(String name, E element)
   {
      Set<ResourceInjectionTargetMetaData> injectionTargets = null;
      if((element instanceof Class) == false)
      {
         // Create an injection target for non-class elements
         ResourceInjectionTargetMetaData target = new ResourceInjectionTargetMetaData();
         target.setInjectionTargetClass(getDeclaringClass(element));
         target.setInjectionTargetName(name);
         injectionTargets = Collections.singleton(target);
      }
      return injectionTargets;
   }

   public static <E extends AnnotatedElement> String getName(E element)
   {
      String name = element.getClass().getSimpleName();
      if(element instanceof Class)
      {
         Class c = (Class) element;
         name = c.getSimpleName();
      }
      else if(element instanceof Field)
      {
         Field f = (Field) element;
         name = f.getName();
      }
      else if(element instanceof Method)
      {
         Method m = (Method) element;
         name = m.getName();
      }
      return name;
   }

   public static <E extends AnnotatedElement> String getDeclaringClass(E element)
   {
      String c = null;
      if(element instanceof Field)
      {
         Field f = (Field) element;
         c = f.getDeclaringClass().getName();
      }
      else if(element instanceof Method)
      {
         Method m = (Method) element;
         c = m.getDeclaringClass().getName();
      }
      return c;
   }

   public static MethodMetaData createMethod(String ejbName, Method method)
   {
      MethodMetaData methodMetaData = new MethodMetaData();
      methodMetaData.setEjbName(ejbName);
      if(method == null)
         methodMetaData.setMethodName("*");
      else
      {
         methodMetaData.setMethodName(method.getName());
         MethodParametersMetaData methodParameters = ProcessorUtils.getMethodParameters(method);
         if(methodParameters != null)
            methodMetaData.setMethodParams(methodParameters);
      }
      return methodMetaData;
   }

   public static MethodParametersMetaData getMethodParameters(Method method)
   {
      MethodParametersMetaData metaData = new MethodParametersMetaData();
      for(Class<?> parameterType : method.getParameterTypes())
      {
         metaData.add(parameterType.getName());
      }
      return metaData;
   }

   public static Descriptions getDescription(String description)
   {
      DescriptionsImpl descriptions = null;
      if(description.length() > 0)
      {
         DescriptionImpl di = new DescriptionImpl();
         di.setDescription(description);
         descriptions = new DescriptionsImpl();
         descriptions.add(di);
      }
      return descriptions;
   }
   public static DescriptionGroupMetaData getDescriptionGroup(String description)
   {
      DescriptionGroupMetaData dg = null;
      if(description.length() > 0)
      {
         dg = new DescriptionGroupMetaData();
         Descriptions descriptions = getDescription(description);
         dg.setDescriptions(descriptions);
      }
      return dg;      
   }

   public static Collection<Class<? extends Annotation>> createAnnotationSet(Class<? extends Annotation> annotation)
   {
      Set<Class<? extends Annotation>> set = new HashSet<Class<? extends Annotation>>(1);
      set.add(annotation);
      return set;
   }
   
   public static Collection<Class<? extends Annotation>> createAnnotationSet(Class<? extends Annotation>... annotations)
   {
      Set<Class<? extends Annotation>> set = new HashSet<Class<? extends Annotation>>();
      for(Class<? extends Annotation> annotation : annotations)
         set.add(annotation);
      return set;
   }
   
}
