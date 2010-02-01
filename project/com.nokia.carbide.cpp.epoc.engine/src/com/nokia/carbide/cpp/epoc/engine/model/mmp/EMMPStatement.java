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

package com.nokia.carbide.cpp.epoc.engine.model.mmp;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTNode;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.mmp.*;
import com.nokia.carbide.internal.cpp.epoc.engine.parser.mmp.IMMPParserConfiguration;


/**
 * Enumerations for every known MMP statement.<p>
 * An enumerator contains its keyword, the type of statement, 
 * and optionally the block type in which it is recognized.
 * <p>
 * Note that most MMP statements have a regular syntax and are generically
 * categorized as FLAG_STATEMENT, SINGLE_ARGUMENT_STATEMENT, LIST_ARGUMENT_STATEMENT.
 * Only special cases have their own categories.
 *
 */
public enum EMMPStatement {
	// special cases first
	AIF("AIF", IMMPParserConfiguration.AIF_STATEMENT), //$NON-NLS-1$
	BITMAP_SOURCE("SOURCE", IMMPParserConfiguration.BITMAP_SOURCE_STATEMENT, "BITMAP"), //$NON-NLS-1$ //$NON-NLS-2$
	OPTION("OPTION", IMMPParserConfiguration.OPTION_STATEMENT), //$NON-NLS-1$
	LINKEROPTION("LINKEROPTION", IMMPParserConfiguration.OPTION_STATEMENT), //$NON-NLS-1$
	OPTION_REPLACE("OPTION_REPLACE", IMMPParserConfiguration.OPTION_STATEMENT), //$NON-NLS-1$
	START_BLOCK("START", IMMPParserConfiguration.START_BLOCK_STATEMENT), //$NON-NLS-1$
	UID("UID", IMMPParserConfiguration.UID_STATEMENT), //$NON-NLS-1$
	
	// flag statements
	ALWAYS_BUILD_AS_ARM("ALWAYS_BUILD_AS_ARM", IMMPParserConfiguration.FLAG_STATEMENT), //$NON-NLS-1$
	ASSPABI("ASSPABI", IMMPParserConfiguration.FLAG_STATEMENT), //$NON-NLS-1$
	ASSPEXPORTS("ASSPEXPORTS", IMMPParserConfiguration.FLAG_STATEMENT), //$NON-NLS-1$
	COMPRESSTARGET("COMPRESSTARGET", IMMPParserConfiguration.FLAG_STATEMENT), //$NON-NLS-1$
	COPY_FOR_STATIC_LINKAGE("COPY_FOR_STATIC_LINKAGE", IMMPParserConfiguration.FLAG_STATEMENT, "PLATFORM"), //$NON-NLS-1$ //$NON-NLS-2$
	EXPORTUNFROZEN("EXPORTUNFROZEN", IMMPParserConfiguration.FLAG_STATEMENT), //$NON-NLS-1$
	NOCOMPRESSTARGET("NOCOMPRESSTARGET", IMMPParserConfiguration.FLAG_STATEMENT), //$NON-NLS-1$
	NOEXPORTLIBRARY("NOEXPORTLIBRARY", IMMPParserConfiguration.FLAG_STATEMENT), //$NON-NLS-1$
	NOSTRICTDEF("NOSTRICTDEF", IMMPParserConfiguration.FLAG_STATEMENT), //$NON-NLS-1$
	SRCDBG("SRCDBG", IMMPParserConfiguration.FLAG_STATEMENT), //$NON-NLS-1$
	STRICTDEPEND("STRICTDEPEND", IMMPParserConfiguration.FLAG_STATEMENT), //$NON-NLS-1$
	EPOCCALLDLLENTRYPOINTS("EPOCCALLDLLENTRYPOINTS", IMMPParserConfiguration.FLAG_STATEMENT), //$NON-NLS-1$
	EPOCFIXEDPROCESS("EPOCFIXEDPROCESS", IMMPParserConfiguration.FLAG_STATEMENT), //$NON-NLS-1$
	EPOCALLOWDLLDATA("EPOCALLOWDLLDATA", IMMPParserConfiguration.FLAG_STATEMENT), //$NON-NLS-1$
	PAGED("PAGED", IMMPParserConfiguration.FLAG_STATEMENT), //$NON-NLS-1$
	UNPAGED("UNPAGED", IMMPParserConfiguration.FLAG_STATEMENT), //$NON-NLS-1$
	WIN32_HEADERS("WIN32_HEADERS", IMMPParserConfiguration.FLAG_STATEMENT), //$NON-NLS-1$
	ARMRT("ARMRT", IMMPParserConfiguration.FLAG_STATEMENT), //$NON-NLS-1$
	WCHARENTRYPOINT("WCHARENTRYPOINT", IMMPParserConfiguration.FLAG_STATEMENT), //$NON-NLS-1$
	INFLATECOMPRESSTARGET("INFLATECOMPRESSTARGET", IMMPParserConfiguration.FLAG_STATEMENT), //$NON-NLS-1$
	BYTEPAIRCOMPRESSTARGET("BYTEPAIRCOMPRESSTARGET", IMMPParserConfiguration.FLAG_STATEMENT), //$NON-NLS-1$
	
