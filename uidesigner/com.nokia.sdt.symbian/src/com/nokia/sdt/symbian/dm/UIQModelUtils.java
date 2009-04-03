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

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertySource;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.SetPropertyCommand;


/**
 * Utilities for interacting with UIQ-specific aspects of models
 *
 */
public abstract class UIQModelUtils extends SymbianModelUtils {

	/** Basic design reference component id */
	public static final String UIQ_DESIGN_REFERENCE = "com.nokia.carbide.uiq.DesignReference"; //$NON-NLS-1$
	public static final String UIQ_APPLICATION = "com.nokia.carbide.uiq.Application"; //$NON-NLS-1$
	public static final String UIQ_APPLICATION_DOCUMENT = "com.nokia.carbide.uiq.ApplicationDocument"; //$NON-NLS-1$
	public static final String UIQ_APPLICATION_UI = "com.nokia.carbide.uiq.ApplicationUI"; //$NON-NLS-1$
	public static final String UIQ_VIEWDIALOGBASE = "com.nokia.carbide.uiq.ViewDialogBase"; //$NON-NLS-1$
	public static final String UIQ_CQIKVIEW= "com.nokia.carbide.uiq.CQikView"; //$NON-NLS-1$
	public static final String UIQ_CQIKSINGLEPAGEVIEW = "com.nokia.carbide.uiq.dummy.SinglePageView"; //$NON-NLS-1$
	public static final String UIQ_CQIKMULTIPAGEVIEW = "com.nokia.carbide.uiq.dummy.MultiPageView"; //$NON-NLS-1$
	public static final String UIQ_CQIKVIEWDIALOG = "com.nokia.carbide.uiq.dummy.ViewDialog"; //$NON-NLS-1$
	public static final String UIQ_CQIKSIMPLEDIALOG = "com.nokia.carbide.uiq.CQikSimpleDialog"; //$NON-NLS-1$
	public static final String UIQ_UI_CONFIGURATION = "com.nokia.carbide.uiq.UIConfiguration"; //$NON-NLS-1$
	public static final String UIQ_VIEW_CONFIGURATIONS_GROUP = "com.nokia.carbide.uiq.ViewConfigurationsGroup"; //$NON-NLS-1$
	public static final String UIQ_VIEW_CONFIGURATION = "com.nokia.carbide.uiq.ViewConfiguration"; //$NON-NLS-1$
	public static final String UIQ_SIMPLEDIALOG_CONFIGURATIONS_GROUP = "com.nokia.carbide.uiq.DialogConfigurationsGroup"; //$NON-NLS-1$
	public static final String UIQ_VIEW_LAYOUTS_GROUP = "com.nokia.carbide.uiq.LayoutsGroup"; //$NON-NLS-1$
	public static final String UIQ_VIEW_LAYOUT = "com.nokia.carbide.uiq.ViewLayout"; //$NON-NLS-1$
	public static final String UIQ_VIEW_PAGE = "com.nokia.carbide.uiq.ViewPage"; //$NON-NLS-1$
	public static final String UIQ_CQIKCONTAINER = "com.nokia.carbide.uiq.CQikContainer"; //$NON-NLS-1$
	public static final String UIQ_SIMPLEDIALOG_CONFIGURATION = "com.nokia.carbide.uiq.DialogConfiguration"; //$NON-NLS-1$
	public static final String UIQ_SIMPLEDIALOG_CONTAINERS_GROUP = "com.nokia.carbide.uiq.ContainersGroup"; //$NON-NLS-1$
	public static final String UIQ_CONTROL_COLLECTION = "com.nokia.carbide.uiq.ControlCollection"; //$NON-NLS-1$
	public static final String UIQ_COMMAND_LISTS_GROUP = "com.nokia.carbide.uiq.CommandListsGroup"; //$NON-NLS-1$
	public static final String UIQ_COMMAND_LIST = "com.nokia.carbide.uiq.CommandList"; //$NON-NLS-1$
	public static final String UIQ_COMMAND_ID = "com.nokia.carbide.uiq.CommandId"; //$NON-NLS-1$
	public static final String UIQ_COMMAND = "com.nokia.carbide.uiq.Command"; //$NON-NLS-1$
	public static final String UIQ_LISTBOX_LAYOUT = "com.nokia.carbide.uiq.ListBoxLayout"; //$NON-NLS-1$

