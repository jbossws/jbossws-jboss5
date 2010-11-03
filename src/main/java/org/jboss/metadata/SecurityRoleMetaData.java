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
package org.jboss.metadata;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The meta data object for the security-role-mapping element.
 *
 * The security-role-mapping element maps the user principal
 * to a different principal on the server. It can for example
 * be used to map a run-as-principal to more than one role.
 *
 * @author Thomas.Diesler@jboss.org
 * @version $Revision$
 */
public class SecurityRoleMetaData extends MetaData
   implements Serializable
{
   private static final long serialVersionUID = 1;

   private String description;
   private String roleName;
   private Set<String> principals;

   public SecurityRoleMetaData()
   {
      this(null);
   }
   public SecurityRoleMetaData(String roleName)
   {
      this.roleName = roleName;
      this.principals = new HashSet<String>();
   }

   public String getDescription()
   {
      return description;
   }
   public void setDescription(String description)
   {
      this.description = description;
   }
   public void addPrincipalName(String principalName)
   {
      principals.add(principalName);
   }

   public void addPrincipalNames(Set<String> principalNames)
   {
      principals.addAll(principalNames);
   }

   public String getRoleName()
   {
      return roleName;
   }
   public void setRoleName(String roleName)
   {
      this.roleName = roleName;
   }

   public Set<String> getPrincipals()
   {
      return principals;
   }
}
