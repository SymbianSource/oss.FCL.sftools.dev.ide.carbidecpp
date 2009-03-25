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

import com.nokia.carbide.cpp.internal.project.ui.editors.common.CarbideTextEditor;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.*;
import org.eclipse.ui.*;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.editors.text.TextEditorActionContributor;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;
import org.eclipse.ui.texteditor.ITextEditor;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;

/**
 * Manages the installation/deinstallation of global actions for multi-page editors.
 * Responsible for the redirection of global actions to the active editor.
 * Multi-page contributor replaces the contributors for the individual editors in the multi-page editor.
 */
public class BldInfEditorContributor extends MultiPageEditorActionBarContributor {
	private IEditorPart activeEditorPart;
	private BldInfEditorContext editorContext;

	private UpdateableAction undoAction;
	private UpdateableAction redoAction;
	
	// for support of the text editors contributions
	private TextEditorActionContributor textContributor;
	private SubActionBars textActionBars;


	/**
	 * Creates a multi-page contributor.
	 */
	public BldInfEditorContributor() {
		super();
		createActions();
		textContributor = new TextEditorActionContributor();
	}
	
	@Override
	public void init(IActionBars bars, IWorkbenchPage page) {
		super.init(bars, page);
		textActionBars = new SubActionBars(bars);
		textContributor.init(textActionBars, page);
	}
	
	@Override
	public void dispose() {
		if (textContributor != null) {
			textContributor.dispose();
		}
		if (textActionBars != null) {
			textActionBars.dispose();
		}
		super.dispose();
	}

	void updateUndoRedo() {
		undoAction.update();
		redoAction.update();
	}
		
	private void registerGlobalActions() {
		IActionBars actionBars = getActionBars();
		actionBars.setGlobalActionHandler(
				ActionFactory.UNDO.getId(), undoAction);
		actionBars.setGlobalActionHandler(
				ActionFactory.REDO.getId(), redoAction);		
	}
	
	/**
	 * Updates actions for form pages, does not handle text page
	 */
	private void updateFormActions(IEditorPart part) {
		IActionBars actionBars = getActionBars();
		undoAction.update();
		redoAction.update();
		actionBars.updateActionBars();
	}
	
	/**
	 * Depending on whether a form page or text editor is active,
	 * we set the appropriate global action handlers and activate/deactivate
	 * the text editors action bars.
	 */
	private void setFormEditorActive(boolean active) {
		IActionBars actionBars = getActionBars();
		actionBars.clearGlobalActionHandlers();
		actionBars.updateActionBars();
		if (active) {
			textActionBars.deactivate();
			registerGlobalActions();
		} else {
			textActionBars.activate();
			Map globalActions = textActionBars.getGlobalActionHandlers();
			if (globalActions != null) {
				for (Iterator iter = globalActions.entrySet().iterator(); iter.hasNext();) {
					Map.Entry entry = (Map.Entry) iter.next();
					actionBars.setGlobalActionHandler((String)entry.getKey(), (IAction)entry.getValue());
				}
			}
			registerGlobalActions();
		}
		actionBars.updateActionBars();
	}


	/**
	 * Returns the action registed with the given text editor.
	 * @return IAction or null if editor is null.
	 */
	protected IAction getAction(ITextEditor editor, String actionID) {
		return (editor == null ? null : editor.getAction(actionID));
	}
	
		@Override
	public void setActiveEditor(IEditorPart part) {
		super.setActiveEditor(part);
		editorContext = (BldInfEditorContext) part.getAdapter(BldInfEditorContext.class);
		Check.checkState(editorContext != null);
	}

	public void setActivePage(IEditorPart part) {
		if (part != activeEditorPart) {
			activeEditorPart = part;
			updateFormActions(part);
			if (part instanceof CarbideTextEditor) {
				textContributor.setActiveEditor(part);
				setFormEditorActive(false);
			} else {
				textContributor.setActiveEditor(null);
				setFormEditorActive(true);
			}
		}
	}
	
	IOperationHistory getOperationHistory() {
		IOperationHistory result = null;
		if (editorContext != null) {
			result = editorContext.operationHistory;
		}
		return result;
	}
	
	static abstract class UpdateableAction extends Action {
		
		public void update() {
			setEnabled(calculateEnabled());
		}
		protected abstract boolean calculateEnabled();
	}
	
	private void createActions() {
		
		undoAction = new UpdateableAction() {
			@Override
			protected boolean calculateEnabled() {
				boolean result = false;
				IOperationHistory opHistory = getOperationHistory();
				if (opHistory != null) {
					result = opHistory.canUndo(editorContext.allContext);
				}
				return result;
			}

			public void run() {
				IOperationHistory opHistory = getOperationHistory();
				if (opHistory != null && opHistory.canUndo(editorContext.allContext)) {
					IUndoableOperation op = opHistory.getUndoOperation(editorContext.allContext);
					try {
						IStatus status = opHistory.undo(editorContext.allContext, null, null);
						editorContext.handleStatus(status);
					} catch (ExecutionException x) {
						editorContext.handleExecutionException(op, x);
					}
				}
			}

			@Override
			public void update() {
				super.update();
				IOperationHistory opHistory = getOperationHistory();
				if (opHistory != null && opHistory.canUndo(editorContext.allContext)) {
					IUndoableOperation undoOp = opHistory.getUndoOperation(editorContext.allContext);
					if (undoOp != null) {
						String label = undoOp.getLabel();
						String fmt = Messages.BldInfEditorContributor_Undo;
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
				IOperationHistory opHistory = getOperationHistory();
				if (opHistory != null) {
					result = opHistory.canRedo(editorContext.allContext);
				}
				return result;
			}
			
			public void run() {
				IOperationHistory opHistory = getOperationHistory();
				if (opHistory != null && opHistory.canRedo(editorContext.allContext)) {
					IUndoableOperation op = opHistory.getRedoOperation(editorContext.allContext);
					try {
						IStatus status = opHistory.redo(editorContext.allContext, null, null);
						editorContext.handleStatus(status);
					} catch (ExecutionException x) {
						editorContext.handleExecutionException(op, x);
					}
				}
			}
			
			@Override
			public void update() {
				super.update();
				IOperationHistory opHistory = getOperationHistory();
				if (opHistory != null && opHistory.canRedo(editorContext.allContext)) {
					IUndoableOperation redoOp = opHistory.getRedoOperation(editorContext.allContext);
					if (redoOp != null) {
						String label = redoOp.getLabel();
						String fmt = Messages.BldInfEditorContributor_Redo;
						Object params[] = {label};
						String text = MessageFormat.format(fmt, params);
						setText(text);
					}
				}
			}	
		};
	}

	public void contributeToMenu(IMenuManager manager) {
	}

	public void contributeToToolBar(IToolBarManager manager) {
	}
}
