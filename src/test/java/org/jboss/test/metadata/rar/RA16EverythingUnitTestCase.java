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


import org.jboss.metadata.rar.spec.ActivationspecMetaData;
import org.jboss.metadata.rar.spec.JCA16MetaData;

/**
 * Test ResourceAdapter ra.xml metadata.
 *
 * @author Jeff Zhang
 * @version $Revision$
 */
public class RA16EverythingUnitTestCase extends BaseRAUnitTestCase
{
   public RA16EverythingUnitTestCase(String name)
   {
      super(name);
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
