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

import com.nokia.carbide.cpp.epoc.engine.image.IBitmapSource;
import com.nokia.carbide.cpp.epoc.engine.image.IBitmapSourceReference;
import com.nokia.carbide.cpp.epoc.engine.model.EGeneratedHeaderFlags;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.*;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefineFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTopLevelNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPListArgumentStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ViewASTBase;
import com.nokia.cpp.internal.api.utils.core.HostOS;
import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.PathUtils;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Test MMP view manipulation
 *
 */
public class TestMMPView extends BaseMMPViewTest {

	public void testSourceParsing0() throws Exception {
		makeModel("SOURCE here.cpp\nSOURCEPATH ..\\src\nSOURCE foo.cpp bar.cpp\n");

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		List<IPath> sources = view.getSources();
		match(new String[] { "group/here.cpp", "src/foo.cpp", "src/bar.cpp" }, sources);
		
		IASTListNode<IASTTopLevelNode> nodes = ((ViewASTBase)view).getFilteredTranslationUnit().getNodes();
		IASTMMPListArgumentStatement src = (IASTMMPListArgumentStatement) nodes.get(2);
		assertNotNull(src.getSourcePathDependentContext());
		assertEquals(nodes.get(1), src.getSourcePathDependentContext().getSourcePathStatement());				
		view.dispose();
		model.dispose();
	}

	public void testSourceParsing1() throws Exception {
		// handle absolute paths
		makeModel("SOURCEPATH \\foo\\bar\nSOURCE foo.cpp bar.cpp\n");

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		_testSourceParsing1(view);
		_testSourceParsing1(view.getData());
		view.dispose();
		model.dispose();
	}

	private void _testSourceParsing1(IMMPData data) {
		List<IPath> sources = data.getSources();
		match(new String[] { "/foo/bar/foo.cpp", "/foo/bar/bar.cpp" }, sources);
	}
	
	public void testSourceChanging0() {
		makeModel("SOURCE a b c");

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		view.getSources().add(new Path("group/d"));

		// newline added to ensure file is sane
		commitTest(view, "SOURCE a b c d\n");
		view.dispose();
		model.dispose();
	}
	
	public void testSourceChanging0b() {
		makeModel("SOURCE a b //foo\n"+
				"SOURCE c d //bar\n");

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		commitTest(view, "SOURCE a b //foo\n"+"SOURCE c d //bar\n");
		view.dispose();
		model.dispose();
	}

	public void testSourceChanging1() throws Exception {
		makeModel("");

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		////
		view.getSources().add(new Path("src/foo.cpp"));
		
		match(new IPath[] { new Path("src") }, view.getEffectiveSourcePaths());
		// not committed
		match(new IPath[] { }, view.getRealSourcePaths());
		
		commitTest(view, "SOURCEPATH ../src\nSOURCE foo.cpp\n");
		match(new IPath[] { new Path("src") }, view.getEffectiveSourcePaths());
		match(new IPath[] { new Path("src") }, view.getRealSourcePaths());

		////
		
		view.getSources().remove(new Path("src/foo.cpp"));
		match(new IPath[] { }, view.getEffectiveSourcePaths());
		// not committed
		match(new IPath[] { new Path("src") }, view.getRealSourcePaths());

		commitTest(view, "");
		match(new IPath[] { }, view.getRealSourcePaths());

		////
		
		view.getSources().add(new Path("inc/test.inl"));
		match(new IPath[] { new Path("inc") }, view.getEffectiveSourcePaths());
		match(new IPath[] { }, view.getRealSourcePaths());
		view.revert();
		match(new IPath[] {  }, view.getEffectiveSourcePaths());
		match(new IPath[] { }, view.getRealSourcePaths());

		commitTest(view, "");
		
		view.dispose();
		model.dispose();
	}
	
	public void testSourceChanging2() throws Exception {
		makeModel("");

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		////
		view.getSources().add(new Path("src/foo.cpp"));
		view.getSources().add(new Path("src/bar.cpp"));
		view.getSources().add(new Path("src/baz.cpp"));
		match(new IPath[] { new Path("src") }, view.getEffectiveSourcePaths());
		match(new IPath[] { }, view.getRealSourcePaths());

		commitTest(view, "SOURCEPATH ../src\nSOURCE foo.cpp bar.cpp baz.cpp\n");
		match(new IPath[] { new Path("src") }, view.getEffectiveSourcePaths());
		match(new IPath[] { new Path("src") }, view.getRealSourcePaths());

		////
		
		view.getSources().remove(new Path("src/bar.cpp"));
		match(new IPath[] { new Path("src") }, view.getEffectiveSourcePaths());
		match(new IPath[] { new Path("src") }, view.getRealSourcePaths());

		commitTest(view, "SOURCEPATH ../src\nSOURCE foo.cpp baz.cpp\n");

		view.dispose();
		model.dispose();
	}

	public void testSourceChanging3() throws Exception {
		makeModel("");

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		////
		view.getSources().add(new Path("src/foo.cpp"));
		view.getSources().add(new Path("src/bar.cpp"));
		view.getSources().add(new Path("src/baz.cpp"));
		match(new IPath[] { new Path("src") }, view.getEffectiveSourcePaths());
		match(new IPath[] { }, view.getRealSourcePaths());

		commitTest(view, "SOURCEPATH ../src\nSOURCE foo.cpp bar.cpp baz.cpp\n");
		match(new IPath[] { new Path("src") }, view.getRealSourcePaths());
		
		////
		
		view.getSources().remove(new Path("src/foo.cpp"));
		view.getSources().remove(new Path("src/bar.cpp"));
		view.getSources().add(new Path("src/bar.cpp"));
		view.getSources().add(new Path("src/foo.cpp"));
		view.getSources().add(new Path("src/blab.cpp"));
		match(new IPath[] { new Path("src") }, view.getEffectiveSourcePaths());

		commitTest(view, "SOURCEPATH ../src\nSOURCE baz.cpp bar.cpp foo.cpp blab.cpp\n");
		match(new IPath[] { new Path("src") }, view.getRealSourcePaths());
		
		///
		view.getSources().add(new Path("newsrc/files/newfile.cpp"));
		match(new IPath[] { new Path("src"), new Path("newsrc/files") }, view.getEffectiveSourcePaths());
		commitTest(view, "SOURCEPATH ../src\nSOURCE baz.cpp bar.cpp foo.cpp blab.cpp\n"+
				"SOURCEPATH ../newsrc/files\nSOURCE newfile.cpp\n");
		match(new IPath[] { new Path("src"), new Path("newsrc/files") }, view.getRealSourcePaths());
		
		view.dispose();
		model.dispose();
	}

	public void testSourceChanging4() throws Exception {
		// handle absolute paths
		makeModel("");

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		view.getSources().add(new Path("/foo/bar/src/foo.cpp"));
		view.getSources().add(new Path("src/bar.cpp"));

		commitTest(view, "SOURCEPATH /foo/bar/src\nSOURCE foo.cpp\n"+
				"SOURCEPATH ../src\nSOURCE bar.cpp\n");

		match(new IPath[] { new Path("/foo/bar/src"), new Path("src") }, view.getRealSourcePaths());

		view.dispose();
		model.dispose();
	}
	
	public void testSourceChanging5() throws Exception {
		// handle preserving existing sourcepaths
		makeModel("SOURCEPATH ..\\ \n"+
				"SOURCE a.cpp sub\\b.cpp ..\\gfx\\c.cpp\n");

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		match(new IPath[] { new Path("") }, view.getRealSourcePaths());

		// don't change wonky user stuff
		commitTest(view, "SOURCEPATH ..\\ \n"+
				"SOURCE a.cpp sub\\b.cpp ..\\gfx\\c.cpp\n");

		///
		// when we add our own files, use the "smart" algorithm
		view.getSources().add(new Path("gfx/d.cpp"));
		commitTest(view, "SOURCEPATH ..\\ \n"+
		"SOURCE a.cpp sub\\b.cpp ..\\gfx\\c.cpp\n"+
		"SOURCEPATH ..\\gfx\n"+
		"SOURCE d.cpp\n");

		match(new IPath[] { new Path(""), new Path("gfx") }, view.getRealSourcePaths());

		view.dispose();
		model.dispose();
	}
	
	public void testSourceChanging6() throws Exception {
		// handle preserving existing sourcepaths
		makeModel("SOURCEPATH ..\\src\n"+
				"SOURCE a.cpp b.cpp\n"+
				"SOURCE c.cpp\n"+
				"SOURCE d.cpp\n"+
				"BASEADDRESS 0\n");

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		match(new IPath[] { new Path("src") }, view.getRealSourcePaths());

		// when we add our own files, use the "smart" algorithm
		view.getSources().set(2, new Path("gfx/c.cpp"));
		commitTest(view, "SOURCEPATH ..\\src\n"+
		"SOURCE a.cpp b.cpp\n"+
		"SOURCE ..\\gfx\\c.cpp d.cpp\n"+
		"BASEADDRESS 0\n"+
		"");

		match(new IPath[] { new Path("src") }, view.getRealSourcePaths());

		view.dispose();
		model.dispose();
	}
	
