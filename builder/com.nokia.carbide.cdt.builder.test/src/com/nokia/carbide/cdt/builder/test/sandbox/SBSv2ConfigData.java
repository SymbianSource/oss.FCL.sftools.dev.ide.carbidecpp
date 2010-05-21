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
* Contributors:
*
* Description: 
* Test the BldInfViewPathHelper class.
*
*/
package com.nokia.carbide.cdt.builder.test.sandbox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

public class SBSv2ConfigData implements ISBSv2ConfigData {

	/** The supporting SDK. May be null if it's a base configuration */
	ISymbianSDK sdk;
	
	/**
	 * A unique build alias. There can only be one alias definition, but an SDK can change the meaning of the alias 
	 */
	String buildAlias;
	/*
	 * The dotted name for the alias. One build alias can have multiple meanings, each defined in an SDK
	 */
	String meaning;
	
	String target = null;
	String platform = null;
	String releaseDirectory = null;
	
	public SBSv2ConfigData(String buildAlias, String meaning, ISymbianSDK sdk){
		this.buildAlias = buildAlias;
		this.meaning = meaning;
		if (sdk != null){
			this.sdk = sdk;
		} 
	}

	@Override
	public String getBuildAlias() {
		return buildAlias;
	}

	@Override
	public String getMeaning() {
		return meaning;
	}


	@Override
	public String getReleaseDirectory(ISymbianSDK sdk) {
		if (releaseDirectory == null){
			initDefaultConfigTargetInfo(sdk);
		}
		return releaseDirectory;
	}

	private void initDefaultConfigTargetInfo(ISymbianSDK sdk) {
		List<String> aliasOrMeaningArray = new ArrayList<String>();
		aliasOrMeaningArray.add(buildAlias);
		HashMap <String, String> configResponse = SBSv2QueryUtils.queryConfigTargetInfo(aliasOrMeaningArray, sdk);
		String releaseTree = configResponse.get(meaning);
		if (releaseTree == null){
			// TODO: Throw Exception
			return;
		}
		IPath releasePath = new Path(releaseTree);
		int epoc32SegmentIndex = 0;
		for (String segment : releasePath.segments()){
			if (segment.toLowerCase().equals("epoc32"))
				break;
			epoc32SegmentIndex++;
		}
		platform = releasePath.segment(epoc32SegmentIndex+2);
		target = releasePath.segment(epoc32SegmentIndex+3);
		String device = releasePath.getDevice();
		releaseDirectory = releasePath.removeFirstSegments(epoc32SegmentIndex).toPortableString();
		releaseDirectory = releaseDirectory.replace(device, "");
		
	}

	@Override
	public ISBSv2ConfigPreprocessorInfo getBuildData(ISymbianSDK sdk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTraditionalTarget(ISymbianSDK sdk) {
		if (target == null){
			initDefaultConfigTargetInfo(sdk);
		}
		
		return target;
	}

	@Override
	public String getTraditionalPlatform(ISymbianSDK sdk) {
		if (platform == null){
			initDefaultConfigTargetInfo(sdk);
		}
		
		return platform;
	}

	public String toString(){
		return "Alias = " + buildAlias + " : Meaning = " + meaning;
	}

	@Override
	public ISymbianSDK getSupportingSDK() {
		return sdk;
	}
	

}
