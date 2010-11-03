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

import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.jboss.metadata.javaee.support.NamedMetaData;

/**
 * ResourceInjectionMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class ResourceInjectionMetaData extends NamedMetaData
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 6333738851813890701L;

   /** The mapped name */
   private String mappedName;
   private String resolvedJndiName;

   /** The injection targets */
   private Set<ResourceInjectionTargetMetaData> injectionTargets;

   /** The ignore dependency */
   private EmptyMetaData ignoreDependency;
   
   /**
    * Create a new ResourceInjectionMetaData.
    */
   public ResourceInjectionMetaData()
   {
      // For serialization
   }

   @XmlTransient
   public String getName()
   {
      return super.getName();
   }
   
   /**
    * Get the jndiName.
    * 
    * @return the jndiName.
    */
   public String getJndiName()
   {
      return getMappedName();
   }

   /**
    * Set the jndiName.
    * 
    * @param jndiName the jndiName.
    * @throws IllegalArgumentException for a null jndiName
    */
   @XmlElement(required=false)
   public void setJndiName(String jndiName)
   {
      setMappedName(jndiName);
   }
   
   /**
    * Get the mappedName.
    * 
    * @return the mappedName.
    */
   public String getMappedName()
   {
      return mappedName;
   }

   /**
    * Set the mappedName.
    * 
    * @param mappedName the mappedName.
    * @throws IllegalArgumentException for a null mappedName
    */
   @XmlElement(required=false)
   //@JBossXmlNsPrefix(prefix="jee", schemaTargetIfNotMapped=true)
   public void setMappedName(String mappedName)
   {
      if (mappedName == null)
         throw new IllegalArgumentException("Null mappedName");
      this.mappedName = mappedName;
   }

   /**
    * An unmanaged runtime jndi name for the resource. Used by deployers to
    * propagate resolved resource location.
    * 
    * @return
    */
   public String getResolvedJndiName()
   {
      return resolvedJndiName;
   }
   @XmlTransient
   public void setResolvedJndiName(String resolvedJndiName)
   {
      this.resolvedJndiName = resolvedJndiName;
   }

   /**
    * Get the injectionTargets.
    * 
    * @return the injectionTargets.
    */
   public Set<ResourceInjectionTargetMetaData> getInjectionTargets()
   {
      return injectionTargets;
   }

   /**
    * Set the injectionTargets.
    * 
    * @param injectionTargets the injectionTargets.
    * @throws IllegalArgumentException for a null injectionTargets
    */
   @XmlElement(name="injection-target", /*type=NonNullLinkedHashSet.class,*/ required=false)
   //@JBossXmlNsPrefix(prefix="jee", schemaTargetIfNotMapped=true)
   public void setInjectionTargets(Set<ResourceInjectionTargetMetaData> injectionTargets)
   {
      if (injectionTargets == null)
         throw new IllegalArgumentException("Null injectionTargets");
      this.injectionTargets = injectionTargets;
   }

   /**
    * Get the ignoreDependency.
    * 
    * @return the ignoreDependency.
    */
   public EmptyMetaData getIgnoreDependency()
   {
      return ignoreDependency;
   }

   /**
    * Set the ignoreDependency.
    * 
    * @param ignoreDependency the ignoreDependency.
    * @throws IllegalArgumentException for a null ignoreDependency
    */
   @XmlElement(required=false)
   public void setIgnoreDependency(EmptyMetaData ignoreDependency)
   {
      if (ignoreDependency == null)
         throw new IllegalArgumentException("Null ignoreDependency");
      this.ignoreDependency = ignoreDependency;
   }
   
   /**
    * Get whether the dependency is ignored
    * 
    * @return true when the dependency is ignored
    */
   @XmlTransient
   public boolean isDependencyIgnored()
   {
      return ignoreDependency != null;
   }
   
   /**
    * Merge the contents of override with original into this.
    * 
    * @param override data which overrides original
    * @param original the original data
    */
   public void merge(ResourceInjectionMetaData override, ResourceInjectionMetaData original)
   {
      super.merge(override, original);
      if (override != null && override.mappedName != null)
         setMappedName(override.mappedName);
      else if (original != null && original.mappedName != null)
         setMappedName(original.mappedName);
      if (override != null && override.ignoreDependency != null)
         setIgnoreDependency(override.ignoreDependency);
      else if (original != null && original.ignoreDependency != null)
         setIgnoreDependency(original.ignoreDependency);
      
      // TODO proper merge
      if (override != null && override.injectionTargets != null)
         setInjectionTargets(override.injectionTargets);
      else if (original != null && original.injectionTargets != null)
         setInjectionTargets(original.injectionTargets);
   }
}
