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
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;
import org.eclipse.ui.dialogs.PreferencesUtil;

import com.nokia.carbide.cdt.builder.CarbideBuilderPlugin;
import com.nokia.carbide.cdt.builder.project.ICarbideBuildConfiguration;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectInfo;
import com.nokia.carbide.cdt.builder.project.ICarbideProjectModifier;
import com.nokia.carbide.cdt.internal.builder.ui.CarbideCPPBuilderUIHelpIds;
import com.nokia.carbide.cpp.internal.api.sdk.ISBSv2BuildContext;
import com.nokia.carbide.cpp.internal.api.sdk.ISDKManagerInternal;
import com.nokia.carbide.cpp.internal.qt.core.QtConfigFilter;
import com.nokia.carbide.cpp.internal.qt.core.QtCorePlugin;
import com.nokia.carbide.cpp.internal.qt.core.QtSDKFilter;
import com.nokia.carbide.cpp.internal.sdk.core.model.SDKManager;
import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.carbide.cpp.sdk.ui.shared.BuildTargetTreeNode;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.HostOS;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

@SuppressWarnings("restriction")
public class ManageConfigurationsDialog extends TrayDialog {
	
	protected ContainerCheckedTreeViewer properSdkViewer;
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
		
		if (HostOS.IS_WIN32){
			ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
			if (!((SDKManager)sdkMgr).checkDevicesXMLSynchronized()){
				if (sdkMgr instanceof ISDKManagerInternal){
					ISDKManagerInternal sdkMgrInternal = (ISDKManagerInternal)sdkMgr;
					sdkMgrInternal.fireDevicesXMLChanged();
				}	
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
		
		class SDKNodeLabelProvider extends LabelProvider implements IColorProvider {

			public Color getForeground(Object element) {
				if (element instanceof BuildTargetTreeNode){
					BuildTargetTreeNode treeNode = (BuildTargetTreeNode)element;
					if (treeNode.getValue() instanceof ISymbianSDK){
						if (treeNode.toString().contains(BuildTargetTreeNode.SDK_NODE_ERROR_EPOCROOT_INVALID)){
							return WorkbenchUtils.getSafeShell().getDisplay().getSystemColor(SWT.COLOR_RED);
						}
					}
				}
				
				return null;
			}

			public Color getBackground(Object element) {
				return null;
			}
		}
		
		properSdkViewer.setLabelProvider(new SDKNodeLabelProvider());
		TreeNodeContentProvider treeNodeContentProvider = new TreeNodeContentProvider();
		filteringContentProviderWrapper = 
			new FilteringContentProviderWrapper(treeNodeContentProvider);
		
		// add filters for Qt projects
		if (QtCorePlugin.isQtProject(cpi.getProject())) {
			filteringContentProviderWrapper.setFilter(new QtSDKFilter());
			filteringContentProviderWrapper.setConfigFilter(new QtConfigFilter());
		}
		
		drawSDKConfigTree();
		
		BrokenConfigurationInProjectTreeNode[] brokenTreeInput = BrokenConfigurationInProjectTreeNode.getTreeViewerInput(cpi);
		if (brokenTreeInput.length > 0) {
			final Label uninstalledSdkConfigLabel = new Label(container, SWT.NONE);
			uninstalledSdkConfigLabel.setToolTipText(Messages.getString("ManageConfigurationsDialog.Unavailable_Config_Label_ToolTip")); //$NON-NLS-1$
			uninstalledSdkConfigLabel.setText(Messages.getString("ManageConfigurationsDialog.Unavailable_SDks_and_Configs")); //$NON-NLS-1$
		}

		final Label sdkStaticHelp = new Label(parent, SWT.WRAP);
		sdkStaticHelp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		sdkStaticHelp.setText(Messages.getString("ManageConfigurationsDialog.Select_config_help_text")); //$NON-NLS-1$
		
		Link configPrefLink = new Link(parent, SWT.WRAP);
		configPrefLink.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		configPrefLink.setText(Messages.getString("ManageConfigurationsDialog.Select_Filtering_Prefs_Link")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		configPrefLink.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		configPrefLink.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// I don't see a way to open it to a specific tab, only the page
				if (Window.OK == PreferencesUtil.createPreferenceDialogOn(getShell(), "com.nokia.carbide.cpp.sdk.ui.preferences.BuildPlatformFilterPage", null, null, 0).open()){ //$NON-NLS-1$
					drawSDKConfigTree();
				}
			}
		});
		
