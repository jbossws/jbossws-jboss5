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

import javax.xml.bind.annotation.XmlElement;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptionGroup;

/**
 * taglib/tag metadata
 * 
 * @author Remy Maucherat
 * @version $Revision: 75201 $
 */
public class TagMetaData extends NamedMetaDataWithDescriptionGroup
{
   private static final long serialVersionUID = 1;
   
   private String tagClass;
   private String teiClass;
   private BodyContentType bodyContent;
   private List<VariableMetaData> variables;
   private List<AttributeMetaData> attributes;
   private boolean dynamicAttributes = false;

   public String getTagClass()
   {
      return tagClass;
   }
   public void setTagClass(String tagClass)
   {
      this.tagClass = tagClass;
   }
   @XmlElement(name="tagclass")
   public void setTagClass0(String tagClass)
   {
       setTagClass(tagClass);
   }

   public String getTeiClass()
   {
      return teiClass;
   }
   public void setTeiClass(String teiClass)
   {
      this.teiClass = teiClass;
   }
   @XmlElement(name="teiclass")
   public void setTeiClass0(String teiClass)
   {
       setTeiClass(teiClass);
   }

   public BodyContentType getBodyContent()
   {
      return bodyContent;
   }
   public void setBodyContent(BodyContentType bodyContent)
   {
      this.bodyContent = bodyContent;
   }
   @XmlElement(name="bodycontent")
   public void setBodyContent0(BodyContentType bodyContent)
   {
       setBodyContent(bodyContent);
   }

   public boolean getDynamicAttributes()
   {
      return dynamicAttributes;
   }
   public void setDynamicAttributes(boolean dynamicAttributes)
   {
      this.dynamicAttributes = dynamicAttributes;
   }

   public List<VariableMetaData> getVariables()
   {
      return variables;
   }
   @XmlElement(name="variable")
   public void setVariables(List<VariableMetaData> variables)
   {
      this.variables = variables;
   }

   public List<AttributeMetaData> getAttributes()
   {
      return attributes;
   }
   @XmlElement(name="attribute")
   public void setAttributes(List<AttributeMetaData> attributes)
   {
      this.attributes = attributes;
   }

   public String toString()
   {
      StringBuilder tmp = new StringBuilder("ServletMetaData(id=");
      tmp.append(getId());
      tmp.append(",tagClass=");
      tmp.append(tagClass);
      tmp.append(",teiClass=");
      tmp.append(teiClass);
      tmp.append(",dynamicAttributes=");
      tmp.append(dynamicAttributes);
      tmp.append(",bodyContent=");
      tmp.append(bodyContent);
      tmp.append(')');
      return tmp.toString();
   }
}
