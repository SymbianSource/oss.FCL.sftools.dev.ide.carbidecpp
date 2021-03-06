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
package com.nokia.carbide.cpp.internal.api.sdk.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;

import com.nokia.carbide.cpp.internal.api.sdk.SBSv2Utils;
import com.nokia.carbide.cpp.internal.api.sdk.sbsv2.SBSv2MinimumVersionException;
import com.nokia.carbide.cpp.internal.api.sdk.sbsv2.SBSv2QueryUtils;
import com.nokia.carbide.cpp.internal.sdk.core.model.SBSv2BuildInfo;
import com.nokia.carbide.cpp.internal.sdk.core.model.SDKManager;
import com.nokia.carbide.cpp.internal.sdk.ui.AddSBSv2ProductVariant;
import com.nokia.carbide.cpp.internal.sdk.ui.Messages;
import com.nokia.carbide.cpp.sdk.core.ICarbideInstalledSDKChangeListener;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuilderID;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.cpp.internal.api.utils.core.HostOS;

/**
 * @since 1.4
 */
@SuppressWarnings("restriction")
public class SBSv2PlatformFilterComposite extends Composite {

	private static HashMap<String, String> aliasMap = new HashMap<String, String>();
	private static List<String> productVariantList = new ArrayList<String>();
	
	private CheckboxTableViewer buildAliasTableViewer;
	private ListViewer customVariantListViewer;
	private Button refreshButton;
	private Button addVariantButton;
	private Button removeVariantButton;

