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

import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.web.jboss.JBossWebMetaData;
import org.jboss.wsf.spi.deployment.Deployment;
import org.jboss.wsf.spi.deployment.DeploymentAspect;
import org.jboss.ejb3.Ejb3Deployment;

/**
 * Determines the correct runtime loader for per deployment type
 * and makes it available through the {@link Deployment}.
 *
 * @author Heiko.Braun@jboss.com
 */
public class RuntimeLoaderDeploymentAspect extends DeploymentAspect
{

   public void create(Deployment dep)
   {

      // JSE endpoints
      if (dep.getAttachment(JBossWebMetaData.class) != null)
      {
         JBossWebMetaData webMetaData = dep.getAttachment(JBossWebMetaData.class);
         ClassLoader classLoader = webMetaData.getContextLoader();
         dep.setRuntimeClassLoader(classLoader);
      }

      // EJB3 endpoints
      else if (dep.getAttachment(Ejb3Deployment.class) != null)
      {
         dep.setRuntimeClassLoader(dep.getInitialClassLoader());
      }

      // EJB21 endpoints
      else if (dep.getAttachment(JBossMetaData.class) != null)
      {
         dep.setRuntimeClassLoader(dep.getInitialClassLoader());
      }

      else
      {
         throw new IllegalArgumentException("Unable to determine runtime loader");
      }
   }
}
