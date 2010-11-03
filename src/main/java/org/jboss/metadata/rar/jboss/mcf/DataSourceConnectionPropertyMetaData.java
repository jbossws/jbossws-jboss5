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
package org.jboss.metadata.rar.jboss.mcf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * A DataSourceConnectionPropertyMetaData.
 * 
 * @author <a href="weston.price@jboss.org">Weston Price</a>
 * @author Jeff Zhang
 * @version $Revision$
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DataSourceConnectionPropertyMetaData
   implements java.io.Serializable
{
   private static final long serialVersionUID = 1444129514410015366L;
   
   protected String name;
   
   protected String value;

   public String getName()
   {
      return name;
   }
   
   @XmlAttribute(name="name")
   public void setName(String name)
   {
      this.name = name;
   }
   
   public String getValue()
   {
      return value;
   }
   @XmlValue
   public void setValue(String value)
   {
      this.value = value;
   }
   
   public String toString()
   {
      return "[" + name + "=" + value + "]";
   }
}
