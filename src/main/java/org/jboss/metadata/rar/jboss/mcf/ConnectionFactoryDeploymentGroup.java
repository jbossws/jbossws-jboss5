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
package org.jboss.metadata.rar.jboss.mcf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.spec.JavaEEMetaDataConstants;
import org.jboss.xb.annotations.JBossXmlSchema;

/**
 * A ConnectionFactoryDeploymentGroup.
 * 
 * @author Jeff Zhang
 * @version $Revision$
 */
@XmlRootElement(name="connection-factories", namespace=JavaEEMetaDataConstants.JBOSS_NS)
@JBossXmlSchema(
      xmlns={@XmlNs(namespaceURI = JavaEEMetaDataConstants.JBOSS_NS, prefix = "jboss")},
      ignoreUnresolvedFieldOrClass=false,
      namespace=JavaEEMetaDataConstants.JBOSS_NS,
      elementFormDefault=XmlNsForm.QUALIFIED,
      normalizeSpace=true)
@XmlType(name="connection-factories", namespace="")
public class ConnectionFactoryDeploymentGroup implements Serializable
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -7650097478654698297L;

   /** The deployments   */
   private List<ManagedConnectionFactoryDeploymentMetaData> deployments = new ArrayList<ManagedConnectionFactoryDeploymentMetaData>();
   
   /** The loader repository config 
   @XmlElement(name="loader-repository")
   @XmlJavaTypeAdapter(LoaderRepositoryAdapter.class)
   private LoaderRepositoryConfig loaderRepositoryConfig;
   */
   
   public void addManagedConnectionFactoryDeployment(ManagedConnectionFactoryDeploymentMetaData deployment)
   {
      this.deployments.add(deployment);
   }
   
   public List<ManagedConnectionFactoryDeploymentMetaData> getDeployments()
   {
      return deployments;
   }

   /**
    * Set the deployments.
    * 
    * @param deployments The deployments to set.
    */

   @XmlElements({@XmlElement(name="no-tx-connection-factory", type=NoTxConnectionFactoryDeploymentMetaData.class),
      @XmlElement(name="tx-connection-factory",type=TxConnectionFactoryDeploymentMetaData.class)})
   public void setDeployments(List<ManagedConnectionFactoryDeploymentMetaData> deployments)
   {
      this.deployments = deployments;
   }
}
