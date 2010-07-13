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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;

import com.nokia.carbide.discovery.ui.Activator;
import com.nokia.carbide.internal.discovery.ui.extension.IPortalPage;
import com.nokia.carbide.internal.discovery.ui.extension.IPortalPage.IActionBar;
import com.nokia.cpp.internal.api.utils.ui.WorkbenchUtils;

public class PortalEditor extends EditorPart {

	private static final String ID = "com.nokia.carbide.discovery.ui.portalEditor"; //$NON-NLS-1$
	private static final String CONTEXT_ID = ID + ".context"; //$NON-NLS-1$
	private static IEditorInput input;
	private List<IPortalPage> uninitializedPages;
	private Composite backgroundParent;
	private Image oldBGImg;
	private List<Resource> resources;
	private StackComposite stackComposite;
	
	private Map<IPortalPage, Control> pageToControlMap;
	private NavigationBar navigationBar;

	public PortalEditor() {
		resources = new ArrayList<Resource>();
		getPortalPages();
		pageToControlMap = new HashMap<IPortalPage, Control>();
	}
	
	private void getPortalPages() {
		uninitializedPages = new ArrayList<IPortalPage>();
		IConfigurationElement[] elements = 
			Platform.getExtensionRegistry().getConfigurationElementsFor(Activator.PLUGIN_ID + ".portalPage");
		for (IConfigurationElement element : elements) {
			try {
				uninitializedPages.add((IPortalPage) element.createExecutableExtension("class")); //$NON-NLS-1$
			} 
			catch (CoreException e) {
				Activator.logError("Could not load portal page", e);
			}
		}
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
		backgroundParent.setLayout(GridLayoutFactory.fillDefaults().create());
		// create top naviation bar
		navigationBar = createNavigationBar(backgroundParent);
		// create stack composite
		createStackComposite(backgroundParent, navigationBar);
		return backgroundParent;
	}

	private void createStackComposite(Composite parent, NavigationBar bar) {
		stackComposite = new StackComposite(parent, backgroundParent);
		for (IPortalPage page : uninitializedPages) {
			Control control = createPage(page);
			pageToControlMap.put(page, control);
		}
		stackComposite.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).create());
	}

	private Control createPage(IPortalPage page) {
		Composite pageComposite = new SharedBackgroundComposite(stackComposite, backgroundParent);
		pageComposite.setLayout(GridLayoutFactory.fillDefaults().numColumns(2).spacing(20, 0)
				.extendedMargins(20, 20, 20, 0).create());
		ActionUIUpdater updater = new ActionUIUpdater();
		IActionBar[] commandBars = page.createCommandBars(this, updater);
		if (commandBars.length > 0) {
			Composite taskComposite = new SharedBackgroundComposite(pageComposite, backgroundParent);
			taskComposite.setLayout(GridLayoutFactory.fillDefaults().create());
			taskComposite.setLayoutData(GridDataFactory.fillDefaults().grab(false, true).create());
			for (IActionBar actionBar : commandBars) {
				Control control = createTaskBarControl(taskComposite, actionBar, updater);
				control.setLayoutData(GridDataFactory.fillDefaults().indent(0, 0).create());
			}
		}
		Composite pageControl = new RoundedCornerComposite(pageComposite, backgroundParent, 
				null, pageComposite.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		pageControl.setLayout(GridLayoutFactory.fillDefaults().margins(2, 2).create());
		pageControl.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).create());
		Control control = page.createControl(pageControl, this);
		control.setLayoutData(GridDataFactory.fillDefaults().grab(true, true).create());
		return pageComposite;
	}

	private Control createTaskBarControl(Composite clientArea, IActionBar actionBar, ActionUIUpdater updater) {
		TaskBar taskBar = new TaskBar(clientArea, this, actionBar);
		updater.setTaskBar(taskBar);
		return taskBar;
	}

	private NavigationBar createNavigationBar(Composite parent) {
		NavigationBar bar = new NavigationBar(this, parent);
		for (IPortalPage page : uninitializedPages) {
			bar.addNavButton(bar, page);
		}
		
		return bar;
	}
	
	void showPage(IPortalPage page) {
		if (uninitializedPages.contains(page)) {
			uninitializedPages.remove(page);
			page.init();
		}
		stackComposite.showControl(pageToControlMap.get(page));
	}

	private void applyBG(final Composite composite) {
		composite.addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event event) {
				Rectangle rect = composite.getClientArea();
				Image newImage = new Image(composite.getDisplay(), rect.width, rect.height);
				GC gc = new GC(newImage);
				gc.setForeground(composite.getDisplay().getSystemColor(SWT.COLOR_BLACK));
				gc.setBackground(composite.getDisplay().getSystemColor(SWT.COLOR_GRAY));
				gc.fillGradientRectangle(0, 0, rect.width, rect.height, true);
				gc.dispose();
				composite.setBackgroundImage(newImage);

				if (oldBGImg != null)
					oldBGImg.dispose();
				oldBGImg = newImage;
			}
		});
	}

	@Override
	public void setFocus() {
	}
	
	public static void openPortal() {
		try {
			WorkbenchUtils.openEditor(getInput(), ID);
		} catch (PartInitException e) {
			Activator.logError("Could not open portal", e);
		}
	}

	private static IEditorInput getInput() {
		if (input == null) {
			input = new IEditorInput() {
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
					return null;
				}
				
				@Override
				public String getName() {
					return "Carbide.c++ Portal";
				}
				
				@Override
				public ImageDescriptor getImageDescriptor() {
					return null;
				}
				
				@Override
				public boolean exists() {
					return false;
				}
			};
		}
			
		return input;
	}
	
	Image createImage(ImageDescriptor desc) {
//		Image image = new Image(Display.getCurrent(), desc.getImageData().scaledTo(16, 16));
		Image image = desc.createImage();
		resources.add(image);
		return image;
	}
	
	Font createFont(String name, int height, int style) {
		Font font = new Font(Display.getCurrent(), name, height, style);
		
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

}
