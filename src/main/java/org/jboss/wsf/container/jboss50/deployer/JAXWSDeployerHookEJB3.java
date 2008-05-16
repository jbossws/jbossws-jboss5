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
package org.jboss.wsf.container.jboss50.deployer;

//$Id$

import org.jboss.deployers.structure.spi.DeploymentUnit;
import org.jboss.deployers.vfs.spi.structure.VFSDeploymentUnit;
import org.jboss.ejb3.EJBContainer;
import org.jboss.ejb3.Ejb3Deployment;
import org.jboss.ejb3.mdb.MessagingContainer;
import org.jboss.ejb3.stateless.StatelessContainer;
import org.jboss.metadata.serviceref.VirtualFileAdaptor;
import org.jboss.wsf.spi.deployment.Deployment;
import org.jboss.wsf.spi.deployment.Deployment.DeploymentType;
import org.jboss.wsf.spi.deployment.ArchiveDeployment;
import org.jboss.wsf.spi.deployment.Endpoint;
import org.jboss.wsf.spi.deployment.Service;

import javax.jws.WebService;
import javax.xml.ws.WebServiceProvider;
import java.util.Iterator;

/**
 * A deployer JAXWS EJB3 Endpoints
 *
 * @author Thomas.Diesler@jboss.org
 * @since 25-Apr-2007
 */
public class JAXWSDeployerHookEJB3 extends AbstractDeployerHookEJB
{
   /** Get the deployemnt type this deployer can handle
    */
   public DeploymentType getDeploymentType()
   {
      return DeploymentType.JAXWS_EJB3;
   }

   @Override
   public Deployment createDeployment(DeploymentUnit unit)
   {
      ArchiveDeployment dep = newDeployment(unit);
      dep.setRootFile(new VirtualFileAdaptor(((VFSDeploymentUnit)unit).getRoot()));
      dep.setRuntimeClassLoader(unit.getClassLoader());
      dep.setType(getDeploymentType());

      Service service = dep.getService();

      Ejb3Deployment ejb3Deployment = unit.getAttachment(Ejb3Deployment.class);
      if (ejb3Deployment == null)
         throw new IllegalStateException("Deployment unit does not contain ejb3 deployment");

      // Copy the attachments
      dep.addAttachment(Ejb3Deployment.class, ejb3Deployment);

      Iterator it = ejb3Deployment.getEjbContainers().values().iterator();
      while (it.hasNext())
      {
         EJBContainer container = (EJBContainer)it.next();
         if (isWebServiceBean(container))
         {
            String ejbName = container.getEjbName();
            String epBean = container.getBeanClassName();

            // Create the endpoint
            Endpoint ep = newEndpoint(epBean);
            ep.setShortName(ejbName);
            service.addEndpoint(ep);
         }
      }

      return dep;
   }

   @Override
   public boolean isWebServiceDeployment(DeploymentUnit unit)
   {
      Ejb3Deployment ejb3Deployment = unit.getAttachment(Ejb3Deployment.class);
      if (ejb3Deployment == null)
         return false;

      boolean isWebServiceDeployment = false;

      Iterator it = ejb3Deployment.getEjbContainers().values().iterator();
      while (it.hasNext())
      {
         EJBContainer container = (EJBContainer)it.next();
         if (isWebServiceBean(container))
         {
            isWebServiceDeployment = true;
            break;
         }
      }

      return isWebServiceDeployment;
   }

   private boolean isWebServiceBean(EJBContainer container)
   {
      boolean isWebServiceBean = false;
      if (container instanceof StatelessContainer || container instanceof MessagingContainer)
      {
         boolean isWebService = container.resolveAnnotation(WebService.class) != null;
         boolean isWebServiceProvider = container.resolveAnnotation(WebServiceProvider.class) != null;
         isWebServiceBean = isWebService || isWebServiceProvider;
      }
      return isWebServiceBean;
   }
}