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
	
	/** The name of the variant that this configuration extends  */
	private static final String EXTENDS = "EXTENDS"; //$NON-NLS-1$
	/** The name of the variant  */
	private static final String VARIANT = "VARIANT"; //$NON-NLS-1$
	/** Defines the variant as a virtual variant, which means that this variant configuration is not compiled.  */
	private static final String VIRTUAL = "VIRTUAL"; //$NON-NLS-1$
	/** 	 The global variant hrh file, which is include to the building and image creation. If this parameter is not existing the system tries to include a <variantname>.hrh  */
	private static final String VARIANT_HRH = "VARIANT_HRH"; //$NON-NLS-1$
	/** set,prepend or append include paths to the global list of build time system includes (Used during abld command).  */
	private static final String BUILD_INCLUDE = "BUILD_INCLUDE"; //$NON-NLS-1$
	/** set,prepend or append include paths to the global list of rom build time system includes (Used during buildrom.pl command).  */
	private static final String ROM_INCLUDE = "ROM_INCLUDE"; //$NON-NLS-1$
	
	private IASTSBVTranslationUnit tu;
	private String variantName;
	private boolean sawHeaderComment;
	private boolean sawExtends;
	private boolean sawBuildHRH;
	private String extendsVariantStr;
	private String varintHRHStr;
	private boolean isVirtual;
	
	/** Path, flag */
	private Map<String, String> buildIncludePaths = new HashMap<String, String>();
	
	/** ROM build includes */
	private Map<String, String> romBuildIncludePaths = new HashMap<String, String>();;
	
	/**
	 * @param model
	 * @param parser
	 * @param viewConfiguration
	 */
	public SBVView(ModelBase model, IViewConfiguration viewConfiguration) {
		super(model, null, viewConfiguration);
		tu = null;
	}
	
	private void refresh() {
		extendsVariantStr = ""; //$NON-NLS-1$
		
		IDocumentParser sbvParser = ParserFactory.createSBVParser();
		tu = (IASTSBVTranslationUnit) sbvParser.parse(getModel().getPath(), getModel().getDocument());
		
		sawHeaderComment = false;
		sawExtends = false;
		sawBuildHRH = false;
		
		for (IASTTopLevelNode stmt : tu.getNodes()) {
			if (stmt instanceof IASTSBVFlagStatement) {
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
		if (!sawExtends && option.equals(EXTENDS)) {
			setExtends(value);
			sawExtends = true;
		} 
		
		else if (!sawBuildHRH && option.equals(VARIANT_HRH)) {
			setBuildHRHFile(value);
			sawBuildHRH = true;
		} 
		
		else if (option.equals(VARIANT)) {
			setVariantName(value);
		}
		
		else if (option.equals(BUILD_INCLUDE)){
			addBuildInclude(value);
		}
		
		else if (option.equals(ROM_INCLUDE)){
			addROMInclude(value);
		}
		
	}

	/**
	 * Handle a flag statement
	 * @param flag
	 */
	private void handleStatement(String flag) {
		if (flag.equals(VIRTUAL)) {
			setVirtualFlag(true);
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
		if (!sawExtends) {
			messageList.add(ASTFactory.createErrorMessage("SBVView.NoCustomizesStatement",
					new Object[0],
					new MessageLocation(fullPath)));
		}
	}


	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.sbv.ISBVView#getCustomizes()
	 */
	public String getExtends() {
		return extendsVariantStr;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.sbv.ISBVView#setCustomizes(java.lang.String)
	 */
	public void setExtends(String platform) {
		this.extendsVariantStr = platform;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.IView#getData()
	 */
	public IData getData() {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.sbv.ISBVView#setVirtualFlag(boolean)
	 */
	public void setVirtualFlag(boolean flag) {
		this.isVirtual = flag;
	}

	public boolean getVirtualFlag() {
		return isVirtual;
	}

	public String getBuildVariantHRH() {
		return varintHRHStr;
	}

	public void setBuildHRHFile(String pathStr) {
		varintHRHStr = pathStr;
	}
	
	public void setVariantName(String variantName){
		this.variantName = variantName;
	}
	
	public String getVariantName(){
		return variantName;
	}

	public void addBuildInclude(String arguments) {
		String[] args = arguments.split("\\s+");
		if (args.length == 2){
			buildIncludePaths.put(args[1], args[0]);
		}
		
	}

	public void addROMInclude(String arguments) {
		String[] args = arguments.split("\\s+");
		if (args.length == 2){
			romBuildIncludePaths.put(args[1], args[0]);
		}
		
	}

	public Map<String, String> getBuildIncludes() {
		return buildIncludePaths;
	}

	public Map<String, String> getROMBuildIncludes() {
		return romBuildIncludePaths;
	}
	
	
	
}
