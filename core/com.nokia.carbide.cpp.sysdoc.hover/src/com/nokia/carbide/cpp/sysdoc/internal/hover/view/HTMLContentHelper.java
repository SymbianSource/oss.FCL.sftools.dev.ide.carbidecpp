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
package com.nokia.carbide.cpp.sysdoc.internal.hover.view;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.nokia.carbide.cpp.sysdoc.internal.hover.dao.PathNode;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.Logger;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.URLHelper;

/**
 * Creates or modify contents in a dynamic way for several circumstances such as
 * multiple entry for an API.
 */
public class HTMLContentHelper {

	public static String createMultiOptionHelpPageContent(
			List<PathNode> pathAsNodesList) {

		StringBuilder sb = new StringBuilder();

		for (PathNode node : pathAsNodesList) {
			// <a href="myLink"> boooo </a>

			URL url = null;
			try {
				String fullPath = URLHelper.getFullPath(node.getPath());
				url = new URL(fullPath);
			} catch (MalformedURLException e) {
				Logger.logError(e);
				continue;
			} catch (Exception e) {
				Logger.logDebug(e);
			}
			sb.append("<a  href= \"");
			sb.append(url.toString());
			sb.append("\">");
			sb.append(node.getComponent());
			sb.append("</a>");
			sb.append("<br>");
		}
		return sb.toString();
	}
}
