/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata.javaee.spec;

import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;


/**
 * A port-component-ref type
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
@XmlType(name="port-component-refType", propOrder={"serviceEndpointInterface", "enableMtom", "portComponentLink"})
public class PortComponentRef extends IdMetaDataImpl
{
   private static final long serialVersionUID = 1;

   private String serviceEndpointInterface;
   private boolean enableMtom;
   private String portComponentLink;

   public boolean isEnableMtom()
   {
      return enableMtom;
   }
   public void setEnableMtom(boolean enableMtom)
   {
      this.enableMtom = enableMtom;
   }
   public String getPortComponentLink()
   {
      return portComponentLink;
   }
   public void setPortComponentLink(String portComponentLink)
   {
      this.portComponentLink = portComponentLink;
   }
   public String getServiceEndpointInterface()
   {
      return serviceEndpointInterface;
   }
   public void setServiceEndpointInterface(String serviceEndpointInterface)
   {
      this.serviceEndpointInterface = serviceEndpointInterface;
   }

   public PortComponentRef merge(PortComponentRef ref)
   {
      PortComponentRef merged = new PortComponentRef();
      merged.merge(this, ref);
      return merged;
   }
   public void merge(PortComponentRef override, PortComponentRef original)
   {
      if(override != null && override.serviceEndpointInterface != null)
         serviceEndpointInterface = override.serviceEndpointInterface;
      else if(original != null && original.serviceEndpointInterface != null)
         serviceEndpointInterface = original.serviceEndpointInterface;
      if(override != null && override.portComponentLink != null)
         portComponentLink = override.portComponentLink;
      else if(original != null && original.portComponentLink != null)
         portComponentLink = original.portComponentLink;
      if(override != null && override.enableMtom)
         enableMtom = override.enableMtom;
      else if(original != null && original.enableMtom)
         enableMtom = original.enableMtom;
   }
}