	/** bug 10968 */
	public void testSourceChanging7() throws Exception {
		makeModel("SOURCEPATH ..\\src\n"+
				"SOURCE a.cpp b.cpp\n"+
				"SOURCE c.cpp\n"+
				"SOURCE d.cpp\n"+
				"SOURCEPATH ..\\data\n" +
				"START RESOURCE extra.rss\n"+
				"END\n");

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		// when we add files, put them in the right section (DUH)
		view.getSources().add(new Path("data/Foo.cpp"));
		commitTest(view, "SOURCEPATH ..\\src\n"+
				"SOURCE a.cpp b.cpp\n"+
				"SOURCE c.cpp\n"+
				"SOURCE d.cpp\n"+
				"SOURCEPATH ..\\data\n" +
				"START RESOURCE extra.rss\n"+
				"END\n"+
				"SOURCE Foo.cpp\n" +
				"");

		match(new IPath[] { new Path("src"), new Path("data") }, view.getRealSourcePaths());
		
		assertTrue(view.getSources().contains(new Path("data/Foo.cpp")));

		view.dispose();
		model.dispose();
	}
	
	/** bug 10968 */
	public void testSourceChanging7b() throws Exception {
		makeModel("SOURCEPATH ..\\src\n"+
				"SOURCE a.cpp b.cpp\n"+
				"SOURCE c.cpp\n"+
				"SOURCE d.cpp\n"+
				"SOURCEPATH ..\\data\n" +
				"SOURCEPATH ..\\empty\n" +
				"START RESOURCE extra.rss\n"+
				"END\n");

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		// when we add files, put them in the right section (DUH)
		view.getSources().add(new Path("data/Foo.cpp"));
		commitTest(view, "SOURCEPATH ..\\src\n"+
				"SOURCE a.cpp b.cpp\n"+
				"SOURCE c.cpp\n"+
				"SOURCE d.cpp\n"+
				"SOURCEPATH ..\\empty\n" +
				"START RESOURCE extra.rss\n"+
				"END\n"+
				"SOURCEPATH ..\\data\n" +
				"SOURCE Foo.cpp\n" +
				"");

		match(new IPath[] { new Path("src"), new Path("empty"), new Path("data") }, view.getRealSourcePaths());
		
		assertTrue(view.getSources().contains(new Path("data/Foo.cpp")));

		view.dispose();
		model.dispose();
	}
	
	public void testSourceChangingCond1() throws Exception {
		makeModel("SOURCEPATH ..\\src\n#if 1\nSOURCE a.cpp\n#endif\nSOURCE b.cpp c.cpp");

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		// ensure no changes (not even newline)
		commitTest(view, "SOURCEPATH ..\\src\n#if 1\nSOURCE a.cpp\n#endif\nSOURCE b.cpp c.cpp");
		match(new IPath[] { new Path("src") }, view.getRealSourcePaths());

		view.getSources().add(new Path("src/foo.cpp"));
		match(new IPath[] { new Path("src") }, view.getEffectiveSourcePaths());
		
		// newline added to ensure file is sane
		commitTest(view, "SOURCEPATH ..\\src\n#if 1\nSOURCE a.cpp\n#endif\nSOURCE b.cpp c.cpp foo.cpp\n");
		match(new IPath[] { new Path("src") }, view.getRealSourcePaths());
		
		///
		
		view.getSources().remove(new Path("src/a.cpp"));
		view.getSources().add(0, new Path("src/a2.cpp"));
		match(new IPath[] { new Path("src") }, view.getEffectiveSourcePaths());

		view.commit();
		match(new IPath[] { new Path("src") }, view.getRealSourcePaths());

		assertEquals("SOURCEPATH ..\\src\n#if 1\n#endif\nSOURCE a2.cpp b.cpp c.cpp foo.cpp\n",
				model.getDocument().get());

		//

		view.getSources().remove(new Path("src/a.cpp"));
		match(new IPath[] { new Path("src") }, view.getEffectiveSourcePaths());

		commitTest(view, "SOURCEPATH ..\\src\n#if 1\n#endif\nSOURCE a2.cpp b.cpp c.cpp foo.cpp\n");
		match(new IPath[] { new Path("src") }, view.getRealSourcePaths());

		view.dispose();
		model.dispose();
	}


	public void testSourceChangingCond2() throws Exception {
		makeModel("#if 1\nSOURCEPATH ..\\src\nSOURCE a.cpp\n#endif\n");

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		view.getSources().add(new Path("src/foo.cpp"));
		match(new IPath[] { new Path("src") }, view.getEffectiveSourcePaths());
		match(new IPath[] { new Path("src") }, view.getRealSourcePaths());

		commitTest(view,
				"#if 1\nSOURCEPATH ..\\src\nSOURCE a.cpp\n#endif\n"+
				//"SOURCEPATH ..\\src\n"+ 
				"SOURCE foo.cpp\n");
		match(new IPath[] { new Path("src") }, view.getRealSourcePaths());

		view.dispose();
		model.dispose();
	}

	public void testSourcePaths0() {
		makeModel("SOURCEPATH ..\\src\n"+
				"SOURCEPATH ..\\data\n"
				);

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		match(new IPath[] { new Path("src"), new Path("data") }, view.getRealSourcePaths());
		match(new IPath[] { }, view.getEffectiveSourcePaths());
		match(new IPath[] { new Path("src"), new Path("data") }, view.getData().getRealSourcePaths());
		match(new IPath[] { }, view.getData().getEffectiveSourcePaths());
		
	}
	
	public void testSourcePaths1() {
		makeModel("SOURCEPATH ..\\src\n"+
				"SOURCE file.cpp util\\other.cpp\n"
				);

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		match(new IPath[] { new Path("src"), new Path("src/util") }, view.getEffectiveSourcePaths());
		match(new IPath[] { new Path("src") }, view.getRealSourcePaths());
		match(new IPath[] { new Path("src"), new Path("src/util") }, view.getData().getEffectiveSourcePaths());
		match(new IPath[] { new Path("src") }, view.getData().getRealSourcePaths());
		
	}

	public void testListParsing() throws Exception {
		makeModel("LIBRARY e32.lib avkon.lib\nLIBRARY eikctl.lib\n"+
				"ASSPLIBRARY assp1.lib\nDEBUGLIBRARY lop.lib o.lib\nSTATICLIBRARY static.lib\n"+
				"USERINCLUDE ..\\inc ..\\data\\ \n"+
				"SYSTEMINCLUDE \\include\\oem\n"
				);

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		_testListParsing(view);
		_testListParsing(view.getData());
		
		view.dispose();
		model.dispose();
	}

	private void _testListParsing(IMMPData data) {
		match(new String[] { "e32.lib", "avkon.lib", "eikctl.lib" }, data.getLibraries());
		match(new String[] { "assp1.lib" }, data.getASSPLibraries());
		match(new String[] { "lop.lib", "o.lib" }, data.getDebugLibraries());
		match(new String[] { "static.lib" }, data.getStaticLibraries());
		match(new String[] { "inc", "data/" }, data.getUserIncludes());
		match(new String[] { "/include/oem" }, data.getSystemIncludes());
	}
	
