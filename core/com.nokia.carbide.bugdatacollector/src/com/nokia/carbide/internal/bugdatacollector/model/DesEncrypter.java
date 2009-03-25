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
 
package com.nokia.carbide.internal.bugdatacollector.model;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class is used encrypting and decrypting the BugZilla password 
 * in the preference page. 
 *
 */
public class DesEncrypter {
	Cipher ecipher;
    Cipher dcipher;
    final String SECRET_KEY = "#kjIj&sA"; //$NON-NLS-1$

    public DesEncrypter() {
        try {
        	byte[] bytesOfKey = SECRET_KEY.getBytes();
        	SecretKey key = new SecretKeySpec(bytesOfKey, "DES"); //$NON-NLS-1$
            ecipher = Cipher.getInstance("DES"); //$NON-NLS-1$
            dcipher = Cipher.getInstance("DES"); //$NON-NLS-1$
            ecipher.init(Cipher.ENCRYPT_MODE, key);
            dcipher.init(Cipher.DECRYPT_MODE, key);

        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

    /**
     * Encrypts the given string.
     * @param str string to encrypt
     * @return encrypted string, null if errors occurred
     */
    public String encrypt(String str) {
    	if (str == "") //$NON-NLS-1$
    		return str;
    	
        try {
            // Encode the string into bytes using utf-8
            byte[] utf8 = str.getBytes("UTF8"); //$NON-NLS-1$

            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);

            // Encode bytes to base64 to get a string
            return new sun.misc.BASE64Encoder().encode(enc);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Decrypts the given string.
     * @param str string to decrypt
     * @return decrypted string, null if errors occurred
     */
    public String decrypt(String str) {
    	if (str == "") //$NON-NLS-1$
    		return str;

    	try {
            // Decode base64 to get bytes
            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

            // Decrypt
            byte[] utf8 = dcipher.doFinal(dec);

            // Decode using utf-8
            return new String(utf8, "UTF8"); //$NON-NLS-1$
        } catch (Exception e) {
        }
        return null;
    }
}
