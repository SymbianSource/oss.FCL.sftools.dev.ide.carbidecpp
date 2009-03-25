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

package com.nokia.carbide.cpp.epoc.engine.tests.misc;

import com.nokia.carbide.cpp.epoc.engine.preprocessor.DefineFactory;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.epoc.engine.tests.BaseTest;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.MacroScanner;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;


public class TestMacroScanner extends BaseTest {

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.tests.BaseTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		// ignore non-directives and conditionals
		parserConfig.getFilesystem().put("macros.hrh",
				"#define FIRST 1\n"+
				"stupid junk {\n"+
				"#define SECOND\n"+
				"};\n"+
				"#include \"other.h\"\n"+
				"#define FOURTH(a,b,c) a+b+c\n"+
				"#if 0\n"+
				"#define FIFTH a+\\\nb+\\\nc\n"+
				"#endif\n"
				);
		parserConfig.getFilesystem().put("other.h",
				"#define THIRD() x\n");

	}
	
	public void testBasic() {
		
		MacroScanner scanner = new MacroScanner(parserConfig.getIncludeFileLocator(),
				parserConfig.getModelDocumentProvider(), 
				parserConfig.getTranslationUnitProvider());
		scanner.scanFile(new File("macros.hrh"));
		Collection<IDefine> defs = scanner.getMacroDefinitions();
		assertEquals(5, defs.size());
		Iterator<IDefine> iter = defs.iterator();
		IDefine d = iter.next();
		assertEquals("FIRST", d.getName());
		assertNull(d.getArgumentNames());
		assertEquals("1", d.getExpansion());
		assertEquals("FIRST", d.getNameAndArguments());
		assertEquals("FIRST 1", d.getDefinitionText());

		d = iter.next();
		assertEquals("SECOND", d.getName());
		assertNull(d.getArgumentNames());
		assertEquals("1", d.getExpansion()); // never null
		assertEquals("SECOND", d.getNameAndArguments());
		assertEquals("SECOND 1", d.getDefinitionText());
		
		d = iter.next();
		assertEquals("THIRD", d.getName());
		assertNotNull(d.getArgumentNames());
		assertEquals(0, d.getArgumentNames().length);
		assertEquals("x", d.getExpansion());
		assertEquals("THIRD()", d.getNameAndArguments());
		assertEquals("THIRD() x", d.getDefinitionText());
		
		d = iter.next();
		assertEquals("FOURTH", d.getName());
		String[] args = d.getArgumentNames();
		assertNotNull(args);
		assertEquals(3, args.length);
		assertEquals("a", args[0]);
		assertEquals("b", args[1]);
		assertEquals("c", args[2]);
		assertEquals("a+b+c", d.getExpansion());
		assertEquals("FOURTH(a,b,c)", d.getNameAndArguments());
		assertEquals("FOURTH(a,b,c) a+b+c", d.getDefinitionText());

		d = iter.next();
		assertEquals("FIFTH", d.getName());
		assertNull(d.getArgumentNames());
		assertEquals("a+b+c", d.getExpansion());
		assertEquals("FIFTH a+b+c", d.getDefinitionText());
	}
	
	public void testDefineFactory() {
		IDefine define = DefineFactory.createSimpleFreeformDefine("f");
		assertEquals("f", define.getName());
		assertNull(define.getArgumentNames());
		assertEquals("1", define.getExpansion());
		assertEquals("f 1", define.getDefinitionText());

		define = DefineFactory.createSimpleFreeformDefine("\tf ");
		assertEquals("f", define.getName());
		assertNull(define.getArgumentNames());
		assertEquals("1", define.getExpansion());
		assertEquals("f 1", define.getDefinitionText());
		
		define = DefineFactory.createSimpleFreeformDefine("MACRO=");
		assertEquals("MACRO", define.getName());
		assertNull(define.getArgumentNames());
		assertEquals("", define.getExpansion());
		assertEquals("MACRO", define.getDefinitionText());
	
		define = DefineFactory.createSimpleFreeformDefine("\tMACRO =    ");
		assertEquals("MACRO", define.getName());
		assertNull(define.getArgumentNames());
		assertEquals("", define.getExpansion());
		assertEquals("MACRO", define.getDefinitionText());

		define = DefineFactory.createSimpleFreeformDefine("FOO=3");
		assertEquals("FOO", define.getName());
		assertNull(define.getArgumentNames());
		assertEquals("3", define.getExpansion());
		assertEquals("FOO 3", define.getDefinitionText());
	
		define = DefineFactory.createSimpleFreeformDefine("\tFOO =  3  ");
		assertEquals("FOO", define.getName());
		assertNull(define.getArgumentNames());
		assertEquals("3", define.getExpansion());
		assertEquals("FOO 3", define.getDefinitionText());

		define = DefineFactory.createSimpleFreeformDefine("FOO=ALL -TCB");
		assertEquals("FOO", define.getName());
		assertNull(define.getArgumentNames());
		assertEquals("ALL -TCB", define.getExpansion());
		assertEquals("FOO ALL -TCB", define.getDefinitionText());

		define = DefineFactory.createSimpleFreeformDefine("  \tFOO \t \tALL -TCB");
		assertEquals("FOO", define.getName());
		assertNull(define.getArgumentNames());
		assertEquals("ALL -TCB", define.getExpansion());

		// not simple macros!
		define = DefineFactory.createSimpleFreeformDefine("FOO(x,y) (x+y)");
		assertEquals("FOO", define.getName());
		assertNull(define.getArgumentNames());
		assertEquals("(x,y) (x+y)", define.getExpansion());

		define = DefineFactory.createSimpleFreeformDefine("\t\tFOO ( x\t, \ty )   (x+y)  ");
		assertEquals("FOO", define.getName());
		assertNull(define.getArgumentNames());
		assertEquals("( x\t, \ty )   (x+y)", define.getExpansion());
		
	}
	
	public void testUndef() throws Exception {
		parserConfig.getFilesystem().put("macros.hrh",
				"#define FIRST 1\n"+
				"stupid junk {\n"+
				"#define SECOND 2\n"+
				"#undef FIRST\n"+
				"};\n"+
				"#undef SECOND\n" +
				"#define SECOND 3\n"+
				""
				);
		
		MacroScanner scanner = new MacroScanner(parserConfig.getIncludeFileLocator(),
				parserConfig.getModelDocumentProvider(), 
				parserConfig.getTranslationUnitProvider());
		scanner.scanFile(new File("macros.hrh"));
		
		// we keep the #define statements even if undef'd
		assertEquals(3, scanner.getDefines().size());
		
		// with #undefs, entries are dropped and reassigned
		Collection<IDefine> defs = scanner.getMacroDefinitions();
		assertEquals(1, defs.size());
		IDefine d = defs.iterator().next();
		assertEquals("SECOND", d.getName());
		assertEquals("3", d.getExpansion());
	}
}
