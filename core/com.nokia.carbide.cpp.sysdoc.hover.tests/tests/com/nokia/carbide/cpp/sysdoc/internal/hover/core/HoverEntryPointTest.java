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

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.ui.IEditorPart;
import org.junit.Test;

import com.nokia.carbide.cpp.sysdoc.hover.BaseTest;
import com.nokia.carbide.cpp.sysdoc.hover.TestHelper;

public class HoverEntryPointTest extends BaseTest{

	@Test
	public void testConstructor(){
		HoverEntryPoint he= new HoverEntryPoint();
		assertNotNull(he);
	}	
	
	
	@Test
	public void testHoverEntryPoint() throws Exception{
		TestHelper.waitIndexingComplete(20000);
		HoverEntryPoint he= new HoverEntryPoint();
		IEditorPart editorPart = TestHelper.getIEditorPart();
		he.setEditor(editorPart);
		checkHoverObject( he, "LOCAL_D", 565, 7, false);
		checkHoverObject( he,"CConsoleBase", 573, 12,
				false);
		checkHoverObject( he,"New", 1235, 3, false);
		// not Symbian API
		checkHoverObject(he,null, 1, 34, true);
		checkHoverObject( he,null, 797, 4, true);
		checkHoverObject( he,null, 412, 17, true);
	}


	private void checkHoverObject(HoverEntryPoint he,String fqn, int i, int j, boolean isNull) throws Exception {
		IRegion hoverRegion= new Region(i, j);
		Object hoverInfo = he.getHoverInfo2(null, hoverRegion);			
		ASTHoverControllerTest.checkHoverObject(hoverInfo, fqn, isNull);
	}
}
