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
package com.nokia.sdt.symbian;

/**
 * Component attributes specific to S60 
 *
 */
public interface S60ComponentAttributes extends SymbianComponentAttributes {

	/**
	 * A boolean flag indicating that a component is the contents of the navipane.
	 */
	public static final String NAVIPANE_CONTENT = "is-navipane-content"; //$NON-NLS-1$
	
	/**
	 * A boolean flag indicating that a component is the contents of a dialog.
	 */
	public static final String DIALOG_CONTENT = "is-dialog-content"; //$NON-NLS-1$
	
	/**
	 * A boolean flag indicating that a component is the contents of a form.
	 */
	public static final String FORM_CONTENT = "is-form-content"; //$NON-NLS-1$
	
	/**
	 * A boolean flag indicating that a component is the contents of a CCoeControl.
	 */
	public static final String CCOECONTROL_CONTENT = "is-ccoecontrol-content"; //$NON-NLS-1$
	
	/**
	 * A boolean flag indicating that a component is the content of a setting item list
	 */
	public static final String SETTING_ITEM_LIST_CONTENT = "is-setting-item-list-content"; //$NON-NLS-1$
	
	/**
	 * An attribute to define the pane id for a status pane pane.
	 */
	public static final String STATUS_PANE_ID = "status-pane-id"; //$NON-NLS-1$
    
    /**
     * A boolean flag indicating that a component supports a CBA which is located in the parent.  
     * This is done to allow a consistent placement of the CBA (control pane) in the outline, 
     * while also avoiding issues about CAknView containing a CAknDialog which both have CBAs.
     */
    public static final String USES_CBA_IN_PARENT = "allow-cba-in-parent"; //$NON-NLS-1$
    
    /**
     * An attribute to define the name of the options menu reference property (e.g. optionsMenu).
     */
    public static final String OPTIONS_MENU_PROPERTY_NAME = "options-menu-property-name"; //$NON-NLS-1$
    
    /**
     * A boolean flag indicating that a component has a builtin options menu (e.g. CAknForm). 
     * This flag is used by the S60 wizard to inhibit creation of the options menu.
     */
    public static final String HAS_BUILTIN_OPTIONS_MENU = "has-builtin-options-menu"; //$NON-NLS-1$
    
    /**
     * An attribute for containers naming which property defines the background color.
     */
    public static final String CONTAINER_BACKGROUND_COLOR_PROPERTY_NAME = "container-background-color-property-name"; //$NON-NLS-1$

    /**
     * An attribute for containers naming the actual look and feel color that defines 
     * the background color. Used as a fallback when the "container-background-color-property-name" 
     * is not set.
     */
    public static final String CONTAINER_BACKGROUND_COLOR = "container-background-color"; //$NON-NLS-1$

    /**
     * An attribute for AppUI defining the layout name to use for a non-layout-aware app.
     */
    public static final String NON_LAYOUT_AWARE_LAYOUT = "non-layout-aware-layout"; //$NON-NLS-1$

    /**
     * An attribute with alternate CBA default for use by the wizard.
     */
    public static final String DEFAULT_CBA_VALUE = "default-cba-value"; //$NON-NLS-1$
    
    /**
     * A boolean flag inidicating that the component should only allow a restricted control pane 
     * implementation (i.e., one with fewer and only builtin choices). This is used by Forms.
     */
    public static final String USE_RESTRICTED_CBA = "use-restricted-cba"; //$NON-NLS-1$
    
    /**
     * An enumeration indicating the type of model the component is allowed
     * to exist in, for use when a component can be added by the user.<p>
     * Currently used by the status pane and its child components.<p>
     * Values are:<p>
     * <b>any</b> (either root or view)<br>
     * <b>root</b> (root only)<br>
     * <b>view</b> (meaning non-root)<br>
     * <p>
     * If not specified, <b>any</b> is assumed.
     */
    public static final String MODEL_TYPE_DISPOSITION = "model-type-disposition"; //$NON-NLS-1$
    
    /**
     * The name of a C/C++ type to use as the underlying storage 
     * in a generated data model for a component of the given type.
     */
    public static final String DATA_MODEL_CPP_TYPE = "data-model-cpp-type"; //$NON-NLS-1$

    /**
     * In CAknSettingItemList, the type that implements CAknSettingItem.
     */
    public static final String SETTING_ITEM_CLASS_NAME = "setting-item-class-name"; //$NON-NLS-1$

    /**
     * In CAknSettingItemList, the pattern for the CAknSettingItem constructor.<p>
     * Argument {0} is the identifier (e.g. "aId") and {1} is the data model getter
     * (e.g. "Text").<p>
     * If this attribute is not specified, "{0}, {1}" is assumed.
     */
    public static final String SETTING_ITEM_CONSTRUCTOR_PATTERN = "setting-item-constructor-pattern"; //$NON-NLS-1$

    /**
     * In CAknSettingItemList, indicates that the AVKON_SETTING_PAGE has
     * an associated_resource which can be found as from the setting item's
     * &lt;mapResource ... id="associated" /&gt; entry.
     * If this attribute is not specified, "{0}, {1}" is assumed.
     */
    public static final String SETTING_ITEM_HAS_ASSOCIATED_RESOURCE = "setting-item-has-associated-resource"; //$NON-NLS-1$

	/**
	 * A boolean flag indicating that a component is the content of a data query dialog
	 */
	public static final String DATA_QUERY_CONTENT = "is-dataquery-content"; //$NON-NLS-1$
	
    /**
     * As a CAknQueryDialog, the actual type used for the specific kind of data query.
     */
    public static final String DATA_QUERY_CLASS_NAME = "dataquery-className"; //$NON-NLS-1$

    /**
     * For children of CCoeControl, whether this child wants to be the "initialFocus" control
     */
    public static final String WANTS_INITIAL_FOCUS = "wants-initial-focus"; //$NON-NLS-1$
}
