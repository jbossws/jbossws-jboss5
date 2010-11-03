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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptionGroup;

/**
 * The taglib spec metadata
 * Locations that must be scanned for .tld files are:
 * - All paths in a WAR under WEB-INF, except WEB-INF/lib and WEB-INF/classes
 * - All paths under META-INF in JARs
 * @author Remy Maucherat
 * @version $Revision: 70996 $
 */
public class TldMetaData extends NamedMetaDataWithDescriptionGroup
{
   private static final long serialVersionUID = 1;

   private String dtdPublicId;
   private String dtdSystemId;
   private String version;
   private String tlibVersion;
   private String jspVersion;
   private ValidatorMetaData validator;
   private List<ListenerMetaData> listeners;
   private List<TagMetaData> tags;
   private List<TagFileMetaData> tagFiles;
   private List<FunctionMetaData> functions;

   /**
    * Callback for the DTD information
    * @param root
    * @param publicId
    * @param systemId
    */
   @XmlTransient
   public void setDTD(String root, String publicId, String systemId)
   {
      this.dtdPublicId = publicId;
      this.dtdSystemId = systemId;
   }
   /**
    * Get the DTD public id if one was seen
    * @return the value of the web.xml dtd public id
    */
   @XmlTransient
   public String getDtdPublicId()
   {
      return dtdPublicId;
   }
   /**
    * Get the DTD system id if one was seen
    * @return the value of the web.xml dtd system id
    */
   @XmlTransient
   public String getDtdSystemId()
   {
      return dtdSystemId;
   }

   public String getVersion()
   {
      return version;
   }
   @XmlAttribute
   public void setVersion(String version)
   {
      this.version = version;
   }

   @XmlTransient
   public boolean is12()
   {
      return dtdPublicId != null && dtdPublicId.equals("-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.2//EN");
   }
   @XmlTransient
   public boolean is20()
   {
      return version != null && version.equals("2.0");
   }
   @XmlTransient
   public boolean is21()
   {
      return version != null && version.equals("2.1");
   }

   public String getUri()
   {
      return getName();
   }
   public void setUri(String uri)
   {
      super.setName(uri);
   }

   public String getTlibVersion()
   {
      return tlibVersion;
   }
   public void setTlibVersion(String tlibVersion)
   {
      this.tlibVersion = tlibVersion;
   }

   public String getJspVersion()
   {
      return jspVersion;
   }
   public void setJspVersion(String jspVersion)
   {
      this.jspVersion = jspVersion;
   }

   public ValidatorMetaData getValidator()
   {
      return validator;
   }
   public void setJspVersion(ValidatorMetaData validator)
   {
      this.validator = validator;
   }

   public List<TagMetaData> getTags()
   {
      return tags;
   }
   @XmlElement(name="tag")
   public void setTags(List<TagMetaData> tags)
   {
      this.tags = tags;
   }

   public List<TagFileMetaData> getTagFiles()
   {
      return tagFiles;
   }
   @XmlElement(name="tag-file")
   public void setTagFiles(List<TagFileMetaData> tagFiles)
   {
      this.tagFiles = tagFiles;
   }

   public List<FunctionMetaData> getFunctions()
   {
      return functions;
   }
   @XmlElement(name="function")
   public void setFunctions(List<FunctionMetaData> functions)
   {
      this.functions = functions;
   }

   public List<ListenerMetaData> getListeners()
   {
      return listeners;
   }
   @XmlElement(name="listener")
   public void setListeners(List<ListenerMetaData> listeners)
   {
      this.listeners = listeners;
   }

}
