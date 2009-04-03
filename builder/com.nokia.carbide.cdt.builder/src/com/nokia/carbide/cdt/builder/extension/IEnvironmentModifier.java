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
package com.nokia.carbide.cdt.builder.extension;

import java.util.Map;

import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;

/**
 * Allows those using the EnvironmentModifier extension point to modify the
 * environment variables used by the Carbide.c++ builder.
 *
 */
public interface IEnvironmentModifier {

	/**
	 * Take the existing environment and return the desired modified environment.
	 * @param buildContext the build configuration being built
	 * @param environment a map of the current set of environment variables <name, value>
	 * @return the modified map of environment variables
	 */
	Map<String, String> getModifiedEnvironment(ICarbideBuildConfiguration buildContext, Map<String, String> environment);
}
