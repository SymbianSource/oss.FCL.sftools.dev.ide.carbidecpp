/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.sdt.series60.component;

import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.component.symbian.laf.Series60LAF;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.*;
import com.nokia.sdt.displaymodel.IDisplayModel.LayoutAreaConfiguration;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.sdt.symbian.S60ComponentAttributes;
import com.nokia.sdt.symbian.dm.S60ModelUtils;
import com.nokia.sdt.symbian.workspace.ISymbianProjectContext;
import com.nokia.cpp.internal.api.utils.core.CacheMap;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.utils.drawing.GC;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;
import com.nokia.sdt.workspace.IProjectContext;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.*;

public class AknViewVisualImplFactory implements IImplFactory, AknViewVisualImplKeys {
	
	public Object getImpl(EObject componentInstance) {
		return new VisualAppearance(componentInstance);
	}
	
	
	static IProjectContext getProjectContext(IComponentInstance ci) {
		IProjectContext result = null;
		IDesignerDataModel model = ci.getDesignerDataModel();
		if (model != null) {
			result = model.getProjectContext();
		}
		return result;
	}
	
	static ISymbianProjectContext getSymbianProjectContext(IProjectContext projectContext) {
		ISymbianProjectContext result = (ISymbianProjectContext) 
			projectContext.getAdapter(ISymbianProjectContext.class);
		return result;
	}
	
	static class VisualAppearance implements IVisualAppearance {
		private EObject emfObject;
		private IComponentInstance componentInstance;
		private ILayoutObject layoutObject;
		private CacheMap imageCache;
		private LayoutAreaConfigurationListener configurationListener;

		VisualAppearance(EObject emfObject) {
			this.emfObject = emfObject;
			this.componentInstance = (IComponentInstance) EcoreUtil.getRegisteredAdapter(
					emfObject, IComponentInstance.class);
			this.layoutObject = (ILayoutObject) EcoreUtil.getRegisteredAdapter(
					emfObject, ILayoutObject.class);
			Check.checkArg(componentInstance);
			Check.checkArg(layoutObject);
            
            // react to future changes and regenerate 
            EObject statusPaneInstance = ModelUtils.findFirstComponentInstance(emfObject, S60ModelUtils.S60_STATUSPANE, true);
            if (statusPaneInstance != null) {
                ModelUtils.getComponentInstance(statusPaneInstance).addChildListener(new IComponentInstanceChildListener() {

                    private void redraw() {
                        purgeCache();
                        cacheSnapshots();
                        
                        ILayoutObject layoutObject = ModelUtils.getLayoutObject(VisualAppearance.this.emfObject);
                        if (layoutObject != null)
                            layoutObject.forceRedraw();
                    }
                        
                    public void childAdded(EObject parent, EObject child) {
                        redraw();
                    }
                    public void childRemoved(EObject parent, EObject child) {
                        redraw();
                    }

                    public void childrenReordered(EObject parent) {
                    }});
            }
        }
		
		public EObject getEObject() {
			return emfObject;
		}
		
		private void drawCachedImage(GC gc, String imageKey, String positionKey) {
			Image image = (Image) imageCache.get(imageKey);
			Point position = (Point) imageCache.get(positionKey);
			if (image != null && position != null) {
				gc.drawImage(image, position.x, position.y);
			}
		}

