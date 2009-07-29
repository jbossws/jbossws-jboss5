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
package org.jboss.webservices.integration.metadata;

import org.jboss.deployers.structure.spi.DeploymentUnit;
import org.jboss.webservices.integration.util.ASHelper;
import org.jboss.wsf.common.integration.WSHelper;
import org.jboss.wsf.spi.deployment.Deployment;
import org.jboss.wsf.spi.deployment.DeploymentAspect;
import org.jboss.wsf.spi.metadata.j2ee.EJBArchiveMetaData;
import org.jboss.wsf.spi.metadata.j2ee.JSEArchiveMetaData;

/**
 * An aspect that builds container independent meta data. 
 *
 * @author Thomas.Diesler@jboss.org
 * @author <a href="ropalka@redhat.com">Richard Opalka</a>
 */
public final class ContainerMetaDataDeploymentAspect extends DeploymentAspect
{
   
   /** JSE meta data builder. */
   private JSEMetaDataBuilder jseMetaDataBuilder = new JSEMetaDataBuilder();
   /** EJB3 meta data builder. */
   private EJB3MetaDataBuilder ejb3MetaDataBuilder = new EJB3MetaDataBuilder();
   /** EJB21 meta data builder. */
   private EJB21MetaDataBuilder ejb21MetaDataBuilder = new EJB21MetaDataBuilder();

   /**
    * Constructor.
    */
   public ContainerMetaDataDeploymentAspect()
   {
      super();
   }
   
   /**
    * Build container independent meta data.
    * 
    * @param dep webservice deployment
    */
   @Override
   public void start( final Deployment dep )
   {
      final DeploymentUnit unit = WSHelper.getRequiredAttachment( dep, DeploymentUnit.class );
      
      if ( ASHelper.isJseDeployment( unit ) )
      {
         final JSEArchiveMetaData jseMetaData = this.jseMetaDataBuilder.create( dep, unit );
         dep.addAttachment( JSEArchiveMetaData.class, jseMetaData );
      }
      else if ( ASHelper.isJaxwsEjbDeployment( unit ) )
      {
         final EJBArchiveMetaData ejbMetaData = this.ejb3MetaDataBuilder.create( dep, unit );
         dep.addAttachment( EJBArchiveMetaData.class, ejbMetaData );
      }
      else if ( ASHelper.isJaxrpcEjbDeployment( unit ) )
      {
         final EJBArchiveMetaData ejbMetaData = this.ejb21MetaDataBuilder.create( dep, unit );
         dep.addAttachment( EJBArchiveMetaData.class, ejbMetaData );
      }
   }
   
}
