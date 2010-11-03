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
package org.jboss.test.metadata.web;

import java.net.URL;

import junit.framework.TestCase;

import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.web.jboss.JBoss50WebMetaData;
import org.jboss.metadata.web.jboss.JBossWebMetaData;
import org.jboss.metadata.web.spec.Web23MetaData;
import org.jboss.metadata.web.spec.Web24MetaData;
import org.jboss.metadata.web.spec.Web25MetaData;
import org.jboss.metadata.web.spec.WebMetaData;
import org.jboss.xb.binding.Unmarshaller;
import org.jboss.xb.binding.UnmarshallerFactory;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBinding;
import org.jboss.xb.builder.JBossXBBuilder;

/**
 * Basic tests of parsing web.xml descriptors using the JBossXBBuilder
 * as done in the TomcatDeployer
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class TomcatDeployerUnitTestCase extends TestCase
{

   public TomcatDeployerUnitTestCase(String name)
   {
      super(name);
   }

   public void testConfweb23() throws Exception
   {
      JBossWebMetaData webApp = unmarshal(Web23MetaData.class);
      DescriptionGroupMetaData dg = webApp.getDescriptionGroup();
      assertNotNull(dg);
      assertEquals("TomcatDeployer_confweb.xml", dg.getDescription());
      assertEquals("web-app_2_3", webApp.getId());
      assertEquals("2.3", webApp.getVersion());
   }
   public void testConfweb24() throws Exception
   {
      JBossWebMetaData webApp = unmarshal(Web24MetaData.class);
      DescriptionGroupMetaData dg = webApp.getDescriptionGroup();
      assertNotNull(dg);
      assertEquals("TomcatDeployer_confweb.xml", dg.getDescription());
      assertEquals("web-app_2_4", webApp.getId());
      assertEquals("2.4", webApp.getVersion());
      assertNotNull(webApp.getServletByName("default"));
      assertEquals(0, webApp.getServletByName("default").getLoadOnStartup());
   }
   public void testConfweb25() throws Exception
   {
      JBossWebMetaData webApp = unmarshal(Web25MetaData.class);
      DescriptionGroupMetaData dg = webApp.getDescriptionGroup();
      assertNotNull(dg);
      assertEquals("TomcatDeployer_confweb.xml", dg.getDescription());
      assertEquals("web-app_2_5", webApp.getId());
      assertEquals("2.5", webApp.getVersion());
   }

   protected JBossWebMetaData unmarshal(Class clazz)
      throws Exception
   {
      String name = getClass().getSimpleName();
      int end = name.indexOf("UnitTestCase");
      name = name.substring(0, end) + "_" + super.getName() + ".xml";
      return unmarshal(name, clazz);
   }

   protected JBossWebMetaData unmarshal(String name, Class clazz)
      throws Exception
   {
      UnmarshallerFactory factory = UnmarshallerFactory.newInstance();
      Unmarshaller unmarshaller = factory.newUnmarshaller();
      unmarshaller.setSchemaValidation(true);
      URL webXml = getClass().getResource(name);
      if (webXml == null)
         throw new IllegalStateException("Unable to find: "+name);
      SchemaBinding schema = JBossXBBuilder.build(clazz);
      WebMetaData confWebMD = (WebMetaData) unmarshaller.unmarshal(webXml.toString(), schema);
      JBoss50WebMetaData sharedMetaData = new JBoss50WebMetaData();
      sharedMetaData.merge(null, confWebMD);

      return sharedMetaData;
   }
}
