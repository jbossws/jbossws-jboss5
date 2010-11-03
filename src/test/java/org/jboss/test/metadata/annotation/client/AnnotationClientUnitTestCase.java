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
package org.jboss.test.metadata.annotation.client;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.ejb.EJB;
import javax.ejb.EJBs;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContexts;
import javax.persistence.PersistenceUnit;
import javax.persistence.PersistenceUnits;
import javax.xml.ws.WebServiceRef;
import javax.xml.ws.WebServiceRefs;

import org.jboss.metadata.annotation.creator.AnnotationContext;
import org.jboss.metadata.annotation.creator.client.ApplicationClient5MetaDataCreator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.annotation.finder.DefaultAnnotationFinder;
import org.jboss.metadata.client.jboss.JBossClient5DTDMetaData;
import org.jboss.metadata.client.jboss.JBossClient5MetaData;
import org.jboss.metadata.client.jboss.JBossClientMetaData;
import org.jboss.metadata.client.spec.AnnotationMergedView;
import org.jboss.metadata.client.spec.ApplicationClient14DTDMetaData;
import org.jboss.metadata.client.spec.ApplicationClient14MetaData;
import org.jboss.metadata.client.spec.ApplicationClient5MetaData;
import org.jboss.metadata.javaee.jboss.JBossServiceReferenceMetaData;
import org.jboss.metadata.javaee.jboss.JBossServiceReferencesMetaData;
import org.jboss.metadata.javaee.spec.AnnotatedEJBReferenceMetaData;
import org.jboss.metadata.javaee.spec.AnnotatedEJBReferencesMetaData;
import org.jboss.metadata.javaee.spec.EnvironmentEntryMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbackMetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferenceMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceEnvironmentReferenceMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;
import org.jboss.metadata.javaee.spec.ResourceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.ServiceReferencesMetaData;
import org.jboss.test.metadata.annotation.client.basic.Client;
import org.jboss.test.metadata.annotation.client.basic.DefaultWebServiceService;
import org.jboss.test.metadata.annotation.client.basic.ResourceIF;
import org.jboss.test.metadata.common.PackageScanner;
import org.jboss.test.metadata.common.ScanPackage;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;
import org.jboss.xb.binding.sunday.unmarshalling.DefaultSchemaResolver;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBindingResolver;
import org.omg.CORBA.ORB;

/**
 * Tests of JBossClientMetaData from annotations + xml merging
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision:$
 */
public class AnnotationClientUnitTestCase extends AbstractJavaEEMetaDataTest
{
   private static final String classMainName = "org.jboss.test.metadata.annotation.client.basic.Client"; 
   
   public static SchemaBindingResolver initResolver()
   {
      DefaultSchemaResolver resolver = new DefaultSchemaResolver();
      
      resolver.addClassBindingForLocation("application-client_1_3.dtd", ApplicationClient14DTDMetaData.class);
      resolver.addClassBindingForLocation("application-client_1_4.xsd", ApplicationClient14MetaData.class);
      resolver.addClassBindingForLocation("application-client_5.xsd", ApplicationClient5MetaData.class);
      resolver.addClassBindingForLocation("jboss-client_5_0.dtd", JBossClient5DTDMetaData.class);
      resolver.addClassBindingForLocation("jboss-client_5_0.xsd", JBossClient5MetaData.class);
      resolver.addClassBindingForLocation("jboss-client", JBossClient5DTDMetaData.class);
      return resolver;
   }

   public AnnotationClientUnitTestCase(String name)
   {
      super(name);
   }

   @SuppressWarnings("unchecked")
   @ScanPackage("org.jboss.test.metadata.annotation.client.basic")
   public void testApplicationClientProcessor()
      throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      ApplicationClient5MetaDataCreator creator = new ApplicationClient5MetaDataCreator(finder, classMainName);
      ApplicationClient5MetaData clientMD = creator.create(classes);
      assertEquals(3, clientMD.getAnnotatedEjbReferences().size());

      AnnotatedEJBReferenceMetaData resourceMethodBean = clientMD.getAnnotatedEjbReferences().get(classMainName + "/resourceMethodBean");
      assertNotNull(resourceMethodBean);
      assertEquals(ResourceIF.class, resourceMethodBean.getBeanInterface());
      assertEquals("ResourceOnMethodBean", resourceMethodBean.getLink());
      Set<ResourceInjectionTargetMetaData> resourceMethodBeanTargets = resourceMethodBean.getInjectionTargets();
      assertNotNull(resourceMethodBeanTargets);
      assertEquals(1, resourceMethodBeanTargets.size());
      ResourceInjectionTargetMetaData method = resourceMethodBeanTargets.iterator().next();
      assertEquals(Client.class.getName(), method.getInjectionTargetClass());
      assertEquals("setResourceMethodBean", method.getInjectionTargetName());

