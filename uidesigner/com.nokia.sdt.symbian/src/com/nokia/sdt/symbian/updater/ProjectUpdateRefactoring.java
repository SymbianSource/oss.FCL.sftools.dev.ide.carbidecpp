/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.sdt.symbian.updater;

import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring;
import org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor;

/**
 * 
 *
 */
public class ProjectUpdateRefactoring extends ProcessorBasedRefactoring {

	private ProjectRefactoringProcessor processor;

	/**
	 * 
	 */
	public ProjectUpdateRefactoring(ProjectRefactoringProcessor processor) {
		super(processor);
		this.processor = processor;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring#getProcessor()
	 */
	@Override
	public RefactoringProcessor getProcessor() {
		return processor;
	}

}
