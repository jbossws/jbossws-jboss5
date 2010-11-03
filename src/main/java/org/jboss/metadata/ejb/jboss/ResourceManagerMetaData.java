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
package org.jboss.metadata.ejb.jboss;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 * ResourceManagerMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="resource-managerType", propOrder={"descriptions", "resName", "resJndiName", "resUrl"})
public class ResourceManagerMetaData extends NamedMetaDataWithDescriptions
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 244708249262277696L;

   /** The resource class */
   private String resClass;
   
   /** The resource jndi name */
   private String resJndiName;
   
   /** The resource url */
   private String resUrl;
   
   @XmlTransient
   public String getName()
   {
      return super.getName();
   }
   
   /**
    * Get the resName.
    * 
    * @return the resName.
    */
   public String getResName()
   {
      return getName();
   }

   /**
    * Set the resName.
    * 
    * @param resName the resName.
    * @throws IllegalArgumentException for a null resName
    */
   @XmlElement(required=true)
   public void setResName(String resName)
   {
      setName(resName);
   }

   /**
    * Get the resClass.
    * 
    * @return the resClass.
    */
   @XmlAttribute
   public String getResClass()
   {
      return resClass;
   }

   /**
    * Set the resClass.
    * 
    * @param resClass the resClass.
    * @throws IllegalArgumentException for a null resClass
    */
   public void setResClass(String resClass)
   {
      if (resClass == null)
         throw new IllegalArgumentException("Null resClass");
      this.resClass = resClass;
   }

   /**
    * Get the resJndiName.
    * 
    * @return the resJndiName.
    */
   public String getResJndiName()
   {
      return resJndiName;
   }

   /**
    * Set the resJndiName.
    * 
    * @param resJndiName the resJndiName.
    * @throws IllegalArgumentException for a null resJndiName
    */
   public void setResJndiName(String resJndiName)
   {
      if (resJndiName == null)
         throw new IllegalArgumentException("Null resJndiName");
      this.resJndiName = resJndiName;
   }

   /**
    * Get the resUrl.
    * 
    * @return the resUrl.
    */
   public String getResUrl()
   {
      return resUrl;
   }

   /**
    * Set the resUrl.
    * 
    * @param resUrl the resUrl.
    * @throws IllegalArgumentException for a null resUrl
    */
   @XmlElement(required=true)
   public void setResUrl(String resUrl)
   {
      if (resUrl == null)
         throw new IllegalArgumentException("Null resUrl");
      this.resUrl = resUrl;
   }
   
   /**
    * Get the resource
    * 
    * @return the resource
    */
   @XmlTransient
   public String getResource()
   {
      if (resJndiName != null)
         return resJndiName;
      else
         return resUrl;
   }
}
