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
package com.nokia.sdt.symbian.ui.appeditor;

import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.symbian.ui.noexport.Messages;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.*;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the installation/deinstallation of global actions for multi-page editors.
 * Responsible for the redirection of global actions to the active editor.
 * Multi-page contributor replaces the contributors for the individual editors in the multi-page editor.
 */
public class ApplicationEditorContributor extends MultiPageEditorActionBarContributor {
	private IEditorPart activeEditorPart;
	private AppEditorContext editorContext;
	
	private UpdateableAction deleteAction;
	private UpdateableAction undoAction;
	private UpdateableAction redoAction;

	private Map<IEditorPart, IEditorActionBarContributor> pageContributors;
	private IPartListener partListener;
	
	/**
	 * Creates a multi-page contributor.
	 */
	public ApplicationEditorContributor() {
		super();
		createActions();
		pageContributors = new HashMap();
	}
	

	@Override
	public void setActiveEditor(IEditorPart part) {
		super.setActiveEditor(part);
		updateActions(part);
	}

	public void setActivePage(IEditorPart part) {
		if (part != activeEditorPart) {
			updateActions(part);
			activeEditorPart = part;
		}
		for (IEditorActionBarContributor contributor : pageContributors.values()) {
			if (part instanceof IDesignerEditor) {
				contributor.setActiveEditor(part);
			} 
			else {
				contributor.setActiveEditor(null);
			}
		}
		getActionBars().updateActionBars();
	}
	
	void updateUndoRedo() {
		undoAction.update();
		redoAction.update();
	}
	
	private void updateActions(IEditorPart part) {
		if (part == null)
			return;
		
		AppEditorContext context = (AppEditorContext) part.getAdapter(AppEditorContext.class);
		if (context == null)
			return;

		editorContext = context;
		activeEditorPart = part;
		IActionBars actionBars = getActionBars();
		if (actionBars != null) {
			actionBars.setGlobalActionHandler(
				ActionFactory.DELETE.getId(), deleteAction);
			actionBars.setGlobalActionHandler(
				ActionFactory.UNDO.getId(), undoAction);
			actionBars.setGlobalActionHandler(
				ActionFactory.REDO.getId(), redoAction);

			deleteAction.update();
			undoAction.update();
			redoAction.update();
		}
		
	}
	
	private CommandStack getCommandStack() {
		if (editorContext != null)
			return editorContext.getCommandStack();
		return null;
	}
	
	static abstract class UpdateableAction extends Action {
	
		public void update() {
			setEnabled(calculateEnabled());
		}
		protected abstract boolean calculateEnabled();
	}
	
	private void createActions() {
		
		deleteAction = new UpdateableAction() {
			public void run() {
				
			}
			
			protected boolean calculateEnabled() {
				return false;
			}
		};
		undoAction = new UpdateableAction() {
			
			@Override
			protected boolean calculateEnabled() {
				boolean result = false;
				CommandStack commandStack = getCommandStack();
				if (commandStack != null) {
					result = commandStack.canUndo();
				}
				return result;
			}

			public void run() {
				CommandStack commandStack = getCommandStack();
				if (commandStack != null && commandStack.canUndo()) {
					commandStack.undo();
				}
			}

			@Override
			public void update() {
				super.update();
				CommandStack commandStack = getCommandStack();
				if (commandStack != null) {
					Command cmd = commandStack.getUndoCommand();
					if (cmd != null) {
						String label = cmd.getLabel();
						String fmt = Messages.getString("ApplicationEditorContributor.undoCommandFormat"); //$NON-NLS-1$
						Object params[] = {label};
						String text = MessageFormat.format(fmt, params);
						setText(text);
					}
				}
			}	
			
			
		};
		
		redoAction = new UpdateableAction() {
			@Override
			protected boolean calculateEnabled() {
				boolean result = false;
				CommandStack commandStack = getCommandStack();
				if (commandStack != null) {
					result = commandStack.canRedo();
				}
				return result;
			}
			
			public void run() {
				CommandStack commandStack = getCommandStack();
				if (commandStack != null && commandStack.canRedo()) {
					commandStack.redo();
				}
				
			}
		};
	}
	
	public void addPageContributor(IEditorPart editorPart, IEditorActionBarContributor contributor) {
		pageContributors.put(editorPart, contributor);
	}
	
	private IEditorActionBarContributor getPageContributor(IEditorPart editorPart) {
		return pageContributors.get(editorPart);
	}
	
	@Override
	public void init(IActionBars bars, IWorkbenchPage page) {
		super.init(bars, page);
		getPage().addPartListener(partListener = new IPartListener() {
			public void partActivated(IWorkbenchPart part) {
				IEditorPart activeEditor = getPage().getActiveEditor();
				if (activeEditor == null)
					return;
				IEditorActionBarContributor pageContributor = getPageContributor(activeEditor);
				if (pageContributor != null)
					pageContributor.setActiveEditor(activeEditor);
			}

			public void partDeactivated(IWorkbenchPart part) {}
			public void partBroughtToTop(IWorkbenchPart part) {}
			public void partClosed(IWorkbenchPart part) {}
			public void partOpened(IWorkbenchPart part) {}
		});
	}
	
	@Override
	public void dispose() {
		if (partListener != null) {
			getPage().removePartListener(partListener);
			partListener = null;
		}
		super.dispose();
	}
}
