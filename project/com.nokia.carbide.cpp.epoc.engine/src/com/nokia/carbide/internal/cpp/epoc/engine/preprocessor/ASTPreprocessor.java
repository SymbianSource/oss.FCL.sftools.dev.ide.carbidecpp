/*
* Copyright (c) 2006-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.preprocessor;

import com.nokia.carbide.cpp.epoc.engine.model.IModelDocumentProvider;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IDefine;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IIncludeFileLocator;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.ITranslationUnit;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.ITranslationUnitProvider;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.IViewFilter;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.*;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.*;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTNode;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.Token;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ASTParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ParserUtils;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import java.io.File;
import java.util.*;

/**
 * This class expands a DOM in accordance with a configuration (a fixed set of
 * macros), evaluating preprocessor directives and expanding macro calls to
 * create a flattened DOM. The nodes in the flattened DOM maps back to nodes in
 * the original DOM and regions in that DOM defined by particular conditions
 * expressed in the preprocessor directives. Using this knowledge, changes to
 * the view can be applied to the appropriate regions in the original DOM, or
 * new regions can be created where the view's configuration differs from that
 * of the existing regions.
 * 
 */
public class ASTPreprocessor implements IPreprocessor, IMacroProvider {
	private static final int MAXIMUM_INCLUDE_STACK_SIZE = 256;
	Stack<IConditionalBlock> blockStack;
	Stack<Boolean> takenStack; // one per #if level
	IConditionalBlock parentBlock;
	IConditionalBlock targetBlock;
	private IViewFilter filter;
	private IPreprocessorFilter outFilter;
	
	enum IfState {
		IncludeToNext,
		SkipToNext,
		IncludeToEndif,
		SkipToEndif;

		/**
		 * @return
		 */
		public boolean isSkip() {
			return this == SkipToNext || this == SkipToEndif; // || this == SkipToEndifNoElse;
		}
	};
	Stack<IfState> ifStateStack; // one per #if level, but not current
	private IfState ifState;
	/** current combined state of conditionality for the whole taken stack */
	private boolean currentState;
	private IConditionalBlock rootBlock;
	private Stack<IASTPreprocessorTestStatement> ifStack;
	private HashMap<String, IASTPreprocessorDefineStatement> macros;
	private IIncludeFileLocator fileLocator;
	private ITranslationUnitProvider tuProvider;
	private TopLevelNodeProvider nodeIterator;
	private Set<File> referencedFiles;
	private IdentityHashMap<IASTNode, IConditionalBlock> nodeToBlockMap;
	private HashMap<IConditionalBlock, Integer> nodeToDepthMap;
	private Set<IASTNode> macroReferencingNodes;
	private Set<IASTNode> variantMacroReferencingNodes;
	private IASTTranslationUnit tu;
	protected Map<ISourceRegion, IASTTranslationUnit> includeToTranslationUnitMap;
	protected Map<File, IASTTranslationUnit> includeFileToTranslationUnitMap;
	private List<ISourceRegion> variantMacroExpansionRegions;
	private IModelDocumentProvider modelDocumentProvider;
	
	public ASTPreprocessor(ITranslationUnitProvider tuProvider, IIncludeFileLocator fileLocator,
			IModelDocumentProvider modelDocumentProvider) {
		Check.checkArg(tuProvider);
		Check.checkArg(fileLocator);
		Check.checkArg(modelDocumentProvider);
		this.tuProvider = tuProvider;
		this.fileLocator = fileLocator;
		this.modelDocumentProvider = modelDocumentProvider;
		this.referencedFiles = new HashSet<File>();
		this.includeToTranslationUnitMap = new HashMap<ISourceRegion, IASTTranslationUnit>();
		this.includeFileToTranslationUnitMap = new HashMap<File, IASTTranslationUnit>();
		this.outFilter = new DefaultPreprocessorFilter();
	}
	
	public IConditionalBlock getRootBlock() {
		return rootBlock;
	}
	
	class TopLevelNodeProvider implements Iterator<IASTTopLevelNode> {
		Stack<Iterator<IASTTopLevelNode>> pushedIterators;
		Iterator<IASTTopLevelNode> currentIter;
		private Stack<File> pushedFiles;
		
		public TopLevelNodeProvider() {
			pushedIterators = new Stack<Iterator<IASTTopLevelNode>>();
			pushedFiles = new Stack<File>();
		}
		
