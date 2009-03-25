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
package com.nokia.carbide.cpp.sysdoc.hover;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.nokia.carbide.cpp.sysdoc.internal.hover.core.ASTHoverControllerTest;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.BrowserControlCreatorTest;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverEntryPointTest;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverManagerTest;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.AsynchronousLookupTest;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.DevLibComparatorTest;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.DevLibHelperTester;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.DevLibLocatorTest;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.impl.DevLibPluginControllerTest;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.interX.InterXAnalyserTest;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.interX.InterXIndexerTest;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.sdk.SDKAgentTest;
import com.nokia.carbide.cpp.sysdoc.internal.hover.preferences.PreferenceInitializerTest;
import com.nokia.carbide.cpp.sysdoc.internal.hover.preferences.PreferencePageViewTest;
import com.nokia.carbide.cpp.sysdoc.internal.hover.preferences.PreferencesPageControllerTest;
import com.nokia.carbide.cpp.sysdoc.internal.hover.view.DeveloperLibraryViewTest;
import com.nokia.carbide.cpp.sysdoc.internal.hover.webserver.JettyWebServerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses( { 
		ASTHoverControllerTest.class,
		HoverEntryPointTest.class,
		PreferenceInitializerTest.class,
		PreferencesPageControllerTest.class, PreferencePageViewTest.class,
		HoverManagerTest.class, AsynchronousLookupTest.class,
		BrowserControlCreatorTest.class, SDKAgentTest.class,
		DevLibComparatorTest.class, DevLibLocatorTest.class,
		DevLibHelperTester.class, DevLibPluginControllerTest.class,
		InterXIndexerTest.class, InterXAnalyserTest.class,
		JettyWebServerTest.class, DeveloperLibraryViewTest.class
		})
public class AllTests {

}
