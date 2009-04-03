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


package com.nokia.sdt.uidesigner.ui.actions;

import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.LayoutAreaConfigurationListener;
import com.nokia.sdt.displaymodel.IDisplayModel.LayoutAreaConfiguration;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.utils.EditorUtils;
import com.nokia.sdt.uimodel.UIModelPlugin;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.ControlContribution;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;

import java.util.Collection;
import java.util.Iterator;

public class LayoutSizeComboContributionItem extends ControlContribution {

	private IWorkbenchPage page;
	private Combo combo;
	private boolean shouldShow = true;
	private IDesignerEditor editor;
	private LayoutAreaConfigurationListener layoutAreaListener;
	private IPartListener partListener;

	public LayoutSizeComboContributionItem(IWorkbenchPage workbenchPage) {
		super("com.nokia.sdt.uidesigner.ui.layoutSize_widget"); //$NON-NLS-1$
		Check.checkArg(workbenchPage);
		page = workbenchPage;
		page.addPartListener(partListener = new IPartListener() {
			public void partActivated(IWorkbenchPart part) {
				handleEditorChanged();
			}

			public void partBroughtToTop(IWorkbenchPart part) {}
			public void partClosed(IWorkbenchPart part) {}
			public void partDeactivated(IWorkbenchPart part) {}

			public void partOpened(IWorkbenchPart part) {
				handleEditorChanged();
			}
		});
	}
	
	protected Control createControl(Composite parent) {
		combo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				handleWidgetSelected(e);
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				handleWidgetSelected(e);
			}
		});
		
		combo.setVisibleItemCount(10);
		// Initialize width of combo
		refresh();
		return combo;
	}

	void refresh() {
		if (combo == null || combo.isDisposed())
			return;
		
		IDisplayModel displayModel = safeGetDisplayModel(editor);
		combo.setEnabled(displayModel != null);
		
		if (displayModel == null || displayModel.getRootContainer() == null) {
			combo.setText(""); //$NON-NLS-1$
			return;
		}
		
		combo.setItems(getLayoutSizesAsText());
		String currentLayoutSize = getCurrentLayoutSizeAsText();
		int index = combo.indexOf(currentLayoutSize);
		if (index != -1)
			combo.select(index);
		else
			combo.select(0);
		
		int width = computeWidth(combo);
		int height = combo.getSize().y;
		combo.setSize(width, height);
		
		combo.setEnabled(true);
	}
	
	public void handleEditorChanged() {
		IDisplayModel displayModel = null;
		if (editor != null && layoutAreaListener != null) {
			displayModel = safeGetDisplayModel(editor);
			if (displayModel != null)
				displayModel.removeLayoutAreaListener(layoutAreaListener);
		}
		editor = getDesignerEditor();
		displayModel = safeGetDisplayModel(editor);
		if (displayModel != null) {
			displayModel.addLayoutAreaListener(layoutAreaListener = new LayoutAreaConfigurationListener() {
				public void configurationChanging(LayoutAreaConfiguration configuration) {}
				public void configurationChanged(LayoutAreaConfiguration configuration) {
					refresh();
				}
				public void configurationSetChanged(Collection newConfigurations) {
					refresh();
				}
			});
		}
		refresh();
	}

	private String[] getLayoutSizesAsText() {
		IDisplayModel displayModel = safeGetDisplayModel(editor);
		Check.checkState(displayModel != null);
		Collection layouts = displayModel.getLayoutAreaConfigurations();
		String[] sizeStrings = new String[layouts.size()];
		int i = 0;
		for (Iterator iter = layouts.iterator(); iter.hasNext(); i++) {
			LayoutAreaConfiguration layout = (LayoutAreaConfiguration) iter.next();
			sizeStrings[i] = layout.getDisplayName();
		}		
		
		return sizeStrings;
	}
	
	private String getCurrentLayoutSizeAsText() {
		IDisplayModel displayModel = safeGetDisplayModel(editor);
		Check.checkState(displayModel != null);
		LayoutAreaConfiguration currentConfiguration = displayModel.getCurrentConfiguration();
		if (currentConfiguration == null)
			return "<Unknown>";
		return currentConfiguration.getDisplayName();
	}

	private void setCurrentLayout() {
		LayoutAreaConfiguration currentLayout = getCurrentLayout();
		if (currentLayout != null) {
			boolean ok = true;
			try {
				IDisplayModel displayModel = safeGetDisplayModel(editor);
				if (displayModel != null)
					displayModel.setCurrentConfiguration(currentLayout);
			}
			catch (CoreException x) {
				Logging.showErrorDialog(null, x.getMessage(), x.getStatus());
				ok = false;
			}
			if (ok) {
				// save in the plugin settings
				UIModelPlugin.getDefault().getPreferenceStore().setValue(
						EditorUtils.LAST_CONFIG, currentLayout.getID());
			}
		}
	}
	
	private LayoutAreaConfiguration getCurrentLayout() {
		if (combo == null || combo.isDisposed())
			return null;
		
		IDisplayModel displayModel = safeGetDisplayModel(editor);
		if (displayModel == null)
			return null;
		
		Collection layouts = displayModel.getLayoutAreaConfigurations();
		String displayString = combo.getText();
		for (Iterator iter = layouts.iterator(); iter.hasNext();) {
			LayoutAreaConfiguration layout = (LayoutAreaConfiguration) iter.next();
			if (layout.getDisplayName().equals(displayString)) {
				return layout;
			}
		}
		
		Check.checkContract(false);
		return null;
	}
	
	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(SelectionEvent)
	 */
	private void handleWidgetSelected(SelectionEvent event) {
		setCurrentLayout();
		refresh();
	}
	
	private IDesignerEditor getDesignerEditor() {
		IEditorPart activeEditor = page.getActiveEditor();
		if (activeEditor == null)
			return null;
		
		return (IDesignerEditor) activeEditor.getAdapter(IDesignerEditor.class);
	}
	
	private static IDisplayModel safeGetDisplayModel(IDesignerEditor editor) {
		if (editor == null)
			return null;
		
		return editor.getDisplayModel();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.ContributionItem#dispose()
	 */
	public void dispose() {
		if (partListener != null && page != null) {
			page.removePartListener(partListener);
			partListener = null;
		}
		if (combo != null) {
			combo.dispose();
			combo = null;
		}
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

