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
/* START_USECASES: CU1 END_USECASES */
package com.nokia.carbide.cpp.uiq.ui.viewwizard;

import com.nokia.cpp.internal.api.utils.core.Check;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import java.text.MessageFormat;
import java.util.Random;

public class AppUIDText {
	static final int kMinAppUIDValue = 0x01000000;
	static final int kMaxAppUIDValue = 0x0fffffff;

	private Text text;

	public static Random randomNumberGenerator = new Random();
	private static final String HEX_PREFIX = "0x"; //$NON-NLS-1$
	private static final String kMinAppUIDStr = 
		createCanonicalHexString(kMinAppUIDValue);
	private static final String kMaxAppUIDStr = 
		createCanonicalHexString(kMaxAppUIDValue);

	public AppUIDText(Composite parent, int style) {
		text = new Text(parent, style);
		setRandom();
	}
	
	public void setRandom() {
		text.setText(getRandomAppUID());
	}
	
	public boolean isValid() {
		return validateAppUIDText(text.getText());
	}
	
	public String getErrorString() {
		if (!isValid()) {
		    String tmpl = Messages.getString("AppUIDText.Error"); //$NON-NLS-1$
		    Object params[] = { kMinAppUIDStr, kMaxAppUIDStr };
		    String error = MessageFormat.format(tmpl, params);
		   	return error;
		}
		
		return null;
	}
	
    private static String createCanonicalHexString(int value) {
    	final char[] ZEROS = { '0', '0', '0', '0', '0', '0', '0', '0' };
    	String unprocessedHex = Integer.toHexString(value).toUpperCase();
    	StringBuffer buf = new StringBuffer(10);
    	buf.append(HEX_PREFIX);
    	int numSigDigits = unprocessedHex.length();
    	int numLeadingZeros = 8 - numSigDigits;
    	buf.append(ZEROS, 0, numLeadingZeros);
    	buf.append(unprocessedHex);
    	
    	return buf.toString();
    }
    
    private static boolean validateAppUIDText(String text) {
    	String valueText;
    	if (!text.startsWith(HEX_PREFIX) && !text.startsWith(HEX_PREFIX.toUpperCase()))
    		return false;

    	valueText = text.substring(2);
    	
    	int value = 0;
    	try {
    		value = Integer.parseInt(valueText, 16);
    	}
    	catch (NumberFormatException e) {
    		// nothing to do
    	}
    	
   		return validateAppUIDValue(value);
    }

	private static boolean validateAppUIDValue(int value) {
		return (value >= kMinAppUIDValue) && (value <= kMaxAppUIDValue);		
	}
    
	String getRandomAppUID() {
		int value = randomNumberGenerator.nextInt();
		if ((value & kMinAppUIDValue) == 0)
			value |= kMinAppUIDValue;
		value &= kMaxAppUIDValue;
		Check.checkState(validateAppUIDValue(value));
		return createCanonicalHexString(value);
	}
	
	public Text getControl() {
		return text;
	}
}