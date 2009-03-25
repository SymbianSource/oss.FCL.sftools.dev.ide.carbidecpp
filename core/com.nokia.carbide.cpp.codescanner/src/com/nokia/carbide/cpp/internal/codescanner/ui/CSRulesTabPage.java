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

package com.nokia.carbide.cpp.internal.codescanner.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.nokia.carbide.cpp.internal.codescanner.CSPlugin;
import com.nokia.carbide.cpp.internal.codescanner.Messages;
import com.nokia.carbide.cpp.internal.codescanner.config.CSCategory;
import com.nokia.carbide.cpp.internal.codescanner.config.CSConfigSettings;
import com.nokia.carbide.cpp.internal.codescanner.config.CSRule;
import com.nokia.carbide.cpp.internal.codescanner.config.CSScript;
import com.nokia.carbide.cpp.internal.codescanner.config.CSSeverity;

/**
 * A class to create and control the CodeScanner Rules tab page.
 */
public class CSRulesTabPage extends Composite {

	/**
	 * Inner class to handle labels of the rules table.
	 */
	private class CSRulesLabelProvider extends LabelProvider implements
	ITableLabelProvider {

		public String getColumnText(Object element, int index) {
			CSRule rule = (CSRule) element;
			switch (index) {
				case 0:
					return rule.getScript().toString();
				case 1:
					return rule.getCategory().toString();
				case 2:
					return rule.getSeverity().toString();
				default:
					return "unknown " + index;
			}
		}
	
		public Image getColumnImage(Object element, int index) {
			return null;
		}
	}

	// private members for various controls of this preference page
	private Group tableGroup = null;
	private Label detailsLabel = null;
	private CheckboxTableViewer rulesTableViewer = null;
	private Button editButton = null;
	private Button enableAllButton = null;
	private Button disableAllButton = null;
	private String cclassIgnore = "";
	private String lfunctionIgnore = "";
	private String forbiddenWords = "";
	private String openIgnore = "";
	private String worryingComments = "";
	private int longLinesLength = 0;
	private CSConfigSettings defaultConfigSettings = null;
	private List<CSRule> rules = null;
	private CSRulesSorter rulesSorter = null;

