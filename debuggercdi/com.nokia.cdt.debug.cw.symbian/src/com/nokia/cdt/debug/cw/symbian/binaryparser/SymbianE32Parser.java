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
 
import java.io.IOException;

import org.eclipse.cdt.core.AbstractCExtension;
import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.IBinaryParser;
import org.eclipse.cdt.utils.ERandomAccessFile;
import org.eclipse.core.runtime.IPath;

/**
 * Binary Parser for Symbian E32 Image file (executable file).
 */
public class SymbianE32Parser extends AbstractCExtension implements IBinaryParser {

	/* (non-Javadoc)
	 * @see org.eclipse.cdt.core.IBinaryParser#getBinary(org.eclipse.core.runtime.IPath)
	 */
	public IBinaryFile getBinary(IPath path) throws IOException {
		return getBinary(null, path);
	}


	public IBinaryFile getBinary(byte[] hints, IPath path) throws IOException {
		if (path == null) {
			throw new IOException(CCorePlugin.getResourceString("Util.exception.nullPath")); //$NON-NLS-1$
		}

		if (!isBinary(hints, path)) {
			return null;			
		}
		
		IBinaryFile binary = null;

		int binaryType = IBinaryFile.EXECUTABLE;
		if (hints != null) {
			// see comment in isBinary().
			if (hints[0] == 0x79)
				binaryType = IBinaryFile.SHARED;
		}
		
		binary = new SymbianE32BinaryObject(
						this, 
						path, 
						binaryType);// this also implies the icon for the file in project view.
		
		return binary;
	}

	/**
	 * @see org.eclipse.cdt.core.model.IBinaryParser#getFormat()
	 */
	public String getFormat() {
		return "Symbian E32"; //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see org.eclipse.cdt.core.IBinaryParser#isBinary(byte[], org.eclipse.core.runtime.IPath)
	 */
	public boolean isBinary(byte[] array, IPath path) {
		boolean accept = false;
		
		if (array == null) {  // possible ?
			ERandomAccessFile efile = null;
		
			try {
				efile = new ERandomAccessFile(path.toOSString(), "r"); //$NON-NLS-1$
	
				efile.seek(0);
				array = new byte[32]; 
			    efile.readFully(array);
			}
			catch (IOException e) {};

			try {
				if (efile != null)
					efile.close();
			}
			catch (IOException e) {};
		}

		/* E32 image header:  See "class E32ImageHeader" in SymbianE32BinaryObject for more.
		   {
		   TUint32 iUid1;
		   TUint32 iUid2;
		   TUint32 iUid3;
		   TUint32 iCheck;
		   TUint iSignature; // 'EPOC'
		   TCpu iCpu; // 0x1000 = X86, 0x2000 = ARM, 0x4000 = M*Core
		   ...
		   }
		   
		   The first UID can be thought of as a system level identifier, 
		   for example 0x10000079 for DLLs and 0x1000007A for executables.

		   4 bytes starting at byte #16:  45 50 4f 43  ......  "EPOC".
		*/
		if (array != null && array.length >= 20) {
		    if ((array[0]  == 0x7a || array[0] == 0x79) && 
			    	array[1]  == 0x00 && array[2]  == 0x00 && array[3] == 0x10 &&
					array[16] == 0x45 && array[17] == 0x50 && array[18] == 0x4f && array[19]== 0x43)
						accept = true;
		}

		return accept;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.cdt.core.IBinaryParser#getBufferSize()
	 */
	public int getHintBufferSize() {
		return 32;
	}
}
