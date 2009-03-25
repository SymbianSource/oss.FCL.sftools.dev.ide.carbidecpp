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
package com.nokia.sdt.component.adapter;

/**
 * Declarations of keys for component attributes. Component attributes do not
 * have to be declared in this file to be used. Doing so is a convention that
 * helps to document the attributes that are in use. Not all attributes may
 * apply to all targets.
 */
public interface CommonAttributes {

	/** 
	 * A boolean flag indicating that a component is a layout object. 
	 * A layout object is always created in the upper part of the editor where the “layout” exists, 
	 * and must define location and size properties.
	 */
	static final String IS_LAYOUT_OBJECT = "is-layout-object"; //$NON-NLS-1$

	/**
	 * A boolean flag indicating that a component can be a layout container. 
	 * Containers have the additional behavior that they can contain other layout objects.
	 */
	static final String IS_LAYOUT_CONTAINER = "is-layout-container"; //$NON-NLS-1$

	/**
	 * A boolean flag indicating that a component is a non-layout object. 
	 * A non-layout object is always created in the lower part of the editor and appears only as an icon.
	 */
	static final String IS_NON_LAYOUT_OBJECT = "is-non-layout-object"; //$NON-NLS-1$

	/**
	 * A boolean flag indicating that a component is a transient UI object. 
	 * It will appear in the upper part of the editor when it is selected, 
	 * and all other layout objects will be greyed out. An example of this is a menu bar. 
	 * Generally, a trainsient object is also a non-layout object. 
	 * Without declaring the latter, it would only show up in the outline view until selected. 
	 * If a transient object is also a container, its children should not be transient objects 
	 * but regular layout objects.
	 */
	static final String IS_TRANSIENT_OBJECT = "is-transient-object"; //$NON-NLS-1$
	
	
	/**
	 * A boolean flag indicating that a component can be a container in the outline. 
	 * Containers have the additional behavior that they can contain other objects.
	 * Layout containers are automatically outline containers
	 */
	static final String IS_OUTLINE_CONTAINER = "is-outline-container"; //$NON-NLS-1$
	
	/**
	 * A boolean flag indicating that a component can only be added to a container 
	 * defining its "is-top-level-content-container" attribute as true.
	 */
	static final String IS_TOP_LEVEL_ONLY_LAYOUT_OBJECT = "is-top-level-only-layout-object"; //$NON-NLS-1$

	/**
	 * A boolean flag indicating that a component must be the exclusive content 
	 * of its container and can have no siblings.
	 */
	static final String IS_EXCLUSIVE_CHILD_LAYOUT_OBJECT = "is-exclusive-child-layout-object"; //$NON-NLS-1$

	/**
	 * A boolean flag indicating that a component is not removable by the user in the editor. 
	 * No delete operation will be available for it.
	 */
	static final String IS_NOT_USER_REMOVABLE = "is-not-user-removable"; //$NON-NLS-1$

	/**
	 * A boolean flag indicating that a component is not resizable by the user in the editor. 
	 * Selection trackers will not include resize trackers.
	 */
	static final String IS_NON_RESIZABLE_LAYOUT_OBJECT = "is-non-resizable-layout-object"; //$NON-NLS-1$

	/**
	 * A boolean flag indicating that a component is not moveable or resizable by the user in the editor.
	 */
	static final String IS_NON_RESIZABLE_OR_MOVEABLE_LAYOUT_OBJECT = "is-non-resizable-or-moveable-layout-object"; //$NON-NLS-1$

	/**
	 * A boolean flag indicating that children of this container can't be reordered 
	 * by the user in the outline or via any reorder actions (move up/move down).
	 */
	static final String FIXED_CHILD_ORDER = "fixed-child-order"; //$NON-NLS-1$
	
	/**
	 * The Java class used by the root container for its IDisplayModel implementation. 
	 * This attribute should only be set (and is required to be set) by the root container. 
	 */
	static final String DISPLAY_MODEL_CLASS = "display-model-class"; //$NON-NLS-1$

	/**
	 * A boolean flag indicating to the UI designer that the component should not appear in the toolbox.
	 */
	static final String NOT_IN_TOOLBOX = "not-in-toolbox"; //$NON-NLS-1$

	/**
	 * A boolean flag indicating initial content for the view wizard (e.g., List). 
	 * This will show the component (and its thumbnail) in the wizard’s initial content page.
	 */
	static final String IS_INITIAL_CONTENT = "is-initial-content"; //$NON-NLS-1$
	
	/**
	 * A boolean flag indicating a container that can be used as a top level content container 
	 * (e.g. CCoeControl, Form, or SettingsItemList).
	 */
	static final String IS_TOP_LEVEL_CONTENT_CONTAINER = "is-top-level-content-container"; //$NON-NLS-1$
	
