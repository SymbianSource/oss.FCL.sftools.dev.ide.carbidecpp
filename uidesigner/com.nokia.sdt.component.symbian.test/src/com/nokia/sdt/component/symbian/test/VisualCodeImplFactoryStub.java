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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.datamodel.adapter.IDirectLabelEdit;
import com.nokia.sdt.datamodel.adapter.IVisualAppearance;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.utils.drawing.GC;
import com.nokia.sdt.utils.drawing.IFont;

/**
 * 
 *
 */
public class VisualCodeImplFactoryStub implements IImplFactory {

	public final static int TEST_COLOR = SWT.COLOR_RED;

	class VisualStub implements IVisualAppearance, IDirectLabelEdit {
	
		private EObject instance;
	
		public VisualStub(EObject componentInstance) {
			this.instance = componentInstance;
		}
		
		/* (non-Javadoc)
		 * @see com.nokia.sdt.component.adapter.IRender#draw(com.nokia.sdt.utils.drawing.GC, org.eclipse.emf.ecore.EObject)
		 */
		public void draw(GC gc, ILookAndFeel laf) {
			Color background = Display.getCurrent().getSystemColor(TEST_COLOR);
			gc.setBackground(background);
		}
		
		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.IVisualAppearance#getPreferredSize(int, int, com.nokia.sdt.displaymodel.ILookAndFeel)
		 */
		public Point getPreferredSize(int wHint, int hHint, ILookAndFeel laf) {
			return new Point(wHint + 10, hHint + 20);
		}

		public String[] getPropertyPaths() {
			return new String[] { "foo" };
		}

		public Rectangle getVisualBounds(String propertyPath, ILookAndFeel laf) {
			return new Rectangle(0, 0, 10, 10);
		}

		public IFont getLabelFont(String propertyPath, ILookAndFeel laf) {
			return null; // no look and feel
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
		return new VisualStub(componentInstance);
	}
}
