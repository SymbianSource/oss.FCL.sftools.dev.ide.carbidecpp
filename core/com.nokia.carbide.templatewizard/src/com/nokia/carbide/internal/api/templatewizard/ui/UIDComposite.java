/*
* Copyright (c) 2006, 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.internal.api.templatewizard.ui;

import com.nokia.carbide.internal.api.templatewizard.ui.TemplateWizardPage.FieldModel;
import com.nokia.carbide.templatewizard.Messages;
import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.util.Random;

/**
 * A composite with text field for entering a 32 bit UID in hex format and random button
 */
public class UIDComposite extends Composite {

	private Text text;
	private Button button;

	public static Random random = new Random();
	public static final String HEX_PREFIX = "0x"; //$NON-NLS-1$
	public static final int HEX_PREFIX_LEN = HEX_PREFIX.length();
	public static final String WITHOUT_0X_PREFIX = "-WITHOUT_0X"; //$NON-NLS-1$
	private long minValue;
	private long maxValue;
	
	public UIDComposite(Composite parent, FieldModel model, long minValue, long maxValue) {
		super(parent, SWT.NULL);
		final GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		setLayout(gridLayout);
		text = new Text(this, SWT.BORDER);
		text.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		this.minValue = minValue;
		this.maxValue = maxValue;
		setRandom();
		model.setValue(text.getText());
		button = new Button(this, SWT.PUSH);
		button.setText(Messages.getString("UIDComposite.RandomLabel")); //$NON-NLS-1$
		button.setLayoutData(new GridData(GridData.END, GridData.CENTER, false, false));
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setRandom();
			}
		});
	}
	
	public void setRandom() {
		text.setText(getRandomAppUID());
		Event event = new Event();
		event.item = text;
		text.notifyListeners(SWT.Modify, event);
	}
	
	public boolean isValid() {
		return validateAppUIDText();
	}
	
    private static String createCanonicalHexString(long value) {
    	final char[] ZEROS = { '0', '0', '0', '0', '0', '0', '0', '0' };
    	String unprocessedHex = Long.toHexString(value).toUpperCase();
    	StringBuffer buf = new StringBuffer(10);
    	buf.append(HEX_PREFIX);
    	int numSigDigits = unprocessedHex.length();
    	int numLeadingZeros = 8 - numSigDigits;
    	if (numLeadingZeros > 0)
    		buf.append(ZEROS, 0, numLeadingZeros);
    	buf.append(unprocessedHex);
    	
    	return buf.toString();
    }
    
    public static String makeCanonicalHexString(String hexString) {
    	try {
    		long l = Long.parseLong(hexString.substring(HEX_PREFIX_LEN), 16);
    		return createCanonicalHexString(l);
    	}
    	catch (NumberFormatException e) {
    	}
    	catch (IndexOutOfBoundsException e) {
    	}
    	
    	return hexString;
    }
    
    public static boolean isValidHexString(String string) {
    	if (string.length() < HEX_PREFIX_LEN || 
    			!string.substring(0, HEX_PREFIX_LEN).equalsIgnoreCase(HEX_PREFIX))
    		return false;

    	try {
    		Long.parseLong(string.substring(HEX_PREFIX_LEN), 16);
    	}
    	catch (NumberFormatException e) {
    		return false;
    	}
    	
    	return true;
    }
    
    public boolean validateAppUIDText() {
    	String string = text.getText().trim();
    	if (!string.substring(0, HEX_PREFIX_LEN).equalsIgnoreCase(HEX_PREFIX))
    		return false;

    	String valueText = string.substring(HEX_PREFIX_LEN);
    	
    	long value = -1;
    	try {
    		value = Long.parseLong(valueText, 16);
    	}
    	catch (NumberFormatException e) {
    		// drop through
    	}
    	
   		return validateAppUIDValue(value);
    }

	private boolean validateAppUIDValue(long value) {
		return (value >= minValue) && (value <= maxValue);		
	}
    
	String getRandomAppUID() {
		long value = Math.abs(random.nextLong());
		value = (value % (maxValue - minValue)) + minValue;
		Check.checkState(validateAppUIDValue(value));
		return createCanonicalHexString(value);
	}

	public Text getTextControl() {
		return text;
	}

	public String getMinText() {
		return createCanonicalHexString(minValue);
	}

	public String getMaxText() {
		return createCanonicalHexString(maxValue);
	}
	
	public static String getWithout0x(String with0x) {
		Check.checkContract(with0x.startsWith(HEX_PREFIX));
		return with0x.substring(HEX_PREFIX.length());
	}

	@Override
	public void setToolTipText(String string) {
		super.setToolTipText(string);
		text.setToolTipText(string);
		button.setToolTipText(string);
	}
}