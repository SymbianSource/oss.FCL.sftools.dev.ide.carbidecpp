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

package com.nokia.carbide.cpp.epoc.engine.tests.model;

import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IBldInfView;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IExport;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakMakeReference;

import org.eclipse.core.runtime.Path;


public class TestBldInfView3 extends BaseBldInfViewTest {
	
	public void testRename1() {
		String text= "PRJ_MMPFILES\n"+
		"first.mmp\n"+
		"#ifndef MACRO\n"+
		"second.mmp\n"+
		"#endif\n"+
		"third.mmp\n"+
		"\n"+
		"PRJ_TESTMMPFILES\n"+
		"unrelated\n"+
		"related\n"+
		"PRJ_EXPORTS\n"+
		"same\n"+
		"PRJ_MMPFILES\n"+
		"fourth.mmp\n"+
		"fifth.mmp\n"+
		"";
		
		makeModel(text);
		IBldInfView view = getView(config);
		
		IMakMakeReference fourth = view.getMakMakeReferences().get(3);
		fourth.setPath(new Path("group/fourth_renamed.mmp"));
		
		commitTest(view, 
				"PRJ_MMPFILES\n"+
				"first.mmp\n"+
				"#ifndef MACRO\n"+
				"second.mmp\n"+
				"#endif\n"+
				"third.mmp\n"+
				"\n"+
				"PRJ_TESTMMPFILES\n"+
				"unrelated\n"+
				"related\n"+
				"PRJ_EXPORTS\n"+
				"same\n"+
				"PRJ_MMPFILES\n"+
				"fourth_renamed.mmp\n"+
				"fifth.mmp\n"+
				"");
	}

	public void testRename2() {
		String text= "PRJ_MMPFILES\n"+
		"first.mmp\n"+
		"#ifndef MACRO\n"+
		"second.mmp\n"+
		"#endif\n"+
		"third.mmp\n"+
		"\n"+
		"PRJ_TESTMMPFILES\n"+
		"unrelated\n"+
		"related\n"+
		"PRJ_EXPORTS\n"+
		"same\n"+
		"PRJ_MMPFILES\n"+
		"fourth.mmp\n"+
		"fifth.mmp\n"+
		"";
		
		makeModel(text);
		IBldInfView view = getView(config);
		
		IExport export = view.getExports().get(0);
		export.setSourcePath(new Path("group/exported"));
		
		commitTest(view, 
				"PRJ_MMPFILES\n"+
				"first.mmp\n"+
				"#ifndef MACRO\n"+
				"second.mmp\n"+
				"#endif\n"+
				"third.mmp\n"+
				"\n"+
				"PRJ_TESTMMPFILES\n"+
				"unrelated\n"+
				"related\n"+
				"PRJ_EXPORTS\n"+
				"exported same\n"+
				"PRJ_MMPFILES\n"+
				"fourth.mmp\n"+
				"fifth.mmp\n"+
				""
				);
	}
	
	public void testModifyInclude() {
		String text = "" +
				"PRJ_PLATFORMS\n"+
				"DEFAULT\n"+
				"\n"+
				"#include \"other.inf\"\n"+
				"\n"+
				"";
		String other = "" +
				"PRJ_PLATFORMS\n"+
				"DEFAULT\n"+
				"\n"+
				"PRJ_MMPFILES\n"+
				"Test.mmp\n"+
				"";
		
		String inclFile = projectPath.append("group/other.inf").toOSString();
		parserConfig.getFilesystem().put(inclFile, other);

		makeModel(text);
		IBldInfView view = getView(config);
		
		IMakMakeReference ref = view.createMMPReference();
		ref.setPath(new Path("group/Test.mmp"));
		ref.setTidy(true);
		view.getMakMakeReferences().set(0, ref);
		
		commitTest(view, text);
		
		String other2 = "" +
		"PRJ_PLATFORMS\n"+
		"DEFAULT\n"+
		"\n"+
		"PRJ_MMPFILES\n"+
		"Test.mmp TIDY\n"+
		"";

		assertEquals(other2, parserConfig.getFilesystem().get(inclFile));
		
		//////
		
		view = getView(config);
		
		ref = view.createMMPReference();
		ref.setPath(new Path("group/Test.mmp"));
		ref.setTidy(true);
		ref.setBuildAsArm(true);
		view.getMakMakeReferences().set(0, ref);
		
		commitTest(view, text);
		
		String other3 = "" +
		"PRJ_PLATFORMS\n"+
		"DEFAULT\n"+
		"\n"+
		"PRJ_MMPFILES\n"+
		"Test.mmp TIDY BUILD_AS_ARM\n"+
		"";

		assertEquals(other3, parserConfig.getFilesystem().get(inclFile));

	}

	public void testParsingWithBrokenMacros() {
		
		// ensure that the spaces below in the unresolved macro don't trip up the parser
		String text = "\r\n" + 
				"PRJ_PLATFORMS\r\n" + 
				"DEFAULT\r\n" + 
				"\r\n" + 
				"PRJ_EXPORTS\r\n" + 
				"..\\inc\\TSEngInterface.h APP_LAYER_DOMAIN_EXPORT_PATH( SecondaryDisplay\\TSEngInterface.h )\r\n" + 
				"\r\n" + 
				"..\\data\\telephonyserviceengine_stub.SIS\\\r\n" + 
				"    \\epoc32\\data\\z\\system\\install\\telephonyserviceengine_stub.SIS\r\n" + 
				"\r\n" + 
				"PRJ_MMPFILES\r\n" + 
				"telephonyserviceengine.mmp\r\n" + 
				"\r\n" + 
				"PRJ_TESTMMPFILES\r\n" + 
				"\r\n" + 
				"PRJ_TESTEXPORTS";

		makeModel(text);
		IBldInfView view = getView(config);

		assertEquals(2, view.getExports().size());
		assertEquals(1, view.getMakMakeReferences().size());
		assertEquals(0, view.getTestMakMakeReferences().size());

	}

	public void testCatenatedLineBug4840() {
		
		String text = "\r\n" +
				"#define APP_LAYER_DOMAIN_EXPORT_PATH(x) \\epoc32\\include\\ ## x\r\n"+
				"PRJ_PLATFORMS\r\n" + 
				"DEFAULT\r\n" + 
				"\r\n" + 
				"PRJ_EXPORTS\r\n" + 
				"..\\inc\\TSEngInterface.h APP_LAYER_DOMAIN_EXPORT_PATH( SecondaryDisplay\\TSEngInterface.h )\r\n" + 
				"\r\n" + 
				"..\\data\\telephonyserviceengine_stub.SIS\\\r\n" + 
				"    \\epoc32\\data\\z\\system\\install\\telephonyserviceengine_stub.SIS\r\n" + 
				"\r\n" + 
				"PRJ_MMPFILES\r\n" + 
				"telephonyserviceengine.mmp\r\n" + 
				"\r\n" + 
				"PRJ_TESTMMPFILES\r\n" + 
				"\r\n" + 
				"PRJ_TESTEXPORTS";

		makeModel(text);
		IBldInfView view = getView(config);

		assertEquals(2, view.getExports().size());
		assertEquals(1, view.getMakMakeReferences().size());
		assertEquals(0, view.getTestMakMakeReferences().size());

	}
	
}

