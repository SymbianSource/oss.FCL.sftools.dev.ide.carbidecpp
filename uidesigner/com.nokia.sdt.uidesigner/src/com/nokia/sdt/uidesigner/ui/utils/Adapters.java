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


package com.nokia.sdt.uidesigner.ui.utils;

import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.*;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.displaymodel.adapter.*;
import com.nokia.sdt.editor.IComponentEditor;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.IActionFilter;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySource2;

/**
 * 
 * Convenience class to provide data model adapters as well as
 * component adapters from data model objects.
 * The data model adapters will not throw, but return null instead
 * The component adapters should not throw unless something really bad happens
 */
public class Adapters {
	
	private static Adapter getAdapter(EObject object, Class adapterClass) {
		return EcoreUtil.getRegisteredAdapter(object, adapterClass);
	}

	public static IComponentInstance getComponentInstance(EObject object) {
		return (IComponentInstance) getAdapter(object, IComponentInstance.class);
	}
	
	public static IPropertySource getPropertySource(EObject object) {
		return (IPropertySource) getAdapter(object, IPropertySource.class);
	}

	public static IPropertySource2 getPropertySource2(EObject object) {
		return (IPropertySource2) getAdapter(object, IPropertySource2.class);
	}
	
	public static ILayoutObject getLayoutObject(EObject object) {
		return (ILayoutObject) getAdapter(object, ILayoutObject.class);
	}

	public static ILayoutContainer getLayoutContainer(EObject object) {
		return (ILayoutContainer) getAdapter(object, ILayoutContainer.class);
	}
	
	public static IContainer getContainer(EObject object) {
		return (IContainer) getAdapter(object, IContainer.class);
	}
	
	public static IDisplayObject getDisplayObject(EObject object) {
		return (IDisplayObject) getAdapter(object, IDisplayObject.class);
	}
	
	public static IComponent getComponent(EObject object) {
		IComponentInstance componentInstance = getComponentInstance(object);
		if (componentInstance != null) {
			return componentInstance.getComponent();
		}
		return null;
	}
	
	public static IDesignerImages getDesignerImages(EObject object) {
		IComponent component = getComponent(object);
        
		// note: getComponent() may return null for legal reasons
        // (i.e. a missing component)
        if (component == null)
            return null;
		
		return (IDesignerImages) component.getAdapter(IDesignerImages.class);
	}
	
	public static IAttributes getAttributes(EObject object) {
		IComponent component = getComponent(object);

        // note: getComponent() may return null for legal reasons
        // (i.e. a missing component)
		if (component == null)
            return null;

		return (IAttributes) component.getAdapter(IAttributes.class);
	}

	public static IVisualAppearance getVisualAppearance(EObject object) {
		return (IVisualAppearance) getAdapter(object, IVisualAppearance.class);
	}
	
	public static boolean isTransientObject(EObject object) {
		IAttributes attributes = Adapters.getAttributes((EObject) object);
		return (attributes != null) && 
			attributes.getBooleanAttribute(CommonAttributes.IS_TRANSIENT_OBJECT, false);
	}

	public static IDirectLabelEdit getDirectLabelEdit(EObject object) {
		return (IDirectLabelEdit) getAdapter(object, IDirectLabelEdit.class);
	}

	public static IComponentEditor getComponentEditor(EObject object) {
		return (IComponentEditor) getAdapter(object, IComponentEditor.class);
	}

    public static IDirectImageEdit getDirectImageEdit(EObject object) {
        return (IDirectImageEdit) getAdapter(object, IDirectImageEdit.class);
    }
    
    public static IActionFilter getActionFilter(EObject object) {
    	return (IActionFilter) getAdapter(object, IActionFilter.class);
    }
    
    public static ISetValueCommandExtender getSetValueCommandExtender(EObject object) {
    	return (ISetValueCommandExtender) getAdapter(object, ISetValueCommandExtender.class);
    }

	public static IScrollBoundsProvider getScrollBoundsProvider(EObject object) {
    	return (IScrollBoundsProvider) getAdapter(object, IScrollBoundsProvider.class);
	}
	
	public static ICreationToolProvider getCreationToolProvider(EObject object) {
		return (ICreationToolProvider) getAdapter(object, ICreationToolProvider.class);
	}
}
