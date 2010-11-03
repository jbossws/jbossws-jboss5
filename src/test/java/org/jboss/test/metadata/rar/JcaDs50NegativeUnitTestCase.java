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

import java.io.PrintWriter;
import java.io.StringWriter;

import org.jboss.metadata.rar.jboss.mcf.ManagedConnectionFactoryDeploymentGroup;

import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;

/**
 * Test ResourceAdapter ds.xml metadata.
 *
 * @author Jeff Zhang
 * @version $Revision$
 */
public class JcaDs50NegativeUnitTestCase extends AbstractJavaEEEverythingTest
{
   public JcaDs50NegativeUnitTestCase(String name)
   {
      super(name);
   }

   /**
    * test miss jndi-name
    */
   public void testParserJndiName() throws Exception
   {
      negativeParserTest("jndi-name");
   }
   
   /**
    * test miss connection-url
    */
   public void testParserConnectionUrl() throws Exception
   {
      negativeParserTest("connection-url");
   }
   
   /**
    * test miss driver-class
    */
   public void testParserDriverClass() throws Exception
   {
      negativeParserTest("driver-class");
   }
   
   protected ManagedConnectionFactoryDeploymentGroup unmarshalMCFD() throws Exception
   {
      return unmarshal(ManagedConnectionFactoryDeploymentGroup.class);
   }
   
   private void negativeParserTest(String element) throws Exception
   {
      try
      {
         ManagedConnectionFactoryDeploymentGroup connector = unmarshalMCFD();
         fail(getName() + " should be failed since we miss " + element);
      } catch (Exception expected)
      {
         StringWriter sw = new StringWriter();
         PrintWriter pw = new PrintWriter(sw);
         expected.printStackTrace(pw);
         assertTrue(sw.toString().indexOf(element) > 0);
      }    
   }


}
