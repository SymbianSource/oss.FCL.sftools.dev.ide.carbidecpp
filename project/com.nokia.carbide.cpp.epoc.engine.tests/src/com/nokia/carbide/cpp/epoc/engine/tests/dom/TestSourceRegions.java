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
package com.nokia.carbide.cpp.epoc.engine.tests.dom;

import com.nokia.carbide.cpp.epoc.engine.DocumentFactory;
import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.IViewParserConfiguration;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AcceptedNodesViewFilter;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IViewFilter;
import com.nokia.carbide.cpp.epoc.engine.tests.BaseTest;
import com.nokia.carbide.cpp.epoc.engine.tests.model.dummy.DummyModel;
import com.nokia.carbide.cpp.epoc.engine.tests.model.dummy.DummyView;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IDocumentSourceRegion;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IMultiDocumentSourceRegion;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ISourceRegion;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTUtils;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.DocumentSourceLocation;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.MultiDocumentSourceLocation;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ViewASTBase;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Region;

import java.io.File;
import java.util.Collection;
import java.util.Collections;

public class TestSourceRegions extends BaseTest {

	private IDocument document;
	private Path location;
	private IDocument document2;
	private Path location2;
	private IViewConfiguration viewConfig;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		document = DocumentFactory.createDocument("0123456789");
		location = new Path("c:/files/myfile.txt");
		document2 = DocumentFactory.createDocument("abcdefghi");
		location2 = new Path("c:/files/myfile.inc");
		
