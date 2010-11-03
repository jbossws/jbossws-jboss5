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

import org.jboss.metadata.rar.jboss.JBossRAMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;

/**
 * Test JCA jboss-ra.xml metadata.
 *
 * @author Jeff Zhang
 * @version $Revision$
 */
public class JbossRA10UnitTestCase extends AbstractJavaEEMetaDataTest
{
   public JbossRA10UnitTestCase(String name)
   {
      super(name);
   }

   public void testBasic() throws Exception
   {
      JBossRAMetaData jbossRA = unmarshal(JBossRAMetaData.class);
      assertNotNull(jbossRA);
      assertEquals(jbossRA.getRaConfigProps().size(), 9);
      assertEquals(jbossRA.getRaConfigProps().get(0).getValue(), "XMLOVERRIDE");
      assertEquals(jbossRA.getRaConfigProps().get(8).getName(), "DoubleRAR");
   }
}
