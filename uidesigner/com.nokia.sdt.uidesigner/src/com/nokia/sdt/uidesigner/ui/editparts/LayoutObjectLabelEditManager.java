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


package com.nokia.sdt.uidesigner.ui.editparts;

import com.nokia.sdt.datamodel.adapter.IDirectLabelEdit;
import com.nokia.sdt.datamodel.util.ModelUtils;
import com.nokia.sdt.displaymodel.ILookAndFeel;
import com.nokia.sdt.editor.IDesignerEditor;
import com.nokia.sdt.uidesigner.ui.utils.Adapters;
import com.nokia.sdt.utils.drawing.IFont;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

/**
 * 
 *
 */
public class LayoutObjectLabelEditManager extends DirectLabelEditManager {
	private String propertyPath;
	private IDirectLabelEdit directLabelEdit;
	private LayoutObjectCellEditorLocator layoutObjectCellEditorLocator;
	
	public LayoutObjectLabelEditManager(GraphicalEditPart source, Class editorType, CellEditorLocator locator,
																String propertyPath, IDesignerEditor editor) {
		super(source, editorType, locator, editor);
		this.propertyPath = propertyPath;
		this.layoutObjectCellEditorLocator = (LayoutObjectCellEditorLocator) locator;
		this.directLabelEdit = Adapters.getDirectLabelEdit((EObject) getEditPart().getModel()); 
	}

	/**
	 * @see org.eclipse.gef.tools.DirectEditManager#bringDown()
	 */
	protected void bringDown() {
		//This method might be re-entered when super.bringDown() is called.
		Font disposeFont = font;
		font = null;

		super.bringDown();
		
		if (disposeFont != null)
			disposeFont.dispose();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.tools.DirectEditManager#initCellEditor()
	 */
	protected void initCellEditor() {
		TextCellEditor editor = (TextCellEditor) getCellEditor();
		LayoutObjectEditPart editPart = (LayoutObjectEditPart) getEditPart();
		EObject object = (EObject) editPart.getModel();
		getDirectEditRequest().setDirectEditFeature(propertyPath);

		if (initialKeyEvent == null) {
			Object value = ModelUtils.getEditablePropertyValueFromPath(object, propertyPath);
			editor.setValue(value);
		}
		else {
			Text text = (Text) editor.getControl(); /* @see DirectLabelEditManager.createCellEditorOn */
			text.setText("" + initialKeyEvent.character); //$NON-NLS-1$
		}

		Figure figure = (Figure) editPart.getFigure();
		IEditPartEditorProvider editorProvider = (IEditPartEditorProvider) getEditPart();
		ILookAndFeel lookAndFeel = editorProvider.getEditor().getDisplayModel().getLookAndFeel();
		IFont ifont = directLabelEdit.getLabelFont(propertyPath, lookAndFeel);

        // get the scaling factor 
        Dimension pointSize = new Dimension(100, 100);
        figure.translateToAbsolute(pointSize);
        double scale = pointSize.width / 100.;
        
        if (font != null) {
            font.dispose();
            font = null;
        }
        // try to get the SWT version of the font used in the label
        float size;
        int scaledSize;
        if (ifont != null) {
            size = ifont.getSize();
            scaledSize = (int) (size * scale);
            
            // make absolute size reasonable for display
            if (scaledSize < 10) {
                scaledSize = 10;
                size = (float) (scaledSize / scale);
            }
            else if (scaledSize > 96) {
                scaledSize = 96;
                size = (float) (scaledSize / scale);
            }
            
            font = ifont.getSWTFont(Display.getDefault(), scaledSize);
        } else {
            size = 12;
            scaledSize = (int)(size * scale);
            font = new Font(Display.getDefault(), "Arial Unicode MS", scaledSize, 0); //$NON-NLS-1$
        }
        
        // need to make a GC for the display to get at the font metrics
		GC gc = new GC(Display.getDefault());
		gc.setFont(font);
		FontMetrics fontMetrics = gc.getFontMetrics();
		gc.dispose();
        
        // ensure the box is big enough to see text, but not too big
		String value = getCellEditor().getValue() != null ?  getCellEditor().getValue().toString() : ""; //$NON-NLS-1$
        // get enough width to add some text
		int minWidth = 128;
		if (value != null)
			minWidth = (int) (value.length() * (fontMetrics.getAverageCharWidth() + 5) / scale);
        if (minWidth >= 128)
            minWidth = (int) (128);
        // get enough height to actually show the font
        int minHeight = (int) Math.ceil(fontMetrics.getHeight() / scale);
        if (minHeight < 16)
            minHeight = 16;
		layoutObjectCellEditorLocator.setMinDimension(new Dimension(minWidth, minHeight));
        
		Text text = (Text) editor.getControl();
		text.setFont(font);
		hookCellEditor();
	}

	protected String getPropertyPath() {
		return propertyPath;
	}

	protected EObject getInstance() {
		return directLabelEdit.getEObject();
	}
}
