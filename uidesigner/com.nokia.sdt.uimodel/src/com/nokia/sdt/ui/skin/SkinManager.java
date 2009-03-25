/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
* All rights reserved.
* This component and the accompanying materials are made available
* under the terms of the License "Eclipse Public License v1.0"
* which accompanies this distribution, and is available
* at the URL "http://www.eclipse.org/legal/epl-v10.html".
*
* Initial Contributors:
* Nokia Corporation - initial contribution.
*
* Contributors:
*
* Description: 
*
*/
package com.nokia.sdt.ui.skin;


import com.nokia.sdt.uimodel.UIModelPlugin;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.IStatus;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.*;

public class SkinManager {
	
		static final String SKIN_EXTENSION = ".skin"; //$NON-NLS-1$
	
		// map of symbolic name to collection of ISkin
		// TODO - this could possibly changed to maintain weak references
		// to collections. The idea that clients would obtain a strong reference
		// ensuring the collection and its skins remain valid. When all such
		// strong references go away the collection would be eligible for reclamation.
		// This would require that clients are always prepared to rebuild collections
		// that have been released.
		// An unregister method is not provided, since it would not be needed in the
		// above scenario.
	private static HashMap skinLists = new HashMap();
	
	public static void registerSkinList(String name, List list) {
		synchronized(skinLists) {
			skinLists.put(name, list);
		}
	}
	
	public static List lookupSkinList(String name) {
		List result = null;
		synchronized (skinLists) {
			result = (List) skinLists.get(name);
		}
		return result;
	}
	
		/**
		 * This methods returns a collection of all the skins discovered by
		 * recursing the file system starting at the given directory.
		 * It first looks for an existing collection registered with the
		 * path as the collection name, or it recurses the path, loading all 
		 * discovered skins, adding them to the collection. In the second
		 * case the collection is registered using the path as the name.
		 * @param rootDirectoryPath
		 * @return
		 */
	public static List getDiscoveredFileSystemCollection(String rootDirectoryPath) {
		List result = lookupSkinList(rootDirectoryPath);
		if (result == null) {
			File rootDir = new File(rootDirectoryPath);
			if (rootDir.isDirectory()) {
				ArrayList skinFiles = new ArrayList();
				addSkinFiles(rootDir, skinFiles);
				ArrayList skins = new ArrayList();
				for (Iterator iter = skinFiles.iterator(); iter.hasNext();) {
					File currFile = (File) iter.next();
					try {
						ISkin skin = SkinLoader.loadSkin(currFile.toURL());
						skins.add(skin);
					} catch (Exception x) {
						String fmt = com.nokia.sdt.uimodel.Messages.getString("SkinManager.1"); //$NON-NLS-1$
						Object args[] = {currFile.getAbsolutePath()};
						String msg = MessageFormat.format(fmt, args);
						Logging.log(UIModelPlugin.getDefault(), 
								Logging.newStatus(UIModelPlugin.getDefault(),
										IStatus.ERROR, msg, x));
					}					
				}
				if (skins.size() > 0) {
					result = skins;
				}
			}
			if (result != null)
				registerSkinList(rootDirectoryPath, result);
		}
		return result;
	}
	
	public static ISkin loadSkin(URL url) {
		ISkin result = null;
		Exception caughtException = null;
		try {
			result = SkinLoader.loadSkin(url);
		} catch (IOException x) {
			caughtException = x;
		} catch (URISyntaxException x) {
			caughtException = x;
		}
		
		if (caughtException != null) {
			String fmt = com.nokia.sdt.uimodel.Messages.getString("SkinManager.1"); //$NON-NLS-1$
			Object args[] = {url};
			String msg = MessageFormat.format(fmt, args);
			Logging.log(UIModelPlugin.getDefault(), 
					Logging.newStatus(UIModelPlugin.getDefault(),
							IStatus.ERROR, msg, caughtException));
		}					
		return result;
	}
	
	private static void addSkinFiles(File dir, ArrayList list) {
		File[] contents = dir.listFiles();
		if (contents != null) {
			for (int i = 0; i < contents.length; i++) {
				File f = contents[i];
				if (f.isDirectory()) {
					addSkinFiles(f, list);
				}
				else {
					if (f.getName().endsWith(SKIN_EXTENSION)) {
						list.add(f);
					}
				}
			}
		}
	}

	public static void sortCollectionByDisplayText(List skinList) {
		
		Collections.sort(skinList, new Comparator() {
			public int compare(Object o1, Object o2) {
				// TODO change to use ISkin
				int area1 = 0;
				int area2 = 0;
				int result = area1 - area2;
				if (result == 0) {
					String name1 = ""; //$NON-NLS-1$
					String name2 = ""; //$NON-NLS-1$
					result = name1.compareTo(name2);
				}
				return result;
			}
		});
	}
}
