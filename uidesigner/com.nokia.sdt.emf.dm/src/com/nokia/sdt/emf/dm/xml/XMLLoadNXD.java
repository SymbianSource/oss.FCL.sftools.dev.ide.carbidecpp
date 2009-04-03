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
package com.nokia.sdt.emf.dm.xml;

import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.impl.SAXWrapper;
import org.eclipse.emf.ecore.xmi.impl.XMLLoadImpl;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Override default EMF implementation to
 * perform XML customization not possible with
 * the EMF XMLMap
 *
 */
public class XMLLoadNXD extends XMLLoadImpl {

	public XMLLoadNXD(XMLHelper helper) {
		super(helper);
	}
	
	protected DefaultHandler makeDefaultHandler()
	{
		return new SAXWrapper(new XMLHandlerNXD(resource, helper, options));
	}

}
