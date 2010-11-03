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
package org.jboss.metadata.javaee.jboss;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.spec.PortComponentRef;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;

/**
 <xsd:sequence>
 <xsd:element name="service-ref-name" type="xsd:string"/>
 <xsd:element name="service-impl-class" type="xsd:string" minOccurs="0" maxOccurs="1"/>
 <xsd:element name="service-qname" type="xsd:string" minOccurs="0" maxOccurs="1"/>
 <xsd:element name="config-name" type="xsd:string" minOccurs="0" maxOccurs="1"/>
 <xsd:element name="config-file" type="xsd:string" minOccurs="0" maxOccurs="1"/>
 <xsd:element name="handler-chain" type="xsd:string" minOccurs="0" maxOccurs="1"/>
 <xsd:element name="port-component-ref" type="jboss:port-component-ref-type" minOccurs="0" maxOccurs="unbounded"/>
 <xsd:element name="wsdl-override" type="xsd:string" minOccurs="0" maxOccurs="1"/>
 </xsd:sequence>
 <xsd:attribute name="id" type="xsd:ID"/>
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
@XmlType(name = "service-refType", propOrder={"serviceRefName", "serviceClass",
      "serviceQname", "configName", "configFile", "handlerChain", "JBossPortComponentRef", "wsdlOverride"})
public class JBossServiceReferenceMetaData extends ServiceReferenceMetaData
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 5693673588576610322L;

   /** The service-impl-class */
   private String serviceClass;

   /** The config-name */
   private String configName;
   /** The config-file */
   private String configFile;

   /** The handler-chain */
   private String handlerChain;

   private List<JBossPortComponentRef> jbossPortComponentRef;

   /** The wsdl file override */
   private String wsdlOverride;

   /**
    * Create a new JBossServiceReferenceMetaData.
    */
   public JBossServiceReferenceMetaData()
   {
      // For serialization
   }

   /**
    * Get the serviceRefName.
    * 
    * @return the serviceRefName.
    */
   public String getServiceRefName()
   {
      return getName();
   }

   /**
    * Set the serviceRefName.
    * 
    * @param serviceRefName the serviceRefName.
    * @throws IllegalArgumentException for a null serviceRefName
    */
   @XmlElement(name = "service-ref-name")
   public void setServiceRefName(String serviceRefName)
   {
      setName(serviceRefName);
   }

   public String getConfigFile()
   {
      return configFile;
   }

   public void setConfigFile(String configFile)
   {
      this.configFile = configFile;
   }

   public String getConfigName()
   {
      return configName;
   }

   public void setConfigName(String configName)
   {
      this.configName = configName;
   }

   public String getHandlerChain()
   {
      return handlerChain;
   }

   public void setHandlerChain(String handlerChain)
   {
      this.handlerChain = handlerChain;
   }

   public String getServiceClass()
   {
      return serviceClass;
   }

   @XmlElement(name = "service-impl-class")
   public void setServiceClass(String serviceClass)
   {
      this.serviceClass = serviceClass;
   }

   public List<JBossPortComponentRef> getJBossPortComponentRef()
   {
      return jbossPortComponentRef;
   }

   @XmlElement(name = "port-component-ref", type = JBossPortComponentRef.class)
   public void setJBossPortComponentRef(List<JBossPortComponentRef> portComponentRef)
   {
      this.jbossPortComponentRef = (List<JBossPortComponentRef>)portComponentRef;
   }

   @Override
   public List<? extends PortComponentRef> getPortComponentRef()
   {
      return jbossPortComponentRef;
   }

   @XmlTransient
   @Override
   public void setPortComponentRef(List<? extends PortComponentRef> portComponentRef)
   {
      super.setPortComponentRef(portComponentRef);
   }
   
   public String getWsdlOverride()
   {
      return wsdlOverride;
   }

   public void setWsdlOverride(String wsdlOverride)
   {
      this.wsdlOverride = wsdlOverride;
   }

   public ServiceReferenceMetaData merge(ServiceReferenceMetaData original)
   {
      JBossServiceReferenceMetaData merged = new JBossServiceReferenceMetaData();
      merged.merge(this, original);
      return merged;
   }

   public JBossServiceReferenceMetaData merge(JBossServiceReferenceMetaData original)
   {
      JBossServiceReferenceMetaData merged = new JBossServiceReferenceMetaData();
      merged.merge(this, original);
      return merged;
   }

   /**
    * Merge the contents of override with original into this.
    * 
    * @param override data which overrides original
    * @param original the original data
    */
   public void merge(JBossServiceReferenceMetaData override, ServiceReferenceMetaData original)
   {
      super.merge(override, original);

      // TODO: how to merge portComponentRef
      if (original != null && original.getPortComponentRef() != null)
      {
         if (jbossPortComponentRef == null)
            jbossPortComponentRef = new ArrayList<JBossPortComponentRef>();
         for (PortComponentRef ref : original.getPortComponentRef())
         {
            JBossPortComponentRef jref = new JBossPortComponentRef();
            jref.merge(null, ref);
            jbossPortComponentRef.add(jref);
         }
      }
      if (override != null && override.getJBossPortComponentRef() != null)
      {
         if (jbossPortComponentRef == null)
            jbossPortComponentRef = new ArrayList<JBossPortComponentRef>();
         for (JBossPortComponentRef ref : override.getJBossPortComponentRef())
         {
            JBossPortComponentRef jref = null;
            boolean shouldAdd = true;
            //  TODO: there is no unique key so 
            for(JBossPortComponentRef ref2 : jbossPortComponentRef)
            {
               String sei = ref2.getServiceEndpointInterface();
               if(sei != null && sei.equals(ref.getServiceEndpointInterface()))
               {
                  jref = ref2;
                  shouldAdd = false;
                  break;
               }
            }
            if(jref == null)
               jref = new JBossPortComponentRef();
            jref.merge(null, ref);
            if(shouldAdd)
               jbossPortComponentRef.add(jref);
         }
      }

      if (override != null && override.getServiceClass() != null)
         setServiceClass(override.getServiceClass());
      if (override != null && override.getConfigName() != null)
         setConfigName(override.getConfigName());
      if (override != null && override.getConfigFile() != null)
         setConfigFile(override.getConfigFile());
      if (override != null && override.getHandlerChain() != null)
         setHandlerChain(override.getHandlerChain());
      if (override != null && override.getWsdlOverride() != null)
         setWsdlOverride(override.getWsdlOverride());
   }
}