		/**
		 * Push the top-level nodes from the given translation unit into
		 * the stream before the node that would be returned by the last call to #next().
		 * @param tu
		 */
		public void pushTranslationUnit(File file, IASTTranslationUnit tu, IASTTopLevelNode node) {
			// watch for infinite #includes
			if (pushedFiles.size() >= MAXIMUM_INCLUDE_STACK_SIZE) {
				reportProblem(node, "ASTPreprocessor.IncludeNestingTooDeep", //$NON-NLS-1$
						new Object[] { file });
				return;
			}

			if (node != null) {
				// establish a mapping from the TU to its first #include
				if (tu.getSourceNodes() == null)
					tu.setSourceNodes(new IASTNode[] { node });
				
				includeToTranslationUnitMap.put(node.getSourceRegion(), tu);
				includeFileToTranslationUnitMap.put(file, tu);
			}
			
			pushedFiles.push(file);
			
			pushedIterators.push(tu.getNodes().iterator());
			currentIter = pushedIterators.peek();
			
			enteredTranslationUnit(tu);
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			//if (currentIter.hasNext())
			//	return true;
			for (Iterator<IASTTopLevelNode> iter : pushedIterators)
				if (iter.hasNext())
					return true;
			return false;
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		public IASTTopLevelNode next() {
			while (true) {
				if (!currentIter.hasNext()) {
					popCurrentFile();
				}
				else
					return currentIter.next();
			}
		}

		/* (non-Javadoc)
		 * @see java.util.Iterator#remove()
		 */
		public void remove() {
			currentIter.remove();
		}

		public void dispose() {
			while (true) {
				try {
					popCurrentFile();
				} catch (NoSuchElementException e) {
					return;
				}
			}
		}

		/**
		 * 
		 */
		private void popCurrentFile() {
			pushedIterators.pop();
			exitedTranslationUnit();
			if (pushedIterators.size() > 0) {
				currentIter = pushedIterators.peek();
				pushedFiles.pop();
				if (outFilter != null) {
					IASTPreprocessorStatement stmt = outFilter.filterIncludeExit();
					if (stmt != null) {
						appendDirectiveNode(stmt);
					}
				}
			}
			else
				throw new NoSuchElementException();
		}
	}
	
	public void setFilter(IPreprocessorFilter filter) {
		this.outFilter = filter;
	}
	
