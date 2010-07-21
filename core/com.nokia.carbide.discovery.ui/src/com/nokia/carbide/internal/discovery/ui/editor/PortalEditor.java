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
import com.nokia.carbide.discovery.ui.Messages;
import com.nokia.carbide.internal.discovery.ui.extension.IPortalPage;
import com.nokia.carbide.internal.discovery.ui.extension.IPortalPage.IActionBar;
import com.nokia.cpp.internal.api.utils.core.Pair;
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
		loadPortalPages();
		pageToControlMap = new HashMap<IPortalPage, Control>();
	}
	
	private void loadPortalPages() {
		List<Pair<IPortalPage, Integer>> pageExtensions = new ArrayList<Pair<IPortalPage,Integer>>();
		IConfigurationElement[] elements = 
			Platform.getExtensionRegistry().getConfigurationElementsFor(Activator.PLUGIN_ID + ".portalPage"); //$NON-NLS-1$
		for (IConfigurationElement element : elements) {
			try {
				IPortalPage portalPage = (IPortalPage) element.createExecutableExtension("class"); //$NON-NLS-1$
				String rankString = element.getAttribute("rank"); //$NON-NLS-1$
				int rank = Integer.MAX_VALUE;
				if (rankString != null) {
					try {
						rank = Integer.parseInt(rankString);
					}
					catch (NumberFormatException e) {
						Activator.logError(MessageFormat.format(Messages.PortalEditor_PageRankError,
										portalPage.getClass().getName()), e);
					}
				}
				pageExtensions.add(new Pair<IPortalPage, Integer>(portalPage, rank));
			} 
			catch (CoreException e) {
				Activator.logError(Messages.PortalEditor_PageLoadError, e);
			}
		}
		Collections.sort(pageExtensions, new Comparator<Pair<IPortalPage, Integer>>() {
			@Override
			public int compare(Pair<IPortalPage, Integer> o1, Pair<IPortalPage, Integer> o2) {
				return o1.second.compareTo(o2.second);
			}
		});
		uninitializedPages = new ArrayList<IPortalPage>();
		for (Pair<IPortalPage, Integer> pair : pageExtensions) {
			uninitializedPages.add(pair.first);
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
		GridLayoutFactory.fillDefaults().applyTo(backgroundParent);
		// create top naviation bar
		navigationBar = createNavigationBar(backgroundParent);
		GridDataFactory.swtDefaults().grab(true, false).align(SWT.CENTER, SWT.TOP).indent(10, 10).applyTo(navigationBar);
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
		GridDataFactory.fillDefaults().grab(true, true).applyTo(stackComposite);
	}

	private Control createPage(IPortalPage page) {
		Composite pageComposite = new SharedBackgroundComposite(stackComposite, backgroundParent);
		GridLayoutFactory.fillDefaults().numColumns(2).spacing(20, 0).extendedMargins(20, 20, 20, 0).applyTo(pageComposite);
		ActionUIUpdater updater = new ActionUIUpdater();
		IActionBar[] commandBars = page.createCommandBars(this, updater);
		if (commandBars.length > 0) {
			Composite taskComposite = new SharedBackgroundComposite(pageComposite, backgroundParent);
			GridLayoutFactory.fillDefaults().applyTo(taskComposite);
			GridDataFactory.fillDefaults().grab(false, true).applyTo(taskComposite);
			for (IActionBar actionBar : commandBars) {
				Control control = createTaskBarControl(taskComposite, actionBar, updater);
				GridDataFactory.fillDefaults().minSize(150, SWT.DEFAULT).grab(true, false).indent(0, 0).applyTo(control);
			}
		}
		Composite pageControl = new RoundedCornerComposite(pageComposite, backgroundParent, 
				null, pageComposite.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		GridLayoutFactory.fillDefaults().margins(2, 2).applyTo(pageControl);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(pageControl);
		Control control = page.createControl(pageControl, this);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(control);
		return pageComposite;
	}

	private Control createTaskBarControl(Composite clientArea, IActionBar actionBar, ActionUIUpdater updater) {
		TaskBar taskBar = new TaskBar(clientArea, this, actionBar);
		updater.addTaskBar(taskBar);
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
		try {
			WorkbenchUtils.openEditor(getInput(), ID);
		} catch (PartInitException e) {
			Activator.logError(Messages.PortalEditor_PageOpenError, e);
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
					return Messages.PortalEditor_Name;
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

}
