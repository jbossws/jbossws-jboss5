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

import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;

/**
 * The meta data information for a resource-ref element.
 * The resource-ref element contains a declaration of enterprise bean's
 * reference to an external resource. It consists of an optional description,
 * the resource manager connection factory reference name, the
 * indication of the resource manager connection factory type expected
 * by the enterprise bean code, the type of authentication (Application 
 * or Container), and an optional specification of the shareability of
 * connections obtained from the resource (Shareable or Unshareable).
 * Used in: entity, message-driven, and session
 *
 * @author <a href="mailto:sebastien.alborini@m4x.org">Sebastien Alborini</a>
 * @author <a href="mailto:Scott.Stark@jboss.org">Scott Stark</a>.
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 57300 $
 */
@Deprecated
public class ResourceRefMetaData extends OldMetaData<ResourceReferenceMetaData>
{
   /**
    * Create a new ResourceRefMetaData.
    *
    * @param delegate the delegate
    * @return the metadata
    * @throws IllegalArgumentException for a null delegate or an unknown delegate
    */
   public static ResourceRefMetaData create(ResourceReferenceMetaData delegate)
   {
      if (delegate == null)
         throw new IllegalArgumentException("Null delegate");
      return new ResourceRefMetaData(delegate);
   }

   /**
    * Create a new ResourceRefMetaData.
    *
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   public ResourceRefMetaData(ResourceReferenceMetaData delegate)
   {
      super(delegate);
   }

   /**
    * Get the resource name
    * 
    * @return the resource name
    */
   public String getResourceName()
   {
      String name = getName();
      if (name == null)
         return getRefName();
      return name;
   }

   /**
    * Set ther resource name
    * 
    * @param resName the resource name
    * @throws UnsupportedOperationException always
    */
   public void setResourceName(String resName)
   {
      throw new UnsupportedOperationException("setResourceName");
   }

   /**
    * Get the containerAuth.
    * 
    * @return the containerAuth.
    */
   public boolean isContainerAuth()
   {
      return getDelegate().isContainerAuth();
   }

   /**
    * Get the isShareable.
    * 
    * @return the isShareable.
    */
   public boolean isShareable()
   {
      return getDelegate().isShareable();
   }

   /**
    * Get the jndiName.
    * 
    * @return the jndiName.
    */
   public String getJndiName()
   {
      return getDelegate().getMappedName();
   }

   /**
    * Get the name.
    * 
    * @return the name.
    */
   public String getName()
   {
      return getDelegate().getName();
   }

   /**
    * Get the refName.
    * 
    * @return the refName.
    */
   public String getRefName()
   {
      return getDelegate().getName();
   }

   /**
    * Get the resURL.
    * 
    * @return the resURL.
    */
   public String getResURL()
   {
      return getDelegate().getResUrl();
   }

   /**
    * Get the type.
    * 
    * @return the type.
    */
   public String getType()
   {
      return getDelegate().getType();
   }
}
