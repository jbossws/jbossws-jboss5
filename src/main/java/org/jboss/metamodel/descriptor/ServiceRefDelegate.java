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
package org.jboss.metamodel.descriptor;

// $Id$

import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.Referenceable;

import org.jboss.logging.Logger;
import org.jboss.wsf.spi.SPIProvider;
import org.jboss.wsf.spi.SPIProviderResolver;
import org.jboss.wsf.spi.deployment.UnifiedVirtualFile;
import org.jboss.wsf.spi.serviceref.ServiceRefElement;
import org.jboss.wsf.spi.serviceref.ServiceRefHandler;
import org.jboss.wsf.spi.serviceref.ServiceRefHandlerFactory;
import org.jboss.wsf.spi.metadata.j2ee.serviceref.UnifiedServiceRefMetaData;
import org.jboss.xb.binding.UnmarshallingContext;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;

/**
 * Factory for ServiceRefHandler
 * 
 * @author Thomas.Diesler@jboss.org
 * @since 05-May-2004
 */
@Deprecated
public class ServiceRefDelegate implements ServiceRefHandler
{
   public ServiceRefDelegate()
   {
   }

   public Referenceable createReferenceable(UnifiedServiceRefMetaData serviceRefUMDM)
   {
      throw new UnsupportedOperationException("Not supported in AS 5 MD");
   }
   public UnifiedServiceRefMetaData newServiceRefMetaData()
   {
      throw new UnsupportedOperationException("This have been deprecated");
   }
}
