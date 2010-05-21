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
* Test the BldInfViewPathHelper class.
*
*/
package com.nokia.carbide.cdt.builder.test.sandbox;

import java.util.List;

import org.eclipse.core.runtime.IPath;

/**
 * TODO: This API is not yet developed
 * 
 * @noimplement
 */
public interface ISBSv2ConfigPreprocessorInfo {

	List<String> getMacroList();
	
	List<IPath>	getSystemIncludes();
	
	IPath getVariantConfig();
	
	String getCompiler();
	
	IPath getCompilerPrefix();
	
	ISBSv2ConfigData getSBSv2QueryConfigData();
	
}
