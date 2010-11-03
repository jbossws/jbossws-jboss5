/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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

import org.jboss.annotation.javaee.Icon;
import org.jboss.metadata.javaee.support.LanguageMetaData;
import org.jboss.xb.annotations.JBossXmlNsPrefix;

/**
 * IconImpl.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="iconType", propOrder={"smallIcon", "largeIcon"})
public class IconImpl extends LanguageMetaData implements Icon
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -8544489136833248714L;

   /** The small icon */
   private String smallIcon = "";

   /** The large icon */
   private String largeIcon = "";
   
   /**
    * Create a new IconImpl.
    */
   public IconImpl()
   {
      super(Icon.class);
   }

   public String largeIcon()
   {
      return getLargeIcon();
   }

   public String smallIcon()
   {
      return getSmallIcon();
   }

   /**
    * Get the largeIcon.
    * 
    * @return the largeIcon.
    */
   @JBossXmlNsPrefix(prefix = "jee", applyToComponentContent=false, schemaTargetIfNotMapped=true)
   public String getLargeIcon()
   {
      return largeIcon;
   }

   /**
    * Set the largeIcon.
    * 
    * @param largeIcon the largeIcon.
    * @throws IllegalArgumentException for a null largeIcon
    */
   public void setLargeIcon(String largeIcon)
   {
      if (largeIcon == null)
         throw new IllegalArgumentException("Null largeIcon");
      this.largeIcon = largeIcon;
   }

   /**
    * Get the smallIcon.
    * 
    * @return the smallIcon.
    */
   @JBossXmlNsPrefix(prefix = "jee", applyToComponentContent=false, schemaTargetIfNotMapped=true)
   public String getSmallIcon()
   {
      return smallIcon;
   }

   /**
    * Set the smallIcon.
    * 
    * @param smallIcon the smallIcon.
    * @throws IllegalArgumentException for a null smallIcon
    */
   public void setSmallIcon(String smallIcon)
   {
      if (smallIcon == null)
         throw new IllegalArgumentException("Null smallIcon");
      this.smallIcon = smallIcon;
   }
}