	/**
	 * Preprocess the preprocessor TU and fill in the translation unit.
	 * @return
	 */
	public IPreprocessorResults preprocess(final IASTTranslationUnit ppTu, 
			IViewFilter viewFilter, Collection<IDefine> defines) {
		this.filter = viewFilter;

		this.tu = ASTFactory.createTranslationUnit();
		tu.getNodes().clear();
		
		macros = new HashMap<String, IASTPreprocessorDefineStatement>();
		
		for (IDefine macro : defines) {
			macros.put(macro.getName(), 
					ASTFactory.createPreprocessorDefineStatement(
							ASTFactory.createPreprocessorLiteralTextNode(macro.getName()),
							ASTFactory.createPreprocessorLiteralTextNodeList(macro.getArgumentNames()),
							ASTFactory.createPreprocessorTokenStream(macro.getExpansion())));
		}
		
		macroReferencingNodes = new HashSet<IASTNode>();
		variantMacroReferencingNodes = new HashSet<IASTNode>();
		variantMacroExpansionRegions = new ArrayList<ISourceRegion>();
		
		/** map of out nodes to regions */
		nodeToBlockMap = new IdentityHashMap<IASTNode, IConditionalBlock>();
		
		blockStack = new Stack<IConditionalBlock>();
		ifStack = new Stack<IASTPreprocessorTestStatement>();
		takenStack = new Stack<Boolean>();
		ifStateStack = new Stack<IfState>();
		parentBlock = null;
		targetBlock = null;
		currentState = true;
		ifState = null;
		//currentIfTaken = false;
		//evaluatingCurrent = true;
		
		nodeToDepthMap = new HashMap<IConditionalBlock, Integer>();
		
		nodeIterator = new TopLevelNodeProvider();
		nodeIterator.pushTranslationUnit(null, ppTu, null);
		
		IASTTopLevelNode lastPpNode = null;
		
		while (nodeIterator.hasNext()) {
			IASTTopLevelNode ppNode = nodeIterator.next();
			lastPpNode = ppNode;
			
			// test nodes modify the regions and rely on filtering
			if (ppNode instanceof IASTPreprocessorTestStatement) {
				IASTPreprocessorTestStatement test = (IASTPreprocessorTestStatement) ppNode;
				handleTestStatement(test, false);
				filterOrAppendDirective(test);
				continue;
			}
			
			// other directives should only be used if filtered in
			if (isIncludingNode()) {
				if (ppNode instanceof IASTPreprocessorDefineStatement) {
					IASTPreprocessorDefineStatement define = (IASTPreprocessorDefineStatement) ppNode;
					handleDefineStatement(define);
					filterOrAppendDirective(define);
					continue;
				} else if (ppNode instanceof IASTPreprocessorUndefStatement) {
					IASTPreprocessorUndefStatement undef = (IASTPreprocessorUndefStatement) ppNode;
					handleUndefStatement(undef);
					filterOrAppendDirective(undef);
					continue;
				} else if (ppNode instanceof IASTPreprocessorIncludeStatement) {
					IASTPreprocessorIncludeStatement include = (IASTPreprocessorIncludeStatement) ppNode;
					handleIncludeStatement(include);
					filterOrAppendDirective(include);
					continue;
				} else if (ppNode instanceof IASTPreprocessorErrorStatement
						|| ppNode instanceof IASTPreprocessorWarningStatement) {
					IASTPreprocessorStatement problem = (IASTPreprocessorStatement) ppNode;
					handleErrorOrWarningStatement(problem);
					filterOrAppendDirective(problem);
					continue;
				} else if (ppNode instanceof IASTPreprocessorPragmaStatement) {
					// ignore these
					filterOrAppendDirective((IASTPreprocessorPragmaStatement) ppNode);
					continue;
				} else if (ppNode instanceof IASTPreprocessorUnknownStatement) {
					// ignore these
					filterOrAppendDirective((IASTPreprocessorUnknownStatement) ppNode);
					continue;
				} else if (ppNode instanceof IASTPreprocessorIncludeStatement) {
					IASTPreprocessorIncludeStatement include = (IASTPreprocessorIncludeStatement) ppNode;
					handleIncludeStatement(include);
					filterOrAppendDirective(include);
					continue;
				}
			}
			
			// the rest are language nodes
			ensureTargetRegion();
			
			IASTTopLevelNode node = expandMacrosInStatement(ppNode);
			
			appendPreprocessedNode(ppNode, node);
		}
		
		if (ifStack.size() > 0) {
			reportProblem(lastPpNode, "ASTPreprocessor.UnbalancedIfsOnExit", //$NON-NLS-1$
						new Object[0]);
			// fixup dangling blocks so we can still commit
			while (ifStack.size() > 0) {
				IASTPreprocessorEndifStatement endifStmt = ASTFactory.createPreprocessorEndifStatement();
				endifStmt.copySourceInfo(lastPpNode);
				if (lastPpNode.getSourceRegion() != null) {
					endifStmt.setSourceRegion(lastPpNode.getSourceRegion().getExclusiveTailRegion());
				}
				handleTestStatement(endifStmt, true);
			}
		}

		// exhaust the node iterators so we know that all the TUs have closed 
		nodeIterator.dispose();

		Check.checkState(blockStack.size() == 0);
		Check.checkState(takenStack.size() == 0);
		
		// Ensure the TU incorporates all the source for the statements that
		// are filtered in.   
		ParserUtils.setSourceRangeForListNode(null, tu.getNodes(), null);
		tu.copySourceInfo(tu.getNodes());
		
		// If that fails, copy empty source range from main file
		if (tu.getSourceRegion() == null) {
			tu.getNodes().copySourceInfo(ppTu.getNodes());
			tu.copySourceInfo(ppTu);
		}
		
		tu.setMainDocument(ppTu.getMainDocument());
		tu.setMainLocation(ppTu.getMainLocation());

		tu.setSourceNodes(new IASTNode[] { ppTu });
		
		tu.setDirty(false);
		tu.getNodes().setDirty(false);

		return new IPreprocessorResults() {
			public IASTTranslationUnit getOriginalTranslationUnit() {
				return ppTu;
			}
			
			public IASTTranslationUnit getFilteredTranslationUnit() {
				return tu;
			}

			public Collection<File> getReferencedFiles() {
				return referencedFiles;
			}
			
			public IASTTranslationUnit getTranslationUnitFor(IASTPreprocessorIncludeStatement include) {
				return includeToTranslationUnitMap.get(include.getSourceRegion());
			}

			public IASTTranslationUnit getTranslationUnitFor(File file) {
				return includeFileToTranslationUnitMap.get(file);
			}
			
			public void replaceTranslationUnitFor(File file,
					IASTTranslationUnit tu) {
				IASTTranslationUnit previous = includeFileToTranslationUnitMap.put(file, tu);
				for (Map.Entry<ISourceRegion, IASTTranslationUnit> regionEntry : includeToTranslationUnitMap.entrySet()) {
					if (regionEntry.getValue() == previous) {
						regionEntry.setValue(tu);
					}
				}
			}

			public IConditionalBlock getRootBlock() {
				return rootBlock;
			}
			
			/**
			 * Tell whether the preprocessor node was expanded with variant macros.
			 * @param ppStmts
			 * @return
			 */
			public boolean nodesUseVariantMacros(IASTNode[] ppStmts) {
				for (IASTNode ppStmt : ppStmts) {
					if (variantMacroReferencingNodes.contains(ppStmt))
						return true;
				}
				return false;
			}

			/**
			 * Tell if the source range covered by the node references macros
			 * @param node
			 * @return
			 */
			public boolean nodeReferencesVariantMacros(IASTNode node) {
				ISourceRegion region = node.getSourceRegion();
				if (region == null)
					return false;
				for (ISourceRegion macroRegion : variantMacroExpansionRegions) {
					if (region.contains(macroRegion)) {
						return true;
					}
				}
				return false;
			}

			public boolean nodeContainsNonSpanningVariantMacros(IASTNode node) {
				ISourceRegion region = node.getSourceRegion();
				if (region == null)
					return false;
				for (ISourceRegion macroRegion : variantMacroExpansionRegions) {
					// first, see if the node has macro references embedded.
					if (region.contains(macroRegion)) {
						// if macro expands to exact content, fine
						//if (region.equals(macroRegion))
						//	return true;
						
						/*
						// now see if the macro does not span a child.
						boolean spansChild = false;
						for (IASTNode kid : node.getChildren()) {
							ISourceRegion kidRegion = kid.getSourceRegion();
							// look for kids that are not fully enclosed by the macro itself (meaning they were generated by it)
							if (kidRegion != null && macroRegion.equals(kidRegion)) {
								spansChild = true;
								break;
							}
						}*/
						// now see if the macro is not enclosed in a child.
						boolean enclosedInChild = false;
						for (IASTNode kid : node.getChildren()) {
							ISourceRegion kidRegion = kid.getSourceRegion();
							// look for kids that are not fully enclosed by the macro itself (meaning they were generated by it)
							if (kidRegion != null && region.contains(kidRegion)
									&& !region.getExclusiveHeadRegion().equals(kidRegion.getExclusiveHeadRegion())
									&& !region.getExclusiveTailRegion().equals(kidRegion.getExclusiveTailRegion())) {
								if (kidRegion.contains(macroRegion)) {
									enclosedInChild = true;
									break;
								}
							}
						}
						
						if (!enclosedInChild) {
							return true;
						}
						
						// keep searching (there may be several macro expansions at once) 
					}
					
					// TODO: stop searching eventually (need to order these regions somehow)
				}
				return false;
			}

			/**
			 * Turn off the macro-using flag for this line.
			 * @param node
			 */
			public void unsetNodeUsesMacros(IASTNode node) {
				macroReferencingNodes.remove(node);
				variantMacroReferencingNodes.remove(node);
			}
			
			/**
			 * @return the nodeToBlockMap
			 */
			public Map<IASTNode, IConditionalBlock> getNodeToBlockMap() {
				return nodeToBlockMap;
			}
		};
	}

