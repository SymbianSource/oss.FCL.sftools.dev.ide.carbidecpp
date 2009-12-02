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

import com.nokia.carbide.cpp.epoc.engine.DocumentFactory;
import com.nokia.carbide.cpp.epoc.engine.model.IViewParserConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.MakefileModelFactory;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileView;
import com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IViewFilter;
import com.nokia.carbide.cpp.epoc.engine.tests.BaseTest;
import com.nokia.cpp.internal.api.utils.core.HostOS;
import com.nokia.cpp.internal.api.utils.core.IMessage;

import org.eclipse.cdt.make.core.makefile.ICommand;
import org.eclipse.cdt.make.core.makefile.IMacroDefinition;
import org.eclipse.cdt.make.core.makefile.IMakefile;
import org.eclipse.cdt.make.core.makefile.ITargetRule;
import org.eclipse.cdt.make.core.makefile.gnu.IConditional;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;

import java.util.ArrayList;
import java.util.Collection;


public class TestMakefileView extends BaseTest {

	private AcceptedNodesViewFilter viewFilter;
	private ArrayList<IDefine> macros;
	private IMakefileViewConfiguration viewConfig;
	private IPath path;
	private IMakefileOwnedModel model;
		
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.tests.BaseTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		this.path = projectPath.append("group/Icons_scalable_dc.mk");

		
		viewFilter = new AcceptedNodesViewFilter();
		macros = new ArrayList<IDefine>();
		viewConfig = new IMakefileViewConfiguration() {

			public IViewFilter getViewFilter() {
				return viewFilter;
			}

			public Collection<IDefine> getMacros() {
				return macros;
			}
			
			public IViewParserConfiguration getViewParserConfiguration() {
				return parserConfig;
			}

			/* (non-Javadoc)
			 * @see com.nokia.carbide.cpp.epoc.engine.model.makefile.IMakefileViewConfiguration#getMakefileStyle()
			 */
			public String getMakefileStyle() {
				return "GNU";
			}
		};

	}
	
	protected void makeModel(String text) {
		// TODO: tests should work with both slash directions on both OSes
		if (HostOS.IS_UNIX)
			text = text.replaceAll("\\\\(?!\r|\n)", "/");
		IDocument document = DocumentFactory.createDocument(text);
		model = new MakefileModelFactory().createModel(path, document);

		model.parse();
	}
	
	protected IMakefileView getView(IMakefileViewConfiguration config) {
		IMakefileView view = (IMakefileView) model.createView(config);
		assertNotNull(view);
		return view;
	}
	
	protected void commitTest(IMakefileView view, String refText) {
		// TODO: tests should work with both slash directions on both OSes
		if (HostOS.IS_UNIX)
			refText = refText.replaceAll("\\\\(?!\r|\n)", "/");
		commitTest(model, view, refText);
	}
	
	final String TEST_1 =
		"# Very special makefile\n"+
		"SRCS=foo.cpp bar.cpp\n"+
		"OBJS=$(SRCS:.cpp=.obj)\n"+
		"\n"+
		"\n"+
		"all: test myfile.exe\n"+
		"\n"+
		"\n"+
		"test:\n"+
		"\techo \"Test happens\"\n"+
		".PHONY:\n"+
		"myfile.exe: $(OBJS)\n"+
		"\tld -o $< $^ -lc\n"+
		"\n";

	public void testParseBasic() {
		// ensure everything's wired up properly to the CDT make core plugin
		makeModel(TEST_1);
		
		IMakefileView view = getView(viewConfig);
		assertNotNull(view);
		
		assertNotNull(view.getMakefile());

		// basic test 
		IMakefile makefile = view.getMakefile();
		assertEquals(2, makefile.getMacroDefinitions().length);
		assertEquals(9, makefile.getDirectives().length);
		assertEquals(4, makefile.getRules().length);
		
		view.dispose();
		model.dispose();
	}
	
	public void testEOL() {
		makeModel("FOO=6\r\n"+
				"BAR=33\r\n");
		
		IMakefileView view = getView(viewConfig);
		assertEquals("\r\n", view.getEOL());
	}

	public void testRewriteBasic() {
		// test that we can rewrite the document without any changes
		makeModel(TEST_1);
		
		IMakefileView view = getView(viewConfig);
		assertNotNull(view);

		assertFalse(view.hasChanges());
		
		commitTest(view, TEST_1);
		view.dispose();
		model.dispose();
	}
	
	public void testMacroExpansion() {
		makeModel(TEST_1);
		
		IMakefileView view = getView(viewConfig);
		assertEquals("SRCS", view.getMakefile().expandString("SRCS"));
		assertEquals("these are foo.cpp bar.cpp, man", view.getMakefile().expandString("these are $(SRCS), man"));
		
		assertFalse(view.hasChanges());
		commitTest(view, TEST_1);
		
		view.dispose();
		model.dispose();
	}

	public void testCommandsInvoking() {
		makeModel(TEST_1);

		IMakefileView view = getView(viewConfig);

		ICommand[] commands = view.findCommandsInvoking("ld");
		assertEquals(1, commands.length);
		assertNotNull(commands[0].getParent());
	
		String mifconvEXELine = "MIFCONV=$(TOOLDIR)\\mifconv"+ (HostOS.IS_WIN32 ? ".exe" : "") + "\n";  
		makeModel("TOOLSDIR=c:\\my\\tools\n"+
				mifconvEXELine +
				"\n"+
				"all: mytool\n"+
				"\n"+
				"foo.mif: foo.svg\n"+
				"\t$(MIFCONV) -o $@ $^\n"+
				"\t$(MIFCONV) -o $@.bak $^\n");
		
		view = getView(viewConfig);

		commands = view.findCommandsInvoking("mifconv");
		assertEquals(2, commands.length);
		assertNotNull(commands[0].getParent());
		ITargetRule oldRule = (ITargetRule) commands[1].getParent();
		assertNotNull(oldRule);
		
		// some more tests...
		String newRule = oldRule.getTarget() + ": " + oldRule.getPrerequisites()[0] + " foo.h" + view.getEOL()
		+ oldRule.getCommands()[0];
		
		view.replaceDirective(oldRule, newRule);

		commitTest(view, "TOOLSDIR=c:\\my\\tools\n"+
				mifconvEXELine +
				"\n"+
				"all: mytool\n"+
				"\n"+
				"foo.mif: foo.svg foo.h\n"+
				"\t$(MIFCONV) -o $@ $^\n");

		
	}
	
	final String TEST_2 =
		"ifeq ($(PLATFORM), WINS)\n"+
		"ZDIR=$(EPOCROOT)\\release\\wins\\z\n"+
		"else\n"+
		"ZDIR=$(EPOCROOT)\\release\\$(PLATFORM)\\$(RELEASE)\\$(TARGET)\\z\n"+
		"endif\n"+
		"RESOURCE: $(ZDIR)\\sys\\gfx\\foo.mif\n";
	final String TEST_2_a =
		"ifeq ($(PLATFORM), WINS)\n"+
		"ZDIR=$(EPOCROOT)\\release\\wins\\z\n"+
		"else\n"+
		"ZDIR=FOO\n"+
		"endif\n"+
		"RESOURCE: $(ZDIR)\\sys\\gfx\\foo.mif\n";

	public void testQueryMacroDefs() {
		makeModel(TEST_1);

		IMakefileView view = getView(viewConfig);
		IMacroDefinition definition = view.getMakefile().getMacroDefinitions("OBJS")[0];
		assertNotNull(definition);
		
		definition = view.getMakefile().getMacroDefinitions("SRCS")[0];
		assertNotNull(definition);
		
		////////
		
		makeModel(TEST_2);
		view = getView(viewConfig);
		IMacroDefinition[] defs = view.getAllMacroDefinitions("ZDIR");
		assertNotNull(defs);
		
		assertEquals(2, defs.length);
		assertTrue(defs[0].getParent() instanceof IConditional);
		assertTrue(defs[1].getParent() instanceof IConditional);
		assertNotSame(defs[0].getParent(), defs[1].getParent());
		
	}
	
	public void testQueryTargets() {
		makeModel(TEST_1);

		IMakefileView view = getView(viewConfig);

		ITargetRule rule = view.findRuleForTarget("myfile.exe", false);
		assertNotNull(rule);
		
		makeModel("BLD : do_nothing\r\n" + 
				"\r\n" + 
				"CLEAN : do_nothing\r\n" + 
				"\r\n" + 
				"LIB : do_nothing\r\n" + 
				"\r\n" + 
				"CLEANLIB : do_nothing\r\n" + 
				"\r\n" + 
				"RESOURCE :	\r\n" + 
				"	mifconv $(ICONTARGETFILENAME) \\\r\n" + 
				"		/c32 $(ICONDIR)\\qgn_menu_Birthdays.svg\r\n" + 
				"		\r\n" + 
				"FREEZE : do_nothing\r\n" + 
				"\r\n" + 
				"SAVESPACE : do_nothing\r\n" + 
				"\r\n" + 
				"RELEASABLES :\r\n" + 
				"	@echo $(ICONTARGETFILENAME)\r\n" + 
				"\r\n" + 
				"FINAL : do_nothing\r\n" + 
				"\r\n"); 
		view = getView(viewConfig);
		
		rule = view.findRuleForTarget("RESOURCE", true);
		assertNotNull(rule);
		
		ICommand[] commands = view.findCommandsInvoking("mifconv");
		assertEquals(1, commands.length);
		assertEquals(rule, commands[0].getParent());
		
		view.dispose();
		model.dispose();
	}
	
	final String TEST_1_a =	
		TEST_1 + 	// we lose the final newlines
		"FOO=123  # yup\n";

	final String TEST_1_b =
		"virus.exe:\n"+
		"\tgcc -o $@ doom.c\n"+
		TEST_1_a; 


	public void testAddedDirectives() {
		makeModel(TEST_1);
		IMakefileView view = getView(viewConfig);
		
		assertEquals(2, view.getMakefile().getMacroDefinitions().length);
		view.insertTextBefore("FOO=123  # yup\n", null);
		assertTrue(view.hasChanges());
		
		assertEquals(3, view.getMakefile().getMacroDefinitions().length);
		assertEquals("123=FOO", view.getMakefile().expandString("$(FOO)=FOO"));
		
		commitTest(view,
				TEST_1_a);
		
		String target = "virus.exe:\n" + "\tgcc -o $@ doom.c\n";
		view.insertText(null, target);
		
		commitTest(view,
				TEST_1_b
				);
				
		
		view.dispose();
		model.dispose();
	}

	public void testOverrideDirectives() {
		makeModel(TEST_1);
		
		IMakefileView view = getView(viewConfig);
		IMacroDefinition oldDef = view.getMakefile().getMacroDefinitions("SRCS")[0];
		String newVal = oldDef.getValue() + " more.cpp";
		String newText = oldDef.getName() + " = " + newVal + "\n";
		view.replaceDirective(oldDef, newText);
		
		IMacroDefinition checkDef = view.getMakefile().getMacroDefinitions("SRCS")[0];
		assertEquals(newText, checkDef.toString());
	
		view.dispose();
		model.dispose();
		
		///////
		
		makeModel(TEST_2);
		view = getView(viewConfig);
		IMacroDefinition[] defs = view.getAllMacroDefinitions("ZDIR");
		assertNotNull(defs);
		
		assertEquals(2, defs.length);

		oldDef = defs[1];
		
		view.replaceDirective(oldDef, "ZDIR=FOO" + view.getEOL());

		commitTest(view, TEST_2_a);

	}
	
	public void testUnexpand1() {
		makeModel("ZDIR=$(EPOCROOT)\\release\\winscw\n"+
				"TARGETDIR=$(ZDIR)\\data\n"+
				"ICONTARGETFILENAME=$(TARGETDIR)\\foo.mif\n");
		IMakefileView view = getView(viewConfig);

		doUnexpandTest(view, "$(EPOCROOT)\\release\\winscw\\data\\more.mif", 
				"$(TARGETDIR)\\more.mif");

		doUnexpandTest(view, "1 2 $(EPOCROOT)\\release\\winscw\\data\\more.mif 3 4", 
			"1 2 $(TARGETDIR)\\more.mif 3 4");

		doUnexpandTest(view, "$(EPOCROOT)\\release\\winscw\\more.mif", 
			"$(ZDIR)\\more.mif");

		doUnexpandTest(view, "$(EPOCROOT)\\release\\foo\\data\\more.mif", 
			"$(EPOCROOT)\\release\\foo\\data\\more.mif");

		doUnexpandTest(view, "\\release\\winscw\\data\\more.mif", 
			"\\release\\winscw\\data\\more.mif");

	}
	
	
	private void doUnexpandTest(IMakefileView view, String expanded,
			String unexpanded) {

		// TODO: tests should ideally work with both slash directions on both OSes
		if (HostOS.IS_WIN32) {
			String repl = view.unexpandMacros(expanded, 
					new String[] {  "TARGETDIR", "ZDIR" });
			assertEquals(unexpanded, repl);
		} else {
			expanded = HostOS.convertPathToUnix(expanded);
			unexpanded = HostOS.convertPathToUnix(unexpanded);
			String repl = view.unexpandMacros(expanded, 
					new String[] {  "TARGETDIR", "ZDIR" });
			assertEquals(unexpanded, repl);
		}
		
	}

	public void testProblems() {
		makeModel("\n\n)ZDIR@foo{\n"+
				"^^ placate ^^\n");
		IMakefileView view = getView(viewConfig);
		IMessage[] messages = view.getMessages();
		for (IMessage msg : messages)
			System.out.println(msg);
		assertEquals(2, messages.length);
		assertEquals(IMessage.ERROR, messages[0].getSeverity());
		assertEquals(IMessage.ERROR, messages[1].getSeverity());
	}
}
