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
package com.nokia.carbide.internal.discovery.ui.editor;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.nokia.carbide.discovery.ui.Activator;
import com.nokia.carbide.discovery.ui.Messages;
import com.nokia.carbide.internal.discovery.ui.extension.ICommandBarFactory;
import com.nokia.carbide.internal.discovery.ui.extension.IPortalEditor;
import com.nokia.carbide.internal.discovery.ui.extension.IPortalPageLayer;
import com.nokia.cpp.internal.api.utils.core.Pair;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

public class PortalEditor extends EditorPart implements IPortalEditor, IElementFactory {
	
	private static final class PortalEditorInput implements IEditorInput {
		@SuppressWarnings("rawtypes")
		@Override
		public Object getAdapter(Class adapter) {
			return null;
		}

		@Override
		public String getToolTipText() {
			return getName();
		}

		@Override
		public IPersistableElement getPersistable() {
			return new IPersistableElement() {
				@Override
				public void saveState(IMemento memento) {
				}
				
				@Override
				public String getFactoryId() {
					return "com.nokia.carbide.discovery.ui.portalEditorFactory"; //$NON-NLS-1$
				}
			};
		}

		@Override
		public String getName() {
			return Messages.PortalEditor_Name;
		}

		@Override
		public ImageDescriptor getImageDescriptor() {
			return null;
		}

		@Override
		public boolean exists() {
			return true;
		}
	}

	class LayerExtension {
		public LayerExtension(IPortalPageLayer layer, String title, int order) {
			this.layer = layer;
			this.title = title;
			this.order = order;
		}
		public IPortalPageLayer layer;
		public String title;
		public int order;
	}

	private static final String ID = "com.nokia.carbide.discovery.ui.portalEditor"; //$NON-NLS-1$
	private static final String CONTEXT_ID = ID + ".context"; //$NON-NLS-1$
	private static IEditorInput input;
	private List<PortalPage> pages;
	private Composite backgroundParent;
	private Image oldBGImg;
	private List<Resource> resources;
	private StackComposite stackComposite;
	private NavigationBar navigationBar;

	public PortalEditor() {
		resources = new ArrayList<Resource>();
		loadPortalPages();
	}
	
	private Map<String, List<LayerExtension>> loadPortalLayers() {
		Map<String, List<LayerExtension>> pageIdToExtensionsMap = 
			new HashMap<String, List<LayerExtension>>();
		IConfigurationElement[] elements = 
			Platform.getExtensionRegistry().getConfigurationElementsFor(Activator.PLUGIN_ID + ".portalPageLayer"); //$NON-NLS-1$
		for (IConfigurationElement element : elements) {
			String pageId = element.getAttribute("pageId"); //$NON-NLS-1$
			String title = element.getAttribute("title"); //$NON-NLS-1$
			int order = getOrderStringFromElement(element, title);
			try {
				IPortalPageLayer extension = (IPortalPageLayer) element.createExecutableExtension("class"); //$NON-NLS-1$
				if (!pageIdToExtensionsMap.containsKey(pageId))
					pageIdToExtensionsMap.put(pageId, new ArrayList<LayerExtension>());
				pageIdToExtensionsMap.get(pageId).add(new LayerExtension(extension, title, order));
			} catch (CoreException e) {
				Activator.logError(MessageFormat.format(Messages.PortalEditor_PageLoadError, pageId), e);
			}
		}
		return pageIdToExtensionsMap;
	}

	private void loadPortalPages() {
		Map<String, List<LayerExtension>> portalLayersMap = loadPortalLayers();
		List<Pair<PortalPage, Integer>> pageList = new ArrayList<Pair<PortalPage, Integer>>();
		IConfigurationElement[] elements = 
			Platform.getExtensionRegistry().getConfigurationElementsFor(Activator.PLUGIN_ID + ".portalPage"); //$NON-NLS-1$
		for (IConfigurationElement element : elements) {
			String id = element.getAttribute("id"); //$NON-NLS-1$
			int order = getOrderStringFromElement(element, id);
			String title = element.getAttribute("title"); //$NON-NLS-1$
			String tooltip = element.getAttribute("tooltip"); //$NON-NLS-1$
			String imageFilePath = element.getAttribute("image"); //$NON-NLS-1$
			String pluginId = element.getContributor().getName();
			ImageDescriptor imageDesc = AbstractUIPlugin.imageDescriptorFromPlugin(pluginId, imageFilePath);
			List<LayerExtension> portalLayers = portalLayersMap.get(id);
			if (portalLayers == null || portalLayers.isEmpty()) {
				Activator.logError(MessageFormat.format(Messages.PortalEditor_NoLayersError, id), null);
			}
			ICommandBarFactory commandBarFactory = null;
			if (element.getAttribute("class") != null) { //$NON-NLS-1$
				try {
					commandBarFactory = (ICommandBarFactory) element.createExecutableExtension("class"); //$NON-NLS-1$
				} catch (CoreException e) {
					Activator.logError(MessageFormat.format(Messages.PortalEditor_BadCommandBarFactoryError, id), e);
				}
			}
			PortalPage portalPage = new PortalPage(title, tooltip, imageDesc, id, portalLayers, commandBarFactory);
			pageList.add(new Pair<PortalPage, Integer>(portalPage, order));
		}
		Collections.sort(pageList, new Comparator<Pair<PortalPage, Integer>>() {
			@Override
			public int compare(Pair<PortalPage, Integer> o1, Pair<PortalPage, Integer> o2) {
				return o1.second.compareTo(o2.second);
			}
		});
		pages = new ArrayList<PortalPage>();
		for (Pair<PortalPage, Integer> pair : pageList) {
			pages.add(pair.first);
		}
	}

