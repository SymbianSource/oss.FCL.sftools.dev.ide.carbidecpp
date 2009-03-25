/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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
import com.nokia.sdt.component.symbian.displaymodel.Utilities;
import com.nokia.sdt.component.symbian.laf.Series60LAF;
import com.nokia.sdt.datamodel.adapter.*;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.displaymodel.adapter.ILayoutObject;
import com.nokia.sdt.symbian.ScalableText;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.sdt.utils.drawing.*;
import com.nokia.sdt.utils.drawing.GC;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 *
 */
public class DataQueryVisualAndLayoutImplFactory implements IImplFactory {

	public static class VisualAdapter implements IVisualAppearance, ILayout, IDirectLabelEdit {

		private EObject eObject;
		private IComponentInstance componentInstance;
		private IPropertySource propertySource;
		
		private boolean initedRects;
		private Rectangle instanceRect;
		private Rectangle popupRect;
		private Rectangle promptRect;
		private Rectangle promptRect2;
		private Rectangle editRect;
		private Rectangle editRect2;
		private boolean isMulti;
		private String[] propertyPaths;
		
		private final static String TEXT_PROPERTY = "text";
		private final static String TEXT2_PROPERTY = "text2";
		private final static String MULTI_LINE_QUERY_ID = "com.nokia.sdt.series60.MultiLineDataQuery";
		
		/**
		 * @param instance
		 */
		public VisualAdapter(EObject instance) {
			eObject = instance;
			this.componentInstance = (IComponentInstance) EcoreUtil.getRegisteredAdapter(
					eObject, IComponentInstance.class);
			this.propertySource = (IPropertySource) EcoreUtil.getRegisteredAdapter(
					eObject, IPropertySource.class);
			Check.checkArg(componentInstance);
			Check.checkArg(propertySource);
			componentInstance.addPropertyListener(new IComponentInstancePropertyListener() {
				public void propertyChanged(EObject componentInstance, Object propertyId) {
					// force layout if the prompt changes
				}
			});
			
			isMulti = ModelUtils.isInstanceOf(instance, MULTI_LINE_QUERY_ID);
		}
		
		private void calculateRects(boolean forceCalculate, ILookAndFeel laf) {
			boolean calculate = forceCalculate || !initedRects;
			if (calculate) {
				initializeRects();
				
				int padding = laf.getInteger("note.padding", 8);
				Point noteInset = laf.getDimension("note.inset");
				int lineGap = laf.getInteger("note.text.lineGap", 0);
				Point screenSize = laf.getDimension(Series60LAF.SCREEN_SIZE);
				Rectangle controlPaneBounds = laf.getRectangle("control.pane.bounds");
				Rectangle editorsRect = laf.getRectangle("query.editor");
				boolean portrait = laf.getBoolean("is.portrait", true);
				Rectangle contentBounds = laf.getRectangle(Series60LAF.CONTENT_PANE_BOUNDS);
		        int width = Math.min(screenSize.x, screenSize.y) - 4 * padding;

		        // the instance width doesn't change relative to the prompts, only the height does
				if (portrait) {
					instanceRect.x = 0;
					popupRect.y = 0;
					popupRect.x = contentBounds.x + (contentBounds.width - width) / 2;
					instanceRect.width = screenSize.x;
					// still need instanceRect y and height, popupRect height
				}
				else {
					instanceRect.y = 0;
					instanceRect.height = screenSize.y;
					instanceRect.width = screenSize.y;
					instanceRect.x = screenSize.x - instanceRect.width;
					// still need popupRect y and height (centered)
				}
				
				// from the instance width, the popup width can be set
				popupRect.width = width;
				
				// the prompt rect is then determined for the first prompt
				promptRect.y = padding*2;
				promptRect.x = popupRect.x + editorsRect.x;
				promptRect.width = popupRect.width - promptRect.x;
				String text = getScalablePromptText(TEXT_PROPERTY, laf, promptRect.width);
				IFont font = getLabelFont(TEXT_PROPERTY, laf);
				int maxLines = isMulti ? 2 : 3;
				Point extent = calculateWrappedTextSize(text, font, promptRect.width, lineGap, maxLines);
				promptRect.height = extent.y;
				
				// the editor rect can then be determined for the first editor
				editRect.x = popupRect.x + editorsRect.x;
				editRect.y = promptRect.y + promptRect.height + editorsRect.y;
				editRect.width = editorsRect.width;
				editRect.height = editorsRect.height;
				
				if (isMulti) {
					promptRect2.y = editRect.y + editRect.height + editorsRect.y + noteInset.y + (padding*3);
					promptRect2.x = promptRect.x;
					promptRect2.width = promptRect.width;
					text = getScalablePromptText(TEXT2_PROPERTY, laf, promptRect.width);
					font = getLabelFont(TEXT2_PROPERTY, laf);
					extent = calculateWrappedTextSize(text, font, promptRect2.width, lineGap, maxLines);
					promptRect2.height = extent.y;

					editRect2.x = editRect.x;
					editRect2.y = promptRect2.y + promptRect2.height + editorsRect.y;
					editRect2.width = editRect.width;
					editRect2.height = editRect.height;
					popupRect.height = editRect2.y + editRect2.height + editorsRect.y + noteInset.y + (padding*2);
				}
				else {
					popupRect.height = editRect.y + editRect.height + editorsRect.y + noteInset.y + (padding*2);
				}

				if (portrait) {
					instanceRect.height = popupRect.height + (padding*2) + controlPaneBounds.height;
					instanceRect.y = screenSize.y - instanceRect.height;
				}
				else {
					popupRect.y = (screenSize.y - popupRect.height) / 2; 
					promptRect.y += popupRect.y;
					editRect.y += popupRect.y;
					if (isMulti) {
						promptRect2.y += popupRect.y;
						editRect2.y += popupRect.y;
					}
				}
			}
		}
		
