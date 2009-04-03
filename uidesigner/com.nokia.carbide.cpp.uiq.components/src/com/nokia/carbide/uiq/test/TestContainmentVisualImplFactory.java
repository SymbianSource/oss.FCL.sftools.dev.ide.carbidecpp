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


package com.nokia.carbide.uiq.test;

import com.nokia.sdt.component.adapter.IImplFactory;
import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.component.symbian.laf.Series60LAF;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.displaymodel.adapter.ILayoutContainer;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.carbide.uiq.test.VisualUtils;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.*;
import org.eclipse.ui.views.properties.IPropertySource;

public class TestContainmentVisualImplFactory implements IImplFactory {

	public static class Layout implements  ILayout {
		
		private EObject eObject;
		private IComponentInstance componentInstance;
		private ILayoutObject layoutObject;
		private IPropertySource propertySource;
		private final static int TOP_LEFT = 3;
		private final static int BOTTOM_RIGHT = 5;
		private Rectangle lastCalculatedRect;
		
		private final static String FILL_RECT_PROPERTY = "fillRect";
		private final static String NORMALIZED_ROW_HEIGHTS = "normalizeRowHeights";
		private final static String HORIZONTAL_ALIGNMENT = "horizontalAlignment";
		private final static String VERTICAL_ALIGNMENT = "verticalAlignment";
		private final static String LEFT_MARGIN = "leftMargin";
		private final static String RIGHT_MARGIN = "rightMargin";
		private final static String TOP_MARGIN = "topMargin";
		private final static String BOTTOM_MARGIN = "bottomMargin";

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
			componentInstance.addPropertyListener(new IComponentInstancePropertyListener() {
				public void propertyChanged(EObject componentInstance, Object propertyId) {
					if (propertyId.equals(FILL_RECT_PROPERTY)) {
						ILayoutContainer container = 
							(ILayoutContainer) EcoreUtil.getRegisteredAdapter(getEObject(), ILayoutContainer.class);
						container.layoutChildren();
					}
				}
			});
		}
		
		private boolean fillClientRect() {
			if (propertySource.getPropertyValue(FILL_RECT_PROPERTY) == null)
				return false;
			return ((Boolean) propertySource.getPropertyValue(FILL_RECT_PROPERTY)).booleanValue();
		}
		
