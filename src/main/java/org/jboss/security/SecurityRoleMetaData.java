/*
* JBoss, Home of Professional Open Source
* Copyright 2005, JBoss Inc., and individual contributors as indicated
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
package org.jboss.security;

import java.util.Set;

import org.jboss.metadata.OldMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.spi.MetaData;

/**
 * The meta data object for the security-role-mapping element.
 *
 * The security-role-mapping element maps the user principal
 * to a different principal on the server. It can for example
 * be used to map a run-as-principal to more than one role.
 *
 * @author Thomas.Diesler@jboss.org
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 * @version $Revision: 40750 $
 */
@Deprecated
public class SecurityRoleMetaData extends OldMetaData<org.jboss.metadata.javaee.spec.SecurityRoleMetaData> 
{
   /**
    * Create a new SecurityRoleMetaData.
    *
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   public SecurityRoleMetaData(org.jboss.metadata.javaee.spec.SecurityRoleMetaData delegate)
   {
      super(delegate);
   }
   
   /**
    * Create a new SecurityMetaData.
    * 
    * @param metaData the delegate metadata
    * @throws IllegalArgumentException for a null metaData
    * @throws IllegalStateException if the metadata doesn't have an {@link SecurityRolesMetaData}
    */
   protected SecurityRoleMetaData(MetaData metaData)
   {
      super(metaData, org.jboss.metadata.javaee.spec.SecurityRoleMetaData.class);
   }

   /**
    * Get the role name
    * 
    * @return the role name
    */
   public String getRoleName()
   {
      return getDelegate().getRoleName();
   }

   /**
    * Get the principals
    * 
    * @return the principals
    */
   public Set<String> getPrincipals()
   {
      return getDelegate().getPrincipals();
   }

   /**
    * Add a principal name
    * 
    * @param principalName the principal name
    * @throws UnsupportedOperationException always
    */
   public void addPrincipalName(String principalName)
   {
      throw new UnsupportedOperationException("addPrincipalName");
   }

   /**
    * Add some principal names
    * 
    * @param principalNames the principal names
    * @throws UnsupportedOperationException always
    */
   public void addPrincipalNames(Set principalNames)
   {
      throw new UnsupportedOperationException("addPrincipalNames");
   }
}
