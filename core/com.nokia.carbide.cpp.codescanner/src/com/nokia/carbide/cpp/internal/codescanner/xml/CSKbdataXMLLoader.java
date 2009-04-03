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

package com.nokia.carbide.cpp.internal.codescanner.xml;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DocumentRoot;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataType;
import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.util.KbdataResourceFactoryImpl;

/**
 * A class for loading CodeScanner knowledge base rule from an XML file.
 *
 */
public class CSKbdataXMLLoader {

	/**
	 * Load a knowledge base rule from a kbdata file
	 * @param url - full path to the file
	 * @return KbdataType - a model that represents CodeScanner knowledge base rule
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static KbdataType loadKbdata(URL url)
		throws URISyntaxException, IOException  {
		if (url == null)
			return null;
		URI xmlURI = URI.createURI(url.toString());

		KbdataResourceFactoryImpl factory = new KbdataResourceFactoryImpl();
		Resource r = factory.createResource(xmlURI);

		r.load(null);
		EList<EObject> contents = r.getContents();

		DocumentRoot root = (DocumentRoot) contents.get(0);
		KbdataType kbdata = root.getKbdata();

		return kbdata;
	}
}
