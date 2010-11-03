package org.jboss.metadata.web.spec;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.spec.JavaEEMetaDataConstants;
import org.jboss.xb.annotations.JBossXmlSchema;

/**
 * Web application spec metadata.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
@XmlRootElement(name="web-app", namespace=JavaEEMetaDataConstants.JAVAEE_NS)
@JBossXmlSchema(
      xmlns={@XmlNs(namespaceURI = JavaEEMetaDataConstants.JAVAEE_NS, prefix = "jee")},
      ignoreUnresolvedFieldOrClass=false,
      namespace=JavaEEMetaDataConstants.JAVAEE_NS,
      elementFormDefault=XmlNsForm.QUALIFIED,
      normalizeSpace=true)
@XmlType(name="web-appType",
      namespace=JavaEEMetaDataConstants.JAVAEE_NS,
      propOrder={"descriptionGroup", "distributable", "contextParams", "filters", "filterMappings", "listeners", "servlets",
      "servletMappings", "sessionConfig", "mimeMappings", "welcomeFileList", "errorPages", "jspConfig", "securityContraints",
      "loginConfig", "securityRoles", "jndiEnvironmentRefsGroup", "messageDestinations", "localEncodings"})
public class Web25MetaData extends WebMetaData
{
   private static final long serialVersionUID = 1;
   private boolean metadataComplete;

   public boolean isMetadataComplete()
   {
      return metadataComplete;
   }

   @XmlAttribute(name="metadata-complete")
   public void setMetadataComplete(boolean metadataComplete)
   {
      this.metadataComplete = metadataComplete;
   }

}
