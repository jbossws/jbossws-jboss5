/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors as indicated
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
package org.jboss.metadata.client.spec;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.jboss.metadata.javaee.spec.AnnotatedEJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.EJBReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntriesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferenceMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferencesMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferenceMetaData;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferencesMetaData;
import org.jboss.metadata.javaee.spec.RemoteEnvironment;
import org.jboss.metadata.javaee.spec.RemoteEnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferencesMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;
import org.jboss.metadata.javaee.support.AbstractMappedMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptionGroup;

/**
 * The common application client meta data for all JavaEE and J2EE versions.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision$
 */
public class ApplicationClientMetaData extends IdMetaDataImplWithDescriptionGroup implements RemoteEnvironment
{
   private static final long serialVersionUID = 1;
   /** The environment */
   private RemoteEnvironmentRefsGroupMetaData jndiEnvironmentRefsGroup;
   
   private String dtdPublicId;
   private String dtdSystemId;
   /** The version of the client */
   private String version;
   /** The callback handler */
   private String callbackHandler;
   /** The metadata-complete attribute */
   private boolean metadataComplete;

   /** The message destinations */
   private MessageDestinationsMetaData messageDestinations;

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
      // Set the version from legacy public ids
      if(dtdPublicId != null)
      {
         if(dtdPublicId.contains("1.2"))
            setVersion("1.2");
         if(dtdPublicId.contains("1.3"))
            setVersion("1.3");
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

   
   public String getVersion()
   {
      return version;
   }
   public void setVersion(String version)
   {
      this.version = version;
   }

   
   public boolean isMetadataComplete()
   {
      return metadataComplete;
   }
   @XmlAttribute(name="metadata-complete")
   public void setMetadataComplete(boolean metadataComplete)
   {
      this.metadataComplete = metadataComplete;
   }

   public String getCallbackHandler()
   {
      return callbackHandler;
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
   // TODO?
   @XmlTransient
   public AnnotatedEJBReferencesMetaData getAnnotatedEjbReferences()
   {
      AnnotatedEJBReferencesMetaData refs = null;
      if(jndiEnvironmentRefsGroup != null)
         refs = jndiEnvironmentRefsGroup.getAnnotatedEjbReferences();
      return refs;
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

   public RemoteEnvironmentRefsGroupMetaData getJndiEnvironmentRefsGroup()
   {
      return jndiEnvironmentRefsGroup;
   }

   /**
    * Get a message destination
    * 
    * @param name the name of the destination
    * @return the destination or null if not found
    */
   public MessageDestinationMetaData getMessageDestinationByName(String name)
   {
      if (messageDestinations == null)
         return null;
      return messageDestinations.get(name);
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

   /**
    * Get the messageDestinations.
    * 
    * @return the messageDestinations.
    */
   public MessageDestinationsMetaData getMessageDestinations()
   {
      return messageDestinations;
   }

   public ResourceEnvironmentReferenceMetaData getResourceEnvironmentReferenceByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getResourceEnvironmentReferences());
   }

   public PersistenceUnitReferenceMetaData getPersistenceUnitReferenceByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getPersistenceUnitRefs());
   }
   
   public PersistenceUnitReferencesMetaData getPersistenceUnitRefs()
   {
      if(jndiEnvironmentRefsGroup != null)
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
   
   public ResourceEnvironmentReferencesMetaData getResourceEnvironmentReferences()
   {
      if (jndiEnvironmentRefsGroup != null)
         return jndiEnvironmentRefsGroup.getResourceEnvironmentReferences();
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

   public void setCallbackHandler(String callbackHandler)
   {
      this.callbackHandler = callbackHandler;
   }
   
   public void setJndiEnvironmentRefsGroup(RemoteEnvironmentRefsGroupMetaData jndiEnvironmentRefsGroup)
   {
      if (jndiEnvironmentRefsGroup == null)
         throw new IllegalArgumentException("Null jndiEnvironmentRefsGroup");
      this.jndiEnvironmentRefsGroup = jndiEnvironmentRefsGroup;
   }
   
   /**
    * Set the messageDestinations.
    * 
    * @param messageDestinations the messageDestinations.
    * @throws IllegalArgumentException for a null messageDestinations
    */
   @XmlElement(name="message-destination")
   public void setMessageDestinations(MessageDestinationsMetaData messageDestinations)
   {
      if (messageDestinations == null)
         throw new IllegalArgumentException("Null messageDestinations");
      this.messageDestinations = messageDestinations;
   }
}
