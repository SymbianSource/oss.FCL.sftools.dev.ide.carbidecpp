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
package com.nokia.carbide.cpp.internal.project.ui.actions;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.nokia.carbide.cpp.epoc.engine.image.IMultiImageSource;
import com.nokia.carbide.cpp.ui.CarbideUIPlugin;
import com.nokia.carbide.cpp.ui.ICarbideSharedImages;

abstract public class EditMBMMIFMenuEntryActionBase extends ModifyMultiImageSourceActionBase {

	private Map<MenuItem, IPath> menuToTargetPathMap;
	private Menu entryMenu;

	public EditMBMMIFMenuEntryActionBase() {
		menuToTargetPathMap = new HashMap<MenuItem, IPath>();
	}
	
	public void selectionChanged(final IAction action, ISelection selection) {
		if (!(selection instanceof IStructuredSelection) || selection.isEmpty()
				|| ((IStructuredSelection) selection).size() != 1) {
			modelFile = null;
			return;
		}
		
		Object element = ((IStructuredSelection) selection).getFirstElement();
		if (element instanceof IFile) {
			modelFile = (IFile) element;
			action.setEnabled(true);
			action.setMenuCreator(new IMenuCreator() {
				public void dispose() {
					if (entryMenu != null) {
						entryMenu.dispose();
						entryMenu = null;
					}
				}

				public Menu getMenu(Control parent) {
					return null;
				}

				public Menu getMenu(Menu parent) {
					if (entryMenu == null) {
						entryMenu = new Menu(parent);
						entryMenu.addMenuListener(new MenuListener() {

							public void menuHidden(MenuEvent e) {
							}

							public void menuShown(MenuEvent e) {
								fillMenu(entryMenu);
							}
							
						});
					}
					return entryMenu;
				}
				
			});
		} else {
			modelFile = null;
			action.setEnabled(false);
			action.setMenuCreator(null);
		}
	}

	public void run(IAction action) {
		
	}
	
	/** Return the list of multi image sources for the current modelFile */
	abstract List<IMultiImageSource> getMultiImageSources();
	
	protected void fillMenu(Menu menu) {
		menuToTargetPathMap.clear();
		
		// clear
		MenuItem[] items = menu.getItems();
		for (int i = 0; i < items.length; i++) {
			items[i].dispose();
		}

		boolean empty = true;
		
		List<IMultiImageSource> multiImageSources = getMultiImageSources();
		if (multiImageSources != null) {
			for (IMultiImageSource mis : multiImageSources) {
				empty = false;
				MenuItem item = new MenuItem(menu, SWT.PUSH);
				IPath targetPath = mis.getTargetPath();
				String label;
				String targetFile = mis.getTargetFile(); 
				String extension = new Path(targetFile).getFileExtension();
				if (targetPath != null && !targetPath.isEmpty()) {
					label = MessageFormat.format(
						"{0} ({1})", //$NON-NLS-1$
						new Object[] { targetFile, targetPath.toOSString() }); //$NON-NLS-1$
				} else {
					label = MessageFormat.format(
							"{0}", //$NON-NLS-1$
							new Object[] { targetFile });
				}
				item.setText(label);
				item.setImage(CarbideUIPlugin.getSharedImages().getImage(
						extension == null || extension.toLowerCase().equals("mbm") //$NON-NLS-1$
						? ICarbideSharedImages.IMG_MBM_FILE_16_16
								: ICarbideSharedImages.IMG_MIF_FILE_16_16));
				final IPath targetFilePath = mis.getTargetFilePath();
				item.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						editEntry(targetFilePath);
					}
				});
				
			}
		}
		
		if (empty) {
			MenuItem emptyItem = new MenuItem(menu, SWT.PUSH);
			emptyItem.setText("<No entries>");
			emptyItem.setEnabled(false);
		}
	}

	/** Edit the MBM or MIF with the given target path */
	abstract protected void editEntry(IPath targetPath);

}
