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

package com.nokia.carbide.internal.cpp.epoc.engine.parser;

import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTProblemNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTopLevelNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTranslationUnit;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTVisitor;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IDocumentSourceRegion;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ISourceRegion;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.jface.text.IDocument;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * This is a parser for MMP. 
 *
 */
public abstract class BaseASTParser implements ITranslationUnitParser {

	protected boolean hadErrors;
	
	public IASTTranslationUnit finalizeTu(IASTTranslationUnit ppTu, IASTTranslationUnit langTu) {
		langTu.setMainDocument(ppTu.getMainDocument());
		langTu.setMainLocation(ppTu.getMainLocation());
		
		mergeProblems(langTu, ppTu);
		
		if (langTu == null)
			return null;
		
		langTu.setDirtyTree(false);

		// Every statement consumes at least one PP-stmt, and sometimes more.
		// We scan the preprocessed DOM, but we need to get nodes from the preparser DOM, 
		// to capture any embedded conditional #ifs
		List<IASTTopLevelNode> tsList = new ArrayList<IASTTopLevelNode>();
		ListIterator<IASTTopLevelNode> iterator = ppTu.getNodes().listIterator();
		IASTNode curNode = null;
		IASTTopLevelNode prevPreparsedNode = null;
		for (IASTTopLevelNode node : langTu.getNodes()) {
			tsList.clear();
			ISourceRegion nodeRegion = node.getSourceRegion();
			if (nodeRegion != null) {
				while (!nodeRegion.isEmpty() && iterator.hasNext()) {
					curNode = iterator.next();
					ISourceRegion curSourceRegion = curNode.getSourceRegion();
					if (curSourceRegion == null) {
						continue;
					}
					
					if (nodeRegion.contains(curSourceRegion)) {
						prevPreparsedNode = addPreparsedNodeSequenceFrom(tsList, prevPreparsedNode, curNode.getSourceNodes());
						nodeRegion = nodeRegion.getRegionWithout(
								((IDocumentSourceRegion)curSourceRegion).extendToStart());
					} 
				}
			}
			if (!(node instanceof IASTProblemNode)) {
				if (tsList.size() == 0 && !node.getSourceRegion().isEmpty()) {
					EpocEnginePlugin.log(new IllegalStateException(
							"The parser encountered an unexpected problem; it may be corrupted if saved:  " + node.getSourceReference())); //$NON-NLS-1$
					Check.checkState(false);
				}
			}
			IASTTopLevelNode[] tsArray = tsList.toArray(new IASTTopLevelNode[tsList.size()]);
			node.setSourceNodes(tsArray);
			prevPreparsedNode = null;
		}

		langTu.setSourceNodes(new IASTNode[] { ppTu });
		
		langTu.accept(new IASTVisitor() {
			int idx = 0;

			public int visit(IASTNode node) {
				node.setId(new Integer(idx++));
				return VISIT_CHILDREN;
			}
		});
		
		return langTu;
	}

	private IASTTopLevelNode addPreparsedNodeSequenceFrom(List<IASTTopLevelNode> tsList, IASTTopLevelNode prevPreparsedNode, IASTNode[] sourceNodes) {
		for (int idx = 0; idx < sourceNodes.length; idx++) {
			IASTTopLevelNode sourceNode = (IASTTopLevelNode) sourceNodes[idx];
			if (prevPreparsedNode != null && isInSameDocument(prevPreparsedNode, sourceNode)) {
				while (prevPreparsedNode != null && prevPreparsedNode != sourceNode) {
					tsList.add(prevPreparsedNode);
					prevPreparsedNode = prevPreparsedNode.getNext();
				}
			}
			tsList.add(sourceNode);
			prevPreparsedNode = sourceNode.getNext();
		}
		return prevPreparsedNode;
	}

	private boolean isInSameDocument(IASTTopLevelNode node1,
			IASTTopLevelNode node2) {
		IDocument doc1 = getDocument(node1);
		IDocument doc2 = getDocument(node2);
		return doc1 == doc2;
	}

	private IDocument getDocument(IASTTopLevelNode node) {
		if (node.getSourceRegion() == null)
			return null;
		return node.getSourceRegion().getInclusiveHeadRegion().getDocument();
	}

	/**
	 * @param mmpTu
	 * @param ppTu
	 */
	private void mergeProblems(IASTTranslationUnit mmpTu, IASTTranslationUnit ppTu) {
		ProblemVisitor problemVisitor = new ProblemVisitor();
		ppTu.accept(problemVisitor);
		IASTProblemNode[] problems = problemVisitor.getProblems();
		for (IASTProblemNode node : problems) {
			hadErrors = true;
			if (node instanceof IASTTopLevelNode)
				mmpTu.getNodes().add((IASTTopLevelNode) node.copy());
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.parser.IParser#hadErrors()
	 */
	public boolean hadErrors() {
		return hadErrors;
	}
}
