/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.emf.component.loader;

import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.component.symbian.Messages;
import com.nokia.sdt.emf.component.ComponentPackage;
import com.nokia.sdt.emf.component.DocumentRoot;
import com.nokia.sdt.emf.component.util.ComponentResourceFactoryImpl;
import com.nokia.cpp.internal.api.utils.core.Logging;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.IOWrappedException;
import org.xml.sax.SAXParseException;

import java.io.IOException;
import java.text.MessageFormat;


/**
 * 
 */
public class Loader {
	
	/**
	 * Loads a component descriptor document
	 * 
	 * @param xmlURI the file to load
	 * @return the root element, or null if error occurred
	 */
	public DocumentRoot	load(URI xmlURI) {
		ComponentResourceFactoryImpl factory = new ComponentResourceFactoryImpl();
		Resource r = factory.createResource(xmlURI);
		boolean ok = false;
		try {
			// With the move to EMF 2.3, EMF suddenly decided the entries referencing
			// properties are not changeable. I couldn't find any doc on why this 
			// is so or how to override it via schema or EMF tools, but it prevents us 
			// from dynamically adding properties. The code below sets the structural 
			EReference ref = ComponentPackage.eINSTANCE.getPropertiesType_AbstractProperty();
			ref.setChangeable(true);
			
			r.load(null);
			ok = true;
		}
		catch (IOException x) {
			logError(xmlURI, x);
		}
		catch (RuntimeException x) {
			logError(xmlURI, x);
		}
		DocumentRoot root = null;
		if (ok) {
			EList contents = r.getContents();
			if (contents != null && contents.size() > 0)
			{
				root = (DocumentRoot) contents.get(0);
			}
			r.unload();
		}

		return root;
	}
	
	private void logError(URI uri, Exception x) {
		ComponentSystemPlugin plugin = ComponentSystemPlugin.getDefault();
        String msg;
        if (x instanceof IOWrappedException && ((IOWrappedException)x).getWrappedException() instanceof SAXParseException) {
            SAXParseException spe = (SAXParseException)((IOWrappedException)x).getWrappedException();
            String tmpl = Messages.getString("Loader.1"); //$NON-NLS-1$
            Object params[] = {uri.toString(),
                    Integer.toString(spe.getLineNumber()),
                    x.getLocalizedMessage()
                     };
            msg = MessageFormat.format(tmpl, params);
        } else {
            String tmpl = Messages.getString("Loader.0"); //$NON-NLS-1$
            Object params[] = {uri.toString(), x.getLocalizedMessage()};
            msg = MessageFormat.format(tmpl, params);
        }
		Logging.log(plugin, Logging.newStatus(plugin,
						IStatus.ERROR, msg, x));

	}
}
