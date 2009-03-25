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

package com.nokia.carbide.cpp.epoc.engine.model.bldinf;

import com.nokia.carbide.cpp.epoc.engine.model.IView;

import java.util.List;

/**
 * A view onto bld.inf contents.
 * 
 */
public interface IBldInfView extends IView, IBldInfData {
	/**
	 * Access/modify supported platforms, with case-insensitive membership tests
	 * <p>
	 * This list is not validated. It contains literal strings like “default”,
	 * “-winscw”, etc. The client is expected to understand what to put here.
	 * Friendlier APIs may be provided if we can get a good story on what these
	 * cases mean:
	 * <li>Is there a way to set platforms to “default” then get the actual
	 * list back? Do we want to “flatten” the list in any case? Note, the list
	 * can change between SDKs, which are difficult to distinguish in #ifdefs.
	 * <li>If the editor shows the actual list of platforms, what does the
	 * source eventually show? Given different platforms that may come and go in
	 * the future (and between SDKs), should the actual list even be displayed?
	 * 
	 * @return
	 */
	List<String> getPlatforms(); 
	
	/**
	 * Access/modify prj_exports contents
	 * @return
	 */
	List<IExport> getExports();
	/**
	 * Access/modify prj_testexports
	 * contents (may share entries from #getExports())
	 * @return
	 */
	List<IExport> getTestExports();
	
	/**
	 * Access/modify prj_mmpfiles contents (may share entries from
	 * #getMakMakeReferences())
	 * @return
	 */
	List<IMakMakeReference> getMakMakeReferences(); 

	/**
	 * Access/modify test_mmpfiles contents (may share entries from
	 * #getTestMakMakeReferences())
	 * @return
	 */
	List<IMakMakeReference> getTestMakMakeReferences();
	
	/**
	 * Get copy of list of normal and test MMPs and makefiles.
	 * <p>
	 * This is a copy of information derived from
	 * the view contents at the time of the query.
	 * <p> 
	 * (modify through #getMakMakeReferences() / #getTestMakMakeReferences()) 
	 */
	IMakMakeReference[] getAllMakMakeReferences();
	
	/**
	 * Get copy of list of normal and test MMP files.
	 * <p>
	 * This is a copy of information derived from
	 * the view contents at the time of the query.
	 * <p> 
	 * (modify through #getMakMakeReferences() / #getTestMakMakeReferences()) 
	 */
	IMMPReference[] getAllMMPReferences();

	/**
	 * Get copy of list of normal and test makefiles 
	 * <p>
	 * This is a copy of information derived from
	 * the view contents at the time of the query.
	 * <p> 
	 * (modify through #getMakMakeReferences() / #getTestMakMakeReferences()) 
	 */
	IMakefileReference[] getAllMakefileReferences();

	// Factories for the higher-level types. There may be overloads for common
	// constructor arguments.
	/**
	 * Creates, doesn’t add
	 */
	IExport createExport(); 
	/**
	 * Creates, doesn’t add
	 * @return
	 */
	IMMPReference createMMPReference();
	/**
	 * Creates, doesn’t add
	 * @return
	 */
	IMakefileReference createMakefileReference();

	/**
	 * Access/modify the PRJ_EXTENSIONS contents.
	 * @return list of IExtension, read/modify
	 */
	List<IExtension> getExtensions();
	
	/**
	 * Acess/modify the PRJ_TESTEXTENSIONS contents.
	 * @return list of IExtension, read/modify
	 */
	List<IExtension> getTestExtensions();
	
	/**
	 * Create an IExtension.  Not added to the view.
	 * @return empty IExtension instance
	 */
	IExtension createExtension();
	
	/**
	 * Get all the IExtensions from the normal and test sections.
	 * @return array of IExtension, never null
	 */
	IExtension[] getAllExtensions();
	
	IBldInfData getData();
}
