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


package com.nokia.carbide.templatewizard.symbian.tests;

import java.net.URL;

import junit.framework.TestCase;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cpp.internal.api.sdk.ui.TemplateUtils;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.internal.api.template.engine.Template;
import com.nokia.carbide.internal.templatewizard.symbian.tests.TestsPlugin;

public class TemplateUtilsTest extends TestCase {
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	private Template createTemplate(String filterArgs) {
		URL url = FileLocator.find(TestsPlugin.plugin.getBundle(), 
				new Path("Data/S60-ControlApplication/template.xml"), null);
		return new Template(url, null, null, null, filterArgs, null, null, null, null);
	}
	
	private ISymbianSDK createSymbianSDK(String framework, String sdkVersion) {
		return new TestSymbianSDK(framework, sdkVersion);
	}
	
	public void testSdkMatchesTemplate() {
		Template template2x = createTemplate("S60:2.1;2.6-2.8");
		Template template31 = createTemplate("S60:3.1");
		Template templateUIQ2x = createTemplate("UIQ:2.0-2.9");
		ISymbianSDK sdkS60_27 = createSymbianSDK("S60", "2.7");
		ISymbianSDK sdkS60_32 = createSymbianSDK("S60", "3.2");
		ISymbianSDK sdkUIQ_29 = createSymbianSDK("UIQ", "2.9");
		assertTrue(TemplateUtils.sdkMatchesTemplate(sdkS60_27, template2x));
		assertFalse(TemplateUtils.sdkMatchesTemplate(sdkS60_27, template31));
		assertFalse(TemplateUtils.sdkMatchesTemplate(sdkS60_27, templateUIQ2x));
		assertFalse(TemplateUtils.sdkMatchesTemplate(sdkS60_32, template2x));
		assertFalse(TemplateUtils.sdkMatchesTemplate(sdkS60_32, template31));
		assertFalse(TemplateUtils.sdkMatchesTemplate(sdkS60_32, templateUIQ2x));
		assertFalse(TemplateUtils.sdkMatchesTemplate(sdkUIQ_29, template2x));
		assertFalse(TemplateUtils.sdkMatchesTemplate(sdkUIQ_29, template31));
		assertTrue(TemplateUtils.sdkMatchesTemplate(sdkUIQ_29, templateUIQ2x));
	}

}