	/**
	 * @param include
	 */
	private void filterOrAppendDirective(IASTPreprocessorStatement stmt) {
		stmt = outFilter.filterDirective(stmt);
		if (stmt != null) {
			appendDirectiveNode(stmt);
		}
	}

	protected void enteredTranslationUnit(IASTTranslationUnit ppTu) {
		IConditionalBlock block = new ConditionalBlock(ppTu.getSourceRegion());
		if (rootBlock == null)
			rootBlock = block;
		pushBlock(block);

	}
	
	protected void exitedTranslationUnit() {
		popBlock();
	}

	/**
	 * Add a node to the output.  The input may be null for new nodes
	 * (e.g. problems)
	 * @param ppNode original pre-parsed node, may be null
	 * @param node preprocessed node
	 */
	private void appendPreprocessedNode(IASTTopLevelNode ppNode, IASTTopLevelNode node) {
		if (ppNode != null)
			targetBlock.addNode(ppNode);

		if (isIncludingNode() || node instanceof IASTProblemNode) {
			tu.getNodes().add(node);
			
			if (ppNode != null) {
				node.setSourceNodes(new IASTNode[] { ppNode });
			}
			nodeToBlockMap.put(node, targetBlock);
			nodeToDepthMap.put(targetBlock, blockStack.size());
		}
	}

	/**
	 * Add a directive node to the output.  
	 * @param node original node
	 */
	private void appendDirectiveNode(IASTTopLevelNode node) {
		tu.getNodes().add((IASTTopLevelNode) node.copy());
	}

	/**
	 * 
	 */
	private void ensureTargetRegion() {
		if (targetBlock == null) {
			targetBlock = new ConditionalBlock();
			parentBlock.addChild(targetBlock);
		}
	}

	/**
	 * Tell whether the current node is included in
	 * the view model given the view filter and the
	 * current evaluation state.
	 * @return true: node is allowed by the view filter,
	 * false: it's not
	 */
	private boolean isIncludingNode() {
		
		if (blockStack.size() > 1) {
			// TODO: if variant macros...
			
			if (filter.evaluateConditionalStatements()) {
				if (filter.combineBranches())
					return true;
				else
					return currentState;
			} else {
				// no conditional statements allowed
				return false;
			}
		} else {
			return filter.evaluateUnconditionalStatements();
		}
	}

	/**
	 * Push an if() state onto onto the stack, adding it as a child of the current region,
	 * and update current region.
	 * @param block
	 */
	private void pushBlock(IConditionalBlock block) {
		blockStack.push(block);
		if (parentBlock != null)
			parentBlock.addChild(block);
		parentBlock = block;
	}

	/**
	 * Pop the current region and evaluation level from the stack.
	 */
	private void popBlock() {
		blockStack.pop();
		if (blockStack.size() > 0)
			parentBlock = blockStack.peek();
		else
			parentBlock = null;
	}
	
	/**
	 * Push an if() state onto onto the stack, adding it as a child of the current region,
	 * and update current region.
	 * @param region
	 */
	private void pushIfStack(IASTPreprocessorTestStatement stmt) {
		pushBlock(new IfViewRegion());
		ifStack.push(stmt);
		targetBlock = null;
	}

	/**
	 * Pop the current region and evaluation level from the stack.
	 */
	private void popIfStack(IASTTopLevelNode node) {
		popBlock();
		if (ifStack.size() == 0) {
			reportProblem(node, "ASTPreprocessor.UnbalancedIfsOnClose", //$NON-NLS-1$
					new Object[0]);
		} else {
			ifStack.pop();
		}
		targetBlock = null;
	}
	
	/**
	 * Create and push new view region(s) encompassing the tests
	 * performed in the expression.  
	 * <p>
	 * The new regions should be parented
	 * to the current region.
	 * @param node
	 */
	private void setupRegionsForConditional(IASTPreprocessorTestStatement stmt) {
		IConditionalBlock block = new ConditionalBlock(stmt);
		parentBlock.addChild(block);
		targetBlock = block;
	}

