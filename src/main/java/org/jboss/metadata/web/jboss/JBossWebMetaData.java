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
package org.jboss.metadata.web.jboss;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.jboss.metadata.common.jboss.WebserviceDescriptionsMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.jboss.RunAsIdentityMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBLocalReferencesMetaData;
import org.jboss.metadata.javaee.spec.EJBReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.EmptyMetaData;
import org.jboss.metadata.javaee.spec.Environment;
import org.jboss.metadata.javaee.spec.EnvironmentEntriesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;
import org.jboss.metadata.javaee.spec.JavaEEMetaDataConstants;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferenceMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferencesMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;
import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceContextReferencesMetaData;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.metadata.javaee.spec.RunAsMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefsMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;
import org.jboss.metadata.javaee.support.AbstractMappedMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptionGroup;
import org.jboss.metadata.web.spec.ErrorPageMetaData;
import org.jboss.metadata.web.spec.FilterMappingMetaData;
import org.jboss.metadata.web.spec.FiltersMetaData;
import org.jboss.metadata.web.spec.JspConfigMetaData;
import org.jboss.metadata.web.spec.ListenerMetaData;
import org.jboss.metadata.web.spec.LocaleEncodingsMetaData;
import org.jboss.metadata.web.spec.LoginConfigMetaData;
import org.jboss.metadata.web.spec.MimeMappingMetaData;
import org.jboss.metadata.web.spec.SecurityConstraintMetaData;
import org.jboss.metadata.web.spec.ServletMappingMetaData;
import org.jboss.metadata.web.spec.ServletMetaData;
import org.jboss.metadata.web.spec.ServletsMetaData;
import org.jboss.metadata.web.spec.SessionConfigMetaData;
import org.jboss.metadata.web.spec.Web25MetaData;
import org.jboss.metadata.web.spec.WebMetaData;
import org.jboss.metadata.web.spec.WelcomeFileListMetaData;

