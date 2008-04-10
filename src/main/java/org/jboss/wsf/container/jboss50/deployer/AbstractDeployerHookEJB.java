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
package org.jboss.wsf.container.jboss50.deployer;

import org.jboss.deployers.structure.spi.DeploymentUnit;
import org.jboss.deployers.spi.DeploymentException;
import org.jboss.wsf.spi.deployment.Deployment;
import static org.jboss.wsf.spi.deployment.Deployment.DeploymentState;

//$Id$

/**
 * An abstract deployer for EJB Endpoints
 *
 * @author Thomas.Diesler@jboss.org
 * @since 25-Apr-2007
 */
public abstract class AbstractDeployerHookEJB extends ArchiveDeployerHook
{

   public void deploy(DeploymentUnit unit) throws DeploymentException
   {
      if(!ignoreDeployment(unit) && isWebServiceDeployment(unit))
      {
         super.deploy(unit); // Calls create 

         Deployment dep = unit.getAttachment(Deployment.class);
         if(null==dep || DeploymentState.CREATED != dep.getState())
            throw new DeploymentException("Create step failed");

         // execute the 'start' step
         getWsfRuntime().start(dep); 

      }

   }
}