		public void draw(GC gc, ILookAndFeel laf) {
		    EObject obj = componentInstance.getEObject();
/*
 * Changed to inhibit caching behavior without removing the support
 * for caching present in AknViewVisualImplFactor, StatusPaneVisualImplFactory,
 * and AknViewVisualImplGetSnapshotsAction. This cache reflects data
 * from the root model, and there's no notification protocol in place
 * to notify when it's stale. However, since the root model is currently
 * cached, in theory we could just get the images we need every time we need
 * to render, especially since the images rendered by a component are in
 * turn cached by the editor. But removing all this support would be a pain
 * if we ever did need this caching. So for now this is changed to re-get the
 * images every time we need to redraw.
 * old behavior == useCache = true;
 */
		    final boolean useCache = false;
		    if (useCache) {
		    	if (imageCache == null) {
		    		cacheSnapshots();
		    		if (imageCache == null)
		    			return;
		    	}
		    } else {
		    	cacheSnapshots();
		    	if (imageCache == null)
		    		return;
		    }
 
            EObject statusPaneInstance = ModelUtils.findFirstComponentInstance(obj, S60ModelUtils.S60_STATUSPANE, true);
            
			boolean hasLocalStatusContextPane = statusPaneInstance != null && 
			    ModelUtils.findFirstComponentInstance(statusPaneInstance, S60ModelUtils.S60_CONTEXT_ICON_BASE, true) != null;
			boolean hasLocalStatusTitlePane = statusPaneInstance != null &&
			    ModelUtils.findFirstComponentInstance(statusPaneInstance, S60ModelUtils.S60_TITLE_PANE_BASE, true) != null;
			boolean hasLocalControlPane = statusPaneInstance != null &&
			    ModelUtils.findFirstComponentInstance(statusPaneInstance, S60ModelUtils.S60_CONTROL_PANE_BASE, true) != null;
			boolean hasLocalNaviContentPane = statusPaneInstance != null &&
				ModelUtils.findImmediateChildWithAttributeValue(statusPaneInstance, 
						S60ComponentAttributes.NAVIPANE_CONTENT, "true") != null;
			
			drawCachedImage(gc, APPUI_IMAGE, APPUI_POSITION);
			
			if (!hasLocalStatusContextPane && laf.getBoolean(Series60LAF.SHOW_CONTEXT_ICON, true)) {
				drawCachedImage(gc, STATUS_PANE_CONTEXT_IMAGE, STATUS_PANE_CONTEXT_POSITION);
			}
			
			if (!hasLocalStatusTitlePane) {
				drawCachedImage(gc, STATUS_PANE_TITLE_IMAGE, STATUS_PANE_TITLE_POSITION);
			}
			
			if (!hasLocalControlPane) {
				drawCachedImage(gc, CONTROL_PANE_IMAGE, CONTROL_PANE_POSITION);
			}
			
			if (!hasLocalNaviContentPane) {
				drawCachedImage(gc, NAVI_CONTENT_IMAGE, NAVI_CONTENT_POSITION);
			}
		}

		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.IVisualAppearance#getPreferredSize(int, int, com.nokia.sdt.displaymodel.ILookAndFeel)
		 */
		public Point getPreferredSize(int wHint, int hHint, ILookAndFeel laf) {
			return null;
		}
		
		private void purgeCache() {
			if (imageCache != null) {
				imageCache.dispose();
				imageCache = null;
			}
		}
		
		private IDisplayModel doGetDisplayModel(IDesignerDataModel model, EObject container) {
			IDisplayModel result = null;
			try {
				result = model.getDisplayModelForRootContainer(container);
			}
			catch (CoreException x) {
				Series60ComponentPlugin.log(x);
			}
			return result;
		}
		
		private void cacheSnapshots() {
			EObject rootContainer = componentInstance.getRootContainer();
			final IDisplayModel displayModel = doGetDisplayModel(componentInstance.getDesignerDataModel(),rootContainer);
			if (displayModel != null) {
				if (imageCache != null) {
					imageCache.disposeAll();
				}
				else {
					imageCache = new CacheMap();
					displayModel.getCache().put(CACHEIMAGEKEY, imageCache);
				}
				String currentSkinName = "";
                if (displayModel.getCurrentConfiguration() != null)
                    currentSkinName = displayModel.getCurrentConfiguration().getSkin().getDisplayName();
				IProjectContext pc = getProjectContext(componentInstance);
				if (pc != null) {
					ISymbianProjectContext spc = getSymbianProjectContext(pc);
					if (spc != null) {
						IDesignerDataModelSpecifier rootModelSpec = spc.getRootModel();
						if (rootModelSpec != null) {
							
							AknViewVisualImplGetSnapshotsAction action = 
								new AknViewVisualImplGetSnapshotsAction(currentSkinName, 
										layoutObject.getBounds(), imageCache, emfObject);
							rootModelSpec.runWithLoadedModelNoSourceGen(action);
							
							if (configurationListener == null) {
								configurationListener = new LayoutAreaConfigurationAdapter() {
									public void configurationChanging(LayoutAreaConfiguration configuration) {
										purgeCache();		
									}
								};
								displayModel.addLayoutAreaListener(configurationListener);
							}
						}
					}
				}
			}
		}
	}
}
