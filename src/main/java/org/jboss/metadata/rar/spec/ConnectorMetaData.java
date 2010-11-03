/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata.rar.spec;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptionGroup;


/**
 * Connector meta data
 *
 * @author <a href="mailto:adrian@jboss.com">Adrian Brock</a>
 * @author Jeff Zhang
 * @version $Revision$
 */
public class ConnectorMetaData extends IdMetaDataImplWithDescriptionGroup
{
   private static final long serialVersionUID = 7047130842894140222L;

   private String dtdPublicId;
   private String dtdSystemId;
   /** The version */
   private String version;

   /** The vendor name */
   private String vendorName;

   /** The eis type */
   private String eisType;

   /** The resource adapter version */
   private String raVersion;

   /** The license */
   private LicenseMetaData lmd;
   
   private ResourceAdapterMetaData ra;

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
   /**
    * Get the connector version
    * 
    * @return the connector version
    */
   public String getVersion()
   {
      return version;
   }
   /**
    * Set the connector version
    * 
    * @param version the connector version
    */
   @XmlAttribute(required=true)
   public void setVersion(String version)
   {
      this.version = version;
   }

   /**
    * Is this a servlet 2.3 version application
    * @return true if this is a javaee 2.3 version application
    */
   @XmlTransient
   public boolean is10()
   {
      return dtdPublicId != null && dtdPublicId.equals("-//Sun Microsystems, Inc.//DTD Connector 1.0//EN"); 
   }
   @XmlTransient
   public boolean is15()
   {
      return version != null && version.equals("1.5");
   }
   @XmlTransient
   public boolean is16()
   {
      return version != null && version.equals("1.6");
   }

   /**
    * Get the vendor name
    * 
    * @return the vendor name
    */
   public String getVendorName()
   {
      return vendorName;
   }

   /**
    * Set the vendor name
    * 
    * @param vendorName the vendor name
    */
   @XmlElement(required=true)
   public void setVendorName(String vendorName)
   {
      this.vendorName = vendorName;
   }

   /**
    * Get the eis type
    * 
    * @return the eis type
    */
   public String getEISType()
   {
      return eisType;
   }

   /**
    * Set the eis Type
    * 
    * @param eisType the eis type
    */
   @XmlElement(name="eis-type", required=true)
   public void setEISType(String eisType)
   {
      this.eisType = eisType;
   }

   /**
    * Get the resource adapter version
    * 
    * @return the resource adapter version
    */
   public String getRAVersion()
   {
      return raVersion;
   }

   /**
    * Set the resource adapter version
    * 
    * @param version the resource adapter version
    */
   @XmlElement(name="resourceadapter-version", required=true)
   public void setRAVersion(String version)
   {
      this.raVersion = version;
   }

   /**
    * Get the license
    * 
    * @return the license
    */
   public LicenseMetaData getLicense()
   {
      return lmd;
   }
   /**
    * Get the license
    * 
    * @return the license
    */
   public void setLicense(LicenseMetaData lmd)
   {
      this.lmd = lmd;
   }
   
   @XmlElement(name="resourceadapter", required=true)
   public void setRa(ResourceAdapterMetaData ra) throws Exception{
      this.ra = ra;
   }
   public ResourceAdapterMetaData getRa() {
      return ra;
   }
      
   public String toString()
   {
      StringBuffer buffer = new StringBuffer();
      buffer.append("ConnectorMetaData").append('@');
      buffer.append(Integer.toHexString(System.identityHashCode(this)));
      buffer.append("[version=").append(version);
      buffer.append(" vendorName=").append(vendorName);
      buffer.append(" eisType=").append(eisType);
      buffer.append(" resourceAdapterVersion=").append(raVersion);
      buffer.append(" license=").append(lmd);
      buffer.append(" resourceadapter=").append(ra);
      buffer.append(']');
      return buffer.toString();
   }

}
