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
/**
 * 
 */
package com.nokia.sdt.component.symbian;

import org.eclipse.core.runtime.*;
import org.osgi.framework.Bundle;

/** 
 * Creates a component library from an extension point.
 * If the extension specifies a "class", then we use the code
 * in the library to implement IComponentLibrary.  Otherwise,
 * we use our default ComponentLibrary implementation.
 * 
 * 
 */
public class ComponentLibraryExtensionFactory {
    /** Create a component library */
    static public IComponentLibrary create(IExtension extension, IConfigurationElement ce, ComponentProvider provider) throws CoreException {
        /* either create user-specified component library class or use the default */
		IComponentLibrary result = null;
        if (ce.getAttribute("class") != null) { //$NON-NLS-1$
            result = (IComponentLibrary)ce.createExecutableExtension("class"); //$NON-NLS-1$
        } else {
            result = new ComponentLibrary(extension, ce);
        }
		if (result != null) {
			result.setProvider(provider);
			Bundle bundle = Platform.getBundle(extension.getNamespaceIdentifier());
			result.setBundle(bundle);
		}
		return result;
    }

}
