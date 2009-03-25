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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.eclipse.cdt.core.parser.util.ObjectMap;

import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.interX.InterXIndexController;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.Logger;

/**
 * 
 * A compound object holding entry information from interchange file. It holds
 * API's name, component name, path and sub paths.
 */
public class PathNode {
	private final String component;
	private final String name;
	private String path;
	private ObjectMap sub;

	public String getName() {
		return name;
	}

	public ObjectMap getSub() {
		if (sub == null) {
			sub = new ObjectMap(4);
		}
		return sub;
	}

	public String getComponent() {
		return component;
	}

	public String getPath() {
		return path;
	}


	public PathNode(final String name, final String component, final String path) {
		this.name = name;
		this.component = component;
		// lets construct the path at the beginning as it is immutable, and dont
		// waste time later to construct it
		constructPath(path);
	}

	/**
	 * Given a full qualified API name, path node is retrieved.
	 * 
	 * @param fqn
	 *            : full qualified API name
	 * @return pathnode which holds the SDL contentn path of API
	 */
	public PathNode visitFQNNodeChain(final String[] fqn) {
		ObjectMap s = sub;

		if (!fqn[0].equals(name)) {
			return null;
		}

		PathNode n = this;
		for (int i = 1; i < fqn.length; i++) {
			String f = fqn[i];
			if (s == null) {
				return n;
			}
			n = (PathNode) s.get(f);
			if (n != null) {
				s = n.sub;
			} else {
				return null;
			}
		}
		return n;
	}

	/**
	 * Constructs path. Resolves escpace characters, encoding and relative path
	 * issues.
	 * 
	 * @param pathRelT
	 *            : relative bare path which is extracted from interchange file
	 */
	private void constructPath(String pathRelT) {
		try {

			String pathRel = pathRelT.replace("&", "&amp;");
			String encodedPath = URLEncoder.encode(pathRel, "UTF-8");
			encodedPath = encodedPath.replace("*", "%2a");
			encodedPath = encodedPath.replace("+", "%20");
			path = InterXIndexController.getInterXIndexer().getRootDir() + "/"
					+ component + "/" + encodedPath;

			path = path.replace(' ', '_');
			path = path.replace("%23", "#");
			// path = URLHelper.getFullURI(path).toString();
		} catch (UnsupportedEncodingException e) {
			Logger.logError(e);
		}
	}
}