		private Rectangle getMaximumUsableRect() {
			Rectangle bounds = layoutObject.getBounds();
			Rectangle rect = new Rectangle(0, 0, bounds.width, bounds.height);
			if (!fillClientRect()) {
				return VisualUtils.shrinkRect(rect, TOP_LEFT, TOP_LEFT, BOTTOM_RIGHT, BOTTOM_RIGHT);
			}
			
			return rect;
		}
		
		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.IVisualAppearance#draw(com.nokia.sdt.utils.drawing.GC, com.nokia.sdt.displaymodel.ILookAndFeel)
		 */

		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.IVisualAppearance#getPreferredSize(int, int, com.nokia.sdt.displaymodel.ILookAndFeel)
		 */

		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.ILayout#layout(com.nokia.sdt.displaymodel.ILookAndFeel)
		 */
		public void layout(ILookAndFeel laf) {
			Rectangle usableRect = getMaximumUsableRect();
			Rectangle savedRect = lastCalculatedRect;
			EObject[] children = componentInstance.getChildren();
			if (children != null) {
				int[] heights = new int[children.length];
				int maxWidth = 0;
				for (int i = 0; i < children.length; i++) {
					if (Utilities.isNonLayoutObject(children[i]))
						continue;
					
					IVisualAppearance childAppearance = 
						(IVisualAppearance) EcoreUtil.getRegisteredAdapter(children[i], IVisualAppearance.class);
					IPropertySource childProperties = 
						(IPropertySource) EcoreUtil.getRegisteredAdapter(children[i], IPropertySource.class);
//					Boolean showText = (Boolean)childProperties.getPropertyValue("showText");
					
					if (childAppearance != null) {
						Point size = null;
						if (fillClientRect()) // if we're filling the rect, give child our width
							size = childAppearance.getPreferredSize(usableRect.width, -1, laf);
						else // otherwise, let them give us their width
							size = childAppearance.getPreferredSize(-1, -1, laf);
						if (size == null)
							heights[i] = 0;
						else {
							heights[i] = size.y;
							maxWidth = Math.max(maxWidth, size.x);
						}
					}
				}
				calculateRect(usableRect, heights, maxWidth, laf);
				int curY;
				String halign,valign,rmargin,lmargin,tmargin,bmargin;
				int hpos,vextra;
				if(((Boolean) propertySource.getPropertyValue(NORMALIZED_ROW_HEIGHTS)))
					{
					int maxheight=0;
					for (int i = 0; i < children.length; i++)
						{
						IVisualAppearance childAppearance = 
							(IVisualAppearance) EcoreUtil.getRegisteredAdapter(children[i], IVisualAppearance.class);
							Point size = null;
						size = childAppearance.getPreferredSize(-1, -1, laf);
						if(size.y > maxheight) maxheight=size.y;
						}
					maxheight=50;
					curY = 0;
					for (int i = 0; i < children.length; i++) {
						if (Utilities.isNonLayoutObject(children[i]))
							continue;

						IVisualAppearance childAppearance = 
							(IVisualAppearance) EcoreUtil.getRegisteredAdapter(children[i], IVisualAppearance.class);
							Point size = null;
							size = childAppearance.getPreferredSize(-1, -1, laf);

							IPropertySource childProperties = 
								(IPropertySource) EcoreUtil.getRegisteredAdapter(children[i], IPropertySource.class);
							
							halign = (String)childProperties.getPropertyValue(HORIZONTAL_ALIGNMENT);
							valign = (String)childProperties.getPropertyValue(VERTICAL_ALIGNMENT);
							lmargin = (String)childProperties.getPropertyValue(LEFT_MARGIN);
							rmargin = (String)childProperties.getPropertyValue(RIGHT_MARGIN);
							tmargin = (String)childProperties.getPropertyValue(TOP_MARGIN);
							bmargin = (String)childProperties.getPropertyValue(BOTTOM_MARGIN);
							
							Rectangle childBounds;

							if(  halign.equals("EQikLayoutHAlignLeft"))
		                    	hpos = 0;
		                    else if(  halign.equals("EQikLayoutHAlignRight"))
		                    	hpos = usableRect.width-size.x;
		                    else if( halign.equals("EQikLayoutHAlignCenter"))
		                    	hpos = usableRect.width/2-size.x/2;
		                    else
		                    	hpos = 0;

							if(  valign.equals("EQikLayoutVAlignTop"))
		                    	vextra = 0;
		                    else if(  valign.equals("EQikLayoutVAlignCenter"))
		                    	vextra = (maxheight-size.y)/2;
		                    else if( valign.equals("EQikLayoutVAlignBottom"))
		                    	vextra = maxheight-size.y;
		                    else
		                    	vextra = 0;

							if( lmargin.equals("EQikLayoutContentGapLeft")&& hpos==0 )
								hpos+=2;
							if( lmargin.equals("EQikLayoutContentGap")&& hpos==0 )
								hpos+=4;
							if( lmargin.equals("EQikLayoutLeftEdgeToContent")&& hpos==0 )
								hpos+=4;
							if( lmargin.equals("EQikLayoutLeftEdgeToPopOut") && hpos==0 )
								hpos+=7;

							if( rmargin.equals("EQikLayoutContentGapRight")&& hpos==usableRect.width-size.x )
								hpos-=2;
							if( rmargin.equals("EQikLayoutContentGap")&& hpos==usableRect.width-size.x )
								hpos-=4;
							if( rmargin.equals("EQikLayoutRightEdgeToContent")&& hpos==usableRect.width-size.x )
								hpos-=4;
							if( rmargin.equals("EQikLayoutRightEdgeToPopOut") && hpos==usableRect.width-size.x )
								hpos-=7;

							if( tmargin.equals("EQikLayoutRowTopMargin")&& vextra==0 )
								vextra++;

							if( bmargin.equals("EQikLayoutRowBottomMargin")&& vextra==maxheight-size.y )
								vextra--;
							
							
							childBounds = new Rectangle(hpos, curY+vextra, size.x , size.y);
	
								
						curY += maxheight;
						ILayoutObject childObject = 
							(ILayoutObject) EcoreUtil.getRegisteredAdapter(children[i], ILayoutObject.class);
						if (childObject != null)
	                        childObject.setBounds(childBounds);
						}
					}
				else
				{
				curY = lastCalculatedRect.y;
				for (int i = 0; i < children.length; i++) {
					if (Utilities.isNonLayoutObject(children[i]))
						continue;

					IVisualAppearance childAppearance = 
					(IVisualAppearance) EcoreUtil.getRegisteredAdapter(children[i], IVisualAppearance.class);
					Point size = null;
					size = childAppearance.getPreferredSize(-1, -1, laf);

					IPropertySource childProperties = 
						(IPropertySource) EcoreUtil.getRegisteredAdapter(children[i], IPropertySource.class);
					
					halign = (String)childProperties.getPropertyValue(HORIZONTAL_ALIGNMENT);
					valign = (String)childProperties.getPropertyValue(VERTICAL_ALIGNMENT);
					lmargin = (String)childProperties.getPropertyValue(LEFT_MARGIN);
					rmargin = (String)childProperties.getPropertyValue(RIGHT_MARGIN);

					Rectangle childBounds;

					if(  halign.equals("EQikLayoutHAlignLeft"))
                    	hpos = 0;
                    else if(  halign.equals("EQikLayoutHAlignRight"))
                    	hpos = usableRect.width-size.x;
                    else if( halign.equals("EQikLayoutHAlignCenter"))
                    	hpos = usableRect.width/2-size.x/2;
                    else
                    	hpos = 0;

					if( lmargin.equals("EQikLayoutContentGapLeft")&& hpos==0 )
						hpos+=2;
					if( lmargin.equals("EQikLayoutContentGap")&& hpos==0 )
						hpos+=4;
					if( lmargin.equals("EQikLayoutLeftEdgeToContent")&& hpos==0 )
						hpos+=4;
					if( lmargin.equals("EQikLayoutLeftEdgeToPopOut") && hpos==0 )
						hpos+=7;

					if( rmargin.equals("EQikLayoutContentGapRight")&& hpos==usableRect.width-size.x )
						hpos-=2;
					if( rmargin.equals("EQikLayoutContentGap")&& hpos==usableRect.width-size.x )
						hpos-=4;
					if( rmargin.equals("EQikLayoutRightEdgeToContent")&& hpos==usableRect.width-size.x )
						hpos-=4;
					if( rmargin.equals("EQikLayoutRightEdgeToPopOut") && hpos==usableRect.width-size.x )
						hpos-=7;
					
					
                    childBounds = new Rectangle(hpos, curY, size.x , size.y);

						curY += heights[i];
					ILayoutObject childObject = 
						(ILayoutObject) EcoreUtil.getRegisteredAdapter(children[i], ILayoutObject.class);
					if (childObject != null)
                        childObject.setBounds(childBounds);
					}
				}
			}
			else {
				calculateRect(usableRect, new int[] {0}, 1, laf);
			}
			if (!lastCalculatedRect.equals(savedRect))
				Utilities.fireImageChanged(getEObject());
		}
		
		public Point getPreferredSize(int hint, int hint2, ILookAndFeel laf) {
			return null;
		}

		private void calculateRect(Rectangle usableRect, int[] childHeights, int maxWidth, ILookAndFeel laf) {
			if (fillClientRect()) {
				lastCalculatedRect = usableRect;
			}
			else {
				int totalHeight = 0;
				for (int i = 0; i < childHeights.length; i++) {
					totalHeight += childHeights[i];
				}
				Rectangle bounds = layoutObject.getBounds();
				Point screenSize = laf.getDimension(Series60LAF.SCREEN_SIZE);
				Check.checkState(screenSize != null);
				int top = 0;//((screenSize.y - totalHeight) / 2) - bounds.y;
				int left = 0;//Math.max((usableRect.width - maxWidth) / 2, usableRect.x);
				int width = Math.max(maxWidth, usableRect.width);
				lastCalculatedRect = new Rectangle(left, top, width, totalHeight);
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
