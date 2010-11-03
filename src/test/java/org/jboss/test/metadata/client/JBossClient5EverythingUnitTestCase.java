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

import org.jboss.metadata.client.jboss.JBossClient5MetaData;
import org.jboss.metadata.client.jboss.JBossClientMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationReferenceMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;
import org.jboss.test.metadata.javaee.JBossXBTestDelegate;
import org.jboss.xb.binding.sunday.unmarshalling.DefaultSchemaResolver;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBindingResolver;

/**
 * Test all entries of an JBoss client 5 descriptor.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision$
 */
public class JBossClient5EverythingUnitTestCase extends AbstractJavaEEEverythingTest
{

   public JBossClient5EverythingUnitTestCase(String name)
   {
      super(name);
   }

   protected void assertEverything(JBossClientMetaData jbossClientMetaData)
   {
      assertEquals("appClientJndiName", jbossClientMetaData.getJndiName());
      assertEquals("http://localhost", jbossClientMetaData.getJndiEnvironmentRefsGroup().getResourceReferences().get("appClientResourceRef2Name").getResUrl());
      MessageDestinationReferenceMetaData mref = jbossClientMetaData.getMessageDestinationReferenceByName("appClientMessageDestinationRef1Name");
      assertNotNull(mref);
      assertEquals("MDB_QUEUE", mref.getJndiName());
      assertEquals("MDB_QUEUE", mref.getMappedName());

      assertEquals(2, jbossClientMetaData.getDepends().size());
      int count = 1;
      for(String depends : jbossClientMetaData.getDepends())
      {
         assertEquals("appClientDepends" + count, depends);
         count++;
      }
   }
   
   public static SchemaBindingResolver initResolver()
   {
      //return schemaResolverForClass(JBossClient5MetaData.class);
      DefaultSchemaResolver resolver = new DefaultSchemaResolver();
      resolver.addClassBindingForLocation("jboss-client_5_0.xsd", JBossClient5MetaData.class);
      // Workaround wildard resolution slowness
      resolver.addClassBinding("http://www.jboss.com/xml/ns/javaee", JBossClient5MetaData.class);
      return resolver;
   }
   
   public void testEverything() throws Exception
   {
      JBossXBTestDelegate xbdelegate = (JBossXBTestDelegate) super.getDelegate();
      xbdelegate.setValidateSchema(true);
      JBossClient5MetaData jbossClientMetaData = unmarshal();
      assertEverything(jbossClientMetaData);
   }
   
   /**
    * When there is no jndiEnvironmentRefsGroup we don't want
    * any NPE to pop up.
    * @throws Exception
    */
   public void testNoJndiEnvironmentRefsGroup() throws Exception
   {
      JBossClientMetaData metaData = new JBossClient5MetaData();
      assertNull(metaData.getJndiEnvironmentRefsGroup());
      assertNull(metaData.getEjbReferences());
      assertNull(metaData.getEjbReferenceByName("unknown"));
      assertNull(metaData.getAnnotatedEjbReferences());
      assertNull(metaData.getEnvironmentEntries());
      assertNull(metaData.getEnvironmentEntryByName("unknown"));
      assertNull(metaData.getMessageDestinationReferences());
      assertNull(metaData.getMessageDestinationReferenceByName("unknown"));
      assertNull(metaData.getPersistenceUnitRefs());
      assertNull(metaData.getPersistenceUnitReferenceByName("unknown"));
      assertNull(metaData.getPostConstructs());
      assertNull(metaData.getPreDestroys());
      assertNull(metaData.getServiceReferences());
      assertNull(metaData.getServiceReferenceByName("unknown"));
      assertNull(metaData.getResourceEnvironmentReferences());
      assertNull(metaData.getResourceEnvironmentReferenceByName("unknown"));
      assertNull(metaData.getResourceReferences());
      assertNull(metaData.getResourceReferenceByName("unknown"));
   }
   
   protected JBossClient5MetaData unmarshal() throws Exception
   {
      return unmarshal(JBossClient5MetaData.class);
   }
}
