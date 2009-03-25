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
package com.nokia.carbide.cpp.sysdoc.internal.hover.dal;

import java.util.List;
import java.util.concurrent.Callable;

import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.interX.InterXIndexController;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dao.DevLibContent;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dao.NotFoundMarker;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dao.PathNode;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.Logger;

/**
 * Given the resolved API name, it fetches the content from interchange data
 * structure
 */
public class AsynchronousLookup implements Callable<DevLibContent> {
	protected String fqn;

	public AsynchronousLookup(String fqn) {
		this.fqn = fqn;
	}

	/*
	 * @see java.util.concurrent.Callable#call()
	 */
	public DevLibContent call() throws Exception {
		List<PathNode> pathAsNodesList = InterXIndexController
				.getInterXIndexer().getPathAsNodes(fqn);
		if (pathAsNodesList == null || pathAsNodesList.isEmpty()) {
			Logger.logDebug(fqn + " is not found in interchane file");
			return new NotFoundMarker();
		}
		return new DevLibContent(pathAsNodesList);
	}
}
