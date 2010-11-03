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
 * ResourceReferenceMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="resource-refType", 
      propOrder={ 
         "descriptions", "resourceRefName", "type", "resAuth", "resSharingScope",
         "resourceName", "jndiName", "mappedName", "resUrl",
         "injectionTargets", "ignoreDependency"
         } )
public class ResourceReferenceMetaData extends ResourceInjectionMetaDataWithDescriptions implements MergeableMappedMetaData<ResourceReferenceMetaData>
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 1900675456507941940L;

   /** The type */
   private String type;
   
   /** The authority */
   private ResourceAuthorityType authority;
   
   /** The sharing scope */
   private ResourceSharingScopeType sharingScope;
   
   /** The resource name */
   private String resourceName;
   
   /** The resource url */
   private String resUrl;
   
   /**
    * Create a new ResourceReferenceMetaData.
    */
   public ResourceReferenceMetaData()
   {
      // For serialization
   }

   /**
    * Get the resourceRefName.
    * 
    * @return the resourceRefName.
    */
   public String getResourceRefName()
   {
      return getName();
   }

   /**
    * Set the resourceRefName.
    * 
    * @param resourceRefName the resourceRefName.
    * @throws IllegalArgumentException for a null resourceRefName
    */
   @XmlElement(name="res-ref-name")
   public void setResourceRefName(String resourceRefName)
   {
      setName(resourceRefName);
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
   @XmlElement(name="res-type")
   public void setType(String type)
   {
      if (type == null)
         throw new IllegalArgumentException("Null type");
      this.type = type;
   }

   /**
    * Get the authority.
    * 
    * @return the authority.
    */
   public ResourceAuthorityType getResAuth()
   {
      return authority;
   }

   /**
    * Set the authority.
    * 
    * @param authority the authority.
    * @throws IllegalArgumentException for a null authority
    */
   public void setResAuth(ResourceAuthorityType authority)
   {
      if (authority == null)
         throw new IllegalArgumentException("Null authority");
      this.authority = authority;
   }

   /**
    * Get the containerAuth.
    * 
    * @return the containerAuth.
    */
   public boolean isContainerAuth()
   {
      if (authority == null)
         return false;
      else
         return authority == ResourceAuthorityType.Container;
   }

   /**
    * Get the sharingScope.
    * 
    * @return the sharingScope.
    */
   public ResourceSharingScopeType getResSharingScope()
   {
      return sharingScope;
   }

   /**
    * Set the sharingScope.
    * 
    * @param sharingScope the sharingScope.
    * @throws IllegalArgumentException for a null sharingScope
    */
   @XmlElement(name="res-sharing-scope")
   public void setResSharingScope(ResourceSharingScopeType sharingScope)
   {
      if (sharingScope == null)
         throw new IllegalArgumentException("Null sharingScope");
      this.sharingScope = sharingScope;
   }

   /**
    * Get the isShareable.
    * 
    * @return the isShareable.
    */
   public boolean isShareable()
   {
      if (sharingScope == null)
         return true;
      else
         return sharingScope == ResourceSharingScopeType.Shareable;
   }

   public ResourceReferenceMetaData merge(ResourceReferenceMetaData original)
   {
      ResourceReferenceMetaData merged = new ResourceReferenceMetaData();
      merged.merge(this, original);
      return merged;
   }
   
   /**
    * Merge the contents of override with original into this.
    * 
    * @param override data which overrides original
    * @param original the original data
    */
   public void merge(ResourceReferenceMetaData override, ResourceReferenceMetaData original)
   {
      super.merge(override, original);
      if (override != null && override.type != null)
         setType(override.type);
      else if (original.type != null)
         setType(original.type);
      if (override != null && override.resourceName != null)
         setResourceName(override.resourceName);
      else if(original.resourceName != null)
         setResourceName(original.resourceName);
      if (override != null && override.authority != null)
         setResAuth(override.authority);
      else if (original.authority != null)
         setResAuth(original.authority);
      if (override != null && override.sharingScope != null)
         setResSharingScope(override.sharingScope);
      else if (original.sharingScope != null)
         setResSharingScope(original.sharingScope);
      if (override != null && override.resUrl != null)
         setResUrl(override.resUrl);
      else if (original.resUrl != null)
         setResUrl(original.resUrl);
   }

   /**
    * Get the resourceName.
    * 
    * @return the resourceName.
    */
   public String getResourceName()
   {
      return resourceName;
   }

   /**
    * Set the resourceName.
    * 
    * @param resourceName the resourceName.
    * @throws IllegalArgumentException for a null resourceName
    */
   //@SchemaProperty(mandatory=false)
   @XmlElement(required=false)
   public void setResourceName(String resourceName)
   {
      if (resourceName == null)
         throw new IllegalArgumentException("Null resourceName");
      this.resourceName = resourceName;
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
   //@SchemaProperty(mandatory=false)
   @XmlElement(required=false)
   public void setResUrl(String resUrl)
   {
      if (resUrl == null)
         throw new IllegalArgumentException("Null resUrl");
      this.resUrl = resUrl;
   }

   @Override
   public String toString()
   {
      StringBuilder tmp = new StringBuilder("ResourceReferenceMetaData{");
      tmp.append("name=");
      tmp.append(getResourceRefName());
      tmp.append(",resource-name=");
      tmp.append(getResourceName());
      tmp.append(",res-auth=");
      tmp.append(getResAuth());
      tmp.append(",res-sharing-scope=");
      tmp.append(getResSharingScope());
      tmp.append(",res-url=");
      tmp.append(getResUrl());
      tmp.append(",ignore-dependecy=");
      tmp.append(super.isDependencyIgnored());
      tmp.append(",jndi-name=");
      tmp.append(super.getJndiName());
      tmp.append(",resolvoed-jndi-name=");
      tmp.append(super.getResolvedJndiName());
      tmp.append('}');
      return tmp.toString();
   }
}
