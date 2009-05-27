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

import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.osgi.framework.Version;

import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.internal.project.ui.ProjectUIPlugin;
import com.nokia.carbide.cpp.internal.project.ui.editors.common.*;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.commands.EMMPListSelector;
import com.nokia.carbide.cpp.internal.project.ui.mmpEditor.commands.EMMPStringValueSelector;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.cpp.internal.api.utils.core.*;
import com.nokia.cpp.internal.api.utils.ui.*;
import com.nokia.cpp.internal.api.utils.ui.editor.FormEditorEditingContext;
import com.swtdesigner.ResourceManager;
import com.swtdesigner.SWTResourceManager;

public class OverviewPage extends MMPEditorFormPage {
	
	private Composite activeBuildInfoComposite;
	private Label uid3Label;
	private Label uid2Label;
	private ComboViewer targetTypeViewer;
	private Label activeBuildConfigLabel;
	private Text uid3Text;
	private Text uid2Text;
	private Text targetPathText;
	private Label targetPathLabel;
	private Text targetNameText;
	private Label targetNameLabel;
	private ImageHyperlink sourceFilesImageHyperlink;
	private ImageHyperlink librariesImageHyperlink;
	private ImageHyperlink optionsImageHyperlink;
	private Hyperlink addAUserHyperlink;
	private Hyperlink addASystemHyperlink;

	// target types known not to require UID2. Although GUI EXEs do
	// require UID, there's no unambiguous distinction between GUI and
	// console exes.
	static final String NO_UID2_TARGET_TYPES[] = {"lib", "exe"}; //$NON-NLS-1$

	/**
	 * Create the form page
	 * @param id
	 * @param title
	 */
	public OverviewPage(MMPEditorContext editorContext) {
		super(editorContext, MMPEditorContext.OVERVIEW_PAGE_ID, Messages.OverviewPage_overviewPageTitle);
	}

