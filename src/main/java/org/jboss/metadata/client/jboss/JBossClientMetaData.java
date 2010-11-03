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
package org.jboss.metadata.client.jboss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.jboss.metadata.client.spec.ApplicationClientMetaData;
import org.jboss.metadata.ejb.jboss.JBossEnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.AnnotatedEJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.EJBReferenceMetaData;
import org.jboss.metadata.javaee.spec.EJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.Environment;
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
import org.jboss.metadata.javaee.support.IdMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImpl;
import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptionGroup;

/**
 * The jboss javaee client application metadata
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class JBossClientMetaData extends IdMetaDataImplWithDescriptionGroup
   implements RemoteEnvironment
{
   private static final long serialVersionUID = 1L;

   /** The jndi name for the client environment */
   private String jndiName;
   
   /** The environment */
   private JBossEnvironmentRefsGroupMetaData jndiEnvironmentRefsGroup;
   /** A list of extra dependencies to wait on */
   private List<String> depends;
   /** The legacy dtd public id */
   private String dtdPublicId;
   /** The legacy dtd system id */
   private String dtdSystemId;
   /** The version of the jboss client descriptor */
   private String version;
   /** The callback handler */
   private String callbackHandler;
   /** Whether the spec metadata is complete */
   private boolean metaDataComplete;
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
         if(dtdPublicId.contains("3.0"))
            setVersion("3.0");
         if(dtdPublicId.contains("3.2"))
            setVersion("3.2");
         if(dtdPublicId.contains("4.0"))
            setVersion("4.0");
         if(dtdPublicId.contains("4.2"))
            setVersion("4.2");
         if(dtdPublicId.contains("5.0"))
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

   
   public String getVersion()
   {
      return version;
   }
   @XmlAttribute(name="version")
   public void setVersion(String version)
   {
      this.version = version;
   }

   
   public boolean isMetadataComplete()
   {
      return metaDataComplete;
   }
   public void setMetadataComplete(boolean metaDataComplete)
   {
      this.metaDataComplete = metaDataComplete;
   }
   public String getCallbackHandler()
   {
      return callbackHandler;
   }
   public void setCallbackHandler(String callbackHandler)
   {
      this.callbackHandler = callbackHandler;
   }

   public List<String> getDepends()
   {
      return depends;
   }
   public void setDepends(List<String> depends)
   {
      this.depends = depends;
   }

   public String getJndiName()
   {
      return jndiName;
   }
   public void setJndiName(String jndiName)
   {
      this.jndiName = jndiName;
   }
   
   public Environment getJndiEnvironmentRefsGroup()
   {
      return jndiEnvironmentRefsGroup;
   }
   @XmlElement(type=JBossEnvironmentRefsGroupMetaData.class)
   public void setJndiEnvironmentRefsGroup(Environment jndiEnvironmentRefsGroup)
   {
      if(jndiEnvironmentRefsGroup == null)
         throw new IllegalArgumentException("jndiEnvironmentRefsGroup is null");
      this.jndiEnvironmentRefsGroup = (JBossEnvironmentRefsGroupMetaData) jndiEnvironmentRefsGroup;
   }

   // Convinence accessors
   
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
   public MessageDestinationsMetaData getMessageDestinations()
   {
      return messageDestinations;
   }
   public void setMessageDestinations(
         MessageDestinationsMetaData messageDestinations)
   {
      this.messageDestinations = messageDestinations;
   }
   @XmlTransient
   public EJBReferenceMetaData getEjbReferenceByName(String name)
   {
      if(jndiEnvironmentRefsGroup == null)
         return null;
      return jndiEnvironmentRefsGroup.getEjbReferenceByName(name);
   }

   @XmlTransient
   public EJBReferencesMetaData getEjbReferences()
   {
      if(jndiEnvironmentRefsGroup == null)
         return null;
      return jndiEnvironmentRefsGroup.getEjbReferences();
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

   @XmlTransient
   public EnvironmentEntriesMetaData getEnvironmentEntries()
   {
      if(jndiEnvironmentRefsGroup == null)
         return null;
      return jndiEnvironmentRefsGroup.getEnvironmentEntries();
   }

   @XmlTransient
   public EnvironmentEntryMetaData getEnvironmentEntryByName(String name)
   {
      if(jndiEnvironmentRefsGroup == null)
         return null;
      return jndiEnvironmentRefsGroup.getEnvironmentEntryByName(name);
   }

   @XmlTransient
   public MessageDestinationReferenceMetaData getMessageDestinationReferenceByName(String name)
   {
      if(jndiEnvironmentRefsGroup == null)
         return null;
      return jndiEnvironmentRefsGroup.getMessageDestinationReferenceByName(name);
   }

   @XmlTransient
   public MessageDestinationReferencesMetaData getMessageDestinationReferences()
   {
      if(jndiEnvironmentRefsGroup == null)
         return null;
      return jndiEnvironmentRefsGroup.getMessageDestinationReferences();
   }

   @XmlTransient
   public PersistenceUnitReferenceMetaData getPersistenceUnitReferenceByName(String name)
   {
      if(jndiEnvironmentRefsGroup == null)
         return null;
      return jndiEnvironmentRefsGroup.getPersistenceUnitReferenceByName(name);
   }

   @XmlTransient
   public PersistenceUnitReferencesMetaData getPersistenceUnitRefs()
   {
      if(jndiEnvironmentRefsGroup == null)
         return null;
      return jndiEnvironmentRefsGroup.getPersistenceUnitRefs();
   }

   @XmlTransient
   public LifecycleCallbacksMetaData getPostConstructs()
   {
      if(jndiEnvironmentRefsGroup == null)
         return null;
      return jndiEnvironmentRefsGroup.getPostConstructs();
   }

   @XmlTransient
   public LifecycleCallbacksMetaData getPreDestroys()
   {
      if(jndiEnvironmentRefsGroup == null)
         return null;
      return jndiEnvironmentRefsGroup.getPreDestroys();
   }

   @XmlTransient
   public ServiceReferenceMetaData getServiceReferenceByName(String name)
   {
      if(jndiEnvironmentRefsGroup == null)
         return null;
      return jndiEnvironmentRefsGroup.getServiceReferenceByName(name);
   }

   @XmlTransient
   public ServiceReferencesMetaData getServiceReferences()
   {
      if(jndiEnvironmentRefsGroup == null)
         return null;
      return jndiEnvironmentRefsGroup.getServiceReferences();
   }

   @XmlTransient
   public ResourceEnvironmentReferenceMetaData getResourceEnvironmentReferenceByName(String name)
   {
      if(jndiEnvironmentRefsGroup == null)
         return null;
      return jndiEnvironmentRefsGroup.getResourceEnvironmentReferenceByName(name);
   }

   @XmlTransient
   public ResourceEnvironmentReferencesMetaData getResourceEnvironmentReferences()
   {
      if(jndiEnvironmentRefsGroup == null)
         return null;
      return jndiEnvironmentRefsGroup.getResourceEnvironmentReferences();
   }

   @XmlTransient
   public ResourceReferenceMetaData getResourceReferenceByName(String name)
   {
      if(jndiEnvironmentRefsGroup == null)
         return null;
      return jndiEnvironmentRefsGroup.getResourceReferenceByName(name);
   }

   @XmlTransient
   public ResourceReferencesMetaData getResourceReferences()
   {
      if(jndiEnvironmentRefsGroup == null)
         return null;
      return jndiEnvironmentRefsGroup.getResourceReferences();
   }

   @Override
   public void merge(IdMetaData override, IdMetaData original)
   {
      throw new RuntimeException("wrong merge method called");
   }
   
   @Override
   public void merge(IdMetaDataImpl override, IdMetaDataImpl original)
   {
      throw new RuntimeException("wrong merge method called");
   }
   
   /**
    * Merge jboss + spec into this
    * @param jboss
    * @param spec
    */
   public void merge(JBossClientMetaData jboss, ApplicationClientMetaData spec, boolean mustOverride)
   {
      super.merge(jboss, spec);
      
      RemoteEnvironmentRefsGroupMetaData jbossEnv = null;
      RemoteEnvironmentRefsGroupMetaData specEnv = null;
      MessageDestinationsMetaData jbossMsgs = null;
      MessageDestinationsMetaData specMsgs = null;
      if(jboss != null)
      {
         if(jboss.depends != null)
            setDepends(jboss.depends);
         if(jboss.jndiName != null)
            this.setJndiName(jboss.jndiName);
         else if(jboss.getDescriptionGroup() != null && jboss.getDescriptionGroup().getDisplayName() != null)
            this.setJndiName(jboss.getDescriptionGroup().getDisplayName());
         if(jboss.dtdPublicId != null)
            this.dtdPublicId = jboss.dtdPublicId;
         if(jboss.dtdSystemId != null)
            this.dtdSystemId = jboss.dtdSystemId;
         if(jboss.version != null)
            this.version = jboss.version;
         jbossEnv = jboss.jndiEnvironmentRefsGroup;
         jbossMsgs = jboss.getMessageDestinations();
      }
      if(spec != null)
      {
         specEnv = spec.getJndiEnvironmentRefsGroup();
         specMsgs = spec.getMessageDestinations();
         if(jndiName == null && spec.getDescriptionGroup() != null && spec.getDescriptionGroup().getDisplayName() != null)
            setJndiName(spec.getDescriptionGroup().getDisplayName());
         metaDataComplete = spec.isMetadataComplete();
         specMsgs = spec.getMessageDestinations();
      }

      if(jboss != null && jboss.callbackHandler != null)
         this.setCallbackHandler(jboss.callbackHandler);
      else if(spec != null && spec.getCallbackHandler() != null)
         this.setCallbackHandler(spec.getCallbackHandler());

      if(jndiEnvironmentRefsGroup == null)
         jndiEnvironmentRefsGroup = new JBossEnvironmentRefsGroupMetaData();
      jndiEnvironmentRefsGroup.merge(jbossEnv, specEnv, "jboss-client.xml", "application-client.xml", false);

      // Get the merged resource-env-refs
      ResourceEnvironmentReferencesMetaData resEnvRefs = jndiEnvironmentRefsGroup.getResourceEnvironmentReferences();

      // Merge the message-destinations
      messageDestinations = MessageDestinationsMetaData.merge(jbossMsgs, specMsgs, "jboss-client.xml", "application-client.xml");

      /* Need to map message-destinations to resource-env-refs for legacy
       * descriptors that did not have message-destinations
      */
      if(messageDestinations != null && resEnvRefs != null)
      {
         for(MessageDestinationMetaData md : messageDestinations)
         {
            if(md.getMappedName() == null)
            {
               ResourceEnvironmentReferenceMetaData ref = resEnvRefs.get(md.getMessageDestinationName());
               if(ref != null)
                  md.setMappedName(ref.getMappedName());
            }
         }
      }

      /** A  HashMap<String, ArrayList<MessageDestinationReferenceMetaData>> of
       * message-destination-ref that resolve to a jndi-name via a message-destination
       * via a message-destination-link
       */
      HashMap<String, ArrayList<MessageDestinationReferenceMetaData>> resourceEnvReferenceLinks
         = new HashMap<String, ArrayList<MessageDestinationReferenceMetaData>>();
      /* Merge the message-destination-ref elements
      This is a bit convoluted because legacy jboss descriptors did not support
      message-destination-ref elements.
      A message-destination-ref is linked to a jndi-name either via
      the message-destination-ref/message-destination-ref-name mapping to
      a jboss resource-env-ref/resource-env-ref-name if there is no
      message-destination-link, or by the message-destination-link ->
      message-destination/message-destination-name mapping to a jboss
      resource-env-ref/resource-env-ref-name.
      */
      if(specEnv != null)
      {
         ResourceEnvironmentReferencesMetaData specEnvRefs = specEnv.getResourceEnvironmentReferences();
         MessageDestinationReferencesMetaData specMsgRefs = specEnv.getMessageDestinationReferences();
         MessageDestinationReferencesMetaData msgRefs = jndiEnvironmentRefsGroup.getMessageDestinationReferences();
         if(msgRefs == null)
         {
            msgRefs = new MessageDestinationReferencesMetaData();
            jndiEnvironmentRefsGroup.setMessageDestinationReferences(msgRefs);
         }
         if(specMsgRefs != null)
         for(MessageDestinationReferenceMetaData ref : specMsgRefs)
         {
            ref = (MessageDestinationReferenceMetaData)ref.clone();
            String link = ref.getLink();
            if (link != null)
            {
               ArrayList<MessageDestinationReferenceMetaData> linkedRefs = resourceEnvReferenceLinks.get(link);
               if (linkedRefs == null)
               {
                  linkedRefs = new ArrayList<MessageDestinationReferenceMetaData>();
                  resourceEnvReferenceLinks.put(link, linkedRefs);
               }
               linkedRefs.add(ref);
            }
            if(msgRefs.contains(ref) == false)
               msgRefs.add(ref);
            else
            {
               MessageDestinationReferenceMetaData existingRef = msgRefs.get(ref.getMessageDestinationRefName());
               existingRef.merge(null, ref);
            }
         }

         // Merge the spec resource-env-refs
         if(resEnvRefs != null && specEnvRefs != null)
         for(ResourceEnvironmentReferenceMetaData ref : resEnvRefs)
         {
            String resRefName = ref.getResourceEnvRefName();
            ResourceEnvironmentReferenceMetaData specRef = specEnvRefs.get(resRefName);
            if (specRef == null)
            {
               // Try the resourceEnvReferenceLinks
               ArrayList<MessageDestinationReferenceMetaData> linkedRefs = resourceEnvReferenceLinks.get(resRefName);
               if (linkedRefs != null)
               {
                  for(MessageDestinationReferenceMetaData mref : linkedRefs)
                  {
                     // Need to make sure this is the ref in the map
                     MessageDestinationReferenceMetaData existingRef = msgRefs.get(mref.getMessageDestinationRefName());
                     if(existingRef.getIgnoreDependency() != null)
                        ref.setIgnoreDependency(mref.getIgnoreDependency());
                     if(existingRef.getType() != null)
                        ref.setType(mref.getType());
                     existingRef.setMappedName(ref.getMappedName());
                  }
               }
               else if(msgRefs.containsKey(resRefName))
               {
                  MessageDestinationReferenceMetaData mref = msgRefs.get(resRefName);
                  mref.setMappedName(ref.getMappedName());                  
               }
               else
               {
                  throw new IllegalStateException("resource-env-ref " + resRefName + " found in jboss-client.xml but not in application-client.xml");
               }
            }
            else
            {
               // Merge the spec ref into the jboss ref
               ref.merge(null, specRef);
            }
         }
      }

   }
}
