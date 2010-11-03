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
import javax.xml.bind.annotation.XmlValue;

import org.jboss.annotation.javaee.DisplayName;
import org.jboss.metadata.javaee.support.LanguageMetaData;

/**
 * DisplayNameImpl.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlType(name="display-nameType")
public class DisplayNameImpl extends LanguageMetaData implements DisplayName
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 8863089892578047773L;
   
   /** The display name */
   private String displayName = "";
   
   /**
    * Create a new DisplayNameImpl.
    */
   public DisplayNameImpl()
   {
      super(DisplayName.class);
   }

   public String value()
   {
      return getDisplayName();
   }

   /**
    * Get the displayName.
    * 
    * @return the displayName.
    */
   public String getDisplayName()
   {
      return displayName;
   }

   /**
    * Set the displayName.
    * 
    * @param displayName the displayName.
    * @throws IllegalArgumentException for a null displayName
    */
   @XmlValue
   public void setDisplayName(String displayName)
   {
      if (displayName == null)
         throw new IllegalArgumentException("Null displayName");
      this.displayName = displayName;
   }
}
