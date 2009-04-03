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

package com.nokia.sdt.sourcegen;

import org.eclipse.core.runtime.IPath;

import java.util.*;

/**
 * Context for patches being applied.
 * 
 *
 */
public class PatchContext {
	private List<ISourceGenPatch> failedPatches;
	private List<ISourceGenPatch> appliedPatches;
	private List<ISourceGenPatch> skippedPatches;
	private IPath logFilePath;
	
	/**
	 * 
	 */
	public PatchContext(IPath logFilePath) {
		this.logFilePath = logFilePath;
		failedPatches = new ArrayList<ISourceGenPatch>();
		appliedPatches = new ArrayList<ISourceGenPatch>();
		skippedPatches = new ArrayList<ISourceGenPatch>();
	}
	
	/**
	 * @return Returns the applicable patches, which does
	 * not indicate whether they were actually applied or not (read/write)
	 */
	public List<ISourceGenPatch> getApplicablePatches() {
		return appliedPatches;
	}

	/**
	 * @return Returns the failedPatches (read/write)
	 */
	public List<ISourceGenPatch> getFailedPatches() {
		return failedPatches;
	}

	/**
	 * @return Returns the skippedPatches, which is a subset
	 * of the applicable patches (read/write)
	 */
	public List<ISourceGenPatch> getSkippedPatches() {
		return skippedPatches;
	}

	
	/**
	 * Issue a failure for all the given patches.
	 * @param patches
	 * @param string
	 */
	public void failAll(List<ISourceGenPatch> patches, String string) {
		for (Iterator iterator = patches.iterator(); iterator.hasNext();) {
			ISourceGenPatch patch = (ISourceGenPatch) iterator.next();
			patch.markConflicting(string);
			failedPatches.add(patch);
		}
	}

	/**
	 * Get the log file path (project-relative) 
	 * @return
	 */
	public IPath getLogFile() {
		return logFilePath;
	}
	
	/**
	 * Merge contents of another context into this one
	 * @param other
	 */
	public void merge(PatchContext other) {
		appliedPatches.addAll(other.getApplicablePatches());
		failedPatches.addAll(other.getFailedPatches());
		skippedPatches.addAll(other.getSkippedPatches());
	}

}
