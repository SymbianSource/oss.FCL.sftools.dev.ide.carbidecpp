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


package com.nokia.sdt.displaymodel;

import com.nokia.sdt.datamodel.IDesignerDataModel;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.displaymodel.adapter.IContainer;
import com.nokia.sdt.displaymodel.adapter.ILayoutContainer;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.sdt.ui.skin.ISkin;
import com.nokia.cpp.internal.api.utils.core.CacheMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

import java.util.Collection;


/**
 * 
 * <br><br>
 * The main interface to the display model
 */
public interface IDisplayModel {
	
	/**
	 * Initialize the display model with the dataModel and
	 * root container
	 * Should be called only once.
	 * @throws CoreException 
	 */
	void initialize(IDesignerDataModel dataModel, EObject rootContainer) throws CoreException;
	
	/**
	 * A layout area configuration<br>
	 * Layout areas have a size, display name and an optional skin
	 */
	interface LayoutAreaConfiguration {

		/**
		 * Get the internal name for this configuration
		 */
		String getID();
		/**
		 * @return the size of the layout as <code>org.eclipse.swt.graphics.Point</code>
		 */
		Point getSize();
		
		/**
		 * @return the display name of the layout configuration as <code>String</code>
		 */
		String getDisplayName();
		
		/**
		 * @return the ISkin for the layout or <code>null</code>
		 */
		ISkin getSkin();
	}

	/**
	 * Call when completely finished with the display model
	 * to release all its resources.
	 */
	void dispose();
		
	/**
	 * @return the supported layouts of this display model as a collection
	 * of <code>LayoutAreaConfiguration</code>
	 * <br>A display model must support at least one layout
	 */
	Collection<LayoutAreaConfiguration> getLayoutAreaConfigurations();

	/**
	 * @param layout the <code>LayoutAreaConfiguration</code> to set as the current one
	 *  <br>
	 *  A display model can install hotzone listeners to the skin.
	 */
	void setCurrentConfiguration(LayoutAreaConfiguration layout) throws CoreException;

	/**
	 * @return the current layout as <code>LayoutAreaConfiguration</code>
	 */
	LayoutAreaConfiguration getCurrentConfiguration();
	
	/**
	 * @param listener the <code>LayoutAreaConfigurationListener</code> to be added
	 */
	void addLayoutAreaListener(LayoutAreaConfigurationListener listener);
	
	/**
	 * @param listener the <code>LayoutAreaConfigurationListener</code> to be removed
	 */
	void removeLayoutAreaListener(LayoutAreaConfigurationListener listener);

	/**
	 * Provides parameters and resources used for drawing. Disposed 
	 * only by the display model
	 */
	ILookAndFeel getLookAndFeel();
	
	/**
	 * Returns the root container associated with the display model
	 */
	EObject getRootContainer();
	
	/**
	 * Returns whether this object should be selected by the select-all action
	 */
	boolean isSelectAllObject(EObject object);
	
	/**
	 * Returns an image that is a composite of the root container and
	 * all children.
	 */
	Image createCompositeImage();
	
	/**
	 * Returns a cache linked to this display model. It provides a means
	 * for caching objects that will be released when this 
	 * display model is disposed
	 */
	CacheMap getCache();

	/**
	 * Returns the root object for non-layout objects
	 * @return EObject
	 */
	EObject getNonLayoutRoot();

	/**
	 * Returns the top level content container
	 * @return EObject
	 */
	EObject getContentContainer();
	
	
	/**
	 * Sets the currently selected model objects
	 * @param objects EObject[]
	 */
	void setSelectedObjects(EObject[] objects);

	/** Get the data model in use */
	IDesignerDataModel getDataModel();
	
	/** Create an IContainer instance for this display model and component */
	IContainer createContainer(IComponentInstance ci);
	
	/** Create an ILayoutContainer instance for this display model and component */
	ILayoutContainer createLayoutContainer(IComponentInstance ci);
	
	/** Create an ILayoutObject instance for this display model and component */
	ILayoutObject createLayoutObject(IComponentInstance ci);

}
