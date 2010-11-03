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
package org.jboss.metadata.javaee.jboss;

// $Id$

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.xb.annotations.JBossXmlConstants;
import org.jboss.xb.annotations.JBossXmlType;

/**
 * A remapping of ParamValueMetaData to support prop-name, prop-value elements
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
@XmlType(name="stub-propertyType", propOrder={"propName", "propValue"})
@JBossXmlType(modelGroup=JBossXmlConstants.MODEL_GROUP_UNORDERED_SEQUENCE)
public class StubPropertyMetaData extends ParamValueMetaData
{
   private static final long serialVersionUID = 1;

   public String getPropName()
   {
      return super.getParamName();
   }

   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   public void setPropName(String name)
   {
      super.setParamName(name);
   }

   public String getPropValue()
   {
      return super.getParamValue();
   }

   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   public void setPropValue(String value)
   {
      super.setParamValue(value);
   }
}
