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

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Version;

import com.nokia.carbide.cpp.internal.sdk.ui.Messages;
import com.nokia.carbide.cpp.internal.sdk.ui.SDKUIHelpIds;
import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.carbide.cpp.sdk.core.SymbianSDKFactory;
import com.nokia.cpp.internal.api.utils.core.HostOS;
import com.nokia.cpp.internal.api.utils.ui.BrowseDialogUtils;

/**
 * Dialog that allows the user to add a new Symbian OS SDK or custkit
 * to the devices.xml file.
 */
public class AddSDKDialog extends TrayDialog {

	private Combo sdkVersionCombo;
	private Combo osVersionCombo;
	private Combo isDefaultCombo;
	private Combo vendorCombo;
	private Text deviceIDtext;
	private Text epocRootText;
	private Button browseEPOCROOTButton;
	
	public static final String DEFAULT_YES = "yes"; //$NON-NLS-1$
	public static final String DEFAULT_NO  = "no"; //$NON-NLS-1$
	public static final String AUTO_DETECT  = "(Auto-Detect)"; //$NON-NLS-1$
	
	/**
	 * Create the dialog
	 * @param parentShell
	 */
	public AddSDKDialog(Shell parentShell) {
		super(parentShell);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(Messages.getString("AddSDKDialog.Add_New_SDK")); //$NON-NLS-1$
	}
	
	@Override
	protected void okPressed() {
		
		if (deviceIDtext.getText().length() > 0){
			if (!isValidIDName(deviceIDtext.getText())){
				return;
			}
			
			if (vendorCombo.getText().length() > 0){
				if (!isValidVendorName(vendorCombo.getText())){
					return;
				}
			} else {
				MessageDialog.openError(getShell(), Messages.getString("AddSDKDialog.Missing_Vendor_Name"), Messages.getString("AddSDKDialog.Enter_Vendor_Name")); //$NON-NLS-1$ //$NON-NLS-2$
				return;
			}
		} else {
			MessageDialog.openError(getShell(), Messages.getString("AddSDKDialog.Missing_SDK_ID"), Messages.getString("AddSDKDialog.Enter_SDK_ID")); //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}
		
		if (epocRootText.getText().length() > 1){
			if (HostOS.IS_WIN32 && epocRootText.getText().charAt(1) != ':'){
				MessageDialog.openError(getShell(), Messages.getString("AddSDKDialog.Malformed_EPOCROOT"), Messages.getString("AddSDKDialog.EPOCROOT_drive_spec")); //$NON-NLS-1$ //$NON-NLS-2$
				return;
			}
			
			if (epocRootText.getText().toUpperCase().endsWith("EPOC32") || epocRootText.getText().toUpperCase().endsWith("EPOC32\\")){ //$NON-NLS-1$ //$NON-NLS-2$
				if (!MessageDialog.openConfirm(getShell(), Messages.getString("AddSDKDialog.Possible_wrong_epocroot"), Messages.getString("AddSDKDialog.EPOCROOT_Should_Point"))){ //$NON-NLS-1$ //$NON-NLS-2$
					return;
				}
			} else {
				File epocRootFile = new File(epocRootText.getText());
				if (!epocRootFile.exists()){
					if (!MessageDialog.openConfirm(getShell(), Messages.getString("AddSDKDialog.EPOCROOT_does_not_exist"), Messages.getString("AddSDKDialog.EPOCROOT_does_not_exist_msg"))){ //$NON-NLS-1$ //$NON-NLS-2$
						return;
					}
				}
				if (epocRootFile.toString().indexOf(" ") != -1){
					// Paths should not have spaces...
					MessageDialog.openError(getShell(), "EPOC Root Contains Spaces", Messages.getString("AddSDKDialog.EPOCROOT_contains_spaces")); //$NON-NLS-1$ //$NON-NLS-2$
					return;
					
				}
			}
		} else {
			if (epocRootText.getText().length() == 1 && epocRootText.getText().charAt(0) == '\\'){
				// OK - Currently accept only a '\'. May need to reconsider side-effects
			} else {
				MessageDialog.openError(getShell(), Messages.getString("AddSDKDialog.Missing_EPOCROOT"), Messages.getString("AddSDKDialog.Please_enter_an_EPOCROOT")); //$NON-NLS-1$ //$NON-NLS-2$
				return;
			}
		}
		
		// No objections raised, write the new device entry
		String osVersionText = osVersionCombo.getText();
		Version osVersion;
		String osBranch = ""; //$NON-NLS-1$
		if (osVersionText.equals(AUTO_DETECT)){
			osVersion = new Version("0.0"); //$NON-NLS-1$
		} else {
			int len = osVersionText.length();
			if (Character.isLetter(osVersionText.charAt(len-1))){
				osBranch = osVersionText.substring(len-1);
				osVersionText = osVersionText.substring(0, len-1);
				osVersion = new Version(osVersionText);
			} else {
				osVersion = new Version(osVersionText);
			}
		}
		
		Version sdkVersion = new Version("0.0"); //$NON-NLS-1$
		if (!sdkVersionCombo.getText().equals(AUTO_DETECT)){
			sdkVersion = new Version(sdkVersionCombo.getText());
		}
		
		ISymbianSDK sdk = SymbianSDKFactory.createInstance(deviceIDtext.getText(), 
														   epocRootText.getText(), 
														   vendorCombo.getText(), 
														   osVersion,
														   osBranch,
														   sdkVersion, 
														   false);
		
		SDKCorePlugin.getSDKManager().addSDK(sdk);
		
		List<ISymbianSDK> sdkList = SDKCorePlugin.getSDKManager().getSDKList();
		if (sdkList != null){
			sdkList.add(sdk);
		}
		
		if (isDefaultCombo.getText().equals(DEFAULT_YES)){
			sdk.setIsDefaultSDK(true);
			SDKCorePlugin.getSDKManager().setDefaultSDK(sdk);
		} else {
			sdk.setIsDefaultSDK(false);
		}
		
		super.okPressed();
	}
	
	private boolean isValidIDName(String sdkID){
		boolean isValid = true;
		
		
		// check for spaces in ID
		if (sdkID.contains(" ")){ //$NON-NLS-1$
			MessageDialog.openError(getShell(), Messages.getString("AddSDKDialog.Invalid_SDK_ID"), Messages.getString("AddSDKDialog.SDK_ID_No_Spaces")); //$NON-NLS-1$ //$NON-NLS-2$
			return false;
		}
		
		// check that the ID is unique...
		for (ISymbianSDK sdk : SDKCorePlugin.getSDKManager().getSDKList()){
			if (sdk.getUniqueId().equalsIgnoreCase(sdkID)){
				// id already exists, choose a different one...
				MessageDialog.openError(getShell(), Messages.getString("AddSDKDialog.Duplicate_ID"), Messages.getString("AddSDKDialog.Duplicate_ID_Msg")); //$NON-NLS-1$ //$NON-NLS-2$
				return false;
			}
		}
		
		return isValid;
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
			MessageDialog.openError(getShell(), Messages.getString("AddSDKDialog.Invalid_name_attrib"), Messages.getString("AddSDKDialog.Invalid_name_attrib_msg")); //$NON-NLS-1$ //$NON-NLS-2$
		}
			
		return isValid;
	}

