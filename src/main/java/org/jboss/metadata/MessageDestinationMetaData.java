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

import org.jboss.metadata.spi.MetaData;

/** 
 * Message Destination Metadata
 * 
 * @author <a href="mailto:adrian@jboss.com">Adrian Brock</a>.
 * @version $Revision: 37390 $
 */
public class MessageDestinationMetaData extends OldMetaData<org.jboss.metadata.javaee.spec.MessageDestinationMetaData>
{
   /**
    * Create a new MessageDestinationMetaData.
    *
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   public MessageDestinationMetaData(org.jboss.metadata.javaee.spec.MessageDestinationMetaData delegate)
   {
      super(delegate);
   }
   
   /**
    * Create a new MessageDestinationMetaData.
    * 
    * @param metaData the delegate metadata
    * @throws IllegalArgumentException for a null metaData
    * @throws IllegalStateException if the metadata doesn't have an {@link MessageDestinationMetaData}
    */
   protected MessageDestinationMetaData(MetaData metaData)
   {
      super(metaData, org.jboss.metadata.javaee.spec.MessageDestinationMetaData.class);
   }

   /**
    * Get the destination name
    * 
    * @return the destination name
    */
   public String getName()
   {
      return getDelegate().getMessageDestinationName();
   }

   /**
    * Get the jndi name
    * 
    * @return the jndi name
    */
   public String getJNDIName()
   {
      return getDelegate().getMappedName();
   }
}
