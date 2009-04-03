/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.cpp.internal.api.sdk.ui;

import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.internal.api.template.engine.Template;

import org.eclipse.jface.viewers.IFilter;

public class TemplateSDKsFilter implements IFilter {
	
	public TemplateSDKsFilter() {
		super();
	}
	
	public boolean select(Object toTest) {
		boolean matchesOneSDK = false;
		if (toTest instanceof Template) {
			Template template = (Template) toTest;
			for (ISymbianSDK symbianSDK : TemplateUtils.getEnabledSDKs()) {
				if (TemplateUtils.sdkMatchesTemplate(symbianSDK, template)) {
					matchesOneSDK = true;
					break;
				}
			}
		}
		return matchesOneSDK;
	}
}