package com.nokia.carbide.discovery.ui.view;


import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

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
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.part.ViewPart;

import com.nokia.carbide.discovery.ui.Activator;



/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

@SuppressWarnings("restriction")
public class DiscoveryView extends ViewPart implements ISelectionChangedListener {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "testdiscovery.views.TestView";

	private CatalogViewer viewer;
	private Action refreshAction;
	private BaseSelectionListenerAction selectAllAction;
	private BaseSelectionListenerAction selectNoneAction;

	private boolean updated;

	private Button installButton;

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
		
		installButton = new Button(c, SWT.PUSH);
		installButton.setText("Install Checked Items...");
		ImageDescriptor imgDesc = Activator.getImageDescriptor("icons/icon-discovery.png");
		installButton.setImage(imgDesc.createImage());
		installButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DiscoveryUi.install(viewer.getCheckedItems(), getSite().getWorkbenchWindow());
			}
		});
		viewer.addSelectionChangedListener(this);
		
		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "TestDiscovery.viewer");
		makeActions();
		contributeToActionBars();
	}
	
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		installButton.setEnabled(!event.getSelection().isEmpty());
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
		catalog.getDiscoveryStrategies().add(new BundleDiscoveryStrategy());

		// look for remote descriptor
		RemoteBundleDiscoveryStrategy remoteDiscoveryStrategy = new RemoteBundleDiscoveryStrategy();
		File f = new File("D:\\Users\\Discovery\\discovery.xml");
		try {
			remoteDiscoveryStrategy.setDirectoryUrl(f.toURI().toURL().toExternalForm());
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(e);
		}
//		catalog.getDiscoveryStrategies().add(remoteDiscoveryStrategy);

		return catalog;
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
	}

	private void makeActions() {
		refreshAction = new Action() {
			public void run() {
				viewer.refresh();
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
				Object[] elements = getAllCatalogItems();
				IStructuredSelection allItemsSelection = new StructuredSelection(elements);
				return allItemsSelection;
			}

			private Object[] getAllCatalogItems() {
				List<Object> catalogItems = new ArrayList<Object>();
				IStructuredContentProvider provider = 
					(IStructuredContentProvider) viewer.getViewer().getContentProvider();
				Object[] elements = provider.getElements(viewer.getViewer().getInput());
				for (Object item : elements) {
					if (item instanceof CatalogItem)
						catalogItems.add(item);
				}
				return catalogItems.toArray();
			};
			
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
		viewer.addSelectionChangedListener(selectAllAction);
		viewer.addSelectionChangedListener(selectNoneAction);
	}
	
	@Override
	public void dispose() {
		viewer.removeSelectionChangedListener(selectAllAction);
		viewer.removeSelectionChangedListener(selectNoneAction);
		viewer.removeSelectionChangedListener(this);
		super.dispose();
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		updateCatalog();
	}

	private void updateCatalog() {
		if (!updated) {
			updated = true;
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					viewer.updateCatalog();
				}
			});
		}
	}

}