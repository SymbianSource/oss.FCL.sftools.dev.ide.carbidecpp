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
package com.nokia.sdt.uidesigner.ui.actions;

import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.DesignerEditorPage;
import com.nokia.sdt.uidesigner.ui.UIDesignerPlugin;
import com.nokia.sdt.uidesigner.ui.utils.Strings;
import com.nokia.sdt.uimodel.UIModelPlugin;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.swtdesigner.ResourceManager;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.action.ControlContribution;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;

public class FilterPaletteContributionItem extends ControlContribution {

	private Button button;
	private DesignerEditorPage editor;
	private IPartService service;
	private IPartListener partListener;
	private boolean shouldShow = true;
	private final static String LAST_CONFIG = "FilterPaletteContributionItem.LastSetConfiguration"; //$NON-NLS-1$
	private final static QualifiedName QUALIFIED_LAST_CONFIG = 
						new QualifiedName(UIDesignerPlugin.getDefault().toString(), LAST_CONFIG);
	private final static boolean DEFAULT_STATE = true;
	
	public FilterPaletteContributionItem(IPartService partService) {
		super("com.nokia.sdt.uidesigner.ui.filterPalette_widget"); //$NON-NLS-1$
		Check.checkArg(partService);
		service = partService;
		partService.addPartListener(partListener = new IPartListener() {
			public void partActivated(IWorkbenchPart part) {
				setEditor(part);
			}

			public void partBroughtToTop(IWorkbenchPart part) {}
			public void partClosed(IWorkbenchPart part) {}
			public void partDeactivated(IWorkbenchPart part) {}
			public void partOpened(IWorkbenchPart part) {
				setEditor(part);
			}
		});
	}
	
	protected void setEditor(IWorkbenchPart part) {
		IDesignerEditor editor = (IDesignerEditor) part.getAdapter(IDesignerEditor.class);
		if (editor != null) {
			this.editor = (DesignerEditorPage) editor;
			refresh();
		}
	}

	protected void refresh() {
		if ((button == null) || button.isDisposed() || (editor == null) || (editor.getDataModel() == null))
			return;
		
		button.setSelection(getSavedPaletteFiltering(editor.getDataModelResource()));
	}

	protected Control createControl(Composite parent) {
		button = new Button(parent, SWT.TOGGLE);
		button.setImage(ResourceManager.getPluginImage(
				UIDesignerPlugin.getDefault(), "icons/paletteFilter.png")); //$NON-NLS-1$
		button.setToolTipText(Strings.getString("FilterPaletteContributionItem.Tooltip")); //$NON-NLS-1$
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleChangeState();
			}
		});
		button.setSelection(DEFAULT_STATE);
		refresh();
		
		return button;
	}

	protected void handleChangeState() {
		Check.checkState(editor != null);
		boolean state = button.getSelection();
		// save in the plugin settings
		UIModelPlugin.getDefault().getPreferenceStore().setValue(LAST_CONFIG, state);
		// save in the resource
		if (editor != null) {
			IResource resource = editor.getDataModelResource();
			try {
				resource.setPersistentProperty(QUALIFIED_LAST_CONFIG, String.valueOf(state));
			} 
			catch (CoreException e) {} // don't do anything if this fails
		}
		editor.updatePalette(false);
	}

	public static boolean getSavedPaletteFiltering(IResource resource) {
		String savedStateString = null;
		
		// first try the resource
		try {
			if (resource != null)
				savedStateString = resource.getPersistentProperty(QUALIFIED_LAST_CONFIG);
		} 
		catch (CoreException e) {} // don't do anything if this fails

		// then try the plugin setting
		if ((savedStateString == null) || (savedStateString.length() == 0))
			savedStateString = UIModelPlugin.getDefault().getPreferenceStore().getString(LAST_CONFIG);

		// if still no saved setting, then default to true
		if ((savedStateString == null) || (savedStateString.length() == 0))
			return DEFAULT_STATE;
			
		return Boolean.parseBoolean(savedStateString);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.ContributionItem#dispose()
	 */
	public void dispose() {
		if (partListener == null)
			return;
		service.removePartListener(partListener);
		button = null;
		partListener = null;
		editor = null;
		super.dispose();
	}

    
    public void setShouldBeVisible(boolean visible) {
    	this.shouldShow  = visible;
    	setVisible(visible);
    }
    
    public void setVisible(boolean visible) {
    	super.setVisible(visible && shouldShow);
    }
}
