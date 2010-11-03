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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;
import org.jboss.xb.binding.JBossXBRuntimeException;

/**
 * Authentication mechanism meta data
 *
 * @author <a href="mailto:adrian@jboss.com">Adrian Brock</a>
 * @author Jeff Zhang
 * @version $Revision$
 */
@XmlType(name="authentication-mechanismType", 
		propOrder={"descriptions", "authenticationMechanismType", "credentialInterfaceClass"})
public class AuthenticationMechanismMetaData extends IdMetaDataImplWithDescriptions
{
   static final long serialVersionUID = 1562443409483033688L;

   /** The authentication mechanism type */
   private String authenticationMechanismType;

   /** The credential interface class */
   private String credentialInterfaceClass;

   /**
    * Get the authentication mechanism type
    * 
    * @return the authentication mechanism type
    */
   public String getAuthenticationMechanismType()
   {
      return authenticationMechanismType;
   }

   /**
    * Set the authentication mechanism type
    * 
    * @param authenticationMechanismType the type
    */
   @XmlElement(name="authentication-mechanism-type", required=true)
   public void setAuthenticationMechanismType(String authenticationMechanismType)
   {
      if (authenticationMechanismType != null)
      {
         if (authenticationMechanismType.equals("BasicPassword") ||
             authenticationMechanismType.equals("Kerbv5"))
             this.authenticationMechanismType = authenticationMechanismType;
         else
            throw new JBossXBRuntimeException("authentication-mechanism-type contains an invalid value");
      }
   }

   /**
    * Get the credential interface class
    * 
    * @return the credential interface class
    */
   public String getCredentialInterfaceClass()
   {
      return credentialInterfaceClass;
   }

   /**
    * Set the credential interface class
    * 
    * @param credentialInterfaceClass the class
    */
   @XmlElement(name="credential-interface", required=true)
   public void setCredentialInterfaceClass(String credentialInterfaceClass)
   {
      if (credentialInterfaceClass != null)
      {
         if (credentialInterfaceClass.equals("javax.resource.spi.security.PasswordCredential") ||
               credentialInterfaceClass.equals("org.ietf.jgss.GSSCredential") ||
               credentialInterfaceClass.equals("javax.resource.spi.security.GenericCredential"))
             this.credentialInterfaceClass = credentialInterfaceClass;
         else
            throw new JBossXBRuntimeException("credential-interface is set wrong property");
      }
   }
   
   public String toString()
   {
      StringBuffer buffer = new StringBuffer();
      buffer.append("AuthenticationMechanismMetaData").append('@');
      buffer.append(Integer.toHexString(System.identityHashCode(this)));
      buffer.append("[authenticationMechanismType=").append(authenticationMechanismType);
      buffer.append(" credentialInterfaceClass=").append(credentialInterfaceClass);
      buffer.append(']');
      return buffer.toString();
   }
}
