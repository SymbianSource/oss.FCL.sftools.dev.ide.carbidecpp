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
package com.nokia.carbide.cdt.internal.api.builder.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.viewers.TreeNode;

import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cpp.internal.api.sdk.SDKManagerInternalAPI;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;

public class BrokenConfigurationInProjectTreeNode extends TreeNode  {

	/**
	 * Constructs a new tree node for the given SDK
	 * @param value the SDK to create the tree node for
	 */
	public BrokenConfigurationInProjectTreeNode(ISymbianSDK value, ICarbideProjectInfo cpi) {
		super(value);
		ArrayList<ISymbianBuildContext> childConfig = new ArrayList<ISymbianBuildContext>();
		List<ICarbideBuildConfiguration> buildConfigList = cpi.getBuildConfigurations();
		
		for (ICarbideBuildConfiguration config : buildConfigList) {
			if (config.getSDK().getUniqueId().equals(value.getUniqueId())) {
				childConfig.add(config.getBuildContext());
			}
		}

		TreeNode[] children = new TreeNode[childConfig.size()];
		int index = 0;
		for (ISymbianBuildContext buildContext : childConfig) {
			children[index++] = new TreeNode(buildContext) {
				@Override
				public String toString() {
					ISymbianBuildContext context = (ISymbianBuildContext)getValue();
					return context.getDisplayString();
				}
			};
		}
		setChildren(children);
	}

	public String toString() {
		ISymbianSDK value = (ISymbianSDK) getValue();
		return value.getUniqueId();
	}
	
	/**
	 * Get the SDK for this node
	 * @return the ISymbianSDK object for this tree node
	 */
	public ISymbianSDK getSymbianSDK() {
		return (ISymbianSDK) getValue();
	}
	
	/**
	 * Gets the list of SDK tree nodes for use in a tree viewer.  The SDK's are gathered
	 * from the SDK preferernces page.  Both enabled and uninstalled SDK's are used.  
	 * Each SDK node will have build confgurations for children appropriate for the SDK.  
	 * These configurations are filtered based on the platform filtering preference panel.
	 * @return
	 */
	public static BrokenConfigurationInProjectTreeNode[] getTreeViewerInput(ICarbideProjectInfo cpi) {		
		if (cpi == null) {
			return new BrokenConfigurationInProjectTreeNode[0];
		}
		
		// Only add SDKs in project that are not enabled
		List<ICarbideBuildConfiguration> buildConfigList = cpi.getBuildConfigurations();
		HashMap<String,ISymbianSDK> projectUninstalledSdkHashMap = new HashMap<String, ISymbianSDK>();
		
		for (ICarbideBuildConfiguration config : buildConfigList) {
			ISymbianSDK currentSDK = config.getSDK();
			if (SDKManagerInternalAPI.getMissingSdk(currentSDK.getUniqueId()) != null) {
				projectUninstalledSdkHashMap.put(currentSDK.getUniqueId(), currentSDK);
			}
		}

		BrokenConfigurationInProjectTreeNode[] results = new BrokenConfigurationInProjectTreeNode[projectUninstalledSdkHashMap.size()];
		int index = 0;
		for (ISymbianSDK sdk : projectUninstalledSdkHashMap.values()) {
			BrokenConfigurationInProjectTreeNode treeNode = new BrokenConfigurationInProjectTreeNode(sdk, cpi);
			results[index++] = treeNode;
		}

		return results;

	}
}