		private void initializeRects() {
			instanceRect = new Rectangle(0,0,0,0);
			popupRect = new Rectangle(0,0,0,0);
			promptRect = new Rectangle(0,0,0,0);
			promptRect2 = new Rectangle(0,0,0,0);
			editRect = new Rectangle(0,0,0,0);
			editRect2 = new Rectangle(0,0,0,0);
			initedRects = true;
		}

		/* (non-Javadoc)
		 * @see com.nokia.sdt.datamodel.adapter.IVisualAppearance#draw(com.nokia.sdt.utils.drawing.GC, com.nokia.sdt.displaymodel.ILookAndFeel)
		 */
		public void draw(GC gc, ILookAndFeel laf) {
			calculateRects(false, laf);
			drawCBA(gc, laf);
			drawFillAndBorder(gc, laf);
			drawPrompts(gc, laf);
		}
		
		private ILayoutObject getLayoutObject() {
			return Utilities.getLayoutObject(getEObject());
		}
		
		private void drawCBA(GC gc, ILookAndFeel laf) {
			String leftText = "Ok";
			String rightText = "Cancel";
			Rectangle r = laf.getRectangle("control.pane.bounds");
			Rectangle rect = new Rectangle(r.x, r.y, r.width, r.height);
			Rectangle bounds = getLayoutObject().getBounds();
			int x = rect.x - bounds.x;
			int y = rect.y - bounds.y;
			int width = rect.width;
			int height = rect.height;

			Color backColor = laf.getColor("control.pane.background");
			gc.setBackground(backColor);

			IFont font = laf.getFont("control.pane.font");
			gc.setFont(font);
			gc.setForeground(laf.getColor("control.pane.text"));
			
			int margin = laf.getInteger("control.pane.text.margin", 5);
			Point extent = font.stringExtent(leftText + "/" + rightText);

			if (laf.getBoolean("is.portrait", true)) {
				int fontOffset = (height - extent.y) / 2;
				rect = new Rectangle(x + margin, y + fontOffset, width - (2*margin), height - fontOffset);
				gc.fillRectangle(x, y, width, height);
				gc.drawFormattedString(leftText, rect, IFont.ALIGN_LEFT, 0);
				gc.drawFormattedString(rightText, rect, IFont.ALIGN_RIGHT, 0);
			}
			else {
				Rectangle sbar1Bounds = laf.getRectangle("status.bar1.bounds");
				Rectangle sbar2Bounds = laf.getRectangle("status.bar2.bounds");
				int fontOffset = (sbar2Bounds.height - extent.y) / 2;
				
				rect = new Rectangle(x, y + fontOffset, width-margin, height-fontOffset);
				gc.fillRectangle(x, y + sbar1Bounds.y, width, sbar1Bounds.height);
				gc.drawFormattedString(rightText, rect, IFont.ALIGN_RIGHT, 0);
				
				rect.y = y + height - sbar2Bounds.height + fontOffset;
				rect.height = sbar2Bounds.height - fontOffset;
				gc.fillRectangle(x, y + sbar2Bounds.y, width, sbar2Bounds.height);
				gc.drawFormattedString(leftText, rect, IFont.ALIGN_RIGHT, 0);
			}
		}
		
