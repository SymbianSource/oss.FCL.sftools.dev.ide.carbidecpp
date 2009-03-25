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
package com.nokia.carbide.cpp.internal.project.ui.sharedui;

import com.nokia.carbide.cpp.internal.project.ui.Messages;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIHelpIds;
import com.nokia.carbide.internal.api.templatewizard.ui.IWizardDataPage;

import org.eclipse.cdt.core.CConventions;
import org.eclipse.cdt.core.browser.IQualifiedTypeName;
import org.eclipse.cdt.core.browser.QualifiedTypeName;
import org.eclipse.cdt.internal.ui.dialogs.cpaths.FolderSelectionDialog;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class ClassNameAndLocationPage extends WizardPage implements IWizardDataPage {

	private Text classNameEdit;
	private Text sourceEdit;
	private Text headerEdit;
	private Button browseHeaderButton;
	private Button browseSourceButton;
	private Button useDefaultButton;
	private Button forceLowerCaseButton;
	private IProject project;
	
	private static final String TEMPLATE_VAR_CLASSNAME = "classname";  //$NON-NLS-1$
	private static final String TEMPLATE_VAR_SOURCE_FILE_NAME = "srcFileName";  //$NON-NLS-1$
	private static final String TEMPLATE_VAR_INC_FILE_NAME = "incFileName";  //$NON-NLS-1$
	private static final String TEMPLATE_VAR_SRC_DIR = "sourceDir";  //$NON-NLS-1$
	private static final String TEMPLATE_VAR_INC_DIR = "incDir";  //$NON-NLS-1$
	private static final String FORCE_LOWERCASE = "forceLowercase"; //$NON-NLS-1$
	
	/**
	 * Create the wizard page
	 */
	public ClassNameAndLocationPage(String title, String description) {
		super(Messages.getString("ClassNameAndLocationPage.Name")); //$NON-NLS-1$
		setTitle(title);
		setDescription(description);
	}


	@SuppressWarnings("unchecked")
	public Map<String, Object> getPageValues() {
		Map data = new HashMap();	
		IPath srcDir = new Path(sourceEdit.getText());
		IPath incDir = new Path(headerEdit.getText());
		data.put(TEMPLATE_VAR_CLASSNAME, (Object) classNameEdit.getText());
		
		data.put(TEMPLATE_VAR_SOURCE_FILE_NAME, (Object) srcDir.lastSegment());
		data.put(TEMPLATE_VAR_INC_FILE_NAME, (Object) incDir.lastSegment());
		
		// remove the project name from the text...
		srcDir = srcDir.removeFirstSegments(1);
		srcDir = srcDir.removeLastSegments(1);
		
		incDir = incDir.removeFirstSegments(1);
		incDir = incDir.removeLastSegments(1);
		
		data.put(TEMPLATE_VAR_SRC_DIR, (Object) srcDir.toOSString());
		data.put(TEMPLATE_VAR_INC_DIR, (Object) incDir.toOSString());
		
		getDialogSettings().put(FORCE_LOWERCASE, forceLowerCaseButton.getSelection());

		return data;
	}

	public void createControl(Composite parent) {
		
		Composite container = new Composite(parent, SWT.NULL);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.marginTop = 10;
		gridLayout.marginRight = 10;
		gridLayout.marginLeft = 10;
		gridLayout.marginBottom = 10;
		gridLayout.horizontalSpacing = 10;
		gridLayout.numColumns = 4;
		container.setLayout(gridLayout);
		getProject();
		final Label classNameLabel = new Label(container, SWT.NONE);
		final GridData gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		classNameLabel.setLayoutData(gridData);
		classNameLabel.setText(Messages.getString("ClassNameAndLocationPage.ClassNameLabel")); //$NON-NLS-1$
		
		classNameEdit = new Text(container, SWT.BORDER);
		final GridData gd_classNameEdit = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1);
		gd_classNameEdit.widthHint = 403;
		classNameEdit.setLayoutData(gd_classNameEdit);
		classNameEdit.setToolTipText(Messages.getString("ClassNameAndLocationPage.ClassName_ToolTip"));	//$NON-NLS-1$
		classNameEdit.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (useDefaultButton.getSelection()) {
					setDefaultHeaderAndSourceName(classNameEdit.getText());
				}
				setPageComplete(validatePage());
			}
		});
		
		useDefaultButton = new Button(container, SWT.CHECK);
		useDefaultButton.setText(Messages.getString("ClassNameAndLocationPage.UseDefault")); //$NON-NLS-1$
		useDefaultButton.setToolTipText(Messages.getString("ClassNameAndLocationPage.UseDefault_Tooltip"));	//$NON-NLS-1$
		addButtonListener(useDefaultButton);
		
		final Label headerLabel = new Label(container, SWT.NONE);
		headerLabel.setText(Messages.getString("ClassNameAndLocationPage.Header"));	//$NON-NLS-1$
		
		headerEdit = new Text(container, SWT.BORDER);
		headerEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		headerEdit.setToolTipText(Messages.getString("ClassNameAndLocationPage.Header_Tooltip"));	//$NON-NLS-1$
		headerEdit.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				Point selection = headerEdit.getSelection();
				setHeaderPath(new Path(headerEdit.getText()));
				headerEdit.setSelection(selection);
				setPageComplete(validatePage());
			}
		});
		
		browseHeaderButton = new Button(container, SWT.NONE);
		browseHeaderButton.setText(Messages.getString("ClassNameAndLocationPage.Browse"));	//$NON-NLS-1$
		addButtonListener(browseHeaderButton);
		
		new Label(container, SWT.NONE); // filler for grid

		final Label sourceLabel = new Label(container, SWT.NONE);
		sourceLabel.setText(Messages.getString("ClassNameAndLocationPage.Source"));	//$NON-NLS-1$
		
		sourceEdit = new Text(container, SWT.BORDER);
		final GridData gd_sourceEdit = new GridData(SWT.FILL, SWT.CENTER, true, false);
		sourceEdit.setLayoutData(gd_sourceEdit);
		sourceEdit.setToolTipText(Messages.getString("ClassNameAndLocationPage.Source_Tooltip"));	//$NON-NLS-1$
		sourceEdit.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				Point selection = sourceEdit.getSelection();
				setSourcePath(new Path(sourceEdit.getText()));
				sourceEdit.setSelection(selection);
				setPageComplete(validatePage());
			}
		});
		
		browseSourceButton = new Button(container, SWT.NONE);
		browseSourceButton.setText(Messages.getString("ClassNameAndLocationPage.Browse"));	//$NON-NLS-1$
		addButtonListener(browseSourceButton);
		
		forceLowerCaseButton = new Button(container, SWT.CHECK);
		forceLowerCaseButton.setText(Messages.getString("ClassNameAndLocationPage.ForceLowerCaseLabel")); //$NON-NLS-1$
		forceLowerCaseButton.setToolTipText(Messages.getString("ClassNameAndLocationPage.ForceLowerCaseTooltip")); //$NON-NLS-1$
		forceLowerCaseButton.setSelection(getDialogSettings().getBoolean(FORCE_LOWERCASE));
		addButtonListener(forceLowerCaseButton);
		
		setControl(container);
		initDefaults();
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), ProjectUIHelpIds.CLASS_LOCATION_PAGE);

	}
	
	private void initDefaults(){
		if (useDefaultButton != null){   // test to see if controls are inited. other wizard page may change the project causing defaults to be re-inited.
			String defaultClassName ="ClassName";	//$NON-NLS-1$
			useDefaultButton.setSelection(true);
			classNameEdit.setText(defaultClassName);
			setUseDefaultControlsEnabled(false);
			setDefaultHeaderAndSourceName(defaultClassName);
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
				if (e.getSource().equals(browseSourceButton)) {
					IPath srcPath = chooseSourceFile();
					if (srcPath != null){
						setSourcePath(srcPath);
					}
				} else if (e.getSource().equals(browseHeaderButton)) {
					IPath headerPath = chooseHeaderFile();
					if (headerPath != null){
						setHeaderPath(headerPath);
					}
				}  else if (e.getSource().equals(useDefaultButton)){
					setUseDefaultControlsEnabled(!useDefaultButton.getSelection());
					if (useDefaultButton.getSelection()){
						setDefaultHeaderAndSourceName(classNameEdit.getText());
					}
				} else if (e.getSource().equals(forceLowerCaseButton)) {
					if (forceLowerCaseButton.getSelection()) {
						setHeaderPath(new Path(headerEdit.getText()));
						setSourcePath(new Path(sourceEdit.getText()));
					}
					else if (useDefaultButton.getSelection()) {
						setDefaultHeaderAndSourceName(classNameEdit.getText());
					}
				}
			}
		};
		aButton.addSelectionListener(listener);
	}
	
	public IProject getProject(){
		if (project == null){
			for (IWizardPage page : this.getWizard().getPages()){
				if (page instanceof ChooseProjectPage){
					ChooseProjectPage cpp = (ChooseProjectPage)page;
					project = cpp.getProject();
				}
			}
		}
		
		return project;
	}
		
	@SuppressWarnings("restriction")
	private IPath chooseHeaderFile() {

		ILabelProvider lp = new WorkbenchLabelProvider();
		ITreeContentProvider cp = new WorkbenchContentProvider();
		FolderSelectionDialog dialog = new FolderSelectionDialog(getShell(), lp, cp);
		dialog.setAllowMultiple(false);
		dialog.setTitle(Messages.getString("ClassNameAndLocationPage.Choose_Header_File_Location")); //$NON-NLS-1$
		dialog.setMessage(MessageFormat.format(Messages.getString("ClassNameAndLocationPage.Available_folders"), project.getName())); //$NON-NLS-1$

		dialog.setInput(project);
		dialog.setInitialSelection(project);
		if (dialog.open() == Window.OK) {
			IResource res = (IResource) dialog.getFirstResult();
			return res.getFullPath();
		}
		return null;

	}

	@SuppressWarnings("restriction")
	private IPath chooseSourceFile() {
		IProject project = getProject();
		ILabelProvider lp = new WorkbenchLabelProvider();
		ITreeContentProvider cp = new WorkbenchContentProvider();
		FolderSelectionDialog dialog = new FolderSelectionDialog(getShell(),lp, cp);
		dialog.setAllowMultiple(false);
		dialog.setTitle(Messages.getString("ClassNameAndLocationPage.Choose_Source_File_Location")); //$NON-NLS-1$
		dialog.setMessage(MessageFormat.format(Messages.getString("ClassNameAndLocationPage.Available_folders"), project.getName())); //$NON-NLS-1$

		dialog.setInput(project);
		dialog.setInitialSelection(project);
		if (dialog.open() == Window.OK) {
			IResource res = (IResource) dialog.getFirstResult();
			return res.getFullPath();
		}
		return null;
	}   
	
	private void setHeaderPath(IPath headerPath){
		setPath(headerEdit, headerPath);
	}

	private void setSourcePath(IPath sourcePath){
		setPath(sourceEdit, sourcePath);
	}
	
	private void setPath(Text text, IPath path) {
		path = path.makeRelative();
		String filename = path.lastSegment();
		if (filename != null) {
			if (forceLowerCaseButton.getSelection()) {
				filename = filename.toLowerCase();
				path = path.removeLastSegments(1).append(filename);
			}
		}
		String pathStr = path.toPortableString();
		if (!text.getText().equals(pathStr))
			text.setText(pathStr);
	}
	
	private void setUseDefaultControlsEnabled(boolean isEnabled){
		browseHeaderButton.setEnabled(isEnabled);
		browseSourceButton.setEnabled(isEnabled);
		sourceEdit.setEnabled(isEnabled);
		headerEdit.setEnabled(isEnabled);
	}


	public void setProject(IProject project){
		this.project = project;
		// if the project has changed we need to reset the default values
		initDefaults();
	}

	private boolean validatePage(){
		
		if (getProject() == null){
			return false; // project not seleted yet.
		}
		
		boolean isValid = true;
		setMessage(null);
		String className = classNameEdit.getText();
		
		if (className.length() == 0){
			setErrorMessage(Messages.getString("ClassNameAndLocationPage.Please_enter_a_class_name")); //$NON-NLS-1$
			return false;
		}
		
		 IQualifiedTypeName typeName = new QualifiedTypeName(className);
	        if (typeName.isQualified()) {
	        	setErrorMessage(Messages.getString("ClassNameAndLocationPage.Class_name_qualified")); //$NON-NLS-1$
	            return false;
	        }
	    
			IStatus val = CConventions.validateClassName(className);
			if (val.getSeverity() == IStatus.ERROR) {
				setErrorMessage(Messages.getString("ClassNameAndLocationPage.Class_name_not_valid") + val.getMessage()); //$NON-NLS-1$
				return false;
			} else if (val.getSeverity() == IStatus.WARNING) {
				setErrorMessage(Messages.getString("ClassNameAndLocationPage.Class_name_discouraged") + val.getMessage()); //$NON-NLS-1$
				// continue checking
			}
			
			String headerStr = headerEdit.getText();
			String srcStr =    sourceEdit.getText();
			// check for file exist. you should not be able to overwrite existing files
			IPath projectHeaderPath = project.getLocation().removeLastSegments(1);
			projectHeaderPath = projectHeaderPath.append(headerStr);
			if (projectHeaderPath.toFile().isFile() && projectHeaderPath.toFile().exists()){
				setErrorMessage(Messages.getString("ClassNameAndLocationPage.Header_Exists"));	//$NON-NLS-1$
				return false;
			}
			
			IPath projectSrcPath = project.getLocation().removeLastSegments(1);
			projectSrcPath = projectSrcPath.append(srcStr);
			if (projectSrcPath.toFile().isFile() && projectSrcPath.toFile().exists()){
				setErrorMessage(Messages.getString("ClassNameAndLocationPage.Source_Exists")); //$NON-NLS-1$
				return false;
			}
			
			// check header and source name
			if (!headerStr.toLowerCase().endsWith(".hpp") && !headerStr.toLowerCase().endsWith(".h") && //$NON-NLS-1$ //$NON-NLS-2$
				!headerStr.toLowerCase().endsWith(".i")   && !headerStr.toLowerCase().endsWith(".ii")){ //$NON-NLS-1$ //$NON-NLS-2$
				setMessage(Messages.getString("ClassNameAndLocationPage.Header_Name_Discouraged"), 2);	//$NON-NLS-1$
			}
			
			if (!srcStr.toLowerCase().endsWith(".cpp") && !srcStr.toLowerCase().endsWith(".c") && //$NON-NLS-1$ //$NON-NLS-2$
					!srcStr.toLowerCase().endsWith(".cxx")   && !srcStr.toLowerCase().endsWith(".cp")){ //$NON-NLS-1$ //$NON-NLS-2$
					setMessage(Messages.getString("ClassNameAndLocationPage.Source_Name_Discouraged"), 2);	//$NON-NLS-1$
			}
			
			// check that first segment is the project name
			String projectName = getProject().getName();
			if (!headerStr.startsWith(projectName)){
				setErrorMessage(Messages.getString("ClassNameAndLocationPage.Bad_Project_Path_Header"));	//$NON-NLS-1$
				return false;
			}
			
			if (!srcStr.startsWith(projectName)){
				setErrorMessage(Messages.getString("ClassNameAndLocationPage.Bad_Project_Path_Source"));	//$NON-NLS-1$
				return false;
			}
			

		if (isValid){
			setErrorMessage(null);
		}
		
		return isValid;
	}
	
	private void setDefaultHeaderAndSourceName(String className){
		IProject project = getProject();
		if (project != null){
			setHeaderPath(new Path(project.getName() + "/inc/" + className + ".h")); //$NON-NLS-1$ //$NON-NLS-2$ 
			setSourcePath(new Path(project.getName() + "/src/" + className + ".cpp")); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}
	
}
