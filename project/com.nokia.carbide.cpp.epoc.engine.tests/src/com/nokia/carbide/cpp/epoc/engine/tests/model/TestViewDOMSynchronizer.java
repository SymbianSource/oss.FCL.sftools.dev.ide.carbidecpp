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
* Test that a list of view modifications properly updates a preparser DOM.
*
*
*/
package com.nokia.carbide.cpp.epoc.engine.tests.model;

import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ASTFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorIncludeStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.ASTMMPFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPFlagStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPListArgumentStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPSingleArgumentStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStartBlockStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPUidStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorResults;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.rewriter.DocumentUpdater;
import com.nokia.carbide.internal.cpp.epoc.engine.model.*;
import com.nokia.cpp.internal.api.utils.core.Pair;

import org.eclipse.core.runtime.IPath;

import java.util.Arrays;
public class TestViewDOMSynchronizer extends BaseMMPViewTest {

	// idx in parsed
	final static int MT1_SOURCEPATH = 0;
	final static int MT1_SOURCE = 1;
	final static int MT1_BASEADDRESS = 2;
	final static int HT1_FIRST = 3;
	final static int HT1_DEFFILE = 3;
	final static int HT1_NOSTRICTDEF = 4;
	final static int HT1_VENDORID = 5;
	final static int MT1_SECUREID = 6;
	final static int MT1_UID = 7;
	
	// idx in preparser
	final static int MT1_PP_INCLUDE = 6;
	
	String mainText1 = 
		"// comment\n"+
		"SOURCEPATH ..\\src // 0\n"+ //0
		"SOURCE file1.cpp file2.cpp // 1\n"+ //1
		"\n"+
		"BASEADDRESS 0x6000 // 2\n"+ //2
		"\n"+
		"#include \"header.mmh\"\n"+
		"\n"+
		"#ifdef SECURE_UID\n"+
		"SECUREID SECURE_UID // 6\n"+ //6
		"#endif\n"+
		"\n"+
		"UID 0x2 0x3 // 7\n"+ //7
		"\n"+
		"// end comment\n";

	
	String headerText1 = 
		"// comment\n"+
		"#define SECURE_UID 1\n"+
		"\n"+
		"DEFFILE myfile // 3\n"+ //3
		"\n"+
		"#if defined(SECURE_UID)\n"+
		"NOSTRICTDEF // 4\n"+ //4
		"VENDORID 0 // 5\n"+ //5
		"#endif\n"+
		"\n"+
		"// end comment\n";

	private IMMPView view;

	private IPath header1Path;

	private IASTTranslationUnit modelTu;

	private IASTTranslationUnit viewTu;

	private IPreprocessorResults ppResults;

	private IASTPreprocessorIncludeStatement headerIncl;
	private IASTTranslationUnit headerTu;
		
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		header1Path = projectPath.append("../inc/header.mmh");
		parserConfig.getFilesystem().put(header1Path.toOSString(), headerText1);
		
