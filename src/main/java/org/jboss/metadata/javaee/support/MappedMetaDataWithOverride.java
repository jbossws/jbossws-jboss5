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
package org.jboss.metadata.javaee.support;

/**
 * MappedMetaDataWithOverride.
 *
 * @param <C> the overriden component type
 * @param <T> the mapped type
 * @param <O> the overriden type
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public interface MappedMetaDataWithOverride<C extends MappableMetaData, T extends MappableMetaDataWithOverride<C>, O extends MappedMetaData<C>> extends MappedMetaData<T>, OverrideMetaData<O>
{
   /**
    * Create an override metadata
    * 
    * @param data the overridden metadata
    * @return the override metadata
    */
   T createOverride(C data);
   
   /**
    * Create an original metadata
    * 
    * @param data the overridden metadata
    * @return the original
    */
   C createOriginal(T data);
}
