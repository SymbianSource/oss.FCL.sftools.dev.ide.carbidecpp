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
import java.util.List;

/**
 * A hover input originating from SysDocs.
 */
public class DevLibContent {
	protected URL url;
	protected String content;
	private List<PathNode> pathAsNodesList;

	public List<PathNode> getPathAsNodesList() {
		return pathAsNodesList;
	}


	public DevLibContent(URL url, String content) {
		this.url = url;
		this.content = content;
	}

	public DevLibContent(List<PathNode> pathAsNodesList) {
		this.pathAsNodesList = pathAsNodesList;
	}

	public URL getURL() {
		return url;
	}

	public String getContent() {
		return content;
	}

	@Override
	public String toString() {
		return "SysdocHoverInput(" + url + " " + content + ")";
	}
}