	/**
	 * Evaluate the value of the conditional statement in situ
	 * @param stmt
	 */
	private boolean evaluateConditional(IASTPreprocessorStatement stmt) {
		int value = 0;
		if (stmt instanceof IASTPreprocessorIfdefStatement) {
			value = evaluateDefined(((IASTPreprocessorIfdefStatement) stmt).getMacroName().getValue()) ? 1 : 0; 
		} else if (stmt instanceof IASTPreprocessorIfndefStatement) {
			value = evaluateDefined(((IASTPreprocessorIfndefStatement) stmt).getMacroName().getValue()) ? 0 : 1; 
		} else if (stmt instanceof IASTPreprocessorElifStatement) {
			value = evaluateExpr(((IASTPreprocessorElifStatement) stmt).getTokenStream());
		} else if (stmt instanceof IASTPreprocessorIfStatement) {
			value = evaluateExpr(((IASTPreprocessorIfStatement) stmt).getTokenStream());
		} else {
			Check.checkState(false);
		}
		
		return (value != 0);
	}
	
	/**
	 * Parse the tokens from an include directive and return
	 * whether it's user/system and its filename.  For use by
	 * the dependency scanner.
	 * @param macroProvider
	 * @param include
	 * @param tokens expanded token stream from #include (macro-expanded if possible)
	 * @return pair of Boolean=true for user, false for system and String=filename text
	 */
	public static Pair<Boolean, String> parseIncludeDirective(
			IASTPreprocessorIncludeStatement include,
			List<IToken> tokens) {
		String fileName = Token.getTokenText(tokens).trim();
		boolean isUser = false;
		if (fileName.matches("\".*\"")) { //$NON-NLS-1$
			isUser = true;
			fileName = fileName.substring(1, fileName.length() - 1);
		} else if (fileName.matches("<.*>")) { //$NON-NLS-1$
			fileName = fileName.substring(1, fileName.length() - 1);
		} else {
			/*
			 * This happens often with compiler prefix files which use #include __PRODUCT_PREFIX_FILE__.
			 * At this time we lack sufficient detail to resolve this macro, so hide the error.
			EpocEnginePlugin.log(new FileNotFoundException(
					"Cannot include file with incorrect syntax: " + fileName + " at " + include.getSourceReference())); //$NON-NLS-1$ //$NON-NLS-2$
			*/
			return null;
		}
		return new Pair(isUser, fileName);
		
	}
	
	private void reportProblem(IASTNode node, String messageKey, Object[] args) {
		MessageLocation location = node.getMessageLocation();
		IMessage message = ASTFactory.createErrorMessage(messageKey, args, location);
		
		emitProblem(message);
	}
	
	private void reportProblem(IASTPreprocessorStatement msgStmt) {
		MessageLocation location = msgStmt.getMessageLocation();
		IMessage message;
		List<IToken> tokens = null;
		String msgKey = null;
		int severity = 0;
		if (msgStmt instanceof IASTPreprocessorErrorStatement) {
			severity = IMessage.ERROR;
			msgKey = "ASTPreprocessor.ErrorDirective"; //$NON-NLS-1$
			tokens = ((IASTPreprocessorErrorStatement)msgStmt).getError().getTokens();
		} else if (msgStmt instanceof IASTPreprocessorWarningStatement) {
			severity = IMessage.WARNING;
			msgKey = "ASTPreprocessor.WarningDirective"; //$NON-NLS-1$
			tokens = ((IASTPreprocessorWarningStatement)msgStmt).getWarning().getTokens();
		} else {
			Check.checkState(false);
		}
		message = ASTFactory.createMessage(severity, msgKey, 
					new Object[] { Token.getTokenText(tokens) }, location);
		emitProblem(message);
	}

	/**
	 * @param message
	 */
	private void emitProblem(IMessage message) {
		IASTProblemTopLevelNode problem = ASTFactory.createProblemTopLevelNode(
				null, message);
		appendPreprocessedNode(null, problem);
	}

