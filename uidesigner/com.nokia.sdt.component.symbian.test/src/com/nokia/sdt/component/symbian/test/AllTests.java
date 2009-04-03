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

import com.nokia.sdt.component.symbian.componentchecker.ComponentChecker;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.nokia.sdt.component.symbian.test");
		//$JUnit-BEGIN$
		suite.addTestSuite(BadComponents.class);
		suite.addTestSuite(InitializerScriptAdapterTest.class);
		suite.addTestSuite(ReconcilePropertyScriptAdapterTest.class);
		suite.addTestSuite(InfoItemsAdapterTest.class);
		suite.addTestSuite(AttributeAdapterTest.class);
		suite.addTestSuite(ImplementationAdapterTest.class);
		suite.addTestSuite(ChildCommandExtenderAdapterTest.class);
		suite.addTestSuite(LayoutCodeAdapterTest.class);
		suite.addTestSuite(LayoutScriptAdapterTest.class);
		suite.addTestSuite(VisualAdapterTest.class);
		suite.addTestSuite(ImplementationDelegateTest.class);
		suite.addTestSuite(CompoundPropertyDefaultTest.class);
		suite.addTestSuite(ChildListenerAdapterTest.class);
		suite.addTestSuite(ComponentValidatorTest.class);
		suite.addTestSuite(ProviderTest.class);
		suite.addTestSuite(LocalizedComponentTests.class);
		suite.addTestSuite(PropertyEditorClassTest.class);
		suite.addTestSuite(ModelUpdaterAdapterTest.class);
		suite.addTestSuite(PropertyOverrideTest.class);
		suite.addTestSuite(InitializerCodeAdapterTest.class);
		suite.addTestSuite(CustomizerUICodeAdapterTest.class);
		suite.addTestSuite(QueryContainmentAdapterTest.class);
		suite.addTestSuite(SetValueCommandExtenderAdapterTest.class);
		suite.addTestSuite(DocumentationAdapterTest.class);
		suite.addTestSuite(ArrayPropertyTest.class);
		suite.addTestSuite(DesignerImageAdapterTest.class);
		suite.addTestSuite(PropertyListenerAdapterTest.class);
		suite.addTestSuite(LoaderTest.class);
		suite.addTestSuite(ComponentSDKSelectorTest.class);
		//$JUnit-END$
		suite.addTest(ComponentChecker.suite());
		return suite;
	}

}
