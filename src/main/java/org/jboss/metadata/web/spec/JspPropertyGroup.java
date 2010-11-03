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
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptionGroup;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
@XmlType(name="jsp-property-groupType", propOrder={"descriptionGroup", "urlPatterns", "elIgnored", "pageEncoding",
      "scriptingInvalid", "xml", "includePrelude", "includeCoda", "deferredSyntaxAllowedAsLiteral", "trimDirectiveWhitespaces"})
public class JspPropertyGroup extends IdMetaDataImplWithDescriptionGroup
{
   private static final long serialVersionUID = 1;

   private List<String> urlPatterns;
   private boolean scriptingInvalid;
   private boolean elIgnored;
   private boolean isXml;
   private boolean deferredSyntaxAllowedAsLiteral;
   private boolean trimDirectiveWhitespaces;
   private String pageEncoding;
   private List<String> includePrelude;
   private List<String> includeCoda;

   public boolean isDeferredSyntaxAllowedAsLiteral()
   {
      return deferredSyntaxAllowedAsLiteral;
   }
   public void setDeferredSyntaxAllowedAsLiteral(
         boolean deferredSyntaxAllowedAsLiteral)
   {
      this.deferredSyntaxAllowedAsLiteral = deferredSyntaxAllowedAsLiteral;
   }
   public boolean isElIgnored()
   {
      return elIgnored;
   }
   public void setElIgnored(boolean elIgnored)
   {
      this.elIgnored = elIgnored;
   }
   public List<String> getIncludeCoda()
   {
      return includeCoda;
   }
   public void setIncludeCoda(List<String> includeCoda)
   {
      this.includeCoda = includeCoda;
   }
   public List<String> getIncludePrelude()
   {
      return includePrelude;
   }
   public void setIncludePrelude(List<String> includePrelude)
   {
      this.includePrelude = includePrelude;
   }
   public boolean isXml()
   {
      return isXml;
   }
   @XmlElement(name="is-xml")
   public void setXml(boolean isXml)
   {
      this.isXml = isXml;
   }
   public String getPageEncoding()
   {
      return pageEncoding;
   }
   public void setPageEncoding(String pageEncoding)
   {
      this.pageEncoding = pageEncoding;
   }
   public boolean isScriptingInvalid()
   {
      return scriptingInvalid;
   }
   public void setScriptingInvalid(boolean scriptingInvalid)
   {
      this.scriptingInvalid = scriptingInvalid;
   }
   public boolean isTrimDirectiveWhitespaces()
   {
      return trimDirectiveWhitespaces;
   }
   public void setTrimDirectiveWhitespaces(boolean trimDirectiveWhitespaces)
   {
      this.trimDirectiveWhitespaces = trimDirectiveWhitespaces;
   }
   public List<String> getUrlPatterns()
   {
      return urlPatterns;
   }
   @XmlElement(name="url-pattern")
   public void setUrlPatterns(List<String> urlPatterns)
   {
      this.urlPatterns = urlPatterns;
   }
}
