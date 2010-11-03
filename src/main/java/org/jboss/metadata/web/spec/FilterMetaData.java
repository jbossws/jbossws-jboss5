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
package org.jboss.metadata.web.spec;

import java.util.List;

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptionGroup;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
@XmlType(name="filterType", propOrder={"descriptionGroup", "filterName", "filterClass", "initParam"})
public class FilterMetaData extends NamedMetaDataWithDescriptionGroup
{
   private static final long serialVersionUID = 1;

   /** The filter class */
   private String filterClass;
   /** The filter init-params */
   private List<ParamValueMetaData> initParam;

   public String getFilterName()
   {
      return getName();
   }
   public void setFilterName(String name)
   {
      super.setName(name);
   }

   public String getFilterClass()
   {
      return filterClass;
   }
   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
   public void setFilterClass(String filterClass)
   {
      this.filterClass = filterClass;
   }

   public List<ParamValueMetaData> getInitParam()
   {
      return initParam;
   }

   public void setInitParam(List<ParamValueMetaData> initParam)
   {
      this.initParam = initParam;
   }

}
