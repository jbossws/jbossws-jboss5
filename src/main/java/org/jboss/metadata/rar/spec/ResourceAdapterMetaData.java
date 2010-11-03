/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * ResourceAdapter meta data
 *
 * @author Jeff Zhang
 * @version $Revision: $
 */
@XmlType(name="resourceadapterType", 
      propOrder={"raClass", "configProperty", "outboundRa", "inboundRa", "adminObjects", "securityPermissions"})
public class ResourceAdapterMetaData extends IdMetaDataImpl
{
   private static final long serialVersionUID = -1583292998139497984L;

   private String raClass;
   private List<ConfigPropertyMetaData> configProperty;
   private OutboundRaMetaData outboundRa;
   private InboundRaMetaData inboundRa;
   private List<AdminObjectMetaData> adminObjects;
   private List<SecurityPermissionMetaData> securityPermissions;

   @XmlElement(name="resourceadapter-class")
   public void setRaClass(String raClass) {
      this.raClass = raClass;
   }

   public String getRaClass() {
      return raClass;
   }

   @XmlElement(name="config-property")
   public void setConfigProperty(List<ConfigPropertyMetaData> configProperty) {
      this.configProperty = configProperty;
   }

   public List<ConfigPropertyMetaData> getConfigProperty() {
      return configProperty;
   }

   @XmlElement(name="outbound-resourceadapter")
   public void setOutboundRa(OutboundRaMetaData outboundRa) {
      this.outboundRa = outboundRa;
   }

   public OutboundRaMetaData getOutboundRa() {
      return outboundRa;
   }

   @XmlElement(name="inbound-resourceadapter")
   public void setInboundRa(InboundRaMetaData inboundRa) {
      this.inboundRa = inboundRa;
   }

   public InboundRaMetaData getInboundRa() {
      return inboundRa;
   }

   @XmlElement(name="adminobject")
   public void setAdminObjects(List<AdminObjectMetaData> adminObjects) {
      this.adminObjects = adminObjects;
   }

   public List<AdminObjectMetaData> getAdminObjects() {
      return adminObjects;
   }

   @XmlElement(name="security-permission")
   public void setSecurityPermissions(List<SecurityPermissionMetaData> securityPermissions) {
      this.securityPermissions = securityPermissions;
   }

   public List<SecurityPermissionMetaData> getSecurityPermissions() {
      return securityPermissions;
   }
   
   public String toString()
   {
      StringBuffer buffer = new StringBuffer();
      buffer.append("ResourceAdapterMetaData").append('@');
      buffer.append(Integer.toHexString(System.identityHashCode(this)));
      buffer.append("[resourceadapter-class=").append(raClass);
      buffer.append(" config-property=").append(configProperty);
      buffer.append(" outbound-resourceadapter=").append(outboundRa);
      buffer.append(" inbound-resourceadapter=").append(inboundRa);
      buffer.append(" adminobject=").append(adminObjects);
      buffer.append(" security-permission=").append(securityPermissions);
      buffer.append(']');
      return buffer.toString();
   }
}
