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
*/
package com.nokia.carbide.cpp.internal.qt.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.preference.IPreferenceStore;

import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.cpp.internal.api.utils.core.HostOS;
import com.trolltech.qtcppproject.QtProjectPlugin;
import com.trolltech.qtcppproject.preferences.PreferenceConstants;

@SuppressWarnings({ "restriction" })
public class QtSDKUtils {
	
	private static class QtSDK {
		
		QtSDK(String name, String incPath, String binPath){
			this.binPath = binPath;
			this.incPath = incPath;
			this.name = name;
		}
		
		public String name;
		public String binPath;
		public String incPath;
	}
	
	/** Qt bin folder for internal SDK installs - epocroot relative */
	private static final String QT_SDK_BIN_PATH = "epoc32/tools/qt";
	/** Qt include folder for internal SDK installs - epocroot relative */
	private static final String QT_SDK_INC_PATH = "epoc32/include/mw";
	
	/** qt subfolder under QT_INC_FOLDER */
	private static final String QT_FOLDER = "qt";
	private static final String QT_MKSPECS = "mkspecs";
	private static final String QT_QMAKE_WIN32 = "qmake.exe";
	private static final String QT_QMAKE_UNIX = "qmake";
	
	public static final String QT_DEFAULT_SDK_NAME = "<Default>";
	
	private static List<QtSDK> qtSDKList = new ArrayList<QtSDK>();
	
	// private data from QtProject.java
	private static final String QTVERSION = "com.trolltech.qtcppproject.properties.qtversion";
	
	static private boolean isQtInternallyInstalled(ISymbianSDK sdk){
		
		String epocRoot = sdk.getEPOCROOT();
		if (new File(epocRoot + QT_SDK_BIN_PATH).exists() && 
			new File(epocRoot + QT_SDK_INC_PATH).exists() &&
			new File(epocRoot + QT_SDK_INC_PATH + File.separator + QT_FOLDER).exists() &&
			new File(epocRoot + QT_SDK_BIN_PATH + File.separator + QT_MKSPECS).exists() ) 
		{
			if (HostOS.IS_WIN32 && new File(epocRoot + QT_SDK_BIN_PATH + File.separator + QT_QMAKE_WIN32).exists()){
				return true;
			} else if (HostOS.IS_UNIX && new File(epocRoot + QT_SDK_BIN_PATH + File.separator + QT_QMAKE_UNIX).exists()){
				return true;
			}
		}
		
		return false;
	}
	
	static public String getQtSDKNameForSymbianSDK(ISymbianSDK sdk){
		
		String epocRoot = sdk.getEPOCROOT();
		File qtBinPath = new File (epocRoot + QT_SDK_BIN_PATH);
		File qtIncPath = new File (epocRoot + QT_SDK_INC_PATH);
		
		refreshQtStoredSDKs();
		
		if (qtSDKList.size() == 0){
			return null;
		}
		
		for (QtSDK qtSDK : qtSDKList){
			File currBinPath = new File(qtSDK.binPath);
			File currIncPath = new File(qtSDK.incPath);
			
			if (currBinPath.equals(qtBinPath) && currIncPath.equals(qtIncPath)){
				return qtSDK.name;
			}
		}
		
		return null;
	}
	
	static public void addQtSDKForSymbianSDK(ISymbianSDK sdk, boolean makeDefault){
		
		refreshQtStoredSDKs();
		if ((getQtSDKNameForSymbianSDK(sdk) == null) && isQtInternallyInstalled(sdk)){
			IPath binPath = new Path(sdk.getEPOCROOT() + QT_SDK_BIN_PATH);
			IPath incPath = new Path(sdk.getEPOCROOT() + QT_SDK_INC_PATH);
			addQtSDK(sdk.getUniqueId(), binPath, incPath, makeDefault);
		}
	}
	
	static private void addQtSDK(String name, IPath binPath, IPath incPath, boolean makeDefault){

		IPreferenceStore store = QtProjectPlugin.getDefault().getPreferenceStore();
		int count = store.getInt(PreferenceConstants.QTVERSION_COUNT);
		
		// Store settings using zero-index base
		store.setValue(PreferenceConstants.QTVERSION_NAME + "."
				+ Integer.toString(count), name);
		store.setValue(PreferenceConstants.QTVERSION_BINPATH + "."
				+ Integer.toString(count), binPath.toOSString());
		store.setValue(PreferenceConstants.QTVERSION_INCLUDEPATH + "."
				+ Integer.toString(count), incPath.toOSString());
		
		if (makeDefault || count == 0){
			store.setValue(PreferenceConstants.QTVERSION_DEFAULT, count);
		}
		
		store.setValue(PreferenceConstants.QTVERSION_COUNT, count + 1); // # of table items, base is 1 (i.e. not zero)
		
		refreshQtStoredSDKs();
	}
	
	static void refreshQtStoredSDKs(){
		
		qtSDKList.clear();
		
		IPreferenceStore store = QtProjectPlugin.getDefault().getPreferenceStore();
		int count = store.getInt(PreferenceConstants.QTVERSION_COUNT);
		for (int i = 0; i < count; i++) {
			String nameKey = PreferenceConstants.QTVERSION_NAME + "."
					+ Integer.toString(i);
			String binpathKey = PreferenceConstants.QTVERSION_BINPATH + "."
					+ Integer.toString(i);
			String includepathKey = PreferenceConstants.QTVERSION_INCLUDEPATH
					+ "." + Integer.toString(i);
			String name = "";
			String binPath = "";
			String incPath = "";
			if (store.contains(nameKey)) {
				name = store.getString(nameKey);
			}
			if (store.contains(binpathKey)) {
				binPath = store.getString(binpathKey);
			}
			if (store.contains(includepathKey)) {
				incPath = store.getString(includepathKey);
			}
			
			QtSDK qtSDK = new QtSDK(name, incPath, binPath);
			qtSDKList.add(qtSDK);
		}
	}
	
	public static void setDefaultQtSDKForProject(IProject project, String qtSDKName) throws CoreException{
		project.setPersistentProperty(new QualifiedName("", QTVERSION), qtSDKName);
	}
	
	public static String getDefaultQtSDKForProject(IProject project) throws CoreException{
		return project.getPersistentProperty(new QualifiedName("", QTVERSION));
	}
	
}


