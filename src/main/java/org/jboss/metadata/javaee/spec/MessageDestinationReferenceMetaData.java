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
 * MessageDestinationReferenceMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="message-destination-refType", propOrder={"descriptions", "messageDestinationRefName", "type",
      "messageDestinationUsage", "link", "jndiName", "mappedName", "injectionTargets", "ignoreDependency"})
public class MessageDestinationReferenceMetaData extends ResourceInjectionMetaDataWithDescriptions implements MergeableMappedMetaData<MessageDestinationReferenceMetaData>
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 2129990191983873784L;

   /** The type */
   private String type;
   
   /** The usage */
   private MessageDestinationUsageType usage;
   
   /** The link */
   private String link;

   /**
    * Create a new MessageDestinationReferenceMetaData.
    */
   public MessageDestinationReferenceMetaData()
   {
      // For serialization
   }

   /**
    * Get the messageDestinationRefName.
    * 
    * @return the messageDestinationRefName.
    */
   public String getMessageDestinationRefName()
   {
      return getName();
   }

   /**
    * Set the messageDestinationRefName.
    * 
    * @param messageDestinationRefName the messageDestinationRefName.
    * @throws IllegalArgumentException for a null messageDestinationRefName
    */
   public void setMessageDestinationRefName(String messageDestinationRefName)
   {
      setName(messageDestinationRefName);
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
   @XmlElement(name="message-destination-type")
   public void setType(String type)
   {
      if (type == null)
         throw new IllegalArgumentException("Null type");
      this.type = type;
   }

   /**
    * Get the usage.
    * 
    * @return the usage.
    */
   public MessageDestinationUsageType getMessageDestinationUsage()
   {
      return usage;
   }

   /**
    * Set the usage.
    * 
    * @param usage the usage.
    * @throws IllegalArgumentException for a null usage
    */
   @XmlElement(name="message-destination-usage")
   public void setMessageDestinationUsage(MessageDestinationUsageType usage)
   {
      if (usage == null)
         throw new IllegalArgumentException("Null usage");
      this.usage = usage;
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
   @XmlElement(name="message-destination-link")
   public void setLink(String link)
   {
      if (link == null)
         throw new IllegalArgumentException("Null link");
      this.link = link;
   }

   public MessageDestinationReferenceMetaData merge(MessageDestinationReferenceMetaData original)
   {
      MessageDestinationReferenceMetaData merged = new MessageDestinationReferenceMetaData();
      merged.merge(this, original);
      return merged;
   }
   
   /**
    * Merge the contents of override with original into this.
    * 
    * @param override data which overrides original
    * @param original the original data
    */
   public void merge(MessageDestinationReferenceMetaData override, MessageDestinationReferenceMetaData original)
   {
      super.merge(override, original);
      if (override != null && override.type != null)
         setType(override.type);
      else if (original.type != null)
         setType(original.type);
      if (override != null && override.usage != null)
         setMessageDestinationUsage(override.usage);
      else if (original.usage != null)
         setMessageDestinationUsage(original.usage);
      if (override != null && override.link != null)
         setLink(override.link);
      else if (original.link != null)
         setLink(original.link);
   }

   @Override
   public String toString()
   {
      StringBuilder tmp = new StringBuilder("MessageDestinationReferenceMetaData{");
      tmp.append("name=");
      tmp.append(getMessageDestinationRefName());
      tmp.append(",type=");
      tmp.append(getType());
      tmp.append(",link=");
      tmp.append(getLink());
      tmp.append(",ignore-dependecy=");
      tmp.append(super.isDependencyIgnored());
      tmp.append(",jndi-name=");
      tmp.append(super.getJndiName());
      tmp.append(",resolvoed-jndi-name=");
      tmp.append(super.getResolvedJndiName());
      tmp.append(",usage=");
      tmp.append(getMessageDestinationUsage());
      tmp.append('}');
      return tmp.toString();
   }
}
