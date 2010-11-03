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
 * Admin object meta data
 *
 * @author <a href="mailto:adrian@jboss.com">Adrian Brock</a>
 * @author Jeff Zhang
 * @version $Revision$
 */
@XmlType(name="adminobjectType", 
		propOrder={"adminObjectInterfaceClass", "adminObjectImplementationClass", "configProps"})
public class AdminObjectMetaData extends IdMetaDataImpl
{
   private static final long serialVersionUID = 5647786972921112792L;

   /** The admin object interface class */
   private String adminObjectInterfaceClass;

   /** The admin object implementation class */
   private String adminObjectImplementationClass;

   private List<ConfigPropertyMetaData> configProps;
   /**
    * Get the admin object interface class
    * 
    * @return the admin object interface class
    */
   public String getAdminObjectInterfaceClass()
   {
      return adminObjectInterfaceClass;
   }

   /**
    * Set the admin object interface class
    * 
    * @param adminObjectInterfaceClass the class name
    */
   @XmlElement(name="adminobject-interface", required=true)
   public void setAdminObjectInterfaceClass(String adminObjectInterfaceClass)
   {
      this.adminObjectInterfaceClass = adminObjectInterfaceClass;
   }

   /**
    * Get the admin object implementation class
    * 
    * @return the admin object implementation class
    */
   public String getAdminObjectImplementationClass()
   {
      return adminObjectImplementationClass;
   }

   /**
    * Set the admin object implementation class
    * 
    * @param adminObjectImplementationClass the class name
    */
   @XmlElement(name="adminobject-class", required=true)
   public void setAdminObjectImplementationClass(String adminObjectImplementationClass)
   {
      this.adminObjectImplementationClass = adminObjectImplementationClass;
   }

   @XmlElement(name="config-property")
   public void setConfigProps(List<ConfigPropertyMetaData> configProps) {
      this.configProps = configProps;
   }

   public List<ConfigPropertyMetaData> getConfigProps() {
      return configProps;
   }  
   
   public String toString()
   {
      StringBuffer buffer = new StringBuffer();
      buffer.append("AdminObjectMetaData").append('@');
      buffer.append(Integer.toHexString(System.identityHashCode(this)));
      buffer.append("[adminObjectInterfaceClass=").append(adminObjectInterfaceClass);
      buffer.append(" adminObjectImplementationClass=").append(adminObjectImplementationClass);
      buffer.append(" properties=").append(configProps);
      buffer.append(']');
      return buffer.toString();
   }

}