	DEBUGGABLE("DEBUGGABLE", IMMPParserConfiguration.FLAG_STATEMENT), //$NON-NLS-1$
	/**
	 * @since 2.0
	 */
	DEBUGGABLE_UDEBONLY("DEBUGGABLE_UDEBONLY", IMMPParserConfiguration.FLAG_STATEMENT), //$NON-NLS-1$
	
	/**
	 * @since 2.1
	 */
	FEATUREVARIANT("FEATUREVARIANT", IMMPParserConfiguration.FLAG_STATEMENT), //$NON-NLS-1$
	
	/**
	 * @since 2.5 - Added with Symbian^3
	 */
	STDCPP("STDCPP", IMMPParserConfiguration.FLAG_STATEMENT), //$NON-NLS-1$
	NOSTDCPP("NOSTDCPP", IMMPParserConfiguration.FLAG_STATEMENT), //$NON-NLS-1$
	
	HEADER("HEADER", IMMPParserConfiguration.FLAG_STATEMENT, "RESOURCE|BITMAP"), //$NON-NLS-1$ //$NON-NLS-2$
	HEADERONLY("HEADERONLY", IMMPParserConfiguration.FLAG_STATEMENT, "RESOURCE|BITMAP"), //$NON-NLS-1$ //$NON-NLS-2$
	
	// single-argument statements
	ARMFPU("ARMFPU", IMMPParserConfiguration.SINGLE_ARGUMENT_STATEMENT), //$NON-NLS-1$
	DEFFILE("DEFFILE", IMMPParserConfiguration.SINGLE_ARGUMENT_STATEMENT), //$NON-NLS-1$
	EXPORTLIBRARY("EXPORTLIBRARY", IMMPParserConfiguration.SINGLE_ARGUMENT_STATEMENT), //$NON-NLS-1$
	EPOCPROCESSPRIORITY("EPOCPROCESSPRIORITY", IMMPParserConfiguration.SINGLE_ARGUMENT_STATEMENT), //$NON-NLS-1$
	LINKAS("LINKAS", IMMPParserConfiguration.SINGLE_ARGUMENT_STATEMENT), //$NON-NLS-1$
	SOURCEPATH("SOURCEPATH", IMMPParserConfiguration.SINGLE_ARGUMENT_STATEMENT), //$NON-NLS-1$
	TARGETTYPE("TARGETTYPE", IMMPParserConfiguration.SINGLE_ARGUMENT_STATEMENT), //$NON-NLS-1$
	TARGETPATH("TARGETPATH", IMMPParserConfiguration.SINGLE_ARGUMENT_STATEMENT), //$NON-NLS-1$
	TARGET("TARGET", IMMPParserConfiguration.SINGLE_ARGUMENT_STATEMENT), //$NON-NLS-1$
	BASEADDRESS("BASEADDRESS", IMMPParserConfiguration.SINGLE_ARGUMENT_STATEMENT), //$NON-NLS-1$
	EPOCSTACKSIZE("EPOCSTACKSIZE", IMMPParserConfiguration.SINGLE_ARGUMENT_STATEMENT), //$NON-NLS-1$
	SECUREID("SECUREID", IMMPParserConfiguration.SINGLE_ARGUMENT_STATEMENT), //$NON-NLS-1$
	VENDORID("VENDORID", IMMPParserConfiguration.SINGLE_ARGUMENT_STATEMENT), //$NON-NLS-1$
	VID("VID", IMMPParserConfiguration.SINGLE_ARGUMENT_STATEMENT), //$NON-NLS-1$
	EPOCDATALINKADDRESS("EPOCDATALINKADDRESS", IMMPParserConfiguration.SINGLE_ARGUMENT_STATEMENT), //$NON-NLS-1$
	FIRSTLIB("FIRSTLIB", IMMPParserConfiguration.SINGLE_ARGUMENT_STATEMENT), //$NON-NLS-1$
	VAR("VAR", IMMPParserConfiguration.SINGLE_ARGUMENT_STATEMENT), //$NON-NLS-1$
	DEPENDS("DEPENDS", IMMPParserConfiguration.SINGLE_ARGUMENT_STATEMENT), //$NON-NLS-1$
	
	// list-argument statements
	
