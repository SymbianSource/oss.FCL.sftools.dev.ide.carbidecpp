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
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.cpp.internal.api.sdk.SymbianBuildContextDataCache;
import com.nokia.carbide.cpp.internal.sdk.core.model.SDKManager;
import com.nokia.carbide.cpp.internal.sdk.core.model.SymbianSDK;
import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.carbide.cpp.sdk.ui.shared.AddSDKDialog;
import com.nokia.carbide.cpp.ui.TextAndDialogCellEditor;
import com.nokia.cpp.internal.api.utils.ui.BrowseDialogUtils;

public class SDKPreferencePage
	extends PreferencePage
	implements IWorkbenchPreferencePage {
	
	private class SDKLabelProvider extends LabelProvider implements ITableLabelProvider, ITableColorProvider {
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			ISymbianSDK sdk = (ISymbianSDK) element;
			switch (columnIndex) {
			case 1:
				return sdk.getUniqueId();
			case 2:
				return sdk.getEPOCROOT();
			default:
				return "";
			}
		}

		public Color getForeground(Object element, int columnIndex) {
			ISymbianSDK sdk = (ISymbianSDK) element;
			return updateSDKcolor(sdk);
		}

		public Color getBackground(Object element, int columnIndex) {
			return white;
		}
	}

	private class IdEditingSupport extends EditingSupport {
		private TextCellEditor editor;

		public IdEditingSupport(ColumnViewer viewer) {
			super(viewer);
			editor = new TextCellEditor((Composite) viewer.getControl());
		}

		@Override
		protected boolean canEdit(Object element) {
			if (element instanceof ISymbianSDK) {
				return true;
			}
			return false;
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return editor;
		}

		@Override
		protected Object getValue(Object element) {
			ISymbianSDK sdk = (ISymbianSDK) element;
			return sdk.getUniqueId();
		}

		@Override
		protected void setValue(Object element, Object value) {
			String sdkID = value.toString();

			// check for spaces in ID
			if (sdkID.contains(" ")){ //$NON-NLS-1$
				MessageDialog.openError(getShell(), Messages.getString("AddSDKDialog.Invalid_SDK_ID"), Messages.getString("AddSDKDialog.SDK_ID_No_Spaces")); //$NON-NLS-1$ //$NON-NLS-2$
				return;
			}

			ISymbianSDK sdk = (ISymbianSDK) element;
			((SymbianSDK)sdk).setUniqueId(sdkID);
			SDKCorePlugin.getSDKManager().updateSDK(sdk);
			getViewer().refresh();
		}
	}

	private class LocationEditingSupport extends EditingSupport {
		private LocationCellEditor editor;

		public LocationEditingSupport(ColumnViewer viewer) {
			super(viewer);
			editor = new LocationCellEditor((Composite) viewer.getControl());
		}

		@Override
		protected boolean canEdit(Object element) {
			if (element instanceof ISymbianSDK) {
				return true;
			}
			return false;
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return editor;
		}

		@Override
		protected Object getValue(Object element) {
			ISymbianSDK sdk = (ISymbianSDK) element;
			return sdk.getEPOCROOT();
		}

		@Override
		protected void setValue(Object element, Object value) {
			ISymbianSDK sdk = (ISymbianSDK) element;
			((SymbianSDK)sdk).setEPOCROOT(value.toString());
			SDKCorePlugin.getSDKManager().updateSDK(sdk);
			getViewer().refresh();
		}
	}

	private class LocationCellEditor extends TextAndDialogCellEditor {
		private Button button;
		private Text text;

		public LocationCellEditor(Composite parent) {
			super(parent);
		}

		@Override
		protected Control createContents(Composite parent) {
			text = (Text) super.createContents(parent);
			return text;
		}

		@Override
		protected Control createControl(Composite parent) {
			Control control = super.createControl(parent);
			button = getButton();
			button.setText(Messages.getString("SDKPreferencePage.Browse_Location_Label")); //$NON-NLS-1$
			return control;
		}

		@Override
		protected Object openDialogBox(Control cellEditorWindow) {
			DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.OPEN);
			BrowseDialogUtils.initializeFrom(dialog, text);
			return dialog.open();
		}		
	}

	private class SDKViewerStrategy extends ColumnViewerEditorActivationStrategy {
		public SDKViewerStrategy(ColumnViewer viewer) {
			super(viewer);
		}

		@Override
		protected boolean isEditorActivationEvent(ColumnViewerEditorActivationEvent event) {
			return (event.eventType ==  ColumnViewerEditorActivationEvent.MOUSE_DOUBLE_CLICK_SELECTION);
		}
	}

	private class ScanJobListener implements IJobChangeListener {
		public void done(IJobChangeEvent event) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					finishRescanning();
				}
			});
		}

		public void aboutToRun(IJobChangeEvent event) {}
		public void awake(IJobChangeEvent event) {}
		public void running(IJobChangeEvent event) {}
		public void scheduled(IJobChangeEvent event) {}
		public void sleeping(IJobChangeEvent event) {}
		
	}

	private ISDKManager sdkMgr;
	private List<ISymbianSDK> sdkList;
	private ScanJobListener scanJobListner;
	private CheckboxTableViewer sdkListTableViewer;
	private Button addButton;
	private Button deleteButton;
	private Button propertiesButton;
	private Button rescanButton;
	private Label iconLabel;
	private Label statusLabel;

	private Color black;
	private Color gray;
	private Color red;
	private Color white;

	/**
	 * Constructor.
	 */
	public SDKPreferencePage() {
		super();
//		scanJobListner = new ScanJobListener();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent){
		sdkMgr = SDKCorePlugin.getSDKManager();
		if (sdkMgr == null){
			return; 
		}
		sdkList = sdkMgr.getSDKList();
		if (sdkMgr instanceof SDKManager) {
			SDKManager mgr = (SDKManager) sdkMgr;
//			mgr.addScanJobListner(scanJobListner);
		}

		super.createControl(parent);

		// Hide "Restore Defaults" button
		getDefaultsButton().setVisible(false);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(super.getControl(), SDKUIHelpIds.SDK_PREFERENCES_PAGE);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.DialogPage#dispose()
	 */
	public void dispose() {
		if (sdkMgr != null && sdkMgr instanceof SDKManager){
			SDKManager mgr = (SDKManager) sdkMgr;
			mgr.removeScanJobLisner(scanJobListner);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench arg0) {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	public boolean performOk() {
		// Remember which SDK is enabled
		for (ISymbianSDK sdk : sdkMgr.getSDKList()){
			((SymbianSDK)sdk).setEnabled(false);
		}
		Object[] sdkObjects = sdkListTableViewer.getCheckedElements();
		for (Object currObj : sdkObjects){
			if (currObj instanceof ISymbianSDK){
				ISymbianSDK sdk = (ISymbianSDK)currObj;
				((SymbianSDK)sdk).setEnabled(true);
			}
		}

		// Update cached SDK info
		sdkMgr.updateCarbideSDKCache();
		return super.performOk();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		// Set up colors used in this preference page
		Shell shell = parent.getShell();
		black = shell.getDisplay().getSystemColor(SWT.COLOR_BLACK);
		gray = shell.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
		red = shell.getDisplay().getSystemColor(SWT.COLOR_RED);
		white = shell.getDisplay().getSystemColor(SWT.COLOR_WHITE);

		Composite content = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		content.setLayout(gridLayout);

		// SDK table
		sdkListTableViewer = CheckboxTableViewer.newCheckList(content, 
				SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION);
		createSDKTable();

		// Buttons composite
		Composite composite1 = new Composite(content, SWT.NONE);
		gridLayout = new GridLayout();
		gridLayout.makeColumnsEqualWidth = true;
		composite1.setLayout(gridLayout);
		GridData gridData = new GridData(SWT.LEFT, SWT.TOP, true, false);
		composite1.setLayoutData(gridData);

		// Add button
		addButton = new Button(composite1, SWT.NONE);
		addButton.setLayoutData(gridData);
		addButton.setText(Messages.getString("SDKPreferencePage.Add_Button_Label")); //$NON-NLS-1$
		addButton.setToolTipText(Messages.getString("SDKPreferencePage.Add_Button_ToolTip")); //$NON-NLS-1$
		addButtonListener(addButton);

		// Delete button
		deleteButton = new Button(composite1, SWT.NONE);
		deleteButton.setLayoutData(gridData);
		deleteButton.setText(Messages.getString("SDKPreferencePage.Delete_Button_Label")); //$NON-NLS-1$
		deleteButton.setToolTipText(Messages.getString("SDKPreferencePage.Delete_Button_ToolTip")); //$NON-NLS-1$
		addButtonListener(deleteButton);

		// Properties button
		propertiesButton = new Button(composite1, SWT.NONE);
		propertiesButton.setLayoutData(gridData);
		propertiesButton.setText(Messages.getString("SDKPreferencePage.Properties_Button_Label")); //$NON-NLS-1$
		propertiesButton.setToolTipText(Messages.getString("SDKPreferencePage.Properties_Button_ToolTip")); //$NON-NLS-1$
		addButtonListener(propertiesButton);

		// Status and Rescan composite
		Composite composite2 = new Composite(content, SWT.NONE);
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.widthHint = 350;
		gridData.heightHint = 50;
		composite2.setLayoutData(gridData);
		gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		composite2.setLayout(gridLayout);

		// IStatus icon label
		iconLabel = new Label(composite2, SWT.NONE);
		// IStatus text label
		statusLabel = new Label(composite2, SWT.WRAP);
		gridData = new GridData(SWT.LEFT, SWT.TOP, true, false);
		gridData.verticalSpan = 2;
		statusLabel.setLayoutData(gridData);

		new Label(content, SWT.WRAP); // filler
		
		// Rescan button
		rescanButton = new Button(content, SWT.NONE);
		rescanButton.setToolTipText(Messages.getString("SDKPreferencePage.Rescan_Button_ToolTip")); //$NON-NLS-1$
		rescanButton.setText(Messages.getString("SDKPreferencePage.Rescan_Button_Label")); //$NON-NLS-1$
		addButtonListener(rescanButton);

		// Populate SDK table
		addSDKComponentTableItems();
		selectSDKEntry(0);

		return content;
	}

	private void addButtonListener(final Button aButton) {
		SelectionListener listener = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e)  {
				if (e.getSource().equals(addButton)) {
					handleAddButton();
				} else if (e.getSource().equals(deleteButton)) {
					handleDeleteButton();
				} else if (e.getSource().equals(propertiesButton)) {
					handlePropertiesButton();
				} else if (e.getSource().equals(rescanButton)) {
					handleRescanButton();
				}
			}
		};
		aButton.addSelectionListener(listener);
	}

	private void addSDKComponentTableItems() {
		sdkListTableViewer.setLabelProvider(new SDKLabelProvider());
		sdkListTableViewer.setContentProvider(new ArrayContentProvider());
		sdkList = sdkMgr.getSDKList();
		sdkListTableViewer.setInput(sdkList.toArray());
		Table table = sdkListTableViewer.getTable();
		int count = table.getItemCount();
		if (count < 10) {	// min. number of rows
			count = 10;
		} else 
		if (count > 20) {	// max. number of rows
			count = 20;
		}
		table.setToolTipText(Messages.getString("SDKPreferencePage.List_of_Available_SDKs_ToolTip")); //$NON-NLS-1$
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.widthHint = 350;
		gridData.heightHint = table.getItemHeight() * (count + 2);
		table.setLayoutData(gridData);
		setCheckedElements();
		addSDKTableViewerListeners();
		if (sdkList == null || sdkList.size() == 0){
			statusError(Messages.getString("SDKPreferencePage.No_SDKs_Available_Message")); //$NON-NLS-1$
		}
	}

	private void addSDKTableViewerListeners(){
		sdkListTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				if(event.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection)event.getSelection();
					if (selection.size() == 1){
						ISymbianSDK sdk = (ISymbianSDK)selection.getFirstElement();
						deleteButton.setEnabled(true);
						propertiesButton.setEnabled(true);		
						updateSDKStatus(sdk);
					}
					else {
						deleteButton.setEnabled(false);
						propertiesButton.setEnabled(false);			        	   
					}	
				}
			}
		});
	}

	private void createSDKTable() {
		Table table = sdkListTableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(false);

		SDKViewerStrategy strategy = new SDKViewerStrategy(sdkListTableViewer);
		TableViewerEditor.create(sdkListTableViewer, strategy, ColumnViewerEditor.DEFAULT);

		TableViewerColumn enabledCol = new TableViewerColumn(sdkListTableViewer, SWT.LEFT);
		enabledCol.getColumn().setText(Messages.getString("SDKPreferencePage.SDK_Table_Enabled_Column_Label")); //$NON-NLS-1$
		enabledCol.getColumn().setWidth(50);

		TableViewerColumn idCol = new TableViewerColumn(sdkListTableViewer, SWT.LEFT);
		idCol.setEditingSupport(new IdEditingSupport(sdkListTableViewer)); //$NON-NLS-1$
		idCol.getColumn().setText(Messages.getString("SDKPreferencePage.SDK_Table_ID_Column_Label"));
		idCol.getColumn().setWidth(160);

		TableViewerColumn locationCol = new TableViewerColumn(sdkListTableViewer, SWT.LEFT);
		locationCol.setEditingSupport(new LocationEditingSupport(sdkListTableViewer));
		locationCol.getColumn().setText(Messages.getString("SDKPreferencePage.SDK_Table_Location_Column_Label")); //$NON-NLS-1$
		locationCol.getColumn().setWidth(170);
	}

	private void handleAddButton() {
		AddSDKDialog dialog = new AddSDKDialog(getShell());
		if (dialog.open() == AddSDKDialog.OK){
			sdkList = sdkMgr.getSDKList();
			sdkListTableViewer.setInput(sdkList.toArray());
			setCheckedElements();
			sdkListTableViewer.refresh();
			selectSDKEntry(sdkList.size() - 1);
		}
	}

	private void handleDeleteButton() {
		ISymbianSDK sdk = (ISymbianSDK)((IStructuredSelection)sdkListTableViewer.getSelection()).getFirstElement();
		int index = sdkListTableViewer.getTable().getSelectionIndex();
		if (sdk != null){
			if (sdkMgr.removeSDK(sdk.getUniqueId())){
				sdkList = sdkMgr.getSDKList();
				sdkListTableViewer.setInput(sdkList.toArray());
				if (index > 0) {
					selectSDKEntry(index - 1);
				} else {
					selectSDKEntry(index);
				}
				sdkListTableViewer.refresh();
			}
		}
	}

	private void handlePropertiesButton() {
		ISymbianSDK sdk = (ISymbianSDK)((IStructuredSelection)sdkListTableViewer.getSelection()).getFirstElement();
		int index = sdkListTableViewer.getTable().getSelectionIndex();
		if (sdk != null){
			SDKPropertiesDialog sdkPropDlg = new SDKPropertiesDialog(getShell(), sdk);
			sdkPropDlg.open();
			selectSDKEntry(index);
		} else {
			MessageDialog.openError(getShell(), Messages.getString("SDKPreferencePage.No_SDK_Selected"), Messages.getString("SDKPreferencePage.No_selected_SDK_detected")); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	private void handleRescanButton() {
		// forcible rescan; dump cache
		SymbianBuildContextDataCache.refreshForSDKs(null);
		startRescanning();
		sdkMgr.scanSDKs();
		finishRescanning();
	}

	private void startRescanning() {
		rescanButton.setText(Messages.getString("SDKPreferencePage.Rescanning_Button_Label")); //$NON-NLS-1$
		rescanButton.setEnabled(false);
	}

	private void finishRescanning(){
		sdkListTableViewer.getTable().clearAll();
		sdkListTableViewer.refresh();
		sdkList.clear();
		sdkList = sdkMgr.getSDKList();
		addSDKComponentTableItems();
		sdkListTableViewer.refresh();
		selectSDKEntry(0);
		rescanButton.setText(Messages.getString("SDKPreferencePage.Rescan_Button_Label")); //$NON-NLS-1$
		rescanButton.setEnabled(true);
	}

	private void selectSDKEntry(int index) {
		ISymbianSDK sdk = (ISymbianSDK)sdkListTableViewer.getElementAt(index);
		if (sdk != null){
			sdkListTableViewer.setSelection(new StructuredSelection(sdk), true);
			sdkListTableViewer.getTable().setFocus();
		}
		updateSDKStatus(sdk);
	}

	private void setCheckedElements() {
		Iterator<ISymbianSDK> iterator = sdkList.iterator();
		while (iterator.hasNext()) {
			ISymbianSDK sdk = iterator.next();
			sdkListTableViewer.setChecked(sdk, sdk.isEnabled());
		}
	}

	private void statusClear() {
		iconLabel.setImage(null);
		statusLabel.setText("");
	}

	private void statusError(String msg) {
		String errorMsg = "Error : " + msg;
		iconLabel.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_ERROR_TSK));
		statusLabel.setText(errorMsg);
		statusLabel.setForeground(red);
		statusLabel.setBackground(gray);
		statusLabel.update();
		statusLabel.getParent().layout(true);
	}

	private void statusWarning(String msg) {
		String warningMsg = "Warning : " + msg; //$NON-NLS-1$
		iconLabel.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK));
		statusLabel.setText(warningMsg);
		statusLabel.setForeground(black);
		statusLabel.setBackground(gray);
		statusLabel.update();
		statusLabel.getParent().layout(true);
	}

	private void statusInfo(String msg) {
		String infoMsg = "Info : " + msg; //$NON-NLS-1$
		iconLabel.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_INFO_TSK));
		statusLabel.setText(infoMsg);
		statusLabel.setForeground(black);
		statusLabel.setBackground(gray);
		statusLabel.update();
		statusLabel.getParent().layout(true);
	}

	private Color updateSDKcolor(ISymbianSDK sdk){
		Color color = black;

		// Check SDK EPOCROOT
		String epocRootStr = sdk.getEPOCROOT();
		IPath epocRoot = new Path(epocRootStr);
		epocRoot = epocRoot.append("epoc32");
		File epocRootFile = epocRoot.toFile();
		if (!epocRootFile.exists()) {
			color = red;
		}
		
		// Check SDK OS Version
		if ((sdk.getOSVersion().getMajor() < 9 ||
			(sdk.getOSVersion().getMajor() == 9 && sdk.getOSVersion().getMinor() < 4))) {
			color = red;
		}

		return color;
	}

	private void updateSDKStatus(ISymbianSDK sdk){
		// No SDK selected
		if (sdk == null) {
			statusWarning(Messages.getString("SDKPreferencePage.No_SDKs_Available_Message")); //$NON-NLS-1$
			return;
		} else {
			// Check SDK EPOCROOT
			String epocRootStr = sdk.getEPOCROOT();
			IPath epocRoot = new Path(epocRootStr);
			epocRoot = epocRoot.append("epoc32");
			File epocRootFile = epocRoot.toFile();
			if (!epocRootFile.exists()) {
				statusError(Messages.getString("SDKPreferencePage.Invalid_Location_Message")); //$NON-NLS-1$
				return;
			}

			// Check SDK OS Version
			if ((sdk.getOSVersion().getMajor() < 9 ||
				(sdk.getOSVersion().getMajor() == 9 && sdk.getOSVersion().getMinor() < 4))) {
				statusError(MessageFormat.format(
						Messages.getString("SDKPreferencePage.Invalid_SDK_Message"),  //$NON-NLS-1$
						sdk.getOSVersion().toString())); //$NON-NLS-1$
				return;
			}			

			// No error
			statusClear();
		}
	}

}