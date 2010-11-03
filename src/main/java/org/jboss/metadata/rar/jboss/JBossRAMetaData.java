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
package org.jboss.metadata.rar.jboss;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.metadata.javaee.spec.JavaEEMetaDataConstants;
import org.jboss.metadata.javaee.support.IdMetaDataImpl;

import org.jboss.xb.annotations.JBossXmlSchema;

/**
 * A JBossRAMetaData.
 * 
 * @author <a href="weston.price@jboss.com">Weston Price</a>
 * @author <a href="vicky.kak@jboss.com">Vicky Kak</a>
 * @author Jeff Zhang
 * @version $Revision: 75672 $
 */
@XmlRootElement(name="jboss-ra", namespace="http://www.jboss.org/schema/ra")
@JBossXmlSchema(
      xmlns={@XmlNs(namespaceURI = "http://www.jboss.org/schema/ra", prefix = "ra")},
      ignoreUnresolvedFieldOrClass=false,
      namespace="http://www.jboss.org/schema/ra",
      elementFormDefault=XmlNsForm.QUALIFIED,
      normalizeSpace=true)
public class JBossRAMetaData extends IdMetaDataImpl
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -8041048198795930659L;
   
   private List<RaConfigPropertyMetaData> raConfigProps;

   @XmlElement(name="ra-config-property")
   public void setRaConfigProps(List<RaConfigPropertyMetaData> raConfigProps) {
      this.raConfigProps = raConfigProps;
   }

   public List<RaConfigPropertyMetaData> getRaConfigProps() {
      return raConfigProps;
   }
   
 
}
