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
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectModifier;
import com.nokia.carbide.cdt.internal.builder.ui.CarbideCPPBuilderUIHelpIds;
import com.nokia.carbide.cpp.internal.api.sdk.ISDKManagerInternal;
import com.nokia.carbide.cpp.internal.qt.core.QtConfigFilter;
import com.nokia.carbide.cpp.internal.qt.core.QtCorePlugin;
import com.nokia.carbide.cpp.internal.qt.core.QtSDKFilter;
import com.nokia.carbide.cpp.sdk.core.*;
import com.nokia.carbide.cpp.sdk.ui.shared.BuildTargetTreeNode;
import com.nokia.cpp.internal.api.utils.core.Check;

public class ManageConfigurationsDialog extends TrayDialog {
	
	protected ContainerCheckedTreeViewer properSdkViewer;
	protected ContainerCheckedTreeViewer missingSdkViewer;
	private FilteringContentProviderWrapper filteringContentProviderWrapper;
	private final ICarbideProjectInfo cpi;
	
	static class FilteringContentProviderWrapper implements ITreeContentProvider {

		private ITreeContentProvider wrappedProvider;
		private IFilter filter;
		private IFilter configFilter;
		
		public FilteringContentProviderWrapper(ITreeContentProvider wrappedProvider) {
			super();
			Check.checkArg(wrappedProvider);
			this.wrappedProvider = wrappedProvider;
		}

		public Object[] getElements(Object inputElement) {
			ArrayList<Object> filteredElements = new ArrayList<Object>();
			Object[] elements = wrappedProvider.getElements(inputElement);
			for (int i = 0; i < elements.length; i++) {
				Object object = elements[i];
				if (filter == null || filter.select(object))
					filteredElements.add(object);
			}
			
			return filteredElements.toArray();
		}

		public void dispose() {
			wrappedProvider.dispose();
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			wrappedProvider.inputChanged(viewer, oldInput, newInput);
		}

		public Object[] getChildren(Object parentElement) {
			List<Object> filteredElements = new ArrayList<Object>();
			Object[] elements = wrappedProvider.getChildren(parentElement);
			if (elements != null) {
				for (int i = 0; i < elements.length; i++) {
					Object object = elements[i];
					if (configFilter == null || configFilter.select(object))
						filteredElements.add(object);
				}
			}
			
			return filteredElements.toArray();
		}

		public Object getParent(Object element) {
			return wrappedProvider.getParent(element);
		}

		public boolean hasChildren(Object element) {
			return wrappedProvider.hasChildren(element);
		}

		public void setFilter(IFilter filter) {
			this.filter = filter;
		}
		
