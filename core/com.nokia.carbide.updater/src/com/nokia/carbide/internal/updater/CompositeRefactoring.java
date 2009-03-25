/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.internal.updater;

import org.eclipse.core.runtime.*;
import org.eclipse.ltk.core.refactoring.*;

import java.util.List;

public class CompositeRefactoring extends Refactoring {

	private final List<Refactoring> refactoringList;
	private final String name;

	public CompositeRefactoring(List<Refactoring> refactoringList, String name) {
		this.refactoringList = refactoringList;
		this.name = name;
	}
	
	
	@Override
	public RefactoringStatus checkFinalConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();
		for (Refactoring refactoring : refactoringList) {
			status.merge(refactoring.checkFinalConditions(pm));
		}
		
		return status;
	}

	@Override
	public RefactoringStatus checkInitialConditions(IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();
		for (Refactoring refactoring : refactoringList) {
			status.merge(refactoring.checkInitialConditions(pm));
		}
		
		return status;
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		CompositeChange change = new CompositeChange(getName());
		for (Refactoring refactoring : refactoringList) {
			change.add(refactoring.createChange(pm));
		}
		
		return change;
	}

	@Override
	public String getName() {
		return name;
	}

}
