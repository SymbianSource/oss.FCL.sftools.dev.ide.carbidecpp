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
package com.nokia.carbide.cdt.builder;

import com.nokia.carbide.cdt.builder.project.IBuildArgumentsInfo;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDKFeatures;

/*
 * Wrapper for the build arguments settings in Carbide Build Configuration preferences
 */
public class BuildArgumentsInfo implements IBuildArgumentsInfo {

	public static final String BLDMAKEBLDFILESARGSSTORAGE = "BLDMAKEBLDFILESARGSSTORAGE"; //$NON-NLS-1$
	public static final String BLDMAKECLEANARGSSTORAGE = "BLDMAKECLEANARGSSTORAGE"; //$NON-NLS-1$
	public static final String ABLDBUILDARGSSTORAGE = "ABLDBUILDARGSSTORAGE"; //$NON-NLS-1$
	public static final String ABLDEXPORTARGSSTORAGE = "ABLDEXPORTARGSSTORAGE"; //$NON-NLS-1$
	public static final String ABLDMAKEFILEARGSSTORAGE = "ABLDMAKEFILEARGSSTORAGE"; //$NON-NLS-1$
	public static final String ABLDLIBRARYARGSSTORAGE = "ABLDLIBRARYARGSSTORAGE"; //$NON-NLS-1$
	public static final String ABLDRESOURCEARGSSTORAGE = "ABLDRESOURCEARGSSTORAGE"; //$NON-NLS-1$
	public static final String ABLDTARGETARGSSTORAGE = "ABLDTARGETARGSSTORAGE"; //$NON-NLS-1$
	public static final String ABLDFINALARGSSTORAGE = "ABLDFINALARGSSTORAGE"; //$NON-NLS-1$
	public static final String ABLDCLEANARGSSTORAGE = "ABLDCLEANARGSSTORAGE"; //$NON-NLS-1$
	public static final String ABLDFREEZEARGSSTORAGE = "ABLDFREEZEARGSSTORAGE"; //$NON-NLS-1$
	
	
	public String bldmakeBldFilesArgs;
	public String bldmakeCleanArgs;
	public String abldBuildArgs;
	public String abldExportArgs;
	public String abldMakefileArgs;
	public String abldLibraryArgs;
	public String abldResourceArgs;
	public String abldTargetArgs;
	public String abldFinalArgs;
	public String abldCleanArgs;
	public String abldFreezeArgs;
	
	
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

		if (sdk.getSupportedFeatures().contains(ISymbianSDKFeatures.IS_EKA2)) {
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
	
	public BuildArgumentsInfo(BuildArgumentsInfo argInfo) {

		this.bldmakeBldFilesArgs = argInfo.bldmakeBldFilesArgs;
		this.bldmakeCleanArgs = argInfo.bldmakeCleanArgs;
		this.abldBuildArgs = argInfo.abldBuildArgs;
		this.abldExportArgs = argInfo.abldExportArgs;
		this.abldMakefileArgs = argInfo.abldMakefileArgs;
		this.abldLibraryArgs = argInfo.abldLibraryArgs;
		this.abldResourceArgs = argInfo.abldResourceArgs;
		this.abldTargetArgs = argInfo.abldTargetArgs;
		this.abldFinalArgs = argInfo.abldFinalArgs;
		this.abldCleanArgs = argInfo.abldCleanArgs;
		this.abldFreezeArgs = argInfo.abldFreezeArgs;
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
