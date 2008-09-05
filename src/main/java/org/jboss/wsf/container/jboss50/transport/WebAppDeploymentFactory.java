/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.wsf.container.jboss50.transport;

import org.jboss.deployers.client.spi.DeploymentFactory;
import org.jboss.deployers.structure.spi.DeploymentUnit;
import org.jboss.logging.Logger;
import org.jboss.metadata.web.jboss.JBossWebMetaData;
import org.jboss.wsf.container.jboss50.deployment.tomcat.WebMetaDataModifier;
import org.jboss.wsf.spi.deployment.Deployment;
import org.jboss.wsf.spi.deployment.WSFDeploymentException;
import org.jboss.wsf.spi.transport.HttpSpec;

/**
 * Deploy the generated webapp to JBoss by attaching it
 * to the {@link org.jboss.deployers.structure.spi.DeploymentUnit}
 * 
 * @author Thomas.Diesler@jboss.org
 * @author Heiko.Braun@jboss.com
 * 
 * @since 12-May-2006
 */
public class WebAppDeploymentFactory
{
   private WebMetaDataModifier webMetaDataModifier;

   public void setWebMetaDataModifier(WebMetaDataModifier webMetaDataModifier)
   {
      this.webMetaDataModifier = webMetaDataModifier;
   }

   /**
    * Creates and attaches web meta data to a deployment unit
    * @param dep    
    * @return a mofified deployment unit
    */
   public DeploymentUnit create(Deployment dep, JBossWebMetaData jbwmd )
   {
      if (jbwmd == null)
         throw new IllegalArgumentException("Web meta data is cannot be null");

      DeploymentUnit unit = dep.getAttachment(DeploymentUnit.class);
      if (unit != null)
      {
         try
         {
            webMetaDataModifier.modifyMetaData(dep);
         
            // Attaching it to the DeploymentUnit will cause a new webapp deployment
            unit.addAttachment(JBossWebMetaData.class, jbwmd);
            unit.addAttachment(HttpSpec.PROPERTY_GENERATED_WEBAPP, Boolean.TRUE);

         }
         catch (Exception ex)
         {
            WSFDeploymentException.rethrow(ex);
         }
      }
      else
      {
         throw new IllegalStateException("Cannot obtain deployment unit");
      }

      return unit;
   }
   
   public void destroy(Deployment dep)
   {
      // nothing to do?
   }
}
