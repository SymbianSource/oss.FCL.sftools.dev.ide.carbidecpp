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

package com.nokia.carbide.internal.cpp.epoc.engine.dom;

import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorDefineStatement;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IASTPreprocessorTokenStream;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IMacroTokenLocation;
import com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ITokenLocation;


public class MacroTokenLocation implements IMacroTokenLocation {
	private IASTPreprocessorDefineStatement define;
	private int parentOffset;
	private ITokenLocation parentLocation;
	private int parentLength;

	public MacroTokenLocation(IASTPreprocessorDefineStatement define,
			int parentOffset, int parentLength, ITokenLocation parentLocation) {
		this.define = define;
		this.parentOffset = parentOffset;
		this.parentLength = parentLength;
		this.parentLocation = parentLocation;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IMacroTokenLocation))
			return false;
		IMacroTokenLocation location = (IMacroTokenLocation) obj;
		return location.getDefineStatement().equals(define) 
			&& location.getParentLocation().equals(parentLocation)
			&& location.getParentOffset() == parentOffset;
	}

	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IMacroTokenLocation#getDefineStatement()
	 */
	public IASTPreprocessorDefineStatement getDefineStatement() {
		return define;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IMacroTokenLocation#getParentOffset()
	 */
	public int getParentOffset() {
		return parentOffset;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ITokenLocation#getParentLength()
	 */
	public int getParentLength() {
		return parentLength;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.IMacroTokenLocation#getParentLocation()
	 */
	public ITokenLocation getParentLocation() {
		return parentLocation;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.cpp.epoc.engine.dom.ITokenLocation#get(int, int)
	 */
	public String get(int offset, int length) {
		IASTPreprocessorTokenStream macroExpansion = define.getMacroExpansion();
		if (macroExpansion == null)
			throw new IllegalArgumentException();
		return macroExpansion.getNewText().substring(offset, offset + length);
	}

}
