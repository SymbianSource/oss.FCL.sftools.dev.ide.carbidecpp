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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.nokia.carbide.cdt.builder.EMMPPathContext;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.ControlManager;
import com.nokia.cpp.internal.api.utils.ui.editor.FormEditorEditingContext;

public class SourcesSectionPart extends SectionPart {

	private MMPEditorContext editorContext;
	private SourceSelectionViewer sourceViewer;
	private List<IPath> missingFilePaths;

	/**
	 * Create the SectionPart
	 * @param parent
	 * @param toolkit
	 * @param style
	 */
	public SourcesSectionPart(Composite parent, FormToolkit toolkit, int style) {
		super(parent, toolkit, style);
		createClient(getSection(), toolkit);
	}
	
	void initialize(MMPEditorContext editorContext, ControlManager controlManager) {
		this.editorContext = editorContext;		
		sourceViewer.setInput(editorContext.project);
		controlManager.add(new SourcesTreeHandler(editorContext, 
				new FormEditorEditingContext(editorContext.editor, sourceViewer.getControl()), 
				sourceViewer, this));
	}


	/**
	 * Fill the section
	 */
	private void createClient(Section section, FormToolkit toolkit) {
		section.setText(Messages.SourcesSectionPart_sourcesSectionTitle);
		section.setDescription(Messages.SourcesSectionPart_sourcesSectionDescription);
		Composite container = toolkit.createComposite(section);
		final FillLayout fillLayout = new FillLayout();
		fillLayout.marginWidth = 3;
		fillLayout.marginHeight = 3;
		container.setLayout(fillLayout);
		toolkit.paintBordersFor(container);
		section.setClient(container);

		sourceViewer = new SourceSelectionViewer(container, SWT.NONE);
		Tree tree = sourceViewer.getTree();
		toolkit.adapt(tree, true, true);
		sourceViewer.setInput(new Object());	
	}
	
	@Override
	public void refresh() {
		super.refresh();
		updateViewerCheckState();
	}
		
	private void updateViewerCheckState() {
		sourceViewer.setAllChecked(false);
		List<IPath> sources = editorContext.mmpView.getSources();
		List<IFile> checkedFiles = new ArrayList<IFile>();
		missingFilePaths = new ArrayList<IPath>();
		for (IPath path : sources) {
			IPath path2 = editorContext.pathHelper.convertMMPToProject(EMMPPathContext.SOURCE, path);
			if (path2 != null) {
				path = path2;
				IFile file = SourcesTreeHandler.resolveMMPSourceFile(editorContext.project, path);
				if (file != null) {
					if (sourceViewer.setChecked(file, true)) {
						checkedFiles.add(file);
					} else {
						missingFilePaths.add(path);
					}
				} else {
					missingFilePaths.add(path);
				}
			} else {
				missingFilePaths.add(path);
			}
		}
		sourceViewer.updateContainerCheckStates(checkedFiles);
	}
	
	public List<IPath> getMissingFilePaths() {
		return missingFilePaths;
	}

	public SourceSelectionViewer getTreeViewer() {
		return sourceViewer;
	}

}
