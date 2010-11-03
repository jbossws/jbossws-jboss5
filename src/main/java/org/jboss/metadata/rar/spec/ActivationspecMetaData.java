/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata.rar.spec;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * Activationspec meta data
 *
 * @author Jeff Zhang
 * @version $Revision: $
 */
@XmlType(name="activationspecType", propOrder={"asClass", "requiredConfigProps", "configProps"})
public class ActivationspecMetaData extends IdMetaDataImpl
{
   private static final long serialVersionUID = -1583292998359427984L;
   
   private String asClass;
   private List<RequiredConfigPropertyMetaData> requiredConfigProps;
   private List<ConfigPropertyMetaData> configProps;

   @XmlElement(name="activationspec-class", required=true)
   public void setAsClass(String asClass) {
      this.asClass = asClass;
   }

   public String getAsClass() {
      return asClass;
   }

   @XmlElement(name="required-config-property")
   public void setRequiredConfigProps(List<RequiredConfigPropertyMetaData> requiredConfigProps) {
      this.requiredConfigProps = requiredConfigProps;
   }

   public List<RequiredConfigPropertyMetaData> getRequiredConfigProps() {
      return requiredConfigProps;
   }

   public List<ConfigPropertyMetaData> getConfigProps()
   {
      return configProps;
   }
   
   @XmlElement(name="config-property")
   public void setConfigProps(List<ConfigPropertyMetaData> configProps)
   {
      this.configProps = configProps;
   }

}
