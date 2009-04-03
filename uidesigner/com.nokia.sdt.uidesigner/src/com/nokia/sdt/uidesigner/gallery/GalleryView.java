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
package com.nokia.sdt.uidesigner.gallery;

import com.nokia.sdt.datamodel.*;
import com.nokia.sdt.uidesigner.ui.UIDesignerPlugin;
import com.nokia.sdt.uidesigner.ui.utils.Messages;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;
import com.nokia.sdt.workspace.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.action.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

import java.util.ArrayList;
import java.util.Iterator;

public class GalleryView extends ViewPart implements IOpenListener {
	private GalleryViewer viewer;
	private GalleryContentProvider contentProvider;
	private GalleryLabelProvider labelProvider;
	private Action openAction;
	private Action zoom50Action;
	private Action zoom75Action;
	private Action zoom100Action;
		// values to be restored from memento
	private Float restoreScaling;
	private ArrayList restoreCollapsedProjects;
	
	private static final String TAG_SCALING = "scaling";
	private static final String TAG_COLLAPSED_PROJECT = "collapsedProject";
	private static final String TAG_NAME = "name";
	private static final String HELP_CONTEXT_ID = 
		UIDesignerPlugin.PLUGIN_ID + "." + "galleryViewContext"; //$NON-NLS-1$
		

	/**
	 * The constructor.
	 */
	public GalleryView() {	
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new GalleryViewer(parent);
		contentProvider = new GalleryContentProvider();
		viewer.setContentProvider(contentProvider);
		labelProvider = new GalleryLabelProvider();
		viewer.setLabelProvider(labelProvider);
		viewer.addOpenListener(this);
		
		makeActions();
		hookContextMenu();
		contributeToActionBars();
			
		viewer.setInput(WorkspaceContext.getContext());
		
		restoreCollapsedProjects();
		
		if (restoreScaling != null) {
			viewer.setScaling(restoreScaling.doubleValue());
			restoreScaling = null;
		}
		setMenuChecks();
		WorkbenchUtils.setHelpContextId(viewer.getControl(), HELP_CONTEXT_ID);
	}

	private void restoreCollapsedProjects() {
		if (restoreCollapsedProjects != null) {
			Section[] sections = viewer.getSections();
			if (sections != null) {
				for (int i = 0; i < sections.length; i++) {
					String text = sections[i].getText();
					if (restoreCollapsedProjects.contains(text)) {
						sections[i].setExpanded(false);
					}
				}
			}
			restoreCollapsedProjects = null;
		}
	}
	
	public void dispose() {
		if (contentProvider != null) {
			contentProvider.dispose();
			contentProvider = null;
		}
		if (labelProvider != null) {
			labelProvider.dispose();
			labelProvider = null;
		}
		super.dispose();
	}
	
	private void openSelection(IStructuredSelection selection) {
		for (Iterator iter = selection.iterator(); iter.hasNext();) {
			Object item = iter.next();
			if (item instanceof IDesignerDataModelSpecifier) {
				try {
					IDesignerDataModelSpecifier specifier = (IDesignerDataModelSpecifier) item;
					specifier.openInEditor();
				} catch (CoreException x) {
					IStatus status = Logging.newSimpleStatus(0, IStatus.ERROR, x.getMessage(), x);
					Logging.log(UIDesignerPlugin.getDefault(), status);
					Logging.showErrorDialog(null, x.getMessage(), status);
				}
			}
		}
	}		
	
	/**
	 * Respond to open event (double-click) from viewer
	 */
	public void open(OpenEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		openSelection(selection);
	}