	public void testListChanging() throws Exception {
		makeModel("");

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		view.getLibraries().add("e32.lib");
		view.getLibraries().add("avkon.lib");
		view.getStaticLibraries().add("mystatic.lib");
		view.getDebugLibraries().add("debuglib.lib");
		view.getDebugLibraries().remove("debuglib.lib");

		commitTest(view,
				"LIBRARY e32.lib avkon.lib\nSTATICLIBRARY mystatic.lib\n");
		
		///
		
		view.getStaticLibraries().remove("mystatic.lib");
		commitTest(view,
		"LIBRARY e32.lib avkon.lib\n");
		
		///
		view.getStaticLibraries().add("mystatic.lib");
		view.getDebugLibraries().add("debuglib.lib");
		commitTest(view,
		"LIBRARY e32.lib avkon.lib\nDEBUGLIBRARY debuglib.lib\nSTATICLIBRARY mystatic.lib\n");

		//
		view.getUserIncludes().add(new Path("group"));
		commitTest(view,
		"LIBRARY e32.lib avkon.lib\nDEBUGLIBRARY debuglib.lib\nSTATICLIBRARY mystatic.lib\n"+
		"USERINCLUDE .\n"
				);
		
		///
		
		view.getUserIncludes().add(new Path("gfx"));
		
		// really a relative path
		view.getSystemIncludes().add(new Path("epoc32/release/winscw/udeb"));
		
		commitTest(view,
		"LIBRARY e32.lib avkon.lib\nDEBUGLIBRARY debuglib.lib\nSTATICLIBRARY mystatic.lib\n"+
		"USERINCLUDE . ../gfx\n"+
		"SYSTEMINCLUDE ../epoc32/release/winscw/udeb\n"
				);
		
		//
		// no match
		view.getSystemIncludes().remove(new Path("/epoc32/release/winscw/udeb"));
		commitTest(view,
				"LIBRARY e32.lib avkon.lib\nDEBUGLIBRARY debuglib.lib\nSTATICLIBRARY mystatic.lib\n"+
				"USERINCLUDE . ../gfx\n"+
				"SYSTEMINCLUDE ../epoc32/release/winscw/udeb\n"
						);
		view.getSystemIncludes().remove(new Path("epoc32/release/winscw/udeb"));
		commitTest(view,
				"LIBRARY e32.lib avkon.lib\nDEBUGLIBRARY debuglib.lib\nSTATICLIBRARY mystatic.lib\n"+
				"USERINCLUDE . ../gfx\n"
						);

		view.dispose();
		model.dispose();
	}
	
	public void testListChanging2() throws Exception {
		makeModel("");

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		Map<EMMPStatement, List<String>> listArgumentSettings = view.getListArgumentSettings();
		assertTrue(listArgumentSettings.keySet().contains(EMMPStatement.LIBRARY));
		assertTrue(listArgumentSettings.keySet().contains(EMMPStatement.STATICLIBRARY));
		assertTrue(listArgumentSettings.keySet().contains(EMMPStatement.DEBUGLIBRARY));
		assertTrue(listArgumentSettings.keySet().contains(EMMPStatement.ASSPLIBRARY));
		listArgumentSettings.get(EMMPStatement.LIBRARY).add("e32.lib");
		listArgumentSettings.get(EMMPStatement.LIBRARY).add("avkon.lib");
		listArgumentSettings.get(EMMPStatement.STATICLIBRARY).add("mystatic.lib");
		listArgumentSettings.get(EMMPStatement.ASSPLIBRARY).add("mystatic.lib");

		commitTest(view,
				"LIBRARY e32.lib avkon.lib\nSTATICLIBRARY mystatic.lib\nASSPLIBRARY mystatic.lib\n");
		
		view.dispose();
		model.dispose();
	}
	
	public void testListChangingRemove() throws Exception {
		String text = "LIBRARY e32.lib avkon.lib\nSTATICLIBRARY mystatic.lib\nASSPLIBRARY mystatic.lib\n";
		makeModel(text);

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		Map<EMMPStatement, List<String>> listArgumentSettings = view.getListArgumentSettings();
		listArgumentSettings.remove(EMMPStatement.LIBRARY);
		
		commitTest(view,
				"STATICLIBRARY mystatic.lib\nASSPLIBRARY mystatic.lib\n");

		///
		view.getListArgumentSettings().get(EMMPStatement.ASSPLIBRARY).clear();
		commitTest(view,
		"STATICLIBRARY mystatic.lib\n");
		
		//
		view.getListArgumentSettings().put(EMMPStatement.STATICLIBRARY, null);
		commitTest(view,
			"");
		
		view.dispose();
		model.dispose();
	}
	
	public void testListChangingIllegal() throws Exception {
		String text = "LIBRARY e32.lib avkon.lib\nSTATICLIBRARY mystatic.lib\nASSPLIBRARY mystatic.lib\n";
		makeModel(text);

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		Map<EMMPStatement, List<String>> listArgumentSettings = view.getListArgumentSettings();
		
		try {
			// not a list argument like this
			listArgumentSettings.put(EMMPStatement.SOURCE, new ArrayList<String>());
			fail();
		} catch (IllegalArgumentException e) {
		}

		try {
			listArgumentSettings.put(EMMPStatement.BASEADDRESS, new ArrayList<String>());
			fail();
		} catch (IllegalArgumentException e) {
		}
		
		view.dispose();
		model.dispose();
	}
	
	public void testListChangingCond1() throws Exception {
		makeModel("LIBRARY e32.lib avkon.lib\n#if 1\nSTATICLIBRARY mystatic.lib\n#endif\n");

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		view.getStaticLibraries().add("anotherstatic.lib");
		commitTest(view,
		"LIBRARY e32.lib avkon.lib\n#if 1\nSTATICLIBRARY mystatic.lib\n#endif\n"
				+"STATICLIBRARY anotherstatic.lib\n");
		
		view.dispose();
		model.dispose();
	}

	public void testSingleArgumentStatementParsing() {
		makeModel("BASEADDRESS 0xA0000000\n"+
				"TARGETTYPE exe\n"+
				"TARGETTYPE error\n"+
				"TARGETPATH \\sys\\bin\\ \n"+
				"TARGET myprog.exe\n"+
				"TARGET ignored.exe\n" // only first honoroed
				);

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		_testSingleArg1(view);
		_testSingleArg1(view.getData());
		
		view.dispose();
		model.dispose();
		
	}

	private void _testSingleArg1(IMMPData data) {
		assertEquals("0xA0000000", data.getSingleArgumentSettings().get(
				EMMPStatement.BASEADDRESS));
		assertEquals("exe", data.getSingleArgumentSettings().get(
				EMMPStatement.TARGETTYPE));
		assertEquals("\\sys\\bin\\", data.getSingleArgumentSettings().get(
				EMMPStatement.TARGETPATH));
		assertEquals("myprog.exe", data.getSingleArgumentSettings().get(
				EMMPStatement.TARGET));
		
		assertNull(data.getSingleArgumentSettings().get(
				EMMPStatement.FIRSTLIB));
		assertNull(data.getSingleArgumentSettings().get(
				EMMPStatement.LINKAS));
	}
	public void testSingleArgumentStatementParsing2() {
		makeModel(
				"TARGET \"my program.exe\"\n"
				);

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		assertEquals("my program.exe", view.getSingleArgumentSettings().get(
				EMMPStatement.TARGET));
		assertEquals("my program.exe", view.getData().getSingleArgumentSettings().get(
				EMMPStatement.TARGET));
		
		view.dispose();
		model.dispose();
		
	}
	
	public void testSingleArgumentStatementChanging() {
		makeModel("// hi mom\n"
				);

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		view.getSingleArgumentSettings().put(EMMPStatement.BASEADDRESS, "0xA0000000");
		view.getSingleArgumentSettings().put(EMMPStatement.TARGETTYPE, "exe");
		view.getSingleArgumentSettings().put(EMMPStatement.TARGETPATH, "\\sys\\bin\\");
		view.getSingleArgumentSettings().put(EMMPStatement.TARGET, "myprog.exe");

		commitTest(view,
		"// hi mom\n"+
		"TARGETTYPE exe\n"+
		"TARGETPATH \\sys\\bin\\ \n"+
		"TARGET myprog.exe\n"+
		"BASEADDRESS 0xA0000000\n"
		);
		
		///
		
		view.getSingleArgumentSettings().put(EMMPStatement.TARGETTYPE, "dll");
		commitTest(view,
				"// hi mom\n"+
				"TARGETTYPE dll\n"+
				"TARGETPATH \\sys\\bin\\ \n"+
				"TARGET myprog.exe\n"+
				"BASEADDRESS 0xA0000000\n"
				);

		///

		view.dispose();
		model.dispose();
		
	}
	
	public void testSingleArgumentStatementChanging2() {
		// validate removing/deleting keys
		String text = "// hi mom\n"+
				"TARGETTYPE exe\n"+
				"TARGETPATH \\sys\\bin\\ \n"+
				"TARGET myprog.exe\n"+
				"BASEADDRESS 0xA0000000\n";
		makeModel(text);

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		// either remove, set null, or set empty to remove statement
		view.getSingleArgumentSettings().remove(EMMPStatement.TARGET);
		view.getSingleArgumentSettings().remove(EMMPStatement.TARGETTYPE);
		commitTest(view,
				"// hi mom\n"+
				"TARGETPATH \\sys\\bin\\ \n"+
				"BASEADDRESS 0xA0000000\n"
				);
		
		///
		model.getDocument().set(text);
		view.revert();
		
		view.getSingleArgumentSettings().put(EMMPStatement.TARGET, null);
		view.getSingleArgumentSettings().put(EMMPStatement.TARGETTYPE, null);
		commitTest(view,
				"// hi mom\n"+
				"TARGETPATH \\sys\\bin\\ \n"+
				"BASEADDRESS 0xA0000000\n"
				);
		
		///
		model.getDocument().set(text);
		view.revert();
		
		view.getSingleArgumentSettings().put(EMMPStatement.TARGET, "");
		view.getSingleArgumentSettings().put(EMMPStatement.TARGETTYPE, "");
		commitTest(view,
				"// hi mom\n"+
				"TARGETPATH \\sys\\bin\\ \n"+
				"BASEADDRESS 0xA0000000\n"
				);
		
		////
		
		view.dispose();
		model.dispose();
		
	}

