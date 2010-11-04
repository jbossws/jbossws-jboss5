/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata.rar.spec;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.spec.JavaEEMetaDataConstants;
import org.jboss.xb.annotations.JBossXmlSchema;

/**
 * Connector meta data
 *
 * @author Jeff Zhang
 * @version $Revision:  $
 */
@XmlRootElement(name="connector", namespace=JavaEEMetaDataConstants.JAVAEE_NS)
@JBossXmlSchema(
      xmlns={@XmlNs(namespaceURI = JavaEEMetaDataConstants.JAVAEE_NS, prefix = "jee")},
      ignoreUnresolvedFieldOrClass=false,
      namespace=JavaEEMetaDataConstants.JAVAEE_NS,
      elementFormDefault=XmlNsForm.QUALIFIED,
      normalizeSpace=true)
@XmlType(name="connectorType", propOrder={"descriptionGroup", "vendorName", "EISType", "RAVersion", "license", "ra", "requiredWorkContexts"})
public class JCA16MetaData extends ConnectorMetaData
{
   private static final long serialVersionUID = 7047130842344140262L;
   
   private boolean metadataComplete;
   private List<String> requiredWorkContexts;
   
   
   public boolean isMetadataComplete() 
   {
      return metadataComplete;
   }
   
   @XmlAttribute(required=true)
   public void setMetadataComplete(boolean metadataComplete) 
   {
      this.metadataComplete = metadataComplete;
   }
   
   public List<String> getRequiredWorkContexts() 
   {
      return requiredWorkContexts;
   }
   
   @XmlElement(name = "required-work-context")
   public void setRequiredWorkContexts(List<String> requiredWorkContexts) 
   {
      this.requiredWorkContexts = requiredWorkContexts;
   }
}