	// NOTE: order matters
	EPOCHEAPSIZE("EPOCHEAPSIZE", IMMPParserConfiguration.LIST_ARGUMENT_STATEMENT), //$NON-NLS-1$
	
	CAPABILITY("CAPABILITY", IMMPParserConfiguration.LIST_ARGUMENT_STATEMENT), //$NON-NLS-1$
	LIBRARY("LIBRARY", IMMPParserConfiguration.LIST_ARGUMENT_STATEMENT), //$NON-NLS-1$
	DEBUGLIBRARY("DEBUGLIBRARY", IMMPParserConfiguration.LIST_ARGUMENT_STATEMENT), //$NON-NLS-1$
	STATICLIBRARY("STATICLIBRARY", IMMPParserConfiguration.LIST_ARGUMENT_STATEMENT), //$NON-NLS-1$
	WIN32_LIBRARY("WIN32_LIBRARY", IMMPParserConfiguration.LIST_ARGUMENT_STATEMENT), //$NON-NLS-1$
	ASSPLIBRARY("ASSPLIBRARY", IMMPParserConfiguration.LIST_ARGUMENT_STATEMENT), //$NON-NLS-1$

	LANG("LANG", IMMPParserConfiguration.LIST_ARGUMENT_STATEMENT), //$NON-NLS-1$
	MACRO("MACRO", IMMPParserConfiguration.LIST_ARGUMENT_STATEMENT), //$NON-NLS-1$
	SOURCE("SOURCE", IMMPParserConfiguration.LIST_ARGUMENT_STATEMENT), //$NON-NLS-1$
	USERINCLUDE("USERINCLUDE", IMMPParserConfiguration.LIST_ARGUMENT_STATEMENT), //$NON-NLS-1$
	SYSTEMINCLUDE("SYSTEMINCLUDE", IMMPParserConfiguration.LIST_ARGUMENT_STATEMENT), //$NON-NLS-1$
	RESOURCE("RESOURCE", IMMPParserConfiguration.LIST_ARGUMENT_STATEMENT), //$NON-NLS-1$
	SYSTEMRESOURCE("SYSTEMRESOURCE", IMMPParserConfiguration.LIST_ARGUMENT_STATEMENT), //$NON-NLS-1$
	DOCUMENT("DOCUMENT", IMMPParserConfiguration.LIST_ARGUMENT_STATEMENT), //$NON-NLS-1$

	RAMTARGET("RAMTARGET", IMMPParserConfiguration.LIST_ARGUMENT_STATEMENT), //$NON-NLS-1$
	ROMTARGET("ROMTARGET", IMMPParserConfiguration.LIST_ARGUMENT_STATEMENT), //$NON-NLS-1$
	ARMLIBS("ARMLIBS", IMMPParserConfiguration.LIST_ARGUMENT_STATEMENT), //$NON-NLS-1$
	
	MESSAGE("MESSAGE", IMMPParserConfiguration.LIST_ARGUMENT_STATEMENT), //$NON-NLS-1$
	
	// this takes a dotted argument and optionally an "explicit" keyword
	VERSION("VERSION", IMMPParserConfiguration.LIST_ARGUMENT_STATEMENT), //$NON-NLS-1$

	;
	
	private String keyword;
	private int category;
	private String blockPattern;

	/** A statement which appears only in certain blocks.
	 * 
	 * @param keyword token to match
	 * @param category category of statement
	 * @param blockPattern the block pattern.  This is read as a sequence of
	 * space-separated fields; the first of which matches the START ... token. 
	 * The optional second argument 
	 */
	private EMMPStatement(String keyword, int category, String blockPattern) {
		this.keyword = keyword;
		this.category = category;
		this.blockPattern = blockPattern;
	}

	private EMMPStatement(String keyword, int category) {
		this.keyword = keyword;
		this.category = category;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return keyword;
	}
	
	/**
	 * Tell if the given statement is an instance of this statement type.
	 * @param stmt
	 * @return
	 */
	public boolean matches(IASTMMPStatement stmt) {
		if (stmt == null)
			return false;
		
		if (stmt.getKeywordName() == null || !stmt.getKeywordName().toUpperCase().equals(keyword))
			return false;
			
		if (blockPattern == null) 
			return true;
		
		// make sure block matches
		IASTNode parent = stmt;
		while ((parent = parent.getParent()) != null) {
			if (parent instanceof IASTMMPStartBlockStatement) {
				String stmtBlockType = ((IASTMMPStartBlockStatement) parent).
					getBlockType().getValue().toUpperCase();
				return stmtBlockType.matches(blockPattern);
			}
		}
		return false;
	}

	/**
	 * Get the category
	 * @return one of IMMPParserConfiguration# elements
	 */
	public int getCategory() {
		return category;
	}
}
