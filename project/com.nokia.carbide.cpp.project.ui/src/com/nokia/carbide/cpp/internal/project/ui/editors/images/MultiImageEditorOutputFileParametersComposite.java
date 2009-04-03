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

import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.epoc.engine.model.EGeneratedHeaderFlags;
import com.nokia.cpp.internal.api.utils.core.Pair;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class MultiImageEditorOutputFileParametersComposite extends Composite {

	MultiImageEditorContext editorContext;
	private Text targetFileText;
	private Button defaultTargetFileButton;
	private Button defaultHeaderFileButton;
	private Text headerFileText;
	private Composite headerRadioGroup;
	private Button noHeaderButton;
	private Button headerButton;
	private Button headerOnlyButton;
	IMultiImageSource multiImageSource;
	MultiImageListPage page;
	
	/**
	 * @param parent
	 * @param style
	 * @param editor 
	 */
	public MultiImageEditorOutputFileParametersComposite(Composite parent, int style,
			final MultiImageListPage page) {
		super(parent, style);
		this.page = page;
		this.editorContext = page.getEditorContext();
		this.multiImageSource = editorContext.getMultiImageSource();
		
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		this.setLayout(gridLayout);

		final Label targetFileLabel = new Label(this, SWT.NONE);
		targetFileLabel.setLayoutData(new GridData());
		targetFileLabel.setText(Messages.getString("OutputFileParametersComposite.TargetFileLabel")); //$NON-NLS-1$

		targetFileText = new Text(this, SWT.BORDER);
		targetFileText.setToolTipText(Messages.getString("OutputFileParametersComposite.TargetFileTooltip")); //$NON-NLS-1$
		targetFileText.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		targetFileText.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
			}

			public void focusLost(FocusEvent e) {
				AbstractMultiImageEditorOperation operation = 
					new AbstractMultiImageEditorOperation<IPath>(
							editorContext, 
							Messages.getString("OutputFileParametersComposite.ChangeTargetFileCommand"), //$NON-NLS-1$
							emptyIfNull(multiImageSource.getTargetFilePath()),
							new Path(targetFileText.getText())) {
					@Override
					protected void update(IPath value) {
						if (value.segmentCount() == 0) {
							multiImageSource.setTargetPath(null);
							multiImageSource.setTargetFile(""); //$NON-NLS-1$
						} else {
							multiImageSource.setTargetPath(value.removeLastSegments(1));
							multiImageSource.setTargetFile(value.lastSegment());
						}
						refresh();
					}
				};
				
				editorContext.pushAndExecute(operation);
			}
			
		});
		
		defaultTargetFileButton = new Button(this, SWT.NONE);
		defaultTargetFileButton.setToolTipText(Messages.getString("OutputFileParametersComposite.SetDefaultFileFileHelp")); //$NON-NLS-1$
		defaultTargetFileButton.setText(Messages.getString("OutputFileParametersComposite.DefaultButton")); //$NON-NLS-1$
		defaultTargetFileButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				String baseName = "project"; //$NON-NLS-1$
				IProject project = MultiImageEditorOutputFileParametersComposite.this.editorContext.getProject();
				if (project != null)
					baseName = project.getName();
				String extension = MultiImageEditorOutputFileParametersComposite.this.editorContext.getGeneratedExtension();
				String oldFilename = multiImageSource.getTargetFile();
				String newFilename = baseName + "." + extension; //$NON-NLS-1$
				
				IPath targetPath = new Path(""); //$NON-NLS-1$
				if (editorContext.isEKA2()) {
					targetPath = new Path("resource\\apps"); //$NON-NLS-1$
				}
				IPath oldPath = multiImageSource.getTargetPath();
				IPath newPath = targetPath;
				
				AbstractMultiImageEditorOperation operation = 
					new AbstractMultiImageEditorOperation<Pair<IPath, String>>(
							editorContext, 
							Messages.getString("OutputFileParametersComposite.SetDefaultTargetFileCommand"), //$NON-NLS-1$
							new Pair(oldPath, oldFilename),
							new Pair(newPath, newFilename)) {
					@Override
					protected void update(Pair<IPath, String> value) {
						multiImageSource.setTargetPath(value.first);
						multiImageSource.setTargetFile(value.second);
						refresh();
					}
				};
				
				editorContext.pushAndExecute(operation);
			}
			
		});

		final Label headerFileLabel = new Label(this, SWT.NONE);
		headerFileLabel.setText(Messages.getString("OutputFileParametersComposite.HeaderFileLabel")); //$NON-NLS-1$

		headerFileText = new Text(this, SWT.BORDER);
		headerFileText.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		headerFileText.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
			}

			public void focusLost(FocusEvent e) {
				
				MultiImageListPageOperation operation = 
					new MultiImageListPageOperation(
							Messages.getString("OutputFileParametersComposite.SetGeneratedHeaderFileCommand"), page) { //$NON-NLS-1$

						IPath oldPath = multiImageSource.getGeneratedHeaderFilePath();
						IPath path = headerFileText.getText().trim().length() > 0 ? 
								new Path(headerFileText.getText()) : null;
						IPath defaultPath = multiImageSource.getDefaultGeneratedHeaderFilePath();
						EGeneratedHeaderFlags headerFlags = getGeneratedHeaderFlags();
						
						/* (non-Javadoc)
						 * @see com.nokia.carbide.cpp.internal.project.ui.editors.images.ImageEditorOperation#doesAnything()
						 */
						@Override
						protected boolean doesAnything() {
							if (oldPath == null) {
								if (path != null)
									if (!defaultPath.equals(path))
										return true;
									else
										return false;
								else
									return false;
							} else {
								if (path != null) {
									return true;
								} else {
									return false;
								}
							}
						}
						
						@Override
						protected void redo() {
							if (path.segmentCount() == 0) {
								multiImageSource.setGeneratedHeaderFilePath(null);
							} else if (path.equals(defaultPath)) {
								multiImageSource.setGeneratedHeaderFilePath(null);
								multiImageSource.setHeaderFlags(headerFlags);
							} else {
								if (oldPath == null || !oldPath.equals(path)) {
									multiImageSource.setGeneratedHeaderFilePath(path);
								}
							}
						}

						@Override
						protected void undo() {
							multiImageSource.setGeneratedHeaderFilePath(oldPath);
							multiImageSource.setHeaderFlags(headerFlags);
						}
				};
				
				editorContext.pushAndExecute(operation);
			}
			
		});

		defaultHeaderFileButton = new Button(this, SWT.NONE);
		defaultHeaderFileButton.setToolTipText(Messages.getString("OutputFileParametersComposite.SetDefaultHeaderFileHelp")); //$NON-NLS-1$
		defaultHeaderFileButton.setLayoutData(new GridData());
		defaultHeaderFileButton.setText(Messages.getString("OutputFileParametersComposite.DefaultButton")); //$NON-NLS-1$
		defaultHeaderFileButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				MultiImageListPageOperation operation = 
					new MultiImageListPageOperation(
							Messages.getString("OutputFileParametersComposite.SetDefaultGeneratedHeaderFileCommand"), page) { //$NON-NLS-1$
					
						IPath oldPath = multiImageSource.getGeneratedHeaderFilePath();
						EGeneratedHeaderFlags oldFlags = getGeneratedHeaderFlags();
						
						@Override
						protected boolean doesAnything() {
							return multiImageSource.canSetGeneratedHeaderFilePath();
						}

						@Override
						protected void redo() {
							multiImageSource.setGeneratedHeaderFilePath(null);
							multiImageSource.setHeaderFlags(oldFlags);
							refresh();
						}

						@Override
						protected void undo() {
							multiImageSource.setGeneratedHeaderFilePath(oldPath);
							multiImageSource.setHeaderFlags(oldFlags);
							refresh();
						}
				};
				
				editorContext.pushAndExecute(operation);
				
			}
			
		});
		
		final Label headerGenerationLabel = new Label(this, SWT.NONE);
		headerGenerationLabel.setLayoutData(new GridData());
		headerGenerationLabel.setText(Messages.getString("OutputFileParametersComposite.HeaderGenerationLabel")); //$NON-NLS-1$

		headerRadioGroup = new Composite(this, SWT.NONE);
		headerRadioGroup.setLayoutData(new GridData());
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 3;
		headerRadioGroup.setLayout(gridLayout_1);

		noHeaderButton = new Button(headerRadioGroup, SWT.RADIO);
		noHeaderButton.setToolTipText(Messages.getString("OutputFileParametersComposite.NoHeaderTip")); //$NON-NLS-1$
		noHeaderButton.setText(Messages.getString("OutputFileParametersComposite.NoHeaderLabel")); //$NON-NLS-1$

		headerButton = new Button(headerRadioGroup, SWT.RADIO);
		headerButton.setToolTipText(Messages.getString("OutputFileParametersComposite.HeaderTip")); //$NON-NLS-1$
		headerButton.setText(Messages.getString("OutputFileParametersComposite.HeaderLabel")); //$NON-NLS-1$

		headerOnlyButton = new Button(headerRadioGroup, SWT.RADIO);
		headerOnlyButton.setToolTipText(Messages.getString("OutputFileParametersComposite.OnlyHeaderTip")); //$NON-NLS-1$
		headerOnlyButton.setText(Messages.getString("OutputFileParametersComposite.OnlyHeaderLabel")); //$NON-NLS-1$
		headerOnlyButton.setEnabled(editorContext.supportsHeaderOnlyFlag());

		setTabList(new Control[] {targetFileText, defaultTargetFileButton, headerFileText, defaultHeaderFileButton, headerRadioGroup});
		
					
		noHeaderButton.addSelectionListener(new HeaderStateListener(this, EGeneratedHeaderFlags.NoHeader));
		headerButton.addSelectionListener(new HeaderStateListener(this, EGeneratedHeaderFlags.Header));
		headerOnlyButton.addSelectionListener(new HeaderStateListener(this, EGeneratedHeaderFlags.HeaderOnly));
	}
	
	/**
	 */
	protected IPath emptyIfNull(IPath path) {
		return path != null ? path : new Path(""); //$NON-NLS-1$
	}

	/**
	 * @return
	 */
	protected EGeneratedHeaderFlags getGeneratedHeaderFlags() {
		return noHeaderButton.getSelection() ? EGeneratedHeaderFlags.NoHeader :
			headerButton.getSelection() ? EGeneratedHeaderFlags.Header :
				EGeneratedHeaderFlags.HeaderOnly;
	}

	public void refresh() {
		Shell shell = getShell();
		if (shell != null) {
			String targetFile = multiImageSource.getTargetFile();
			shell.setText(targetFile != null && targetFile.length() > 0 
					? targetFile : Messages.getString("OutputFileParametersComposite.UnnamedTargetFileStub")); //$NON-NLS-1$
		}
		IPath targetFilePath = multiImageSource.getTargetFilePath();
		targetFileText.setText(targetFilePath != null ? targetFilePath.toOSString() : ""); //$NON-NLS-1$
		IPath headerPath = multiImageSource.getGeneratedHeaderFilePath();
		EGeneratedHeaderFlags flags = multiImageSource.getHeaderFlags();
		if (headerPath == null && flags != EGeneratedHeaderFlags.NoHeader) {
			headerPath = multiImageSource.getDefaultGeneratedHeaderFilePath();
		}
		if (headerPath != null)
			headerFileText.setText(headerPath.toOSString());
		else
			headerFileText.setText(""); //$NON-NLS-1$
			
		if (multiImageSource.canSetGeneratedHeaderFilePath()) {
			boolean hasHeader = multiImageSource.getHeaderFlags() != EGeneratedHeaderFlags.NoHeader;
			headerFileText.setEnabled(hasHeader);
			headerFileText.setToolTipText(Messages.getString("OutputFileParametersComposite.HeaderFileTooltip")); //$NON-NLS-1$
			defaultHeaderFileButton.setEnabled(hasHeader);
		} else {
			headerFileText.setEnabled(true);
			headerFileText.setEditable(false);
			headerFileText.setToolTipText(Messages.getString("OutputFileParametersComposite.GeneratedHeaderFileTooltip")); //$NON-NLS-1$
			defaultHeaderFileButton.setEnabled(false);
		}
		noHeaderButton.setSelection(flags == EGeneratedHeaderFlags.NoHeader);
		headerButton.setSelection(flags == EGeneratedHeaderFlags.Header);
		headerOnlyButton.setSelection(flags == EGeneratedHeaderFlags.HeaderOnly);
	}

}
