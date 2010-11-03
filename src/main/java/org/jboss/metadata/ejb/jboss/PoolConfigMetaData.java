/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
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
package org.jboss.metadata.ejb.jboss;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Represents an <pool-config> element of the jboss.xml deployment descriptor
 *
 * @author <a href="mailto:bdecoste@jboss.com">William DeCoste</a>
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version <tt>$Revision$</tt>
 */
@XmlType(name="pool-configType", propOrder={"value", "maxSize", "timeout"})
public class PoolConfigMetaData
{
   private String value = null;
   private Integer maxSize = null;
   private Integer timeout = null;
   
   public String getValue()
   {
      return value;
   }

   @XmlElement(name="pool-value")
   public void setValue(String value)
   {
      this.value = value;
   }

   @XmlElement(name="pool-max-size")
   public Integer getMaxSize()
   {
      return maxSize;
   }

   public void setMaxSize(Integer maxSize)
   {
      this.maxSize = maxSize;
   }
   
   @XmlElement(name="pool-timeout")
   public Integer getTimeout()
   {
      return timeout;
   }

   public void setTimeout(Integer timeout)
   {
      this.timeout = timeout;
   }

   public String toString()
   {
      StringBuffer sb = new StringBuffer(100);
      sb.append("[");
      sb.append("poolClass=").append(value);
      sb.append(", maxSize=").append(maxSize);
      sb.append(", timeout=").append(timeout);
      sb.append("]");
      return sb.toString();
   }

   public void merge(PoolConfigMetaData override, PoolConfigMetaData original)
   {
      if(original != null)
      {
         if(original.maxSize != null)
            maxSize = original.maxSize;
         if(original.timeout != null)
            timeout = original.timeout;
         if(original.value != null)
            value = original.value;
      }
      
      if(override != null)
      {
         if(override.maxSize != null)
            maxSize = override.maxSize;
         if(override.timeout != null)
            timeout = override.timeout;
         if(override.value != null)
            value = override.value;
      }
   }
}
