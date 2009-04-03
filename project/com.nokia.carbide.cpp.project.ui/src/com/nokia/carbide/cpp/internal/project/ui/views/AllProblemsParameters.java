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
package com.nokia.carbide.cpp.internal.project.ui.views;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.views.markers.FiltersContributionParameters;

public class AllProblemsParameters extends FiltersContributionParameters {

	final static int SEVERITY_ERROR = 1 << IMarker.SEVERITY_ERROR;
	final static int SEVERITY_WARNING = 1 << IMarker.SEVERITY_WARNING;
	final static int SEVERITY_INFO = 1 << IMarker.SEVERITY_INFO;

	private static Map<String, Integer> problemsMap;
	static {
		problemsMap = new HashMap<String, Integer>();
		problemsMap.put(IMarker.SEVERITY, new Integer(SEVERITY_ERROR + SEVERITY_WARNING + SEVERITY_INFO));
	}

	/**
	 * Create a new instance of the receiver.
	 */
	public AllProblemsParameters() {
		super();
	}

	@Override
	public Map<String, Integer> getParameterValues() {
		return problemsMap;
	}

}
