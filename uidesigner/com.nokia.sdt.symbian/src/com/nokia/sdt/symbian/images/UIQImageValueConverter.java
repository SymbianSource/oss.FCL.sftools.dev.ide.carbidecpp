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

import com.nokia.sdt.component.property.IPropertyInformation;
import com.nokia.sdt.datamodel.IModelMessage;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import java.text.MessageFormat;
import java.util.Collection;

/**
 * This value converter additionally handles the "uri" property.
 * 
 *
 */
public class UIQImageValueConverter extends SymbianImageValueConverter {

	/* (non-Javadoc)
     * @see com.nokia.sdt.component.property.ICompoundPropertyValueConverter#getEditableValue(org.eclipse.emf.ecore.EObject, org.eclipse.ui.views.properties.IPropertySource)
     */
    public Object getEditableValue(EObject object, IPropertySource ps) {
		return new ImagePropertyInfo(ps,
			IMAGE_PROPERTY_MULTI_IMAGE_FILE_SUBPROPERTY,
			IMAGE_PROPERTY_IMAGE_ID_SUBPROPERTY, 
			IMAGE_PROPERTY_MASK_ID_SUBPROPERTY,
			IMAGE_PROPERTY_URI_SUBPROPERTY);
	}

    /* (non-Javadoc)
     * @see com.nokia.sdt.component.property.ICompoundPropertyValueConverter#applyEditableValue(org.eclipse.emf.ecore.EObject, java.lang.Object, org.eclipse.ui.views.properties.IPropertySource)
     */
    public void applyEditableValue(EObject object, Object editableValue,
    		IPropertySource ps) {
    	Check.checkArg(editableValue instanceof ImagePropertyInfo);
		((ImagePropertyInfo)editableValue).save(ps,
				IMAGE_PROPERTY_MULTI_IMAGE_FILE_SUBPROPERTY,
				IMAGE_PROPERTY_IMAGE_ID_SUBPROPERTY, 
				IMAGE_PROPERTY_MASK_ID_SUBPROPERTY,
				IMAGE_PROPERTY_URI_SUBPROPERTY);
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.symbian.images.ICompoundImagePropertyConverter#validate(java.util.List, com.nokia.sdt.datamodel.adapter.IComponentInstance, org.eclipse.ui.views.properties.IPropertySource)
	 */
	public void validate(Collection<IModelMessage> messages,
			IPropertyDescriptor pd,
			IPropertySource ps) {
		validateKnownImage(messages, pd, ps,
				IMAGE_PROPERTY_MULTI_IMAGE_FILE_SUBPROPERTY,
				IMAGE_PROPERTY_IMAGE_ID_SUBPROPERTY,
				IMAGE_PROPERTY_MASK_ID_SUBPROPERTY,
				IMAGE_PROPERTY_URI_SUBPROPERTY);
		
		ImagePropertyInfo imageInfo = new ImagePropertyInfo(ps,
				IMAGE_PROPERTY_MULTI_IMAGE_FILE_SUBPROPERTY,
				IMAGE_PROPERTY_IMAGE_ID_SUBPROPERTY, 
				IMAGE_PROPERTY_MASK_ID_SUBPROPERTY,
				IMAGE_PROPERTY_URI_SUBPROPERTY);
		
		if (imageInfo.uriInfo == null) 
			return;

		EObject object = ((IPropertyInformation) ps).getPropertyOwner(null);
		IComponentInstance instance = ModelUtils.getComponentInstance(object);
		String propertyPath = ((IPropertyInformation) ps).getPropertyPath();
		
		URIImageInfo uriInfo = imageInfo.uriInfo;
		IStatus status = uriInfo.validate();
		if (!status.isOK()) {
			addMessage(messages, instance, status.getSeverity(),
					MessageFormat.format(
							Messages.getString("UIQImageValueConverter.ValidationMessageTailFormat"), //$NON-NLS-1$
							new Object[] { status.getMessage(), imageInfo.uri, propertyPath, instance.getName() }),
							propertyPath); 
		}
	}
}
