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

package com.nokia.carbide.internal.cpp.epoc.engine.model.bldinf;

import com.nokia.carbide.cpp.epoc.engine.model.bldinf.*;
import com.nokia.carbide.internal.cpp.epoc.engine.model.BaseData;

import java.util.*;


public class BldInfData extends BaseData<IBldInfView> implements IBldInfData {
	private List<String> platforms;
	private List<IExport> exports;
	private List<IMakMakeReference> maks;
	private List<IExport> testExports;
	private List<IMakMakeReference> testMaks;
	private List<IExtension> extensions;
	private List<IExtension> testExtensions;

	public BldInfData(IBldInfView view) {
		super(view);
		this.platforms = Collections.unmodifiableList(new ArrayList<String>(view.getPlatforms()));
		this.exports = Collections.unmodifiableList(copyExports(view.getExports()));
		this.testExports = Collections.unmodifiableList(copyExports(view.getTestExports()));
		this.maks = Collections.unmodifiableList(copyMaks(view.getMakMakeReferences()));
		this.testMaks = Collections.unmodifiableList(copyMaks(view.getTestMakMakeReferences()));
		this.extensions = Collections.unmodifiableList(copyExtensions(view.getExtensions()));
		this.testExtensions = Collections.unmodifiableList(copyExtensions(view.getTestExtensions()));
	}

	/**
	 * @param extensions2
	 * @return
	 */
	private List<IExtension> copyExtensions(List<IExtension> extensions) {
		List<IExtension> copy = new ArrayList<IExtension>();
		for (IExtension extension : extensions)
			copy.add(extension.copy());
		return copy;	
	}
	/**
	 * @param makMakeReferences
	 * @return
	 */
	private List<IMakMakeReference> copyMaks(
			List<IMakMakeReference> maks) {
		List<IMakMakeReference> copy = new ArrayList<IMakMakeReference>();
		for (IMakMakeReference mak : maks)
			copy.add(mak.copy());
		return copy;	
	}

	/**
	 * @param exports
	 * @return
	 */
	private List<IExport> copyExports(List<IExport> exports) {
		List<IExport> copy = new ArrayList<IExport>();
		for (IExport export : exports)
			copy.add(export.copy());
		return copy;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfData#getAllExtensions()
	 */
	public IExtension[] getAllExtensions() {
		List<IExtension> allExtensions = new ArrayList<IExtension>(this.extensions);
		allExtensions.addAll(testExtensions);
		return (IExtension[]) allExtensions.toArray(new IExtension[allExtensions.size()]);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfData#getAllMMPReferences()
	 */
	public IMMPReference[] getAllMMPReferences() {
		List<IMMPReference> mmps = new ArrayList<IMMPReference>();
		for (IMakMakeReference mak : maks) {
			if (mak instanceof IMMPReference)
				mmps.add((IMMPReference) mak);
		}
		for (IMakMakeReference mak : testMaks) {
			if (mak instanceof IMMPReference)
				mmps.add((IMMPReference) mak);
		}
		return (IMMPReference[]) mmps.toArray(new IMMPReference[mmps.size()]);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfData#getAllMakMakeReferences()
	 */
	public IMakMakeReference[] getAllMakMakeReferences() {
		List<IMakMakeReference> makmakes = new ArrayList<IMakMakeReference>();
		makmakes.addAll(maks);
		makmakes.addAll(testMaks);
		return (IMakMakeReference[]) makmakes.toArray(new IMakMakeReference[makmakes.size()]);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfData#getAllMakefileReferences()
	 */
	public IMakefileReference[] getAllMakefileReferences() {
		List<IMakefileReference> makefiles = new ArrayList<IMakefileReference>();
		for (IMakMakeReference mak : maks) {
			if (mak instanceof IMakefileReference)
				makefiles.add((IMakefileReference) mak);
		}
		for (IMakMakeReference mak : testMaks) {
			if (mak instanceof IMakefileReference)
				makefiles.add((IMakefileReference) mak);
		}
		return (IMakefileReference[]) makefiles.toArray(new IMakefileReference[makefiles.size()]);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfData#getExports()
	 */
	public List<IExport> getExports() {
		return exports;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfData#getExtensions()
	 */
	public List<IExtension> getExtensions() {
		return extensions;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfData#getMakMakeReferences()
	 */
	public List<IMakMakeReference> getMakMakeReferences() {
		return maks;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfData#getPlatforms()
	 */
	public List<String> getPlatforms() {
		return platforms;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfData#getTestExports()
	 */
	public List<IExport> getTestExports() {
		return testExports;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfData#getTestExtensions()
	 */
	public List<IExtension> getTestExtensions() {
		return testExtensions;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfData#getTestMakMakeReferences()
	 */
	public List<IMakMakeReference> getTestMakMakeReferences() {
		return testMaks;
	}


}
