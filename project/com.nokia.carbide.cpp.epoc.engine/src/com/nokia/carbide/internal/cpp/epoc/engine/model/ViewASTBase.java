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

package com.nokia.carbide.internal.cpp.epoc.engine.model;

import com.nokia.carbide.cpp.epoc.engine.model.IModelDocumentProvider;
import com.nokia.carbide.cpp.epoc.engine.model.IOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.IViewConfiguration;
import com.nokia.carbide.cpp.epoc.engine.model.IViewParserConfiguration;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ASTFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorTokenStreamStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTProblemNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTopLevelNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ISourceRegion;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessor;
import com.nokia.carbide.internal.api.cpp.epoc.engine.preprocessor.IPreprocessorResults;
import com.nokia.carbide.internal.cpp.epoc.engine.dom.ASTUtils;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ITranslationUnitParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ProblemVisitor;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.*;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.IDocument;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Base implementation of a view that uses an AST / DOM.
 * 
 */
public abstract class ViewASTBase<Model extends IOwnedModel> extends ViewBase<Model> {
	private IASTTranslationUnit tu;
	private TranslationUnitListener tuListener;
	private IPreprocessor preprocessor;
	private IPreprocessorResults ppResults;
	
	public ViewASTBase(ModelBase model, ITranslationUnitParser parser,
			IViewConfiguration viewConfiguration) {
		super(model, parser, viewConfiguration);
	}

	/**
	 * Reparse the preprocessor TU into the language TU.
	 */
	protected Map<IPath, IDocument> internalReparse(Map<IPath, IDocument> documentMap) {
		// preprocess...
		this.preprocessor = createPreprocessor(documentMap);
		this.ppResults = preprocessor.preprocess(
				model.getTranslationUnit(),
				getViewConfiguration().getViewFilter(),
				getViewConfiguration().getMacros());

		// then parse.
		this.tu = parser.parse(ppResults);
		return ASTUtils.getDocumentMap(this.tu);
	}