		private void drawPrompts(GC gc, ILookAndFeel laf) {
			gc.setForeground(laf.getColor("EEikColorDialogText"));
			String text = getScalablePromptText(TEXT_PROPERTY, laf, promptRect.width);
			IFont font = getLabelFont(TEXT_PROPERTY, laf);
			gc.setFont(font);
			int lineGap = laf.getInteger("note.text.lineGap", 0);
			Rectangle r;
			r = promptRect;
			gc.drawFormattedString(text, r, getPromptFontFlags(), lineGap);
			if (isMulti) {
				text = getScalablePromptText(TEXT2_PROPERTY, laf, promptRect2.width);				
				font = getLabelFont(TEXT2_PROPERTY, laf);
				gc.setFont(font);
				r = promptRect2;
				gc.drawFormattedString(text, promptRect2, getPromptFontFlags(), lineGap);
			}
		}
		
		private String getScalablePromptText(String propertyName, ILookAndFeel laf, int maxWidth) {
			String t = (String) propertySource.getPropertyValue(propertyName);
			IFont font = getLabelFont(propertyName, laf);
			return ScalableText.chooseScalableText(t, font, maxWidth);
		}
		
		private int getPromptFontFlags() {
			return IFont.ALIGN_LEFT | IFont.WRAPPING_ENABLED | IFont.OVERFLOW_ELLIPSIS;
		}
		
		private Point calculateWrappedTextSize(String string, IFont font, int width, int lineGap, int maxLines) {
			String[] lines = TextRendering.formatIntoLines(font, string, width, getPromptFontFlags(), maxLines);
			int maxWidth = 0;
			int gappedLineHeight = font.getHeight() + lineGap;
			
			for (int i = 0; i < lines.length; i++) {
				Point extent = font.stringExtent(lines[i]);
				maxWidth = Math.max(maxWidth, extent.x);
			}

			return new Point(maxWidth, lines.length * gappedLineHeight);
		}

		private void drawFillAndBorder(GC gc, ILookAndFeel laf) {
			Color color = laf.getColor("EEikColorWindowBackground");
			Rectangle borderRect = VisualUtils.shrinkRect(popupRect, 0, 0, 3, 3);
			if (color != null) {
				gc.setBackground(color);
				gc.fillRectangle(borderRect);
			}
			gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_BLACK));
			gc.drawRectangle(borderRect);
			int l = borderRect.x;
			int r = l + borderRect.width;
			int t = borderRect.y;
			int b = t + borderRect.height;
			gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW));
			gc.drawLine(r + 1, t + 1, r + 1, b + 1);
			gc.drawLine(l + 1, b + 1, r + 1, b + 1);
			gc.setForeground(gc.getDevice().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
			gc.drawLine(r + 2, t + 2, r + 2, b + 2);
			gc.drawLine(l + 2, b + 2, r + 2, b + 2);
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
			calculateRects(true, laf);
			getLayoutObject().setBounds(instanceRect);
			EObject[] children = componentInstance.getChildren();
			int numChildren = isMulti ? 2 : 1;
			if ((children == null) || (children.length != numChildren))
				return; // should not be laying out

			ILayoutObject child = Utilities.getLayoutObject(children[0]);
			child.setBounds(editRect);
			
			if (isMulti) {
				child = Utilities.getLayoutObject(children[1]);
				child.setBounds(editRect2);
			}
		}

		public IFont getLabelFont(String propertyPath, ILookAndFeel laf) {
			return laf.getFont("message.font");
		}

		public String[] getPropertyPaths() {
			if (propertyPaths == null) {
				if (isMulti)
					propertyPaths = new String[] { TEXT_PROPERTY, TEXT2_PROPERTY };
				else
					propertyPaths = new String[] { TEXT_PROPERTY };
			}
			return propertyPaths;
		}

		public Rectangle getVisualBounds(String propertyPath, ILookAndFeel laf) {
			calculateRects(false, laf);
			if (propertyPath.equals(TEXT_PROPERTY))
				return promptRect;
			else if (propertyPath.equals(TEXT2_PROPERTY))
				return promptRect2;
			
			return null;
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
		return new VisualAdapter(componentInstance);
	}

}
