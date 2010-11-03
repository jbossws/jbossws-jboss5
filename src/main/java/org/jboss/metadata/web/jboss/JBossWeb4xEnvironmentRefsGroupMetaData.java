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
package org.jboss.metadata.web.jboss;

import org.jboss.metadata.ejb.jboss.JBossEnvironmentRefsGroupMetaData;
import org.jboss.xb.annotations.JBossXmlModelGroup;

/**
 * A JBossWeb4xEnvironmentRefsGroupMetaData.
 * This is to address ordering issues in jboss-web_4.x.
 * 
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
@JBossXmlModelGroup(name="jndiEnvironmentRefsGroup",
      propOrder={"resourceEnvironmentReferences", "resourceReferences", "ejbReferences", "ejbLocalReferences",
      "messageDestinationReferences", "serviceReferences"})
public class JBossWeb4xEnvironmentRefsGroupMetaData extends JBossEnvironmentRefsGroupMetaData
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 1L;

}
