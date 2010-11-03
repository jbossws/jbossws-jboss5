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

import javax.xml.bind.annotation.XmlElement;

/**
 * A DataSourceDeploymentMetaData.
 * 
 * @author <a href="weston.price@jboss.org">Weston Price</a>
 * @author Jeff Zhang
 * @version $Revision$
 */
public class DataSourceDeploymentMetaData extends ManagedConnectionFactoryDeploymentMetaData implements JDBCProviderSupport
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 1440129014410915366L;
   
   private static final String CONNECTION_DEFINITION = "javax.sql.DataSource";
   
   private static final String RAR_NAME = "jboss-local-jdbc.rar";

   private String transactionIsolation;
   
   private String userName;
   
   private String passWord;
   
   private String newConnectionSQL;
   
   private String checkValidConnectionSQL;
   
   private String validConnectionCheckerClassName;
   
   private String exceptionSorterClassName;
   
   private String staleConnectionCheckerClassName;
   
   private String trackStatements;
   
   private int preparedStatementCacheSize = 0;
   
   private boolean sharePreparedStatements;
   
   private boolean useQueryTimeout;
   
   private int queryTimeout;
   
   private long useTryLock;
   
   private String urlDelimiter;
   
   private String urlSelectorStrategyClassName;
   
   public DataSourceDeploymentMetaData()
   {
      setConnectionDefinition(CONNECTION_DEFINITION);
      setRarName(RAR_NAME);
      
   }

   public String getTransactionIsolation()
   {
      return transactionIsolation;
   }

   public void setTransactionIsolation(String transactionIsolation)
   {
      this.transactionIsolation = transactionIsolation;
   }

   public String getPassWord()
   {
      return passWord;
   }
   
   @XmlElement(name="password")
   public void setPassWord(String passWord)
   {
      this.passWord = passWord;
   }

   public String getUserName()
   {
      return userName;
   }

   public void setUserName(String userName)
   {
      this.userName = userName;
   }
   
      
   public String getCheckValidConnectionSQL()
   {
      return checkValidConnectionSQL;
   }

   @XmlElement(name="check-valid-connection-sql")
   public void setCheckValidConnectionSQL(String checkValidConnectionSQL)
   {
      this.checkValidConnectionSQL = checkValidConnectionSQL;
   }

   public String getExceptionSorterClassName()
   {
      return exceptionSorterClassName;
   }

   public void setExceptionSorterClassName(String exceptionSorterClassName)
   {
      this.exceptionSorterClassName = exceptionSorterClassName;
   }

   public String getNewConnectionSQL()
   {
      return newConnectionSQL;
   }

   @XmlElement(name="new-connection-sql")
   public void setNewConnectionSQL(String newConnectionSQL)
   {
      this.newConnectionSQL = newConnectionSQL;
   }

   public String getValidConnectionCheckerClassName()
   {
      return validConnectionCheckerClassName;
   }

   public void setValidConnectionCheckerClassName(String validConnectionCheckerClassName)
   {
      this.validConnectionCheckerClassName = validConnectionCheckerClassName;
   }

   public String getStaleConnectionCheckerClassName()
   {
      return staleConnectionCheckerClassName;
   }

   public void setStaleConnectionCheckerClassName(String staleConnectionCheckerClassName)
   {
      this.staleConnectionCheckerClassName = staleConnectionCheckerClassName;
   }

   public String getUrlDelimiter()
   {
      return urlDelimiter;
   }

   public void setUrlDelimiter(String urlDelimiter)
   {
      this.urlDelimiter = urlDelimiter;
   }

   public String getUrlSelectorStrategyClassName()
   {
      return urlSelectorStrategyClassName;
   }

   public void setUrlSelectorStrategyClassName(String urlSelectorStrategyClassName)
   {
      this.urlSelectorStrategyClassName = urlSelectorStrategyClassName;
   }
      
   public int getPreparedStatementCacheSize()
   {
      return preparedStatementCacheSize;
   }

   public void setPreparedStatementCacheSize(int preparedStatementCacheSize)
   {
      this.preparedStatementCacheSize = preparedStatementCacheSize;
   }

   public int getQueryTimeout()
   {
      return queryTimeout;
   }

   public void setQueryTimeout(int queryTimeout)
   {
      this.queryTimeout = queryTimeout;
   }

   public long getUseTryLock()
   {
      return useTryLock;
   }

   public void setUseTryLock(long useTryLock)
   {
      this.useTryLock = useTryLock;
   }

   public boolean isSharePreparedStatements()
   {
      return sharePreparedStatements;
   }

   public void setSharePreparedStatements(boolean sharePreparedStatements)
   {
      this.sharePreparedStatements = sharePreparedStatements;
   }

   public String getTrackStatements()
   {
      return trackStatements;
   }

   public void setTrackStatements(String trackStatements)
   {
      this.trackStatements = trackStatements;
   }

   public boolean isUseQueryTimeout()
   {
      return useQueryTimeout;
   }

   @XmlElement(name="set-tx-query-timeout")
   public void setUseQueryTimeout(boolean useQueryTimeout)
   {
      this.useQueryTimeout = useQueryTimeout;
   }

   @Override
   public List<ManagedConnectionFactoryPropertyMetaData> getManagedConnectionFactoryProperties()
   {
      List<ManagedConnectionFactoryPropertyMetaData> properties = new ArrayList<ManagedConnectionFactoryPropertyMetaData>();
      ManagedConnectionFactoryPropertyMetaData property = null;
            
      if(getUserName() != null)
      {
         property = new ManagedConnectionFactoryPropertyMetaData();
         property.setName("UserName");
         property.setValue(getUserName());
         properties.add(property);
      }
      
      if(getPassWord() != null)
      {
         property = new ManagedConnectionFactoryPropertyMetaData();
         property.setName("Password");
         property.setValue(getPassWord());
         properties.add(property);
         
      }
      
      if(getTransactionIsolation() != null)
      {
         property = new ManagedConnectionFactoryPropertyMetaData();
         property.setName("TransactionIsolation");
         property.setValue(getTransactionIsolation());
         properties.add(property);
         
      }
      
      if(getNewConnectionSQL() != null)
      {
         property = new ManagedConnectionFactoryPropertyMetaData();
         property.setName("NewConnectionSQL");
         property.setValue(getNewConnectionSQL());         
         properties.add(property);

      }
      
      if(getCheckValidConnectionSQL() != null)
      {
         property = new ManagedConnectionFactoryPropertyMetaData();
         property.setName("CheckValidConnectionSQL");
         property.setValue(getCheckValidConnectionSQL());                  
         properties.add(property);

      }
      
      if(getValidConnectionCheckerClassName() != null)
      {
         property = new ManagedConnectionFactoryPropertyMetaData();
         property.setName("ValidConnectionCheckerClassName");
         property.setValue(getValidConnectionCheckerClassName());                           
         properties.add(property);

      }
      
      if(getExceptionSorterClassName() != null)
      {
         property = new ManagedConnectionFactoryPropertyMetaData();
         property.setName("ExceptionSorterClassName");
         property.setValue(getExceptionSorterClassName());                           
         properties.add(property);         
      }
      
      if(getStaleConnectionCheckerClassName() != null)
      {
         property = new ManagedConnectionFactoryPropertyMetaData();
         property.setName("StaleConnectionCheckerClassName");
         property.setValue(getStaleConnectionCheckerClassName());                           
         properties.add(property);         
      }
      
      if(getUrlSelectorStrategyClassName() != null)
      {
         property = new ManagedConnectionFactoryPropertyMetaData();
         property.setName("URLSelectorStrategyClassName");
         property.setValue(getUrlSelectorStrategyClassName());                           
         properties.add(property);         
      }
      
      if(getUrlDelimiter() != null)
      {
         property = new ManagedConnectionFactoryPropertyMetaData();
         property.setName("URLDelimiter");
         property.setValue(getUrlDelimiter());                           
         properties.add(property);         
      }
      
      property = new ManagedConnectionFactoryPropertyMetaData();
      property.setName("PreparedStatementCacheSize");
      property.setType("int");
      property.setValue(String.valueOf(getPreparedStatementCacheSize()));
      properties.add(property);
      
      property = new ManagedConnectionFactoryPropertyMetaData();
      property.setName("SharePreparedStatements");
      property.setType("boolean");
      property.setValue(String.valueOf(isSharePreparedStatements()));
      properties.add(property);
      
      property = new ManagedConnectionFactoryPropertyMetaData();
      property.setName("QueryTimeout");
      property.setType("int");
      property.setValue(String.valueOf(getQueryTimeout()));
      properties.add(property);
      
      property = new ManagedConnectionFactoryPropertyMetaData();
      property.setName("UseTryLock");
      property.setType("long");
      property.setValue(String.valueOf(getUseTryLock()));
      properties.add(property);
      
      property = new ManagedConnectionFactoryPropertyMetaData();
      property.setName("TransactionQueryTimeout");
      property.setType("boolean");
      property.setValue(String.valueOf(isUseQueryTimeout()));
      properties.add(property);
      
      property = new ManagedConnectionFactoryPropertyMetaData();
      property.setName("ValidateOnMatch");
      property.setType("boolean");
      property.setValue(String.valueOf(isValidateOnMatch()));
      properties.add(property);
      
      if (getTrackStatements() != null)
      {
         property = new ManagedConnectionFactoryPropertyMetaData();
         property.setName("TrackStatements");
         property.setType("java.lang.String");
         property.setValue(String.valueOf(getTrackStatements()));
         properties.add(property);
      }
      
      return properties;
   }
}
