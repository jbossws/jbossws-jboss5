/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
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
package org.jboss.metadata.serviceref;

import java.util.Iterator;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.Referenceable;
import javax.xml.namespace.QName;

import org.jboss.logging.Logger;
import org.jboss.metadata.javaee.jboss.CallPropertyMetaData;
import org.jboss.metadata.javaee.jboss.JBossPortComponentRef;
import org.jboss.metadata.javaee.jboss.JBossServiceReferenceMetaData;
import org.jboss.metadata.javaee.jboss.StubPropertyMetaData;
import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.javaee.spec.PortComponentRef;
import org.jboss.metadata.javaee.spec.ServiceReferenceHandlerChainMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceHandlerChainsMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceHandlerMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceHandlersMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.util.naming.Util;
import org.jboss.wsf.spi.SPIProvider;
import org.jboss.wsf.spi.SPIProviderResolver;
import org.jboss.wsf.spi.deployment.UnifiedVirtualFile;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedCallPropertyMetaData;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerChainMetaData;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerChainsMetaData;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedHandlerMetaData;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedInitParamMetaData;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedPortComponentRefMetaData;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedServiceRefMetaData;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedStubPropertyMetaData;
import org.jboss.wsf.spi.serviceref.ServiceRefHandler;
import org.jboss.wsf.spi.serviceref.ServiceRefHandlerFactory;

