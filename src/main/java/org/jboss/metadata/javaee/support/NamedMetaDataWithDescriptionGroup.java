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
 * NamedMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class NamedMetaDataWithDescriptionGroup extends NamedMetaData implements MappableMetaData
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -1282107210311451130L;
   
   /** The description */
   private DescriptionGroupMetaData descriptionGroup;

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
   //@SchemaProperty(noInterceptor=true)
   public void setDescriptionGroup(DescriptionGroupMetaData descriptionGroup)
   {
      if (descriptionGroup == null)
         throw new IllegalArgumentException("Null descriptionGroup");
      this.descriptionGroup = descriptionGroup;
   }

   @Override
   public void merge(NamedMetaData override, NamedMetaData original)
   {
      super.merge(override, original);
      NamedMetaDataWithDescriptionGroup n0 = (NamedMetaDataWithDescriptionGroup) override;
      NamedMetaDataWithDescriptionGroup n1 = (NamedMetaDataWithDescriptionGroup) original;
      if(n0 != null && n0.getDescriptionGroup() != null)
         setDescriptionGroup(n0.getDescriptionGroup());
      else if(n1 != null && n1.getDescriptionGroup() != null)
         setDescriptionGroup(n1.getDescriptionGroup());
   }

}
