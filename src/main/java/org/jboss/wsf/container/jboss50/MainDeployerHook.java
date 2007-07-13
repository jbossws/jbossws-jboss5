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

//$Id$

import org.jboss.deployers.spi.DeploymentException;
import org.jboss.deployers.structure.spi.DeploymentUnit;
import org.jboss.wsf.container.jboss50.AbstractDeployerHook;
import org.jboss.wsf.spi.deployment.Deployment;

/**
 * A basic hook that delegates a deployment manger.
 * 
 * @author Thomas.Diesler@jboss.org
 * @since 25-Apr-2007
 */
public class MainDeployerHook extends AbstractDeployerHook
{
   public void deploy(DeploymentUnit unit) throws DeploymentException
   {
      if (ignoreDeployment(unit))
         return;
      
      Deployment dep = unit.getAttachment(Deployment.class);
      if (dep != null)
      {
         deploymentAspectManager.deploy(dep);
      }
   }

   public void undeploy(DeploymentUnit unit)
   {
      if (ignoreDeployment(unit))
         return;
      
      Deployment dep = unit.getAttachment(Deployment.class);
      if (dep != null)
      {
         deploymentAspectManager.undeploy(dep);
      }
   }
}
