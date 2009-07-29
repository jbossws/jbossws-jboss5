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
package org.jboss.webservices.integration.tomcat;

import org.jboss.wsf.spi.deployment.Deployment;
import org.jboss.wsf.spi.deployment.DeploymentAspect;

/**
 * Modifies web meta data to configure webservice stack endpoints and properties.
 * 
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public final class WebMetaDataModifyingDeploymentAspect extends DeploymentAspect
{
   
   /** Web meta data modifier. */
   private WebMetaDataModifier webMetaDataModifier;

   /**
    * Constructor.
    */
   public WebMetaDataModifyingDeploymentAspect()
   {
      super();
   }

   /**
    * Sets Web meta data modifier. This method is called using MC injection.
    * 
    * @param webMDModifier web meta data modifier
    */
   public void setWebMetaDataModifier( final WebMetaDataModifier webMDModifier )
   {
      this.webMetaDataModifier = webMDModifier;
   }

   /**
    * Modifies web meta data.
    * 
    * @param dep webservice deployment
    */
   public void start( final Deployment dep )
   {
      this.webMetaDataModifier.modify( dep );
   }
   
}
