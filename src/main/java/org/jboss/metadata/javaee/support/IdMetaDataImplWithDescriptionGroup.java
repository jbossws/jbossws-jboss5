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
package org.jboss.metadata.javaee.support;

import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;

/**
 * IdMetaDataImplWithDescriptionGroup.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class IdMetaDataImplWithDescriptionGroup extends IdMetaDataImpl
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 3906255525185441267L;
   
   /** The description group */
   private DescriptionGroupMetaData descriptionGroup;

   /**
    * Create a new IdMetaDataImplWithDescriptions.
    */
   public IdMetaDataImplWithDescriptionGroup()
   {
      // For serialization
   }

   /**
    * Get the descriptionGroup.
    * 
    * @return the descriptionGroup.
    */
   public DescriptionGroupMetaData getDescriptionGroup()
   {
      return descriptionGroup;
   }

   /**
    * Set the descriptionGroup.
    * 
    * @param descriptionGroup the descriptionGroup.
    * @throws IllegalArgumentException for a null descriptionGroup
    */
   public void setDescriptionGroup(DescriptionGroupMetaData descriptionGroup)
   {
      if (descriptionGroup == null)
         throw new IllegalArgumentException("Null descriptionGroup");
      this.descriptionGroup = descriptionGroup;
   }

   public void merge(IdMetaDataImplWithDescriptionGroup override,
         IdMetaDataImplWithDescriptionGroup original)
   {
      super.merge(override, original);
      if(override != null && override.descriptionGroup != null)
         setDescriptionGroup(override.descriptionGroup);
      else if(original != null && original.descriptionGroup != null)
         setDescriptionGroup(original.descriptionGroup);         
   }
}
