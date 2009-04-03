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
package com.nokia.sdt.uidesigner.events;

import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;
import com.nokia.sdt.editor.IDesignerDataModelEditor;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.UIDesignerPlugin;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.*;
import org.eclipse.ui.part.*;
import org.eclipse.ui.views.contentoutline.ContentOutline;

/**
 * Main view for the Event editor. The EventView consists
 * of a set of IEventPages. Typically the default implementation,
 * EventPage, will be instantiated, but Workbench parts may
 * provide their own via adapters.
 *
 */
public class EventView extends PageBookView implements ISelectionListener {
	
	private ISelection bootstrapSelection;
	private static final String HELP_CONTEXT_ID = 
		UIDesignerPlugin.PLUGIN_ID + "." + "eventViewContext"; //$NON-NLS-1$
	
	public void init(IViewSite site) throws PartInitException {
		site.getPage().addSelectionListener(this);
		super.init(site);
	}
	
	public void dispose() {
		getSite().getPage().removeSelectionListener(this);
		super.dispose();
	}
	
	protected IPage createDefaultPage(PageBook book) {
		EventPage result = new EventPage(this);
		initPage(result);
		result.createControl(book);
		return result;
	}
	
	protected PageRec doCreatePage(IWorkbenchPart part) {
		// Follow property sheet pattern and ask the part 
		// for an event page
		IEventPage page = (IEventPage) part.getAdapter(IEventPage.class);
		
		// property sheet also looks for adapters contributed via
		// extension point
		if (page == null) {
			page = (IEventPage)Platform.getAdapterManager().loadAdapter(
					part, IEventPage.class.getName());
		}
		
		if (page == null) {
			// make a distinct page for every part that has an IDesignerEditor
			IDesignerDataModelEditor editor = 
				(IDesignerDataModelEditor) part.getAdapter(IDesignerDataModelEditor.class);
			if (editor != null) {
				EventPage ep = new EventPage(this);
				ep.associateDesignerEditor(editor);
				page = ep;
			}
		}
		// if no page is created then the current page will remain
		PageRec result = null;
		if (page != null) {
			initPage(page);
			page.createControl(getPageBook());
			result = new PageRec(part, page);
		}
		return result;
	}

	protected void doDestroyPage(IWorkbenchPart part, PageRec pageRecord) {
		IEventPage page = (IEventPage) pageRecord.page;
		pageRecord.dispose();
		if (page != null) {
			page.dispose();
		}
	}

	protected IWorkbenchPart getBootstrapPart() {
		IWorkbenchPart result = null;
		IWorkbenchPage page = getSite().getPage();
		if (page != null) {
			bootstrapSelection = page.getSelection();
			result = page.getActivePart();
		} 
		return result;
	}

	protected boolean isImportant(IWorkbenchPart part) {
		return part.getAdapter(IDesignerDataModelEditor.class) != null;
	}
	
	public void partActivated(IWorkbenchPart part) {
		// Again, following the example of the property sheet
		IContributedContentsView view = (IContributedContentsView) 
			part.getAdapter(IContributedContentsView.class);
		IWorkbenchPart source = null;
		if (view != null)
			source = view.getContributingPart();
		if (source != null)
			super.partActivated(source);
		else
			super.partActivated(part);
		
		// When the view is first opened, pass the selection to the page		
		if (bootstrapSelection != null) {
			IEventPage currentPage = (IEventPage) getCurrentPage();
			if (currentPage != null)
				currentPage.selectionChanged(part, bootstrapSelection);
			bootstrapSelection = null;
		}
	}
	
    public void selectionChanged(IWorkbenchPart part, ISelection selection) {
    	// We want to synch to the active designer editor, if any, or to an
    	// outline view that is broadcasting a selection change. We don't 
    	// want to synch to every view, like the properties view does, since
    	// this view isn't universal and would be empty. For example, if the user
    	// has a view editor selection, activates the projects view and selects something, we
    	// want this view to still show events from the view editor.
    	
        // Pass selections from editor and outline parts on to the current page
    	IEventPage page = (IEventPage) getCurrentPage();
    	if (page != null) {
	        if (part instanceof IEditorPart || part instanceof ContentOutline) {
	        	page.selectionChanged(part, selection);
	        }
	        else { 
	        	// if part is another view part, set the selection from the active editor
	        	IWorkbenchPage workbenchPage = part.getSite().getPage();
	        	IEditorPart activeEditor = workbenchPage.getActiveEditor();
	        	if (activeEditor != null) {
		        	IDesignerEditor activeDesignerEditor = 
		        		(IDesignerEditor) activeEditor.getAdapter(IDesignerEditor.class);
		        	if (activeDesignerEditor != null) {
		        		page.selectionChanged(part, activeDesignerEditor.getSelectionManager().getSelection());
		        	}
	        	}
	        }
    	}
    }

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		WorkbenchUtils.setHelpContextId(getPageBook(), HELP_CONTEXT_ID);
	}

}