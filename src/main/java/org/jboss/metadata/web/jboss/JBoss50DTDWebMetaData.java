/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
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

import org.jboss.xb.annotations.JBossXmlSchema;

/**
 * DTD based schema for use with jboss-web.xml without a namespace
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
@XmlRootElement(name="jboss-web", namespace="")
@JBossXmlSchema(
      xmlns={@XmlNs(namespaceURI = "", prefix = "jee")},
      ignoreUnresolvedFieldOrClass=false,
      namespace="",
      elementFormDefault=XmlNsForm.UNSET,
      normalizeSpace=true)
@XmlType(name="jboss-webType", namespace="", propOrder={"classLoading", "securityDomain", "jaccAllStoreRole", "contextRoot",
      "virtualHosts", "useSessionCookies", "replicationConfig", "environmentRefsGroup", "securityRoles", "messageDestinations",
      "webserviceDescriptions", "depends", "servlets", "maxActiveSessions", "passivationConfig"})
public class JBoss50DTDWebMetaData extends JBossWebMetaData
{
   private static final long serialVersionUID = 1;
}
