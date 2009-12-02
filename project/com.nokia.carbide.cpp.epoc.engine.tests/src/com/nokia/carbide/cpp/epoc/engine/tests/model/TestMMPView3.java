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

import com.nokia.carbide.cdt.builder.DefaultMMPViewConfiguration;
import com.nokia.carbide.cdt.builder.DefaultViewParserConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefineFactory;
import com.nokia.carbide.cpp.internal.api.sdk.SymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianBuildContext;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.carbide.cpp.sdk.core.SymbianSDKFactory;
import com.nokia.cpp.internal.api.utils.core.HostOS;

import org.eclipse.core.runtime.*;
import org.osgi.framework.Version;

import java.util.ArrayList;
import java.util.List;

/**
 * Test MMP view manipulation
 *
 */
public class TestMMPView3 extends BaseMMPViewTest {
	ISymbianSDK sdk;
	ISymbianBuildContext winscwContext;
	ISymbianBuildContext armv5Context;
	ISymbianBuildContext armiContext;
	ISymbianBuildContext gcceContext;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		List<ISymbianBuildContext> buildContexts = new ArrayList<ISymbianBuildContext>();

		// we need to have a fixed idea of the SDK for these tests (even when workspace isn't running)
		sdk = null;
		if (!Platform.isRunning()) {
			sdk = SymbianSDKFactory.createInstance("S60_3rd", "c:\\symbian\\9.1\\S60_3rd", "com.nokia.s60", 
					new Version(9,0,0), null, null, false);
			// add to memory for standalone test, hopefully only affecting my system
			SDKCorePlugin.getSDKManager().addSDK(sdk);
		} else {
			// only look for existing sdk here
			for (ISymbianSDK anSdk : SDKCorePlugin.getSDKManager().getSDKList()) {
				if (anSdk.getOSVersion() != null && anSdk.getOSVersion().compareTo(new Version(9, 0, 0)) >= 0) {
					sdk = anSdk;
					break;
				}
			}
		}
		if (sdk != null) {
			winscwContext = new SymbianBuildContext(sdk, "WINSCW", "UDEB");
			armv5Context = new SymbianBuildContext(sdk, "ARMV5", "UDEB");
			armiContext = new SymbianBuildContext(sdk, "ARMI", "UREL");
			gcceContext = new SymbianBuildContext(sdk, "GCCE", "UREL");
			buildContexts.add(winscwContext);
			buildContexts.add(armv5Context);
			buildContexts.add(armiContext);
			buildContexts.add(gcceContext);
		}
	}
	
	private void assertDefFile(String mmpText, ISymbianBuildContext context, String projPathStr) {
		makeModel(mmpText);
		
		DefaultMMPViewConfiguration viewConfiguration = new DefaultMMPViewConfiguration(context, projectPath.append("group/bld.inf"), new AcceptedNodesViewFilter());

		if (context == winscwContext) {
			viewConfiguration.getExtraMacros().add(DefineFactory.createSimpleFreeformDefine("WINS"));
		} else if (context == armiContext) {
			viewConfiguration.getExtraMacros().add(DefineFactory.createSimpleFreeformDefine("GCC32"));
		}
		
		((DefaultViewParserConfiguration)viewConfiguration.getViewParserConfiguration()).setProjectPath(projectPath);
		
		IMMPView mmpView = getView(viewConfiguration);
		
		IPath realPath = mmpView.getDefFile();
		
		if (realPath == null) {
			if (projPathStr == null)
				return;
			assertEquals(projPathStr, realPath);
		} else {
			if (projPathStr != null) {
				//if (defFile == null || !(defFile.startsWith("/") || defFile.startsWith("\\")))
				//	realPath = mmpViewPathHelper.convertMMPToProject(EMMPPathContext.DEFFILE, realPath);
				
				if(realPath == null || !realPath.toPortableString().equalsIgnoreCase(projPathStr))
					assertEquals(projPathStr, realPath);
			} else {
				assertEquals(projPathStr, realPath);
			}
		}
		
		// if we got here, now try converting back 
		boolean isFixedDirectory = false;
		String origDefFile = mmpView.getSingleArgumentSettings().get(EMMPStatement.DEFFILE);
		if (origDefFile != null && new Path(HostOS.convertPathToUnix(origDefFile)).segmentCount() > 1)
			isFixedDirectory = true;
		mmpView.setDefFile(realPath, isFixedDirectory);
		IPath convPath = mmpView.getDefFile();
		if (convPath == null && realPath == null) {
			//
		} else if (convPath != null && realPath != null) {
			if (!convPath.toOSString().equalsIgnoreCase(realPath.toOSString())) {
				assertEquals(realPath, convPath);
			}
		} else {
			assertEquals(realPath, convPath);
		}
		
		/*
		if (realPath.toString().indexOf("unnamed") < 0 
				&& !(defFile != null && defFile.indexOf("~") >= 0)) {
			String reText = mmpViewPathHelper.convertPhysicalFileToMMPDefFile(realPath);
			if (reText == null) {
				if (defFile != null)
					assertEquals(defFile, reText);
			} else {
				if (defFile == null || new Path(defFile).hasTrailingSeparator()) {
					// see if based on target file
					String targetFile = mmpView.getSingleArgumentSettings().get(EMMPStatement.TARGET);
					String expBaseName = new Path(targetFile).removeFileExtension().toString() + ".def";
					if (!expBaseName.equalsIgnoreCase(reText))
						assertEquals(expBaseName, reText);
					//assertEquals(defFile, reText);
				} else {
					// we allow adding .def automatically
					if (new Path(defFile).getFileExtension() == null)
						defFile += ".def";
					if (!defFile.equalsIgnoreCase(reText)) {
						assertEquals(defFile, reText);
					}
				}
			}
		}*/
	}
	
	public void testEmpty() throws Exception {
		if (sdk == null)
			return;

		/// none at all
		String text;
		
		text = "";
		assertDefFile(text, winscwContext, null);
	}
	
	public void testDefaultFilename() throws Exception {
		if (sdk == null)
			return;

		// default filename
		String text = "TARGETTYPE DlL";

		assertDefFile(text, winscwContext, "bwins/unnamedU.def");
		assertDefFile(text, armv5Context, "eabi/unnamedU.def");
		assertDefFile(text, armiContext, "bmarm/unnamedU.def");
		assertDefFile(text, gcceContext, "eabi/unnamedU.def");

		// target-based filename
		text = "TARGET foo.dll\n"+
			"TARGETTYPE EXEdll\n";
		assertDefFile(text, winscwContext, "bwins/fooU.def");
		assertDefFile(text, armv5Context, "eabi/fooU.def");
		assertDefFile(text, armiContext, "bmarm/fooU.def");
		assertDefFile(text, gcceContext, "eabi/fooU.def");

		// default filename, no U
		text = "NOSTRICTDEF\n"+
			"TARGETTYPE DlL";

		assertDefFile(text, winscwContext, "bwins/unnamed.def");
		assertDefFile(text, armv5Context, "eabi/unnamed.def");
		assertDefFile(text, armiContext, "bmarm/unnamed.def");
		assertDefFile(text, gcceContext, "eabi/unnamed.def");

		// target-based filename, no U
		text = "NOSTRICTDEF\n"+
			"TARGET foo.dll\n"+
			"TARGETTYPE EXEdll\n";
		assertDefFile(text, winscwContext, "bwins/foo.def");
		assertDefFile(text, armv5Context, "eabi/foo.def");
		assertDefFile(text, armiContext, "bmarm/foo.def");
		assertDefFile(text, gcceContext, "eabi/foo.def");
	}

	public void testRealFilename() throws Exception {
		if (sdk == null)
			return;

		
		// directory of MMP by default, and the target platform goes ../<plat>/filename, e.g., project level
		
		// real filename, no U
		String text = "TARGETTYPE DlL\n"+
			"DEFFILE myfile.def";

		assertDefFile(text, winscwContext, "bwins/myfileU.def");
		assertDefFile(text, armv5Context, "eabi/myfileU.def");
		assertDefFile(text, armiContext, "bmarm/myfileU.def");
		assertDefFile(text, gcceContext, "eabi/myfileU.def");

		// target-based filename, no U
		text = "NOSTRICTDEF\n"+
			"TARGET foo.dll\n"+
			"TARGETTYPE EXEdll\n"+
			"DEFFILE myfile";
		assertDefFile(text, winscwContext, "bwins/myfile.def");
		assertDefFile(text, armv5Context, "eabi/myfile.def");
		assertDefFile(text, armiContext, "bmarm/myfile.def");
		assertDefFile(text, gcceContext, "eabi/myfile.def");

	}

	public void testRealFilenameAndDirectory() throws Exception {
		if (sdk == null)
			return;

		
		// real filename, no U
		String text = "TARGETTYPE DlL\n"+
			"DEFFILE ..\\myfile.def";

		assertDefFile(text, winscwContext, "myfileU.def");
		assertDefFile(text, armv5Context, "myfileU.def");
		assertDefFile(text, armiContext, "myfileU.def");
		assertDefFile(text, gcceContext, "myfileU.def");

		text = "NOSTRICTDEF\n"+
			"DEFFILE ..\\myfile";
		assertDefFile(text, winscwContext, "myfile.def");
		assertDefFile(text, armv5Context, "myfile.def");
		assertDefFile(text, armiContext, "myfile.def");
		assertDefFile(text, gcceContext, "myfile.def");

	}

	public void testDirectory() throws Exception {
		if (sdk == null)
			return;

		
		// this fixes the directory meaning platform is not supplied
		String text = "TARGETTYPE DlL\n"+
		"TARGET myfile.dll\n"+
			"DEFFILE deffiles\\ ";

		assertDefFile(text, winscwContext, "group/deffiles/myfileU.def");
		assertDefFile(text, armv5Context, "group/deffiles/myfileU.def");
		assertDefFile(text, armiContext, "group/deffiles/myfileU.def");
		assertDefFile(text, gcceContext, "group/deffiles/myfileU.def");

		// this fixes the directory meaning platform is not supplied
		text = "TARGETTYPE DlL\n"+
			"TARGET myfile.dll\n"+
			"#ifdef WINS\n"+
			"DEFFILE deffiles\\winscw\\ \n"+
			"#elif defined(GCC32)\n" +
			"DEFFILE deffiles\\bmarm\\ \n"+
			"#else\n"+
			"DEFFILE deffiles\\eabi\\ \n"+
			"#endif\n";
		assertDefFile(text, winscwContext, "group/deffiles/winscw/myfileU.def");
		assertDefFile(text, armv5Context, "group/deffiles/eabi/myfileU.def");
		assertDefFile(text, armiContext, "group/deffiles/bmarm/myfileU.def");
		assertDefFile(text, gcceContext, "group/deffiles/eabi/myfileU.def");

	}

	public void testTildeFilename() throws Exception {
		if (sdk == null)
			return;

		String text = "TARGETTYPE DlL\n"+
			"TARGET myfile.dll\n"+
			"DEFFILE \\~\\release\\deffiles\\ ";

		assertDefFile(text, winscwContext, "/bwins/release/deffiles/myfileU.def");
		assertDefFile(text, armv5Context, "/eabi/release/deffiles/myfileU.def");
		assertDefFile(text, armiContext, "/bmarm/release/deffiles/myfileU.def");
		assertDefFile(text, gcceContext, "/eabi/release/deffiles/myfileU.def");

		text = "TARGETTYPE DlL\n"+
		"TARGET myfile.dll\n"+
			"DEFFILE ..\\places\\~\\ ";

		assertDefFile(text, winscwContext, "places/bwins/myfileU.def");
		assertDefFile(text, armv5Context, "places/eabi/myfileU.def");
		assertDefFile(text, armiContext, "places/bmarm/myfileU.def");
		assertDefFile(text, gcceContext, "places/eabi/myfileU.def");

		text = "TARGETTYPE DlL\n"+
			"TARGET myfile.dll\n"+
			"DEFFILE .\\~\\file.def ";

		assertDefFile(text, winscwContext, "group/bwins/fileU.def");
		assertDefFile(text, armv5Context, "group/eabi/fileU.def");
		assertDefFile(text, armiContext, "group/bmarm/fileU.def");
		assertDefFile(text, gcceContext, "group/eabi/fileU.def");

		///
		
		text = "TARGETTYPE DlL\n"+
		"TARGET myfile.dll\n"+
		"NOSTRICTDEF\n"+
		"DEFFILE \\~\\release\\deffiles\\ ";

		assertDefFile(text, winscwContext, "/bwins/release/deffiles/myfile.def");
		assertDefFile(text, armv5Context, "/eabi/release/deffiles/myfile.def");
		assertDefFile(text, armiContext, "/bmarm/release/deffiles/myfile.def");
		assertDefFile(text, gcceContext, "/eabi/release/deffiles/myfile.def");
	
		text = "TARGETTYPE DlL\n"+
		"TARGET myfile.dll\n"+
		"NOSTRICTDEF\n"+
			"DEFFILE ..\\places\\~\\ ";
	
		assertDefFile(text, winscwContext, "places/bwins/myfile.def");
		assertDefFile(text, armv5Context, "places/eabi/myfile.def");
		assertDefFile(text, armiContext, "places/bmarm/myfile.def");
		assertDefFile(text, gcceContext, "places/eabi/myfile.def");
	
		text = "TARGETTYPE DlL\n"+
			"TARGET myfile.dll\n"+
			"NOSTRICTDEF\n"+
			"DEFFILE .\\~\\file.def ";
	
		assertDefFile(text, winscwContext, "group/bwins/file.def");
		assertDefFile(text, armv5Context, "group/eabi/file.def");
		assertDefFile(text, armiContext, "group/bmarm/file.def");
		assertDefFile(text, gcceContext, "group/eabi/file.def");

	}
	
	public void testDefFileAPIs() {
		if (sdk == null)
			return;

		String text = "DEFFILE first.def\n"+
		"DEFFILE .\\second.def\n";
		makeModel(text);
		IMMPView view = getView(mmpConfig);
		assertEquals("first.def", view.getSingleArgumentSettings().get(EMMPStatement.DEFFILE));
		assertEquals(new Path("BWINS/firstU.def"), view.getDefFile());
		commitTest(view, text);
		
		////
		
		view.setDefFile(new Path("BWINS/fooU.def"), false);
		assertEquals("foo.def", view.getSingleArgumentSettings().get(EMMPStatement.DEFFILE));
		assertEquals(new Path("BWINS/fooU.def"), view.getDefFile());

		String text2 = "DEFFILE foo.def\n"+
		"DEFFILE .\\second.def\n";

		commitTest(view, text2);
		
		////
		
		view.getSingleArgumentSettings().remove(EMMPStatement.DEFFILE);
		
		String text3 = 
		"DEFFILE .\\second.def\n";

		commitTest(view, text3);
		
		/// 
		
		assertEquals(new Path("group/secondU.def"), view.getDefFile());
		
		view.getFlags().add(EMMPStatement.NOSTRICTDEF);
		assertEquals(new Path("group/second.def"), view.getDefFile());

		String text4 = 
			"DEFFILE .\\second.def\n"+
			"\n"+
			"NOSTRICTDEF\n";

		commitTest(view, text4);
		
		///
		
		view.setDefFile(new Path("group/funny.def"), true);
		assertEquals(".\\funny.def", view.getSingleArgumentSettings().get(EMMPStatement.DEFFILE));
		assertEquals(new Path("group/funny.def"), view.getDefFile());

		String text5 = 
			"DEFFILE .\\funny.def\n"+
			"\n"+
			"NOSTRICTDEF\n";

		commitTest(view, text5);
		
		///
		
		view.getFlags().remove(EMMPStatement.NOSTRICTDEF);
		assertEquals(".\\funny.def", view.getSingleArgumentSettings().get(EMMPStatement.DEFFILE));
		assertEquals(new Path("group/funnyU.def"), view.getDefFile());

	}
	
	public void testDefFileAPIs2() {
		if (sdk == null)
			return;

		String text = "DEFFILE first.def\n"+
		"DEFFILE .\\second.def\n";
		makeModel(text);
		IMMPView view = getView(mmpConfig);
		assertEquals("first.def", view.getSingleArgumentSettings().get(EMMPStatement.DEFFILE));

		view.getSingleArgumentSettings().put(EMMPStatement.DEFFILE, ".\\..\\foo.def");
		view.getFlags().add(EMMPStatement.NOSTRICTDEF);
		assertEquals(new Path("foo.def"), view.getDefFile());

		String text2 = 
			"DEFFILE .\\..\\foo.def\n"+
			"DEFFILE .\\second.def\n"+
			"\n"+
			"NOSTRICTDEF\n";

		commitTest(view, text2);

	}
	

}
