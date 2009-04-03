/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.component.symbian.displaymodel;

import com.nokia.sdt.component.adapter.CommonAttributes;
import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.component.symbian.laf.SymbianLookAndFeel;
import com.nokia.sdt.component.symbian.properties.ComponentReferencePropertyDescriptor;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.adapter.ComponentInstanceVisitor.Visitor;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.GlobalCache;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.displaymodel.LayoutAreaConfigurationListener;
import com.nokia.sdt.displaymodel.adapter.IDisplayObject;
import com.nokia.sdt.displaymodel.adapter.ILayoutContainer;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.sdt.editor.EditorServices;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.ui.skin.ISkin;
import com.nokia.sdt.ui.skin.SkinManager;
import com.nokia.sdt.uimodel.UIModelPlugin;
import com.nokia.cpp.internal.api.utils.core.CacheMap;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.Logging;
import com.nokia.sdt.workspace.WorkspaceContext;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This is the base of the display model which abstracts away the notions
 * of how layout objects/containers are generated and how layout area
 * configurations are handled.
 * 
 * 
 *
 */
public abstract class DisplayModelBase implements IDisplayModel {

	protected static ArrayList displayModels = new ArrayList();
	
	enum Variant {
		SERIES_60,
		UIQ
	};
	@SuppressWarnings("unused") Variant variant; // not used yet
	protected IDesignerDataModel dataModel;
	protected EObject rootContainer;
	private URL baseDataURL;
	
	static final String SKIN_CACHE_KEY = DisplayModelBase.class.getName() + ".skins";
	static final String LAF_CACHE_KEY = DisplayModelBase.class.getName() + ".laf";
	private CacheMap cache = new CacheMap();
	
	private HashMap<IComponentInstance, ComponentInstanceListener> instanceListeners = new HashMap();
	
	protected List<LayoutAreaConfiguration> configurations = new ArrayList<LayoutAreaConfiguration>();
	protected LayoutConfiguration currentConfiguration;
	List<LayoutAreaConfigurationListener> configurationListeners = new ArrayList();

	public DisplayModelBase() {
		synchronized(displayModels) {
			displayModels.add(this);
		}
	}

	public void initialize(IDesignerDataModel dataModel, EObject rootContainer) throws CoreException {
		Check.checkArg(dataModel);
		Check.checkArg(rootContainer);
		this.dataModel = dataModel;
		this.rootContainer = rootContainer;
		
		IComponentInstance rootInstance = ModelUtils.getComponentInstance(rootContainer);
		Check.checkContract(rootInstance != null);
		
		try {
			baseDataURL = getBaseDataURL();
		} catch (IOException e) {
			ComponentSystemPlugin.log(e);
			baseDataURL = null;
		}
		
		addComponentInstanceListener(rootInstance);
		
		dataModel.addAdapterFactory(new DataModelAdapterFactory(this));
		
		// Make sure the root container has a non-empty size. We would like
		// layout code to be able to use the ILookAndFeel. To automatically
		// pick a look and feel we need to know the root container size (and may 
		// other parameters). So before initializing the look and feel, and forcing
		// an initial layout, check root container
		ILayoutObject rootLO = ModelUtils.getLayoutObject(rootContainer);
		Check.checkContract(rootLO != null);
		org.eclipse.swt.graphics.Rectangle bounds = rootLO.getBounds();
		if (bounds == null || bounds.isEmpty()) {
			// default, should never/rarely happen -- init to classic size
			bounds = new Rectangle(0, 0, 176, 208);
			rootLO.setBounds(bounds);
		}
	
		initConfigurations();
		
		// Force initial layout, in case some children are not layed
		// out. We must assume the root container has a size, otherwise
		// we can't init an ILookAndFeel.
		ILayoutContainer lc = (ILayoutContainer) EcoreUtil.getRegisteredAdapter(
				rootContainer, ILayoutContainer.class);
		Check.checkContract(lc != null);
		lc.layoutChildren();
	}
	
	/** Determine the base URL for display model related data. */
	abstract protected URL getBaseDataURL()  throws IOException;
	
	/** Initialize the LayoutAreaConfigurations */
	abstract protected void initConfigurations() throws CoreException;

