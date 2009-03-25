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

package com.nokia.sdt.sourcegen.patcher;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import com.nokia.sdt.sourcegen.ISourceGenPatch;
import com.nokia.sdt.sourcegen.core.Messages;
import com.nokia.cpp.internal.api.utils.core.Check;

/**
 * 
 *
 */
public class Patcher {

	private List<AbstractPatchInstruction> instructions;
	private ISourceGenPatch patch;

	/**
	 * @throws PatchException 
	 * 
	 */
	public Patcher(ISourceGenPatch patch) throws PatchException {
		this.patch = patch;
		this.instructions = createUnifiedDiffPatchInstructions(patch.getPatchLines());
	}

	/**
	 * @param patchLines
	 * @return
	 */
	private List<AbstractPatchInstruction> createUnifiedDiffPatchInstructions(String[] patchLines) throws PatchException {
		List<AbstractPatchInstruction> instructions = new ArrayList<AbstractPatchInstruction>();
		List<String> lines = null;
		List<String> newLines = null;
		AbstractPatchInstruction current = null;
		
		char lastCode = 0;
		boolean hadAnyContext = false;
		for (String line : patchLines) {
			char code = 0;
			if (line.length() < 2) {
				// empty lines not allowed
				throw new PatchException(
						MessageFormat.format(Messages.getString("Patcher.InvalidEmptyPatchLine"), //$NON-NLS-1$
								new Object[] { line }));
			} else {
				code = line.charAt(0);
				if ((code != '+' && code != '-' && code != ' ') 
					|| line.charAt(1) != ' ') {
					throw new PatchException(
							MessageFormat.format(Messages.getString("Patcher.InvalidPatchLine"), //$NON-NLS-1$
									new Object[] { line }));
				}
				line = line.substring(2);
			}

			boolean continueReplaceBlock = (lastCode == '-' && code == '+'); 
			if (code != lastCode && !continueReplaceBlock) {
				lastCode = code;
				lines = new ArrayList<String>();
			
				switch (code) {
				case ' ':
					current = new MatchLinesPatchInstruction(lines);
					hadAnyContext = true;
					break;
				case '+':
					current = new InsertLinesPatchInstruction(lines, hadAnyContext);
					break;
				case '-':
					newLines = new ArrayList<String>();
					current = new ReplaceLinesPatchInstruction(lines, newLines);
					hadAnyContext = true;
					break;
				}
				instructions.add(current);
			}
			
			if (current instanceof ReplaceLinesPatchInstruction) {
				if (code == '-') {
					lines.add(line);
				} else {
					newLines.add(line);
				}
			} else {
				lines.add(line);
			}
		}
		
		return instructions;
	}
	
	/**
	 * Apply the patch to the lines, modifying it in the process
	 *
	 */
	public boolean apply(List<String> currentLines) {
		return apply(new LineArrayPatchHandler(currentLines));
	}

	/**
	 * Apply the patch using the handler
	 */
	public boolean apply(IPatchHandler handler) {
		Check.checkArg(handler);
		
		// match entire patch against some start line
		int cursor = match(handler);
		if (cursor == -1)
			return false;

		// now apply it
		for (AbstractPatchInstruction instr : instructions) {
			cursor = instr.apply(patch, cursor, handler);
			Check.checkState(cursor >= 0);
		}
		return true;
	}

	/**
	 * Match the patches against the lines.
	 * Find the first line that allows 
	 * @param currentLines
	 * @return first line that allows patches to be cleanly applied
	 */
	private int match(IPatchHandler handler) {
		// check up to last line (in case of insert)
		for (int l = 0; ; l++) {
			int matches = match(l, handler);
			if (matches == instructions.size())
				return l;
			if (!handler.hasLine(l))
				break;
		}
		return -1;
	}

	/**
	 * Try to match the instructions against the lines starting
	 * at the given line
	 * @param line
	 * @param currentLines
	 * @return number of matches before failure
	 */
	private int match(int line, IPatchHandler handler) {
		int matches = 0;
		for (AbstractPatchInstruction instr : instructions) {
			line = instr.match(patch, line, handler);
			if (line == -1)
				break;
			matches += 1;
		}
		return matches;
	}
}
