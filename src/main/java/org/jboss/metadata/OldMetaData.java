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
package org.jboss.metadata;

import org.jboss.metadata.spi.MetaData;
import org.w3c.dom.Element;

/**
 * OldMetaData.
 * 
 * @param <T> the delegate type
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@Deprecated
public class OldMetaData<T> extends org.jboss.metadata.MetaData
{
   /** The meta data */
   private MetaData metaData;
   
   /** The delegate */
   private T delegate;
   
   /**
    * Create a new OldMetaData.
    * 
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   public OldMetaData(T delegate)
   {
      if (delegate == null)
         throw new IllegalArgumentException("Null delegate");
      this.delegate = delegate;
   }
   
   /**
    * Create a new OldMetaData.
    * 
    * @param metaData the delegate metadata
    * @param type the delegate type
    * @throws IllegalArgumentException for a null metaData or type
    * @throws IllegalStateException if the metadata doesn't have a T
    */
   public OldMetaData(MetaData metaData, Class<T> type)
   {
      if (metaData == null)
         throw new IllegalArgumentException("Null metaData");
      if (type == null)
         throw new IllegalArgumentException("Null type");
      
      this.metaData = metaData;
      
      delegate = metaData.getMetaData(type);
      if (delegate == null)
         throw new IllegalStateException(type.getName() + " not found in " + metaData);
   }

   /**
    * Get the delegate.
    * 
    * @return the delegate.
    */
   protected T getDelegate()
   {
      return delegate;
   }

   /**
    * Get the metaData.
    * 
    * @return the metaData.
    */
   protected MetaData getMetaData()
   {
      if (metaData == null)
         throw new IllegalStateException("MetaData has not been set: " + this);
      return metaData;
   }

   @Override
   public String toString()
   {
      return super.toString() + "{" + getDelegate() + "}";
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null || obj instanceof OldMetaData == false)
         return false;
      OldMetaData other = (OldMetaData) obj;
      return getDelegate().equals(other.getDelegate());
   }
   
   @Override
   public int hashCode()
   {
      return getDelegate().hashCode();
   }
   
   @Override
   public void importEjbJarXml(Element element)
   {
      throw new UnsupportedOperationException("importEjbJarXml");
   }

   @Override
   public void importJbossXml(Element element)
   {
      throw new UnsupportedOperationException("importJbossXml");
   }
}
