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

import org.jboss.metadata.client.jboss.JBossClientMetaData;
import org.jboss.metadata.client.spec.ApplicationClient5MetaData;
import org.jboss.metadata.client.spec.ApplicationClientMetaData;
import org.jboss.metadata.javaee.jboss.JBossPortComponentRef;
import org.jboss.metadata.javaee.jboss.JBossServiceReferenceMetaData;
import org.jboss.metadata.javaee.spec.PortComponentRef;
import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;

/**
 * Tests of an application client 5 descriptor.
 *
 * @author Scott.Stark
 * @version $Revision$
 */
public class ApplicationClient5UnitTestCase extends AbstractJavaEEEverythingTest
{
   public ApplicationClient5UnitTestCase(String name)
   {
      super(name);
   }

   public void test4915()
      throws Exception
   {
      ApplicationClientMetaData specMetaData = unmarshal();
      JBossClientMetaData jbossMetaData = unmarshal("JBossClient5_test4915.xml", JBossClientMetaData.class, null);
      JBossClientMetaData mergedMetaData = new JBossClientMetaData();
      mergedMetaData.merge(jbossMetaData, specMetaData, true);

      assertEquals(7, mergedMetaData.getServiceReferences().size());
      JBossServiceReferenceMetaData port2 = (JBossServiceReferenceMetaData) mergedMetaData.getServiceReferenceByName("Port2");
      List<? extends PortComponentRef> pcrefs = port2.getJBossPortComponentRef();
      assertEquals(1, pcrefs.size());
      JBossPortComponentRef pcref = (JBossPortComponentRef) pcrefs.get(0);
      assertEquals("META-INF/jbossws-client-config.xml", pcref.getConfigFile());
      assertEquals("Custom Client", pcref.getConfigName());
      assertEquals("org.jboss.test.ws.jaxws.samples.webserviceref.TestEndpoint", pcref.getServiceEndpointInterface());
      assertEquals("META-INF/wsdl/TestEndpoint.wsdl", port2.getWsdlOverride());
   }

   protected ApplicationClientMetaData unmarshal() throws Exception
   {
      return unmarshal(ApplicationClient5MetaData.class);
   }
}
