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
package org.jboss.wsf.container.jboss50;

// $Id$

import org.dom4j.Document;
import org.jboss.wsf.framework.deployment.WebAppGeneratorDeploymentAspect;
import org.jboss.wsf.spi.deployment.Deployment;
import org.jboss.wsf.spi.deployment.SecurityHandler;

/**
 * Add doctype declarations to the generated descriptors 
 * 
 * @author Thomas.Diesler@jboss.org
 * @since 13-Oct-2007
 */
public class WebApp50GeneratorDeploymentAspect extends WebAppGeneratorDeploymentAspect
{
   @Override
   protected Document createWebAppDescriptor(Deployment dep, SecurityHandler securityHandler)
   {
      Document document = super.createWebAppDescriptor(dep, securityHandler);
      document.addDocType("web-app", "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN", "http://java.sun.com/dtd/web-app_2_3.dtd");
      return document;
   }

   @Override
   protected Document createJBossWebAppDescriptor(Deployment dep, SecurityHandler securityHandler)
   {
      Document document = super.createJBossWebAppDescriptor(dep, securityHandler);
      document.addDocType("jboss-web", "-//JBoss//DTD Web Application 5.0//EN", "http://www.jboss.org/j2ee/dtd/jboss-web_5_0.dtd");
      return document;
   }
}
