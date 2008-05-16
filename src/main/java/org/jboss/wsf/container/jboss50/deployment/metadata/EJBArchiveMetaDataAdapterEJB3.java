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
package org.jboss.wsf.container.jboss50.deployment.metadata;

// $Id$

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jboss.deployers.structure.spi.DeploymentUnit;
import org.jboss.ejb3.Container;
import org.jboss.ejb3.EJBContainer;
import org.jboss.ejb3.Ejb3Deployment;
import org.jboss.ejb3.mdb.MessagingContainer;
import org.jboss.ejb3.stateless.StatelessContainer;
import org.jboss.logging.Logger;
import org.jboss.metadata.common.jboss.WebserviceDescriptionMetaData;
import org.jboss.metadata.common.jboss.WebserviceDescriptionsMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnterpriseBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossGenericBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.jboss.WebservicesMetaData;
import org.jboss.metadata.ejb.spec.ActivationConfigPropertyMetaData;
import org.jboss.metadata.javaee.spec.PortComponent;
import org.jboss.wsf.spi.deployment.Deployment;
import org.jboss.wsf.spi.metadata.j2ee.EJBArchiveMetaData;
import org.jboss.wsf.spi.metadata.j2ee.EJBMetaData;
import org.jboss.wsf.spi.metadata.j2ee.EJBSecurityMetaData;
import org.jboss.wsf.spi.metadata.j2ee.MDBMetaData;
import org.jboss.wsf.spi.metadata.j2ee.SLSBMetaData;
import org.jboss.wsf.spi.metadata.j2ee.EJBArchiveMetaData.PublishLocationAdapter;

/**
 * Build container independent application meta data 
 *
 * @author Thomas.Diesler@jboss.org
 * @since 14-Apr-2007
 */
public class EJBArchiveMetaDataAdapterEJB3
{
   // logging support
   private static Logger log = Logger.getLogger(EJBArchiveMetaDataAdapterEJB3.class);

   public EJBArchiveMetaData buildMetaData(Deployment dep, DeploymentUnit unit)
   {
      Ejb3Deployment ejb3Deployment = unit.getAttachment(Ejb3Deployment.class);
      dep.addAttachment(Ejb3Deployment.class, ejb3Deployment);

      EJBArchiveMetaData umd = new EJBArchiveMetaData();
      buildEnterpriseBeansMetaData(umd, ejb3Deployment);

      JBossMetaData jbMetaData = unit.getAttachment(JBossMetaData.class);
      if (jbMetaData != null)
         buildWebservicesMetaData(umd, jbMetaData);

      return umd;
   }

   private void buildWebservicesMetaData(EJBArchiveMetaData ejbMetaData, JBossMetaData jbMetaData)
   {
      WebservicesMetaData wsMetaData = jbMetaData.getWebservices();
      if (wsMetaData != null)
      {
         String contextRoot = wsMetaData.getContextRoot();
         ejbMetaData.setWebServiceContextRoot(contextRoot);

         ejbMetaData.setPublishLocationAdapter(getPublishLocationAdpater(wsMetaData));

         WebserviceDescriptionsMetaData wsDescriptions = wsMetaData.getWebserviceDescriptions();
         if (wsDescriptions != null)
         {
            if (wsDescriptions.size() > 1)
               log.warn("Multiple <webservice-description> elements not supported");

            if (wsDescriptions.size() > 0)
            {
               WebserviceDescriptionMetaData wsd = wsDescriptions.iterator().next();
               ejbMetaData.setConfigName(wsd.getConfigName());
               ejbMetaData.setConfigFile(wsd.getConfigFile());
            }
         }
      }
   }

   private void buildEnterpriseBeansMetaData(EJBArchiveMetaData jarMetaData, Ejb3Deployment ejb3Deployment)
   {
      List<EJBMetaData> ejbMetaDataList = new ArrayList<EJBMetaData>();
      Iterator<Container> it = ejb3Deployment.getEjbContainers().values().iterator();
      while (it.hasNext())
      {
         EJBContainer container = (EJBContainer)it.next();
         
         EJBMetaData ejbMetaData = null;
         PortComponent pcMetaData = null;
         if (container instanceof StatelessContainer)
         {
            ejbMetaData = new SLSBMetaData();
            JBossEnterpriseBeanMetaData beanMetaData = container.getXml();
            if (beanMetaData instanceof JBossGenericBeanMetaData)
            {
               pcMetaData = ((JBossGenericBeanMetaData)beanMetaData).getPortComponent();
            }
            else if (beanMetaData instanceof JBossSessionBeanMetaData)
            {
               pcMetaData = ((JBossSessionBeanMetaData)beanMetaData).getPortComponent();
            }
         }
         else if (container instanceof MessagingContainer)
         {
            ejbMetaData = new MDBMetaData();
            MessagingContainer mdb = (MessagingContainer)container;
            Map props = mdb.getActivationConfigProperties();
            if (props != null)
            {
               ActivationConfigPropertyMetaData destProp = (ActivationConfigPropertyMetaData)props.get("destination");
               if (destProp != null)
               {
                  String destination = destProp.getValue();
                  ((MDBMetaData)ejbMetaData).setDestinationJndiName(destination);
               }
            }
         }

         if (ejbMetaData != null)
         {
            ejbMetaData.setEjbName(container.getEjbName());
            ejbMetaData.setEjbClass(container.getBeanClassName());

            if (pcMetaData != null)
            {
               ejbMetaData.setPortComponentName(pcMetaData.getPortComponentName());
               ejbMetaData.setPortComponentURI(pcMetaData.getPortComponentURI());
               EJBSecurityMetaData smd = new EJBSecurityMetaData();
               smd.setAuthMethod(pcMetaData.getAuthMethod());
               smd.setTransportGuarantee(pcMetaData.getTransportGuarantee());
               smd.setSecureWSDLAccess(pcMetaData.getSecureWSDLAccess());
               ejbMetaData.setSecurityMetaData(smd);
            }
            
            ejbMetaDataList.add(ejbMetaData);
         }
      }
      
      jarMetaData.setEnterpriseBeans(ejbMetaDataList);
   }

   private PublishLocationAdapter getPublishLocationAdpater(final WebservicesMetaData wsMetaData)
   {
      return new PublishLocationAdapter() {
         public String getWsdlPublishLocationByName(String name)
         {
            String wsdlPublishLocation = null;
            WebserviceDescriptionsMetaData wsDescriptions = wsMetaData.getWebserviceDescriptions();
            if (wsDescriptions != null && wsDescriptions.get(name) != null)
            {
               WebserviceDescriptionMetaData wsdMetaData = wsDescriptions.get(name);
               wsdlPublishLocation = wsdMetaData.getWsdlPublishLocation();
            }
            return wsdlPublishLocation;
         }
      };
   }
}
