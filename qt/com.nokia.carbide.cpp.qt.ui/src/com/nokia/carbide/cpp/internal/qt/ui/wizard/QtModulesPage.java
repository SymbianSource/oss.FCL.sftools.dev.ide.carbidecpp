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
* Wizard page used to select a list of Carbide.c++ build configurations.  Currently used
* by the new project and import bld.inf wizards, but can be used by any wizard that needs
* to get a list of build configs.
*
*/
package com.nokia.carbide.cpp.internal.qt.ui.wizard;

import java.util.Collections;
import java.util.Map;

import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.PlatformUI;

import com.nokia.carbide.cpp.internal.qt.ui.QtUIHelpIds;
import com.nokia.carbide.internal.api.templatewizard.ui.IWizardDataPage;

public class QtModulesPage extends WizardPage implements IWizardDataPage {
	
	private static final String UID = ".uid"; //$NON-NLS-1$
	protected static final String MODULES_TEXT_VALUE_KEY = "modulesText"; //$NON-NLS-1$

	private static final String CORE = "core"; //$NON-NLS-1$
	private static final String GUI = "gui"; //$NON-NLS-1$
	private static final String SQL = "sql"; //$NON-NLS-1$
	private static final String XML = "xml"; //$NON-NLS-1$
	private static final String XMLPATTERNS = "xmlpatterns"; //$NON-NLS-1$
	private static final String NETWORK = "network"; //$NON-NLS-1$
	private static final String SVG = "svg"; //$NON-NLS-1$
	private static final String OPENGL = "opengl"; //$NON-NLS-1$
	private static final String WEBKIT = "webkit"; //$NON-NLS-1$
	private static final String SCRIPT = "script"; //$NON-NLS-1$
	private static final String PHONON = "phonon"; //$NON-NLS-1$

	private NewQtCppProjectWizard theWizard;
	
	private Button coreModule;
	private Button guiModule;
	private Button sqlModule;
	private Button xmlModule;
	private Button xmlPatternsModule;
	private Button networkModule;
	private Button svgModule;
	private Button openglModule;
	private Button webkitModule;
	private Button scriptModule;
	private Button phononModule;

	/**
	 * Default constructor
	 */
	public QtModulesPage(NewQtCppProjectWizard wizard) {
		super(Messages.QtModulesPage_QtModulesPageTitle);
		theWizard = wizard;
		setTitle(Messages.QtModulesPage_QtModulesPageTitle);
		setDescription(Messages.QtModulesPage_QtModulesPageDescription);
	}

	/**
	 * see {@link IDialogPage#createControl(Composite)}
	 */
	public void createControl(Composite parent) {
		setPageComplete(false);
		setErrorMessage(null);
		setMessage(null);

		initializeDialogUnits(parent);
        
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout());
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		createControls(composite);
		
		setControl(composite);
 