      AnnotatedEJBReferenceMetaData resourceFieldBean = clientMD.getAnnotatedEjbReferences().get(classMainName + "/resourceFieldBean");
      assertNotNull(resourceFieldBean);
      assertEquals("ResourceOnFieldBean", resourceFieldBean.getLink());
      Set<ResourceInjectionTargetMetaData> resourceFieldBeanTargets = resourceFieldBean.getInjectionTargets();
      assertNotNull(resourceFieldBeanTargets);
      assertEquals(1, resourceFieldBeanTargets.size());
      ResourceInjectionTargetMetaData field = resourceFieldBeanTargets.iterator().next();
      assertEquals(Client.class.getName(), field.getInjectionTargetClass());
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
      assertEquals(Client.class.getName(), sendQueueField.getInjectionTargetClass());
      assertEquals("sendQueue", sendQueueField.getInjectionTargetName());

      // ORB maps to a resource-env-ref
      ResourceEnvironmentReferenceMetaData orbRes = clientMD.getResourceEnvironmentReferenceByName(Client.class.getName() + "/orb");
      assertNotNull(orbRes);
      Set<ResourceInjectionTargetMetaData> orbResTargets = orbRes.getInjectionTargets();
      assertNotNull(orbResTargets);
      assertEquals(1, orbResTargets.size());
      ResourceInjectionTargetMetaData orbField = orbResTargets.iterator().next();
      assertEquals(Client.class.getName(), orbField.getInjectionTargetClass());
      assertEquals("orb", orbField.getInjectionTargetName());

      // URL maps to resource-ref
      ResourceReferenceMetaData urlRes = clientMD.getResourceReferenceByName("jboss-home-page");
      assertNotNull(urlRes);
      assertEquals("http://www.jboss.org", urlRes.getMappedName());
      Set<ResourceInjectionTargetMetaData> urlResTargets = urlRes.getInjectionTargets();
      assertNotNull(urlResTargets);
      assertEquals(1, urlResTargets.size());
      ResourceInjectionTargetMetaData urlResField = urlResTargets.iterator().next();
      assertEquals(Client.class.getName(), urlResField.getInjectionTargetClass());
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
      assertEquals(Client.class.getName(), queueNameEntryField.getInjectionTargetClass());
      assertEquals("queueName", queueNameEntryField.getInjectionTargetName());
      // Float
      // int

      LifecycleCallbacksMetaData postConstructs = clientMD.getPostConstructs();
      assertNotNull(postConstructs);
      assertEquals(1, postConstructs.size());
      LifecycleCallbackMetaData pc = postConstructs.get(0);
      assertEquals(Client.class.getName(), pc.getClassName());
      assertEquals("postConstruct", pc.getMethodName());
      
      LifecycleCallbacksMetaData preDestroys = clientMD.getPreDestroys();
      assertNotNull(preDestroys);
      assertEquals(1, preDestroys.size());
      LifecycleCallbackMetaData pd = preDestroys.get(0);
      assertEquals(Client.class.getName(), pd.getClassName());
      assertEquals("destroy", pd.getMethodName());

      // @WebServiceRef
      ServiceReferenceMetaData wsRef = clientMD.getServiceReferenceByName("service/somewebservice");
      assertNotNull(wsRef);
      Field wsRefField = Client.class.getDeclaredField("service");
      assertEquals(wsRefField, wsRef.getAnnotatedElement());
      assertEquals(DefaultWebServiceService.class.getName(), wsRef.getServiceRefType());
      Set<ResourceInjectionTargetMetaData> wsRefTargets = wsRef.getInjectionTargets();
      ResourceInjectionTargetMetaData wsRefFieldTarget = wsRefTargets.iterator().next();
      assertEquals(Client.class.getName(), wsRefFieldTarget.getInjectionTargetClass());
      assertEquals("service", wsRefFieldTarget.getInjectionTargetName());
      assertNull(wsRef.getServiceInterface());
      