		public void setConfigFilter(IFilter filter) {
			this.configFilter = filter;
		}
	}
	
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public ManageConfigurationsDialog(Shell parentShell, ICarbideProjectInfo cpi) {
		super(parentShell);
		this.cpi = cpi;
		setShellStyle(getShellStyle() | SWT.RESIZE);
		
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		if (!sdkMgr.checkDevicesXMLSynchronized()){
			if (sdkMgr instanceof ISDKManagerInternal){
				ISDKManagerInternal sdkMgrInternal = (ISDKManagerInternal)sdkMgr;
				sdkMgrInternal.fireDevicesXMLChanged();
			}	
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(Messages.getString("ManageConfigurationsDialog.Add_Remove_Build_Configs")); //$NON-NLS-1$
	}
	
	@Override
	protected void okPressed() {
		
		saveConfigurations();
		
		super.okPressed();
	}
	
	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		container.setLayout(gridLayout);
		
		final Label sdkConfigLabel = new Label(container, SWT.NONE);
		sdkConfigLabel.setToolTipText(Messages.getString("ManageConfigurationsDialog.Config_Label_ToolTip")); //$NON-NLS-1$
		sdkConfigLabel.setText(Messages.getString("ManageConfigurationsDialog.Available_SDks_and_Configs")); //$NON-NLS-1$
		
		properSdkViewer = new ContainerCheckedTreeViewer(container, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		properSdkViewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		properSdkViewer.setLabelProvider(new LabelProvider());
		TreeNodeContentProvider treeNodeContentProvider = new TreeNodeContentProvider();
		filteringContentProviderWrapper = 
			new FilteringContentProviderWrapper(treeNodeContentProvider);
		
		// add filters for Qt projects
		if (QtCorePlugin.isQtProject(cpi.getProject())) {
			filteringContentProviderWrapper.setFilter(new QtSDKFilter());
			filteringContentProviderWrapper.setConfigFilter(new QtConfigFilter());
		}
		
		boolean sbsv2Project = CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(cpi.getProject());
		
		properSdkViewer.setContentProvider(filteringContentProviderWrapper);
		properSdkViewer.setInput(BuildTargetTreeNode.getTreeViewerInput(sbsv2Project));
		propagateSdkTree();
		properSdkViewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				// Disclose the tree if the user selected the parent node
				Object obj = event.getElement();
				if (obj instanceof BuildTargetTreeNode){
					BuildTargetTreeNode bttn = (BuildTargetTreeNode)obj;
						properSdkViewer.setExpandedState(bttn, true);
				}
				validatePage();
			}
		});

		BrokenConfigurationInProjectTreeNode[] brokenTreeInput = BrokenConfigurationInProjectTreeNode.getTreeViewerInput(cpi);
		if (brokenTreeInput.length > 0) {
			final Label uninstalledSdkConfigLabel = new Label(container, SWT.NONE);
			uninstalledSdkConfigLabel.setToolTipText(Messages.getString("ManageConfigurationsDialog.Unavailable_Config_Label_ToolTip")); //$NON-NLS-1$
			uninstalledSdkConfigLabel.setText(Messages.getString("ManageConfigurationsDialog.Unavailable_SDks_and_Configs")); //$NON-NLS-1$
			missingSdkViewer = new ContainerCheckedTreeViewer(container, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
			missingSdkViewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
			missingSdkViewer.setLabelProvider(new LabelProvider());
			filteringContentProviderWrapper = 
				new FilteringContentProviderWrapper(treeNodeContentProvider);
			missingSdkViewer.setContentProvider(filteringContentProviderWrapper);
			missingSdkViewer.setInput(brokenTreeInput);
			propagateMissingSdkTree();
			missingSdkViewer.addCheckStateListener(new ICheckStateListener() {
				public void checkStateChanged(CheckStateChangedEvent event) {
					// Disclose the tree if the user selected the parent node
					Object obj = event.getElement();
					if (obj instanceof BrokenConfigurationInProjectTreeNode){
						BrokenConfigurationInProjectTreeNode bttn = (BrokenConfigurationInProjectTreeNode)obj;
						missingSdkViewer.setExpandedState(bttn, true);
					}
					validatePage();
				}
			});			
		}

		final Label sdkStaticHelp = new Label(parent, SWT.WRAP);
		sdkStaticHelp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		sdkStaticHelp.setText(Messages.getString("ManageConfigurationsDialog.Select_config_help_text")); //$NON-NLS-1$
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, CarbideCPPBuilderUIHelpIds.CARBIDE_BUILDER_MANAGE_CONFIGURATIONS_DLG);
		
		return container;
	}

	/**
	 * Create contents of the button bar
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(650, 500);
	}
	
	protected boolean validatePage() {
		boolean isValid = false;
		if (properSdkViewer.getCheckedElements().length == 0) {
			getButton(IDialogConstants.OK_ID).setEnabled(false);
		} else {
			getButton(IDialogConstants.OK_ID).setEnabled(true);
			isValid = true;
		}
		return isValid;
	}
	
	private void propagateSdkTree(){
		 List<ICarbideBuildConfiguration> buildConfigList = cpi.getBuildConfigurations();
		TreeItem[] items = properSdkViewer.getTree().getItems();
		for (int i=0; i<items.length; i++) {
			TreeNode sdkNode = (TreeNode)items[i].getData();
			if (sdkNode.getValue() instanceof ISymbianSDK && sdkNode.hasChildren()) {
				TreeNode[] configNode = sdkNode.getChildren();
				for (TreeNode currConfigNode : configNode){
					if (currConfigNode.getValue() instanceof ISymbianBuildContext){
						// if the current config is already a config set it to checked.
						ISymbianBuildContext buildContext = (ISymbianBuildContext)currConfigNode.getValue();
						for (ICarbideBuildConfiguration currExistingConfig : buildConfigList){
							boolean checkIt = false;
							checkIt = currExistingConfig.equals(buildContext);
							if (CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(cpi.getProject()) &&
											!checkIt && currExistingConfig.getSBSv2Alias() == null){
								
								// extra check to see if we're using SBSv2 and config display name is older SBSv1 style
								if (buildContext.getTargetString().equals(currExistingConfig.getTargetString()) &&
									buildContext.getPlatformString().equals(currExistingConfig.getPlatformString()) &&
									buildContext.getSDK().getUniqueId().equals(currExistingConfig.getSDK().getUniqueId() )
									&& buildContext.getSBSv2Alias() != null && buildContext.getSBSv2Alias().split("_").length == 2){
									
									checkIt = true;
								}
							}
							if (checkIt){
								// must expand parent before checking, otherwise, we won't succeed
								properSdkViewer.setExpandedState(sdkNode, true);
								properSdkViewer.setChecked(currConfigNode, true);
								break;
							}
						}
					}
				}				
			}
		}
		properSdkViewer.refresh();
	}
	
	private void propagateMissingSdkTree() {
		TreeItem[] items = missingSdkViewer.getTree().getItems();
		// all nodes in this tree are select, as the tree only reflect selected config with bad SDK
		for (int i=0; i<items.length; i++) {
			TreeNode sdkNode = (TreeNode)items[i].getData();
			if (sdkNode.getValue() instanceof ISymbianSDK) {
				missingSdkViewer.setExpandedState(sdkNode, true);
				missingSdkViewer.setChecked(sdkNode, true);		
			}
		}
		missingSdkViewer.refresh();		
	}
	
	private void saveConfigurations(){
		
		// get the modifier
		ICarbideProjectModifier cpm = CarbideBuilderPlugin.getBuildManager().getProjectModifier(cpi.getProject());

		// First go through all the checked tree items and add any configurations that are checked that don't already exist
		Object[] checkedElements = properSdkViewer.getCheckedElements();
		//List<ICarbideBuildConfiguration> buildConfigList = new ArrayList<ICarbideBuildConfiguration>();
		List<ICarbideBuildConfiguration> buildConfigList  = cpm.getBuildConfigurations();
		
		for (Object obj : checkedElements){
			TreeNode node = (TreeNode)obj;
			boolean configAlreadyExists = false;
			if (node.getValue() instanceof ISymbianBuildContext) {
				ISymbianBuildContext context = (ISymbianBuildContext)node.getValue();
				// Now check to see if the config already exists, if not create a new one
				for (ICarbideBuildConfiguration currExistingConfig : buildConfigList){
					if (currExistingConfig.equals(context)){
						configAlreadyExists = true;
						break;
					}
				}
				
				if (!configAlreadyExists){
					cpm.createNewConfiguration(context, false);
				}
			}
		}
		
		// Now check for good SDK configurations we need to remove. We need to be very careful here because a user may have a configuration
		// in the .settings but it may not actually appear in the tree view because the SDK is currently invalid or the license expired
		// so we cannot delete these.
		buildConfigList = cpm.getBuildConfigurations(); // get the list again as we may have added to it
		Iterator<ICarbideBuildConfiguration> configListIter = buildConfigList.iterator();
		while(configListIter.hasNext()){
			ICarbideBuildConfiguration currExistingConfig = configListIter.next();	
			TreeItem[] items = properSdkViewer.getTree().getItems();
			for (TreeItem currTreeItem : items) {
				TreeNode sdkNode = (TreeNode)currTreeItem.getData();
				if (sdkNode.getValue() instanceof ISymbianSDK) {
					TreeNode[] configNode = sdkNode.getChildren();
					for (TreeNode currConfigNode : configNode){
						if (currConfigNode.getValue() instanceof ISymbianBuildContext){
							// if the current config is already a config set it to checked.
							ISymbianBuildContext buildContext = (ISymbianBuildContext)currConfigNode.getValue();
							if (currExistingConfig.equals(buildContext)){
								// The configuration is in both the tree viewer and the .settings
								// Now find out if it's checked. If it's not checked remove it
								if (!properSdkViewer.getChecked(currConfigNode)){
									ICarbideBuildConfiguration config = cpm.getNamedConfiguration(buildContext.getDisplayString());
									if (config == null){
										config = cpm.getNamedConfiguration(currExistingConfig.getDisplayString());
									}
									if (config != null){
										cpm.deleteConfiguration(config);
										break;
									}
									
								} 
							}
						}
					}
				}
			}
		}
		
		// now look for dead SDK config we removed
		if (missingSdkViewer != null) {
			TreeItem[] items = missingSdkViewer.getTree().getItems();
			for (TreeItem currentTreeItem : items) {
				TreeNode sdkNode = (TreeNode)currentTreeItem.getData();
				if (sdkNode.getValue() instanceof ISymbianSDK) {
					TreeNode[] configNode = sdkNode.getChildren();
					for (TreeNode currConfigNode : configNode){
						if (missingSdkViewer.getChecked(currConfigNode) == false) {
							ISymbianBuildContext buildContext = (ISymbianBuildContext)currConfigNode.getValue();
							ICarbideBuildConfiguration config = cpm.getNamedConfiguration(buildContext.getDisplayString());	
							if (config != null) {
								cpm.deleteConfiguration(config);
							}
						}
					}
				}
			}			
		}
		
		// now apply any changes
		cpm.saveChanges();
	}

}
