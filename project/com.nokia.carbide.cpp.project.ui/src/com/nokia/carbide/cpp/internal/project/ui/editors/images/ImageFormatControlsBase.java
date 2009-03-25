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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

/**
 * The color/grayscale, color/mask depth, and "default" button block
 *
 */
public abstract class ImageFormatControlsBase extends Composite {

	protected BitDepthsCombo maskDepthCombo;
	protected BitDepthsCombo colorDepthCombo;
	protected Button colorButton;
	protected Button greyButton;
	private boolean disallowZeroDepth;

	/**
	 * @param selected
	 * @param style
	 * @param label 
	 */
	public ImageFormatControlsBase(Composite group, int style, boolean allowMixed, boolean disallowZeroDepth) {
		super(group, style);
		this.disallowZeroDepth = disallowZeroDepth;
		setLayout(new GridLayout(2, false));

		final Label colorDepthLabel = new Label(this, SWT.NONE);
		colorDepthLabel.setText(Messages.getString("ImageFormatControls.ColorDepthLabel")); //$NON-NLS-1$
		colorDepthLabel.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));
		colorDepthCombo = new BitDepthsCombo(this, allowMixed, new int[] { 1, 2, 4, 8, 12, 16, 24 });
		colorDepthCombo.getCCombo().setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
		colorDepthCombo.getCCombo().setToolTipText(Messages.getString("ImageFormatControls.ColorDepthComboTooltip")); //$NON-NLS-1$

		final Label maskDepthLabel = new Label(this, SWT.NONE);
		maskDepthLabel.setText(Messages.getString("ImageFormatControls.MaskDepthLabel")); //$NON-NLS-1$
		maskDepthLabel.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));
		maskDepthCombo = new BitDepthsCombo(this, allowMixed, new int[] { 0, 1, 8 }
		);
		maskDepthCombo.getCCombo().setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
		maskDepthCombo.getCCombo().setToolTipText(Messages.getString("ImageFormatControls.MaskDepthComboTooltip")); //$NON-NLS-1$
		

		final Label headerRadioLabel = new Label(this, SWT.NONE);
		headerRadioLabel.setText(Messages.getString("ImageFormatControls.ColorFormatLabel")); //$NON-NLS-1$
		headerRadioLabel.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));

		Composite buttons = new Composite(this, SWT.NONE);
		buttons.setLayoutData(new GridData(GridData.BEGINNING, GridData.FILL, false, false));
		buttons.setLayout(new GridLayout(1, false));
		colorButton = new Button(buttons, SWT.RADIO);
		colorButton.setText(Messages.getString("ImageFormatControls.ColorLabel")); //$NON-NLS-1$
		colorButton.setToolTipText(Messages.getString("ImageFormatControls.ColorFormatTooltip")); //$NON-NLS-1$
		colorButton.setLayoutData(new GridData(GridData.BEGINNING, GridData.FILL, false, false));
		greyButton = new Button(buttons, SWT.RADIO);
		greyButton.setText(Messages.getString("ImageFormatControls.GrayscaleLabel")); //$NON-NLS-1$
		greyButton.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
		greyButton.setToolTipText(Messages.getString("ImageFormatControls.GrayscaleFormatTooltip")); //$NON-NLS-1$
		
		updateColorDepths(allowMixed, disallowZeroDepth);
	}

	protected void updateColorDepths(boolean isColor, boolean isGrey) {
		int[] baseDepths;
		int depth = colorDepthCombo.getDepth();
		if (isColor == isGrey) {
			baseDepths = new int[] { 1, 2, 4, 8, 12, 16, 24, 32 };
		} else if (isColor) {
			baseDepths = new int[] { 4, 8, 12, 16, 24, 32 };
		} else {
			baseDepths = new int[] { 1, 2, 4, 8 };
		}
		int[] depths;
		if (disallowZeroDepth) {
			depths = baseDepths;
		} else {
			depths = new int[baseDepths.length + 1];
			depths[0] = 0;
			System.arraycopy(baseDepths, 0, depths, 1, baseDepths.length);
		}
		colorDepthCombo.setDepths(depths);
		colorDepthCombo.setDepth(depth);
	}

	abstract void refresh();

}
