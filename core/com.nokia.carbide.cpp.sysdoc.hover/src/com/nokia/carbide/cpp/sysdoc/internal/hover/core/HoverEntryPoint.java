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
 *				Deniz TURAN
 * Description: 
 * 				
 */
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
 *				Deniz TURAN
 * Description: 
 * 				
 */
package com.nokia.carbide.cpp.sysdoc.internal.hover.core;

import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.cdt.ui.text.c.hover.ICEditorTextHover;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHoverExtension;
import org.eclipse.jface.text.ITextHoverExtension2;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;

/**
 * SysdocHover is the entry point where we provide an object representing the
 * information about the element being hovered over to the Eclipse framework.
 */
public class HoverEntryPoint implements ICEditorTextHover, ITextHoverExtension,
		ITextHoverExtension2 {
	private IEditorPart editor;
	private ASTHoverController astHoverAgent = new ASTHoverController();

	public void setEditor(IEditorPart editor) {
		this.editor = editor;
	}

	/*
	 * NOT used!. We getHoverInfo2
	 */
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		return null;
	}

	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		return new Region(offset, 1);
	}

	public Object getHoverInfo2(ITextViewer textViewer, IRegion hoverRegion) {
		IEditorInput editorInput = editor.getEditorInput();
		ITranslationUnit tu = CUIPlugin.getDefault().getWorkingCopyManager()
				.getWorkingCopy(editorInput);

		Object hoverInfo = astHoverAgent.getASTHoverInfo(tu, hoverRegion
				.getOffset(), hoverRegion.getLength());

		return hoverInfo;
	}

	public IInformationControlCreator getHoverControlCreator() {
		return new BrowserControlCreator();
	}
}