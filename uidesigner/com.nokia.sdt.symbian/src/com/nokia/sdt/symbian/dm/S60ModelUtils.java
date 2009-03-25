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

package com.nokia.sdt.symbian.dm;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.SetPropertyCommand;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.ArrayList;

/**
 * Utilities for interacting with S60-specific aspects of models
 *
 */
public abstract class S60ModelUtils extends SymbianModelUtils {

	public enum S60RootModelType {
		VIEW_APPUI,
		BASIC_APPUI,
		LEGACY,
		UNKNOWN
	}
	
	// fixed component IDs
	public static final String S60_DESIGN_REFERENCE = "com.nokia.sdt.series60.DesignReference"; //$NON-NLS-1$
	public static final String S60_AVKON_VIEW_REFERENCE = "com.nokia.sdt.series60.AvkonViewReference"; //$NON-NLS-1$
	public static final String S60_AVKON_VIEW = "com.nokia.sdt.series60.CAknView"; //$NON-NLS-1$
	public static final String S60_VIEW_APPUI = "com.nokia.sdt.series60.CAknViewAppUi"; //$NON-NLS-1$
	public static final String S60_APPUI = "com.nokia.sdt.series60.CAknAppUi"; //$NON-NLS-1$
	public static final String S60_APPLICATION = "com.nokia.sdt.series60.CAknApplication"; //$NON-NLS-1$
	public static final String S60_NAVITAB = "com.nokia.sdt.series60.NaviTabs"; //$NON-NLS-1$
	public static final String S60_STATUSPANE = "com.nokia.sdt.series60.StatusPane"; //$NON-NLS-1$
	public static final String S60_TITLE_PANE_BASE = "com.nokia.sdt.series60.StatusPaneTitleBase"; //$NON-NLS-1$
	public static final String S60_CONTEXT_ICON = "com.nokia.sdt.series60.StatusPaneContext"; //$NON-NLS-1$
	public static final String S60_CONTEXT_ICON_BASE = "com.nokia.sdt.series60.StatusPaneContextBase"; //$NON-NLS-1$
	public static final String S60_DEFAULT_APPUI = "com.nokia.sdt.series60.DefaultAppUi"; //$NON-NLS-1$
	public static final String S60_APPUI_BASE = "com.nokia.sdt.series60.CAknAppUiBase"; //$NON-NLS-1$
	public static final String S60_CONTROL_PANE_BASE = "com.nokia.sdt.series60.CBABase"; //$NON-NLS-1$

	// fixed property types
	public static final String S60_IMAGE_PROPERTY_TYPE = "com.nokia.sdt.symbian.imageProperty"; //$NON-NLS-1$

	public static String getAvkonViewName(String baseName) {
		return baseName + "View"; //$NON-NLS-1$
	}
	/**
	 * Discovers the flavor of S60 root data model.<p>
	 * "full" models have an application and either view switching or basic AppUi;<p>
	 * "legacy" models have a basic AppUi but no application;<p>
	 * "unknown" models do not match known root model patterns (may be view model)
	 */
	public static S60RootModelType getS60RootModelType(IDesignerDataModel model) {
		S60RootModelType result = S60RootModelType.UNKNOWN;
		EObject appUi = findAppUi(model);
		EObject root = model.getRoot();
		if (root != null) {
			if (appUi != null) {
				String legacyProperty = model.getProperty(LEGACY_APPLICATION_PROPERTY);
				if (Boolean.valueOf(legacyProperty)) {
					result = S60RootModelType.LEGACY;
				} else {
					if (ModelUtils.isInstanceOf(appUi, S60_VIEW_APPUI)) {
						result = S60RootModelType.VIEW_APPUI;
					} else {
						result = S60RootModelType.BASIC_APPUI;
					}
				}
			}
		}
		return result;
	}