	/**
	 * Create contents of the form
	 * @param managedForm
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		form.setText(Messages.OverviewPage_overviewFormTitle);
		Composite body = form.getBody();
		final TableWrapLayout tableWrapLayout_2 = new TableWrapLayout();
		tableWrapLayout_2.rightMargin = 20;
		tableWrapLayout_2.verticalSpacing = 8;
		tableWrapLayout_2.numColumns = 2;
		body.setLayout(tableWrapLayout_2);
		toolkit.paintBordersFor(body);
	
		FormUtilities.addHelpContextToolbarItem(form.getForm(), 
				HelpContexts.OVERVIEW_PAGE, 
				Messages.helpTooltip);

		FormText formText;
		formText = toolkit.createFormText(body, false);
		final TableWrapData tableWrapData_1 = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP);
		tableWrapData_1.colspan = 2;
		formText.setLayoutData(tableWrapData_1);
		formText.setText(Messages.OverviewPage_editorExplanation +
				Messages.OverviewPage_editorExplanation2 +
				Messages.OverviewPage_editorExplanation3, false, false);

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
		
		final Label buildConfigStaticLabel = toolkit.createLabel(activeBuildInfoComposite, Messages.OverviewPage_activeBuildConfigLabel, SWT.NONE);
		buildConfigStaticLabel.setLocation(27, 10);
		buildConfigStaticLabel.setFont(SWTResourceManager.getFont("Arial", 8, SWT.BOLD)); //$NON-NLS-1$

		activeBuildConfigLabel = toolkit.createLabel(activeBuildInfoComposite, "", SWT.NONE); //$NON-NLS-1$
		activeBuildConfigLabel.setToolTipText(Messages.OverviewPage_activeBuildConfigTooltip);
		activeBuildConfigLabel.setLocation(10, 10);
	
		final Section targetInformationSection = toolkit.createSection(body, Section.DESCRIPTION | Section.TITLE_BAR);
		final TableWrapData tableWrapData_3 = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP);
		tableWrapData_3.maxWidth = 250;
		tableWrapData_3.heightHint = 150;
		targetInformationSection.setLayoutData(tableWrapData_3);
		targetInformationSection.setDescription(Messages.OverviewPage_targetInfoDescription);
		targetInformationSection.setText(Messages.OverviewPage_targetInfoTitle);

		final Composite composite = toolkit.createComposite(targetInformationSection, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		composite.setLayout(gridLayout);
		toolkit.adapt(composite);
		targetInformationSection.setClient(composite);
		toolkit.paintBordersFor(composite);

		targetNameLabel = toolkit.createLabel(composite, Messages.OverviewPage_targetNameLabel, SWT.NONE);

		targetNameText = toolkit.createText(composite, null);
		targetNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		targetNameText.setToolTipText(Messages.OverviewPage_targetTooltip);
		
		toolkit.createLabel(composite, Messages.OverviewPage_targetTypeLabel, SWT.NONE);

		targetTypeViewer = new ComboViewer(composite, SWT.READ_ONLY);
		targetTypeViewer.setContentProvider(new ArrayContentProvider());
		targetTypeViewer.setLabelProvider(new LabelProvider());
		targetTypeViewer.setSorter(new ViewerSorter());
		targetTypeViewer.setInput(new Object());
		Combo targetTypeCombo = targetTypeViewer.getCombo();
		targetTypeCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		targetTypeCombo.setToolTipText(Messages.OverviewPage_targetTypeTooltip);
		toolkit.adapt(targetTypeCombo, true, true);
		
		targetPathLabel = toolkit.createLabel(composite, Messages.OverviewPage_targetPathLabel, SWT.NONE);
		// set default layout data so we have the exclude flag available later
		targetPathLabel.setLayoutData(new GridData());

		targetPathText = toolkit.createText(composite, null);
		targetPathText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		targetPathText.setToolTipText(Messages.OverviewPage_targetPathTooltip);

		uid2Label = toolkit.createLabel(composite, Messages.OverviewPage_uid2Label, SWT.NONE);

		uid2Text = toolkit.createText(composite, null);
		uid2Text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		uid2Text.setToolTipText(Messages.OverviewPage_uid2Tooltip);

		uid3Label = toolkit.createLabel(composite, Messages.OverviewPage_uid3Label, SWT.NONE);

		uid3Text = toolkit.createText(composite, null);
		uid3Text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		uid3Text.setToolTipText(Messages.OverviewPage_uid3Tooltip);

		final Section projectDefinitionSection = toolkit.createSection(body, Section.TITLE_BAR);
		final TableWrapData tableWrapData_4 = new TableWrapData(TableWrapData.LEFT, TableWrapData.TOP);
		tableWrapData_4.grabHorizontal = true;
		tableWrapData_4.heightHint = 140;
		tableWrapData_4.maxWidth = 276;
		projectDefinitionSection.setLayoutData(tableWrapData_4);
		projectDefinitionSection.setText(Messages.OverviewPage_projectDefSectionTitle);

		final Composite composite_1 = toolkit.createComposite(projectDefinitionSection, SWT.NONE);
		final TableWrapLayout tableWrapLayout_1 = new TableWrapLayout();
		tableWrapLayout_1.numColumns = 2;
		composite_1.setLayout(tableWrapLayout_1);
		toolkit.paintBordersFor(composite_1);
		projectDefinitionSection.setClient(composite_1);

		sourceFilesImageHyperlink = toolkit.createImageHyperlink(composite_1, SWT.NONE);
		sourceFilesImageHyperlink.setImage(ResourceManager.getPluginImage(ProjectUIPlugin.getDefault(), "icons/pencil.png")); //$NON-NLS-1$
		sourceFilesImageHyperlink.setText(Messages.OverviewPage_sourcesLinkTitle);
		sourceFilesImageHyperlink.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				editorContext.editor.setActivePage(MMPEditorContext.SOURCES_PAGE_ID);
			}
		});

		toolkit.createLabel(composite_1, Messages.OverviewPage_sourcesLinkDescription, SWT.WRAP);

		librariesImageHyperlink = toolkit.createImageHyperlink(composite_1, SWT.NONE);
		librariesImageHyperlink.setImage(ResourceManager.getPluginImage(ProjectUIPlugin.getDefault(), "icons/pencil.png")); //$NON-NLS-1$
		librariesImageHyperlink.setText(Messages.OverviewPage_libsLinktitle);
		librariesImageHyperlink.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				editorContext.editor.setActivePage(MMPEditorContext.LIBRARIES_PAGE_ID);
			}
		});

		toolkit.createLabel(composite_1, Messages.OverviewPage_libsLinkDescription, SWT.WRAP);

		optionsImageHyperlink = toolkit.createImageHyperlink(composite_1, SWT.NONE);
		optionsImageHyperlink.setImage(ResourceManager.getPluginImage(ProjectUIPlugin.getDefault(), "icons/pencil.png")); //$NON-NLS-1$
		optionsImageHyperlink.setText(Messages.OverviewPage_optionsLinkTitle);
		optionsImageHyperlink.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				editorContext.editor.setActivePage(MMPEditorContext.OPTIONS_PAGE_ID);
			}
		});

		toolkit.createLabel(composite_1, Messages.OverviewPage_optionsLinkDescription, SWT.WRAP);

		final Section commonTasksSection = toolkit.createSection(body, Section.TITLE_BAR);
		final TableWrapData tableWrapData_5 = new TableWrapData(TableWrapData.FILL_GRAB, TableWrapData.TOP);
		tableWrapData_5.maxWidth = 250;
		commonTasksSection.setLayoutData(tableWrapData_5);
		commonTasksSection.setText(Messages.OverviewPage_commonTasksTitle);

		final Composite composite_2 = toolkit.createComposite(commonTasksSection, SWT.NONE);
		composite_2.setLayout(new FormLayout());
		toolkit.paintBordersFor(composite_2);
		commonTasksSection.setClient(composite_2);

		addAUserHyperlink = toolkit.createHyperlink(composite_2, Messages.OverviewPage_addUserPathLink, SWT.NONE);
		final FormData formData_6 = new FormData();
		formData_6.top = new FormAttachment(0, 5);
		formData_6.left = new FormAttachment(0, 5);
		addAUserHyperlink.setLayoutData(formData_6);
		addAUserHyperlink.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				doAddInclude(EMMPListSelector.USER_INCLUDES);
			}		
		});

		addASystemHyperlink = toolkit.createHyperlink(composite_2, Messages.OverviewPage_addSysPathLink, SWT.NONE);
		final FormData formData_7 = new FormData();
		formData_7.top = new FormAttachment(addAUserHyperlink, 5, SWT.BOTTOM);
		formData_7.left = new FormAttachment(addAUserHyperlink, 0, SWT.LEFT);
		addASystemHyperlink.setLayoutData(formData_7);
		addASystemHyperlink.addHyperlinkListener(new HyperlinkAdapter() {
			public void linkActivated(HyperlinkEvent e) {
				doAddInclude(EMMPListSelector.SYS_INCLUDES);
			}		
		});
		
		hookControls();
	}

	/**
	 * Check whether the file being edited is an MMP file.
	 * @return
	 */
	private boolean isMMPFile() {
		String fileName = editorContext.editor.getEditorInput().getName();
		String extension = TextUtils.getExtension(fileName);
		if (extension.toLowerCase().equals(Messages.OverviewPage_mmpExtension)) {
			return true;
		}
		else {
			return false;
		}
	}

