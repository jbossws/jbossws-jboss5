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
import org.jboss.metadata.ear.spec.ConnectorModuleMetaData;
import org.jboss.metadata.ear.spec.Ear50MetaData;
import org.jboss.metadata.ear.spec.Ear5xMetaData;
import org.jboss.metadata.ear.spec.EjbModuleMetaData;
import org.jboss.metadata.ear.spec.JavaModuleMetaData;
import org.jboss.metadata.ear.spec.ModuleMetaData;
import org.jboss.metadata.ear.spec.ModulesMetaData;
import org.jboss.metadata.ear.spec.WebModuleMetaData;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.DescriptionImpl;
import org.jboss.metadata.javaee.spec.DisplayNameImpl;
import org.jboss.metadata.javaee.spec.IconImpl;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleMetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEMetaDataTest;

/**
 * Ear5x tests
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class Ear5xEverythingUnitTestCase extends AbstractJavaEEMetaDataTest
{
   public static Test suite()
   {
      return suite(Ear5xEverythingUnitTestCase.class);
   }
   
   public Ear5xEverythingUnitTestCase(String name)
   {
      super(name);
   }
   
   protected Ear5xMetaData unmarshal() throws Exception
   {
      return unmarshal(Ear50MetaData.class);
   }

   public void testEverything() throws Exception
   {
      //enableTrace("org.jboss.xb");
      Ear5xMetaData result = unmarshal();
      assertEquals("application-test-everything", result.getId());
      assertEquals("5", result.getVersion());
      assertDescriptions(result);
      assertDisplayName(result);
      assertIcon(result);
      assertSecurityRoles(result);
      assertLibraryDirectory(result);
      assertModules(result);
   }

   protected void assertDescriptions(Ear5xMetaData ear)
      throws Exception
   {
      DescriptionGroupMetaData group = ear.getDescriptionGroup();
      assertNotNull(group);
      Descriptions descriptions = group.getDescriptions();
      assertNotNull(descriptions);
      
      DescriptionImpl den = new DescriptionImpl();
      den.setDescription("en-ear-desc");
      DescriptionImpl dfr = new DescriptionImpl();
      dfr.setLanguage("fr");
      dfr.setDescription("fr-ear-des");
      DescriptionImpl dde = new DescriptionImpl();
      dde.setLanguage("de");
      dde.setDescription("de-ear-des");
      Description[] expected = {den, dfr, dde};
      assertEquals(expected, descriptions.value());
   }
   
   protected void assertDisplayName(Ear5xMetaData ear)
      throws Exception
   {
      DescriptionGroupMetaData group = ear.getDescriptionGroup();
      assertNotNull(group);
      DisplayNames displayNames = group.getDisplayNames();
      assertNotNull(displayNames);
      
      DisplayNameImpl en = new DisplayNameImpl();
      en.setDisplayName("en-ear-disp");
      DisplayNameImpl fr = new DisplayNameImpl();
      fr.setDisplayName("fr-ear-disp");
      fr.setLanguage("fr");
      DisplayNameImpl de = new DisplayNameImpl();
      de.setDisplayName("de-ear-disp");
      de.setLanguage("de");

      DisplayName[] expected = {en, fr, de};
      assertEquals(expected, displayNames.value());
   }
   
   protected void assertIcon(Ear5xMetaData ear)
      throws Exception
   {
      DescriptionGroupMetaData group = ear.getDescriptionGroup();
      assertNotNull(group);
      Icons icons = group.getIcons();
      assertNotNull(icons);
      
      IconImpl en = new IconImpl();
      en.setId("en-ear-icon-id");
      en.setSmallIcon("en-ear-small-icon");
      en.setLargeIcon("en-ear-large-icon");
      IconImpl fr = new IconImpl();
      fr.setLanguage("fr");
      fr.setId("fr-ear-icon-id");
      fr.setSmallIcon("fr-ear-small-icon");
      fr.setLargeIcon("fr-ear-large-icon");
      IconImpl de = new IconImpl();
      de.setLanguage("de");
      de.setId("de-ear-icon-id");
      de.setSmallIcon("de-ear-small-icon");
      de.setLargeIcon("de-ear-large-icon");
      
      Icon[] expected = {en, fr, de};
      assertEquals(expected, icons.value());
   }

   protected void assertSecurityRoles(Ear5xMetaData ear)
   {
      SecurityRolesMetaData roles = ear.getSecurityRoles();
      assertEquals("There are 2 roles", 2, roles.size());
      SecurityRoleMetaData role0 = roles.get("role0");
      assertEquals("security-role0", role0.getId());
      assertEquals("role0", role0.getName());
      assertEquals("The 0 security role", role0.getDescriptions().value()[0].value());
      SecurityRoleMetaData role1 = roles.get("role1");
      assertEquals("security-role1", role1.getId());
      assertEquals("role1", role1.getName());
      assertEquals("The 1 security role", role1.getDescriptions().value()[0].value());
   }
   protected void assertLibraryDirectory(Ear5xMetaData ear)
   {
      assertEquals("lib0", ear.getLibraryDirectory());
   }
   protected void assertModules(Ear5xMetaData ear)
   {
      ModulesMetaData modules = ear.getModules();
      assertEquals(6, modules.size());
      ModuleMetaData connector = modules.get(0);
      assertEquals("connector0", connector.getId());
      assertEquals("META-INF/alt-ra.xml", connector.getAlternativeDD());
      ConnectorModuleMetaData connectorMD = (ConnectorModuleMetaData) connector.getValue();
      assertEquals("rar0.rar", connectorMD.getConnector());
      ModuleMetaData java = modules.get(1);
      assertEquals("java0", java.getId());
      assertEquals("META-INF/alt-application-client.xml", java.getAlternativeDD());
      JavaModuleMetaData javaMD = (JavaModuleMetaData)java.getValue();
      assertEquals("client0.jar", javaMD.getClientJar());
      ModuleMetaData ejb0 = modules.get(2);
      assertEquals("ejb0", ejb0.getId());
      assertEquals("META-INF/alt-ejb-jar.xml", ejb0.getAlternativeDD());
      EjbModuleMetaData ejb0MD = (EjbModuleMetaData)ejb0.getValue();
      assertEquals("ejb-jar0.jar", ejb0MD.getEjbJar());
      ModuleMetaData ejb1 = modules.get(3);
      assertEquals("ejb1", ejb1.getId());
      assertEquals("META-INF/alt-ejb-jar.xml", ejb1.getAlternativeDD());
      EjbModuleMetaData ejb1MD = (EjbModuleMetaData)ejb1.getValue();
      assertEquals("ejb-jar1.jar", ejb1MD.getEjbJar());
      ModuleMetaData web0 = modules.get(4);
      assertEquals("web0", web0.getId());
      assertEquals("WEB-INF/alt-web.xml", web0.getAlternativeDD());
      WebModuleMetaData web0MD = (WebModuleMetaData) web0.getValue();
      assertEquals("/web0", web0MD.getContextRoot());
      assertEquals("web-app0.war", web0MD.getWebURI());
      ModuleMetaData web1 = modules.get(5);
      assertEquals("web1", web1.getId());
      assertEquals("WEB-INF/alt-web.xml", web1.getAlternativeDD());
      WebModuleMetaData web1MD = (WebModuleMetaData) web1.getValue();
      assertEquals("/web1", web1MD.getContextRoot());
      assertEquals("web-app1.war", web1MD.getWebURI());
   }
}
