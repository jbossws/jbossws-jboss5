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

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;


/**
 * OldMetaDataIterator.
 * 
 * @param <N> the new type
 * @param <O> the old type
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@Deprecated
public class OldMetaDataIterator<N, O extends OldMetaData<N>> implements Iterator<O>
{
   /** The delegate */
   private Iterator<? extends N> delegate;
   
   /** The factory method */
   private Method create;
   
   /**
    * Create a new OldMetaDataIterator.
    * 
    * @param collection the new Collection
    * @param newClass the new class
    * @param oldClass the old class
    */
   public OldMetaDataIterator(Iterable<? extends N> collection, Class<N> newClass, Class<O> oldClass)
   {
      if (collection == null)
         collection = Collections.emptyList();
      delegate = collection.iterator();
      
      try
      {
         create = oldClass.getMethod("create", new Class[] { newClass });
      }
      catch (Exception e)
      {
         throw new RuntimeException("Unable to find factory method: " + oldClass.getName() + ".create(" + newClass.getName() + ")");
      }
   }

   public boolean hasNext()
   {
      return delegate.hasNext();
   }

   @SuppressWarnings("unchecked")
   public O next()
   {
      N next = delegate.next();
      try
      {
         return (O) create.invoke(null, new Object[] { next });
      }
      catch (Exception e)
      {
         throw new RuntimeException("Error invoking factory method: " + create, e);
      }
   }

   public void remove()
   {
      throw new UnsupportedOperationException("remove");
   }
}
