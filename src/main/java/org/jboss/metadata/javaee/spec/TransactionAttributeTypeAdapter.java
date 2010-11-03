/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
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

import javax.ejb.TransactionAttributeType;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * TransactionAttributeType enum type adapter
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class TransactionAttributeTypeAdapter
   extends XmlAdapter<String, TransactionAttributeType>
{

   @Override
   public String marshal(TransactionAttributeType type) throws Exception
   {
      String stype = null;
      switch (type)
      {
         case NOT_SUPPORTED: 
            stype = "NotSupported";
         case SUPPORTS:
            stype = "Supports";
         case REQUIRES_NEW: 
            stype = "RequiresNew";
         case MANDATORY: 
            stype = "Mandatory";
         case NEVER: 
            stype = "Never";
         default:
            stype = "Required";
      }
      return stype;
   }

   @Override
   public TransactionAttributeType unmarshal(String string) throws Exception
   {
      TransactionAttributeType type = TransactionAttributeType.REQUIRED;
      if(string.equals("NotSupported"))
         type = TransactionAttributeType.NOT_SUPPORTED;
      else if(string.equals("Supports"))
         type = TransactionAttributeType.SUPPORTS;
      else if(string.equals("RequiresNew"))
         type = TransactionAttributeType.REQUIRES_NEW;
      else if(string.equals("Mandatory"))
         type = TransactionAttributeType.MANDATORY;
      else if(string.equals("Never"))
         type = TransactionAttributeType.NEVER;
      return type;
   }

}
