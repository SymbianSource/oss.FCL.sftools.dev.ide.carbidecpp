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
package com.nokia.carbide.cpp.internal.project.ui.editors.images;

import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;

/**
 * The color/grayscale, color/mask depth, and "default" button block
 *
 */
public class AIFImageFormatControls extends ImageFormatControlsBase {

	private AIFEditorContext editorContext;
	private AIFEditorListPage page;

	/**
	 * @param selected
	 * @param style
	 * @param label 
	 */
	public AIFImageFormatControls(AIFEditorListPage page_, Composite group, int style) {
		super(group, style, false, false);
		this.editorContext = page_.getEditorContext();
		this.page = page_;

		colorDepthCombo.getCCombo().addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				int cd = colorDepthCombo.getDepth();
				final int oldDepth = editorContext.getMMPAIFInfo().getColorDepth(); 
				final int newDepth = cd > 0 ? cd : 0;
				final int oldMaskDepth = editorContext.getMMPAIFInfo().getMaskDepth();
				final int newMaskDepth = cd > 0 ? oldMaskDepth : 0;
				
				MultiImageEditorOperation operation = new MultiImageEditorOperation(
						Messages.getString("AIFImageFormatControls.SetColorDepthCommand"), editorContext) { //$NON-NLS-1$
					
					@Override
					protected boolean doesAnything() {
						return oldDepth != newDepth || oldMaskDepth != newMaskDepth;
					}

					@Override
					protected void redo() {
						editorContext.getMMPAIFInfo().setColorDepth(newDepth);
						editorContext.getMMPAIFInfo().setMaskDepth(newMaskDepth);
						page.refresh();
						page.refreshTable();
					}

					@Override
					protected void undo() {
						editorContext.getMMPAIFInfo().setColorDepth(oldDepth);
						editorContext.getMMPAIFInfo().setMaskDepth(oldMaskDepth);
						page.refresh();
						page.refreshTable();
					}
					
				};
				editorContext.pushAndExecute(operation);
			}
			
		});

		maskDepthCombo.getCCombo().addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				int md = maskDepthCombo.getDepth();
				final int oldDepth = editorContext.getMMPAIFInfo().getColorDepth(); 
				final int newDepth = md > 0 && oldDepth == 0 ? 8 : oldDepth;
				final int oldMaskDepth = editorContext.getMMPAIFInfo().getMaskDepth();
				final int newMaskDepth = md > 0 ? md : 0;
				
				MultiImageEditorOperation operation = new MultiImageEditorOperation(
						Messages.getString("AIFImageFormatControls.SetMaskDepthCommand"), editorContext) { //$NON-NLS-1$

					
					@Override
					protected boolean doesAnything() {
						return oldMaskDepth != newMaskDepth || oldDepth != newDepth;
					}

					@Override
					protected void redo() {
						editorContext.getMMPAIFInfo().setColorDepth(newDepth);
						editorContext.getMMPAIFInfo().setMaskDepth(newMaskDepth);
						page.refresh();
						page.refreshTable();
					}

					@Override
					protected void undo() {
						editorContext.getMMPAIFInfo().setColorDepth(oldDepth);
						editorContext.getMMPAIFInfo().setMaskDepth(oldMaskDepth);
						page.refresh();
						page.refreshTable();
					}
					
				};
				editorContext.pushAndExecute(operation);
			}
			
		});

		colorButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				updateColorFlags(editorContext, true);
				updateColorDepths(true, false);
				
			}
			
		});
		
		greyButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				updateColorFlags(editorContext, false);
				updateColorDepths(false, true);
			}
			
		});
		
	}

	private void updateColorFlags(final AIFEditorContext editorContext, final boolean isColor) {
		MultiImageEditorOperation operation = new MultiImageEditorOperation(
				Messages.getString("AIFImageFormatControls.SetColorFlagCommand"), editorContext) { //$NON-NLS-1$

			boolean oldFlag = editorContext.getMMPAIFInfo().isColor();
			boolean newFlag = isColor;
			
			@Override
			protected boolean doesAnything() {
				return oldFlag != newFlag;
			}

			@Override
			protected void redo() {
				editorContext.getMMPAIFInfo().setColor(newFlag);
				page.refresh();
				page.refreshTable();
			}

			@Override
			protected void undo() {
				editorContext.getMMPAIFInfo().setColor(oldFlag);
				page.refresh();
				page.refreshTable();
			}
			
		};
		editorContext.pushAndExecute(operation);
	}
	
	public void refresh() {
		IMMPAIFInfo info = editorContext.getMMPAIFInfo();
		
		colorDepthCombo.setDepth(info.getColorDepth());
		maskDepthCombo.setDepth(info.getMaskDepth());
		
		colorButton.setSelection(info.isColor());
		greyButton.setSelection(!info.isColor());
		
		updateColorDepths(info.isColor(), !info.isColor());
	}
	

}
