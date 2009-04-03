/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

import com.nokia.sdt.editor.IDesignerDataModelEditor;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.actions.*;
import com.nokia.sdt.uidesigner.ui.actions.ZoomComboContributionItem;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.*;
import org.eclipse.jface.action.*;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.RetargetAction;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.forms.editor.FormEditor;

/**
 * 
 * 
 */
public class ViewEditorActionBarContributor extends ActionBarContributor {

	private ZoomComboContributionItem zoomItem;
	private FilterPaletteContributionItem filterPaletteItem;
	private LayoutSizeComboContributionItem layoutSizeItem;
	private IToolBarManager toolBarManager;
	
	private class ActionContributionItemWithVisibleControl extends ActionContributionItem {

		private boolean shouldShow = true;

		public ActionContributionItemWithVisibleControl(IAction action) {
			super(action);
		}
		
		@Override
		public void setVisible(boolean visible) {
			super.setVisible(visible && shouldShow);
		}

		public void setShouldBeVisible(boolean visible) {
			this.shouldShow = visible;
			setVisible(visible);
		}
		
	}
	
	private void setToolbarItemsVisible(boolean visible) {
		if (toolBarManager == null) return;
		
		if (zoomItem != null) {
			zoomItem.setShouldBeVisible(visible);
		}
		
		if (filterPaletteItem != null) {
			filterPaletteItem.setShouldBeVisible(visible);
		}
		
		if (layoutSizeItem != null) {
			layoutSizeItem.setShouldBeVisible(visible);
		}
		
		IContributionItem[] items = toolBarManager.getItems();
		for (int i = 0; i < items.length; i++) {
			IContributionItem contributionItem = items[i];
			if (contributionItem instanceof ActionContributionItemWithVisibleControl) {
				ActionContributionItemWithVisibleControl withVisibleControl = 
					(ActionContributionItemWithVisibleControl) contributionItem;
				withVisibleControl.setShouldBeVisible(visible);
			}
		}

		toolBarManager.update(true);
	}
	
	/**
	 * Create actions managed by this contributor.
	 * 
	 * @see org.eclipse.gef.ui.actions.ActionBarContributor#buildActions()
	 */
	protected void buildActions() {
		addAction(ActionFactory.CUT.create(getPage().getWorkbenchWindow()));
		addAction(ActionFactory.COPY.create(getPage().getWorkbenchWindow()));
		addAction(ActionFactory.PASTE.create(getPage().getWorkbenchWindow()));
		addRetargetAction((RetargetAction) ActionFactory.UNDO.create(getPage().getWorkbenchWindow()));
		addRetargetAction((RetargetAction) ActionFactory.REDO.create(getPage().getWorkbenchWindow()));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.LEFT));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.CENTER));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.RIGHT));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.TOP));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.MIDDLE));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.BOTTOM));
		addRetargetAction(new MatchWidthRetargetAction());
		addRetargetAction(new MatchHeightRetargetAction());
		IWorkbenchAction action = ActionFactory.REVERT.create(getPage().getWorkbenchWindow());
		addAction(action);
		addGlobalActionKey(action.getId());
	}

	/**
	 * Add actions to the given toolbar.
	 * 
	 * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToToolBar(org.eclipse.jface.action.IToolBarManager)
	 */
	public void contributeToToolBar(IToolBarManager toolBarManager) {
		super.contributeToToolBar(toolBarManager);
		toolBarManager.add(getAction(ActionFactory.UNDO.getId()));
		toolBarManager.add(getAction(ActionFactory.REDO.getId()));
		toolBarManager.add(new Separator());
		toolBarManager.add(getAction(ActionFactory.CUT.getId()));
		toolBarManager.add(getAction(ActionFactory.COPY.getId()));
		toolBarManager.add(getAction(ActionFactory.PASTE.getId()));
		toolBarManager.add(new Separator());
		toolBarManager.add(new ActionContributionItemWithVisibleControl(
				getAction(GEFActionConstants.ALIGN_LEFT)));
		toolBarManager.add(new ActionContributionItemWithVisibleControl(
				getAction(GEFActionConstants.ALIGN_CENTER)));
		toolBarManager.add(new ActionContributionItemWithVisibleControl(
				getAction(GEFActionConstants.ALIGN_RIGHT)));
		toolBarManager.add(new Separator());
		toolBarManager.add(new ActionContributionItemWithVisibleControl(
				getAction(GEFActionConstants.ALIGN_TOP)));
		toolBarManager.add(new ActionContributionItemWithVisibleControl(
				getAction(GEFActionConstants.ALIGN_MIDDLE)));
		toolBarManager.add(new ActionContributionItemWithVisibleControl(
				getAction(GEFActionConstants.ALIGN_BOTTOM)));
		
		toolBarManager.add(new Separator());	
		toolBarManager.add(new ActionContributionItemWithVisibleControl(
				getAction(GEFActionConstants.MATCH_WIDTH)));
		toolBarManager.add(new ActionContributionItemWithVisibleControl(
				getAction(GEFActionConstants.MATCH_HEIGHT)));
		
		toolBarManager.add(new Separator());	
		toolBarManager.add(zoomItem = new ZoomComboContributionItem(getPage()));
		toolBarManager.add(filterPaletteItem = new FilterPaletteContributionItem(getPage()));
		toolBarManager.add(layoutSizeItem = new LayoutSizeComboContributionItem(getPage()));
		this.toolBarManager = toolBarManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.ActionBarContributor#declareGlobalActionKeys()
	 */
	protected void declareGlobalActionKeys() {
		addGlobalActionKey(ActionFactory.SELECT_ALL.getId());
		addGlobalActionKey(ActionFactory.DELETE.getId());
		addGlobalActionKey(ActionFactory.CUT.getId());
		addGlobalActionKey(ActionFactory.COPY.getId());
		addGlobalActionKey(ActionFactory.PASTE.getId());
	}

	public void setActiveEditor(IEditorPart editor) {
		if (editor != null) {
			IDesignerDataModelEditor ddme = (IDesignerDataModelEditor) editor.getAdapter(IDesignerDataModelEditor.class);
			if (ddme.hasOpenError())
				return;
			
			super.setActiveEditor(editor);
			zoomItem.setZoomManager((ZoomManager) editor.getAdapter(ZoomManager.class));
			FormEditor formEditor = (FormEditor) editor.getAdapter(FormEditor.class);
			if (formEditor != null) {
				editor = formEditor.getActiveEditor();
			}
			layoutSizeItem.handleEditorChanged();
		}
		
		// set visible when called with the DesignerEditorPage, but not with the editor itself
		setToolbarItemsVisible(editor != null 
				&& editor.getAdapter(IDesignerEditor.class) != null);
	}
}