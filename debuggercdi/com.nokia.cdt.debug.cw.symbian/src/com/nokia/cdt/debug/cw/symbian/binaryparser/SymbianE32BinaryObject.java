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
package com.nokia.cdt.debug.cw.symbian.binaryparser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.cdt.core.IAddressFactory;
import org.eclipse.cdt.core.IBinaryParser;
import org.eclipse.cdt.core.IBinaryParser.IBinaryObject;
import org.eclipse.cdt.core.IBinaryParser.ISymbol;
import org.eclipse.cdt.utils.Addr32Factory;
import org.eclipse.cdt.utils.BinaryObjectAdapter;
import org.eclipse.cdt.utils.coff.PE;
import org.eclipse.cdt.utils.coff.parser.PEBinaryObject;
import org.eclipse.cdt.utils.elf.Elf;
import org.eclipse.cdt.utils.elf.parser.ElfBinaryObject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/***
 * Class for parsing Symbian E32 file. As to symbolics data, the .sym
 * file accompanying the executable file is actually used.  
 */
public class SymbianE32BinaryObject extends BinaryObjectAdapter {

	class E32ImageHeader
	{
	   int iUid1;
	   int iUid2;
	   int iUid3;
	   int iCheck;
	   int iSignature; // 'EPOC'
	   short iCpu; // 0x1000 = X86, 0x2000 = ARM, 0x4000 = M*Core
	   int iCheckSumCode; // sum of all 32 bit words in .text
	   int iCheckSumData; // sum of all 32 bit words in .data
	   short iVersion;
	   long iTime;
	   int iFlags; // 0 = exe, 1 = dll, +2 = no call entry points
	   int iCodeSize; // size of code, import address table, constant data and export dir
	   int iDataSize; // size of initialized data
	   int iHeapSizeMin;
	   int iHeapSizeMax;
	   int iStackSize;
	   int iBssSize;
	   int iEntryPoint; // offset into code of entry point
	   int iCodeBase; // where the code is linked for 
	   int iDataBase; // where the data is linked for
	   int iDllRefTableCount; // filling this in enables E32ROM to leave space for it
	   int iExportDirOffset; // offset into the file of the export address table
	   int iExportDirCount;
	   int iTextSize; // size of just the text section
	   int iCodeOffset; // file offset to code section
	   int iDataOffset; // file offset to data section
	   int iImportOffset; // file offset to import section
	   int iCodeRelocOffset; // relocations for code and const
	   int iDataRelocOffset; // relocations for data
	   //TProcessPriority iPriority; // priority of this process
	};

	private IBinaryObject		m_proxy;
	private BinaryObjectInfo 	m_info;

	public SymbianE32BinaryObject(IBinaryParser parser, IPath p, int type){
		super(parser, p, type);

		m_proxy = null;
		m_info = null;
		
		// For an Symbian exe file, use the corresponding .sym file as
		// our proxy BinaryObject.
		//
		String exePath = p.toOSString();
		int dotIndex = exePath.lastIndexOf('.');
		if (dotIndex > 0) {
			exePath = exePath.substring(0, dotIndex);
		}
		exePath = exePath + ".sym"; //$NON-NLS-1$
		File symFile = new File(exePath);
		if (symFile.exists())
		{
			// First check if it's an Elf file.
			boolean isElf = false;
			boolean isPE = false;
			try {
				Elf elf = new Elf(exePath);
				// If the above constructor succeeds, it's Elf
				elf.dispose();
				isElf = true; 
			}
			catch (IOException e) {}

			if (! isElf) {
				// Check if it's PECOFF  file
				try {
					PE pe = new PE(exePath);
					// If the above constructor succeeds, it's PE
					pe.dispose();
					isPE = true; 
				}
				catch (IOException e) {}
			}
			
			IPath symIPath = new Path(exePath);

			if (isElf)
				m_proxy = new ElfBinaryObject(
						parser, 
						symIPath, 
						type); // this also implies the icon for the file in project view.
			else if (isPE)
				m_proxy = new PEBinaryObject(
						parser, 
						symIPath, 
						type);
			else {
				// Hmm, unknown format for the .sym file, how come ?
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.cdt.utils.BinaryObjectAdapter#getName()
	 */
	public String getName() {
		return super.getName();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.cdt.core.IBinaryParser.IBinaryFile#getContents()
	 */
	public InputStream getContents() throws IOException {
		return super.getContents();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.cdt.core.IBinaryParser.IBinaryObject#getSymbols()
	 */
	public ISymbol[] getSymbols() {
		if (m_proxy != null)
			return m_proxy.getSymbols();
		
		return NO_SYMBOLS;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class adapter) {
		if (m_proxy != null)
			return m_proxy.getAdapter(adapter);

		return super.getAdapter(adapter);
	}


	/* (non-Javadoc)
	 * @see org.eclipse.cdt.utils.BinaryObjectAdapter#getAddressFactory()
	 */
	public IAddressFactory getAddressFactory() {
		if (m_proxy != null)
			return m_proxy.getAddressFactory();

		return new Addr32Factory();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.cdt.utils.BinaryObjectAdapter#getBinaryObjectInfo()
	 */
	protected BinaryObjectInfo getBinaryObjectInfo() {
		// needed ?
		if (m_info == null)
		{
			m_info = new BinaryObjectInfo();
			m_info.cpu = "ARM";  //$NON-NLS-1$
			m_info.isLittleEndian = true;

			if (m_proxy != null) {
				m_info.cpu = m_proxy.getCPU(); 
				m_info.isLittleEndian = m_proxy.isLittleEndian();
				m_info.text = m_proxy.getText();
				m_info.bss = m_proxy.getBSS();
				m_info.data = m_proxy.getData();
				m_info.hasDebug = m_proxy.hasDebug();
				m_info.needed = ((BinaryObjectAdapter)m_proxy).getNeededSharedLibs();
			}
		}
		
		return m_info;
	}
}