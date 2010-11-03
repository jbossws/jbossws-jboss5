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
package org.jboss.metadata.web.jboss;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.xb.annotations.JBossXmlConstants;
import org.jboss.xb.annotations.JBossXmlSchema;
import org.jboss.xb.annotations.JBossXmlType;

/**
 * A JBoss4xDTDWebMetaData.
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
@XmlRootElement(name="jboss-web", namespace="")
@JBossXmlSchema(
      xmlns={@XmlNs(namespaceURI = "", prefix = "jee")},
      ignoreUnresolvedFieldOrClass=false,
      namespace="",
      elementFormDefault=XmlNsForm.UNSET,
      normalizeSpace=true)
@XmlType(name="jboss-webType", namespace="", propOrder={"classLoading", "securityDomain", "jaccAllStoreRole", "contextRoot",
      "virtualHosts", "useSessionCookies", "replicationConfig", "environmentRefsGroup4x", "securityRoles", "messageDestinations",
      "webserviceDescriptions", "depends", "servlets"})
@JBossXmlType(modelGroup=JBossXmlConstants.MODEL_GROUP_UNORDERED_SEQUENCE)
public class JBoss4xDTDWebMetaData extends JBossWebMetaData
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 1L;

   public void setEnvironmentRefsGroup4x(JBossWeb4xEnvironmentRefsGroupMetaData env)
   {
      super.setEnvironmentRefsGroup(env);
   }
}
