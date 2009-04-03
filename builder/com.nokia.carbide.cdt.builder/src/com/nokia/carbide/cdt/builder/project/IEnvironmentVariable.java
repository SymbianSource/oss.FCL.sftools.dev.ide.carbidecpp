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
package com.nokia.carbide.cdt.builder.project;

/**
 * IEnvironmentVariable represents a single environment variable.
 * @see IEnvironmentVarsInfo
 */
public interface IEnvironmentVariable {
	
	/** The variable should be used as it comes from the OS */
	public final int ENV_VAR_USE_DEFAULT = 0;
	/** The value is added to the beginning of the current variable */
	public final int ENV_VAR_USE_PREPEND = 1;
	/** The value is added to the end of the current variable */
	public final int ENV_VAR_USE_APPEND = 2;
	/** The value replacest the current variable in it's entirety */
	public final int ENV_VAR_USE_REPLACE = 3;
	/** The variable is not used */
	public final int ENV_VAR_USE_UNDEFINE = 4;
	
	public static final String EQUALS = "="; //$NON-NLS-1$
	
	public static final String PATH_ENV_VAR_NAME = "PATH"; //$NON-NLS-1$
	public static final String EPOCROOT_ENV_VAR_NAME = "EPOCROOT"; //$NON-NLS-1$
	public static final String MWCSYM2INCLUDES_ENV_VAR_NAME = "MWCSYM2INCLUDES"; //$NON-NLS-1$
	public static final String MWCSYM2LIBRARIES_ENV_VAR_NAME = "MWSYM2LIBRARIES"; //$NON-NLS-1$
	public static final String MWCSYM2LIBRARYFILES_ENV_VAR_NAME = "MWSYM2LIBRARYFILES"; //$NON-NLS-1$
	
	String getName();
	
	String getValue();
	
	int getUse();
	
	void setName(String name);
	
	void setValue(String value);
	
	void setUse(int use);
}