	public void testSingleArgumentStatementIllegalChanges() {
		// validate accessing invalid keys
		makeModel("// hi mom\n"
				);

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		try {
			view.getSingleArgumentSettings().put(EMMPStatement.SOURCE, "0xA0000000");
			fail();
		} catch (IllegalArgumentException e) {
			
		}

		try {
			view.getSingleArgumentSettings().put(EMMPStatement.SOURCEPATH, "0xA0000000");
			fail();
		} catch (IllegalArgumentException e) {
			
		}
		view.dispose();
		model.dispose();
		
	}

	public void testFlagStatementParsing() {
		makeModel("SRCDBG\n"+
				"NOEXPORTLIBRARY\n"+
				"EPOCCALLDLLENTRYPOINTS\n"
				);

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		_testFlagParsing(view);
		_testFlagParsing(view.getData());
		
		view.dispose();
		model.dispose();
	}

	private void _testFlagParsing(IMMPData data) {
		assertTrue(data.getFlags().contains(EMMPStatement.SRCDBG));
		assertTrue(data.getFlags().contains(EMMPStatement.NOEXPORTLIBRARY));
		assertTrue(data.getFlags().contains(EMMPStatement.EPOCCALLDLLENTRYPOINTS));
		
		assertFalse(data.getFlags().contains(EMMPStatement.ASSPABI));
		assertFalse(data.getFlags().contains(EMMPStatement.ALWAYS_BUILD_AS_ARM));
	}

	public void testFlagStatementChanging() {
		makeModel(""
				);

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		view.getFlags().add(EMMPStatement.SRCDBG);
		view.getFlags().add(EMMPStatement.NOEXPORTLIBRARY);
		view.getFlags().add(EMMPStatement.EPOCCALLDLLENTRYPOINTS);
		view.getFlags().remove(EMMPStatement.ASSPABI);
		
		commitTest(view,
		"SRCDBG\n"+
		"NOEXPORTLIBRARY\n"+
		"EPOCCALLDLLENTRYPOINTS\n"
		);
		
		///
		view.getFlags().add(EMMPStatement.ASSPABI);
		commitTest(view,
				"SRCDBG\n"+
				"NOEXPORTLIBRARY\n"+
				"EPOCCALLDLLENTRYPOINTS\n"+
				"ASSPABI\n"
				);
		
		view.dispose();
		model.dispose();
		
	}
	
	public void testFlagStatementIllegal() {
		makeModel(""
				);

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		try {
			view.getFlags().add(EMMPStatement.SOURCE);
			fail();
		} catch (IllegalArgumentException e) {
			
		}
		try {
			view.getFlags().add(EMMPStatement.SOURCEPATH);
			fail();
		} catch (IllegalArgumentException e) {
			
		}
		
		commitTest(view, "");
		
		view.dispose();
		model.dispose();
		
	}
	
	public void testMultiLine() {
		makeModel("SOURCE a b \\\nc d");

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		commitTest(view, "SOURCE a b \\\nc d");

		///
		view.getSources().add(new Path("group/e"));
		// newline added to ensure file is sane
		commitTest(view, "SOURCE a b c d e\n");
		
		view.dispose();
		model.dispose();

	}
	
	public void testChangeInclude() {
		String TEST_INCLUDES_1 = 
			"SOURCE foo.cpp bar.cpp\n"+
			"#include \"incfile.h\"\n";
		String TEST_INCLUDES_1_incfile_h = 
			"SOURCE inc.cpp other.cpp\n";
		

		String userPath = projectPath.append("group/incfile.h").toOSString().toLowerCase();
		parserConfig.getFilesystem().put(userPath, TEST_INCLUDES_1_incfile_h);
		
		makeModel(TEST_INCLUDES_1);
		
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		assertEquals(4, view.getSources().size());

		// no changes
		commitTest(view, TEST_INCLUDES_1);

		assertEquals(TEST_INCLUDES_1_incfile_h, parserConfig.getFilesystem().get(userPath));

		///
		
		view.getSources().remove(2);

		String TEST_INCLUDES_1_user_b =
			"SOURCE foo.cpp bar.cpp\n"+
			"#include \"incfile.h\"\n";
		String TEST_INCLUDES_1_incfile_h_user_b = 
			"SOURCE other.cpp\n";
		
		commitTest(view, TEST_INCLUDES_1_user_b);
		assertEquals(TEST_INCLUDES_1_incfile_h_user_b, parserConfig.getFilesystem().get(userPath));

		view.dispose();
		model.dispose();
	}

	public void testOldResourcesParsing0() {
		makeModel("RESOURCE foo.rss\n");
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		_testOldResourcsParsing0(view);
		_testOldResourcsParsing0(view.getData());
	}

	private void _testOldResourcsParsing0(IMMPData data) {
		List<IMMPResource> resources = data.getResourceBlocks();
		assertEquals(0, resources.size());
		
		List<IPath> userResources = data.getUserResources();
		assertEquals(1, userResources.size());
		assertEquals(new Path("group/foo.rss"), userResources.get(0));
	}
	
	public void testOldResourcesParsing1() {
		// un-targeted resources eventually get the TARGETPATH
		makeModel("RESOURCE foo.rss\n"+
				"TARGETPATH \\oh\\yeah");
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		_testOldResourcesParsing1(view);
		_testOldResourcesParsing1(view.getData());
	}

	private void _testOldResourcesParsing1(IMMPData data) {
		List<IMMPResource> resources = data.getResourceBlocks();
		assertEquals(0, resources.size());
		
		List<IPath> userResources = data.getUserResources();
		assertEquals(1, userResources.size());
		assertEquals(new Path("group/foo.rss"), userResources.get(0));
		
		assertEquals("\\oh\\yeah", data.getSingleArgumentSettings().get(EMMPStatement.TARGETPATH));
	}
	
	public void testOldResourcesParsing2() {
		// the LANGs unite into one glom for all the old-style resources 
		makeModel("TARGETPATH \\some\\strange\\ \n"+
				"LANG 01\n"+
				"RESOURCE foo.rss\n"+
				"LANG 02 03\n"+
				"SYSTEMRESOURCE ..\\data\\bar.rss baz.rss\n"				
		);
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		_testOldResourcesParsing2(view);
		_testOldResourcesParsing2(view.getData());
	}

	private void _testOldResourcesParsing2(IMMPData data) {
		List<IMMPResource> resources = data.getResourceBlocks();
		assertEquals(0, resources.size());
		
		List<IPath> userResources = data.getUserResources();
		assertEquals(1, userResources.size());
		assertEquals(new Path("group/foo.rss"), userResources.get(0));

		List<IPath> systemResources = data.getSystemResources();
		assertEquals(2, systemResources.size());
		assertEquals(new Path("data/bar.rss"), systemResources.get(0));
		assertEquals(new Path("group/baz.rss"), systemResources.get(1));

		List<EMMPLanguage> langs = data.getLanguages();
		assertEquals(3, langs.size());
		assertEquals(EMMPLanguage.English, langs.get(0));
		assertEquals(EMMPLanguage.forLangCode(2), langs.get(1));
		assertEquals(EMMPLanguage.forLangCode(3), langs.get(2));
	}

	public void testOldResourcesChanging() {
		makeModel("TARGETPATH \\some\\strange\\ \n"+
				"LANG 01\n"+
				"SYSTEMRESOURCE foo.rss\n"+
				"LANG 02 03\n"+
				"RESOURCE ..\\data\\bar.rss baz.rss\n"				
		);
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		view.getLanguages().add(EMMPLanguage.Arabic);
		
		// two implicit SOURCEPATHs but none in reality
		assertEquals(2, view.getEffectiveSourcePaths().length);
		commitTest(view,
				"TARGETPATH \\some\\strange\\ \n"+
				"LANG 01\n"+
				"SYSTEMRESOURCE foo.rss\n"+
				"LANG 02 03 37\n"+
				"RESOURCE ..\\data\\bar.rss baz.rss\n");
		
		view.getUserResources().add(new Path("test/testrsrc.rss"));
		view.getSystemResources().remove(0);
		
		// another implicit sourcepath but only one new statement
		assertEquals(3, view.getEffectiveSourcePaths().length);
		
		commitTest(view,
				"TARGETPATH \\some\\strange\\ \n"+
				"LANG 01\n"+
				"LANG 02 03 37\n"+
				"RESOURCE ..\\data\\bar.rss baz.rss\n"+
				"SOURCEPATH ..\\test\n"+
				"RESOURCE testrsrc.rss\n"
				);

	}
	
