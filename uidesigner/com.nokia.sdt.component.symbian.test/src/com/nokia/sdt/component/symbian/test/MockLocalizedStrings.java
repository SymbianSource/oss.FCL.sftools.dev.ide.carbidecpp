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
package com.nokia.sdt.component.symbian.test;

import com.nokia.cpp.internal.api.utils.core.ILocalizedStrings;

import java.util.Locale;

public class MockLocalizedStrings implements ILocalizedStrings {

	public String getString(String key) {
		return null;
	}

	public String getString(String key, Locale l) {
		return null;
	}

	public String checkPercentKey(String s) {
		return s;
	}

	public boolean hasString(String key) {
		return false;
	}

	public boolean hasStringForLocale(String key, Locale l) {
		return false;
	}

}
