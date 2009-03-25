/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies). 
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
package com.nokia.cdt.debug.cw.symbian.internal.symbolreader;

import java.math.BigInteger;

import com.nokia.cdt.debug.cw.symbian.symbolreader.IFunction;

import cwdbg.SymFunction;

public class Function implements IFunction {

	private int id;
	private String name;
	private BigInteger lowBounds;
	private BigInteger highBounds;

	public Function(SymFunction symFunction) {
		this.id = symFunction.id;
		this.name = symFunction.name;
		this.lowBounds = BigInteger.valueOf(symFunction.lowAddr.addr);
		this.highBounds = BigInteger.valueOf(symFunction.highAddr.addr);
	}

	public Function(int id, String name, BigInteger lowBounds, BigInteger highBounds) {
		this.id = id;
		this.name = name;
		this.lowBounds = lowBounds;
		this.highBounds = highBounds;
	}

	/* (non-Javadoc)
	 * @see com.nokia.cdt.debug.cw.symbian.symbolreader.IFunction#getName()
	 */
	public String getName() {
		return name;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.cdt.debug.cw.symbian.symbolreader.IFunction#getLowBounds()
	 */
	public BigInteger getLowBounds(){
		return lowBounds;	
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.cdt.debug.cw.symbian.symbolreader.IFunction#getHighBounds()
	 */
	public BigInteger getHighBounds(){
		return highBounds;	
	}

	/* (non-Javadoc)
	 * @see com.nokia.cdt.debug.cw.symbian.symbolreader.IFunction#getId()
	 */
	public int getId() {
		return id;
	}

}