	private void asyncRefresh() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				// we need to save and restore collapsed state around refresh
				Section[] sections = viewer.getSections();
				if (sections != null) {
					restoreCollapsedProjects = new ArrayList();
					for (int i = 0; i < sections.length; i++) {
						if (!sections[i].isExpanded()) {
							restoreCollapsedProjects.add(sections[i].getText());
						}
					}
				}
				viewer.refresh();
				restoreCollapsedProjects();
			}
		});
	}	
	
	private void asyncRefresh1Model(final IDesignerDataModelSpecifier modelSpecifier) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				LabeledImage li = viewer.findControlForModelObject(modelSpecifier);
				if (li != null) {
					Image snapshot = modelSpecifier.getSnapshot();
					if (snapshot != null) {
						li.setImage(snapshot);
						li.redraw();
						li.getParent().layout(true);
						li.getParent().pack();
						viewer.layoutForm();
					}
					else { // shouldn't happen, but update view
						viewer.refresh();
					}
				}
			}
		});
	}	
	
	private void setMenuChecks() {
		double currentScaling = viewer.getScaling();
		if (currentScaling == 0.5) {
			zoom50Action.setChecked(true);
			zoom75Action.setChecked(false);
			zoom100Action.setChecked(false);
		}
		else if (currentScaling == 0.75) {
			zoom50Action.setChecked(false);
			zoom75Action.setChecked(true);
			zoom100Action.setChecked(false);
			}
		else if (currentScaling == 1.0) {
			zoom50Action.setChecked(false);
			zoom75Action.setChecked(false);
			zoom100Action.setChecked(true);
		}
		else {
			currentScaling = GalleryViewer.DEFAULT_SCALE;
			setMenuChecks();
		}
	}
	
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
		addViewMenuItems(bars.getMenuManager());
	}

	private void addViewMenuItems(IMenuManager menuManager) {
		menuManager.add(zoom50Action);
		menuManager.add(zoom75Action);
		menuManager.add(zoom100Action);
	}
	
	private void fillContextMenu(IMenuManager manager) {
		ISelection selection = viewer.getSelection();
		if (selection != null && !selection.isEmpty()) {
			manager.add(openAction);
			manager.add(new Separator());
		}
		manager.add(zoom50Action);
		manager.add(zoom75Action);
		manager.add(zoom100Action);
		manager.add(new Separator());
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
	/*
		manager.add(zoom50Action);
		manager.add(zoom75Action);
		manager.add(zoom100Action);
		manager.add(new Separator());
	*/
	}

	private void makeActions() {
		zoom50Action = new Action(Messages.getString("GalleryView.2"), IAction.AS_RADIO_BUTTON) { //$NON-NLS-1$
			public void run() {
				viewer.setScaling(0.5);
			}
		};
		zoom50Action.setToolTipText(Messages.getString("GalleryView.3")); //$NON-NLS-1$
		
		zoom75Action = new Action(Messages.getString("GalleryView.4"), IAction.AS_RADIO_BUTTON) { //$NON-NLS-1$
			public void run() {
				viewer.setScaling(0.75);
			}
			};
		zoom75Action.setToolTipText(Messages.getString("GalleryView.5")); //$NON-NLS-1$
		
		zoom100Action = new Action(Messages.getString("GalleryView.6"), IAction.AS_RADIO_BUTTON) { //$NON-NLS-1$
			public void run() {
				viewer.setScaling(1.0);
			}
		};
		zoom100Action.setToolTipText(Messages.getString("GalleryView.7")); //$NON-NLS-1$
	
		openAction = new Action(Messages.getString("GalleryView.8")) { //$NON-NLS-1$
			public void run() {
				ISelection selection = viewer.getSelection();
				if (selection instanceof IStructuredSelection) {
					openSelection((IStructuredSelection) selection);
				}
			}
		};
		openAction.setToolTipText(Messages.getString("GalleryView.9")); //$NON-NLS-1$
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.setFocus();
	}
	
	class GalleryContentProvider implements ITreeContentProvider {
		private IWorkspaceContextListener wsListener;
		private IDesignerDataModelListener dmListener;
		
		GalleryContentProvider() {
			wsListener = new WorkspaceContextAdapter() {
				public void projectContextAdded(IProjectContext projectContext) {
					asyncRefresh();
				}
				public void projectContextChanged(IProjectContext projectContext) {
					asyncRefresh();
				}
				public void projectContextRemoved(IProjectContext projectContext) {
					asyncRefresh();
				}
			};
			WorkspaceContext wc = WorkspaceContext.getContext();
			wc.addListener(wsListener);
			dmListener = new DesignerDataModelAdapter() {
				public void dataModelChanged(IDesignerDataModelSpecifier modelSpecifier) {
					asyncRefresh1Model(modelSpecifier);
				}
			};
			DesignerDataModelNotifier.instance().addListener(dmListener);
		}
		
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		
		public void dispose() {
			if (wsListener != null) {
				WorkspaceContext wc = WorkspaceContext.getContext();
				wc.removeListener(wsListener);
			}
			if (dmListener != null) {
				DesignerDataModelNotifier.instance().removeListener(dmListener);
			}
		}
		
		public Object[] getElements(Object parent) {
			return getChildren(parent);
		}
		
		public Object getParent(Object child) {
			Object result = null;
			if (child instanceof IProjectContext) {
				result = WorkspaceContext.getContext();
			}
			else if (child instanceof IDesignerDataModelSpecifier) {
				result = ((IDesignerDataModelSpecifier)child).getProjectContext();
			}
			return result;
		}
		
		private boolean projectHasDesigns(IProjectContext project) {
			boolean result = false;
			IDesignerDataModelSpecifier[] modelSpecifiers = project.getModelSpecifiers();
			if (modelSpecifiers != null) {
				for (int i = 0; i < modelSpecifiers.length; i++) {
					if (modelSpecifiers[i].isUIDesign()) {
						result = true;
						break;
					}
				}
			}
			return result;
		}
		
		public Object[] getChildren(Object parent) {
			ArrayList result = new ArrayList();
			WorkspaceContext wc = WorkspaceContext.getContext();
			if (parent.equals(wc)) {
				// only return projects that have openable designs
				IProjectContext[] projects = wc.getProjects();
				if (projects != null) {
					for (int i = 0; i < projects.length; i++) {					
						if (projectHasDesigns(projects[i])) {
							result.add(projects[i]);
						}
					}
				}
			}
			else if (parent instanceof IProjectContext) {
				IProjectContext pc = (IProjectContext) parent;
				IDesignerDataModelSpecifier[] specs = pc.getModelSpecifiers();
				if (specs != null) {
					for (int i = 0; i < specs.length; i++) {
						IDesignerDataModelSpecifier specifier = specs[i];
						if (specifier.isUIDesign())
							result.add(specifier);
					}
				}
			}
			return result.toArray();
		}
		
		public boolean hasChildren(Object parent) {
			Object[] children = getChildren(parent);
			return children != null && children.length > 0;
		}
	}
	
	class GalleryLabelProvider extends LabelProvider {

		public String getText(Object obj) {
			String result = null;
			if (obj instanceof IProjectContext) {
				IProjectContext pc = (IProjectContext) obj;
				result = pc.getProject().getName();
			}
			else if (obj instanceof IDesignerDataModelSpecifier) {
				IDesignerDataModelSpecifier spec = (IDesignerDataModelSpecifier) obj;
				result = spec.getDisplayName();
			}
			return result;
		}
		
		public Image getImage(Object obj) {
			Image result = null;
			if (obj instanceof IDesignerDataModelSpecifier) {
				IDesignerDataModelSpecifier spec = (IDesignerDataModelSpecifier) obj;
				result = spec.getSnapshot();
			}
			return result;
		}
	}

	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site, memento);
		restoreState(memento);
	}

	public void saveState(IMemento memento) {
		super.saveState(memento);
		
		if (viewer != null) {
			memento.putFloat(TAG_SCALING, (float)viewer.getScaling());
			
			// The default state for a section is expanded, so we only need
			// to record which are collapsed.
			Section[] sections = viewer.getSections();
			if (sections != null) {
				for (int i = 0; i < sections.length; i++) {
					if (!sections[i].isExpanded()) {
						IMemento child = memento.createChild(TAG_COLLAPSED_PROJECT);
						child.putString(TAG_NAME, sections[i].getText());
					}
				}
			}
		}
	}
	
	private void restoreState(IMemento memento) {
		if (memento != null) {
			restoreScaling = memento.getFloat(TAG_SCALING);
			IMemento[] children = memento.getChildren(TAG_COLLAPSED_PROJECT);
			if (children != null) {
				restoreCollapsedProjects = new ArrayList();
				for (int i = 0; i < children.length; i++) {
					String projectName = children[i].getString(TAG_NAME);
					restoreCollapsedProjects.add(projectName);
				}
			}
		}
	}
}