	private void handleIncludeStatement(IASTPreprocessorIncludeStatement include) {
		// parse the token stream to find the name and user/system style
		List<IToken> tokens = expandMacrosInStream(include.getName(), include);
		String fileName = Token.getTokenText(tokens).trim();
		boolean isUser = false;
		if (fileName.matches("\".*\"")) { //$NON-NLS-1$
			isUser = true;
			fileName = fileName.substring(1, fileName.length() - 1);
		} else if (fileName.matches("<.*>")) { //$NON-NLS-1$
			fileName = fileName.substring(1, fileName.length() - 1);
		} else {
			reportProblem(include, "ASTPreprocessor.IncludeIncorrectSyntax", //$NON-NLS-1$
					new Object[] { fileName, include.getSourceReference() });
			return;
		}
		
		// get the current location as a basis for the search
		File currentFile = null;
		IPath currentFilePath = null;
		ISourceRegion currentSourceRegion = include.getSourceRegion();
		if (currentSourceRegion != null) {
			currentFilePath = currentSourceRegion.getFirstLocation();
		}
		if (currentFilePath != null) {
			IPath filePath = currentFilePath.removeLastSegments(1);
			if (filePath.segmentCount() > 0) {
				currentFile = filePath.toFile();
			}
		}
		
		// find a matching file
		File includeFile = fileLocator.findIncludeFile(fileName, isUser, currentFile);
		
		if (includeFile == null) {
			reportProblem(include, "ASTPreprocessor.IncludeFileNotFound", //$NON-NLS-1$ 
					new Object[] { fileName, include.getSourceReference() });
			return;
		}
		
		if (outFilter != null) {
			IASTPreprocessorStatement stmt = outFilter.filterIncludeEntry(
					include, new Path(includeFile.getAbsolutePath()));
			if (stmt != null)
				appendDirectiveNode(stmt);
		}
		
		// remember we hit the file, and look up a cached TU
		referencedFiles.add(includeFile);
		ITranslationUnit includeTu_ = tuProvider.getTranslationUnit(includeFile, modelDocumentProvider);
		if (includeTu_ instanceof IASTTranslationUnit) {
			// make a copy of the TU, since it may be cached
			IASTTranslationUnit includeTu = (IASTTranslationUnit) ((IASTTranslationUnit) includeTu_).copy();
			nodeIterator.pushTranslationUnit(includeFile, includeTu, include);
		} else {
			reportProblem(include, "ASTPreprocessor.IncludeFileNotLoaded", //$NON-NLS-1$
					new Object[] { includeFile, include.getSourceReference() });
		}
	}
	
	private void handleDefineStatement(IASTPreprocessorDefineStatement define) {
		macros.put(define.getMacroName().getValue(),
				define);
	}
	
	private void handleUndefStatement(IASTPreprocessorUndefStatement undef) {
		macros.remove(undef.getMacroName().getValue());
	}
	
	private void handleErrorOrWarningStatement(IASTPreprocessorStatement msg) {
		// only report as problem if we aren't gleefully accepting
		// every node, since usually we'll see this in:
		//
		//	#if FOO
		// 		...
		//	#elif BAR
		//		...
		//	#else
		//		#error neither FOO nor BAR is set
		//	#endif
		//
		if (!filter.combineBranches()) {
			reportProblem(msg);
		}
	}

	/**
	 * Evaluate the node and create a region
	 * @param node
	 */
	private void handleTestStatement(IASTPreprocessorTestStatement node, boolean ignoreErrors) {
		if (node instanceof IASTPreprocessorIfStatement
				|| node instanceof IASTPreprocessorIfdefStatement
				|| node instanceof IASTPreprocessorIfndefStatement) {
			if (targetBlock != null && targetBlock.getStartNode() != null)
				((ConditionalBlock)targetBlock).setEndNode(node);
			pushIfStack(node); 
			((ConditionalBlock)parentBlock).setStartNode(node);
			setupRegionsForConditional(node);
			((ConditionalBlock)targetBlock).setStartNode(node);
			
			ifStateStack.push(ifState);
			if (ifState != null && ifState.isSkip()) {
				currentState = false;
				ifState = IfState.SkipToEndif;
			} else {
				boolean taken = evaluateConditional(node);
				currentState = taken;
				if (taken)
					ifState = IfState.IncludeToNext;
				else
					ifState = IfState.SkipToNext;
			}
		} 
		else if (node instanceof IASTPreprocessorElseStatement) {
			if (!ignoreErrors) {
				if (!(parentBlock instanceof IfViewRegion) 
					|| (targetBlock != null &&
							targetBlock.getCondition() instanceof IASTPreprocessorElseStatement)) {
					reportProblem(node, "ASTPreprocessor.UnexpectedElse", //$NON-NLS-1$
							new Object[0]);
					return;
				}
			}
			IConditionalBlock elseBlock = new ConditionalBlock(node);
			parentBlock.addChild(elseBlock);
			if (targetBlock != null)
				((ConditionalBlock)targetBlock).setEndNode(node);
			targetBlock = elseBlock;
			((ConditionalBlock)targetBlock).setStartNode(node);

			if (ifState == IfState.SkipToNext) {
				currentState = true;
				ifState = IfState.IncludeToEndif;
			} else {
				currentState = false;
				ifState = IfState.SkipToEndif;
			}

		} 
		else if (node instanceof IASTPreprocessorElifStatement) {
			if (!ignoreErrors && !(parentBlock instanceof IfViewRegion)) {
				reportProblem(node, "ASTPreprocessor.UnexpectedElif", //$NON-NLS-1$
						new Object[0]);
				return;
			}
			if (targetBlock != null)
				((ConditionalBlock)targetBlock).setEndNode(node);
			setupRegionsForConditional(node);
			((ConditionalBlock)targetBlock).setStartNode(node);
	
			if (ifState == IfState.SkipToNext) {
				boolean taken = evaluateConditional(node);
				if (taken) {
					ifState = IfState.IncludeToNext;
					currentState = true;
				} else {
					currentState = false;
				}
			} else if (ifState == IfState.SkipToEndif) {
				currentState = false;
			} else if (ifState == IfState.IncludeToNext) {
				ifState = IfState.SkipToEndif;
				currentState = false;
				
			}
		} 
		else if (node instanceof IASTPreprocessorEndifStatement) {
			if (!ignoreErrors && !(parentBlock instanceof IfViewRegion)) {
				reportProblem(node, "ASTPreprocessor.UnexpectedEndif", //$NON-NLS-1$
						new Object[0]);
				return;
			}
			if (targetBlock != null)
				((ConditionalBlock)targetBlock).setEndNode(node);
			((ConditionalBlock)parentBlock).setEndNode(node);
			popIfStack(node);
			
			ifState = ifStateStack.pop();
			currentState = ifState == null || !ifState.isSkip();
		}
	}