	/** UI Design control attributes and properties */
	public static final String VIEWDIALOG_ATTRIBUTE_ISAPPUICONTAINER = "is-appui-container"; //$NON-NLS-1$
	public static final String VIEWDIALOG_PROPERTY_ISAPPUICONTAINER = "isAppUIContainer"; //$NON-NLS-1$
	
	// fixed property types
	/** MBM-only image */
	public static final String UIQ_MBM_IMAGE_PROPERTY_TYPE = "com.nokia.carbide.uiq.MbmImageProperty"; //$NON-NLS-1$
	/** MBM or URI image */
	public static final String UIQ_IMAGE_PROPERTY_TYPE = "com.nokia.carbide.uiq.ImageProperty"; //$NON-NLS-1$
	
	// fixed property ids
	public static final Object UICONFIGURATION_UICONFIG = "uiconfig"; //$NON-NLS-1$
	
	public enum UIQRootModelType {
		VIEW_APPUI,
		BASIC_APPUI,
		LEGACY,
		UNKNOWN
	}

	public static String getCQikViewName(String baseName) {
		return baseName + "View"; //$NON-NLS-1$
	}
	
	public static String getCQikSimpleDialogName(String baseName) {
		return baseName + "SimpleDialog"; //$NON-NLS-1$
	}
	/**
	 * Discovers the flavor of UIQ root data model.<p>
	 * "full" models have an application and either view switching or basic AppUi;<p>
	 * "legacy" models have a basic AppUi but no application;<p>
	 * "unknown" models do not match known root model patterns (may be view model)
	 */
	public static UIQRootModelType getUIQRootModelType(IDesignerDataModel model) {
		UIQRootModelType result = UIQRootModelType.UNKNOWN;
		EObject appUi = findAppUi(model);
		EObject root = model.getRoot();
		if (root != null) {
			if (appUi != null) {
				String legacyProperty = model.getProperty(LEGACY_APPLICATION_PROPERTY);
				if (Boolean.valueOf(legacyProperty)) {
					result = UIQRootModelType.LEGACY;
				} else {
					if (ModelUtils.isInstanceOf(appUi, UIQ_APPLICATION_UI)) {
						result = UIQRootModelType.VIEW_APPUI;
					} else {
						result = UIQRootModelType.BASIC_APPUI;
					}
				}
			}
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
	static Command ensureInitialUIQDesign(IDesignerDataModel rootModel) {
		Command result = null;
		EObject appUi = findAppUi(rootModel);
		if (appUi != null) {
			IPropertySource appUiPS = ModelUtils.getPropertySource(appUi);
			String initialViewName = (String) appUiPS.getPropertyValue(APPUI_INITIALVIEW);
			boolean haveValidInitialView = false;
			if (initialViewName != null) {
				EObject designRef = rootModel.findByNameProperty(initialViewName);
				if (designRef != null) {
					haveValidInitialView = ModelUtils.isInstanceOf(designRef, UIQ_DESIGN_REFERENCE);
				}
			}
			if (!haveValidInitialView) {
				EObject newInitialDesignRef = null;
				IComponentInstance appUiCI = ModelUtils.getComponentInstance(appUi);
				EObject[] children = appUiCI.getChildren();
				if (children != null) {
					for (EObject child : children) {
						if (ModelUtils.isInstanceOf(child, UIQ_DESIGN_REFERENCE)) {
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
	static boolean isImageRssMemberNameImpl(String memberName) {
		return memberName.equals("bmpfile") //$NON-NLS-1$
		|| memberName.equals("bmp1file") //$NON-NLS-1$
		|| memberName.equals("bmp2file") //$NON-NLS-1$
		|| memberName.equals("tab_bmpfile") //$NON-NLS-1$
		;
	}
}
