/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata.ear.spec;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.ear.jboss.ServiceModuleMetaData;
import org.jboss.metadata.javaee.support.NamedMetaData;
import org.jboss.xb.annotations.JBossXmlChild;
import org.jboss.xb.annotations.JBossXmlChildren;

/**
 * Application module metadata
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
@XmlType(name="moduleType", propOrder={"value", "alternativeDD"})
/*@JBossXmlChildren
({
   @JBossXmlChild(name="connector", type=ConnectorModuleMetaData.class),
   @JBossXmlChild(name="ejb", type=EjbModuleMetaData.class),
   @JBossXmlChild(name="java", type=JavaModuleMetaData.class),
   @JBossXmlChild(name="web", type=WebModuleMetaData.class),
   @JBossXmlChild(name="service", type=ServiceModuleMetaData.class),
   @JBossXmlChild(name="har", type=ServiceModuleMetaData.class)   
})
*/public class ModuleMetaData extends NamedMetaData
{
   private static final long serialVersionUID = 1;
   private AbstractModule module;
   private String altDD;

   public enum ModuleType {Connector, Client, Ejb, Service, Web};

   public AbstractModule getValue()
   {
      return module;
   }
   
   public void setValue(AbstractModule value)
   {
      this.module = value;
      // Set the mappable name to the module file name
      super.setName(value.getFileName());
   }
   
   public String getAlternativeDD()
   {
      return altDD;
   }
   
   @XmlElement(name="alt-dd")
   public void setAlternativeDD(String altDD)
   {
      this.altDD = altDD;
   }

   @XmlTransient
   public String getFileName()
   {
      String fileName = null;
      if (module != null)
         fileName = module.getFileName();
      return fileName;
   }
   @XmlTransient
   public ModuleType getType()
   {
      ModuleType type = ModuleType.Client;
      if (module instanceof EjbModuleMetaData)
         type = ModuleType.Ejb;
      else if(module instanceof ConnectorModuleMetaData)
         type = ModuleType.Connector;
      else if(module instanceof JavaModuleMetaData)
         type = ModuleType.Client;
      else if(module instanceof WebModuleMetaData)
         type = ModuleType.Web;
      else if(module instanceof ServiceModuleMetaData)
         type = ModuleType.Service;
      return type;
   }
}
