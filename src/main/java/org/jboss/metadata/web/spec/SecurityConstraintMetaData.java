/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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
package org.jboss.metadata.web.spec;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * The web app security-constraints
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
@XmlType(name="security-constraintType", propOrder={"displayName", "resourceCollections", "authConstraint", "userDataConstraint"})
public class SecurityConstraintMetaData extends IdMetaDataImpl
{
   private static final long serialVersionUID = 1;

   private String displayName;
   private WebResourceCollectionsMetaData resourceCollections;
   private AuthConstraintMetaData authConstraint;
   private UserDataConstraintMetaData userDataConstraint;

   public AuthConstraintMetaData getAuthConstraint()
   {
      return authConstraint;
   }
   public void setAuthConstraint(AuthConstraintMetaData authConstraint)
   {
      this.authConstraint = authConstraint;
   }

   public String getDisplayName()
   {
      return displayName;
   }
   public void setDisplayName(String displayName)
   {
      this.displayName = displayName;
   }

   public WebResourceCollectionsMetaData getResourceCollections()
   {
      return resourceCollections;
   }
   @XmlElement(name="web-resource-collection")
   public void setResourceCollections(
         WebResourceCollectionsMetaData resourceCollections)
   {
      this.resourceCollections = resourceCollections;
   }

   public UserDataConstraintMetaData getUserDataConstraint()
   {
      return userDataConstraint;
   }
   public void setUserDataConstraint(UserDataConstraintMetaData userDataConstraint)
   {
      this.userDataConstraint = userDataConstraint;
   }

   /** The unchecked flag is set when there is no security-constraint/auth-constraint
   @return true if there is no auth-constraint
    */
   @XmlTransient
   public boolean isUnchecked()
   {
      return authConstraint == null;
   }

   /** The excluded flag is set when there is an empty
   security-constraint/auth-constraint element
   @return true if there is an empty auth-constraint
   */
   @XmlTransient
   public boolean isExcluded()
   {
      boolean isExcluded = authConstraint != null
      && authConstraint.getRoleNames() == null;
      return isExcluded;
   }

   /** Accessor for the security-constraint/auth-constraint/role-name(s)
    * @return A possibly empty set of constraint role names. Use isUnchecked
    * and isExcluded to check for no or an emtpy auth-constraint
   */
   @XmlTransient
   public List<String> getRoleNames()
   {
      List<String> roleNames = Collections.emptyList();
      if (authConstraint != null && authConstraint.getRoleNames() != null)
         roleNames = authConstraint.getRoleNames();
      return roleNames;
   }

   /**
    * Accessor for the UserDataConstraint.TransportGuarantee
    * @return UserDataConstraint.TransportGuarantee
    */
   @XmlTransient
   public TransportGuaranteeType getTransportGuarantee()
   {
      TransportGuaranteeType type = TransportGuaranteeType.NONE;
      if (userDataConstraint != null)
         type = userDataConstraint.getTransportGuarantee();
      return type;
   }
}
