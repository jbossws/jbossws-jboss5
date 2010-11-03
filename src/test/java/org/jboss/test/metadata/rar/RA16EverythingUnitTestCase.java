/*
* JBoss, Home of Professional Open Source
* Copyright 2008, JBoss Inc., and individual contributors as indicated
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
package org.jboss.test.metadata.rar;

import java.util.List;

import org.jboss.metadata.rar.spec.ActivationspecMetaData;
import org.jboss.metadata.rar.spec.AdminObjectMetaData;
import org.jboss.metadata.rar.spec.ConnectionDefinitionMetaData;
import org.jboss.metadata.rar.spec.InboundRaMetaData;
import org.jboss.metadata.rar.spec.JCA16MetaData;
import org.jboss.metadata.rar.spec.MessageListenerMetaData;
import org.jboss.metadata.rar.spec.OutboundRaMetaData;
import org.jboss.metadata.rar.spec.ResourceAdapterMetaData;
import org.jboss.metadata.rar.spec.TransactionSupportMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;
import org.jboss.xb.binding.sunday.unmarshalling.DefaultSchemaResolver;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBindingResolver;
/**
 * Test ResourceAdapter ra.xml metadata.
 *
 * @author Jeff Zhang
 * @version $Revision:$
 */
public class RA16EverythingUnitTestCase extends BaseRAUnitTestCase {

   public static SchemaBindingResolver initResolver()
   {
      DefaultSchemaResolver resolver = new DefaultSchemaResolver();
      resolver.addClassBindingForLocation("connector_1_6.xsd", JCA16MetaData.class);
      return resolver;
   }

   public RA16EverythingUnitTestCase(String name) {
      super(name);
   }
   
   protected void setUp() throws Exception
   {
      super.setUp();
   }

   public void testEverything() throws Exception
   {
      JCA16MetaData connector = unmarshal();
      assertNotNull(connector);
      assertEverything(connector);
   }
   
   public void testMinimal() throws Exception
   {
      JCA16MetaData connector = unmarshal();
      assertNotNull(connector);
      assertTrue(connector.getVendorName().startsWith("Red"));
      assertTrue(connector.getEISType().startsWith("JMS"));
      assertEquals(connector.getRAVersion(), getRAVersion());
      assertNotNull(connector.getRa());
      assertNull(connector.getRa().getRaClass());
      assertNull(connector.getRa().getConfigProperty());
      assertNull(connector.getRa().getOutboundRa());
      assertNull(connector.getRa().getInboundRa());
      assertNull(connector.getRa().getAdminObjects());
      assertNull(connector.getRa().getSecurityPermissions());
   }
   
   protected JCA16MetaData unmarshal() throws Exception
   {
      return unmarshal(JCA16MetaData.class);
   }
   
   protected void assertEverything(JCA16MetaData connector) throws Exception
   {
      assertDescriptionGroup("connector", connector.getDescriptionGroup());
      assertGeneralInfo(connector);
      assertTrue(connector.isMetadataComplete());
      assertRA(connector.getRa());
      assertEquals(connector.getRequiredWorkContexts().size(), 2);
      assertEquals(connector.getRequiredWorkContexts().get(0), "java.work.Context1");
   }
   @Override
   protected String getRAVersion()
   {
      return "6.0";
   }
   @Override
   protected void assertConfigPropsInAS(ActivationspecMetaData as)
   {
      assertNotNull(as.getConfigProps());
      assertFalse(as.getConfigProps().isEmpty());
   }

}
