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

package com.nokia.carbide.cpp.epoc.engine.tests;

import com.nokia.carbide.cpp.epoc.engine.DocumentFactory;
import com.nokia.carbide.cpp.epoc.engine.model.IOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.IView;
import com.nokia.carbide.cpp.epoc.engine.model.IViewParserConfiguration;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ASTFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorTokenStream;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTProblemNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTopLevelNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTVisitor;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IDocumentSourceRegion;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ISourceRegion;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTUtils;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.SourceLocationVisitorAdapter;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ViewASTBase;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.IDocumentParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ParserFactory;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ProblemVisitor;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.ASTPreprocessor;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IConditionalBlock;
import com.nokia.cpp.internal.api.utils.core.FileUtils;
import com.nokia.cpp.internal.api.utils.core.IMessage;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.osgi.framework.Bundle;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;


public abstract class BaseTest extends TestCase {
	
	public class DocSourceLocationVisitor extends SourceLocationVisitorAdapter {

		StringBuilder builder;
		private NodeVisitor nodeVisitor;
		private IASTNode node;
		
		/**
		 * @param expectFullSourceInfo 
		 * @param refText
		 */
		public DocSourceLocationVisitor(NodeVisitor nodeVisitor) {
			this.builder = nodeVisitor.builder;
			this.nodeVisitor = nodeVisitor;
			this.node = null;
		}

		public int visit(IDocumentSourceRegion region) {
			for (ISourceRegion loc : nodeVisitor.regions) {
				if (loc == region) {
					builder.append("duplicate location in tree: " + node + "\n");
				}
			}
			nodeVisitor.regions.add(region);
			
			try {
				String content;
				content = region.getDocument().get(region.getRegion().getOffset(), region.getRegion().getLength());
				if (!nodeVisitor.expectMacros) {
					String ref = node.getNewText();
					if (!compareSource(ref, content))
						builder.append("source looks suspicious: expected '" + ref + "' but got '" + content+"'\n");
				}
			} catch (BadLocationException e) {
				builder.append("cannot resolve source at " + region.getRegion() + " for " +node+"\n");
			}
			return VISIT_CHILDREN;
		}
	}

	
	public class NodeVisitor implements IASTVisitor {

		public IASTNode currentNode;
		List<ISourceRegion> regions;
		StringBuilder builder = new StringBuilder();
		boolean expectMacros;
		List<IDocument> documents;
		private DocSourceLocationVisitor nodeVisitor;
		
		/**
		 * @param expectFullSourceInfo 
		 * @param refText
		 */
		public NodeVisitor(boolean expectMacros) {
			this.expectMacros = expectMacros;
			this.documents = new ArrayList<IDocument>();
			this.regions = new ArrayList<ISourceRegion>();
			this.nodeVisitor = new DocSourceLocationVisitor(this);
		}

		/* (non-Javadoc)
		 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTVisitor#visit(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode)
		 */
		public int visit(IASTNode node) {
			if (node.getSourceRegion() == null) {
				builder.append("null source location in " + node +"\n");
				return VISIT_CHILDREN;
			}
			this.currentNode = node;
			nodeVisitor.node = node;
			node.getSourceRegion().accept(nodeVisitor);
			return VISIT_CHILDREN;
		}

	}

	protected ParserConfigurationBase parserConfig;
	protected IPath projectPath;
	protected boolean ignoreWhiteSpaceInRefTest;
	protected boolean allowParseErrors = false;

