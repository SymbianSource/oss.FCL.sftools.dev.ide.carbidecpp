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

import org.eclipse.cdt.debug.core.model.ICDebugTarget;
import org.eclipse.cdt.debug.core.model.ICStackFrame;
import org.eclipse.cdt.debug.core.model.ICThread;
import org.eclipse.cdt.debug.internal.core.model.CDebugElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.INullSelectionListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.freescale.cdt.debug.cw.core.CWPlugin;
import com.freescale.cdt.debug.cw.core.cdi.Session;
import com.nokia.carbide.cpp.debug.kernelaware.IDataChangedListener;
import com.nokia.carbide.cpp.debug.kernelaware.IOSObjectProperties;
import com.nokia.carbide.cpp.debug.kernelaware.KernelawarePlugin;
import com.nokia.carbide.cpp.debug.kernelaware.OSDataManager;
import com.nokia.carbide.cpp.debug.kernelaware.OSObjectProcess;
import com.nokia.carbide.cpp.debug.kernelaware.OSObjectThread;
import com.nokia.carbide.cpp.internal.featureTracker.FeatureUseTrackerConsts;
import com.nokia.carbide.cpp.internal.featureTracker.FeatureUseTrackerPlugin;

import cwdbg.EclipseDEConstants;

public class SymbianOSView extends ViewPart implements ISelectionListener,
		INullSelectionListener, IDataChangedListener, Runnable,
		IDebugEventSetListener {

	/*
	 * Help context ID. Should be: PluginID + "." + <words without '.'>
	 */
	private static final String HelpID_Prefix = KernelawarePlugin
			.getUniqueIdentifier()
			+ "."; //$NON-NLS-1$

	public static final String s_HelpID_View = HelpID_Prefix
			+ "symbian_os_data_view"; //$NON-NLS-1$

	public static final String s_HelpID_OverviewTab = HelpID_Prefix
			+ "symbian_os_data_view_overview_tab"; //$NON-NLS-1$

	public static final String s_HelpID_ProcessTab = HelpID_Prefix
			+ "symbian_os_data_view_process_tab"; //$NON-NLS-1$

	public static final String s_HelpID_ThreadTab = HelpID_Prefix
			+ "symbian_os_data_view_thread_tab"; //$NON-NLS-1$

	public static final String s_HelpID_ChunkTab = HelpID_Prefix
			+ "symbian_os_data_view_chunk_tab"; //$NON-NLS-1$

	public static final String s_HelpID_LibraryTab = HelpID_Prefix
			+ "symbian_os_data_view_library_tab"; //$NON-NLS-1$

    public static final ImageDescriptor clearImageDesc = AbstractUIPlugin.imageDescriptorFromPlugin(
    		PlatformUI.PLUGIN_ID, "$nl$/icons/full/etool16/clear_co.gif"); //$NON-NLS-1$

	private TabItem m_overviewTabItem;

	private TabItem m_processTabItem;

	private TabItem m_threadTabItem;

	private TabItem m_chunkTabItem;

	private TabItem m_libraryTabItem;

	private TreeViewer m_overviewTreeViewer;

	private TableViewer m_processTableViewer;

	private TableViewer m_threadTableViewer;

	private TableViewer m_chunkTableViewer;

	private TableViewer m_libraryTableViewer;

	private StructuredViewer m_currentViewer;

	private Session m_currentSession = null;

	private Action m_actionRefresh;

	private Action m_actionToggleAutoRefresh;

	private Action m_actionDebug;

	private Action m_actionProperties;

	private Action m_actionSetTimer;

	private Action m_actionCollapseAll;

	private Action m_actionDoubleClick;

	// in seconds.
	private int m_timedRefreshInterval = 20;

	// Is the view closed by user ?
	private boolean m_disposed;

	// Make this option static so that it's consistent across view sessions.
	static boolean m_autoRefresh = true;

	private TabFolder tabFolder;
	
	private int refreshCount;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.freescale.cdt.debug.cw.core.ui.os.OSView#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {

		tabFolder = new TabFolder(parent, SWT.NONE);
		tabFolder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				DoAction_TabSelection(e.item);
			}
		});

		ImageDescriptor imageDesc;

		/***********************************************************************
		 * Overview (treeview) tab
		 */
		m_overviewTabItem = new TabItem(tabFolder, SWT.NONE);
		m_overviewTabItem
				.setToolTipText(Messages.getString("SymbianOSView.OverViewToolTip")); //$NON-NLS-1$
		m_overviewTabItem.setText(Messages.getString("SymbianOSView.OverViewLabel")); //$NON-NLS-1$
		imageDesc = KernelawarePlugin
				.getImageDescriptor("icons//tab_overview.png"); //$NON-NLS-1$
		m_overviewTabItem.setImage(imageDesc.createImage());

		m_overviewTreeViewer = (new OverviewTab()).createControl(tabFolder,
				m_overviewTabItem);

		/***********************************************************************
		 * Process Tab
		 */
		m_processTabItem = new TabItem(tabFolder, SWT.NONE);
		m_processTabItem
				.setToolTipText(Messages.getString("SymbianOSView.ProcessesToolTip")); //$NON-NLS-1$
		m_processTabItem.setText(Messages.getString("SymbianOSView.ProcessesLabel")); //$NON-NLS-1$
		imageDesc = KernelawarePlugin
				.getImageDescriptor("icons//tab_process.gif"); //$NON-NLS-1$
		m_processTabItem.setImage(imageDesc.createImage());

		m_processTableViewer = (new ProcessTab()).createControl(tabFolder,
				m_processTabItem);

		/***********************************************************************
		 * Thread Tab
		 */
		m_threadTabItem = new TabItem(tabFolder, SWT.NONE);
		m_threadTabItem.setToolTipText(Messages.getString("SymbianOSView.ThreadsToolTip")); //$NON-NLS-1$
		m_threadTabItem.setText(Messages.getString("SymbianOSView.ThreadsLabel")); //$NON-NLS-1$
		imageDesc = KernelawarePlugin
				.getImageDescriptor("icons//tab_thread.gif"); //$NON-NLS-1$
		m_threadTabItem.setImage(imageDesc.createImage());

		m_threadTableViewer = (new ThreadTab()).createControl(tabFolder,
				m_threadTabItem);

		/***********************************************************************
		 * Chunk Tab
		 */
		m_chunkTabItem = new TabItem(tabFolder, SWT.NONE);
		m_chunkTabItem.setToolTipText(Messages.getString("SymbianOSView.ChunksToolTip")); //$NON-NLS-1$
		m_chunkTabItem.setText(Messages.getString("SymbianOSView.ChunksLabel")); //$NON-NLS-1$
		imageDesc = KernelawarePlugin
				.getImageDescriptor("icons//tab_chunk.gif"); //$NON-NLS-1$
		m_chunkTabItem.setImage(imageDesc.createImage());

		m_chunkTableViewer = (new ChunkTab()).createControl(tabFolder,
				m_chunkTabItem);

		/***********************************************************************
		 * Library Tab
		 */
		m_libraryTabItem = new TabItem(tabFolder, SWT.NONE);
		m_libraryTabItem
				.setToolTipText(Messages.getString("SymbianOSView.LibrariesToolTip")); //$NON-NLS-1$
		m_libraryTabItem.setText(Messages.getString("SymbianOSView.LibrariesLabel")); //$NON-NLS-1$
		imageDesc = KernelawarePlugin
				.getImageDescriptor("icons//tab_library.gif"); //$NON-NLS-1$
		m_libraryTabItem.setImage(imageDesc.createImage());

		m_libraryTableViewer = (new LibraryTab()).createControl(tabFolder,
				m_libraryTabItem);

		/***********************************************************************
		 * Other View setting
		 */
		// Default current tab.
		tabFolder.setSelection(0);

		// Default selection provider.
		// add a viewer as selection provider of the view, so that selected item
		// in the viewer can have its properties displayed in Properties View.
		m_currentViewer = m_overviewTreeViewer;
		getSite().setSelectionProvider(m_currentViewer);

		// listen to selection in debug view
		getSite().getPage().addSelectionListener(
				IDebugUIConstants.ID_DEBUG_VIEW, this);

		/***********************************************************************
		 * Help context IDs.
		 */
		PlatformUI.getWorkbench().getHelpSystem()
				.setHelp(parent, s_HelpID_View);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(
				m_overviewTabItem.getControl(), s_HelpID_OverviewTab);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(
				m_processTabItem.getControl(), s_HelpID_ProcessTab);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(
				m_threadTabItem.getControl(), s_HelpID_ThreadTab);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(
				m_chunkTabItem.getControl(), s_HelpID_ChunkTab);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(
				m_libraryTabItem.getControl(), s_HelpID_LibraryTab);

		/***********************************************************************
		 * Actions
		 */
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();

		hookSelectionChangedListeners();

		m_disposed = false;

		// If there is already a debug session running, try to show OS data for
		// it.
		Session currSelectedSession = Session.getUIFocusSession();
		if (currSelectedSession != null) {
			// getUIFocusSession() above may give us a dead session. Excluse
			// that case.
			if (currSelectedSession.isActive()){
				try {
					showDataForDevice(currSelectedSession);
				} 
				catch (InterruptedException e) {
					e.printStackTrace();		
				}
			}
		}

		updateActionStatus();

		DebugPlugin.getDefault().addDebugEventListener(this);
	}

	public void dataDirty() {
		// Must run this in UI thread.
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				showDataAsStale(true);
			}
		});
	};

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				SymbianOSView.this.fillContextMenu(manager);
			}
		});

		// Hook up context menu for each of the viewers.
		//
		Menu menu;
		StructuredViewer viewer = m_overviewTreeViewer;
		menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);

		viewer = m_processTableViewer;
		menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);

		viewer = m_threadTableViewer;
		menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);

		viewer = m_chunkTableViewer;
		menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);

		viewer = m_libraryTableViewer;
		menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	/**
	 * Hook up listeners for selection change in the view.
	 */
	private void hookSelectionChangedListeners() {
		ISelectionChangedListener listener = new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				// Update status of commands.
				updateActionStatus();
			}
		};

		m_overviewTreeViewer.addSelectionChangedListener(listener);
		m_processTableViewer.addSelectionChangedListener(listener);
		m_threadTableViewer.addSelectionChangedListener(listener);
		m_chunkTableViewer.addSelectionChangedListener(listener);
		m_libraryTableViewer.addSelectionChangedListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.ViewPart#init(org.eclipse.ui.IViewSite,
	 *      org.eclipse.ui.IMemento)
	 */
	@Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site, memento);
		FeatureUseTrackerPlugin.getFeatureUseProxy().startUsingFeature(FeatureUseTrackerConsts.CARBIDE_KERNELAWARE);
	}

	@Override
	public void dispose() {
		// Must do the following, otherwise a zombie will exist after user close
		// the view.
		//
		m_disposed = true;

		getSite().setSelectionProvider(null);

		getSite().getPage().removeSelectionListener(
				IDebugUIConstants.ID_DEBUG_VIEW, this);

		DebugPlugin.getDefault().removeDebugEventListener(this);

		// remove the timed task.
		disableTimedRefresh();

		FeatureUseTrackerPlugin.getFeatureUseProxy().stopUsingFeature(FeatureUseTrackerConsts.CARBIDE_KERNELAWARE);

		super.dispose();
	}

	@Override
	public void setFocus() {
		// Make sure the current tab get the focus so that help system can
		// get the correct context ID for the focused control so as to display
		// matching context-sensitive help......12/18/06
		m_currentViewer.getControl().setFocus();
	}

	// This is called when selection in workbench changes.
	// We only honor the change in Debug View.
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (!(selection instanceof IStructuredSelection))
			return;

		IStructuredSelection structSel = (IStructuredSelection) selection;
		if (structSel.size() != 1)
			return;

		Object sel = structSel.getFirstElement();

		// Allow StackFrame & thread for now.
		// If we hornor selection of CDebugTarget, debugger may freeze on start
		// when the OS View is open before debugger starts.
		// Figure out a solution later..... 11/29/06
		// Not a problem any more in Eclipse 3.3 + CDT4.0....... 11/15/07
		//
		if (!(sel instanceof ICStackFrame || sel instanceof ICThread || sel instanceof ICDebugTarget))
			return;

		Session newSession = Session.getSessionFromDebugContext(sel);

		if (newSession == null) // how come ?
			return;

		/* A terminated session, or a special case:
		 * Start the first debug session while the OS View is open, the
		 * CDT Target (process) is created and auto selected in Debug View
		 * but corresponding CWProcess is not created yet. In such case
		 * we should not try to refresh. Part of fix to bug 5320.. 12/04/07
		 */  
		if (! newSession.isActive())
			return;
		
		/*
		 * If user just selects a different item (stackframe, thread, process)
		 * in the same session, and the session's OS data is not dirty, don't
		 * bother to update.
		 */
		boolean refresh = false;

		if (m_currentSession == null) {
			refresh = true;
		}
		else if (!m_currentSession.equals(newSession))
			// Different session is selected (by user).
			refresh = true;
		else {
			// The same session is selected (by user or automatically done when debuggee
			// is suspended). Refresh only when data is dirty and AutoRefresh is
			// turned on.
			if (newSession.getOSDataManager().isDataDirty() && m_autoRefresh) {
				refresh = true;

				// Check this for stop mode debug. Fix bug 3467...03/28/07
				if (newSession.isSystemModeDebug()
						&& newSession.isSystemRunning())
					refresh = false;
			}
		}

		if (refresh){
			try {
				showDataForDevice(newSession);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();		
			}
		}	
	}

	/**
	 * Asynchronously get the OS data for the given session. Show progress and
	 * allow user cancel.
	 * 
	 * @param session -
	 *            for which session (i.e. machine) to get the OS data.
	 * @param listener -
	 *            who's interested in finish event.
	 * @param userInitiated -
	 *            is the compute initiated by user or automatically by program.
	 */
	private void computeInput(final Session session,
			final IDataChangedListener listener, final boolean userInitiated) {

		// Note the job name is also referenced in Target.java.
		// So watch out when changing it !....04/07/07
		Job refreshJob = new Job(Messages.getString("SymbianOSView.RefreshDataTitle")) { //$NON-NLS-1$
			protected IStatus run(IProgressMonitor monitor) {
				IStatus status = Status.OK_STATUS;
				try {
					OSDataManager osDM = (OSDataManager) session
							.getOSDataManager();

					if (osDM != null) {
						/*
						 * For crash debugger, the refreshing is time consuming
						 * (more than one minute at present). So never do
						 * auto-refresh for it, only user-initiated refreshing
						 * is allowed. This way startup of crash debugger won't
						 * be slow.... [LW_20070213] 02/13/07
						 */
						if (!(session.getLaunchTypeName().contains("crash") && !userInitiated)) //$NON-NLS-1$
							status = osDM.updateAll(monitor);
					}
				} catch (DebugException e) {
					status = new Status(IStatus.ERROR, KernelawarePlugin
							.getUniqueIdentifier(), 0, e.getMessage(), null);
				}

				listener.dataUpdated(session, status);
				refreshCount++;

				monitor.done();

				return status;
			}
		};

		refreshJob.setUser(userInitiated);
		refreshJob.setPriority(Job.LONG);
		refreshJob.schedule();
	}

	/**
	 * Given a session (namely a machine, or device), display its OS data in the
	 * view.
	 * 
	 * @param newSession
	 */
	private void showDataForDevice(Session newSession) throws InterruptedException {
		// Make the view be listener for dataChanged event from the underlying
		// session.
		if (m_currentSession != null)
			((OSDataManager) m_currentSession.getOSDataManager())
					.removeDataChangedListener(this);
	
		((OSDataManager) newSession.getOSDataManager())
				.addDataChangedListener(this);

		// User selects a debug session of ours.
		m_currentSession = newSession;

		// This fixes the timing issue with attaching to a process @bug 7580 
		Thread.sleep(500);
		
		updateActionStatus();

		// if the new session is for stop mode, disable timer for auto-refresh.
		// ...... 12/03/06
		if (newSession.isSystemModeDebug()) {
			// Stop the timed task.
			disableTimedRefresh();
		}
		// The scheduled refresh may conflict with the computeInput() 
		// below and result in deadlock. 
		// Actually the timed refresh will be scheduled in computeInput().
		// So don't do this here......... 12/04/2007 
		//		else if (newSession.isActive())	// Don't if the session is a terminated one 
		//			scheduleTimedRefresh();

		showDataAsStale(true);

		computeInput(m_currentSession, this, false);

		/*
		 * Hide Chunk and Library tabs per debug session type. But I don't see
		 * an easy way in SWT yet.........Nov. 2006
		 */
	}

	private void packTableViewer(TableViewer viewer) {
		TableColumn[] cols = viewer.getTable().getColumns();
		for (int i = 0; i < cols.length; i++)
			if (cols[i].getResizable() != false)
				cols[i].pack();
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillContextMenu(IMenuManager manager) {
		if (m_currentViewer == null)
			return;

		ISelection selection = m_currentViewer.getSelection();
		if (selection == null)
			return;

		Object obj = ((IStructuredSelection) selection).getFirstElement();
		if (obj == null)
			return;

		// Add "debug" command if selection is a process or thread.
		if (obj instanceof OSObjectProcess || obj instanceof OSObjectThread) {
			manager.add(m_actionDebug);
		}

		// For all others, add "properties" command.
		manager.add(m_actionProperties);

		// Other plug-ins can contribute their actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(m_actionRefresh);
		manager.add(m_actionToggleAutoRefresh);
		manager.add(m_actionDebug);
		manager.add(new Separator());
		manager.add(m_actionProperties);
		manager.add(m_actionSetTimer);
		manager.add(m_actionCollapseAll);
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(m_actionRefresh);
		manager.add(m_actionToggleAutoRefresh);
		manager.add(m_actionDebug);
		manager.add(m_actionProperties);
		manager.add(m_actionSetTimer);
		manager.add(m_actionCollapseAll);
	}

	private void makeActions() {
		m_actionRefresh = new Action(Messages.getString("SymbianOSView.RefreshActionLabel"), IAction.AS_PUSH_BUTTON) { //$NON-NLS-1$
			public void run() {
				DoAction_Refresh();
			}
		};
		m_actionRefresh.setToolTipText(Messages.getString("SymbianOSView.RefreshActionToolTip")); //$NON-NLS-1$
		m_actionRefresh.setImageDescriptor(KernelawarePlugin
				.getImageDescriptor("icons//refresh.gif")); //$NON-NLS-1$

		m_actionToggleAutoRefresh = new Action(Messages.getString("SymbianOSView.AutoRefreshActionLabel"), //$NON-NLS-1$
				IAction.AS_CHECK_BOX) {
			public void run() {
				DoAction_ToggleAutoRefresh();
			}
		};
		m_actionToggleAutoRefresh
				.setToolTipText(Messages.getString("SymbianOSView.AutoRefreshActionToolTip")); //$NON-NLS-1$
		m_actionToggleAutoRefresh.setChecked(m_autoRefresh);
		m_actionToggleAutoRefresh.setImageDescriptor(KernelawarePlugin
				.getImageDescriptor("icons//auto_refresh.gif")); //$NON-NLS-1$

		m_actionDebug = new Action(Messages.getString("SymbianOSView.DebugActionLabel"), IAction.AS_PUSH_BUTTON) { //$NON-NLS-1$
			public void run() {
				DoAction_Debug();
			}
		};
		m_actionDebug.setToolTipText(Messages.getString("SymbianOSView.DebugActionToolTip")); //$NON-NLS-1$
		m_actionDebug.setImageDescriptor(KernelawarePlugin
				.getImageDescriptor("icons//Debug.png")); //$NON-NLS-1$

		m_actionProperties = new Action(Messages.getString("SymbianOSView.PropertiesActionLabel"), IAction.AS_PUSH_BUTTON) { //$NON-NLS-1$
			public void run() {
				DoAction_Properties();
			}
		};
		m_actionProperties
				.setToolTipText(Messages.getString("SymbianOSView.PropertiesActionToolTip")); //$NON-NLS-1$
		m_actionProperties.setImageDescriptor(KernelawarePlugin
				.getImageDescriptor("icons//properties.gif")); //$NON-NLS-1$

		m_actionSetTimer = new Action(Messages.getString("SymbianOSView.SetTimerActionLabel"), IAction.AS_PUSH_BUTTON) { //$NON-NLS-1$
			public void run() {
				DoAction_SetTimer();
			}
		};
		m_actionSetTimer
				.setToolTipText(Messages.getString("SymbianOSView.SetTimerActionToolTip")); //$NON-NLS-1$
		m_actionSetTimer.setImageDescriptor(KernelawarePlugin
				.getImageDescriptor("icons//set_timer.gif")); //$NON-NLS-1$

		m_actionCollapseAll = new Action(Messages.getString("SymbianOSView.CollapseAllActionLabel"), IAction.AS_PUSH_BUTTON) { //$NON-NLS-1$
			public void run() {
				DoAction_CollapseAll();
			}
		};
		m_actionCollapseAll.setToolTipText(Messages.getString("SymbianOSView.CollapseAllActionToolTip")); //$NON-NLS-1$
		m_actionCollapseAll.setImageDescriptor(KernelawarePlugin
				.getImageDescriptor("icons//collapseall.gif")); //$NON-NLS-1$

		m_actionDoubleClick = new Action() {
			public void run() {
				DoAction_Debug();
			}
		};
	}

	private void hookDoubleClickAction() {
		IDoubleClickListener listener = new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				m_actionDoubleClick.run();
			}
		};

		m_processTableViewer.addDoubleClickListener(listener);
		m_threadTableViewer.addDoubleClickListener(listener);
	}

	private void showMessage(String message) {
		MessageDialog
				.openInformation(m_processTableViewer.getControl().getShell(),
						Messages.getString("SymbianOSView.MessageTitle"), Messages.getString("SymbianOSView.MessagePrefix") + message); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void DoAction_TabSelection(Widget item) {
		if (item == null)
			return;
		
		if (item.equals(m_overviewTabItem)) {
			m_currentViewer = m_overviewTreeViewer;
		} else if (item.equals(m_processTabItem))
			m_currentViewer = m_processTableViewer;
		else if (item.equals(m_threadTabItem))
			m_currentViewer = m_threadTableViewer;
		else if (item.equals(m_chunkTabItem))
			m_currentViewer = m_chunkTableViewer;
		else if (item.equals(m_libraryTabItem))
			m_currentViewer = m_libraryTableViewer;

		getSite().setSelectionProvider(m_currentViewer);

		/*
		 * Now the tab selection has changed.
		 * 
		 * At this time if user selects an item in the tab, the properties of
		 * the item won't be displayed in Properties View. That's because the
		 * above selectionProvider change is not honored until the view loses
		 * and regains focus (a platform bug ?). So we do this: change focus to
		 * any other view (here I choose Variable View as it's most likely
		 * present and in the same pane as this OS View) and then change it back
		 * to this view. This is not a perfect solution, but...
		 * 
		 * 
		 * This is also needed for the help system to get tab-specific context
		 * ID. See setFocus() for more.
		 */
		int viewState_old = IWorkbenchPage.STATE_RESTORED;
		int viewState_new = IWorkbenchPage.STATE_RESTORED;
		IWorkbenchPartReference thisRef = getSite().getPage()
				.getReference(this);
		if (thisRef != null)
			viewState_old = getSite().getPage().getPartState(thisRef);

		try {
			getSite().getPage().showView(IDebugUIConstants.ID_VARIABLE_VIEW);
		} catch (PartInitException e) {
			e.printStackTrace();
		}

		if (thisRef != null)
			viewState_new = getSite().getPage().getPartState(thisRef);
		
		// Put focus back.
		getSite().getPage().activate(this);

		// Restore the state.
		// Added the "new != old" check otherwise the setPartState() would cause
		// NPE in core code in Eclipse 3.3
		if (thisRef != null && viewState_new != viewState_old)
			getSite().getPage().setPartState(thisRef, viewState_old);
		
		// enable/disable actions/commands accordingly.
		updateActionStatus();
	}

	/**
	 * Force refresh of all data in the view by re-reading from the target
	 * device.
	 * 
	 */
	public void DoAction_Refresh() {
		if (m_currentSession == null)
			return;

		disableTimedRefresh();

		m_currentSession.getOSDataManager().setDataDirty();

		computeInput(m_currentSession, this, true);
	}

	public void DoAction_ToggleAutoRefresh() {
		setAutoRefresh(!m_autoRefresh);
	}

	public void setAutoRefresh(boolean enabled) {
		m_autoRefresh = enabled;
	}
	
	public void DoAction_CollapseAll() {
		if (m_overviewTreeViewer != null)
			m_overviewTreeViewer.collapseAll();
	}

	public void DoAction_Debug() {
		/*
		 * Attach debugger to a process or thread.
		 */
		if (m_currentViewer == null)
			return;
		if (m_currentSession == null || !m_currentSession.isActive())
			return;

		ISelection selection = m_currentViewer.getSelection();
		if (selection == null)
			return;

		Object obj = ((IStructuredSelection) selection).getFirstElement();
		if (obj == null)
			return;

		// Only allow attaching to a process
		int procID, threadID;

		String procName = ""; //$NON-NLS-1$
		if (obj instanceof OSObjectProcess) {

			// User select to debug a process
			OSObjectProcess objProcess = (OSObjectProcess) obj;
			procID = objProcess.getID();
			if (m_currentSession.processBeingDebugged(procID)) {
				showMessage(Messages.getString("SymbianOSView.ProcessUnderDebugMsg")); //$NON-NLS-1$
				return;
			}

			procName = objProcess.getName();

			// Just targetting the first thread in the process
			OSObjectThread[] threads = objProcess.getThreads();
			if (threads.length > 0)
				threadID = threads[0].getID();
			else
				// no thread, should not happen.
				return;
		} else if (obj instanceof OSObjectThread) {

			OSObjectThread thread = (OSObjectThread) obj;
			procID = thread.getProcessID();
			if (procID == EclipseDEConstants.J_OSObjectID_Invalid) // owner
				// unknown
				return;
			threadID = thread.getID();

			if (m_currentSession.threadBeingDebugged(procID, threadID)) {
				showMessage(Messages.getString("SymbianOSView.ThreadUnderDebugMsg")); //$NON-NLS-1$
				return;
			}

			if (m_currentSession.processBeingDebugged(procID)) {
				// the owner process is already under debug
				// no need to pass process name to backend.
				procName = ""; //$NON-NLS-1$
			} else {
				procName = (String) thread
						.getRawPropertyValue(IOSObjectProperties.ID.OwningProcessName);
			}
		} else
			// other objects
			return;

		// With TRK, we may get process name like "app[ef1a9bef]0001".
		// Do some adjustment here.
		if (procName.length() > 0) {
			int i = procName.indexOf('[');
			if (i > 0)
				procName = procName.substring(0, i);

			// for a name without extension like "app", add .exe
			if (!procName.contains(".")) //$NON-NLS-N$ //$NON-NLS-1$
				procName = procName + ".exe"; //$NON-NLS-N$ //$NON-NLS-1$
		}

		// Now the real action...
		try {
			m_currentSession.attachToThread(procID, threadID, procName);
		} catch (DebugException e) {
			showMessage(e.getMessage());
		}
	}

	/**
	 * Open Properties View and show property of selected item in current tab.
	 */
	private void DoAction_Properties() {
		if (m_currentViewer == null)
			return;

		ISelection selection = m_currentViewer.getSelection();
		if (selection == null)
			return;

		// Open the Properties view in case it's not.
		try {
			getSite().getPage().showView(
					org.eclipse.ui.IPageLayout.ID_PROP_SHEET);
		} catch (PartInitException e) {
			e.printStackTrace();
		}

		// Change focus back to this view so that the selection in the viewer is
		// displayed in the Properties View.
		getSite().getPage().activate(this);
	}

	/**
	 * Pop up a dialog for user to enter the time interval.
	 */
	private void DoAction_SetTimer() {
		// validator for user input.
		class Validator implements IInputValidator {

			public String isValid(String newText) {
				int i;

				try {
					i = Integer.valueOf(newText);
				} catch (Exception e) {
					return Messages.getString("SymbianOSView.DecimalError"); //$NON-NLS-1$
				}

				if (i < 3 || i > 600)
					return Messages.getString("SymbianOSView.RangeError"); //$NON-NLS-1$

				return null;
			}
		}

		int currentInterval = CWPlugin
				.getDefault()
				.getPluginPreferences()
				.getInt(
						cwdbg.PreferenceConstants.J_PN_OSViewAutoRefreshInterval);

		InputDialog dg = new InputDialog(
				this.getSite().getShell(),
				Messages.getString("SymbianOSView.MessageTitle"), // $NON-NLS-1$ //$NON-NLS-1$
				Messages.getString("SymbianOSView.IntervalMessage"), //$NON-NLS-1$
				Integer.toString(currentInterval), new Validator());

		if (dg.open() == Window.OK) {
			String input = dg.getValue();
			currentInterval = Integer.valueOf(input);

			// Save in our global pref storage.
			CWPlugin.getDefault().getPluginPreferences().setValue(
					cwdbg.PreferenceConstants.J_PN_OSViewAutoRefreshInterval,
					currentInterval);
		}
	}

	/**
	 * Enable/disable actions based on current selection.
	 */
	private void updateActionStatus() {
		boolean enableRefresh = false;
		boolean enableToggleAutoRefresh = true;
		boolean enableDebug = false;
		boolean enableProperties = false;
		boolean enableSetTimer = false;
		boolean enableCollapseAll = false;

		if (m_actionRefresh == null)	// not created yet.
			return;
		
		if (m_currentSession != null && m_currentSession.isActive()) {
			enableRefresh = true;

			if (!m_currentSession.isSystemModeDebug())
				// enable this for TRK debugger only.
				enableSetTimer = true;

			if (m_currentSession.getLaunchTypeName().contains("crash")) //$NON-NLS-1$
				// disable auto-refresh for crash debugger. See my comment
				// [LW_20070213].
				enableToggleAutoRefresh = false;

			if (m_currentViewer != null) {
				
				// tab specific commands
				//
				if (m_currentViewer == m_overviewTreeViewer)
					enableCollapseAll = true;
				
				// Selection specific commands
				//
				ISelection selection = m_currentViewer.getSelection();
				Object obj = ((IStructuredSelection) selection)
						.getFirstElement();
				if (obj != null) {
					// This is true as long as there is a valid selection in the
					// view.
					enableProperties = true;

					if (obj instanceof OSObjectProcess
							|| obj instanceof OSObjectThread)
						enableDebug = true;

				}
			}
		}

		m_actionRefresh.setEnabled(enableRefresh);
		m_actionToggleAutoRefresh.setEnabled(enableToggleAutoRefresh);
		m_actionDebug.setEnabled(enableDebug);
		m_actionProperties.setEnabled(enableProperties);
		m_actionSetTimer.setEnabled(enableSetTimer);
		m_actionCollapseAll.setEnabled(enableCollapseAll);
	}

	/**
	 * Give visual hint on whether the data in the view is stale.
	 * 
	 * @param stale
	 */
	private void showDataAsStale(boolean stale) {
		if (m_disposed)
			return;

		Color co = stale ? PlatformUI.getWorkbench().getDisplay()
				.getSystemColor(SWT.COLOR_DARK_GRAY) : null;

		/*
		 * To show that data is stale, I display them in gray text. But the
		 * grayed out text is kinda hard to read. Another solution is to change
		 * the background to gray, but not ideal either..... 11/30/06
		 */
		// m_overviewTreeViewer.getControl().setBackground(co);
		m_overviewTreeViewer.getControl().setForeground(co);
		m_processTableViewer.getControl().setForeground(co);
		m_threadTableViewer.getControl().setForeground(co);
		m_chunkTableViewer.getControl().setForeground(co);
		m_libraryTableViewer.getControl().setForeground(co);
	}

	private void scheduleTimedRefresh() {
		if (m_currentSession == null || !m_currentSession.isActive())
			return;

		// Not for stop mode debug.
		if (m_currentSession.isSystemModeDebug())
			return;

		// Honor preference settings real time.
		m_timedRefreshInterval = CWPlugin
				.getDefault()
				.getPluginPreferences()
				.getInt(
						cwdbg.PreferenceConstants.J_PN_OSViewAutoRefreshInterval);

		Runnable timedRunnable = this;

		PlatformUI.getWorkbench().getDisplay().timerExec(
				m_timedRefreshInterval * 1000 /* milliseconds */,
				timedRunnable);

	}

	private void disableTimedRefresh() {
		Runnable timedRunnable = this;

		PlatformUI.getWorkbench().getDisplay().timerExec(-1, timedRunnable);
	}

	/**
	 * This is for timed auto-refresh of OS data in the view. Refer to where
	 * Display.timerExec() is invoked.
	 * 
	 * @see java.lang.Runable#run
	 */
	public void run() {
		// View is closed or session terminated
		if (m_disposed || m_currentSession == null)
			return;

		// show as stale.
		showDataAsStale(true);

		// just in case this is cleared
		if (m_currentSession == null)
			return;

		// Refresh data if so requested.
		if (m_autoRefresh) {
			// Mark cache as dirty.
			m_currentSession.getOSDataManager().setDataDirty();

			computeInput(m_currentSession, this, false);
		} else
			scheduleTimedRefresh();
	}

	public void dataUpdated(final Session session, final IStatus status) {
		// User already selected another session during refreshing.
		// Just do nothing. "selectionChanged()" will make sure data for the new
		// session will be displayed.
		if (session != m_currentSession) {
			return;
		}

		final OSDataManager osDM = (OSDataManager) session.getOSDataManager();
		if (osDM == null)
			return;

		/*
		 * Even if we got error, we treat the data as updated so that no more
		 * refreshing request will be handled until next auto-refresh or
		 * user-refresh. This way user won't get repeated error message when he,
		 * say, clicks on different target/thread of the same session in the debug view.
		 */
		osDM.setUpdatedFlag(0xFFFF);

		Runnable runnable = new Runnable() {
			public void run() {
				if (status.isOK()) {
					// Remember tree state
					Object[] expandedNodes = m_overviewTreeViewer
							.getExpandedElements();
					ISelection selection = m_overviewTreeViewer.getSelection();

					m_overviewTreeViewer.setInput(osDM);

					// Restore tree state
					m_overviewTreeViewer.setExpandedElements(expandedNodes);
					m_overviewTreeViewer.setSelection(selection);
					m_overviewTreeViewer.getTree().showSelection();

					m_processTableViewer.setInput(osDM);
					m_threadTableViewer.setInput(osDM);
					m_chunkTableViewer.setInput(osDM);
					m_libraryTableViewer.setInput(osDM);

					/*
					 * The above call will populate the table. Here we can make
					 * sure each column is packed to show all info in preferred
					 * size. Do we really want to do this as it may be against
					 * user's desire ? Let's wait for user feedback....11/02/06
					 */
					packTableViewer(m_processTableViewer);
					packTableViewer(m_threadTableViewer);

					showDataAsStale(false);
				}

				// Data refreshed, reschedule our next timed refresh.
				scheduleTimedRefresh();

				if (status.getSeverity() != IStatus.OK
						&& status.getSeverity() != IStatus.CANCEL) {
					showDataAsStale(true);
					// Now the computeInput() is done in a job, and job
					// mechanism will display any error automatically.
				}
			}
		};

		// Don't update UI if the view is already closed by user.
		if (!m_disposed)
			PlatformUI.getWorkbench().getDisplay().asyncExec(runnable);
	}

	public void handleDebugEvents(DebugEvent[] events) {
		for (int i = 0; i < events.length; i++) {
			DebugEvent event = events[i];

			if (! (event.getSource() instanceof CDebugElement))
				continue;
			
			CDebugElement elem = (CDebugElement) event.getSource();
			if (elem == null || ! (elem.getCDISession() instanceof Session)) // not from our debugger
				return;
			
			final Session session = (Session) elem.getCDISession();
			if (session == null)
				return;
			
			if (event.getKind() == DebugEvent.TERMINATE &&
				elem instanceof ICDebugTarget) 
			{
				// a Target (process) is terminated
				if (session == m_currentSession && !session.isActive()) {
					// and the session is terminated.
					
					session.getOSDataManager().setDataDirty();
					
					updateActionStatus();
					
					// remove timed task, if any.
					PlatformUI.getWorkbench().getDisplay().syncExec(
							new Runnable() {
								public void run() {
									disableTimedRefresh();
									
									// Show data as stale after end of session.
									showDataAsStale(true);
								}
							});

					m_currentSession = null;
				}
			}
			else if (event.getKind() == DebugEvent.CREATE &&
					elem instanceof ICThread) 
				{
					// a thread in our debug session is created.
					if (m_currentSession == null) {
						// If the OS Data view has no associated session yet, 
						// associate it with the session so that OS Data View can
						// show data for a never-stopped debug session. Fix bug 4428.
						// ...... 12/04/07
						// But only do that for run mode debug. For stop mode, 
						// we should only show data whenever the system is stopped
						// and the thread in Debug View is selected...12/05/07 
						if (session.isSystemModeDebug())
							return;
						
						PlatformUI.getWorkbench().getDisplay().syncExec(
								new Runnable() {
									public void run() {
										try {
											showDataForDevice(session);
										} 
										catch (InterruptedException e) {
											e.printStackTrace();
										}	
									}
								});
					}
				}
		}
	}

	@Override
	public Object getAdapter(Class adapter) {
		if (adapter.isInstance(this))
			return this;
		if (adapter.isInstance(tabFolder))
			return tabFolder;
		if (adapter.isInstance(m_currentViewer))
			return m_currentViewer;
		
		return super.getAdapter(adapter);
	}

	public int getRefreshCount() {
		return refreshCount;
	}
}
