/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
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
package org.jboss.test.metadata.jbmeta66.unit;

import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.JBossGenericBeanMetaData;
import org.jboss.metadata.ejb.jboss.JBossMetaData;
import org.jboss.metadata.ejb.jboss.JBossSessionBeanMetaData;
import org.jboss.metadata.ejb.spec.EjbJar30MetaData;
import org.jboss.metadata.ejb.spec.EjbJarMetaData;
import org.jboss.test.metadata.ejb.AbstractEJBEverythingTest;
import org.jboss.xb.binding.sunday.unmarshalling.DefaultSchemaResolver;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBindingResolver;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class GenericBeanWithHomeTestCase extends AbstractEJBEverythingTest
{
   public static SchemaBindingResolver initResolver()
   {
      DefaultSchemaResolver resolver = new DefaultSchemaResolver();
//      resolver.addClassBindingForLocation("ejb-jar_2_0.dtd", EjbJar20MetaData.class);
//      resolver.addClassBindingForLocation("ejb-jar_2_1.xsd", EjbJar21MetaData.class);
      resolver.addClassBindingForLocation("ejb-jar_3_0.xsd", EjbJar30MetaData.class);
//      resolver.addClassBindingForLocation("jboss_3_0.dtd", JBoss50DTDMetaData.class);
//      resolver.addClassBindingForLocation("jboss_3_2.dtd", JBoss50DTDMetaData.class);
//      resolver.addClassBindingForLocation("jboss_4_0.dtd", JBoss50DTDMetaData.class);
//      resolver.addClassBindingForLocation("jboss_4_2.dtd", JBoss50DTDMetaData.class);
//      resolver.addClassBindingForLocation("jboss_5_0.dtd", JBoss50DTDMetaData.class);
      resolver.addClassBindingForLocation("jboss_5_0.xsd", JBoss50MetaData.class);
      // Set the JBoss50DTDMetaData class as the default for the jboss root element
//      resolver.addClassBindingForLocation("jboss", JBoss50DTDMetaData.class);
      // Workaround wildard resolution slowness
      resolver.addClassBinding("http://www.jboss.com/xml/ns/javaee", JBoss50MetaData.class);
      return resolver;
   }

   public GenericBeanWithHomeTestCase(String name)
   {
      super(name);
   }

   public void testMerge() throws Exception
   {
      EjbJarMetaData specMetaData = unmarshal("ejb-jar.xml", EjbJarMetaData.class);
      JBossMetaData metaData = unmarshal("jboss.xml", JBossMetaData.class);
      
      JBossMetaData mergedMetaData = new JBossMetaData();
      mergedMetaData.merge(metaData, specMetaData);
      
      JBossSessionBeanMetaData bean = (JBossSessionBeanMetaData) mergedMetaData.getEnterpriseBean("TestBean");
      assertEquals("TestBeanHomeJndiName", bean.getHomeJndiName());
      assertEquals("TestBeanLocalHomeJndiName", bean.getLocalHomeJndiName());
   }
   
   public void testParse() throws Exception
   {
      JBossMetaData jboss = unmarshal("jboss.xml", JBossMetaData.class);
      JBossGenericBeanMetaData bean = (JBossGenericBeanMetaData) jboss.getEnterpriseBean("TestBean");
      assertEquals("TestBeanHomeJndiName", bean.getHomeJndiName());
      assertEquals("TestBeanLocalHomeJndiName", bean.getLocalHomeJndiName());
   }
}
