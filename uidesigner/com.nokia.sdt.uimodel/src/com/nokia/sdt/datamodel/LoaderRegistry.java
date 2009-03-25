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
package com.nokia.sdt.datamodel;

import com.nokia.sdt.sourcegen.ISourceGenProvider;
import com.nokia.sdt.uimodel.UIModelPlugin;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

import org.eclipse.core.runtime.*;
import org.eclipse.emf.common.util.URI;

public class LoaderRegistry {
	
	public static final String LOADER_EXTENSION = "modelLoader";


	public static LoadResult loadModel(URI fileURI, IDesignerDataModelSpecifier specifier, ISourceGenProvider sgProvider) {
		LoadResult result = null;
		String fileExtension = fileURI.fileExtension();
	
       	// Get implementors of the modelLoader extension point
        IExtensionRegistry er = Platform.getExtensionRegistry();
        IExtensionPoint ep = er.getExtensionPoint(
                UIModelPlugin.PLUGIN_ID, LOADER_EXTENSION);

        // Iterate over all providers looking for the requested one
		IDesignerDataModelProvider loader = null;
        IExtension[] extensions = ep.getExtensions();
        for (int i = 0; i < extensions.length; i++) {
			IExtension extension = extensions[i];
			IConfigurationElement[] ces = extension.getConfigurationElements();
			if (ces != null && ces.length >= 1) {
				IConfigurationElement loaderElement = ces[0];
				String name = loaderElement.getAttribute("fileExtension");
				if (name != null && name.equals(fileExtension)) {
					if (loaderElement.getAttribute("class") != null) {
						try {
							loader = (IDesignerDataModelProvider) loaderElement.createExecutableExtension("class");
						} catch (CoreException x) {
							IStatus status = Logging.newStatus(UIModelPlugin.getDefault(), x);
							result = new LoadResult(null, status);
						}
					}
				}
			}
        }
		
		if (loader != null) {
			result = loader.load(fileURI, specifier, sgProvider);
		}
		
		return result;
	}
}