	/////////
	
	
	public void testNewResourcesParsing0() {
		makeModel("SOURCEPATH ..\\data\n"+
				"START RESOURCE foo.rss\n"+
				"END\n");
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		_testNewResourcesParsing0(view);
		_testNewResourcesParsing0(view.getData());
	}

	private void _testNewResourcesParsing0(IMMPData data) {
		List<IMMPResource> resources = data.getResourceBlocks();
		assertEquals(1, resources.size());
		IMMPResource resource = resources.get(0);
		
		assertEquals(new Path("data/foo.rss"), resource.getSource());
		assertEquals(0, resource.getDependsFiles().size());
		assertNull(resource.getTargetPath());
		assertNull(resource.getTargetFile());
		assertEquals(0, resource.getLanguages().size());
		assertEquals(EGeneratedHeaderFlags.NoHeader, resource.getHeaderFlags());
		assertNull(resource.getUid2());
		assertNull(resource.getUid3());
	}
	
	public void testNewResourcesParsing1() {
		makeModel("START RESOURCE ..\\data\\foo.rss\n"+
				"DEPENDS foo1.rsg\n"+
				"DEPENDS foo2.rsg\n"+
				"LANG 01 10\n"+
				"HEADER\n"+
				"TARGET foo.rsc\n"+
				"TARGETPATH \\sys\\bin\\ \n"+
				"UID 0x1000 0x2000\n"+
			"END\n"
				+
				"SOURCEPATH ..\\data\n"+
				"START RESOURCE foo.rss\n"+
				"DEPENDS foo1.rsg\n"+
				"DEPENDS foo2.rsg\n"+
				"TARGETPATH \\sys\\bin \n"+
				"TARGET foo.rsc\n"+
				"HEADER\n"+
				"LANG 01 10\n"+
				"UID 0x1000 0x2000\n"+
			"END\n");
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		_testNewResourcesParsing1(view);
		_testNewResourcesParsing1(view.getData());
	}

	private void _testNewResourcesParsing1(IMMPData data) {
		List<IMMPResource> resources = data.getResourceBlocks();
		assertEquals(2, resources.size());
		IMMPResource resource = resources.get(0);
		
		assertEquals(new Path("data/foo.rss"), resource.getSource());
		assertEquals(2, resource.getDependsFiles().size());
		assertEquals("foo1.rsg", resource.getDependsFiles().get(0));
		assertEquals("foo2.rsg", resource.getDependsFiles().get(1));
		assertEquals(new Path("/sys/bin"), resource.getTargetPath());
		assertEquals("foo.rsc", resource.getTargetFile());
		assertEquals(2, resource.getLanguages().size());
		assertEquals(EGeneratedHeaderFlags.Header, resource.getHeaderFlags());
		assertEquals("0x1000", resource.getUid2());
		assertEquals("0x2000", resource.getUid3());
		
		// ensure ordering of fields doesn't affect anything
		IMMPResource resource2 = resources.get(1);
		assertEquals(resource, resource2);
	}

	public void testNewResourcesParsing1b() {
		// handle absolute paths
		makeModel(
				"SOURCEPATH \\foo\\bar\\ \n"+
				"START RESOURCE foo.rss\n"+
				"END\n");
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		_testNewResourcesParsing1b(view);
		_testNewResourcesParsing1b(view.getData());
	}

	private void _testNewResourcesParsing1b(IMMPData data) {
		List<IMMPResource> resources = data.getResourceBlocks();
		assertEquals(1, resources.size());
		IMMPResource resource = resources.get(0);

		assertEquals(new Path("/foo/bar/foo.rss"), resource.getSource());
	}

	/** check that TARGET, SOURCE, etc. inside blocks are not visible */
	public void testNewResourcesParsing2a() {
		makeModel("TARGETPATH \\sys\\target\n"+
				"TARGET foo\n"+
				"UID 0x1 0x2\n"+
				"START RESOURCE ..\\data\\foo.rss\n"+
				"DEPENDS foo1.rsg\n"+
				"DEPENDS foo2.rsg\n"+
				"TARGETPATH \\sys\\bin\\ \n"+
				"UID 0x3 0x4\n"+
				"END\n");
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		_testNewResourcesParsing2a(view);
		_testNewResourcesParsing2a(view.getData());
	}

	private void _testNewResourcesParsing2a(IMMPData data) {
		assertEquals(new Path("/sys/target/foo"), data.getTargetFilePath());
		assertEquals("0x1", data.getUid2());
		assertEquals("0x2", data.getUid3());
		
		List<IMMPResource> resources = data.getResourceBlocks();
		assertEquals(1, resources.size());
		IMMPResource resource = resources.get(0);

		assertEquals("foo1.rsg", resource.getDependsFiles().get(0));
		assertEquals("foo2.rsg", resource.getDependsFiles().get(1));
		assertEquals(new Path("/sys/bin"), resource.getTargetPath());
		assertEquals("0x3", resource.getUid2());
		assertEquals("0x4", resource.getUid3());
	}

	public void testNewResourcesParsing2b() {
		makeModel(
				"START RESOURCE ..\\data\\foo.rss\n"+
				"DEPENDS foo1.rsg\n"+
				"DEPENDS foo2.rsg\n"+
				"TARGETPATH \\sys\\bin\n"+
				"UID 0x3 0x4\n"+
				"END\n");
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		_testNewResourcesParsing2b(view);
		_testNewResourcesParsing2b(view.getData());
	}

	private void _testNewResourcesParsing2b(IMMPData data) {
		assertNull(data.getSingleArgumentSettings().get(EMMPStatement.TARGETPATH));
		assertNull(data.getUid2());
		assertNull(data.getUid3());
		
		List<IMMPResource> resources = data.getResourceBlocks();
		assertEquals(1, resources.size());
		IMMPResource resource = resources.get(0);

		assertEquals("foo1.rsg", resource.getDependsFiles().get(0));
		assertEquals("foo2.rsg", resource.getDependsFiles().get(1));
		assertEquals(new Path("/sys/bin"), resource.getTargetPath());
		assertEquals("0x3", resource.getUid2());
		assertEquals("0x4", resource.getUid3());
	}

	public void testNewResourcesParsing2c() {
		makeModel("TARGETPATH \\sys\\target\n"+
				"UID 0x1 0x2\n"+
				"START RESOURCE ..\\data\\foo.rss\n"+
				"END\n");
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		_testNewResourcesParsing2c(view);
		_testNewResourcesParsing2c(view.getData());
	}

	private void _testNewResourcesParsing2c(IMMPData data) {
		assertEquals("\\sys\\target",
				data.getSingleArgumentSettings().get(EMMPStatement.TARGETPATH));

		assertEquals("0x1", data.getUid2());
		assertEquals("0x2", data.getUid3());
		
		List<IMMPResource> resources = data.getResourceBlocks();
		assertEquals(1, resources.size());
		IMMPResource resource = resources.get(0);

		assertEquals(0, resource.getDependsFiles().size());
		assertNull(resource.getTargetPath());
		assertNull(resource.getUid2());
		assertNull(resource.getUid3());
	}


	public void testNewResourcesParsing3() {
		// don't get confused by other blocks
		makeModel("START WINSCW\n"+
			"END\n");
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		List<IMMPResource> resources = view.getResourceBlocks();
		assertEquals(0, resources.size());
		
		resources = view.getData().getResourceBlocks();
		assertEquals(0, resources.size());
	}

	public void testNewResourcesChanging0() {
		makeModel("START RESOURCE ..\\data\\foo.rss\n"+
			"END\n"
				);
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		List<IMMPResource> resources = view.getResourceBlocks();
		assertEquals(1, resources.size());
		IMMPResource resource = resources.get(0);
		
		resource.setTargetPath(new Path("/sys/data/myapp"));
		resource.getLanguages().add(EMMPLanguage.American);
		
		commitTest(view,
				"START RESOURCE ..\\data\\foo.rss\n"+
				"\tTARGETPATH \\sys\\data\\myapp\n"+
				"\tLANG 10\n"+
				"END\n"
				);
		
	}
	
