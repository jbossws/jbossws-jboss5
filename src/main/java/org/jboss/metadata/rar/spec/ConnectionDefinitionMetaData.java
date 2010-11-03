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
 * Connection Definition meta data
 *
 * @author <a href="mailto:adrian@jboss.com">Adrian Brock</a>
 * @author Jeff Zhang
 * @version $Revision: $
 */
@XmlType(name="connection-definitionType", 
		propOrder={"managedConnectionFactoryClass", "configProps", "connectionFactoryInterfaceClass",
		           "connectionFactoryImplementationClass", "connectionInterfaceClass", "connectionImplementationClass"})
public class ConnectionDefinitionMetaData  extends IdMetaDataImpl
{
   private static final long serialVersionUID = -138227135002730221L;
   
   /** The managed connection factory class */
   private String managedConnectionFactoryClass;

   /** The connector metadata */
   private List<ConfigPropertyMetaData> configProps;

   /** The connection factory interface class */
   private String connectionFactoryInterfaceClass;

   /** The connection factory implementation class */
   private String connectionFactoryImplementationClass;

   /** The connection interface class */
   private String connectionInterfaceClass;

   /** The connection implementation class */
   private String connectionImplementationClass;
   


   /**
    * Get the managed connection factory class
    * 
    * @return the managed connection factory class
    */
   public String getManagedConnectionFactoryClass()
   {
      return managedConnectionFactoryClass;
   }

   /**
    * Set the managed connection factory class
    * 
    * @param managedConnectionFactoryClass the class name
    */
   @XmlElement(name="managedconnectionfactory-class", required=true)
   public void setManagedConnectionFactoryClass(String managedConnectionFactoryClass)
   {
      this.managedConnectionFactoryClass = managedConnectionFactoryClass;
   }
   
   @XmlElement(name="config-property")
   public void setConfigProps(List<ConfigPropertyMetaData> configProps) {
      this.configProps = configProps;
   }

   public List<ConfigPropertyMetaData> getConfigProps() {
      return configProps;
   }
   /**
    * Get the connection factory interface class
    * 
    * @return the connection factory interface class
    */
   public String getConnectionFactoryInterfaceClass()
   {
      return connectionFactoryInterfaceClass;
   }

   /**
    * Set the connection factory interface class
    * 
    * @param connectionFactoryInterfaceClass the class name
    */
   @XmlElement(name="connectionfactory-interface", required=true)
   public void setConnectionFactoryInterfaceClass(String connectionFactoryInterfaceClass)
   {
      this.connectionFactoryInterfaceClass = connectionFactoryInterfaceClass;
   }

   /**
    * Get the connection factory implementation class
    * 
    * @return the connection factory implementation class
    */
   public String getConnectionFactoryImplementationClass()
   {
      return connectionFactoryImplementationClass;
   }

   /**
    * Set the connection factory implementation class
    * 
    * @param connectionFactoryImplementationClass the class name
    */
   @XmlElement(name="connectionfactory-impl-class", required=true)
   public void setConnectionFactoryImplementationClass(String connectionFactoryImplementationClass)
   {
      this.connectionFactoryImplementationClass = connectionFactoryImplementationClass;
   }

   /**
    * Get the connection interface class
    * 
    * @return the connection interface class
    */
   public String getConnectionInterfaceClass()
   {
      return connectionInterfaceClass;
   }

   /**
    * Set the connection interface class
    * 
    * @param connectionInterfaceClass the class name
    */
   @XmlElement(name="connection-interface", required=true)
   public void setConnectionInterfaceClass(String connectionInterfaceClass)
   {
      this.connectionInterfaceClass = connectionInterfaceClass;
   }

   /**
    * Get the connection implementation class
    * 
    * @return the connection implementation class
    */
   public String getConnectionImplementationClass()
   {
      return connectionImplementationClass;
   }

   /**
    * Set the connection implementation class
    * 
    * @param connectionImplementationClass the class name
    */
   @XmlElement(name="connection-impl-class", required=true)
   public void setConnectionImplementationClass(String connectionImplementationClass)
   {
      this.connectionImplementationClass = connectionImplementationClass;
   }
   
   public String toString()
   {
      StringBuffer buffer = new StringBuffer();
      buffer.append("ConnectionDefinitionMetaData").append('@');
      buffer.append(Integer.toHexString(System.identityHashCode(this)));
      buffer.append("[managedConnectionFactoryClass=").append(managedConnectionFactoryClass);
      buffer.append(" connectionFactoryInterfaceClass=").append(connectionFactoryInterfaceClass);
      buffer.append(" connectionFactoryImplementationClass=").append(connectionFactoryImplementationClass);
      buffer.append(" connectionInterfaceClass=").append(connectionInterfaceClass);
      buffer.append(" connectionImplementationClass=").append(connectionImplementationClass);
      buffer.append(" properties=").append(configProps);
      buffer.append(']');
      return buffer.toString();
   }
}