		getControl().setData(UID, "QtModulesPage"); //$NON-NLS-1$
		getControl().setData("WizardPage", this); //$NON-NLS-1$
	}

	private void createControls(Composite parent) {

		Group group = new Group(parent, SWT.NONE);
		group.setText(Messages.QtModulesPage_groupText);
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		coreModule = new Button(group, SWT.CHECK);
		coreModule.setText(Messages.QtModulesPage_coreModuleText);
		coreModule.setToolTipText(Messages.QtModulesPage_coreModuleToolTip);
		coreModule.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		coreModule.setSelection(true);
		coreModule.setEnabled(false);
		coreModule.setData(UID, "coreModule"); //$NON-NLS-1$

		guiModule = new Button(group, SWT.CHECK);
		guiModule.setText(Messages.QtModulesPage_guiModuleText);
		guiModule.setToolTipText(Messages.QtModulesPage_guiModuleToolTip);
		guiModule.setData(UID, "guiModule"); //$NON-NLS-1$
		if (theWizard.getSelectedTemplate().getGroupLabel().equals("Qt GUI")) {
			guiModule.setSelection(true);
			guiModule.setEnabled(false);
		}

		sqlModule = new Button(group, SWT.CHECK);
		sqlModule.setText(Messages.QtModulesPage_sqlModuleText);
		sqlModule.setToolTipText(Messages.QtModulesPage_sqlModuleToolTip);
		sqlModule.setData(UID, "sqlModule"); //$NON-NLS-1$

		xmlModule = new Button(group, SWT.CHECK);
		xmlModule.setText(Messages.QtModulesPage_xmlModuleText);
		xmlModule.setToolTipText(Messages.QtModulesPage_xmlModuleToolTip);
		xmlModule.setData(UID, "xmlModule"); //$NON-NLS-1$

		xmlPatternsModule = new Button(group, SWT.CHECK);
		xmlPatternsModule.setText(Messages.QtModulesPage_xmlPatternsModuleText);
		xmlPatternsModule.setToolTipText(Messages.QtModulesPage_xmlPatternsModuleToolTip);
		xmlPatternsModule.setData(UID, "xmlPatternsModule"); //$NON-NLS-1$

		networkModule = new Button(group, SWT.CHECK);
		networkModule.setText(Messages.QtModulesPage_networkModuleText);
		networkModule.setToolTipText(Messages.QtModulesPage_networkModuleToolTip);
		networkModule.setData(UID, "networkModule"); //$NON-NLS-1$

		svgModule = new Button(group, SWT.CHECK);
		svgModule.setText(Messages.QtModulesPage_svgModuleText);
		svgModule.setToolTipText(Messages.QtModulesPage_svgModuleToolTip);
		svgModule.setData(UID, "svgModule"); //$NON-NLS-1$

		openglModule = new Button(group, SWT.CHECK);
		openglModule.setText(Messages.QtModulesPage_openglModuleText);
		openglModule.setToolTipText(Messages.QtModulesPage_openglModuleToolTip);
		openglModule.setData(UID, "openglModule"); //$NON-NLS-1$

		webkitModule = new Button(group, SWT.CHECK);
		webkitModule.setText(Messages.QtModulesPage_webkitModuleText);
		webkitModule.setToolTipText(Messages.QtModulesPage_webkitModuleToolTip);
		webkitModule.setData(UID, "webkitModule"); //$NON-NLS-1$

		scriptModule = new Button(group, SWT.CHECK);
		scriptModule.setText(Messages.QtModulesPage_scriptModuleText);
		scriptModule.setToolTipText(Messages.QtModulesPage_scriptModuleToolTip);
		scriptModule.setData(UID, "scriptModule"); //$NON-NLS-1$

		phononModule = new Button(group, SWT.CHECK);
		phononModule.setText(Messages.QtModulesPage_phononModuleText);
		phononModule.setToolTipText(Messages.QtModulesPage_phononModuleToolTip);
		phononModule.setData(UID, "phononModule"); //$NON-NLS-1$

		setPageComplete(validatePage());
	}
	
	protected boolean validatePage() {
		return true;
	}
	
	/**
	 * Returns the list of build configs selected in this page to template wizards.
	 * @return the map of values for this page - 
	 * a singleton map containing the list of selected build configs mapped to the key "selectedBuildConfigs". 
	 */
	public Map<String, Object> getPageValues() {
		return Collections.singletonMap(MODULES_TEXT_VALUE_KEY, (Object)getSelectedModulesText());
	}

	private String getSelectedModulesText() {
		String text = CORE;
		final String filler = " \\\n    "; //$NON-NLS-1$
		
		if (guiModule.getSelection()) {
			text = text + filler + GUI;
		}
		
		if (sqlModule.getSelection()) {
			text = text + filler + SQL;
		}
		
		if (xmlModule.getSelection()) {
			text = text + filler + XML;
		}

		if (xmlPatternsModule.getSelection()) {
			text = text + filler + XMLPATTERNS;
		}

		if (networkModule.getSelection()) {
			text = text + filler + NETWORK;
		}

		if (svgModule.getSelection()) {
			text = text + filler + SVG;
		}

		if (openglModule.getSelection()) {
			text = text + filler + OPENGL;
		}

		if (webkitModule.getSelection()) {
			text = text + filler + WEBKIT;
		}

		if (webkitModule.getSelection()) {
			text = text + filler + WEBKIT;
		}

		if (scriptModule.getSelection()) {
			text = text + filler + SCRIPT;
		}

		if (phononModule.getSelection()) {
			text = text + filler + PHONON;
		}
		
		text = "QT        += " + text; //$NON-NLS-1$

		// have to explicitly remove gui since it's default
		if (!guiModule.getSelection()) {
			text = text + "\nQT        -= " + GUI;
		}

		return text;
	}
	
	@Override
	public void performHelp() {
        PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl().getShell(), QtUIHelpIds.QT_MODULES_PAGE);
	}
}
