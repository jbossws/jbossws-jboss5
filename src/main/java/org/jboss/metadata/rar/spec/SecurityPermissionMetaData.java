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

/**
 * Security Permission meta data
 *
 * @author <a href="mailto:adrian@jboss.com">Adrian Brock</a>
 * @author Jeff Zhang
 * @version $Revision$
 */
@XmlType(name="security-permissionType", propOrder={"descriptions", "securityPermissionSpec"})
public class SecurityPermissionMetaData extends IdMetaDataImplWithDescriptions
{
   private static final long serialVersionUID = -2819460637074430187L;

   /** The security permission spec */
   private String securityPermissionSpec;

   /**
    * Get the security permission spec
    * 
    * @return the security permission spec
    */
   public String getSecurityPermissionSpec()
   {
      return securityPermissionSpec;
   }

   /**
    * Set the security permission spec
    * 
    * @param securityPermissionSpec the spec
    */
   @XmlElement(required=true)
   public void setSecurityPermissionSpec(String securityPermissionSpec)
   {
      this.securityPermissionSpec = securityPermissionSpec;
   }
   
   public String toString()
   {
      StringBuffer buffer = new StringBuffer();
      buffer.append("SecurityPermissionMetaData").append('@');
      buffer.append(Integer.toHexString(System.identityHashCode(this)));
      buffer.append("[spec=").append(securityPermissionSpec);
      buffer.append(']');
      return buffer.toString();
   }
}
