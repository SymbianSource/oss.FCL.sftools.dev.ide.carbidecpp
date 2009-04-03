/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.sdt.uidesigner.ui;

import com.nokia.carbide.cpp.internal.featureTracker.FeatureUseTrackerConsts;
import com.nokia.carbide.cpp.internal.featureTracker.FeatureUseTrackerPlugin;
import com.nokia.sdt.uidesigner.ui.utils.Strings;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

import org.eclipse.core.runtime.*;
import org.eclipse.jface.dialogs.ErrorDialog;

public class DesignerEditor extends AbstractDesignerDataModelEditor {

	private static final String HELP_CONTEXT_ID = 
		UIDesignerPlugin.PLUGIN_ID + ".viewEditorContext"; //$NON-NLS-1$
	
	private DesignerEditorPage designerEditorPage;
	
	public void addPages() {
		if (hasOpenError()) {
			addErrorPage();
			return;
		}
		designerEditorPage = 
			new DesignerEditorPage(this);
		try {
			int index = addPage(designerEditorPage, getEditorInput());
			setPageText(index, Strings.getString("DesignerEditor.DesignPageLabel")); //$NON-NLS-1$
			initializeExtenders();
		} catch (CoreException e) {
			ErrorDialog.openError(getSite().getShell(), 
					Strings.getString("DesignerEditor.PageCreateError"), //$NON-NLS-1$
					null, e.getStatus());
			Logging.log(UIDesignerPlugin.getDefault(), e.getStatus());
		}
		
	}

	@Override
	protected IStatus doLoadInput(IProgressMonitor monitor) {
		FeatureUseTrackerPlugin.getFeatureUseProxy().startUsingFeature(FeatureUseTrackerConsts.CARBIDE_UI_DESIGNER);
		return super.doLoadInput(monitor);
	}

	@Override
	public void dispose() {
		super.dispose();
		designerEditorPage = null; // page will be disposed by superclass
		FeatureUseTrackerPlugin.getFeatureUseProxy().stopUsingFeature(FeatureUseTrackerConsts.CARBIDE_UI_DESIGNER);
	}

	@Override
	public Object getAdapter(Class adapter) {
		Object object = super.getAdapter(adapter);
		if (object == null && designerEditorPage != null)
			return designerEditorPage.getAdapter(adapter);
		
		return object;
	}

	public void disposeOutlinePage() {
		designerEditorPage.disposeOutlinePage();
	}

	public void refreshFromModel() {
		designerEditorPage.refreshFromModel();
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		getSite().getPage().activate(this); // make sure we're the active part before saving
		super.doSave(monitor);
		designerEditorPage.doSave(monitor);
	}
	
	public void reload() {
		designerEditorPage.prepareForReload();
		super.reload();
		getEditorSite().getActionBarContributor().setActiveEditor(getActiveEditor());
	}

	@Override
	protected void createPages() {
		super.createPages();
		WorkbenchUtils.setHelpContextId(getContainer(), HELP_CONTEXT_ID);
	}

	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
		getEditorSite().getActionBars().updateActionBars();
		getEditorSite().getActionBarContributor().setActiveEditor(getActiveEditor());
	}
}