/**
 * The combined web.xml/jboss-web.xml metadata
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class JBossWebMetaData extends IdMetaDataImplWithDescriptionGroup
{
   private static final long serialVersionUID = 1;

   // Spec information
   private String servletVersion;
   private EmptyMetaData distributable;
   private boolean metadataComplete;
   private List<ParamValueMetaData> contextParams;
   private SessionConfigMetaData sessionConfig;
   private FiltersMetaData filters;
   private List<FilterMappingMetaData> filterMappings;
   private List<ErrorPageMetaData> errorPages;
   private JspConfigMetaData jspConfig;
   private List<ListenerMetaData> listeners;
   private LoginConfigMetaData loginConfig;
   private List<MimeMappingMetaData> mimeMappings;
   private List<ServletMappingMetaData> servletMappings;
   private List<SecurityConstraintMetaData> securityContraints;
   private WelcomeFileListMetaData welcomeFileList;
   private LocaleEncodingsMetaData localEncodings;

   // JBoss extended information
   private String dtdPublicId;
   private String dtdSystemId;
   private String version;
   private String contextRoot;
   private String alternativeDD;
   private String securityDomain;
   private String jaccContextID;
   /** The loader repository */
   private ClassLoadingMetaData classLoading;
   /** A list of extra dependencies to wait on */
   private List<String> depends;
   /** */
   private Map<String, RunAsIdentityMetaData> runAsIdentity = new HashMap<String, RunAsIdentityMetaData>();
   private SecurityRolesMetaData securityRoles = new SecurityRolesMetaData();
   /** The servlets */
   private JBossServletsMetaData servlets = new JBossServletsMetaData();
   /** The message destinations */
   private MessageDestinationsMetaData messageDestinations = new MessageDestinationsMetaData();
   /** The environment */
   private JBossEnvironmentRefsGroupMetaData jndiEnvironmentRefsGroup;
   /** The web app virtual host list */
   private List<String> virtualHosts;
   private boolean flushOnSessionInvalidation;
   private boolean useSessionCookies;
   private ReplicationConfig replicationConfig;
   private PassivationConfig passivationConfig;
   private WebserviceDescriptionsMetaData webserviceDescriptions = new WebserviceDescriptionsMetaData();
   private Boolean jaccAllStoreRole;

   /** The web context class loader used to create the java:comp context */
   @Deprecated
   private transient ClassLoader encLoader;
   /** The web context class loader, used to create the ws4ee service endpoint */
   @Deprecated
   private transient ClassLoader cxtLoader;
   /**
    * this is really a hack for new injection code  so that we can reparse web.xml/jbossweb.xml for JavaEE 5 injections
    * todo remove this when we clean up
    */
   private HashMap arbitraryMetadata = new HashMap();

   /**
    * The maximum number of active sessions allowed, or -1 for no limit
    */
   private Integer maxActiveSessions = null;

   /** Should the context use session cookies or use default */
   private int sessionCookies = SESSION_COOKIES_DEFAULT;

   public static final int SESSION_COOKIES_DEFAULT = 0;
   public static final int SESSION_COOKIES_ENABLED = 1;
   public static final int SESSION_COOKIES_DISABLED = 2;

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
      // Set the version based on the public id
      if (dtdPublicId != null && dtdPublicId.contains("3.0"))
         setVersion("3.0");
      else if (dtdPublicId != null && dtdPublicId.contains("3.2"))
         setVersion("3.2");
      else if (dtdPublicId != null && dtdPublicId.contains("2.4"))
         setVersion("4.0");
      else if (dtdPublicId != null && dtdPublicId.contains("4.2"))
         setVersion("4.2");
      else if (dtdPublicId != null && dtdPublicId.contains("5.0"))
         setVersion("5.0");
      else if(dtdSystemId != null && dtdSystemId.contains("3_0"))
         setVersion("3.0");
      else if(dtdSystemId != null && dtdSystemId.contains("3_2"))
         setVersion("3.2");
      else if(dtdSystemId != null && dtdSystemId.contains("4_0"))
         setVersion("4.0");
      else if(dtdSystemId != null && dtdSystemId.contains("4_2"))
         setVersion("4.2");
      else if(dtdSystemId != null && dtdSystemId.contains("5_0"))
         setVersion("5.0");
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
   public void setVersion(String version)
   {
      this.version = version;
   }
   public String getServletVersion()
   {
      return servletVersion;
   }
   public void setServletVersion(String servletVersion)
   {
      this.servletVersion = servletVersion;
   }


   /**
    * Is this a servlet 2.3 version application
    * @return true if this is a javaee 2.3 version application
    */
   @XmlTransient
   public boolean is23()
   {
      return servletVersion != null && servletVersion.equals(JavaEEMetaDataConstants.J2EE_13_WEB); 
   }
   @XmlTransient
   public boolean is24()
   {
      return servletVersion != null && servletVersion.equals("2.4");
   }
   @XmlTransient
   public boolean is25()
   {
      return servletVersion != null && servletVersion.equals("2.5");
   }

   
   public boolean isMetadataComplete()
   {
      return metadataComplete;
   }
   public void setMetadataComplete(boolean metadataComplete)
   {
      this.metadataComplete = metadataComplete;
   }

   public EmptyMetaData getDistributable()
   {
      return distributable;
   }
   public void setDistributable(EmptyMetaData distributable)
   {
      this.distributable = distributable;
   }
   public SessionConfigMetaData getSessionConfig()
   {
      return sessionConfig;
   }
   public void setSessionConfig(SessionConfigMetaData sessionConfig)
   {
      this.sessionConfig = sessionConfig;
   }
   public List<ParamValueMetaData> getContextParams()
   {
      return contextParams;
   }
   @XmlElement(name="context-param")
   public void setContextParams(List<ParamValueMetaData> params)
   {
      this.contextParams = params;
   }

   public FiltersMetaData getFilters()
   {
      return filters;
   }
   @XmlElement(name="filter")
   public void setFilters(FiltersMetaData filters)
   {
      this.filters = filters;
   }

   public List<FilterMappingMetaData> getFilterMappings()
   {
      return filterMappings;
   }
   @XmlElement(name="filter-mapping")
   public void setFilterMappings(List<FilterMappingMetaData> filterMappings)
   {
      this.filterMappings = filterMappings;
   }

   public List<ErrorPageMetaData> getErrorPages()
   {
      return errorPages;
   }
   @XmlElement(name="error-page")
   public void setErrorPages(List<ErrorPageMetaData> errorPages)
   {
      this.errorPages = errorPages;
   }

   public JspConfigMetaData getJspConfig()
   {
      return jspConfig;
   }
   public void setJspConfig(JspConfigMetaData jspConfig)
   {
      this.jspConfig = jspConfig;
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

   public LocaleEncodingsMetaData getLocalEncodings()
   {
      return localEncodings;
   }
   @XmlElement(name="locale-encoding-mapping-list")
   public void setLocalEncodings(LocaleEncodingsMetaData localEncodings)
   {
      this.localEncodings = localEncodings;
   }

   public LoginConfigMetaData getLoginConfig()
   {
      return loginConfig;
   }
   public void setLoginConfig(LoginConfigMetaData loginConfig)
   {
      this.loginConfig = loginConfig;
   }

   public List<MimeMappingMetaData> getMimeMappings()
   {
      return mimeMappings;
   }
   @XmlElement(name="mime-mapping")
   public void setMimeMappings(List<MimeMappingMetaData> mimeMappings)
   {
      this.mimeMappings = mimeMappings;
   }

   public JBossServletMetaData getServletByName(String name)
   {
      JBossServletMetaData servlet = null;
      if(servlets != null)
         servlet = servlets.get(name);
      return servlet;
   }
   public JBossServletsMetaData getServlets()
   {
      return servlets;
   }
   @XmlElement(name="servlet")
   public void setServlets(JBossServletsMetaData servlets)
   {
      this.servlets = servlets;
   }

   public List<ServletMappingMetaData> getServletMappings()
   {
      return servletMappings;
   }
   @XmlElement(name="servlet-mapping")
   public void setServletMappings(List<ServletMappingMetaData> servletMappings)
   {
      this.servletMappings = servletMappings;
   }

   public List<SecurityConstraintMetaData> getSecurityContraints()
   {
      return securityContraints;
   }
   @XmlElement(name="security-constraint")
   public void setSecurityContraints(List<SecurityConstraintMetaData> securityContraints)
   {
      this.securityContraints = securityContraints;
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

   public WelcomeFileListMetaData getWelcomeFileList()
   {
      return welcomeFileList;
   }
   public void setWelcomeFileList(WelcomeFileListMetaData welcomeFileList)
   {
      this.welcomeFileList = welcomeFileList;
   }

   public EJBLocalReferenceMetaData getEjbLocalReferenceByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getEjbLocalReferences());
   }

   public EJBLocalReferencesMetaData getEjbLocalReferences()
   {
      if (jndiEnvironmentRefsGroup != null)
         return jndiEnvironmentRefsGroup.getEjbLocalReferences();
      return null;
   }

   public EJBReferenceMetaData getEjbReferenceByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getEjbReferences());
   }

   public EJBReferencesMetaData getEjbReferences()
   {
      if (jndiEnvironmentRefsGroup != null)
         return jndiEnvironmentRefsGroup.getEjbReferences();
      return null;
   }

   public EnvironmentEntriesMetaData getEnvironmentEntries()
   {
      if (jndiEnvironmentRefsGroup != null)
         return jndiEnvironmentRefsGroup.getEnvironmentEntries();
      return null;
   }

   public EnvironmentEntryMetaData getEnvironmentEntryByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getEnvironmentEntries());
   }

   public MessageDestinationReferenceMetaData getMessageDestinationReferenceByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getMessageDestinationReferences());
   }

   public MessageDestinationReferencesMetaData getMessageDestinationReferences()
   {
      if (jndiEnvironmentRefsGroup != null)
         return jndiEnvironmentRefsGroup.getMessageDestinationReferences();
      return null;
   }

   public PersistenceContextReferenceMetaData getPersistenceContextReferenceByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getPersistenceContextRefs());
   }

   public PersistenceContextReferencesMetaData getPersistenceContextRefs()
   {
      if (jndiEnvironmentRefsGroup != null)
         return jndiEnvironmentRefsGroup.getPersistenceContextRefs();
      return null;
   }

   public PersistenceUnitReferenceMetaData getPersistenceUnitReferenceByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getPersistenceUnitRefs());
   }

   public PersistenceUnitReferencesMetaData getPersistenceUnitRefs()
   {
      if (jndiEnvironmentRefsGroup != null)
         return jndiEnvironmentRefsGroup.getPersistenceUnitRefs();
      return null;
   }

   public LifecycleCallbacksMetaData getPostConstructs()
   {
      if (jndiEnvironmentRefsGroup != null)
         return jndiEnvironmentRefsGroup.getPostConstructs();
      return null;
   }

   public LifecycleCallbacksMetaData getPreDestroys()
   {
      if (jndiEnvironmentRefsGroup != null)
         return jndiEnvironmentRefsGroup.getPreDestroys();
      return null;
   }

   public ResourceEnvironmentReferenceMetaData getResourceEnvironmentReferenceByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getResourceEnvironmentReferences());
   }

   public ResourceEnvironmentReferencesMetaData getResourceEnvironmentReferences()
   {
      if (jndiEnvironmentRefsGroup != null)
         return jndiEnvironmentRefsGroup.getResourceEnvironmentReferences();
      return null;
   }

   public ResourceReferenceMetaData getResourceReferenceByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getResourceReferences());
   }

   public ResourceReferencesMetaData getResourceReferences()
   {
      if (jndiEnvironmentRefsGroup != null)
         return jndiEnvironmentRefsGroup.getResourceReferences();
      return null;
   }

   public ServiceReferenceMetaData getServiceReferenceByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getServiceReferences());
   }

   public ServiceReferencesMetaData getServiceReferences()
   {
      if (jndiEnvironmentRefsGroup != null)
         return jndiEnvironmentRefsGroup.getServiceReferences();
      return null;
   }

   public MessageDestinationsMetaData getMessageDestinations()
   {
      return messageDestinations;
   }
   @XmlElement(name="message-destination")
   public void setMessageDestinations(MessageDestinationsMetaData messageDestinations)
   {
      this.messageDestinations = messageDestinations;
   }

   public String getAlternativeDD()
   {
      return alternativeDD;
   }
   public void setAlternativeDD(String alternativeDD)
   {
      this.alternativeDD = alternativeDD;
   }
   public String getContextRoot()
   {
      return contextRoot;
   }
   public void setContextRoot(String contextRoot)
   {
      this.contextRoot = contextRoot;
   }

   public List<String> getDepends()
   {
      return depends;
   }
   public ClassLoader getENCLoader()
   {
      return encLoader;
   }
   @XmlTransient
   public void setENCLoader(ClassLoader encLoader)
   {
      this.encLoader = encLoader;
   }

   public ClassLoader getContextLoader()
   {
      return cxtLoader;
   }
   @XmlTransient
   public void setContextLoader(ClassLoader cxtLoader)
   {
      this.cxtLoader = cxtLoader;
   }

   public void setDepends(List<String> depends)
   {
      this.depends = depends;
   }

   public ClassLoadingMetaData getClassLoading()
   {
      return classLoading;
   }

   public void setClassLoading(ClassLoadingMetaData classLoading)
   {
      this.classLoading = classLoading;
   }
   
   public String getJaccContextID()
   {
      return jaccContextID;
   }
   public void setJaccContextID(String jaccContextID)
   {
      this.jaccContextID = jaccContextID;
   }
   @XmlTransient
   public String getPublicID()
   {
      return this.getDtdPublicId();
   }
   public String getSecurityDomain()
   {
      return securityDomain;
   }
   public void setSecurityDomain(String securityDomain)
   {
      if(securityDomain == null)
         throw new IllegalArgumentException("securityDomain is null");
      this.securityDomain = securityDomain.trim();
   }

   public HashMap getArbitraryMetadata()
   {
      return arbitraryMetadata;
   }
   public void setArbitraryMetadata(HashMap arbitraryMetadata)
   {
      this.arbitraryMetadata = arbitraryMetadata;
   }
   public boolean isFlushOnSessionInvalidation()
   {
      return flushOnSessionInvalidation;
   }
   public void setFlushOnSessionInvalidation(boolean flushOnSessionInvalidation)
   {
      this.flushOnSessionInvalidation = flushOnSessionInvalidation;
   }
   public Integer getMaxActiveSessions()
   {
      return maxActiveSessions;
   }
   public void setMaxActiveSessions(Integer maxActiveSessions)
   {
      this.maxActiveSessions = maxActiveSessions;
   }
   public PassivationConfig getPassivationConfig()
   {
      return passivationConfig;
   }
   public void setPassivationConfig(PassivationConfig passivationConfig)
   {
      this.passivationConfig = passivationConfig;
   }
   public ReplicationConfig getReplicationConfig()
   {
      return replicationConfig;
   }
   public void setReplicationConfig(ReplicationConfig replicationConfig)
   {
      this.replicationConfig = replicationConfig;
   }

   public boolean isUseSessionCookies()
   {
      return useSessionCookies;
   }
   public void setUseSessionCookies(boolean useSessionCookies)
   {
      this.useSessionCookies = useSessionCookies;
   }

   public int getSessionCookies()
   {
      return this.sessionCookies;
   }
   public void setSessionCookies(int sessionCookies)
   {
      this.sessionCookies = sessionCookies;
   }

   public List<String> getVirtualHosts()
   {
      return virtualHosts;
   }
   @XmlElement(name="virtual-host")
   public void setVirtualHosts(List<String> virtualHosts)
   {
      this.virtualHosts = virtualHosts;
   }

   public WebserviceDescriptionsMetaData getWebserviceDescriptions()
   {
      return webserviceDescriptions;
   }
   @XmlElement(name="webservice-description")
   public void setWebserviceDescriptions(
         WebserviceDescriptionsMetaData webserviceDescriptions)
   {
      this.webserviceDescriptions = webserviceDescriptions;
   }

   /**
    * Get the security-role names from the web.xml descriptor
    * @return Set<String> of the security-role names from the web.xml
    */
   @XmlTransient
   public Set<String> getSecurityRoleNames()
   {
      return new HashSet<String>(securityRoles.keySet());
   }
   /**
    * Get the servlet/security-role-refs
    * @param servletName
    * @return
    */
   @XmlTransient
   public SecurityRoleRefsMetaData getSecurityRoleRefs(String servletName)
   {
      SecurityRoleRefsMetaData refs = null;
      if(getServlets() != null)
      {
         ServletMetaData servlet = getServlets().get(servletName);
         if (servlet != null)
         {
            refs = servlet.getSecurityRoleRefs();
         }
      }
      return refs;
   }

   public Map<String, Set<String>> getPrincipalVersusRolesMap()
   {
      Map<String, Set<String>> principalRolesMap = null;
      
      for(SecurityRoleMetaData srm : securityRoles)
      {
         String rolename = srm.getRoleName();
         if(principalRolesMap == null)
            principalRolesMap = new HashMap<String, Set<String>>();
         if(srm.getPrincipals() != null)
         for(String pr : srm.getPrincipals())
         {
            Set<String> roleset = (Set<String>)principalRolesMap.get(pr);
            if(roleset == null)
               roleset = new HashSet<String>();
            if(!roleset.contains(rolename))
               roleset.add(rolename);
            principalRolesMap.put(pr, roleset);
         } 
      } 
      return principalRolesMap;
   }

   /**
    * Get the jndiEnvironmentRefsGroup.
    * 
    * @return the jndiEnvironmentRefsGroup.
    */
   public Environment getJndiEnvironmentRefsGroup()
   {
      return jndiEnvironmentRefsGroup;
   }

   /**
    * Set the jndiEnvironmentRefsGroup.
    * 
    * @param jndiEnvironmentRefsGroup the jndiEnvironmentRefsGroup.
    * @throws IllegalArgumentException for a null jndiEnvironmentRefsGroup
    */
   @XmlElement(type=JBossEnvironmentRefsGroupMetaData.class)
   public void setJndiEnvironmentRefsGroup(Environment env)
   {
      if (env == null)
         throw new IllegalArgumentException("Null jndiEnvironmentRefsGroup");
      JBossEnvironmentRefsGroupMetaData jenv = (JBossEnvironmentRefsGroupMetaData) env;
      if(jndiEnvironmentRefsGroup != null)
         jndiEnvironmentRefsGroup.merge(jenv, null, null, "jboss-web.xml", "web.xml", false);
      else
         jndiEnvironmentRefsGroup = jenv;
   }
   
   public boolean isJaccAllStoreRole()
   {
      if(jaccAllStoreRole == null) return Boolean.FALSE;
      return jaccAllStoreRole;
   }

   @XmlElement(name = "jacc-star-role-allow")
   public void setJaccAllStoreRole(boolean isJaccAllStoreRole)
   {
      this.jaccAllStoreRole = Boolean.valueOf(isJaccAllStoreRole);
   }

   @XmlTransient
   public MessageDestinationMetaData getMessageDestination(String name)
   {
      return messageDestinations.get(name);
   }

   /**
    * Access the RunAsIdentity associated with the given servlet
    * @param servletName - the servlet-name from the web.xml
    * @return RunAsIdentity for the servet if one exists, null otherwise
    */
   @XmlTransient
   public RunAsIdentityMetaData getRunAsIdentity(String servletName)
   {
      RunAsIdentityMetaData identity = runAsIdentity.get(servletName);
      if (identity == null)
      {
         JBossServletsMetaData servlets = getServlets();
         if(servlets != null)
         {
            ServletMetaData servlet = servlets.get(servletName);
            if (servlet != null)
            {
               // Check for a web.xml run-as only specification
               synchronized (runAsIdentity)
               {
                  RunAsMetaData runAs = servlet.getRunAs();
                  if (runAs != null)
                  {
                     String roleName = runAs.getRoleName();
                     identity = new RunAsIdentityMetaData(roleName, null);
                     runAsIdentity.put(servletName, identity);
                  }
               }
            }
         }
      }
      return identity;
   }

   /**
    *
    * @return servlet/run-as <String servlet-name, RunAsIdentityMetaData>
    */
   public Map<String, RunAsIdentityMetaData> getRunAsIdentity()
   {
      return runAsIdentity;
   }

   /** The jboss-web.xml servlet/run-as <String servlet-name, SecurityRoleMetaData>
    */
   public void setRunAsIdentity(Map<String, RunAsIdentityMetaData> runAsIdentity)
   {
      this.runAsIdentity.clear();
      this.runAsIdentity.putAll(runAsIdentity);
   }

   
   public void mergeSecurityRoles(SecurityRolesMetaData roles)
   {
      if(this.securityRoles == null)
         securityRoles = roles;
      else
         securityRoles.merge(roles, null);
   }
   public void merge(JBossWebMetaData override, WebMetaData original)
   {
      this.merge(override, original, "jboss-web.xml", "web.xml", false);
   }
   public void merge(JBossWebMetaData override, WebMetaData original,
         String overrideFile, String overridenFile, boolean mustOverride)
   {
      super.merge(override, original);

      if(override != null && override.servletVersion!= null)
         setServletVersion(override.servletVersion);
      else if(original != null && original.getVersion() != null)
         setServletVersion(original.getVersion());

      if(override != null && override.distributable!= null)
         setDistributable(override.distributable);
      else if(original != null && original.getDistributable() != null)
         setDistributable(original.getDistributable());

      if(override != null && override.metadataComplete != false)
         setMetadataComplete(override.metadataComplete);
      else if(original != null && (original instanceof Web25MetaData) )
      {
         Web25MetaData web25MD = (Web25MetaData) original;
         setMetadataComplete(web25MD.isMetadataComplete());
      }

      if(override != null && override.contextParams!= null)
         setContextParams(override.contextParams);
      else if(original != null && original.getContextParams() != null)
         setContextParams(original.getContextParams());
      
      if(override != null && override.sessionConfig!= null)
         setSessionConfig(override.sessionConfig);
      else if(original != null && original.getSessionConfig() != null)
         setSessionConfig(original.getSessionConfig());
      
      if(override != null && override.filters!= null)
         setFilters(override.filters);
      else if(original != null && original.getFilters() != null)
         setFilters(original.getFilters());
      
      if(override != null && override.filterMappings!= null)
         setFilterMappings(override.filterMappings);
      else if(original != null && original.getFilterMappings() != null)
         setFilterMappings(original.getFilterMappings());
      
      if(override != null && override.errorPages!= null)
         setErrorPages(override.errorPages);
      else if(original != null && original.getErrorPages() != null)
         setErrorPages(original.getErrorPages());
      
      if(override != null && override.jspConfig!= null)
         setJspConfig(override.jspConfig);
      else if(original != null && original.getJspConfig() != null)
         setJspConfig(original.getJspConfig());
      
      if(override != null && override.listeners!= null)
         setListeners(override.listeners);
      else if(original != null && original.getListeners() != null)
         setListeners(original.getListeners());
      
      if(override != null && override.loginConfig!= null)
         setLoginConfig(override.loginConfig);
      else if(original != null && original.getLoginConfig() != null)
         setLoginConfig(original.getLoginConfig());
      
      if(override != null && override.mimeMappings!= null)
         setMimeMappings(override.mimeMappings);
      else if(original != null && original.getMimeMappings() != null)
         setMimeMappings(original.getMimeMappings());
      
      if(override != null && override.servletMappings!= null)
         setServletMappings(override.servletMappings);
      else if(original != null && original.getServletMappings() != null)
         setServletMappings(original.getServletMappings());
      
      if(override != null && override.securityContraints!= null)
         setSecurityContraints(override.securityContraints);
      else if(original != null && original.getSecurityContraints() != null)
         setSecurityContraints(original.getSecurityContraints());
      
      if(override != null && override.welcomeFileList!= null)
         setWelcomeFileList(override.welcomeFileList);
      else if(original != null && original.getWelcomeFileList() != null)
         setWelcomeFileList(original.getWelcomeFileList());
      
      if(override != null && override.localEncodings!= null)
         setLocalEncodings(override.localEncodings);
      else if(original != null && original.getLocalEncodings() != null)
         setLocalEncodings(original.getLocalEncodings());
      
      if(override != null && override.jaccAllStoreRole != null)
         this.jaccAllStoreRole = override.jaccAllStoreRole;
      
      if(override != null && override.dtdPublicId != null)
         this.dtdPublicId = override.dtdPublicId;
      
      if(override != null && override.dtdSystemId != null)
         this.dtdSystemId = override.dtdSystemId;
      
      if(override != null && override.version!= null)
         setVersion(override.version);
      else if(original != null && original.getVersion() != null)
         setVersion(original.getVersion());
      
      if(override != null && override.contextRoot!= null)
         setContextRoot(override.contextRoot);
      
      if(override != null && override.alternativeDD!= null)
         setAlternativeDD(override.alternativeDD);
      
      if(override != null && override.securityDomain!= null)
         setSecurityDomain(override.securityDomain);
      
      if(override != null && override.jaccContextID!= null)
         setJaccContextID(override.jaccContextID);
      
      if(override != null && override.classLoading!= null)
         setClassLoading(override.classLoading);
      
      if(override != null && override.depends!= null)
         setDepends(override.depends);
      
      if(override != null && override.runAsIdentity!= null)
         setRunAsIdentity(override.runAsIdentity);

      if(securityRoles == null)
         securityRoles = new SecurityRolesMetaData();
      SecurityRolesMetaData overrideRoles = null;
      SecurityRolesMetaData originalRoles = null;
      if(override != null)
         overrideRoles = override.getSecurityRoles();
      if(original != null)
         originalRoles = original.getSecurityRoles();
      securityRoles.merge(overrideRoles, originalRoles);

      JBossServletsMetaData soverride = null;
      ServletsMetaData soriginal = null;
      if(override != null)
         soverride = override.getServlets();
      if(original != null)
         soriginal = original.getServlets();
      servlets = JBossServletsMetaData.merge(soverride, soriginal);

      MessageDestinationsMetaData overrideMsgDests = null;
      MessageDestinationsMetaData originalMsgDests = null;
      if(override != null && override.messageDestinations!= null)
         overrideMsgDests = override.messageDestinations;
      if(original != null && original.getMessageDestinations() != null)
         originalMsgDests = original.getMessageDestinations();
      this.messageDestinations = MessageDestinationsMetaData.merge(overrideMsgDests,
            originalMsgDests, overridenFile, overrideFile);

      if(this.jndiEnvironmentRefsGroup == null)
         jndiEnvironmentRefsGroup = new JBossEnvironmentRefsGroupMetaData();
      Environment env = null;
      JBossEnvironmentRefsGroupMetaData jenv = null;
      if( override != null )
         jenv = override.jndiEnvironmentRefsGroup;
      if(original != null)
         env = original.getJndiEnvironmentRefsGroup();
      jndiEnvironmentRefsGroup.merge(jenv, env, null, overrideFile, overridenFile, mustOverride);

      if(override != null && override.virtualHosts!= null)
         setVirtualHosts(override.virtualHosts);
      
      if(override != null && override.flushOnSessionInvalidation)
         setFlushOnSessionInvalidation(override.flushOnSessionInvalidation);
      
      if(override != null && override.useSessionCookies)
         setUseSessionCookies(override.useSessionCookies);
      
      if(override != null && override.replicationConfig!= null)
         setReplicationConfig(override.replicationConfig);
      
      if(override != null && override.passivationConfig!= null)
         setPassivationConfig(override.passivationConfig);
      
      if(override != null && override.webserviceDescriptions!= null)
         setWebserviceDescriptions(override.webserviceDescriptions);
      
      if(override != null && override.arbitraryMetadata!= null)
         setArbitraryMetadata(override.arbitraryMetadata);
      
      if(override != null && override.maxActiveSessions != null)
         setMaxActiveSessions(override.maxActiveSessions);
      
      if(override != null && override.sessionCookies != -1)
         setSessionCookies(override.sessionCookies);

      // Update run-as indentity for a run-as-principal
      if(servlets != null)
      {
         for(JBossServletMetaData servlet : servlets)
         {
            String servletName = servlet.getServletName();
            String principalName = servlet.getRunAsPrincipal();
            // Get the web.xml run-as primary role
            String webXmlRunAs = null;
            if(servlet.getRunAs() != null)
               webXmlRunAs = servlet.getRunAs().getRoleName();
            if (principalName != null)
            {
               // Update the run-as indentity to use the principal name
               if (webXmlRunAs == null)
               {
                  //Needs to be merged from Annotations
                  webXmlRunAs = "PLACEHOLDER_FOR_ANNOTATION";
                  //throw new IllegalStateException("run-as-principal: " + principalName + " found in jboss-web.xml but there was no run-as in web.xml");
               }
               // See if there are any additional roles for this principal
               Set<String> extraRoles = securityRoles.getSecurityRoleNamesByPrincipal(principalName);
               RunAsIdentityMetaData runAsId = new RunAsIdentityMetaData(webXmlRunAs, principalName, extraRoles);
               runAsIdentity.put(servletName, runAsId);
            }
            else if (webXmlRunAs != null)
            {
               RunAsIdentityMetaData runAsId = new RunAsIdentityMetaData(webXmlRunAs, null);
               runAsIdentity.put(servletName, runAsId);
            }
         }
      }
   }
}
