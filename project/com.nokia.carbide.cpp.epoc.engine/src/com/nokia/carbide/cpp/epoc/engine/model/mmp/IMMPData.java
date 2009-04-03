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

package com.nokia.carbide.cpp.epoc.engine.model.mmp;

import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.epoc.engine.model.IData;

import org.eclipse.core.runtime.IPath;

import java.util.*;

/**
 * The data provider for MMP views.
 * <p>
 * This view contains a read-only copy of data derived from an IMMPView,
 * intended for caching.  Some interfaces available through the interface
 * may be modifiable, but any changes are ignored.
 * <p>
 * The usage of IPaths in this data is the same as with IMMPView.
 *
 */
public interface IMMPData extends IData<IMMPView> {
	/** 
	 * Get the combined TARGETPATH + TARGET path.  
	 * @return path to the file
	 * @see #getSingleArgumentSettings() for individual TARGETPATH, TARGET settings
	 */
	IPath getTargetFilePath();
	
	/** Get the paths of sources.  
	 * <p>
	 * This list provides unified access to source files.  
	 *	SOURCEPATH and SOURCE statements are managed behind the scenes.
	 * @see MMPViewPathHelper
	 */
	List<IPath> getSources(); 
	
	// 	Source path query
	/** 
	 * Get the paths provided by SOURCEPATH statements during the
	 * last parse. 
	 * <p>
	 * This is not the same as all the directories actually
	 * referenced (e.g. 'SOURCE subdir\file.cpp' does not  SOURCEPATH
	 * for 'subdir').
	 * <p>
	 * There is no need to explicitly manage source paths; they will be
	 * generated automatically based on the paths referenced in
	 * sources, resources, and documents.
	 * @see #getEffectiveSourcePaths()
	 * @see #getSources()
	 * @see #getUserResources()
	 * @see #getSystemResources()
	 * @see #getDocuments()
	 */
	IPath[] getRealSourcePaths();
		
	/** 
	 * Get the array of paths referenced in current sources, resources,
	 * and documents, which are likely to become SOURCEPATH statements.
	 * <p>
	 * This includes every distinct directory referenced (e.g. a 'SOURCE subdir\file.cpp'
	 * statement brings in a new effective directory for 'subdir').
	 * <p>  
	 * This is a copy of information derived from
	 * the current sources at the time of the query.
	 * <p>
	 * There is no need to explicitly manage source paths; they will be
	 * generated automatically based on the paths referenced in
	 * sources, resources, and documents.
	 * @see #getRealSourcePaths()
	 * @see #getSources()
	 * @see #getUserResources()
	 * @see #getSystemResources()
	 * @see #getDocuments()
	 */
	IPath[] getEffectiveSourcePaths();
		
	//	Include manipulation
	// These methods provide unified access to include directories.
	/** access/modify paths of includes.  */
	List<IPath> getUserIncludes(); 
	/** access/modify paths of system includes */
	List<IPath> getSystemIncludes();
	
	// 	Library manipulation
	//	This method provides unified access to library lists.  
	//	Presumably libraries are just filenames, but makmake allows 
	//	for paths.  Just support strings for now, which may have any 
	//	format.
	//	
	/** Get libraries 
	 * <p>
	 * Libraries are simple filenames with the .lib extension in all
	 * platforms.  (Technically these may also be relative
	 * paths.)
	 * <p>
	 * In makmake, this list also contributes to the debug library list.
	 * This API does not represent that.  This list is independent of
	 * the debug libraries list.
	 * @see #getDebugLibraries() 
	 */
	List<String> getLibraries();
	
	/** Get debug libraries; with case-insensitive membership tests 
	 * <p>
	 * Libraries are simple filenames with the .lib extension in all
	 * platforms.  (Technically these may also be relative
	 * paths.)
	 * <p>
	 * Note, makmake treats the debug libraries as an addition to normal
	 * libraries.  This API doesn't represent that.  This is just the list
	 * of libraries in DEBUGLIBRARY statements.   
	 */
	List<String> getDebugLibraries();
	/** Get static libraries; with case-insensitive membership tests 
	 * <p>
	 * Libraries are simple filenames with the .lib extension in all
	 * platforms.  (Technically these may also be relative
	 * paths.)
	 */
	List<String> getStaticLibraries();
	/** Get Win32 libraries; with case-insensitive membership tests 
	 * <p>
	 * Libraries are simple filenames with the .lib extension in all
	 * platforms.  (Technically these may also be relative
	 * paths.)
	 */
	List<String> getWin32Libraries();
	/** Get ASSP libraries; with case-insensitive membership tests 
	 * <p>
	 * Libraries are simple filenames with the .lib extension in all
	 * platforms.  (Technically these may also be relative
	 * paths.)
	 */
	List<String> getASSPLibraries();
	
