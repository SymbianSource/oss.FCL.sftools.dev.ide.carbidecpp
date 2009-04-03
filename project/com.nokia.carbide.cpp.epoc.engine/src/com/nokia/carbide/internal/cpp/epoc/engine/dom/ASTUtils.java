/*
* Copyright (c) 2009-2009 Nokia Corporation and/or its subsidiary(-ies).
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
package com.nokia.carbide.internal.cpp.epoc.engine.dom;

import com.nokia.carbide.cpp.epoc.engine.DocumentFactory;
import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTProblemNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTVisitor;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IDocumentSourceRegion;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IMultiDocumentSourceRegion;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ISourceRegion;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ISourceRegionVisitor;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IConditionalBlock;
import com.nokia.carbide.internal.cpp.epoc.engine.preprocessor.IConditionalBlockVisitor;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class ASTUtils {
	/**
	 * Validate that the source locations for a node and its children are
	 * consistent.
	 * <p>
	 * The location in a parent must include every location of its children,
	 * but a child may have no source.
	 * @param node
	 * @throws IllegalStateException if sources are invalid
	 */
	public static void validateSourceLocations(IASTNode node) throws IllegalStateException {
		StringBuilder builder = new StringBuilder();
		validateNodeSourceLocations(node, builder);
		if (builder.length() > 0)
			throw new IllegalStateException(builder.toString());
	}

	private static void validateNodeSourceLocations(IASTNode node, StringBuilder builder) {
		ISourceRegion sourceRegion = node.getSourceRegion();
		if (sourceRegion != null) {
			try {
				sourceRegion.validate();
			} catch (IllegalStateException e) {
				builder.append(e.getMessage() + "\n"); //$NON-NLS-1$
			}
		}
		for (IASTNode child : node.getChildren()) {
			ISourceRegion childRegion = child.getSourceRegion();
			if (sourceRegion != null) {
				if (childRegion != null && !sourceRegion.contains(childRegion))
					builder.append(sourceRegion + " does not contain " + childRegion + " in " + child + " for parent " + node + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			} else {
				if (childRegion != null)
					builder.append("non-null child location " + childRegion + " in " + child + " for parent " + node + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			}
			validateNodeSourceLocations(child, builder);
		}
	}

	/**
	 * Create a source location for the given document, path, and region.
	 * @param document non-null IDocument
	 * @param location non-null full IPath
	 * @param region non-null IRegion
	 */
	public static IDocumentSourceRegion createDocumentSourceRegion(IDocument document, IPath location, IRegion region) {
		return new DocumentSourceLocation(document, location, region);
	}

	/**
	 * Set the source location for a node that includes the range between the given locations.
	 * This may collapse regions together.  If two regions span a document boundary, the end
	 * of the previous and head of the next document are automatically included.
	 * @param startRegion an ISourceLocation, may be null
	 * @param endRegion an ISourceLocation, may be null
	 * @return new ISourceLocation if both inputs are non-null, else null
	 */
	public static ISourceRegion getEnclosingRegion(ISourceRegion startRegion, ISourceRegion endRegion) {
		if (startRegion == null || endRegion == null)
			return null;
		
		IMultiDocumentSourceRegion multiSourceRegion = new MultiDocumentSourceLocation();
		multiSourceRegion.addSourceRegion(startRegion);
		
		IDocumentSourceRegion startDocEndRegion = startRegion.getExclusiveTailRegion();
		IDocumentSourceRegion endDocStartRegion = endRegion.getExclusiveHeadRegion();
		if (startDocEndRegion.getDocument() == endDocStartRegion.getDocument()) {
			// same document, so combine
			multiSourceRegion.addSourceRegion(new DocumentSourceLocation(
						startDocEndRegion.getDocument(),
						startDocEndRegion.getLocation(),
						new Region(startDocEndRegion.getRegion().getOffset(),
								endDocStartRegion.getRegion().getLength() +
								endDocStartRegion.getRegion().getOffset() -
								startDocEndRegion.getRegion().getOffset())));
		} else {
			// get the tail of the start doc and the head of the end doc
			int startDocLength = startDocEndRegion.getDocument().getLength();
			if (startDocLength > startDocEndRegion.getRegion().getOffset()) {
				multiSourceRegion.addSourceRegion(new DocumentSourceLocation(
						startDocEndRegion.getDocument(),
						startDocEndRegion.getLocation(),
						new Region(startDocEndRegion.getRegion().getOffset(),
								startDocLength - startDocEndRegion.getRegion().getOffset())));
			}
			int endDocStart = endDocStartRegion.getRegion().getOffset();
			if (endDocStart > 0) {
				multiSourceRegion.addSourceRegion(new DocumentSourceLocation(
						endDocStartRegion.getDocument(),
						endDocStartRegion.getLocation(),
						new Region(0, endDocStartRegion.getRegion().getOffset())));
			}
		}
		
		multiSourceRegion.addSourceRegion(endRegion);
		return multiSourceRegion.getCanonicalSourceRegion();
		
	}

	/**
	 * Get the location encompassing all the locations, including blank
	 * space between 
	 * @param locations non-null array
	 * @return new ISourceLocation or null for empty location list
	 */
	public static ISourceRegion getEnclosingRegion(ISourceRegion[] locations) {
    	if (locations.length == 0)
    		return null;
    	
		if (locations.length == 1) {
			return locations[0].copy();
		} 

		Map<Pair<IDocument, IPath>, IRegion> documentSpans = new LinkedHashMap<Pair<IDocument, IPath>, IRegion>();
		for (ISourceRegion region : locations) {
			for (IDocumentSourceRegion docRegion : region.getDocumentSourceRegions()) {
				updateDocumentSpan(documentSpans, (IDocumentSourceRegion) docRegion);
			}
		}
		IMultiDocumentSourceRegion multiRegion = new MultiDocumentSourceLocation();
		for (Map.Entry<Pair<IDocument, IPath>, IRegion> entry : documentSpans.entrySet()) {
			multiRegion.addSourceRegion(new DocumentSourceLocation(
					entry.getKey().first, entry.getKey().second, entry.getValue()));
		}
		return multiRegion.getCanonicalSourceRegion();
		
		/*
		ISourceLocation location = null;
		for (ISourceLocation childLocation : locations) {
			if (childLocation != null) {
				if (location != null) {
					location = ASTUtils.getEnclosingLocation(location, childLocation);
				} else {
					location = childLocation.copy();
				}
			}
		}
		return location;
		*/
		
	}
	
	/**
	 * Get the location representing a serial composition of the given locations.
	 * This assumes that adjacent regions in the same document consume intermittent text.
	 * Unlike {@link #getEnclosingRegion(...)}, though, regions are assumed to be in order.
	 * @param locations non-null array
	 * @return new ISourceLocation or null for empty location list
	 */
	public static ISourceRegion getSerialRegion(ISourceRegion[] locations) {
    	if (locations.length == 0)
    		return null;
    	
		if (locations.length == 1) {
			return locations[0].copy();
		} 

		IMultiDocumentSourceRegion multiRegion = new MultiDocumentSourceLocation();

		IDocument currentDocument = null;
		IPath currentPath = null;
		IRegion currentRegion = null;
		for (ISourceRegion region : locations) {
			for (IDocumentSourceRegion docRegion : region.getDocumentSourceRegions()) {

				if (currentDocument != null && currentDocument != docRegion.getDocument()) {
					multiRegion.addSourceRegion(new DocumentSourceLocation(
							currentDocument, currentPath, currentRegion));
					currentDocument = null;
				}
				
				if (currentDocument == null) {
					currentDocument = docRegion.getDocument();
					currentPath = docRegion.getLocation();
					currentRegion = docRegion.getRegion();
				} else {
					int locStart = docRegion.getRegion().getOffset();
					int locEnd = locStart + docRegion.getRegion().getLength();
					int start = currentRegion.getOffset();
					int end = start + currentRegion.getLength();
					int newMin = Math.min(locStart, start);
					currentRegion = new Region(newMin, Math.max(locEnd, end) - newMin);
				}
			}
		}

		if (currentDocument != null) {
			multiRegion.addSourceRegion(new DocumentSourceLocation(
					currentDocument, currentPath, currentRegion));
		}
		
		return multiRegion.getCanonicalSourceRegion();
		
	}
	
	
	private static void updateDocumentSpan(Map<Pair<IDocument, IPath>, IRegion> documentSpans, IDocumentSourceRegion docRegion) {
		IDocument document = docRegion.getDocument();
		Pair<IDocument, IPath> docKey = new Pair(document, docRegion.getLocation());
		IRegion region = documentSpans.get(docKey);
		if (region == null) {
			documentSpans.put(docKey, docRegion.getRegion());
		} else {
			int locStart = docRegion.getRegion().getOffset();
			int locEnd = locStart + docRegion.getRegion().getLength();
			int start = region.getOffset();
			int end = start + region.getLength();
			int newMin = Math.min(locStart, start);
			region = new Region(newMin, Math.max(locEnd, end) - newMin);
			documentSpans.put(docKey, region);
		}
		
	}

	/**
	 * Get the location encompassing all the nodes, including blank
	 * space between 
	 * @param nodes
	 * @return new ISourceLocation or null for empty node list
	 */
	public static ISourceRegion getEnclosingRegion(IASTNode[] nodes) {
		List<ISourceRegion> regions = new ArrayList<ISourceRegion>();
		for (IASTNode node : nodes) {
			ISourceRegion region = node.getSourceRegion();
			if (region != null)
				regions.add(region);
		}
		return getEnclosingRegion((ISourceRegion[]) regions.toArray(new ISourceRegion[regions.size()]));
	}
	
	public static boolean nodesSpanProblems(IASTNode[] ppStmts) {
		for (IASTNode ppStmt : ppStmts) {
			if (ppStmt instanceof IASTProblemNode)
				return true;
		}
		return false;
	}

	public static IASTNode[] getOriginalSourceNodes(IASTNode langNode) {
		IASTNode[] ppStmts = langNode.getSourceNodes();
		if (ppStmts != null) {
			for (int i = 0; i < ppStmts.length; i++) {
				// this is either a preparsed or preprocessor DOM
				IASTNode[] origNodes = ppStmts[i].getSourceNodes();
				if (origNodes != null) {
					Check.checkState(origNodes.length == 1);
					ppStmts[i] = origNodes[0];
				}
			}
		}
		return ppStmts;
	}

	/**
	 * Tell if a node in the language TU contains a conditional block.
	 * 
	 * @param langNode
	 * @return
	 */
	public static boolean isConditionalBlockInLangNode(IConditionalBlock rootBlock, IASTNode langNode) {
		final ISourceRegion langNodeRegion = langNode.getSourceRegion();
		if (langNodeRegion == null)
			return false;

		final IConditionalBlock[] theRegion = new IConditionalBlock[1];
		rootBlock.accept(new IConditionalBlockVisitor() {

			public int visit(IConditionalBlock block) {
				if (block.isContainedInRegion(langNodeRegion) 
						&& block.getCondition() != null) {
					theRegion[0] = block;
					return VISIT_ABORT;
				}
				return VISIT_CHILDREN;
			}
		});
		return theRegion[0] != null;
	}

	/**
	 * Reset all the source regions in the node and its children.
	 * Used when moving nodes between files, for instance
	 * @param node
	 */
	public static void resetSourceRegions(IASTNode node) {
		node.accept(new IASTVisitor() {

			public int visit(IASTNode node) {
				node.setSourceRegion(null);
				return VISIT_CHILDREN;
			}
			
		});
		
	}

	/**
	 * Duplicate the content of source regions, by making proxy documents to hold the existing
	 * content, if any.  Used when moving nodes between files, where an ordering dependency
	 * would otherwise change the content of a document referenced by another TU.
	 * @param node root of subtree that will be copied away
	 * @param targetPath the path of the target document 
	 */
	public static void copyAwaySourceRegionContents(IASTNode node, final IPath targetPath) {
		final Map<IPath, IDocument> proxyDocs = new HashMap<IPath, IDocument>();
		node.accept(new IASTVisitor() {

			public int visit(IASTNode node) {
				ISourceRegion region = node.getSourceRegion();
				if (region != null) {
					if (region instanceof IDocumentSourceRegion) {
						IDocumentSourceRegion docRegion = (IDocumentSourceRegion) region;
						if (!docRegion.getLocation().equals(targetPath)) {
							IDocument document = proxyDocs.get(targetPath);
							if (document == null) {
								document = DocumentFactory.createDocument(docRegion.getDocument().get());
								proxyDocs.put(targetPath, document);
							}
							node.setSourceRegion(ASTUtils.createDocumentSourceRegion(document, 
									docRegion.getLocation(), docRegion.getRegion()));
						}
					} else {
						// multi-document region... just bail
						EpocEnginePlugin.log(new Exception(""), //$NON-NLS-1$
								"Cannot preserve source when moving statements composed from multiple files to another file"); //$NON-NLS-1$
						node.setSourceRegion(null);
					}
				}
				return VISIT_CHILDREN;
			}
			
		});
		
	}

	public static Map<IPath, IDocument> getDocumentMap(IASTNode tu) {
		final Map<IPath, IDocument> documentMap = new HashMap<IPath, IDocument>();
		tu.getSourceRegion().accept(new ISourceRegionVisitor() {

			public int visit(IDocumentSourceRegion region) {
				if (!documentMap.containsKey(region.getLocation())) {
					documentMap.put(region.getLocation(), region.getDocument());
				} else {
					Check.checkState(documentMap.get(region.getLocation()) == region.getDocument());
				}
				return VISIT_SIBLINGS;
			}

			public int visit(IMultiDocumentSourceRegion region) {
				for (IDocumentSourceRegion docRegion : region.getDocumentSourceRegions()) {
					visit(docRegion);
				}
				return VISIT_SIBLINGS;
			}

			public int visit(ISourceRegion region) {
				return VISIT_CHILDREN;
			}
			
		});
		return documentMap;
	}


}
