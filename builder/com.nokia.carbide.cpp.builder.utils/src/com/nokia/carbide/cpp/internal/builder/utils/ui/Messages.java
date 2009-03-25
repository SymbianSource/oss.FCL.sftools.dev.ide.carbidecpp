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
package com.nokia.carbide.cpp.internal.builder.utils.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.nokia.carbide.cpp.internal.builder.utils.ui.messages"; //$NON-NLS-1$
	public static String PreprocessPreferencePage_CppArguments;
	public static String PreprocessPreferencePage_CppArgumentsToolTip;
	public static String PreprocessPreferencePage_OutputToConsole;
	public static String PreprocessPreferencePage_OutputToConsoleToolTip;
	public static String PreprocessPreferencePage_OutputToFile;
	public static String PreprocessPreferencePage_OutputToFileToolTip;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
