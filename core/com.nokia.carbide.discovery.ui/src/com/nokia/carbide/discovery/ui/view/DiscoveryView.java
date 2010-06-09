package com.nokia.carbide.discovery.ui.view;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.internal.p2.discovery.Catalog;
import org.eclipse.equinox.internal.p2.discovery.DiscoveryCore;
import org.eclipse.equinox.internal.p2.discovery.compatibility.BundleDiscoveryStrategy;
import org.eclipse.equinox.internal.p2.discovery.compatibility.RemoteBundleDiscoveryStrategy;
import org.eclipse.equinox.internal.p2.discovery.model.CatalogItem;
import org.eclipse.equinox.internal.p2.ui.discovery.DiscoveryUi;
import org.eclipse.equinox.internal.p2.ui.discovery.wizards.CatalogConfiguration;
import org.eclipse.equinox.internal.p2.ui.discovery.wizards.CatalogViewer;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.engine.IProfile;
import org.eclipse.equinox.p2.engine.IProfileRegistry;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.p2.operations.ProvisioningSession;
import org.eclipse.equinox.p2.query.IQueryResult;
import org.eclipse.equinox.p2.query.QueryUtil;
import org.eclipse.equinox.p2.repository.artifact.IArtifactRepositoryManager;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;
import org.eclipse.equinox.p2.ui.ProvisioningUI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.part.ViewPart;

import com.nokia.carbide.discovery.ui.Activator;
import com.nokia.carbide.discovery.ui.Messages;

@SuppressWarnings("restriction")
public class DiscoveryView extends ViewPart {
	
	private static final String DIRECTORY_KEY = "com.nokia.carbide.discovery.directory"; //$NON-NLS-1$

	private CatalogViewer viewer;
	private Action refreshAction;
	private BaseSelectionListenerAction checkAllAction;
	private BaseSelectionListenerAction checkNoneAction;
	private BaseSelectionListenerAction installAction;
	private Action showInstallWizardAction;

	private boolean initialized;

	private ISelectionChangedListener selectionListener;

	public DiscoveryView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		Composite c = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(c);
		viewer = new CatalogViewer(getCatalog(), getSite(), getSite().getWorkbenchWindow(), getConfiguration());
		viewer.createControl(c);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(viewer.getControl());
		
		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), 
				"com.nokia.carbide.discovery.ui.view.DiscoveryView.catalogviewer"); //$NON-NLS-1$
		makeActions();
		contributeToActionBars();
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
		String url = getFromServerProperties(DIRECTORY_KEY);
		if (url != null) {
			remoteDiscoveryStrategy.setDirectoryUrl(url);
			catalog.getDiscoveryStrategies().add(remoteDiscoveryStrategy);
		}
		else // look for descriptors from installed bundles
			catalog.getDiscoveryStrategies().add(new BundleDiscoveryStrategy());

		return catalog;
	}

	public static String getFromServerProperties(String key) {
		Location installLocation = Platform.getInstallLocation();
		URL url = installLocation.getURL();
		IPath path = new Path(url.getPath());
		path = path.append("configuration/server.properties"); //$NON-NLS-1$
		File file = path.toFile();
		Properties properties = new Properties();
		try {
			InputStream is = new FileInputStream(file);
			properties.load(is);
			is.close();
		} catch (IOException e) {
			String message = 
				MessageFormat.format(Messages.DiscoveryView_MissingDirectoryURLError, key);
			Activator.logError(message, e);
		}
		return (String) properties.get(key);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(checkAllAction);
		manager.add(checkNoneAction);
		manager.add(new Separator());
		manager.add(showInstallWizardAction);
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(refreshAction);
		manager.add(installAction);
	}

	private void makeActions() {
		refreshAction = new Action() {
			public void run() {
				viewer.setSelection(StructuredSelection.EMPTY);
				viewer.updateCatalog();
				viewer.refresh();
			}
		};
		refreshAction.setText(Messages.DiscoveryView_RefreshLabel);
		refreshAction.setImageDescriptor(Activator.getImageDescriptor("icons/refresh.gif")); //$NON-NLS-1$
		checkAllAction = new BaseSelectionListenerAction(Messages.DiscoveryView_CheckAllLabel) {
			public void run() {
				viewer.setSelection(StructuredSelection.EMPTY);
				viewer.setSelection(getAllItemsSelection());
				viewer.refresh();
			}

			private IStructuredSelection getAllItemsSelection() {
				List<CatalogItem> catalogItems = viewer.getCatalog().getItems();
				return new StructuredSelection(catalogItems);
			}

			protected boolean updateSelection(IStructuredSelection selection) {
				return !getAllItemsSelection().equals(selection);
			};
		};
		checkNoneAction = new BaseSelectionListenerAction(Messages.DiscoveryView_UncheckAllLabel) {
			public void run() {
				viewer.setSelection(StructuredSelection.EMPTY);
				viewer.refresh();
			};
			
			protected boolean updateSelection(IStructuredSelection selection) {
				return !selection.isEmpty();
			};
		};
		installAction = new BaseSelectionListenerAction(Messages.DiscoveryView_InstallLabel) {
			public void run() {
				DiscoveryUi.install(viewer.getCheckedItems(), new ProgressMonitorDialog(DiscoveryView.this.getViewSite().getShell()));
			};
			
			protected boolean updateSelection(IStructuredSelection selection) {
				return !selection.isEmpty();
			};
		};
		installAction.setImageDescriptor(Activator.getImageDescriptor("icons/icon-discovery.png")); //$NON-NLS-1$
		showInstallWizardAction = new Action(Messages.DiscoveryView_AdvancedInstallLabel) {
			public void run() {
				showInstallWizard();
			}
		};
		viewer.addSelectionChangedListener(checkAllAction);
		viewer.addSelectionChangedListener(checkNoneAction);
		viewer.addSelectionChangedListener(installAction);
		selectionListener = new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				IActionBars bars = getViewSite().getActionBars();
				bars.getStatusLineManager().setMessage(
						selection.isEmpty() ? null : MessageFormat.format(
								Messages.DiscoveryView_StatusLineFmt, selection.size()));
			}
		};
		viewer.addSelectionChangedListener(selectionListener);
	}
	
	@Override
	public void dispose() {
		viewer.removeSelectionChangedListener(checkAllAction);
		viewer.removeSelectionChangedListener(checkNoneAction);
		viewer.removeSelectionChangedListener(installAction);
		viewer.removeSelectionChangedListener(selectionListener);
		
		super.dispose();
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		if (!initialized) {
			initialized = true;
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					if (viewer.getViewer().getContentProvider() != null) {
						viewer.updateCatalog();
					}
				}
			});
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
		
		String profileId = defaultUI.getProfileId();
		IProfileRegistry profileRegistry = (IProfileRegistry) agent.getService(IProfileRegistry.SERVICE_NAME);
		IProfile profile = profileRegistry.getProfile(profileId);
		IQueryResult<IInstallableUnit> queryResult = profile.query(QueryUtil.createIUGroupQuery(), null);
		List<String> list = new ArrayList<String>();
		for (Iterator<IInstallableUnit> iter = queryResult.iterator(); iter.hasNext();) {
			IInstallableUnit iu = iter.next();
			list.add(iu.getId() + "|" + iu.getVersion());
		}
		Collections.sort(list);
		for (String s : list) {
			System.out.println(s);
		}
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

}