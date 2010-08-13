/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.carbide.internal.discovery.ui.extension;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.equinox.internal.p2.discovery.Catalog;
import org.eclipse.equinox.internal.p2.discovery.DiscoveryCore;
import org.eclipse.equinox.internal.p2.discovery.compatibility.BundleDiscoveryStrategy;
import org.eclipse.equinox.internal.p2.discovery.compatibility.RemoteBundleDiscoveryStrategy;
import org.eclipse.equinox.internal.p2.discovery.model.CatalogItem;
import org.eclipse.equinox.internal.p2.ui.discovery.DiscoveryUi;
import org.eclipse.equinox.internal.p2.ui.discovery.wizards.CatalogConfiguration;
import org.eclipse.equinox.internal.p2.ui.discovery.wizards.CatalogViewer;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepositoryManager;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.eclipse.equinox.p2.ui.ProvisioningUI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.handlers.IHandlerService;

import com.nokia.carbide.discovery.ui.Activator;
import com.nokia.carbide.discovery.ui.Messages;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

@SuppressWarnings("restriction")
public abstract class AbstractDiscoveryPortalPageLayer implements IPortalPageLayer {

	private class RunnableContextDialog extends ProgressMonitorDialog {
		private final String title;

		private RunnableContextDialog(Shell parent, String title) {
			super(parent);
			this.title = title;
		}

		@Override
		protected void configureShell(Shell shell) {
			super.configureShell(shell);
			shell.setText(title);
		}
		
	}

	protected class ActionBar implements IActionBar {
		private IAction[] actions;

		public ActionBar(IEditorPart part) {
			actions = makeActions(part);
		}
		
		@Override
		public String getTitle() {
			return Messages.AbstractDiscoveryPortalPageLayer_Title;
		}

		@Override
		public IAction[] getActions() {
			return actions;
		}

		@Override
		public String[] getHighlightedActionIds() {
			return new String[] {INSTALL_ACTION_ID};
		}
	}
	
	protected static final String INSTALL_ACTION_ID = 
		AbstractDiscoveryPortalPageLayer.class.getName() + ".install"; //$NON-NLS-1$
	protected static final String UNCHECK_ALL_ACTION_ID = 
		AbstractDiscoveryPortalPageLayer.class.getName() + ".uncheckAll"; //$NON-NLS-1$
	protected static final String CHECK_ALL_ACTION_ID = 
		AbstractDiscoveryPortalPageLayer.class.getName() + ".checkAll"; //$NON-NLS-1$
	protected static final String ADV_INSTALL_ACTION_ID = 
		AbstractDiscoveryPortalPageLayer.class.getName() + ".advancedInstall"; //$NON-NLS-1$
	protected static final String REFRESH_ACTION_ID = 
		AbstractDiscoveryPortalPageLayer.class.getName() + ".refresh"; //$NON-NLS-1$

	protected IEditorPart part;
	private CatalogViewer viewer;
	private List<ISelectionChangedListener> selectionListeners;
	private IActionUIUpdater updater;

	@Override
	public Control createControl(Composite parent, IEditorPart part) {
		Composite c = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().applyTo(c);
		viewer = new CatalogViewer(getCatalog(), part.getEditorSite(), 
				new RunnableContextDialog(part.getEditorSite().getShell(), 
						Messages.AbstractDiscoveryPortalPageLayer_GatheringExtensionsDesc), 
				getConfiguration());
		viewer.createControl(c);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(viewer.getControl());
		
		return c;
	}

