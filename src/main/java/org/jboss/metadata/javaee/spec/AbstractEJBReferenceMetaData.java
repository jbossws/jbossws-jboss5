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
import javax.xml.bind.annotation.XmlTransient;

import org.jboss.metadata.javaee.support.ResourceInjectionMetaDataWithDescriptions;

/**
 * AbstractEJBReferenceMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class AbstractEJBReferenceMetaData extends ResourceInjectionMetaDataWithDescriptions
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 6067128692691909155L;

   /** The type */
   private EJBReferenceType type;
   
   /** The link */
   private String link;
   
   /**
    * Create a new EJBReferenceMetaData.
    */
   public AbstractEJBReferenceMetaData()
   {
      // For serialization
   }

   /**
    * Get the ejbRefName.
    * 
    * @return the ejbRefName.
    */
   public String getEjbRefName()
   {
      return getName();
   }

   /**
    * Set the ejbRefName.
    * 
    * @param ejbRefName the ejbRefName.
    * @throws IllegalArgumentException for a null ejbRefName
    */
   public void setEjbRefName(String ejbRefName)
   {
      setName(ejbRefName);
   }

   /**
    * Get the type.
    * 
    * @return the type.
    */
   public EJBReferenceType getEjbRefType()
   {
      return type;
   }

   /**
    * Set the type.
    * 
    * @param type the type.
    * @throws IllegalArgumentException for a null type
    */
   @XmlElement(name="ejb-ref-type")
   public void setEjbRefType(EJBReferenceType type)
   {
      if (type == null)
         throw new IllegalArgumentException("Null type");
      this.type = type;
   }

   /**
    * Get the type.
    * 
    * @return the type.
    */
   @XmlTransient
   public String getType()
   {
      if (type == null)
         throw new IllegalStateException("Type has not been set: " + this);
      return type.name();
   }

   /**
    * Get the link.
    * 
    * @return the link.
    */
   public String getLink()
   {
      return link;
   }

   /**
    * Set the link.
    * 
    * @param link the link.
    * @throws IllegalArgumentException for a null link
    */
   @XmlElement(name="ejb-link")
   public void setLink(String link)
   {
      if (link == null)
         throw new IllegalArgumentException("Null link");
      this.link = link;
   }
   
   /**
    * Merge the contents of override with original into this.
    * 
    * @param override data which overrides original
    * @param original the original data
    */
   public void merge(AbstractEJBReferenceMetaData override, AbstractEJBReferenceMetaData original)
   {
      super.merge(override, original);
      if(override != null && override.getEjbRefName() != null)
         setEjbRefName(override.getEjbRefName());
      else if(original != null && original.getEjbRefName() != null)
         setEjbRefName(original.getEjbRefName());
      if (override != null && override.type != null)
         setEjbRefType(override.type);
      else if (original != null && original.type != null)
         setEjbRefType(original.type);
      if (override != null && override.link != null)
         setLink(override.link);
      else if (original != null && original.link != null)
         setLink(original.link);
   }
}
