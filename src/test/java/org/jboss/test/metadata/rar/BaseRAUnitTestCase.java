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
import org.jboss.metadata.rar.spec.ConnectorMetaData;
import org.jboss.metadata.rar.spec.InboundRaMetaData;
import org.jboss.metadata.rar.spec.MessageListenerMetaData;
import org.jboss.metadata.rar.spec.OutboundRaMetaData;
import org.jboss.metadata.rar.spec.ResourceAdapterMetaData;
import org.jboss.metadata.rar.spec.TransactionSupportMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;

/**
 * Base Test ResourceAdapter ra.xml metadata.
 *
 * @author Jeff Zhang
 * @version $Revision$
 */
public abstract class BaseRAUnitTestCase extends AbstractJavaEEEverythingTest {

   public BaseRAUnitTestCase(String name) {
      super(name);
   }
   
   protected void setUp() throws Exception
   {
      super.setUp();
      // enable trace only when debugging enableTrace("org.jboss.xb");
   }

   abstract protected String getRAVersion();
   
   protected void assertGeneralInfo(ConnectorMetaData connector) 
   {
      assertTrue(connector.getVendorName().startsWith("Red"));
      assertTrue(connector.getEISType().startsWith("JMS"));
      assertEquals(connector.getRAVersion(), getRAVersion());
      assertNotNull(connector.getLicense());
      assertTrue(connector.getLicense().getRequired());
   }

   protected void assertRA(ResourceAdapterMetaData ra) {
      assertNotNull(ra.getRaClass());
      assertNotNull(ra.getConfigProperty());
      assertEquals(ra.getConfigProperty().size(), 1);
      assertEquals(ra.getConfigProperty().get(0).getName(), "logLevel");
      assertEquals(ra.getConfigProperty().get(0).getValue(), "DEBUG");
      assertOutboundAdapter(ra.getOutboundRa());
      assertInoundAdapter(ra.getInboundRa());
      assertAdminObject(ra.getAdminObjects());
      assertNotNull(ra.getSecurityPermissions());
      assertEquals(ra.getSecurityPermissions().get(0).getSecurityPermissionSpec(), "sa");
   }
   
   private void assertOutboundAdapter(OutboundRaMetaData outboundRa) {
      assertNotNull(outboundRa.getConDefs());
      ConnectionDefinitionMetaData conDef = outboundRa.getConDefs().get(0);
      assertTrue(conDef.getManagedConnectionFactoryClass().endsWith("JmsManagedConnectionFactory"));
      assertEquals(conDef.getConfigProps().size(), 7);
      assertEquals(conDef.getConfigProps().get(6).getName(), "UseTryLock");
      assertTrue(conDef.getConnectionFactoryInterfaceClass().endsWith("JmsConnectionFactory"));
      assertTrue(conDef.getConnectionFactoryImplementationClass().endsWith("JmsConnectionFactoryImpl"));
      assertTrue(conDef.getConnectionInterfaceClass().endsWith("Session"));
      assertTrue(conDef.getConnectionImplementationClass().endsWith("JmsSession"));
      assertEquals(outboundRa.getTransSupport(), TransactionSupportMetaData.XATransaction);
      assertNotNull(outboundRa.getAuthMechanisms().get(0));
      assertEquals(outboundRa.getAuthMechanisms().get(0).getAuthenticationMechanismType(), "BasicPassword");
      assertTrue(outboundRa.getAuthMechanisms().get(0).getCredentialInterfaceClass().endsWith("PasswordCredential"));
      assertEquals(outboundRa.getAuthMechanisms().get(0).getId(), "amid");
      assertFalse(outboundRa.isReAuthSupport());

   }

   private void assertInoundAdapter(InboundRaMetaData inboundRa) {
      assertNotNull(inboundRa.getMessageAdapter());
      assertNotNull(inboundRa.getMessageAdapter().getMessageListeners());
      MessageListenerMetaData msgListener = inboundRa.getMessageAdapter().getMessageListeners().get(0);
      assertNotNull(msgListener);
      assertTrue(msgListener.getType().endsWith("MessageListener"));
      assertTrue(msgListener.getActivationSpecType().getAsClass().endsWith("JmsActivationSpec"));
      assertNotNull(msgListener.getActivationSpecType().getRequiredConfigProps());
      assertFalse(msgListener.getActivationSpecType().getRequiredConfigProps().isEmpty());
      assertConfigPropsInAS(msgListener.getActivationSpecType());

   }
   
   abstract protected void assertConfigPropsInAS(ActivationspecMetaData as);

   private void assertAdminObject(List<AdminObjectMetaData> adminObjects) {
      assertEquals(adminObjects.size(), 1);
      assertTrue(adminObjects.get(0).getAdminObjectInterfaceClass().endsWith("TestInterface"));
      assertTrue(adminObjects.get(0).getAdminObjectImplementationClass().endsWith("TestImplementation"));
      assertEquals(adminObjects.get(0).getConfigProps().size(), 2);
      assertEquals(adminObjects.get(0).getConfigProps().get(0).getName(), "StringProperty");
   }

}

