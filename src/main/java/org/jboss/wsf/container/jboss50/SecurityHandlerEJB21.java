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
package org.jboss.wsf.container.jboss50;

//$Id$

import org.dom4j.Element;
import org.jboss.metadata.common.ejb.IAssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.spec.AssemblyDescriptorMetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.wsf.spi.deployment.Deployment;
import org.jboss.wsf.spi.deployment.SecurityHandler;
import org.jboss.wsf.spi.metadata.j2ee.EJBArchiveMetaData;

import java.util.Iterator;
import java.util.Map;

/**
 * Generate a service endpoint deployment for EJB endpoints 
 * 
 * @author Thomas.Diesler@jboss.org
 * @since 12-May-2006
 */
public class SecurityHandlerEJB21 implements SecurityHandler
{
   public void addSecurityDomain(Element jbossWeb, Deployment dep)
   {
      EJBArchiveMetaData ejbMetaData = dep.getAttachment(EJBArchiveMetaData.class);
      if (ejbMetaData == null)
         throw new IllegalStateException("Cannot obtain application meta data");
      
      String securityDomain = ejbMetaData.getSecurityDomain();
      if (securityDomain != null)
      {
         if (securityDomain.startsWith("java:/jaas/") == false)
            securityDomain = "java:/jaas/" + securityDomain;
         
         jbossWeb.addElement("security-domain").addText(securityDomain);
      }
   }
   
   public void addSecurityRoles(Element webApp, Deployment dep)
   {
      // Fix: http://jira.jboss.org/jira/browse/JBWS-309
      JBossMetaData jbmd = dep.getAttachment(JBossMetaData.class);
      IAssemblyDescriptorMetaData assemblyDescriptor = jbmd.getAssemblyDescriptor();
      if (assemblyDescriptor != null)
      {
         SecurityRolesMetaData srmd = assemblyDescriptor.getSecurityRoles();
         if (srmd != null)
         {
            Iterator it = srmd.keySet().iterator();
            while (it.hasNext())
            {
               webApp.addElement("security-role").addElement("role-name").addText((String)it.next());
            }
         }
      }
   }
}
