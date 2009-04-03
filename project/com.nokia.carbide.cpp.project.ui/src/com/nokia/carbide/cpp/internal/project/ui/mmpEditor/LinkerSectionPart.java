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
package com.nokia.carbide.cpp.internal.project.ui.mmpEditor;

import java.util.ArrayList;
import java.util.List;

import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.ControlHandler;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.ControlManager;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.ControlHandler.IValidator;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.commands.ChangeDefFileOperation;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.dialogs.Utilities;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.nokia.carbide.cdt.builder.EMMPPathContext;
import com.nokia.carbide.cdt.builder.InvalidDriveInMMPPathException;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.sdt.utils.ProjectFileResourceProxyVisitor;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.cpp.internal.api.utils.ui.editor.FormEditorEditingContext;

public class LinkerSectionPart extends SectionPart {

	private Label win32BaseAddressLabel;
	private Label linkAsInternalLabel;
	private final MMPEditorContext editorContext;
	private final ControlManager controlManager;
	private Button compressTargetExecutableButton;
	private Button noStrictdefButton;
	private Button dontExportLibraryButton;
	private Button exportUnfrozenButton;
	private ComboViewer defFileViewer;
	private Text baseAddrText;
	private Text linkAsText;
	
	static final String DEF_EXTENSION = "def"; //$NON-NLS-1$
	
	/**
	 * Create the SectionPart
	 * @param parent
	 * @param toolkit
	 * @param style
	 */
	public LinkerSectionPart(Composite parent, FormToolkit toolkit, int style, 
			MMPEditorContext editorContext, ControlManager controlManager) {
		super(parent, toolkit, style);
		this.editorContext = editorContext;
		this.controlManager = controlManager;
		createClient(getSection(), toolkit);
	}
	

