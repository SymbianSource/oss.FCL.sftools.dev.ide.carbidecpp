package com.nokia.carbide.discovery.ui.view;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.equinox.internal.p2.discovery.Catalog;
import org.eclipse.equinox.internal.p2.discovery.DiscoveryCore;
import org.eclipse.equinox.internal.p2.discovery.compatibility.RemoteBundleDiscoveryStrategy;
import org.eclipse.equinox.internal.p2.discovery.model.CatalogItem;
import org.eclipse.equinox.internal.p2.ui.discovery.DiscoveryUi;
import org.eclipse.equinox.internal.p2.ui.discovery.wizards.CatalogConfiguration;
import org.eclipse.equinox.internal.p2.ui.discovery.wizards.CatalogViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.IStructuredSelection;
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

@SuppressWarnings("restriction")
public class DiscoveryView extends ViewPart {
	
	private static final String DIRECTORY_KEY = "com.nokia.carbide.discovery.directory"; //$NON-NLS-1$

	private CatalogViewer viewer;
	private Action refreshAction;
	private BaseSelectionListenerAction selectAllAction;
	private BaseSelectionListenerAction selectNoneAction;
	private BaseSelectionListenerAction installAction;

	private boolean initialized;

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
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "TestDiscovery.viewer");
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

		// look for descriptors from installed bundles
//		catalog.getDiscoveryStrategies().add(new BundleDiscoveryStrategy());

		// look for remote descriptor
		RemoteBundleDiscoveryStrategy remoteDiscoveryStrategy = new RemoteBundleDiscoveryStrategy();
		String url = getFromServerProperties(DIRECTORY_KEY);
		remoteDiscoveryStrategy.setDirectoryUrl(url);
		catalog.getDiscoveryStrategies().add(remoteDiscoveryStrategy);

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
				MessageFormat.format("Could not find URL in configuration/server.properties file for key={0}", key);
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
		manager.add(selectAllAction);
		manager.add(selectNoneAction);
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(refreshAction);
		manager.add(installAction);
	}

	private void makeActions() {
		refreshAction = new Action() {
			public void run() {
				viewer.updateCatalog();
			}
		};
		refreshAction.setText("Refresh");
		refreshAction.setImageDescriptor(Activator.getImageDescriptor("icons/refresh.gif"));
		selectAllAction = new BaseSelectionListenerAction("Select All Items") {
			public void run() {
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
		selectNoneAction = new BaseSelectionListenerAction("Deselect All Items") {
			public void run() {
				viewer.setSelection(StructuredSelection.EMPTY);
				viewer.refresh();
			};
			
			protected boolean updateSelection(IStructuredSelection selection) {
				return !selection.isEmpty();
			};
		};
		installAction = new BaseSelectionListenerAction("Install Checked Items...") {
			public void run() {
				DiscoveryUi.install(viewer.getCheckedItems(), new ProgressMonitorDialog(DiscoveryView.this.getViewSite().getShell()));
			};
			
			protected boolean updateSelection(IStructuredSelection selection) {
				return !selection.isEmpty();
			};
		};
		installAction.setImageDescriptor(Activator.getImageDescriptor("icons/icon-discovery.png"));
		viewer.addSelectionChangedListener(selectAllAction);
		viewer.addSelectionChangedListener(selectNoneAction);
		viewer.addSelectionChangedListener(installAction);
	}
	
	@Override
	public void dispose() {
		viewer.removeSelectionChangedListener(selectAllAction);
		viewer.removeSelectionChangedListener(selectNoneAction);
		viewer.removeSelectionChangedListener(installAction);
		
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
					viewer.updateCatalog();
					viewer.setSelection(StructuredSelection.EMPTY);
				}
			});
		}
	}
}