/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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

import org.jboss.metadata.rar.jboss.mcf.LocalDataSourceDeploymentMetaData;
import org.jboss.metadata.rar.jboss.mcf.ManagedConnectionFactoryDeploymentGroup;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;
import org.jboss.xb.binding.sunday.unmarshalling.DefaultSchemaResolver;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBindingResolver;

public class JcaDs50UnitTestCase extends AbstractJavaEEMetaDataTest
{
   public static SchemaBindingResolver initResolver()
   {
      DefaultSchemaResolver resolver = new DefaultSchemaResolver();
      //resolver.addClassBinding("", ManagedConnectionFactoryDeploymentGroup.class);
      resolver.addClassBindingForLocation("jboss-ds_5_0.dtd", ManagedConnectionFactoryDeploymentGroup.class);
      return resolver;
   }

   public JcaDs50UnitTestCase(String name) {
      super(name);
   }


   public void testBasic() throws Exception
   {

      ManagedConnectionFactoryDeploymentGroup ds = unmarshal(ManagedConnectionFactoryDeploymentGroup.class);
      assertNotNull(ds);
      assertEquals(ds.getDeployments().size(), 1);
      assertNotNull(ds.getDeployments().get(0));
      assertTrue(ds.getDeployments().get(0) instanceof LocalDataSourceDeploymentMetaData);
      LocalDataSourceDeploymentMetaData ld = (LocalDataSourceDeploymentMetaData)ds.getDeployments().get(0);
      assertNotNull(ld);

   }

   public void testEverything() throws Exception
   {

      ManagedConnectionFactoryDeploymentGroup ds = unmarshal(ManagedConnectionFactoryDeploymentGroup.class);
      assertNotNull(ds);
      assertEquals(ds.getDeployments().size(), 1);
      assertNotNull(ds.getDeployments().get(0));
      assertTrue(ds.getDeployments().get(0) instanceof LocalDataSourceDeploymentMetaData);
      LocalDataSourceDeploymentMetaData ld = (LocalDataSourceDeploymentMetaData)ds.getDeployments().get(0);
      assertEquals(ld.getJndiName(), "DefaultDS");
      assertTrue(ld.getConnectionUrl().startsWith("jdbc"));
      assertEquals(ld.getConnectionProperties().size(), 0);
      assertEquals(ld.getDriverClass(), "org.hsqldb.jdbcDriver");
      assertEquals(ld.getUserName(), "sa");
      assertEquals(ld.getPassWord(), "");
      assertEquals(ld.getMinSize(), 5);
      assertEquals(ld.getMaxSize(), 20);
      assertEquals(ld.getIdleTimeoutMinutes(), 0);
      assertTrue(ld.getNewConnectionSQL().endsWith("sql"));
      assertTrue(ld.getValidConnectionCheckerClassName().endsWith("Checker"));
      assertTrue(ld.getCheckValidConnectionSQL().startsWith("select"));
      assertTrue(ld.getExceptionSorterClassName().endsWith("Sorter"));
      assertEquals(ld.getTrackStatements(), "");
      assertEquals(ld.getPreparedStatementCacheSize(), 32);
      

   }
}

