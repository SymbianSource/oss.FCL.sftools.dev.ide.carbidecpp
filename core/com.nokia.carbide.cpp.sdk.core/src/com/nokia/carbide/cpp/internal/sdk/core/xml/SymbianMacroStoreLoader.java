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
*/
package com.nokia.carbide.cpp.internal.sdk.core.xml;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.DocumentRoot;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.SymbianMacroStoreType;
import com.nokia.carbide.cpp.internal.sdk.core.gen.SymbianMacroStore.util.SymbianMacroStoreResourceFactoryImpl;

public class SymbianMacroStoreLoader {

	public static SymbianMacroStoreType loadMacroStore(URL url) throws URISyntaxException, IOException  {
		if (url == null)
			return null;
		URI xmlURI = URI.createURI(url.toString());

		SymbianMacroStoreResourceFactoryImpl factory = new SymbianMacroStoreResourceFactoryImpl();
		Resource r = factory.createResource(xmlURI);

		r.load(null);
		EList contents = r.getContents();
	
		DocumentRoot root = (DocumentRoot) contents.get(0);
		SymbianMacroStoreType macroStore = root.getSymbianMacroStore();

		return macroStore;
	} 
}
