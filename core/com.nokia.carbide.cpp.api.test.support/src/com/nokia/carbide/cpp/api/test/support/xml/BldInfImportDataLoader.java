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



package com.nokia.carbide.cpp.api.test.support.xml;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfFilesType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.BldInfImportDataType;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.DocumentRoot;
import com.nokia.carbide.cpp.api.test.support.gen.InfImportTestData.util.InfImportTestDataResourceFactoryImpl;

/**
 * Class with static methods to read data for basic information to import
 * bld.inf file(s). For schema information, see infImportData.xsd
 *
 */
public class BldInfImportDataLoader {
	
	/**
	 * Load on XML file containing all bld.inf file data.
	 * @param url - Location of XML file
	 * @return BldInfFilesType, root to get all the bld.inf file data
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static BldInfFilesType loadBldInfImportData(URL url) throws URISyntaxException, IOException  {
		if (url == null)
			return null;
		URI xmlURI = URI.createURI(url.toString());

		InfImportTestDataResourceFactoryImpl factory = new InfImportTestDataResourceFactoryImpl();
		Resource r = factory.createResource(xmlURI);

		r.load(null);
		EList contents = r.getContents();
	
		DocumentRoot root = (DocumentRoot) contents.get(0);
		BldInfImportDataType importDataRoot = root.getBldInfImportData();
		BldInfFilesType bldInfImportData =  importDataRoot.getBldInfFiles();
		return bldInfImportData;
	}
	
}
