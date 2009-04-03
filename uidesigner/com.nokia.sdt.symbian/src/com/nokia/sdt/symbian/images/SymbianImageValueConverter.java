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
import com.nokia.sdt.component.property.IPropertyInformation;
import com.nokia.sdt.datamodel.IModelMessage;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.symbian.Messages;
import com.nokia.sdt.symbian.dm.ModelMessage;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import java.text.MessageFormat;
import java.util.Collection;

/**
 * This implements the standard converter for compound image properties,
 * e.g. where the "bmpfile", "bmpid", "bmpmask" (and on UIQ, the "uri") subproperties
 * form an image property.
 * 
 *
 */
public class SymbianImageValueConverter implements ICompoundPropertyValueConverter2 {

    public static final String IMAGE_PROPERTY_IMAGE_ID_SUBPROPERTY = "bmpid"; //$NON-NLS-1$
    public static final String IMAGE_PROPERTY_MASK_ID_SUBPROPERTY = "bmpmask"; //$NON-NLS-1$
    public static final String IMAGE_PROPERTY_MULTI_IMAGE_FILE_SUBPROPERTY = "bmpfile"; //$NON-NLS-1$
    public static final String IMAGE_PROPERTY_URI_SUBPROPERTY = "uri"; //$NON-NLS-1$

    /* (non-Javadoc)
     * @see com.nokia.sdt.component.property.ICompoundPropertyValueConverter#getEditableValue(org.eclipse.emf.ecore.EObject, org.eclipse.ui.views.properties.IPropertySource)
     */
    public Object getEditableValue(EObject object, IPropertySource ps) {
		return new ImagePropertyInfo(ps,
			IMAGE_PROPERTY_MULTI_IMAGE_FILE_SUBPROPERTY,
			IMAGE_PROPERTY_IMAGE_ID_SUBPROPERTY, 
			IMAGE_PROPERTY_MASK_ID_SUBPROPERTY,
			null);
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
				null);
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
				null);
	}

	protected static void addMessage(Collection<IModelMessage> messages,
			IComponentInstance currentInstance,
			int severity, String messageText, Object propertyID) {
		ModelMessage msg = new ModelMessage(severity,
				currentInstance.getDesignerDataModel().getModelSpecifier().createMessageLocation(),
				"ModelValidator.ModelMessage", //$NON-NLS-1$
				messageText, currentInstance.getComponentId(), 
				currentInstance.getName(),
				propertyID, null);
		messages.add(msg);
	}


	/**
	 */
	public static void validateKnownImage(Collection<IModelMessage> messages,
			IPropertyDescriptor pd,
			IPropertySource ps,
			String filePropertyId,
			String imagePropertyId,
			String maskPropertyId,
			String uriPropertyId) {
		
		ImagePropertyInfo imageInfo = new ImagePropertyInfo(ps,
				filePropertyId,
				imagePropertyId, 
				maskPropertyId,
				uriPropertyId);
		
		if (!imageInfo.isUnknownImage())
			return;

		EObject object = ((IPropertyInformation) ps).getPropertyOwner(null);
		IComponentInstance instance = ModelUtils.getComponentInstance(object);

		String propertyPath = ((IPropertyInformation) ps).getPropertyPath();
		String displayPath = ModelUtils.makeDisplayPropertyPath(
				object, propertyPath);
		if (displayPath == null) {
			displayPath = propertyPath;
		}
		
		// For all three properties, we assume that if there is a property value, but getImagePropertyInfoFromProperty
		// could not provide a corresponding info object then there may be a problem, so we issue a warning.
		if (imageInfo.isMissingImageFile()) {
			if (filePropertyId != null) {
				String bmpFileName = (String) ps.getPropertyValue(filePropertyId);
				String fmt = Messages.getString("SymbianImagePropertyConverter.validationWarningMultiImage"); //$NON-NLS-1$
				Object params[] = {bmpFileName, displayPath, instance.getName()};
				String message = MessageFormat.format(fmt, params);
				addMessage(messages, instance, IStatus.WARNING, message, pd.getId());
			}
		} else {
			// Don't bother emitting warnings on bitmap and mask field if the multi-image
			// wasn't found, it's redundant.
			String sourceFile;
			if (imageInfo.multiImageInfo != null) {
				sourceFile = imageInfo.multiImageInfo.getSourceFile();
			} else {
				sourceFile = Messages.getString("SymbianImagePropertyConverter.0"); //$NON-NLS-1$
			}
			if (imageInfo.isMissingBitmap()) {
				String bmpName = (String) ps.getPropertyValue(imagePropertyId);
				String fmt = Messages.getString("SymbianImagePropertyConverter.validationWarningBitmapFile"); //$NON-NLS-1$
				Object params[] = {bmpName, sourceFile, 
						displayPath, instance.getName()};
				String message = MessageFormat.format(fmt, params);
				addMessage(messages, instance, IStatus.WARNING, message, pd.getId());				
			}
			if (imageInfo.isMissingMask()) {
				String maskName = (String) ps.getPropertyValue(maskPropertyId);
				String fmt = Messages.getString("SymbianImagePropertyConverter.validationWarningBitmapMaskFile"); //$NON-NLS-1$
				Object params[] = {maskName, sourceFile, 
						displayPath, instance.getName()};
				String message = MessageFormat.format(fmt, params);
				addMessage(messages, instance, IStatus.WARNING, message, pd.getId());					
			}
			if (imageInfo.isMissingURI()) {
				String uri = (String) ps.getPropertyValue(uriPropertyId);
				String fmt = Messages.getString("SymbianImagePropertyConverter.validationWarningURIFile"); //$NON-NLS-1$
				Object params[] = {uri, displayPath, instance.getName()};
				String message = MessageFormat.format(fmt, params);
				addMessage(messages, instance, IStatus.WARNING, message, pd.getId());					

			}
		}

	}
}
