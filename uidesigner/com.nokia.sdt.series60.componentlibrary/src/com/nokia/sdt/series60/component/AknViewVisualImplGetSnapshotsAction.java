/*
* Copyright (c) 2005 Nokia Corporation and/or its subsidiary(-ies).
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

import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.LoadResult;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.IDisplayModel.LayoutAreaConfiguration;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.sdt.symbian.dm.S60ModelUtils;
import com.nokia.cpp.internal.api.utils.core.CacheMap;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.workspace.IDesignerDataModelSpecifier;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.*;
import org.eclipse.ui.views.properties.IPropertySource;

class AknViewVisualImplGetSnapshotsAction implements IDesignerDataModelSpecifier.IRunWithModelAction, AknViewVisualImplKeys {
	String desiredSkinName;
	Rectangle desiredBounds;
	CacheMap imageCache;
	EObject aknView;
	
	AknViewVisualImplGetSnapshotsAction(String skinName, Rectangle desiredBounds, CacheMap imageCache, EObject viewInstance) {
		this.desiredSkinName = skinName;
		this.desiredBounds = desiredBounds;
		this.imageCache = imageCache;
		this.aknView = viewInstance;
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

	private void cacheInstanceSnapshot(EObject instance, String imageKey, String positionKey, Point offset) {
		if (instance != null) {
			ILayoutObject lo = ModelUtils.getLayoutObject(instance);
			if (lo != null) {
				Image image = lo.getImage();
				Rectangle bounds = lo.getBounds();
				Point position = new Point(bounds.x, bounds.y);
				position.x += offset.x;
				position.y += offset.y;
				
				if (image != null) // put throws if the value is null
					imageCache.put(imageKey, image);
				imageCache.put(positionKey, position);
			}
		}
	}
	
	public Object run(LoadResult lr) {
		IDesignerDataModel rootModel = lr.getModel();
		if (rootModel != null) {
			EObject[] rootContainers = rootModel.getRootContainers();
			if (rootContainers != null && rootContainers.length == 1) {
				// init the display model and apply the skin we're using in the target
				EObject rootContainer = rootContainers[0];
				IDisplayModel displayModel = null;
				LayoutAreaConfiguration savedConfig = null;
				try {
					displayModel = doGetDisplayModel(rootModel, rootContainer);
					if (displayModel != null) {
						savedConfig = displayModel.getCurrentConfiguration();
						// we only need to set the bounds if the skin couldn't be set
						if (!ModelUtils.applySkinToDisplayModel(displayModel, desiredSkinName)) {
							ILayoutObject rootLO = (ILayoutObject) EcoreUtil.getRegisteredAdapter(
									rootContainers[0], ILayoutObject.class);
							if (rootLO != null) {
								rootLO.setBounds(desiredBounds);
							}
						}
					}
				}
				catch (CoreException x) {
					Series60ComponentPlugin.log(x);
					displayModel = null;
				}
				
				if (displayModel != null) {	
					// look up the components we're interested in a get the images
					EObject instance = ModelUtils.findFirstComponentInstance(rootContainer, S60ModelUtils.S60_APPUI, true);
					cacheInstanceSnapshot(instance, APPUI_IMAGE, APPUI_POSITION, new Point(0,0));
					imageCache.put(APPUI_POSITION, new Point(0,0));
					
					EObject statusPaneInstance = ModelUtils.findFirstComponentInstance(rootContainer, S60ModelUtils.S60_STATUSPANE, true);
					
                    if (statusPaneInstance != null) {
                    	boolean setActiveTab = S60ModelUtils.isNaviPaneTabbingEnabled(rootModel);
                    	if (setActiveTab)
                    		setActiveTab(rootModel, instance, false);
                        StatusPaneVisualImplFactory.cacheSnapshots(imageCache, statusPaneInstance);
                    	if (setActiveTab)
                    		setActiveTab(rootModel, instance, true);
                   }

                    EObject controlPaneInstance = ModelUtils.findFirstComponentInstance(rootContainer, S60ModelUtils.S60_CONTROL_PANE_BASE, true);
                    if (controlPaneInstance != null) {
                        cacheInstanceSnapshot(controlPaneInstance, CONTROL_PANE_IMAGE, CONTROL_PANE_POSITION, new Point(0, 0));
                    }

					if (savedConfig != null) {
						try {
							displayModel.setCurrentConfiguration(savedConfig);
						} catch (CoreException x) {
							Series60ComponentPlugin.log(x);
						}
					}
				}
			}
		}
		return null;
	}

	private void setActiveTab(IDesignerDataModel rootModel, EObject appUi, boolean initial) {
		int tabIndex = 0;
		IPropertySource properties = null;
		if (initial) {
			properties = Utilities.getPropertySource(appUi);
			String initialDesignName = (String) properties.getPropertyValue("initialDesign");
			tabIndex = getTabIndex(appUi, initialDesignName);
		}
		else {
			tabIndex = getTabIndex(appUi, getViewReferenceName(appUi));
		}
		EObject tabsInstance = ModelUtils.findFirstComponentInstance(appUi, S60ModelUtils.S60_NAVITAB, false);
		Check.checkContract(tabsInstance != null);
		properties = Utilities.getPropertySource(tabsInstance);
		properties.setPropertyValue("active", new Integer(tabIndex));
	}

	private String getViewReferenceName(EObject appUi) {
		IComponentInstance viewInstance = Utilities.getComponentInstance(aknView);
		String viewInstanceName = viewInstance.getName();
		IComponentInstance ci = Utilities.getComponentInstance(appUi);
		EObject[] children = ci.getChildren();
		for (int i = 0; i < children.length; i++) {
			EObject child = children[i];
			IComponentInstance childInstance = Utilities.getComponentInstance(child);
			if (childInstance.getComponentId().equals(S60ModelUtils.S60_AVKON_VIEW_REFERENCE)) {
				IPropertySource properties = Utilities.getPropertySource(child);
				String baseName = 
					(String) properties.getPropertyValue(S60ModelUtils.DESIGNREF_BASENAME);
				String avkonViewName = S60ModelUtils.getAvkonViewName(baseName);
				if (viewInstanceName.equals(avkonViewName))
					return childInstance.getName();
			}
		}
		return null;
	}

	private int getTabIndex(EObject appUi, String designName) {
		IComponentInstance ci = Utilities.getComponentInstance(appUi);
		EObject[] children = ci.getChildren();
		int tabIndex = -1;
		for (int i = 0; i < children.length; i++) {
			EObject child = children[i];
			IComponentInstance childInstance = Utilities.getComponentInstance(child);
			if (childInstance.getComponentId().equals(S60ModelUtils.S60_AVKON_VIEW_REFERENCE)) {
				IPropertySource properties = Utilities.getPropertySource(child);
				Boolean value = 
					(Boolean) properties.getPropertyValue(S60ModelUtils.VIEWREF_IN_TAB_GROUP);
				if (value.booleanValue()) {
					tabIndex++;
					if (childInstance.getName().equals(designName))
						return tabIndex;
				}
			}
		}
		return 0;
	}			
}