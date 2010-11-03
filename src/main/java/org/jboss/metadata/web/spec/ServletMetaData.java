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
package org.jboss.metadata.web.spec;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.javaee.spec.RunAsMetaData;
import org.jboss.metadata.javaee.spec.SecurityRoleRefsMetaData;
import org.jboss.metadata.javaee.support.MergeableMetaData;
import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptionGroup;

/**
 * web-app/servlet metadata
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
@XmlType(name="servletType", propOrder={"descriptionGroup", "servletName", "servletClass", "jspFile", "initParam", "loadOnStartup",
      "runAs", "securityRoleRefs"})
public class ServletMetaData extends NamedMetaDataWithDescriptionGroup
   implements MergeableMetaData<ServletMetaData>
{
   private static final long serialVersionUID = 1;
   
   private static final int loadOnStartupDefault = -1;

   private String servletClass;
   private String jspFile;
   /** The servlet init-params */
   private List<ParamValueMetaData> initParam;
   private int loadOnStartup = loadOnStartupDefault;
   private RunAsMetaData runAs;
   /** The security role ref */
   private SecurityRoleRefsMetaData securityRoleRefs;

   public String getServletName()
   {
      return getName();
   }
   public void setServletName(String name)
   {
      super.setName(name);
   }

   public String getServletClass()
   {
      return servletClass;
   }
   public void setServletClass(String servletClass)
   {
      this.servletClass = servletClass;
   }

   public List<ParamValueMetaData> getInitParam()
   {
      return initParam;
   }
   public void setInitParam(List<ParamValueMetaData> initParam)
   {
      this.initParam = initParam;
   }
   public String getJspFile()
   {
      return jspFile;
   }
   public void setJspFile(String jspFile)
   {
      this.jspFile = jspFile;
   }
   public int getLoadOnStartup()
   {
      return loadOnStartup;
   }
   public void setLoadOnStartup(int loadOnStartup)
   {
      this.loadOnStartup = loadOnStartup;
   }
   public RunAsMetaData getRunAs()
   {
      return runAs;
   }
   public void setRunAs(RunAsMetaData runAs)
   {
      this.runAs = runAs;
   }
   public SecurityRoleRefsMetaData getSecurityRoleRefs()
   {
      return securityRoleRefs;
   }
   @XmlElement(name="security-role-ref")
   public void setSecurityRoleRefs(SecurityRoleRefsMetaData securityRoleRefs)
   {
      this.securityRoleRefs = securityRoleRefs;
   }

   public ServletMetaData merge(ServletMetaData original)
   {
      ServletMetaData merged = new ServletMetaData();
      merged.merge(this, original);
      return merged;
   }
   public void merge(ServletMetaData override, ServletMetaData original)
   {
      super.merge(override, original);
      if(override != null && override.servletClass != null)
         setServletClass(override.servletClass);
      else if(original != null && original.servletClass != null)
         setServletClass(original.servletClass);
      if(override != null && override.jspFile != null)
         setJspFile(override.jspFile);
      else if(original != null && original.jspFile != null)
         setJspFile(original.jspFile);
      if(override != null && override.initParam != null)
         setInitParam(override.initParam);
      else if(original != null && original.initParam != null)
         setInitParam(original.initParam);
      if(override != null && override.loadOnStartup != loadOnStartupDefault)
         setLoadOnStartup(override.loadOnStartup);
      else if(original != null && original.loadOnStartup != loadOnStartupDefault)
         setLoadOnStartup(original.loadOnStartup);
      if(override != null && override.runAs != null)
         setRunAs(override.runAs);
      else if(original != null && original.runAs != null)
         setRunAs(original.runAs);
      if(override != null && override.securityRoleRefs != null)
         setSecurityRoleRefs(override.securityRoleRefs);
      else if(original != null && original.securityRoleRefs != null)
         setSecurityRoleRefs(original.securityRoleRefs);
   }

   public String toString()
   {
      StringBuilder tmp = new StringBuilder("ServletMetaData(id=");
      tmp.append(getId());
      tmp.append(",servletClass=");
      tmp.append(servletClass);
      tmp.append(",jspFile=");
      tmp.append(jspFile);
      tmp.append(",loadOnStartup=");
      tmp.append(loadOnStartup);
      tmp.append(",runAs=");
      tmp.append(runAs);
      tmp.append(')');
      return tmp.toString();
   }
}
