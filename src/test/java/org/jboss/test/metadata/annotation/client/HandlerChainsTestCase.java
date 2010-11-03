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
package org.jboss.test.metadata.annotation.client;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import java.util.HashSet;

import org.jboss.metadata.annotation.creator.client.ApplicationClient5MetaDataCreator;
import org.jboss.metadata.annotation.creator.ws.WebServiceHandlerChainProcessor;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.client.spec.ApplicationClient5MetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceHandlerChainMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceHandlerChainsMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;
import org.jboss.test.metadata.annotation.client.handlerchains.AnnotatedClient;
import org.jboss.test.metadata.annotation.client.handlerchains.AnnotatedClientExternal;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;

/**
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public class HandlerChainsTestCase extends AbstractJavaEEMetaDataTest
{

   private static final String classMainName = "org.jboss.test.metadata.annotation.client.handlerchains.AnnotatedClient";
   
   AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
   
   public HandlerChainsTestCase(String name)
   {
      super(name);
   }

   public void testHandlerChainsProcessor()
   {
      ServiceReferenceMetaData metaData = new ServiceReferenceMetaData();
      
      WebServiceHandlerChainProcessor<Class<?>> processor = new WebServiceHandlerChainProcessor<Class<?>>(finder);
      processor.process(metaData, AnnotatedClient.class);
      
      ServiceReferenceHandlerChainsMetaData handlerChains = metaData.getHandlerChains();
      assertNotNull(handlerChains);
      assertNotNull(handlerChains.getHandlers());
      assertEquals(10, handlerChains.getHandlers().size());
      ServiceReferenceHandlerChainMetaData serviceHandlerChain = handlerChains.getHandlers().get(0);
      assertNotNull(serviceHandlerChain);
      assertEquals(" SOAP11ServerHandler ", serviceHandlerChain.getHandler().get(0).getHandlerName());
      assertEquals(" org.jboss.test.ws.jaxws.handlerscope.ProtocolHandler ", serviceHandlerChain.getHandler().get(0).getHandlerClass());
      
   }

   public void testAssembledHandlerChainsProcessors()
   {
      enableTrace("org.jboss.metadata.annotation.creator");
      
      Collection<Class<?>> classes = new HashSet<Class<?>>();
      classes.add(AnnotatedClient.class);
      ApplicationClient5MetaDataCreator creator = new ApplicationClient5MetaDataCreator(finder, classMainName);
      ApplicationClient5MetaData clientMD = creator.create(classes);
      
      ServiceReferencesMetaData serviceRefs = clientMD.getServiceReferences();
      assertNotNull(serviceRefs);
      assertEquals(4, serviceRefs.size());
      
      ServiceReferenceMetaData serviceRef = serviceRefs.get(AnnotatedClient.class.getName() +"/endpoint");
      assertNotNull(serviceRef);
      ServiceReferenceHandlerChainsMetaData handlerChains = serviceRef.getHandlerChains();
      assertNotNull(handlerChains);
      assertNotNull(handlerChains.getHandlers());
      assertEquals(10, handlerChains.getHandlers().size());
      ServiceReferenceHandlerChainMetaData serviceHandlerChain = handlerChains.getHandlers().get(0);
      assertNotNull(serviceHandlerChain);
      assertEquals(" SOAP11ServerHandler ", serviceHandlerChain.getHandler().get(0).getHandlerName());
      assertEquals(" org.jboss.test.ws.jaxws.handlerscope.ProtocolHandler ", serviceHandlerChain.getHandler().get(0).getHandlerClass());
      
      ServiceReferenceMetaData endpoint2 = serviceRefs.get(AnnotatedClient.class.getName() + "/endpoint2");
      assertNotNull(endpoint2);
      assertNotNull(endpoint2.getHandlerChains());
      assertNotNull(endpoint2.getHandlerChains().getHandlers());
      assertEquals(1, endpoint2.getHandlerChains().getHandlers().size());
      ServiceReferenceHandlerChainMetaData endpoint2HandlerChain = endpoint2.getHandlerChains().getHandlers().get(0);
      assertNotNull(endpoint2HandlerChain);
      assertEquals("ClientLogicalHandler", endpoint2HandlerChain.getHandler().get(0).getHandlerName());
      assertEquals("org.jboss.test.ws.ClientLogicalHandler", endpoint2HandlerChain.getHandler().get(0).getHandlerClass());
   }
   
   public void testHandlerChainWithExternalReference()
   {
      try
      {
         Collection<Class<?>> classes = new HashSet<Class<?>>();
         classes.add(AnnotatedClientExternal.class);
         ApplicationClient5MetaDataCreator creator = new ApplicationClient5MetaDataCreator(finder, AnnotatedClientExternal.class.getName());
         creator.create(classes);
         
         fail("@HandlerChain points to a non existing host.");
      }
      catch(Exception e)
      {
         // ok
      }
   }
}