	public void testNewResourcesChanging1() {
		makeModel(""
				);
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		IMMPResource resource = view.createMMPResource();
		resource.setTargetPath(new Path("/where"));
		resource.setTargetFile("targ.rsc");
		assertFalse(resource.isValid());
		resource.setSource(new Path("/foo/bar/data/uidesign.rss"));
		assertTrue(resource.isValid());
		ArrayList<String> depends = new ArrayList<String>();
		depends.add("uidesign1.rsg");
		depends.add("uidesign2.rsg");
		resource.setDependsFiles(depends);
		
		resource.setHeaderFlags(EGeneratedHeaderFlags.Header);
		resource.getLanguages().add(EMMPLanguage.French);
		
		view.getResourceBlocks().add(resource);
		
		commitTest(view,
				"START RESOURCE /foo/bar/data/uidesign.rss\n"+
				"\tDEPENDS uidesign1.rsg\n"+
				"\tDEPENDS uidesign2.rsg\n"+
				"\tTARGET targ.rsc\n"+
				"\tTARGETPATH /where\n"+
				"\tHEADER\n"+ 
				"\tLANG 02\n"+
				"END\n"
				);
		
	}
	
	public void testNewResourcesChanging1b() {
		
		makeModel(
				"START RESOURCE \\foo\\bar\\data\\uidesign.rss\n"+
				"DEPENDS uidesign.rsg1\n"+
				"\tTARGET targ.rsc\n"+
				"\tTARGETPATH \\where\n"+
				"\tHEADER\n"+ 
				"\tLANG 02\n"+
				"END\n"
				);

		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		IMMPResource resource = view.getResourceBlocks().get(0);
		resource.getDependsFiles().clear();
		resource.setTargetFile(null);
		resource.setTargetPath(null);
		resource.setHeaderFlags(EGeneratedHeaderFlags.NoHeader);
		resource.getLanguages().clear();
		
		commitTest(view,
				"START RESOURCE \\foo\\bar\\data\\uidesign.rss\n" +
				"END\n");

	}
	public void testNewResourcesChanging3() {
		// ensure new stuff added at end
		makeModel("START RESOURCE a.rss\n"+
				"// comment\n"+
				"END\n"+
				"START RESOURCE b.rss\n"+
				"// comment\n"+
				"END\n"
				);
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		IMMPResource resource = view.createMMPResource();
		resource.setSource(new Path("data/uidesign.rss"));
		view.getResourceBlocks().add(resource);
		
		commitTest(view,
				"START RESOURCE a.rss\n"+
				"// comment\n"+
				"END\n"+
				"START RESOURCE b.rss\n"+
				"// comment\n"+
				"END\n"+
				"SOURCEPATH ../data\n"+
				"START RESOURCE uidesign.rss\n"+
				"END\n"
				);
		
	}
	
	public void testNewResourcesChanging2() {
		// ensure reordering detected
		makeModel("START RESOURCE a.rss\n"+
				"// comment\n"+
				"END\n"+
				"START RESOURCE b.rss\n"+
				"// comment\n"+
				"END\n"
				);
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		// reorder
		IMMPResource a = view.getResourceBlocks().remove(0);
		
		IMMPResource resource = view.createMMPResource();
		resource.setSource(new Path("data/uidesign.rss"));
		view.getResourceBlocks().add(resource);

		view.getResourceBlocks().add(a);

		commitTest(view,
				"START RESOURCE b.rss\n"+
				"// comment\n"+
				"END\n"+
				"SOURCEPATH ../data\n"+
				"START RESOURCE uidesign.rss\n"+
				"END\n"+
				"SOURCEPATH .\n"+
				"START RESOURCE a.rss\n"+
				"// comment\n"+
				"END\n"+
				""
				);
		
	}
	
	public void testNewResourcesChangingCond() {
		makeModel("START RESOURCE a.rss\n"+
				"#if MY_TARGET\n"+
				"HEADERONLY\n"+
				"#else\n"+
				"HEADER\n"+
				"#endif\n"+
				"END\n"
				);
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		IMMPResource resource = view.getResourceBlocks().get(0);
		resource.setSource(new Path("data/uidesign.rss"));

		commitTest(view,
				"#if 0\n" +
				"START RESOURCE a.rss\n"+
				"#if MY_TARGET\n" + 
				"HEADERONLY\n" + 
				"#else\n" + 
				"HEADER\n" + 
				"#endif\n" + 
				"END\n" + 
				"#endif\n" + 
				"SOURCEPATH ../data\n"+
				"START RESOURCE uidesign.rss\n" + 
				"\tHEADER\n" + 
				"END\n" 
				);
	}

	public void testTargetFilePath() {
		makeModel("TARGETPATH \\foo\\bar\n"+
				"TARGET file.exe\n");
		IMMPView view = getView(mmpConfig);
		assertEquals(new Path("/foo/bar/file.exe"), view.getTargetFilePath());
		
		// keep predom slash fmt
		view.setTargetFilePath(new Path("/sys/bin/nasty.exe"));
		commitTest(view, "TARGETPATH \\sys\\bin\n"+
			"TARGET nasty.exe\n");
		
		view.getSingleArgumentSettings().put(EMMPStatement.TARGETPATH, "\\sys\\dll");
		commitTest(view, "TARGETPATH \\sys\\dll\n"+
		"TARGET nasty.exe\n");
		
		view.getSingleArgumentSettings().put(EMMPStatement.TARGET, "mytest.dll");
		commitTest(view, "TARGETPATH \\sys\\dll\n"+
		"TARGET mytest.dll\n");

	}
	public void testAIFParsing0() {
		makeModel("TARGETPATH \\foo\\bar\n"+
				"AIF targetfile.aif ..\\data\\ aiffile.rss\n");
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		_testAIFParsing0(view);
		_testAIFParsing0(view.getData());
	}

	private void _testAIFParsing0(IMMPData data) {
		assertEquals(1, data.getAifs().size());
		IMMPAIFInfo info = data.getAifs().get(0);
		assertEquals(new Path("data/aiffile.rss"), info.getResource());
		// TARGETPATH not represented
		assertEquals(new Path("targetfile.aif"), info.getTarget());
		
		assertEquals(0, info.getColorDepth());
		assertEquals(0, info.getMaskDepth());
		assertFalse(info.isColor());
		assertEquals(0, info.getSourceBitmaps().size());
	}
	
	public void testAIFParsing1() {
		makeModel("TARGETPATH \\foo\\bar\n"+
				"AIF targetfile.aif ..\\data aiffile.rss c12,1 list_icon.bmp list_icon_mask.bmp context_icon.bmp context_icon_mask.bmp\n");
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		_testAIFParsing1(view);
		_testAIFParsing1(view.getData());

	}

	private void _testAIFParsing1(IMMPData data) {
		assertEquals(1, data.getAifs().size());
		IMMPAIFInfo info = data.getAifs().get(0);
		assertEquals(new Path("data/aiffile.rss"), info.getResource());
		// TARGETPATH not represented
		assertEquals(new Path("targetfile.aif"), info.getTarget());

		assertEquals(12, info.getColorDepth());
		assertEquals(1, info.getMaskDepth());
		assertTrue(info.isColor());
		assertEquals(2, info.getSourceBitmaps().size());
		
		IBitmapSourceReference ref;
		ref = info.getSourceBitmaps().get(0);
		assertEquals(new Path("data/list_icon.bmp"), ref.getPath());
		assertEquals(new Path("data/list_icon_mask.bmp"), ref.getMaskPath());
		ref = info.getSourceBitmaps().get(1);
		assertEquals(new Path("data/context_icon.bmp"), ref.getPath());
		assertEquals(new Path("data/context_icon_mask.bmp"), ref.getMaskPath());
	}
	
	public void testAIFChanging0() {
		makeModel("TARGETPATH \\foo\\bar\n"+
				"AIF targetfile.aif ..\\data\\ aiffile.rss\n");
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		// leave trailing sep alone
		commitTest(view,
				"TARGETPATH \\foo\\bar\n"+
		"AIF targetfile.aif ..\\data\\ aiffile.rss\n");
		
		///
		// add bitmaps
		
		IMMPAIFInfo aif = view.getAifs().get(0);
		aif.setColor(true);
		aif.setColorDepth(12);
		aif.setMaskDepth(1);
		
		IBitmapSourceReference ref = aif.createBitmapSourceReference();
		ref.setPath(new Path("data/foo.bmp"));
		ref.setMaskPath(new Path("data/foo_mask.bmp"));
		aif.getSourceBitmaps().add(ref);
		
		// remove trailing sep here
		commitTest(view,
				"TARGETPATH \\foo\\bar\n"+
				"AIF targetfile.aif ..\\data aiffile.rss c12,1 foo.bmp foo_mask.bmp\n");
		
		//////
		
		// turn off bitmaps
		aif = view.getAifs().get(0);
		aif.setColorDepth(0);

		commitTest(view,
				"TARGETPATH \\foo\\bar\n"+
				"AIF targetfile.aif ..\\data aiffile.rss\n");

		view.dispose();
	}
	
