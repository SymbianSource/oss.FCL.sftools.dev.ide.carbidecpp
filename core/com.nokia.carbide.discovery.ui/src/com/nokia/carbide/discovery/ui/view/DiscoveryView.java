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
import org.eclipse.equinox.internal.p2.discovery.compatibility.BundleDiscoveryStrategy;
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
				"com.nokia.carbide.discovery.ui.view.DiscoveryView.viewer"); //$NON-NLS-1$
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
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(refreshAction);
		manager.add(installAction);
	}

	private void makeActions() {
		refreshAction = new Action() {
			public void run() {
				viewer.setSelection(StructuredSelection.EMPTY);
				viewer.refresh();
				viewer.updateCatalog();
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

}