	/** Create a ComponentInstanceListenerBase instance */
	abstract protected ComponentInstanceListener addComponentInstanceListener(IComponentInstance ci);
	
	public void dispose() {
		
		dataModel.displayModelDisposed(this);
		dataModel = null;
		
		// disconnect all listeners
		synchronized(instanceListeners) {
			for (Iterator iter = instanceListeners.entrySet().iterator(); iter.hasNext();) {
				Map.Entry element = (Map.Entry) iter.next();
				ComponentInstanceListener cil = (ComponentInstanceListener) element.getValue();
				cil.disconnect();
			}
			instanceListeners.clear();
		}
		
		cache.disposeAll();
		
		// After the last display model is disposed we clear the
		// cache.
		boolean isLast;
		synchronized(displayModels) {
			displayModels.remove(this);
			isLast = displayModels.size() == 0;
		}		
		if (isLast) {
			GlobalCache.disposeAll();
			// remove any still-cached models and sourcegen info
			WorkspaceContext.getContext().unloadCachedModels();
		}
		// clear references to help with leak checking
		cache = null;
		configurationListeners = null;
		configurations = null;
		currentConfiguration = null;
		instanceListeners = null;
		rootContainer = null;
	}

	public EObject getRootContainer() {
		return rootContainer;
	}

	public boolean isSelectAllObject(EObject object) {
		IDisplayObject displayObject =
			(IDisplayObject) EcoreUtil.getRegisteredAdapter(object, IDisplayObject.class);
		boolean isRemovable = (displayObject != null) && displayObject.isRemovable();
		boolean isSystemComponent = 
			Utilities.booleanAttribute(this, object, CommonAttributes.IS_SYSTEM_COMPONENT, false);
		// don't select all the transient children of a transient object
		boolean isTransient = Utilities.isTransient(displayObject) && 
				!Utilities.isTransientObject(displayObject.getEObject());;
		
		return isRemovable && !isSystemComponent && !isTransient;
	}

	public ILookAndFeel getLookAndFeel() {
		return currentConfiguration != null? 
				currentConfiguration.getLookAndFeel() : null;
	}

	public CacheMap getCache() {
		return cache;
	}

	protected void registerInstanceListener(IComponentInstance ci, ComponentInstanceListener listener) {
		synchronized (instanceListeners) {
			instanceListeners.put(ci, listener);
		}
	}

	protected void unregisterInstanceListener(IComponentInstance ci) {
		ComponentInstanceListener listener = null;
		synchronized(instanceListeners) {
			listener = (ComponentInstanceListener) instanceListeners.get(ci);
			instanceListeners.remove(ci);
		}
		
		if (listener != null) {
			listener.disconnect();
		}
	}

	protected ComponentInstanceListener listenerForInstance(IComponentInstance ci) {
		ComponentInstanceListener listener = null;
		synchronized(instanceListeners) {	
			listener = (ComponentInstanceListener) instanceListeners.get(ci);
		}
		return listener;
	}

	/** Get a cached look and feel or load the LAF from the XML file at the given URL */
	protected SymbianLookAndFeel getLookAndFeel(URL lafURL) {
		SymbianLookAndFeel result = null;
		CacheMap globalCache = GlobalCache.getCache();
		CacheMap lafCache = (CacheMap) globalCache.get(LAF_CACHE_KEY);
		if (lafCache != null) {
			result = (SymbianLookAndFeel) lafCache.get(lafURL);
		}	
		else {
			lafCache = new CacheMap();
			globalCache.put(LAF_CACHE_KEY, lafCache);
		}
		
		if (result == null) {
			try {
			    URI uri = URI.createURI(lafURL.toString());
			    result = new SymbianLookAndFeel(this, uri);
			    lafCache.put(lafURL, result);
			}
			catch (IOException x) {
				String fmt = Messages.getString("DisplayModelBase.CannotLoadLAFFile"); //$NON-NLS-1$
				Object params[]= {lafURL};
				String msg = MessageFormat.format(fmt, params);
				IStatus status = Logging.newSimpleStatus(ComponentSystemPlugin.getDefault(),
						IStatus.ERROR, msg, x);
				Logging.log(ComponentSystemPlugin.getDefault(), status);
			}
		}
		return result;
	}