	public void testAIFChanging1() {
		makeModel("TARGETPATH \\foo\\bar\n"+
				"AIF targetfile.aif ..\\data\\ aiffile.rss\n");
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		IMMPAIFInfo aif = view.createMMPAIFInfo();
		
		aif.setResource(new Path("group/resource_aif.rss"));
		aif.setTarget(new Path("output_aif.aif"));
		aif.setColor(true);
		aif.setColorDepth(12);
		
		IBitmapSourceReference ref = aif.createBitmapSourceReference();
		ref.setPath(new Path("data/foo.bmp"));
		aif.getSourceBitmaps().add(ref);
		
		view.getAifs().add(aif);
		
		commitTest(view,
				"TARGETPATH \\foo\\bar\n"+
				"AIF targetfile.aif ..\\data\\ aiffile.rss\n"+
				"AIF output_aif.aif .. group\\resource_aif.rss c12 data\\foo.bmp\n"
				);
		
		////
		
		view.getAifs().remove(0);

		commitTest(view,
				"TARGETPATH \\foo\\bar\n"+
				"AIF output_aif.aif .. group\\resource_aif.rss c12 data\\foo.bmp\n");

		///
		view.getAifs().clear();
		commitTest(view,
				"TARGETPATH \\foo\\bar\n");

		view.dispose();
	}

	public void testAIFChanging1b() {
		makeModel("TARGETPATH \\foo\\bar\n");
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		IMMPAIFInfo aif = view.createMMPAIFInfo();
		
		aif.setResource(new Path("aif/resource_aif.rss"));
		aif.setTarget(new Path("output_aif.aif"));
		aif.setColor(true);
		aif.setColorDepth(12);
		
		IBitmapSourceReference ref = aif.createBitmapSourceReference();
		ref.setPath(new Path("aif/foo.bmp"));
		aif.getSourceBitmaps().add(ref);
		
		view.getAifs().add(aif);
		
		commitTest(view,
				"TARGETPATH \\foo\\bar\n"+
				"\n"+ // added newline 
				"AIF output_aif.aif ..\\aif resource_aif.rss c12 foo.bmp\n"
				);
		
		view.dispose();
	}

	public void testAIFChanging2() {
		// reorder
		makeModel("TARGETPATH \\foo\\bar\n"+
				"AIF targetfile.aif ..\\data\\ aiffile.rss\n"+
				"AIF output_aif.aif ..\\ group\\resource_aif.rss c12 data\\foo.bmp\n");
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);

		IMMPAIFInfo aif = view.getAifs().get(0);
		view.getAifs().remove(0);
		view.getAifs().add(aif);
		
