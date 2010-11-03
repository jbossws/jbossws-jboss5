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
package org.jboss.metadata.javaee.jboss;

import org.jboss.metadata.javaee.support.AbstractMappedMetaData;

/**
 * JndiRefsMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class JndiRefsMetaData extends AbstractMappedMetaData<JndiRefMetaData>
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 7447964711034672079L;

   /**
    * Create a new JndiRefsMetaData.
    */
   public JndiRefsMetaData()
   {
      super("jndi ref name");
   }
   
   public void merge(JndiRefsMetaData override, JndiRefsMetaData original)
   {
      super.merge(override, original);
      if (original != null)
      {
         for (JndiRefMetaData property : original)
            add(property);
      }
      if (override != null)
      {
         for (JndiRefMetaData property : override)
            add(property);
      }
   }
}
