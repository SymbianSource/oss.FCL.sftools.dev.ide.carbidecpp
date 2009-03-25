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
import java.util.ArrayList;

import org.omg.CORBA.IntHolder;
import org.omg.CORBA.StringHolder;

import com.nokia.cdt.debug.cw.symbian.symbolreader.IFunction;
import com.nokia.cdt.debug.cw.symbian.symbolreader.ISourceLocation;
import com.nokia.cdt.debug.cw.symbian.symbolreader.ISymbolFile;

import cwdbg.Address;
import cwdbg.AddressHolder;
import cwdbg.SymFunction;
import cwdbg.SymWorld;

public class SymbolFile implements ISymbolFile {

	private SymWorld symWorld;

	public SymbolFile(SymWorld value) {
		value.AddRef();
		this.symWorld = value;
	}

	/* (non-Javadoc)
	 * @see com.nokia.cdt.debug.cw.symbian.symbolreader.ISymbolFile#close()
	 */
	public void close() {
		symWorld.Release();
	}

	/* (non-Javadoc)
	 * @see com.nokia.cdt.debug.cw.symbian.symbolreader.ISymbolFile#getFunctions()
	 */
	public IFunction[] getFunctions() {
		ArrayList<Function> functions = new ArrayList<Function>();
		
		SymFunction[] symFunctions = symWorld.GetFunctionList();
		for (int i = 0; i < symFunctions.length; i++) {
			functions.add(new Function(symFunctions[i]));
		}
		return functions.toArray(new Function[functions.size()]);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.cdt.debug.cw.symbian.symbolreader.ISymbolFile#findSourceLocation(java.math.BigInteger)
	 */
	public ISourceLocation findSourceLocation(BigInteger address)
	{
		Address addr = new Address(address.longValue(), ""); // $NON-NLS-1$
		IntHolder lineNumberHolder = new IntHolder();
		StringHolder fileHolder = new StringHolder();

		cwdbg.DebugError err = symWorld.FindSourceCorrespondance(addr, fileHolder, lineNumberHolder);
		if (err != null) {
			err.Release();
			return null;		
		}
		return new SourceLocation(fileHolder.value, lineNumberHolder.value);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.cdt.debug.cw.symbian.symbolreader.ISymbolFile#findFunctionByAddress(java.math.BigInteger)
	 */
	public IFunction findFunctionByAddress(BigInteger address)
	{
		Address adr = new Address(address.longValue(), ""); // $NON-NLS-1$
		IntHolder id = new IntHolder();

		cwdbg.DebugError err = symWorld.FindFunctionByAddress(adr, id);
		if (err != null) {
			err.Release();
			return null;		
		}

		StringHolder nameHolder = new StringHolder();
		err = symWorld.GetFunctionName(id.value, true, nameHolder);
		if (err != null) {
			err.Release();
			return null;		
		}

		AddressHolder startHolder = new AddressHolder();
		AddressHolder endHolder = new AddressHolder();		
		err = symWorld.GetFunctionBoundaries(id.value, startHolder, endHolder);
		if (err != null) {
			err.Release();
			return null;		
		}

		return new Function(id.value, nameHolder.value, BigInteger.valueOf(startHolder.value.addr), BigInteger.valueOf(endHolder.value.addr));		
	}

	public String[] getSourceFiles() {	
		return symWorld.GetSourceFiles();
	}

}
