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
package org.jboss.metadata.client.spec;

import org.jboss.metadata.javaee.spec.RemoteEnvironmentRefsGroupMetaData;

/**
 * Create a merged ApplicationClient5MetaData view from an xml + annotation views
 * @author Scott.Stark@jboss.org
 * @version $Revision:$
 */
public class AnnotationMergedView
{
   public static void merge(ApplicationClient5MetaData merged, ApplicationClientMetaData xml, ApplicationClientMetaData annotation)
   {
      if(xml != null)
      {
         if(xml.getVersion() != null)
            merged.setVersion(xml.getVersion());
         merged.setDTD("", xml.getDtdPublicId(), xml.getDtdSystemId());
         if(xml.getCallbackHandler() != null)
            merged.setCallbackHandler(xml.getCallbackHandler());
         merged.setMetadataComplete(xml.isMetadataComplete());

         if(xml.getMessageDestinations() != null)
            merged.setMessageDestinations(xml.getMessageDestinations());
      }

      // Merge the remote env
      RemoteEnvironmentRefsGroupMetaData environmentRefsGroup = new RemoteEnvironmentRefsGroupMetaData();
      RemoteEnvironmentRefsGroupMetaData xmlEnv = xml != null ? xml.getJndiEnvironmentRefsGroup() : null;
      RemoteEnvironmentRefsGroupMetaData annEnv = annotation != null ? annotation.getJndiEnvironmentRefsGroup() : null;
      environmentRefsGroup.merge(xmlEnv, annEnv, "application.xml", "annotations", false);
      merged.setJndiEnvironmentRefsGroup(environmentRefsGroup);
   }
}
