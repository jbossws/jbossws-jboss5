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
package org.jboss.wsf.container.jboss50.transport;

// $Id$

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jboss.deployers.client.spi.DeployerClient;
import org.jboss.deployers.spi.attachments.MutableAttachments;
import org.jboss.deployers.vfs.spi.client.VFSDeploymentFactory;
import org.jboss.logging.Logger;
import org.jboss.virtual.VFS;
import org.jboss.virtual.VirtualFile;
import org.jboss.wsf.container.jboss50.deployment.tomcat.WebXMLRewriterImpl;
import org.jboss.wsf.spi.deployment.Deployment;
import org.jboss.wsf.spi.deployment.DeploymentAspect;
import org.jboss.wsf.spi.deployment.WSFDeploymentException;
import org.jboss.wsf.spi.transport.HttpSpec;
import org.jboss.wsf.spi.WSFRuntime;

/**
 * Publish the HTTP service endpoint to Tomcat 
 * 
 * @author Thomas.Diesler@jboss.org
 * @since 12-May-2006
 */
public class WebAppDeploymentFactory
{
   // provide logging
   private static Logger log = Logger.getLogger(WebAppDeploymentFactory.class);

   private DeployerClient mainDeployer;
   private WebXMLRewriterImpl webXMLRewriter;
   private Map<String, org.jboss.deployers.client.spi.Deployment> deploymentMap = new HashMap<String, org.jboss.deployers.client.spi.Deployment>();

   public void setMainDeployer(DeployerClient mainDeployer)
   {
      this.mainDeployer = mainDeployer;
   }

   public void setWebXMLRewriter(WebXMLRewriterImpl serviceEndpointPublisher)
   {
      this.webXMLRewriter = serviceEndpointPublisher;
   }

   public void create(Deployment dep, URL webAppURL)
   {
      if (webAppURL == null)
         throw new IllegalArgumentException("Web meta data URL cannot be null");
         
      log.debug("publishServiceEndpoint: " + webAppURL);
      try
      {
         webXMLRewriter.rewriteWebXml(dep);
         dep.setProperty(HttpSpec.PROPERTY_WEBAPP_URL, webAppURL);
         org.jboss.deployers.client.spi.Deployment deployment = createDeploymentContext(webAppURL);
         
         // Mark the deployment as generated web app so the JSE deployer hook can ignore it 
         MutableAttachments attach = (MutableAttachments)deployment.getPredeterminedManagedObjects();
         attach.addAttachment(HttpSpec.PROPERTY_GENERATED_WEBAPP, Boolean.TRUE);

         mainDeployer.deploy(deployment);

         deploymentMap.put(webAppURL.toExternalForm(), deployment);
      }
      catch (Exception ex)
      {
         WSFDeploymentException.rethrow(ex);
      }
   }

   public void destroy(Deployment dep)
   {
      URL warURL = (URL)dep.getProperty(HttpSpec.PROPERTY_WEBAPP_URL);
      if (warURL == null)
      {
         log.error("Cannot obtain generated webapp URL");
         return;
      }

      log.debug("destroyServiceEndpoint: " + warURL);
      try
      {
         org.jboss.deployers.client.spi.Deployment deployment = deploymentMap.get(warURL.toExternalForm());
         if (deployment != null)
         {
            mainDeployer.undeploy(deployment);
            deploymentMap.remove(warURL.toExternalForm());
         }
      }
      catch (Exception ex)
      {
         WSFDeploymentException.rethrow(ex);
      }
   }

   private org.jboss.deployers.client.spi.Deployment createDeploymentContext(URL warURL) throws Exception
   {
      VirtualFile file = VFS.getRoot(warURL);
      return VFSDeploymentFactory.getInstance().createVFSDeployment(file);
   }
}
