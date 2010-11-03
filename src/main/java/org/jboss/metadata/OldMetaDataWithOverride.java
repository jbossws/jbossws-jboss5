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

import org.jboss.metadata.javaee.support.OverrideMetaData;
import org.jboss.metadata.spi.MetaData;

/**
 * OldMetaDataWithOverride.
 * 
 * @param <T> the delegate type
 * @param <O> the overriden metadata
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@SuppressWarnings("deprecation")
public class OldMetaDataWithOverride<T extends OverrideMetaData<O>, O> extends OldMetaData<T>
{
   /** The overriden delegate */
   private O overridenDelegate;
   
   /**
    * Create a new OldMetaDataWithOverride.
    * 
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   public OldMetaDataWithOverride(T delegate)
   {
      super(delegate);
      this.overridenDelegate = delegate.getOverridenMetaData(); 
   }
   
   /**
    * Create a new OldMetaData.
    * 
    * @param metaData the delegate metadata
    * @param type the delegate type
    * @throws IllegalArgumentException for a null metaData or type
    * @throws IllegalStateException if the metadata doesn't have a T
    */
   public OldMetaDataWithOverride(MetaData metaData, Class<T> type)
   {
      super(metaData, type);
      this.overridenDelegate = getDelegate().getOverridenMetaData();
   }

   /**
    * Get the overriden delegate.
    * 
    * @return the delegate.
    */
   protected O getOverridenDelegate()
   {
      return overridenDelegate;
   }
}