	/** Get a cached skin or load the skin from the skin XML file at the given URL */
	protected ISkin getSkin(URL skinURL) {
		ISkin result = null;
		CacheMap globalCache = GlobalCache.getCache();
		CacheMap skinCache = (CacheMap) globalCache.get(SKIN_CACHE_KEY);
		if (skinCache != null) {
			result = (ISkin) skinCache.get(skinURL);
		}	
		else {
			skinCache = new CacheMap();
			globalCache.put(SKIN_CACHE_KEY, skinCache);
		}
		
		if (result == null) {
			result = SkinManager.loadSkin(skinURL);
			if (result != null) {
				skinCache.put(skinURL, result);
			}
		}
		return result;
	}

	public URL getSkinURL(String skinFile) {
		try {
			return new URL(baseDataURL, "skins/" + skinFile); //$NON-NLS-1$
		} catch (MalformedURLException e) {
			return null;
		} 
	}

	public URL getLAFURL(String lafFile) {
		try {
			return new URL(baseDataURL, "laf/" + lafFile); //$NON-NLS-1$
		} catch (MalformedURLException e) {
			return null;
		}
	}

	public URL getFontURL(String fontFile) {
		try {
			return new URL(baseDataURL, "fonts/" + fontFile); //$NON-NLS-1$
		} catch (MalformedURLException e) {
			return null;
		}
	}