	/**
	 * Evaluate a preprocessor expression
	 * @param node
	 * @return
	 */
	private int evaluateExpr(IASTPreprocessorTokenStream tokenStream) {
		ASTParser parser = new ASTParser();
		IASTPreprocessorExpression expression = parser.parse(tokenStream, this);
		if (expression != null) {
			if (parser.hadErrors()) {
				reportProblem(tokenStream, "ASTPreprocessor.ConditionalInvalidExpression", //$NON-NLS-1$ 
						new Object[] { tokenStream.getNewText() });
			}
			return evaluateExpr(expression);
		}
		else {
			reportProblem(tokenStream, "ASTPreprocessor.ConditionalNoExpression", //$NON-NLS-1$ 
					new Object[0]);
			return 0;
		}
			
	}

	/**
	 * Evaluate a preprocessor expression
	 * @param node
	 * @return
	 */
	private int evaluateExpr(IASTPreprocessorExpression node) {
		if (node instanceof IASTPreprocessorLiteralExpression) {
			return evaluateLiteral((IASTPreprocessorLiteralExpression) node);
		} else if (node instanceof IASTPreprocessorUnaryExpression) {
			return evaluateUnary((IASTPreprocessorUnaryExpression) node);
		} else if (node instanceof IASTPreprocessorBinaryExpression) {
			return evaluateBinary((IASTPreprocessorBinaryExpression) node);
		} else if (node instanceof IASTPreprocessorTrinaryExpression) {
			return evaluateTrinary((IASTPreprocessorTrinaryExpression) node);
		} else {
			Check.checkState(false);
			return 0;
		}
	}

	/**
	 * @param expression
	 * @return
	 */
	private int evaluateUnary(IASTPreprocessorUnaryExpression expression) {
		IASTPreprocessorExpression op = expression.getOperand();
		switch (expression.getOperator()) {
		case IASTPreprocessorUnaryExpression.K_DEFINED:
			// TODO: error checking
			return macros.get(op.getNewText()) != null ? 1 : 0;
		case IASTPreprocessorUnaryExpression.K_INVERT:
			return ~evaluateExpr(op);
		case IASTPreprocessorUnaryExpression.K_NEGATE:
			return -evaluateExpr(op);
		case IASTPreprocessorUnaryExpression.K_NOT:
			return evaluateExpr(op) != 0 ? 0 : 1;
		case IASTPreprocessorUnaryExpression.K_PARENTHESIS:
			return evaluateExpr(op);
		case IASTPreprocessorUnaryExpression.K_PLUS:
			return evaluateExpr(op);
		default:
			Check.checkState(false);
		return 0;
		}
	}

	/**
	 * @param expression
	 * @return
	 */
	private int evaluateBinary(IASTPreprocessorBinaryExpression expression) {
		IASTPreprocessorExpression left = expression.getLeftOperand();
		IASTPreprocessorExpression right = expression.getRightOperand();
		switch (expression.getOperator()) {
		case IASTPreprocessorBinaryExpression.K_ADD:
			return evaluateExpr(left) + evaluateExpr(right);
		case IASTPreprocessorBinaryExpression.K_AND:
			return evaluateExpr(left) & evaluateExpr(right);
		case IASTPreprocessorBinaryExpression.K_DIV:
			return evaluateExpr(left) / evaluateExpr(right);
		case IASTPreprocessorBinaryExpression.K_EQUALS:
			return evaluateExpr(left) == evaluateExpr(right) ? 1 : 0;
		case IASTPreprocessorBinaryExpression.K_GREATER_THAN:
			return evaluateExpr(left) > evaluateExpr(right) ? 1 : 0;
		case IASTPreprocessorBinaryExpression.K_GREATER_THAN_OR_EQUALS:
			return evaluateExpr(left) >= evaluateExpr(right) ? 1 : 0;
		case IASTPreprocessorBinaryExpression.K_LESS_THAN:
			return evaluateExpr(left) < evaluateExpr(right) ? 1 : 0;
		case IASTPreprocessorBinaryExpression.K_LESS_THAN_OR_EQUALS:
			return evaluateExpr(left) <= evaluateExpr(right) ? 1 : 0;
		case IASTPreprocessorBinaryExpression.K_LOG_AND:
			return (evaluateExpr(left) != 0) && (evaluateExpr(right) != 0) ? 1 : 0;
		case IASTPreprocessorBinaryExpression.K_LOG_OR:
			return (evaluateExpr(left) != 0) || (evaluateExpr(right) != 0) ? 1 : 0;
		case IASTPreprocessorBinaryExpression.K_MOD:
			return evaluateExpr(left) % evaluateExpr(right);
		case IASTPreprocessorBinaryExpression.K_MUL:
			return evaluateExpr(left) * evaluateExpr(right);
		case IASTPreprocessorBinaryExpression.K_NOT_EQUALS:
			return evaluateExpr(left) != evaluateExpr(right) ? 1 : 0;
		case IASTPreprocessorBinaryExpression.K_OR:
			return evaluateExpr(left) | evaluateExpr(right);
		case IASTPreprocessorBinaryExpression.K_SHL:
			return evaluateExpr(left) << evaluateExpr(right);
		case IASTPreprocessorBinaryExpression.K_SHR:
			return evaluateExpr(left) >> evaluateExpr(right);
		case IASTPreprocessorBinaryExpression.K_SUB:
			return evaluateExpr(left) - evaluateExpr(right);
		case IASTPreprocessorBinaryExpression.K_XOR:
			return evaluateExpr(left) ^ evaluateExpr(right);
		default:
			Check.checkState(false);
			return 0;
		}
	}

