/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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
package org.jboss.metadata.javaee.spec;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * RunAsMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="run-asType", propOrder={"descriptions", "roleName"})
public class RunAsMetaData extends IdMetaDataImplWithDescriptions 
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 6132381662482264933L;

   /** The role name */
   private String roleName;
   
   /**
    * Create a new SecurityRoleRefMetaData.
    */
   public RunAsMetaData()
   {
      // For serialization
   }

   /**
    * Get the roleName.
    * 
    * @return the roleName.
    */
   public String getRoleName()
   {
      return roleName;
   }

   /**
    * Set the roleName.
    * 
    * @param roleName the roleName.
    * @throws IllegalArgumentException for a null roleName
    */
   //@JBossXmlNsPrefix(prefix="jee")
   @XmlElement(required=true)
   public void setRoleName(String roleName)
   {
      if (roleName == null)
         throw new IllegalArgumentException("Null roleName");
      this.roleName = roleName;
   }

   public String toString()
   {
      StringBuilder tmp = new StringBuilder("RunAsMetaData(id=");
      tmp.append(getId());
      tmp.append(",roleName=");
      tmp.append(roleName);
      tmp.append(')');
      return tmp.toString();
   }
}