	//	Resource manipulation
	/** Get the new-style resource blocks
	 * */
	List<IMMPResource> getResourceBlocks();

	/** Get the old-style RESOURCE statement. */
	List<IPath> getUserResources();
	/** Get the old-style SYSTEMRESOURCE statement. */
	List<IPath> getSystemResources();
	
	/** Get the old-style LANG statement.
	 * <p>
	 * NOTE: LANG as it appears inside START RESOURCE is contained in IMMPResource. 
	 */
	List<EMMPLanguage> getLanguages();
	
	//	Bitmap access
	List<IMultiImageSource> getMultiImageSources();
	
	/** 
	 * Get bitmaps from the unified set of START BITMAP
	 * blocks.
	 * <p>
	 * This aliases #getMultiImageSources().
	 * <p>
	 * IMMPBitmap is an extension of IMultiImageSource
	 * which specifically provides access to BMP files (rather than
	 * a set which may allow SVGs).  IMMPBitmap instances cannot hold SVGs.
	 */  
	List<IMMPBitmap> getBitmaps();
	//	AIF manipulation
	//	These methods allow setting the AIF configuration data 
	//	for the appropriate SDKs.   
	/** Get AIFs */ 
	List<IMMPAIFInfo> getAifs(); 

	//	Option setting
	//	These methods provide unified access to the simple options.  
	//	Any statements mirroring API above may also be acessed through 
	//	these methods.
	//	The getters and setters apply to specific statement types.  
	//	If they are used with inappropriate types (i.e. flag 
	//	setters with SOURCE statements), IllegalArgumentExceptions 
	//	(IAEs) are thrown.  
	
	//		Flags
	//	If a flag exists, it is considered set, else it is 
	//	considered unset, regardless of the sense (normal or “no-“) 
	//	of the flag.  
	/** 
	 * Get flags set in MMP.
	 * <p>
	 * Keys for non-flag statements may not be added without an IAE. 
	 */
	Set<EMMPStatement> getFlags();
	
	//		Single-argument values
	//	The value of a single-argument statement reflects the 
	//	last value set.
	/**
	 * Access/modify values of single-argument statements in MMP.
	 * <p>
	 * Anything exposed through other API is not in this map 
	 * (e.g. SOURCEPATH, DEFFILE).
	 */
	Map<EMMPStatement, String> getSingleArgumentSettings();
	
	//		List argument values
	//	The value of list argument statements is the ordered 
	//	union of all the statements of the same type.
	/**
	 * Get list values for list argument statements in MMP; with
	 * case-insensitive membership tests.
	 * <p>
	 * This also allows access to the libraries lists (which are provided as 
	 * separate API for convenience).
	 * Sources and includes must be accessed with their own API since they
	 * return lists of IPaths.  
	 */
	Map<EMMPStatement, List<String>> getListArgumentSettings();

	/** Get the UID2 value.  May be null. */
	String getUid2();

	/** Get the UID3 value.  May be null. */
	String getUid3();
	
	/** Get the OPTION options for given toolchains. 
	 * @return map of toolchain name to option string
	 */
	Map<String, String> getOptions();
	
	/** Get the LINKEROPTION options for given toolchains.
	 * @return map of toolchain name to option string
	 */
	Map<String, String> getLinkerOptions();
	
	/** Get the OPTION_REPLACE options for given toolchains.
	 * @return map of toolchain name to option string
	 */
	Map<String, String> getReplaceOptions();
	
	/**
	 * Get the paths to documents.  Like sources and
	 * resources, these are either project-relative or absolute in
	 * some way.
	 */
	List<IPath> getDocuments();
	
	/**
	 * Get the DEFFILE path.  May return null if no DEFFILE specified and
	 * the target does not require a .def file.<p>
	 * @return project-relative or full path to .def file, or null 
	 * @see #getSingleArgumentSettings()
	 * @see EMMPStatement#DEFFILE
	 */
	IPath getDefFile();

	/**
	 * Tell if the DEFFILE setting refers to a fixed directory, e.g.,
	 * if it has a path and is not a bare filename.
	 * <p>
	 * We maintain this
	 * as a distinct datum because IPath does not always cleanly represent
	 * the conversion of a string to a path (e.g. it may be canonicalized
	 * and/or lack a leading "./" due to workarounds).
	 * @return true: the DEFFILE statement has a directory embedded in it
	 * and will not be automatically moved to a platform-specific location
	 * @see #getSingleArgumentSettings()
	 * @see EMMPStatement#DEFFILE
	 */
	boolean isDefFileInFixedDirectory();

}
