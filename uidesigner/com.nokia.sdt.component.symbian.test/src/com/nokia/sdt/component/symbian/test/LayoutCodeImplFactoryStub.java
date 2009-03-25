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


package com.nokia.sdt.component.symbian.test;

import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.ILayout;
import com.nokia.sdt.displaymodel.ILookAndFeel;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * 
 *
 */
public class LayoutCodeImplFactoryStub implements IImplFactory {

	public final static int TEST_OFFSET = 25;

	class LayoutStub implements ILayout {
	
		private EObject instance;
	
		public LayoutStub(EObject componentInstance) {
			this.instance = componentInstance;
		}
		
		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.ILayout#layout()
		 */
		public void layout(ILookAndFeel laf) {
			IComponentInstance componentInstance = 
				(IComponentInstance) EcoreUtil.getRegisteredAdapter(getEObject(), IComponentInstance.class);
			IPropertySource properties = 
				(IPropertySource) EcoreUtil.getRegisteredAdapter(getEObject(), IPropertySource.class);
			EObject child = componentInstance.getChildren()[0];
			IPropertySource childProperties = 
				(IPropertySource) EcoreUtil.getRegisteredAdapter(child, IPropertySource.class);
			IPropertySource location = (IPropertySource) childProperties.getPropertyValue("location");
			location.setPropertyValue("x", new Integer(getX(properties)));
			location.setPropertyValue("y", new Integer(getY(properties) + TEST_OFFSET));
			IPropertySource size = (IPropertySource) childProperties.getPropertyValue("size");
			size.setPropertyValue("width", new Integer(getWidth(properties)));
			size.setPropertyValue("height", new Integer(getHeight(properties) - TEST_OFFSET));
		}
		
		private int getX(IPropertySource properties) {
			IPropertySource location = (IPropertySource) properties.getPropertyValue("location");			
			return ((Integer) location.getPropertyValue("x")).intValue();
		}
		
		private int getY(IPropertySource properties) {
			IPropertySource location = (IPropertySource) properties.getPropertyValue("location");			
			return ((Integer) location.getPropertyValue("y")).intValue();
		}
		
		private int getWidth(IPropertySource properties) {
			IPropertySource location = (IPropertySource) properties.getPropertyValue("size");			
			return ((Integer) location.getPropertyValue("width")).intValue();
		}
		
		private int getHeight(IPropertySource properties) {
			IPropertySource location = (IPropertySource) properties.getPropertyValue("size");			
			return ((Integer) location.getPropertyValue("height")).intValue();
		}

		public Point getPreferredSize(int wHint, int hHint, ILookAndFeel laf) {
			return new Point(wHint, hHint);
		}

		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.IModelAdapter#getEObject()
		 */
		public EObject getEObject() {
			return instance;
		}

	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.component.adapter.ICodeImplFactory#getImpl(org.eclipse.emf.ecore.EObject)
	 */
	public Object getImpl(EObject componentInstance) {
		return new LayoutStub(componentInstance);
	}
}
