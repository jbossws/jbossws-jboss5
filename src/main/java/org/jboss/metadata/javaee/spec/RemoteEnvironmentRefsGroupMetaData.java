/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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
package org.jboss.metadata.javaee.spec;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.jboss.metadata.javaee.jboss.JBossServiceReferencesMetaData;
import org.jboss.metadata.javaee.support.AbstractMappedMetaData;
import org.jboss.xb.annotations.JBossXmlCollection;
import org.jboss.xb.annotations.JBossXmlModelGroup;

/**
 * References which are only available remote (for application clients).
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author <a href="carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision$
 */
@JBossXmlModelGroup(name="jndiEnvironmentRefsGroup",
      propOrder={
         "environmentEntries", 
         "ejbReferences",
         "serviceReferences",
         "resourceReferences", 
         "resourceEnvironmentReferences",
         "messageDestinationReferences",
         "persistenceUnitRefs",
         "postConstructs",
         "preDestroys"})
public class RemoteEnvironmentRefsGroupMetaData
   implements Serializable, RemoteEnvironment, MutableRemoteEnvironment
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 2L;

   /** The environment entries */
   private EnvironmentEntriesMetaData environmentEntries;

   /** @EJB references */
   private AnnotatedEJBReferencesMetaData annotatedEjbReferences;

   /** The ejb references */
   private EJBReferencesMetaData ejbReferences;

   /** The service references */
   private ServiceReferencesMetaData serviceReferences;

   /** The resource references */
   private ResourceReferencesMetaData resourceReferences;
   
   /** The resource environment references */
   private ResourceEnvironmentReferencesMetaData resourceEnvironmentReferences;
   
   /** The message destination references */
   private MessageDestinationReferencesMetaData messageDestinationReferences;
   
   /** The persistence unit  reference */
   private PersistenceUnitReferencesMetaData persistenceUnitRefs;

   /** The post construct methods */
   private LifecycleCallbacksMetaData postConstructs;
   
   /** The pre destroy methods */
   private LifecycleCallbacksMetaData preDestroys;
   
   /**
    * Create a new EnvironmentRefsGroupMetaData.
    */
   public RemoteEnvironmentRefsGroupMetaData()
   {
      // For serialization
   }

   private LifecycleCallbacksMetaData addAll(LifecycleCallbacksMetaData current, LifecycleCallbacksMetaData additions)
   {
      if(additions == null)
         return current;
      if(current == null)
         current = new LifecycleCallbacksMetaData();
      // Don't allow duplicates
      for(LifecycleCallbackMetaData lcmd : additions)
      {
         if(current.contains(lcmd) == false)
            current.add(lcmd);
      }
      return current;
   }

   /**
    * Get the environmentEntries.
    * 
    * @return the environmentEntries.
    */
   public EnvironmentEntriesMetaData getEnvironmentEntries()
   {
      return environmentEntries;
   }

   /**
    * Set the environmentEntries.
    * 
    * @param environmentEntries the environmentEntries.
    * @throws IllegalArgumentException for a null environmentEntries
    */
   @XmlElement(name="env-entry")
   public void setEnvironmentEntries(EnvironmentEntriesMetaData environmentEntries)
   {
      if (environmentEntries == null)
         throw new IllegalArgumentException("Null environmentEntries");
      this.environmentEntries = environmentEntries;
   }

   /**
    * Get the ejbReferences.
    * 
    * @return the ejbReferences.
    */
   public EJBReferencesMetaData getEjbReferences()
   {
      return ejbReferences;
   }

   /**
    * Set the ejbReferences.
    * 
    * @param ejbReferences the ejbReferences.
    * @throws IllegalArgumentException for a null ejbReferences
    */
   @XmlElement(name="ejb-ref")
   public void setEjbReferences(EJBReferencesMetaData ejbReferences)
   {
      if (ejbReferences == null)
         throw new IllegalArgumentException("Null ejbReferences");
      this.ejbReferences = ejbReferences;
   }

   @XmlTransient
   public AnnotatedEJBReferencesMetaData getAnnotatedEjbReferences()
   {
      return annotatedEjbReferences;
   }
   @XmlTransient
   public void setAnnotatedEjbReferences(AnnotatedEJBReferencesMetaData annotatedEjbReferences)
   {
      if (annotatedEjbReferences == null)
         throw new IllegalArgumentException("Null annotatedEjbReferences");
      this.annotatedEjbReferences = annotatedEjbReferences;
   }

   /**
    * Get the serviceReferences.
    * 
    * @return the serviceReferences.
    */
   public ServiceReferencesMetaData getServiceReferences()
   {
      return serviceReferences;
   }
   
   /**
    * Set the serviceReferences.
    * 
    * @param serviceReferences the serviceReferences.
    * @throws IllegalArgumentException for a null serviceReferences
    */
   @JBossXmlCollection(type=ServiceReferencesMetaData.class)
   @XmlElement(name="service-ref", type=ServiceReferenceMetaData.class)
   public void setServiceReferences(ServiceReferencesMetaData serviceReferences)
   {
      this.serviceReferences = (ServiceReferencesMetaData)serviceReferences;
   }

   /**
    * Get the resourceReferences.
    * 
    * @return the resourceReferences.
    */
   public ResourceReferencesMetaData getResourceReferences()
   {
      return resourceReferences;
   }

   /**
    * Set the resourceReferences.
    * 
    * @param resourceReferences the resourceReferences.
    * @throws IllegalArgumentException for a null resourceReferences
    */
   @XmlElement(name="resource-ref")
   public void setResourceReferences(ResourceReferencesMetaData resourceReferences)
   {
      if (resourceReferences == null)
         throw new IllegalArgumentException("Null resourceReferences");
      this.resourceReferences = resourceReferences;
   }

   /**
    * Get the resourceEnvironmentReferences.
    * 
    * @return the resourceEnvironmentReferences.
    */
   public ResourceEnvironmentReferencesMetaData getResourceEnvironmentReferences()
   {
      return resourceEnvironmentReferences;
   }

   /**
    * Set the resourceEnvironmentReferences.
    * 
    * @param resourceEnvironmentReferences the resourceEnvironmentReferences.
    * @throws IllegalArgumentException for a null resourceEnvironmentReferences
    */
   @XmlElement(name="resource-env-ref")
   public void setResourceEnvironmentReferences(ResourceEnvironmentReferencesMetaData resourceEnvironmentReferences)
   {
      if (resourceEnvironmentReferences == null)
         throw new IllegalArgumentException("Null resourceEnvironmentReferences");
      this.resourceEnvironmentReferences = resourceEnvironmentReferences;
   }

   /**
    * Get the messageDestinationReferences.
    * 
    * @return the messageDestinationReferences.
    */
   public MessageDestinationReferencesMetaData getMessageDestinationReferences()
   {
      return messageDestinationReferences;
   }

   /**
    * Set the messageDestinationReferences.
    * 
    * @param messageDestinationReferences the messageDestinationReferences.
    * @throws IllegalArgumentException for a null messageDestinationReferences
    */
   @XmlElement(name="message-destination-ref")
   public void setMessageDestinationReferences(MessageDestinationReferencesMetaData messageDestinationReferences)
   {
      if (messageDestinationReferences == null)
         throw new IllegalArgumentException("Null messageDestinationReferences");
      this.messageDestinationReferences = messageDestinationReferences;
   }

   /**
    * Get the postConstructs.
    * 
    * @return the postConstructs.
    */
   public LifecycleCallbacksMetaData getPostConstructs()
   {
      return postConstructs;
   }

   /**
    * Set the postConstructs.
    * 
    * @param postConstructs the postConstructs.
    * @throws IllegalArgumentException for a null postConstructs
    */
   //@SchemaProperty(name="post-construct", noInterceptor=true)
   @XmlElement(name="post-construct")
   public void setPostConstructs(LifecycleCallbacksMetaData postConstructs)
   {
      if (postConstructs == null)
         throw new IllegalArgumentException("Null postConstructs");
      this.postConstructs = postConstructs;
   }

   /**
    * Get the preDestroys.
    * 
    * @return the preDestroys.
    */
   public LifecycleCallbacksMetaData getPreDestroys()
   {
      return preDestroys;
   }

   /**
    * Set the preDestroys.
    * 
    * @param preDestroys the preDestroys.
    * @throws IllegalArgumentException for a null preDestroys
    */
   //@SchemaProperty(name="pre-destroy", noInterceptor=true)
   @XmlElement(name="pre-destroy")
   public void setPreDestroys(LifecycleCallbacksMetaData preDestroys)
   {
      if (preDestroys == null)
         throw new IllegalArgumentException("Null preDestroys");
      this.preDestroys = preDestroys;
   }

   /**
    * Get the persistenceUnitRefs.
    * 
    * @return the persistenceUnitRefs.
    */
   public PersistenceUnitReferencesMetaData getPersistenceUnitRefs()
   {
      return persistenceUnitRefs;
   }

   /**
    * Set the persistenceUnitRefs.
    * 
    * @param persistenceUnitRefs the persistenceUnitRefs.
    * @throws IllegalArgumentException for a null persistenceUnitRefs
    */
   @XmlElement(name="persistence-unit-ref")
   public void setPersistenceUnitRefs(PersistenceUnitReferencesMetaData persistenceUnitRefs)
   {
      if (persistenceUnitRefs == null)
         throw new IllegalArgumentException("Null persistenceUnitRefs");
      this.persistenceUnitRefs = persistenceUnitRefs;
   }

   public EJBReferenceMetaData getEjbReferenceByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getEjbReferences());
   }

   public EnvironmentEntryMetaData getEnvironmentEntryByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getEnvironmentEntries());
   }

   public MessageDestinationReferenceMetaData getMessageDestinationReferenceByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getMessageDestinationReferences());
   }

   public PersistenceUnitReferenceMetaData getPersistenceUnitReferenceByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getPersistenceUnitRefs());
   }

   public ResourceEnvironmentReferenceMetaData getResourceEnvironmentReferenceByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getResourceEnvironmentReferences());
   }

   public ResourceReferenceMetaData getResourceReferenceByName(String name)
   {
      return AbstractMappedMetaData.getByName(name, getResourceReferences());
   }
   public ServiceReferenceMetaData getServiceReferenceByName(String name)
   {
      ServiceReferencesMetaData srefs = this.getServiceReferences();
      return AbstractMappedMetaData.getByName(name, srefs);
   }

   public void merge(RemoteEnvironment jbossEnv, RemoteEnvironment specEnv,
         String overrideFile, String overridenFile, boolean mustOverride)
   {
      AnnotatedEJBReferencesMetaData annotatedEjbRefs = null;
      EJBReferencesMetaData ejbRefs = null;
      EJBReferencesMetaData jbossEjbRefs = null;
      ServiceReferencesMetaData serviceRefs = null;
      ServiceReferencesMetaData jbossServiceRefs = null;
      ResourceReferencesMetaData resRefs = null;
      ResourceReferencesMetaData jbossResRefs = null;
      ResourceEnvironmentReferencesMetaData resEnvRefs = null;
      ResourceEnvironmentReferencesMetaData jbossResEnvRefs = null;
      MessageDestinationReferencesMetaData messageDestinationRefs = null;
      MessageDestinationReferencesMetaData jbossMessageDestinationRefs = null;
      PersistenceUnitReferencesMetaData persistenceUnitRefs = null;
      PersistenceUnitReferencesMetaData jbossPersistenceUnitRefs = null;
//      LifecycleCallbacksMetaData postConstructs = null;
//      LifecycleCallbacksMetaData preDestroys = null;
      
      if(jbossEnv != null)
      {
         postConstructs = addAll(postConstructs, jbossEnv.getPostConstructs());
         preDestroys = addAll(preDestroys, jbossEnv.getPreDestroys());
      }
      if(specEnv != null)
      {
         postConstructs = addAll(postConstructs, specEnv.getPostConstructs());
         preDestroys = addAll(preDestroys, specEnv.getPreDestroys());
      }
      
      if (specEnv != null)
      {
         annotatedEjbRefs = specEnv.getAnnotatedEjbReferences();
         ejbRefs = specEnv.getEjbReferences();
         serviceRefs = specEnv.getServiceReferences();
         resRefs = specEnv.getResourceReferences();
         resEnvRefs = specEnv.getResourceEnvironmentReferences();
         messageDestinationRefs = specEnv.getMessageDestinationReferences();
         persistenceUnitRefs = specEnv.getPersistenceUnitRefs();
      }
      
      if (jbossEnv != null)
      { 
         jbossEjbRefs = jbossEnv.getEjbReferences();
         jbossServiceRefs = jbossEnv.getServiceReferences();
         jbossResRefs = jbossEnv.getResourceReferences();
         jbossResEnvRefs = jbossEnv.getResourceEnvironmentReferences();
         jbossMessageDestinationRefs = jbossEnv.getMessageDestinationReferences();
         jbossPersistenceUnitRefs = jbossEnv.getPersistenceUnitRefs();
      }
      else
      {
         // Merge into this
         jbossEjbRefs = getEjbReferences();
         jbossServiceRefs = getServiceReferences();
         jbossResRefs = getResourceReferences();
         jbossResEnvRefs = getResourceEnvironmentReferences();
         jbossMessageDestinationRefs = getMessageDestinationReferences();
         jbossPersistenceUnitRefs = getPersistenceUnitRefs();
      }


      EJBReferencesMetaData mergedEjbRefs = EJBReferencesMetaData.merge(jbossEjbRefs, ejbRefs, overrideFile, overridenFile, mustOverride);
      if (mergedEjbRefs != null)
         setEjbReferences(mergedEjbRefs);
      
      ServiceReferencesMetaData mergedServiceRefs = JBossServiceReferencesMetaData.merge(jbossServiceRefs, serviceRefs, overrideFile, overridenFile);
      if (mergedServiceRefs != null)
         setServiceReferences(mergedServiceRefs);

      ResourceReferencesMetaData mergedResRefs = ResourceReferencesMetaData.merge(jbossResRefs, resRefs, overrideFile, overridenFile, mustOverride);
      if (mergedResRefs != null)
         setResourceReferences(mergedResRefs);

      ResourceEnvironmentReferencesMetaData mergedResEnvRefs = ResourceEnvironmentReferencesMetaData.merge(jbossResEnvRefs, resEnvRefs, overrideFile, overridenFile);
      if (mergedResEnvRefs != null)
         setResourceEnvironmentReferences(mergedResEnvRefs);

      MessageDestinationReferencesMetaData mergedMessageDestinationRefs = MessageDestinationReferencesMetaData.merge(jbossMessageDestinationRefs, messageDestinationRefs, "", "", mustOverride);
      if (mergedMessageDestinationRefs != null)
         setMessageDestinationReferences(mergedMessageDestinationRefs);

      if(specEnv != null)
      {
         EnvironmentEntriesMetaData envEntries = specEnv.getEnvironmentEntries();
         if (envEntries != null)
         {
            EnvironmentEntriesMetaData thisEnv = this.getEnvironmentEntries();
            if(thisEnv != null)
               thisEnv.addAll(envEntries);
            else
               this.setEnvironmentEntries(envEntries);
         }
      }
      if(jbossEnv != null)
      {
         EnvironmentEntriesMetaData envEntries = jbossEnv.getEnvironmentEntries();
         if (envEntries != null)
         {
            if(environmentEntries == null)
               environmentEntries = new EnvironmentEntriesMetaData();
            // Merge the entries with existing entries
            for(EnvironmentEntryMetaData entry : envEntries)
            {
               EnvironmentEntryMetaData thisEntry = environmentEntries.get(entry.getEnvEntryName());
               if(thisEntry != null)
                  thisEntry.merge(entry, null);
               else
                  environmentEntries.add(entry);
            }
         }
      }
      
      if (persistenceUnitRefs != null || jbossPersistenceUnitRefs != null)
      {
         if(this.persistenceUnitRefs == null)
            this.persistenceUnitRefs = new PersistenceUnitReferencesMetaData();
         this.persistenceUnitRefs.merge(jbossPersistenceUnitRefs, persistenceUnitRefs);
      }

      // Fill the annotated refs with the xml descriptor
      AnnotatedEJBReferencesMetaData annotatedRefs = AnnotatedEJBReferencesMetaData.merge(this.getEjbReferences(), annotatedEjbRefs);
      if(annotatedRefs != null)
         this.setAnnotatedEjbReferences(annotatedRefs);
   }
}