	/**
	 * @param expression
	 * @return
	 */
	private int evaluateTrinary(IASTPreprocessorTrinaryExpression expression) {
		IASTPreprocessorExpression trueB = expression.getTrueBranch();
		IASTPreprocessorExpression falseB = expression.getFalseBranch();
		IASTPreprocessorExpression cond = expression.getCondition();
		return (evaluateExpr(cond) != 0) ? evaluateExpr(trueB) : evaluateExpr(falseB);
	}

	/**
	 * @param node
	 * @return
	 */
	private int evaluateLiteral(IASTPreprocessorLiteralExpression lit) {
		// will be number by now, or else undefined macro
		String value = lit.getValue();
		try {
			return ASTNode.parseIntValue(value);
		} catch (NumberFormatException e) {
			// Undefined macro
			return 0;
		}
	}

	private IASTTopLevelNode expandMacrosInStatement(IASTTopLevelNode ppNode) {
		if (!(ppNode instanceof IASTPreprocessorTokenStreamStatement))
			return (IASTTopLevelNode) ppNode.copy();
		
		IASTPreprocessorTokenStreamStatement ppTokenStream = (IASTPreprocessorTokenStreamStatement) ppNode;
		List<IToken> tokens = expandMacrosInStream(ppTokenStream.getTokenStream(), ppNode);
		
		IASTPreprocessorTokenStreamStatement tokenStreamStmt = ASTFactory.createPreprocessorTokenStreamStatement();
		tokenStreamStmt.setId(ppNode.getId());
		tokenStreamStmt.setSourceNodes(ppNode.getSourceNodes());
		IASTPreprocessorTokenStream tokenStream = tokenStreamStmt.getTokenStream();
		
		for (IToken token : tokens) {
			tokenStream.getTokens().add(token);
		}
		
		ParserUtils.setSourceRangeForTokenStream(tokenStream, tokenStream);
		tokenStreamStmt.copySourceInfo(tokenStream);
		
		return tokenStreamStmt;
	}

	private List<IToken> expandMacrosInStream(IASTPreprocessorTokenStream stream, IASTNode owningNode) {
		
		MacroExpandingStream expandedStream = new MacroExpandingStream(this, stream.getTokens());
		
		List<IToken> tokens = null;
		
		MacroExpandingTokenIterator iter = expandedStream.iterator();
		try {
			while (true /*iter.hasNext()*/) {
				IToken token = iter.next();
				if (tokens == null) { 
					tokens = new ArrayList<IToken>();
				}
				tokens.add(token);
			}
		} catch (NoSuchElementException e) {
		}
		
		if (iter.hitMacros().size() > 0) {
			macroReferencingNodes.add(owningNode);
			for (IASTPreprocessorDefineStatement define : iter.hitMacros()) {
				// Weed out variant macros.  
				// NOTE: We assume that macros defined to themselves are
				// MMP platform macros and consider them invariant.
				if (isPlatformMacro(define)) {
					// platform macro; considered invariant
				} else {
					variantMacroReferencingNodes.add(owningNode);
				}					
			}
		}
		for (IMacroTokenLocation location : iter.getMacroExpansionTokenLocations()) {
			ISourceRegion info = ParserUtils.getFlattenedTokenLocation(null, location);
			if (info != null && !isPlatformMacro(location.getDefineStatement())) {
				variantMacroExpansionRegions.add(info);
			}
		}
		return tokens;
	}

	private boolean isPlatformMacro(IASTPreprocessorDefineStatement define) {
		return define.getMacroArgs() == null && define.getMacroExpansion() != null
				&& define.getMacroName().getValue().equals(define.getMacroExpansion().getNewText());
	}

	private boolean evaluateDefined(String macroName) {
		return macros.containsKey(macroName);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.preprocessor.IMacroProvider#lookupMacro(java.lang.String)
	 */
	public IASTPreprocessorDefineStatement lookupMacro(String name) {
		return macros.get(name);
	}

	
	public int getBlockDepth(IConditionalBlock block) {
		Integer depth = nodeToDepthMap.get(block);
		Check.checkArg(depth);
		return depth;
	}

}
