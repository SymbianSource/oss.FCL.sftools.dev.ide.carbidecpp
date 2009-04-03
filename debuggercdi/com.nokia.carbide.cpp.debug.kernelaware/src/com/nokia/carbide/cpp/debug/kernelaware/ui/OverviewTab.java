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
package com.nokia.carbide.cpp.debug.kernelaware.ui;

import com.freescale.cdt.debug.cw.core.os.IOSDataManager;
import com.nokia.carbide.cpp.debug.kernelaware.*;

import cwdbg.EclipseDEConstants;

import org.eclipse.debug.core.DebugException;
import org.eclipse.jface.action.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class OverviewTab {

	public class OverviewDataProvider implements ITreeContentProvider {

		private OverviewTabPseudoNode[] rootNodes = {
				new OverviewTabPseudoNode(
						EclipseDEConstants.J_OSObjectType_Process, Messages.getString("OverviewTab.ProcessesLabel"), //$NON-NLS-1$
						null /* ignore */),
				new OverviewTabPseudoNode(
						EclipseDEConstants.J_OSObjectType_Library, Messages.getString("OverviewTab.LibrariesLabel"), //$NON-NLS-1$
						null /* ignore */) };

		private OSDataManager input;

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
		 */
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		public void dispose() {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
		 *      java.lang.Object, java.lang.Object)
		 */
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			if (newInput == null)
				input = null;
			else if (newInput instanceof OSDataManager)
				input = (OSDataManager) newInput;
		}

		public Object[] getChildren(Object parentElement) {
			if (input == null)
				return new Object[0];

			if (parentElement instanceof IOSDataManager)
				return rootNodes;
			
			// Under a Process node, we put two pseudo nodes: "Threads" and
			// "Chunks".
			if (parentElement instanceof OSObjectProcess) {
				OverviewTabPseudoNode pNodes[] = new OverviewTabPseudoNode[2];

				pNodes[0] = new OverviewTabPseudoNode(
						EclipseDEConstants.J_OSObjectType_Thread, Messages.getString("OverviewTab.ThreadsLabel"), //$NON-NLS-1$
						parentElement);
				pNodes[1] = new OverviewTabPseudoNode(
						EclipseDEConstants.J_OSObjectType_Chunk, Messages.getString("OverviewTab.ChunksLabel"), //$NON-NLS-1$
						parentElement);
				return pNodes;
			}

			if (parentElement instanceof OverviewTabPseudoNode) 
			synchronized (input)
			{

				OverviewTabPseudoNode pNode = (OverviewTabPseudoNode) parentElement;

				int originalDataFlags = input.getUpdatedFlag();
				// always use cache (instead of refreshing from target) so that
				// 1. In stop mode, while target is running, user can view "stale"
				//    data in the tree instead of getting exception "cannot get
				//    data while running"
				// 2. Refreshing only happens in two cases: auto-refresh and user-refresh.
				input.setUpdatedFlag(0xFFFF);
				
				try {
					switch (pNode.getType()) {
					case EclipseDEConstants.J_OSObjectType_Process:
						return input.getProcesses();
					case EclipseDEConstants.J_OSObjectType_Library:
						return input.getLibraries();
					case EclipseDEConstants.J_OSObjectType_Thread:
						// Thread pseudo node under a process.
						return input
								.getThreadsForProcess((OSObjectProcess) pNode
										.getParent());
					case EclipseDEConstants.J_OSObjectType_Chunk:
						// Chunk pseudo node under a process.
						return input
								.getChunksForProcess((OSObjectProcess) pNode
										.getParent());
					default:
						assert (false);
						break;
					}
				} catch (DebugException e) {
					e.printStackTrace();
				}
				finally {
					input.setUpdatedFlag(originalDataFlags);
				}
			}

			return new Object[0];
		}

		// Is this method really needed ?
		public Object getParent(Object element) {
			if (element instanceof OverviewTabPseudoNode) {
				OverviewTabPseudoNode pNode = (OverviewTabPseudoNode) element;
				if (pNode.getParent() == null)
					return input;
				else
					return pNode.getParent();
			} else if (element instanceof OSObjectProcess)
				return rootNodes[0];

			return null;
		}

		public boolean hasChildren(Object element) {
			return getChildren(element).length > 0;
		}
	}

	class OverviewItemFilter extends ItemFilter {
		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			if (element instanceof OverviewTabPseudoNode)
				return true;

			// We only filter the first level objects (process & library)
			if (element instanceof OSObjectThread
					|| element instanceof OSObjectChunk)
				return true;

			return match(element.toString());
		}
	}

	private Text filterText;
    private ToolBarManager clearFilterToolBar;


	private OverviewItemFilter nameFilter = new OverviewItemFilter();

	private TreeViewer viewer;

	public OverviewTab() {
	}

	public TreeViewer createControl(TabFolder tabFolder, TabItem tabItem) {
		final Composite composite = new Composite(tabFolder, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.marginWidth = 0;
		gridLayout.marginTop = 5;
		gridLayout.marginHeight = 0;
		gridLayout.numColumns = 3;
		composite.setLayout(gridLayout);
		tabItem.setControl(composite);

		final Label filterLabel = new Label(composite, SWT.NONE);
		final GridData gridData = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
		gridData.horizontalIndent = 5;
		filterLabel.setLayoutData(gridData);
		filterLabel.setText(Messages.getString("SymbianOSView.NameFilterLabel")); //$NON-NLS-1$

		filterText = new Text(composite, SWT.SINGLE | SWT.BORDER | SWT.SEARCH);
		filterText.setText(Messages.getString("SymbianOSView.NameFilterInitialText")); //$NON-NLS-1$
		filterText.setToolTipText(Messages.getString("SymbianOSView.NameFilterToolTip")); //$NON-NLS-1$
		final GridData gd_filterText = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		gd_filterText.widthHint = 200;
		filterText.setLayoutData(gd_filterText);
		clearFilterToolBar = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL);
		clearFilterToolBar.createControl(composite);

		IAction clearTextAction = new Action("", IAction.AS_PUSH_BUTTON) {//$NON-NLS-1$
			public void run() {
				filterText.setText(""); //$NON-NLS-1$
				FilterItems();
			}
		};
		clearTextAction.setToolTipText(Messages.getString("SymbianOSView.ClearToolTip")); //$NON-NLS-1$
		clearTextAction.setImageDescriptor(SymbianOSView.clearImageDesc);
		clearTextAction.setDisabledImageDescriptor(SymbianOSView.clearImageDesc);
		clearFilterToolBar.add(clearTextAction);
		clearFilterToolBar.update(false);
        // initially there is no text to clear
		clearFilterToolBar.getControl().setVisible(false);
		
		filterText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				FilterItems();
				clearFilterToolBar.getControl().setVisible(filterText.getText().length() > 0);
			}
		});
		filterText.addFocusListener(new FocusAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.swt.events.FocusListener#focusLost(org.eclipse.swt.events.FocusEvent)
			 */
			public void focusGained(FocusEvent e) {
				/*
				 * Running in an asyncExec because the selectAll() does not
				 * appear to work when using mouse to give focus to text.
				 */
				Display display = filterText.getDisplay();
				display.asyncExec(new Runnable() {
					public void run() {
						if (!filterText.isDisposed()) {
							filterText.selectAll();
						}
					}
				});
			}
		});

		viewer = new TreeViewer(composite, SWT.BORDER);
		viewer.setContentProvider(new OverviewDataProvider());
		viewer.setLabelProvider(new OSViewLabelProvider(viewer));
		viewer.setInput(new Object());
		viewer.addFilter(nameFilter);
		Tree tree = viewer.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

		return viewer;
	}

	private void FilterItems() {
		String pattern = filterText.getText();
		if (pattern.length() == 0)
			viewer.removeFilter(nameFilter);
		else {
			nameFilter.setPattern(pattern);

			if (viewer.getFilters().length == 0)
				viewer.addFilter(nameFilter);
			else
				viewer.refresh();
		}
	}
}
