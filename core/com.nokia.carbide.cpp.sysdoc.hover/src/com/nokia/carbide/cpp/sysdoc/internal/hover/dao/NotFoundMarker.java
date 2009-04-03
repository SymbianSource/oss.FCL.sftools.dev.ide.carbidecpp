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
package com.nokia.carbide.cpp.sysdoc.internal.hover.dao;

import java.net.URL;

/**
 * A marker object used in content controller to specify content is not found in
 * interchange map file.
 */
public class NotFoundMarker extends DevLibContent {

	public NotFoundMarker(URL url, String content) {
		super(url, content);
	}

	public NotFoundMarker() {
		this(null, null);
	}
}