		commitTest(view,
				"TARGETPATH \\foo\\bar\n"+
				"AIF output_aif.aif ..\\ group\\resource_aif.rss c12 data\\foo.bmp\n"+
				"AIF targetfile.aif ..\\data\\ aiffile.rss\n"
				);
	}
	public void testAIFChanging2b() {
		// reorder
		makeModel("TARGETPATH \\foo\\bar\n"+
		"AIF output_aif.aif ..\\ group\\resource_aif.rss c12 data\\foo.bmp\n");
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		IMMPAIFInfo aif = view.getAifs().get(0);
		aif.setMaskDepth(1);
		aif.getSourceBitmaps().get(0).setMaskPath(new Path("data/foom.bmp"));
		commitTest(view,
				"TARGETPATH \\foo\\bar\n"+
				"AIF output_aif.aif .. group\\resource_aif.rss c12,1 data\\foo.bmp data\\foom.bmp\n"
				);
	}

	public void testBitmapParsing0() {
		makeModel("START BITMAP foo.mbm\n"+"END\n");
		
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		_testBitmapParsing0(view);
		_testBitmapParsing0(view.getData());
	}

	private void _testBitmapParsing0(IMMPData data) {
		List<IMMPBitmap> bitmaps = data.getBitmaps();
		assertEquals(1, bitmaps.size());
		
		IMMPBitmap bitmap = bitmaps.get(0);
		assertEquals("foo.mbm", bitmap.getTargetFile());
		assertNull(bitmap.getTargetPath());
		assertEquals(EGeneratedHeaderFlags.NoHeader, bitmap.getHeaderFlags());
		assertEquals(0, bitmap.getSources().size());
	}
	
	public void testBitmapParsing1() {
		// ensure lowercase 'bitmap' matches too
		makeModel("START bitmap foo.mbm\n"+
				"TARGETPATH \\sys\\pix\n"+
				"HEADER\n"+
				"SOURCEPATH ..\\gfx\n"+
				"SOURCE c12,1 1.bmp 1_mask.bmp 2.bmp 2_mask.bmp\n"+
				"SOURCEPATH .\n"+
				"SOURCE c8 mass.bmp\n"+
				"END\n");
		
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		_testBitmapParsing1(view);
		_testBitmapParsing1(view.getData());

	}

	private void _testBitmapParsing1(IMMPData data) {
		List<IMMPBitmap> bitmaps = data.getBitmaps();
		assertEquals(1, bitmaps.size());
		
		IMMPBitmap bitmap = bitmaps.get(0);
		assertEquals("foo.mbm", bitmap.getTargetFile());
		assertEquals(new Path("/sys/pix"), bitmap.getTargetPath());
		assertEquals(EGeneratedHeaderFlags.Header, bitmap.getHeaderFlags());
		assertEquals(3, bitmap.getSources().size());
		
		List<IBitmapSource> sources = bitmap.getBitmapSources();
		IBitmapSource bm = sources.get(0);
		assertEquals(12, bm.getDepth());
		assertEquals(1, bm.getMaskDepth());
		assertTrue(bm.isColor());
		assertEquals(new Path("gfx/1.bmp"), bm.getPath());
		assertEquals(new Path("gfx/1_mask.bmp"), bm.getMaskPath());
		
		bm = sources.get(1);
		assertEquals(12, bm.getDepth());
		assertEquals(1, bm.getMaskDepth());
		assertTrue(bm.isColor());
		assertEquals(new Path("gfx/2.bmp"), bm.getPath());
		assertEquals(new Path("gfx/2_mask.bmp"), bm.getMaskPath());
		
		bm = sources.get(2);
		assertEquals(8, bm.getDepth());
		assertEquals(0, bm.getMaskDepth());
		assertTrue(bm.isColor());
		assertEquals(new Path("group/mass.bmp"), bm.getPath());
	}

	public void testBitmapChanging0() {
		makeModel("START BITMAP foo.mbm\n"+
				"END\n");
		
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		IMMPBitmap bitmap = view.createMMPBitmap();

		bitmap.setTargetFile("target.mbm");
		bitmap.setTargetPath(PathUtils.createPath("e:/foo/bar"));
		bitmap.setHeaderFlags(EGeneratedHeaderFlags.Header);

		IBitmapSource source = bitmap.createBitmapSource();

		source.setDepth(8);
		source.setMaskDepth(8);
		source.setPath(new Path("data/pix/icon.bmp"));
		source.setMaskPath(new Path("data/pix/icon_mask_soft.bmp"));

		bitmap.getSources().add(source);
		
		view.getBitmaps().add(bitmap);

		// new files: fwd slash
		commitTest(view,
				"START BITMAP foo.mbm\n"+
				"END\n"+
				"START BITMAP target.mbm\n"+
				"\tTARGETPATH e:/foo/bar\n"+
				"\tHEADER\n"+
				"\tSOURCEPATH ../data/pix\n"+
				"\tSOURCE 8,8 icon.bmp icon_mask_soft.bmp\n"+
				"END\n");
				
	}
	
	public void testBitmapChanging1() {
		String nativePath;
		if (HostOS.IS_WIN32)
			nativePath = "e:\\foo\\bar";
		else
			nativePath = "e:/foo/bar";
		makeModel(
				"START BITMAP target.mbm\n"+
				"// comment 1\n"+
				"\tTARGETPATH "+ nativePath + "\n"+
				"// comment 2\n"+
				"\tHEADER\n"+
				"// comment 3\n"+
				"\tSOURCEPATH ..\\data\\pix\n"+
				"// comment 4\n"+
				"\tSOURCE 8,8 icon.bmp icon_mask_soft.bmp\n"+
				"END\n");
		
		IMMPView view = getView(mmpConfig);
		assertNotNull(view);
		
		IMMPBitmap bitmap = view.getBitmaps().get(0);

		bitmap.setTargetFile("target.mbm");
		bitmap.setTargetPath(PathUtils.createPath(nativePath));
		bitmap.setHeaderFlags(EGeneratedHeaderFlags.Header);

		IBitmapSource source = (IBitmapSource) bitmap.getSources().get(0);

		source.setDepth(8);
		source.setMaskDepth(8);
		source.setPath(new Path("data/pix/icon.bmp"));
		source.setMaskPath(new Path("data/pix/icon_mask_soft.bmp"));

		// should be no change
		commitTest(view,
				"START BITMAP target.mbm\n"+
				"// comment 1\n"+
				"\tTARGETPATH " + nativePath + "\n"+
				"// comment 2\n"+
				"\tHEADER\n"+
				"// comment 3\n"+
				"\tSOURCEPATH ..\\data\\pix\n"+
				"// comment 4\n"+
				"\tSOURCE 8,8 icon.bmp icon_mask_soft.bmp\n"+
				"END\n");
	
		///
		bitmap = view.getBitmaps().get(0);
		bitmap.setHeaderFlags(EGeneratedHeaderFlags.HeaderOnly);
		
		// unfortunately we have to rewrite, reslash, and lose comments here 
		String nativePix;
		if (HostOS.IS_WIN32)
			nativePix = "..\\data\\pix";
		else
			nativePix = "../data/pix";
		commitTest(view,
				"START BITMAP target.mbm\n"+
				"\tTARGETPATH " +  nativePath + "\n"+
				"\tSOURCEPATH " + nativePix + "\n"+
				"\tSOURCE 8,8 icon.bmp icon_mask_soft.bmp\n"+
				"\tHEADERONLY\n"+
				"END\n");

	}

	
	public void testOptionParsing0() {
		makeModel("OPTION CW -w all\n"+
				"OPTION CW -O2\n"+
				"OPTION GCCE -I..\n"+
				"LINKEROPTION CW -msgstyle gcc\n"+
				"OPTION_REPLACE ARMV5 -O4\n");
		IMMPView view = getView(mmpConfig);
		
		_testOptionParsing(view);
		_testOptionParsing(view.getData());
	}

	private void _testOptionParsing(IMMPData data) {
		Map<String, String> opts = data.getOptions();
		assertEquals(2, opts.size());
		assertEquals("-w all -O2", opts.get("CW"));
		assertEquals("-I..", opts.get("GCCE"));
		assertNull(opts.get("ARMV5"));
		
		opts = data.getLinkerOptions();
		assertEquals(1, opts.size());
		assertEquals("-msgstyle gcc", opts.get("CW"));
		
		opts = data.getReplaceOptions();
		assertEquals(1, opts.size());
		assertEquals("-O4", opts.get("ARMV5"));
	}
	
	public void testOptionRewriting0() {
		makeModel("OPTION CW -w all\n"+
				"OPTION CW -O2\n"+
				"OPTION GCCE -I..\n"+
				"LINKEROPTION CW -msgstyle gcc\n"+
				"OPTION_REPLACE ARMV5 -O4\n");
		IMMPView view = getView(mmpConfig);
		
		// no changes
		commitTest(view,
				"OPTION CW -w all\n"+
				"OPTION CW -O2\n"+
				"OPTION GCCE -I..\n"+
				"LINKEROPTION CW -msgstyle gcc\n"+
				"OPTION_REPLACE ARMV5 -O4\n");

		////
		
		Map<String, String> opts = view.getOptions();
		opts.remove("CW");
		opts.put("UI", "design");
		
		commitTest(view,
				"OPTION GCCE -I..\n"+
				"LINKEROPTION CW -msgstyle gcc\n"+
				"OPTION_REPLACE ARMV5 -O4\n"+
				"OPTION UI design\n");
		
		////
		
		opts.put("UI", "design or not");

		commitTest(view,
				"OPTION GCCE -I..\n"+
				"LINKEROPTION CW -msgstyle gcc\n"+
				"OPTION_REPLACE ARMV5 -O4\n"+
				"OPTION UI design or not\n");

	}
	
	public void testOptionRewriting1() {
		makeModel("OPTION CW -w all\n"+
				"OPTION CW -O2\n");
		IMMPView view = getView(mmpConfig);
		
		Map<String, String> opts = view.getOptions();
		opts.put("CW", "-w -O2");
		
		commitTest(view,
				"OPTION CW -w -O2\n");
		

	}
	public void testOptionRewriting2() {
		makeModel("OPTION CW \"-w all\"\n"+
				"OPTION CW -O2\n");
		IMMPView view = getView(mmpConfig);
		
		Map<String, String> opts = view.getOptions();
		opts.put("CW", "\"-w all\" -opt size");
		
		commitTest(view,
				"OPTION CW \"-w all\"\n"+
				"OPTION CW -opt size\n");
		

	}
	public void testOptionParsing() {
		makeModel("SOURCEPATH ..\\src\n"+
				"SOURCE a.cpp\n"+
				"START WINS\n"+
				"SOURCE win.cpp\n"+
				"BASEADDRESS 0x6000\n"+
				"END\n"+
				"USERINCLUDE ..\\inc\n");
		IMMPView view = getView(mmpConfig);
		_testOptionParsing0(view);
		_testOptionParsing0(view.getData());
	}

	private void _testOptionParsing0(IMMPData data) {
		assertEquals(1, data.getSources().size());
		assertNull(data.getSingleArgumentSettings().get(EMMPStatement.BASEADDRESS));
		assertEquals(1, data.getUserIncludes().size());
		
		///
		// platform macros need to be defined like this:
		macros.add(DefineFactory.createDefine("WINS", "WINS"));
		
		data = getView(mmpConfig);
		assertEquals(2, data.getSources().size());
		assertEquals("0x6000", data.getSingleArgumentSettings().get(EMMPStatement.BASEADDRESS));
		assertEquals(1, data.getUserIncludes().size());
	}
	
	public void testPlatformOptionChanging() {
		makeModel("SOURCEPATH ..\\src\n"+
				"SOURCE a.cpp\n"+
				"START WINS\n"+
				"SOURCE win.cpp\n"+
				"BASEADDRESS 0x6000\n"+
				"END\n"+
				"USERINCLUDE ..\\inc\n");
		IMMPView view = getView(mmpConfig);

		// all changes are applied globally, so they go outside the blocks
		view.getSingleArgumentSettings().put(EMMPStatement.BASEADDRESS, "0x1111");
		commitTest(view, "SOURCEPATH ..\\src\n"+
				"SOURCE a.cpp\n"+
				"START WINS\n"+
				"SOURCE win.cpp\n"+
				"BASEADDRESS 0x6000\n"+
				"END\n"+
				"USERINCLUDE ..\\inc\n"+
				"\n"+ // added newline 
				"BASEADDRESS 0x1111\n"
				);
		
		///
		// platform macros need to be defined like this:
		macros.add(DefineFactory.createDefine("WINS", "WINS"));
		
		view = getView(mmpConfig);
		// here, the blocks already contain values, so they should be reused
		view.getSingleArgumentSettings().put(EMMPStatement.BASEADDRESS, "0x4444");
		commitTest(view, "SOURCEPATH ..\\src\n"+
				"SOURCE a.cpp\n"+
				"START WINS\n"+
				"SOURCE win.cpp\n"+
				"BASEADDRESS 0x4444\n"+
				"END\n"+
				"USERINCLUDE ..\\inc\n"+
				"\n"+
				"BASEADDRESS 0x1111\n"	// still present
				);
		
	}

	public void testProblems() {
		makeModel("this is unknown\n"+
				"** this is dangerous\n"+
				"SOURCE a.cpp\n"+
				"??? nonsense!\n");
		IMMPView view = getView(mmpConfig);
		
		IMessage[] messages = view.getMessages();
		assertEquals(3, messages.length);
		assertEquals(IMessage.ERROR, messages[0].getSeverity());
		assertEquals(IMessage.ERROR, messages[1].getSeverity());
		assertEquals(IMessage.WARNING, messages[2].getSeverity());

		assertEquals(1, view.getSources().size());
		commitTest(view, "this is unknown\n"+
				"** this is dangerous\n"+
				"SOURCE a.cpp\n"+
				"??? nonsense!\n");
	}
	
	public void testUnknownRewriting() {
		String text = "class\n"+
		"\t{\n"+
		"\tint foo(void);\n"+
		"\t};\n";
		makeModel(text);
		IMMPView view = getView(allConfig);
		commitTest(view, text);
	}
	
	public void testEMMPLanguageGetCodeStrings() {
		// test special case for SC
		EMMPLanguage lang0 = EMMPLanguage.forLangCode(0);
		assertEquals("SC", lang0.getCodeString());
		// test 1-digit case
		EMMPLanguage lang1 = EMMPLanguage.forLangCode(1);
		assertEquals("01", lang1.getCodeString());
		// test 2-digit case
		EMMPLanguage lang2 = EMMPLanguage.forLangCode(99);
		assertEquals("99", lang2.getCodeString());
		// test 3-digit case (unknown languages)
		EMMPLanguage lang3 = EMMPLanguage.forLangCode(327);
		assertEquals("327", lang3.getCodeString());
	}
}	
	