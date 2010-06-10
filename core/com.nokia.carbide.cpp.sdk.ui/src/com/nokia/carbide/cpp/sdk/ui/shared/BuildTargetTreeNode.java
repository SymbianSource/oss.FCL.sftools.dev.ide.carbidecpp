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


package com.nokia.carbide.cpp.sdk.ui.shared;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.TreeNode;

import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;

/**
 * A tree node representing a Symbian OS SDK.  This node's children will be the
 * list of available build configurations.
 */
public class BuildTargetTreeNode extends TreeNode {

	/**
	 * Constructs a new tree node for the given SDK
	 * @param value the SDK to create the tree node for
	 */
	public BuildTargetTreeNode(ISymbianSDK value) {
		this(value, false);
	}

	/**
	 * Constructs a new tree node for the given SDK
	 * @param value the SDK to create the tree node for
	 * @since 1.4
	 */
	public BuildTargetTreeNode(ISymbianSDK value, boolean sbsv2Project) {
		super(value);
		
		List<ISymbianBuildContext> configurations = sbsv2Project ? 
				SBSv2Utils.getFilteredSBSv2BuildContexts(value) : 
				value.getBuildInfo(ISymbianBuilderID.SBSV1_BUILDER).getFilteredBuildConfigurations(value);

		TreeNode[] children = new TreeNode[configurations.size()];
		int index = 0;
		for (ISymbianBuildContext config : configurations) {
			children[index++] = new TreeNode(config) {
				@Override
				public String toString() {
					ISymbianBuildContext context = (ISymbianBuildContext)getValue();
					String sdkId = context.getSDK().getUniqueId();
					return context.getDisplayString().replace("[" + sdkId + "]", "");
				}
			};
		}
		setChildren(children);
	}

	/**
	 * Returns the unique id the SDK associated with this node
	 */
	public String toString() {
		ISymbianSDK value = (ISymbianSDK) getValue();
		File f = new File(value.getEPOCROOT());
		if (!f.exists()){
			return value.getUniqueId() + " -- SDK location does not exist! Check Symbian SDKs!";
		}
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
	 * from the SDK preferences page.  Only enabled SDK's are used.  Each SDK node will
	 * have build configurations for children appropriate for the SDK.  These configurations
	 * are filtered based on the platform filtering preference panel.
	 * @return
	 */
	public static BuildTargetTreeNode[] getTreeViewerInput() {
		return getTreeViewerInput(false);
	}

	/**
	 * Gets the list of SDK tree nodes for use in a tree viewer.  The SDK's are gathered
	 * from the SDK preferences page.  Only enabled SDK's are used.  Each SDK node will
	 * have build configurations for children appropriate for the SDK.  These configurations
	 * are filtered based on the platform filtering preference panel.
	 * @param sbsv2Project true if this is an SBSv2 project which affects how the build
	 * configuration list is calculated
	 * @return array of BuildTargetTreeNode, or null
	 * @since 1.4
	 */
	public static BuildTargetTreeNode[] getTreeViewerInput(boolean sbsv2Project) {
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		List<ISymbianSDK> sdkList = sdkMgr.getSDKList();
		if (sdkList == null)
			return null;
		
		List<ISymbianSDK> sdkListCopy = new ArrayList<ISymbianSDK>();
		// Only add SDKs that are enabled
		for (ISymbianSDK currSDK : sdkList){
			if (currSDK.isEnabled()){
				sdkListCopy.add(currSDK);
			}
		}
		
		if (sbsv2Project) {
			// filter non-SBSv2 supported SDK's
			sdkListCopy = SBSv2Utils.getSupportedSDKs(sdkListCopy);
		}
		
		BuildTargetTreeNode[] input = new BuildTargetTreeNode[sdkListCopy.size()];
		int index = 0;
		for (ISymbianSDK sdk : sdkListCopy) {
			
			BuildTargetTreeNode treeNode = new BuildTargetTreeNode(sdk, sbsv2Project);
			if (treeNode.getChildren() != null){
				input[index++] = treeNode;
			}
		}
		
		// Filter out any SDKs that don't have configs
		BuildTargetTreeNode[] realInput = new BuildTargetTreeNode[index];
		index = 0;
		for (BuildTargetTreeNode currNode : input) {
			if (currNode != null){
				realInput[index++] = currNode;
			}
		}
		return realInput;
	}

}
