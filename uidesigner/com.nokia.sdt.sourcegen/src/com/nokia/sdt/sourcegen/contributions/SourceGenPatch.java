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

import com.nokia.sdt.sourcegen.ISourceGenPatch;
import com.nokia.sdt.sourcegen.core.Messages;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.core.runtime.IPath;
import org.osgi.framework.Version;

import java.text.MessageFormat;

/**
 * 
 *
 */
public class SourceGenPatch implements ISourceGenPatch {

	private Version fromVersion;
	private Version toVersion;
	private String[] patchLines;
	private ILocation location;
	private IContribution contrib;
	private int serialNumber;
	private String description;
	private boolean conflicting;
	private String conflictMessage;

	public SourceGenPatch(PatchContribution patchContrib, int serialNumber) {
		this.serialNumber = serialNumber;
		this.contrib = patchContrib.getContribution();
		this.fromVersion = patchContrib.getFromVersion();
		this.toVersion = patchContrib.getToVersion();
		
		this.patchLines = contrib.getText().split("\r|\n|\r\n"); //$NON-NLS-1$
		if (patchLines.length > 0 && patchLines[0].startsWith(DESCRIPTION_PREFIX)) {
			String descriptionText = patchLines[0].substring(DESCRIPTION_PREFIX.length());
			this.description = MessageFormat.format(Messages.getString("SourceGenPatch.PatchWithDescriptionFormat"), //$NON-NLS-1$
					new Object[] { serialNumber, descriptionText });
			String[] newLines = new String[patchLines.length - 1];
			System.arraycopy(patchLines, 1, newLines, 0, patchLines.length - 1);
			this.patchLines = newLines;
		} else {
			this.description = MessageFormat.format(Messages.getString("SourceGenPatch.PatchWithoutDescriptionFormat"), //$NON-NLS-1$
					new Object[] { serialNumber });
		}
		
		this.location = contrib.getLocation();
		Check.checkArg(location != null);
		
		this.conflicting = false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (String l : patchLines) {
			buffer.append(l);
			buffer.append('\n');
		}
		return "@@@ " + location + "@@@\n" + buffer; //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.ISourceGenPatch#getDescription()
	 */
	public String getDescription() {
		return description;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.ISourceGenPatch#getSerialNumber()
	 */
	public int getSerialNumber() {
		return serialNumber;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.ISourceGenPatch#setSerialNumber(int)
	 */
	public void setSerialNumber(int number) {
		this.serialNumber = number;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.ISourceGenPatch#getComponent()
	 */
	public String getComponentId() {
		return contrib.getComponentId();
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.ISourceGenPatch#getPath()
	 */
	public IPath getPath() {
		return location.getPath();
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.ISourceGenPatch#getLocationPath()
	 */
	public String getLocationPath() {
		return location.getLocationPath();
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.ISourceGenPatch#getFromVersion()
	 */
	public Version getFromVersion() {
		return fromVersion;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.ISourceGenPatch#getToVersion()
	 */
	public Version getToVersion() {
		return toVersion;
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.ISourceGenPatch#getPatchLines()
	 */
	public String[] getPatchLines() {
		return patchLines;
	}

	/**
	 * Accessor for sourcegen engine that applies patches.
	 * @return
	 */
	public ILocation getLocation() {
		return location;
	}

	/**
	 * Get the contribution that contributed the patch
	 * @return
	 */
	public IContribution getContribution() {
		return contrib;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.sdt.sourcegen.ISourceGenPatch#isConflicting()
	 */
	public boolean isConflicting() {
		return conflicting;
	}
	
	public String getConflictMessage() {
		return conflictMessage;
	}
	
	public void markConflicting(String conflictMessage) {
		this.conflicting = true;
		this.conflictMessage = conflictMessage;
	}
}	
