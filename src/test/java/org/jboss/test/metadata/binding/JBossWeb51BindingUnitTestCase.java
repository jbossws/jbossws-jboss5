/*
  * JBoss, Home of Professional Open Source
  * Copyright 2005, JBoss Inc., and individual contributors as indicated
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
package org.jboss.test.metadata.binding;


import org.jboss.metadata.web.jboss.JBoss50WebMetaData;

/**
 * A JBossWeb51BindingUnitTestCase.
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class JBossWeb51BindingUnitTestCase extends SchemaBindingValidationTest
{
   public JBossWeb51BindingUnitTestCase(String name)
   {
      super(name);
   }

   public void configureLogging()
   {
      super.configureLogging();
      //enableTrace(getClass().getName());
   }

   public void setUp() throws Exception
   {
      super.setUp();
      
      // not used in this schema
      //ignoreType(new QName(JavaEEMetaDataConstants.JBOSS_NS, "webservice-descriptionType"));
   }
   
   public void testJBossWeb51() throws Exception
   {
      assertEquivalent("jboss-web_5_1.xsd", JBoss50WebMetaData.class);
   }
}
