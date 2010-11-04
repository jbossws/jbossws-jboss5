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

import org.jboss.metadata.javaee.spec.EJBLocalReferenceMetaData;

/**
 * An ejb-local-ref encapsulation
 * 
 * @author <a href="mailto:docodan@mvcsoft.com">Daniel OConnor</a>
 * @author Scott.Stark@jboss.org
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 57300 $
 */
@Deprecated
public class EjbLocalRefMetaData extends OldMetaData<EJBLocalReferenceMetaData>
{
   /**
    * Create a new EjbLocalRefMetaData.
    *
    * @param delegate the delegate
    * @return the metadata
    * @throws IllegalArgumentException for a null delegate or an unknown delegate
    */
   public static EjbLocalRefMetaData create(EJBLocalReferenceMetaData delegate)
   {
      if (delegate == null)
         throw new IllegalArgumentException("Null delegate");
      return new EjbLocalRefMetaData(delegate);
   }

   /**
    * Create a new EjbLocalRefMetaData.
    *
    * @param delegate the delegate
    * @throws IllegalArgumentException for a null delegate
    */
   public EjbLocalRefMetaData(EJBLocalReferenceMetaData delegate)
   {
      super(delegate);
   }

   /**
    * Get the home.
    * 
    * @return the home.
    */
   public String getLocalHome()
   {
      return getDelegate().getLocalHome();
   }

   /**
    * Get the jndiName.
    * 
    * @return the jndiName.
    */
   public String getJndiName()
   {
      String jndiName = getDelegate().getResolvedJndiName();
      // this fallback to mapped/jndi-name should affect only local tests?
      // the AS tests pass without it
      if(jndiName == null)
         jndiName = getDelegate().getMappedName();
      return jndiName;
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
    * Get the name.
    * 
    * @return the name.
    */
   public String getName()
   {
      return getDelegate().getName();
   }

   /**
    * Get the local.
    * 
    * @return the local.
    */
   public String getLocal()
   {
      return getDelegate().getLocal();
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
    * Get the invoker binding
    * 
    * @param bindingName the binding name
    * @return the invoker binding or null if not found
    * @throws UnsupportedOperationException always
    */
   public String getInvokerBinding(String bindingName)
   {
      throw new UnsupportedOperationException("local refs have to external invokers");
   }
}