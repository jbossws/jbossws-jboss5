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
import javax.xml.bind.annotation.XmlType;

/**
 * A XADataSourceDeploymentMetaData.
 * 
 * @author <a href="weston.price@jboss.org">Weston Price</a>
 * @author Jeff Zhang
 * @version $Revision$
 */
@XmlType(name="xa-datasource", propOrder={"jndiName", "useJavaContext", "trackConnectionByTransaction", "interleaving", "xaDataSourceClass",
      "xaDataSourceProperties", "urlProperty", "urlDelimiter", "urlSelectorStrategyClassName", "isSameRMOverrideValue",
      "transactionIsolation", "userName", "passWord", "securityMetaData", "minSize", "maxSize", "blockingTimeoutMilliSeconds",
      "backgroundValidation", "backgroundValidationMillis", "idleTimeoutMinutes", "allocationRetry", "allocationRetryWaitMillis",
      "validateOnMatch", "noTxSeparatePools", "xaResourceTimeout", "newConnectionSQL", "checkValidConnectionSQL",
      "validConnectionCheckerClassName", "exceptionSorterClassName", "staleConnectionCheckerClassName", "trackStatements",
      "prefill", "preparedStatementCacheSize", "sharePreparedStatements", "useQueryTimeout", "queryTimeout", "useTryLock",
      "dbmsMetaData", "typeMapping", "dependsNames"})
@XmlAccessorType(XmlAccessType.FIELD)
public class XADataSourceDeploymentMetaData extends DataSourceDeploymentMetaData
{

   /** The serialVersionUID */
   private static final long serialVersionUID = -6919645811610960978L;
   
   private static final String RAR_NAME = "jboss-xa-jdbc.rar";
   
   private String xaDataSourceClass;
   
   private List<XAConnectionPropertyMetaData> xaDataSourceProperties = new ArrayList<XAConnectionPropertyMetaData>();
   
   private String urlProperty;

   private int xaResourceTimeout;

   public XADataSourceDeploymentMetaData()
   {
      setRarName(RAR_NAME);
      setTransactionSupportMetaData(ManagedConnectionFactoryTransactionSupportMetaData.XA);
   }
   public String getXaDataSourceClass()
   {
      return xaDataSourceClass;
   }

   @XmlElement(name="xa-datasource-class")
   public void setXaDataSourceClass(String xaDataSourceClass)
   {
      this.xaDataSourceClass = xaDataSourceClass;
   }
   
   public String getUrlProperty()
   {
      return urlProperty;
   }

   public void setUrlProperty(String urlProperty)
   {
      this.urlProperty = urlProperty;
   }
   public int getXaResourceTimeout()
   {
      return xaResourceTimeout;
   }

   public void setXaResourceTimeout(int xaResourceTimeout)
   {
      this.xaResourceTimeout = xaResourceTimeout;
   }
   public List<XAConnectionPropertyMetaData> getXaDataSourceProperties()
   {
      return this.xaDataSourceProperties;      
   }
   
   @XmlElement(name="xa-datasource-property")
   public void setXaDataSourceProperties(List<XAConnectionPropertyMetaData> xaDataSourceProperties)
   {
      this.xaDataSourceProperties = xaDataSourceProperties;
   }
   
   public List<ManagedConnectionFactoryPropertyMetaData> getManagedConnectionFactoryProperties()
   {
      List<ManagedConnectionFactoryPropertyMetaData> properties = super.getManagedConnectionFactoryProperties();
      ManagedConnectionFactoryPropertyMetaData property = null;
      
      if(getXaDataSourceClass() != null)
      {
         property = new ManagedConnectionFactoryPropertyMetaData();
         property.setName("XADataSourceClass");
         property.setValue(getXaDataSourceClass());
         properties.add(property);
      }
      
      List<XAConnectionPropertyMetaData> dsProps = getXaDataSourceProperties();
      
      StringBuffer dsBuff = new StringBuffer();

      if (dsProps != null)
      {
         for (XAConnectionPropertyMetaData data : dsProps)
         {
            dsBuff.append(data.getName() + "=" + data.getValue() + "\n");
         }
      }
      
      property = new ManagedConnectionFactoryPropertyMetaData();
      property.setName("XADataSourceProperties");
      property.setValue(dsBuff.toString());
      properties.add(property);
      
      if(getUrlProperty() != null)
      {
         property = new ManagedConnectionFactoryPropertyMetaData();
         property.setName("URLProperty");
         property.setValue(getUrlProperty());
         properties.add(property);
      }
      
      return properties;
   }
}
