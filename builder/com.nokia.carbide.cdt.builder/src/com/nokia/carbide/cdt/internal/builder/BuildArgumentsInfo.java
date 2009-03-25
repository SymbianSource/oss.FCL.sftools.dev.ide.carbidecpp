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
package com.nokia.carbide.cdt.internal.builder;

import org.eclipse.cdt.core.settings.model.ICStorageElement;

import com.nokia.carbide.cdt.builder.project.IBuildArgumentsInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

public class BuildArgumentsInfo implements IBuildArgumentsInfo {

	private static final String BLDMAKEBLDFILESARGSSTORAGE = "BLDMAKEBLDFILESARGSSTORAGE"; //$NON-NLS-1$
	private static final String BLDMAKECLEANARGSSTORAGE = "BLDMAKECLEANARGSSTORAGE"; //$NON-NLS-1$
	private static final String ABLDBUILDARGSSTORAGE = "ABLDBUILDARGSSTORAGE"; //$NON-NLS-1$
	private static final String ABLDEXPORTARGSSTORAGE = "ABLDEXPORTARGSSTORAGE"; //$NON-NLS-1$
	private static final String ABLDMAKEFILEARGSSTORAGE = "ABLDMAKEFILEARGSSTORAGE"; //$NON-NLS-1$
	private static final String ABLDLIBRARYARGSSTORAGE = "ABLDLIBRARYARGSSTORAGE"; //$NON-NLS-1$
	private static final String ABLDRESOURCEARGSSTORAGE = "ABLDRESOURCEARGSSTORAGE"; //$NON-NLS-1$
	private static final String ABLDTARGETARGSSTORAGE = "ABLDTARGETARGSSTORAGE"; //$NON-NLS-1$
	private static final String ABLDFINALARGSSTORAGE = "ABLDFINALARGSSTORAGE"; //$NON-NLS-1$
	private static final String ABLDCLEANARGSSTORAGE = "ABLDCLEANARGSSTORAGE"; //$NON-NLS-1$
	private static final String ABLDFREEZEARGSSTORAGE = "ABLDFREEZEARGSSTORAGE"; //$NON-NLS-1$
	
	
	private String bldmakeBldFilesArgs;
	private String bldmakeCleanArgs;
	private String abldBuildArgs;
	private String abldExportArgs;
	private String abldMakefileArgs;
	private String abldLibraryArgs;
	private String abldResourceArgs;
	private String abldTargetArgs;
	private String abldFinalArgs;
	private String abldCleanArgs;
	private String abldFreezeArgs;
	
	
	public BuildArgumentsInfo(ISymbianSDK sdk) {

		this.bldmakeBldFilesArgs = ""; //$NON-NLS-1$
		this.bldmakeCleanArgs = ""; //$NON-NLS-1$
		this.abldBuildArgs = ""; //$NON-NLS-1$
		this.abldExportArgs = ""; //$NON-NLS-1$
		this.abldMakefileArgs = ""; //$NON-NLS-1$
		this.abldLibraryArgs = ""; //$NON-NLS-1$
		this.abldResourceArgs = ""; //$NON-NLS-1$
		this.abldTargetArgs = ""; //$NON-NLS-1$
		this.abldFinalArgs = ""; //$NON-NLS-1$
		this.abldCleanArgs = ""; //$NON-NLS-1$
		this.abldFreezeArgs = ""; //$NON-NLS-1$

		if (sdk.isEKA2()) {
			this.abldFreezeArgs = "-r"; //$NON-NLS-1$
			
		}
	}

	public BuildArgumentsInfo(String bldmakeBldFilesArgs, String bldmakeCleanArgs, String abldBuildArgs, String abldExportArgs,
			String abldMakefileArgs, String abldLibraryArgs, String abldResourceArgs, String abldTargetArgs, String abldFinalArgs,
			String abldCleanArgs, String abldFreezeArgs) {

		this.bldmakeBldFilesArgs = bldmakeBldFilesArgs;
		this.bldmakeCleanArgs = bldmakeCleanArgs;
		this.abldBuildArgs = abldBuildArgs;
		this.abldExportArgs = abldExportArgs;
		this.abldMakefileArgs = abldMakefileArgs;
		this.abldLibraryArgs = abldLibraryArgs;
		this.abldResourceArgs = abldResourceArgs;
		this.abldTargetArgs = abldTargetArgs;
		this.abldFinalArgs = abldFinalArgs;
		this.abldCleanArgs = abldCleanArgs;
		this.abldFreezeArgs = abldFreezeArgs;
	}
	
