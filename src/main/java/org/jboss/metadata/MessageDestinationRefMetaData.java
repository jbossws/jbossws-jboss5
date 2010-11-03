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

import org.jboss.metadata.javaee.spec.MessageDestinationReferenceMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationUsageType;

/** 
 * Message Destination Reference Metadata
 * 
 * @author <a href="mailto:adrian@jboss.com">Adrian Brock</a>.
 * @version $Revision: 57300 $
 */
@Deprecated
public class MessageDestinationRefMetaData extends OldMetaData<MessageDestinationReferenceMetaData>
{
   /** Whether this consumes */
   public static final int CONSUMES = 1;

   /** Whether this produces */
   public static final int PRODUCES = 2;

   /** Whether this consumes and produces */
   public static final int CONSUMESPRODUCES = 3;

   /**
    * Create a new MessageDestinationRefMetaData.
    *
    * @param delegate the delegate
    * @return the metadata
    * @throws IllegalArgumentException for a null delegate or an unknown delegate
    */
   public static MessageDestinationRefMetaData create(MessageDestinationReferenceMetaData delegate)
   {
      if (delegate == null)
         throw new IllegalArgumentException("Null delegate");
      return new MessageDestinationRefMetaData(delegate);
   }

   /**
    * Create a new MesssageDestinationRefMetaData.
    *
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   public MessageDestinationRefMetaData(MessageDestinationReferenceMetaData delegate)
   {
      super(delegate);
   }

   /**
    * Get the jndi name
    * 
    * @return the jndi name
    */
   public String getJNDIName()
   {
      return getJndiName();
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
    * Get the link.
    * 
    * @return the link.
    */
   public String getLink()
   {
      return getDelegate().getLink();
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

   /**
    * Get the usage.
    * 
    * @return the usage.
    */
   public int getUsage()
   {
      MessageDestinationUsageType usage = getDelegate().getMessageDestinationUsage();
      if (usage == null)
         return 0;
      if (usage == MessageDestinationUsageType.Consumes)
         return CONSUMES;
      if (usage == MessageDestinationUsageType.Produces)
         return PRODUCES;
      if (usage == MessageDestinationUsageType.ConsumesProduces)
         return CONSUMESPRODUCES;
      return 0;
   }
}
