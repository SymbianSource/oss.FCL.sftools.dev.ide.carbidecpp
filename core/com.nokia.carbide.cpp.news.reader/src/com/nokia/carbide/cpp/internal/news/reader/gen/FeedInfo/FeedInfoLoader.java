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

package com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.impl.FeedInfoFactoryImpl;
import com.nokia.carbide.cpp.internal.news.reader.gen.FeedInfo.util.FeedInfoResourceFactoryImpl;

/**
 * A class for loading/saving feed information from/to an XML file.
 *
 */
public class FeedInfoLoader {

	/**
	 * Load feed information from a file. 
	 * @param url - Full path to the file
	 * @return a FeedInfoType type object with feed information
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static FeedInfoType loadFeedInfo(URL url)
		throws URISyntaxException, IOException  {
		if (url == null)
			return null;

		URI uri = URI.createURI(url.toString());
		FeedInfoResourceFactoryImpl factory = new FeedInfoResourceFactoryImpl();
		Resource r = factory.createResource(uri);
		r.load(null);
		EList<EObject> contents = r.getContents();
		DocumentRoot root = (DocumentRoot)contents.get(0);
		FeedInfoType feedInfo = root.getFeedInfo();
		return feedInfo;
	}

	/**
	 * Save feed information to a file.
	 * @param feedInfo - the feed information to be saved
	 * @param url - Full path to the file
	 * @return true on success
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static boolean saveFeedInfo(FeedInfoType feedInfo, URL url)
		throws URISyntaxException, IOException {
		if (url == null)
			return false;

		URI uri = URI.createURI(url.toString());
		FeedInfoResourceFactoryImpl rsrcFactory = new FeedInfoResourceFactoryImpl();
		Resource r = rsrcFactory.createResource(uri);
		EList<EObject> contents = r.getContents();
		FeedInfoFactoryImpl factory = new FeedInfoFactoryImpl();
		DocumentRoot root = factory.createDocumentRoot();
		root.setFeedInfo(feedInfo);
		contents.add(root);
		r.save(null);
		return true;		
	}

}
