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

package com.nokia.sdt.sourcegen.contributions;

import com.nokia.sdt.sourcegen.*;
import com.nokia.sdt.sourcegen.core.Messages;
import com.nokia.sdt.sourcegen.core.SourceGenContext;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ltk.core.refactoring.*;

import java.text.MessageFormat;
import java.util.*;

/**
 * This class incorporates a single multi-file patch operation into a
 * refactoring operation.  It is not directly a Refactoring, but the
 * ISourceGenUpgradingProvider 
 * The order of calls is:<p>
 * * construct<br>
 * loop:<br>
 * * runSourcePatches()<br>
 * * optional: applyChanges()<br>
 * * getResults()<br>
 * 
 * 
 * 
 *
 */
public class PatchRefactoringEngine {
	private PatchContext patchContext;
	private SourceGenContext context;
	private IPath patchLogFilePath;
	private Map<ISourceGenPatch, TextEditChangeGroup> patchToTextEditChangeGroupMap;
	
	/**
	 * @param path 
	 * 
	 */
	public PatchRefactoringEngine(SourceGenContext context, IPath patchLogFilePath) {
		this.context = context;
		this.patchLogFilePath = patchLogFilePath;
		this.patchToTextEditChangeGroupMap = new HashMap<ISourceGenPatch, TextEditChangeGroup>();
	}

	/**
	 * Run the patches over the current sources.
	 * @param patchInfo
	 * @return info about results
	 */
	public Change gatherSourceChanges(ISourceGenPatchInfo patchInfo) {
		this.patchContext = new PatchContext(patchLogFilePath);
		
		// organize patches by domain
		Map<IDomain, List<SourceGenPatch>> domainsToPatches = new HashMap<IDomain, List<SourceGenPatch>>();
		int serialNumber = 0;
		for (ISourceGenPatch ipatch : patchInfo.getSourceGenPatches()) {
			if (!(ipatch instanceof SourceGenPatch)) {
				ipatch.markConflicting(Messages.getString("PatchEngine.UnknownPatchClass")); //$NON-NLS-1$
				patchContext.getFailedPatches().add(ipatch);
				continue;
			}

			SourceGenPatch patch = (SourceGenPatch) ipatch;
			ILocation location = patch.getLocation();
			IDomain domain = location.getDomain();
			
			List<SourceGenPatch> patches = domainsToPatches.get(domain);
			if (patches == null) {
				patches = new ArrayList<SourceGenPatch>();
				domainsToPatches.put(domain, patches);
			}
			
			patch.setSerialNumber(++serialNumber);
			patches.add(patch);
		}
		
		CompositeChange changes = new CompositeChange(""); ////$NON-NLS-1$  //Messages.getString("PatchRefactoringEngine.SourceChangesDescription")); //$NON-NLS-1$
		changes.markAsSynthetic();
		
		for (IDomain domain : domainsToPatches.keySet()) {
			List<SourceGenPatch> patches = domainsToPatches.get(domain);
			
			IDomainPatchRefactoringEngine engine = domain.createPatchRefactoringEngine(patchContext);
			engine.applyPatches(patches);
			changes.add(engine.getChanges());
			Map<ISourceGenPatch, TextEditChangeGroup> map = engine.getPatchToTextEditMap();
			for (Map.Entry<ISourceGenPatch, TextEditChangeGroup> entry : map.entrySet())
				patchToTextEditChangeGroupMap.put(entry.getKey(), entry.getValue());
		}
		
		return changes; 
	}

	/**
	 * @return Returns the patchContext.
	 */
	public PatchContext getPatchContext() {
		return patchContext;
	}
	
	/**
	 * Get the results from patching.  Must be run sometime after #runSourcePatches().
	 */
	public RefactoringStatus getValidationStatus() {
		Check.checkContract(patchContext != null);
		
		// return with the news
		RefactoringStatus status = new RefactoringStatus();
		for (ISourceGenPatch patch : patchContext.getFailedPatches()) {
			status.addEntry(RefactoringStatus.WARNING,
					constructMessage(patch),
					null /* context */,
					"com.nokia.sdt.sourcegen", //$NON-NLS-1$
					0);
		}
		return status;
	}

	/**
	 * Construct an appropriate message for the refactoring status.
	 * @param patch
	 * @return
	 */
	private String constructMessage(ISourceGenPatch patch) {
		String baseMessage = patch.getConflictMessage();
		String pathInfo;
		if (context.getProject() != null)
			pathInfo = new Path(context.getProject().getName()).append(patch.getPath()).toString();
		else
			pathInfo = patch.getPath().toString();
		String locationInfo = patch.getLocationPath();
		if (locationInfo == null || locationInfo.length() == 0)
			locationInfo = Messages.getString("PatchRefactoringEngine.MainFileLocationLabel"); //$NON-NLS-1$
		
		String format = Messages.getString("PatchRefactoringEngine.ConflictingPatchDescription"); //$NON-NLS-1$
		return MessageFormat.format(format, new Object[] { pathInfo, locationInfo, baseMessage });
	}

	/**
	 * Look at the changes and see which were applied.
	 */
	public void detectSkippedPatches() {
		for (Map.Entry<ISourceGenPatch, TextEditChangeGroup> entry : patchToTextEditChangeGroupMap.entrySet()) {
			ISourceGenPatch patch = entry.getKey();
			TextEditChangeGroup changeGroup = entry.getValue();
			if (!changeGroup.isEnabled())
				patchContext.getSkippedPatches().add(patch);
		}
	}


}
