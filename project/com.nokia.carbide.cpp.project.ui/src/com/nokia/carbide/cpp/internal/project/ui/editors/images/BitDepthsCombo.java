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
package com.nokia.carbide.cpp.internal.project.ui.editors.images;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

import java.util.HashMap;
import java.util.Map;


/**
 * This is a combo box which supports a list of depths and a "mixed" value
 *
 */
public class BitDepthsCombo {

	final String MIXED = Messages.getString("BitDepthsCombo.MixedValueComboEntry"); //$NON-NLS-1$
	private Combo combo;
	private Map<Integer, String> depthMap;
	private boolean allowMixed;

	public BitDepthsCombo(Composite parent, boolean allowMixed, int[] depths) {
		this.allowMixed = allowMixed; 
		depthMap = new HashMap<Integer, String>();
		combo = new Combo(parent, SWT.BORDER | SWT.READ_ONLY );
		setDepths(depths);
	}
	
	/**
	 * @param depths
	 */
	public void setDepths(int[] depths) {
		combo.removeAll();
		if (allowMixed)
			combo.add(MIXED);
		depthMap.clear();
		depthMap.put(-1, MIXED);
		for (int depth : depths) {
			String depthString = depth > 0 ? Integer.toString(depth) : Messages.getString("BitDepthsCombo.NotApplicable"); //$NON-NLS-1$
			combo.add(depthString);
			depthMap.put(depth, depthString);
		}
	}

	public Combo getCCombo() {
		return combo;
	}

	public void setDepth(int depth) {
		String string = depthMap.get(depth);
		if (string != null)
			combo.setText(string);
		else if (depth < 0 && allowMixed)
			combo.setText(MIXED);
		else
			combo.setText(Messages.getString("BitDepthsCombo.NotApplicable")); //$NON-NLS-1$
	}
	
	public int getDepth() {
		String text = combo.getText();
		for (Map.Entry<Integer, String> entry : depthMap.entrySet()) {
			if (entry.getValue().equals(text))
				return entry.getKey();
		}
		return -1;
	}
}
