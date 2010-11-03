package org.jboss.metadata.web.spec;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.xb.annotations.JBossXmlConstants;
import org.jboss.xb.annotations.JBossXmlSchema;
import org.jboss.xb.annotations.JBossXmlType;

/**
 * Web application spec metadata.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
@XmlRootElement(name="web-app", namespace="")
@JBossXmlSchema(
      xmlns={@XmlNs(namespaceURI = "", prefix="jee")},
      ignoreUnresolvedFieldOrClass=false,
      namespace="",
      elementFormDefault=XmlNsForm.UNSET,
      normalizeSpace=true)
@XmlType(name="web-appType",
      propOrder={"descriptionGroup", "distributable", "contextParams", "filters", "filterMappings", "listeners", "servlets",
      "servletMappings", "sessionConfig", "mimeMappings", "welcomeFileList", "errorPages", "taglibs", "securityContraints",
      "loginConfig", "securityRoles", "jndiEnvironmentRefsGroup"})
@JBossXmlType(modelGroup=JBossXmlConstants.MODEL_GROUP_UNORDERED_SEQUENCE)
public class Web23MetaData extends WebMetaData
{
   private static final long serialVersionUID = 1;

   public boolean isMetadataComplete()
   {
      return true;
   }

   @Override
   public String getVersion()
   {
      return "2.3";
   }

   public List<TaglibMetaData> getTaglibs()
   {
      JspConfigMetaData jspConfig = super.getJspConfig();
      List<TaglibMetaData> taglibs = null;
      if (jspConfig != null)
      {
         taglibs = jspConfig.getTaglibs();
      }
      return taglibs;
   }
   /**
    * Map the 2.3 taglibs onto jsp-config/taglibs
    * @param taglibs
    */
   @XmlElement(name="taglib")
   public void setTaglibs(List<TaglibMetaData> taglibs)
   {
      JspConfigMetaData jspConfig = new JspConfigMetaData();
      jspConfig.setTaglibs(taglibs);
      super.setJspConfig(jspConfig);
   }
}
