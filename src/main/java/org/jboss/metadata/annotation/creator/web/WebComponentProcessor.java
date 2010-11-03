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
package org.jboss.metadata.annotation.creator.web;

import java.lang.reflect.AnnotatedElement;

import org.jboss.metadata.annotation.creator.AbstractComponentProcessor;
import org.jboss.metadata.annotation.creator.DeclareRolesProcessor;
import org.jboss.metadata.annotation.creator.Processor;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.javaee.spec.EnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.web.spec.ServletsMetaData;
import org.jboss.metadata.web.spec.WebMetaData;

/**
 * Create the correct meta data for web component.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class WebComponentProcessor extends AbstractComponentProcessor<WebMetaData>
   implements Processor<WebMetaData,Class<?>>
{
   public WebComponentProcessor(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
      // @RunAs
      addTypeProcessor(new RunAsProcessor(finder));
      // @DeclareRoles
      addTypeProcessor(new DeclareRolesProcessor(finder));
   }

   @Override
   public void process(WebMetaData metaData, Class<?> type)
   {
      super.process(metaData, type);

      // 
      EnvironmentRefsGroupMetaData env = metaData.getJndiEnvironmentRefsGroup();
      if(env == null)
      {
         env = new EnvironmentRefsGroupMetaData();
         metaData.setJndiEnvironmentRefsGroup(env);   
      }
      super.process(env, type);
      
      // @RunAs
      ServletsMetaData servlets = metaData.getServlets();
      if(servlets == null)
      {
         servlets = new ServletsMetaData();
         metaData.setServlets(servlets);
      }
      super.processClass(servlets, type);
      
      // @DeclareRoles
      SecurityRolesMetaData securityRoles = metaData.getSecurityRoles();
      if(securityRoles == null)
      {
         securityRoles = new SecurityRolesMetaData();
         metaData.setSecurityRoles(securityRoles);
      }
      super.processClass(securityRoles, type);
   }
}
