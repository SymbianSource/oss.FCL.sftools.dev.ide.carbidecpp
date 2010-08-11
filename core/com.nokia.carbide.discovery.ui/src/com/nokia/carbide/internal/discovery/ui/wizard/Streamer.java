/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.carbide.internal.discovery.ui.wizard;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.text.MessageFormat;
import java.util.Collection;

import com.nokia.cpp.internal.api.utils.core.Pair;

/**
 * Serializes feature infos and repository URIs into output stream as XML
 * Example serialized single test connection:
 * 
 * <blockquote><pre>
 *&lt;featuresConfiguration version="1"&gt;
 *	&lt;autoImportOriginalVersions value="false"/&gt;
 *	&lt;repository uri="http://cdn.symbian.org/carbide/updates/3.0/discovery"/&gt;
 *	&lt;feature id="com.nokia.example.feature.group" version="1.0.0"/&gt;
 *&lt;/featuresConfiguration&gt;
 * </pre></blockquote>
 */
class Streamer {

	private static final String CURRENT_VERSION = "1"; //$NON-NLS-1$
	
	private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n"; //$NON-NLS-1$
	private static final String FEATURES_CONFIG_START = "<featuresConfiguration version=\"" + CURRENT_VERSION + "\">\n"; //$NON-NLS-1$ //$NON-NLS-2$
	private static final String FEATURES_CONFIG_END = "</featuresConfiguration>\n"; //$NON-NLS-1$
	private static final String AUTO_IMPORT_ORIGINAL_VERSION_FMT = "\t<autoImportOriginalVersions value=\"{0}\"/>\n"; //$NON-NLS-1$
	private static final String REPOSITORY_FMT = "\t<repository uri=\"{0}\"/>\n"; //$NON-NLS-1$
	private static final String FEATURE_FMT = "\t<feature id=\"{0}\" version=\"{1}\"/>\n"; //$NON-NLS-1$

	public static void writeToXML(OutputStream os, Collection<URI> repositories, Collection<FeatureInfo> featureInfos) throws IOException {
		os.write(XML_HEADER.getBytes());
		os.write(FEATURES_CONFIG_START.getBytes());
		
		// write auto-import original versions
		String originalVersionElement = MessageFormat.format(AUTO_IMPORT_ORIGINAL_VERSION_FMT, false);
		os.write(originalVersionElement.getBytes());

		// write the repositories
		for (URI uri : repositories) {
			String repositoryElement = MessageFormat.format(REPOSITORY_FMT, uri);
			os.write(repositoryElement.getBytes());
		}
		
		// write the featureInfos
		for (FeatureInfo info : featureInfos) {
			String featureElement = MessageFormat.format(FEATURE_FMT, info.getId(), info.getVersion());
			os.write(featureElement.getBytes());
		}
		
		os.write(FEATURES_CONFIG_END.getBytes());
		os.close();
	}
	
	public static Pair<Collection<URI>, Collection<FeatureInfo>> readFromXML(InputStream is) {
		
		return null; // TODO
	}
}
