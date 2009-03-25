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

import com.nokia.carbide.cpp.epoc.engine.model.EGeneratedHeaderFlags;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

/**
 * Moved out from MultiImageEditorOutputFileParametersComposite to solve
 * javac bug.
 *
 */
class HeaderStateListener implements SelectionListener {
	/**
	 * 
	 */
	private final MultiImageEditorOutputFileParametersComposite outputFileParametersComposite;
	private EGeneratedHeaderFlags newFlags;

	public HeaderStateListener(MultiImageEditorOutputFileParametersComposite composite, EGeneratedHeaderFlags newFlags) {
		outputFileParametersComposite = composite;
		this.newFlags = newFlags;
	}
	public void widgetDefaultSelected(SelectionEvent e) {
	}

	public void widgetSelected(SelectionEvent e) {
		AbstractMultiImageEditorOperation operation = 
			new AbstractMultiImageEditorOperation<EGeneratedHeaderFlags>(
					outputFileParametersComposite.editorContext, Messages.getString("OutputFileParametersComposite.SetHeaderFileGenerationCommand"), //$NON-NLS-1$
					outputFileParametersComposite.multiImageSource.getHeaderFlags(),
					newFlags) {
				
			/* (non-Javadoc)
			 * @see com.nokia.carbide.cpp.internal.project.ui.editors.images.OutputFileParametersComposite.OutputFileParametersOperation#update(java.lang.Object)
			 */
			@Override
			protected void update(EGeneratedHeaderFlags value) {
				HeaderStateListener.this.outputFileParametersComposite.multiImageSource.setHeaderFlags(value);
				HeaderStateListener.this.outputFileParametersComposite.refresh();					
			}
		};
		
		outputFileParametersComposite.editorContext.pushAndExecute(operation);
	}
}
