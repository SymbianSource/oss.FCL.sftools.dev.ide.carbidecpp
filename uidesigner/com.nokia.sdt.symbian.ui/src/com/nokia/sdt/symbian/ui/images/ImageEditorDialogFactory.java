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

package com.nokia.sdt.symbian.ui.images;

import com.nokia.sdt.component.property.ICompoundPropertyValueConverter;
import com.nokia.sdt.component.property.IPropertyInformation;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.datamodel.util.NodePathLookupResult;
import com.nokia.sdt.symbian.images.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 * 
 */
public class ImageEditorDialogFactory {

	static public IImageEditorDialog createImageEditorDialog(Shell shell,
			EObject object, String propertyPath, Object value,
			ICellEditorValidator validator) {
		if (value == null) {
			// invalid value -- fallback
			return new SingleMbmImageEditorDialog(shell,
					new ImagePropertyInfo(), object, propertyPath, validator);
		}

		if (value instanceof ImagePropertyInfo) {
			// see if a URI is present
			NodePathLookupResult result = ModelUtils.readProperty(object, propertyPath, false);
			ICompoundPropertyValueConverter compoundPropertyValueConverter = null;
			if (result.result instanceof IPropertyInformation) {
				compoundPropertyValueConverter = ((IPropertyInformation) result.result).getCompoundPropertyValueConverter();
			}
			if (compoundPropertyValueConverter instanceof UIQImageValueConverter) {
				return new SingleMbmOrUriImageEditorDialog(shell,
						(ImagePropertyInfo) value, object, propertyPath, validator);
			} else if (compoundPropertyValueConverter instanceof UIQURIImageValueConverter) {
					return new SingleURIImageEditorDialog(shell,
							(ImagePropertyInfo) value, object, propertyPath, validator);
			} else {
				return new SingleMbmImageEditorDialog(shell,
					(ImagePropertyInfo) value, object, propertyPath, validator);
			}
		} else if (value instanceof SymbianMultiImagePropertyInfo){
			return new MultiMbmImageEditorDialog(shell,
					(SymbianMultiImagePropertyInfo) value,
					object, propertyPath, validator);
		}
		return null;
	}
}