	public static boolean isNaviPaneTabbingEnabled(IDesignerDataModel model) {
		boolean result = false;
		EObject appUi = findAppUi(model);
		if (appUi != null) {
			EObject naviTab = ModelUtils.findFirstComponentInstance(appUi, S60_NAVITAB, true);
			result = naviTab != null;
		}
		return result;
	}
	/**
	 * Create an undoable EMF command to enable or disable navipane tabbing
	 * Returns null if the current state matches the desired state
	 */
	public static Command enableNaviPaneTabbing(IDesignerDataModel model, boolean enabled) {
		if (isNaviPaneTabbingEnabled(model) == enabled) {
			return null;
		}
		Command result = null;
		EObject appUi = findAppUi(model);
		if (appUi != null) {
			if (enabled) {
				EObject statusPane = ModelUtils.findFirstComponentInstance(appUi, S60_STATUSPANE, true);
				if (statusPane != null) {
					IComponentSet componentSet = model.getComponentSet();
					IComponent component = componentSet.lookupComponent(S60_NAVITAB);
					if (component != null) {
						EObject naviTabInstance = model.createNewComponentInstance(component);
						Command addCommand = model.createAddNewComponentInstanceCommand(
								statusPane, naviTabInstance, IDesignerDataModel.AT_END);
						result = addCommand;
						
					}
					else {
						logMissingComponentMessage(S60_NAVITAB);
					}
				} else {
					logMissingComponentInstanceMessage(S60_STATUSPANE);
				}
			}
			else {
				EObject naviTabInstance = ModelUtils.findFirstComponentInstance(appUi, S60_NAVITAB, true);
				if (naviTabInstance != null) {
					ArrayList<EObject> objsToRemove = new ArrayList<EObject>();
					objsToRemove.add(naviTabInstance);
					result = model.createRemoveComponentInstancesCommand(objsToRemove);
				}
			}
		}
		else {
			logMissingComponentInstanceMessage(S60_VIEW_APPUI);
		}
		return result;
	}
	/**
	 * Ensure that some valid design reference is set for a root model.
	 * <p>
	 * Note: package-private; use {@link SymbianModelUtils#ensureInitialView(IDesignerDataModel)} instead
	 * 
	 * @param model
	 * @return
	 */
	static Command ensureInitialS60View(IDesignerDataModel rootModel) {
		Command result = null;
		EObject appUi = findAppUi(rootModel);
		if (appUi != null) {
			boolean isViewAppUi = ModelUtils.isInstanceOf(appUi, S60_VIEW_APPUI);
			IPropertySource appUiPS = ModelUtils.getPropertySource(appUi);
			String initialViewName = (String) appUiPS.getPropertyValue(APPUI_INITIALVIEW);
			boolean haveValidInitialView = false;
			if (initialViewName != null) {
				EObject designRef = rootModel.findByNameProperty(initialViewName);
				if (designRef != null) {
					if (isViewAppUi) {
						haveValidInitialView = ModelUtils.isInstanceOf(designRef, S60_AVKON_VIEW_REFERENCE);
					} else {
						haveValidInitialView = ModelUtils.isInstanceOf(designRef, S60_DESIGN_REFERENCE);
					}
				}
			}
			if (!haveValidInitialView) {
				EObject newInitialDesignRef = null;
				IComponentInstance appUiCI = ModelUtils.getComponentInstance(appUi);
				EObject[] children = appUiCI.getChildren();
				if (children != null) {
					for (EObject child : children) {
						if ((isViewAppUi && ModelUtils.isInstanceOf(child, S60_AVKON_VIEW_REFERENCE)) ||
							 ModelUtils.isInstanceOf(child, S60_DESIGN_REFERENCE)) {
							newInitialDesignRef = child;
							break;
						}
					}
				}
				if (newInitialDesignRef != null) {
					IComponentInstance viewRefCI = ModelUtils.getComponentInstance(newInitialDesignRef);
					SetPropertyCommand spc = new SetPropertyCommand(appUiPS, APPUI_INITIALVIEW, viewRefCI.getName());
					result = spc;
				}
			}
		}
		return result;
	}
	/**
	 * Tell if the given RSS field name is known to map to an image property.
	 * Used as an optimization in RSS mapping -- does not need to be exact.
	 * @param memberName
	 * @return
	 */
	public static boolean isImageRssMemberNameImpl(String memberName) {
		return memberName.equals("bmpfile"); //$NON-NLS-1$
	}


}
