/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
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
package org.jboss.metadata.test.generator;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.QName;

import org.jboss.logging.Logger;
import org.jboss.xb.binding.sunday.unmarshalling.AllBinding;
import org.jboss.xb.binding.sunday.unmarshalling.AttributeBinding;
import org.jboss.xb.binding.sunday.unmarshalling.ElementBinding;
import org.jboss.xb.binding.sunday.unmarshalling.ModelGroupBinding;
import org.jboss.xb.binding.sunday.unmarshalling.ParticleBinding;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBinding;
import org.jboss.xb.binding.sunday.unmarshalling.SequenceBinding;
import org.jboss.xb.binding.sunday.unmarshalling.TermBinding;
import org.jboss.xb.binding.sunday.unmarshalling.TypeBinding;
import org.jboss.xb.builder.JBossXBBuilder;
import org.jboss.xb.builder.runtime.EnumValueAdapter;

/**
 * A simplistic schema generator.
 * 
 * This is meant to generate a schema for jboss-client from JBossClient5MetaData, using
 * it on other classes will probably produce errors.
 * 
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version $Revision: $
 */
public class SchemaGenerator
{
   private static final Logger log = Logger.getLogger(SchemaGenerator.class);
   
   private static final Field field;
   
   private static Map<String, String> prefixByNs = new HashMap<String, String>();
   private static int indent = 0;
   
   static
   {
      try
      {
         field = SchemaBinding.class.getDeclaredField("nsByPrefix");
         field.setAccessible(true);
      }
      catch (SecurityException e)
      {
         throw new RuntimeException(e);
      }
      catch (NoSuchFieldException e)
      {
         throw new RuntimeException(e);
      }
   }
   
   private static String attrs(ParticleBinding particle)
   {
      String s = "";
      if(particle.getMinOccurs() != 1)
         s = " minOccurs=\""  + particle.getMinOccurs() + "\"";
      if(particle.getMaxOccursUnbounded())
         s += " maxOccurs=\"unbounded\"";
      else if(particle.getMaxOccurs() != 1)
         s += " maxOccurs=\"" + particle.getMaxOccurs() + "\"";
      return s;
   }
   
   private static void dumpAttributes(Collection<AttributeBinding> attributes)
   {
      for(AttributeBinding attribute : attributes)
      {
         println("<xsd:attribute name=\"" + attribute.getQName().getLocalPart() + "\" type=\"" + prefixed(attribute.getType().getQName()) + "\"/>");
      }
   }
   
   private static void dumpComplexType(TypeBinding type)
   {
      println("<xsd:complexType name=\"" + type.getQName().getLocalPart() + "\">");
      indent++;
      dumpParticle(type.getParticle());
      dumpAttributes(type.getAttributes());
      indent--;
      println("</xsd:complexType>");
   }
   
   private static void dumpElement(ParticleBinding particle, ElementBinding element)
   {
      println("<xsd:element name=\"" + element.getQName().getLocalPart() + "\" type=\"" + prefixed(element.getType().getQName()) + "\"" + attrs(particle) + "/>");
      
      //println("</xsd:element>");
   }
   
   private static void dumpParticle(ParticleBinding particle)
   {
      dumpTerm(particle, particle.getTerm());
   }
   
   private static void dumpModelGroup(ParticleBinding particle, ModelGroupBinding modelGroup, String name)
   {
      // FIXME: wickedness in XB, it generates dummy model groups
      boolean isDummyModelGroup = isDummyModelGroup(particle, modelGroup);
      if(!isDummyModelGroup)
      {
         println("<xsd:" + name + attrs(particle) + ">");
         indent++;
      }
      Collection<ParticleBinding> particles = modelGroup.getParticles();
      for(ParticleBinding p : particles)
      {
         dumpParticle(p);
      }
      if(!isDummyModelGroup)
      {
         indent--;
         println("</xsd:" + name + ">");
      }
   }
   
   private static void dumpModelGroup(ModelGroupBinding modelGroup)
   {
      println("<xsd:group name=\"" + modelGroup.getQName() + "\">");
//      if(modelGroup instanceof AllBinding)
//         log.warn("Weirdness: found all binding on " + modelGroup);
      println("   <xsd:sequence>");
      indent += 2;
      Collection<ParticleBinding> particles = modelGroup.getParticles();
      for(ParticleBinding p : particles)
      {
         dumpParticle(p);
      }
      indent -= 2;
      println("   </xsd:sequence>");
      println("</xsd:group>");
   }
   
