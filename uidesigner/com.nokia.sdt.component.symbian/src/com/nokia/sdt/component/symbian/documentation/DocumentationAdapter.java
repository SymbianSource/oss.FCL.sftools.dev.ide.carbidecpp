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


package com.nokia.sdt.component.symbian.documentation;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.IDocumentation;
import com.nokia.sdt.component.symbian.IFacetContainer;
import com.nokia.sdt.emf.component.DocumentationType;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class DocumentationAdapter implements IDocumentation {

	private IComponent component;
	private EStructuralFeature documentationFeature;

	/**
	 * Initialize the adapter with the documentation feature.
	 * Element is of type DocumentationType
	 * @param documentationFeature
	 */
	DocumentationAdapter(Plugin plugin, IComponent component,
			EStructuralFeature documentationFeature) {
		Check.checkArg(component);
		this.component = component;
		this.documentationFeature = documentationFeature;
	}

	private DocumentationType getDocumentationTypeFromContainer(IFacetContainer fc) {
		DocumentationType docObj = null;
		EObject container = fc.getEMFContainer();
		Object featureObj = container.eGet(documentationFeature);
		if (featureObj instanceof DocumentationType)
			docObj = (DocumentationType) featureObj;
		
		return docObj;
	}
	
	public String getHelpTopic() {
		DocumentationType docObj = getDocumentationTypeFromContainer((IFacetContainer) component);
		if (docObj != null)
			return docObj.getHelpTopic();

		return null;
	}

	public String getInformation() {
		String result = null;
		IFacetContainer fc = (IFacetContainer) component;
		DocumentationType docObj = getDocumentationTypeFromContainer(fc);
		if (docObj != null) {
			result = docObj.getInformation();
			if (!TextUtils.isEmpty(result)) {
				ILocalizedStrings ls = fc.getLocalizedStrings();
				if (ls != null) {
					result = ls.checkPercentKey(result);
				}
			}
		}
		return result;
	}

	public String getWizardDescription() {
		String result = null;
		IFacetContainer fc = (IFacetContainer) component;
		DocumentationType docObj = getDocumentationTypeFromContainer(fc);
		if (docObj != null) {
			result = docObj.getWizardDescription();
			if (!TextUtils.isEmpty(result)) {
				ILocalizedStrings ls = fc.getLocalizedStrings();
				if (ls != null) {
					result = ls.checkPercentKey(result);
				}
			}
		}
		return result;
	}

	public IComponent getComponent() {
		return component;
	}

}
