/*
* Copyright (c) 2009-2010 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.carbide.cpp.internal.sdk.ui;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Version;

import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;

public class SDKPropertiesDialog extends TrayDialog {
	
	ISymbianSDK sdk;
	private CCombo osVersionCombo;
	private CCombo sdkVersionCombo;
	private CCombo sdkNameCombo;
	private CCombo isDefaultCombo;
	private Text sdkIDText;
	private Text epocRootText;
//	private Button browseEPOCROOTButton;
	private Table propsTable;
	
	private static String DEFAULT_DEVICE_YES = "yes"; //$NON-NLS-1$
	private static String DEFAULT_DEVICE_NO = "no"; //$NON-NLS-1$
	
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public SDKPropertiesDialog(Shell parentShell, ISymbianSDK sdk) {
		super(parentShell);
		this.sdk = sdk;
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(Messages.getString("SDKPropertiesDialog.SDK_Properties_For") + sdk.getUniqueId()); //$NON-NLS-1$
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		final GridLayout gridLayout = new GridLayout();
		container.setLayout(gridLayout);

		final Label sdkPropertiesLabel = new Label(container, SWT.NONE);
		sdkPropertiesLabel.setText(Messages.getString("SDKPropertiesDialog.Available_SDK_Properties")); //$NON-NLS-1$
		
		propsTable = new Table(container, SWT.BORDER);
		propsTable.setLinesVisible(true);
		propsTable.setHeaderVisible(true);
		final GridData sdkPropTable = new GridData(GridData.FILL, GridData.FILL, true, true);
		sdkPropTable.widthHint = 300;
		propsTable.setLayoutData(sdkPropTable);
		
		final TableColumn sdkPropCol1 = new TableColumn(propsTable, SWT.NONE);
		sdkPropCol1.setWidth(111);
		sdkPropCol1.setText(Messages.getString("SDKPropertiesDialog.Property")); //$NON-NLS-1$

		final TableColumn sdkPropCol2 = new TableColumn(propsTable, SWT.NONE);
		sdkPropCol2.setWidth(287);
		sdkPropCol2.setText(Messages.getString("SDKPropertiesDialog.Value")); //$NON-NLS-1$
		
		// SDK ID at Row 1.
		TableItem itemSDKId = new TableItem(propsTable, SWT.NONE);
		itemSDKId.setText(new String[] { Messages.getString("SDKPropertiesDialog.SDK_ID"), "" }); //$NON-NLS-1$ //$NON-NLS-2$
		
		// SDK Name at Row 2.
		TableItem itemSDKName = new TableItem(propsTable, SWT.NONE);
		itemSDKName.setText(new String[] { Messages.getString("SDKPropertiesDialog.SDK_Name"), "" }); //$NON-NLS-1$ //$NON-NLS-2$
		
		// SDK Name at Row 3.
		TableItem itemEPOCROOTName = new TableItem(propsTable, SWT.NONE);
		itemEPOCROOTName.setText(new String[] { "EPOCROOT", "", "" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		
		// OS Version at Row 4.
		TableItem itemOSVersion = new TableItem(propsTable, SWT.NONE);
		itemOSVersion.setText(new String[] { Messages.getString("SDKPropertiesDialog.OS_Version"), "" }); //$NON-NLS-1$ //$NON-NLS-2$
		
		// SDK Version at Row 5.
		TableItem itemSDKVersion = new TableItem(propsTable, SWT.NONE);
		itemSDKVersion.setText(new String[] { Messages.getString("SDKPropertiesDialog.SDK_Version"), "" }); //$NON-NLS-1$ //$NON-NLS-2$
		
		//	Is default at Row 6.
		TableItem itemDefaultDevice = new TableItem(propsTable, SWT.NONE);
		itemDefaultDevice.setText(new String[] { Messages.getString("SDKPropertiesDialog.Default_SDK"), "" }); //$NON-NLS-1$ //$NON-NLS-2$
		
		TableItem itemPrefixFile = new TableItem(propsTable, SWT.NONE);
		if ((sdk.getPrefixFile() != null) && (sdk.getPrefixFile().toString().length() > 0)){
			itemPrefixFile.setText(new String[] { Messages.getString("SDKPropertiesDialog.Prefix_File"), sdk.getPrefixFile().toString()}); //$NON-NLS-1$
		} else {
			itemPrefixFile.setText(new String[] { Messages.getString("SDKPropertiesDialog.Prefix_File"), "unknown"}); //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		
		IPath incPath = sdk.getIncludePath();
		if (incPath != null){
			TableItem itemIncPath = new TableItem(propsTable, SWT.NONE);
			itemIncPath.setText(new String[] { Messages.getString("SDKPropertiesDialog.Include_Dir"), incPath.toOSString()}); //$NON-NLS-1$
		}
		
		IPath toolsPath = sdk.getToolsPath();
		if (toolsPath != null){
			TableItem itemToolsPath = new TableItem(propsTable, SWT.NONE);
			itemToolsPath.setText(new String[] { Messages.getString("SDKPropertiesDialog.Tools_Dir"), toolsPath.toOSString()}); //$NON-NLS-1$
		}
		
		IPath relRoot = sdk.getReleaseRoot();
		if (relRoot != null){
			TableItem itemRelRootPath = new TableItem(propsTable, SWT.NONE);
			itemRelRootPath.setText(new String[] { Messages.getString("SDKPropertiesDialog.Release_Dir"), relRoot.toOSString()}); //$NON-NLS-1$
		}
		
		Date createDate = sdk.getCreationDate();
		if (createDate != null){
			TableItem itemDate = new TableItem(propsTable, SWT.NONE);
			itemDate.setText(new String[] { Messages.getString("SDKPropertiesDialog.SDK_Create_Date"), createDate.toString()}); //$NON-NLS-1$
		}
		
		URL url =sdk.getPublisherURL();
		if (url != null){
			TableItem itemURL = new TableItem(propsTable, SWT.NONE);
			itemURL.setText(new String[] { Messages.getString("SDKPropertiesDialog.Publisher_URL"), url.toString()}); //$NON-NLS-1$
		}
		
		String pubName = sdk.getPublisherName();
		if (pubName != null && pubName.length() > 0){
			TableItem itemVendor = new TableItem(propsTable, SWT.NONE);
			itemVendor.setText(new String[] { Messages.getString("SDKPropertiesDialog.Publisher_Name"), pubName}); //$NON-NLS-1$
		}
		
		String descr = sdk.getSDKDescription();
		if (descr != null && descr.length() > 0){
			TableItem itemSDKDescr = new TableItem(propsTable, SWT.NONE);
			itemSDKDescr.setText(new String[] { Messages.getString("SDKPropertiesDialog.SDK_Description"), descr}); //$NON-NLS-1$
		}
		
		// Set up the editable fields
		setUpTableEditFields();
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, SDKUIHelpIds.SDK_PROPERTIES_DIALOG);
		
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
		return new Point(418, 375);
	}
	
	private void setUpTableEditFields(){
		TableItem[] items = propsTable.getItems();
		TableEditor editor = new TableEditor(propsTable);
		
		// Add the SDK ID editor to the 1st row, seonds column
		sdkIDText = new Text(propsTable, SWT.NONE);
		sdkIDText.setText(sdk.getUniqueId());
	    editor.grabHorizontal = true;
	    editor.setEditor(sdkIDText, items[0], 1);
	    Color white = getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE);
	    sdkIDText.setBackground(white);  
		
	    //	 Add the SDK Name combo to the 2nd row, second column
	    editor = new TableEditor(propsTable);
		sdkNameCombo = new CCombo(propsTable, SWT.NONE);
		sdkNameCombo.setText(sdk.getName());
		sdkNameCombo.add(ISymbianSDK.S60_SDK_NAME);
		sdkNameCombo.add(ISymbianSDK.S80_SDK_NAME);
		sdkNameCombo.add(ISymbianSDK.TECHVIEW_SDK_NAME);
		sdkNameCombo.add(ISymbianSDK.UIQ_SDK_NAME);
	    editor.grabHorizontal = true;
	    editor.setEditor(sdkNameCombo, items[1], 1);
	    sdkIDText.setBackground(white); 
	    
	    /*
	     * ??? HOW DO YOU ADD A BUTTON TO A CELL  WITH ANOTHER CONTROL
	    editor = new TableEditor(propsTable);
	    browseEPOCROOTButton = new Button(propsTable, SWT.RIGHT);
	    browseEPOCROOTButton.setBounds(5, 5, 5, 5);
		browseEPOCROOTButton.setToolTipText("Choose the folder where 'epoc32' exists.");
		browseEPOCROOTButton.setText("...");
		 editor.grabHorizontal = true;
		editor.setEditor(browseEPOCROOTButton, items[2], 1);
		//addButtonListener(browseEPOCROOTButton);
	    */
	    //  Add the EPOCROOT text to the 3rd row, second column
	    editor = new TableEditor(propsTable);
		epocRootText = new Text(propsTable, SWT.NONE);
		epocRootText.setText(sdk.getEPOCROOT());
	    editor.grabHorizontal = true;
	    editor.setEditor(epocRootText, items[2], 1);
	    epocRootText.setBackground(white);
	    
	    // Add the OS Version combo to the 4th row, second column
	    editor = new TableEditor(propsTable);
		osVersionCombo = new CCombo(propsTable, SWT.NONE);
		osVersionCombo.setText(sdk.getOSVersion().toString() + sdk.getSDKOSBranch());
		List<String> supportedOSVersions = new ArrayList<String>();
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		supportedOSVersions = sdkMgr.getSymbianMacroStore().getSupportedOSVersions();
		for (String currVer : supportedOSVersions){
			osVersionCombo.add(currVer);
		}
	    editor.grabHorizontal = true;
	    editor.setEditor(osVersionCombo, items[3], 1);
	    osVersionCombo.setBackground(white); 
	    osVersionCombo.setEditable(false);
	    
	    // Add the SDK Version combo to the 5th row, second column
	    editor = new TableEditor(propsTable);
	    sdkVersionCombo = new CCombo(propsTable, SWT.NONE);
	    sdkVersionCombo.setText(sdk.getSDKVersion().toString());
	    List<String> sdkVersions = new ArrayList<String>();
	    sdkVersions = sdkMgr.getSymbianMacroStore().getSDKVersions();
		for (String currVer : sdkVersions){
			sdkVersionCombo.add(currVer);
		}
	    editor.grabHorizontal = true;
	    editor.setEditor(sdkVersionCombo, items[4], 1);
	    sdkVersionCombo.setBackground(white);
	    
	    // Add the default combo to the 6th row, second column
	    editor = new TableEditor(propsTable);
	    isDefaultCombo = new CCombo(propsTable, SWT.NONE);
	    editor.grabHorizontal = true;
	    editor.setEditor(isDefaultCombo, items[5], 1);
	    isDefaultCombo.add(DEFAULT_DEVICE_YES);
	    isDefaultCombo.add(DEFAULT_DEVICE_NO);
	    if (sdk.isDefaultSDK()){
	    	isDefaultCombo.setText(DEFAULT_DEVICE_YES);
	    } else {
	    	isDefaultCombo.setText(DEFAULT_DEVICE_NO);
	    }
	}

	@Override
	protected void okPressed() {
		
		if (!validateData()){
			return;
		}
		
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		if (!sdk.getUniqueId().equals(sdkIDText.getText())){
			// SDK ID has changed, we'll need to delete the old ID from devices.xml
			sdkMgr.removeSDK(sdk.getUniqueId());
		}
		sdk.setUniqueID(sdkIDText.getText());
		if (sdk.getEPOCROOT().compareTo(epocRootText.getText()) != 0){
			// EPOCROOT has changed, re-scan the SDK.
			sdk.setEPOCROOT(epocRootText.getText());
			sdk.scanSDK();
		}
		
		sdk.setName(sdkNameCombo.getText());
		
		String osVerString = osVersionCombo.getText();
		int len = osVerString.length();
		if (Character.isLetter(osVerString.charAt(len-1))){
			String branch = osVerString.substring(len-1);
			sdk.setOSSDKBranch(branch);
			osVerString = osVerString.substring(0, len-1);
		} else {
			sdk.setOSSDKBranch(""); //$NON-NLS-1$
		}
		
		sdk.setOSVersion(Version.parseVersion(osVerString));
		
		try {
			sdk.setSDKVersion(Version.parseVersion(sdkVersionCombo.getText()));
		} catch (NumberFormatException e) {
			MessageDialog.openError(getShell(), Messages.getString("SDKPropertiesDialog.Illegal_Verion_Title"), Messages.getString("SDKPropertiesDialog.Illegal_SDKVerion_Msg")); //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}
		
		if (isDefaultCombo.getText().equals(DEFAULT_DEVICE_YES)){
			sdk.setIsDefaultSDK(true);
		} else {
			sdk.setIsDefaultSDK(false);
		}
		sdkMgr.updateSDK(sdk);
		sdkMgr.setDefaultSDK(sdk);
	    
		super.okPressed();
	}
	
	private boolean validateData(){
		boolean isOK = true;
		
		// make sure id is not null and is not a duplicate
		if ((sdkIDText.getText().length() > 0) ){
			if (!sdk.getUniqueId().equals(sdkIDText.getText())){
				ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
				List<ISymbianSDK> sdkList = sdkMgr.getSDKList();
				for (ISymbianSDK currSDK : sdkList){
					if (currSDK.getUniqueId().equalsIgnoreCase(sdkIDText.getText())){
						MessageDialog.openError(getShell(), Messages.getString("SDKPropertiesDialog.Duplicate_ID"), Messages.getString("SDKPropertiesDialog.Duplicate_ID_Message")); //$NON-NLS-1$ //$NON-NLS-2$
						return false;
					}
				}
			}
		} else {
			MessageDialog.openError(getShell(), Messages.getString("SDKPropertiesDialog.Zero_Len_ID"), Messages.getString("SDKPropertiesDialog.Zero_Len_ID_Msg")); //$NON-NLS-1$ //$NON-NLS-2$
			return false;
		}
		
		// make sure name is proper format
		if (sdkNameCombo.getText().length() > 0){
			if (!isValidVendorName(sdkNameCombo.getText())){
				return false;
			}
		}else{
			MessageDialog.openError(getShell(), Messages.getString("SDKPropertiesDialog.Zero_Len_Name"), Messages.getString("SDKPropertiesDialog.Zero_Len_Name_Msg")); //$NON-NLS-1$ //$NON-NLS-2$
			return false;
		}
		
		// make sure epocroot exists
		if (epocRootText.getText().length() > 0){
			if (!epocRootText.getText().equals(sdk.getEPOCROOT())){
				File rootFile = new File(epocRootText.getText());
				if (!rootFile.exists()){
					if (!MessageDialog.openQuestion(getShell(), Messages.getString("SDKPropertiesDialog.EPOCROOT_No_Exist"), Messages.getString("SDKPropertiesDialog.EPOCROOT_No_Exist_Msg"))){ //$NON-NLS-1$ //$NON-NLS-2$
						return false;
					}				
				}
			}
		}else{
			MessageDialog.openError(getShell(), Messages.getString("SDKPropertiesDialog.Zero_Len_EPOCROOT"), Messages.getString("SDKPropertiesDialog.Zero_Len_EPOCROOT_Msg")); //$NON-NLS-1$ //$NON-NLS-2$
			return false;
		}
		return isOK;
	}
	
	private boolean isValidVendorName(String vendor){
		boolean isValid = true;
		
		String[] vendorSplit = vendor.split("[.]"); //$NON-NLS-1$
		if (vendorSplit.length == 3){ 
			if (!vendorSplit[0].toLowerCase().startsWith("com")){ //$NON-NLS-1$
			isValid = false;
			}
		} else {
			isValid = false;
		}
		
		if (isValid == false){
			MessageDialog.openError(getShell(), Messages.getString("SDKPropertiesDialog.Invalid_Name_Attrib"), Messages.getString("SDKPropertiesDialog.Invalid_Name_Attrib_Msg")); //$NON-NLS-1$ //$NON-NLS-2$
		}
			
		return isValid;
	}

}
