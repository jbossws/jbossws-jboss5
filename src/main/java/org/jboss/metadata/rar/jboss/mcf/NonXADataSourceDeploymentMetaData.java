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
package org.jboss.metadata.rar.jboss.mcf;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Provide a common base for non xa data source deployment meta data.
 * 
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @author Scott.Stark@jboss.org
 * @author Jeff Zhang
 * @version $Revision$
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class NonXADataSourceDeploymentMetaData extends DataSourceDeploymentMetaData
{
   private static final long serialVersionUID = 1444129014410015366L;
   
   private String driverClass;
   
   private String connectionUrl;
   
   private List<DataSourceConnectionPropertyMetaData> connectionProperties = new ArrayList<DataSourceConnectionPropertyMetaData>();

   public String getConnectionUrl()
   {
      return connectionUrl;
   }

   @XmlElement(required=true)
   public void setConnectionUrl(String connectionUrl)
   {
      this.connectionUrl = connectionUrl;
   }

   public String getDriverClass()
   {
      return driverClass;
   }

   @XmlElement(required=true)
   public void setDriverClass(String driverClass)
   {
      this.driverClass = driverClass;
   }
   
   public List<DataSourceConnectionPropertyMetaData> getConnectionProperties()
   {
      return this.connectionProperties;      
   }
   
   @XmlElement(name="connection-property")
   public void setConnectionProperties(List<DataSourceConnectionPropertyMetaData> connectionProperties)
   {
      this.connectionProperties = connectionProperties;
   }
   
   @Override
   public List<ManagedConnectionFactoryPropertyMetaData> getManagedConnectionFactoryProperties()
   {
      
      List<ManagedConnectionFactoryPropertyMetaData> properties = super.getManagedConnectionFactoryProperties();
      ManagedConnectionFactoryPropertyMetaData property = null;
      
      if(getConnectionUrl() != null)
      {
         property = new ManagedConnectionFactoryPropertyMetaData();
         property.setName("ConnectionURL");
         property.setValue(getConnectionUrl());
         properties.add(property);
         
      }
      
      if(getDriverClass() != null)
      {
         property = new ManagedConnectionFactoryPropertyMetaData();
         property.setName("DriverClass");
         property.setValue(getDriverClass());
         properties.add(property);         
      }
      
      StringBuffer propBuff = new StringBuffer();
      List<DataSourceConnectionPropertyMetaData> dsProps = getConnectionProperties();

      if (dsProps != null)
      {
         for (DataSourceConnectionPropertyMetaData prop : dsProps)
         {
            propBuff.append(prop.getName() + "=" + prop.getValue() + "\n");      
         }
      }
      
      property = new ManagedConnectionFactoryPropertyMetaData();
      property.setName("ConnectionProperties");
      property.setValue(propBuff.toString());
      properties.add(property);

      return properties;
      
   }
}
