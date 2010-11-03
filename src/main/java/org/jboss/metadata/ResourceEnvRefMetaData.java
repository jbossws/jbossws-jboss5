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

import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;

/**
 * The meta data information for a resource-env-ref element.
 * The resource-env-ref element contains a declaration of an enterprise
 * bean's reference to an administered object associated with a resource
 * in the enterprise bean's environment. It consists of an optional
 * description, the resource environment reference name, and an indication
 * of the resource environment reference type expected by the enterprise
 * bean code.
 * <p/>
 * Used in: entity, message-driven and session
 * <p/>
 * Example:
 * <resource-env-ref>
 * <resource-env-ref-name>jms/StockQueue</resource-env-ref-name>
 * <resource-env-ref-type>javax.jms.Queue</resource-env-ref-type>
 * </resource-env-ref>
 *
 * @author <a href="mailto:Scott.Stark@jboss.org">Scott Stark</a>.
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 57300 $
 */
@Deprecated
public class ResourceEnvRefMetaData extends OldMetaData<ResourceEnvironmentReferenceMetaData>
{
   /**
    * Create a new ResourceEnvRefMetaData.
    *
    * @param delegate the delegate
    * @return the metadata
    * @throws IllegalArgumentException for a null delegate or an unknown delegate
    */
   public static ResourceEnvRefMetaData create(ResourceEnvironmentReferenceMetaData delegate)
   {
      if (delegate == null)
         throw new IllegalArgumentException("Null delegate");
      return new ResourceEnvRefMetaData(delegate);
   }

   /**
    * Create a new ResourceEnvRefMetaData.
    *
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   public ResourceEnvRefMetaData(ResourceEnvironmentReferenceMetaData delegate)
   {
      super(delegate);
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
    * Get the type.
    * 
    * @return the type.
    */
   public String getType()
   {
      return getDelegate().getType();
   }
}
