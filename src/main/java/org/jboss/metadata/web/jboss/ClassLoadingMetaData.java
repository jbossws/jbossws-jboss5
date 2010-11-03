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
package org.jboss.metadata.web.jboss;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

import org.jboss.metadata.common.jboss.LoaderRepositoryMetaData;


/**
 * A ClassLoadingMetaData.
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class ClassLoadingMetaData implements Serializable
{
   private static final long serialVersionUID = 1L;

   private boolean java2ClassLoadingCompliance;
   private boolean wasJava2ClassLoadingComplianceSet;
   private LoaderRepositoryMetaData loaderRepository;

   public boolean wasJava2ClassLoadingComplianceSet()
   {
      return wasJava2ClassLoadingComplianceSet;
   }
   @XmlAttribute(name="java2ClassLoadingCompliance")
   public boolean isJava2ClassLoadingCompliance()
   {
      return java2ClassLoadingCompliance;
   }
   
   public void setJava2ClassLoadingCompliance(boolean value)
   {
      this.java2ClassLoadingCompliance = value;
      wasJava2ClassLoadingComplianceSet = true;
   }

   public LoaderRepositoryMetaData getLoaderRepository()
   {
      return loaderRepository;
   }
   
   public void setLoaderRepository(LoaderRepositoryMetaData loaderRepository)
   {
      this.loaderRepository = loaderRepository;
   }
}
