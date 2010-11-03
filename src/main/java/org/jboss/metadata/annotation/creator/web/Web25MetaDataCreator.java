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
package org.jboss.metadata.annotation.creator.web;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.MessageDriven;
import javax.ejb.Stateful;
import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.Consumer;
import org.jboss.ejb3.annotation.Service;
import org.jboss.logging.Logger;
import org.jboss.metadata.annotation.creator.AbstractCreator;
import org.jboss.metadata.annotation.creator.Creator;
import org.jboss.metadata.annotation.finder.AnnotationFinder;
import org.jboss.metadata.web.spec.Web25MetaData;
import org.jboss.metadata.web.spec.WebMetaData;

/**
 * Create a Web25MetaData instance from the class annotations
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class Web25MetaDataCreator extends AbstractCreator<WebMetaData>
      implements Creator<Collection<Class<?>>, Web25MetaData>
{
 
   /** The ignore type annotations */
   private static final Set<Class<? extends Annotation>> ignoreTypeAnnotations;
   
   /** The Logger. */
   private static final Logger log = Logger.getLogger(Web25MetaDataCreator.class);
   
   static
   {
      // Ignoring classes with the the following type annotations
      ignoreTypeAnnotations = new HashSet<Class<? extends Annotation>>();
      ignoreTypeAnnotations.add(Stateful.class);
      ignoreTypeAnnotations.add(Stateless.class);
      ignoreTypeAnnotations.add(MessageDriven.class);
      ignoreTypeAnnotations.add(Service.class);
      ignoreTypeAnnotations.add(Consumer.class);
   }
   
   public Web25MetaDataCreator(AnnotationFinder<AnnotatedElement> finder)
   {
      super(finder);
      addProcessor(new WebComponentProcessor(finder));
   }

   public Web25MetaData create(Collection<Class<?>> classes)
   {
      // Don't create meta data for a empty collection
      if(classes == null || classes.isEmpty())
         return null;
      
      // Create meta data
      Web25MetaData metaData = create();

      processMetaData(classes, metaData);
      
      return metaData;
   }
   
   protected Web25MetaData create()
   {
      Web25MetaData metaData = new Web25MetaData();
      metaData.setVersion("2.5");
      return metaData;
   }
   
   protected boolean validateClass(Class<?> clazz)
   {
      boolean trace = log.isTraceEnabled();
      for(Class<? extends Annotation> annotation : ignoreTypeAnnotations)
      {
         if(finder.getAnnotation(clazz, annotation) != null)
         {
            if(trace)
               log.trace("won't process class: " + clazz + ", because of the type annotation: "+ annotation);
            return false;
         }
      }
      return true;
   }

}
