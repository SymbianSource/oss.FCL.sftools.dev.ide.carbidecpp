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
package com.nokia.carbide.internal.discovery.ui.view;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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
import org.eclipse.equinox.p2.operations.ProvisioningSession;
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
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import com.nokia.carbide.discovery.ui.Activator;
import com.nokia.carbide.discovery.ui.Messages;
import com.nokia.carbide.internal.discovery.ui.extension.IPortalPageLayer;
import com.nokia.carbide.internal.discovery.ui.extension.OpenPreferencePageAction;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

@SuppressWarnings("restriction")
public class InstallExtensionsPortalExtension implements IPortalPageLayer {

	private static final String INSTALL_ACTION_ID = InstallExtensionsPortalExtension.class.getName() + ".install"; //$NON-NLS-1$

	private final class RunnableContextDialog extends ProgressMonitorDialog {
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

	private final class ActionBar implements IActionBar {
		private IAction[] actions;

		public ActionBar(IEditorPart part) {
			actions = makeActions(part);
		}
		
		@Override
		public String getTitle() {
			return Messages.InstallExtensionsPortalExtension_InstallExtensionsTitle;
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
	
	private class SettingsBar implements IActionBar {
		@Override
		public String getTitle() {
			return "Settings";
		}

		@Override
		public IAction[] getActions() {
			List<IAction> actions = new ArrayList<IAction>();
			actions.add(new OpenPreferencePageAction(
					"Capabilities", 
					"org.eclipse.sdk.capabilities"));
			actions.add(new OpenPreferencePageAction(
					"Code Style", 
					"org.eclipse.cdt.ui.preferences.CodeFormatterPreferencePage"));
			actions.add(new OpenPreferencePageAction(
					"Key bindings", 
					"org.eclipse.ui.preferencePages.Keys"));
			actions.add(new OpenPreferencePageAction(
					"Proxies", 
					"org.eclipse.ui.net.NetPreferences"));
			return (IAction[]) actions.toArray(new IAction[actions.size()]);
		}

		@Override
		public String[] getHighlightedActionIds() {
			return null;
		}
	}

	private static final String DIRECTORY_KEY = "com.nokia.carbide.discovery.directory"; //$NON-NLS-1$

	private CatalogViewer viewer;
	private List<ISelectionChangedListener> selectionListeners;
	private IActionUIUpdater updater;

	public InstallExtensionsPortalExtension() {
	}

	@Override
	public Control createControl(Composite parent, IEditorPart part) {
		Composite c = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().applyTo(c);
		viewer = new CatalogViewer(getCatalog(), part.getEditorSite(), 
				new RunnableContextDialog(part.getEditorSite().getShell(), 
						Messages.InstallExtensionsPage_GatherExtensionsTitle), 
				getConfiguration());
		viewer.createControl(c);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(viewer.getControl());
		
		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), 
				"com.nokia.carbide.discovery.ui.view.DiscoveryView.catalogviewer"); //$NON-NLS-1$
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
				}
			});
		}
	}
	
	@Override
	public IActionBar[] createCommandBars(IEditorPart part, IActionUIUpdater updater) {
		this.updater = updater;
		return new IActionBar[] { new ActionBar(part), new SettingsBar() };
	}

	private CatalogConfiguration getConfiguration() {
		CatalogConfiguration configuration = new CatalogConfiguration();
		configuration.setShowTagFilter(false);
		return configuration;
	}

	private Catalog getCatalog() {
		Catalog catalog = new Catalog();
		catalog.setEnvironment(DiscoveryCore.createEnvironment());
		catalog.setVerifyUpdateSiteAvailability(false);
		
		// look for remote descriptor
		RemoteBundleDiscoveryStrategy remoteDiscoveryStrategy = new RemoteBundleDiscoveryStrategy();
		String url = Activator.getFromServerProperties(DIRECTORY_KEY);
		if (url != null) {
			remoteDiscoveryStrategy.setDirectoryUrl(url);
			catalog.getDiscoveryStrategies().add(remoteDiscoveryStrategy);
		}
		else // look for descriptors from installed bundles
			catalog.getDiscoveryStrategies().add(new BundleDiscoveryStrategy());

		return catalog;
	}

	private IAction[] makeActions(final IEditorPart part) {
		selectionListeners = new ArrayList<ISelectionChangedListener>();
		List<IAction> actions = new ArrayList<IAction>();
		IAction action;
		
		// install
		action = new BaseSelectionListenerAction(Messages.InstallExtensionsPage_InstallLabel) {
			public void run() {
				DiscoveryUi.install(viewer.getCheckedItems(), 
						new RunnableContextDialog(part.getEditorSite().getShell(), 
								Messages.InstallExtensionsPage_GatheringInstallInfoTitle));
			};
			
			protected boolean updateSelection(IStructuredSelection selection) {
				scheduleUpdateAllActionUIs();
				return !selection.isEmpty();
			};
		};
		action.setToolTipText(Messages.InstallExtensionsPage_InstallTip);
		action.setId(INSTALL_ACTION_ID);
		selectionListeners.add((ISelectionChangedListener) action);
		actions.add(action);
		
		// refresh
		action = new Action(Messages.InstallExtensionsPage_RefreshLabel) {
			public void run() {
				viewer.setSelection(StructuredSelection.EMPTY);
				viewer.updateCatalog();
				viewer.refresh();
			}
		};
		actions.add(action);
		
		// check all
		action = new BaseSelectionListenerAction(Messages.InstallExtensionsPage_CheckAllLabel) {
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
		action.setId(InstallExtensionsPortalExtension.class.getName() + ".checkAll"); //$NON-NLS-1$
		selectionListeners.add((ISelectionChangedListener) action);
		actions.add(action);
		
		// uncheck all
		action = new BaseSelectionListenerAction(Messages.InstallExtensionsPage_UncheckAllLabel) {
			public void run() {
				viewer.setSelection(StructuredSelection.EMPTY);
				viewer.refresh();
			};
			
			protected boolean updateSelection(IStructuredSelection selection) {
				scheduleUpdateAllActionUIs();
				return !selection.isEmpty();
			};
		};
		action.setId(InstallExtensionsPortalExtension.class.getName() + ".uncheckAll"); //$NON-NLS-1$
		selectionListeners.add((ISelectionChangedListener) action);
		actions.add(action);
		
		// advanced install
		action = new Action(Messages.InstallExtensionsPage_AdvancedInstallLabel) {
			public void run() {
				showInstallWizard();
			}
		};
		actions.add(action);
		
		ISelectionChangedListener selectionListener = new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				IActionBars bars = part.getEditorSite().getActionBars();
				bars.getStatusLineManager().setMessage(
						selection.isEmpty() ? null : MessageFormat.format(
								Messages.InstallExtensionsPage_StatusLineFmt, selection.size()));
			}
		};
		selectionListeners.add(selectionListener);
		
		return (IAction[]) actions.toArray(new IAction[actions.size()]);
	}
	
	@Override
	public void dispose() {
		for (ISelectionChangedListener listener : selectionListeners) {
			viewer.removeSelectionChangedListener(listener);
		}
	}

	private void showInstallWizard() {
		ProvisioningUI defaultUI = ProvisioningUI.getDefaultUI();
		ProvisioningSession session = defaultUI.getSession();
		IProvisioningAgent agent = session.getProvisioningAgent();
		IMetadataRepositoryManager metadataManager = (IMetadataRepositoryManager) agent.getService(IMetadataRepositoryManager.SERVICE_NAME);
		IArtifactRepositoryManager artifactManager = (IArtifactRepositoryManager) agent.getService(IArtifactRepositoryManager.SERVICE_NAME);
		for (URI uri : getCatalogURIs()) {
			metadataManager.addRepository(uri);
			artifactManager.addRepository(uri);
		}
		defaultUI.openInstallWizard(null, null, null);
	}

	private Collection<URI> getCatalogURIs() {
		Set<URI> uris = new HashSet<URI>();
		for (CatalogItem catalogItem : viewer.getCatalog().getItems()) {
			try {
				uris.add(new URI(catalogItem.getSiteUrl()));
			} catch (URISyntaxException e) {
				// ignore bad URIs
			}
		}
		return uris;
	};

	private void scheduleUpdateAllActionUIs() {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				updater.updateAll();
			}
		});
	};
}
