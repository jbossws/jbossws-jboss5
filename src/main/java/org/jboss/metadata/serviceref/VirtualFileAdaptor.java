/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
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
package org.jboss.metadata.serviceref;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.jboss.virtual.VFS;
import org.jboss.virtual.VirtualFile;
import org.jboss.wsf.spi.deployment.UnifiedVirtualFile;

// $Id: VirtualFileAdaptor.java 4049 2007-08-01 11:26:30Z thomas.diesler@jboss.com $

/**
 * A JBoss50 VirtualFile adaptor
 *
 * @author Thomas.Diesler@jboss.org
 * @author Ales.Justin@jboss.org
 * @since 05-May-2006
 */
public class VirtualFileAdaptor implements UnifiedVirtualFile
{
   private static final long serialVersionUID = -4509594124653184347L;

   private static final ObjectStreamField[] serialPersistentFields =
   {
      new ObjectStreamField("rootUrl", URL.class),
      new ObjectStreamField("path", String.class),
   };

   /** Minimal info to get full vfs file structure */
   private URL rootUrl;
   private String path;
   /** The virtual file */
   private transient VirtualFile file;

   public VirtualFileAdaptor(VirtualFile file)
   {
      this.file = file;
   }

   public VirtualFileAdaptor(URL rootUrl, String path)
   {
      if (rootUrl == null)
         throw new IllegalArgumentException("Null root url");
      if (path == null)
         throw new IllegalArgumentException("Null path");

      this.rootUrl = rootUrl;
      this.path = path;
   }

   /**
    * Get the virtual file.
    * Create file from root url and path if it doesn't exist yet.
    *
    * @return virtual file root
    * @throws IOException for any error
    */
   @SuppressWarnings("deprecation")
   protected VirtualFile getFile() throws IOException
   {
      if (file == null)
      {
         VirtualFile root = VFS.getRoot(rootUrl);
         file = root.findChild(path);
      }
      return file;
   }

   @SuppressWarnings("deprecation")
   public UnifiedVirtualFile findChild(String child) throws IOException
   {
      VirtualFile vf = getFile().findChild(child);
      return new VirtualFileAdaptor(vf);
   }

   public URL toURL()
   {
      try
      {
         return getFile().toURL();
      }
      catch (Exception e)
      {
         return null;
      }
   }

   private void writeObject(ObjectOutputStream out) throws IOException, URISyntaxException
   {
      URL url = rootUrl;
      if (url == null)
      {
         VFS vfs = getFile().getVFS();
         url = vfs.getRoot().toURL();
      }
      String pathName = path;
      if (pathName == null)
         pathName = getFile().getPathName();

      ObjectOutputStream.PutField fields = out.putFields();
      fields.put("rootUrl", url);
      fields.put("path", pathName);
      out.writeFields();
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
   {
      ObjectInputStream.GetField fields = in.readFields();
      rootUrl = (URL) fields.get("rootUrl", null);
      path = (String) fields.get("path", null);
   }

   public List<UnifiedVirtualFile> getChildren() throws IOException
   {
      List<VirtualFile> vfList = getFile().getChildren();
      if (vfList == null)
         return null;
      List<UnifiedVirtualFile> uvfList = new LinkedList<UnifiedVirtualFile>();
      for (VirtualFile vf : vfList)
      {
         uvfList.add(new VirtualFileAdaptor(vf));
      }
      return uvfList;
   }

   public String getName()
   {
      try
      {
         return getFile().getName();
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }
}
