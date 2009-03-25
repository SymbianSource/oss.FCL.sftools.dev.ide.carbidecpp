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

import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CodescannerConfigType;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.DocumentRoot;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.CSConfigFactoryImpl;
import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.util.CSConfigResourceFactoryImpl;

/**
 * A class for loading/saving Codescanner configuration settings from/to an XML file.
 *
 */
public class CSConfigXMLLoader {
	
	/**
	 * Load a Codescanner configuration file
	 * @param url - Full path to the file
	 * @return CodescannerConfigType - a model that represents Codescanner configuration settings
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static CodescannerConfigType loadCSConfig(URL url) 
		throws URISyntaxException, IOException  {
		if (url == null)
			return null;
		URI xmlURI = URI.createURI(url.toString());

		CSConfigResourceFactoryImpl factory = new CSConfigResourceFactoryImpl();
		Resource r = factory.createResource(xmlURI);

		r.load(null);
		EList<EObject> contents = r.getContents();
	
		DocumentRoot root = (DocumentRoot) contents.get(0);
		CodescannerConfigType buildConifgs = root.getCodescannerConfig();
		
		return buildConifgs;
	}

	/**
	 * Save a Codescanner configuation settings to file
	 * @param config - The Codescanner configuration settings to save to file
	 * @param url -  Full path to the file
	 * @return true on success
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static boolean writeCSConfig(CodescannerConfigType config, URL url) 
		throws URISyntaxException, IOException {
		if (url == null)
			return false;
		URI xmlURI = URI.createURI(url.toString());
	
		CSConfigResourceFactoryImpl resFactory = new CSConfigResourceFactoryImpl();
		Resource r = resFactory.createResource(xmlURI);
		EList<EObject> contents = r.getContents();
	
		CSConfigFactoryImpl factory = new CSConfigFactoryImpl();
		DocumentRoot root = factory.createDocumentRoot();
		root.setCodescannerConfig(config);
		contents.add(root);
						
		// write to disk
		r.save(null);
		return true;
}

}
