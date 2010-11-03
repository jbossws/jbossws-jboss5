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
package org.jboss.metadata.annotation.creator.ws;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.spec.JavaEEMetaDataConstants;
import org.jboss.metadata.javaee.spec.ServiceReferenceHandlerChainsMetaData;
import org.jboss.xb.annotations.JBossXmlSchema;

/**
 * Defines a XmlRoot for ServiceReferenceHandlerChainsMetaData, as it needs to be
 * parsed for @HandlerChain.
 * 
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
@XmlRootElement(name= "handler-chains", namespace = JavaEEMetaDataConstants.JAVAEE_NS)
@JBossXmlSchema(
   xmlns={@XmlNs(namespaceURI = JavaEEMetaDataConstants.JAVAEE_NS, prefix = "javaee")},
   ignoreUnresolvedFieldOrClass=false,
   namespace=JavaEEMetaDataConstants.JAVAEE_NS,
   elementFormDefault=XmlNsForm.QUALIFIED)
@XmlType(name = "service-ref_handler-chainsType", propOrder={"handlers"})
public class ServiceReferenceHandlerChainsWrapper extends ServiceReferenceHandlerChainsMetaData
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 1;
   
   public ServiceReferenceHandlerChainsWrapper()
   {
      super();
   }
   
}

