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

import org.jboss.metadata.rar.jboss.mcf.ConnectionFactoryDeploymentGroup;
import org.jboss.metadata.rar.jboss.mcf.DataSourceDeploymentMetaData;
import org.jboss.metadata.rar.jboss.mcf.LocalDataSourceDeploymentMetaData;
import org.jboss.metadata.rar.jboss.mcf.ManagedConnectionFactoryDeploymentGroup;
import org.jboss.metadata.rar.jboss.mcf.ManagedConnectionFactoryDeploymentMetaData;
import org.jboss.metadata.rar.jboss.mcf.NonXADataSourceDeploymentMetaData;
import org.jboss.metadata.rar.jboss.mcf.SecurityDeploymentType;
import org.jboss.metadata.rar.jboss.mcf.TxConnectionFactoryDeploymentMetaData;
import org.jboss.metadata.rar.jboss.mcf.XADataSourceDeploymentMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;

/**
 * Test JCA ds.xml metadata.
 *
 * @author Jeff Zhang
 * @version $Revision$
 */
public class JcaDs50UnitTestCase extends AbstractJavaEEMetaDataTest
{
   public JcaDs50UnitTestCase(String name)
   {
      super(name);
   }

   protected ManagedConnectionFactoryDeploymentGroup unmarshalMCFD() throws Exception
   {
      return unmarshal(ManagedConnectionFactoryDeploymentGroup.class);
   }
   
   protected ConnectionFactoryDeploymentGroup unmarshalCFD() throws Exception
   {
      return unmarshal(ConnectionFactoryDeploymentGroup.class);
   }

   /**
    * test a minimal local-tx-datasource
    */
   public void testDsMinimal() throws Exception
   {

      ManagedConnectionFactoryDeploymentGroup ds = unmarshalMCFD();
      assertNotNull(ds);
      assertEquals(ds.getDeployments().size(), 1);
      assertNotNull(ds.getDeployments().get(0));
      assertTrue(ds.getDeployments().get(0) instanceof LocalDataSourceDeploymentMetaData);
      LocalDataSourceDeploymentMetaData ld = (LocalDataSourceDeploymentMetaData)ds.getDeployments().get(0);
      assertTestNoXAMinimal(ld);
   }
   
   /**
    * test a basic local-tx-datasource, for example hsql-ds.xml
    */   
   public void testDsBasic() throws Exception
   {

      ManagedConnectionFactoryDeploymentGroup ds = unmarshalMCFD();
      LocalDataSourceDeploymentMetaData ld = (LocalDataSourceDeploymentMetaData)ds.getDeployments().get(0);
      assertTestNoXAMinimal(ld);
      assertEquals(ld.getConnectionProperties().size(), 0);
      assertEquals(ld.getUserName(), "sa");
      assertEquals(ld.getPassWord(), "");
      
      assertEquals(ld.getSecurityMetaData().getDomain(), "HsqlDbRealm");
      assertEquals(ld.getMinSize(), 5);
      assertEquals(ld.getMaxSize(), 20);
      assertEquals(ld.getIdleTimeoutMinutes(), 0);


      assertEquals(ld.getTrackStatements(), "");
      assertEquals(ld.getPreparedStatementCacheSize(), 32);
      assertNotNull(ld.getDbmsMetaData());
      assertEquals(ld.getDbmsMetaData().getTypeMapping(), "Hypersonic SQL");
   }
   
   /**
    * test a basic connection-factories, for example jms-ds.xml
    */   
   public void testCfBasic() throws Exception
   {

      ConnectionFactoryDeploymentGroup ds = unmarshalCFD();
      ManagedConnectionFactoryDeploymentMetaData mcfd = (ManagedConnectionFactoryDeploymentMetaData)ds.getDeployments().get(0);
      assertNotNull(mcfd);
      assertTrue(mcfd instanceof TxConnectionFactoryDeploymentMetaData);
      assertEquals(mcfd.getJndiName(), "JmsXA");
      assertTrue(((TxConnectionFactoryDeploymentMetaData)mcfd).getXaTransaction());
      assertEquals(mcfd.getRarName(), "jms-ra.rar");
      assertTrue(mcfd.getConnectionDefinition().endsWith("JmsConnectionFactory"));
      assertEquals(mcfd.getManagedConnectionFactoryProperties().size(), 2);
      assertEquals(mcfd.getManagedConnectionFactoryProperties().get(0).getName(), "SessionDefaultType");
      assertEquals(mcfd.getManagedConnectionFactoryProperties().get(1).getValue(), "java:/DefaultJMSProvider");

      assertEquals(mcfd.getSecurityMetaData().getSecurityDeploymentType(), SecurityDeploymentType.DOMAIN_AND_APPLICATION);
      assertEquals(mcfd.getMaxSize(), 20);
   }

   /**
    * test a everything of no-tx-datasource
    */ 
   public void testNoTxDsEverything() throws Exception
   {
      ManagedConnectionFactoryDeploymentGroup ds = unmarshalMCFD();
      NonXADataSourceDeploymentMetaData ld = (NonXADataSourceDeploymentMetaData)ds.getDeployments().get(0);
      assertTestNoXAMinimal(ld);
      
      assertFalse(ld.isUseJavaContext());
      assertTestUrl(ld);

      assertEquals(ld.getConnectionProperties().size(), 2);
      assertTrue(ld.getConnectionProperties().get(0).getName().equals("char.encoding"));
      assertTrue(ld.getConnectionProperties().get(1).getValue().equals("+8"));
      assertTestUser(ld);
      assertEquals(ld.getSecurityMetaData().getDomain(), "HsqlDbRealm");
      assertTestConnectionPool(ld);
      assertTrue(ld.isValidateOnMatch());
      assertTestDatasource(ld);

      assertTestAddition(ld);
      assertTestOption(ld);
   }