	public void loadFromStorage(ICStorageElement rootStorage) {
		String value = rootStorage.getAttribute(BLDMAKEBLDFILESARGSSTORAGE);
		if (value != null) {
			bldmakeBldFilesArgs = value;
		}
		
		value = rootStorage.getAttribute(BLDMAKECLEANARGSSTORAGE);
		if (value != null) {
			bldmakeCleanArgs = value;
		}

		value = rootStorage.getAttribute(ABLDBUILDARGSSTORAGE);
		if (value != null) {
			abldBuildArgs = value;
		}

		value = rootStorage.getAttribute(ABLDEXPORTARGSSTORAGE);
		if (value != null) {
			abldExportArgs = value;
		}

		value = rootStorage.getAttribute(ABLDMAKEFILEARGSSTORAGE);
		if (value != null) {
			abldMakefileArgs = value;
		}

		value = rootStorage.getAttribute(ABLDLIBRARYARGSSTORAGE);
		if (value != null) {
			abldLibraryArgs = value;
		}

		value = rootStorage.getAttribute(ABLDRESOURCEARGSSTORAGE);
		if (value != null) {
			abldResourceArgs = value;
		}

		value = rootStorage.getAttribute(ABLDTARGETARGSSTORAGE);
		if (value != null) {
			abldTargetArgs = value;
		}

		value = rootStorage.getAttribute(ABLDFINALARGSSTORAGE);
		if (value != null) {
			abldFinalArgs = value;
		}

		value = rootStorage.getAttribute(ABLDCLEANARGSSTORAGE);
		if (value != null) {
			abldCleanArgs = value;
		}

		value = rootStorage.getAttribute(ABLDFREEZEARGSSTORAGE);
		if (value != null) {
			abldFreezeArgs = value;
		}
	}
	
	public void saveToStorage(ICStorageElement rootStorage) {
		if (bldmakeBldFilesArgs.trim().length() > 0) {
			rootStorage.setAttribute(BLDMAKEBLDFILESARGSSTORAGE, bldmakeBldFilesArgs);
		}

		if (bldmakeCleanArgs.trim().length() > 0) {
			rootStorage.setAttribute(BLDMAKECLEANARGSSTORAGE, bldmakeCleanArgs);
		}

		if (abldBuildArgs.trim().length() > 0) {
			rootStorage.setAttribute(ABLDBUILDARGSSTORAGE, abldBuildArgs);
		}

		if (abldExportArgs.trim().length() > 0) {
			rootStorage.setAttribute(ABLDEXPORTARGSSTORAGE, abldExportArgs);
		}

		if (abldMakefileArgs.trim().length() > 0) {
			rootStorage.setAttribute(ABLDMAKEFILEARGSSTORAGE, abldMakefileArgs);
		}

		if (abldLibraryArgs.trim().length() > 0) {
			rootStorage.setAttribute(ABLDLIBRARYARGSSTORAGE, abldLibraryArgs);
		}

		if (abldResourceArgs.trim().length() > 0) {
			rootStorage.setAttribute(ABLDRESOURCEARGSSTORAGE, abldResourceArgs);
		}

		if (abldTargetArgs.trim().length() > 0) {
			rootStorage.setAttribute(ABLDTARGETARGSSTORAGE, abldTargetArgs);
		}

		if (abldFinalArgs.trim().length() > 0) {
			rootStorage.setAttribute(ABLDFINALARGSSTORAGE, abldFinalArgs);
		}

		if (abldCleanArgs.trim().length() > 0) {
			rootStorage.setAttribute(ABLDCLEANARGSSTORAGE, abldCleanArgs);
		}

		if (abldFreezeArgs.trim().length() > 0) {
			rootStorage.setAttribute(ABLDFREEZEARGSSTORAGE, abldFreezeArgs);
		}
	}

	public String getBldmakeBldFilesArgs() {
		return bldmakeBldFilesArgs;
	}

	public String getBldmakeCleanArgs() {
		return bldmakeCleanArgs;
	}

	public String getAbldBuildArgs() {
		return abldBuildArgs;
	}

	public String getAbldExportArgs() {
		return abldExportArgs;
	}

	public String getAbldMakefileArgs() {
		return abldMakefileArgs;
	}

	public String getAbldLibraryArgs() {
		return abldLibraryArgs;
	}

	public String getAbldResourceArgs() {
		return abldResourceArgs;
	}

	public String getAbldTargetArgs() {
		return abldTargetArgs;
	}

	public String getAbldFinalArgs() {
		return abldFinalArgs;
	}

	public String getAbldCleanArgs() {
		return abldCleanArgs;
	}

	public String getAbldFreezeArgs() {
		return abldFreezeArgs;
	}
}
