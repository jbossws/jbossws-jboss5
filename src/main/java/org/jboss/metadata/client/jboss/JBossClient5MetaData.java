/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors as indicated
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
package org.jboss.metadata.client.jboss;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.spec.JavaEEMetaDataConstants;
import org.jboss.xb.annotations.JBossXmlSchema;

/**
 * JBoss specific meta data for JavaEE 5 clients.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision$
 */
@XmlRootElement(name="jboss-client", namespace=JavaEEMetaDataConstants.JBOSS_NS)
@JBossXmlSchema(
      xmlns={@XmlNs(namespaceURI = JavaEEMetaDataConstants.JBOSS_NS, prefix = "jboss"),
            @XmlNs(namespaceURI = JavaEEMetaDataConstants.JAVAEE_NS, prefix = "jee"),
            @XmlNs(namespaceURI = JavaEEMetaDataConstants.XSD_NS, prefix = "xsd")},
      ignoreUnresolvedFieldOrClass=false,
      namespace=JavaEEMetaDataConstants.JBOSS_NS,
      elementFormDefault=XmlNsForm.QUALIFIED,
      normalizeSpace=true)
@XmlType(name="jboss-clientType", namespace=JavaEEMetaDataConstants.JBOSS_NS, propOrder={"callbackHandler", "metadataComplete",
      "descriptionGroup", "jndiName", "jndiEnvironmentRefsGroup", "messageDestinations", "depends"})
public class JBossClient5MetaData extends JBossClientMetaData
{
   private static final long serialVersionUID = 1;
}