   private static void dumpSimpleType(TypeBinding type)
   {
//      System.err.println(type.getAttributes());
//      System.err.println(type.getBaseType());
//      System.err.println(type.getValueAdapter());
      println("<xsd:simpleType name=\"" + type.getQName().getLocalPart() + "\">");
      println("   <xsd:restriction base=\"" + prefixed(type.getBaseType().getQName()) + "\">");
      EnumValueAdapter enumValueAdapter = (EnumValueAdapter) type.getValueAdapter();
      for(Object o : enumValueAdapter.getMapping().values())
      {
         println("      <xsd:enumeration value=\"" + o + "\"/>");
      }
      println("   </xsd:restriction>");
      println("</xsd:simpleType>");
   }
   
   private static void dumpTerm(ParticleBinding particle, TermBinding term)
   {
      if(term instanceof AllBinding)
         dumpModelGroup(particle, (AllBinding) term, "all");
      else if(term.isElement())
         dumpElement(particle, (ElementBinding) term);
      else if(term instanceof SequenceBinding)
         dumpModelGroup(particle, (SequenceBinding) term, "sequence");
      else
         throw new RuntimeException("NYI " + term);
   }
   
   private static void dumpType(TypeBinding type)
   {
      if(type.isSimple())
         dumpSimpleType(type);
      else
         dumpComplexType(type);
   }
   
   @SuppressWarnings("unchecked")
   private static Map<String, String> getNsByPrefix(SchemaBinding binding)
   {
      try
      {
         return (Map<String, String>) field.get(binding);
      }
      catch (IllegalArgumentException e)
      {
         throw new RuntimeException(e);
      }
      catch (IllegalAccessException e)
      {
         throw new RuntimeException(e);
      }
   }
   
   private static boolean isDummyModelGroup(ParticleBinding particle, ModelGroupBinding modelGroup)
   {
      int minOccurs, maxOccurs;
      boolean maxOccursUnbounded;
      if(modelGroup instanceof AllBinding)
      {
         minOccurs = 0;
         maxOccurs = 1;
         maxOccursUnbounded = true;
      }
      else
      {
         minOccurs = particle.getMinOccurs();
         maxOccurs = particle.getMaxOccurs();
         maxOccursUnbounded = particle.getMaxOccursUnbounded();
      }
      Collection<ParticleBinding> particles = modelGroup.getParticles();
      for(ParticleBinding p : particles)
      {
         if(p.getMinOccurs() != minOccurs)
            return false;
         if(p.getMaxOccurs() != maxOccurs)
            return false;
         if(p.getMaxOccursUnbounded() != maxOccursUnbounded)
            return false;
      }
      return true;
   }
   
   public static void main(String args[]) throws ClassNotFoundException
   {
      Class<?> root = Class.forName(args[0]);
      
      // TODO: arg
      String targetNamespace = "http://www.jboss.com/xml/ns/javaee";
      String version = "5.0";
      
      SchemaBinding binding = JBossXBBuilder.build(root);
      
      println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      
      println("<xsd:schema xmlns=\"http://www.w3.org/2001/XMLSchema\"");
      
      Map<String, String> nsByPrefix = getNsByPrefix(binding);
      for(String prefix : nsByPrefix.keySet())
      {
         String namespace = binding.getNamespace(prefix);
         println("   xmlns:" + prefix + "=\"" + namespace + "\"");
         prefixByNs.put(namespace, prefix);
      }
      println("   targetNamespace=\"" + targetNamespace + "\"");
      println("   version=\"" + version + "\"");
      println("   elementFormDefault=\"qualified\"");
      println("   >");
      
      indent++;
      
      Iterator<ParticleBinding> particles = binding.getElementParticles();
      while(particles.hasNext())
      {
         ParticleBinding particle = particles.next();
         dumpParticle(particle);
      }
      
      Iterator<TypeBinding> types = binding.getTypes();
      while(types.hasNext())
      {
         TypeBinding type = types.next();
         if(type.getQName().getNamespaceURI().equals(targetNamespace))
            dumpType(type);
      }
      
      Iterator<ModelGroupBinding> groups = binding.getGroups();
      while(groups.hasNext())
      {
         ModelGroupBinding modelGroup = groups.next();
         dumpModelGroup(modelGroup);
      }
      
      indent--;
      
      println("</xsd:schema>");
   }
   
   private static String prefixed(QName qName)
   {
      // FIXME: StubPropertyMetaData isn't properly setup
      if(qName == null)
         return "xsd:string";
      
      String namespace = qName.getNamespaceURI();
      String prefix = prefixByNs.get(namespace);
      if(prefix == null)
         return qName.toString();
      else
         return prefix + ":" + qName.getLocalPart();
   }
   
   private static void println(String s)
   {
      for(int i = 0; i < indent; i++)
      {
         System.out.print("   ");
      }
      System.out.println(s);
   }
}