	@Override
	public void init() {
		if (!WorkbenchUtils.isJUnitRunning()) { // do not initialize the catalog if JUnit is running
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					for (ISelectionChangedListener listener : selectionListeners) {
						viewer.addSelectionChangedListener(listener);
					}
					viewer.updateCatalog();
					viewer.getControl().setFocus();
				}
			});
		}
	}

	@Override
	public IActionBar[] createCommandBars(IEditorPart part, IActionUIUpdater updater) {
		this.updater = updater;
		return new IActionBar[] { new ActionBar(part) };
	}

	protected CatalogConfiguration getConfiguration() {
		CatalogConfiguration configuration = new CatalogConfiguration();
		configuration.setShowTagFilter(false);
		return configuration;
	}

	protected Catalog getCatalog() {
		Catalog catalog = new Catalog();
		catalog.setEnvironment(DiscoveryCore.createEnvironment());
		catalog.setVerifyUpdateSiteAvailability(false);
		
		// look for remote descriptor
		RemoteBundleDiscoveryStrategy remoteDiscoveryStrategy = new RemoteBundleDiscoveryStrategy();
		String url = getDirectoryURL();
		if (url != null) {
			remoteDiscoveryStrategy.setDirectoryUrl(url);
			catalog.getDiscoveryStrategies().add(remoteDiscoveryStrategy);
		}
		else // look for descriptors from installed bundles
			catalog.getDiscoveryStrategies().add(new BundleDiscoveryStrategy());
	
		return catalog;
	}

	protected String getDirectoryURL() {
		return Activator.getFromServerProperties(getClass().getName());
	}

	protected IAction[] makeActions(final IEditorPart part) {
		this.part = part;
		selectionListeners = new ArrayList<ISelectionChangedListener>();
		List<IAction> actions = new ArrayList<IAction>();
		IAction action;
		
		// install
		action = new BaseSelectionListenerAction(Messages.AbstractDiscoveryPortalPageLayer_InstallActionLabel) {
			public void run() {
				installCatalogItems(viewer.getCheckedItems());
			};
			
			protected boolean updateSelection(IStructuredSelection selection) {
				scheduleUpdateAllActionUIs();
				return !selection.isEmpty();
			};
		};
		action.setToolTipText(Messages.AbstractDiscoveryPortalPageLayer_InstallActionTooltip);
		action.setId(INSTALL_ACTION_ID);
		selectionListeners.add((ISelectionChangedListener) action);
		actions.add(action);
		
		// refresh
		action = new Action(Messages.AbstractDiscoveryPortalPageLayer_RefreshActionLabel) {
			public void run() {
				viewer.setSelection(StructuredSelection.EMPTY);
				viewer.updateCatalog();
				viewer.refresh();
			}
		};
		action.setId(REFRESH_ACTION_ID);
		actions.add(action);
		
		// check all
		action = new BaseSelectionListenerAction(Messages.AbstractDiscoveryPortalPageLayer_CheckAllActionLabel) {
			public void run() {
				viewer.setSelection(StructuredSelection.EMPTY);
				viewer.setSelection(getAllItemsSelection());
				viewer.refresh();
			}
	
			private IStructuredSelection getAllItemsSelection() {
				List<CatalogItem> catalogItems = new ArrayList<CatalogItem>();
				for (CatalogItem catalogItem : viewer.getCatalog().getItems()) {
					if (!catalogItem.isInstalled())
						catalogItems.add(catalogItem);
				}	
				return new StructuredSelection(catalogItems);
			}
	
			protected boolean updateSelection(IStructuredSelection selection) {
				scheduleUpdateAllActionUIs();
				return !getAllItemsSelection().equals(selection);
			}
		};
		action.setId(CHECK_ALL_ACTION_ID);
		selectionListeners.add((ISelectionChangedListener) action);
		actions.add(action);
		
		// uncheck all
		action = new BaseSelectionListenerAction(Messages.AbstractDiscoveryPortalPageLayer_UncheckAllActionLabel) {
			public void run() {
				viewer.setSelection(StructuredSelection.EMPTY);
				viewer.refresh();
			};
			
			protected boolean updateSelection(IStructuredSelection selection) {
				scheduleUpdateAllActionUIs();
				return !selection.isEmpty();
			};
		};
		action.setId(UNCHECK_ALL_ACTION_ID);
		selectionListeners.add((ISelectionChangedListener) action);
		actions.add(action);
		
		// advanced install
		action = new Action(Messages.AbstractDiscoveryPortalPageLayer_AdvancedInstallActionLabel) {
			public void run() {
				showInstallWizard();
			}
		};
		action.setId(ADV_INSTALL_ACTION_ID);
		actions.add(action);
		
		ISelectionChangedListener selectionListener = new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				IActionBars bars = part.getEditorSite().getActionBars();
				bars.getStatusLineManager().setMessage(
						selection.isEmpty() ? null : MessageFormat.format(
								Messages.AbstractDiscoveryPortalPageLayer_CheckedItemsStatusMessage, selection.size()));
			}
		};
		selectionListeners.add(selectionListener);
		
		return (IAction[]) actions.toArray(new IAction[actions.size()]);
	}

	protected void installCatalogItems(List<CatalogItem> items) {
		DiscoveryUi.install(items, 
				new RunnableContextDialog(part.getEditorSite().getShell(), 
						Messages.AbstractDiscoveryPortalPageLayer_GatheringInstallInfoDesc));
	}

	@Override
	public void dispose() {
		for (ISelectionChangedListener listener : selectionListeners) {
			viewer.removeSelectionChangedListener(listener);
		}
	}

	protected void showInstallWizard() {
		IProvisioningAgent agent = ProvisioningUI.getDefaultUI().getSession().getProvisioningAgent();
		IMetadataRepositoryManager metadataManager = (IMetadataRepositoryManager) agent.getService(IMetadataRepositoryManager.SERVICE_NAME);
		IArtifactRepositoryManager artifactManager = (IArtifactRepositoryManager) agent.getService(IArtifactRepositoryManager.SERVICE_NAME);
		for (URI uri : getSiteURIs(viewer.getCatalog().getItems())) {
			metadataManager.addRepository(uri);
			artifactManager.addRepository(uri);
		}
		IHandlerService handlerService = 
			(IHandlerService) PlatformUI.getWorkbench().getService(IHandlerService.class);
        try {
			handlerService.executeCommand("org.eclipse.equinox.p2.ui.sdk.install", null);
		} catch (Exception e) {
			Activator.logError("Could not open install wizard", e);
		}
	}

	protected static Collection<URI> getSiteURIs(List<CatalogItem> catalogItems) {
		Set<URI> uris = new HashSet<URI>();
		for (CatalogItem catalogItem : catalogItems) {
			try {
				uris.add(new URI(catalogItem.getSiteUrl()));
			} catch (URISyntaxException e) {
				// ignore bad URIs
			}
		}
		return uris;
	}

	protected void scheduleUpdateAllActionUIs() {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				updater.updateAll();
			}
		});
	}

}