	/**
	 * Create contents of the dialog
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		container.setLayout(gridLayout);

		final Label deviceIdLabel = new Label(container, SWT.NONE);
		deviceIdLabel.setToolTipText(Messages.getString("AddSDKDialog.Enter_unique_id_for_sdk")); //$NON-NLS-1$
		deviceIdLabel.setText(Messages.getString("AddSDKDialog.SDK_ID")); //$NON-NLS-1$

		deviceIDtext = new Text(container, SWT.BORDER);
		deviceIDtext.setLayoutData(new GridData(263, SWT.DEFAULT));
		new Label(container, SWT.NONE);

		final Label epocrootLabel = new Label(container, SWT.NONE);
		epocrootLabel.setToolTipText(Messages.getString("AddSDKDialog.Enter_location_where_epoc32_folder")); //$NON-NLS-1$
		epocrootLabel.setText("EPOCROOT:"); //$NON-NLS-1$

		epocRootText = new Text(container, SWT.BORDER);
		epocRootText.setLayoutData(new GridData(263, SWT.DEFAULT));

		browseEPOCROOTButton = new Button(container, SWT.NONE);
		browseEPOCROOTButton.setToolTipText(Messages.getString("AddSDKDialog.Choose_folder_where_epoc32_exists")); //$NON-NLS-1$
		browseEPOCROOTButton.setText(Messages.getString("AddSDKDialog.Browse")); //$NON-NLS-1$
		addButtonListener(browseEPOCROOTButton);
		
		final Label vendorLabel = new Label(container, SWT.NONE);
		vendorLabel.setToolTipText(Messages.getString("AddSDKDialog.Enter_name_of_sdk")); //$NON-NLS-1$
		vendorLabel.setText(Messages.getString("AddSDKDialog.Vendor")); //$NON-NLS-1$

		vendorCombo = new Combo(container, SWT.NONE);
		vendorCombo.setLayoutData(new GridData(250, SWT.DEFAULT));
		new Label(container, SWT.NONE);
		vendorCombo.add(ISymbianSDK.S60_SDK_NAME);
		vendorCombo.add(ISymbianSDK.S80_SDK_NAME);
		vendorCombo.add(ISymbianSDK.UIQ_SDK_NAME);
		vendorCombo.add(ISymbianSDK.TECHVIEW_SDK_NAME);

		final Label isDefaultLabel = new Label(container, SWT.NONE);
		isDefaultLabel.setToolTipText(Messages.getString("AddSDKDialog.default_label_tooltip")); //$NON-NLS-1$
		isDefaultLabel.setText(Messages.getString("AddSDKDialog.is_default")); //$NON-NLS-1$

		isDefaultCombo = new Combo(container, SWT.READ_ONLY);
		isDefaultCombo.setLayoutData(new GridData(250, SWT.DEFAULT));
		new Label(container, SWT.NONE);
		isDefaultCombo.add(DEFAULT_YES);
		isDefaultCombo.add(DEFAULT_NO);
		isDefaultCombo.select(1);

		final Label osVersionLabel = new Label(container, SWT.NONE);
		osVersionLabel.setToolTipText(Messages.getString("AddSDKDialog.os_version_tooltip")); //$NON-NLS-1$
		osVersionLabel.setText(Messages.getString("AddSDKDialog.os_version")); //$NON-NLS-1$
		//ISymbianMacroStore sms = 
		List<String> osVersions = new ArrayList<String>();
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		osVersions = sdkMgr.getSymbianMacroStore().getSupportedOSVersions();

		osVersionCombo = new Combo(container, SWT.READ_ONLY);
		osVersionCombo.setLayoutData(new GridData(250, SWT.DEFAULT));
		new Label(container, SWT.NONE);
		osVersionCombo.add(AUTO_DETECT);
		
		for (String currVer : osVersions){
			osVersionCombo.add(currVer);
		}
		osVersionCombo.select(0);

		final Label sdkVersionLabel = new Label(container, SWT.NONE);
		sdkVersionLabel.setToolTipText(Messages.getString("AddSDKDialog.select_sdk_tooltip")); //$NON-NLS-1$
		sdkVersionLabel.setText(Messages.getString("AddSDKDialog.sdk_version")); //$NON-NLS-1$

		sdkVersionCombo = new Combo(container, SWT.READ_ONLY);
		sdkVersionCombo.setLayoutData(new GridData(250, SWT.DEFAULT));
		sdkVersionCombo.add(AUTO_DETECT);
		sdkVersionCombo.select(0);
		
		List<String> sdkVerList = new ArrayList<String>();
		sdkVerList = sdkMgr.getSymbianMacroStore().getSDKVersions();
		for (String currVer : sdkVerList){
			sdkVersionCombo.add(currVer);
		}
		
		new Label(container, SWT.NONE);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, SDKUIHelpIds.SDK_ADD_DIALOG);

		
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
		//return new Point(428, 256);
		return super.getInitialSize();
	}
	
	/**
	 * Sets the listener event to a button.
	 * 
	 * @param aButton
	 */
	private void addButtonListener( final Button aButton ) {
		SelectionListener listener = new SelectionAdapter() {
			public void widgetSelected( SelectionEvent e )  {
				if (e.getSource().equals(browseEPOCROOTButton)) {
					browseEPOCROOT();
				}
			}
		};
		aButton.addSelectionListener(listener);
	}
	
	private void browseEPOCROOT(){
        DirectoryDialog browseDir = new DirectoryDialog(getShell(), SWT.OPEN);
        browseDir.setMessage(Messages.getString("AddSDKDialog.Choose_location_for_EPOCROOT")); //$NON-NLS-1$
        BrowseDialogUtils.initializeFrom(browseDir, HostOS.IS_WIN32 ? "C:/Symbian/" : null); //$NON-NLS-1$
        String dirText = browseDir.open();
        if (dirText != null){
        	epocRootText.setText(dirText);
        }
	}

}
