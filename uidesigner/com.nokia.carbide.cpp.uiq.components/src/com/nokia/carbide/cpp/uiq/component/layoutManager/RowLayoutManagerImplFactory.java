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
/* START_USECASES: CU10 END_USECASES */

package com.nokia.carbide.cpp.uiq.component.layoutManager;

import java.lang.Boolean;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.datamodel.adapter.IComponentInstance;
import com.nokia.sdt.datamodel.adapter.ILayout;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.displaymodel.adapter.ILayoutContainer;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.sdt.displaymodel.adapter.ITargetFeedbackListener;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.requests.DropRequest;
import org.eclipse.swt.graphics.*;
import org.eclipse.ui.views.properties.IPropertySource;

public class RowLayoutManagerImplFactory implements IImplFactory {
	
	static class ImplFact implements ILayout, ITargetFeedbackListener {
		
		static final String ROWLAYOUTMANAGER_VIEW_TOPMARGIN = "RowLayoutManager.View.topMargin"; //$NON-NLS-1$
		static final String ROWLAYOUTMANAGER_VIEW_ROWHEIGHT = "RowLayoutManager.View.rowHeight"; //$NON-NLS-1$
		static final String ROWLAYOUTMANAGER_DIALOG_TOPMARGIN = "RowLayoutManager.Dialog.topMargin"; //$NON-NLS-1$
		static final String ROWLAYOUTMANAGER_DIALOG_ROWHEIGHT = "RowLayoutManager.Dialog.rowHeight"; //$NON-NLS-1$
		static final String DEFAULT_LAYOUT_DATA_PROPERTY = "defaultLayoutData"; //$NON-NLS-1$
		static final String CONTROL_LAYOUT_DATA_PROPERTY = "layoutData"; //$NON-NLS-1$
		static final String NORMALIZEROWHEIGHTS_PROPERTY = "normalizeRowHeights"; //$NON-NLS-1$
		static final String HORIZONTALALIGNMENT_PROPERTY = "horizontalAlignment"; //$NON-NLS-1$
		static final String VERTICALALIGNMENT_PROPERTY = "verticalAlignment"; //$NON-NLS-1$
		static final String LAYOUTWHENINVISIBLE_PROPERTY = "layoutWhenInvisible"; //$NON-NLS-1$
		static final String MINIMUMWIDTH_PROPERTY = "minimumWidth"; //$NON-NLS-1$
		static final String MINIMUMHEIGHT_PROPERTY = "minimumHeight"; //$NON-NLS-1$
		static final String LEFTMARGIN_PROPERTY = "leftMargin"; //$NON-NLS-1$
		static final String RIGHTMARGIN_PROPERTY = "rightMargin"; //$NON-NLS-1$
		static final String TOPMARGIN_PROPERTY = "topMargin"; //$NON-NLS-1$
		static final String BOTTOMMARGIN_PROPERTY = "bottomMargin"; //$NON-NLS-1$
		static final String VERTICALEXCESSGRABWEIGHT_PROPERTY = "verticalExcessGrabWeight"; //$NON-NLS-1$
		static final String SCROLLABLE_PROPERTY = "scrollable"; //$NON-NLS-1$
		static final String VIEW_COMPONENT_ID = "com.nokia.carbide.uiq.CQikView"; //$NON-NLS-1$
		static final String DIALOG_COMPONENT_ID = "com.nokia.carbide.uiq.CQikSimpleDialog"; //$NON-NLS-1$
		static final String CONTAINER_COMPONENT_ID = "com.nokia.carbide.uiq.CQikContainer"; //$NON-NLS-1$
		static final Point ZERO_SIZE = new Point(0, 0);
		
		private EObject eobject = null;
		private Point nestedPreferredSize;
		
		public ImplFact(EObject componentInstance) {
			this.eobject = componentInstance;
			Check.checkArg(eobject);
			this.nestedPreferredSize = new Point(0, 0);
			Check.checkArg(nestedPreferredSize);
		}
		
