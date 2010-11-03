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
package org.jboss.metadata.annotation.creator.client;

import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

import org.jboss.metadata.annotation.creator.AbstractCreator;
import org.jboss.metadata.annotation.creator.Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.client.spec.ApplicationClient5MetaData;

/**
 * Create a ApplicationClient5MetaData instance from the class annotations
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class ApplicationClient5MetaDataCreator extends AbstractCreator<ApplicationClient5MetaData>
   implements Creator<Collection<Class<?>>, ApplicationClient5MetaData>
{
   
   /** The main-class name. */
   private String mainClassName = null;
   
   @Deprecated
   public ApplicationClient5MetaDataCreator(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
      
      addProcessor(new ApplicationClientProcessor(finder));
   }
   
   public ApplicationClient5MetaDataCreator(AnnotationFinder<AnnotatedElement> finder, String mainClassName)
   {
      this(finder);
      if(mainClassName == null)
         throw new IllegalStateException("null mainClassName");
      
      this.mainClassName = mainClassName;
   }
   
   /**
    * Create the meta data for a set of annotated classes.
    * 
    * @param classes
    */
   public ApplicationClient5MetaData create(Collection<Class<?>> classes)
   {
      // Don't create meta data for a empty collection
      if(classes == null || classes.isEmpty())
         return null;
      
      // Create the meta data
      ApplicationClient5MetaData md = create();

      processMetaData(classes, md);
      
      return md;
   }
   
   protected ApplicationClient5MetaData create()
   {
      ApplicationClient5MetaData metaData = new ApplicationClient5MetaData();
      metaData.setVersion("5");
      return metaData;
   }

   protected boolean validateClass(Class<?> clazz)
   {
      // TODO - this check should be removed, this only exists for backward compatibility
      if(mainClassName == null)
         return true;
      
      if(clazz.getName().equals(mainClassName))
         return true;
      
      return false;
   }
   
}
