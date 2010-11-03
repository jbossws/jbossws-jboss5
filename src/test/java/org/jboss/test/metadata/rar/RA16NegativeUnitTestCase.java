/*
* JBoss, Home of Professional Open Source
* Copyright 2008, JBoss Inc., and individual contributors as indicated
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
package org.jboss.test.metadata.rar;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jboss.logging.Logger;
import org.jboss.metadata.rar.spec.JCA16MetaData;
import org.jboss.test.metadata.javaee.AbstractJavaEEEverythingTest;
import org.jboss.util.xml.JBossEntityResolver;

import org.jboss.xb.binding.sunday.unmarshalling.DefaultSchemaResolver;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBindingResolver;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * Test ResourceAdapter ra.xml metadata.
 *
 * @author Jeff Zhang
 * @version $Revision:$
 */
public class RA16NegativeUnitTestCase extends AbstractJavaEEEverythingTest
{
   private static final Logger log = Logger.getLogger(RA16NegativeUnitTestCase.class);
   private static final SAXParserFactory FACTORY;
   
   static
   {
      FACTORY = SAXParserFactory.newInstance();
      FACTORY.setNamespaceAware(true);
      FACTORY.setValidating(true);
   }
   private static final EntityResolver RESOLVER = new JBossEntityResolver();
   
   public static SchemaBindingResolver initResolver()
   {
      DefaultSchemaResolver resolver = new DefaultSchemaResolver();
      resolver.addClassBindingForLocation("connector_1_6.xsd", JCA16MetaData.class);
      return resolver;
   }

   public RA16NegativeUnitTestCase(String name)
   {
      super(name);
   }

   protected void setUp() throws Exception
   {
      super.setUp();
      enableTrace("org.jboss.xb");
   }

   /**
    * test miss resourceadapter-version
    */
   public void testParserRAVersion() throws Exception
   {
      negativeParserTest("resourceadapter-version");
   }

   /**
    * test miss vendor-name
    */
   public void testParserVendorName() throws Exception
   {
      negativeParserTest("vendor-name");
   }
   
   /**
    * test miss eis-type
    */
   public void testParserEISType() throws Exception
   {
      negativeParserTest("eis-type");
   }

   /**
    * test miss connection-definition
    */
   public void testParserConDef() throws Exception
   {
      negativeParserTest("connection-definition", false);
   }
   
   /**
    * test miss reauthentication-support
    */
   public void testParserReAuth() throws Exception
   {
      negativeParserTest("reauthentication-support", false);
   }
   
   /**
    * test miss transaction-support
    */
   public void testParserTransSupport() throws Exception
   {
      negativeParserTest("transaction-support");
   }
   
   /**
    * test miss managedconnectionfactory-class
    */
   public void testParserMCFClass() throws Exception
   {
      negativeParserTest("managedconnectionfactory-class");
   } 
   
   /**
    * test miss connectionfactory-interface
    */
   public void testParserCFInterface() throws Exception
   {
      negativeParserTest("connectionfactory-interface");
   } 
   
   /**
    * test miss managedconnectionfactory-class
    */
   public void testParserCFImpl() throws Exception
   {
      negativeParserTest("connectionfactory-impl-class");
   } 
   
   /**
    * test miss connection-interface
    */
   public void testParserConnectionInterface() throws Exception
   {
      negativeParserTest("connection-interface");
   } 
   
   /**
    * test miss connection-impl-class
    */
   public void testParserConnectionImpl() throws Exception
   {
      negativeParserTest("connection-impl-class", false);
   } 
   
   /**
    * test miss
    */
   private void negativeParserTest(String element) throws Exception
   {
      negativeParserTest(element, true);
   }
   private void negativeParserTest(String element, boolean validateByXB) throws Exception
   {
      if (validateByXB)
      {
         try
         {
            JCA16MetaData connector = unmarshal();
            fail(getName() + " should be failed since we miss " + element);
         } catch (Exception expected)
         {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            expected.printStackTrace(pw);
            assertTrue(sw.toString().indexOf(element) > 0);
         }    
      } else {
         URL xmlFile = this.getClass().getResource(getTestXmlFileName());
         assertFalse(isValid(xmlFile, element));
      }
   }

   protected JCA16MetaData unmarshal() throws Exception
   {
      return unmarshal(JCA16MetaData.class);
   }
   
   private String getTestXmlFileName()
   {
      String name = getClass().getSimpleName();
      int index = name.lastIndexOf("UnitTestCase");
      if (index != -1)
         name = name.substring(0, index);
      name = name + "_" + getName() + ".xml";
      return name;
   }
   
   private static boolean isValid(final URL xmlFile, final String element)
   {
      SAXParser parser;      
      try
      {
         parser = FACTORY.newSAXParser();
      }
      catch (Exception e)
      {
         throw new IllegalStateException("Failed to instantiate a SAX parser: " + e.getMessage());
      }

      try
      {
         parser.getXMLReader().setFeature("http://apache.org/xml/features/validation/schema", true);
      }
      catch (SAXException e)
      {
         throw new IllegalStateException("Schema validation feature is not supported by the parser: " + e.getMessage());
      }

      
      InputStream is;
      try
      {
         is = xmlFile.openStream();
      }
      catch (Exception e)
      {
         throw new IllegalStateException("Failed to open file: " + xmlFile.getPath(), e);
      }

      final boolean[] failed = new boolean[1];
      try
      {
         parser.parse(is, new DefaultHandler()
         {
            public void warning(SAXParseException e)
            {
            }

            public void error(SAXParseException e) throws SAXException
            {
               log.error(xmlFile.getPath() + "[" + e.getLineNumber() + ","  + e.getColumnNumber() + "]: " + e.getMessage());
               failed[0] = true;
               assertTrue(e.getMessage().indexOf(element) > 0);
               throw e;
            }

            public void fatalError(SAXParseException e) throws SAXException
            {
               log.error(xmlFile.getPath() + "[" + e.getLineNumber() + ","  + e.getColumnNumber() + "]: " + e.getMessage());
               failed[0] = true;
               assertTrue(e.getMessage().indexOf(element) > 0);
               throw e;
            }

            public InputSource resolveEntity(String publicId, String systemId) throws SAXException
            {
               InputSource is = null;
               if (RESOLVER != null)
               {
                  try
                  {
                     is = RESOLVER.resolveEntity(publicId, systemId);
                  }
                  catch (Exception e)
                  {
                     throw new IllegalStateException("Failed to resolveEntity " + systemId + ": " + systemId);
                  }
               }

               if (is == null)
               {
                  throw new SAXException("Failed to resolve entity: publicId=" + publicId + " systemId=" + systemId);
               }

               return is;
            }
         });
      }
      catch(SAXException e)
      {
      }
      catch (IOException e)
      {
         throw new IllegalStateException("Failed to read file: " + xmlFile.getPath(), e);
      }

      return !failed[0];
   }
}
