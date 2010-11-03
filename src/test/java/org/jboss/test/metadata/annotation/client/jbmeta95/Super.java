/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.test.metadata.annotation.client.jbmeta95;

import java.net.URL;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.transaction.UserTransaction;
import javax.xml.ws.WebServiceRef;

import org.omg.CORBA.ORB;

/**
 * @author <a href="mailto:emuckenh@redhat.com">Emanuel Muckenhuber</a>
 * @version $Revision$
 */
public class Super
{
   private static ResourceIF resourceMethodBean;
   @WebServiceRef(name="service/somewebservice")
   static DefaultWebServiceService service = null;

   @EJB(name = "resourceFieldBean", beanName = "ResourceOnFieldBean")
   private static ResourceIF resourceFieldBean;

   @EJB(name="ejb/resourceClassBean", beanName = "ResourcesOnClassBean", mappedName="refs/resources/ResourcesOnClassBean")
   private static ResourceIF resourceClassBean;

   @EJB(name = "resourceMethodBean", beanName = "ResourceOnMethodBean")
   private static void setResourceMethodBean(ResourceIF rif)
   {
      resourceMethodBean = rif;
   }

   @Resource(name="sendQueue")
   private static Queue sendQueue;

   @Resource(name="receiveQueue")
   private static Queue receiveQueue;

   @Resource(name="queueConnectionFactory")
   private static QueueConnectionFactory queueConnectionFactory;

   @Resource(name="queueName", mappedName="queue/testQueue")
   private static String queueName;
   @Resource(mappedName="3.14159", description="pi to 5", name="PI")
   private static Float pi;

   @Resource
   private static ORB orb;
   @Resource(name="jboss-home-page", mappedName="http://www.jboss.org")
   private static URL jbossHome;
   private static UserTransaction utx;

   @Resource(name="user-tx")
   private static void setUserTransaction(UserTransaction utx)
   {
      Super.utx = utx;
   }

   @PostConstruct
   public static void postConstruct()
   {
   }
   @PreDestroy
   public static void destroy()
   {
   }   
}

