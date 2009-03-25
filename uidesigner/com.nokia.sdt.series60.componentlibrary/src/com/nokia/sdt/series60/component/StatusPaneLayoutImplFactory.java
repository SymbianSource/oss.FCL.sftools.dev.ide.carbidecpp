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


package com.nokia.sdt.series60.component;

import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.ILayout;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.sdt.symbian.S60ComponentAttributes;
import com.nokia.sdt.symbian.dm.S60ModelUtils;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.views.properties.IPropertySource;

public class StatusPaneLayoutImplFactory implements IImplFactory {

	public static class Layout implements ILayout {

		private EObject eObject;
		private IComponentInstance componentInstance;
		private ILayoutObject layoutObject;
		private IPropertySource propertySource;
		
		/**
		 * @param instance
		 */
		public Layout(EObject instance) {
			eObject = instance;
			this.componentInstance = (IComponentInstance) EcoreUtil.getRegisteredAdapter(
					eObject, IComponentInstance.class);
			this.layoutObject = (ILayoutObject) EcoreUtil.getRegisteredAdapter(
					eObject, ILayoutObject.class);
			this.propertySource = (IPropertySource) EcoreUtil.getRegisteredAdapter(
					eObject, IPropertySource.class);
			Check.checkArg(componentInstance);
			Check.checkArg(layoutObject);
			Check.checkArg(propertySource);
		}
		

		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.IVisualAppearance#getPreferredSize(int, int, com.nokia.sdt.displaymodel.ILookAndFeel)
		 */
		public Point getPreferredSize(int wHint, int hHint, ILookAndFeel laf) {
			return null; // not needed
		}

		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.ILayout#layout(com.nokia.sdt.displaymodel.ILookAndFeel)
		 */
		public void layout(ILookAndFeel laf) {
//			System.out.println("layout called");
		    Rectangle rect;
		    ILayoutObject layoutObject;
            
            // layout title
            layoutObject = ModelUtils.getLayoutObject(ModelUtils.findFirstComponentInstance(
            		eObject, S60ModelUtils.S60_TITLE_PANE_BASE, true));
            if (layoutObject != null) {
                rect = laf.getRectangle("status.pane.title.bounds");
                if (rect != null)
                    layoutObject.setBounds(rect);
            }
            
            // layout context icon
            layoutObject = ModelUtils.getLayoutObject(ModelUtils.findFirstComponentInstance(eObject, S60ModelUtils.S60_CONTEXT_ICON_BASE, true));
            if (layoutObject != null) {
                if (laf.getBoolean("is.portrait", true)) {
                    Point size = laf.getDimension("context.icon.size");
                    Point offs = laf.getPosition("context.icon.position");
                    if (size != null && offs != null) {
                        rect = new Rectangle(offs.x, offs.y, size.x, size.y);
                        layoutObject.setBounds(rect);
                    }
                } else {
                    layoutObject.setBounds(new Rectangle(0, 0, 0, 0));
                }
            } 
            
            // layout navi pane content
            layoutObject = ModelUtils.getLayoutObject(ModelUtils.findImmediateChildWithAttributeValue(eObject, S60ComponentAttributes.NAVIPANE_CONTENT, "true"));
            if (layoutObject != null) {
                rect = laf.getRectangle("navi.content.bounds");
                if (rect != null)
                    layoutObject.setBounds(rect);
            } 
		}

		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.IModelAdapter#getEObject()
		 */
		public EObject getEObject() {
			return eObject;
		}

	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.adapter.IImplFactory#getImpl(org.eclipse.emf.ecore.EObject)
	 */
	public Object getImpl(EObject componentInstance) {
		return new Layout(componentInstance);
	}

}