		doParse(mainText1);
		
	}
	
	/**
	 * Parse the given main text.  Finds the first #include and sets up
	 * headerIncl and headerTu from that.
	 * @param mainText
	 */
	private void doParse(String mainText) {
		makeModel(mainText);
		view = getView(mmpConfig);
		modelTu = ((ModelBase) view.getModel()).getTranslationUnit();
		viewTu = ((ViewASTBase) view).getFilteredTranslationUnit();
		
		ppResults = ((ViewASTBase) view).getPreprocessorResults();

		for (IASTNode node : modelTu.getNodes()) {
			if (node instanceof IASTPreprocessorIncludeStatement) {
				headerIncl = (IASTPreprocessorIncludeStatement) node;
				headerTu = ppResults.getTranslationUnitFor(headerIncl);
				break;
			}
		}

		((ModelBase) model).lock();
		((ViewASTBase) view).lock();
		
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	protected ISynchronizerResults applyModifications(IViewModification[] modifications) {
		ViewDOMSynchronizer<IMMPOwnedModel, IMMPView> modelSynchronizer = 
			new ViewDOMSynchronizer<IMMPOwnedModel, IMMPView>(
					modelTu, viewTu, ppResults, 
					Arrays.asList(modifications));
		return modelSynchronizer.sync();
	}
	
	/**
	 * @param mods
	 */
	protected void applyModificationsNoProblems(IViewModification[] mods) {
		ISynchronizerResults results = applyModifications(mods);
		assertEquals(0, results.getMessages().size());
	}

	public void testNoChanges() {
		IViewModification[] modifications = new IViewModification[] {
				
		};
		applyModificationsNoProblems(modifications);
		assertEquals(mainText1, getCurrentText(modelTu));
	}
	
	protected String getCurrentText(IASTTranslationUnit tu) {
		// hackery to handle cases where we look at the updated #include TU (which is always
		// preparsed and whose source node is the #include that brought it in) versus the
		// view TU (whose original TU is the model TU)
		IASTTranslationUnit origTu = tu;
		if (tu.getSourceNodes() == null) {
			origTu = ppResults.getOriginalTranslationUnit();
			DocumentUpdater.updateDocuments(ppResults, origTu);
		} else {
			DocumentUpdater.updateDocuments(ppResults, origTu);
		}
		
		return origTu.getMainDocument().get();
	}

	protected IViewAddModification makeAdd(final IASTNode parent, final IASTNode node) {
		return new IViewAddModification() {

			public IASTNode[] getNodes() {
				return new IASTNode[] { node };
			}

			public IASTNode getParent() {
				return parent;
			}
			
			public Pair<IASTNode, IASTNode> getInsertAnchors() {
				return new Pair(null, null);
			}
		};
	}
	
	protected IViewAddModification makeAdd(final IASTNode parent, 
			final IASTNode node, 
			final IASTNode before,
			final IASTNode after) {
		return new IViewAddModification() {

			public IASTNode[] getNodes() {
				return new IASTNode[] { node };
			}

			public IASTNode getParent() {
				return parent;
			}
			
			public Pair<IASTNode, IASTNode> getInsertAnchors() {
				return new Pair(before, after);
			}
		};
	}


	protected IViewChangeModification makeChange(final IASTNode node) {
		return new IViewChangeModification() {

			public IASTNode[] getNodes() {
				return new IASTNode[] { node };
			}
			
			public IASTNode[] getReplacementNodes() {
				return null;
			}
			
			public IASTNode getParent() {
				return null;
			}
		};
	}
	protected IViewChangeModification makeChange(final IASTNode parent, final IASTNode node) {
		return new IViewChangeModification() {

			public IASTNode[] getNodes() {
				return new IASTNode[] { node };
			}
			
			public IASTNode[] getReplacementNodes() {
				return null;
			}

			public IASTNode getParent() {
				return parent;
			}
		};
	}
	
	protected IViewChangeModification makeChange(final IASTNode node, final IASTNode[] nodes) {
		return new IViewChangeModification() {

			public IASTNode[] getNodes() {
				return new IASTNode[] { node };
			}
			
			public IASTNode[] getReplacementNodes() {
				return nodes;
			}

			public IASTNode getParent() {
				return null;
			}
		};
	}
	protected IViewDeleteModification makeDelete(final IASTNode node) {
		return new IViewDeleteModification() {

			public IASTNode[] getNodes() {
				return new IASTNode[] { node };
			}
		};
	}
	
	protected IViewReorderModification makeReorder(final IASTNode[] nodes) {
		return new IViewReorderModification() {

			public IASTNode[] getNodes() {
				return nodes;
			}
		};
	}

	public void testMainDocumentChange1() {
		IASTMMPSingleArgumentStatement stmt = (IASTMMPSingleArgumentStatement) 
			viewTu.getNodes().get(MT1_SOURCEPATH);
		stmt.getArgument().setValue("..\\src2");

		IViewModification[] mods = new IViewModification[] {
				makeChange(stmt)
		};
		applyModificationsNoProblems(mods);
		
		String mainText1b = 
			"// comment\n"+
			"SOURCEPATH ..\\src2 // 0\n"+ //change
			"SOURCE file1.cpp file2.cpp // 1\n"+
			"\n"+
			"BASEADDRESS 0x6000 // 2\n"+
			"\n"+
			"#include \"header.mmh\"\n"+
			"\n"+
			"#ifdef SECURE_UID\n"+
			"SECUREID SECURE_UID // 6\n"+
			"#endif\n"+
			"\n"+
			"UID 0x2 0x3 // 7\n"+
			"\n"+
			"// end comment\n";
		assertEquals(mainText1b, getCurrentText(modelTu));
		assertEquals(headerText1, getCurrentText(headerTu));
	}

	public void testMainDocumentChange2() {
		IASTMMPSingleArgumentStatement stmt = (IASTMMPSingleArgumentStatement) 
			viewTu.getNodes().get(MT1_SECUREID);
		stmt.getArgument().setValue("0x123");

		applyModificationsNoProblems(new IViewModification[] {
				makeChange(stmt)
		});
		
		String mainText1b = 
			"// comment\n"+
			"SOURCEPATH ..\\src // 0\n"+
			"SOURCE file1.cpp file2.cpp // 1\n"+
			"\n"+
			"BASEADDRESS 0x6000 // 2\n"+
			"\n"+
			"#include \"header.mmh\"\n"+
			"\n"+
			"#ifdef SECURE_UID\n"+
			"#if 0\n"+ //add
			"SECUREID SECURE_UID // 6\n"+ //add
			"#endif\n"+ //add
			"SECUREID 0x123 // 6\n"+ //changed  
			"#endif\n"+
			"\n"+
			"UID 0x2 0x3 // 7\n"+
			"\n"+
			"// end comment\n";
		assertEquals(mainText1b, getCurrentText(modelTu));
		assertEquals(headerText1, getCurrentText(headerTu));
	}

	public void testMainDocumentChange3() {
		IASTMMPStatement stmt = (IASTMMPStatement) 
			viewTu.getNodes().get(MT1_SOURCE);

		IASTMMPSingleArgumentStatement srcPath = ASTMMPFactory.createMMPSingleArgumentStatement("SOURCEPATH", "../foo");
		applyModificationsNoProblems(new IViewModification[] {
				makeChange(stmt, new IASTNode[] { srcPath, stmt })
		});
		
		String mainText1b = 
			"// comment\n"+
			"SOURCEPATH ..\\src // 0\n"+
			"SOURCEPATH ../foo\n"+ // replaced
			"SOURCE file1.cpp file2.cpp // 1\n"+ // replaced
			"\n"+
			"BASEADDRESS 0x6000 // 2\n"+
			"\n"+
			"#include \"header.mmh\"\n"+
			"\n"+
			"#ifdef SECURE_UID\n"+
			"SECUREID SECURE_UID // 6\n"+
			"#endif\n"+
			"\n"+
			"UID 0x2 0x3 // 7\n"+
			"\n"+
			"// end comment\n";
		assertEquals(mainText1b, getCurrentText(modelTu));
		assertEquals(headerText1, getCurrentText(headerTu));
	}

	public void testHeaderDocumentChange1() {
		IASTMMPSingleArgumentStatement stmt = (IASTMMPSingleArgumentStatement) 
			viewTu.getNodes().get(HT1_DEFFILE);
		stmt.getArgument().setValue("monkey.def");

		applyModificationsNoProblems(new IViewModification[] {
				makeChange(stmt)
		});
		
		String headerText1b = 
				"// comment\n"+
				"#define SECURE_UID 1\n"+
				"\n"+
				"DEFFILE monkey.def // 3\n"+ //3
				"\n"+
				"#if defined(SECURE_UID)\n"+
				"NOSTRICTDEF // 4\n"+ //4
				"VENDORID 0 // 5\n"+//5
				"#endif\n"+
				"\n"+
				"// end comment\n";
		assertEquals(mainText1, getCurrentText(modelTu));
		assertEquals(headerText1b, getCurrentText(headerTu));
	}

	public void testHeaderDocumentChange2() {
		IASTMMPSingleArgumentStatement stmt = (IASTMMPSingleArgumentStatement) 
			viewTu.getNodes().get(HT1_VENDORID);
		stmt.getArgument().setValue("0x123");

		applyModificationsNoProblems(new IViewModification[] {
				makeChange(stmt)
		});
		
		String headerText1b = 
			"// comment\n"+
			"#define SECURE_UID 1\n"+
			"\n"+
			"DEFFILE myfile // 3\n"+ //3
			"\n"+
			"#if defined(SECURE_UID)\n"+
			"NOSTRICTDEF // 4\n"+ //4
			"VENDORID 0x123 // 5\n"+//5
			"#endif\n"+
			"\n"+
			"// end comment\n";
		assertEquals(mainText1, getCurrentText(modelTu));
		assertEquals(headerText1b, getCurrentText(headerTu));
	}

	public void testMainDocumentDelete1() {
		IViewModification[] viewModifications = new IViewModification[] {
						makeDelete(viewTu.getNodes().get(MT1_UID)),
						makeDelete(viewTu.getNodes().get(MT1_SOURCE)),
				};
		applyModificationsNoProblems(viewModifications);

		String mainText1b = 
			"// comment\n"+
			"SOURCEPATH ..\\src // 0\n"+
			// remove SOURCE
			"\n"+
			"BASEADDRESS 0x6000 // 2\n"+
			"\n"+
			"#include \"header.mmh\"\n"+
			"\n"+
			"#ifdef SECURE_UID\n"+
			"SECUREID SECURE_UID // 6\n"+
			"#endif\n"+
			"\n"+
			// remove UID
			"\n"+
			"// end comment\n";
		assertEquals(mainText1b, getCurrentText(modelTu));
		assertEquals(headerText1, getCurrentText(headerTu));
	}
	
	public void testHeaderDocumentDelete1() {
		IViewModification[] viewModifications = new IViewModification[] {
						makeDelete(viewTu.getNodes().get(HT1_VENDORID)),
						makeDelete(viewTu.getNodes().get(HT1_DEFFILE)),
				};
		applyModificationsNoProblems(viewModifications);
	
		String headerText1b = 
			"// comment\n"+
			"#define SECURE_UID 1\n"+
			"\n"+
			"\n"+
			"#if defined(SECURE_UID)\n"+
			"NOSTRICTDEF // 4\n"+ //4
			"#endif\n"+
			"\n"+
			"// end comment\n";
		assertEquals(mainText1, getCurrentText(modelTu));
		assertEquals(headerText1b, getCurrentText(headerTu));
		
	}

	public void testMainAndHeaderDocumentReorder1() {
		// this should be a no-op since the nodes are already in order, 
		// but if the view asks for it, the synchronizer will obey...
		String mainText2 =
			"START RESOURCE outer.rss\n"+
			"\tTARGETPATH a\n"+
			"END\n"+
			"\n"+
			"#include \"header.mmh\"\n"+
			"";
		String headerText2 = "" +
				"START RESOURCE first.rss\n"+
				"\tTARGETPATH a\n"+
				"END\n"+
				"\n"+
				"START RESOURCE second.rss\n"+
				"\tTARGETPATH b\n"+
				"END\n"+
				"";
				
		parserConfig.getFilesystem().put(header1Path.toOSString(), headerText2);

		doParse(mainText2);

		IViewModification[] viewModifications = new IViewModification[] {
					makeChange(viewTu.getNodes().get(0)),
					makeChange(viewTu.getNodes().get(2)),
					makeReorder(new IASTNode[] { viewTu.getNodes().get(0), viewTu.getNodes().get(2) }),
				};
		applyModificationsNoProblems(viewModifications);
	
		String mainText2b =
			"START RESOURCE outer.rss\n"+
			"\tTARGETPATH a\n"+
			"END\n"+
			"\n"+
			"#include \"header.mmh\"\n"+
			"START RESOURCE second.rss\n"+
			"\tTARGETPATH b\n"+
			"END\n"+
			"";

		String headerText2b = 
			"START RESOURCE first.rss\n"+
			"\tTARGETPATH a\n"+
			"END\n"+
			"\n"+
			"";
		assertEquals(headerText2b, getCurrentText(headerTu));
		assertEquals(mainText2b, getCurrentText(modelTu));
		
	}
	public void testMainDocumentReorder1() {
		IASTMMPSingleArgumentStatement srcpathStmt = (IASTMMPSingleArgumentStatement) 
			viewTu.getNodes().get(MT1_SOURCEPATH);
		IASTMMPListArgumentStatement srcStmt = (IASTMMPListArgumentStatement) 
			viewTu.getNodes().get(MT1_SOURCE);
		IASTMMPUidStatement uidStmt =
			(IASTMMPUidStatement) viewTu.getNodes().get(MT1_UID);

		applyModificationsNoProblems(new IViewModification[] {
				makeReorder(new IASTNode[] {
						uidStmt, srcpathStmt, srcStmt	
					})
			});
		
		String mainText1b = 
			"// comment\n" + 
			"UID 0x2 0x3 // 7\n" + 
			"SOURCEPATH ..\\src // 0\n" + 
			"\n" + 
			"BASEADDRESS 0x6000 // 2\n" + 
			"\n" + 
			"#include \"header.mmh\"\n" + 
			"\n" + 
			"#ifdef SECURE_UID\n" + 
			"SECUREID SECURE_UID // 6\n" + 
			"#endif\n" + 
			"\n" + 
			"SOURCE file1.cpp file2.cpp // 1\n" + 
			"\n" + 
			"// end comment\n" + 
			"";
		assertEquals(mainText1b, getCurrentText(modelTu));
		assertEquals(headerText1, getCurrentText(headerTu));
	}

	public void testMainDocumentReorder2() {
		ISynchronizerResults results = applyModifications(new IViewModification[] {
				makeReorder(new IASTNode[] {
						viewTu.getNodes().get(MT1_SECUREID),
						viewTu.getNodes().get(MT1_BASEADDRESS)
					})
			});
		assertEquals(1, results.getMessages().size());
		checkMessages(results.getMessages());
		System.out.println(results.getMessages().get(0));
		
		String mainText1b = 
			"// comment\n" + 
			"SOURCEPATH ..\\src // 0\n" + 
			"SOURCE file1.cpp file2.cpp // 1\n" + 
			"\n" + 
			"BASEADDRESS 0x6000 // 2\n" + 
			"\n" + 
			"#include \"header.mmh\"\n" + 
			"\n" + 
			"#ifdef SECURE_UID\n" + 
			"SECUREID SECURE_UID // 6\n" + 
			"#endif\n" + 
			"\n" + 
			"UID 0x2 0x3 // 7\n" + 
			"\n" + 
			"// end comment\n" + 
			"";
		assertEquals(mainText1b, getCurrentText(modelTu));
		assertEquals(headerText1, getCurrentText(headerTu));
	}

	public void testHeaderDocumentReorder1() {
		applyModificationsNoProblems(new IViewModification[] {
				makeReorder(new IASTNode[] {
						viewTu.getNodes().get(HT1_VENDORID),
						viewTu.getNodes().get(HT1_NOSTRICTDEF)	
					})
			});
		
		String headerText1b = 
			"// comment\n"+
			"#define SECURE_UID 1\n"+
			"\n"+
			"DEFFILE myfile // 3\n"+ //3
			"\n"+
			"#if defined(SECURE_UID)\n"+
			"VENDORID 0 // 5\n"+//5
			"NOSTRICTDEF // 4\n"+ //4
			"#endif\n"+
			"\n"+
			"// end comment\n";
		assertEquals(mainText1, getCurrentText(modelTu));
		assertEquals(headerText1b, getCurrentText(headerTu));
	}

	/** One is inside a conditional so won't be moved */
	public void testHeaderDocumentReorder2() {
		ISynchronizerResults results = applyModifications(new IViewModification[] {
				makeReorder(new IASTNode[] {
						viewTu.getNodes().get(HT1_VENDORID),
						viewTu.getNodes().get(HT1_DEFFILE)	
					})
			});
		assertEquals(1, results.getMessages().size());
		checkMessages(results.getMessages());
		System.out.println(results.getMessages().get(0));

		String headerText1b = 
			"// comment\n" + 
			"#define SECURE_UID 1\n" + 
			"\n" + 
			"DEFFILE myfile // 3\n" + 
			"\n" + 
			"#if defined(SECURE_UID)\n" + 
			"NOSTRICTDEF // 4\n" + 
			"VENDORID 0 // 5\n" + 
			"#endif\n" + 
			"\n" + 
			"// end comment\n" + 
			"";
		assertEquals(mainText1, getCurrentText(modelTu));
		assertEquals(headerText1b, getCurrentText(headerTu));
	}

	/** Two inside conditionals can be moved */
	public void testHeaderDocumentReorder3() {
		applyModificationsNoProblems(new IViewModification[] {
				makeReorder(new IASTNode[] {
						viewTu.getNodes().get(HT1_VENDORID),
						viewTu.getNodes().get(HT1_NOSTRICTDEF),
					})
			});
		
		String headerText1b = 
			"// comment\n"+
			"#define SECURE_UID 1\n"+
			"\n"+
			"DEFFILE myfile // 3\n"+ //3
			"\n"+
			"#if defined(SECURE_UID)\n"+
			"VENDORID 0 // 5\n"+//5
			"NOSTRICTDEF // 4\n"+ //4
			"#endif\n"+
			"\n"+
			"// end comment\n" +
			"";
		assertEquals(mainText1, getCurrentText(modelTu));
		assertEquals(headerText1b, getCurrentText(headerTu));
	}
	
	public void testGenericAdd1() {
		IASTMMPListArgumentStatement srcStmt =
			ASTMMPFactory.createMMPListArgumentStatement("SOURCE", 
					new String[] { "file3.cpp" });
		
		applyModificationsNoProblems(new IViewModification[] {
			makeAdd(viewTu, srcStmt)	
		});
		
		String mainText1b = 
			"// comment\n"+
			"SOURCEPATH ..\\src // 0\n"+
			"SOURCE file1.cpp file2.cpp // 1\n"+
			"\n"+
			"BASEADDRESS 0x6000 // 2\n"+
			"\n"+
			"#include \"header.mmh\"\n"+
			"\n"+
			"#ifdef SECURE_UID\n"+
			"SECUREID SECURE_UID // 6\n"+
			"#endif\n"+
			"\n"+
			"UID 0x2 0x3 // 7\n"+
			"\n"+
			"// end comment\n"+
			"SOURCE file3.cpp\n"+
			"";
		assertEquals(mainText1b, getCurrentText(modelTu));
		assertEquals(headerText1, getCurrentText(headerTu));
	}

	public void testGenericAdd2() {
		IASTMMPStartBlockStatement startStmt = ASTMMPFactory.createMMPStartBlockStatement("RESOURCE", 
				Arrays.asList(new String[] { "foo.rss" }));
		startStmt.getList().add(
				ASTMMPFactory.createMMPSingleArgumentStatement("TARGETPATH", "\\sys\\bin"));
		startStmt.getList().add(
				ASTMMPFactory.createMMPFlagStatement("HEADERONLY"));
		
		applyModificationsNoProblems(new IViewModification[] {
			makeAdd(viewTu, startStmt)	
		});
		
		String mainText1b = 
			"// comment\n"+
			"SOURCEPATH ..\\src // 0\n"+
			"SOURCE file1.cpp file2.cpp // 1\n"+
			"\n"+
			"BASEADDRESS 0x6000 // 2\n"+
			"\n"+
			"#include \"header.mmh\"\n"+
			"\n"+
			"#ifdef SECURE_UID\n"+
			"SECUREID SECURE_UID // 6\n"+
			"#endif\n"+
			"\n"+
			"UID 0x2 0x3 // 7\n"+
			"\n"+
			"// end comment\n"+
			"START RESOURCE foo.rss\n"+
			"\tTARGETPATH \\sys\\bin\n"+
			"\tHEADERONLY\n"+
			"END\n"+
			"";
		assertEquals(mainText1b, getCurrentText(modelTu));
		assertEquals(headerText1, getCurrentText(headerTu));
	}
	
	public void testGenericAddAndReorder() {
		String mainText2 = 
			"// comment\n"+
			"SOURCEPATH ..\\src // 0\n"+ //0
			"SOURCE file1.cpp file2.cpp // 1\n"+ //1
			"\n"+
			"START RESOURCE foo.rss\n"+
			"\tTARGETPATH \\out\n"+
			"END\n"+
			"\n"+
			"// end comment\n";

		doParse(mainText2);
		
		// a new resource
		IASTMMPStartBlockStatement startStmt = ASTMMPFactory.createMMPStartBlockStatement("RESOURCE", 
				Arrays.asList(new String[] { "bar.rss" }));
		startStmt.getList().add(
				ASTMMPFactory.createMMPSingleArgumentStatement("TARGETPATH", "\\sys\\bin"));
		startStmt.getList().add(
				ASTMMPFactory.createMMPFlagStatement("HEADERONLY"));
		
		applyModificationsNoProblems(new IViewModification[] {
			makeAdd(viewTu, startStmt),
			makeReorder(new IASTNode[] { startStmt, viewTu.getNodes().get(2) } )
		});
		
		String mainText2b = 
			"// comment\n" + 
			"SOURCEPATH ..\\src // 0\n" + 
			"SOURCE file1.cpp file2.cpp // 1\n" + 
			"\n" + 
			"START RESOURCE bar.rss\n" + 
			"	TARGETPATH \\sys\\bin\n" + 
			"	HEADERONLY\n" + 
			"END\n" + 
			"START RESOURCE foo.rss\n" + 
			"	TARGETPATH \\out\n" + 
			"\n" + 
			"// end comment\n" + 
			"END\n" + 
			"";
		assertEquals(mainText2b, getCurrentText(modelTu));
		assertEquals(headerText1, getCurrentText(headerTu));
	}
	
	public void testMainDocumentAdd1() {
		IASTMMPListArgumentStatement srcStmt =
			ASTMMPFactory.createMMPListArgumentStatement("SOURCE", 
					new String[] { "file3.cpp" });
		
		applyModificationsNoProblems(new IViewModification[] {
			makeAdd(modelTu, srcStmt)	
		});
		
		String mainText1b = 
			"// comment\n"+
			"SOURCEPATH ..\\src // 0\n"+
			"SOURCE file1.cpp file2.cpp // 1\n"+
			"\n"+
			"BASEADDRESS 0x6000 // 2\n"+
			"\n"+
			"#include \"header.mmh\"\n"+
			"\n"+
			"#ifdef SECURE_UID\n"+
			"SECUREID SECURE_UID // 6\n"+
			"#endif\n"+
			"\n"+
			"UID 0x2 0x3 // 7\n"+
			"\n"+
			"// end comment\n"+
			"SOURCE file3.cpp\n"+
			"";
		assertEquals(mainText1b, getCurrentText(modelTu));
		assertEquals(headerText1, getCurrentText(headerTu));
	}

	public void testMainDocumentAdd2() {
		IASTMMPStartBlockStatement startStmt = ASTMMPFactory.createMMPStartBlockStatement("RESOURCE", 
				Arrays.asList(new String[] { "foo.rss" }));
		startStmt.getList().add(
				ASTMMPFactory.createMMPSingleArgumentStatement("TARGETPATH", "\\sys\\bin"));
		startStmt.getList().add(
				ASTMMPFactory.createMMPFlagStatement("HEADERONLY"));
		
		applyModificationsNoProblems(new IViewModification[] {
			makeAdd(modelTu, startStmt)	
		});
		
		String mainText1b = 
			"// comment\n"+
			"SOURCEPATH ..\\src // 0\n"+
			"SOURCE file1.cpp file2.cpp // 1\n"+
			"\n"+
			"BASEADDRESS 0x6000 // 2\n"+
			"\n"+
			"#include \"header.mmh\"\n"+
			"\n"+
			"#ifdef SECURE_UID\n"+
			"SECUREID SECURE_UID // 6\n"+
			"#endif\n"+
			"\n"+
			"UID 0x2 0x3 // 7\n"+
			"\n"+
			"// end comment\n"+
			"START RESOURCE foo.rss\n"+
			"\tTARGETPATH \\sys\\bin\n"+
			"\tHEADERONLY\n"+
			"END\n"+
			"";
		assertEquals(mainText1b, getCurrentText(modelTu));
		assertEquals(headerText1, getCurrentText(headerTu));
	}
	
	public void testMainDocumentAddAndReorder() {
		String mainText2 = 
			"// comment\n"+
			"SOURCEPATH ..\\src // 0\n"+ //0
			"SOURCE file1.cpp file2.cpp // 1\n"+ //1
			"\n"+
			"START RESOURCE foo.rss\n"+
			"\tTARGETPATH \\out\n"+
			"END\n"+
			"\n"+
			"// end comment\n";

		doParse(mainText2);
		
		// a new resource
		IASTMMPStartBlockStatement startStmt = ASTMMPFactory.createMMPStartBlockStatement("RESOURCE", 
				Arrays.asList(new String[] { "bar.rss" }));
		startStmt.getList().add(
				ASTMMPFactory.createMMPSingleArgumentStatement("TARGETPATH", "\\sys\\bin"));
		startStmt.getList().add(
				ASTMMPFactory.createMMPFlagStatement("HEADERONLY"));
		
		applyModificationsNoProblems(new IViewModification[] {
			makeAdd(modelTu, startStmt),
			makeReorder(new IASTNode[] { startStmt, viewTu.getNodes().get(2) } )
		});
		
		String mainText2b = 
			"// comment\n" + 
			"SOURCEPATH ..\\src // 0\n" + 
			"SOURCE file1.cpp file2.cpp // 1\n" + 
			"\n" + 
			"START RESOURCE bar.rss\n" + 
			"	TARGETPATH \\sys\\bin\n" + 
			"	HEADERONLY\n" + 
			"END\n" + 
			"START RESOURCE foo.rss\n" + 
			"	TARGETPATH \\out\n" + 
			"\n" + 
			"// end comment\n" + 
			"END\n" + 
			"";
		assertEquals(mainText2b, getCurrentText(modelTu));
		assertEquals(headerText1, getCurrentText(headerTu));
	}

	
	public void testMainDocumentAddInOrder1() {
		IASTMMPStartBlockStatement startStmt = ASTMMPFactory.createMMPStartBlockStatement("RESOURCE", 
				Arrays.asList(new String[] { "foo.rss" }));
		startStmt.getList().add(
				ASTMMPFactory.createMMPSingleArgumentStatement("TARGETPATH", "\\sys\\bin"));
		startStmt.getList().add(
				ASTMMPFactory.createMMPFlagStatement("HEADERONLY"));
		
		applyModificationsNoProblems(new IViewModification[] {
			makeAdd(modelTu, startStmt, null, viewTu.getNodes().get(MT1_BASEADDRESS))	
		});
		
		String mainText1b = 
			"// comment\n"+
			"SOURCEPATH ..\\src // 0\n"+
			"SOURCE file1.cpp file2.cpp // 1\n"+
			"\n"+
			"BASEADDRESS 0x6000 // 2\n"+
			"START RESOURCE foo.rss\n"+
			"\tTARGETPATH \\sys\\bin\n"+
			"\tHEADERONLY\n"+
			"END\n"+
			"\n"+
			"#include \"header.mmh\"\n"+
			"\n"+
			"#ifdef SECURE_UID\n"+
			"SECUREID SECURE_UID // 6\n"+
			"#endif\n"+
			"\n"+
			"UID 0x2 0x3 // 7\n"+
			"\n"+
			"// end comment\n"+
			"";
		assertEquals(mainText1b, getCurrentText(modelTu));
		assertEquals(headerText1, getCurrentText(headerTu));
	}

	public void testMainDocumentAddInOrder2() {
		IASTMMPStartBlockStatement startStmt = ASTMMPFactory.createMMPStartBlockStatement("RESOURCE", 
				Arrays.asList(new String[] { "foo.rss" }));
		startStmt.getList().add(
				ASTMMPFactory.createMMPSingleArgumentStatement("TARGETPATH", "\\sys\\bin"));
		startStmt.getList().add(
				ASTMMPFactory.createMMPFlagStatement("HEADERONLY"));
		
		applyModificationsNoProblems(new IViewModification[] {
			makeAdd(modelTu, startStmt, viewTu.getNodes().get(MT1_BASEADDRESS), null)	
		});
		
		String mainText1b = 
			"// comment\n"+
			"SOURCEPATH ..\\src // 0\n"+
			"SOURCE file1.cpp file2.cpp // 1\n"+
			"\n"+
			"START RESOURCE foo.rss\n"+
			"\tTARGETPATH \\sys\\bin\n"+
			"\tHEADERONLY\n"+
			"END\n"+
			"BASEADDRESS 0x6000 // 2\n"+
			"\n"+
			"#include \"header.mmh\"\n"+
			"\n"+
			"#ifdef SECURE_UID\n"+
			"SECUREID SECURE_UID // 6\n"+
			"#endif\n"+
			"\n"+
			"UID 0x2 0x3 // 7\n"+
			"\n"+
			"// end comment\n"+
			"";
		assertEquals(mainText1b, getCurrentText(modelTu));
		assertEquals(headerText1, getCurrentText(headerTu));
	}
	public void testMainDocumentChangeAndReorder() {
		String mainText2 = 
			"// comment\n"+
			"SOURCEPATH ..\\src // 0\n"+ //0
			"SOURCE file1.cpp file2.cpp // 1\n"+ //1
			"\n"+
			"START RESOURCE foo.rss\n"+ // 2
			"\tTARGETPATH \\out\n"+
			"END\n"+
			"\n"+
			"// end comment\n";

		doParse(mainText2);
		
		// change the list
		IASTMMPListArgumentStatement listStmt = (IASTMMPListArgumentStatement) viewTu.getNodes().get(1);
		listStmt.getList().add(ASTFactory.createPreprocessorLiteralTextNode("baz.cpp"));
		
		applyModificationsNoProblems(new IViewModification[] {
			makeChange(listStmt),
			makeReorder(new IASTNode[] { viewTu.getNodes().get(2), listStmt } )
		});
		
		String mainText2b = 
			"// comment\n" + 
			"SOURCEPATH ..\\src // 0\n" + 
			"START RESOURCE foo.rss\n" + 
			"\n" + 
			"	TARGETPATH \\out\n" + 
			"END\n" + 
			"SOURCE file1.cpp file2.cpp baz.cpp // 1\n" + 
			"\n" + 
			"// end comment\n" + 
			"";
		assertEquals(mainText2b, getCurrentText(modelTu));
		assertEquals(headerText1, getCurrentText(headerTu));
	}
	
	public void testHeaderDocumentAdd1() {
		IASTMMPListArgumentStatement srcStmt =
			ASTMMPFactory.createMMPListArgumentStatement("SOURCE", 
					new String[] { "file3.cpp" });
		
		applyModificationsNoProblems(new IViewModification[] {
			makeAdd(headerTu, srcStmt)	
		});

		String headerText1b = 
			"// comment\n"+
			"#define SECURE_UID 1\n"+
			"\n"+
			"DEFFILE myfile // 3\n"+ //3
			"\n"+
			"#if defined(SECURE_UID)\n"+
			"NOSTRICTDEF // 4\n"+ //4
			"VENDORID 0 // 5\n"+ //5
			"#endif\n"+
			"\n"+
			"// end comment\n"+
			"SOURCE file3.cpp\n"+
			"";
			

		assertEquals(mainText1, getCurrentText(modelTu));
		assertEquals(headerText1b, getCurrentText(headerTu));
	}
	
	public void testHeaderDocumentAdd2() {
		IASTMMPStartBlockStatement startStmt = ASTMMPFactory.createMMPStartBlockStatement("RESOURCE", 
				Arrays.asList(new String[] { "foo.rss" }));
		startStmt.getList().add(
				ASTMMPFactory.createMMPSingleArgumentStatement("TARGETPATH", "\\sys\\bin"));
		startStmt.getList().add(
				ASTMMPFactory.createMMPFlagStatement("HEADERONLY"));
		
		applyModificationsNoProblems(new IViewModification[] {
			makeAdd(headerTu, startStmt)	
		});
		
		String headerText1b = 
			"// comment\n"+
			"#define SECURE_UID 1\n"+
			"\n"+
			"DEFFILE myfile // 3\n"+ //3
			"\n"+
			"#if defined(SECURE_UID)\n"+
			"NOSTRICTDEF // 4\n"+ //4
			"VENDORID 0 // 5\n"+ //5
			"#endif\n"+
			"\n"+
			"// end comment\n"+
			"START RESOURCE foo.rss\n"+
			"\tTARGETPATH \\sys\\bin\n"+
			"\tHEADERONLY\n"+
			"END\n"+
			"";
			

		assertEquals(mainText1, getCurrentText(modelTu));
		assertEquals(headerText1b, getCurrentText(headerTu));
	}
	
	public void testHeaderDocumentMultiChange1() {
		IASTMMPStartBlockStatement startStmt = ASTMMPFactory.createMMPStartBlockStatement("RESOURCE", 
				Arrays.asList(new String[] { "foo.rss" }));
		startStmt.getList().add(
				ASTMMPFactory.createMMPSingleArgumentStatement("TARGETPATH", "\\sys\\bin"));
		startStmt.getList().add(
				ASTMMPFactory.createMMPFlagStatement("HEADERONLY"));

		IASTMMPSingleArgumentStatement stmt = (IASTMMPSingleArgumentStatement) 
		viewTu.getNodes().get(HT1_VENDORID);
		stmt.getArgument().setValue("0x123");
	
		applyModificationsNoProblems(new IViewModification[] {
			makeAdd(headerTu, startStmt),
			makeChange(stmt),
			makeDelete(viewTu.getNodes().get(HT1_DEFFILE))
		});
		
		String headerText1b = 
			"// comment\n"+
			"#define SECURE_UID 1\n"+
			"\n"+
			"\n"+
			"#if defined(SECURE_UID)\n"+
			"NOSTRICTDEF // 4\n"+ //4
			"VENDORID 0x123 // 5\n"+ //5
			"#endif\n"+
			"\n"+
			"// end comment\n"+
			"START RESOURCE foo.rss\n"+
			"\tTARGETPATH \\sys\\bin\n"+
			"\tHEADERONLY\n"+
			"END\n"+
			"";
			

		assertEquals(mainText1, getCurrentText(modelTu));
		assertEquals(headerText1b, getCurrentText(headerTu));
	}

	public void testBothDocumentMultiChange1() {
		IASTMMPStartBlockStatement startStmt = ASTMMPFactory.createMMPStartBlockStatement("RESOURCE", 
				Arrays.asList(new String[] { "foo.rss" }));
		startStmt.getList().add(
				ASTMMPFactory.createMMPSingleArgumentStatement("TARGETPATH", "\\sys\\bin"));
		startStmt.getList().add(
				ASTMMPFactory.createMMPFlagStatement("HEADERONLY"));

		IASTMMPSingleArgumentStatement stmt1 = (IASTMMPSingleArgumentStatement) 
			viewTu.getNodes().get(HT1_VENDORID);
		stmt1.getArgument().setValue("0x123");

		IASTMMPUidStatement stmt2 = (IASTMMPUidStatement) 
			viewTu.getNodes().get(MT1_UID);
		stmt2.setUid3(null);

		applyModificationsNoProblems(new IViewModification[] {
			makeAdd(modelTu, startStmt),
			makeChange(stmt1),
			makeDelete(viewTu.getNodes().get(HT1_DEFFILE)),
			makeChange(stmt2),
		});
		
		String mainText1b = 
			"// comment\n"+
			"SOURCEPATH ..\\src // 0\n"+
			"SOURCE file1.cpp file2.cpp // 1\n"+
			"\n"+
			"BASEADDRESS 0x6000 // 2\n"+
			"\n"+
			"#include \"header.mmh\"\n"+
			"\n"+
			"#ifdef SECURE_UID\n"+
			"SECUREID SECURE_UID // 6\n"+
			"#endif\n"+
			"\n"+
			"UID 0x2\n"+ 
			"\n"+
			"// end comment\n"+
			"START RESOURCE foo.rss\n"+
			"\tTARGETPATH \\sys\\bin\n"+
			"\tHEADERONLY\n"+
			"END\n"+
			"";
		String headerText1b = 
			"// comment\n"+
			"#define SECURE_UID 1\n"+
			"\n"+
			"\n"+
			"#if defined(SECURE_UID)\n"+
			"NOSTRICTDEF // 4\n"+ //4
			"VENDORID 0x123 // 5\n"+ //5
			"#endif\n"+
			"\n"+
			"// end comment\n"+
			"";
			

		assertEquals(mainText1b, getCurrentText(modelTu));
		assertEquals(headerText1b, getCurrentText(headerTu));
	}
	
	public void testBothDocumentMultiChange2() {
		String mainText2 = 
			"// comment\n"+
			"START RESOURCE foo.rss // a resource\n"+ //0
			"LANG 01\n"+
			"END\n"+
			"#include \"header.mmh\"\n"+
			"\n"+
			"// end comment\n";
		
		String headerText2 = 
			"//comment\n"+
			"START RESOURCE header1.rss // a resource\n"+ // 1
			"HEADER\n"+
			"END\n"+
			"START RESOURCE header2.rss // a resource\n"+ // 2
			"HEADER\n"+
			"END\n"+
			"//comment\n"+
			"";
		
		parserConfig.getFilesystem().put(header1Path.toOSString(), headerText2);

		doParse(mainText2);

		// delete the second entry, and add another one
		
		IASTMMPStartBlockStatement startStmt = ASTMMPFactory.createMMPStartBlockStatement("RESOURCE", 
				Arrays.asList(new String[] { "bar.rss" }));
		startStmt.getList().add(
				ASTMMPFactory.createMMPSingleArgumentStatement("TARGETPATH", "\\sys\\bin"));
		startStmt.getList().add(
				ASTMMPFactory.createMMPFlagStatement("HEADERONLY"));

		applyModificationsNoProblems(new IViewModification[] {
			makeDelete(viewTu.getNodes().get(1)),
			makeAdd(modelTu, startStmt),
			makeReorder(new IASTNode[] { viewTu.getNodes().get(0), startStmt, viewTu.getNodes().get(2) } )
		});
		
		String mainText1b = 
			"// comment\n"+
			"START RESOURCE foo.rss // a resource\n"+ //0
			"LANG 01\n"+
			"END\n"+
			"#include \"header.mmh\"\n"+
			"\n"+
			"// end comment\n"+
			"START RESOURCE bar.rss\n"+
			"\tTARGETPATH \\sys\\bin\n"+
			"\tHEADERONLY\n"+
			"END\n"+
			"START RESOURCE header2.rss // a resource\n"+ // 2
			"HEADER\n"+
			"END\n"+			
			"";
		String headerText1b = 
			"//comment\n"+
			
			"//comment\n"+
			"";
			

		assertEquals(mainText1b, getCurrentText(modelTu));
		assertEquals(headerText1b, getCurrentText(headerTu));
	}
	public void testBothDocumentReorder1() {
		ISynchronizerResults results = applyModifications(new IViewModification[] {
			makeReorder(new IASTNode[] {
				viewTu.getNodes().get(MT1_BASEADDRESS),	
				viewTu.getNodes().get(HT1_DEFFILE),	
				viewTu.getNodes().get(MT1_UID),	
				viewTu.getNodes().get(MT1_SOURCEPATH),	
				viewTu.getNodes().get(MT1_SOURCE),	
				viewTu.getNodes().get(HT1_VENDORID),	
			})
		});
		assertEquals(1, results.getMessages().size());
		checkMessages(results.getMessages());
		System.out.println(results.getMessages().get(0));

			
		String mainText1b = 
			"// comment\n" + 
			"BASEADDRESS 0x6000 // 2\n" + 
			"DEFFILE myfile // 3\n" + 
			"\n" + 
			"UID 0x2 0x3 // 7\n" + 
			"\n" + 
			"#include \"header.mmh\"\n" + 
			"\n" + 
			"#ifdef SECURE_UID\n" + 
			"SECUREID SECURE_UID // 6\n" + 
			"#endif\n" + 
			"\n" + 
			"SOURCEPATH ..\\src // 0\n" + 
			"\n" + 
			"// end comment\n" + 
			"SOURCE file1.cpp file2.cpp // 1\n" + 
			"";

		
		String headerText1b = 
			"// comment\n"+
			"#define SECURE_UID 1\n"+
			"\n"+
			"\n"+
			"#if defined(SECURE_UID)\n"+
			"NOSTRICTDEF // 4\n"+ //4
			"VENDORID 0 // 5\n"+
			"#endif\n"+
			"\n"+
			"// end comment\n";

		assertEquals(headerText1b, getCurrentText(headerTu));
		assertEquals(mainText1b, getCurrentText(modelTu));
	}
	
	public void testMainDocumentChangeBlock1() {
		String mainText2 = 
			"// comment\n"+
			"SOURCEPATH ..\\src\n"+ //0
			"SOURCE file1.cpp file2.cpp\n"+ //1
			"\n"+
			"START RESOURCE foo.rss // a resource\n"+
			"\tTARGETPATH \\out // not default\n"+
			"END\n"+
			"\n"+
			"// end comment\n";

		doParse(mainText2);
		
		IASTMMPStartBlockStatement startStmt = (IASTMMPStartBlockStatement) viewTu.getNodes().get(2);
		startStmt.getBlockArguments().set(0, ASTFactory.createPreprocessorLiteralTextNode("bar.rss"));
		IASTMMPSingleArgumentStatement tp = (IASTMMPSingleArgumentStatement) startStmt.getList().get(0);
		tp.getArgument().setValue("\\sys\\bin");
		startStmt.getList().add(ASTMMPFactory.createMMPFlagStatement("HEADER"));
		
		applyModificationsNoProblems(new IViewModification[] {
			makeChange(viewTu, startStmt),
		});
		
		String mainText2b = 
			"// comment\n"+
			"SOURCEPATH ..\\src\n"+ //0
			"SOURCE file1.cpp file2.cpp\n"+ //1
			"\n"+
			"START RESOURCE bar.rss // a resource\n"+
			"\tTARGETPATH \\sys\\bin // not default\n"+
			"HEADER\n"+  // fix this indentation
			"END\n"+
			"\n"+
			"// end comment\n"+
			"";
		assertEquals(mainText2b, getCurrentText(modelTu));
		assertEquals(headerText1, getCurrentText(headerTu));
	}
	
	public void testHeaderDocumentChangeBlock1() {
		_testHeaderDocumentChangeBlock1(false);
		_testHeaderDocumentChangeBlock1(true);
	}

	private void _testHeaderDocumentChangeBlock1(boolean changeParent) {
		String headerText2 = 
			"// comment\n"+
			"SOURCEPATH ..\\src\n"+ //0
			"SOURCE file1.cpp file2.cpp\n"+ //1
			"\n"+
			"START RESOURCE foo.rss // a resource\n"+
			"\tTARGETPATH \\out // not default\n"+
			"END\n"+
			"\n"+
			"// end comment\n";
		parserConfig.getFilesystem().put(header1Path.toOSString(), headerText2);

		String mainText2 = 
			"#include \"header.mmh\"\n";
		doParse(mainText2);
		
		IASTMMPStartBlockStatement startStmt = (IASTMMPStartBlockStatement) viewTu.getNodes().get(2);
		startStmt.getBlockArguments().set(0, ASTFactory.createPreprocessorLiteralTextNode("bar.rss"));
		IASTMMPSingleArgumentStatement tp = (IASTMMPSingleArgumentStatement) startStmt.getList().get(0);
		tp.getArgument().setValue("\\sys\\bin");
		startStmt.getList().add(ASTMMPFactory.createMMPFlagStatement("HEADER"));
		
		applyModificationsNoProblems(new IViewModification[] {
				(changeParent ? 
						makeChange(headerTu, startStmt) :
				 makeChange(startStmt)),
		});
		
		String headerText2b = 
			"// comment\n"+
			"SOURCEPATH ..\\src\n"+ //0
			"SOURCE file1.cpp file2.cpp\n"+ //1
			"\n"+
			"START RESOURCE bar.rss // a resource\n"+
			"\tTARGETPATH \\sys\\bin // not default\n"+
			"HEADER\n"+  //  fix this indentation
			"END\n"+
			"\n"+
			"// end comment\n"+
			"";
		assertEquals(mainText2, getCurrentText(modelTu));
		assertEquals(headerText2b, getCurrentText(headerTu));
		
	}
	
	/** This case is not supported yet. */
	public void unsupported_testMainDocumentChangeSubBlock1() {
		String mainText2 = 
			"SOURCEPATH ..\\src\n"+ //0
			"SOURCE file1.cpp file2.cpp\n"+ //1
			"\n"+
			"START RESOURCE foo.rss // a resource\n"+
			"\tTARGETPATH \\out // not default\n"+
			"END\n"+
			"\n"+
			"// end comment\n";

		doParse(mainText2);
		
		IASTMMPStartBlockStatement startStmt = (IASTMMPStartBlockStatement) viewTu.getNodes().get(2);
		startStmt.getBlockArguments().set(0, ASTFactory.createPreprocessorLiteralTextNode("bar.rss"));
		IASTMMPSingleArgumentStatement tp = (IASTMMPSingleArgumentStatement) startStmt.getList().get(0);
		tp.getArgument().setValue("\\sys\\bin");
		IASTMMPFlagStatement headerStmt = ASTMMPFactory.createMMPFlagStatement("HEADER");
		
		applyModificationsNoProblems(new IViewModification[] {
				makeAdd(startStmt, headerStmt),
				makeChange(startStmt, tp),
		});
		
		String mainText2b = 
			"// comment\n"+
			"SOURCEPATH ..\\src\n"+ //0
			"SOURCE file1.cpp file2.cpp\n"+ //1
			"\n"+
			"START RESOURCE bar.rss // a resource\n"+
			"\tTARGETPATH \\sys\\bin // not default\n"+
			"HEADER\n"+  // fix this indentation
			"END\n"+
			"\n"+
			"// end comment\n"+
			"";
		assertEquals(mainText2b, getCurrentText(modelTu));
		assertEquals(headerText1, getCurrentText(headerTu));
	}
	
	public void testChangeIncludedFile() {
		String mainText2 = 
			"// comment\n"+
			"START RESOURCE foo.rss // a resource\n"+
			"#include \"header.mmh\"\n"+
			"END\n"+
			"\n"+
			"// end comment\n";
		
		String headerText2 = 
			"//comment\n"+
			"TARGETPATH foo\n"+
			"//comment\n"+
			"";
		
		parserConfig.getFilesystem().put(header1Path.toOSString(), headerText2);

		doParse(mainText2);
		
		IASTMMPStartBlockStatement startStmt = (IASTMMPStartBlockStatement) viewTu.getNodes().get(0);
		IASTMMPSingleArgumentStatement tp = (IASTMMPSingleArgumentStatement) startStmt.getList().get(0);
		tp.getArgument().setValue("\\sys\\bin");
		IASTMMPFlagStatement headerStmt = ASTMMPFactory.createMMPFlagStatement("HEADER");
		startStmt.getStatements().add(headerStmt);
		
		applyModificationsNoProblems(new IViewModification[] {
				makeChange(viewTu, startStmt),
		});
		
		String mainText2b = 
			"// comment\n" + 
			"#if 0\n" + 
			"START RESOURCE foo.rss // a resource\n" + 
			"#include \"header.mmh\"\n" + 
			"END\n" + 
			"#endif\n" + 
			"START RESOURCE foo.rss\n" + 
			"TARGETPATH \\sys\\bin\n" + 
			"	HEADER\n" + 
			"END\n" + 
			"\n" + 
			"// end comment\n" + 
			"";
		String headerText2b = 
			"//comment\n"+
			"TARGETPATH foo\n"+	// not changed since #if 0...
			"//comment\n"+
			"";
		assertEquals(mainText2b, getCurrentText(modelTu));
		assertEquals(headerText2b, getCurrentText(headerTu));
	}

	public void testHeaderDocumentDelete2() {
		String headerText2 = "" +
				"START RESOURCE first.rss\n"+
				"\tTARGETPATH a\n"+
				"END\n"+
				"\n"+
				"START RESOURCE second.rss\n"+
				"\tTARGETPATH b\n"+
				"END\n"+
				"";
				
		parserConfig.getFilesystem().put(header1Path.toOSString(), headerText2);
	
		doParse(mainText1);
	
		IViewModification[] viewModifications = new IViewModification[] {
					makeChange(viewTu.getNodes().get(MT1_BASEADDRESS)),
					makeChange(viewTu.getNodes().get(MT1_SOURCE)),
					makeChange(viewTu.getNodes().get(MT1_SOURCEPATH)),
					makeChange(viewTu.getNodes().get(HT1_FIRST + 1)),
					makeChange(viewTu.getNodes().get(HT1_FIRST + 2)), // mainfile
					makeDelete(viewTu.getNodes().get(HT1_FIRST)),
				};
		applyModificationsNoProblems(viewModifications);
	
		String headerText1b = 
			"\n"+
			"START RESOURCE second.rss\n"+
			"\tTARGETPATH b\n"+
			"END\n"+
			"";
		assertEquals(mainText1, getCurrentText(modelTu));
		assertEquals(headerText1b, getCurrentText(headerTu));
		
	}
}