	/**
	 * Fill the section
	 */
	private void createClient(Section section, FormToolkit toolkit) {
		section.setText(Messages.LinkerSectionPart_title);
		Composite container = toolkit.createComposite(section);
		toolkit.paintBordersFor(container);
		final FormLayout formLayout = new FormLayout();
		formLayout.marginHeight = 2;
		formLayout.marginWidth = 2;
		container.setLayout(formLayout);
		//
		section.setClient(container);

		final Label linkerDefinitionFileLabel = toolkit.createLabel(container, Messages.LinkerSectionPart_defFileLabel, SWT.NONE);
		final FormData formData_10 = new FormData();
		formData_10.left = new FormAttachment(0, 16);
		formData_10.right = new FormAttachment(0, 200);
		formData_10.top = new FormAttachment(0, 5);
		linkerDefinitionFileLabel.setLayoutData(formData_10);

		defFileViewer = new ComboViewer(container, SWT.READ_ONLY|SWT.FLAT);
		defFileViewer.setContentProvider(new ArrayContentProvider());
		defFileViewer.setLabelProvider(new DefFileLabelProvider());
		defFileViewer.setInput(getDefFiles());
		
		Combo defFileCombo = defFileViewer.getCombo();
		defFileCombo.setToolTipText(Messages.LinkerSectionPart_defFileTooltip);
		final FormData formData_8 = new FormData();
		formData_8.left = new FormAttachment(linkerDefinitionFileLabel, 0, SWT.LEFT);
		formData_8.right = new FormAttachment(0, 200);
		formData_8.top = new FormAttachment(0, 24);
		formData_8.bottom = new FormAttachment(0, 45);
		defFileCombo.setLayoutData(formData_8);
		toolkit.adapt(defFileCombo, true, true);

		exportUnfrozenButton = toolkit.createButton(container, Messages.LinkerSectionPart_exportUnfrozenBtn, SWT.CHECK);
		exportUnfrozenButton.setToolTipText(Messages.LinkerSectionPart_exportUnfrozenTooltip);
		final FormData formData = new FormData();
		formData.bottom = new FormAttachment(defFileCombo, 21, SWT.BOTTOM);
		formData.top = new FormAttachment(defFileCombo, 5, SWT.BOTTOM);
		formData.right = new FormAttachment(defFileCombo, 0, SWT.RIGHT);
		formData.left = new FormAttachment(0, 15);
		exportUnfrozenButton.setLayoutData(formData);

		dontExportLibraryButton = toolkit.createButton(container, Messages.LinkerSectionPart_dontExportBtn, SWT.CHECK);
		dontExportLibraryButton.setToolTipText(Messages.LinkerSectionPart_dontExportTooltip);
		final FormData formData_1 = new FormData();
		formData_1.bottom = new FormAttachment(exportUnfrozenButton, 21, SWT.BOTTOM);
		formData_1.top = new FormAttachment(exportUnfrozenButton, 5, SWT.BOTTOM);
		formData_1.right = new FormAttachment(exportUnfrozenButton, 0, SWT.RIGHT);
		formData_1.left = new FormAttachment(exportUnfrozenButton, 0, SWT.LEFT);
		dontExportLibraryButton.setLayoutData(formData_1);

		noStrictdefButton = toolkit.createButton(container, Messages.LinkerSectionPart_noStrictDefBtn, SWT.CHECK);
		noStrictdefButton.setToolTipText(Messages.LinkerSectionPart_noStrictDefTooltip);
		final FormData formData_2 = new FormData();
		formData_2.bottom = new FormAttachment(dontExportLibraryButton, 21, SWT.BOTTOM);
		formData_2.top = new FormAttachment(dontExportLibraryButton, 5, SWT.BOTTOM);
		formData_2.right = new FormAttachment(dontExportLibraryButton, 0, SWT.RIGHT);
		formData_2.left = new FormAttachment(dontExportLibraryButton, 0, SWT.LEFT);
		noStrictdefButton.setLayoutData(formData_2);

		compressTargetExecutableButton = toolkit.createButton(container, Messages.LinkerSectionPart_compressExeBtn, SWT.CHECK);
		compressTargetExecutableButton.setToolTipText(Messages.LinkerSectionPart_compressTargetTooltip);
		final FormData formData_3 = new FormData();
		formData_3.bottom = new FormAttachment(noStrictdefButton, 21, SWT.BOTTOM);
		formData_3.top = new FormAttachment(noStrictdefButton, 5, SWT.BOTTOM);
		formData_3.right = new FormAttachment(noStrictdefButton, 0, SWT.RIGHT);
		formData_3.left = new FormAttachment(noStrictdefButton, 0, SWT.LEFT);
		compressTargetExecutableButton.setLayoutData(formData_3);

		linkAsInternalLabel = toolkit.createLabel(container, Messages.LinkerSectionPart_linkAsLabel, SWT.NONE);
		final FormData formData_4 = new FormData();
		formData_4.bottom = new FormAttachment(compressTargetExecutableButton, 18, SWT.BOTTOM);
		formData_4.top = new FormAttachment(compressTargetExecutableButton, 5, SWT.BOTTOM);
		formData_4.right = new FormAttachment(compressTargetExecutableButton, 0, SWT.RIGHT);
		formData_4.left = new FormAttachment(compressTargetExecutableButton, 0, SWT.LEFT);
		linkAsInternalLabel.setLayoutData(formData_4);

		linkAsText = toolkit.createText(container, null, SWT.NONE);
		linkAsText.setToolTipText(Messages.LinkerSectionPart_linkAsTooltip);
		final FormData formData_5 = new FormData();
		formData_5.top = new FormAttachment(linkAsInternalLabel, 5, SWT.BOTTOM);
		formData_5.right = new FormAttachment(linkAsInternalLabel, 0, SWT.RIGHT);
		formData_5.left = new FormAttachment(linkAsInternalLabel, 0, SWT.LEFT);
		linkAsText.setLayoutData(formData_5);

		win32BaseAddressLabel = toolkit.createLabel(container, Messages.LinkerSectionPart_win32BaseAddrLabel, SWT.NONE);
		formData_10.right = new FormAttachment(win32BaseAddressLabel, 0, SWT.RIGHT);
		final FormData formData_6 = new FormData();
		formData_6.top = new FormAttachment(0, 175);
		formData_6.right = new FormAttachment(linkAsText, 0, SWT.RIGHT);
		formData_6.left = new FormAttachment(linkAsText, 0, SWT.LEFT);
		win32BaseAddressLabel.setLayoutData(formData_6);

		baseAddrText = toolkit.createText(container, null, SWT.NONE);
		baseAddrText.setToolTipText(Messages.LinkerSectionPart_baseAddrTooltip);
		final FormData formData_7 = new FormData();
		formData_7.top = new FormAttachment(0, 192);
		formData_7.right = new FormAttachment(win32BaseAddressLabel, 0, SWT.RIGHT);
		formData_7.left = new FormAttachment(win32BaseAddressLabel, 0, SWT.LEFT);
		baseAddrText.setLayoutData(formData_7);

		Button browseButton;
		browseButton = toolkit.createButton(container, Messages.LinkerSectionPart_browseBtn, SWT.NONE);
		final FormData formData_9 = new FormData();
		formData_9.top = new FormAttachment(defFileCombo, -23, SWT.BOTTOM);
		formData_9.bottom = new FormAttachment(defFileCombo, 0, SWT.BOTTOM);
		formData_9.left = new FormAttachment(defFileCombo, 5, SWT.RIGHT);
		browseButton.setLayoutData(formData_9);
		browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browseForDefFile();
			}
		});
		
		hookControls();
		refresh();
	}
	
	protected void browseForDefFile() {
		FileDialog dialog = new FileDialog(getSection().getShell(), SWT.OPEN);
		
		// browse to current file so user can see what 'default' is
		IPath curDefFile = editorContext.mmpView.getDefFile();
		if (curDefFile != null) {
			curDefFile = editorContext.pathHelper.convertMMPToFilesystem(EMMPPathContext.DEFFILE, curDefFile);
			if (!curDefFile.hasTrailingSeparator()) {
				dialog.setFileName(curDefFile.lastSegment());
			}
			while (curDefFile.segmentCount() > 0 && !curDefFile.toFile().exists()) {
				curDefFile = curDefFile.removeLastSegments(1);
			}
			dialog.setFilterPath(curDefFile.toOSString());
		}
		
		dialog.setFilterNames(new String[] {Messages.LinkerSectionPart_defFileBrowseFilterName});
		dialog.setFilterExtensions(new String[] {Messages.LinkerSectionPart_defFileBrowseFilter});
		String selectedPath = dialog.open();
		if (selectedPath != null && selectedPath.length() > 0) {
			IPath path = Utilities.validateandConfirmMMPPath(new Path(selectedPath), 
					editorContext.activeBuildConfig, EMMPPathContext.DEFFILE, 
					editorContext.pathHelper, getSection().getShell());
			if (path != null) {
				// TODO: add UI to let the user say "this is a fixed directory and don't strip out platform if present"
				makeDefFileOperation(path, false);
				ControlHandler.getHandlerForControl(defFileViewer.getControl()).refresh();
				ControlHandler.getHandlerForControl(noStrictdefButton).refresh();
			}
		}
	}

	private void hookControls() {
		DefFileControlHandler defFileControlHandler = new DefFileControlHandler();
		controlManager.add(defFileControlHandler);
		SingleSettingTextHandler handler = new SingleSettingTextHandler(baseAddrText,
				new FormEditorEditingContext(editorContext.editor, baseAddrText),
				new RegExInputValidator(RegExInputValidator.HEX, true, Messages.LinkerSectionPart_baseAddressValidationErr),
				EMMPStatement.BASEADDRESS, editorContext);
		handler.setLabel(win32BaseAddressLabel);
		controlManager.add(handler);
		controlManager.add(new SingleSettingTextHandler(linkAsText,
				new FormEditorEditingContext(editorContext.editor, linkAsText),
				null, EMMPStatement.LINKAS, editorContext));
		controlManager.add(new FlagSettingHandler(compressTargetExecutableButton,
				new FormEditorEditingContext(editorContext.editor, compressTargetExecutableButton),
				EMMPStatement.COMPRESSTARGET, editorContext));
		controlManager.add(new FlagSettingHandler(exportUnfrozenButton,
				new FormEditorEditingContext(editorContext.editor, exportUnfrozenButton),
				EMMPStatement.EXPORTUNFROZEN, editorContext));
		FlagSettingHandler noStrictDefSettingHandler = new FlagSettingHandler(noStrictdefButton,
						new FormEditorEditingContext(editorContext.editor, noStrictdefButton),
						EMMPStatement.NOSTRICTDEF, editorContext);
		controlManager.add(noStrictDefSettingHandler);
		controlManager.add(new FlagSettingHandler(dontExportLibraryButton,
				new FormEditorEditingContext(editorContext.editor, dontExportLibraryButton),
				EMMPStatement.NOEXPORTLIBRARY, editorContext));

		controlManager.addValidationDependency(noStrictDefSettingHandler, defFileControlHandler);
	}
	
	private List<Object> getDefFiles() {
		List<Object> result = new ArrayList<Object>();
		result.add(""); // for "use default" choice //$NON-NLS-1$
		ProjectFileResourceProxyVisitor visitor = new ProjectFileResourceProxyVisitor("def", true); //$NON-NLS-1$
		try {
			editorContext.project.accept(visitor, IResource.NONE);
			result.addAll(visitor.getRequestedFiles());
		} catch (CoreException x) {
			ProjectUIPlugin.log(x);
		}
		// ensure the def file currently referenced in the mmp is included
		IPath currDefFile = editorContext.mmpView.getDefFile();
		if (currDefFile != null) {
			IPath projRelative = editorContext.pathHelper.convertMMPToProject(EMMPPathContext.DEFFILE, currDefFile);
			if (projRelative != null) {
				if (!result.contains(projRelative)) {
					result.add(projRelative);
				}
			} else {
				result.add(currDefFile);
			}
		}
		return result;
	}
		
	private IPath currentDefFilePath() {
		IPath result = null;
		IStructuredSelection ss = (IStructuredSelection) defFileViewer.getSelection();
		Object element = ss.getFirstElement();
		if (element instanceof IPath) {
			result = (IPath) element;
			
		}
		return result;
	}
	
	private void makeDefFileOperation(IPath newPath, boolean isFixedDirectory) {
		ChangeDefFileOperation op = new ChangeDefFileOperation(
				editorContext.mmpView, 
				new FormEditorEditingContext(editorContext.editor, defFileViewer.getControl()),
				defFileViewer.getControl(), 
				noStrictdefButton,
				newPath,
				isFixedDirectory);
		editorContext.executeOperation(op);
	}
	
	static class DefFileLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			String result = null;
			if (element instanceof IPath) {
				result = element.toString();
			} else if ("".equals(element)) { //$NON-NLS-1$
				result = Messages.LinkerSectionPart_useDefaultDefFileLabel;
			} else {
				result = element.toString();
			}
			return result;
		}
	}

	class DefFileValidator implements IValidator {

		public IStatus validate(Control control) {
			IStatus result = null;
			String text = ControlHandler.getControlText(control);
			IPath defPath = new Path(text);
			if (TextUtils.strlen(text) > 0 && defPath.segmentCount() > 0 && !defPath.hasTrailingSeparator()) {
				// invalid if the file does not have a 'U' when it should 
				if (!editorContext.mmpView.getFlags().contains(EMMPStatement.NOSTRICTDEF)) {
					if (!defPath.removeFileExtension().lastSegment().toUpperCase().endsWith("U")) { //$NON-NLS-1$
						result = new Status(IStatus.ERROR, ProjectUIPlugin.PLUGIN_ID, 0, 
								Messages.LinkerSectionPart_InvalidDefFileNameForUnicodeError,
								null); 
					}
				}
			}
			return result;
		}
		
	}
	
	class DefFileControlHandler extends ControlHandler {

		public DefFileControlHandler() {
			super(defFileViewer.getControl(), defFileViewer, new DefFileValidator(), false);
			
			addListener(new ControlHandlerAdapter() {
				@Override
				public void valueModified(Control control) {
					makeDefFileOperation();
					ControlHandler.getHandlerForControl(noStrictdefButton).refresh();
				}
			});
		}
		
		private void makeDefFileOperation() {
			IPath newPath = currentDefFilePath();
			if (newPath != null) {
				try {
					newPath = editorContext.pathHelper.convertProjectOrFullPathToMMP(
							EMMPPathContext.DEFFILE, newPath);
				} catch (InvalidDriveInMMPPathException e) {
					// should not happen
					ProjectUIPlugin.log(e);
					newPath = e.getPathNoDevice();
				}
			}
			// TODO: add UI to let the user say "this is a fixed directory and don't strip out platform if present"
			LinkerSectionPart.this.makeDefFileOperation(newPath, false);
		}
						
		public void refresh() {
			whileUpdatingControl(new Runnable() {
				public void run() {
					defFileViewer.setInput(getDefFiles());
					IPath defFile = editorContext.mmpView.getDefFile();
					if (editorContext.mmpView.getSingleArgumentSettings().get(EMMPStatement.DEFFILE) == null) {
						defFile = null;
					}
					if (defFile != null) {
						IPath projRelative = editorContext.pathHelper.convertMMPToProject(EMMPPathContext.DEFFILE, defFile);
						if (projRelative != null) {
							defFile = projRelative;
						}
						defFileViewer.setSelection(new StructuredSelection(defFile));
					} else {
						defFileViewer.setSelection(new StructuredSelection("")); //$NON-NLS-1$
					}
				}
			});
			}
	}
}