	private HashMap<String, String> refMap;
	protected IPath modelPath;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		if (projectPath == null)
			projectPath = new Path("c:/tests/project");
		parserConfig = new ParserConfigurationBase(projectPath) {

			@Override
			protected IASTTranslationUnit parse(IPath path, IDocument document) {
				return BaseTest.this.parse(path, document);
			}
			
		};
		refMap = new HashMap<String, String>();
		if (modelPath == null)
			modelPath = projectPath.append("model.txt");
	}

	protected IASTTranslationUnit parse(IPath path, IDocument document) {
		IDocumentParser parser = ParserFactory.createPreParser();
		IASTTranslationUnit tu = parser.parse(path, document);
		
		if (!allowParseErrors  && parser.hadErrors()) {
			ProblemVisitor visitor = new ProblemVisitor();
			tu.accept(visitor);
			StringBuffer buffer = new StringBuffer();
			for (IASTProblemNode node : visitor.getProblems()) {
				buffer.append(node.getMessage());
				buffer.append('\n');
			}
			fail(buffer.toString());
		}
		
		ASTUtils.validateSourceLocations(tu);
		return tu;
	}

	protected IASTTranslationUnit parse(IPath path, String text) {
		return parse(path, DocumentFactory.createDocument(text));
	}
		
	protected void validateNewNode(IASTNode node) {
		assertFalse(node.isDirty());
		assertFalse(node.isDirtyTree());
	}
	
	protected void testCopiedTree(IASTNode a, IASTNode b) {
		assertFalse(a == b);
		assertEquals(a, b);
		checkHashCodes(a, b);
		assertEquals(a.getNewText(), b.getNewText());
	}
	
	/**
	 * Compare hash nodes for equal trees, which must match at every level.
	 * @param a
	 * @param b
	 */
	protected void checkHashCodes(IASTNode a, IASTNode b) {
		IASTNode copy = a.copy();
		// recursively copy source info so making sure automatically generated equals/hashcode 
		// compares fine, since they all compare children down to source info.
		//copy.copySourceInfo(b);
		recursiveCopySourceInfo(copy, b);
		if (!copy.equals(b))
			fail("problem with #equals() in parents of " + a.getClass());
		IASTNode[] aKids = a.getChildren();
		IASTNode[] bKids = b.getChildren();
		for (int i = 0; i < aKids.length; i++) {
			checkHashCodes(aKids[i], bKids[i]);
		}
		int aCode = copy.hashCode();
		int bCode = b.hashCode();
		if (aCode == bCode)
			return;

		// ensure this isn't due to source changes
		recursiveResetSource(copy);
		IASTNode bCopy = b.copy();
		recursiveResetSource(bCopy);
		if (copy.hashCode() == bCopy.hashCode())
			return;
			
		// all kids matched, so node itself is bad
		fail("problem with #hashCode for " + a.getClass());
	}

	private void recursiveResetSource(IASTNode node) {
		node.copySourceInfo(null);
		for (IASTNode kid : node.getChildren())
			recursiveResetSource(kid);
	}
	
	private void recursiveCopySourceInfo(IASTNode dst, IASTNode src) {
		dst.copySourceInfo(src);
		IASTNode[] srcKids = src.getChildren();
		IASTNode[] dstKids = dst.getChildren();
		for (int i = 0; i < srcKids.length; i++) {
			recursiveCopySourceInfo(dstKids[i], srcKids[i]);
		}
	}

	/**
	 * Test that a parsed TU matches a reference TU.
	 * @param refTu
	 * @param tu
	 * @boolean expectPP if true, preprocessing is enabled and source contents may not match, so don't complain;
	 * else, every node's new text is compared against its source info, reduced to a canonical
	 * form, and matched
	 */
	protected void testParsedTu(IASTTranslationUnit refTu, IASTTranslationUnit tu, boolean expectPP) {
		checkParentage(null, tu);
		assertFalse(tu.isDirtyTree());
		if (!refTu.equalValue(tu)) {
			assertEquals(refTu.getNewText(), tu.getNewText()); // ensure a diff dialog
			// maybe not textually different
			fail("TU's return nonequal but are textually equal");
		}
		// the hash codes depend on the ids on the tree, so set them
		int id = 0;
		for (IASTTopLevelNode node : refTu.getNodes()) {
			node.setId(id++);
		}
		checkHashCodes(refTu, tu);
		assertEquals(refTu.getNewText(), tu.getNewText());
		testSourceRegions(tu, expectPP);
		assertEquals(tu.copy(), tu);
	}
	
	/**
	 * @param tu
	 */
	private void checkParentage(IASTNode parent, IASTNode node) {
		if (parent != null)
			assertTrue(parent == node.getParent());
		IASTNode[] kids = node.getChildren();
		for (IASTNode kid : kids)
			checkParentage(node, kid);
	}

	/**
	 * Test that a parsed TU matches a reference TU where it counts.
	 * @param refTu
	 * @param tu
	 */
	protected void testParsedTu(IASTTranslationUnit refTu, IASTTranslationUnit tu) {
		testParsedTu(refTu, tu, false);
	}

	/**
	 * @param expectMacros 
	 * @param tu
	 */
	protected void testSourceRegions(IASTNode node, boolean expectMacros) {
		NodeVisitor visitor = new NodeVisitor(expectMacros);
		node.accept(visitor);
		if (visitor.builder.length() != 0)
			fail("source region problems:\n" + visitor.builder);
	}

	/**
	 * @param expectMacros 
	 * @param tu
	 */
	protected void testSourceRegions(IView view, boolean expectMacros) {
		testSourceRegions(((ViewASTBase)view).getFilteredTranslationUnit(), expectMacros);
	}


    private static final Pattern commentPattern = Pattern.compile("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)"); //$NON-NLS-1$

    /**
     * This strips comments from the text
     */
    public static CharSequence stripComments(CharSequence text) {
        // remove all the comments
    	if (text == null)
    		return null;
        Matcher commentMatcher = commentPattern.matcher(text);
        text = commentMatcher.replaceAll(""); //$NON-NLS-1$
        return text;
    }
 
	/**
	 * Compare two strings canonically.
	 * @param newText
	 * @param content
	 * @return
	 */
	private boolean compareSource(String ref, String text) {
		ref = ref.replaceAll("\\\\(\r\n|\r|\n)", "");
		text = text.replaceAll("\\\\(\r\n|\r|\n)", "");
		ref = stripComments(ref.toLowerCase()).toString();
		text = stripComments(text.toLowerCase()).toString();
		ref = ref.replaceAll("(\\s|\r\n|\r|\n)+", "");
		text = text.replaceAll("(\\s|\r\n|\r|\n)+", "");
		if (!ref.equals(text)) {
			return false;
		}
		return true;
	}

	protected IASTPreprocessorTokenStream createSimpleTokenStream(String text) {
		return ASTFactory.createPreprocessorTokenStream(text);
	}

	protected void commitTest(IOwnedModel model, IView view, String refText) {
		view.commit();
		
		String currentText = model.getDocument().get();
		
		assertTextEquals(refText, currentText, ignoreWhiteSpaceInRefTest);

		// do it again to ensure nothing breaks: second commit can't rewrite doc
		// or fire a change
		final boolean changed[ ] = {false, false};
		IDocumentListener listener = new IDocumentListener() {

			public void documentAboutToBeChanged(DocumentEvent event) {
				changed[0] = true;
			}

			public void documentChanged(DocumentEvent event) {
				changed[1] = true;
			}
		};
		
		model.getDocument().addDocumentListener(listener);
		view.commit();
		
		String secondRoundText = model.getDocument().get();
		assertEquals("Unexpected change in second commit!", currentText, secondRoundText);
		assertFalse("Unexpected change in second commit!", changed[0] && changed[1]);
		
		model.getDocument().removeDocumentListener(listener);
	}

	/**
	 * Compare text, optionally ignoring differences in whitespace (but not utter deletion)
	 * @param refText
	 * @param string
	 * @param ignoreWhiteSpace
	 */
	protected void assertTextEquals(String refText, String docText, boolean ignoreWhiteSpace) {
		if (ignoreWhiteSpace) {
			refText = refText.replaceAll("[ \t\f]+", " ");
			refText = TextUtils.canonicalizeNewlines(refText);
			docText = docText.replaceAll("[ \t\f]+", " ");
			docText = TextUtils.canonicalizeNewlines(docText);
		}
		assertEquals(refText, docText);
		
	}

	protected void match(String[] entries, List list) {
		StringBuilder builder = new StringBuilder();
		int idx = 0;
		for (Object obj : list) {
			if (idx >= entries.length) {
				builder.append("extra entry " + obj+"\n");
			} else if (!obj.toString().equals(entries[idx])) {
				builder.append("mismatch, expected " + entries[idx] +", got " + obj+"\n");
			}
			idx++;
		}
		while (idx < entries.length) {
			builder.append("missing entry " + entries[idx++]+"\n");
		}
		if (builder.length() > 0)
			fail(builder.toString());
	}
	
	protected void match(IPath[] entries, IPath[] paths) {
		StringBuilder builder = new StringBuilder();
		int idx = 0;
		for (Object obj : paths) {
			if (idx >= entries.length) {
				builder.append("extra entry " + obj+"\n");
			} else if (!obj.equals(entries[idx])) {
				builder.append("mismatch, expected " + entries[idx] +", got " + obj+"\n");
			}
			idx++;
		}
		while (idx < entries.length) {
			builder.append("missing entry " + entries[idx++]+"\n");
		}
		if (builder.length() > 0)
			fail(builder.toString());
	}
	
	/**
	 * Waste time so Eclipse will run resource listeners.
	 *
	 */
	protected void sleep() throws Exception {
		Job job = new WorkspaceJob("foo") {

			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				monitor.beginTask("foo", 2);
				monitor.worked(1);
				monitor.worked(1);
				monitor.done();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
				return Status.OK_STATUS;
			}
			
		};
		job.schedule();
		job.join();
	}
	
	 /**
     * Load a file relative to this plugin in the running workbench 
     * @param file
     * @return File
     * @throws IOException
     */
    public static File pluginRelativeFile(String file) throws IOException {
        if (TestsPlugin.getDefault() == null)
            return null;
        Bundle bundle = TestsPlugin.getDefault().getBundle();
        if (bundle == null)
            return null;
        URL url = FileLocator.find(bundle, new Path("."), null);
        if (url == null)
            TestCase.fail("could not make URL from bundle " + bundle + " and path " + file);
        url = FileLocator.resolve(url);
        TestCase.assertEquals("file", url.getProtocol());
        return new File(url.getPath(), file);
        
    }

    /**
     *  Find a file relative to the project.  Works if running
     *  in the workbench or standalone.
     *  @param file the relative path (from the project) to the file
     *  @return File
     */
    public static File projectRelativeFile(String file) throws Exception {
        File f;
        if (!Platform.isRunning()) {
            // get file relative to CWD (i.e. this project)
            f = new File(file);
            f = f.getCanonicalFile();
        } else {
            // get file relative to running plugin (still this project)
            f = pluginRelativeFile(file);
        }
        if (f == null)
            TestCase.fail("Cannot find file " + file + " relative to project");
        return f;
    }

	protected IASTProblemNode[] getProblems(IASTNode node) {
		ProblemVisitor visitor = new ProblemVisitor();
		node.accept(visitor);
		return visitor.getProblems();
	}
	
	protected IASTProblemNode[] getProblems(IView view) {
		return getProblems(((ViewASTBase)view).getFilteredTranslationUnit());
	}

	protected void checkBlockInfo(IConditionalBlock rootBlock) {
		StringBuilder builder = new StringBuilder();
		checkBlockInfo(builder, rootBlock);
		if (builder.length() > 0)
			fail(builder.toString());
	}

	/**
	 * @param rootBlock
	 */
	private void checkBlockInfo(StringBuilder builder, IConditionalBlock block) {
		ISourceRegion span = block.getSpan();
		if (span == null) {
			builder.append("Failed to get span for block: " + block);
			return;
		}
		ISourceRegion fullSpan = block.getFullSpan();
		if (fullSpan == null) {
			builder.append("Failed to get full span for block: " + block);
			return;
		}
		if (!fullSpan.contains(span)) {
			builder.append("Full span does not include span: " + block);
			return;
		}
		IConditionalBlock[] kids = block.getChildren();
		for (IConditionalBlock kid : kids) {
			checkBlockInfo(builder, kid);
			ISourceRegion kidSpan = kid.getSpan();
			if (kidSpan != null) {
				if (!kidSpan.contains(span) && !span.contains(kidSpan)) {
					builder.append("child span extends beyond parent: " + kid + " ==> " + block);
				}
			}
			kidSpan = kid.getFullSpan();
			if (kidSpan != null) {
				if (!kidSpan.contains(fullSpan) && !fullSpan.contains(kidSpan)) {
					builder.append("child full span extends beyond parent: " + kid + " ==> " + block);
				}
			}
		}
	}

	/**
	 * Check that problems look sane
	 * @param messages
	 */
	protected void checkMessages(IMessage[] messages) {
		for (IMessage message : messages) {
			assertNotNull(message.getMessage());
			assertFalse(message.getMessage().matches("(?s)!.*!"));
		}
	}

	/**
	 * Check that problems look sane
	 * @param messages
	 */
	protected void checkMessages(Collection<IMessage> messages) {
		for (IMessage message : messages) {
			assertNotNull(message.getMessage());
			assertFalse(message.getMessage().matches("(?s)!.*!"));
		}
	}

	/**
	 * Check that problems look sane
	 * @param messages
	 */
	protected void checkMessages(IASTProblemNode[] problems) {
		for (IASTProblemNode problem : problems) {
			assertNotNull(problem.getMessage());
			assertFalse(problem.getMessage().getMessage().matches("!.*!"));
		}
	}

	protected void assertNoProblems(IASTNode node) {
		IASTProblemNode[] problems = getProblems(node);
		if (problems.length > 0) {
			StringBuilder builder = new StringBuilder();
			for (IASTProblemNode problem : problems) {
				builder.append(problem.getMessage());
				builder.append('\n');
			}
			fail("problems detected:\n"+builder.toString());
		}
	}
	protected void assertNoMessages(IMessage[] messages) {
		if (messages.length > 0) {
			StringBuilder builder = new StringBuilder();
			for (IMessage message : messages) {
				builder.append(message);
				builder.append('\n');
			}
			fail("problems detected:\n"+builder.toString());
		}
	}

	protected void checkNoProblems(IView view) {
		if (view instanceof ViewASTBase)
			assertNoProblems(((ViewASTBase)view).getFilteredTranslationUnit());
		else
			assertNoMessages(view.getMessages());
	}
	
	protected String loadFileText(String filepath) throws Exception {
		File file = projectRelativeFile(filepath);
		return new String(FileUtils.readFileContents(file, null));
	}
	
	protected void dumpSourceInfo(IASTNode node) {
		node.accept(new IASTVisitor() {

			public int visit(IASTNode node) {
				System.out.println(node.getSourceRegion() + ": " + node);
				return IASTVisitor.VISIT_CHILDREN;
			}
			
		});
	}
	
	/** 
	 * Look for a file in the filesystem given a full path that
	 * would appear in an ISourceLocation
	 * @param fs
	 * @param location
	 * @return filename used in fs
	 */ 
	protected String lookupRefFile(Map<String, String> fs, IPath location) {
		for (Map.Entry<String, String> entry : fs.entrySet()) {
			if (entry.getKey() != null) {
				if (entry.getKey().equalsIgnoreCase(location.lastSegment())
						|| entry.getKey().equalsIgnoreCase(location.toOSString())) {
					return entry.getKey();
				}
			}
		}
		return null;
	}
	
	protected void runRefTest_(IView view, ISourceRegion mainRegion, final Map<String, String> refMap) {
		// update
		view.commit();
		
		// look for locations in the TU's main location, which should
		// span any contained locations, and ensure all the files were
		// updated as expected
		final Set<String> visitedFiles = new HashSet<String>();
		mainRegion.accept(new SourceLocationVisitorAdapter() {

			public int visit(IDocumentSourceRegion docRegion) {
				String file = lookupRefFile(refMap, docRegion.getLocation());
				if (file == null)
					fail("reference to unknown file: " + docRegion.getLocation());
				assertEquals(refMap.get(file), 
						docRegion.getDocument().get());
				// test that we only see it once
				visitedFiles.add(file);
				return VISIT_CHILDREN;
			}
			
		});
		
		// ensure we saw all the expected files
//		assertEquals(refMap.size(), visitedFiles.size());
	}
	
	/**
	 * Run the rewriting test on the given tu, which may span multiple
	 * documents.  The refMap contains the mapping of filenames 
	 * (registered in parserConfig.fs) to the new contents.  Any files
	 * no longer expected to be referenced should be removed.
	 * <p>   
	 * We run the test twice, once on
	 * the original tu and once on a copy, ensuring the results are the
	 * same, as a double-check of the copying of the dirty state.
	 * @param tu
	 * @param refText
	 */
	protected void runRefTest(IView view) {

		IASTTranslationUnit tu = null;
		tu = ((ViewASTBase) view).getFilteredTranslationUnit();
		
		// save off the documents before the test
		final Map<IPath, IDocument> origDocs = new HashMap<IPath, IDocument>();
		tu.getSourceRegion().accept(new SourceLocationVisitorAdapter() {

			public int visit(IDocumentSourceRegion region) {
				IDocumentSourceRegion docRegion = (IDocumentSourceRegion) region;
				if (!origDocs.containsKey(docRegion.getLocation())) {
					origDocs.put(docRegion.getLocation(), DocumentFactory.createDocument(docRegion.getDocument().get()));
				}
				return VISIT_CHILDREN;
			}
			
		});
		runRefTest_(view, tu.getSourceRegion(), refMap);
		
		/*
		// restore docs for another go
		tu.getSourceRegion().accept(new SourceLocationVisitorAdapter() {

			public int visit(IDocumentSourceRegion docRegion) {
				if (origDocs.containsKey(docRegion.getLocation())) {
					docRegion.getDocument().set(origDocs.get(docRegion.getLocation()).get());
					origDocs.remove(docRegion.getLocation());
				}
				return VISIT_CHILDREN;
			}
			
		});
		
		view.forceSynchronized();
		
		runRefTest_(view, tu.getSourceRegion(), refMap);
		*/
	}
	
	protected Map<String, String> copyFilesystem() {
		return new HashMap<String, String>(parserConfig.getFilesystem());
	}
	
	protected void addRefFile(String filename, String text) {
		addRefFile(filename, text, null);
	}
	protected void addRefFile(String filename, String text, String refText) {
		if (filename == null)
			filename = modelPath.lastSegment();
		else
			filename = projectPath.append(filename).toOSString();
		parserConfig.getFilesystem().put(filename, text);
		refMap.put(filename, refText != null ? refText : text);
	}
	
	protected ASTPreprocessor createPreprocessor(IViewParserConfiguration parserConfiguration) {
		return new ASTPreprocessor(parserConfiguration.getTranslationUnitProvider(),
				parserConfiguration.getIncludeFileLocator(), parserConfiguration.getModelDocumentProvider());

	}
	
   public static String cleanNewlines(String text) {
        Pattern p = Pattern.compile("\r\n|\r(?!\n)", Pattern.MULTILINE);
        Matcher m = p.matcher(text);
        return m.replaceAll("\n");
    }
 
    /**
     * Read the contents of the file into a string, optionally cleaning
     * it up for ease of comparison
     * @param file full path or project-relative file
     * @param whitespaceCleaning if true, remove leading/trailing space from the
     * file text 
     * @param newlineCleaning if true, convert newlines from file text to canonical format ('\n')
     */
    public static String readFile(File file, boolean whitespaceCleaning, boolean newlineCleaning) throws IOException {
        StringBuffer sb = new StringBuffer();
        try {
            Reader reader = new InputStreamReader(
                    new FileInputStream(file), "UTF-8");
            char[] buf = new char[1024];
            int len;
            while ((len = reader.read(buf)) > 0) {
                sb.append(buf, 0, len);
            }
            reader.close();
        } catch (IOException e) {
            TestCase.fail("Couldn't find " + file);
        }
        
        if (whitespaceCleaning) {
            int idx = 0;
            while (idx < sb.length() && Character.isWhitespace(sb.charAt(idx)))
                idx++;
            sb.delete(0, idx);
            
            idx = sb.length();
            while (idx > 0 && Character.isWhitespace(sb.charAt(idx - 1)))
                idx--;
            
            sb.delete(idx, sb.length());
        }
        
        String sbText = sb.toString();
        
        if (newlineCleaning) {
        	sbText = cleanNewlines(sbText);
        }
    
        return sbText;
    }
    
    /**
     * Read the contents of the file into a string, optionally cleaning
     * it up for ease of comparison
     * @param file full path or project-relative file
     * @param whitespaceCleaning if true, remove leading/trailing space from the
     * file text 
     * @param newlineCleaning if true, convert newlines from file text to canonical format ('\n')
     */
    public static String readFile(String file, boolean whitespaceCleaning, boolean newlineCleaning) throws Exception {
        File f;
        f = new File(file);
        if (!f.exists())
            f = projectRelativeFile(file);
        
        return readFile(f, whitespaceCleaning, newlineCleaning);
    }
    
    /**
     * Compare the contents of the file to the text string, optionally
     * cleaning them up to make comparisons easier
     * @param file project-relative file
     * @param txt
     * @param whitespaceCleaning if true, remove <b>leading and trailing</b> 
     * space from the file text and the canonical text
     * @param newlineCleaning if true, convert newlines from file text to canonical format ('\n')
     */
    public static void compareFiles(String file, String txt, boolean whitespaceCleaning, boolean newlineCleaning) throws Exception {
        String fileText = readFile(file, whitespaceCleaning, newlineCleaning);
    
        if (whitespaceCleaning) {
            int idx = 0;
            while (idx < txt.length() && Character.isWhitespace(txt.charAt(idx)))
                idx++;
            txt = txt.substring(idx);
            
            idx = txt.length();
            while (idx > 0 && Character.isWhitespace(txt.charAt(idx - 1)))
                idx--;
            
            txt = txt.substring(0, idx);
        }
        
        TestCase.assertEquals(fileText, txt);
    }

    /**
     * Compare two trees of files recursively.<p>
     * Any file in refDir must exist in projDir.<p>
     * Any file in projDir, except for dot-files, must exist in refDir.<p>
     * And the contents must match, excepting leading and trailing whitespace. 
     * @param refDir
     * @param projDir
     */
    public static void compareTrees(File refDir, File projDir) throws Exception {
        if (!refDir.exists() || !refDir.isDirectory())
            TestCase.fail("no such reference directory: " + refDir);
        if (!projDir.exists() || !projDir.isDirectory())
            TestCase.fail("no such project directory: " + projDir);
        
        File[] refs = refDir.listFiles();
        for (int i = 0; i < refs.length; i++) {
            File projRef = new File(projDir, refs[i].getName());
            if (refs[i].isDirectory()) {
            	if (refs[i].getName().matches("CVS"))
            		continue;
                compareTrees(refs[i], projRef);
            }
            else {
                if (!projRef.exists())
                    TestCase.fail("missing file in project: " + projRef);
                else
                    compareFiles(refs[i], projRef, true, true);
            }
        }
        
        File[] projs = projDir.listFiles();
        for (int i = 0; i < projs.length; i++) {
        	// ignore project dotfiles and any backups made
        	if (!projs[i].getName().startsWith(".") && !projs[i].getName().toLowerCase().endsWith(".bak")) {
                File refRef = new File(refDir, projs[i].getName());
                if (!refRef.exists()) {
                    TestCase.fail("unexpected additional file in project: " + projs[i]);
                }
            }
        }
    }
    
    /**
     * Compare the contents of two files, optionally
     * cleaning them up to make comparisons easier
     * @param refFile reference file
     * @param otherFile file to compare with
     * @param whitespaceCleaning if true, remove <b>leading and trailing</b> 
     * space from the file text and the canonical text
     * @param newlineCleaning if true, convert newlines from file text to canonical format ('\n')
     */
    public static void compareFiles(File refFile, File otherFile,
			boolean whitespaceCleaning, boolean newlineCleaning)
			throws Exception {

		String refText = readFile(refFile, whitespaceCleaning, newlineCleaning);
		String otherText = readFile(otherFile, whitespaceCleaning,
				newlineCleaning);

		TestCase.assertEquals(refText, otherText);
	}

	public static void compareFiles(String message, File refFile,
			File otherFile, boolean whitespaceCleaning, boolean newlineCleaning)
			throws Exception {

		String refText = readFile(refFile, whitespaceCleaning, newlineCleaning);
		String otherText = readFile(otherFile, whitespaceCleaning,
				newlineCleaning);

		TestCase.assertEquals(message, refText, otherText);
	}


}
