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
package com.nokia.cdt.debug.cw.symbian.ui;


import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.cdt.utils.ui.controls.ControlFactory;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.freescale.cdt.debug.cw.core.CWPlugin;
import com.freescale.cdt.debug.cw.core.ui.CWDebugUIPlugin;
import com.freescale.cdt.debug.cw.core.ui.DebuggerUIMessages;

import cwdbg.PreferenceConstants;


public class GlobalSettings extends PreferencePage implements
		IWorkbenchPreferencePage, IPropertyChangeListener {
	
	private String contextHelpID = "debugger_global_settings_page_help"; //$NON-NLS-1$

	public class PreferenceStore implements IPreferenceStore {
		private Preferences m_prefs;

		private HashMap m_listeners = new HashMap();

		public PreferenceStore(Preferences prefs) {
			m_prefs = prefs;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.preference.IPreferenceStore#addPropertyChangeListener(org.eclipse.jface.util.IPropertyChangeListener)
		 */
		public void addPropertyChangeListener(
				final IPropertyChangeListener jface_listener) {
			Preferences.IPropertyChangeListener listener = new Preferences.IPropertyChangeListener() {

				public void propertyChange(
						org.eclipse.core.runtime.Preferences.PropertyChangeEvent event) {
					jface_listener.propertyChange(
							new PropertyChangeEvent(PreferenceStore.this, event.getProperty(), event.getNewValue(),
							event.getOldValue()));
				}
			};

			m_listeners.put(jface_listener, listener);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.preference.IPreferenceStore#firePropertyChangeEvent(java.lang.String,
		 *      java.lang.Object, java.lang.Object)
		 */
		public void firePropertyChangeEvent(String name, Object oldValue,
				Object newValue) {
			Iterator iter = m_listeners.keySet().iterator();

			while (iter.hasNext()) {
				((IPropertyChangeListener)iter.next()).propertyChange(
						new PropertyChangeEvent(this, name, oldValue, newValue));
			}
		}

		/*
		 * org.eclipse.jface.preference.IPreferenceStore methods in which we
		 * simply redirect the calls to our org.eclipse.core.runtime.Preferences
		 * object
		 */
		public boolean contains(String name) {
			return m_prefs.contains(name);
		}

		public boolean getBoolean(String name) {
			return m_prefs.getBoolean(name);
		}

		public boolean getDefaultBoolean(String name) {
			return m_prefs.getDefaultBoolean(name);
		}

		public double getDefaultDouble(String name) {
			return m_prefs.getDefaultDouble(name);
		}

		public float getDefaultFloat(String name) {
			return m_prefs.getDefaultFloat(name);
		}

		public int getDefaultInt(String name) {
			return m_prefs.getDefaultInt(name);
		}

		public long getDefaultLong(String name) {
			return m_prefs.getDefaultLong(name);
		}

		public String getDefaultString(String name) {
			return m_prefs.getDefaultString(name);
		}

		public double getDouble(String name) {
			return m_prefs.getDouble(name);
		}

		public float getFloat(String name) {
			return m_prefs.getFloat(name);
		}

		public int getInt(String name) {
			return m_prefs.getInt(name);
		}

		public long getLong(String name) {
			return m_prefs.getLong(name);
		}

		public String getString(String name) {
			return m_prefs.getString(name);
		}

		public boolean isDefault(String name) {
			return m_prefs.isDefault(name);
		}

		public boolean needsSaving() {
			return m_prefs.needsSaving();
		}

		public void setToDefault(String name) {
			m_prefs.setToDefault(name);
		}

		public void setDefault(String name, double value) {
			m_prefs.setDefault(name, value);
		}

		public void setDefault(String name, float value) {
			m_prefs.setDefault(name, value);
		}

		public void setDefault(String name, int value) {
			m_prefs.setDefault(name, value);
		}

		public void setDefault(String name, long value) {
			m_prefs.setDefault(name, value);
		}

		public void setDefault(String name, String value) {
			m_prefs.setDefault(name, value);
		}

		public void setDefault(String name, boolean value) {
			m_prefs.setDefault(name, value);
		}

		public void setValue(String name, double value) {
			m_prefs.setValue(name, value);
		}

		public void setValue(String name, float value) {
			m_prefs.setValue(name, value);
		}

		public void setValue(String name, int value) {
			m_prefs.setValue(name, value);
		}

		public void setValue(String name, long value) {
			m_prefs.setValue(name, value);
		}

		public void setValue(String name, String value) {
			m_prefs.setValue(name, value);
		}

		public void setValue(String name, boolean value) {
			m_prefs.setValue(name, value);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.preference.IPreferenceStore#putValue(java.lang.String,
		 *      java.lang.String)
		 */
		public void putValue(String name, String value) {
			m_prefs.setValue(name, value);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.preference.IPreferenceStore#removePropertyChangeListener(org.eclipse.jface.util.IPropertyChangeListener)
		 */
		public void removePropertyChangeListener(
				IPropertyChangeListener jface_listener) {
			m_listeners.remove(jface_listener);
		}
	}

	public class BooleanFieldEditor2 extends BooleanFieldEditor {

		// Have to subclass BooleanFieldEditor as there is no way to
		// set tool tip text on the standard one.

		public BooleanFieldEditor2(String name, String label, Composite parent) {
			super(name, label, parent);
		}

		public void setToolTipText(Composite comp, String toolTipText) {
			getChangeControl(comp).setToolTipText(toolTipText);
		}
	};

	// enable if there's ever a need for this ...private IWorkbench m_workbench;
	private BooleanFieldEditor2 m_autoLaunchDE;

	private IntegerFieldEditor m_deTimeout;

	private Composite m_deTimeout_parent;

	private BooleanFieldEditor2 m_showRTTI;

	private BooleanFieldEditor2 m_notStepInRuntimeCode;

	private Composite m_arraySize_parent;

	private IntegerFieldEditor m_arraySize;

	private IntegerFieldEditor m_osViewRefreshInterval;

	// We don't use this plugin's preference store for global settings;
	// we use the core CW plugin. That way, the core plugin code can
	// access the prefs without having a build-time cross dependency
	// on this plugin
	//
	private final CWPlugin m_corePlugin = CWPlugin.getDefault();

	private PreferenceStore m_prefStore = new PreferenceStore(m_corePlugin.getPluginPreferences());

	private BooleanFieldEditor2 m_findSourceOutsideSDK;

	/**
	 * Constructor
	 */
	public GlobalSettings() {
		super();
		setPreferenceStore(m_prefStore);

		/* Shown in the panel, right under the panel name */
		setDescription(DebuggerUIMessages.GlobalSettings_debug_settings); 
	}

	private void updateControlState() {
		m_deTimeout.setEnabled(m_autoLaunchDE.getBooleanValue(), m_deTimeout_parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		// The main composite
		Composite composite = new Composite(parent, SWT.NULL);

		GridLayout layout = new GridLayout();

		layout.numColumns = 1;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite.setLayout(layout);
		GridData gridData = new GridData();

		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalAlignment = GridData.FILL;
		composite.setLayoutData(gridData);

		createSpacer(composite, 1);

		Composite icomp = null; // we use this variable for intermediate

		// composites (

		// checkbox: show dynamic runtime type ?
		icomp = new Composite(composite, SWT.NULL);
		gridData = new GridData(SWT.HORIZONTAL);
		gridData.horizontalSpan = 2;
		icomp.setLayoutData(gridData);
		m_showRTTI = new BooleanFieldEditor2(PreferenceConstants.J_PN_ShowRuntimeType,
				DebuggerUIMessages.GlobalSettings_attempt_show_dynamic, icomp); 
		m_showRTTI.setPage(this);
		m_showRTTI.setPreferenceStore(m_prefStore);
		m_showRTTI.load();
		m_showRTTI.setPropertyChangeListener(this);
		m_showRTTI
				.setToolTipText(
						icomp,
						DebuggerUIMessages.GlobalSettings_rtti_tooltip);

		// checkbox: do not step into runtime code ?
		icomp = new Composite(composite, SWT.NULL);
		gridData = new GridData(SWT.HORIZONTAL);
		gridData.horizontalSpan = 2;
		icomp.setLayoutData(gridData);
		m_notStepInRuntimeCode = new BooleanFieldEditor2(PreferenceConstants.J_PN_NotStepInRuntimeCode,
				DebuggerUIMessages.GlobalSettings_do_not_step_into_rt_support, icomp); 
		m_notStepInRuntimeCode.setPage(this);
		m_notStepInRuntimeCode.setPreferenceStore(m_prefStore);
		m_notStepInRuntimeCode.load();
		m_notStepInRuntimeCode.setPropertyChangeListener(this);
		m_notStepInRuntimeCode.setToolTipText(icomp,
				DebuggerUIMessages.GlobalSettings_step_in_runtime_tooltip);

		// entry field: default size for unbounded arrays
		m_arraySize_parent = new Composite(composite, SWT.NULL);
		m_arraySize = new IntegerFieldEditor(PreferenceConstants.J_PN_DefaultArraySize,
				DebuggerUIMessages.GlobalSettings_default_size_for_unbounded_arrays, m_arraySize_parent); 
		m_arraySize.setPage(this);
		m_arraySize.setPreferenceStore(m_prefStore);
		m_arraySize.load();
		m_arraySize.setPropertyChangeListener(this);
		gridData = new GridData();
		gridData.widthHint = convertWidthInCharsToPixels(8);
		m_arraySize.getTextControl(m_arraySize_parent).setLayoutData(gridData);
		m_arraySize.setValidateStrategy(StringFieldEditor.VALIDATE_ON_KEY_STROKE);
		m_arraySize.setValidRange(0, Integer.MAX_VALUE);
		m_arraySize.setErrorMessage(DebuggerUIMessages.GlobalSettings_invalid_array_size); 
		m_arraySize
				.getLabelControl(m_arraySize_parent)
				.setToolTipText(
						DebuggerUIMessages.GlobalSettings_default_array_tooltip);
		m_arraySize.load();

		// checkbox: show dynamic runtime type ?
		icomp = new Composite(composite, SWT.NULL);
		gridData = new GridData(SWT.HORIZONTAL);
		gridData.horizontalSpan = 2;
		icomp.setLayoutData(gridData);
		m_findSourceOutsideSDK = new BooleanFieldEditor2(CWPlugin.PSC_FindSourceOutsideWorkspace,
				DebuggerUIMessages.GlobalSettings_findOutside, icomp); 
		m_findSourceOutsideSDK.setPage(this);
		m_findSourceOutsideSDK.setPreferenceStore(m_prefStore);
		m_findSourceOutsideSDK.load();
		m_findSourceOutsideSDK.setPropertyChangeListener(this);
		m_findSourceOutsideSDK
				.setToolTipText(
						icomp,
						DebuggerUIMessages.GlobalSettings_foundOutsideTooltip);

		// entry field: interval for auto-refreshing of data in OS View.
		icomp = new Composite(composite, SWT.NULL);
		m_osViewRefreshInterval = new IntegerFieldEditor(
				PreferenceConstants.J_PN_OSViewAutoRefreshInterval,
				DebuggerUIMessages.GlobalSettings_refresh_time_interval,
				icomp);
		m_osViewRefreshInterval.setPage(this);
		m_osViewRefreshInterval.setPreferenceStore(m_prefStore);
		m_osViewRefreshInterval.setPropertyChangeListener(this);
		gridData = new GridData();
		gridData.widthHint = convertWidthInCharsToPixels(8);
		m_osViewRefreshInterval.getTextControl(icomp).setLayoutData(gridData);
		m_osViewRefreshInterval
				.setValidateStrategy(StringFieldEditor.VALIDATE_ON_KEY_STROKE);
		m_osViewRefreshInterval.setValidRange(3, 600);
		m_osViewRefreshInterval
				.setErrorMessage(DebuggerUIMessages.GlobalSettings_invalid_time_interval);
		m_osViewRefreshInterval
				.getLabelControl(icomp)
				.setToolTipText(
						DebuggerUIMessages.GlobalSettings_refresh_interval);
		m_osViewRefreshInterval.load();

		Group group = ControlFactory.createGroup(composite, DebuggerUIMessages.GlobalSettings_debug_engine, 2); 

		// Hide the DE launch settings in a production build (unless overriden with a property) 
		group.setVisible(m_corePlugin.isDebugEnvironment() || System.getProperty("cw.showDeGlobalSettings") != null); // $NON-NLS-1$ //$NON-NLS-1$

		// Auto Launch DE checkbox
		icomp = new Composite(group, SWT.NULL);
		gridData = new GridData(SWT.HORIZONTAL);
		gridData.horizontalSpan = 2;
		icomp.setLayoutData(gridData);
		m_autoLaunchDE = new BooleanFieldEditor2(PreferenceConstants.J_PN_AutoLaunchDE,
				DebuggerUIMessages.GlobalSettings_automatic_launch_de_server, icomp); 
		m_autoLaunchDE.setPage(this);
		m_autoLaunchDE.setPreferenceStore(m_prefStore);
		m_autoLaunchDE.load();
		m_autoLaunchDE.setPropertyChangeListener(this);
		m_autoLaunchDE
				.setToolTipText(icomp,
						"Launch the Debugger Engine, otherwise assume it is already running."); //$NON-NLS-1$

		// DE Launch Timeout text edit field
		m_deTimeout_parent = new Composite(group, SWT.NULL);
		m_deTimeout = new IntegerFieldEditor(PreferenceConstants.J_PN_DELaunchTimeout,
				DebuggerUIMessages.GlobalSettings_timeout_secs, m_deTimeout_parent);
		m_deTimeout.setPage(this);
		m_deTimeout.setPreferenceStore(m_prefStore);
		m_deTimeout.load();
		m_deTimeout.setPropertyChangeListener(this);
		gridData = new GridData();
		gridData.widthHint = convertWidthInCharsToPixels(8);
		m_deTimeout.getTextControl(m_deTimeout_parent).setLayoutData(gridData);
		m_deTimeout.setValidateStrategy(StringFieldEditor.VALIDATE_ON_KEY_STROKE);
		m_deTimeout.setValidRange(0, Integer.MAX_VALUE);
		m_deTimeout.setErrorMessage(DebuggerUIMessages.GlobalSettings_invalid_timeout_range); 
		m_deTimeout.load();

		updateControlState();

		String helpContextID = CWDebugUIPlugin.getPluginId() + "." + contextHelpID; //$NON-NLS-1$	
		PlatformUI.getWorkbench().getHelpSystem().setHelp(super.getControl(), helpContextID);

		return composite;
	}

	/**
	 * @see IPreferencePage#performOk()
	 */
	public boolean performOk() {
		boolean result = super.performOk();

		storeValues();
		m_corePlugin.savePluginPreferences();
		return result;
	}

	/**
	 * Sets the default preferences.
	 * 
	 * @see PreferencePage#performDefaults()
	 */
	protected void performDefaults() {
		setDefaultValues();
		super.performDefaults();
	}

	private void setDefaultValues() {
		m_autoLaunchDE.loadDefault();
		m_deTimeout.loadDefault();
		m_showRTTI.loadDefault();
		m_notStepInRuntimeCode.loadDefault();
		m_arraySize.loadDefault();
		m_findSourceOutsideSDK.loadDefault();
		m_osViewRefreshInterval.loadDefault();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(IWorkbench)
	 */
	public void init(IWorkbench workbench) {// enable if there's ever a need for this ...
		// m_workbench = workbench;
	}

	protected void createSpacer(Composite composite, int span) {
		Label label = new Label(composite, SWT.NONE);
		GridData gd = new GridData();

		gd.horizontalSpan = span;
		label.setLayoutData(gd);
	}

	/**
	 * Store the preference values based on the state of the component widgets
	 */
	private void storeValues() {
		m_autoLaunchDE.store();
		m_deTimeout.store();
		m_showRTTI.store();
		m_notStepInRuntimeCode.store();
		m_arraySize.store();
		m_findSourceOutsideSDK.store();
		m_osViewRefreshInterval.store();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#dispose()
	 */
	public void dispose() {
		m_autoLaunchDE.dispose();
		m_deTimeout.dispose();
		m_showRTTI.dispose();
		m_notStepInRuntimeCode.dispose();
		m_arraySize.dispose();
		m_findSourceOutsideSDK.dispose();
		m_osViewRefreshInterval.dispose();
		super.dispose();
	}

	/**
	 * @see org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		// gets called when any of the controls are hit/modified
		updateControlState();
	}
}