	/**
	 * Create contents of this tab page.
	 * @param parent - the parent composite
	 */
	public CSRulesTabPage(Composite parent) {
		super(parent, SWT.NONE);
		defaultConfigSettings = CSPlugin.getConfigManager().getDefaultConfig();
		rules = new ArrayList<CSRule>();
		
		this.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		this.setLayout(gridLayout);

		// table for CodeScanner rules
		tableGroup = new Group(this, SWT.NONE);
		final GridData tableGridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		tableGridData.heightHint = 250;
		tableGridData.widthHint = 200;
		tableGroup.setText(Messages.getString("RulesTabPage.RulesTableMessage"));
		tableGroup.setLayoutData(tableGridData);
		tableGroup.setLayout(new GridLayout());
		
		rulesTableViewer = CheckboxTableViewer.newCheckList(tableGroup, SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION);
		rulesTableViewer.setLabelProvider(new CSRulesLabelProvider());
		rulesTableViewer.setContentProvider(new ArrayContentProvider());
		rulesSorter = new CSRulesSorter();
		rulesTableViewer.setSorter(rulesSorter);
		rulesTableViewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				CSRule rule = (CSRule)event.getElement();
				rule.setEnabled(event.getChecked());
			}
		});
		rulesTableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				handleEdit();
			}
		});

		Table rulesTable = rulesTableViewer.getTable();
		rulesTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		rulesTable.setHeaderVisible(true);
		rulesTable.setLinesVisible(true);
		
		// create the columns of the table
		TableColumn tableColumn1 = new TableColumn(rulesTable, SWT.LEFT);
		tableColumn1.setText(Messages.getString("RulesTabPage.RulesTableRulesLabel"));
		tableColumn1.setWidth(170);
		tableColumn1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				rulesSorter.setSortingType(CSRulesSorter.SORT_BY_RULE);
				rulesSorter.sort(rulesTableViewer, rules.toArray());
				rulesTableViewer.setInput(rules.toArray());
				setCheckedElements();
			}
		});

		TableColumn tableColumn2 = new TableColumn(rulesTable, SWT.LEFT);
		tableColumn2.setText(Messages.getString("RulesTabPage.RulesTableCategoriesLabel"));
		tableColumn2.setWidth(80);
		tableColumn2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				rulesSorter.setSortingType(CSRulesSorter.SORT_BY_CATEGORY);
				rulesSorter.sort(rulesTableViewer, rules.toArray());
				rulesTableViewer.setInput(rules.toArray());
				setCheckedElements();
			}
		});

		TableColumn tableColumn3 = new TableColumn(rulesTable, SWT.LEFT);
		tableColumn3.setText(Messages.getString("RulesTabPage.RulesTableSeveritiesLabel"));
		tableColumn3.setWidth(65);
		tableColumn3.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				rulesSorter.setSortingType(CSRulesSorter.SORT_BY_SEVERITY);
				rulesSorter.sort(rulesTableViewer, rules.toArray());
				rulesTableViewer.setInput(rules.toArray());
				setCheckedElements();
			}
		});
		
		// various buttons for manipulating the rules
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		final GridLayout buttonGridLayout = new GridLayout();
		buttonGridLayout.makeColumnsEqualWidth = true;
		composite.setLayout(buttonGridLayout);
		final GridData buttonsGridData = new GridData(SWT.NONE, SWT.NONE, true, false);
		buttonsGridData.widthHint = 80;

		editButton = new Button(composite, SWT.NONE);
		editButton.setLayoutData(buttonsGridData);
		editButton.setText(Messages.getString("RulesTabPage.EditRuleLabel"));
		editButton.setToolTipText(Messages.getString("RulesTabPage.EditRuleMessage"));
		editButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				handleEdit();
			}
		});
		editButton.setEnabled(false);
		
		enableAllButton = new Button(composite, SWT.NONE);
		enableAllButton.setLayoutData(buttonsGridData);
		enableAllButton.setText(Messages.getString("RulesTabPage.EnableAllRulesLabel"));
		enableAllButton.setToolTipText(Messages.getString("RulesTabPage.EnableAllRulesMessage"));
		enableAllButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				handleEnableAll();
			}
		});

		disableAllButton = new Button(composite, SWT.NONE);
		disableAllButton.setLayoutData(buttonsGridData);
		disableAllButton.setText(Messages.getString("RulesTabPage.DisableAllRulesLabel"));
		disableAllButton.setToolTipText(Messages.getString("RulesTabPage.DisableAllRulesMessage"));
		disableAllButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				handleDisableAll();
			}
		});

		// enable the Edit and Details buttons only when a single rule is selected
		rulesTable.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				boolean enable = (rulesTableViewer.getTable().getSelectionCount() == 1);
				editButton.setEnabled(enable);
				handleDetails();
			}
		});

		// label to display detail info of a selected rule
		Group detailsGroup = new Group(this, SWT.NONE);
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.heightHint = 60;
		gridData.widthHint = 200;
		detailsGroup.setText(Messages.getString("RulesTabPage.DetailsLabel"));
		detailsGroup.setLayoutData(gridData);
		detailsGroup.setLayout(new GridLayout());

		detailsLabel = new Label(detailsGroup, SWT.WRAP);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.heightHint = 55;
		gridData.widthHint = 200;
		detailsLabel.setLayoutData(gridData);
		detailsLabel.setText("");
	}

	/**
	 * Enables the receiver if the argument is true, and disables it otherwise.
	 * @param enabled - the new enable state.
	 */
	public void setEnabled(boolean enabled) {
		boolean ruleSelected = (rulesTableViewer.getTable().getSelectionCount() == 1);
		tableGroup.setEnabled(enabled);
		rulesTableViewer.getTable().setEnabled(enabled);
		editButton.setEnabled(enabled && ruleSelected);
		enableAllButton.setEnabled(enabled);
		disableAllButton.setEnabled(enabled);
		detailsLabel.setEnabled(enabled);
	}

	/**
	 * Set the default values of this tab page.
	 */
	public void setDefaults() {
		rules.clear();
		for (CSScript script : CSScript.values()) {
			if (!script.equals(CSScript.script_unknown)) {
				String categoryString = "category_" + defaultConfigSettings.getScriptCategory(script);
				CSCategory category = CSCategory.toCategory(categoryString);
				String severityString = "severity_" + defaultConfigSettings.getScriptSeverity(script);
				CSSeverity severity = CSSeverity.toSeverity(severityString);
				boolean enabled = defaultConfigSettings.getScriptEnabled(script);
				CSRule rule = new CSRule(script, category, severity, enabled);
				rules.add(rule);
			}
		}
		rulesTableViewer.setInput(rules.toArray());
		setCheckedElements();
		cclassIgnore = defaultConfigSettings.getScriptCClassIgnore();
		longLinesLength = defaultConfigSettings.getScriptLongLinesLength();
		forbiddenWords = defaultConfigSettings.getScriptForbiddenWords();
		openIgnore = defaultConfigSettings.getScriptOpenIgnore();
		worryingComments = defaultConfigSettings.getScriptWorryingComments();
	}

	/**
	 * Retrieve the stored preference settings values of this tab page.
	 */
	public void getStoredPreferenceValues() {
		IPreferenceStore store = CSPlugin.getCSPrefsStore();

		// retrieve the stored tokenized strings
		String scriptsString = store.getString(CSPreferenceConstants.RULE_SCRIPTS);
		if (scriptsString == null)
			return;
		StringTokenizer scriptTokens = new StringTokenizer(scriptsString, CSPreferenceConstants.DELIMETER);	
		String categoriesString = store.getString(CSPreferenceConstants.RULE_CATEGORIES);
		if (categoriesString == null)
			return;
		StringTokenizer categoryTokens = new StringTokenizer(categoriesString, CSPreferenceConstants.DELIMETER);
		String severitiesString = store.getString(CSPreferenceConstants.RULE_SEVERITIES);
		if (severitiesString == null)
			return;
		StringTokenizer severityTokens = new StringTokenizer(severitiesString, CSPreferenceConstants.DELIMETER);
		String enabledString = store.getString(CSPreferenceConstants.RULES_ENABLED);
		if (enabledString == null)
			return;
		StringTokenizer enabledTokens = new StringTokenizer(enabledString, CSPreferenceConstants.DELIMETER);

		// populate the rules table with the stored values
		int index = 0;
		rules.clear();
		while (scriptTokens.hasMoreTokens() &&
			   categoryTokens.hasMoreTokens() &&
			   severityTokens.hasMoreTokens() &&
			   enabledTokens.hasMoreTokens()) {
			String scriptString = "script_" + scriptTokens.nextToken();
			CSScript script = CSScript.toScript(scriptString);
			String categoryString = "category_" + categoryTokens.nextToken();
			CSCategory category = CSCategory.toCategory(categoryString);
			String severityString = "severity_" + severityTokens.nextToken();
			CSSeverity severity = CSSeverity.toSeverity(severityString);
			int value = Integer.valueOf(enabledTokens.nextToken());
			boolean enabled = (value != 0);
			if (!script.equals(CSScript.script_unknown) &&
				!category.equals(CSCategory.category_unknown) &&
				!severity.equals(CSSeverity.severity_unknown)) {
				CSRule rule = new CSRule(script, category, severity, enabled);
				rules.add(rule);
				index++;
			}
		}
		rulesTableViewer.setInput(rules.toArray());
		setCheckedElements();

		// retrieve other stored values
		cclassIgnore = store.getString(CSPreferenceConstants.CCLASSIGNORE);
		forbiddenWords = store.getString(CSPreferenceConstants.FORBIDEENWORDS);
		lfunctionIgnore = store.getString(CSPreferenceConstants.LFUNCTIONIGNORE);
		longLinesLength = store.getInt(CSPreferenceConstants.LONGLINES_LENGTH);
		openIgnore = store.getString(CSPreferenceConstants.OPENIGNORE);
		worryingComments = store.getString(CSPreferenceConstants.WORRYINGCOMMENTS);	
	}
	
	/**
	 * Retrieve the stored property settings values of this tab page.
	 */
	public void getStoredPropertyValues(IDialogSettings pageSettings) {
		// retrieve the stored tokenized strings
		String scriptsString = pageSettings.get(CSPreferenceConstants.RULE_SCRIPTS);
		if (scriptsString == null)
			return;
		StringTokenizer scriptTokens = new StringTokenizer(scriptsString, CSPreferenceConstants.DELIMETER);	
		String categoriesString = pageSettings.get(CSPreferenceConstants.RULE_CATEGORIES);
		if (categoriesString == null)
			return;
		StringTokenizer categoryTokens = new StringTokenizer(categoriesString, CSPreferenceConstants.DELIMETER);
		String severitiesString = pageSettings.get(CSPreferenceConstants.RULE_SEVERITIES);
		if (severitiesString == null)
			return;
		StringTokenizer severityTokens = new StringTokenizer(severitiesString, CSPreferenceConstants.DELIMETER);
		String enabledString = pageSettings.get(CSPreferenceConstants.RULES_ENABLED);
		if (enabledString == null)
			return;
		StringTokenizer enabledTokens = new StringTokenizer(enabledString, CSPreferenceConstants.DELIMETER);

		// populate the rules table with the stored values
		int index = 0;
		rules.clear();
		while (scriptTokens.hasMoreTokens() &&
			   categoryTokens.hasMoreTokens() &&
			   severityTokens.hasMoreTokens() &&
			   enabledTokens.hasMoreTokens()) {
			String scriptString = "script_" + scriptTokens.nextToken();
			CSScript script = CSScript.toScript(scriptString);
			String categoryString = "category_" + categoryTokens.nextToken();
			CSCategory category = CSCategory.toCategory(categoryString);
			String severityString = "severity_" + severityTokens.nextToken();
			CSSeverity severity = CSSeverity.toSeverity(severityString);
			int value = Integer.valueOf(enabledTokens.nextToken());
			boolean enabled = (value != 0);
			if (!script.equals(CSScript.script_unknown) &&
				!category.equals(CSCategory.category_unknown) &&
				!severity.equals(CSSeverity.severity_unknown)) {
				CSRule rule = new CSRule(script, category, severity, enabled);
				rules.add(rule);
				index++;
			}
		}
		rulesTableViewer.setInput(rules.toArray());
		setCheckedElements();

		// retrieve other stored values
		cclassIgnore = pageSettings.get(CSPreferenceConstants.CCLASSIGNORE);
		forbiddenWords = pageSettings.get(CSPreferenceConstants.FORBIDEENWORDS);
		lfunctionIgnore = pageSettings.get(CSPreferenceConstants.LFUNCTIONIGNORE);
		longLinesLength = pageSettings.getInt(CSPreferenceConstants.LONGLINES_LENGTH);
		openIgnore = pageSettings.get(CSPreferenceConstants.OPENIGNORE);
		worryingComments = pageSettings.get(CSPreferenceConstants.WORRYINGCOMMENTS);	
	}

	/**
	 * Set the stored preference settings values of this tab page.
	 */
	public boolean setStoredPreferenceValues(){
		IPreferenceStore store = CSPlugin.getCSPrefsStore();
		String scriptString = "";
		String categoryString = "";
		String severityString = "";
		String enabledString = "";
		int total = rules.toArray().length;

		// create tokenized strings from the content of the rules table
		for (int i = 0; i < total; i++) {
			CSRule rule = (CSRule)rules.toArray()[i];
			if (rule != null) {
				scriptString += rule.getScript().toString() + CSPreferenceConstants.DELIMETER;
				categoryString += rule.getCategory().toString() + CSPreferenceConstants.DELIMETER;
				severityString += rule.getSeverity().toString() + CSPreferenceConstants.DELIMETER;
				int flag = 1;
				if (!rulesTableViewer.getChecked(rule))
					flag = 0;
				enabledString += flag + CSPreferenceConstants.DELIMETER;
			}
		}

		// store the tokenized strings
		store.setValue(CSPreferenceConstants.RULE_SCRIPTS, scriptString);
		store.setValue(CSPreferenceConstants.RULE_CATEGORIES, categoryString);
		store.setValue(CSPreferenceConstants.RULE_SEVERITIES, severityString);
		store.setValue(CSPreferenceConstants.RULES_ENABLED, enabledString);

		// store other values
		store.setValue(CSPreferenceConstants.CCLASSIGNORE, cclassIgnore);
		store.setValue(CSPreferenceConstants.FORBIDEENWORDS, forbiddenWords);
		store.setValue(CSPreferenceConstants.LFUNCTIONIGNORE, lfunctionIgnore);
		store.setValue(CSPreferenceConstants.LONGLINES_LENGTH, longLinesLength);
		store.setValue(CSPreferenceConstants.OPENIGNORE, openIgnore);
		store.setValue(CSPreferenceConstants.WORRYINGCOMMENTS, worryingComments);
		return true;
	}

	/**
	 * Set the stored property settings values of this tab page.
	 */
	public boolean setStoredPropertyValues(IDialogSettings pageSettings) {
		String scriptString = "";
		String categoryString = "";
		String severityString = "";
		String enabledString = "";
		int total = rules.toArray().length;

		// create tokenized strings from the content of the rules table
		for (int i = 0; i < total; i++) {
			CSRule rule = (CSRule)rules.toArray()[i];
			if (rule != null) {
				scriptString += rule.getScript().toString() + CSPreferenceConstants.DELIMETER;
				categoryString += rule.getCategory().toString() + CSPreferenceConstants.DELIMETER;
				severityString += rule.getSeverity().toString() + CSPreferenceConstants.DELIMETER;
				int flag = 1;
				if (!rulesTableViewer.getChecked(rule))
					flag = 0;
				enabledString += flag + CSPreferenceConstants.DELIMETER;
			}
		}

		// store the tokenized strings
		pageSettings.put(CSPreferenceConstants.RULE_SCRIPTS, scriptString);
		pageSettings.put(CSPreferenceConstants.RULE_CATEGORIES, categoryString);
		pageSettings.put(CSPreferenceConstants.RULE_SEVERITIES, severityString);
		pageSettings.put(CSPreferenceConstants.RULES_ENABLED, enabledString);

		// store other values
		pageSettings.put(CSPreferenceConstants.CCLASSIGNORE, cclassIgnore);
		pageSettings.put(CSPreferenceConstants.FORBIDEENWORDS, forbiddenWords);
		pageSettings.put(CSPreferenceConstants.LFUNCTIONIGNORE, lfunctionIgnore);
		pageSettings.put(CSPreferenceConstants.LONGLINES_LENGTH, longLinesLength);
		pageSettings.put(CSPreferenceConstants.OPENIGNORE, openIgnore);
		pageSettings.put(CSPreferenceConstants.WORRYINGCOMMENTS, worryingComments);
		return true;
	}

	/**
	 * Initialize the stored preference settings values of this tab page.
	 */
	public static void initializePreferenceValues() {
		IPreferenceStore store = CSPlugin.getCSPrefsStore();
		CSConfigSettings configSettings = CSPlugin.getConfigManager().getDefaultConfig();
		String scriptString = "";
		String categoryString = "";
		String severityString = "";
		String enabledString = "";
		int flag = 1;
		for (CSScript script : CSScript.values()) {
			if (!script.equals(CSScript.script_unknown)) {
				scriptString += script.toString() + CSPreferenceConstants.DELIMETER;
				categoryString += configSettings.getScriptCategory(script) + CSPreferenceConstants.DELIMETER;
				severityString += configSettings.getScriptSeverity(script) + CSPreferenceConstants.DELIMETER;
				if (configSettings.getScriptEnabled(script)) {
					flag = 1;
				}
				else {
					flag = 0;
				}
				enabledString += flag + CSPreferenceConstants.DELIMETER;
			}
		}			
		store.setDefault(CSPreferenceConstants.RULE_SCRIPTS, scriptString);
		store.setDefault(CSPreferenceConstants.RULE_CATEGORIES, categoryString);
		store.setDefault(CSPreferenceConstants.RULE_SEVERITIES, severityString);
		store.setDefault(CSPreferenceConstants.RULES_ENABLED, enabledString);
		store.setDefault(CSPreferenceConstants.CCLASSIGNORE, configSettings.getScriptCClassIgnore());
		store.setDefault(CSPreferenceConstants.FORBIDEENWORDS, configSettings.getScriptForbiddenWords());
		store.setDefault(CSPreferenceConstants.LFUNCTIONIGNORE, configSettings.getScriptLFunctionIgnore());
		store.setDefault(CSPreferenceConstants.LONGLINES_LENGTH, configSettings.getScriptLongLinesLength());
		store.setDefault(CSPreferenceConstants.OPENIGNORE, configSettings.getScriptOpenIgnore());
		store.setDefault(CSPreferenceConstants.WORRYINGCOMMENTS, configSettings.getScriptWorryingComments());		
	}

	/**
	 * Initialize the stored property settings values of this tab page.
	 */
	public static void initializePropertyValues(IDialogSettings pageSettings) {
		CSConfigSettings configSettings = CSPlugin.getConfigManager().getDefaultConfig();
		String scriptString = "";
		String categoryString = "";
		String severityString = "";
		String enabledString = "";
		int flag = 1;
		for (CSScript script : CSScript.values()) {
			if (!script.equals(CSScript.script_unknown)) {
				scriptString += script.toString() + CSPreferenceConstants.DELIMETER;
				categoryString += configSettings.getScriptCategory(script) + CSPreferenceConstants.DELIMETER;
				severityString += configSettings.getScriptSeverity(script) + CSPreferenceConstants.DELIMETER;
				if (configSettings.getScriptEnabled(script)) {
					flag = 1;
				}
				else {
					flag = 0;
				}
				enabledString += flag + CSPreferenceConstants.DELIMETER;
			}
		}			
		pageSettings.put(CSPreferenceConstants.RULE_SCRIPTS, scriptString);
		pageSettings.put(CSPreferenceConstants.RULE_CATEGORIES, categoryString);
		pageSettings.put(CSPreferenceConstants.RULE_SEVERITIES, severityString);
		pageSettings.put(CSPreferenceConstants.RULES_ENABLED, enabledString);
		pageSettings.put(CSPreferenceConstants.CCLASSIGNORE, configSettings.getScriptCClassIgnore());
		pageSettings.put(CSPreferenceConstants.FORBIDEENWORDS, configSettings.getScriptForbiddenWords());
		pageSettings.put(CSPreferenceConstants.LFUNCTIONIGNORE, configSettings.getScriptLFunctionIgnore());
		pageSettings.put(CSPreferenceConstants.LONGLINES_LENGTH, configSettings.getScriptLongLinesLength());
		pageSettings.put(CSPreferenceConstants.OPENIGNORE, configSettings.getScriptOpenIgnore());
		pageSettings.put(CSPreferenceConstants.WORRYINGCOMMENTS, configSettings.getScriptWorryingComments());
	}

	/**
	 * Things to do when user hit the "Edit" button.
	 */
	private void handleEdit() {
		ISelection selection = rulesTableViewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			Object element = ss.getFirstElement();
			if (element instanceof CSRule) {
				CSRule rule = (CSRule)element;
				if (rule != null) {
					boolean hasExtra = false;
					String extraAttrTitle = "";
					String extraAttr = "";

					CSScript script = rule.getScript();
					if (script.equals(CSScript.script_longlines)) {
						hasExtra = true;
						extraAttrTitle = Messages.getString("RulesTabPage.EditLongLinesLabel");
						extraAttr = longLinesLength + "";
					}
					else
					if (script.equals(CSScript.script_forbiddenwords)) {
						hasExtra = true;
						extraAttrTitle = Messages.getString("RulesTabPage.EditForbiddenWordsLabel");
						if (forbiddenWords != null) {
							extraAttr = forbiddenWords;
						}
					}
					else
					if (script.equals(CSScript.script_LFunctionCantLeave)) {
						hasExtra = true;
						extraAttrTitle = Messages.getString("RulesTabPage.EditLFunctionCantLeaveLabel");
						if (lfunctionIgnore != null) {
							extraAttr = lfunctionIgnore;
						}
					}
					else
					if (script.equals(CSScript.script_missingcclass)) {
						hasExtra = true;
						extraAttrTitle = Messages.getString("RulesTabPage.EditMissingCClassLabel");
						if (cclassIgnore != null) {
							extraAttr = cclassIgnore;
						}
					}
					else
					if (script.equals(CSScript.script_open)) {
						hasExtra = true;
						extraAttrTitle = Messages.getString("RulesTabPage.EditOpenLabel");
						if (openIgnore != null) {
							extraAttr = openIgnore;
						}
					}
					else
					if (script.equals(CSScript.script_worryingcomments)) {
						hasExtra = true;
						extraAttrTitle = Messages.getString("RulesTabPage.EditWorryingCommentsLabel");
						if (worryingComments != null) {
							extraAttr = worryingComments;
						}
					}
					CSRulesEditDialog dialog = new CSRulesEditDialog(getShell(), 
																	 script,
																	 rule.getCategory(),
																	 rule.getSeverity(),
																	 hasExtra,
																	 extraAttrTitle,
																	 extraAttr);
					if (dialog.open() == CSRulesEditDialog.OK) {
						CSCategory newCategory = dialog.getCategory();
						if (!newCategory.equals(rule.getCategory())) {
							rule.setCategory(newCategory);
						}
						CSSeverity newSeverity = dialog.getSeverity();
						if (!newSeverity.equals(rule.getSeverity())) {
							rule.setSeverity(newSeverity);
						}
						String newExtraAttr = dialog.getExtra();
						if (dialog.hasExtra() && !newExtraAttr.equals(extraAttr)) {
							if (script.equals(CSScript.script_forbiddenwords)) {
								forbiddenWords = newExtraAttr;
							}
							else
							if (script.equals(CSScript.script_LFunctionCantLeave)) {
								lfunctionIgnore = newExtraAttr;
							}
							else
							if (script.equals(CSScript.script_longlines)) {
								longLinesLength = Integer.valueOf(newExtraAttr);
							}
							else
							if (script.equals(CSScript.script_missingcclass)) {
								cclassIgnore = newExtraAttr;
							}
							else
							if (script.equals(CSScript.script_open)) {
								openIgnore = newExtraAttr;
							}
							else
							if (script.equals(CSScript.script_worryingcomments)) {
								worryingComments = newExtraAttr;
							}
						}
					}
					rulesTableViewer.refresh(true);
				}
			}
		}
	}

	/**
	 * Things to do when user hit the "Details" button.
	 */
	private void handleDetails() {
		ISelection selection = rulesTableViewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			Object element = ss.getFirstElement();
			if (element instanceof CSRule) {
				CSRule rule = (CSRule)element;
				if (rule != null) {
					detailsLabel.setText(rule.getDetails());
				}
				rulesTableViewer.refresh(true);
			}
		}
	}

	/**
	 * Things to do when user hit the "EnableAll" button.
	 */
	private void handleEnableAll() {
		rulesTableViewer.setAllChecked(true);
	}

	/**
	 * Things to do when user hit the "DisableAll" button.
	 */
	private void handleDisableAll() {
		rulesTableViewer.setAllChecked(false);
	}

	/**
	 * Set the checked state of the rules table elements.
	 */
	private void setCheckedElements() {
		for (int i = 0; i < rules.toArray().length; i++) {
			CSRule rule = (CSRule)rules.toArray()[i];
			rulesTableViewer.setChecked(rule, rule.getEnabled());
		}
	}

}
