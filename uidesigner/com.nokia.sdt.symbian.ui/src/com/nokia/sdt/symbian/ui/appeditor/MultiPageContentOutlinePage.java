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

import com.nokia.sdt.editor.IDesignerDataModelEditor;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ListenerList;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.*;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import java.util.*;

public class MultiPageContentOutlinePage extends Page implements IContentOutlinePage, ISelectionChangedListener {
	
	private class EmptyPage implements IContentOutlinePage {
		private Composite control;

		public void createControl(Composite parent) {
			control = new Composite(parent, SWT.NULL);
		}

		public void dispose() {
		}

		public Control getControl() {
			return control;
		}

		public void setActionBars(IActionBars actionBars) {
		}

		public void setFocus() {
		}

		public void addSelectionChangedListener(ISelectionChangedListener listener) {
		}

		public ISelection getSelection() {
			return StructuredSelection.EMPTY;
		}

		public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		}

		public void setSelection(ISelection selection) {
		}

	}

	
	private PageBook pagebook;
	private Map<Object, IContentOutlinePage> subPageMap;
	private EmptyPage emptyPage;
	private IContentOutlinePage currentPage;
	private IActionBars actionBars;
	private ListenerList<ISelectionChangedListener> listeners;
	private ISelection selection;
	private IDesignerDataModelEditor editor;
	
	public MultiPageContentOutlinePage(IDesignerDataModelEditor editor) {
		this.editor = editor;
		subPageMap = new HashMap<Object, IContentOutlinePage>();
		emptyPage = new EmptyPage();
		listeners = new ListenerList<ISelectionChangedListener>();
	}

	public void createControl(Composite parent) {
		pagebook = new PageBook(parent, SWT.NONE);
	}

	public Control getControl() {
		return pagebook;
	}

	public void dispose() {
		for (Iterator<IContentOutlinePage> iter = subPageMap.values().iterator(); iter.hasNext();) {
			iter.next().dispose();
		}
		if (emptyPage != null) {
			emptyPage.dispose();
			emptyPage = null;
		}
		pagebook = null;
		listeners = null; 
		editor.disposeOutlinePage();
		super.dispose();
	}
	
	public void setActionBars(IActionBars actionBars) {
		this.actionBars = actionBars;
		if (currentPage != null)
			setPageActive(currentPage);
	}
	
	public IActionBars getActionBars() {
		return actionBars;
	}

	public void setFocus() {
		if (currentPage != null)
			currentPage.setFocus();
	}

	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.add(listener);
	}

	public ISelection getSelection() {
		return selection;
	}

	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		listeners.remove(listener);
	}

	public void setSelection(ISelection selection) {
		this.selection = selection;
		if (listeners == null)
			return;

		for (Iterator<ISelectionChangedListener> iter = listeners.iterator(); iter.hasNext();) {
			iter.next().selectionChanged(new SelectionChangedEvent(currentPage, selection));
		}
	}

	public void selectionChanged(SelectionChangedEvent event) {
		setSelection(event.getSelection());
	}
	
	private void setPageActive(IContentOutlinePage page) {
		if (page == null) {
			page = emptyPage;
		}
		if (currentPage != null) {
			currentPage.removeSelectionChangedListener(this);
		}

		page.addSelectionChangedListener(this);
		this.currentPage = page;
		
		if (pagebook == null)
			return;

		Control control = page.getControl();
		if (control == null || control.isDisposed()) {
			// create
			page.createControl(pagebook);
			page.setActionBars(getActionBars());			
			control = page.getControl();
		}
		pagebook.showPage(control);
		this.currentPage = page;
	}
	
	public void activatePage(Object reference) {
		Check.checkArg(reference);
		setPageActive(subPageMap.get(reference));
	}
	
	private void initPage(IContentOutlinePage page) {
		if (page instanceof IPageBookViewPage) {
			IPageSite site = getSite();
			if (site != null) {
				try {
					((IPageBookViewPage) page).init(site);
				} 
				catch (PartInitException e) {
					Check.failedArg(e);
				}
			}
		}
	}

    public void init(IPageSite pageSite) {
        super.init(pageSite);
		for (Iterator<IContentOutlinePage> iter = subPageMap.values().iterator(); iter.hasNext();) {
			initPage(iter.next());
		}
   }

	public void addSubPage(IContentOutlinePage page, Object reference) {
		Check.checkArg(reference);
		subPageMap.put(reference, page);
		initPage(page);
	}
}
