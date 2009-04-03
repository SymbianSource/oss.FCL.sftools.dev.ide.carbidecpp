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
package com.nokia.sdt.component.symbian.displaymodel;

import com.nokia.cpp.internal.api.utils.core.StatusBuilder;
import com.nokia.sdt.component.IComponent;
import com.nokia.sdt.component.adapter.CommonAttributes;
import com.nokia.sdt.component.adapter.IAttributes;
import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.IVisualAppearance;
import com.nokia.sdt.displaymodel.IDisplayModel;
import com.nokia.sdt.displaymodel.adapter.*;
import com.nokia.sdt.editor.IComponentEditor;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;
import com.nokia.sdt.utils.ImageUtils;
import com.nokia.sdt.utils.StatusHolder;
import com.nokia.sdt.utils.drawing.GC;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.properties.IPropertySource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Utilities {
	
	private static final String ORDERED_LAYOUT_TYPE = "ordered";
	private static final String VERTICAL_ROW_LAYOUT = "vertical-row";
	private static final String HORIZONTAL_ROW_LAYOUT = "horizontal-row";

	static final String LOCATION_PROPERTY = "location"; //$NON-NLS-1$
	static final String LOCATION_X_PROPERTY = "x"; //$NON-NLS-1$
	static final String LOCATION_Y_PROPERTY = "y"; //$NON-NLS-1$

	static final String SIZE_PROPERTY = "size";	 //$NON-NLS-1$
	static final String SIZE_WIDTH_PROPERTY = "width"; //$NON-NLS-1$
	static final String SIZE_HEIGHT_PROPERTY = "height"; //$NON-NLS-1$

	

	public static IPropertySource getPropertySource(EObject obj) {
		IPropertySource ps = (IPropertySource) EcoreUtil.getRegisteredAdapter(obj, IPropertySource.class);
		return ps;
	}
	
	public static IComponentInstance getComponentInstance(EObject obj) {
		return (IComponentInstance) EcoreUtil.getRegisteredAdapter(obj, IComponentInstance.class);
	}
	
	static int integerPropertyValue(IPropertySource ps, Object id) {
		int result = -1;
		Object i = ps.getPropertyValue(id);
		if (i instanceof Number) {
			result = ((Number)i).intValue();
		}
		return result;
	}
	
	static Rectangle getBoundsFromLayoutProperties(EObject obj) {
		Rectangle result = new Rectangle(0, 0, 0, 0);
		IPropertySource ps = getPropertySource(obj);
		if (ps != null) {
			Object location = ps.getPropertyValue(LOCATION_PROPERTY);
			Object size = ps.getPropertyValue(SIZE_PROPERTY);
			if (location instanceof IPropertySource &&
				size instanceof IPropertySource) {
				IPropertySource locationPS = (IPropertySource) location;
				IPropertySource sizePS = (IPropertySource) size;
				
				int x = integerPropertyValue(locationPS, LOCATION_X_PROPERTY);
				int y = integerPropertyValue(locationPS, LOCATION_Y_PROPERTY);
				int width = integerPropertyValue(sizePS, SIZE_WIDTH_PROPERTY);
				int height = integerPropertyValue(sizePS, SIZE_HEIGHT_PROPERTY);
				result = new Rectangle(x,y, width, height);
			}
		}
		return result;
	}
	
	static void setBoundsIntoLayoutProperties(EObject obj, Rectangle bounds) {
		IPropertySource ps = getPropertySource(obj);
		if (ps != null) {
			Object location = ps.getPropertyValue(LOCATION_PROPERTY);
			Object size = ps.getPropertyValue(SIZE_PROPERTY);
			if (location instanceof IPropertySource &&
				size instanceof IPropertySource) {
				IPropertySource locationPS = (IPropertySource) location;
				IPropertySource sizePS = (IPropertySource) size;
				
				Rectangle oldBounds = getBoundsFromLayoutProperties(obj);
				if (oldBounds.x != bounds.x)
					locationPS.setPropertyValue(LOCATION_X_PROPERTY, new Integer(bounds.x));
				if (oldBounds.y != bounds.y)
					locationPS.setPropertyValue(LOCATION_Y_PROPERTY, new Integer(bounds.y));
				if (oldBounds.width != bounds.width)
					sizePS.setPropertyValue(SIZE_WIDTH_PROPERTY, new Integer(bounds.width));
				if (oldBounds.height != bounds.height)
					sizePS.setPropertyValue(SIZE_HEIGHT_PROPERTY, new Integer(bounds.height));
			}
		}
	}
		
	static LayoutObjectListener[] layoutObjectListenersForObject(EObject obj) {
		LayoutObjectListener[] result = null;
		// if there's no existing adapter then there's no listeners
		ILayoutObject layoutObj = (ILayoutObject) EcoreUtil.getExistingAdapter(obj, ILayoutObject.class);
		if (layoutObj != null) {
			LayoutObjectBase internalLayoutObj = (LayoutObjectBase) layoutObj;
			result = internalLayoutObj.getListeners();
		}
		return result;
	}
	
	public static void fireImageChanged(EObject object) {
			// get existing adapter, can't have listeners if the adapter isn't already attached
		ILayoutObject layoutObj = (ILayoutObject) EcoreUtil.getExistingAdapter(object, ILayoutObject.class);
		if (layoutObj != null) {
			LayoutObjectListener[] listeners = layoutObjectListenersForObject(object);
			if (listeners != null) {
				for (int i = 0; i < listeners.length; i++) {
					listeners[i].imageChanged(layoutObj);
				}
			}
		}
	}

	public static void fireBoundsChanged(EObject object) {
		ILayoutObject layoutObj = (ILayoutObject) EcoreUtil.getExistingAdapter(object, ILayoutObject.class);
		if (layoutObj != null) {
			LayoutObjectListener[] listeners = layoutObjectListenersForObject(object);
			if (listeners != null) {
				for (int i = 0; i < listeners.length; i++) {
					listeners[i].boundsChanged(layoutObj);
				}
			}
		}
	}

	public static ILayoutContainer getLayoutContainer(EObject object) {
		return (ILayoutContainer) EcoreUtil.getRegisteredAdapter(object, ILayoutContainer.class);
	}
	
	public static Image createImageAlpha(Rectangle bounds) {
		if (!bounds.isEmpty()) {
			PaletteData pal = ImageUtils.createStandardPaletteData();
	        byte rgbs[] = new byte[4 * bounds.width * bounds.height]; 
	        byte alphas[] = new byte[bounds.width * bounds.height];
	        ImageData data = new ImageData(bounds.width, bounds.height, 32, pal, 4, rgbs);
            for (int i = bounds.width*bounds.height; i-- > 0; ) {
                alphas[i] = 127;
            }
            data.alphaData = alphas;
			return new Image(Display.getDefault(), data);
		}
		return null;
	}

    public static Image createImageTransparent(Rectangle bounds) {
        if (!bounds.isEmpty()) {
            PaletteData pal = ImageUtils.createStandardPaletteData();
            byte rgbs[] = new byte[4 * bounds.width * bounds.height]; 

            RGB transparent = GC.TRANSPARENT_RGB_VALUE;
            byte r = (byte) transparent.red, g = (byte) transparent.green, b = (byte) transparent.blue;
            for (int i = 0; i < rgbs.length; i += 4) {
                rgbs[i] = r;
                rgbs[i + 1] = g; 
                rgbs[i + 2] = b;
                rgbs[i + 3] = (byte) 0xff;
            }
            ImageData data = new ImageData(bounds.width, bounds.height, 32, pal, 4, rgbs);
            data.transparentPixel = GC.TRANSPARENT_PIXEL;

            return new Image(Display.getDefault(), data);
        }
        return null;
    }

	public static String getStringAttribute(EObject object, String key) {
		IComponent component = getComponentInstance(object).getComponent();
        if (component == null)
            return null;
		IAttributes attributes = (IAttributes) component.getAdapter(IAttributes.class);
		return attributes.getAttribute(key);
	}
	
	public static boolean hasBooleanAttribute(EObject object, String key) {
		IComponent component = getComponentInstance(object).getComponent();
        if (component == null)
            return false;
		IAttributes attributes = (IAttributes) component.getAdapter(IAttributes.class);
		return attributes.getBooleanAttribute(key, false);
	}
	
	public static EObject findTransientObject(Object object) {
		if (object instanceof EObject) {
			if (Adapters.isTransientObject((EObject) object))
				return (EObject) object;
			
			IComponentInstance componentInstance = Adapters.getComponentInstance((EObject) object);
			if (componentInstance != null) 
				return  findTransientObject(componentInstance.getParent());
		}
		
		return null;
	}

	public static boolean isTransient(Object object) {
		return findTransientObject(object) != null;
	}
	
	public static boolean isTransientObject(EObject object) {
		return hasBooleanAttribute(object, CommonAttributes.IS_TRANSIENT_OBJECT);
	}

	public static boolean isNonLayoutObject(EObject object) {
		return hasBooleanAttribute(object, CommonAttributes.IS_NON_LAYOUT_OBJECT);
	}

	public static EObject findFirstNodeByAttribute(EObject root, String key) {
		if (hasBooleanAttribute(root, key))
			return root;
        
        IComponentInstance componentInstance = getComponentInstance(root);
		EObject[] children = componentInstance.getChildren();
		for (int i = 0; (children != null) && (i < children.length); i++) {
			EObject result = findFirstNodeByAttribute(children[i], key);
			if (result != null)
				return result;
		}
		
		return null;
	}

	public static ILayoutObject getLayoutObject(EObject object) {
		return (ILayoutObject) EcoreUtil.getRegisteredAdapter(object, ILayoutObject.class);
	}

	public static IVisualAppearance getVisualAppearance(EObject object) {
		return (IVisualAppearance) EcoreUtil.getRegisteredAdapter(object, IVisualAppearance.class);
	}

	public static IComponentEditor getComponentEditor(EObject object) {
		return (IComponentEditor) EcoreUtil.getRegisteredAdapter(object, IComponentEditor.class);
	}
	
	public static void setStatus(StatusHolder statusHolder, String errorString, Object[] params) {
		if (statusHolder == null)
			return;
		StatusBuilder builder = new StatusBuilder(ComponentSystemPlugin.getDefault());
		builder.add(IStatus.ERROR, errorString, params);
		statusHolder.setStatus(builder.createStatus(null, null));
	}

	public static IComponent getComponent(IDisplayModel dm, EObject obj) {
		IComponent result = null;
		IComponentInstance ci = Utilities.getComponentInstance(obj);
		if (ci != null) {
			result = dm.getDataModel().getComponentSet().lookupComponent(ci.getComponentId());
		}
		return result;
	}
	
	public static IAttributes getAttributes(IDisplayModel dm, EObject obj) {
		IAttributes result = null;
		IComponent component = getComponent(dm, obj);
		if (component != null) {
			result = (IAttributes) component.getAdapter(IAttributes.class);
		}
		return result;
	}

	public static boolean booleanAttribute(IDisplayModel dm, EObject obj, String attribute, boolean defaultValue) {
		boolean result = defaultValue;
		IAttributes attr = getAttributes(dm, obj);
		if (attr != null) {
			result = attr.getBooleanAttribute(attribute, defaultValue);
		}
		return result;
	}

	public static List<EObject> getLayoutChildren(EObject parent) {
		IComponentInstance instance = Utilities.getComponentInstance(parent);
		EObject[] children = instance.getChildren();
		if (children == null)
			return Collections.EMPTY_LIST;
		
		List<EObject> childList = Arrays.asList(children);
		List<EObject> layoutChildren = new ArrayList<EObject>();
		for (EObject child : childList) {
			if (Utilities.getLayoutObject(child) != null)
				layoutChildren.add(child);
		}
		return layoutChildren;
	}
	
	public static boolean hasOrderedLayout(IDisplayModel dm, EObject object) {
		IAttributes attributes = getAttributes(dm, object);
		String layoutType = attributes.getAttribute(CommonAttributes.LAYOUT_TYPE);
		return layoutType != null && layoutType.equalsIgnoreCase(ORDERED_LAYOUT_TYPE);
	}
	
	public static boolean hasVerticalRowLayout(IDisplayModel dm, EObject object) {
		if (hasOrderedLayout(dm, object)) {
			IAttributes attributes = getAttributes(dm, object);
			String orderedLayoutType = attributes.getAttribute(CommonAttributes.ORDERED_LAYOUT_TYPE);
			return orderedLayoutType != null && orderedLayoutType.equalsIgnoreCase(VERTICAL_ROW_LAYOUT);
		}
		
		return false;
	}

	public static boolean hasHorizontalRowLayout(IDisplayModel dm, EObject object) {
		if (hasOrderedLayout(dm, object)) {
			IAttributes attributes = getAttributes(dm, object);
			String orderedLayoutType = attributes.getAttribute(CommonAttributes.ORDERED_LAYOUT_TYPE);
			return orderedLayoutType != null && orderedLayoutType.equalsIgnoreCase(HORIZONTAL_ROW_LAYOUT);
		}
		
		return false;
	}

	public static ITargetFeedbackListener getTargetFeedbackListener(EObject object) {
		return (ITargetFeedbackListener) EcoreUtil.getRegisteredAdapter(object, ITargetFeedbackListener.class);
	}
}
