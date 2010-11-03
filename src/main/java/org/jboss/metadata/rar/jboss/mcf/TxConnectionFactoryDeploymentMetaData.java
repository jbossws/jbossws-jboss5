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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * A TxConnectionFactoryDeploymentMetaData.
 * 
 * @author <a href="weston.price@jboss.org">Weston Price</a>
 * @author Jeff Zhang
 * @version $Revision$
 */
@XmlType(name="tx-connection-factory", propOrder={"jndiName", "localTransactions", "xaTransaction", "trackConnectionByTransaction",
      "rarName", "connectionDefinition", "managedConnectionFactoryProperties", "securityMetaData", "minSize", "maxSize",
      "blockingTimeoutMilliSeconds", "backgroundValidation", "backgroundValidationMillis", "idleTimeoutMinutes", "allocationRetry", "allocationRetryWaitMillis",
      "noTxSeparatePools", "prefill", "xaResourceTimeout", "dbmsMetaData", "typeMapping", "dependsNames"})
@XmlAccessorType(XmlAccessType.FIELD)
public class TxConnectionFactoryDeploymentMetaData extends ManagedConnectionFactoryDeploymentMetaData
{

   /** The serialVersionUID */
   private static final long serialVersionUID = -8491548124974331799L;

   public TxConnectionFactoryDeploymentMetaData()
   {
   }
   
   private int xaResourceTimeout;
   

   private Boolean xaTransaction = Boolean.FALSE;
   
   public int getXaResourceTimeout()
   {
      return xaResourceTimeout;
   }
   
   public Boolean getXaTransaction()
   {
      return xaTransaction;
   }

   @XmlJavaTypeAdapter(ManagedConnectionEmptyContentAdapter.class)
   @XmlElement(name="xa-transaction")
   public void setXaTransaction(Boolean xaTransaction)
   {
      this.xaTransaction = xaTransaction;
      
   }
   
   @XmlElement(name="xa-resource-timeout")
   public void setXaResourceTimeout(int xaResourceTimeout)
   {
      this.xaResourceTimeout = xaResourceTimeout;
   }
   
   @Override
   public Boolean getLocalTransactions()
   {
      return !getXaTransaction();
   }

   @Override
   public ManagedConnectionFactoryTransactionSupportMetaData getTransactionSupportMetaData()
   {
      return getLocalTransactions() ? ManagedConnectionFactoryTransactionSupportMetaData.LOCAL : ManagedConnectionFactoryTransactionSupportMetaData.XA;
   }

}
