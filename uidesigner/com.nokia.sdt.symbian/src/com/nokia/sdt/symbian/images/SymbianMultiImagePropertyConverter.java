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

package com.nokia.sdt.symbian.images;

import com.nokia.sdt.component.property.ICompoundPropertyValueConverter2;
import com.nokia.sdt.datamodel.IModelMessage;
import com.nokia.sdt.datamodel.images.IMultiImagePropertyInfo;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.*;

/**
 * Standard implementation of this property converter. It is initialized by
 * registering abstract image id -> bmpid, bmpmask property id mappings.
 * 
 * 
 * 
 */
public class SymbianMultiImagePropertyConverter implements
		ICompoundPropertyValueConverter2 {

	List<String[]> abstractProperties;

	private final String filePropertyId;

	/**
	 * Create a converter.
	 */
	public SymbianMultiImagePropertyConverter(String filePropertyId) {
		this.filePropertyId = filePropertyId;
		this.abstractProperties = new ArrayList<String[]>();
	}

	/**
	 * Add a new contained image property by supplying a name to use for the
	 * abstract image id (see
	 * {@link IMultiImagePropertyInfo#getImagePropertyInfoMap()} and the
	 * subproperties to read for the image and mask properties.
	 * 
	 * @param abstractId
	 *            name to use in the {@link IMultiImagePropertyInfo} map
	 * @param imagePropertyId
	 *            name of subproperty which specifies the image enumerator
	 * @param maskPropertyId
	 *            name of subproperty which specifies the mask enumerator
	 */
	public void addAbstractImageId(String abstractId, String imagePropertyId,
			String maskPropertyId) {
		abstractProperties.add(new String[] { abstractId, imagePropertyId,
				maskPropertyId });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.component.property.ICompoundPropertyValueConverter#getEditableValue(org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.ui.views.properties.IPropertySource)
	 */
	public Object getEditableValue(EObject object, IPropertySource ps) {
		return new SymbianMultiImagePropertyInfo(ps, filePropertyId,
				(String[][]) abstractProperties
						.toArray(new String[abstractProperties.size()][]));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.component.property.ICompoundPropertyValueConverter#applyEditableValue(org.eclipse.emf.ecore.EObject,
	 *      java.lang.Object, org.eclipse.ui.views.properties.IPropertySource)
	 */
	public void applyEditableValue(EObject object, Object editableValue,
			IPropertySource ps) {
		
		if (editableValue instanceof SymbianMultiImagePropertyInfo) {
			SymbianMultiImagePropertyInfo propertyInfo = (SymbianMultiImagePropertyInfo) editableValue;
			propertyInfo.save(ps);
		} else if (editableValue instanceof ImagePropertyInfo) {
			// There is the possibility of getting here from a component validator
			// which doesn't know about the abstract id. Assume that the same image
			// goes to all the ids.
			ImagePropertyInfo info = (ImagePropertyInfo) editableValue;
			for (String[] abstractProperty : abstractProperties) {
				info.save(ps, filePropertyId, 
						abstractProperty[1],
						abstractProperty[2],
						null);
			}
		} else {
			Check.checkArg(false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.sdt.datamodel.images.IMultiImagePropertyConverter#validate(java.util.Collection,
	 *      org.eclipse.ui.views.properties.IPropertyDescriptor,
	 *      org.eclipse.ui.views.properties.IPropertySource)
	 */
	public void validate(Collection<IModelMessage> messages,
			IPropertyDescriptor pd, IPropertySource ps) {
		for (String[] abstractProperty : abstractProperties) {
			SymbianImageValueConverter.validateKnownImage(messages, pd, ps,
					filePropertyId, abstractProperty[1], abstractProperty[2],
					null);
		}
	}

}
