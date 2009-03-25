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
package com.nokia.carbide.cpp.internal.project.ui.editors.images;

import com.nokia.carbide.cdt.builder.EMMPPathContext;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.dialogs.ChooseFileComposite;
import com.nokia.cpp.internal.api.utils.core.Pair;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class AIFOutputFileParametersComposite extends Composite {

	private AIFEditorContext editorContext;
	private Text targetFileText;
	private Button defaultTargetFileButton;
	private IMMPAIFInfo aifInfo;
	AIFEditorListPage page;
	private ChooseFileComposite resourceChooser;
	
	/**
	 * @param parent
	 * @param style
	 * @param editor 
	 */
	public AIFOutputFileParametersComposite(Composite parent, int style,
			final AIFEditorListPage page) {
		super(parent, style);
		this.page = page;
		this.editorContext = page.getEditorContext();
		this.aifInfo = editorContext.getMMPAIFInfo();
		
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		this.setLayout(gridLayout);

		final Label targetFileLabel = new Label(this, SWT.NONE);
		targetFileLabel.setLayoutData(new GridData());
		targetFileLabel.setText(Messages.getString("AIFOutputFileParametersComposite.TargetFileLabel")); //$NON-NLS-1$

		targetFileText = new Text(this, SWT.BORDER);
		targetFileText.setToolTipText(Messages.getString("AIFOutputFileParametersComposite.TargetFileTooltip")); //$NON-NLS-1$
		targetFileText.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		targetFileText.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
			}

			public void focusLost(FocusEvent e) {
				AbstractMultiImageEditorOperation operation = 
					new AbstractMultiImageEditorOperation<IPath>(
							editorContext, 
							Messages.getString("OutputFileParametersComposite.ChangeTargetFileCommand"), //$NON-NLS-1$
							emptyIfNull(aifInfo.getTarget()),
							new Path(targetFileText.getText())) {
					@Override
					protected void update(IPath value) {
						if (value.segmentCount() == 0) {
							aifInfo.setTarget(null);
						} else {
							aifInfo.setTarget(value);
						}
						refresh();
					}
				};
				
				editorContext.pushAndExecute(operation);
			}
			
		});
		
		defaultTargetFileButton = new Button(this, SWT.NONE);
		defaultTargetFileButton.setToolTipText(Messages.getString("OutputFileParametersComposite.SetDefaultFileFileHelp")); //$NON-NLS-1$
		defaultTargetFileButton.setText(Messages.getString("AIFOutputFileParametersComposite.DefaultButtonLabel")); //$NON-NLS-1$
		defaultTargetFileButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				String baseName = "project"; //$NON-NLS-1$
				IProject project = AIFOutputFileParametersComposite.this.editorContext.getProject();
				if (project != null)
					baseName = project.getName();
				IPath oldPath = aifInfo.getTarget();
				String newFilename = baseName + ".aif"; //$NON-NLS-1$
				
				IPath targetPath = new Path(""); //$NON-NLS-1$
				if (editorContext.isEKA2()) {
					targetPath = new Path("resource\\apps"); //$NON-NLS-1$
				}
				IPath newPath = targetPath.append(newFilename);
				
				AbstractMultiImageEditorOperation operation = 
					new AbstractMultiImageEditorOperation<IPath>(
							editorContext, 
							Messages.getString("OutputFileParametersComposite.SetDefaultTargetFileCommand"), //$NON-NLS-1$
							oldPath,
							newPath) {
					@Override
					protected void update(IPath value) {
						aifInfo.setTarget(value);
						refresh();
					}
				};
				
				editorContext.pushAndExecute(operation);
			}
			
		});

		resourceChooser = new ChooseFileComposite(
				this, SWT.NONE,
				emptyIfNull(aifInfo.getResource()),
				EMMPPathContext.AIF_SOURCE,
				editorContext.getMMPViewPathHelper(),
				editorContext.getBuildContext(),
				editorContext.getRSSFiles(),
				new String[] { "*.rss" }, //$NON-NLS-1$
				Messages.getString("AIFOutputFileParametersComposite.ResourceFilePathLabel")); //$NON-NLS-1$
		resourceChooser.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
		resourceChooser.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent e) {
				updateResource();
			}
		});
	}
	
	/**
	 * 
	 */
	protected void updateResource() {
		IPath oldPath = aifInfo.getResource();
		IPath newPath = resourceChooser.currentPath();
		IPath oldTargetPath = aifInfo.getTarget();
		IPath newTargetPath = oldTargetPath == null ? 
				new Path(newPath.removeFileExtension().addFileExtension("aif").lastSegment())  //$NON-NLS-1$
				: oldTargetPath;
		
		AbstractMultiImageEditorOperation operation = 
			new AbstractMultiImageEditorOperation<Pair<IPath, IPath>>(
					editorContext, 
					Messages.getString("AIFOutputFileParametersComposite.ChangeAIFResourceCommand"), //$NON-NLS-1$
					new Pair<IPath, IPath>(oldPath, oldTargetPath),
					new Pair<IPath, IPath>(newPath, newTargetPath)) {
			@Override
			protected void update(Pair<IPath, IPath> value) {
				aifInfo.setResource(value.first);
				aifInfo.setTarget(value.second);
				refresh();
			}
		};
		
		editorContext.pushAndExecute(operation);
	}

	/**
	 */
	protected IPath emptyIfNull(IPath path) {
		return path != null ? path : new Path(""); //$NON-NLS-1$
	}

	public void refresh() {
		Shell shell = getShell();
		IPath targetFilePath = aifInfo.getTarget();
		if (shell != null) {
			shell.setText(targetFilePath != null && !targetFilePath.isEmpty() 
					? targetFilePath.toString() : Messages.getString("AIFOutputFileParametersComposite.UnnamedTargetFileStub")); //$NON-NLS-1$
		}
		targetFileText.setText(targetFilePath != null ? targetFilePath.toOSString() : ""); //$NON-NLS-1$
		resourceChooser.setFileViewerPath(emptyIfNull(aifInfo.getResource()));
	}

}
