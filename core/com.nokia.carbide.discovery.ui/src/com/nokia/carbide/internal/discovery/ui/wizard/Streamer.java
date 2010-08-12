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
import java.net.URISyntaxException;
import java.text.MessageFormat;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.equinox.p2.metadata.Version;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.nokia.carbide.discovery.ui.Activator;

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
	
	private static final String ROOT_ELEMENT = "featuresConfiguration"; //$NON-NLS-1$
	private static final String WANTS_VERSIONS_ELEMENT = "wantsOriginalVersions"; //$NON-NLS-1$
	private static final String REPOSITORY_ELEMENT = "repository"; //$NON-NLS-1$
	private static final String FEATURE_ELEMENT = "feature"; //$NON-NLS-1$

	private static final String VERSION_ATTR = "version"; //$NON-NLS-1$
	private static final String VALUE_ATTR = "value"; //$NON-NLS-1$
	private static final String URI_ATTR = "uri"; //$NON-NLS-1$
	private static final String ID_ATTR = "id"; //$NON-NLS-1$
	
	private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n"; //$NON-NLS-1$
	
	private static final String ROOT_START = "<" + ROOT_ELEMENT + " " + VERSION_ATTR + "=\"" + CURRENT_VERSION + "\">\n"; //$NON-NLS-1$ //$NON-NLS-2$
	private static final String ROOT_END = "</" + ROOT_ELEMENT + ">\n"; //$NON-NLS-1$
	
	private static final String ORIGINAL_VERSION_FMT = "\t<" + WANTS_VERSIONS_ELEMENT + " " + VALUE_ATTR + "=\"{0}\"/>\n"; //$NON-NLS-1$
	private static final String REPOSITORY_FMT = "\t<" + REPOSITORY_ELEMENT + " " + URI_ATTR + "=\"{0}\"/>\n"; //$NON-NLS-1$
	private static final String FEATURE_FMT = "\t<" + FEATURE_ELEMENT + " " + ID_ATTR + "=\"{0}\" " + VERSION_ATTR + "=\"{1}\"/>\n"; //$NON-NLS-1$

	public static void writeToXML(OutputStream os, ImportExportData data) throws IOException {
		os.write(XML_HEADER.getBytes());
		os.write(ROOT_START.getBytes());
		
		// write auto-import original versions
		String originalVersionElement = MessageFormat.format(ORIGINAL_VERSION_FMT, data.getWantsVersions());
		os.write(originalVersionElement.getBytes());

		// write the repositories
		for (URI uri : data.getURIs()) {
			String repositoryElement = MessageFormat.format(REPOSITORY_FMT, uri);
			os.write(repositoryElement.getBytes());
		}
		
		// write the featureInfos
		for (FeatureInfo info : data.getFeatureInfos()) {
			String featureElement = MessageFormat.format(FEATURE_FMT, info.getId(), info.getVersion());
			os.write(featureElement.getBytes());
		}
		
		os.write(ROOT_END.getBytes());
		os.close();
	}
	
	private static class ReadHandler extends DefaultHandler {

		private final ImportExportData data;

		public ReadHandler(ImportExportData data) {
			this.data = data;
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if (ROOT_ELEMENT.equals(qName)) {
				String versionStr = attributes.getValue(VERSION_ATTR);
				if (!CURRENT_VERSION.equals(versionStr))
					throw new IllegalArgumentException(
							MessageFormat.format("Can only read version {0} of <{1}>", CURRENT_VERSION, ROOT_ELEMENT));
			}
			else if (WANTS_VERSIONS_ELEMENT.equals(qName)) {
				String wantsVersions = attributes.getValue(VALUE_ATTR);
				data.setWantsVersions(Boolean.parseBoolean(wantsVersions));
			}
			else if (REPOSITORY_ELEMENT.equals(qName)) {
				String uriStr = attributes.getValue(URI_ATTR);
				try {
					data.addURI(new URI(uriStr));
				} catch (URISyntaxException e) {
					Activator.logError(MessageFormat.format("Could not parse URI: {0}", uriStr), e);
				}
			}
			else if (FEATURE_ELEMENT.equals(qName)) {
				String id = attributes.getValue(ID_ATTR);
				String versionStr = attributes.getValue(VERSION_ATTR);
				try {
					Version version = Version.create(versionStr);
					data.addFeatureInfo(new FeatureInfo(id, version));
				} catch (IllegalArgumentException e) {
					Activator.logError(MessageFormat.format("Could not parse version: {0}", versionStr), e);
				}
			}
		}
	}
	
	public static ImportExportData readFromXML(InputStream is) throws SAXException, IOException, ParserConfigurationException {
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		ImportExportData data = new ImportExportData();
		parser.parse(is, new ReadHandler(data));
		return data;
	}
	
}
