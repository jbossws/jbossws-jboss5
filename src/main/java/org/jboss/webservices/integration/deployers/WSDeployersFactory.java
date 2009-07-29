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
package org.jboss.webservices.integration.deployers;

import org.jboss.deployers.plugins.deployers.DeployersImpl;
import org.jboss.wsf.spi.deployment.DeploymentAspect;

/**
 * WSDeploymentAspectDeployer factory.
 *
 * @author <a href="ropalka@redhat.com">Richard Opalka</a>
 */
public final class WSDeployersFactory
{
   
   /** Real deployers registry. */
   private final DeployersImpl delegee;
   
   /**
    * Constructor.
    * 
    * @param realDeployers real deployers registry
    */
   public WSDeployersFactory( final DeployersImpl realDeployers )
   {
      this.delegee = realDeployers;
   }

   /**
    * MC incallback method. It will be called each time DeploymentAspect bean will reach INSTALLED state.
    * 
    * @param aspect to create real WS deployer for
    */
   public void newDeployer( final DeploymentAspect aspect )
   {
      this.delegee.addDeployer( new WSDeploymentAspectDeployer( aspect ) );
   }

}