	/**
	 * A boolean flag indicating that a component can never be added to a container 
	 * unless that container specifically has a IQueryContainment implementation (script) allowing it. 
	 */
	static final String NEVER_ADD_COMPONENT = "never-add-component"; //$NON-NLS-1$

	/**
	 * A boolean flag indicating a component that a component has some special system significance 
	 * (e.g., control pane, status pane, navi pane content). 
	 * System components are not selected when the select-all action is invoked.
	 */
	static final String IS_SYSTEM_COMPONENT = "is-system-component"; //$NON-NLS-1$
	
	/**
	 * A string value indicating the type of layout a container has for its children.
	 * Only two types are currently supported
	 * absolute (this is default if not specified) = absolute xy coordinates
	 * ordered = laid out based on child order
	 */
	static final String LAYOUT_TYPE = "layout-type"; //$NON-NLS-1$
	
	/**
	 * A string value indicating the type of ordered layout a container has for its children.
	 * Only one type is currently supported
	 * vertical-row = vertical row layout
	 */
	static final String ORDERED_LAYOUT_TYPE = "ordered-layout-type"; //$NON-NLS-1$
	
	/**
	 * A boolean flag indicating that a container has a single visible child. 
	 * Primarily, using the outline to select a child that is currently not visible (or one of its children)
	 * will cause the new child to become visible and the others to become invisible 
	 */
	static final String SWITCHABLE_CHILD_CONTAINER = "switchable-child-container"; //$NON-NLS-1$

	/**
	 * A boolean flag indicating that a container with a single visible child wants a context menu
	 * to allow users to switch the visible child - in addition to being able to use selection in the outline
	 */
	static final String SWITCHABLE_CHILD_CONTEXT_MENU = "switchable-child-context-menu"; //$NON-NLS-1$
	
	/**
	 * The label to use for the switchable child in the context menu (e.g., use Page for
	 * the menu to read: Change Page). Can be localized by the component - prefix with percent
	 * char (e.g., %Page).
	 */
	static final String SWITCHABLE_CHILD_CONTEXT_MENU_CHILD_LABEL = "switchable-child-context-menu-child-label"; //$NON-NLS-1$
	
	/**
	 * A boolean flag indicating this component is the target of event handler prototypes and handlers. 
	 * When set, the script variable 'handlerClassName' is visible to child components and is the value 
	 * of this component's 'className' property.
	 */
	static final String EVENT_HANDLER_TARGET = "event-handler-target"; //$NON-NLS-1$
	
	/**
	 * A string representing the palette drawer that is associated with some container.
	 * This is used to ensure this drawer is open in the palette when the palette is built.
	 */
	static final String ASSOCIATED_PALETTE_DRAWER = "associated-palette-drawer"; //$NON-NLS-1$
	
	/**
	 * A boolean flag on containers indicating that this container should be scrollable vertically.
	 */
	static final String VERTICAL_SCROLLABLE_CONTAINER = "vertical-scrollable-container"; //$NON-NLS-1$
	
	/**
	 * A boolean flag on containers indicating that this container should be scrollable horizontally.
	 */
	static final String HORIZONTAL_SCROLLABLE_CONTAINER = "horizontal-scrollable-container"; //$NON-NLS-1$
	
	/**
	 * A boolean flag on containers indicating that this container should be collapsed by default
	 * in the outline (without specfying this, containers are expanded by default).
	 */
	static final String COLLAPSED_IN_OUTLINE = "collapsed-in-outline"; //$NON-NLS-1$
	
	/**
	 * The name of the component reference property the value of which contains the name of the instance 
	 * that provides indirection of the layout container adapter.
	 * This allows the layout container to display layout object children of another container.
	 * NOTE: This can only be used with an outline container, since otherwise, there would be 
	 * more than one controller and figure in the graphical viewer for the same model object.
	 * The case of multiple graphical representations in a view for a single model object is not supported.
	 */
	static final String LAYOUT_CONTAINER_INDIRECTION_REFERENCE_PROPERTY = 
					"layout-container-indirection-reference-property"; //$NON-NLS-1$

	/** 
	 * A boolean flag indicating that a component is a layout manager. 
	 * A layout manager is responsible of the sizing and positioning of other 
	 * components through the ILayout implementation.
	 */
	static final String IS_LAYOUT_MANAGER = "is-layout-manager"; //$NON-NLS-1$
	
	/**
	 * A boolean flag that applies only to objects rendered in the transient layer.
	 * This flag indicates that the container's children should not be clipped when they are painted.
	 */
	static final String NO_TRANSIENT_CHILD_CLIPPING = "no-transient-child-clipping"; //$NON-NLS-1$
}
