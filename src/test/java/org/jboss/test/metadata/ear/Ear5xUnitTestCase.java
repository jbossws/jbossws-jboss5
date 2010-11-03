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
package org.jboss.test.metadata.ear;


import junit.framework.Test;

import org.jboss.annotation.javaee.Description;
import org.jboss.annotation.javaee.Descriptions;
import org.jboss.annotation.javaee.DisplayName;
import org.jboss.annotation.javaee.DisplayNames;
import org.jboss.annotation.javaee.Icon;
import org.jboss.annotation.javaee.Icons;
import org.jboss.metadata.ear.spec.Ear50MetaData;
import org.jboss.metadata.ear.spec.Ear5xMetaData;
import org.jboss.metadata.ear.spec.ModuleMetaData;
import org.jboss.metadata.ear.spec.ModulesMetaData;
import org.jboss.metadata.ear.spec.WebModuleMetaData;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.DescriptionImpl;
import org.jboss.metadata.javaee.spec.DisplayNameImpl;
import org.jboss.metadata.javaee.spec.IconImpl;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBindingResolver;

/**
 * Ear5x tests
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class Ear5xUnitTestCase extends AbstractJavaEEMetaDataTest
{
   public static Test suite()
   {
      return suite(Ear5xUnitTestCase.class);
   }

   public static boolean validateSchema()
   {
      return true;
   }

   public static SchemaBindingResolver initResolver()
   {
      return schemaResolverForClass(Ear50MetaData.class);
   }
   
   public Ear5xUnitTestCase(String name)
   {
      super(name);
   }
   
   protected Ear5xMetaData unmarshal() throws Exception
   {
      return unmarshal(Ear50MetaData.class);
   }

   public void testId() throws Exception
   {
      Ear5xMetaData result = unmarshal();
      assertEquals("application-test-id", result.getId());
   }
   
   public void testVersion() throws Exception
   {
      Ear5xMetaData result = unmarshal();
      assertEquals("5", result.getVersion());
      assertFalse(result.isEE14());
      assertTrue(result.isEE5());
   }

   public void testDescriptionGroup() throws Exception
   {
      Ear5xMetaData result = unmarshal();
      DescriptionGroupMetaData group = result.getDescriptionGroup();
      assertNotNull(group);
      Descriptions descriptions = group.getDescriptions();
      assertNotNull(descriptions);
      
      DescriptionImpl en = new DescriptionImpl();
      en.setDescription("en-ear-desc");
      DescriptionImpl de = new DescriptionImpl();
      de.setDescription("de-ear-desc");
      de.setLanguage("de");
      DescriptionImpl fr = new DescriptionImpl();
      fr.setDescription("fr-ear-desc");
      fr.setLanguage("fr");
      
      Description[] expected = {en, fr, de};
      assertEquals(expected, descriptions.value());

      DisplayNames displayNames = group.getDisplayNames();
      assertNotNull(displayNames);
      
      DisplayNameImpl endn = new DisplayNameImpl();
      endn.setDisplayName("en-ear-disp");
      DisplayNameImpl frdn = new DisplayNameImpl();
      frdn.setDisplayName("fr-ear-disp");
      frdn.setLanguage("fr");
      DisplayNameImpl dedn = new DisplayNameImpl();
      dedn.setDisplayName("de-ear-disp");
      dedn.setLanguage("de");

      DisplayName[] expecteddns = {endn, frdn, dedn};
      assertEquals(expecteddns, displayNames.value());

      Icons icons = group.getIcons();
      assertNotNull(icons);
      IconImpl enicn = new IconImpl();
      enicn.setId("en-ear-icon-id");
      enicn.setSmallIcon("en-ear-small-icon");
      enicn.setLargeIcon("en-ear-large-icon");
      IconImpl fricn = new IconImpl();
      fricn.setLanguage("fr");
      fricn.setId("fr-ear-icon-id");
      fricn.setSmallIcon("fr-ear-small-icon");
      fricn.setLargeIcon("fr-ear-large-icon");
      IconImpl deicn = new IconImpl();
      deicn.setLanguage("de");
      deicn.setId("de-ear-icon-id");
      deicn.setSmallIcon("de-ear-small-icon");
      deicn.setLargeIcon("de-ear-large-icon");
      
      Icon[] expectedicns = {enicn, fricn, deicn};
      assertEquals(expectedicns, icons.value());
   }

   public void testWhitespace()
      throws Exception
   {
      Ear5xMetaData result = unmarshal();
      ModulesMetaData modules = result.getModules();
      assertEquals(4, modules.size());
      assertEquals("transport_ejb_vehicle_client.jar", modules.get(0).getName());
      assertEquals("transport_ejb_vehicle_ejb.jar", modules.get(1).getName());
      assertEquals("transport_jsp_vehicle_web.war", modules.get(2).getName());
      assertEquals("transport_servlet_vehicle_web.war", modules.get(3).getName());
      assertEquals("transport_servlet_vehicle", ((WebModuleMetaData)modules.get(3).getValue()).getContextRoot());
   }
}