      assertAnnotationContext(creator.getAnnotationContext());
   }

   @ScanPackage("org.jboss.test.metadata.annotation.client.basic")
   public void testXmlMerge()
      throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      ApplicationClient5MetaDataCreator creator = new ApplicationClient5MetaDataCreator(finder, classMainName);
      ApplicationClient5MetaData clientMD = creator.create(classes);
      
      ApplicationClient5MetaData xmlMD = super.unmarshal(ApplicationClient5MetaData.class);
      EnvironmentEntryMetaData msg = xmlMD.getEnvironmentEntryByName("msg");
      assertNotNull(msg);
      
      ApplicationClient5MetaData merged = new ApplicationClient5MetaData();
      AnnotationMergedView.merge(merged, xmlMD, clientMD);

      msg = merged.getEnvironmentEntryByName("msg");
      assertNotNull(msg);
      assertEquals("java.lang.String", msg.getType());
      assertEquals("how are you?", msg.getValue());

      LifecycleCallbacksMetaData postConstructs = merged.getPostConstructs();
      assertNotNull(postConstructs);
      // 2 because of the null class name in the xml
      assertEquals(2, postConstructs.size());
      LifecycleCallbackMetaData pc0 = postConstructs.get(0);
      assertEquals("postConstruct", pc0.getMethodName());

      LifecycleCallbacksMetaData preDestroys = merged.getPreDestroys();
      assertNotNull(preDestroys);
      // 2 because of the null class name in the xml
      assertEquals(2, preDestroys.size());
      LifecycleCallbackMetaData pd0 = preDestroys.get(0);
      assertEquals("preDestroy", pd0.getMethodName());

      assertEquals("org.jboss.ejb3.test.applicationclient.client.TestCallbackHandler", merged.getCallbackHandler());

      // ORB maps to a resource-env-ref
      ResourceEnvironmentReferenceMetaData orbRes = clientMD.getResourceEnvironmentReferenceByName(Client.class.getName() + "/orb");
      assertNotNull(orbRes);
      assertEquals(ORB.class.getName(), orbRes.getType());
      ResourceEnvironmentReferenceMetaData messageReplier = merged.getResourceEnvironmentReferenceByName("messageReplier");
      assertNotNull(messageReplier);
      assertEquals("javax.jms.Queue", messageReplier.getType());

      // String maps to env-entry
      EnvironmentEntryMetaData queueNameEntry = clientMD.getEnvironmentEntryByName("queueName");
      assertNotNull(queueNameEntry);
      assertEquals("queue/testQueueOverride", queueNameEntry.getValue());
      Set<ResourceInjectionTargetMetaData> queueNameEntryTargets = queueNameEntry.getInjectionTargets();
      assertNotNull(queueNameEntryTargets);
      assertEquals(1, queueNameEntryTargets.size());
      ResourceInjectionTargetMetaData queueNameEntryField = queueNameEntryTargets.iterator().next();
      assertEquals(Client.class.getName(), queueNameEntryField.getInjectionTargetClass());
      assertEquals("queueName", queueNameEntryField.getInjectionTargetName());

      MessageDestinationMetaData md = merged.getMessageDestinationByName("MY-QUEUE");
      assertNotNull(md);
      assertEquals("queue/testQueue", md.getJndiName());
      assertEquals("queue/testQueue", md.getMappedName());
      
      ServiceReferencesMetaData serviceReferences = merged.getServiceReferences();
      assertNotNull(serviceReferences);
      assertEquals(1, serviceReferences.size());
   }
   
   @ScanPackage("org.jboss.test.metadata.annotation.client.basic")
   public void testJBossXmlMerge()
      throws Exception
   {
         AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
         Collection<Class<?>> classes = PackageScanner.loadClasses();
         ApplicationClient5MetaDataCreator creator = new ApplicationClient5MetaDataCreator(finder, classMainName);
         ApplicationClient5MetaData clientMD = creator.create(classes);         
         
         ApplicationClient5MetaData xmlMD = super.unmarshal("AnnotationClient_testXmlMerge.xml", ApplicationClient5MetaData.class);
         ApplicationClient5MetaData merged = new ApplicationClient5MetaData();
         AnnotationMergedView.merge(merged, xmlMD, clientMD);
         
         JBossClientMetaData jbossXmlMD = super.unmarshal(JBossClientMetaData.class);
         // Create a merged view
         JBossClientMetaData mergedMetaData = new JBossClientMetaData();
         mergedMetaData.merge(jbossXmlMD, merged, false);

         EnvironmentEntryMetaData msg = mergedMetaData.getEnvironmentEntryByName("msg");
         assertNotNull(msg);
         assertEquals("java.lang.String", msg.getType());
         assertEquals("how are you?", msg.getValue());

         LifecycleCallbacksMetaData postConstructs = mergedMetaData.getPostConstructs();
         assertNotNull(postConstructs);
         // 2 because of the null class name in the xml
         assertEquals(2, postConstructs.size());
         LifecycleCallbackMetaData pc0 = postConstructs.get(0);
         assertEquals("postConstruct", pc0.getMethodName());

         LifecycleCallbacksMetaData preDestroys = mergedMetaData.getPreDestroys();
         assertNotNull(preDestroys);
         // 2 because of the null class name in the xml
         assertEquals(2, preDestroys.size());
         LifecycleCallbackMetaData pd0 = preDestroys.get(0);
         assertEquals("preDestroy", pd0.getMethodName());

         assertEquals("org.jboss.ejb3.test.applicationclient.client.TestCallbackHandler", mergedMetaData.getCallbackHandler());

         // jms Queue maps to message-destination-refs
         MessageDestinationReferencesMetaData msgRefs = mergedMetaData.getMessageDestinationReferences();
         assertNotNull(msgRefs);
         MessageDestinationReferenceMetaData sendQueue = mergedMetaData.getMessageDestinationReferenceByName("sendQueue");
         assertNotNull(sendQueue);
         assertEquals("MDB_QUEUE", sendQueue.getJndiName());
         assertEquals("MDB_QUEUE", sendQueue.getMappedName());
         Set<ResourceInjectionTargetMetaData> sendQueueTargets = sendQueue.getInjectionTargets();
         assertEquals(1, sendQueueTargets.size());
         ResourceInjectionTargetMetaData sendQueueField = sendQueueTargets.iterator().next();
         assertEquals(Client.class.getName(), sendQueueField.getInjectionTargetClass());
         assertEquals("sendQueue", sendQueueField.getInjectionTargetName());

         // jms queue connection factory maps to resource-ref
         ResourceReferenceMetaData qcfRef = mergedMetaData.getResourceReferenceByName("queueConnectionFactory");
         assertNotNull(qcfRef);
         assertEquals("jms/QueueConnectionFactory", qcfRef.getJndiName());
         assertEquals("jms/QueueConnectionFactory", qcfRef.getMappedName());
         Set<ResourceInjectionTargetMetaData> qcfRefTargets = qcfRef.getInjectionTargets();
         assertEquals(1, qcfRefTargets.size());
         ResourceInjectionTargetMetaData qcfRefField = qcfRefTargets.iterator().next();
         assertEquals(Client.class.getName(), qcfRefField.getInjectionTargetClass());
         assertEquals("queueConnectionFactory", qcfRefField.getInjectionTargetName());
         
         // ORB maps to a resource-env-ref
         ResourceEnvironmentReferenceMetaData orbRes = clientMD.getResourceEnvironmentReferenceByName(Client.class.getName() + "/orb");
         assertNotNull(orbRes);
         assertEquals(ORB.class.getName(), orbRes.getType());
         ResourceEnvironmentReferenceMetaData messageReplier = mergedMetaData.getResourceEnvironmentReferenceByName("messageReplier");
         assertNotNull(messageReplier);
         assertEquals("javax.jms.Queue", messageReplier.getType());

         // String maps to env-entry
         EnvironmentEntryMetaData queueNameEntry = mergedMetaData.getEnvironmentEntryByName("queueName");
         assertNotNull(queueNameEntry);
         assertEquals("queue/testQueueOverride", queueNameEntry.getValue());
         Set<ResourceInjectionTargetMetaData> queueNameEntryTargets = queueNameEntry.getInjectionTargets();
         assertNotNull(queueNameEntryTargets);
         assertEquals(1, queueNameEntryTargets.size());
         ResourceInjectionTargetMetaData queueNameEntryField = queueNameEntryTargets.iterator().next();
         assertEquals(Client.class.getName(), queueNameEntryField.getInjectionTargetClass());
         assertEquals("queueName", queueNameEntryField.getInjectionTargetName());

         MessageDestinationMetaData md = mergedMetaData.getMessageDestinationByName("MY-QUEUE");
         assertNotNull(md);
         assertEquals("queue/testQueue", md.getJndiName());
         assertEquals("queue/testQueue", md.getMappedName());
         
         JBossServiceReferencesMetaData serviceReferences = (JBossServiceReferencesMetaData) mergedMetaData.getServiceReferences();
         assertNotNull(serviceReferences);
         assertEquals(1, serviceReferences.size());
         JBossServiceReferenceMetaData serviceRef = (JBossServiceReferenceMetaData) serviceReferences.iterator().next();
         assertNotNull(serviceRef);
         assertEquals("service/somewebservice", serviceRef.getServiceRefName());
         assertEquals("http://localhost:8080/WSDefaultWebServiceApp/jws/defaultWebService?WSDL", serviceRef.getWsdlOverride());
         Field wsRefField = Client.class.getDeclaredField("service");
         //assertEquals(wsRefField, serviceRef.getAnnotatedElement());
         assertEquals(DefaultWebServiceService.class.getName(), serviceRef.getServiceRefType());
         Set<ResourceInjectionTargetMetaData> wsRefTargets = serviceRef.getInjectionTargets();
         ResourceInjectionTargetMetaData wsRefFieldTarget = wsRefTargets.iterator().next();
         assertEquals(Client.class.getName(), wsRefFieldTarget.getInjectionTargetClass());
         assertEquals("service", wsRefFieldTarget.getInjectionTargetName());
         assertEquals(wsRefField.getType().getName(), serviceRef.getServiceInterface());
   }
   
   private void assertAnnotationContext(AnnotationContext context)
   {
      Collection<Class<? extends Annotation>> typeAnnotations = new HashSet<Class<? extends Annotation>>();
      typeAnnotations.add(Resource.class);
      typeAnnotations.add(Resources.class);
      typeAnnotations.add(EJB.class);
      typeAnnotations.add(EJBs.class);
      typeAnnotations.add(PersistenceContext.class);
      typeAnnotations.add(PersistenceContexts.class);
      typeAnnotations.add(PersistenceUnit.class);
      typeAnnotations.add(PersistenceUnits.class);
      typeAnnotations.add(WebServiceRef.class);
      typeAnnotations.add(WebServiceRefs.class);
      
      // Assert Type annotations
      assertAnnotations(typeAnnotations, context.getTypeAnnotations());
      
      Collection<Class<? extends Annotation>> methodAnnotations = new HashSet<Class<? extends Annotation>>();
      methodAnnotations.add(PreDestroy.class);
      methodAnnotations.add(PostConstruct.class);
      methodAnnotations.add(Resource.class);
      methodAnnotations.add(EJB.class);
      methodAnnotations.add(PersistenceContext.class);
      methodAnnotations.add(PersistenceUnit.class);
      methodAnnotations.add(WebServiceRef.class);
      
      // Assert Method annotations
      assertAnnotations(methodAnnotations, context.getMethodAnnotations());
      
      Collection<Class<? extends Annotation>> fieldAnnotations = new HashSet<Class<? extends Annotation>>();
      fieldAnnotations.add(Resource.class);
      fieldAnnotations.add(EJB.class);
      fieldAnnotations.add(PersistenceContext.class);
      fieldAnnotations.add(PersistenceUnit.class);
      fieldAnnotations.add(WebServiceRef.class);
      
      // Assert Field annotations
      assertAnnotations(fieldAnnotations, context.getFieldAnnotations());
   }
   
   private void assertAnnotations(Collection<Class<? extends Annotation>> expected, Collection<Class<? extends Annotation>> actual)
   {
      assertEquals(expected.size(), actual.size());
      assertTrue(actual.containsAll(expected));
   }
   
   @ScanPackage("org.jboss.test.metadata.annotation.client.basic")
   public void testAnnotatednEnv() throws Exception
   {
      AnnotationFinder<AnnotatedElement> finder = new DefaultAnnotationFinder<AnnotatedElement>();
      Collection<Class<?>> classes = PackageScanner.loadClasses();
      ApplicationClient5MetaDataCreator creator = new ApplicationClient5MetaDataCreator(finder, classMainName);
      
      ApplicationClient5MetaData annotatedMetaData = creator.create(classes);         
      ApplicationClient5MetaData specMetaData = super.unmarshal("AnnotationClient_testAnnotatedEnv.xml", ApplicationClient5MetaData.class);
      JBossClientMetaData metaData = super.unmarshal("JBossAnnotationClient_testAnnotatedEnv.xml", JBossClient5MetaData.class);
      
      // Create a merged view
      ApplicationClient5MetaData specMerged = new ApplicationClient5MetaData();
      AnnotationMergedView.merge(specMerged, specMetaData, annotatedMetaData);
      specMetaData = specMerged;

      JBossClientMetaData mergedMetaData = new JBossClientMetaData();
      mergedMetaData.merge(metaData, specMetaData, false);
      
      assertNotNull(mergedMetaData);
      AnnotatedEJBReferencesMetaData annotatedRefs = mergedMetaData.getAnnotatedEjbReferences();
      assertNotNull(annotatedRefs);
      AnnotatedEJBReferenceMetaData annotatedRef = annotatedRefs.get("ejb/resourceClassBean");
      assertNotNull(annotatedRef);
      assertNotNull(annotatedRef.getIgnoreDependency());
      // The same name for jndi / mappedname
      assertEquals("test_resourceClasJndiName", annotatedRef.getJndiName());
      assertEquals("test_resourceClasJndiName", annotatedRef.getMappedName());
   }
}