		/****** ILayout ******/
		public void layout(ILookAndFeel laf) {
			RowLayoutControl.setLayoutRowSize(getRowSize(laf));

			RowLayoutData defaultLayoutData = getLayoutDataProperty(eobject, DEFAULT_LAYOUT_DATA_PROPERTY);
			RowLayoutControl.setDefaultLayoutData(defaultLayoutData);
			
			IPropertySource ps = Utilities.getPropertySource(eobject);
			if (ps instanceof IPropertySource) {
				boolean bpv = getBooleanPropertyValue(ps, NORMALIZEROWHEIGHTS_PROPERTY);
				RowLayoutControl.setNormalized(bpv);
			}
			
			EObject parentObject = Utilities.getComponentInstance(eobject).getParent();
			List<EObject> layoutSiblings = Utilities.getLayoutChildren(parentObject);
			List<RowLayoutControl> rowControls = new ArrayList<RowLayoutControl>();
			
			// Set width/height from default layout data
			int maxRowControlHeight = 0;
			for (EObject siblingObj : layoutSiblings) {
				ILayoutObject layoutSibling = Utilities.getLayoutObject(siblingObj);
				RowLayoutControl rowControl = new RowLayoutControl();
				rowControl.setLayoutObject(layoutSibling);
				RowLayoutData layoutData = getLayoutDataProperty(layoutSibling.getEObject(), CONTROL_LAYOUT_DATA_PROPERTY);
				rowControl.setLayoutData(layoutData);
				rowControl.setSizeFromLayoutData(laf);
				if (rowControl.layoutControlWhenInvisible())
					rowControls.add(rowControl);
				if (RowLayoutControl.isNormalized())
					maxRowControlHeight = Math.max(maxRowControlHeight, layoutSibling.getBounds().height);
			}
			
			// Distribute excess space
			// TODO: this task is not completed yet.
			
			nestedPreferredSize.x = RowLayoutControl.getLayoutRowSize().x;
			nestedPreferredSize.y = 0;
			int topMargin = getTopMargin(laf);
			int rowGroupOffset = topMargin;
			
			for (RowLayoutControl rowControl : rowControls) {
				rowControl.setRowsSize(maxRowControlHeight);
				Point rowControlPos = new Point(0, rowGroupOffset);
				rowControl.setRowsPosition( rowControlPos );
				rowControl.layoutControl();
				nestedPreferredSize.y += rowControl.getRowGroupHeight();
				rowGroupOffset = topMargin + nestedPreferredSize.y;
			}
			EObject parentContainerObject = Utilities.getComponentInstance(Utilities.getComponentInstance(eobject).getParent()).getParent();
			ILayoutContainer lc = ModelUtils.getLayoutContainer(parentContainerObject);
			lc.layoutChildren();			
			
		}
		
		public Point getPreferredSize(int wHint, int hHint, ILookAndFeel laf) {
			if ( nestedPreferredSize == null || nestedPreferredSize.x == 0 || nestedPreferredSize.y == 0 ) {
				return new Point(wHint, hHint);
			}
			
			if ( isNestedContainer() && isScrollableContainer() )
				return getRowSize(laf);
			
			return nestedPreferredSize;
		}
		
		public IFigure beginTargetFeedback(GraphicalEditPart editPart) {
			return null;
		}
		
		public void mouseMoved(DropRequest dropRequest) {
			
		}
		
		public void endTargetFeedback() {
				
		}
		
		public EObject getEObject() {
			return eobject;
		}
		
		private Point getRowSize(ILookAndFeel laf) {
			int height = 27;
			
			IComponentInstance ci = ModelUtils.getComponentInstance(eobject);
			IComponentInstance rootCI = ModelUtils.getRootInstance(ci);
			String rootComponentId = rootCI.getComponentId();
			if (rootComponentId != null) {
				if ( rootComponentId.equals(VIEW_COMPONENT_ID) )
					height = laf.getInteger(ROWLAYOUTMANAGER_VIEW_ROWHEIGHT, 27);
				else
					height = laf.getInteger(ROWLAYOUTMANAGER_DIALOG_ROWHEIGHT, 28);
			}
			
			EObject parentObject = Utilities.getComponentInstance(eobject).getParent();
			Rectangle appSpace = Utilities.getLayoutObject(parentObject).getBounds();
			int width = appSpace.width;
			return new Point(width, height);
		}
		
