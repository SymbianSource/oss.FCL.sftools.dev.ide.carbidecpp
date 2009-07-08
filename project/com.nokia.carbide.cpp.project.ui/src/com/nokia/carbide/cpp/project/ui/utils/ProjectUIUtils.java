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
package com.nokia.carbide.cpp.project.ui.utils;

import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;

/**
 * Utility class
 * @since 2.0
 */
public class ProjectUIUtils {
	
	/*
	 * Note that the following methods just wrap existing methods in the ProjectUIPlugin.  This
	 * was done because the ProjectUIPlugin is not API nor do we want to make everything in that
	 * class API.  Some people were already using the methods in ProjectUIPlugin even though they
	 * weren't API so we're leaving those there so we don't break existing plugins.
	 */

	/**
	 * Returns the preference option for whether or not to keep bld.inf and mmp files
	 * in sync with project changes.
	 * @return whether or not the option is enabled
	 */
	public static boolean keepProjectsInSync() {
		return ProjectUIPlugin.keepProjectsInSync();
	}

	/**
	 * Sets the preference option for whether or not to keep bld.inf and mmp files
	 * in sync with project changes.
	 * @param keepInSync true to enable the feature, false to disable
	 */
	public static void setKeepProjectsInSync(boolean keepInSync) {
		ProjectUIPlugin.setKeepProjectsInSync(keepInSync);
	}
	
	/**
	 * Returns the preference option for whether or not to add new files in the project
	 * to the bld.inf and mmp files.
	 * @return whether or not the option is enabled
	 */
	public static int getAddFilesToProjectOption() {
		return ProjectUIPlugin.getAddFilesToProjectOption();
	}

	/**
	 * Sets the preference option for whether or not to update bld.inf and mmp files
	 * when new files are added to the project.
	 * @param option true to enable the feature, false to disable
	 */
	public static void setAddFilesToProjectOption(int option) {
		ProjectUIPlugin.setAddFilesToProjectOption(option);
	}

	/**
	 * Returns the preference option for whether or not to update the bld.inf and mmp files
	 * when files in the project are moved or renamed.
	 * @return whether or not the option is enabled
	 */
	public static int getChangedFilesInProjectOption() {
		return ProjectUIPlugin.getChangedFilesInProjectOption();
	}

	/**
	 * Sets the preference option for whether or not to update bld.inf and mmp files
	 * when files in the project are moved or renamed.
	 * @param option true to enable the feature, false to disable
	 */
	public static void setChangedFilesInProjectOption(int option) {
		ProjectUIPlugin.setChangedFilesInProjectOption(option);
	}
	
	/**
	 * Returns the preference option for whether or not to index all source files 
	 * or just those of the build components
	 * @return the option
	 * @since 1.4
	 */
	public static boolean getIndexAllOption() {
		return ProjectUIPlugin.getIndexAllOption();
	}
	
	/**
	 * Sets the preference option for whether or not to index all source files 
	 * or just those of the build components
	 * @param option true to enable the feature, false to disable
	 * @since 1.4
	 */
	public static void setIndexAllOption(boolean option) {
		ProjectUIPlugin.setIndexAllOption(option);
	}
}
