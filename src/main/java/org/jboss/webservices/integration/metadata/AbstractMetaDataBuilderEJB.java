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

import org.jboss.logging.Logger;
import org.jboss.metadata.common.jboss.WebserviceDescriptionMetaData;
import org.jboss.metadata.common.jboss.WebserviceDescriptionsMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.WebservicesMetaData;
import org.jboss.webservices.integration.util.ASHelper;
import org.jboss.wsf.common.integration.WSHelper;
import org.jboss.wsf.spi.deployment.Deployment;
import org.jboss.wsf.spi.metadata.j2ee.EJBArchiveMetaData;
import org.jboss.wsf.spi.metadata.j2ee.PublishLocationAdapter;

/**
 * Common class for EJB meta data builders.
 *
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 * @author <a href="mailto:tdiesler@redhat.com">Thomas Diesler</a>
 */
abstract class AbstractMetaDataBuilderEJB
{

   /** Logger. */
   protected final Logger log = Logger.getLogger(this.getClass());

   /**
    * Builds universal EJB meta data model that is AS agnostic.
    * 
    * @param dep webservice deployment
    * @return universal EJB meta data model
    */
   final EJBArchiveMetaData create(final Deployment dep)
   {
      this.log.debug("Building JBoss agnostic meta data for EJB webservice deployment: " + dep.getSimpleName());

      final EJBArchiveMetaData ejbArchiveMD = new EJBArchiveMetaData();

      this.buildEnterpriseBeansMetaData(dep, ejbArchiveMD);
      this.buildWebservicesMetaData(dep, ejbArchiveMD);

      return ejbArchiveMD;
   }

   /**
    * Template method for build enterprise beans meta data.
    * 
    * @param dep webservice deployment
    * @param ejbMetaData universal EJB meta data model
    */
   protected abstract void buildEnterpriseBeansMetaData(Deployment dep, EJBArchiveMetaData ejbMetaData);

   /**
    * Builds webservices meta data. This methods sets:
    * <ul>
    *   <li>context root</li>
    *   <li>wsdl location resolver</li>
    *   <li>config name</li>
    *   <li>config file</li>
    * </ul>
    * 
    * @param dep webservice deployment
    * @param ejbArchiveMD universal EJB meta data model
    */
   private void buildWebservicesMetaData(final Deployment dep, final EJBArchiveMetaData ejbArchiveMD)
   {
      final JBossMetaData jbossMD = WSHelper.getRequiredAttachment(dep, JBossMetaData.class);
      final WebservicesMetaData webservicesMD = jbossMD.getWebservices();

      if (webservicesMD == null)
      {
         return;
      }

      // set context root
      final String contextRoot = webservicesMD.getContextRoot();
      ejbArchiveMD.setWebServiceContextRoot(contextRoot);
      this.log.debug("Setting context root: " + contextRoot);

      final WebserviceDescriptionsMetaData wsDescriptionsMD = webservicesMD.getWebserviceDescriptions();

      if (wsDescriptionsMD != null)
      {
         // set wsdl location resolver
         final PublishLocationAdapter resolver = new PublishLocationAdapterImpl(wsDescriptionsMD);
         ejbArchiveMD.setPublishLocationAdapter(resolver);

         final WebserviceDescriptionMetaData wsDescriptionMD = ASHelper
               .getWebserviceDescriptionMetaData(wsDescriptionsMD);
         if (wsDescriptionMD != null)
         {
            final String configName = wsDescriptionMD.getConfigName();
            final String configFile = wsDescriptionMD.getConfigFile();

            // set config name
            this.log.debug("Setting config name: " + configName);
            ejbArchiveMD.setConfigName(wsDescriptionMD.getConfigName());

            // set config file
            this.log.debug("Setting config file: " + configFile);
            ejbArchiveMD.setConfigFile(wsDescriptionMD.getConfigFile());
         }
      }
   }

}
