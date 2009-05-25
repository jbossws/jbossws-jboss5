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
package org.jboss.wsf.container.jboss50.deployer;

import java.io.File;

import javax.management.JMException;
import javax.management.ObjectName;

import org.jboss.wsf.common.management.AbstractServerConfig;
import org.jboss.wsf.common.management.AbstractServerConfigMBean;

/**
 * A ServerConfig for AS <= 5.1.0
 * 
 * @author alessio.soldano@jboss.com
 * @author Thomas.Diesler@jboss.org
 *
 */
public class ServerConfigImpl extends AbstractServerConfig implements AbstractServerConfigMBean
{

   public File getServerTempDir()
   {
      return this.getDirFromServerConfig("ServerTempDir");
   }
   
   public File getHomeDir()
   {
      return this.getDirFromServerConfig("HomeDir");
   }

   public File getServerDataDir()
   {
      return this.getDirFromServerConfig("ServerDataDir");
   }
   
   /**
    * Obtains the specified attribute from the server configuration,
    * represented as a {@link File}.
    *  
    * @param attributeName
    * @return
    * @author ALR
    */
   protected File getDirFromServerConfig(final String attributeName)
   {
      // Define the ON to invoke upon
      final ObjectName on = OBJECT_NAME_SERVER_CONFIG;

      // Get the URL location
      File location = null;
      try
      {
         location = (File) getMbeanServer().getAttribute(on, attributeName);
      }
      catch (final JMException e)
      {
         throw new RuntimeException("Could not obtain attribute " + attributeName + " from " + on, e);
      }
      return location;
   }
}
