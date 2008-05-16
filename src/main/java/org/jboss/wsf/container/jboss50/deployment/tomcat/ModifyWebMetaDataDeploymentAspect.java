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
package org.jboss.wsf.container.jboss50.deployment.tomcat;

//$Id$

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.web.jboss.JBossWebMetaData;
import org.jboss.metadata.web.spec.ListenerMetaData;
import org.jboss.metadata.web.spec.ServletMetaData;
import org.jboss.wsf.spi.deployment.Deployment;
import org.jboss.wsf.spi.deployment.DeploymentAspect;
import org.jboss.wsf.spi.deployment.Endpoint;
import org.jboss.wsf.spi.transport.HttpSpec;
import org.jboss.wsf.spi.WSFRuntime;

/**
 * A deployer that modifies the web.xml meta data 
 *
 * @author Thomas.Diesler@jboss.org
 * @since 25-Apr-2007
 */
public class ModifyWebMetaDataDeploymentAspect extends DeploymentAspect
{
   @Override
   public void create(Deployment dep, WSFRuntime runtime)
   {
      String servletClass = (String)dep.getProperty(HttpSpec.PROPERTY_WEBAPP_SERVLET_CLASS);
      if (servletClass == null)
         throw new IllegalStateException("Cannot obtain context property: " + HttpSpec.PROPERTY_WEBAPP_SERVLET_CLASS);

      modifyServletClass(dep, servletClass);

      String listenerClass = (String)dep.getProperty(HttpSpec.PROPERTY_WEBAPP_SERVLET_CONTEXT_LISTENER);
      if (listenerClass != null)
         modifyListener(dep, listenerClass);
      
      Map<String, String> contextParams = (Map<String, String>)dep.getProperty(HttpSpec.PROPERTY_WEBAPP_CONTEXT_PARAMETERS);
      if (contextParams != null)
         modifyContextParams(dep, contextParams);
   }

   private void modifyServletClass(Deployment dep, String servletClass)
   {
      JBossWebMetaData webMetaData = dep.getAttachment(JBossWebMetaData.class);
      if (webMetaData != null)
      {
         for (ServletMetaData servlet : webMetaData.getServlets())
         {
            String endpointClass = servlet.getServletClass();

            // JSP
            if (endpointClass == null || endpointClass.length() == 0)
            {
               log.debug("Ignore servlet class: " + endpointClass);
               continue;
            }

            // Nothing to do if we have an <init-param>
            if (!isAlreadyModified(servlet) && !isJavaxServlet(endpointClass, dep.getInitialClassLoader()))
            {
               servlet.setServletClass(servletClass);
               ParamValueMetaData initParam = new ParamValueMetaData();
               initParam.setParamName(Endpoint.SEPID_DOMAIN_ENDPOINT);
               initParam.setParamValue(endpointClass);
               List<ParamValueMetaData> initParams = servlet.getInitParam();
               if (initParams == null)
               {
                  initParams = new ArrayList<ParamValueMetaData>();
                  servlet.setInitParam(initParams);
               }
               initParams.add(initParam);
            }
         }
      }
   }

   private void modifyListener(Deployment dep, String listenerClass)
   {
      JBossWebMetaData webMetaData = dep.getAttachment(JBossWebMetaData.class);
      if (webMetaData != null)
      {
         ListenerMetaData listener = new ListenerMetaData();
         listener.setListenerClass(listenerClass);
         List<ListenerMetaData> listeners = webMetaData.getListeners();
         if (listeners == null)
         {
            listeners = new ArrayList<ListenerMetaData>();
            webMetaData.setListeners(listeners);
         }
         listeners.add(listener);
      }
   }

   private void modifyContextParams(Deployment dep, Map<String, String> newParams)
   {
      JBossWebMetaData webMetaData = dep.getAttachment(JBossWebMetaData.class);
      if (webMetaData != null)
      {
         for (Map.Entry<String, String> entry : newParams.entrySet())
         {
            ParamValueMetaData ctxParam = new ParamValueMetaData();
            ctxParam.setParamName(entry.getKey());
            ctxParam.setParamValue(entry.getValue());
            List<ParamValueMetaData> contextParams = webMetaData.getContextParams();
            if (contextParams == null)
            {
               contextParams = new ArrayList<ParamValueMetaData>();
               webMetaData.setContextParams(contextParams);
            }
            contextParams.add(ctxParam);
         }
      }
   }

   private boolean isJavaxServlet(String orgServletClass, ClassLoader loader)
   {
      boolean isServlet = false;
      if (loader != null)
      {
         try
         {
            Class servletClass = loader.loadClass(orgServletClass);
            isServlet = javax.servlet.Servlet.class.isAssignableFrom(servletClass);
            if (isServlet == true)
            {
               log.info("Ignore servlet: " + orgServletClass);
            }
         }
         catch (ClassNotFoundException e)
         {
            log.warn("Cannot load servlet class: " + orgServletClass);
         }
      }
      return isServlet;
   }

   private boolean isAlreadyModified(ServletMetaData servlet)
   {
      List<ParamValueMetaData> initParams = servlet.getInitParam();
      if (initParams != null)
      {
         for (ParamValueMetaData param : initParams)
         {
            if (Endpoint.SEPID_DOMAIN_ENDPOINT.equals(param.getParamName()))
               return true;
         }
      }
      return false;
   }
}
