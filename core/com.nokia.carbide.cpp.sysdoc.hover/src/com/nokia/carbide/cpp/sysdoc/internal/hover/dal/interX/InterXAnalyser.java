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
 *				Deniz TURAN
 * Description: 
 * 				
 */
package com.nokia.carbide.cpp.sysdoc.internal.hover.dal.interX;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.nokia.carbide.cpp.sysdoc.internal.hover.exceptions.AbortSAXParsingException;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.Logger;

/**
 * 
 * Interchange file analyser for validation and properties extraction.
 */
public class InterXAnalyser {

	/**
	 * Checks if given file is an valid interchange file
	 * 
	 * @param f
	 *            : interchange file
	 * @return true if the file is an valid interchange file
	 */
	public static boolean isValidInterXFile(URL interXURL) {
		InterXProperties props = null;
		try {
			props = getHeaderAttributes(interXURL);
		} catch (Exception e) {
			Logger.logError(e);
			return false;
		}
		return props.isValid();
	}

	/**
	 * Exctract header information from an interchange file.
	 * 
	 * @param interXURL
	 *            interchange URL
	 * @return Propterties of interchange file.
	 * @throws IOException
	 */
	public static InterXProperties getHeaderAttributes(URL interXURL)
			throws IOException {
		URLConnection con = interXURL.openConnection();
		con.connect();
		InputStream in = con.getInputStream();
		// InputStream in =interXURL.openStream();
		final InterXProperties props = new InterXProperties(interXURL);
		DefaultHandler handler = new DefaultHandler() {

			@Override
			public void startElement(String uri, String localName, String name,
					Attributes attributes) throws SAXException {

				if (name
						.equals(InterXProperties.INTERCHANGEFILE_ELEMENT_SYSTEMWIDELINKS)) {
					props.extractAttributes(attributes);
					throw new AbortSAXParsingException();
				}
			}

			@Override
			public void endElement(String uri, String localName, String name)
					throws SAXException {
				super.endElement(uri, localName, name);
			}
		};

		// Parse interchange file
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(in, handler);
		} catch (AbortSAXParsingException e) {
			// Do NOTHING .. It is used to just abort parsing...
		} catch (Exception e) {
			Logger.logError(e, interXURL.toString());
		}
		in.close();
		return props;
	}
}