	public URL getImageURL(String imageFile) {
		try {
			return new URL(baseDataURL, "images/" + imageFile); //$NON-NLS-1$
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
	protected boolean isRootContainer(ILayoutContainer container) {
		ILayoutObject layoutObject = 
			(ILayoutObject) EcoreUtil.getRegisteredAdapter(container.getEObject(), ILayoutObject.class);
		return layoutObject.getParentContainer() == null;
	}

	boolean isLayoutObject(EObject obj) {
		return Utilities.booleanAttribute(this, obj, CommonAttributes.IS_LAYOUT_OBJECT, false);
	}

	boolean isContainer(EObject obj) {
		return Utilities.booleanAttribute(this, obj, CommonAttributes.IS_LAYOUT_CONTAINER, false);
	}

	private void renderComposite(ILayoutObject layoutObject, GC gc, int hOrigin,
			int vOrigin) {
				Image image = layoutObject.getImage();
				int x = hOrigin;
				int y = vOrigin;
				if (image != null) {
					gc.drawImage(image, x, y);
					image.dispose();
				}
				IComponentInstance ci = ModelUtils.getComponentInstance(layoutObject.getEObject());
				if (ci != null) {
					EObject[] children = ci.getChildren();
					if (children != null)  {
						for (int i = 0; i < children.length; i++) {
							if (!Utilities.isTransientObject(children[i])) {
								ILayoutObject child = ModelUtils.getLayoutObject(children[i]);
								if (child != null) {
									Rectangle childBounds = child.getBounds();
									if (childBounds != null) {
										x = hOrigin + childBounds.x;
										y = vOrigin + childBounds.y;
										renderComposite(child, gc, x, y);
									}
								}
							}
						}
					}
				}
			}

	public Image createCompositeImage() {
		Image image = null;
		ILayoutObject rootLayoutObject = ModelUtils.getLayoutObject(rootContainer);
		if (rootLayoutObject != null) {
			Rectangle rootBounds = rootLayoutObject.getBounds();
			if (rootBounds != null && !rootBounds.isEmpty()) {
				image = new Image(Display.getDefault(), rootBounds.width, rootBounds.height);
				GC gc = new GC(image);
				renderComposite(rootLayoutObject, gc, 0, 0);
				gc.dispose();   
			}
		}
		return image;
	}

	protected abstract ILayoutContainer _createLayoutContainer(IComponentInstance ci);
	
	public final ILayoutContainer createLayoutContainer(IComponentInstance ci) {
		String containerDelegateProperty = getContainerIndirectionRefPropertyName(ci.getEObject());
		if (containerDelegateProperty != null) {
			IPropertySource ps = Utilities.getPropertySource(ci.getEObject());
			Object containerDelegateName = ps.getPropertyValue(containerDelegateProperty);
			if (containerDelegateName != null) {
				EObject containerDelegate = 
					ci.getDesignerDataModel().findByNameProperty(containerDelegateName.toString());
				if (containerDelegate != null) {
					ci = Utilities.getComponentInstance(containerDelegate);
				}
			}
		}
		
		return _createLayoutContainer(ci);
	}

	public EObject getContentContainer() {
		EObject rootContainer = dataModel.getRootContainers()[0];
		return Utilities.findFirstNodeByAttribute(rootContainer, 
						CommonAttributes.IS_TOP_LEVEL_CONTENT_CONTAINER);
	}

	private IDesignerEditor getDesignerEditor() {
		IEditorPart editorPart = EditorServices.findEditor(dataModel);
		Check.checkState(editorPart != null);
		return (IDesignerEditor) editorPart.getAdapter(IDesignerEditor.class);
	}

	public void setSelectedObjects(final EObject[] objects) {
		Visitor visitor = new Visitor() {
			public Object visit(IComponentInstance ci) {
				EObject obj = ci.getEObject();
				ILayoutContainer container = ModelUtils.getLayoutContainer(obj);
				if (container != null &&
						Utilities.booleanAttribute(DisplayModelBase.this, obj, 
								CommonAttributes.SWITCHABLE_CHILD_CONTAINER, false)) {
					((LayoutContainerBase) container).setSelectedObjects(objects);
				}
				return null;
			}
		};
		ComponentInstanceVisitor.preOrderTraversal(rootContainer, visitor);
	}

	public void refreshObjectInViewer(EObject object) {
		GraphicalViewer viewer = getDesignerEditor().getUpperGraphicalViewer();
		EditPart part = (EditPart) viewer.getEditPartRegistry().get(object);
		if (part != null)
			part.refresh();
	}

	public IDesignerDataModel getDataModel() {
		return dataModel;
	}

	public Collection<LayoutAreaConfiguration> getLayoutAreaConfigurations() {
		return configurations;
	}

	public void setCurrentConfiguration(LayoutAreaConfiguration layout) throws CoreException {
		// if configurations does not contain the one passed in, no-op
		if (!configurations.contains(layout))
			return;
		
		if (currentConfiguration == null || !currentConfiguration.getID().equals(layout.getID())) {
			fireLayoutAreaConfigurationChanging(layout);
			currentConfiguration = (LayoutConfiguration) layout;
			
			// resize root container to match the skin
			Rectangle newBounds = layout.getSkin().getEditorAreaBounds();
			ILayoutObject rootLO = ModelUtils.getLayoutObject(rootContainer);
			if (rootLO != null) {
				rootLO.setBounds(newBounds);
			}
	
			fireLayoutAreaConfigurationChanged();
		}
	}

	void fireLayoutAreaConfigurationChanging(LayoutAreaConfiguration newConfiguration) {
		for (Iterator iter = configurationListeners.iterator(); iter.hasNext();) {
			LayoutAreaConfigurationListener l = (LayoutAreaConfigurationListener) iter.next();
			l.configurationChanging(newConfiguration);
		}
	}

	void fireLayoutAreaConfigurationChanged() {
		for (Iterator iter = configurationListeners.iterator(); iter.hasNext();) {
			LayoutAreaConfigurationListener l = (LayoutAreaConfigurationListener) iter.next();
			l.configurationChanged(currentConfiguration);
		}
	}

	public LayoutAreaConfiguration getCurrentConfiguration() {
		return currentConfiguration;
	}

	public void addLayoutAreaListener(LayoutAreaConfigurationListener listener) {
		ArrayList newList = new ArrayList(configurationListeners);
		newList.add(listener);
		configurationListeners = newList;
	}

	public void removeLayoutAreaListener(LayoutAreaConfigurationListener listener) {
		if (configurationListeners == null)
			return;
		ArrayList newList = new ArrayList(configurationListeners);
		newList.remove(listener);
		configurationListeners = newList;
	}

	public void layoutPropertyChanged() {
		String oldLayout = currentConfiguration.getID();
		try {
			initConfigurations();
		} catch (CoreException x) {
			UIModelPlugin.log(x);
		}
		for (Iterator iter = configurationListeners.iterator(); iter.hasNext();) {
			LayoutAreaConfigurationListener l = (LayoutAreaConfigurationListener) iter.next();
			l.configurationSetChanged(configurations);
		}
		
		if (!oldLayout.equals(currentConfiguration.getID())) {
			fireLayoutAreaConfigurationChanging(currentConfiguration);
			fireLayoutAreaConfigurationChanged();
		}
	}

	protected ISkin createDummySkin() {
		return new ISkin() {

			public RGB getBackgroundRGB() {
				return new RGB(192, 192, 192);
			}

			public String getDisplayName() {
				return "Dummy configuration"; //$NON-NLS-1$
			}

			public Rectangle getEditorAreaBounds() {
				return new Rectangle(64, 64, 320, 200);
			}

			public Collection getHotzones() {
				return Collections.EMPTY_LIST;
			}

			public String getID() {
				return "$$dummy$$";
			}

			public Image getImage() {
				return null;
			}

			public Point getSkinSize() {
				return new Point(320, 200);
			}
			
		};
	}
	
	protected ILookAndFeel createDummyLookAndFeel() {
		return new SymbianLookAndFeel(this);
	}
	
	String getContainerIndirectionRefPropertyName(EObject object) {
		return Utilities.getStringAttribute(object, 
				CommonAttributes.LAYOUT_CONTAINER_INDIRECTION_REFERENCE_PROPERTY);
	}

	boolean isContainerIndirectionReference(EObject container) {
		Collection<ReferenceInfo> references = findObjectsWithReferencesTo(getRootContainer(), container, false);
		for (ReferenceInfo reference : references) {
			String propertyName = 
				getContainerIndirectionRefPropertyName(reference.getObjectWithReference());
			if (propertyName != null) {
				String propertyNamePattern = "(?:.*\\.)*" + propertyName; //$NON-NLS-1$
				if (reference.getPropertyPath().matches(propertyNamePattern)) {
					return true;
				}
			}
		}
		
		return false;
	}

	void refreshEditor() {
		IEditorPart editorPart = EditorServices.findEditor(getDataModel());
		if (editorPart != null) {
			IDesignerEditor editor = (IDesignerEditor) editorPart.getAdapter(IDesignerEditor.class);
			editor.refreshFromModel();
		}
	}
	
	static class ReferenceInfo {
		private EObject objectWithReference;
		private String propertyPath;
		public ReferenceInfo(EObject objectWithReference, String propertyPath) {
			this.objectWithReference = objectWithReference;
			this.propertyPath = propertyPath;
		}
		public EObject getObjectWithReference() {
			return objectWithReference;
		}
		public String getPropertyPath() {
			return propertyPath;
		}
	}

	public static Collection<ReferenceInfo> findObjectsWithReferencesTo(
						EObject root, EObject referent, final boolean findFirstOnly) {
		final String referentName = Utilities.getComponentInstance(referent).getName();
		final List<ReferenceInfo> resultList = new ArrayList(); 
		ComponentInstanceVisitor.preOrderTraversal(root, new Visitor() {
			
			public Object visit(final IComponentInstance ci) {
				Object result = PropertyVisitor.visit(ci.getEObject(), new PropertyVisitor.Visitor() {
					final ReferenceInfo[] localResult = { null };
					
					public boolean visit(IPropertyDescriptor pd, Object value) {
						if (pd instanceof ComponentReferencePropertyDescriptor) {
							if (referentName.equals(value)) {
								ComponentReferencePropertyDescriptor refpd = 
									(ComponentReferencePropertyDescriptor) pd;
								localResult[0] = 
									new ReferenceInfo(ci.getEObject(), refpd.getPropertyPath());
							}
						}
						return true;
					}

					public Object getCompletionResult() {
						return localResult[0];
					}
					
				});
				if (result != null) {
					resultList.add((ReferenceInfo) result);
					if (findFirstOnly)
						return result;
				}
				return null;
			}
		});
		
		return resultList;
	}
}