	private ICarbideInstalledSDKChangeListener sdkListener = new ICarbideInstalledSDKChangeListener() {
		public void installedSdkChanged(SDKChangeEventType eventType) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					// refresh locally cached SBSv2 data whenever there is any change to SDK list
					refreshLocalSBSCacheData();			
				}
			});
		}
	};

	SBSv2PlatformFilterComposite(Composite parent) {
		super(parent, SWT.NONE);
	}

	public void createControls() {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns =  2;
		setLayout(gridLayout);
		
		GridData gd = new GridData(SWT.LEFT, SWT.LEFT, true, false);
		gd.widthHint = 200;
		gd.heightHint = 350;

		Label aliasBoxLabel = new Label(this, SWT.NONE);
		aliasBoxLabel.setText(Messages.getString("SBSv2PlatformFilterComposite.PlatformsGroupText"));
		aliasBoxLabel.setToolTipText(Messages.getString("SBSv2PlatformFilterComposite.PlatformsGroupToolTip"));
		
		Label variantBoxLabel = new Label(this, SWT.NONE);
		variantBoxLabel.setText(Messages.getString("SBSv2PlatformFilterComposite.ProductsGroupText"));
		variantBoxLabel.setToolTipText(Messages.getString("SBSv2PlatformFilterComposite.ProductsGroupToolTip"));
		
		buildAliasTableViewer = CheckboxTableViewer.newCheckList(this, SWT.BORDER);
		buildAliasTableViewer.getTable().setLayoutData(gd);
		buildAliasTableViewer.setContentProvider(new ArrayContentProvider());
		buildAliasTableViewer.setLabelProvider(new LabelProvider());
		
		customVariantListViewer = new ListViewer(this);
		customVariantListViewer.getList().setLayoutData(gd);
		customVariantListViewer.setContentProvider(new ArrayContentProvider());
		customVariantListViewer.setLabelProvider(new LabelProvider());
		customVariantListViewer.addSelectionChangedListener( new ISelectionChangedListener() {
			
			public void selectionChanged(SelectionChangedEvent event) {
				if (customVariantListViewer.getSelection() != null && !customVariantListViewer.getSelection().isEmpty()){
					removeVariantButton.setEnabled(true);
				} else {
					removeVariantButton.setEnabled(false);
				}
			}
		});
		
		refreshButton = new Button(this, SWT.NONE);		
		refreshButton.setText(Messages.getString("SBSv2PlatformFilterComposite.RefreshButtonText")); //$NON-NLS-1$
		refreshButton.setToolTipText(Messages.getString("SBSv2PlatformFilterComposite.RefreshButtonToolTip")); //$NON-NLS-1$
		
		refreshButton.addSelectionListener(new SelectionListener() {
			
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				refreshButton.setEnabled(false);
				refreshButton.setText(Messages.getString("SBSv2PlatformFilterComposite.RefreshButtonScanningText")); //$NON-NLS-1$
				
//				for (ISymbianSDK sdk : SDKCorePlugin.getSDKManager().getSDKList()){
//					((SBSv2BuildInfo)sdk.getBuildInfo(ISymbianBuilderID.SBSV2_BUILDER)).clearDataFromBuildCache();
//				}
				SBSv2QueryUtils.removeAllCachedQueries();
				
				refreshLocalSBSCacheData();

				SBSv2QueryUtils.flushAllSBSv2Caches();
				refreshButton.setText(Messages.getString("SBSv2PlatformFilterComposite.RefreshButtonText")); //$NON-NLS-1$
				refreshButton.setEnabled(true);
			}
			
		});
		
		Composite variantButtonsComposite = new Composite(this, SWT.NONE);
		gridLayout = new GridLayout();
		gridLayout.makeColumnsEqualWidth = true;
		gridLayout.numColumns = 2;
		variantButtonsComposite.setLayout(gridLayout);
		GridData gridData = new GridData(SWT.LEFT, SWT.TOP, true, false);
		variantButtonsComposite.setLayoutData(gridData);
		
		addVariantButton = new Button(variantButtonsComposite, SWT.NONE);		
		addVariantButton.setText(Messages.getString("SBSv2PlatformFilterComposite.AddProductButtonText")); //$NON-NLS-1$
		addVariantButton.setToolTipText(Messages.getString("SBSv2PlatformFilterComposite.AddProductButtonToolTip")); //$NON-NLS-1$
		addVariantButton.setLayoutData(gridData);
		addVariantButton.addSelectionListener(new SelectionListener() {
		
			public void widgetDefaultSelected(SelectionEvent e) {widgetSelected(e);}
			public void widgetSelected(SelectionEvent e) {
				if (aliasMap.size() == 0){
					MessageDialog.openError(getShell(), "No build configurations found.", "No build configurations (aliases) were found from any SDKs. Attempted 'sbs --query=aliases' but found no results.");
				} else if  (productVariantList.size() == 0){
						MessageDialog.openError(getShell(), "No products found.", "No products were found from any SDKs. Attempted 'sbs --query=products' but found no results.");
				} else {
					String selectedAlias = "";
					ISelection selectedItem = buildAliasTableViewer.getSelection();
					
					StructuredSelection selection = (StructuredSelection)selectedItem;
					String stringSelection = (String)selection.getFirstElement();
					if (stringSelection != null){
						TableItem[] tableItems = buildAliasTableViewer.getTable().getItems();
						for (TableItem item : tableItems){
							if (stringSelection.equals(item.getText())){
								selectedAlias = item.getText();
								break;
							}
						}
					}
					AddSBSv2ProductVariant addVariantDlg = new AddSBSv2ProductVariant(getShell(), selectedAlias, aliasMap, productVariantList);
					if (addVariantDlg.open() == TrayDialog.OK){
						if (customVariantListViewer.testFindItem(addVariantDlg.getUserCreatedVariant()) == null){
							// doesn't exist, add it. if it does exist just ignore it
							List<String> variantList = (List<String>)customVariantListViewer.getInput();
							variantList.add(addVariantDlg.getUserCreatedVariant());
							customVariantListViewer.setInput(variantList);
							customVariantListViewer.refresh();
						}
					}
				}
			}
			
		});
		
		removeVariantButton = new Button(variantButtonsComposite, SWT.NONE);		
		removeVariantButton.setText(Messages.getString("SBSv2PlatformFilterComposite.RemoveProductButtonText")); //$NON-NLS-1$
		removeVariantButton.setToolTipText(Messages.getString("SBSv2PlatformFilterComposite.RemoveProductButtonToolTip")); //$NON-NLS-1$
		removeVariantButton.setLayoutData(gridData);
		removeVariantButton.setEnabled(false);
		removeVariantButton.addSelectionListener(new SelectionListener() {
			
			public void widgetDefaultSelected(SelectionEvent e) {widgetSelected(e);}
			public void widgetSelected(SelectionEvent e) {
				ISelection selectedVariant = customVariantListViewer.getSelection();
				if (selectedVariant != null){
					StructuredSelection selection = (StructuredSelection)selectedVariant;
					String stringSelection = (String)selection.getFirstElement();
					List<String> data = (List<String>)customVariantListViewer.getInput();
					data.remove(stringSelection);
					customVariantListViewer.setInput(data);
					customVariantListViewer.refresh(true);
					removeVariantButton.setEnabled(false);
				}
			}
			
		});
		
		initTable();
		addListeners();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	public void performOk() {
		// save the list of unchecked configs
		List<String> checkedConfigs = new ArrayList<String>();
		for (TableItem item : buildAliasTableViewer.getTable().getItems()) {
			if (buildAliasTableViewer.getChecked(item.getData())) {
				checkedConfigs.add(item.getText());
			}
		}
		
		for (String variant : customVariantListViewer.getList().getItems()) {
				checkedConfigs.add(variant);
		}
		
		SBSv2Utils.setSBSv2FilteredConfigs(checkedConfigs.toArray(new String[checkedConfigs.size()]));
		removeListeners();
	}
	
	public void performCancel() {
		removeListeners();
	}
	
	private void addListeners() {
		SDKCorePlugin.getSDKManager().addInstalledSdkChangeListener(sdkListener);
	}
	
	private void removeListeners() {
		SDKCorePlugin.getSDKManager().removeInstalledSdkChangeListener(sdkListener);
	}
	
	private void initTable() {

		SBSv2Utils.initDefaultConfigsToFilter();
		
		if (aliasMap.size() == 0){
			try {
				aliasMap = SBSv2QueryUtils.getCompleteAliasList();
			} catch (SBSv2MinimumVersionException e) {
				// Force a scan for version in case system was updated
				SDKCorePlugin.getSDKManager().getSBSv2Version(true);
				try {
					// try, try again...
					aliasMap = SBSv2QueryUtils.getCompleteAliasList();
				} catch (SBSv2MinimumVersionException e2) {
					MessageDialog.openError(getShell(), "Minimum sbs version not met.", e.getMessage());
				}
			} 
		}
		
		if (productVariantList.size() == 0){
			try {
				productVariantList = SBSv2QueryUtils.getCompleteProductVariantList();
			} catch (SBSv2MinimumVersionException e) {
				
			}
		}
		// get saved configs in the store
		List<String> checkedConfigsFromStore = SBSv2Utils.getSBSv2FilteredConfigPreferences();
		
		List<String> sbsAliases = new ArrayList<String>(); // global build aliases
		List<String> savedVariants = new ArrayList<String>(); // custom variants
		for (String key : aliasMap.keySet()){
			sbsAliases.add(key);
		}
		
		for (String config : checkedConfigsFromStore){
			if (config.contains(".")){
				savedVariants.add(config);
			}
		}
		
		Collections.sort(sbsAliases);
		
		if (buildAliasTableViewer != null) {
			buildAliasTableViewer.setInput(sbsAliases);
			// uncheck all configs to init
			buildAliasTableViewer.setAllChecked(false);
			
			for (String config : checkedConfigsFromStore) {
				for (TableItem item : buildAliasTableViewer.getTable().getItems()) {
					if (item.getText().equals(config) && !item.getText().contains(".")) {
						buildAliasTableViewer.setChecked(item.getData(), true);
						break;
					}
				}
			}
		}
		
		if (customVariantListViewer != null) {
			customVariantListViewer.setInput(savedVariants);
		}
		
	}

	private void refreshLocalSBSCacheData() {
		aliasMap.clear();
		productVariantList.clear();
		initTable();
	}

	public void setDefaults(){
		initTable();
		for (TableItem item : buildAliasTableViewer.getTable().getItems()) {
			String tableItemText = item.getText().toLowerCase();
			if (tableItemText.equals("armv5_udeb")  || 
				tableItemText.equals("armv5_urel") ||
				tableItemText.equals("armv5_udeb_gcce")  || 
				tableItemText.equals("armv5_urel_gcce") ||
				tableItemText.equals("winscw_udeb")  ||
				tableItemText.equals("winscw_urel")) {
				
				if (HostOS.IS_UNIX && tableItemText.startsWith("winscw"))
					;  // ignore winscw as an option for Unix
				else
					buildAliasTableViewer.setChecked(item.getData(), true);
				
				
			} else {
				buildAliasTableViewer.setChecked(item.getData(), false);
			}
		}
	}
}