/**
 * Utility to bind service references to JNDI
 * 
 * @author Thomas.Diesler@jboss.org
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public final class ServiceReferenceHandler
{
   private static final Logger log = Logger.getLogger(ServiceReferenceHandler.class);

   private ServiceRefHandler delegate;

   public ServiceReferenceHandler()
   {
      if (delegate == null)
      {
         SPIProvider spiProvider = SPIProviderResolver.getInstance().getProvider();
         delegate = spiProvider.getSPI(ServiceRefHandlerFactory.class).getServiceRefHandler();
      }
   }

   public void bindServiceRef(Context encCtx, String encName, UnifiedVirtualFile vfsRoot, ClassLoader loader, ServiceReferenceMetaData sref) throws NamingException
   {
      if (!sref.isProcessed())
      {
         final UnifiedServiceRefMetaData spiRef = getUnifiedServiceRefMetaData(vfsRoot, sref);
         final Referenceable jndiReferenceable = delegate.createReferenceable(spiRef);
         final String jndiFullName = encCtx.getNameInNamespace() + "/" + encName;
         log.info("Binding service reference to [jndi=" + jndiFullName + "]");
         Util.bind(encCtx, encName, jndiReferenceable);
         sref.setProcessed(true);
      }
   }

   private UnifiedServiceRefMetaData getUnifiedServiceRefMetaData(UnifiedVirtualFile vfsRoot, ServiceReferenceMetaData sref)
   {
      UnifiedServiceRefMetaData result = new UnifiedServiceRefMetaData(vfsRoot);
      result.setServiceRefName(sref.getServiceRefName());
      result.setServiceRefType(sref.getServiceRefType());
      result.setServiceInterface(sref.getServiceInterface());
      result.setWsdlFile(sref.getWsdlFile());
      result.setMappingFile(sref.getJaxrpcMappingFile());
      result.setServiceQName(sref.getServiceQname());
      result.setHandlerChain(sref.getHandlerChain());

      List<? extends PortComponentRef> pcRefs = sref.getPortComponentRef();
      if (pcRefs != null)
      {
         for (PortComponentRef pcRef : pcRefs)
         {
            UnifiedPortComponentRefMetaData upcRef = getUnifiedPortComponentRefMetaData(result, pcRef);
            if (upcRef.getServiceEndpointInterface() != null || upcRef.getPortQName() != null)
               result.addPortComponentRef(upcRef);
            else
               log.warn("Ignore <port-component-ref> without <service-endpoint-interface> and <port-qname>: " + upcRef);
         }
      }

      ServiceReferenceHandlersMetaData srHandlers = sref.getHandlers();
      if (srHandlers != null)
      {
         Iterator<ServiceReferenceHandlerMetaData> it = srHandlers.iterator();
         while (it.hasNext())
         {
            ServiceReferenceHandlerMetaData srHandlerMetaData = it.next();
            UnifiedHandlerMetaData uHandlerMetaData = getUnifiedHandlerMetaData(srHandlerMetaData);
            result.addHandler(uHandlerMetaData);
         }
      }

      ServiceReferenceHandlerChainsMetaData srHandlerChains = sref.getHandlerChains();
      if (srHandlerChains != null)
      {
         UnifiedHandlerChainsMetaData uHandlerChains = new UnifiedHandlerChainsMetaData();
         List<ServiceReferenceHandlerChainMetaData> srHandlerChainList = srHandlerChains.getHandlers();
         for (ServiceReferenceHandlerChainMetaData srHandlerChain : srHandlerChainList)
         {
            UnifiedHandlerChainMetaData uHandlerChain = new UnifiedHandlerChainMetaData();
            uHandlerChain.setServiceNamePattern(srHandlerChain.getServiceNamePattern());
            uHandlerChain.setPortNamePattern(srHandlerChain.getPortNamePattern());
            uHandlerChain.setProtocolBindings(srHandlerChain.getProtocolBindings());
            List<ServiceReferenceHandlerMetaData> srHandlerChainHandlers = srHandlerChain.getHandler();
            Iterator<ServiceReferenceHandlerMetaData> it = srHandlerChainHandlers.iterator();
            while (it.hasNext())
            {
               ServiceReferenceHandlerMetaData srHandlerMetaData = it.next();
               UnifiedHandlerMetaData uHandlerMetaData = getUnifiedHandlerMetaData(srHandlerMetaData);
               uHandlerChain.addHandler(uHandlerMetaData);
            }
            uHandlerChains.addHandlerChain(uHandlerChain);
         }
         result.setHandlerChains(uHandlerChains);
      }

      if (sref instanceof JBossServiceReferenceMetaData)
      {
         JBossServiceReferenceMetaData jbRef = (JBossServiceReferenceMetaData)sref;
         if (jbRef.getServiceClass() != null)
         {
            result.setServiceImplClass(jbRef.getServiceClass());
         }
         result.setConfigName(jbRef.getConfigName());
         result.setConfigFile(jbRef.getConfigFile());
         result.setWsdlOverride(jbRef.getWsdlOverride());
         result.setHandlerChain(jbRef.getHandlerChain());
      }
      
      return result;
   }

   private UnifiedHandlerMetaData getUnifiedHandlerMetaData(ServiceReferenceHandlerMetaData srhmd)
   {
      UnifiedHandlerMetaData uhmd = new UnifiedHandlerMetaData();
      uhmd.setHandlerName(srhmd.getHandlerName());
      uhmd.setHandlerClass(srhmd.getHandlerClass());
      List<ParamValueMetaData> initParams = srhmd.getInitParam();
      if (initParams != null)
      {
         for (ParamValueMetaData initParam : initParams)
         {
            UnifiedInitParamMetaData param = new UnifiedInitParamMetaData();
            param.setParamName(initParam.getParamName());
            param.setParamValue(initParam.getParamValue());
            uhmd.addInitParam(param);
         }
      }
      List<QName> soapHeaders = srhmd.getSoapHeader();
      if (soapHeaders != null)
      {
         for (QName soapHeader : soapHeaders)
         {
            uhmd.addSoapHeader(soapHeader);
         }
      }
      List<String> soapRoles = srhmd.getSoapRole();
      if (soapRoles != null)
      {
         for (String soapRole : soapRoles)
         {
            uhmd.addSoapRole(soapRole);
         }
      }
      List<String> portNames = srhmd.getPortName();
      if (portNames != null)
      {
         for (String portName : portNames)
         {
            uhmd.addPortName(portName);
         }
      }
      return uhmd;
   }

   private UnifiedPortComponentRefMetaData getUnifiedPortComponentRefMetaData(UnifiedServiceRefMetaData usref, PortComponentRef pcref)
   {
      UnifiedPortComponentRefMetaData result = new UnifiedPortComponentRefMetaData(usref);
      result.setServiceEndpointInterface(pcref.getServiceEndpointInterface());
      result.setEnableMTOM(pcref.isEnableMtom());
      result.setPortComponentLink(pcref.getPortComponentLink());
      if (pcref instanceof JBossPortComponentRef)
      {
         JBossPortComponentRef jbpcref = (JBossPortComponentRef)pcref;
         result.setPortQName(jbpcref.getPortQname());
         result.setConfigName(jbpcref.getConfigName());
         result.setConfigFile(jbpcref.getConfigFile());
         List<StubPropertyMetaData> stubProps = jbpcref.getStubProperties();
         if (stubProps != null)
         {
            for (StubPropertyMetaData stubProp : stubProps)
            {
               UnifiedStubPropertyMetaData prop = new UnifiedStubPropertyMetaData();
               prop.setPropName(stubProp.getPropName());
               prop.setPropValue(stubProp.getPropValue());
               result.addStubProperty(prop);
            }
         }
         List<CallPropertyMetaData> callProps = jbpcref.getCallProperties();
         if (callProps != null)
         {
            for (CallPropertyMetaData callProp : callProps)
            {
               UnifiedCallPropertyMetaData prop = new UnifiedCallPropertyMetaData();
               prop.setPropName(callProp.getPropName());
               prop.setPropValue(callProp.getPropValue());
               result.addCallProperty(prop);
            }
         }
      }
      return result;
   }
}
