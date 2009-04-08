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
package org.jboss.wsf.container.jboss50.deployment.metadata;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.jws.WebService;
import javax.xml.ws.WebServiceProvider;

import org.jboss.deployers.structure.spi.DeploymentUnit;
import org.jboss.metadata.javaee.spec.EnvironmentEntriesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;
import org.jboss.metadata.web.jboss.JBossWebMetaData;
import org.jboss.wsf.spi.deployment.Deployment;
import org.jboss.wsf.spi.deployment.DeploymentAspect;
import org.jboss.wsf.spi.deployment.Deployment.DeploymentType;
import org.jboss.wsf.spi.deployment.integration.WebServiceDeclaration;
import org.jboss.wsf.spi.metadata.injection.InjectionMetaData;
import org.jboss.wsf.spi.metadata.injection.InjectionsMetaData;

/**
 * Deployment aspect that builds injection meta data.
 *
 * @author ropalka@redhat.com
 */
public final class InjectionMetaDataDeploymentAspect extends DeploymentAspect
{

   @Override
   public void create(Deployment dep)
   {
      super.create(dep);

      JBossWebMetaData webMD = dep.getAttachment(JBossWebMetaData.class);
      if (webMD == null)
         throw new IllegalStateException("JBossWebMetaData not found");
      
      List<InjectionMetaData> injectionMD = new LinkedList<InjectionMetaData>();
      DeploymentType deploymentType = dep.getType();

      if (deploymentType == DeploymentType.JAXWS_JSE)
      {
         injectionMD.addAll(buildInjectionMetaData(webMD.getEnvironmentEntries()));
      }
      else if (deploymentType == DeploymentType.JAXWS_EJB3)
      {
         // [JBWS-2074] see comment in JIRA
         log.warn("Both @Resource annotated methods/fields and descriptor specified injections don't work in handlers associated with EJB3 endpoints");
         /*
         JBossMetaData jbossMD = unit.getAttachment(JBossMetaData.class);
         JBossEnterpriseBeansMetaData jebMDs = jbossMD.getEnterpriseBeans();
         
         WebServiceDeployment webServiceDeployment = unit.getAttachment(WebServiceDeployment.class);
         EnvironmentEntriesMetaData attachment = new EnvironmentEntriesMetaData();
         
         Iterator<WebServiceDeclaration> it = webServiceDeployment.getServiceEndpoints().iterator();
         while (it.hasNext())
         {
            WebServiceDeclaration container = it.next();
            if (isWebServiceBean(container))
            {
               String ejbName = container.getComponentName();
               EnvironmentEntriesMetaData ejbEnvEntries = jebMDs.get(ejbName).getEnvironmentEntries(); 
               attachment.addAll(ejbEnvEntries);
               injectionMD.addAll(buildInjectionMetaData(ejbEnvEntries));
            }
         }
         */
      }

      dep.getService().addAttachment(InjectionsMetaData.class, new InjectionsMetaData(injectionMD));
   }

   @Override
   public void destroy(Deployment dep)
   {
      dep.getService().removeAttachment(InjectionMetaData.class);

      super.destroy(dep);
   }
   
   private List<InjectionMetaData> buildInjectionMetaData(EnvironmentEntriesMetaData envEntries)
   {
      if ((envEntries == null) || (envEntries.size() == 0))
      {
         return Collections.emptyList();
      }
      
      EnvironmentEntryMetaData eeMD = null;
      LinkedList<InjectionMetaData> retVal = new LinkedList<InjectionMetaData>();
      String envEntryName = null;
      String envEntryValue = null;
      String targetClass = null;
      String targetName = null;
      String valueClass = null;
      
      for (Iterator<EnvironmentEntryMetaData> i = envEntries.iterator(); i.hasNext();)
      {
         eeMD = i.next();
         envEntryName = eeMD.getEnvEntryName();
         envEntryValue = eeMD.getValue();
         valueClass = eeMD.getType();

         Set<ResourceInjectionTargetMetaData> injectionTargets = eeMD.getInjectionTargets();
         if ((injectionTargets != null) && (injectionTargets.size() > 0))
         {
            for (Iterator<ResourceInjectionTargetMetaData> j = injectionTargets.iterator(); j.hasNext(); )
            {
               ResourceInjectionTargetMetaData ritMD = j.next();
               targetClass = ritMD.getInjectionTargetClass();
               targetName = ritMD.getInjectionTargetName();
               InjectionMetaData injectionMD = new InjectionMetaData(targetClass, targetName, valueClass, envEntryName, envEntryValue != null);
               
               retVal.add(injectionMD);
               log.debug(injectionMD);
            }
         }
      }
      
      return retVal;
   }

   private boolean isWebServiceBean(WebServiceDeclaration container)
   {
      boolean isWebService = container.getAnnotation(WebService.class) != null;
      boolean isWebServiceProvider = container.getAnnotation(WebServiceProvider.class) != null;
      
      return isWebService || isWebServiceProvider;
   }

}
