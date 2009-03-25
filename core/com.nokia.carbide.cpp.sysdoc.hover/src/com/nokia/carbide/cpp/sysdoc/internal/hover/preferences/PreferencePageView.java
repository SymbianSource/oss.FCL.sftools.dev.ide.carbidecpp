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
 *				Deniz TURAN
 * Description: 
 * 				
 */
package com.nokia.carbide.cpp.sysdoc.internal.hover.preferences;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.cpp.sysdoc.hover.Activator;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.HoverConstants;
import com.nokia.carbide.cpp.sysdoc.internal.hover.core.MessagesConstants;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.DevLibPluginController;
import com.nokia.carbide.cpp.sysdoc.internal.hover.dal.devlib.DevLibProperties;
import com.nokia.carbide.cpp.sysdoc.internal.hover.uitlis.Logger;

/**
 * 
 * Creates preference page (Window-->Preferences-->Carbide.c++--> Hover Help)
 * User can change active Developer Library or deactivated the plug-in from this
 * panel
 */

public class PreferencePageView extends PreferencePage implements
		IWorkbenchPreferencePage {
	public static final String PREFERENCE_ID="com.nokia.carbide.cpp.sysdoc.internal.hover.preferences.HoverHelpPreferencePage";	
	private Button deActivateCheckBox;
	private Composite topComposite;
	private Combo devLibCombobox;
	private Group devLibGroup;
	private Button autoSelectionDevLibCheckBox;

	public PreferencePageView() {
		super();
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	public void init(IWorkbench workbench) {
	}

	/**
	 * Check if the preference page is valid
	 */
	protected void checkState() {
		boolean deActive = deActivateCheckBox.getSelection();
		if (deActive) {
			resetErrorMessage();
			return;
		}

		// if we dont have any developer library, display an error message
		if (devLibCombobox.getItemCount() == 0) {
			setErrorMessage(MessagesConstants.NOT_AVAILABLE_ANY_DEVELOPER_LIBRARY);
			setValid(false);
		}
	}

	// reset errro message
	private void resetErrorMessage() {
		setErrorMessage(null);
		setValid(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse
	 * .swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {

		setDefaultHelp(parent);
		topComposite = new Composite(parent, SWT.NULL);

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;

		topComposite.setLayout(gridLayout);

		GridData gridTopComData = new GridData();
		gridTopComData.grabExcessVerticalSpace = true;
		topComposite.setLayoutData(gridTopComData);

		// developer library group
		devLibGroup = new Group(topComposite, SWT.NONE);
		devLibGroup.setText("Developer Libraries");
		GridLayout devLibGroupLayout = new GridLayout();
		devLibGroupLayout.numColumns = 2;
		devLibGroup.setLayout(devLibGroupLayout);

		GridData gridDataSPGroup = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL);
		gridDataSPGroup.horizontalSpan = 2;
		gridDataSPGroup.grabExcessVerticalSpace = true;
		gridDataSPGroup.verticalAlignment = GridData.VERTICAL_ALIGN_END;
		devLibGroup.setLayoutData(gridDataSPGroup);

		autoSelectionDevLibCheckBox = new Button(devLibGroup, SWT.CHECK);
		autoSelectionDevLibCheckBox
				.setText(MessagesConstants.AUTOMATICALLY_SELECT_LATEST_DEV_LIB_LABEL);
		GridData gridDataAutoCheckBox = new GridData(GridData.FILL_HORIZONTAL);
		gridDataAutoCheckBox.horizontalSpan = 2;
		autoSelectionDevLibCheckBox.setLayoutData(gridDataAutoCheckBox);
		addAutoSelectionDevLibSelectionListener();
		autoSelectionDevLibCheckBox.setToolTipText(MessagesConstants.HINT_PREFERENCE_AUTO_SELECTION);
		// Developer library combobox
		devLibCombobox = new Combo(devLibGroup, SWT.READ_ONLY | SWT.H_SCROLL);

		GridData gridDataDevLibComboData = new GridData(
				GridData.HORIZONTAL_ALIGN_FILL);
		gridDataDevLibComboData.horizontalSpan = 2;
		gridDataDevLibComboData.widthHint = 100;
		devLibCombobox.setLayoutData(gridDataDevLibComboData);
		devLibCombobox.setToolTipText(MessagesConstants.HINT_PREFERENCE_AVAILABLE_DEVLIBS);
		populateDevLibComboList();

		// deactivate check box...
		deActivateCheckBox = new Button(topComposite, SWT.CHECK);
		deActivateCheckBox.setText("Deactivate Hovering");

		GridData deActivateLayoutData = new GridData();
		deActivateLayoutData.horizontalSpan = 2;
		deActivateLayoutData.verticalAlignment = GridData.END;
		deActivateCheckBox.setLayoutData(deActivateLayoutData);
		addDeActivateCheckBoxSelectionListener();

		loadPreferences();

		if (devLibCombobox.getItemCount() == 0) {
			deActivateEnabledAction();
		}
		return topComposite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {
		storePrefences();
		boolean b = super.performOk();
		PreferencesPageController.getInstance().analyseRecentPropertyChanges();
		return b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
	 */
	@Override
	public void performDefaults() {
		defaultImp();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performApply()
	 */
	@Override
	public void performApply() {
		storePrefences();
		super.performApply();
		PreferencesPageController.getInstance().analyseRecentPropertyChanges();
	}

	// default values
	public void defaultImp() {
		autoSelection();
	}

	// Auto selection is default behaviour
	private void autoSelection() {
		deActivateCheckBox.setSelection(false);
		deActivateDisabledAction();
		// autoSelectionDevLibCheckBox.setEnabled(true);
		devLibCombobox.select(0);
		devLibCombobox.setEnabled(false);
		autoSelectionDevLibCheckBox.setSelection(true);
		checkState();
	}

	/**
	 * Store preferences
	 */
	public void storePrefences() {
		IPreferenceStore store = getPreferenceStore();
		store.setValue(HoverConstants.PREFERENCE_DEACTIVATE_HOVERING,
				deActivateCheckBox.getSelection());

		// auto dev lib selection
		store.setValue(HoverConstants.PREFERENCE_AUTO_DEVLIB_SELECTION,
				autoSelectionDevLibCheckBox.getSelection());

		// Developer Libraries
		String helpPlugin = devLibCombobox.getText();
		store.setValue(HoverConstants.PREFERENCE_DEV_LIB_LOC, helpPlugin);
	}

	/**
	 * Load preferences
	 */
	public void loadPreferences() {
		IPreferenceStore store = getPreferenceStore();

		boolean deActivatedAlready = store
				.getBoolean(HoverConstants.PREFERENCE_DEACTIVATE_HOVERING);
		deActivateCheckBox.setSelection(deActivatedAlready);
		if (deActivatedAlready) {
			deActivateEnabledAction();
			return;
		}

		// auto devlib selection
		boolean autoSelection = store
				.getBoolean(HoverConstants.PREFERENCE_AUTO_DEVLIB_SELECTION);
		autoSelectionDevLibCheckBox.setSelection(autoSelection);
		if (autoSelection) {
			autoSelection();
		}

		// Selected Interchange File
		String selectedInterchange = store
				.getString(HoverConstants.PREFERENCE_DEV_LIB_LOC);
		selectItemInCombo(selectedInterchange);
	}

	// select previous preference among developer libraries
	private void selectItemInCombo(String itemSearched) {
		String[] items = devLibCombobox.getItems();
		for (String item : items) {
			if (item.endsWith(itemSearched)) {
				devLibCombobox.setText(itemSearched);
				setSubDevLibGroupControls(true);
				return;
			}
		}
		Logger
				.logWarn("Previous developer library preference could not find among available developer libraries:"
						+ itemSearched);
		defaultImp();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performHelp()
	 */
	@Override
	public void performHelp() {
		showHelp();
	}

	// display help content
	private void showHelp() {
		PlatformUI.getWorkbench().getHelpSystem().displayHelp(
				HoverConstants.HELP_CONTEXTID);
	}

	// display help content
	private void setDefaultHelp(Composite parent) {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
				HoverConstants.HELP_CONTEXTID);
	}

	// auto selection developer libaray selection listener
	private void addAutoSelectionDevLibSelectionListener() {
		autoSelectionDevLibCheckBox
				.addSelectionListener(new SelectionListener() {

					public void widgetSelected(SelectionEvent event) {
						if (autoSelectionDevLibCheckBox.getSelection()) {
							autoSelection();
						} else {
							devLibCombobox.setEnabled(true);
						}
					}

					public void widgetDefaultSelected(SelectionEvent arg0) {
						Logger.logDebug("2");
					}
				});
	}

	// deactivate selection listener
	private void addDeActivateCheckBoxSelectionListener() {
		deActivateCheckBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if (deActivateCheckBox.getSelection()) {
					deActivateEnabledAction();
				} else {
					deActivateDisabledAction();
					defaultImp();
				}
				checkState();
			}
		});
	}

	/**
	 * Fetch available SDLs and populate SDL plugin combobox
	 */

	private void populateDevLibComboList() {
		Set<DevLibProperties> pluginLists = DevLibPluginController
				.getInstance().getDevLibPropertiesSet();
		String pluginArr[] = new String[pluginLists.size()];
		// pluginArr[0] = PreferenceConstants.EMPTY_ITEM;
		int i = 0;
		for (Iterator<DevLibProperties> it = pluginLists.iterator(); it
				.hasNext();) {
			DevLibProperties devLibProp = it.next();
			String label = devLibProp.getUserFriendlyName();
			pluginArr[i] = label;
			devLibCombobox.setData(label, devLibProp);
			i++;
		}

		devLibCombobox.setItems(pluginArr);
	}

	// when deactivate button is selected, we need to disable other groups
	private void deActivateDisabledAction() {
		setEnabledGroups(true);
	}

	// when deactivate button is deselected, we need to enable other groups
	private void deActivateEnabledAction() {
		setEnabledGroups(false);
	}

	// set enable/disable status of a group
	private void setEnabledGroups(boolean b) {
		setEnableChildren(devLibGroup, b);
	}

	private void setSubDevLibGroupControls(Boolean b) {
		autoSelectionDevLibCheckBox.setEnabled(b);
	}

	// set enable/disable status of children in a group
	private void setEnableChildren(Group g, boolean b) {
		Control[] children = g.getChildren();
		for (Control c : children) {
			c.setEnabled(b);
		}
		g.setEnabled(b);
	}
}