		private RowLayoutData getLayoutDataProperty(EObject eObj, Object id) {
			RowLayoutData ld = new RowLayoutData();
			IPropertySource ps = Utilities.getPropertySource(eObj);
			if (ps instanceof IPropertySource) {
				Object pvObject = ps.getPropertyValue(id);
				if (pvObject instanceof IPropertySource) {
					IPropertySource pvPS = (IPropertySource)pvObject;				
					ld.horizontalAlignment = ld.haEnum.indexOf(getEnumPropertyValue(pvPS, HORIZONTALALIGNMENT_PROPERTY));
					ld.verticalAlignment = ld.vaEnum.indexOf(getEnumPropertyValue(pvPS, VERTICALALIGNMENT_PROPERTY));
					ld.layoutWhenInvisible = getEnumPropertyValue(pvPS, LAYOUTWHENINVISIBLE_PROPERTY);
					ld.minimumWidth = integerPropertyValue(pvPS, MINIMUMWIDTH_PROPERTY);
					ld.minimumHeight = integerPropertyValue(pvPS, MINIMUMHEIGHT_PROPERTY);
					ld.leftMargin = integerPropertyValue(pvPS, LEFTMARGIN_PROPERTY);
					ld.rightMargin = integerPropertyValue(pvPS, RIGHTMARGIN_PROPERTY);
					ld.topMargin = integerPropertyValue(pvPS, TOPMARGIN_PROPERTY);
					ld.bottomMargin = integerPropertyValue(pvPS, BOTTOMMARGIN_PROPERTY);
					//TODO: vertical excess grab weight not handled yet.
					ld.verticalExcessGrabWeight = integerPropertyValue(pvPS, VERTICALEXCESSGRABWEIGHT_PROPERTY);
				}
			}
			return ld;
		}			
		
		private boolean getBooleanPropertyValue(IPropertySource ps, Object id) {
			boolean result = false;
			Object po = ps.getPropertyValue(id);
			if (po instanceof Boolean)
				result = ((Boolean)po).booleanValue();
			
			return result;
		}
		
		private int integerPropertyValue(IPropertySource ps, Object id) {
			int result = -1;
			String i =new String();
			i = (String)ps.getPropertyValue(id);
			//if (i instanceof Number)
				result = ((Number)(Integer.valueOf(i))).intValue();
				
			return result;
		}
		
		private String getEnumPropertyValue(IPropertySource ps, Object id) {
			String result = null;
			Object po = ps.getPropertyValue(id);
			if (po instanceof String)
				result = ((String)po).toString();
			
			return result;
		}
		
		private int getTopMargin(ILookAndFeel laf) {
			if ( isNestedContainer() )
				return 0;
			
			IComponentInstance ci = ModelUtils.getComponentInstance(eobject);
			IComponentInstance rootCI = ModelUtils.getRootInstance(ci);
			String rootComponentId = rootCI.getComponentId();
			if (rootComponentId != null) {
				if ( rootComponentId.equals(VIEW_COMPONENT_ID) )
					return laf.getInteger(ROWLAYOUTMANAGER_VIEW_TOPMARGIN, 2);
				else
					return laf.getInteger(ROWLAYOUTMANAGER_DIALOG_TOPMARGIN, 0);
			}
			return 0;
		}
		
		private boolean isNestedContainer() {
			EObject parent = Utilities.getComponentInstance(eobject).getParent();
			EObject grandParent = Utilities.getComponentInstance(parent).getParent();
			return ModelUtils.isInstanceOf(parent, CONTAINER_COMPONENT_ID) && ModelUtils.isInstanceOf(grandParent, CONTAINER_COMPONENT_ID);
		}
		
		private boolean isScrollableContainer() {
			EObject parent = Utilities.getComponentInstance(eobject).getParent();
			IPropertySource ps = ModelUtils.getPropertySource(parent);
			String scrollable = getEnumPropertyValue(ps, SCROLLABLE_PROPERTY);
			return scrollable.equals("EQikCtScrollableContainer"); //$NON-NLS-1$
		}
	}
	
	public Object getImpl(EObject componentInstance) {
		return new ImplFact(componentInstance);
	}
}
