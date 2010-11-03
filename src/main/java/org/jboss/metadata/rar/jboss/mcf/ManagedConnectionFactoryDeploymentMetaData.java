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

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * A ManagedConnectionFactoryDeployment.
 * 
 * @author <a href="weston.price@jboss.org">Weston Price</a>
 * @author Jeff Zhang
 * @version $Revision$
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ManagedConnectionFactoryDeploymentMetaData
   implements Serializable, ConnectionPoolMetaData
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -4591557831734316580L;

   /** The jndiName */   
   private String jndiName;   
   
   /** The rarName */
   private String rarName;
   
   /** The useJavaContext */
   private boolean useJavaContext = true;   
   
   /** The connectionDefinition */
   protected String connectionDefinition;

   private int minSize = 0;
   
   private int maxSize = 10;
   
   private long blockingTimeoutMilliSeconds = 30000;
   
   private int idleTimeoutMinutes = 30;

   private int allocationRetry = 0;

   private long allocationRetryWaitMillis = 5000;
   
   private Boolean prefill = Boolean.FALSE;
   
   private boolean backgroundValidation;
   
   private long backgroundValidationMillis;

   private boolean validateOnMatch = true;
   
   @XmlJavaTypeAdapter(ManagedConnectionEmptyContentAdapter.class)
   private Boolean useStrictMin = Boolean.FALSE;

   @XmlJavaTypeAdapter(ManagedConnectionEmptyContentAdapter.class)
   private Boolean noTxSeparatePools = Boolean.FALSE;
   
   private String statisticsFormatter = "org.jboss.resource.statistic.pool.JBossDefaultSubPoolStatisticFormatter";
   
   private Boolean isSameRMOverrideValue = Boolean.FALSE;

   // is always true now and left here for the xml binding
   @Deprecated
   private Boolean trackConnectionByTransaction;

   private Boolean interleaving;

   /** The transactionSupportMetaData */
   @XmlTransient
   private ManagedConnectionFactoryTransactionSupportMetaData transactionSupportMetaData = ManagedConnectionFactoryTransactionSupportMetaData.NONE;

   /** The managedConnectionFactoryProperties */
   private List<ManagedConnectionFactoryPropertyMetaData> managedConnectionFactoryProperties;// = new ArrayList<ManagedConnectionFactoryPropertyMetaData>();
   
   /** The securityMetaData */

   private SecurityMetaData securityMetaData;
      
   private List<String> dependsNames;

   private DBMSMetaData dbmsMetaData;

   // todo: this should be wrapped into <metadata> element
   String typeMapping;
   
   /** The localTransactions */
   @XmlJavaTypeAdapter(ManagedConnectionEmptyContentAdapter.class)
   private Boolean localTransactions = Boolean.FALSE;

   public ManagedConnectionFactoryDeploymentMetaData()
   {
      this.interleaving = Boolean.FALSE;
   }
   
   /**
    * Get the connectionDefinition.
    * 
    * @return the connectionDefinition.
    */
   public String getConnectionDefinition()
   {
      return connectionDefinition;
   }

   /**
    * Set the connectionDefinition.
    * 
    * @param connectionDefinition The connectionDefinition to set.
    */
   public void setConnectionDefinition(String connectionDefinition)
   {
      this.connectionDefinition = connectionDefinition;
   }

   /**
    * Get the jndiName. This is the id for the DataSource ManagedObject.
    * 
    * @return the jndiName.
    */
   public String getJndiName()
   {
      return jndiName;
   }

   /**
    * Set the jndiName.
    * 
    * @param jndiName The jndiName to set.
    */
   @XmlElement(required=true)
   public void setJndiName(String jndiName)
   {
      this.jndiName = jndiName;
   }

   /**
    * Get the transactionSupportMetaData.
    * 
    * @return the transactionSupportMetaData.
    */
   public ManagedConnectionFactoryTransactionSupportMetaData getTransactionSupportMetaData()
   {
      return transactionSupportMetaData;
   }

   /**
    * Set the transactionSupportMetaData.
    * 
    * @param transactionSupportMetaData The transactionSupportMetaData to set.
    */
   public void setTransactionSupportMetaData(ManagedConnectionFactoryTransactionSupportMetaData transactionSupportMetaData)
   {
      this.transactionSupportMetaData = transactionSupportMetaData;
   }

   /**
    * Get the useJavaContext.
    * 
    * @return the useJavaContext.
    */
   public boolean isUseJavaContext()
   {
      return useJavaContext;
   }

   /**
    * Set the useJavaContext.
    * 
    * @param useJavaContext The useJavaContext to set.
    */
   public void setUseJavaContext(boolean useJavaContext)
   {
      this.useJavaContext = useJavaContext;
   }

   /**
    * Get the managedConnectionFactoryProperties.
    * 
    * @return the managedConnectionFactoryProperties.
    */
   public List<ManagedConnectionFactoryPropertyMetaData> getManagedConnectionFactoryProperties()
   {
      return managedConnectionFactoryProperties;
   }

   /**
    * Set the managedConnectionFactoryProperties.
    * 
    * @param managedConnectionFactoryProperties The managedConnectionFactoryProperties to set.
    */
   @XmlElement(name="config-property")
   public void setManagedConnectionFactoryProperties(
         List<ManagedConnectionFactoryPropertyMetaData> managedConnectionFactoryProperties)
   {
      this.managedConnectionFactoryProperties = managedConnectionFactoryProperties;
   }

   /**
    * Get the rarName.
    * 
    * @return the rarName.
    */
   public String getRarName()
   {
      return rarName;
   }

   /**
    * Set the rarName.
    * 
    * @param rarName The rarName to set.
    */
   public void setRarName(String rarName)
   {
      this.rarName = rarName;
   }

   /**
    * Get the securityMetaData.
    * 
    * @return the securityMetaData.
    */
   public SecurityMetaData getSecurityMetaData()
   {
      return securityMetaData;
   }

   /**
    * Set the securityMetaData.
    * 
    * @param securityMetaData The securityMetaData to set.
    */
   @XmlElements({
      @XmlElement(name="security-domain", type=SecurityDomainMetaData.class), 
      @XmlElement(name="security-domain-and-application",type=SecurityDomainApplicationManagedMetaData.class), 
      @XmlElement(name="application-managed-security",type=ApplicationManagedSecurityMetaData.class)})
   public void setSecurityMetaData(SecurityMetaData securityMetaData)
   {
      this.securityMetaData = securityMetaData;
   }

   
   /**
    * Get the typeMapping.
    * 
    * @return the typeMapping.
    */
   public String getTypeMapping()
   {
      return typeMapping;
   }

   /**
    * Set the typeMapping.
    * 
    * @param typeMapping The typeMapping to set.
    */
   public void setTypeMapping(String typeMapping)
   {
      this.typeMapping = typeMapping;
   }

   /**
    * Get the dependsNames.
    * 
    * @return the dependsNames.
    */
   public List<String> getDependsNames()
   {
      return dependsNames;
   }

   /**
    * Set the dependsNames.
    * 
    * @param dependsNames The dependsNames to set.
    */
   @XmlElement(name="depends")
   public void setDependsNames(List<String> dependsNames)
   {
      this.dependsNames = dependsNames;
   }

   @XmlElement(name="min-pool-size")
   public void setMinSize(int minSize)
   {
      this.minSize = minSize;
   } 
   
   public int getMinSize()
   {
      return this.minSize;
      
   }
   @XmlElement(name="max-pool-size")
   public void setMaxSize(int maxSize)
   {
      this.maxSize = maxSize;
   }
   
   public int getMaxSize()
   {
      if (this.maxSize >= this.minSize)
      {
         return this.maxSize;
      } else {
         return this.minSize;
      }
      
   }
   
   @XmlElement(name="blocking-timeout-millis")
   public void setBlockingTimeoutMilliSeconds(long blockTimeout)
   {
     this.blockingTimeoutMilliSeconds = blockTimeout;
      
   }
   
   public long getBlockingTimeoutMilliSeconds()
   {
      return this.blockingTimeoutMilliSeconds;
      
   }
   
   public void setIdleTimeoutMinutes(int idleTimeout)
   {
      this.idleTimeoutMinutes = idleTimeout;
   }
   
   public int getIdleTimeoutMinutes()
   {
      return this.idleTimeoutMinutes;
      
   }

   public void setAllocationRetry(int allocationRetry)
   {
      this.allocationRetry = allocationRetry;
   }

   public int getAllocationRetry()
   {
      return allocationRetry;
   }

   public void setAllocationRetryWaitMillis(long allocationRetryWaitMillis)
   {
      this.allocationRetryWaitMillis = allocationRetryWaitMillis;
   }

   public long getAllocationRetryWaitMillis()
   {
      return allocationRetryWaitMillis;
   }

   public void setPrefill(Boolean prefill)
   {
      this.prefill = prefill;
   }

   public Boolean getPrefill()
   {
      return this.prefill;      
   }

   public boolean isBackgroundValidation()
   {
      return backgroundValidation;
   }

   public void setBackgroundValidation(boolean backgroundValidation)
   {
      this.backgroundValidation = backgroundValidation;
   }

   public void setBackgroundValidationMillis(long interval)
   {
      this.backgroundValidationMillis = interval;
   }

   public long getBackgroundValidationMillis()
   {
      return this.backgroundValidationMillis;
   }

   public void setValidateOnMatch(boolean validateOnMatch)
   {
      this.validateOnMatch = validateOnMatch;
   }

   public boolean isValidateOnMatch()
   {
      return this.validateOnMatch;
      
   }

   public Boolean getIsSameRMOverrideValue()
   {
      return isSameRMOverrideValue;
   }

   @XmlElement(name="isSameRM-override-value")
   public void setIsSameRMOverrideValue(Boolean isSameRMOverrideValue)
   {
      this.isSameRMOverrideValue = isSameRMOverrideValue;
   }

   @Deprecated
   public Boolean getTrackConnectionByTransaction()
   {
      return !isInterleaving();
   }

   @Deprecated
   @XmlJavaTypeAdapter(ManagedConnectionEmptyContentAdapter.class)
   @XmlElement(name="track-connection-by-tx")
   public void setTrackConnectionByTransaction(Boolean trackConnectionByTransaction)
   {
      if(Boolean.TRUE == getLocalTransactions() && !Boolean.TRUE.equals(trackConnectionByTransaction))
         throw new IllegalStateException("In case of local transactions track-connection-by-tx must always be true");      
      setInterleaving(!Boolean.TRUE.equals(trackConnectionByTransaction));
   }

   public Boolean isInterleaving()
   {
      return interleaving == Boolean.TRUE && !Boolean.TRUE.equals(getLocalTransactions());
   }

   @XmlJavaTypeAdapter(ManagedConnectionEmptyContentAdapter.class)
   @XmlElement(name="interleaving")
   public void setInterleaving(Boolean interleaving)
   {
      this.interleaving = interleaving;
   }

   public Boolean getLocalTransactions()
   {
      return localTransactions;
   }

   public void setLocalTransactions(Boolean localTransactions)
   {
      this.localTransactions = localTransactions;
   }

   public Boolean getUseStrictMin()
   {
      return useStrictMin;
   }

   public void setUseStrictMin(Boolean useStrictMin)
   {
      this.useStrictMin = useStrictMin;
   }

   public String getStatisticsFormatter()
   {
      return statisticsFormatter;
   }

   public void setStatisticsFormatter(String statisticsFormatter)
   {
      this.statisticsFormatter = statisticsFormatter;
   }
   
   public Boolean getNoTxSeparatePools()
   {
      return this.noTxSeparatePools;
   }

   public void setNoTxSeparatePools(Boolean notxpool)
   {
      this.noTxSeparatePools = notxpool;
   }

   public DBMSMetaData getDbmsMetaData()
   {
      return dbmsMetaData;
   }

   @XmlElement(name="metadata")
   public void setDbmsMetaData(DBMSMetaData dbmsMetaData)
   {
      this.dbmsMetaData = dbmsMetaData;
   }
   

}