	private void hookControls() {
		RegExInputValidator targetNameValidator = new RegExInputValidator("^[\\w|\\.]+$", false, Messages.OverviewPage_targetNameValidationErr);	//$NON-NLS-1$
		SingleSettingTextHandler handler = new SingleSettingTextHandler(targetNameText, 
				new FormEditorEditingContext(editorContext.editor, targetNameText),
				(isMMPFile()) ? targetNameValidator : null,
				EMMPStatement.TARGET, editorContext);
		handler.setLabel(targetNameLabel);
		controlManager.add(handler);
		
		controlManager.add(new SingleSettingTextHandler(targetPathText,
				new FormEditorEditingContext(editorContext.editor, targetPathText),
				null, EMMPStatement.TARGETPATH, editorContext));

		controlManager.add(new SingleSettingTextHandler(targetTypeViewer,
						new FormEditorEditingContext(editorContext.editor, targetTypeViewer.getControl()),
						EMMPStatement.TARGETTYPE, editorContext, false));
		
		NumberValidator uid2NumberValidator = new NumberValidator(0, MMPEditorContext.maxUID, false, Messages.OverviewPage_uid2ValidationErr) {
			public boolean isEmptyAllowed() {
				boolean result = false;
				for (String targetType : NO_UID2_TARGET_TYPES) {
					if (targetType.equalsIgnoreCase(targetTypeViewer.getCombo().getText())) {
						result = true;
						break;
					}
				}
				return result;
			}
		};
		
		StringSettingTextHandler handler2 = new StringSettingTextHandler(uid2Text,
				new FormEditorEditingContext(editorContext.editor, uid2Text),
				(isMMPFile()) ? uid2NumberValidator : null,
				EMMPStringValueSelector.UID2, editorContext);
		handler2.setLabel(uid2Label);
		controlManager.add(handler2);
		controlManager.addValidationDependency(ControlHandler.getHandlerForViewer(targetTypeViewer), handler2);

		NumberValidator uid3NumberValidator = new NumberValidator(0, MMPEditorContext.maxUID, true, Messages.OverviewPage_uid3ValidationErr);
		handler2 = new StringSettingTextHandler(uid3Text,
				new FormEditorEditingContext(editorContext.editor, uid3Text),
				(isMMPFile()) ? uid3NumberValidator : null,
				EMMPStringValueSelector.UID3, editorContext);
		handler2.setLabel(uid3Label);
		controlManager.add(handler2);
	}
	
