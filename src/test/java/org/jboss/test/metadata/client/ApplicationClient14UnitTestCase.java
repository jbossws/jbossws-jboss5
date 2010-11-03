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
package org.jboss.test.metadata.client;

import java.util.List;

import javax.xml.namespace.QName;

import org.jboss.metadata.client.jboss.JBossClientMetaData;
import org.jboss.metadata.client.spec.ApplicationClientMetaData;
import org.jboss.metadata.javaee.jboss.JBossPortComponentRef;
import org.jboss.metadata.javaee.jboss.JBossServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferenceMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationUsageType;
import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;
import org.jboss.metadata.javaee.spec.PortComponentRef;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;

/**
 * Test all entries of an application client 1.4 descriptor.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class ApplicationClient14UnitTestCase extends AbstractJavaEEEverythingTest
{

   public ApplicationClient14UnitTestCase(String name)
   {
      super(name);
   }

   public void testBasic() throws Exception
   {
      ApplicationClientMetaData appClientMetaData = unmarshal();
      assertEquals("1.4", appClientMetaData.getVersion());
      assertEquals("A sample application client descriptor", appClientMetaData.getDescriptionGroup().getDescription());
      assertEquals("J2EE Client Tests", appClientMetaData.getDescriptionGroup().getDisplayName());
      assertEquals("org.jboss.test.client.test.SystemPropertyCallbackHandler", appClientMetaData.getCallbackHandler());
      assertEquals(3, appClientMetaData.getEnvironmentEntries().size());
   }

   public void testBasicOverrides() throws Exception
   {
      ApplicationClientMetaData appClientMetaData = unmarshal("ApplicationClient14_testBasic.xml", ApplicationClientMetaData.class, null);
      JBossClientMetaData jbossClientMetaData = unmarshal("ApplicationClient14_testBasicJBoss.xml", JBossClientMetaData.class, null);
      JBossClientMetaData metaData = new JBossClientMetaData();
      metaData.merge(jbossClientMetaData, appClientMetaData, true);

      assertEquals("org.jboss.test.client.test.SystemPropertyCallbackHandler", metaData.getCallbackHandler());
      assertEquals("A sample application client descriptor", metaData.getDescriptionGroup().getDescription());
      assertEquals("J2EE Client Tests", metaData.getDescriptionGroup().getDisplayName());
      assertEquals("test-client", metaData.getJndiName());
      assertEquals("4.0", metaData.getVersion());

      // Validate the resource-refs
      ResourceReferenceMetaData res0 = metaData.getResourceReferenceByName("url/JBossHome");
      assertNotNull(res0);
      assertNull(res0.getJndiName());
      assertNull(res0.getMappedName());
      assertEquals("java.net.URL", res0.getType());
      assertTrue(res0.isContainerAuth());
      assertEquals("The JBoss Web Site HomePage", res0.getDescriptions().value()[0].value());
      assertEquals("http://www.jboss.org", res0.getResUrl());
      ResourceReferenceMetaData res1 = metaData.getResourceReferenceByName("url/IndirectURL");
      assertNotNull(res1);
      assertEquals("SomeWebSite", res1.getJndiName());
      assertEquals("SomeWebSite", res1.getMappedName());
      assertEquals("java.net.URL", res1.getType());
      assertTrue(res1.isContainerAuth());
      assertEquals("SomeWebSite HomePage", res1.getDescriptions().value()[0].value());
      assertNull(res1.getResUrl());
      // Validate the resource-env-refs
      ResourceEnvironmentReferenceMetaData res2 = metaData.getResourceEnvironmentReferenceByName("jms/aQueue");
      assertNotNull(res2);
      assertEquals("A test of the resource-env-ref tag", res2.getDescriptions().value()[0].value());
      assertEquals("jms/aQueue", res2.getResourceEnvRefName());
      assertEquals("javax.jms.Queue", res2.getType());
      assertEquals("queue/testQueue", res2.getJndiName());
      assertEquals("queue/testQueue", res2.getMappedName());
      // This is legacy behavior that should be dropped?
      ResourceEnvironmentReferenceMetaData res3 = metaData.getResourceEnvironmentReferenceByName("jms/anotherQueue");
      assertNotNull(res3);
      // Validate the message-destination-refs
      MessageDestinationReferenceMetaData msg1 = metaData.getMessageDestinationReferenceByName("jms/anotherQueue");
      assertNotNull(msg1);
      assertEquals("A message-destination-ref needing a jboss/resource-env-ref", msg1.getDescriptions().value()[0].value());
      assertEquals("jms/anotherQueue", msg1.getMessageDestinationRefName());
      assertEquals("javax.jms.Queue", msg1.getType());
      assertNull(msg1.getLink());
      assertEquals(MessageDestinationUsageType.Consumes, msg1.getMessageDestinationUsage());
      assertEquals("queue/A", msg1.getJndiName());
      assertEquals("queue/A", msg1.getMappedName());
      MessageDestinationReferenceMetaData msg2 = metaData.getMessageDestinationReferenceByName("jms/anotherQueue2");
      assertNotNull(msg2);
      assertNull(msg2.getDescriptions());
      assertEquals("jms/anotherQueue2", msg2.getMessageDestinationRefName());
      assertEquals("javax.jms.Queue", msg2.getType());
      assertEquals("TheOtherQueue", msg2.getLink());
      assertEquals(MessageDestinationUsageType.Consumes, msg2.getMessageDestinationUsage());
      assertEquals("queue/B", msg2.getJndiName());
      assertEquals("queue/B", msg2.getMappedName());
      // Validate message-destination
      MessageDestinationsMetaData msgDests = metaData.getMessageDestinations();
      assertEquals(1, msgDests.size());
      MessageDestinationMetaData md0 = msgDests.get("TheOtherQueue");
      assertEquals("Some msg destination needing a jboss/resource-env-ref", md0.getDescriptionGroup().getDescription());
      assertEquals("TheOtherQueue", md0.getMessageDestinationName());
      assertEquals("TheOtherQueue-id", md0.getId());
      assertEquals("queue/B", md0.getJndiName());
      assertEquals("queue/B", md0.getMappedName());      
   }

   public void testServiceRef() throws Exception
   {
      ApplicationClientMetaData appClientMetaData = unmarshal("ApplicationClient14_testServiceRef.xml", ApplicationClientMetaData.class, null);
      JBossClientMetaData jbossClientMetaData = unmarshal("ApplicationClient14_testJBossServiceRef.xml", JBossClientMetaData.class, null);
      JBossClientMetaData metaData = new JBossClientMetaData();
      metaData.merge(jbossClientMetaData, appClientMetaData, true);

      ServiceReferenceMetaData sref = metaData.getServiceReferenceByName("service/beanMirrorSEI");
      assertNotNull(sref);
      assertTrue(sref instanceof JBossServiceReferenceMetaData);
      JBossServiceReferenceMetaData jsref = (JBossServiceReferenceMetaData) sref;

      assertEquals("javax.xml.rpc.Service", jsref.getServiceInterface());
      assertEquals("META-INF/wsdl/BeanMirrorSEI.wsdl", jsref.getWsdlFile());
      assertEquals("BeanMirrorSEI.xml", jsref.getJaxrpcMappingFile());
      assertEquals("http://localhost:8080/BeanMirrorSEIWeb/ws4ee/beanMirrorSEI?WSDL", jsref.getWsdlOverride());
      List<? extends PortComponentRef> pcrefs = jsref.getJBossPortComponentRef();
      assertEquals(1, pcrefs.size());
      JBossPortComponentRef pcref = (JBossPortComponentRef) pcrefs.get(0);
      assertEquals("com.sun.ts.tests.webservices.deploy.beanMirrorSEI.HelloWs", pcref.getServiceEndpointInterface());
      QName qname = new QName("http://BeanMirrorSEI.org", "HelloWsPort");
      assertEquals(qname, pcref.getPortQname());
   }

   protected ApplicationClientMetaData unmarshal() throws Exception
   {
      return unmarshal(ApplicationClientMetaData.class);
   }
}
