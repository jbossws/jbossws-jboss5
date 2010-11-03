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
@XmlRootElement(name="taglib", namespace=JavaEEMetaDataConstants.J2EE_NS)
@JBossXmlSchema(
      xmlns={@XmlNs(namespaceURI = JavaEEMetaDataConstants.J2EE_NS, prefix = "jee")},
      ignoreUnresolvedFieldOrClass=false,
      namespace=JavaEEMetaDataConstants.J2EE_NS,
      elementFormDefault=XmlNsForm.QUALIFIED,
      normalizeSpace=true)
public class Tld20MetaData extends TldMetaData
{
   private static final long serialVersionUID = 1;

}
