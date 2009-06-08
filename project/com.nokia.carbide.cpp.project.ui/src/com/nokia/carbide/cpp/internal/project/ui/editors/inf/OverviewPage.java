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
package com.nokia.carbide.cpp.internal.project.ui.editors.inf;

import com.nokia.carbide.cpp.internal.project.ui.editors.common.FormUtilities;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;
import com.swtdesigner.SWTResourceManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.*;

import java.util.LinkedHashSet;
import java.util.Set;

public class OverviewPage extends BldInfEditorFormPage {
	
	private Composite activeBuildInfoComposite;
	private Label activeBuildConfigLabel;
	private Composite prjPlatformsComposite;
	private Label prjPlatformsLabel;
	private MakMakeFilesSectionPart makMakeFiles;
	private MakMakeFilesSectionPart testMakMakeFiles;

	/**
	 * Create the form page
	 * @param id
	 * @param title
	 */
	public OverviewPage(BldInfEditorContext editorContext) {
		super(editorContext, BldInfEditorContext.OVERVIEW_PAGE_ID, Messages.OverviewPage_OverviewTitle);
	}

	/**
	 * Create contents of the form
	 * @param managedForm
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		form.setText(Messages.OverviewPage_OverviewTitle);
		Composite body = form.getBody();
		final TableWrapLayout tableWrapLayout_2 = new TableWrapLayout();
		tableWrapLayout_2.rightMargin = 20;
		tableWrapLayout_2.verticalSpacing = 8;
		tableWrapLayout_2.numColumns = 2;
		body.setLayout(tableWrapLayout_2);
		toolkit.paintBordersFor(body);
	
		FormUtilities.addHelpContextToolbarItem(form.getForm(), 
				HelpContexts.OVERVIEW_PAGE, 
				Messages.OverviewPage_HelpButtonText);

		FormText formText;
		formText = toolkit.createFormText(body, false);
		final TableWrapData tableWrapData_1 = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP);
		tableWrapData_1.colspan = 2;
		formText.setLayoutData(tableWrapData_1);
		formText.setText(Messages.OverviewPage_Description1 +
				Messages.OverviewPage_Description2 +
				Messages.OverviewPage_Description3, false, false);

		activeBuildInfoComposite = toolkit.createComposite(body, SWT.NONE);
		final RowLayout rowLayout = new RowLayout();
		rowLayout.fill = true;
		rowLayout.wrap = false;
		rowLayout.marginLeft = 20;
		activeBuildInfoComposite.setLayout(rowLayout);
		final TableWrapData tableWrapData_2 = new TableWrapData(TableWrapData.FILL, TableWrapData.TOP);
		tableWrapData_2.maxWidth = 288;
		tableWrapData_2.colspan = 2;
		activeBuildInfoComposite.setLayoutData(tableWrapData_2);
		toolkit.paintBordersFor(activeBuildInfoComposite);
		
		final Label buildConfigStaticLabel = toolkit.createLabel(activeBuildInfoComposite, Messages.OverviewPage_BuildConfigLabel, SWT.NONE);
		buildConfigStaticLabel.setFont(SWTResourceManager.getFont("Arial", 8, SWT.BOLD)); //$NON-NLS-1$

		activeBuildConfigLabel = toolkit.createLabel(activeBuildInfoComposite, "", SWT.NONE); //$NON-NLS-1$
	
		prjPlatformsComposite = toolkit.createComposite(body, SWT.NONE);
		final RowLayout rowLayout2 = new RowLayout();
		rowLayout2.fill = true;
		rowLayout2.wrap = false;
		rowLayout2.marginLeft = 20;
		prjPlatformsComposite.setLayout(rowLayout2);
		final TableWrapData tableWrapData_3 = new TableWrapData(TableWrapData.FILL, TableWrapData.TOP);
		tableWrapData_3.maxWidth = 288;
		tableWrapData_3.colspan = 2;
		prjPlatformsComposite.setLayoutData(tableWrapData_3);
		toolkit.paintBordersFor(prjPlatformsComposite);
		
		final Label prjPlatformsStaticLabel = toolkit.createLabel(prjPlatformsComposite, Messages.OverviewPage_PrjPlatformsLabel, SWT.NONE);
		prjPlatformsStaticLabel.setFont(SWTResourceManager.getFont("Arial", 8, SWT.BOLD)); //$NON-NLS-1$

		prjPlatformsLabel = toolkit.createLabel(prjPlatformsComposite, "", SWT.NONE); //$NON-NLS-1$

		makMakeFiles = new MakMakeFilesSectionPart(editorContext, EBldInfListSelector.MAKMAKEFILES, body, 
				toolkit, Section.TWISTIE | Section.DESCRIPTION |Section.TITLE_BAR | Section.EXPANDED,
				controlManager, false);
		makMakeFiles.initialize(managedForm);
		makMakeFiles.getSection().setText(Messages.OverviewPage_Components);
		makMakeFiles.getSection().setDescription(Messages.OverviewPage_ComponentsToolTip);

		testMakMakeFiles = new MakMakeFilesSectionPart(editorContext, EBldInfListSelector.TEST_MAKMAKEFILES, body, 
				toolkit, Section.TWISTIE | Section.DESCRIPTION |Section.TITLE_BAR | Section.EXPANDED,
				controlManager, true);
		testMakMakeFiles.initialize(managedForm);
		testMakMakeFiles.getSection().setText(Messages.OverviewPage_TestComponents);
		testMakMakeFiles.getSection().setDescription(Messages.OverviewPage_TestComponentsToolTip);

		setInputs();
	}
	
	private void setInputs() {
		makMakeFiles.setFormInput(editorContext.bldInfView.getMakMakeReferences());	
		makMakeFiles.getSection().setExpanded(true);
		
		testMakMakeFiles.setFormInput(editorContext.bldInfView.getTestMakMakeReferences());	
		// only expand the test section if it has items
		testMakMakeFiles.getSection().setExpanded(editorContext.bldInfView.getTestMakMakeReferences().size() > 0);
	}
	
	void refresh() {
		if (getPartControl() != null) {
			// the size does not get reset when calling setText for some reason, so the label width
			// is only as long as the initial string value for some reason.

			// the build config may have changed so we need to reset the inputs
			setInputs();
			
			activeBuildConfigLabel.setText(editorContext.activeBuildConfig.getDisplayString());
			activeBuildConfigLabel.setSize(400, 15);

			Set<String> plats = new LinkedHashSet<String>();
			for (String plat : editorContext.bldInfView.getPlatforms()) {
				plats.add(plat.toUpperCase());
			}
			if (plats.isEmpty()) {
				plats.add("DEFAULT"); //$NON-NLS-1$
			}
			
			String platforms = ""; //$NON-NLS-1$
			for (String plat : plats) {
				if (platforms.length() > 0) {
					platforms = platforms + ", "; //$NON-NLS-1$
				}
				platforms = platforms + plat;
			}
			
			prjPlatformsLabel.setText(platforms);
			prjPlatformsLabel.setSize(400, 15);
		}
		super.refresh();
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		WorkbenchUtils.setHelpContextId(getPartControl(), HelpContexts.OVERVIEW_PAGE);
	}

	public Label getActiveBuildConfigLabel() {
		return activeBuildConfigLabel;
	}

	public Label getProjectPlatformsLabel() {
		return prjPlatformsLabel;
	}

	public SectionPart getComponentsSectionPart() {
		return makMakeFiles;
	}

	public SectionPart getTestComponentsSectionPart() {
		return testMakMakeFiles;
	}

	public String getErrorMessage() {
		return getManagedForm().getForm().getMessage();
	}

}
