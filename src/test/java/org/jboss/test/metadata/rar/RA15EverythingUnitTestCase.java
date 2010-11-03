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
import org.jboss.metadata.rar.spec.ConnectorMetaData;

/**
 * Test ResourceAdapter ra.xml metadata.
 *
 * @author Jeff Zhang
 * @version $Revision$
 */
public class RA15EverythingUnitTestCase extends BaseRAUnitTestCase
{
   public RA15EverythingUnitTestCase(String name)
   {
      super(name);
   }
   
   public void testEverything() throws Exception
   {
      ConnectorMetaData connector = unmarshal();
      assertNotNull(connector);
      assertEverything(connector);
   }
   
   protected ConnectorMetaData unmarshal() throws Exception
   {
      return unmarshal(ConnectorMetaData.class);
   }
   
   protected void assertEverything(ConnectorMetaData connector) throws Exception
   {
      assertDescriptionGroup("connector", connector.getDescriptionGroup());
      assertGeneralInfo(connector);
      assertRA(connector.getRa());
   }
   @Override
   protected String getRAVersion()
   {
      return "5.0";
   }
   @Override
   protected void assertConfigPropsInAS(ActivationspecMetaData as)
   {
   }
}
