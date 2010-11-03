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

import org.jboss.metadata.client.spec.ApplicationClient14DTDMetaData;
import org.jboss.metadata.client.spec.ApplicationClient14MetaData;
import org.jboss.metadata.client.spec.ApplicationClient5MetaData;
import org.jboss.metadata.client.spec.ApplicationClientMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;
import org.jboss.xb.binding.sunday.unmarshalling.DefaultSchemaResolver;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBindingResolver;

/**
 * Test all entries of an application client 5 descriptor.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision$
 */
public class ApplicationClient5EverythingUnitTestCase extends AbstractJavaEEEverythingTest
{

   public ApplicationClient5EverythingUnitTestCase(String name)
   {
      super(name);
   }

   protected void assertEverything(ApplicationClientMetaData appClientMetaData, Mode mode)
   {
      //assertEverythingWithAppMetaData(appClientMetaData);
      assertEquals("5", appClientMetaData.getVersion());
      assertDescriptionGroup("application-client", appClientMetaData.getDescriptionGroup());
      assertRemoteEnvironment("appClient", appClientMetaData, true, mode);
      assertEquals("org.jboss.test.metadata.client.AppClientCallbackHandler", appClientMetaData.getCallbackHandler());
      assertMessageDestinations5("appClientMessageDestination", 2, appClientMetaData.getMessageDestinations(), true);
   }
   protected void assertMessageDestinations5(String prefix, int size, MessageDestinationsMetaData messageDestinations, boolean full)
   {
      assertNotNull("no message destinations are set", messageDestinations);
      assertEquals(size, messageDestinations.size());
      int count = 1;
      for(MessageDestinationMetaData messageDestinationMetaData : messageDestinations)
      {
         assertMessageDestination50(prefix + count, messageDestinationMetaData, Mode.SPEC);
         count++;
      }
   }

   public static SchemaBindingResolver initResolver()
   {
      DefaultSchemaResolver resolver = new DefaultSchemaResolver();
      
      resolver.addClassBindingForLocation("application-client_1_3.dtd", ApplicationClient14DTDMetaData.class);
      resolver.addClassBindingForLocation("application-client_1_4.xsd", ApplicationClient14MetaData.class);
      resolver.addClassBindingForLocation("application-client_5.xsd", ApplicationClient5MetaData.class);
      return resolver;
   }
   
   public void testEverything() throws Exception
   {
      ApplicationClientMetaData appClientMetaData = unmarshal();
      assertEverything(appClientMetaData, Mode.SPEC);
   }
   
   protected ApplicationClientMetaData unmarshal() throws Exception
   {
      return unmarshal(ApplicationClient5MetaData.class);
   }
}
