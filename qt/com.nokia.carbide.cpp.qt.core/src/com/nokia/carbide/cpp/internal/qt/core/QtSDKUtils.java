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
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.preference.IPreferenceStore;

import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.cpp.internal.api.utils.core.HostOS;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.trolltech.qtcppproject.QtProjectPlugin;
import com.trolltech.qtcppproject.preferences.PreferenceConstants;

/**
 * Wrapper utils for the Qt-ecilpse integration and obtaining information about internally installed Qt-sdks
 */
@SuppressWarnings({ "restriction" })
public class QtSDKUtils {
	
	/**
	 * Represents one Qt-SDK from the Qt global preferences
	 */
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
	
	/** Qt bin folder for internal SDK installs - epocroot relative - the deprecated internal location */
	private static final String OLD_QT_SDK_BIN_PATH = "epoc32/tools/qt";
	/** Qt bin folder for internal SDK installs - epocroot relative - the new internal location so that qmake is on the normal developer path with a subst'ed kit */
	private static final String QT_SDK_PATH = "epoc32/tools/";
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
	
	/**
	 * For the given Symbian SDK, test whether or not it has Qt internally built.
	 * Qualification relies on locating the qmake executable and the Qt include directory
	 * @param sdk - The Symbian SDK or source base to test
	 * @return - true if Qt is internally installed
	 */
	static private boolean isQtInternallyInstalled(ISymbianSDK sdk){
		
		if (getQmakeInstallationPath(sdk) != null && getQtIncludeDirectory(sdk) != null)
			return true;
		
		return false;
	}
	
	/**
	 * For the given Symbian SDK, get the Qt SDK name from the Qt global preferences.
	 * @param sdk
	 * @return The Qt SDK display name in the preferences, null if not found
	 */
	static public String getQtSDKNameForSymbianSDK(ISymbianSDK sdk){
		
		String epocRoot = sdk.getEPOCROOT();
		File oldQtBinPath = new Path(epocRoot).append(OLD_QT_SDK_BIN_PATH).toFile();
		File newQtBinPath = new Path(epocRoot).append(QT_SDK_PATH).toFile();
		File qtIncPath = new Path(epocRoot).append(QT_SDK_INC_PATH).toFile();
		
		refreshQtStoredSDKs();
		
		synchronized(qtSDKList) {
			if (qtSDKList.size() == 0){
				return null;
			}
			
			for (QtSDK qtSDK : qtSDKList){
				File currBinPath = new File(qtSDK.binPath);
				File currIncPath = new File(qtSDK.incPath);
				
				if ( (currBinPath.equals(oldQtBinPath) && currIncPath.equals(qtIncPath)) ||
					 (currBinPath.equals(newQtBinPath) && currIncPath.equals(qtIncPath))){
					return qtSDK.name;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Add a single Qt-SDK BIN and INLCUDE path to the Qt global preferences iff it is an internally built
	 * Qt-SDK and the BIN and INC path do not already exist.
	 * @param sdk
	 * @param makeDefault - Set the default name in the global Qt prefs
	 */
	static public void addQtSDKForSymbianSDK(ISymbianSDK sdk, boolean makeDefault){
	
		refreshQtStoredSDKs();
		if ((getQtSDKNameForSymbianSDK(sdk) == null) && isQtInternallyInstalled(sdk)){
			addQtSDK(sdk.getUniqueId(), getQmakeInstallationPath(sdk), getQtIncludeDirectory(sdk), makeDefault);
		} 
	}
	
	/**
	 * Add a Qt-SDK to the Qt global preferences
	 * @param name
	 * @param binPath
	 * @param incPath
	 * @param makeDefault
	 */
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
		
		ResourcesPlugin.getPlugin().getLog().log(Logging.newStatus(QtCorePlugin.getDefault(), 
						IStatus.INFO,
						"New Qt-Symbian SDK added to Qt global preferences: " + name, //$NON-NLS-1$
						null));
		
		store.setValue(PreferenceConstants.QTVERSION_COUNT, count + 1); // # of table items, base is 1 (i.e. not zero)
		
		refreshQtStoredSDKs();
	}
	
	/**
	 * Update the internal list of Qt-SDKs found in the Qt global preferences
	 */
	static void refreshQtStoredSDKs(){
		synchronized(qtSDKList) {
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
	}
	
	/**
	 * This method assumes the IProject has a Qt nature and sets the QTVERSION in the Qt project settings panel
	 * @param project
	 * @param qtSDKName
	 * @throws CoreException
	 */
	public static void setDefaultQtSDKForProject(IProject project, String qtSDKName) throws CoreException{
		project.setPersistentProperty(new QualifiedName("", QTVERSION), qtSDKName);
	}
	
	/**
	 * Assuming a Qt project, this retrieves the Qt project setting for the currently set Qt-SDK
	 * @param project
	 * @return
	 * @throws CoreException
	 */
	public static String getDefaultQtSDKForProject(IProject project) throws CoreException{
		return project.getPersistentProperty(new QualifiedName("", QTVERSION));
	}
	
	/**
	 * Retrieve the directory where qmake resides for a given SDK.
	 * @param sdk - The sdk to search
	 * @return The IPath where qmake resides, null if qmake cannot be found
	 */
	public static IPath getQmakeInstallationPath(ISymbianSDK sdk){
		String epocRoot = sdk.getEPOCROOT();
		String qmakeExecutable = HostOS.IS_WIN32 ? QT_QMAKE_WIN32 : QT_QMAKE_UNIX;
		
		// Test the new location first, where /epoc32/tools/ is normally on the %PATH%
		// else test the old location
		if (new Path(epocRoot).append(QT_SDK_PATH).append(qmakeExecutable).toFile().exists())
			return new Path(epocRoot + QT_SDK_PATH);
		else if (new Path(epocRoot).append(OLD_QT_SDK_BIN_PATH).append(qmakeExecutable).toFile().exists())
			return new Path(epocRoot).append(OLD_QT_SDK_BIN_PATH);

		return null;
	}
	
	/**
	 * Retrieve the qt include directory for a given SDK.
	 * @param sdk - The sdk to search
	 * @return The IPath if the include directory exists, null if the Qt include directory cannot be found
	 */
	public static IPath getQtIncludeDirectory(ISymbianSDK sdk){
		String epocRoot = sdk.getEPOCROOT();
		if (new File(epocRoot + QT_SDK_INC_PATH).exists() )
			return new Path(epocRoot + QT_SDK_INC_PATH);
			
		return null;
	}
}


