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

import com.nokia.sdt.uimodel.Messages;
import com.nokia.cpp.internal.api.utils.core.TextUtils;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.*;

/**
 * Generates the patch log text.
 * 
 *
 */
public abstract class PatchLogWriter {

	public final static String HEADER_FORMAT = Messages.getString("PatchLogWriter.HeaderFormat"); //$NON-NLS-1$
	public final static String FAILED_PATCHES = Messages.getString("PatchLogWriter.FailedPatches"); //$NON-NLS-1$
	public final static String SKIPPED_PATCHES = Messages.getString("PatchLogWriter.SkippedPatches"); //$NON-NLS-1$
	public final static String APPLIED_PATCHES = Messages.getString("PatchLogWriter.AppliedPatches"); //$NON-NLS-1$
	public final static String LOCATION_INFO = Messages.getString("PatchLogWriter.LocationInfo"); //$NON-NLS-1$
	public final static String LOCATION_INFO_INSIDE = Messages.getString("PatchLogWriter.LocationInfoInside"); //$NON-NLS-1$
	public final static String DESCRIPTION_INFO = Messages.getString("PatchLogWriter.DescriptionInfo"); //$NON-NLS-1$
	public final static String COMPONENT_INFO = Messages.getString("PatchLogWriter.ComponentInfo"); //$NON-NLS-1$
	public final static String DIFF_INFO = Messages.getString("PatchLogWriter.DiffInfo"); //$NON-NLS-1$
	
	 /**
	  * Create human-readable log text describing the result of the patch operation.
	  * @return
	  */
	public static String createLogText(String projectName, PatchContext patchContext) {
		StringBuffer buffer = new StringBuffer();
		
		writeHeader(projectName, buffer);
		
		List<ISourceGenPatch> applicablePatches = patchContext.getApplicablePatches();
		Collection<ISourceGenPatch> appliedPatches = new LinkedHashSet<ISourceGenPatch>(applicablePatches);
		appliedPatches.removeAll(patchContext.getSkippedPatches());
		
		writePatchGroup(FAILED_PATCHES, patchContext.getFailedPatches(), buffer);
		writePatchGroup(SKIPPED_PATCHES, patchContext.getSkippedPatches(), buffer);
		writePatchGroup(APPLIED_PATCHES, appliedPatches, buffer);
		
		return buffer.toString();
	}

	private static void writeHeader(String projectName, StringBuffer buffer) {
		Date time = Calendar.getInstance().getTime();
		String now = DateFormat.getDateTimeInstance().format(time);
		buffer.append(MessageFormat.format(HEADER_FORMAT, 
				new Object[] { projectName, now }));
	}

	private static void writePatchGroup(String label, Collection<ISourceGenPatch> patches, StringBuffer buffer) {
		buffer.append(label);
		
		for (ISourceGenPatch patch : patches) {
			writePatch(patch, buffer);
		}
	}

	/**
	 * @param patch
	 * @param buffer
	 */
	private static void writePatch(ISourceGenPatch patch, StringBuffer buffer) {
		buffer.append(MessageFormat.format(DESCRIPTION_INFO, 
				new Object[] { patch.getDescription() } ));
		String locationPath = patch.getLocationPath();
		if (locationPath.length() > 0)
			buffer.append(MessageFormat.format(LOCATION_INFO_INSIDE, 
					new Object[] { patch.getPath(), locationPath } ));
		else
			buffer.append(MessageFormat.format(LOCATION_INFO, 
					new Object[] { patch.getPath() } ));
		buffer.append(MessageFormat.format(COMPONENT_INFO, 
				new Object[] { patch.getComponentId(),
				patch.getFromVersion(),
				patch.getToVersion() } ));
		buffer.append(MessageFormat.format(DIFF_INFO, 
				new Object[] { TextUtils.formatTabbedList(
						Arrays.asList(patch.getPatchLines())) } ));
		buffer.append("\n"); //$NON-NLS-1$
	}


}