	private int getOrderStringFromElement(IConfigurationElement element, String name) {
		int order = Integer.MAX_VALUE;
		String orderString = element.getAttribute("order"); //$NON-NLS-1$
		if (orderString != null) {
			try {
				order = Integer.parseInt(orderString);
			}
			catch (NumberFormatException e) {
				Activator.logError(MessageFormat.format(Messages.PortalEditor_PageRankError, name), e);
			}
		}
		return order;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
	}

	@Override
	public void createPartControl(final Composite parent) {
		Composite body = createBody(parent);
		navigationBar.initUI();

		PlatformUI.getWorkbench().getHelpSystem().setHelp(body, CONTEXT_ID);
	}

	private Composite createBody(final Composite parent) {
		// create background
		backgroundParent = new Composite(parent, SWT.NONE);
		applyBG(backgroundParent);
		GridLayoutFactory.fillDefaults().margins(10, 0).applyTo(backgroundParent);
		// create top navigation bar
		navigationBar = createNavigationBar(backgroundParent);
		GridDataFactory.swtDefaults().grab(true, false).align(SWT.FILL, SWT.TOP).indent(0, 10).applyTo(navigationBar);
		// create stack composite
		createStackComposite(backgroundParent, navigationBar);
		return backgroundParent;
	}

	private void createStackComposite(Composite parent, NavigationBar bar) {
		stackComposite = new StackComposite(parent, backgroundParent);
		for (PortalPage page : pages) {
			page.createPageComposite(stackComposite, this);
		}
		GridDataFactory.fillDefaults().grab(true, true).applyTo(stackComposite);
	}

	private NavigationBar createNavigationBar(Composite parent) {
		NavigationBar bar = new NavigationBar(this, parent);
		for (PortalPage page : pages) {
			bar.addNavButton(bar, page);
		}
		
		return bar;
	}
	
	void showPage(PortalPage page) {
		if (!page.isInitialized()) {
			page.initialize();
		}
		stackComposite.showControl(page.getPageControl());
	}

	private void applyBG(final Composite composite) {
		composite.addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event event) {
				Rectangle rect = composite.getClientArea();
				if (rect.isEmpty())
					return;
				Image newImage = new Image(composite.getDisplay(), rect.width, rect.height);
				GC gc = new GC(newImage);
				gc.setForeground(composite.getDisplay().getSystemColor(SWT.COLOR_WIDGET_FOREGROUND));
				gc.setBackground(composite.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
				gc.fillGradientRectangle(0, 0, rect.width, rect.height, true);
				gc.dispose();
				composite.setBackgroundImage(newImage);

				if (oldBGImg != null)
					oldBGImg.dispose();
				oldBGImg = newImage;
				backgroundParent.layout();
			}
		});
	}

	@Override
	public void setFocus() {
	}
	
	public static void openPortal() {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				try {
					if (PlatformUI.isWorkbenchRunning())
						WorkbenchUtils.openEditor(getInput(), ID);
				} catch (PartInitException e) {
					Activator.logError(Messages.PortalEditor_PageOpenError, e);
				}
			}
		});
	}
	
	private static IEditorInput getInput() {
		if (input == null) {
			input = new PortalEditorInput();
		}
			
		return input;
	}
	
	Image createImage(ImageDescriptor desc, int width, int height) {
		Image image;
		ImageData data = desc.getImageData();
		if (data.width != width || data.height != height) {
			image = new Image(Display.getCurrent(), desc.getImageData().scaledTo(width, height));
		}
		else {
			image = desc.createImage();
		}
		resources.add(image);
		return image;
	}
	
	Font createFont(FontDescriptor desc) {
		Font font = desc.createFont(Display.getCurrent());
		resources.add(font);
		return font;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		for (Resource resource : resources) {
			resource.dispose();
		}
		if (oldBGImg != null)
			oldBGImg.dispose();
	}

	public Composite getBackgroundParent() {
		return backgroundParent;
	}

	@Override
	public IEditorPart getEditorPart() {
		return this;
	}

	@Override
	public IAdaptable createElement(IMemento memento) {
		return new PortalEditorInput();
	}

}
