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

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jboss.deployers.spi.DeploymentException;
import org.jboss.deployers.spi.deployer.helpers.AbstractRealDeployer;
import org.jboss.deployers.structure.spi.DeploymentUnit;
import org.jboss.deployers.vfs.spi.structure.VFSDeploymentUnit;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.serviceref.VirtualFileAdaptor;
import org.jboss.metadata.web.jboss.JBossWebMetaData;
import org.jboss.metadata.web.spec.ServletMetaData;
import org.jboss.virtual.VirtualFile;
import org.jboss.webservices.integration.invocation.InvocationHandlerEJB3;
import org.jboss.webservices.integration.util.ASHelper;
import org.jboss.wsf.spi.SPIProvider;
import org.jboss.wsf.spi.SPIProviderResolver;
import org.jboss.wsf.spi.deployment.ArchiveDeployment;
import org.jboss.wsf.spi.deployment.Deployment;
import org.jboss.wsf.spi.deployment.Deployment.DeploymentType;
import org.jboss.wsf.spi.deployment.DeploymentModelFactory;
import org.jboss.wsf.spi.deployment.Endpoint;
import org.jboss.wsf.spi.deployment.UnifiedVirtualFile;
import org.jboss.wsf.spi.deployment.WSFDeploymentException;
import org.jboss.wsf.spi.deployment.integration.WebServiceDeclaration;
import org.jboss.wsf.spi.deployment.integration.WebServiceDeployment;
import org.jboss.wsf.spi.metadata.webservices.PortComponentMetaData;
import org.jboss.wsf.spi.metadata.webservices.WebserviceDescriptionMetaData;
import org.jboss.wsf.spi.metadata.webservices.WebservicesMetaData;

/**
 * This deployer initializes JBossWS deployment meta data. 
 *
 * @author <a href="ropalka@redhat.com">Richard Opalka</a>
 */
public final class WSDeploymentDeployer extends AbstractRealDeployer
{
   
   /** WSDL and XSD files filter. */
   private static final WSVirtualFileFilter WS_FILE_FILTER = new WSVirtualFileFilter();
   /** Deployment model factory. */
   private final DeploymentModelFactory deploymentModelFactory;

   /**
    * Constructor.
    */
   public WSDeploymentDeployer()
   {
      super();
      
      // inputs
      this.addInput( JBossWebMetaData.class );
      this.addInput( DeploymentType.class );
      
      // outputs
      this.addOutput( JBossWebMetaData.class );
      this.addOutput( Deployment.class );

      // deployment factory
      final SPIProvider spiProvider = SPIProviderResolver.getInstance().getProvider();
      this.deploymentModelFactory = spiProvider.getSPI( DeploymentModelFactory.class );
   }

   /**
    * Creates new Web Service deployment and registers it with deployment unit.
    * 
    * @param unit deployment unit
    * @throws DeploymentException if error occurs
    */
   @Override
   protected void internalDeploy( final DeploymentUnit unit ) throws DeploymentException
   {
      if ( ASHelper.isJaxwsJseDeployment( unit ) )
      {
         this.newJaxwsJseDeployment( unit );
      }
      else if ( ASHelper.isJaxwsEjbDeployment( unit ) )
      {
         this.newJaxwsEjbDeployment( unit );
      }
      else if ( ASHelper.isJaxrpcJseDeployment( unit ) )
      {
         this.newJaxrpcJseDeployment( unit );
      }
      else if ( ASHelper.isJaxrpcEjbDeployment( unit ) )
      {
         this.newJaxrpcEjbDeployment( unit );
      }
   }

   /**
    * Creates new JAXRPC EJB21 deployment and registers it with deployment unit.
    * 
    * @param unit deployment unit
    */
   private void newJaxrpcEjbDeployment( final DeploymentUnit unit )
   {
      final ArchiveDeployment dep = this.newDeployment( unit );
      final JBossMetaData jbmd = this.getAndPropagateAttachment( JBossMetaData.class, unit, dep );
      final WebservicesMetaData wsMetaData = this.getAndPropagateAttachment( WebservicesMetaData.class, unit, dep );
      this.getAndPropagateAttachment( WebServiceDeployment.class, unit, dep );
      
      for ( WebserviceDescriptionMetaData wsd : wsMetaData.getWebserviceDescriptions() )
      {
         for ( PortComponentMetaData pcmd : wsd.getPortComponents() )
         {
            final String ejbName = pcmd.getEjbLink();
            final JBossEnterpriseBeanMetaData beanMetaData = jbmd.getEnterpriseBean( ejbName );
            final String ejbClass = beanMetaData.getEjbClass();

            this.createEndpoint( ejbClass, ejbName, dep );
         }
      }
      
      dep.addAttachment( DeploymentUnit.class, unit );
      unit.addAttachment( Deployment.class, dep );
   }
   
   /**
    * Creates new JAXWS EJB3 deployment and registers it with deployment unit.
    * 
    * @param unit deployment unit
    */
   private void newJaxwsEjbDeployment( final DeploymentUnit unit )
   {
      final ArchiveDeployment dep = this.newDeployment( unit );
      this.getAndPropagateAttachment( WebServiceDeployment.class, unit, dep );

      final Iterator< WebServiceDeclaration > ejbIterator = ASHelper.getJaxwsEjbs( unit ).iterator();
      while ( ejbIterator.hasNext() )
      {
         final WebServiceDeclaration container = ejbIterator.next();
         final String ejbName = container.getComponentName();
         final String ejbClass = container.getComponentClassName();

         final Endpoint ep = this.createEndpoint( ejbClass, ejbName, dep );
         ep.setProperty( InvocationHandlerEJB3.CONTAINER_NAME, container.getContainerName() );
      }
      
      dep.addAttachment( DeploymentUnit.class, unit );
      unit.addAttachment( Deployment.class, dep );
   }
   
