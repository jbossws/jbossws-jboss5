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

import org.jboss.metadata.javaee.support.MergeableMappedMetaData;
import org.jboss.metadata.javaee.support.ResourceInjectionMetaDataWithDescriptions;

/**
 * ResourceEnvironmentReferenceMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="resource-env-refType", propOrder={"descriptions", "resourceEnvRefName", "type", "jndiName", "mappedName", "injectionTargets", "ignoreDependency"})
public class ResourceEnvironmentReferenceMetaData extends ResourceInjectionMetaDataWithDescriptions implements MergeableMappedMetaData<ResourceEnvironmentReferenceMetaData>
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -3906197284118629544L;

   /** The type */
   private String type;
   
   /**
    * Create a new ResourceEnvironmentReferenceMetaData.
    */
   public ResourceEnvironmentReferenceMetaData()
   {
      // For serialization
   }

   /**
    * Get the resourceEnvRefName.
    * 
    * @return the resourceEnvRefName.
    */
   public String getResourceEnvRefName()
   {
      return getName();
   }

   /**
    * Set the resourceEnvRefName.
    * 
    * @param resourceEnvRefName the resourceEnvRefName.
    * @throws IllegalArgumentException for a null resourceEnvRefName
    */
   //@JBossXmlNsPrefix(prefix="jee")
   public void setResourceEnvRefName(String resourceEnvRefName)
   {
      setName(resourceEnvRefName);
   }

   /**
    * Get the type.
    * 
    * @return the type.
    */
   public String getType()
   {
      return type;
   }

   /**
    * Set the type.
    * 
    * @param type the type.
    * @throws IllegalArgumentException for a null type
    */
   @XmlElement(name="resource-env-ref-type")
   //@JBossXmlNsPrefix(prefix="jee")
   public void setType(String type)
   {
      if (type == null)
         throw new IllegalArgumentException("Null type");
      this.type = type;
   }

   public ResourceEnvironmentReferenceMetaData merge(ResourceEnvironmentReferenceMetaData original)
   {
      ResourceEnvironmentReferenceMetaData merged = new ResourceEnvironmentReferenceMetaData();
      merged.merge(this, original);
      return merged;
   }
   
   /**
    * Merge the contents of override with original into this.
    * 
    * @param override data which overrides original
    * @param original the original data
    */
   public void merge(ResourceEnvironmentReferenceMetaData override, ResourceEnvironmentReferenceMetaData original)
   {
      super.merge(override, original);
      if (override != null && override.type != null)
         setType(override.type);
      else if (original != null && original.type != null)
         setType(original.type);
   }
}
