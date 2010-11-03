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
 * outbound ResourceAdapter meta data
 *
 * @author Jeff Zhang
 * @version $Revision: $
 */
@XmlType(name="outbound-resourceadapterType", propOrder={"conDefs", "transSupport", "authMechanisms", "reAuthSupport"})
public class OutboundRaMetaData extends IdMetaDataImpl
{
   private static final long serialVersionUID = -1583292998339497984L;

   private List<ConnectionDefinitionMetaData> conDefs;
   private TransactionSupportMetaData transSupport;
   private List<AuthenticationMechanismMetaData> authMechanisms;
   private boolean reAuthSupport;

   @XmlElement(name="connection-definition", required=true)
   public void setConDefs(List<ConnectionDefinitionMetaData> conDefs) {
      this.conDefs = conDefs;
   }

   public List<ConnectionDefinitionMetaData> getConDefs() {
      return conDefs;
   }

   @XmlElement(name="transaction-support", required=true)
   public void setTransSupport(TransactionSupportMetaData transSupport) {
      this.transSupport = transSupport;
   }

   public TransactionSupportMetaData getTransSupport() {
      return transSupport;
   }

   @XmlElement(name="authentication-mechanism")
   public void setAuthMechanisms(List<AuthenticationMechanismMetaData> authMechanisms) {
      this.authMechanisms = authMechanisms;
   }

   public List<AuthenticationMechanismMetaData> getAuthMechanisms() {
      return authMechanisms;
   }

   @XmlElement(name="reauthentication-support", required=true)
   public void setReAuthSupport(boolean reAuthSupport) {
      this.reAuthSupport = reAuthSupport;
   }

   public boolean isReAuthSupport() {
      return reAuthSupport;
   }
}
