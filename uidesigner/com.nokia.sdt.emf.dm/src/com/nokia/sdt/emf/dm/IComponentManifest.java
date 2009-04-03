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
package com.nokia.sdt.emf.dm;

import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.nokia.sdt.component.IComponentSet;

/**
 * The component manifest records information about
 * the components used in a model.
 * @model
 */
public interface IComponentManifest extends EObject {

	/**
	 * @model type="com.nokia.sdt.emf.dm.IComponentManifestEntry" containment="true"
	 * @return a list of IComponentManifestEntry
	 */
	EList getEntries();
	
	/**
	 * Return the entry for a component, if present.
	 * @param componentID
	 * @return the entry, or null if none
	 */
	IComponentManifestEntry lookup(String componentID);
	
	/**
	 * Update the manifest to contain an entry for each
	 * component referenced in the model. The entry for
	 * each component will match the version of the
	 * component currently loaded
	 */
	void update(IDesignerData root);
	
	/**
	 * Check for updated components by comparing
	 * the current manifest values with the current
	 * components. 
	 * Map keys are component IDs, values are UpdatedComponentInfo
	 * objects.
	 * For the original model format that did not have a component
	 * manifest this method has the side effect of updating the manifest. Otherwise
	 * the manifest is not modified.
	 * @return a map if any updated components, null if none
	 */
	Map<String, ComponentManifestDelta> getComponentDeltas(IDesignerData designerData);
	
	/**
	 * Update the manifest status to indicate that it is
	 * in-synch with the on-disk state of the model. This 
	 * occurs at two points: when a model was just successfully
	 * loaded and when it was successfully saved. Note that
	 * on the load we trust that the data doesn't lie -- it would
	 * be possible to hand-edit the XML to break that.
	 */
	void markSaved();
	
	/**
	 * Return true if the manifest contains entries
	 * related to the last save operation. This is the case
	 * if the model has loaded from a file and not updated
	 * since being loaded. This indicates the manifest can 
	 * be used to determine updated components.
	 * 
	 */
	boolean reflectsLastSave();
}
