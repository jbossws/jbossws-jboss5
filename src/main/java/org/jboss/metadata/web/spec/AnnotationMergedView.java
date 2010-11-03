/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.metadata.web.spec;

import java.util.HashMap;

import org.jboss.metadata.javaee.spec.Environment;
import org.jboss.metadata.javaee.spec.EnvironmentRefsGroupMetaData;
import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;
import org.jboss.metadata.javaee.spec.SecurityRolesMetaData;


/**
 * Create a merged WebMetaData view from an xml + annotation views
 * @author Scott.Stark@jboss.org
 * @author Anil.Saldhana@jboss.org
 * @version $Revision$
 */
public class AnnotationMergedView
{
   public static void merge(WebMetaData merged, WebMetaData xml, WebMetaData annotation)
   {
      //Merge the servlets meta data
      ServletsMetaData servletsMetaData = new ServletsMetaData();
      merge(servletsMetaData,xml.getServlets(), annotation.getServlets());
      merged.setServlets(servletsMetaData);
      
      //Security Roles
      SecurityRolesMetaData securityRolesMetaData = new SecurityRolesMetaData();
      merge(securityRolesMetaData, xml.getSecurityRoles(), annotation.getSecurityRoles());
      merged.setSecurityRoles(securityRolesMetaData);
      
      //Env
      EnvironmentRefsGroupMetaData environmentRefsGroup = new EnvironmentRefsGroupMetaData();
      Environment xmlEnv = xml != null ? xml.getJndiEnvironmentRefsGroup() : null;
      Environment annEnv = annotation != null ? annotation.getJndiEnvironmentRefsGroup() : null;
      environmentRefsGroup.merge(xmlEnv,annEnv, "", "", false);
      merged.setJndiEnvironmentRefsGroup(environmentRefsGroup);
      
      //Message Destinations
      MessageDestinationsMetaData messageDestinations = new MessageDestinationsMetaData();
      messageDestinations.merge(xml.getMessageDestinations(), annotation.getMessageDestinations());
      merged.setMessageDestinations(messageDestinations);
      
      //merge annotation
      mergeIn(merged,annotation);
      //merge xml override
      mergeIn(merged,xml);
   }
   
   private static void merge(ServletsMetaData merged, ServletsMetaData xml,
         ServletsMetaData annotation)
   {
      HashMap<String,String> servletClassToName = new HashMap<String,String>();
      if(xml != null)
      {
         if(xml.getId() != null)
            merged.setId(xml.getId());
         for(ServletMetaData servlet : xml)
         {
            String className = servlet.getServletName();
            if(className != null)
            {
               // Use the unqualified name
               int dot = className.lastIndexOf('.');
               if(dot >= 0)
                  className = className.substring(dot+1);
               servletClassToName.put(className, servlet.getServletName()); 
            }
         }         
      }
      
      // First get the annotation beans without an xml entry
      if(annotation != null)
      {
         for(ServletMetaData servlet : annotation)
         {
            if(xml != null)
            {
               // This is either the servlet-name or the servlet-class simple name
               String servletName = servlet.getServletName();
               ServletMetaData match = xml.get(servletName);
               if(match == null)
               {
                  // Lookup by the unqualified servlet class
                  String xmlServletName = servletClassToName.get(servletName);
                  if(xmlServletName == null)
                     merged.add(servlet);
               }
            }
            else
            {
               merged.add(servlet);
            }
         }
      }
      // Now merge the xml and annotations
      if(xml != null)
      {
         for(ServletMetaData servlet : xml)
         {
            ServletMetaData annServlet = null;
            if(annotation != null)
            {
               String name = servlet.getServletName();
               annServlet = annotation.get(name);
               if(annServlet == null)
               {
                  // Lookup by the unqualified servlet class
                  String className = servlet.getServletClass();
                  if(className != null)
                  {
                     // Use the unqualified name
                     int dot = className.lastIndexOf('.');
                     if(dot >= 0)
                        className = className.substring(dot+1);
                     annServlet = annotation.get(className);
                  }
               }
            }
            // Merge
            ServletMetaData mergedServletMetaData = servlet;
            if(annServlet != null)
            {
               mergedServletMetaData = new ServletMetaData();
               mergedServletMetaData.merge(servlet, annServlet);
            }
            merged.add(mergedServletMetaData);
         }
      } 
   }
   
   private static void merge(SecurityRolesMetaData merged, SecurityRolesMetaData xml,
         SecurityRolesMetaData annotation)
   {
      merged.merge(xml, annotation); 
   }
   
   private static void mergeIn(WebMetaData merged, WebMetaData xml)
   {
      merged.setDTD("", xml.getDtdPublicId(), xml.getDtdSystemId());
      
      //Version
      if(xml.getVersion() != null)
         merged.setVersion(xml.getVersion());
      
      //Description Group
      if(xml.getDescriptionGroup() != null)
         merged.setDescriptionGroup(xml.getDescriptionGroup());
      
      //Merge the Params
      if(xml.getContextParams() != null)
         merged.setContextParams(xml.getContextParams());
      
      //Distributable
      if(xml.getDistributable() != null)
         merged.setDistributable(xml.getDistributable());
      
      //Session Config
      if(xml.getSessionConfig() != null)
         merged.setSessionConfig(xml.getSessionConfig());
      
      //Filters
      if(xml.getFilters() != null)
         merged.setFilters(xml.getFilters());
      
      //Error Pages
      if(xml.getErrorPages() != null)
         merged.setErrorPages(xml.getErrorPages());
      
      //JSP Config
      if(xml.getJspConfig() != null)
         merged.setJspConfig(xml.getJspConfig());
      
      //Listener meta data
      if(xml.getListeners() != null)
         merged.setListeners(xml.getListeners());
      
      //Login Config
      if(xml.getLoginConfig() != null)
         merged.setLoginConfig(xml.getLoginConfig());
      
      //Mime
      if(xml.getMimeMappings() != null)
         merged.setMimeMappings(xml.getMimeMappings());
      
      //Servlet Mapping
      if(xml.getServletMappings() != null)
         merged.setServletMappings(xml.getServletMappings());
      
      //Security Constraints
      if(xml.getSecurityContraints() != null)
         merged.setSecurityContraints(xml.getSecurityContraints());
      
      //Welcome Files
      if(xml.getWelcomeFileList() != null)
         merged.setWelcomeFileList(xml.getWelcomeFileList());
      
      //Local Encodings
      if(xml.getLocalEncodings() != null)
         merged.setLocalEncodings(xml.getLocalEncodings());
   }
}