	void refresh() {
		if (getPartControl() != null) {
			ISymbianSDK sdk = editorContext.activeBuildConfig.getSDK();
			List<String> supportedTargetTypes = new ArrayList<String>();
			List<String> sdkTypes = sdk.getSupportedTargetTypes();
			// this could come back empty if a devkit is not completely installed
			if (sdkTypes != null) {
				supportedTargetTypes.addAll(sdkTypes);
			}

			String targetType = editorContext.mmpView.getSingleArgumentSettings().get(EMMPStatement.TARGETTYPE);
			if (targetType != null && !supportedTargetTypes.contains(targetType)) {
				supportedTargetTypes.add(targetType);
			}
			ControlHandler.getHandlerForViewer(targetTypeViewer).setViewerInput(supportedTargetTypes);
			
			activeBuildConfigLabel.setText(editorContext.activeBuildConfig.getDisplayString());
			
			// only show target path if we're on Symbian OS < 9.1 or there's a value set already
			String targetPath = editorContext.mmpView.getSingleArgumentSettings().get(EMMPStatement.TARGETPATH);
			boolean showTargetPath = TextUtils.strlen(targetPath) > 0;
			if (!showTargetPath) {
				Version version = sdk.getOSVersion();
				if (version.getMajor() < 9 || 
				    (version.getMajor() == 9 && version.getMinor() < 1)) {
					showTargetPath = true;
				}
			}
			// if the part control is not visible, this is probably the first time we got called
			if (!getPartControl().isVisible() || showTargetPath != targetPathLabel.isVisible()) {
				setControlVisibility(targetPathLabel, showTargetPath);
				setControlVisibility(targetPathText, showTargetPath);
				this.getManagedForm().getForm().layout(true, true);
			}
		}
		super.refresh();
	}
	
	private void setControlVisibility(Control control, boolean visible) {
		control.setVisible(visible);
		if (control.getLayoutData() instanceof GridData) {
			GridData ld = (GridData) control.getLayoutData();
			ld.exclude = !visible;
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		WorkbenchUtils.setHelpContextId(getPartControl(), HelpContexts.OVERVIEW_PAGE);
	}
	
	private void doAddInclude(final EMMPListSelector includeType) {
		final IPageChangedListener listener = new IPageChangedListener() {
			public void pageChanged(final PageChangedEvent event) {
				if (event.getSelectedPage() instanceof OptionsPage) {
					editorContext.editor.removePageChangedListener(this);
					final OptionsPage page = (OptionsPage) event.getSelectedPage();
					// try to wait until the page has actually changed
					getSite().getShell().getDisplay().asyncExec(new Runnable() {
						public void run() {
							page.addInclude(includeType);
						}
					});
				}
			}
		};
		editorContext.editor.addPageChangedListener(listener);
		editorContext.editor.setActivePage(MMPEditorContext.OPTIONS_PAGE_ID);
	}

	public Label getActiveBuildConfigLabel() {
		return activeBuildConfigLabel;
	}

	public Text getTargetNameText() {
		return targetNameText;
	}

	public Text getTargetPathText() {
		return targetPathText;
	}

	public Text getUID2Text() {
		return uid2Text;
	}

	public Text getUID3Text() {
		return uid3Text;
	}

	public Combo getTargetTypeCombo() {
		return targetTypeViewer.getCombo();
	}

	public Hyperlink getAddAUserIncludeHyperlink() {
		return addAUserHyperlink;
	}

	public Hyperlink getAddASystemIncludeHyperlink() {
		return addASystemHyperlink;
	}

	public ImageHyperlink getSourcesHyperlink() {
		return sourceFilesImageHyperlink;
	}

	public ImageHyperlink getLibrariesHyperlink() {
		return librariesImageHyperlink;
	}

	public ImageHyperlink getOptionsHyperlink() {
		return optionsImageHyperlink;
	}

	public String getErrorMesaage() {
		return getManagedForm().getForm().getMessage();
	}

}
