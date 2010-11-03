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
package org.jboss.metadata.ejb.jboss;

import javax.xml.bind.annotation.XmlType;

/**
 * A JBossSessionBeanJBoss50MetaData.
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="session-beanType", propOrder={"descriptionGroup", "ejbName", "mappedName", "jndiEnvironmentRefsGroup",
      "securityIdentity", "localBindings", "remoteBindings", "businessLocals", "businessRemotes", 
      "jndiName", "homeJndiName", "callByValue",
      "exceptionOnRollback", "timerPersistence", "configurationName", "invokerBindings", "securityProxy", "localJndiName",
      "localHomeJndiName", "jndiBindingPolicy", "clustered",
      "clusterConfig", "securityDomain", "methodAttributes", "depends", "annotations", "ignoreDependency", "aopDomainName",
      "cacheConfig", "poolConfig", "concurrent", "jndiRefs", "portComponent", "ejbTimeoutIdentity", "iorSecurityConfig"})
public class JBossSessionBeanJBoss50MetaData extends JBossSessionBeanMetaData
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 29928077451682118L;
}
