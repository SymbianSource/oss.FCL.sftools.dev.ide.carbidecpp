/*
* Copyright (c) 2007-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.model.mmp;

import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.*;
import com.nokia.carbide.internal.cpp.epoc.engine.model.BaseData;

import org.eclipse.core.runtime.IPath;

import java.util.*;

public class MMPData extends BaseData<IMMPView> implements IMMPData {
	private final List<IPath> sources;
	private final List<IPath> documents;
	private final List<IPath> userIncludes;
	private final List<IPath> systemIncludes;
	private final Map<EMMPStatement, List<String>> stringListArgumentSettings;
	private final List<IMMPBitmap> bitmaps;
	private final Set<EMMPStatement> flagSettings;
	private final Map<EMMPStatement, String> singleArgumentSettings;
	private final List<IMMPResource> resourceBlocks;
	private final List<IMMPAIFInfo> aifs;

	private final List<IPath> userResources;
	private final List<IPath> systemResources;
	private final List<EMMPLanguage> languages;
	private final String uid2;
	private final String uid3;
	private final Map<String, String> options;
	private final Map<String, String> linkerOptions;
	private final Map<String, String> replaceOptions;
	
	private IPath defFile;
	private IPath[] effectiveSourcePaths;
	private IPath targetPath;
	private Collection<IPath> sourcePaths;
	private boolean isDefFileInFixedDirectory;
	public MMPData(IMMPView view) {
		super(view);
		
		this.flagSettings =
			Collections.unmodifiableSet(
					new HashSet<EMMPStatement>(view.getFlags()));
		this.singleArgumentSettings = 
			Collections.unmodifiableMap(
					new HashMap<EMMPStatement, String>(view.getSingleArgumentSettings()));
		this.stringListArgumentSettings = 
			Collections.unmodifiableMap(
					new HashMap<EMMPStatement, List<String>>(view.getListArgumentSettings()));
		this.sources = 
			Collections.unmodifiableList(new ArrayList<IPath>(view.getSources()));
		this.documents = 
			Collections.unmodifiableList(new ArrayList<IPath>(view.getDocuments()));
		this.userIncludes =
			Collections.unmodifiableList(new ArrayList<IPath>(view.getUserIncludes()));
		this.userResources = 
			Collections.unmodifiableList(new ArrayList<IPath>(view.getUserResources()));
		this.systemIncludes = 
			Collections.unmodifiableList(new ArrayList<IPath>(view.getSystemIncludes()));
		this.systemResources = 
			Collections.unmodifiableList(new ArrayList<IPath>(view.getSystemResources()));
		this.aifs = 
			Collections.unmodifiableList(copyAIFList(view.getAifs()));
		this.bitmaps = 
			Collections.unmodifiableList(copyBitmapList(view.getBitmaps()));
		this.languages =
			Collections.unmodifiableList(new ArrayList<EMMPLanguage>(view.getLanguages()));
		this.resourceBlocks =
			Collections.unmodifiableList(copyResourceList(view.getResourceBlocks()));
		this.options = 
			Collections.unmodifiableMap(new HashMap<String, String>(view.getOptions()));
		this.linkerOptions = 
			Collections.unmodifiableMap(new HashMap<String, String>(view.getLinkerOptions()));
		this.replaceOptions = 
			Collections.unmodifiableMap(new HashMap<String, String>(view.getReplaceOptions()));
		this.uid2 = view.getUid2();
		this.uid3 = view.getUid3();
		this.defFile = view.getDefFile();
		this.effectiveSourcePaths = view.getEffectiveSourcePaths();
		this.targetPath = view.getTargetFilePath();
		this.sourcePaths = Collections.unmodifiableCollection(new ArrayList<IPath>(((MMPView)view).getSourcePaths()));
		this.isDefFileInFixedDirectory = view.isDefFileInFixedDirectory();
	}
	
	private List<IMMPAIFInfo> copyAIFList(List<IMMPAIFInfo> list) {
		List<IMMPAIFInfo> copy = new ArrayList<IMMPAIFInfo>();
		for (IMMPAIFInfo item : list)
			copy.add(item.copy());
		return copy;
	}
	private List<IMMPBitmap> copyBitmapList(List<IMMPBitmap> list) {
		List<IMMPBitmap> copy = new ArrayList<IMMPBitmap>();
		for (IMMPBitmap item : list)
			copy.add((IMMPBitmap) item.copy());
		return copy;
	}
	private List<IMMPResource> copyResourceList(List<IMMPResource> list) {
		List<IMMPResource> copy = new ArrayList<IMMPResource>();
		for (IMMPResource item : list)
			copy.add(item.copy());
		return copy;
	}
	
	public List<String> getASSPLibraries() {
		return stringListArgumentSettings.get(EMMPStatement.ASSPLIBRARY);
	}

	public List<IMMPAIFInfo> getAifs() {
		return aifs;
	}

	public List<IMMPBitmap> getBitmaps() {
		return bitmaps;
	}

	public List<String> getDebugLibraries() {
		return stringListArgumentSettings.get(EMMPStatement.DEBUGLIBRARY);
	}

	public IPath getDefFile() {
		return defFile;
	}

	public List<IPath> getDocuments() {
		return documents;
	}

	public IPath[] getEffectiveSourcePaths() {
		return effectiveSourcePaths;
	}

	public Set<EMMPStatement> getFlags() {
		return flagSettings;
	}

	public List<EMMPLanguage> getLanguages() {
		return languages;
	}

	public List<String> getLibraries() {
		return stringListArgumentSettings.get(EMMPStatement.LIBRARY);
	}

	public Map<String, String> getLinkerOptions() {
		return linkerOptions;
	}

	public Map<EMMPStatement, List<String>> getListArgumentSettings() {
		return stringListArgumentSettings;
	}

	public List<IMultiImageSource> getMultiImageSources() {
		return (List) bitmaps;
	}

	public Map<String, String> getOptions() {
		return options;
	}

	public IPath[] getRealSourcePaths() {
		return (IPath[]) sourcePaths.toArray(new IPath[sourcePaths.size()]);
	}

	public Map<String, String> getReplaceOptions() {
		return replaceOptions;
	}

	public List<IMMPResource> getResourceBlocks() {
		return resourceBlocks;
	}

	public Map<EMMPStatement, String> getSingleArgumentSettings() {
		return singleArgumentSettings;
	}

	public List<IPath> getSources() {
		return sources;
	}

	public List<String> getStaticLibraries() {
		return stringListArgumentSettings.get(EMMPStatement.STATICLIBRARY);
	}

	public List<IPath> getSystemIncludes() {
		return systemIncludes;
	}

	public List<IPath> getSystemResources() {
		return systemResources;
	}

	public IPath getTargetFilePath() {
		return targetPath;
	}

	public String getUid2() {
		return uid2;
	}

	public String getUid3() {
		return uid3;
	}

	public List<IPath> getUserIncludes() {
		return userIncludes;
	}

	public List<IPath> getUserResources() {
		return userResources;
	}

	public List<String> getWin32Libraries() {
		return stringListArgumentSettings.get(EMMPStatement.WIN32_LIBRARY);
	}

	public boolean isDefFileInFixedDirectory() {
		return this.isDefFileInFixedDirectory;
	}

}
