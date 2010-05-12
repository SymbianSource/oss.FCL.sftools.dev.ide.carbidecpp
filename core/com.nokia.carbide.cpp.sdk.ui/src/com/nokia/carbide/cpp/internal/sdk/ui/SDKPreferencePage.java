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
package com.nokia.carbide.cpp.internal.sdk.ui;

import java.io.*;
import java.util.*;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;

import com.nokia.carbide.cpp.internal.api.sdk.SymbianBuildContextDataCache;
import com.nokia.carbide.cpp.internal.sdk.core.model.SDKManager;
import com.nokia.carbide.cpp.sdk.core.*;
import com.nokia.carbide.cpp.sdk.ui.SDKUIPlugin;
import com.nokia.carbide.cpp.sdk.ui.shared.AddSDKDialog;

public class SDKPreferencePage
	extends PreferencePage
	implements IWorkbenchPreferencePage {
	
	ISDKManager sdkMgr;
	private CheckboxTableViewer sdkListTableViewer;
	private List<ISymbianSDK> sdkList; 
	private Button sdkpropertiesButton;
	private Button removeSdkButton;
	private Button addNewSdkButton;
	private Button rescanNowButton;
	
	private Label epocrootLabel;
	private Label availablePlatformsLabel;
	private Label osVersionLabel;
	private Label diagnosticCheckLabel;
	
	private Button listenForDevicesXMLChangeButton;
	
	private static final String EPOCROOT_LABEL = "EPOCROOT: ";	//$NON-NLS-1$
	private static final String PLATFORMS_LABEL = "Available Platforms: "; //$NON-NLS-1$
	private static final String DIAGNOSTIC_CHECK_LABEL = "Diagnostic Check: "; //$NON-NLS-1$
	private static final String OS_VERSION_LABEL = "OS Version: "; //$NON-NLS-1$
	
	private Color RED;
	private Color BLACK;
	private Color GRAY;
	Shell shell;
	
	public SDKPreferencePage() {
		super();
		
	}
	
	@Override
	protected Control createContents(Composite parent) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		
		IPreferenceStore prefsStore = SDKUIPlugin.getDefault().getPreferenceStore();
		sdkMgr = SDKCorePlugin.getSDKManager();
		if (sdkMgr == null){
			return; 
		}
		
		shell = parent.getShell();
		sdkList = sdkMgr.getSDKList();
		RED = shell.getDisplay().getSystemColor(SWT.COLOR_RED);
		BLACK = shell.getDisplay().getSystemColor(SWT.COLOR_BLACK);
		GRAY = shell.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
		
		// check that devices.xml actually exists
		if (sdkMgr instanceof SDKManager)
			((SDKManager) sdkMgr).checkDevicesXMLExistAndCreate();
		
		Composite content = new Composite(parent, SWT.NONE);
		setControl(content);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		content.setLayout(gridLayout);

		final Group availableSymbianOsGroup = new Group(content, SWT.NONE);
		availableSymbianOsGroup.setToolTipText(Messages.getString("SDKPreferencePage.OS_Group_ToolTip")); //$NON-NLS-1$
		final GridData availableSymbianOsGridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		availableSymbianOsGridData.widthHint = 350;
		availableSymbianOsGroup.setText(Messages.getString("SDKPreferencePage.Available_SDKs_Label")); //$NON-NLS-1$
		availableSymbianOsGroup.setLayoutData(availableSymbianOsGridData);
		availableSymbianOsGroup.setLayout(new GridLayout());

		sdkListTableViewer = CheckboxTableViewer.newCheckList(availableSymbianOsGroup, SWT.BORDER);
		sdkListTableViewer.getTable().setLayoutData(availableSymbianOsGridData);
		
		final Composite composite = new Composite(content, SWT.NONE);
		composite.setLayoutData(new GridData());
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.makeColumnsEqualWidth = true;
		composite.setLayout(gridLayout_1);

		removeSdkButton = new Button(composite, SWT.NONE);
		removeSdkButton.setToolTipText(Messages.getString("SDKPreferencePage.Delete_SDK_ToolTip")); //$NON-NLS-1$
		final GridData gridData = new GridData(SWT.LEFT, SWT.TOP, true, false);
		removeSdkButton.setLayoutData(gridData);
		removeSdkButton.setText(Messages.getString("SDKPreferencePage.Remove_SDK_Label")); //$NON-NLS-1$
		addButtonListener(removeSdkButton);
		
		addNewSdkButton = new Button(composite, SWT.NONE);
		addNewSdkButton.setToolTipText(Messages.getString("SDKPreferencePage.Add_New_SDK_ToolTip")); //$NON-NLS-1$
		addNewSdkButton.setLayoutData(gridData);
		addNewSdkButton.setText(Messages.getString("SDKPreferencePage.Add_New_SDK_Label")); //$NON-NLS-1$
		addButtonListener(addNewSdkButton);
		
		sdkpropertiesButton = new Button(composite, SWT.NONE);
		sdkpropertiesButton.setToolTipText(Messages.getString("SDKPreferencePage.SDK_Props_Button_ToolTip")); //$NON-NLS-1$
		sdkpropertiesButton.setLayoutData(gridData);
		sdkpropertiesButton.setText(Messages.getString("SDKPreferencePage.SDK_Props_Button_Label")); //$NON-NLS-1$
		addButtonListener(sdkpropertiesButton);
		
		final Group sdkInformationGroup = new Group(content, SWT.NONE);
		sdkInformationGroup.setToolTipText(Messages.getString("SDKPreferencePage.SDK_Info_ToolTip")); //$NON-NLS-1$
		final GridData sdkInfoGridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		sdkInfoGridData.widthHint = 350;
		sdkInformationGroup.setText(Messages.getString("SDKPreferencePage.SDK_Info_Label")); //$NON-NLS-1$
		sdkInformationGroup.setLayoutData(sdkInfoGridData);
		sdkInformationGroup.setLayout(new GridLayout());

		epocrootLabel = new Label(sdkInformationGroup, SWT.WRAP);
		epocrootLabel.setToolTipText(Messages.getString("SDKPreferencePage.EPOC32_Loc_ToolTip")); //$NON-NLS-1$
		epocrootLabel.setLayoutData(new GridData(300, SWT.DEFAULT));
		epocrootLabel.setText("EPOCROOT:"); //$NON-NLS-1$

		osVersionLabel = new Label(sdkInformationGroup, SWT.WRAP);
		osVersionLabel.setToolTipText(Messages.getString("SDKPreferencePage.OSVesions_ToolTip")); //$NON-NLS-1$
		osVersionLabel.setLayoutData(new GridData(300, SWT.DEFAULT));
		osVersionLabel.setText(Messages.getString("SDKPreferencePage.OSVersion_Label")); //$NON-NLS-1$

		availablePlatformsLabel = new Label(sdkInformationGroup, SWT.WRAP);
		availablePlatformsLabel.setToolTipText(Messages.getString("SDKPreferencePage.Platforms_ToolTip")); //$NON-NLS-1$
		availablePlatformsLabel.setLayoutData(new GridData(300, SWT.DEFAULT));
		availablePlatformsLabel.setText(Messages.getString("SDKPreferencePage.Available_Platforms")); //$NON-NLS-1$

		diagnosticCheckLabel = new Label(sdkInformationGroup, SWT.WRAP);
		diagnosticCheckLabel.setToolTipText(Messages.getString("SDKPreferencePage.Diagnostic_Check_ToolTip")); //$NON-NLS-1$
		diagnosticCheckLabel.setLayoutData(sdkInfoGridData);
		diagnosticCheckLabel.setText(Messages.getString("SDKPreferencePage.Diagnostic_Check_Label")); //$NON-NLS-1$
		new Label(content, SWT.NONE);

		listenForDevicesXMLChangeButton = new Button(content, SWT.CHECK);
		listenForDevicesXMLChangeButton.setText(Messages.getString("SDKPreferencePage.listerForDevicesXML"));
		listenForDevicesXMLChangeButton.setToolTipText(Messages.getString("SDKPreferencePage.listerForDevicesXML_Tooltip"));	//$NON-NLS-1$
		listenForDevicesXMLChangeButton.setSelection(prefsStore.getBoolean(SDKUIPreferenceConstants.LISTEN_FOR_DEVICES_XML_CHANGE));
		
		new Label(content, SWT.WRAP); // filler
		
		rescanNowButton = new Button(content, SWT.NONE);
		rescanNowButton.setToolTipText(Messages.getString("SDKPreferencePage.Rescan_Button_ToolTip")); //$NON-NLS-1$
		rescanNowButton.setLayoutData(new GridData());
		rescanNowButton.setText(Messages.getString("SDKPreferencePage.Rescan_Button_Label")); //$NON-NLS-1$
		addButtonListener(rescanNowButton);
		new Label(content, SWT.NONE);
		
		// Build the checked table of SDKs
		addSDKComponentTableItems();
		
		ISymbianSDK sdk = (ISymbianSDK)sdkListTableViewer.getElementAt(0);
		if (sdk != null){
			sdkListTableViewer.setSelection(new StructuredSelection(sdk), true);
			setSelectedSDKInfoText(sdk);
		}
		
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(super.getControl(), SDKUIHelpIds.SDK_PREFERENCES_PAGE);
	}
	
	public boolean performOk() {
		
		IPreferenceStore prefsStore = SDKUIPlugin.getDefault().getPreferenceStore();
		prefsStore.setValue(SDKUIPreferenceConstants.LISTEN_FOR_DEVICES_XML_CHANGE, listenForDevicesXMLChangeButton.getSelection());
		
		for (ISymbianSDK sdk : sdkMgr.getSDKList()){
			sdk.setEnabled(false);
		}
		Object[] sdkObjects = sdkListTableViewer.getCheckedElements();
		for (Object currObj : sdkObjects){
			if (currObj instanceof ISymbianSDK){
				ISymbianSDK sdk = (ISymbianSDK)currObj;
				sdk.setEnabled(true);
			}
		}
		
		ISDKManager sdkMgr =SDKCorePlugin.getSDKManager();
		sdkMgr.updateCarbideSDKCache();
		
		return super.performOk();
	}
	
	private void addSDKComponentTableItems() {
		sdkListTableViewer.setContentProvider(new SDKTableComponentsContentProvider());
		sdkListTableViewer.setLabelProvider(new SDKTableComponentsLabelProvider());
		Table lTable = sdkListTableViewer.getTable();
		
		sdkList = SDKCorePlugin.getSDKManager().getSDKList();
		sdkListTableViewer.setInput(sdkList);
		lTable.setToolTipText(Messages.getString("SDKPreferencePage.List_of_Available_SDKs_ToolTip")); //$NON-NLS-1$
		lTable.setVisible(true);
		addSDKTableViewerSelectionListener();
		//lTable.setLayoutData(grid);
		if (sdkList == null || sdkList.size() == 0){
			diagnosticCheckLabel.setText(DIAGNOSTIC_CHECK_LABEL + Messages.getString("SDKPreferencePage.No_SDKs_Available")); //$NON-NLS-1$
			diagnosticCheckLabel.setForeground(RED);
			diagnosticCheckLabel.setBackground(GRAY);
		} else {
			setCheckBoxes(sdkList);
		}
	}
	
	/**
	 * Sets the checkbox state for enabled SDKs.
	 */
	private void setCheckBoxes(List<ISymbianSDK> sdkList) {
		List<ISymbianSDK> sdkListCopy = new ArrayList<ISymbianSDK>();
		for (ISymbianSDK sdkCheck : sdkList){
			if (sdkCheck.isEnabled()){
				sdkListCopy.add(sdkCheck);
			}
		}
		sdkListTableViewer.setCheckedElements(sdkListCopy.toArray(new ISymbianSDK[sdkListCopy.size()]));			
	}
	
	 /**
	  *  Extends <code>LabelProvider</code> with the default implementation 
	  *  and implements<code>ITableLabelProvider</code> with the methods
	  *	 to provide the text and/or image for each column of a given element.  
	  *	 Used by table viewers.
	  */
	
	static class SDKTableComponentsLabelProvider extends LabelProvider implements ITableLabelProvider, ITableColorProvider  {

		/**
		 * Returns the label image for the given column of the given element.
		 * The default implementation returns null.
		 * 
		 * @return image object
		 */
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		/**
		 * Returns the label text for the given column of the given element.
		 * 
		 * @return string is the label text for the given column.
		 */
		public String getColumnText(Object arg0, int column) {
			if (arg0 instanceof ISymbianSDK){
				ISymbianSDK sdk = (ISymbianSDK)arg0;
				return sdk.getUniqueId();
			}
			
			return ""; //$NON-NLS-1$
		}

		private Color lBlack = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
		private Color lRed =  Display.getDefault().getSystemColor(SWT.COLOR_RED);
		
		public Color getForeground(Object obj, int index) {
			if (obj instanceof ISymbianSDK) {
				ISymbianSDK sdk = (ISymbianSDK)obj;
				File epocRootTest = new File(sdk.getEPOCROOT());
				if ((sdk.getAvailablePlatforms().size() <= 0) || !epocRootTest.exists()) {
					// There are no build configs and/or no epocroot exists
					return lRed;
				} else {
					return lBlack;
				}
			}
			return null;
		}
		
		public Color getBackground(Object element, int columnIndex) {
			return null;
		}
	}
	
	/** 
	 * This implementation of <code>IStructuredContentProvider</code> handles
	 * 	the case where the viewer input is an unchanging array or collection of elements.
	 * 
	 */
	static class SDKTableComponentsContentProvider implements IStructuredContentProvider {
		

		/**
		 * Returns the elements in the input
		 * 
		 * @return array of objects.
		 */
		public Object[] getElements(Object arg0) {
			if (arg0 instanceof ArrayList) {
				return ((ArrayList<?>)arg0).toArray();
			}
			return new Object[0];
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			//do nothing
		}
	}
	
	/**
	 * Sets the listener event to a button.
	 * 
	 * @param aButton
	 */
	private void addButtonListener( final Button aButton ) {
		SelectionListener listener = new SelectionAdapter() {
			public void widgetSelected( SelectionEvent e )  {
				if (e.getSource().equals(sdkpropertiesButton)) {
					sdkpropertiesButtonAction();
				} else if (e.getSource().equals(addNewSdkButton)) {
					addNewSdkButtonAction();
				} else if (e.getSource().equals(removeSdkButton)) {
					removeSdkButtonAction();
				} else if (e.getSource().equals(rescanNowButton)) {
					rescanNowButtonAction();
				}
				
			}
		};
		aButton.addSelectionListener(listener);
	}
	
	private void sdkpropertiesButtonAction(){
		ISymbianSDK sdk = (ISymbianSDK)((IStructuredSelection)sdkListTableViewer.getSelection()).getFirstElement();
		if (sdk != null){
			SDKPropertiesDialog sdkPropDlg = new SDKPropertiesDialog(getShell(), this, sdk);
			if (sdkPropDlg.open() == SDKPropertiesDialog.OK){
				sdkListTableViewer.refresh();
				setSelectedSDKInfoText(sdk);
				// forcible rescan; dump cache
				SymbianBuildContextDataCache.refreshForSDKs(new ISymbianSDK[] { sdk });
				rescanSDKs(false);
			}
		} else {
			MessageDialog.openError(getShell(), Messages.getString("SDKPreferencePage.No_SDK_Selected"), Messages.getString("SDKPreferencePage.No_selected_SDK_detected")); //$NON-NLS-1$ //$NON-NLS-2$
		}

	}
	
	private void removeSdkButtonAction(){
		ISymbianSDK sdk = (ISymbianSDK)((IStructuredSelection)sdkListTableViewer.getSelection()).getFirstElement();
		if (sdk != null){
			if (MessageDialog.openConfirm(getShell(), Messages.getString("SDKPreferencePage.Confirm_Delete") + sdk.getUniqueId() , Messages.getString("SDKPreferencePage.Confirm_Delete_Msg"))){ //$NON-NLS-1$ //$NON-NLS-2$
				if (sdkMgr.removeSDK(sdk.getUniqueId())){
					sdkList.remove(sdk);
					sdkListTableViewer.refresh();
					sdk = (ISymbianSDK)sdkListTableViewer.getElementAt(0);
					if (sdk != null){
						sdkListTableViewer.setSelection(new StructuredSelection(sdk), true);
						setSelectedSDKInfoText(sdk);
					}
				}
			}
		}
	}
	
	private void addNewSdkButtonAction(){
		
		AddSDKDialog dialog = new AddSDKDialog(getShell());
		if (dialog.open() == AddSDKDialog.OK){
			addSDKComponentTableItems();
			sdkListTableViewer.refresh();
		}
	}
	
	private void rescanNowButtonAction(){
		// forcible rescan; dump cache
		SymbianBuildContextDataCache.refreshForSDKs(null);
		rescanSDKs(true);
	}
	
	private void rescanSDKs(boolean scanForNewPlugins){
		sdkListTableViewer.getTable().clearAll();
		sdkListTableViewer.refresh();
		sdkList.clear();
		ISDKManager sdkMgr = SDKCorePlugin.getSDKManager();
		sdkMgr.scanSDKs();
		sdkList = sdkMgr.getSDKList();
		addSDKComponentTableItems();
		sdkListTableViewer.refresh();
		
		ISymbianSDK sdk = (ISymbianSDK)sdkListTableViewer.getElementAt(0);
		if (sdk != null){
			sdkListTableViewer.setSelection(new StructuredSelection(sdk), true);
			setSelectedSDKInfoText(sdk);
		}
		
		if (scanForNewPlugins){
			NewPluginChecker.checkForNewlyInstalledPlugins(SDKUIPlugin.getDefault().getWorkbench());
		}
	}
	
	/**
	 * Sets the selection listener action event to the CheckboxTableViewer.
	 * 
	 * @param sdkTable 
	 */
	private void addSDKTableViewerSelectionListener(){
		sdkListTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			   public void selectionChanged(SelectionChangedEvent event) {

			       if(event.getSelection() instanceof IStructuredSelection) {
			           IStructuredSelection selection = (IStructuredSelection)event.getSelection();
			           if (selection.size() == 1){
			        	   ISymbianSDK sdk = (ISymbianSDK)selection.getFirstElement();
			        	   sdkpropertiesButton.setEnabled(true);
			          	   removeSdkButton.setEnabled(true);		
			        	   setSelectedSDKInfoText(sdk);
			        
			           }else {
			        	   sdkpropertiesButton.setEnabled(false);
			        	   removeSdkButton.setEnabled(false);			        	   
			           }	
			       }
			   }
			} );
	}
	
	private void setSelectedSDKInfoText(ISymbianSDK sdk){
		boolean sdkHasError = false;
		String epocRootStr = sdk.getEPOCROOT();
		File epocRootTest = new File(epocRootStr);
		if (!epocRootTest.exists()) {
			sdkHasError = true;
			epocrootLabel.setText(EPOCROOT_LABEL + epocRootStr + Messages.getString("SDKPreferencePage.Path_Does_Not_Exist")); //$NON-NLS-1$
			epocrootLabel.setForeground(RED);
			epocrootLabel.setBackground(GRAY);
		}
		else {
			epocrootLabel.setText(EPOCROOT_LABEL + epocRootStr);
			epocrootLabel.setForeground(BLACK);
			epocrootLabel.setBackground(GRAY);
		}
		
		// Set platforms 
		if (sdk.getAvailablePlatforms().size() == 0) {
			sdkHasError = true;
			availablePlatformsLabel.setText(PLATFORMS_LABEL + Messages.getString("SDKPreferencePage.Platforms_cannot_be_determined")); //$NON-NLS-1$
			availablePlatformsLabel.setForeground(RED);
			availablePlatformsLabel.setBackground(GRAY);
		}
		else {
			availablePlatformsLabel.setText(PLATFORMS_LABEL + sdk.getAvailablePlatforms().toString());
			availablePlatformsLabel.setForeground(BLACK);
			availablePlatformsLabel.setBackground(GRAY);
		}
		
		// Set OS Version 
		if (sdk.getOSVersion().getMajor() == 0) {
			sdkHasError = true;
			osVersionLabel.setText(OS_VERSION_LABEL + Messages.getString("SDKPreferencePage.OS_Version_Cannot_Be_Determined")); //$NON-NLS-1$
			osVersionLabel.setForeground(RED);
			osVersionLabel.setBackground(GRAY);
		}
		else if (!SDKCorePlugin.SUPPORTS_SBSV1_BUILDER && 
				 (sdk.getOSVersion().getMajor() < 9 ||
				 (sdk.getOSVersion().getMajor() == 9 && sdk.getOSVersion().getMinor() <= 4))){
			sdkHasError = true;
			osVersionLabel.setText(OS_VERSION_LABEL + "This OS version is not supported: " + sdk.getOSVersion()); 
			osVersionLabel.setForeground(RED);
			osVersionLabel.setBackground(GRAY);
			
		} else {
			if (sdk.getSDKOSBranch().length() > 0) {
				osVersionLabel.setText(OS_VERSION_LABEL + sdk.getOSVersion().toString() + " (Branch = \"" + sdk.getSDKOSBranch() + "\")"); //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				osVersionLabel.setText(OS_VERSION_LABEL + sdk.getOSVersion().toString());
			}
			osVersionLabel.setForeground(BLACK);
			osVersionLabel.setBackground(GRAY);
		}
		
		//  Get diagnostic check 
		//if (lsdk.getSomeError().size() <= 0) {
		if (sdkHasError){
			diagnosticCheckLabel.setText(DIAGNOSTIC_CHECK_LABEL + Messages.getString("SDKPreferencePage.SDK_Cannot_Be_Used")); //$NON-NLS-1$
			diagnosticCheckLabel.setForeground(RED);
			diagnosticCheckLabel.setBackground(GRAY);
		}
		else {
			// check for other types of errors:
			if (!sdk.getToolsPath().toFile().exists()){
				diagnosticCheckLabel.setText(DIAGNOSTIC_CHECK_LABEL + Messages.getString("SDKPreferencePage.No_Tools_Path") +  " " + sdk.getToolsPath().toOSString()); //$NON-NLS-1$
				diagnosticCheckLabel.setForeground(RED);
				diagnosticCheckLabel.setBackground(GRAY);
			} else if (!sdk.getIncludePath().toFile().exists()){
				diagnosticCheckLabel.setText(DIAGNOSTIC_CHECK_LABEL + Messages.getString("SDKPreferencePage.No_Include_Path") +  " " + sdk.getIncludePath().toOSString()); //$NON-NLS-1$
				diagnosticCheckLabel.setForeground(RED);
				diagnosticCheckLabel.setBackground(GRAY);
			} else if ( ((sdk.getPrefixFile() == null) || (!sdk.getPrefixFile().exists())) && (sdk.getOSVersion().getMajor() >= 9)){
					diagnosticCheckLabel.setText(DIAGNOSTIC_CHECK_LABEL + Messages.getString("SDKPreferencePage.No_HRH_File")); //$NON-NLS-1$
					diagnosticCheckLabel.setForeground(RED);
					diagnosticCheckLabel.setBackground(GRAY);
			} else if ( ((sdk.isS60()) && sdk.getSDKVersion().getMajor() == 0)){
				diagnosticCheckLabel.setText(DIAGNOSTIC_CHECK_LABEL + Messages.getString("SDKPreferencePage.No_SDK_Version")); //$NON-NLS-1$
				diagnosticCheckLabel.setForeground(RED);
				diagnosticCheckLabel.setBackground(GRAY);
			} else {
				// Everything is OK....
				diagnosticCheckLabel.setText(DIAGNOSTIC_CHECK_LABEL + "OK\r\n "); //$NON-NLS-1$
				diagnosticCheckLabel.setForeground(BLACK);
				diagnosticCheckLabel.setBackground(GRAY);
			}
		}
   } 
	
	protected List<ISymbianSDK> getSDKList(){
		return sdkList;
	}
	
	protected ISDKManager getSDKManager(){
		return sdkMgr;
	}
}