	/**
	 * Reparse the preprocessor TU into the language TU.
	 */
	protected void reparse(boolean useLoadedDocuments) {
		if (this.tu != null && this.tuListener != null) {
			this.tu.removeListener(tuListener);
		}

		super.reparse(useLoadedDocuments);
		
		if (this.tu != null) {
			this.tuListener = new TranslationUnitListener();
			this.tu.addListener(tuListener);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IView#getFilteredTranslationUnit()
	 */
	public synchronized IASTTranslationUnit getFilteredTranslationUnit() {
		return tu;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IView#getProblemNodes()
	 */
	public synchronized IASTProblemNode[] getProblemNodes() {
		if (tu != null) {
			ProblemVisitor visitor = new ProblemVisitor();
			tu.accept(visitor);
			return visitor.getProblems();
		} else {
			return new IASTProblemNode[0];
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IView#getMessages()
	 */
	public IMessage[] getMessages() {
		if (messages == null) {
			final List<IMessage> messageList = new ArrayList<IMessage>();
			IASTProblemNode[] problems = getProblemNodes();
			for (IASTProblemNode problem : problems) {
				messageList.add(problem.getMessage());
			}
			addViewSpecificMessages(messageList);
			messages = (IMessage[]) messageList.toArray(new IMessage[messageList.size()]);
		}		
		return messages;
	}

	/**
	 * Add messages to the list specific to issues encountered while parsing the filtered TU.
	 * @param messageList
	 */
	abstract protected void addViewSpecificMessages(List<IMessage> messageList);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IView#getReferencedFiles()
	 */
	public synchronized IPath[] getReferencedFiles() {
		if (referencedPaths == null && ppResults != null) {
			Collection<File> files = ppResults.getReferencedFiles();
			referencedPaths = new IPath[files.size() + 1];
			int idx = 0;
			referencedPaths[idx++] = model.getPath();
			for (File file : files) {
				try {
					referencedPaths[idx++] = new Path(file.getCanonicalPath());
				} catch (IOException e) {
					referencedPaths[idx++] = new Path(file.getAbsolutePath());
				}
			}
		}
		return referencedPaths;
	}

	/** 
	 * A proxy to provide alternate IDocuments from a map, for use in
	 * reverting the view after commit() but before any listeners may have
	 * persisted contents to disk.
	 *
	 */
	private class ProxyModelDocumentProvider implements IModelDocumentProvider {

		public final IModelDocumentProvider backendProvider;
		private final Map<IPath, IDocument> documentMap;
		public ProxyModelDocumentProvider(IModelDocumentProvider backendProvider,
				Map<IPath, IDocument> documentMap) {
			this.backendProvider = backendProvider;
			this.documentMap = documentMap;
		}
		
		public IDocument getDocument(File file) {
			// XXX: assume the IPath keys are canonical
			try {
				file = file.getCanonicalFile();
			} catch (IOException e) {
				
			}
			IDocument document = documentMap.get(new Path(file.getAbsolutePath()));
			if (document == null) {
				document = backendProvider.getDocument(file);
			}
			return document;
		}
		
	}
	private IPreprocessor createPreprocessor(Map<IPath, IDocument> documentMap) {
		IViewParserConfiguration viewParserConfiguration = viewConfiguration.getViewParserConfiguration();
		IModelDocumentProvider modelDocumentProvider = viewParserConfiguration.getModelDocumentProvider();
		if (documentMap != null) {
			modelDocumentProvider = new ProxyModelDocumentProvider(modelDocumentProvider, documentMap);
		}
		IPreprocessor preprocessor = new ASTPreprocessor(
				viewParserConfiguration.getTranslationUnitProvider(),
				viewParserConfiguration.getIncludeFileLocator(),
				modelDocumentProvider);
		return preprocessor;
	}

	/**
	 * Examine the changes needed in the filtered DOM and generate 
	 * view modifications.  This may be a destructive operation
	 * (e.g. making the view invalid).  The view will be reverted afterwards.
	 * @param modifications fill in with IViewModification
	 * @param messages add any messages encountered during gathering changes
	 */
	protected abstract void internalGatherChanges(List<IViewModification> modifications, List<IMessage> messages);

	/**
	 * Finalize a preparser DOM (with directives and comments included).  
	 * This may be used, for example, to optimize the structure or add spacing, etc.
	 * It should also be used to expand any augmented nodes added to the DOM
	 * (e.g. for context statements).  
	 * <p>
	 * This will be called once for every file used in the view.
	 * <p>
	 * The preparser DOM will reflect the changes
	 * made from {@link #internalGatherChanges(List, List)}, but not necessarily with
	 * the exact same nodes.  
	 * <p>
	 * NOTE: the preparser DOM will consist of token stream statements and directives
	 * only, unless the view provides {@link IViewChangeModification}
	 * entries to replace all preparser nodes with language nodes, even for unchanged
	 * content. 
	 */
	protected abstract void internalFinalizePreparserTranslationUnit(IASTTranslationUnit tu);
	
	/**
	 * This implementation asks the view to apply changes to its filtered DOM
	 * and then synchronizes the model's preparser DOM with that DOM.
	 */
	protected void internalCommit()  {
		List<IViewModification> modifications = new ArrayList<IViewModification>();
		List<IMessage> messages = new ArrayList<IMessage>();
		lock();
		try {
			lastDirectoryPath = null;
			updateCurrentDirectory(getModel().getPath());
			internalGatherChanges(modifications, messages);
		} finally {
			unlock();
		}
		
		boolean changed = false;
		
		model.lock();
		try {
			Map<File, IASTTranslationUnit> updatedFileMap = Collections.EMPTY_MAP;
			if (tu != null && modifications != null) {
				IASTTranslationUnit modelTu = model.getTranslationUnit();
				ViewDOMSynchronizer synchronizer = new ViewDOMSynchronizer(
						modelTu,
						tu,
						ppResults,
						modifications);
				ISynchronizerResults results = synchronizer.sync();
				messages.addAll(results.getMessages());
				
				updatedFileMap = results.getUpdatedFileMap();
				for (Map.Entry<File, IASTTranslationUnit> entry : updatedFileMap.entrySet()) {
					IASTTranslationUnit tu = entry.getValue();
					internalFinalizePreparserTranslationUnit(tu);
					addSpacing(tu.getNodes());
				}
			}
	
			changed = model.commitDocument(ppResults, updatedFileMap, this);
		} finally {
			model.unlock();
		}
		
		// start over to get fresh source locations and data, using the updated documents
		doRevert(true);
		
		// apply any messages to the DOM for clients to see
		lock();
		for (IMessage message : messages) {
			tu.getNodes().add(ASTFactory.createProblemTopLevelNode(null, message));
		}
		unlock();
		this.messages = null; // in case cached
		
		// always notify of change, or else we've destroyed the DOM and other views will fail to commit
		model.desyncOtherViews(this);
		if (changed) {
			model.fireViewChanged(this);
		}
	}

	/**
	 * This ensures that the TU owned by the view is not changed outside a commit.
	 * <p>
	 * Method exposed to unit tests only
	 *
	 */
	public void unlock() {
		tuListener.allowChanges(false);
	}

	/**
	 * This ensures that the TU owned by the view may be changed inside a commit.
	 * <p>
	 * Method exposed to unit tests only
	 *
	 */
	public void lock() {
		tuListener.allowChanges(true);
	}

	/**
	 * Tell if a node in the language TU is inside a conditional block.
	 * 
	 * @param langNode
	 * @return
	 */
	public boolean isLangNodeInConditionalBlock(IASTNode langNode) {
		return getLangNodeConditionalBlock(langNode) != null;
	}

	/**
	 * Return the conditional block, with a condition, containing
	 * the given node.
	 * 
	 * @param langNode
	 * @return conditional block, or null if not conditional
	 */
	public IConditionalBlock getLangNodeConditionalBlock(IASTNode langNode) {
		if (langNode.getSourceRegion() == null)
			return null;
		IConditionalBlock conditionalBlock = 
			ConditionalBlockUtils.findBlockContaining(
						ppResults.getRootBlock(),
						langNode.getSourceRegion());
		while (conditionalBlock != null
				&& !(conditionalBlock instanceof IfViewRegion))
			conditionalBlock = conditionalBlock.getParent();
		return conditionalBlock;
	}


	/**
	 * Update existing uncommitted changes against the new TU.
	 * <p>
	 * When this is called, the document, model, and view has been updated with
	 * the new TUs, but changes are still present. The old filtered TU is passed
	 * in for reference.
	 * <p>
	 * The implementation does not modify the TU, but modifies its changes:
	 * <li>If the TU reflects a change, remove the change.
	 * <li>If the TU conflicts with a change, keep the change, but return false
	 * later.
	 * <li>Otherwise, keep the change.
	 * 
	 * @param oldTu
	 *            the original TU before merge
	 * @return true: merge succeeded (all changes are reflected or
	 *         non-interfering), else some changes conflict and a #revert() or
	 *         #forceSynchronized() is needed
	 */
	protected abstract boolean internalMerge(IASTTranslationUnit oldTu);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IView#merge()
	 */
	public synchronized boolean merge() {
		synchronized (model) {
			IASTTranslationUnit oldTu = tu;
	
			// get the new filtered TU
			reparse(false);
	
			boolean succeeded = internalMerge(oldTu);
			if (succeeded)
				outOfSync = false;
	
			fireChanged();
			return succeeded;
		}
	}

	/**
	 * @return the results from the last preprocessing
	 */
	public IPreprocessorResults getPreprocessorResults() {
		return ppResults;
	}

	private IPath getNodeLocation(IASTNode node) {
		if (node.getSourceRegion() == null)
			return null;
		return node.getSourceRegion().getFirstLocation();
	}
	
	/**
	 * Tell whether the node comes from a file in the project.
	 * @param node
	 * @return
	 */
	public boolean isNodeInProject(IASTNode node) {
		while (node != null && getNodeLocation(node) == null)
			node = node.getParent();
		if (node != null) {
			// an absolute path will be returned if the location cannot be resolved
			// as the same or child of the project location
			IPath projectLocation = getSourceRelativePath(getNodeLocation(node), false);
			return !projectLocation.isAbsolute();
		} else {
			// no source --> must be in project
			return true;
		}
	}
	
    protected static Object EMPTY_LINE_CATEGORY = new Object();
    protected static Object PREPROCESSOR_LINE_CATEGORY = new Object();
    protected static Object PROBLEM_LINE_CATEGORY = new Object();
    
	/**
     * Take a list of nodes and add blank lines where 
     * appropriate, such as between newly generated categories.
     * Do not alter nodes with source ranges.
     * @param nodes
     */
    protected void addSpacing(IASTListNode<IASTTopLevelNode> nodes) {
    	Object prevKnownCategory = null;
        List<IASTTopLevelNode> origNodes = new ArrayList<IASTTopLevelNode>(nodes);
        for (IASTTopLevelNode node : origNodes) {
        	Object category = getSpacingCategory(node);
            
            if (prevKnownCategory != null 
                && category != null
                && prevKnownCategory != category) {
            	// a category switch
            	if (node.getSourceRegion() == null && prevKnownCategory != EMPTY_LINE_CATEGORY) {
            		int index = nodes.indexOf(node);
            		nodes.add(index, ASTFactory.createPreprocessorTokenStreamStatement("\n")); //$NON-NLS-1$
            	}
            }
            prevKnownCategory = category;
        }    	
    }
    
    /**
     * Return an object which is the same as all other objects in the same spacing
     * category.  Items in the same spacing category do not have additional
     * newlines added in between.  
	 * @param node node to test
	 * @return an object which is == to others in same category, or null
	 * @see #EMPTY_LINE_CATEGORY for a canonical blank line
	 * @see #PREPROCESSOR_LINE_CATEGORY for a canonical comment or problem
	 */
	protected Object getSpacingCategory(IASTTopLevelNode node) {
		if (node instanceof IASTPreprocessorTokenStreamStatement) {
			if (node.getNewText().trim().length() == 0)
				return EMPTY_LINE_CATEGORY;
			else
				return PREPROCESSOR_LINE_CATEGORY;
		} else if (node instanceof IASTPreprocessorStatement) {
			return PREPROCESSOR_LINE_CATEGORY;
		} else if (node instanceof IASTProblemNode) {
			return PROBLEM_LINE_CATEGORY;
		}
		return null;
	}

	/**
	 * Update the current directory based on the filepath of the given node.
	 * @param node
	 */
	public void updateCurrentDirectory(IASTNode node) {
		ISourceRegion region = node.getSourceRegion();
		if (region != null)
			updateCurrentDirectory(region.getInclusiveHeadRegion().getLocation());
	}
	
	/**
	 * Return a list of statements discovered during the last reparse which
	 * were resolved into model elements.  Other statements, either those
	 * ignored or those with errors, should not be added here.
	 * @return non-null collection
	 */
	abstract public Collection<IASTStatement> getKnownStatements();
}
