/*
* Copyright (c) 2007-2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.carbide.internal.cpp.epoc.engine.model.sbv;

import java.util.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.IDocument;

import com.nokia.carbide.cpp.epoc.engine.model.*;
import com.nokia.carbide.cpp.epoc.engine.model.sbv.ISBVOwnedModel;
import com.nokia.carbide.cpp.epoc.engine.model.sbv.ISBVView;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ASTFactory;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTTopLevelNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.sbv.*;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ModelBase;
import com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.IDocumentParser;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.ParserFactory;
import com.nokia.cpp.internal.api.utils.core.*;


public class SBVView extends ViewBase<ISBVOwnedModel> implements ISBVView {
	
	private static final String HEADER = "#<sbv>#"; //$NON-NLS-1$
	private static final String COMPILEWITHPARENT = "COMPILEWITHPARENT"; //$NON-NLS-1$
	private static final String COMPILEALONE = "COMPILEALONE"; //$NON-NLS-1$
	private static final String CUSTOMIZES = "CUSTOMIZES"; //$NON-NLS-1$
	private static final String VARIANT = "VARIANT"; //$NON-NLS-1$
	private static final String VIRTUALVARIANT = "VIRTUALVARIANT"; //$NON-NLS-1$
	
	private IASTSBVTranslationUnit tu;
	private boolean sawHeaderComment;
	private boolean sawCustomizes;
	
	private ETristateFlag compileWithParent;
	private Map<String, String> customizationOptions;
	private String customizes;
	
	/**
	 * @param model
	 * @param parser
	 * @param viewConfiguration
	 */
	public SBVView(ModelBase model, IViewConfiguration viewConfiguration) {
		super(model, null, viewConfiguration);
		tu = null;
		customizationOptions = new HashMap<String, String>();
	}
	
	private void refresh() {
		compileWithParent = ETristateFlag.UNSPECIFIED;
		customizationOptions.clear();
		customizes = ""; //$NON-NLS-1$
		
		IDocumentParser sbvParser = ParserFactory.createSBVParser();
		tu = (IASTSBVTranslationUnit) sbvParser.parse(getModel().getPath(), getModel().getDocument());
		
		sawHeaderComment = false;
		sawCustomizes = false;
		
		for (IASTTopLevelNode stmt : tu.getNodes()) {
			if (stmt instanceof IASTSBVCommentStatement) { 
				if (((IASTSBVCommentStatement) stmt).getNewText().equals(HEADER)) {
					sawHeaderComment = true;
				}
			} else if (stmt instanceof IASTSBVFlagStatement) {
				String flag = ((IASTSBVFlagStatement) stmt).getKeywordName();
				handleStatement(flag);
			} else if (stmt instanceof IASTSBVArgumentStatement) {
				String option = ((IASTSBVArgumentStatement) stmt).getKeywordName();
				String value = ((IASTSBVArgumentStatement) stmt).getArgument().getValue();
				handleStatement(option, value);
			} else { 
				Check.checkState(false);
			}
		}
	}
	
	/**
	 * Handle a statement with an option and a value.
	 * @param option
	 * @param value
	 */
	private void handleStatement(String option, String value) {
		if (!sawCustomizes && option.equals(CUSTOMIZES)) {
			setCustomizes(value);
			sawCustomizes = true;
		} else {
			getCustomizationOptions().put(option, value);
		}
	}

	/**
	 * Handle a flag statement
	 * @param flag
	 */
	private void handleStatement(String flag) {
		if (flag.equals(COMPILEWITHPARENT)) {
			setCompileWithParent(ETristateFlag.ENABLED);
		} else if (flag.equals(COMPILEALONE)) {
			setCompileWithParent(ETristateFlag.DISABLED);
		} else {
			getCustomizationOptions().put(flag, null);
		}
	}

	@Override
	public IPath[] getReferencedFiles() {
		return new IPath[] { model.getPath() };
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase#internalReparse()
	 */
	@Override
	protected Map<IPath, IDocument> internalReparse(Map<IPath, IDocument> overrideDocumentMap) {
		// empty: nothing new provided yet
		Map<IPath, IDocument> documentMap = new HashMap<IPath, IDocument>();
		refresh();
		return documentMap;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase#internalRevertChanges()
	 */
	@Override
	protected void internalRevertChanges() {
		refresh();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase#internalHasChanges()
	 */
	@Override
	protected boolean internalHasChanges() {
		return false;
	}

	/**
	 */
	protected void internalCommit() {
		// no changes supported
	}
	
	@Override
	public boolean merge() {
		return true;
	}

	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.ViewBase#addViewSpecificMessages(java.util.List)
	 */
	@Override
	protected void addViewSpecificMessages(List<IMessage> messageList) {
		IPath fullPath = getModel().getPath();
		if (!sawHeaderComment) {
			messageList.add(ASTFactory.createErrorMessage("SBVView.InvalidSBVHeader",
					new Object[0],
					new MessageLocation(fullPath)));
		}
		if (!sawCustomizes) {
			messageList.add(ASTFactory.createErrorMessage("SBVView.NoCustomizesStatement",
					new Object[0],
					new MessageLocation(fullPath)));
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.sbv.ISBVView#getCompileWithParent()
	 */
	public ETristateFlag getCompileWithParent() {
		return compileWithParent;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.sbv.ISBVView#getCustomizationOptions()
	 */
	public Map<String, String> getCustomizationOptions() {
		return customizationOptions;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.sbv.ISBVView#getCustomizes()
	 */
	public String getCustomizes() {
		return customizes;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.sbv.ISBVView#getName()
	 */
	public String getName() {
		return getModel().getPath().removeFileExtension().lastSegment();
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.sbv.ISBVView#setCompileWithParent(com.nokia.carbide.cpp.epoc.engine.model.ETristateFlag)
	 */
	public void setCompileWithParent(ETristateFlag flag) {
		Check.checkArg(flag);
		this.compileWithParent = flag;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.sbv.ISBVView#setCustomizationOptions(java.util.Map)
	 */
	public void setCustomizationOptions(Map<String, String> map) {
		Check.checkArg(map);
		this.customizationOptions = map;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.sbv.ISBVView#setCustomizes(java.lang.String)
	 */
	public void setCustomizes(String platform) {
		this.customizes = platform;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IView#getData()
	 */
	public IData getData() {
		return null;
	}
}
