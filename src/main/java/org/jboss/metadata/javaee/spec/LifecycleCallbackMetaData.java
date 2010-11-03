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

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * LifecycleCallbackMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 1.1 $
 */
@XmlType(name="lifecycle-callbackType", propOrder={"className", "methodName"})
public class LifecycleCallbackMetaData implements Serializable
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 6453746684927606565L;

   /** The class */
   private String className;
   
   /** The method name */
   private String methodName;
   
   /**
    * Create a new LifecycleCallbackMetaData.
    */
   public LifecycleCallbackMetaData()
   {
      // For serialization
   }

   /**
    * Get the className.
    * 
    * @return the className.
    */
   public String getClassName()
   {
      return className;
   }

   /**
    * Set the className.
    * 
    * @param className the className.
    * @throws IllegalArgumentException for a null className
    */
   @XmlElement(name="lifecycle-callback-class")
   public void setClassName(String className)
   {
      if (className == null)
         throw new IllegalArgumentException("Null className");
      this.className = className;
   }

   /**
    * Get the methodName.
    * 
    * @return the methodName.
    */
   public String getMethodName()
   {
      return methodName;
   }

   /**
    * Set the methodName.
    * 
    * @param methodName the methodName.
    * @throws IllegalArgumentException for a null methodName
    */
   @XmlElement(name="lifecycle-callback-method")
   public void setMethodName(String methodName)
   {
      if (methodName == null)
         throw new IllegalArgumentException("Null methodName");
      this.methodName = methodName;
   }

   @Override
   public boolean equals(Object obj)
   {
      boolean equals = false;
      if(obj instanceof LifecycleCallbackMetaData)
      {
         LifecycleCallbackMetaData lcmd = (LifecycleCallbackMetaData) obj;
         if(className == lcmd.className ||
            (className != null && className.equals(lcmd.className)) )
         {
            equals = methodName == lcmd.methodName
               || methodName != null && methodName.equals(lcmd.methodName);
         }
      }
      return equals;
   }

   @Override
   public int hashCode()
   {
      int hashCode = className != null ? className.hashCode() : 0;
      hashCode += methodName != null ? methodName.hashCode() : 0;
      return hashCode;
   }

}
