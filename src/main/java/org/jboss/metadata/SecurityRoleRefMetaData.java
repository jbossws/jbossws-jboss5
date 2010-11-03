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
package org.jboss.metadata;

import org.jboss.metadata.javaee.spec.DescriptionsImpl;

/**
 * The metadata object for the security-role-ref element.
 * The security-role-ref element contains the declaration of a security
 * role reference in the enterprise bean's code. The declaration con-sists
 * of an optional description, the security role name used in the
 * code, and an optional link to a defined security role.
 * The value of the role-name element must be the String used as the
 * parameter to the EJBContext.isCallerInRole(String roleName) method.
 * The value of the role-link element must be the name of one of the
 * security roles defined in the security-role elements.
 *
 * @author <a href="mailto:sebastien.alborini@m4x.org">Sebastien Alborini</a>
 * @author <a href="mailto:Scott.Stark@jboss.org">Scott Stark</a>.
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class SecurityRoleRefMetaData extends OldMetaData<org.jboss.metadata.javaee.spec.SecurityRoleRefMetaData>
{
   /**
    * Create a new SecurityRoleRefMetaData.
    *
    * @param delegate the delegate
    * @return the metadata
    * @throws IllegalArgumentException for a null delegate or an unknown delegate
    */
   public static SecurityRoleRefMetaData create(org.jboss.metadata.javaee.spec.SecurityRoleRefMetaData delegate)
   {
      if (delegate == null)
         throw new IllegalArgumentException("Null delegate");
      return new SecurityRoleRefMetaData(delegate);
   }

   /**
    * Create a new ResourceRefMetaData.
    *
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   public SecurityRoleRefMetaData(org.jboss.metadata.javaee.spec.SecurityRoleRefMetaData delegate)
   {
      super(delegate);
   }

   /**
    * Get the name
    * 
    * @return the name
    */
   public String getName()
   {
      return getDelegate().getRoleName();
   }

   /**
    * Get the link
    * 
    * @return the link
    */
   public String getLink()
   {
      return getDelegate().getRoleLink();
   }

   /**
    * Get the description
    * 
    * @return the description
    */
   public String getDescription()
   {
      DescriptionsImpl descriptions = (DescriptionsImpl) getDelegate().getDescriptions();
      if (descriptions == null || descriptions.isEmpty())
         return null;
      return descriptions.iterator().next().getDescription();
   }
/*
   public void importEjbJarXml(Element element)
   {
      name = getElementContent(getUniqueChild(element, "role-name"));
      link = getElementContent(getOptionalChild(element, "role-link"));
      description = getElementContent(getOptionalChild(element, "description"));
   }
   */
}
