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
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.nokia.carbide.internal.discovery.ui.extension.IPortalPageLayer;
import com.nokia.carbide.internal.discovery.ui.extension.IPortalPageLayer.IActionBar;


public class PortalPage implements IActionBar {
	
	private class ChangeLayerAction extends Action {
		private Layer layer;

		private ChangeLayerAction(Layer layer) {
			super(layer.getLayer().getTitle());
			this.layer = layer;
		}

		public void run() {
			setCurrentLayer(layer);
		}

		public boolean isEnabled() {
			return !layer.equals(currentLayer);
		}
	}
	
	private class Layer {
		private IPortalPageLayer layer;
		private boolean initialized;
		private Control control;
		
		public Layer(IPortalPageLayer layer) {
			this.layer = layer;
		}
		
		public IPortalPageLayer getLayer() {
			return layer;
		}
		
		public void initialize() {
			layer.init();
			this.initialized = true;
		}
		
		public boolean isInitialized() {
			return initialized;
		}
		
		public void setControl(Control control) {
			this.control = control;
		}
		
		public Control getControl() {
			return control;
		}
	}

	private String id;
	private String title;
	private ImageDescriptor imageDescriptor;
	private StackComposite pageComposite;
	private boolean initialized;
	private List<Layer> layers;
	private Layer currentLayer;
	private IAction[] actions;
	private List<TaskBar> navigationTaskBars;
	
	public PortalPage(String title, ImageDescriptor imageDescriptor, String id, List<IPortalPageLayer> layerExtensions) {
		this.title = title;
		this.imageDescriptor = imageDescriptor;
		this.id = id;
		layers = new ArrayList<Layer>(layerExtensions.size());
		for (IPortalPageLayer layerExtension : layerExtensions) {
			layers.add(new Layer(layerExtension));
		}
		navigationTaskBars = new ArrayList<TaskBar>(layerExtensions.size());
	}
	
	public String getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public ImageDescriptor getImageDescriptor() {
		return imageDescriptor;
	}
	
	public Control getPageControl() {
		return pageComposite;
	}

	public void initialize() {
		setCurrentLayer(layers.get(0));
		initialized = true;
	}
	
	public boolean isInitialized() {
		return initialized;
	}

	public void createPageComposite(Composite parent, PortalEditor portalEditor) {
		Composite backgroundParent = portalEditor.getBackgroundParent();
		pageComposite = new StackComposite(parent, backgroundParent);
		for (Layer layer : layers) {
			Composite layerComposite = new SharedBackgroundComposite(pageComposite, backgroundParent);
			GridDataFactory.fillDefaults().grab(true, true).applyTo(layerComposite);
			GridLayoutFactory.fillDefaults().numColumns(2).spacing(20, 0).extendedMargins(20, 20, 20, 0).applyTo(layerComposite);
			createTaskBarControls(portalEditor, layerComposite, layer.getLayer());
			createMainControl(portalEditor, layerComposite, layer.getLayer());
			layer.setControl(layerComposite);
		}
	}
	
	protected void createTaskBarControls(PortalEditor portalEditor, Composite pageComposite, IPortalPageLayer layerExtension) {
		Composite backgroundParent = portalEditor.getBackgroundParent();
		Composite taskComposite = new SharedBackgroundComposite(pageComposite, backgroundParent);
		GridLayoutFactory.fillDefaults().applyTo(taskComposite);
		GridDataFactory.fillDefaults().grab(false, true).applyTo(taskComposite);
		TaskBar taskBar = new TaskBar(taskComposite, backgroundParent, this);
		GridDataFactory.fillDefaults().minSize(150, SWT.DEFAULT).grab(true, false).indent(0, 0).applyTo(taskBar);
		navigationTaskBars.add(taskBar);
		ActionUIUpdater updater = new ActionUIUpdater();
		IActionBar[] commandBars = layerExtension.createCommandBars(portalEditor, updater);
		for (IActionBar actionBar : commandBars) {
			taskBar = new TaskBar(taskComposite, backgroundParent, actionBar);
			updater.addTaskBar(taskBar);
			GridDataFactory.fillDefaults().minSize(150, SWT.DEFAULT).grab(true, false).indent(0, 0).applyTo(taskBar);
		}
	}

	protected void createMainControl(PortalEditor portalEditor, Composite pageComposite, IPortalPageLayer layerExtension) {
		Color background = pageComposite.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
		Composite mainControl = 
			new RoundedCornerComposite(pageComposite, portalEditor.getBackgroundParent(), null, background);
		GridLayoutFactory.fillDefaults().margins(2, 2).applyTo(mainControl);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(mainControl);
		Control control = layerExtension.createControl(mainControl, portalEditor);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(control);
	}

	@Override
	public IAction[] getActions() {
		if (actions == null) {
			actions = new Action[layers.size()];
			int i = 0;
			for (Layer layer : layers) {
				actions[i++] = new ChangeLayerAction(layer);
			}
		}
		return actions;
	}

	@Override
	public String[] getHighlightedActionIds() {
		return null;
	}

	protected void setCurrentLayer(Layer layer) {
		currentLayer = layer;
		pageComposite.showControl(currentLayer.getControl());
		if (!currentLayer.isInitialized())
			currentLayer.initialize();
		for (TaskBar taskBar : navigationTaskBars) {
			taskBar.updateAllActionsUI();
		}
	}
}