   /**
    * Creates new JAXRPC JSE deployment and registers it with deployment unit.
    * 
    * @param unit deployment unit
    */
   private void newJaxrpcJseDeployment( final DeploymentUnit unit )
   {
      final ArchiveDeployment dep = this.newDeployment( unit );
      final JBossWebMetaData webMetaData = this.getAndPropagateAttachment( JBossWebMetaData.class, unit, dep );
      final WebservicesMetaData wsMetaData = this.getAndPropagateAttachment( WebservicesMetaData.class, unit, dep );

      for ( WebserviceDescriptionMetaData wsd : wsMetaData.getWebserviceDescriptions() )
      {
         for ( PortComponentMetaData pcmd : wsd.getPortComponents() )
         {
            final String servletName = pcmd.getServletLink();
            final ServletMetaData servletMD = ASHelper.getServletForName( webMetaData, servletName );
            final String servletClass = ASHelper.getEndpointName( servletMD );

            this.createEndpoint( servletClass, servletName, dep );
         }
      }

      dep.addAttachment( DeploymentUnit.class, unit );
      unit.addAttachment( Deployment.class, dep );
   }
   
   /**
    * Creates new JAXWS JSE deployment and registers it with deployment unit.
    * 
    * @param unit deployment unit
    */
   private void newJaxwsJseDeployment( final DeploymentUnit unit )
   {
      final ArchiveDeployment dep = this.newDeployment( unit );
      this.getAndPropagateAttachment( JBossWebMetaData.class, unit, dep );

      final List< ServletMetaData > servlets = ASHelper.getJaxwsServlets( unit );
      for ( ServletMetaData servlet : servlets )
      {
         final String servletName = servlet.getName();
         final String servletClass = ASHelper.getEndpointName( servlet );

         this.createEndpoint( servletClass, servletName, dep );
      }

      dep.addAttachment( DeploymentUnit.class, unit );
      unit.addAttachment( Deployment.class, dep );
   }

   /**
    * Creates new Web Service deployment.
    * 
    * @param unit deployment unit
    * @return archive deployment
    */
   private ArchiveDeployment newDeployment( final DeploymentUnit unit )
   {
      final ArchiveDeployment dep = ( ArchiveDeployment ) this.deploymentModelFactory.
         newDeployment( unit.getSimpleName(), unit.getClassLoader() );

      if ( unit instanceof VFSDeploymentUnit )
      {
         final VFSDeploymentUnit vfsUnit = ( VFSDeploymentUnit ) unit;
         final List< VirtualFile > virtualFiles = vfsUnit.getMetaDataFiles( WSDeploymentDeployer.WS_FILE_FILTER );
         final Set< UnifiedVirtualFile > uVirtualFiles = new HashSet< UnifiedVirtualFile >();
         for ( VirtualFile vf : virtualFiles )
         {
            // Adding the roots of the virtual files.
            try
            {
               uVirtualFiles.add( new VirtualFileAdaptor( vf.getVFS().getRoot() ) );
            }
            catch ( IOException ioe )
            {
               throw new WSFDeploymentException( ioe );
            }
         }
         dep.setMetadataFiles( new LinkedList<UnifiedVirtualFile>( uVirtualFiles ) );
      }

      if ( unit.getParent() != null )
      {
         final DeploymentUnit parentUnit = unit.getParent();
         final ArchiveDeployment parentDep = ( ArchiveDeployment ) this.deploymentModelFactory.
            newDeployment( parentUnit.getSimpleName(), parentUnit.getClassLoader() );
         dep.setParent( parentDep );
      }

      dep.setRootFile( new VirtualFileAdaptor( ( ( VFSDeploymentUnit ) unit ).getRoot() ) );
      dep.setRuntimeClassLoader( unit.getClassLoader() );
      final DeploymentType deploymentType = ASHelper.getRequiredAttachment( unit, DeploymentType.class );
      dep.setType( deploymentType );

      return dep;
   }

   /**
    * Creates new Web Service endpoint.
    * 
    * @param endpointClass endpoint class name
    * @param endpointName endpoint name
    * @param dep deployment
    * @return WS endpoint
    */
   private Endpoint createEndpoint( final String endpointClass, final String endpointName, final Deployment dep )
   {
      if ( endpointName == null )
      {
         throw new NullPointerException( "Null endpoint name" );
      }

      if ( endpointClass == null )
      {
         throw new NullPointerException( "Null endpoint class" );
      }
      
      final Endpoint endpoint = this.deploymentModelFactory.newEndpoint( endpointClass );
      endpoint.setShortName( endpointName );
      dep.getService().addEndpoint( endpoint );
      
      return endpoint;
   }
   
   /**
    * Gets specified attachment from deployment unit. 
    * Checks it's not null and then propagates it to <b>dep</b>
    * attachments. Finally it returns attachment value.
    * 
    * @param <A> class type
    * @param attachment attachment
    * @param unit deployment unit
    * @param dep deployment
    * @return attachment value if found in unit
    */
   private <A> A getAndPropagateAttachment
   (
      final Class< A > attachment, final DeploymentUnit unit, final Deployment dep
   )
   {
      final A attachmentValue = ASHelper.getOptionalAttachment( unit, attachment );
      
      if ( attachmentValue != null )
      {
         dep.addAttachment( attachment , attachmentValue );
         return attachmentValue;
      }

      throw new IllegalStateException( "Deployment unit does not contain " + attachment );
   }
   
}
