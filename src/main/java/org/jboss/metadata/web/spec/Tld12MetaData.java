package org.jboss.metadata.web.spec;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.metadata.javaee.spec.JavaEEMetaDataConstants;
import org.jboss.xb.annotations.JBossXmlSchema;

/**
 * TLD spec metadata.
 *
 * @author Remy Maucherat
 * @version $Revision: 81860 $
 */
@XmlRootElement(name="taglib", namespace="")
@JBossXmlSchema(
      xmlns={@XmlNs(namespaceURI = "", prefix="jee")},
      ignoreUnresolvedFieldOrClass=false,
      namespace="",
      elementFormDefault=XmlNsForm.UNSET,
      normalizeSpace=true)
public class Tld12MetaData extends TldMetaData
{
   private static final long serialVersionUID = 1;

   @Override
   public String getVersion()
   {
      return "1.2";
   }

}
