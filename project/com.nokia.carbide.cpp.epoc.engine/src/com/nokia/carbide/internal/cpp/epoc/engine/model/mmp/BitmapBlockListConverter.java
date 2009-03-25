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

package com.nokia.carbide.internal.cpp.epoc.engine.model.mmp;

import com.nokia.carbide.cpp.epoc.engine.EpocEnginePlugin;
import com.nokia.carbide.cpp.epoc.engine.image.IBitmapSource;
import com.nokia.carbide.cpp.epoc.engine.image.IImageSource;
import com.nokia.carbide.cpp.epoc.engine.image.ImageFormat;
import com.nokia.carbide.cpp.epoc.engine.model.EGeneratedHeaderFlags;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.EMMPStatement;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPBitmap;
import com.nokia.carbide.cpp.epoc.engine.preprocessor.AllNodesViewFilter;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ASTFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.ASTMMPFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPBitmapSourceStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPSingleArgumentStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStartBlockStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.model.StructuredItemStatementListConverter;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 *	Convert a START BITMAP ... END block. 
 *
 */
class BitmapBlockListConverter implements StructuredItemStatementListConverter<IASTMMPStartBlockStatement, IMMPBitmap> {
	final static String BITMAP_KEYWORD = "BITMAP"; //$NON-NLS-1$
	
	private MMPView view;

