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

package com.nokia.carbide.cpp.project.core.tests;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.preference.IPreferenceStore;

import com.nokia.carbide.cpp.internal.qt.core.QtCorePlugin;
import com.nokia.carbide.cpp.project.core.ProjectCorePlugin;
import com.trolltech.qtcppproject.QtProjectPlugin;
import com.trolltech.qtcppproject.preferences.PreferenceConstants;

@SuppressWarnings("restriction")
public class QtPropertiesTest extends TestCase {
	
	static int QtSDKCount = 0;
	static String defaultQtVersionName = "";
	
	public void testReadQtPropertiesFirstTime() throws Exception {

		IPreferenceStore store = QtProjectPlugin.getDefault().getPreferenceStore();
		
		assertNotNull("Can't get QtProjectPlugins prefs store.", store);
		
		QtSDKCount = store.getInt(PreferenceConstants.QTVERSION_COUNT);
		assertEquals("There should be no Qt SDKs installed.", 0, QtSDKCount);

		defaultQtVersionName = store.getString(PreferenceConstants.QTVERSION_DEFAULT);
		assertEquals("", defaultQtVersionName);
	}
	
	
	public void testCreateQtSDKEntries() throws Exception {
		
		IPreferenceStore store = QtProjectPlugin.getDefault().getPreferenceStore();
		
		int count = store.getInt(PreferenceConstants.QTVERSION_COUNT);
		
		createQtSDKEntry(store, "TestSDK1", true);
		createQtSDKEntry(store, "TestSDK2", true);
		createQtSDKEntry(store, "TestSDK3", false);
		createQtSDKEntry(store, "TestSDK4", false);
		
		store.setValue(PreferenceConstants.QT_AUTOSETMKSPEC, false);
		store.setValue(PreferenceConstants.QT_AUTOSETMKCMD, false);
		
		count = store.getInt(PreferenceConstants.QTVERSION_COUNT);
		assertEquals("Number of Qt SDKs count if off", count, QtSDKCount);
		
		int defaultSDK = store.getInt(PreferenceConstants.QTVERSION_DEFAULT); // zero-based index
		assertEquals(1, defaultSDK);
	}
	
	public void testCheckForDuplicateSDK() throws Exception {
		
		IPreferenceStore store = QtProjectPlugin.getDefault().getPreferenceStore();
		
		assertEquals(0, findSDKByName(store, "TestSDK1"));
		assertEquals(3, findSDKByName(store, "TestSDK4"));
		assertEquals(-1, findSDKByName(store, "foo"));
	}
	
	public void testCreateProjectAndSetQtDefult() throws Exception {
		// create a simple project
		IProject project = null;
		try {
			project = ProjectCorePlugin.createProject("qtTestProject", null);
			assertNotNull(project);
			
			QtCorePlugin.addQtNature(project, new NullProgressMonitor());
			
			
		} catch (CoreException e) {
			fail();
		}
		
		// private data from QtProject.java
		String QTVERSION = "com.trolltech.qtproject.properties.qtversion";

		String version = project.getPersistentProperty(new QualifiedName("", QTVERSION));
		
		// save 
		project.setPersistentProperty(new QualifiedName("", QTVERSION), "TestSDK1");

			// read
		version = project.getPersistentProperty(new QualifiedName("", QTVERSION));
		assertEquals("TestSDK1", version);

	}
	
	private void createQtSDKEntry(IPreferenceStore store, String name, boolean makeDefault){
		QtSDKCount = store.getInt(PreferenceConstants.QTVERSION_COUNT);
		
		store.setValue(PreferenceConstants.QTVERSION_COUNT, QtSDKCount + 1); // # of table items, not zero based
		store.setValue(PreferenceConstants.QTVERSION_NAME + "."
				+ Integer.toString(QtSDKCount), name);
		store.setValue(PreferenceConstants.QTVERSION_BINPATH + "."
				+ Integer.toString(QtSDKCount), "T:\\epoc32\\tools\\qt");
		store.setValue(PreferenceConstants.QTVERSION_INCLUDEPATH + "."
				+ Integer.toString(QtSDKCount), "T:\\epoc32\\include\\mw");
		
		if (makeDefault){
			store.setValue(PreferenceConstants.QTVERSION_DEFAULT, QtSDKCount);
		}
		
		QtSDKCount = store.getInt(PreferenceConstants.QTVERSION_COUNT);

	}
	
	/**
	 * Check and see if an SDK exists by name and return its zero-based index. Return -1 if not found.
	 * @return
	 */
	private int findSDKByName(IPreferenceStore store, String sdkName){
		int foundIndex = -1;
		
		int count = store.getInt(PreferenceConstants.QTVERSION_COUNT);
		for (int i = 0; i < count; i++) {
			String nameKey = PreferenceConstants.QTVERSION_NAME + "."
					+ Integer.toString(i);
			String binpathKey = PreferenceConstants.QTVERSION_BINPATH + "."
					+ Integer.toString(i);
			String includepathKey = PreferenceConstants.QTVERSION_INCLUDEPATH
					+ "." + Integer.toString(i);
			String name = "";
			//String binpath = "";
			//String includepath = "";
			if (store.contains(nameKey)) {
				name = store.getString(nameKey);
				if (name.equalsIgnoreCase(sdkName)){
					foundIndex = i;
					break;
				}
			}
		}
		
		return foundIndex;
	}
	
	
}