		Link sdkLink = new Link(parent, SWT.WRAP);
		sdkLink.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		sdkLink.setText(Messages.getString("ManageConfigurationsDialog.Select_SymbianSDKs_Prefs_Link")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		sdkLink.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		sdkLink.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// I don't see a way to open it to a specific tab, only the page
				if (Window.OK == PreferencesUtil.createPreferenceDialogOn(getShell(), "com.nokia.carbide.cpp.sdk.ui.preferences.SDKPreferencePage", null, null, 0).open()){ //$NON-NLS-1$
					drawSDKConfigTree();
				}
			}
		});
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, CarbideCPPBuilderUIHelpIds.CARBIDE_BUILDER_MANAGE_CONFIGURATIONS_DLG);
		
		return container;
	}

	private void drawSDKConfigTree() {
		boolean sbsv2Project = CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(cpi.getProject());
	
		properSdkViewer.setContentProvider(filteringContentProviderWrapper);
		BuildTargetTreeNode[] sdkConfigTreeNodes = BuildTargetTreeNode.getTreeViewerInput(sbsv2Project);
		if (sbsv2Project){
			replaceFilteredConfigsFromProject(sdkConfigTreeNodes);
		}
		
		properSdkViewer.setInput(sdkConfigTreeNodes);
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
		
	}

	/**
	 * When displaying build configs there may be configurations in the project that may not be displayed
	 * We add those back in so they reside in the checked tree viewer in case the user wants to remove them.
	 * @param sdkConfigTreeNodes
	 */
	private void replaceFilteredConfigsFromProject(BuildTargetTreeNode[] sdkConfigTreeNodes) {
		List<ICarbideBuildConfiguration> bldConfigs = cpi.getBuildConfigurations();
		
		HashMap<BuildTargetTreeNode, List<ISymbianBuildContext>> missingConfigMap = new HashMap<BuildTargetTreeNode, List<ISymbianBuildContext>>();
		for (ICarbideBuildConfiguration config : bldConfigs){
			boolean foundConfig = false;
			// Add in configs that are only defined in the project and not the
			// suggested filtered config cache
			for (BuildTargetTreeNode sdkConfigNode : sdkConfigTreeNodes){
				ISymbianSDK sdk = sdkConfigNode.getSymbianSDK();
				if (!sdk.getUniqueId().equals(config.getSDK().getUniqueId())){
					continue; // not in this SDK, don't bother looking at all configs
				} else {
					// Found the right SDK, now check and see if the config exists
					TreeNode[] configNodes = sdkConfigNode.getChildren();
					if (configNodes != null){
						for (TreeNode childConfig : configNodes){
							if (childConfig == null){
								continue;
							}
							if (childConfig.getValue() instanceof ISymbianBuildContext){
								ISymbianBuildContext context = (ISymbianBuildContext)(childConfig.getValue());
								if (config.getBuildContext().equals(context)){
									foundConfig = true;
									break;
								}
							}
						}
					}
					if (!foundConfig){
						// save config off, we'll add it back in later
						List<ISymbianBuildContext> contextsToAdd = new ArrayList<ISymbianBuildContext>();
						if (null == missingConfigMap.get(sdkConfigNode)){
							contextsToAdd.add(config.getBuildContext());
						} else {
							contextsToAdd = missingConfigMap.get(sdkConfigNode);
							contextsToAdd.add(config.getBuildContext());
						}
						missingConfigMap.put(sdkConfigNode, contextsToAdd);
						
					}
				}
			}			
		}
		
		for (BuildTargetTreeNode sdkNode : missingConfigMap.keySet()){
			List<ISymbianBuildContext> configsToAdd = missingConfigMap.get(sdkNode);
			TreeNode[] oldConfigNodes = sdkNode.getChildren();
			if (oldConfigNodes == null || oldConfigNodes.length == 0){
				continue;
			}
			TreeNode[] newConfigNodes = new TreeNode[oldConfigNodes.length + configsToAdd.size()];
			int index = 0;
			// build up the old list....
			for (TreeNode newConfigNode : oldConfigNodes){
				if (newConfigNode == null){
					continue;
				}
				if (newConfigNode.getValue() instanceof ISymbianBuildContext){
					ISymbianBuildContext context = (ISymbianBuildContext)(newConfigNode.getValue());
					newConfigNodes[index++] = new TreeNode(context) {
						@Override
						public String toString() {
							ISymbianBuildContext context = (ISymbianBuildContext)getValue();
							String sdkId = context.getSDK().getUniqueId();
							String newDisplayString = stripSDKIDFromConfigName(context.getDisplayString(), sdkId);
							if (context instanceof ISBSv2BuildContext){
								ISBSv2BuildContext v2Context = (ISBSv2BuildContext)context;
								if (v2Context.getConfigQueryData() == null){
									newDisplayString += " ERROR: " + "Unable to load configuration data because the query to sbs failed."; // $NON-NLS-N$
								}
								else if (v2Context.getConfigQueryData().getConfigurationErrorMessage() != null && 
									v2Context.getConfigQueryData().getConfigurationErrorMessage().length() > 0){
									newDisplayString += " ERROR: " + v2Context.getConfigQueryData().getConfigurationErrorMessage();
								}
							} 
							return newDisplayString;
						}
					};
				}
			}
			
			// ... then add the project specific items...
			for (ISymbianBuildContext newContext : configsToAdd){
				newConfigNodes[index++] = new TreeNode(newContext) {
					@Override
					public String toString() {
						ISymbianBuildContext context = (ISymbianBuildContext)getValue();
						String sdkId = context.getSDK().getUniqueId();
						String newDisplayString = stripSDKIDFromConfigName(context.getDisplayString(), sdkId);
						if (context instanceof ISBSv2BuildContext){
							ISBSv2BuildContext v2Context = (ISBSv2BuildContext)context;
							if (v2Context.getConfigQueryData() == null){
								newDisplayString += " ERROR: " + "Unable to load configuration data because the query to sbs failed."; // $NON-NLS-N$
							}
							else if (v2Context.getConfigQueryData().getConfigurationErrorMessage() != null && 
								v2Context.getConfigQueryData().getConfigurationErrorMessage().length() > 0){
								newDisplayString += " ERROR: " + v2Context.getConfigQueryData().getConfigurationErrorMessage();
							}
						} 
						return newDisplayString;
					}
				};
			}
			
			sdkNode.setChildren(newConfigNodes);
			
		}
		
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
							checkIt = currExistingConfig.getBuildContext().equals(buildContext);
							if (CarbideBuilderPlugin.getBuildManager().isCarbideSBSv2Project(cpi.getProject()) &&
											!checkIt){
								
								if (buildContext instanceof ISBSv2BuildContext){
									ISBSv2BuildContext v2Context = (ISBSv2BuildContext)buildContext;
									ISBSv2BuildContext currV2Context = (ISBSv2BuildContext)currExistingConfig.getBuildContext();
									// extra check to see if we're using SBSv2 and config display name is older SBSv1 style
									if (currV2Context.getConfigID().startsWith(ISBSv2BuildContext.BUILDER_ID)){
										if (v2Context.getConfigID().equals(currV2Context.getConfigID())	){
											checkIt = true;
										}
									}
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
					if (currExistingConfig.getBuildContext().equals(context)){
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
					if (configNode != null){
						for (TreeNode currConfigNode : configNode){
							if (currConfigNode.getValue() instanceof ISymbianBuildContext){
								// if the current config is already a config set it to checked.
								ISymbianBuildContext buildContext = (ISymbianBuildContext)currConfigNode.getValue();
								if (currExistingConfig.getBuildContext().equals(buildContext)){
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
		}
		
		// now apply any changes
		cpm.saveChanges();
	}
	
	private static String stripSDKIDFromConfigName(String configName, String sdkID){
		return configName.replace("[" + sdkID + "]", "");
	}

	
}
