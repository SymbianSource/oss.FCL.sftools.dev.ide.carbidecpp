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

package com.nokia.carbide.internal.cpp.epoc.engine.model.bldinf;

import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakMakeReference;
import com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakefileReference;


public class MakefileReference extends MakMakeReference implements
		IMakefileReference {

	private EMakeEngine engine;

	public MakefileReference() {
		
	}
	
	public MakefileReference(MakefileReference other) {
		super(other);
		this.engine = other.engine;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MakefileReference)) 
			return false;
		if (!super.equals(obj))
			return false;
		MakefileReference other = (MakefileReference) obj;
		return other.engine.equals(engine);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.bldinf.MakMakeReference#isValid()
	 */
	@Override
	public boolean isValid() {
		return super.isValid() && engine != null;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakefileReference#getMakeEngine()
	 */
	public EMakeEngine getMakeEngine() {
		return engine;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakefileReference#setMakeEngine(com.nokia.carbide.cpp.epoc.engine.model.bldinf.IMakefileReference.EMakeEngine)
	 */
	public void setMakeEngine(EMakeEngine engine) {
		this.engine = engine;
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.cpp.epoc.engine.model.bldinf.MakMakeReference#copy()
	 */
	@Override
	public IMakMakeReference copy() {
		return new MakefileReference(this);
	}
}
