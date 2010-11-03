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

import org.jboss.metadata.javaee.support.ResourceInjectionMetaDataWithDescriptions;
import org.jboss.xb.annotations.JBossXmlPreserveWhitespace;

/**
 * EnvironmentEntryMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="env-entryType", propOrder={"descriptions", "envEntryName", "type", "value", "jndiName", "mappedName", "injectionTargets", "ignoreDependency"})
public class EnvironmentEntryMetaData extends ResourceInjectionMetaDataWithDescriptions
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -4919146521508624575L;

   /** The type */
   private String type;
   
   /** The value */
   private String value;
   
   /**
    * Create a new EnvironmentEntryMetaData.
    */
   public EnvironmentEntryMetaData()
   {
      // For serialization
   }

   /**
    * Get the envEntryName.
    * 
    * @return the envEntryName.
    */
   public String getEnvEntryName()
   {
      return getName();
   }

   /**
    * Set the envEntryName.
    * 
    * @param envEntryName the envEntryName.
    * @throws IllegalArgumentException for a null envEntryName
    */
   public void setEnvEntryName(String envEntryName)
   {
      setName(envEntryName);
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
   @XmlElement(name="env-entry-type")
   public void setType(String type)
   {
      if (type == null)
         throw new IllegalArgumentException("Null type");
      this.type = type;
   }

   /**
    * Get the value.
    * 
    * @return the value.
    */
   public String getValue()
   {
      return value;
   }

   /**
    * Set the value.
    * 
    * @param value the value.
    * @throws IllegalArgumentException for a null value
    */
   @XmlElement(name="env-entry-value")
   @JBossXmlPreserveWhitespace
   public void setValue(String value)
   {
      if (value == null)
         throw new IllegalArgumentException("Null value");
      this.value = value;
   }

   public void merge(EnvironmentEntryMetaData override,
         EnvironmentEntryMetaData original)
   {
      super.merge(override, original);
      if (override != null && override.type != null)
         setType(override.type);
      else if (original != null && original.type != null)
         setType(original.type);
      if (override != null && override.value != null)
         setValue(override.value);
      else if (original != null && original.value != null)
         setValue(original.value);
   }

}
