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
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.TreeNode;

import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildContext;
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
@SuppressWarnings("restriction")
public class BuildTargetTreeNode extends TreeNode {

	public static final String SDK_NODE_ERROR_EPOCROOT_INVALID = " SDK location does not exist! Check Symbian SDKs!"; //$NON-NLS

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
				value.getBuildInfo(ISymbianBuilderID.SBSV2_BUILDER).getFilteredBuildConfigurations() : 
				value.getBuildInfo(ISymbianBuilderID.SBSV1_BUILDER).getFilteredBuildConfigurations();
				
		if (configurations == null){
			return;
		}
		TreeNode[] children = new TreeNode[configurations.size()];
		int index = 0;
		for (ISymbianBuildContext config : configurations) {
			children[index++] = new TreeNode(config) {
				@Override
				public String toString() {
					ISymbianBuildContext context = (ISymbianBuildContext)getValue();
					String sdkId = context.getSDK().getUniqueId();
					String newDisplayString = stripSDKIDFromConfigName(context.getDisplayString(), sdkId);
					if (context instanceof ISBSv2BuildContext){
						ISBSv2BuildContext v2Context = (ISBSv2BuildContext)context;
						if (v2Context.getConfigQueryData().getConfigurationErrorMessage() != null && 
							v2Context.getConfigQueryData().getConfigurationErrorMessage().length() > 0){
							newDisplayString += " ERROR: " + v2Context.getConfigQueryData().getConfigurationErrorMessage();
						}
					} 
					return newDisplayString;
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
			return value.getUniqueId() + " (" + f.getAbsolutePath() + ") " + SDK_NODE_ERROR_EPOCROOT_INVALID;
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
	 * @param sbsv2Project true if this is an SBSv2 project which affects how the build
	 * configuration list is calculated
	 * @param IRunnableContext - a runnable context for which to update a progress monitor. Cannot be null.
	 * @return array of BuildTargetTreeNode, or null
	 * @since 1.4
	 */
	public static BuildTargetTreeNode[] getTreeViewerInput(final boolean sbsv2Project, IRunnableContext runnableContext) {
		
		final List<BuildTargetTreeNode> assembledInput = new ArrayList<BuildTargetTreeNode>();
		
		try {
			runnableContext.run(true, false, new IRunnableWithProgress(){

				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					
					String msgPrefix = "Building SDK/Configuration Model: "; //$NON-NLS-N$
					
					ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
					List<ISymbianSDK> sdkList = sdkMgr.getSDKList();
					monitor.beginTask(msgPrefix, sdkList.size() + 2);
					if (sdkList == null)
						return;
					
					monitor.worked(1);
					List<ISymbianSDK> sdkListCopy = new ArrayList<ISymbianSDK>();
					// Only add SDKs that are enabled
					for (ISymbianSDK currSDK : sdkList) {
						if (currSDK.isEnabled()) {
							sdkListCopy.add(currSDK);
						}
					}

					if (sbsv2Project) {
						// filter non-SBSv2 supported SDK's
						sdkListCopy = SBSv2Utils.getSupportedSDKs(sdkListCopy);
					}

					BuildTargetTreeNode[] input = new BuildTargetTreeNode[sdkListCopy
							.size()];
					int index = 0;
					monitor.worked(1);
					for (ISymbianSDK sdk : sdkListCopy) {
						monitor.worked(1);
						monitor.setTaskName(msgPrefix + sdk.getUniqueId());
						BuildTargetTreeNode treeNode = new BuildTargetTreeNode(
								sdk, sbsv2Project);
						if (treeNode.getChildren() != null || sbsv2Project) {
							input[index++] = treeNode;
						}
					}

					// Filter out any SDKs that don't have configs
					monitor.worked(1);
					for (BuildTargetTreeNode currNode : input) {
						if (currNode != null) {
							assembledInput.add(currNode);
						}
					}
					monitor.done();
				}
			
			
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if (assembledInput.size() == 0){
			return null;
		}

		return assembledInput.toArray(new BuildTargetTreeNode[assembledInput.size()]);
	}
	
	private static String stripSDKIDFromConfigName(String configName, String sdkID){
		return configName.replace("[" + sdkID + "]", "");
	}


}
