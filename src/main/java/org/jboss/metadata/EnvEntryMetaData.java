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

import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;

/**
 * Environment Entry metadata
 * 
 * @author <a href="mailto:sebastien.alborini@m4x.org">Sebastien Alborini </a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 * @version $Revision: 57300 $
 */
@Deprecated
public class EnvEntryMetaData extends OldMetaData<EnvironmentEntryMetaData>
{
   /**
    * Create a new EnvEntryMetaData.
    *
    * @param delegate the delegate
    * @return the metadata
    * @throws IllegalArgumentException for a null delegate or an unknown delegate
    */
   public static EnvEntryMetaData create(EnvironmentEntryMetaData delegate)
   {
      if (delegate == null)
         throw new IllegalArgumentException("Null delegate");
      return new EnvEntryMetaData(delegate);
   }

   /**
    * Create a new EnvEntryMetaData.
    *
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   public EnvEntryMetaData(EnvironmentEntryMetaData delegate)
   {
      super(delegate);
   }

   /**
    * Get the name.
    * 
    * @return the name.
    */
   public String getName()
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
    * Get the value.
    * 
    * @return the value.
    */
   public String getValue()
   {
      return getDelegate().getValue();
   }
}