		viewConfig = new IViewConfiguration() {

			public IViewFilter getViewFilter() {
				return new AcceptedNodesViewFilter();
			}

			public Collection<IDefine> getMacros() {
				return Collections.EMPTY_LIST;
			}
			
			public IViewParserConfiguration getViewParserConfiguration() {
				return parserConfig;
			}

		};
		
	}
	
	public void testDocument() {
		IDocumentSourceRegion loc = new DocumentSourceLocation(document,
				location, new Region(0, 5));
		loc.validate();
		assertEquals(loc, loc);
		IDocumentSourceRegion[] docs = loc.getDocumentSourceRegions();
		assertEquals(1, docs.length);
	}
	
	public void testMulti() {
		
		IDocumentSourceRegion loc = new DocumentSourceLocation(document,
				location, new Region(0, 5));
		IMultiDocumentSourceRegion mloc = new MultiDocumentSourceLocation();
		mloc.addSourceRegion(loc);
		mloc.validate();
		assertEquals(loc, mloc.getCanonicalSourceRegion());
		assertEquals(loc, mloc.getCanonicalSourceRegion());
		
		assertTrue(mloc.contains(loc));
		
		IDocumentSourceRegion loc2 = new DocumentSourceLocation(document,
				location, new Region(5, 5));
		loc2.validate();
		mloc.addSourceRegion(loc2);
		mloc.validate();
		
		assertTrue(mloc.contains(loc));
		assertTrue(mloc.contains(loc2));
		
		assertFalse(mloc == mloc.getCanonicalSourceRegion());
		IDocumentSourceRegion locAll = (IDocumentSourceRegion) mloc.getCanonicalSourceRegion();
		assertEquals(loc.getDocument(), locAll.getDocument());
		assertEquals(loc.getLocation(), locAll.getLocation());
		assertEquals(new Region(0, 10), locAll.getRegion());
		
	}
	
	public void testUtils() {
		IDocumentSourceRegion loc = new DocumentSourceLocation(document, location, new Region(0, 10));
		IDocumentSourceRegion loc2 = ASTUtils.createDocumentSourceRegion(document, location, new Region(0, 10));
		assertEquals(loc, loc2);
		assertEquals(loc.hashCode(), loc2.hashCode());

		
	}
	public void testEnclosing() {
		// non-adjacent same doc
		IDocumentSourceRegion loc = new DocumentSourceLocation(document,
				location, new Region(0, 3));
		IDocumentSourceRegion loc2 = new DocumentSourceLocation(document,
				location, new Region(9, 1));
		IDocumentSourceRegion locAll = (IDocumentSourceRegion) ASTUtils.getEnclosingRegion(loc, loc2);
		assertEquals(loc.getDocument(), locAll.getDocument());
		assertEquals(loc.getLocation(), locAll.getLocation());
		assertEquals(new Region(0, 10), locAll.getRegion());

		// adjacent same doc
		loc = new DocumentSourceLocation(document,
				location, new Region(3, 4));
		loc2 = new DocumentSourceLocation(document,
				location, new Region(7, 1));
		locAll = (IDocumentSourceRegion) ASTUtils.getEnclosingRegion(loc, loc2);
		assertEquals(loc.getDocument(), locAll.getDocument());
		assertEquals(loc.getLocation(), locAll.getLocation());
		assertEquals(new Region(3, 5), locAll.getRegion());

		// adjacent different doc
		loc = new DocumentSourceLocation(document, location, 
				new Region(8, 2));
		loc2 = new DocumentSourceLocation(document2, location2, 
				new Region(0, 5));
		IMultiDocumentSourceRegion mloc = (IMultiDocumentSourceRegion) ASTUtils.getEnclosingRegion(loc, loc2);
		IDocumentSourceRegion[] documentSourceLocations = mloc.getDocumentSourceRegions();
		assertEquals(2, documentSourceLocations.length);
		assertEquals(loc, documentSourceLocations[0]);
		assertEquals(loc2, documentSourceLocations[1]);

		// non-adjacent different doc, not enclosing file end or start
		loc = new DocumentSourceLocation(document, location, 
				new Region(5, 2));
		loc2 = new DocumentSourceLocation(document2, location2, 
				new Region(0, 7));
		mloc = (IMultiDocumentSourceRegion) ASTUtils.getEnclosingRegion(loc, loc2);
		documentSourceLocations = mloc.getDocumentSourceRegions();
		assertEquals(2, documentSourceLocations.length);
		assertEquals(document, documentSourceLocations[0].getDocument());
		assertEquals(location, documentSourceLocations[0].getLocation());
		assertEquals(new Region(5, 5), documentSourceLocations[0].getRegion());
		assertEquals(document2, documentSourceLocations[1].getDocument());
		assertEquals(location2, documentSourceLocations[1].getLocation());
		assertEquals(new Region(0, 7), documentSourceLocations[1].getRegion());

		// #2
		loc = new DocumentSourceLocation(document, location, 
				new Region(5, 5));
		loc2 = new DocumentSourceLocation(document2, location2, 
				new Region(2, 5));
		mloc = (IMultiDocumentSourceRegion) ASTUtils.getEnclosingRegion(loc, loc2);
		documentSourceLocations = mloc.getDocumentSourceRegions();
		assertEquals(2, documentSourceLocations.length);
		assertEquals(document, documentSourceLocations[0].getDocument());
		assertEquals(location, documentSourceLocations[0].getLocation());
		assertEquals(new Region(5, 5), documentSourceLocations[0].getRegion());
		assertEquals(document2, documentSourceLocations[1].getDocument());
		assertEquals(location2, documentSourceLocations[1].getLocation());
		assertEquals(new Region(0, 7), documentSourceLocations[1].getRegion());

		// #3
		loc = new DocumentSourceLocation(document, location, 
				new Region(5, 2));
		loc2 = new DocumentSourceLocation(document2, location2, 
				new Region(2, 5));
		mloc = (IMultiDocumentSourceRegion) ASTUtils.getEnclosingRegion(loc, loc2);
		documentSourceLocations = mloc.getDocumentSourceRegions();
		assertEquals(2, documentSourceLocations.length);
		assertEquals(document, documentSourceLocations[0].getDocument());
		assertEquals(location, documentSourceLocations[0].getLocation());
		assertEquals(new Region(5, 5), documentSourceLocations[0].getRegion());
		assertEquals(document2, documentSourceLocations[1].getDocument());
		assertEquals(location2, documentSourceLocations[1].getLocation());
		assertEquals(new Region(0, 7), documentSourceLocations[1].getRegion());

	}
	
	public void testDocumentRanges() {
		String incl = 
			"// comment\n"+
			"my entry\n"+
			"\n";
		
		String text =
			"#include \"incl.h\"\n"+
			"// commented\n"+
			"another entry\n";
		
		parserConfig.getFilesystem().put("incl.h", incl);
		
		IDocument document = DocumentFactory.createDocument(text);
		DummyModel model = new DummyModel(new Path("main.c"), document);
		model.parse();
		
		IASTTranslationUnit tu = model.getTranslationUnit();
		testSourceRegions(tu, false);
		
		DummyView view = (DummyView) model.createView(viewConfig);
		IASTTranslationUnit viewTu = view.getFilteredTranslationUnit();
		testSourceRegions(viewTu, true);
		
		assertEquals(5, viewTu.getNodes().size());
		
		ISourceRegion tuRegion = tu.getSourceRegion();
		assertTrue(tuRegion instanceof IDocumentSourceRegion);
		
		IASTTranslationUnit inclTu = (IASTTranslationUnit)
			((ViewASTBase) view).getPreprocessorResults().getTranslationUnitFor(new File("incl.h"));
		assertNotNull(inclTu);
		ISourceRegion inclRegion = inclTu.getSourceRegion();
		assertTrue(inclRegion instanceof IDocumentSourceRegion);
		
		
		ISourceRegion viewTuNodesRegion = viewTu.getNodes().getSourceRegion();
		assertTrue(viewTuNodesRegion instanceof IMultiDocumentSourceRegion);
		ISourceRegion viewTuRegion = viewTu.getSourceRegion();
		assertTrue(viewTuRegion instanceof IMultiDocumentSourceRegion);
	}
	
	public void testDocumentRemoval() {
		IDocumentSourceRegion fullDoc = new DocumentSourceLocation(document, location,
				new Region(0, 100));
		// remove prefix
		IDocumentSourceRegion remove = new DocumentSourceLocation(document, location,
				new Region(0, 10));
		IDocumentSourceRegion remain = (IDocumentSourceRegion) fullDoc.getRegionWithout(remove);
		assertEquals(remain.getDocument(), document);
		assertEquals(remain.getLocation(), location);
		assertEquals(new Region(10, 90), remain.getRegion());
		assertFalse(remain.isEmpty());

		// remove suffix
		remove = new DocumentSourceLocation(document, location,
				new Region(90, 10));
		remain = (IDocumentSourceRegion) fullDoc.getRegionWithout(remove);
		assertEquals(remain.getDocument(), document);
		assertEquals(remain.getLocation(), location);
		assertEquals(new Region(0, 90), remain.getRegion());
		assertFalse(remain.isEmpty());

		// remove whole
		remove = new DocumentSourceLocation(document, location,
				new Region(0, 100));
		remain = (IDocumentSourceRegion) fullDoc.getRegionWithout(remove);
		assertEquals(remain.getDocument(), document);
		assertEquals(remain.getLocation(), location);
		assertEquals(new Region(100, 0), remain.getRegion());
		assertTrue(remain.isEmpty());

		// remove middle
		remove = new DocumentSourceLocation(document, location,
				new Region(10, 80));
		IMultiDocumentSourceRegion multiRemain = (IMultiDocumentSourceRegion) fullDoc.getRegionWithout(remove);
		IDocumentSourceRegion[] remains = multiRemain.getDocumentSourceRegions();
		assertEquals(2, remains.length);
		assertEquals(remains[0].getDocument(), document);
		assertEquals(remains[0].getLocation(), location);
		assertEquals(remains[1].getDocument(), document);
		assertEquals(remains[1].getLocation(), location);
		assertEquals(new Region(0, 10), remains[0].getRegion());
		assertEquals(new Region(90, 10), remains[1].getRegion());
		
		// remove non-contained
		remove = new DocumentSourceLocation(document, location,
				new Region(90, 20));
		remain = (IDocumentSourceRegion) fullDoc.getRegionWithout(remove);
		assertEquals(remain.getDocument(), document);
		assertEquals(remain.getLocation(), location);
		assertEquals(new Region(0, 90), remain.getRegion());
		assertFalse(remain.isEmpty());
		
		IDocumentSourceRegion partDoc = new DocumentSourceLocation(document, location,
				new Region(10, 90));
		remove = new DocumentSourceLocation(document, location,
				new Region(0, 20));
		remain = (IDocumentSourceRegion) partDoc.getRegionWithout(remove);
		assertEquals(remain.getDocument(), document);
		assertEquals(remain.getLocation(), location);
		assertEquals(new Region(20, 80), remain.getRegion());
		assertFalse(remain.isEmpty());
		
	}
	
	public void testMultiDocumentRemoval() {
		IMultiDocumentSourceRegion fullDoc = new MultiDocumentSourceLocation();
		fullDoc.addSourceRegion(new DocumentSourceLocation(document, location,
				new Region(90, 10)));
		fullDoc.addSourceRegion(new DocumentSourceLocation(document2, location2,
				new Region(0, 25)));
		
		// remove prefix
		ISourceRegion remove = new DocumentSourceLocation(document, location,
				new Region(90, 10));
		IDocumentSourceRegion remain = (IDocumentSourceRegion) fullDoc.getRegionWithout(remove);
		assertEquals(new DocumentSourceLocation(document2, location2,
				new Region(0, 25)), remain);

		// remove suffix
		remove = new DocumentSourceLocation(document2, location2,
				new Region(0, 25));
		remain = (IDocumentSourceRegion) fullDoc.getRegionWithout(remove);
		assertEquals(new DocumentSourceLocation(document, location,
				new Region(90, 10)), remain);

		// remove whole
		IMultiDocumentSourceRegion multiRemove = new MultiDocumentSourceLocation();
		multiRemove.addSourceRegion(
				new DocumentSourceLocation(document, location,
						new Region(90, 10)));
		multiRemove.addSourceRegion(
				new DocumentSourceLocation(document2, location2,
						new Region(0, 25)));
		remain = (IDocumentSourceRegion) fullDoc.getRegionWithout(multiRemove);
		assertEquals(remain.getDocument(), document2);
		assertEquals(remain.getLocation(), location2);
		assertEquals(new Region(25, 0), remain.getRegion());
		assertTrue(remain.isEmpty());

		// remove middle 1
		multiRemove = new MultiDocumentSourceLocation(); 
		multiRemove.addSourceRegion(new DocumentSourceLocation(document, location,
				new Region(95, 5)));
		multiRemove.addSourceRegion(new DocumentSourceLocation(document2, location2,
				new Region(0, 10)));
		IMultiDocumentSourceRegion multiRemain = (IMultiDocumentSourceRegion) fullDoc.getRegionWithout(multiRemove);
		IDocumentSourceRegion[] remains = multiRemain.getDocumentSourceRegions();
		assertEquals(2, remains.length);
		assertEquals(remains[0].getDocument(), document);
		assertEquals(remains[0].getLocation(), location);
		assertEquals(remains[1].getDocument(), document2);
		assertEquals(remains[1].getLocation(), location2);
		assertEquals(new Region(90, 5), remains[0].getRegion());
		assertEquals(new Region(10, 15), remains[1].getRegion());

		// remove middle 2
		remove = (new DocumentSourceLocation(document, location,
				new Region(95, 5)));
		multiRemain = (IMultiDocumentSourceRegion) fullDoc.getRegionWithout(remove);
		remains = multiRemain.getDocumentSourceRegions();
		assertEquals(2, remains.length);
		assertEquals(remains[0].getDocument(), document);
		assertEquals(remains[0].getLocation(), location);
		assertEquals(remains[1].getDocument(), document2);
		assertEquals(remains[1].getLocation(), location2);
		assertEquals(new Region(90, 5), remains[0].getRegion());
		assertEquals(new Region(0, 25), remains[1].getRegion());

		// remove middle 3
		remove = (new DocumentSourceLocation(document2, location2,
				new Region(0, 10)));
		multiRemain = (IMultiDocumentSourceRegion) fullDoc.getRegionWithout(remove);
		remains = multiRemain.getDocumentSourceRegions();
		assertEquals(2, remains.length);
		assertEquals(remains[0].getDocument(), document);
		assertEquals(remains[0].getLocation(), location);
		assertEquals(remains[1].getDocument(), document2);
		assertEquals(remains[1].getLocation(), location2);
		assertEquals(new Region(90, 10), remains[0].getRegion());
		assertEquals(new Region(10, 15), remains[1].getRegion());

		// remove non-contained
		remove = new DocumentSourceLocation(document, location,
				new Region(90, 20));
		remain = (IDocumentSourceRegion) fullDoc.getRegionWithout(remove);
		assertEquals(new DocumentSourceLocation(document2, location2,
				new Region(0, 25)), remain);
		
		remove = new DocumentSourceLocation(document2, location2,
				new Region(15, 20));
		multiRemain = (IMultiDocumentSourceRegion) fullDoc.getRegionWithout(remove);
		remains = multiRemain.getDocumentSourceRegions();
		assertEquals(2, remains.length);
		assertEquals(remains[0].getDocument(), document);
		assertEquals(remains[0].getLocation(), location);
		assertEquals(remains[1].getDocument(), document2);
		assertEquals(remains[1].getLocation(), location2);
		assertEquals(new Region(90, 10), remains[0].getRegion());
		assertEquals(new Region(0, 15), remains[1].getRegion());
		
	}
}
