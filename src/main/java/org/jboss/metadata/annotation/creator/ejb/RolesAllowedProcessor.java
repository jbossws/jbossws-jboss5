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
package org.jboss.metadata.annotation.creator.ejb;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;

import javax.annotation.security.RolesAllowed;

import org.jboss.annotation.javaee.Descriptions;
import org.jboss.metadata.annotation.creator.AbstractFinderUser;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.creator.ProcessorUtils;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.ejb.spec.MethodMetaData;
import org.jboss.metadata.ejb.spec.MethodPermissionMetaData;
import org.jboss.metadata.ejb.spec.MethodPermissionsMetaData;
import org.jboss.metadata.ejb.spec.MethodsMetaData;

/**
 * @RolesAllowed processor
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class RolesAllowedProcessor<T extends AnnotatedElement>
   extends AbstractFinderUser
   implements Processor<MethodPermissionsMetaData, T>
{
   public RolesAllowedProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
   }

   
   public void process(MethodPermissionsMetaData metaData, T type)
   {
      RolesAllowed allowed = finder.getAnnotation(type, RolesAllowed.class);
      if(allowed == null)
         return;

      Method method = null;
      if(type instanceof Method)
         method = (Method) type;

      String ejbName = EjbNameThreadLocal.ejbName.get();
      MethodMetaData mmd = ProcessorUtils.createMethod(ejbName, method);
      MethodPermissionMetaData perm = new MethodPermissionMetaData();
      MethodsMetaData methods = perm.getMethods();
      if(methods == null)
      {
         methods = new MethodsMetaData();
         perm.setMethods(methods);
      }
      HashSet<String> roles = new HashSet<String>();
      for(String role : allowed.value())
         roles.add(role);
      perm.setRoles(roles);
      Descriptions descriptions = ProcessorUtils.getDescription("@RolesAllowed for: "+type);
      mmd.setDescriptions(descriptions);
      methods.add(mmd);
      metaData.add(perm);
   }
   
   public Collection<Class<? extends Annotation>> getAnnotationTypes()
   {
      return ProcessorUtils.createAnnotationSet(RolesAllowed.class);
   }

}
