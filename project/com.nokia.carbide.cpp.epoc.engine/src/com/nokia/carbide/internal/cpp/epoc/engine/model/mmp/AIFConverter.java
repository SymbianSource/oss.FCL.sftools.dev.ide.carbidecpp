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
import com.nokia.carbide.cpp.epoc.engine.image.IBitmapSourceReference;
import com.nokia.carbide.cpp.epoc.engine.image.ImageFormat;
import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPAIFInfo;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTListNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTLiteralTextNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.ASTMMPFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPAifStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStatement;
import com.nokia.carbide.internal.cpp.epoc.engine.model.StructuredItemStatementListConverter;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;

import java.util.ArrayList;
import java.util.List;

public class AIFConverter implements StructuredItemStatementListConverter<IASTMMPAifStatement, IMMPAIFInfo> {

	private MMPView view;

	public AIFConverter(MMPView view) {
		this.view = view;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListConverter#allowEmptyStatements()
	 */
	public boolean allowEmptyStatements() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListConverter#elementMatches(java.lang.Object, java.lang.Object)
	 */
	public boolean elementMatches(IMMPAIFInfo element, IMMPAIFInfo another) {
		return element.getTarget().equals(another.getTarget());
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.mmp.MMPView.StructuredItemListConverter#updateNode(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStatement, com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.IASTMMPStatement)
	 */
	public void updateNode(IASTMMPAifStatement existing, IASTMMPAifStatement newElement) {
		if (!existing.getSourcePath().equalValue(newElement.getSourcePath())) {
			existing.setSourcePath((IASTLiteralTextNode) newElement.getSourcePath().copy());
		}
		if (!existing.getResource().equalValue(newElement.getResource())) {
			existing.setResource((IASTLiteralTextNode) newElement.getResource().copy());
		}
		if (!existing.getTargetFile().equalValue(newElement.getTargetFile())) {
			existing.setTargetFile((IASTLiteralTextNode) newElement.getTargetFile().copy());
		}
		if (existing.getColorDepth() == null) {
			if (newElement.getColorDepth() != null) {
				existing.setColorDepth((IASTLiteralTextNode) newElement.getColorDepth().copy());
			}
		} else {
			if (newElement.getColorDepth() == null) {
				existing.setColorDepth(null);
			} else if (!existing.getColorDepth().equalValue(newElement.getColorDepth())){
				existing.setColorDepth((IASTLiteralTextNode) newElement.getColorDepth().copy());
			}
		}
		if (!existing.getArguments().equalValue(newElement.getArguments())) {
			existing.setArguments((IASTListNode<IASTLiteralTextNode>) newElement.getArguments().copy());
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListConverter#fromNode(com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode)
	 */
	public IMMPAIFInfo fromNode(IASTMMPAifStatement node) {
		IMMPAIFInfo info = view.createMMPAIFInfo();
		info.setTarget(FileUtils.createPossiblyRelativePath(node.getTargetFile().getValue()));
		
		IPath sourcePath = view.fromMmpToProjectPath(node.getSourcePath());
		String sourceFile = node.getResource().getValue();
		info.setResource(sourcePath.append(sourceFile));
		
		if (node.getColorDepth() != null) {
			ImageFormat format = null;
			try {
				format = new ImageFormat(node.getColorDepth().getValue());
			} catch (IllegalArgumentException e) {
				EpocEnginePlugin.log(e);
				// TODO: this maybe should just return null and fail
			}
			if (format != null) {
				info.setColor(format.isColor());
				info.setColorDepth(format.getDepth());
				
				info.setMaskDepth(format.getMaskDepth());
				
				// no unpaired images in AIF
				//// either consume 1 or 2 filenames at a time
				//int step = format.getMaskDepth() > 0 ? 2 : 1;
				int step = 2;
				for (int idx = 0; idx < node.getArguments().size(); idx += step) {
					IASTLiteralTextNode file = node.getArguments().get(idx);
					
					IPath filePath = sourcePath.append(file.getValue());
					IBitmapSourceReference ref = info.createBitmapSourceReference();
					ref.setPath(filePath);
					
					if (step > 1 && idx + 1 < node.getArguments().size()) {
						IASTLiteralTextNode maskFile = node.getArguments().get(idx + 1);
						IPath maskPath = sourcePath.append(maskFile.getValue());
						ref.setMaskPath(maskPath);
					}
					
					info.getSourceBitmaps().add(ref);
				}
			}
		}
		
		Check.checkState(info.isValid());
		return info;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase.ListConverter#toNode(java.lang.Object)
	 */
	public IASTMMPAifStatement toNode(IMMPAIFInfo info) {
		if (!info.isValid()) {
			EpocEnginePlugin.log(new IllegalArgumentException("invalid AIF info: " + info)); //$NON-NLS-1$
			return null;
		}
		
		String targetFile = view.pathString(info.getTarget());
		
		CommonPathFinder finder = new CommonPathFinder();
		finder.addFile(info.getResource());
		for (IBitmapSourceReference ref : info.getSourceBitmaps()) {
			finder.addFile(ref.getPath());
			finder.addFile(ref.getMaskPath());
		}
		IPath sourcePath = finder.getCommonPath();
		
		String resource = view.pathString(finder.makeRelativePath(info.getResource()));
		
		String colorFormat = null;
		List<String> bitmaps = new ArrayList<String>();
		if (info.getColorDepth() != 0) {
			ImageFormat format = new ImageFormat(info.isColor(), info.getColorDepth(), info.getMaskDepth());
			colorFormat = format.toString();
			
			boolean hasMasks = info.getMaskDepth() != 0;
			bitmaps = new ArrayList<String>();
			for (IBitmapSourceReference ref : info.getSourceBitmaps()) {
				if (!ref.isValid() || (hasMasks && ref.getMaskPath() == null)) {
					EpocEnginePlugin.log(new IllegalArgumentException("invalid AIF bitmap info: " + ref)); //$NON-NLS-1$
					continue;
				}
				IPath path = finder.makeRelativePath(ref.getPath());
				bitmaps.add(view.pathString(path));
				if (hasMasks) {
					path = finder.makeRelativePath(ref.getMaskPath());
					bitmaps.add(view.pathString(path));
				}
			}
		}
		
		IASTMMPAifStatement stmt = ASTMMPFactory.createMMPAifStatement(
				targetFile, 
				view.pathString(view.fromProjectToMmpPath(sourcePath)), 
				resource, colorFormat, bitmaps);
		return stmt;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.mmp.StatementListConverter#canAddToStatement(java.lang.Object)
	 */
	public boolean canAddToStatement(IMMPAIFInfo model) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.mmp.StatementListConverter#createContextStatement(java.lang.Object)
	 */
	public IASTMMPStatement createContextStatement(IMMPAIFInfo model) {
		return null;
	}

	public boolean changeRequiresNewContext(IMMPAIFInfo existing, IMMPAIFInfo newElement) {
		return false;
	}
	
	public Pair<IASTNode, IASTNode> getInsertAnchors() {
		return null;
	}
	
	public void associateContextStatement(IASTStatement stmt,
			IASTStatement contextStmt) {
		
	}
}
