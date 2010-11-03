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
package org.jboss.test.metadata.common;

import org.jboss.metadata.client.jboss.JBossClient5DTDMetaData;
import org.jboss.metadata.client.jboss.JBossClient5MetaData;
import org.jboss.metadata.client.spec.ApplicationClient14DTDMetaData;
import org.jboss.metadata.client.spec.ApplicationClient14MetaData;
import org.jboss.metadata.client.spec.ApplicationClient5MetaData;
import org.jboss.metadata.ear.jboss.JBoss50AppMetaData;
import org.jboss.metadata.ear.jboss.JBoss50DTDAppMetaData;
import org.jboss.metadata.ear.spec.Ear13DTDMetaData;
import org.jboss.metadata.ear.spec.Ear14MetaData;
import org.jboss.metadata.ear.spec.Ear50MetaData;
import org.jboss.metadata.ejb.jboss.JBoss50DTDMetaData;
import org.jboss.metadata.ejb.jboss.JBoss50MetaData;
import org.jboss.metadata.ejb.jboss.JBoss51MetaData;
import org.jboss.metadata.ejb.spec.EjbJar1xMetaData;
import org.jboss.metadata.ejb.spec.EjbJar20MetaData;
import org.jboss.metadata.ejb.spec.EjbJar21MetaData;
import org.jboss.metadata.ejb.spec.EjbJar30MetaData;
import org.jboss.metadata.jpa.spec.PersistenceMetaData;
import org.jboss.metadata.rar.jboss.JBossRAMetaData;
import org.jboss.metadata.rar.jboss.mcf.ConnectionFactoryDeploymentGroup;
import org.jboss.metadata.rar.jboss.mcf.ManagedConnectionFactoryDeploymentGroup;
import org.jboss.metadata.rar.spec.JCA15MetaData;
import org.jboss.metadata.rar.spec.JCA16MetaData;
import org.jboss.metadata.web.jboss.JBoss4xDTDWebMetaData;
import org.jboss.metadata.web.jboss.JBoss50DTDWebMetaData;
import org.jboss.metadata.web.spec.Web23MetaData;
import org.jboss.metadata.web.spec.Web24MetaData;
import org.jboss.metadata.web.spec.Web25MetaData;
import org.jboss.xb.binding.resolver.MultiClassSchemaResolver;
import org.jboss.xb.binding.resolver.MutableSchemaResolver;

/**
 * A MetaDataSchemaResolverFactory.
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class MetaDataSchemaResolverFactory
{
   public static MutableSchemaResolver createSchemaResolver()
   {
      MultiClassSchemaResolver resolver = new MultiClassSchemaResolver();
      
      // EJB
      resolver.mapLocationToClass("ejb-jar_1_1.dtd", EjbJar1xMetaData.class);
      resolver.mapLocationToClass("ejb-jar_2_0.dtd", EjbJar20MetaData.class);
      resolver.mapLocationToClass("ejb-jar_2_1.xsd", EjbJar21MetaData.class);
      resolver.mapLocationToClass("ejb-jar_3_0.xsd", EjbJar30MetaData.class);
      resolver.mapLocationToClass("jboss", JBoss50DTDMetaData.class);
      resolver.mapLocationToClass("jboss_3_0.dtd", JBoss50DTDMetaData.class);
      resolver.mapLocationToClass("jboss_3_2.dtd", JBoss50DTDMetaData.class);
      resolver.mapLocationToClass("jboss_4_0.dtd", JBoss50DTDMetaData.class);
      resolver.mapLocationToClass("jboss_4_2.dtd", JBoss50DTDMetaData.class);
      resolver.mapLocationToClass("jboss_5_0.dtd", JBoss50DTDMetaData.class);
      resolver.mapLocationToClass("jboss_5_0.xsd", JBoss50MetaData.class);
      resolver.mapLocationToClass("jboss_5_1.xsd", JBoss51MetaData.class);
      
      // client
      resolver.mapLocationToClass("application-client_1_3.dtd", ApplicationClient14DTDMetaData.class);
      resolver.mapLocationToClass("application-client_1_4.xsd", ApplicationClient14MetaData.class);
      resolver.mapLocationToClass("application-client_5.xsd", ApplicationClient5MetaData.class);
      resolver.mapLocationToClass("jboss-client_4_0.dtd", JBossClient5DTDMetaData.class);
      resolver.mapLocationToClass("jboss-client_4_2.dtd", JBossClient5DTDMetaData.class);
      resolver.mapLocationToClass("jboss-client_5_0.dtd", JBossClient5DTDMetaData.class);
      resolver.mapLocationToClass("jboss-client_5_0.xsd", JBossClient5MetaData.class);
      resolver.mapLocationToClass("jboss-client", JBossClient5DTDMetaData.class);

      // WEB
      resolver.mapLocationToClass("web-app_2_3.dtd", Web23MetaData.class);
      resolver.mapLocationToClass("web-app_2_4.xsd", Web24MetaData.class);
      resolver.mapLocationToClass("web-app_2_5.xsd", Web25MetaData.class);
      resolver.mapLocationToClass("jboss-web", JBoss50DTDWebMetaData.class);
      resolver.mapLocationToClass("jboss-web_4_0.dtd", JBoss4xDTDWebMetaData.class);
      resolver.mapLocationToClass("jboss-web_4_2.dtd", JBoss4xDTDWebMetaData.class);
      resolver.mapLocationToClass("jboss-web_5_0.dtd", JBoss50DTDWebMetaData.class);

      // EAR
      resolver.mapLocationToClass("application", Ear13DTDMetaData.class);
      resolver.mapLocationToClass("application_1_2.dtd", Ear13DTDMetaData.class);
      resolver.mapLocationToClass("application_1_3.dtd", Ear13DTDMetaData.class);
      resolver.mapLocationToClass("application_1_4.xsd", Ear14MetaData.class);
      resolver.mapLocationToClass("application_5.xsd", Ear50MetaData.class);
      resolver.mapLocationToClass("jboss-app_3_2.dtd", JBoss50DTDAppMetaData.class);
      resolver.mapLocationToClass("jboss-app_4_0.dtd", JBoss50DTDAppMetaData.class);
      resolver.mapLocationToClass("jboss-app_4_2.dtd", JBoss50DTDAppMetaData.class);
      resolver.mapLocationToClass("jboss-app_5_0.dtd", JBoss50DTDAppMetaData.class);
      resolver.mapLocationToClass("jboss-app_5_0.xsd", JBoss50AppMetaData.class);

      // JPA
      resolver.mapLocationToClass("persistence_1_0.xsd", PersistenceMetaData.class);
      
      // RA
      resolver.mapLocationToClass("connector_1_5.xsd", JCA15MetaData.class);
      resolver.mapLocationToClass("connector_1_6.xsd", JCA16MetaData.class);
      resolver.mapLocationToClass("jboss-ra_1_0.xsd", JBossRAMetaData.class);
      resolver.mapLocationToClasses("jboss-ds_5_0.xsd", ManagedConnectionFactoryDeploymentGroup.class, ConnectionFactoryDeploymentGroup.class);

      return resolver;
   }
}
