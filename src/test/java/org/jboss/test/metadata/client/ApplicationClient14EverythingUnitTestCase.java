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

import java.util.Set;

import org.jboss.metadata.client.spec.ApplicationClient14MetaData;
import org.jboss.metadata.javaee.spec.LifecycleCallbacksMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;
import org.jboss.metadata.javaee.spec.PersistenceUnitReferencesMetaData;
import org.jboss.metadata.javaee.spec.ResourceInjectionTargetMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBindingResolver;

/**
 * Test all entries of an application client 1.4 descriptor.
 *
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision$
 */
public class ApplicationClient14EverythingUnitTestCase extends AbstractJavaEEEverythingTest
{

   public ApplicationClient14EverythingUnitTestCase(String name)
   {
      super(name);
   }

   @Override
   protected void assertJndiName(String prefix, boolean full, String jndiName, Mode mode)
   {
      // an application client 1.4 doens't have a mapped name
      assertNull(jndiName);
   }
   
   @Override
   protected void assertLifecycleCallbacks(String ejbName, String type, int size,
         LifecycleCallbacksMetaData lifecycleCallbacksMetaData)
   {
      // an application client 1.4 doens't have any lifecycle callbacks
      assertNull(lifecycleCallbacksMetaData);
   }
   
   private void assertMessageDestinations14(String prefix, int size, MessageDestinationsMetaData messageDestinations, boolean full)
   {
      assertNotNull("no message destinations are set", messageDestinations);
      assertEquals(size, messageDestinations.size());
      int count = 1;
      for(MessageDestinationMetaData messageDestinationMetaData : messageDestinations)
      {
         assertMessageDestination14(prefix + count, messageDestinationMetaData, Mode.SPEC);
         count++;
      }
   }

   @Override
   protected void assertPersistenceUnitRefs(String prefix, int size,
         PersistenceUnitReferencesMetaData persistenceUnitReferencesMetaData, Mode mode)
   {
      // An application client 1.4 doesn't have any persistence unit refs
      assertNull(persistenceUnitReferencesMetaData);
   }
   
   @Override
   protected void assertResourceInjectionTargets(String prefix, int size, Set<ResourceInjectionTargetMetaData> targets)
   {
      // An application client 1.4 doesn't have any injection targets
      assertNull(targets);
   }
   
   public void testEverything() throws Exception
   {
      ApplicationClient14MetaData appClientMetaData = unmarshal();
      //assertEverythingWithAppMetaData(appClientMetaData);
      assertEquals("1.4", appClientMetaData.getVersion());
      assertDescriptionGroup("application-client", appClientMetaData.getDescriptionGroup());
      assertRemoteEnvironment("appClient", appClientMetaData, true, Mode.SPEC);
      assertEquals("org.jboss.test.metadata.client.AppClientCallbackHandler", appClientMetaData.getCallbackHandler());
      assertMessageDestinations14("appClientMessageDestination", 2, appClientMetaData.getMessageDestinations(), true);
   }
   
   protected ApplicationClient14MetaData unmarshal() throws Exception
   {
      return unmarshal(ApplicationClient14MetaData.class);
   }
}
