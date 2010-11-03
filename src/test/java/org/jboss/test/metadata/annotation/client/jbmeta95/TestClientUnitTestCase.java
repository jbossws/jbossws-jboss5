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
package org.jboss.test.metadata.annotation.client.jbmeta95;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Set;

import junit.framework.TestCase;

import org.jboss.metadata.annotation.creator.client.ApplicationClient5MetaDataCreator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.client.spec.ApplicationClient5MetaData;
import org.jboss.metadata.javaee.spec.AnnotatedEJBReferenceMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbackMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferenceMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.test.metadata.common.PackageScanner;
import org.jboss.test.metadata.common.ScanPackage;

/**
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public class TestClientUnitTestCase extends TestCase
{
   @SuppressWarnings("unchecked")
   @ScanPackage("org.jboss.test.metadata.annotation.client.jbmeta95")
   public void testApplicationClientInheritanceProcessor()
      throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      ApplicationClient5MetaDataCreator creator = new ApplicationClient5MetaDataCreator(finder, "org.jboss.test.metadata.annotation.client.jbmeta95.Client");
      ApplicationClient5MetaData clientMD = creator.create(classes);
      assertEquals(3, clientMD.getAnnotatedEjbReferences().size());

      AnnotatedEJBReferenceMetaData resourceMethodBean = clientMD.getAnnotatedEjbReferences().get("resourceMethodBean");
      assertNotNull(resourceMethodBean);
      assertEquals(ResourceIF.class, resourceMethodBean.getBeanInterface());
      assertEquals("ResourceOnMethodBean", resourceMethodBean.getLink());
      Set<ResourceInjectionTargetMetaData> resourceMethodBeanTargets = resourceMethodBean.getInjectionTargets();
      assertNotNull(resourceMethodBeanTargets);
      assertEquals(1, resourceMethodBeanTargets.size());
      ResourceInjectionTargetMetaData method = resourceMethodBeanTargets.iterator().next();
      assertEquals(Super.class.getName(), method.getInjectionTargetClass());
      assertEquals("setResourceMethodBean", method.getInjectionTargetName());

      AnnotatedEJBReferenceMetaData resourceFieldBean = clientMD.getAnnotatedEjbReferences().get("resourceFieldBean");
      assertNotNull(resourceFieldBean);
      assertEquals("ResourceOnFieldBean", resourceFieldBean.getLink());
      Set<ResourceInjectionTargetMetaData> resourceFieldBeanTargets = resourceFieldBean.getInjectionTargets();
      assertNotNull(resourceFieldBeanTargets);
      assertEquals(1, resourceFieldBeanTargets.size());
      ResourceInjectionTargetMetaData field = resourceFieldBeanTargets.iterator().next();
      assertEquals(Super.class.getName(), field.getInjectionTargetClass());
      assertEquals("resourceFieldBean", field.getInjectionTargetName());

      AnnotatedEJBReferenceMetaData resourceClassBean = clientMD.getAnnotatedEjbReferences().get("ejb/resourceClassBean");
      assertNotNull(resourceClassBean);
      assertEquals("ResourcesOnClassBean", resourceClassBean.getLink());
      assertEquals("refs/resources/ResourcesOnClassBean", resourceClassBean.getMappedName());
      assertEquals("refs/resources/ResourcesOnClassBean", resourceClassBean.getJndiName());

      // jms Queue maps to message-destination-refs
      MessageDestinationReferencesMetaData msgRefs = clientMD.getMessageDestinationReferences();
      assertNotNull(msgRefs);
      MessageDestinationReferenceMetaData sendQueue = clientMD.getMessageDestinationReferenceByName("sendQueue");
      assertNotNull(sendQueue);
      Set<ResourceInjectionTargetMetaData> sendQueueTargets = sendQueue.getInjectionTargets();
      assertNotNull(sendQueueTargets);
      assertEquals(1, sendQueueTargets.size());
      ResourceInjectionTargetMetaData sendQueueField = sendQueueTargets.iterator().next();
      assertEquals(Super.class.getName(), sendQueueField.getInjectionTargetClass());
      assertEquals("sendQueue", sendQueueField.getInjectionTargetName());

      // ORB maps to a resource-env-ref
      ResourceEnvironmentReferenceMetaData orbRes = clientMD.getResourceEnvironmentReferenceByName(Super.class.getName() + "/orb");
      assertNotNull(orbRes);
      Set<ResourceInjectionTargetMetaData> orbResTargets = orbRes.getInjectionTargets();
      assertNotNull(orbResTargets);
      assertEquals(1, orbResTargets.size());
      ResourceInjectionTargetMetaData orbField = orbResTargets.iterator().next();
      assertEquals(Super.class.getName(), orbField.getInjectionTargetClass());
      assertEquals("orb", orbField.getInjectionTargetName());

      // URL maps to resource-ref
      ResourceReferenceMetaData urlRes = clientMD.getResourceReferenceByName("jboss-home-page");
      assertNotNull(urlRes);
      assertEquals("http://www.jboss.org", urlRes.getMappedName());
      Set<ResourceInjectionTargetMetaData> urlResTargets = urlRes.getInjectionTargets();
      assertNotNull(urlResTargets);
      assertEquals(1, urlResTargets.size());
      ResourceInjectionTargetMetaData urlResField = urlResTargets.iterator().next();
      assertEquals(Super.class.getName(), urlResField.getInjectionTargetClass());
      assertEquals("jbossHome", urlResField.getInjectionTargetName());

      // UserTransaction
      
      // String maps to env-entry
      EnvironmentEntryMetaData queueNameEntry = clientMD.getEnvironmentEntryByName("queueName");
      assertNotNull(queueNameEntry);
      assertEquals("queue/testQueue", queueNameEntry.getValue());
      Set<ResourceInjectionTargetMetaData> queueNameEntryTargets = queueNameEntry.getInjectionTargets();
      assertNotNull(queueNameEntryTargets);
      assertEquals(1, queueNameEntryTargets.size());
      ResourceInjectionTargetMetaData queueNameEntryField = queueNameEntryTargets.iterator().next();
      assertEquals(Super.class.getName(), queueNameEntryField.getInjectionTargetClass());
      assertEquals("queueName", queueNameEntryField.getInjectionTargetName());
      // Float
      // int

      LifecycleCallbacksMetaData postConstructs = clientMD.getPostConstructs();
      assertNotNull(postConstructs);
      assertEquals(1, postConstructs.size());
      LifecycleCallbackMetaData pc = postConstructs.get(0);
      assertEquals(Super.class.getName(), pc.getClassName());
      assertEquals("postConstruct", pc.getMethodName());
      
      LifecycleCallbacksMetaData preDestroys = clientMD.getPreDestroys();
      assertNotNull(preDestroys);
      assertEquals(1, preDestroys.size());
      LifecycleCallbackMetaData pd = preDestroys.get(0);
      assertEquals(Super.class.getName(), pd.getClassName());
      assertEquals("destroy", pd.getMethodName());

      // @WebServiceRef
      ServiceReferenceMetaData wsRef = clientMD.getServiceReferenceByName("service/somewebservice");
      assertNotNull(wsRef);
      Field wsRefField = Super.class.getDeclaredField("service");
      assertEquals(wsRefField, wsRef.getAnnotatedElement());
      assertEquals(DefaultWebServiceService.class.getName(), wsRef.getServiceRefType());
      Set<ResourceInjectionTargetMetaData> wsRefTargets = wsRef.getInjectionTargets();
      ResourceInjectionTargetMetaData wsRefFieldTarget = wsRefTargets.iterator().next();
      assertEquals(Super.class.getName(), wsRefFieldTarget.getInjectionTargetClass());
      assertEquals("service", wsRefFieldTarget.getInjectionTargetName());
      assertNull(wsRef.getServiceInterface());
   }
}

