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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.web.jboss.JBossServletMetaData;
import org.jboss.metadata.web.jboss.JBossWebMetaData;
import org.jboss.metadata.web.spec.ServletMetaData;
import org.jboss.webservices.integration.util.ASHelper;
import org.jboss.wsf.common.integration.WSConstants;
import org.jboss.wsf.common.integration.WSHelper;
import org.jboss.wsf.spi.deployment.Deployment;
import org.jboss.wsf.spi.deployment.Endpoint;

/**
 * The modifier of jboss web meta data. 
 * It configures WS transport for every webservice endpoint
 * plus propagates WS stack specific context parameters if required.
 *
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public final class WebMetaDataModifier
{

   /**
    * Constructor.
    */
   public WebMetaDataModifier()
   {
      super();
   }

   /**
    * Modifies web meta data to configure webservice stack transport and properties.
    * 
    * @param dep webservice deployment
    */
   public void modify( final Deployment dep )
   {
      final JBossWebMetaData jbossWebMD = WSHelper.getRequiredAttachment( dep, JBossWebMetaData.class );

      this.propagateContextProps( dep, jbossWebMD );
      this.configureEndpoints( dep, jbossWebMD );
   }

   /**
    * Propagates stack specific context parameters if specified. 
    * 
    * @param dep webservice deployment
    * @param jbossWebMD web meta data
    */
   @SuppressWarnings( "unchecked" )
   private void propagateContextProps( final Deployment dep, final JBossWebMetaData jbossWebMD )
   {
      final Map< String, String > stackContextParams = ( Map< String, String > )
          dep.getProperty( WSConstants.STACK_CONTEXT_PARAMS );
      
      if ( stackContextParams != null )
      {
         final List< ParamValueMetaData > contextParams = this.getContextParams( jbossWebMD );

         for ( Map.Entry< String, String > entry : stackContextParams.entrySet() )
         {
            final ParamValueMetaData newParam = this.newParameter( entry.getKey(), entry.getValue() );
            contextParams.add( newParam );
         }
      }
   }

   /**
    * Configures transport servlet class for every found webservice endpoint. 
    * 
    * @param dep webservice deployment
    * @param jbossWebMD web meta data
    */
   private void configureEndpoints( final Deployment dep, final JBossWebMetaData jbossWebMD )
   {
      final Iterator< JBossServletMetaData > servlets = jbossWebMD.getServlets().iterator();
      
      while ( servlets.hasNext() )
      {
         final ServletMetaData servletMD = servlets.next();
         final ClassLoader loader = dep.getInitialClassLoader();
         final boolean isWebserviceEndpoint = ASHelper.getEndpointClass( servletMD, loader ) != null;

         if ( isWebserviceEndpoint )
         {
            // set transport servlet
            servletMD.setServletClass( this.getTransportClassName( dep ) );

            // configure webservice endpoint
            final String endpointClassName = servletMD.getServletClass();
            final List< ParamValueMetaData > initParams = this.getServletInitParams( servletMD );
            final ParamValueMetaData endpointParam = this.newParameter( 
               Endpoint.SEPID_DOMAIN_ENDPOINT, endpointClassName ); 
            
            initParams.add( endpointParam );
         }
      }
   }
   
   /**
    * Returns stack specific transport class name.
    * 
    * @param dep webservice deployment
    * @return stack specific transport class name
    * @throws IllegalStateException if transport class name is not found in deployment properties map
    */
   private String getTransportClassName( final Deployment dep )
   {
      final String transportClassName = ( String ) dep.getProperty( WSConstants.STACK_TRANSPORT_CLASS );
      
      if ( transportClassName == null )
      {
         throw new IllegalStateException( "Cannot obtain deployment property: " + WSConstants.STACK_TRANSPORT_CLASS );
      }
      
      return transportClassName;
   }
   
   /**
    * Creates new parameter with specified key and value.
    * 
    * @param key the key
    * @param value the value
    * @return new parameter
    */
   private ParamValueMetaData newParameter( final String key, final String value )
   {
      final ParamValueMetaData paramMD = new ParamValueMetaData();
      paramMD.setParamName( key );
      paramMD.setParamValue( value );
      
      return paramMD;
   }
   
   /**
    * Gets servlet init params list. Constructs new init params list if it does not exist yet.
    * 
    * @param servletMD servlet meta data
    * @return servlet init params list
    */
   private List< ParamValueMetaData > getServletInitParams( final ServletMetaData servletMD )
   {
      List< ParamValueMetaData > initParams = servletMD.getInitParam();

      if ( initParams == null )
      {
         initParams = new ArrayList< ParamValueMetaData >();
         servletMD.setInitParam( initParams );
      }
      
      return initParams;
   }

   /**
    * Gets context params list. Constructs new context params list if it does not exist yet.
    * 
    * @param jbossWebMD web meta data
    * @return context params list
    */
   private List< ParamValueMetaData > getContextParams( final JBossWebMetaData jbossWebMD )
   {
      List< ParamValueMetaData > contextParams = jbossWebMD.getContextParams();
      
      if ( contextParams == null )
      {
         contextParams = new ArrayList< ParamValueMetaData >();
         jbossWebMD.setContextParams( contextParams );
      }
      
      return contextParams;
   }

}
