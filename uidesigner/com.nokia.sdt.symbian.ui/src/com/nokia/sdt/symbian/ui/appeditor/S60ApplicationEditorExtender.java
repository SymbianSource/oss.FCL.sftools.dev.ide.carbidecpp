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


package com.nokia.sdt.symbian.ui.appeditor;

import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.editor.*;
import com.nokia.sdt.symbian.dm.S60ModelUtils;
import com.nokia.sdt.symbian.dm.SymbianModelUtils;
import com.nokia.sdt.symbian.dm.SymbianModelUtils.SDKType;
import com.nokia.sdt.symbian.ui.UIPlugin;
import com.nokia.sdt.symbian.ui.appeditor.context.EditingContextCommand;
import com.nokia.sdt.symbian.ui.noexport.Messages;
import com.nokia.sdt.uidesigner.ui.DesignerEditorPage;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.*;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.viewers.*;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import java.util.*;

/**
 *
 */
public class S60ApplicationEditorExtender 
			implements IDesignerDataModelEditorExtender, IPageChangedListener {

	private final static String APPUI_PAGE_HELP_CONTEXT_ID = 
					UIPlugin.PLUGIN_ID + ".appEditorAppUiPageContext"; //$NON-NLS-1$
	
	private IDesignerDataModelEditor dmEditor;
	private FormEditor formEditor;
	
	private OverviewPage overviewPage;
	private ViewsFormPage viewsPage;
	private LanguagesPage languagesPage;
	private IDesignerEditor appUiPage;

	private AppEditorContext editorContext;
	private int	appUiPageIndex = -1;
	private boolean appUiPageActivated;
	private ISelection lastAppUiPageSelection;
	private Object lastSelectedPage;
	private MultiPageContentOutlinePage	contentOutlinePage;

	/* (non-Javadoc)
	 * @see com.nokia.sdt.editor.IDesignerDataModelEditorExtender#editorInitialized(com.nokia.sdt.editor.IDesignerDataModelEditor)
	 */
	public void editorInitialized(IDesignerDataModelEditor editor) throws CoreException {
		if (!SymbianModelUtils.getModelSDK(editor.getDataModel()).equals(SDKType.S60))
			return;
		
		dmEditor = editor;
		formEditor = editor.getFormEditor();
		editorContext = (AppEditorContext) editor.getAdapter(AppEditorContext.class);
		
		createOverviewPage();
		createViewsPage();
		createLanguagesPage();
		createAppUiPage();
		
		formEditor.addPageChangedListener(this);
		dmEditor.getCommandStack().addCommandStackEventListener(new CommandStackEventListener() {
			public void stackChanged(CommandStackEvent event) {
				switch (event.getDetail()) {
				case CommandStack.PRE_EXECUTE:
				case CommandStack.PRE_UNDO:
				case CommandStack.PRE_REDO:
					if (appUiPageIndex >= 0 &&
							!(event.getCommand() instanceof EditingContextCommand)) {
						dmEditor.activatePage(appUiPageIndex);
					}
					break;
				default:
					break;
				}
				
			}
		});
	}

	private void createOverviewPage() throws PartInitException {
		overviewPage = new OverviewPage(editorContext);
		formEditor.addPage(overviewPage);
	}

	private void createViewsPage() throws PartInitException {
		viewsPage = new ViewsFormPage(editorContext);
		formEditor.addPage(viewsPage);
	}

	private void createLanguagesPage() throws PartInitException {
		languagesPage = new LanguagesPage(editorContext);
		formEditor.addPage(languagesPage);
	}

	private void createAppUiPage() throws PartInitException {
		if (editorContext.getS60RootModelType() != S60ModelUtils.S60RootModelType.LEGACY) {
			lastAppUiPageSelection = StructuredSelection.EMPTY;
			DesignerEditorPage designerEditorPage = 
				new DesignerEditorPage(editorContext.getFormEditor()); 
			appUiPageIndex = formEditor.addPage(designerEditorPage, dmEditor.getEditorInput());
			editorContext.setAppUiPageIndex(appUiPageIndex);
			dmEditor.setTabTitle(appUiPageIndex, Messages.getString("ApplicationEditor.appUiPageLabel")); //$NON-NLS-1$
			appUiPage = designerEditorPage;
			
			IContentOutlinePage outlinePage = 
				(IContentOutlinePage) dmEditor.getAdapter(IContentOutlinePage.class);
			if (outlinePage instanceof MultiPageContentOutlinePage)
				contentOutlinePage = (MultiPageContentOutlinePage) outlinePage;
		}
	}

	public void pageChanged(PageChangedEvent event) {
		Object selectedPage = event.getSelectedPage();
		if ((selectedPage == null) || (selectedPage == lastSelectedPage))
			return;
		
		lastSelectedPage = selectedPage;
		if (contentOutlinePage != null) {
			contentOutlinePage.activatePage(selectedPage);
		}
		
		if (appUiPage == null)
			return;
		ISelectionManager selectionManager = appUiPage.getSelectionManager();
		if (selectionManager == null)
			return;
		
		if (!selectedPage.equals(appUiPage)) {
			lastAppUiPageSelection = selectionManager.getSelection();
			selectionManager.setSelection(StructuredSelection.EMPTY);
		}
		else {
			if (!appUiPageActivated) {
				appUiPageActivated = true;
				WorkbenchUtils.setHelpContextId(appUiPage.getControl(), APPUI_PAGE_HELP_CONTEXT_ID);
			}
			pruneAppUiSelection();
			selectionManager.setSelection(lastAppUiPageSelection);
		}
	}

	@SuppressWarnings("unchecked")
	private void pruneAppUiSelection() {
		// remove any items no longer in the model from the last appUi page selection
		List selItems = new ArrayList();
		IStructuredSelection sel = (IStructuredSelection) lastAppUiPageSelection;
		for (Iterator iter = sel.iterator(); iter.hasNext();) {
			EditPart part = (EditPart) iter.next();
			if (ModelUtils.getModel((EObject) part.getModel()) != null)
				selItems.add(part);
		}
		lastAppUiPageSelection = new StructuredSelection(selItems);
	}

}
