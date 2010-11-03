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
package org.jboss.metadata.ear.jboss;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.jboss.metadata.common.jboss.LoaderRepositoryMetaData;
import org.jboss.metadata.ear.spec.Ear5xMetaData;
import org.jboss.metadata.ear.spec.EarMetaData;
import org.jboss.metadata.ear.spec.ModuleMetaData;
import org.jboss.metadata.ear.spec.ModulesMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptionGroup;

/**
 * The jboss application metadata
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class JBossAppMetaData extends IdMetaDataImplWithDescriptionGroup
{
   private static final long serialVersionUID = 1;
   private String dtdPublicId;
   private String dtdSystemId;
   /** jboss application version */
   private String version;
   /** The default application security domain */
   private String securityDomain;
   /** The loader repository */
   private LoaderRepositoryMetaData loaderRepository;
   /** The unauthenticated principal */
   private String unauthenticatedPrincipal;
   private ModulesMetaData modules;
   /** The security roles */
   private SecurityRolesMetaData securityRoles;
   private String libraryDirectory;
   private String jmxName;

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
      // Set the version based on
      if (dtdPublicId != null && dtdPublicId.contains("3.0"))
         setVersion("3.0");
      else if (dtdPublicId != null && dtdPublicId.contains("3.2"))
         setVersion("3.2");
      else if (dtdPublicId != null && dtdPublicId.contains("4.0"))
         setVersion("4.0");
      else if (dtdPublicId != null && dtdPublicId.contains("4.2"))
         setVersion("4.2");
      else if (dtdPublicId != null && dtdPublicId.contains("5.0"))
         setVersion("5.0");
      
      if(getVersion() == null)
      {
         if(dtdSystemId != null && dtdSystemId.contains("jboss-app_3_0.dtd"))
            setVersion("3.0");
         else if(dtdSystemId != null && dtdSystemId.contains("jboss-app_3_2.dtd"))
            setVersion("3.2");
         else if(dtdSystemId != null && dtdSystemId.contains("jboss-app_4_0.dtd"))
            setVersion("4.0");
         else if(dtdSystemId != null && dtdSystemId.contains("jboss-app_4_2.dtd"))
            setVersion("4.2");
         else if(dtdSystemId != null && dtdSystemId.contains("jboss-app_5_0.dtd"))
            setVersion("5.0");
      }
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

   public LoaderRepositoryMetaData getLoaderRepository()
   {
      return loaderRepository;
   }
   public void setLoaderRepository(LoaderRepositoryMetaData loaderRepository)
   {
      this.loaderRepository = loaderRepository;
   }
   public String getSecurityDomain()
   {
      return securityDomain;
   }
   public void setSecurityDomain(String securityDomain)
   {
      this.securityDomain = securityDomain;
   }
   public SecurityRolesMetaData getSecurityRoles()
   {
      return securityRoles;
   }
   @XmlElement(name="security-role")
   public void setSecurityRoles(SecurityRolesMetaData securityRoles)
   {
      this.securityRoles = securityRoles;
   }
   public String getUnauthenticatedPrincipal()
   {
      return unauthenticatedPrincipal;
   }
   public void setUnauthenticatedPrincipal(String unauthenticatedPrincipal)
   {
      this.unauthenticatedPrincipal = unauthenticatedPrincipal;
   }
   public String getVersion()
   {
      return version;
   }
   public void setVersion(String version)
   {
      this.version = version;
   }

   public String getLibraryDirectory()
   {
      return libraryDirectory;
   }
   public void setLibraryDirectory(String libraryDirectory)
   {
      this.libraryDirectory = libraryDirectory;
   }
   
   public String getJmxName()
   {
      return jmxName;
   }
   public void setJmxName(String jmxName)
   {
      this.jmxName = jmxName;
   }

   /**
    * Get the application module information
    * @return the list of application modules
    */
   public ModulesMetaData getModules()
   {
      return modules;
   }
   /**
    * Set the application module information
    * @param module - the list of application modules
    */
   @XmlElement(name="module")
   public void setModules(ModulesMetaData modules)
   {
      this.modules = modules;
   }

   /**
    * This element is from 4.x versions of jboss-app which is now not used in jboss-5
    * @param moduleOrder
    */
   @XmlElement
   public void setModuleOrder(String moduleOrder)
   {
   }
   
   @XmlTransient
   public synchronized ModuleMetaData getModule(String name)
   {
      return modules.get(name);
   }

   public void merge(JBossAppMetaData override, EarMetaData original)
   {
      super.merge(override, original);
      Ear5xMetaData original5x = null;
      if(original instanceof Ear5xMetaData)
         original5x = (Ear5xMetaData) original;

      if(override != null)
      {
         if(override.dtdPublicId != null)
            this.dtdPublicId = override.dtdPublicId;
         if(override.dtdSystemId != null)
            this.dtdSystemId = override.dtdSystemId;
         if(override.version != null)
            this.version = override.version;
         if(override.securityDomain != null)
            setSecurityDomain(override.securityDomain);
         if(override.loaderRepository != null)
            setLoaderRepository(override.loaderRepository);
         if(override.unauthenticatedPrincipal != null)
            setUnauthenticatedPrincipal(override.unauthenticatedPrincipal);
         if(override.unauthenticatedPrincipal != null)
            setUnauthenticatedPrincipal(override.unauthenticatedPrincipal);
         if(override.libraryDirectory != null)
            setLibraryDirectory(override.libraryDirectory);
         else if(original5x != null && original5x.getLibraryDirectory() != null)
            setLibraryDirectory(original5x.getLibraryDirectory());
         if(override.jmxName != null)
            setJmxName(override.jmxName);
      }
      else if(original5x != null && original5x.getLibraryDirectory() != null)
         setLibraryDirectory(original5x.getLibraryDirectory());

      if(modules == null)
         modules = new ModulesMetaData();
      ModulesMetaData overrideModules = null;
      ModulesMetaData originalModules = null;
      if(override != null)
         overrideModules = override.getModules();
      if(original != null)
         originalModules = original.getModules();
      modules.merge(overrideModules, originalModules);

      SecurityRolesMetaData securityRolesMetaData = null;
      SecurityRolesMetaData overrideSecurityRolesMetaData = null;
      if (original != null)
         securityRolesMetaData = original.getSecurityRoles();
      if(override != null)
         overrideSecurityRolesMetaData = override.getSecurityRoles();
      if(securityRoles == null)
         securityRoles = new SecurityRolesMetaData();
      securityRoles.merge(overrideSecurityRolesMetaData, securityRolesMetaData);
   }
}