	BitmapBlockListConverter(MMPView view) {
		this.view = view;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListConverter#elementMatches(java.lang.Object, java.lang.Object)
	 */
	public boolean elementMatches(IMMPBitmap element, IMMPBitmap another) {
		return element.getTargetFile().equalsIgnoreCase(another.getTargetFile());
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListConverter#allowEmptyStatements()
	 */
	public boolean allowEmptyStatements() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListConverter#fromNode(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode, com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListArgumentInfoIter)
	 */
	public IMMPBitmap fromNode(IASTMMPStartBlockStatement node) {
		if (!node.getBlockType().getValue().equalsIgnoreCase(BITMAP_KEYWORD))
			return null;
		
		if (node.getBlockArguments().size() < 1) {
			EpocEnginePlugin.log(new IllegalArgumentException("Skipping malformed START BITMAP block in " + node.getSourceReference() +": " + node.getNewText())); //$NON-NLS-1$ //$NON-NLS-2$
			return null;
		}
		
		IMMPBitmap bitmap = this.view.createMMPBitmap();
		bitmap.setHeaderFlags(EGeneratedHeaderFlags.NoHeader);
		bitmap.setTargetFile(node.getBlockArguments().get(0).getValue());
		
		IPath currentSourcePath = view.defaultProjectRelativeSourcePath();
		
		for (IASTMMPStatement stmt : node.getStatements()) {
			 if (EMMPStatement.TARGETPATH.matches(stmt)) {
				 bitmap.setTargetPath(FileUtils.createPossiblyRelativePath(((IASTMMPSingleArgumentStatement) stmt).getArgument().getValue()));
			}
			else if (EMMPStatement.HEADER.matches(stmt)) {
				bitmap.setHeaderFlags(EGeneratedHeaderFlags.Header);
			} 
			else if (EMMPStatement.HEADERONLY.matches(stmt)) {
				bitmap.setHeaderFlags(EGeneratedHeaderFlags.HeaderOnly);
			} 
			else if (EMMPStatement.SOURCEPATH.matches(stmt)) {
				currentSourcePath = view.fromMmpToProjectPath(((IASTMMPSingleArgumentStatement) stmt).getArgument());
			} 
			else if (EMMPStatement.BITMAP_SOURCE.matches(stmt)) {
				IASTMMPBitmapSourceStatement bm = (IASTMMPBitmapSourceStatement) stmt;
				ImageFormat format = null;
				try {
					format = new ImageFormat(bm.getFormat().getValue());
				} catch (IllegalArgumentException e) {
					String errmsg = e.getLocalizedMessage() + " in " + node.getNewText(); //$NON-NLS-1$ //$NON-NLS-2$
					EpocEnginePlugin.log(Logging.newStatus(EpocEnginePlugin.getDefault(), new IllegalArgumentException(errmsg)));
					format = new ImageFormat(true, 32, 32);
				}
				
				int skip = (format.getMaskDepth() != 0 ? 2 : 1);
				for (int i = 0; i < bm.getArguments().size(); i += skip) {
					IBitmapSource source = bitmap.createBitmapSource();
					source.setColor(format.isColor());
					source.setDepth(format.getDepth());
					source.setMaskDepth(format.getMaskDepth());

					IASTLiteralTextNode file = bm.getArguments().get(i);
					IPath path = MMPView.combinePaths(currentSourcePath, file.getValue());
					source.setPath(path);
					if (skip == 2) {
						try {
							file = bm.getArguments().get(i + 1);
							path = MMPView.combinePaths(currentSourcePath, file.getValue());
							source.setMaskPath(path);
						} catch (IndexOutOfBoundsException e) {
							source.setMaskDepth(0);
							EpocEnginePlugin.log(new IllegalArgumentException("Insufficient filenames supplied for bitmap list in " + bm.getSourceReference() + ": " + bm.getNewText())); //$NON-NLS-1$ //$NON-NLS-2$
						}
					}
					bitmap.getSources().add(source);
				}
			}
			else if (EMMPStatement.START_BLOCK.matches(stmt)
					// marked unknown, not an actual IASTMMPStartBlockStatement
					&& stmt.getNewText().toUpperCase().indexOf(BITMAP_KEYWORD) > 0
					&& view.getViewConfiguration().getViewFilter() instanceof AllNodesViewFilter) {
				// likely this #if construct under the "all" view, ignore:
				//
				//	#ifdef MACRO
				//	START BITMAP a.mbm
				//	#else
				//	START BITMAP b.mbm
				//	#endif
				//		...
				//	END
			}
			else {
				EpocEnginePlugin.log(new IllegalArgumentException("Ignoring unknown statement in START BITMAP in " + stmt.getSourceReference() + ": " + stmt.getNewText())); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		
		return bitmap;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListConverter#toNode(java.lang.Object, com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListArgumentInfoIter)
	 */
	public IASTMMPStartBlockStatement toNode(IMMPBitmap bitmap) {
		if (!bitmap.isValid()) {
			EpocEnginePlugin.log(new IllegalArgumentException("Invalid bitmap block info: " + bitmap)); //$NON-NLS-1$
			return null;
		}
		
		IASTListNode<IASTLiteralTextNode> blockArguments = ASTFactory.createPreprocessorLiteralTextNodeList(
				new String[] { bitmap.getTargetFile() });
		
		IASTListNode<IASTMMPStatement> statements = ASTMMPFactory.createMMPStatementListNode();
		
		if (bitmap.getTargetPath() != null) {
			String targetPath = view.pathString(bitmap.getTargetPath());
			if (targetPath.length() > 0) {
				statements.add(ASTMMPFactory.createMMPSingleArgumentStatement(
						EMMPStatement.TARGETPATH.toString(), targetPath));
			}
		}

		switch (bitmap.getHeaderFlags()) {
		case NoHeader:
			// nothing
			break;
		case Header:
			statements.add(ASTMMPFactory.createMMPFlagStatement(
					EMMPStatement.HEADER.toString()));
			break;
		case HeaderOnly:
			statements.add(ASTMMPFactory.createMMPFlagStatement(
					EMMPStatement.HEADERONLY.toString()));
			break;
		default:
			Check.checkState(false);
		}
		
		ImageFormat lastFormat = null;
		List<IPath> files = new ArrayList<IPath>();
		boolean isAbsolute = true;
		IPath lastSourcePath = null;
		CommonPathFinder pathFinder = new CommonPathFinder();
		pathFinder.addDirectory(view.defaultProjectRelativeSourcePath());
		
		ListIterator<IImageSource> sourceIter = bitmap.getSources().listIterator();
		
		// Scan all the images of the same image format and emit one
		// SOURCEPATH, if needed, plus one SOURCE, for all of them.
		// But, break if a source is ever absolute and group those together.
		
		while (sourceIter.hasNext()) {
			IImageSource imageSource = sourceIter.next();
			if (!(imageSource instanceof IBitmapSource))
				continue;
			IBitmapSource source = (IBitmapSource) imageSource;
			
			ImageFormat format = new ImageFormat(source.isColor(), source.getDepth(), source.getMaskDepth());
			
			if ((isAbsolute != 
				(source.getPath().isAbsolute() || (source.getMaskPath() != null && source.getMaskPath().isAbsolute())))
				|| (lastFormat == null || !format.equals(lastFormat))) 
			{
				// flush a line
				flushBitmapSource(statements, lastFormat, pathFinder, lastSourcePath, files);
				lastSourcePath = pathFinder.getCommonPath();
				pathFinder.reset(null);
				lastFormat = format;
				isAbsolute = source.getPath().isAbsolute() || (source.getMaskPath() != null && source.getMaskPath().isAbsolute());
				files.clear();
			}

			if (!isAbsolute) { 
				pathFinder.addFile(source.getPath());
			}
			files.add(source.getPath());
			
			if (format.getMaskDepth() != 0 && source.getMaskPath() != null) {
				if (!isAbsolute) {
					pathFinder.addFile(source.getMaskPath());
				}
				files.add(source.getMaskPath());
			}
		}
		
		// flush leftovers
		flushBitmapSource(statements, lastFormat, pathFinder, lastSourcePath, files);
		
		IASTMMPStartBlockStatement blockStmt = ASTMMPFactory.createMMPStartBlockStatement(
				ASTFactory.createPreprocessorLiteralTextNode(BITMAP_KEYWORD), //$NON-NLS-1$
				blockArguments,
				statements);
		
		return blockStmt;
	}


	/**
	 */
	private void flushBitmapSource(IASTListNode<IASTMMPStatement> statements, ImageFormat format, 
			CommonPathFinder pathFinder, IPath lastSourcePath, List<IPath> paths) {
		if (format == null || paths.size() == 0)
			return;
		
		IPath commonPath = pathFinder.getCommonPath();
		if (commonPath != null && !lastSourcePath.equals(commonPath)) {
			IASTMMPSingleArgumentStatement sourcePath = ASTMMPFactory.createMMPSingleArgumentStatement(
					EMMPStatement.SOURCEPATH.toString(), 
					view.pathString(view.fromProjectToMmpPath(commonPath)));
			statements.add(sourcePath);
		}
		IASTMMPBitmapSourceStatement source = ASTMMPFactory.createMMPBitmapSourceStatement(
				format.toString(), new String[0]);
		for (ListIterator<IPath> iterator = paths.listIterator(); iterator.hasNext(); ) {
			IPath sourceP = iterator.next();
			IPath relative = commonPath != null ? MMPView.fromProjectToRelativePath(
					pathFinder.getCommonPath(), sourceP) : sourceP;
			source.getArguments().add(ASTFactory.createPreprocessorLiteralTextNode(view.pathString(relative)));
		}
		statements.add(source);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.mmp.MMPView.StructuredItemListConverter#updateNode(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStatement, com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStatement)
	 */
	public void updateNode(IASTMMPStartBlockStatement blockStmt, IASTMMPStartBlockStatement updatedNode) {
		if (!blockStmt.getBlockArguments().equals(updatedNode.getBlockArguments())) {
			blockStmt.setBlockArguments((IASTListNode<IASTLiteralTextNode>) updatedNode.getBlockArguments().copy());
		}
		if (!blockStmt.getStatements().equals(updatedNode.getStatements())) {
			// merge the statements
			view.mergeStatementList(blockStmt.getStatements(), updatedNode.getStatements());
		}
		
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.mmp.StatementListConverter#canAddToStatement(java.lang.Object)
	 */
	public boolean canAddToStatement(IMMPBitmap model) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.mmp.StatementListConverter#createContextStatement(java.lang.Object)
	 */
	public IASTMMPStatement createContextStatement(IMMPBitmap model) {
		return null;
	}

	public boolean changeRequiresNewContext(IMMPBitmap existing, IMMPBitmap newElement) {
		return false;
	}
	
	public Pair<IASTNode, IASTNode> getInsertAnchors() {
		return null;
	}
	
	public void associateContextStatement(IASTStatement stmt,
			IASTStatement contextStmt) {
		
	}
}