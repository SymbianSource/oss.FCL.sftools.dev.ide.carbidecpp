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
package com.nokia.carbide.cpp.debug.kernelaware;

import com.freescale.cdt.debug.cw.core.os.OSObjectStream;

import cwdbg.EclipseDEConstants;

/**
 * class for retrieving list of OS objects from an OS object stream that come
 * from backend.
 * 
 */
public class OSObjectBag {
	private int m_objectType;

	private OSObjectStream m_stream;

	public OSObjectBag(int objectType, OSObjectStream stream) {
		m_objectType = objectType;
		m_stream = stream;
	}

	public OSObject[] getObjectList() {
		OSObject[] result = new OSObject[0];

		// The "propertyDescriptors" is a string like this:
		// <ID>, <data type>, <id>, <data type> ....
		//
		String[] propertyDescList = m_stream.getPropertyDescriptorStream()
				.split(",");
		int propertyCount = propertyDescList.length / 2;

		String[] objList = m_stream.getObjectStream().split(";");
		int objCount = objList.length;

		switch (m_objectType) {
		case EclipseDEConstants.J_OSObjectType_Process:
			result = new OSObjectProcess[objCount];
			break;
		case EclipseDEConstants.J_OSObjectType_Thread:
			result = new OSObjectThread[objCount];
			break;
		case EclipseDEConstants.J_OSObjectType_Chunk:
			result = new OSObjectChunk[objCount];
			break;
		case EclipseDEConstants.J_OSObjectType_Library:
			result = new OSObjectLibrary[objCount];
			break;
		default:
			assert (false);
			return result;
		}

		for (int i = 0; i < objCount; i++) {
			String[] props = objList[i].split(","); // properties of one object
			assert (propertyCount == props.length);

			OSObjectProperty[] properties = new OSObjectProperty[propertyCount];
			String pID, pDataType;
			Object pvalue;
			for (int j = 0; j < propertyCount; j++) {
				pID = propertyDescList[j * 2];
				pDataType = propertyDescList[j * 2 + 1];
				pvalue = getProperyValueFromString(props[j], pDataType);
				properties[j] = new OSObjectProperty(pID, pvalue);
			}

			switch (m_objectType) {
			case EclipseDEConstants.J_OSObjectType_Process:
				result[i] = new OSObjectProcess(properties);
				break;
			case EclipseDEConstants.J_OSObjectType_Thread:
				result[i] = new OSObjectThread(properties);
				break;
			case EclipseDEConstants.J_OSObjectType_Chunk:
				result[i] = new OSObjectChunk(properties);
				break;
			case EclipseDEConstants.J_OSObjectType_Library:
				result[i] = new OSObjectLibrary(properties);
				break;
			}
		}

		return result;
	}

	private Object getProperyValueFromString(String string, String dataType) {
		if (dataType.equals(EclipseDEConstants.J_OSObjectPropertyDataType_SignedInt)) {
			Integer myInt;
			try {
				myInt = Integer.decode(string);
			} catch (Exception e) {
				assert false : "OSObjectBag: Invalid integer from backend.";
				return new Integer(0xffffffff);
			}

			return myInt;
		}
		else if (dataType.equals(EclipseDEConstants.J_OSObjectPropertyDataType_UnsignedInt)) {
			Long myLong;
			try {
				myLong = Long.decode(string);
			} catch (Exception e) {
				assert false : "OSObjectBag: Invalid unsigned long from backend.";
				myLong = new Long(0xffffffff);
			}

			return myLong;
		} else if (dataType
				.equals(EclipseDEConstants.J_OSObjectPropertyDataType_String))
			return string;

		return null;
	}
}