   /**
    * test a everything of local-tx-datasource
    */ 
   public void testLocalTxDsEverything() throws Exception
   {
      ManagedConnectionFactoryDeploymentGroup ds = unmarshalMCFD();
      LocalDataSourceDeploymentMetaData ld = (LocalDataSourceDeploymentMetaData)ds.getDeployments().get(0);
      assertTestNoXAMinimal(ld);
      
      assertFalse(ld.isUseJavaContext());
      assertTestUrl(ld);
      assertNotNull(ld.getTransactionIsolation());
      assertEquals(ld.getConnectionProperties().size(), 2);
      assertTrue(ld.getConnectionProperties().get(0).getName().equals("char.encoding"));
      assertTrue(ld.getConnectionProperties().get(1).getValue().equals("+8"));
      assertTestUser(ld);
      assertEquals(ld.getSecurityMetaData().getDomain(), "HsqlDbRealm");
      assertTestConnectionPool(ld);
      assertTrue(ld.isValidateOnMatch());

      assertTestDatasource(ld);
      
      assertTestAddition(ld);
      assertTestOption(ld);
   }

   /**
    * test a everything of xa-datasource
    */ 
   public void testXaDsEverything() throws Exception
   {
      ManagedConnectionFactoryDeploymentGroup ds = unmarshalMCFD();
      XADataSourceDeploymentMetaData ld = (XADataSourceDeploymentMetaData)ds.getDeployments().get(0);
      
      assertFalse(ld.isUseJavaContext());
      assertTrue(ld.isInterleaving());
      
      assertTrue(ld.getXaDataSourceClass().startsWith("oracle"));
      assertEquals(ld.getXaDataSourceProperties().size(), 3);
      assertEquals(ld.getXaDataSourceProperties().get(0).getName(), "URL");
      assertEquals(ld.getXaDataSourceProperties().get(2).getValue(), "tiger");
      assertTestUrl(ld);
      
      assertFalse(ld.getIsSameRMOverrideValue());

      assertTestUser(ld);
      assertEquals(ld.getSecurityMetaData().getDomain(), "HsqlDbRealm");
      assertTestConnectionPool(ld);
      assertTrue(ld.isValidateOnMatch());

      assertEquals(ld.getXaResourceTimeout(), 300);
      assertTestDatasource(ld);
      
      assertTestAddition(ld);
      assertTestOption(ld);
   }

   private void assertTestUrl(DataSourceDeploymentMetaData ld)
   {
      assertEquals(ld.getUrlDelimiter(), "|");
      assertTrue(ld.getUrlSelectorStrategyClassName().endsWith("Stratery"));
   }

   private void assertTestUser(DataSourceDeploymentMetaData ld)
   {
      assertEquals(ld.getUserName(), "sa");
      assertEquals(ld.getPassWord(), "");
   }

   private void assertTestConnectionPool(ManagedConnectionFactoryDeploymentMetaData ld)
   {
      assertEquals(ld.getMinSize(), 5);
      assertEquals(ld.getMaxSize(), 20);
      assertEquals(ld.getBlockingTimeoutMilliSeconds(), 30000);
      assertTrue(ld.isBackgroundValidation());
      assertEquals(ld.getBackgroundValidationMillis(), 3000);
      assertEquals(ld.getIdleTimeoutMinutes(), 0);
      assertEquals(ld.getAllocationRetry(), 0);
      assertEquals(ld.getAllocationRetryWaitMillis(), 5000);
   }

   private void assertTestDatasource(DataSourceDeploymentMetaData ld)
   {
      assertTrue(ld.getNewConnectionSQL().endsWith("sql"));
      assertTrue(ld.getValidConnectionCheckerClassName().endsWith("Checker"));
      assertTrue(ld.getCheckValidConnectionSQL().startsWith("select"));
      assertTrue(ld.getExceptionSorterClassName().endsWith("Sorter"));
      assertTrue(ld.getStaleConnectionCheckerClassName().endsWith("Checker"));
   }

   private void assertTestAddition(DataSourceDeploymentMetaData ld)
   {
      assertEquals(ld.getPreparedStatementCacheSize(), 32);
      assertTrue(ld.isSharePreparedStatements());
      assertTrue(ld.isUseQueryTimeout());
      assertEquals(ld.getQueryTimeout(), 300);
      assertEquals(ld.getUseTryLock(), 300000);
      assertEquals(ld.getTrackStatements(), "");
   }
   
   private void assertTestOption(ManagedConnectionFactoryDeploymentMetaData ld)
   {
      assertEquals(ld.getTypeMapping(), "Hypersonic SQL");
      assertEquals(ld.getDependsNames().size(), 2);
      assertTrue(ld.getDependsNames().get(0).startsWith("jboss"));
   }

   protected void assertTestNoXAMinimal(NonXADataSourceDeploymentMetaData xad) throws Exception
   {
      assertNotNull(xad);
      assertEquals(xad.getJndiName(), "DefaultDS");
      assertTrue(xad.getConnectionUrl().startsWith("jdbc"));
      assertEquals(xad.getDriverClass(), "org.hsqldb.jdbcDriver");
   }
}

