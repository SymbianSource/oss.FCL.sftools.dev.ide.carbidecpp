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
package com.nokia.carbide.cpp.epoc.engine.tests.model;

import com.nokia.carbide.cpp.epoc.engine.model.mmp.IMMPView;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

public class TestMMPView4 extends BaseMMPViewTest {
	// apparently feeding garbage to the parser can cause problems
	public void testBug3597_2() {
		String text = "// e32\\include\\e32des8.h\r\n" + 
				"//\r\n" + 
				"// Copyright (c) 1995-2004 Symbian Software Ltd. All rights reserved.\r\n" + 
				"//\r\n" + 
				"\r\n" + 
				"#ifndef __E32DES8_H__\r\n" + 
				"#define __E32DES8_H__\r\n" + 
				"\r\n" + 
				"/**\r\n" + 
				"@internalComponent\r\n" + 
				"*/\r\n" + 
				"const TUint KMaskDesLength8=0xfffffff;\r\n" + 
				"/**\r\n" + 
				"@internalComponent\r\n" + 
				"*/\r\n" + 
				"const TInt KShiftDesType8=28;\r\n" + 
				"\r\n" + 
				"class TBufCBase8;\r\n" + 
				"class TDes8;\r\n" + 
				"class TPtrC8;\r\n" + 
				"class TPtr8;\r\n" + 
				"class TPtr16;\r\n" + 
				"#ifndef __KERNEL_MODE__\r\n" + 
				"class HBufC8;\r\n" + 
				"#endif\r\n" + 
				"class TDesC8\r\n" + 
				"/**\r\n" + 
				"@publishedAll\r\n" + 
				"@released\r\n" + 
				"\r\n" + 
				"Abstract base class for 8-bit non-modifiable descriptors.\r\n" + 
				"\r\n" + 
				"The class encapsulates the data member containing the length of data\r\n" + 
				"represented by an 8-bit descriptor. It also provides member functions through\r\n" + 
				"which the data can be accessed, but not modified.\r\n" + 
				"\r\n" + 
				"Data represented by this class is treated as a contiguous set of 8-bit (i.e. \r\n" + 
				"single byte) values or data items.\r\n" + 
				"\r\n" + 
				"This class cannot be instantiated as it is intended to form part of a class \r\n" + 
				"hierarchy; it provides a well defined part of descriptor behaviour. It can, \r\n" + 
				"however, be passed as an argument type for functions which want access to \r\n" + 
				"descriptor data but do not need to modify that data.\r\n" + 
				"\r\n" + 
				"@see TDesC\r\n" + 
				"@see TPtrC8\r\n" + 
				"*/\r\n" + 
				"    {\r\n" + 
				"public:\r\n" + 
				"	inline TBool operator<(const TDesC8 &aDes) const;\r\n" + 
				"	inline TBool operator<=(const TDesC8 &aDes) const;\r\n" + 
				"	inline TBool operator>(const TDesC8 &aDes) const;\r\n" + 
				"	inline TBool operator>=(const TDesC8 &aDes) const;\r\n" + 
				"	inline TBool operator==(const TDesC8 &aDes) const;\r\n" + 
				"	inline TBool operator!=(const TDesC8 &aDes) const;\r\n" + 
				"	inline const TUint8 &operator[](TInt anIndex) const;\r\n" + 
				"	inline TInt Length() const;\r\n" + 
				"	inline TInt Size() const;\r\n" + 
				"	IMPORT_C const TUint8 *Ptr() const;\r\n" + 
				"	IMPORT_C TInt Compare(const TDesC8 &aDes) const;\r\n" + 
				"	IMPORT_C TInt Match(const TDesC8 &aDes) const;\r\n" + 
				"	IMPORT_C TInt MatchF(const TDesC8 &aDes) const;\r\n" + 
				"	IMPORT_C TInt MatchC(const TDesC8 &aDes) const;\r\n" + 
				"	IMPORT_C TInt Locate(TChar aChar) const;\r\n" + 
				"	IMPORT_C TInt LocateReverse(TChar aChar) const;\r\n" + 
				"	IMPORT_C TInt Find(const TDesC8 &aDes) const;\r\n" + 
				"	IMPORT_C TInt Find(const TUint8 *pS,TInt aLenS) const;\r\n" + 
				"	IMPORT_C TPtrC8 Left(TInt aLength) const;\r\n" + 
				"	IMPORT_C TPtrC8 Right(TInt aLength) const;\r\n" + 
				"	IMPORT_C TPtrC8 Mid(TInt aPos) const;\r\n" + 
				"	IMPORT_C TPtrC8 Mid(TInt aPos,TInt aLength) const;\r\n" + 
				"	IMPORT_C TInt CompareF(const TDesC8 &aDes) const;\r\n" + 
				"#ifndef __KERNEL_MODE__\r\n" + 
				"	IMPORT_C TInt CompareC(const TDesC8 &aDes) const;\r\n" + 
				"	IMPORT_C TInt LocateF(TChar aChar) const;\r\n" + 
				"	IMPORT_C TInt LocateReverseF(TChar aChar) const;\r\n" + 
				"	IMPORT_C TInt FindF(const TDesC8 &aDes) const;\r\n" + 
				"	IMPORT_C TInt FindF(const TUint8 *pS,TInt aLenS) const;\r\n" + 
				"	IMPORT_C TInt FindC(const TDesC8 &aDes) const;\r\n" + 
				"	IMPORT_C TInt FindC(const TUint8 *pS,TInt aLenS) const;\r\n" + 
				"	IMPORT_C HBufC8 *Alloc() const;\r\n" + 
				"	IMPORT_C HBufC8 *AllocL() const;\r\n" + 
				"	IMPORT_C HBufC8 *AllocLC() const;\r\n" + 
				"#endif\r\n" + 
				"protected:\r\n" + 
				"	inline TDesC8(TInt aType,TInt aLength);\r\n" + 
				"	inline TDesC8() {}\r\n" + 
				"// delay this for a while\r\n" + 
				"#ifdef SYMBIAN_FIX_TDESC_CONSTRUCTORS\r\n" + 
				"	inline TDesC8( const TDesC8& aOther) : iLength(aOther.iLength) {}\r\n" + 
				"#endif\r\n" + 
				"//	inline ~TDesC8() {}			Commented out for the moment since it breaks code\r\n" + 
				"	inline TInt Type() const;\r\n" + 
				"	inline void DoSetLength(TInt aLength);\r\n" + 
				"	IMPORT_C const TUint8 &AtC(TInt anIndex) const;\r\n" + 
				"private:\r\n" + 
				"	TUint iLength;\r\n" + 
				"	__DECLARE_TEST;\r\n" + 
				"    };\r\n" +
				"//\r\n" + 
				"class TPtrC8 : public TDesC8\r\n" + 
				"/**\r\n" + 
				"@publishedAll\r\n" + 
				"@released\r\n" + 
				"\r\n" + 
				"8-bit non-modifiable pointer descriptor.\r\n" + 
				"\r\n" + 
				"This is a descriptor class intended for instantiation and encapsulates a\r\n" + 
				"pointer to the 8-bit data that it represents. The data can live in ROM or RAM\r\n" + 
				"and this location is separate from the descriptor object itself.\r\n" + 
				"\r\n" + 
				"The data is intended to be accessed, but not changed, through this descriptor. \r\n" + 
				"The base class provides the functions through which data is accessed.\r\n" + 
				"\r\n" + 
				"@see TPtr8\r\n" + 
				"@see TDesC8\r\n" + 
				"@see TDes8\r\n" + 
				"@see TBufC8\r\n" + 
				"@see TBuf8\r\n" + 
				"@see HBufC8\r\n" + 
				"*/\r\n" + 
				"	{\r\n" + 
				"public:\r\n" + 
				"	IMPORT_C TPtrC8();\r\n" + 
				"	IMPORT_C TPtrC8(const TDesC8 &aDes);\r\n" + 
				"	IMPORT_C TPtrC8(const TUint8 *aString);\r\n" + 
				"	IMPORT_C TPtrC8(const TUint8 *aBuf,TInt aLength);\r\n" + 
				"	inline void Set(const TUint8 *aBuf,TInt aLength);\r\n" + 
				"	inline void Set(const TDesC8 &aDes);\r\n" + 
				"	inline void Set(const TPtrC8& aPtr);\r\n" + 
				"private:\r\n" + 
				"	TPtrC8& operator=(const TPtrC8 &aDes);\r\n" + 
				"protected:\r\n" + 
				"	const TUint8 *iPtr;\r\n" + 
				"private:\r\n" + 
				"	__DECLARE_TEST;\r\n" + 
				"	};\r\n" + 
				"//\r\n" + 
				"class TDes8Overflow\r\n" + 
				"/**\r\n" + 
				"@publishedAll\r\n" + 
				"@released\r\n" + 
				"\r\n" + 
				"An interface that defines an overflow handler for an 8-bit descriptor.\r\n" + 
				"\r\n" + 
				"The interface encapsulates a function that is called when an attempt to append \r\n" + 
				"formatted text fails because the descriptor is already at its maximum length.\r\n" + 
				"\r\n" + 
				"A derived class must provide an implementation for the Overflow() member\r\n" + 
				"function.\r\n" + 
				"\r\n" + 
				"@see TDes8::AppendFormat\r\n" + 
				"*/\r\n" + 
				"	{\r\n" + 
				"public:\r\n" + 
				"    /**\r\n" + 
				"    Handles the overflow.\r\n" + 
				"    \r\n" + 
				"    This function is called when the TDes8::AppendFormat() variant that takes\r\n" + 
				"    an overflow handler argument, fails.\r\n" + 
				"	\r\n" + 
				"	@param aDes The 8-bit modifiable descriptor whose overflow results in the \r\n" + 
				"	            call to this overflow handler.\r\n" + 
				"	*/\r\n" + 
				"	virtual void Overflow(TDes8 &aDes)=0;\r\n" + 
				"	};\r\n" + 
				"//\r\n" + 
				"class TDesC16;\r\n" + 
				"class TRealFormat;\r\n" + 
				"class TDes8 : public TDesC8\r\n" + 
				"/** \r\n" + 
				"@publishedAll\r\n" + 
				"@released\r\n" + 
				"\r\n" + 
				"Abstract base class for 8-bit modifiable descriptors.\r\n" + 
				"\r\n" + 
				"The class encapsulates the data member containing the maximum length of data\r\n" + 
				"represented by an 8-bit descriptor. It also provides member functions through\r\n" + 
				"which the data can be modified.\r\n" + 
				"\r\n" + 
				"The class adds to the behaviour provided by TDesC8.\r\n" + 
				"\r\n" + 
				"This class cannot be instantiated as it is intended to form part of a class \r\n" + 
				"hierarchy; it provides a well defined part of descriptor behaviour. It can, \r\n" + 
				"however, be passed as an argument type for functions which need to both modify \r\n" + 
				"and access descriptor data.\r\n" + 
				"\r\n" + 
				"@see TDes\r\n" + 
				"@see TDesC8\r\n" + 
				"@see TDesC16\r\n" + 
				"*/\r\n" + 
				"	{\r\n" + 
				"public:\r\n" + 
				"	inline TDes8& operator=(const TUint8 *aString);\r\n" + 
				"	inline TDes8& operator=(const TDesC8 &aDes);\r\n" + 
				"	inline TDes8& operator=(const TDes8 &aDes);\r\n" + 
				"	inline TInt MaxLength() const;\r\n" + 
				"	inline TInt MaxSize() const;\r\n" + 
				"	inline const TUint8 &operator[](TInt anIndex) const;\r\n" + 
				"	inline TUint8 &operator[](TInt anIndex);\r\n" + 
				"	inline TDes8 &operator+=(const TDesC8 &aDes);\r\n" + 
				"	IMPORT_C void Zero();\r\n" + 
				" 	IMPORT_C void SetLength(TInt aLength);\r\n" + 
				" 	IMPORT_C void SetMax();\r\n" + 
				"	IMPORT_C void Copy(const TDesC8 &aDes);\r\n" + 
				"	IMPORT_C void Copy(const TUint8 *aBuf,TInt aLength);\r\n" + 
				"	IMPORT_C void Copy(const TUint8 *aString);\r\n" + 
				"	IMPORT_C void Copy(const TDesC16 &aDes);\r\n" + 
				"	IMPORT_C void Append(TChar aChar);\r\n" + 
				"	IMPORT_C void Append(const TDesC8 &aDes);\r\n" + 
				"	IMPORT_C void Append(const TDesC16 &aDes);\r\n" + 
				"	IMPORT_C void Append(const TUint8 *aBuf,TInt aLength);\r\n" + 
				"	IMPORT_C void Fill(TChar aChar);\r\n" + 
				"	IMPORT_C void Fill(TChar aChar,TInt aLength);\r\n" + 
				"	IMPORT_C void FillZ();\r\n" + 
				"	IMPORT_C void FillZ(TInt aLength);\r\n" + 
				"	IMPORT_C void Num(TInt64 aVal);\r\n" + 
				"	IMPORT_C void Num(TUint64 aVal, TRadix aRadix);\r\n" + 
				"	IMPORT_C void NumFixedWidth(TUint aVal,TRadix aRadix,TInt aWidth);\r\n" + 
				"	IMPORT_C void AppendNum(TInt64 aVal);\r\n" + 
				"	IMPORT_C void AppendNum(TUint64 aVal, TRadix aRadix);\r\n" + 
				"	IMPORT_C void AppendNumFixedWidth(TUint aVal,TRadix aRadix,TInt aWidth);\r\n" + 
				"#ifndef __KERNEL_MODE__\r\n" + 
				"	IMPORT_C TPtr8 LeftTPtr(TInt aLength) const;\r\n" + 
				"	IMPORT_C TPtr8 RightTPtr(TInt aLength) const;\r\n" + 
				"	IMPORT_C TPtr8 MidTPtr(TInt aPos) const;\r\n" + 
				"	IMPORT_C TPtr8 MidTPtr(TInt aPos,TInt aLength) const;\r\n" + 
				"	IMPORT_C const TUint8 *PtrZ();\r\n" + 
				"	IMPORT_C void CopyF(const TDesC8 &aDes);\r\n" + 
				"	IMPORT_C void CopyC(const TDesC8 &aDes);\r\n" + 
				"	IMPORT_C void CopyLC(const TDesC8 &aDes);\r\n" + 
				"	IMPORT_C void CopyUC(const TDesC8 &aDes);\r\n" + 
				"	IMPORT_C void CopyCP(const TDesC8 &aDes);\r\n" + 
				"	IMPORT_C void Swap(TDes8 &aDes);\r\n" + 
				"	IMPORT_C void AppendFill(TChar aChar,TInt aLength);\r\n" + 
				"	IMPORT_C void ZeroTerminate();\r\n" + 
				"	IMPORT_C void Fold();\r\n" + 
				"	IMPORT_C void Collate();\r\n" + 
				"	IMPORT_C void LowerCase();\r\n" + 
				"	IMPORT_C void UpperCase();\r\n" + 
				"	IMPORT_C void Capitalize();\r\n" + 
				"	IMPORT_C void Repeat(const TUint8 *aBuf,TInt aLength);\r\n" + 
				"	IMPORT_C void Repeat(const TDesC8 &aDes);\r\n" + 
				"	IMPORT_C void Trim();\r\n" + 
				"	IMPORT_C void TrimAll();\r\n" + 
				"	IMPORT_C void TrimLeft();\r\n" + 
				"	IMPORT_C void TrimRight();\r\n" + 
				"	IMPORT_C void Insert(TInt aPos,const TDesC8 &aDes);\r\n" + 
				"	IMPORT_C void Delete(TInt aPos,TInt aLength);\r\n" + 
				"	IMPORT_C void Replace(TInt aPos,TInt aLength,const TDesC8 &aDes);\r\n" + 
				"	IMPORT_C void Justify(const TDesC8 &aDes,TInt aWidth,TAlign anAlignment,TChar aFill);\r\n" + 
				"	IMPORT_C void NumFixedWidthUC(TUint aVal,TRadix aRadix,TInt aWidth);\r\n" + 
				"	IMPORT_C void NumUC(TUint64 aVal, TRadix aRadix=EDecimal);\r\n" + 
				"	IMPORT_C TInt Num(TReal aVal,const TRealFormat &aFormat) __SOFTFP;\r\n" + 
				"	IMPORT_C void AppendNumFixedWidthUC(TUint aVal,TRadix aRadix,TInt aWidth);\r\n" + 
				"	IMPORT_C TInt AppendNum(TReal aVal,const TRealFormat &aFormat) __SOFTFP;\r\n" + 
				"	IMPORT_C void AppendNumUC(TUint64 aVal,TRadix aRadix=EDecimal);\r\n" + 
				"	IMPORT_C void Format(TRefByValue<const TDesC8> aFmt,...);\r\n" + 
				"	IMPORT_C void FormatList(const TDesC8 &aFmt,VA_LIST aList);\r\n" + 
				"	IMPORT_C void AppendJustify(const TDesC8 &Des,TInt aWidth,TAlign anAlignment,TChar aFill);\r\n" + 
				"	IMPORT_C void AppendJustify(const TDesC8 &Des,TInt aLength,TInt aWidth,TAlign anAlignment,TChar aFill);\r\n" + 
				"	IMPORT_C void AppendJustify(const TUint8 *aString,TInt aWidth,TAlign anAlignment,TChar aFill);\r\n" + 
				"	IMPORT_C void AppendJustify(const TUint8 *aString,TInt aLength,TInt aWidth,TAlign anAlignment,TChar aFill);\r\n" + 
				"	IMPORT_C void AppendFormat(TRefByValue<const TDesC8> aFmt,TDes8Overflow *aOverflowHandler,...);\r\n" + 
				"	IMPORT_C void AppendFormat(TRefByValue<const TDesC8> aFmt,...);\r\n" + 
				"	IMPORT_C void AppendFormatList(const TDesC8 &aFmt,VA_LIST aList,TDes8Overflow *aOverflowHandler=NULL);\r\n" + 
				"	IMPORT_C TPtr16 Expand();\r\n" + 
				"	IMPORT_C void Collapse();\r\n" + 
				"#endif //__KERNEL_MODE__\r\n" + 
				"protected:\r\n" + 
				"	inline TDes8(TInt aType,TInt aLength,TInt aMaxLength);\r\n" + 
				"	inline TUint8 *WPtr() const;\r\n" + 
				"	inline TDes8() {}\r\n" + 
				"// delay this for a while\r\n" + 
				"#ifdef SYMBIAN_FIX_TDESC_CONSTRUCTORS\r\n" + 
				"	inline TDes8(const TDes8& aOther) : TDesC8(aOther), iMaxLength(aOther.iMaxLength) {}\r\n" + 
				"#endif\r\n" + 
				"	void DoAppendNum(TUint64 aVal, TRadix aRadix, TUint aA, TInt aW);\r\n" + 
				"	void DoPadAppendNum(TInt aLength, TInt aW, const TUint8* aBuf);\r\n" + 
				"protected:\r\n" + 
				"	TInt iMaxLength;\r\n" + 
				"	__DECLARE_TEST;\r\n" + 
				"    };\r\n" + 
				"//\r\n" + 
				"class TPtr8 : public TDes8\r\n" + 
				"/**\r\n" + 
				"@publishedAll\r\n" + 
				"@released\r\n" + 
				"\r\n" + 
				"8-bit modifiable pointer descriptor.\r\n" + 
				"\r\n" + 
				"This is a descriptor class intended for instantiation and encapsulates a\r\n" + 
				"pointer to the 8-bit data that it represents. The data can live in ROM or\r\n" + 
				"RAM and this location is separate from the descriptor object itself.\r\n" + 
				"\r\n" + 
				"The data is intended to be accessed and modified through this descriptor. \r\n" + 
				"The base classes provide the functions through which the data can be \r\n" + 
				"manipulated.\r\n" + 
				"\r\n" + 
				"@see TPtr\r\n" + 
				"@see TPtrC8\r\n" + 
				"@see TDesC8\r\n" + 
				"@see TDes8\r\n" + 
				"@see TBufC8\r\n" + 
				"@see TBuf8\r\n" + 
				"@see HBufC8\r\n" + 
				"*/\r\n" + 
				"	{\r\n" + 
				"public:\r\n" + 
				"	IMPORT_C TPtr8(TUint8 *aBuf,TInt aMaxLength);\r\n" + 
				"	IMPORT_C TPtr8(TUint8 *aBuf,TInt aLength,TInt aMaxLength);\r\n" + 
				"	inline TPtr8& operator=(const TUint8 *aString);\r\n" + 
				"	inline TPtr8& operator=(const TDesC8& aDes);\r\n" + 
				"	inline TPtr8& operator=(const TPtr8& aPtr);\r\n" + 
				"	inline void Set(TUint8 *aBuf,TInt aLength,TInt aMaxLength);\r\n" + 
				"	inline void Set(const TPtr8 &aPtr);\r\n" + 
				"private:\r\n" + 
				"	IMPORT_C TPtr8(TBufCBase8 &aLcb,TInt aMaxLength);\r\n" + 
				"protected:\r\n" + 
				"	TUint8 *iPtr;\r\n" + 
				"private:\r\n" + 
				"	friend class TBufCBase8;\r\n" + 
				"	__DECLARE_TEST;\r\n" + 
				"	};\r\n" + 
				"//\r\n" + 
				"class TBufCBase8 : public TDesC8\r\n" + 
				"/**\r\n" + 
				"@internalAll\r\n" + 
				"*/\r\n" + 
				"	{\r\n" + 
				"protected:\r\n" + 
				"	IMPORT_C TBufCBase8();\r\n" + 
				"	inline TBufCBase8(TInt aLength);\r\n" + 
				"	IMPORT_C TBufCBase8(const TUint8 *aString,TInt aMaxLength);\r\n" + 
				"	IMPORT_C TBufCBase8(const TDesC8 &aDes,TInt aMaxLength);\r\n" + 
				"	IMPORT_C void Copy(const TUint8 *aString,TInt aMaxLength);\r\n" + 
				"	IMPORT_C void Copy(const TDesC8 &aDes,TInt aMaxLength);\r\n" + 
				"	inline TPtr8 DoDes(TInt aMaxLength);\r\n" + 
				"	inline TUint8 *WPtr() const;\r\n" + 
				"	};\r\n" + 
				"//\r\n" + 
				"#ifndef __KERNEL_MODE__\r\n" + 
				"class RReadStream;\r\n" + 
				"class HBufC8 : public TBufCBase8\r\n" + 
				"/**\r\n" + 
				"@publishedAll\r\n" + 
				"@released\r\n" + 
				"\r\n" + 
				"8-bit heap descriptor.\r\n" + 
				"\r\n" + 
				"This is a descriptor class which provides a buffer of fixed length, allocated \r\n" + 
				"on the heap, for containing and accessing data.\r\n" + 
				"\r\n" + 
				"The class is intended for instantiation.\r\n" + 
				"\r\n" + 
				"Heap descriptors have the important property that they can be made larger \r\n" + 
				"or smaller, changing the size of the descriptor buffer. This is achieved by \r\n" + 
				"reallocating the descriptor. Unlike the behaviour of dynamic buffers, \r\n" + 
				"reallocation is not done automatically.\r\n" + 
				"\r\n" + 
				"Data is intended to be accessed, but not modified; however, it can be \r\n" + 
				"completely replaced using the assignment operators of this class. The base\r\n" + 
				"class (TDesC8) provides the functions through which the data is accessed.\r\n" + 
				"\r\n" + 
				"The descriptor is hosted by a heap cell, and the 8-bit data that the\r\n" + 
				"descriptor represents is part of the descriptor object itself. The size of the\r\n" + 
				"cell depends on the requested maximum length of the descriptor buffer when the\r\n" + 
				"descriptor is created or re-allocated.\r\n" + 
				"\r\n" + 
				"It is important to note that the size of the allocated cell, and, therefore, \r\n" + 
				"the resulting maximum length of the descriptor, may be larger than requested \r\n" + 
				"due to the way memory is allocated in Symbian OS. The amount by which this \r\n" + 
				"may be rounded up depends on the platform and build type.\r\n" + 
				"\r\n" + 
				"@see HBufC\r\n" + 
				"@see TPtr8\r\n" + 
				"@see TDesC8\r\n" + 
				"*/\r\n" + 
				"	{\r\n" + 
				"public:\r\n" + 
				"	IMPORT_C static HBufC8 *New(TInt aMaxLength);\r\n" + 
				"	IMPORT_C static HBufC8 *NewL(TInt aMaxLength);\r\n" + 
				"	IMPORT_C static HBufC8 *NewLC(TInt aMaxLength);\r\n" + 
				"	IMPORT_C static HBufC8 *NewMax(TInt aMaxLength);\r\n" + 
				"	IMPORT_C static HBufC8 *NewMaxL(TInt aMaxLength);\r\n" + 
				"	IMPORT_C static HBufC8 *NewMaxLC(TInt aMaxLength);\r\n" + 
				"	IMPORT_C static HBufC8 *NewL(RReadStream &aStream,TInt aMaxLength);\r\n" + 
				"	IMPORT_C static HBufC8 *NewLC(RReadStream &aStream,TInt aMaxLength);\r\n" + 
				"	IMPORT_C HBufC8& operator=(const TUint8 *aString);\r\n" + 
				"	IMPORT_C HBufC8& operator=(const TDesC8 &aDes);\r\n" + 
				"	inline HBufC8& operator=(const HBufC8 &aLcb);\r\n" + 
				"	IMPORT_C HBufC8 *ReAlloc(TInt aMaxLength);\r\n" + 
				"	IMPORT_C HBufC8 *ReAllocL(TInt aMaxLength);\r\n" + 
				"	IMPORT_C TPtr8 Des();\r\n" + 
				"private:\r\n" + 
				"	inline HBufC8(TInt aLength);\r\n" + 
				"private:\r\n" + 
				"	TText8 iBuf[1];\r\n" + 
				"	__DECLARE_TEST;\r\n" + 
				"	};\r\n" + 
				"#endif\r\n" + 
				"//\r\n" + 
				"/**\r\n" + 
				"@internalComponent\r\n" + 
				"*/\r\n" + 
				"#define __Size8 (sizeof(TInt)/sizeof(TInt8))\r\n" + 
				"/**\r\n" + 
				"@internalComponent\r\n" + 
				"*/\r\n" + 
				"#define __Align8(s) ((((s)+__Size8-1)/__Size8)*__Size8)\r\n" + 
				"//\r\n" + 
				"template <TInt S>\r\n" + 
				"class TBufC8 : public TBufCBase8\r\n" + 
				"/**\r\n" + 
				"@publishedAll\r\n" + 
				"@released\r\n" + 
				"\r\n" + 
				"8-bit non-modifiable buffer descriptor. \r\n" + 
				"\r\n" + 
				"This is a descriptor class which provides a buffer of fixed length for\r\n" + 
				"containing and accessing TUint8 data.\r\n" + 
				"\r\n" + 
				"The class intended for instantiation. The 8-bit data that the descriptor\r\n" + 
				"represents is part of the descriptor object itself. \r\n" + 
				"\r\n" + 
				"The class is templated, based on an integer value which defines the size of \r\n" + 
				"the descriptor\'s data area.\r\n" + 
				"\r\n" + 
				"The data is intended to be accessed, but not modified; however, it can be \r\n" + 
				"completely replaced using the assignment operators of this class. The base \r\n" + 
				"class provides the functions through which the data is accessed.\r\n" + 
				"\r\n" + 
				"@see TBufC\r\n" + 
				"@see TDesC8\r\n" + 
				"@see TPtr8\r\n" + 
				"@see TUint8\r\n" + 
				"*/\r\n" + 
				"	{\r\n" + 
				"public:\r\n" + 
				"	inline TBufC8();\r\n" + 
				"    inline TBufC8(const TUint8 *aString);\r\n" + 
				"	inline TBufC8(const TDesC8 &aDes);\r\n" + 
				"	inline TBufC8<S> &operator=(const TUint8 *aString);\r\n" + 
				"	inline TBufC8<S> &operator=(const TDesC8 &aDes);\r\n" + 
				"	inline TPtr8 Des();\r\n" + 
				"protected:\r\n" + 
				"	TUint8 iBuf[__Align8(S)];\r\n" + 
				"	};\r\n" + 
				"//\r\n" + 
				"class TBufBase8 : public TDes8\r\n" + 
				"/**\r\n" + 
				"@internalAll\r\n" + 
				"*/\r\n" + 
				"	{\r\n" + 
				"protected:\r\n" + 
				"	IMPORT_C TBufBase8(TInt aMaxLength);\r\n" + 
				"	IMPORT_C TBufBase8(TInt aLength,TInt aMaxLength);\r\n" + 
				"	IMPORT_C TBufBase8(const TUint8* aString,TInt aMaxLength);\r\n" + 
				"	IMPORT_C TBufBase8(const TDesC8& aDes,TInt aMaxLength);\r\n" + 
				"	};\r\n" + 
				"//\r\n" + 
				"template <TInt S>\r\n" + 
				"class TBuf8 : public TBufBase8\r\n" + 
				"/**\r\n" + 
				"@publishedAll\r\n" + 
				"@released\r\n" + 
				"\r\n" + 
				"A descriptor class which provides a buffer of fixed length for\r\n" + 
				"containing, accessing and manipulating TUint8 data.\r\n" + 
				"\r\n" + 
				"The class is intended for instantiation. The 8-bit data that the descriptor \r\n" + 
				"represents is part of the descriptor object itself.\r\n" + 
				"\r\n" + 
				"The class is templated, based on an integer value which determines the size \r\n" + 
				"of the data area which is created as part of the buffer descriptor object; \r\n" + 
				"this is also the maximum length of the descriptor.\r\n" + 
				"\r\n" + 
				"The data is intended to be both accessed and modified. The base classes provide \r\n" + 
				"the functions through which the data is accessed.\r\n" + 
				"\r\n" + 
				"@see TBuf\r\n" + 
				"@see TDesC8\r\n" + 
				"@see TDes8\r\n" + 
				"@see TPtr8\r\n" + 
				"*/\r\n" + 
				"	{\r\n" + 
				"public:\r\n" + 
				"	inline TBuf8();\r\n" + 
				"	inline explicit TBuf8(TInt aLength);\r\n" + 
				"    inline TBuf8(const TUint8* aString);\r\n" + 
				"	inline TBuf8(const TDesC8& aDes);\r\n" + 
				"	inline TBuf8<S>& operator=(const TUint8* aString);\r\n" + 
				"	inline TBuf8<S>& operator=(const TDesC8& aDes);\r\n" + 
				"	inline TBuf8<S>& operator=(const TBuf8<S>& aBuf);\r\n" + 
				"protected:\r\n" + 
				"	TUint8 iBuf[__Align8(S)];\r\n" + 
				"	};\r\n" + 
				"\r\n" + 
				"//\r\n" + 
				"template <TInt S>\r\n" + 
				"class TAlignedBuf8 : public TBufBase8\r\n" + 
				"/**\r\n" + 
				"@internalComponent\r\n" + 
				"\r\n" + 
				"A descriptor class functionally identical to TBuf8, the only\r\n" + 
				"difference from it being that TAlignedBuf8\'s internal buffer \r\n" + 
				"is guaranteed to be 64-bit aligned.\r\n" + 
				"\r\n" + 
				"At present this class is not intended for general use. It exists\r\n" + 
				"solely to support TPckgBuf which derives from it.\r\n" + 
				"\r\n" + 
				"@see TBuf8\r\n" + 
				"@see TPckgBuf\r\n" + 
				"*/\r\n" + 
				"{\r\n" + 
				"public:\r\n" + 
				"	inline TAlignedBuf8();\r\n" + 
				"	inline explicit TAlignedBuf8(TInt aLength);\r\n" + 
				"    inline TAlignedBuf8(const TUint8* aString);\r\n" + 
				"	inline TAlignedBuf8(const TDesC8& aDes);\r\n" + 
				"	inline TAlignedBuf8<S>& operator=(const TUint8* aString);\r\n" + 
				"	inline TAlignedBuf8<S>& operator=(const TDesC8& aDes);\r\n" + 
				"	inline TAlignedBuf8<S>& operator=(const TAlignedBuf8<S>& aBuf);\r\n" + 
				"protected:\r\n" + 
				"	union {\r\n" + 
				"		double only_here_to_force_8byte_alignment;\r\n" + 
				"		TUint8 iBuf[__Align8(S)];\r\n" + 
				"	};\r\n" + 
				"};\r\n" + 
				"\r\n" + 
				"#ifndef __KERNEL_MODE__\r\n" + 
				"\r\n" + 
				"class RBuf8 : public TDes8\r\n" + 
				"/**\r\n" + 
				"@publishedAll\r\n" + 
				"@released\r\n" + 
				"\r\n" + 
				"8 bit resizable buffer descriptor.\r\n" + 
				"\r\n" + 
				"The class provides a buffer that contains, accesses and manipulates\r\n" + 
				"TUint8 data. The buffer itself is on the heap, and is managed by the class.\r\n" + 
				"\r\n" + 
				"Internally, RBuf8 behaves in one of two ways:\r\n" + 
				"\r\n" + 
				"- as a TPtr8 descriptor type, where the buffer just contains data\r\n" + 
				"- as a pointer to a heap descriptor, an HBufC8* type, where the buffer\r\n" + 
				"  contains both	descriptor information and the data.\r\n" + 
				"\r\n" + 
				"Note that the handling of the distinction is hidden from view.\r\n" + 
				"\r\n" + 
				"An RBuf8 object can allocate its own buffer. Alternatively, it can take\r\n" + 
				"ownership of a pre-existing section of allocated memory, or it can take\r\n" + 
				"ownership of a pre-existing heap descriptor. It can also reallocate the buffer\r\n" + 
				"to resize it. Regardless of the way in which the buffer has been allocated,\r\n" + 
				"the RBuf8 object is responsible for freeing memory when the object itself is closed.\r\n" + 
				"\r\n" + 
				"The class is intended for instantiation.\r\n" + 
				"\r\n" + 
				"The class is derived from TDes8, which means that data can be both accessed\r\n" + 
				"and modified. The base classes provide the functions through which the data is\r\n" + 
				"accessed. In addition, an RBuf8 object can be passed to any function that is\r\n" + 
				"prototyped to take a TDes8 or a TDesC8 type.\r\n" + 
				"\r\n" + 
				"@see TBuf8\r\n" + 
				"@see TPtr8\r\n" + 
				"@see HBufC8\r\n" + 
				"@see TDesC8\r\n" + 
				"@see TDes8\r\n" + 
				"*/\r\n" + 
				"	{\r\n" + 
				"public:\r\n" + 
				"	IMPORT_C RBuf8();\r\n" + 
				"	IMPORT_C explicit RBuf8(HBufC8* aHBuf);\r\n" + 
				"	IMPORT_C void Assign(const RBuf8& aRBuf);\r\n" + 
				"	IMPORT_C void Assign(TUint8 *aHeapCell,TInt aMaxLength);\r\n" + 
				"	IMPORT_C void Assign(TUint8 *aHeapCell,TInt aLength,TInt aMaxLength);\r\n" + 
				"	IMPORT_C void Assign(HBufC8* aHBuf);\r\n" + 
				"	IMPORT_C void Swap(RBuf8& aRBuf);\r\n" + 
				"	IMPORT_C TInt Create(TInt aMaxLength);\r\n" + 
				"	IMPORT_C void CreateL(TInt aMaxLength);\r\n" + 
				"	IMPORT_C TInt CreateMax(TInt aMaxLength);\r\n" + 
				"	IMPORT_C void CreateMaxL(TInt aMaxLength);\r\n" + 
				"	inline void CreateL(RReadStream &aStream,TInt aMaxLength);\r\n" + 
				"	IMPORT_C TInt Create(const TDesC8& aDes);\r\n" + 
				"	IMPORT_C void CreateL(const TDesC8& aDes);\r\n" + 
				"	IMPORT_C TInt Create(const TDesC8& aDes,TInt aMaxLength);\r\n" + 
				"	IMPORT_C void CreateL(const TDesC8& aDes,TInt aMaxLength);\r\n" + 
				"	IMPORT_C TInt ReAlloc(TInt aMaxLength);\r\n" + 
				"	IMPORT_C void ReAllocL(TInt aMaxLength);\r\n" + 
				"	IMPORT_C void Close();\r\n" + 
				"	IMPORT_C void CleanupClosePushL();\r\n" + 
				"\r\n" + 
				"protected:\r\n" + 
				"	IMPORT_C RBuf8(TInt aType,TInt aLength,TInt aMaxLength);\r\n" + 
				"	RBuf8(const RBuf8&); // Outlaw copy construction\r\n" + 
				"	union\r\n" + 
				"		{\r\n" + 
				"		TUint8* iEPtrType;		//Pointer to data used when RBuf is of EPtr type\r\n" + 
				"		HBufC8* iEBufCPtrType;	//Pointer to data used when RBuf is of EBufCPtr type\r\n" + 
				"		};\r\n" + 
				"	__DECLARE_TEST;\r\n" + 
				"	};\r\n" + 
				"\r\n" + 
				"#endif\r\n" + 
				"\r\n" + 
				"/**\r\n" + 
				"@publishedAll\r\n" + 
				"@released\r\n" + 
				"\r\n" + 
				"Value reference used in operator TLitC8::__TRefDesC8()\r\n" + 
				"\r\n" + 
				"@see TRefByValue\r\n" + 
				"*/\r\n" + 
				"typedef TRefByValue<const TDesC8> __TRefDesC8;\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"template <TInt S>\r\n" + 
				"class TLitC8\r\n" + 
				"/**\r\n" + 
				"@publishedAll\r\n" + 
				"@released\r\n" + 
				"\r\n" + 
				"Encapsulates literal text. \r\n" + 
				"\r\n" + 
				"This is always constructed using an _LIT8 macro.\r\n" + 
				"\r\n" + 
				"This class is build independent; i.e. an explicit 8-bit build variant\r\n" + 
				"is generated for both a non-Unicode build and a Unicode build.\r\n" + 
				"\r\n" + 
				"The class has no explicit constructors.\r\n" + 
				"\r\n" + 
				"@see _LIT8\r\n" + 
				"*/\r\n" + 
				"	{\r\n" + 
				"public:\r\n" + 
				"	inline const TDesC8* operator&() const;\r\n" + 
				"	inline operator const TDesC8&() const;\r\n" + 
				"	inline const TDesC8& operator()() const;\r\n" + 
				"	inline operator const __TRefDesC8() const;\r\n" + 
				"public:\r\n" + 
				"    /**\r\n" + 
				"    @internalComponent\r\n" + 
				"    */\r\n" + 
				"	TUint iTypeLength;\r\n" + 
				"	\r\n" + 
				"	/**\r\n" + 
				"    @internalComponent\r\n" + 
				"    */\r\n" + 
				"	TText8 iBuf[__Align8(S)];\r\n" + 
				"	};\r\n" + 
				"\r\n" + 
				"#endif\r\n" + 
				"";
		
		allowParseErrors = true;
		makeModel(text);
		IMMPView view = getView(mmpConfig);
		commitTest(view, text);
		
	}

	/**
	 * This bug is essentially caused by getting the wrong IDocument mapping
	 * for the main translation unit.  The full hairy text of the MMPs and includes
	 * is left here just for fun, I guess.
	 */

	public void testBug3674() {
		final String sdk_version = "#ifndef SDK_VERSION_MMP\r\n" + 
				"#define SDK_VERSION_MMP\r\n" + 
				"MACRO __SDK_VERSION_MMP__\r\n" + 
				"MACRO __S60V2__\r\n" + 
				"#define __S60V2__\r\n" + 
				"MACRO __S60V3__\r\n" + 
				"#define __S60V3__\r\n" + 
				"#define __SDK__ S60_3rd_MR\r\n" + 
				"#ifdef WINS\r\n" + 
				"#define DEFFILENAME(x) EXPORTUNFROZEN\r\n" + 
				"#define FACTORYDEFFILENAME(x) DEFFILE .\\ ## S60_3rd_MR ## \\ ## x ## -wins.DEF\r\n" + 
				"#else\r\n" + 
				"#define DEFFILENAME(x) EXPORTUNFROZEN\r\n" + 
				"#define FACTORYDEFFILENAME(x) DEFFILE .\\ ## S60_3rd_MR ## \\ ## x ## -thumb.DEF\r\n" + 
				"#endif\r\n" + 
				"\r\n" + 
				"#endif // SDK_VERSION_MMP\r\n" + 
				"";

		String context = "#ifndef __CONTEXT_MMP_INCLUDED__\r\n" + 
				"#define __CONTEXT_MMP_INCLUDED__  1\r\n" + 
				"MACRO __CONTEXT_MMP__\r\n" + 
				"#ifdef __S60V3__\r\n" + 
				"SRCDBG\r\n" + 
				"CAPABILITY ReadDeviceData ReadUserData WriteUserData LocalServices Location NetworkServices UserEnvironment WriteDeviceData SwEvent\r\n" + 
				"#endif\r\n" + 
				"\r\n" + 
				"#endif\r\n" + 
				"";
		
		String context_uids = "/*\r\n" + 
				" * UIDs for Context components\r\n" + 
				" *\r\n" + 
				" * *app.h files should include this, instead of\r\n" + 
				" * defining the value\r\n" + 
				" * NOTE: these values are automatically updated\r\n" + 
				" *	 in the pkg files by update_sis_versions.pl\r\n" + 
				" *	 but have to manually updated in *_common.mmp\r\n" + 
				" *\r\n" + 
				"\r\n" + 
				"Jaiku UIDs:\r\n" + 
				"0x200084B3 - 0x200084C6\r\n" + 
				"0x200089E1 - 0x200089F4\r\n" + 
				"\r\n" + 
				" */\r\n" + 
				"#define CONTEXT_UID_CONTEXT_LOG 0x200084B3\r\n" + 
				"#define CONTEXT_UID_CL_AUTOSTART 0x200084B4\r\n" + 
				"#define CONTEXT_UID_STARTER 0x200084B5\r\n" + 
				"\r\n" + 
				"#define CONTEXT_UID_CONTEXTSERVER 0x200084B6\r\n" + 
				"#define CONTEXT_UID_CONTEXTCLIENT 0x200084B7\r\n" + 
				"#define CONTEXT_UID_CONTEXTCOMMON 0x200084B8\r\n" + 
				"\r\n" + 
				"#define CONTEXT_UID_CONTEXTNOTIFY 0x200084B9\r\n" + 
				"#define CONTEXT_UID_CONTEXTNOTIFYCLIENT 0x200084BA\r\n" + 
				"\r\n" + 
				"#define CONTEXT_UID_CONTEXTCONTACTS 0x200084BB\r\n" + 
				"#define CONTEXT_UID_BLACKBOARDDATA	0x200084BC\r\n" + 
				"#define CONTEXT_UID_BLACKBOARDSERVER	0x200084BD\r\n" + 
				"\r\n" + 
				"#define CONTEXT_UID_BLACKBOARDCLIENT	0x200084BE\r\n" + 
				"#define CONTEXT_UID_BLACKBOARDFACTORY	0x200084BF\r\n" + 
				"#define CONTEXT_UID_CONTEXTSENSORS	0x200084C1\r\n" + 
				"#define CONTEXT_UID_SENSORDATAFACTORY	0x200084C2\r\n" + 
				"#define CONTEXT_UID_CONTEXTNETWORK	0x200084C3\r\n" + 
				"#define CONTEXT_UID_CONTEXTCOMMON2	0x200084C4\r\n" + 
				"#define CONTEXT_UID_CONTEXTMEDIA	0x200084C5\r\n" + 
				"\r\n" + 
				"#define CONTEXT_UID_CONTEXTMEDIAFACTORY 0x200084C6\r\n" + 
				"#define CONTEXT_UID_CONTEXTSENSORDATA   0x200089E1\r\n" + 
				"#define CONTEXT_UID_CONTEXTUI		0x200089E2\r\n" + 
				"#define CONTEXT_UID_CONTEXTMEDIADATA	0x200089E3\r\n" + 
				"\r\n" + 
				"#define CONTEXT_UID_CONTEXTMEDIAUI	0x200089E4\r\n" + 
				"\r\n" + 
				"#define CONTEXT_UID_SETTINGS		0x200089E5\r\n" + 
				"#define CONTEXT_UID_SHUTTER		0x200089E6\r\n" + 
				"#define CONTEXT_UID_EXPAT		0x200089E7\r\n" + 
				"#define CONTEXT_UID_CONTEXTCONTACTSUI	0x200089E8\r\n" + 
				"#define CONTEXT_UID_CONTEXTCOMMSENSORS  0x200089E9\r\n" + 
				"#define CONTEXT_UID_CONTEXTSTARTER	0x200089EA\r\n" + 
				"#define CONTEXT_UID_CONTEXTWELCOME	0x200089EB\r\n" + 
				"#define CONTEXT_UID_JAIKUUIKIT      	0x200089EC\r\n" + 
				"#define CONTEXT_UID_JAIKUTOOL		0x200089ED\r\n" + 
				"\r\n" + 
				"#define CONTEXT_UID_SYMBIANOSUNIT	0x200089EE\r\n" + 
				"";
		String bb_common = "/* \r\n" + 
				"    Copyright (C) 0000  Test Code Comments\r\n" + 
				"\r\n" + 
				"    blah blah blah bla blah blah blah bla blah blah blah bla\r\n" + 
				"    blah blah blah bla blah blah blah bla blah blah blah bla\r\n" + 
				"    blah blah blah bla blah blah blah bla blah blah blah bla\r\n" + 
				"    blah blah blah bla blah blah blah bla blah blah blah bla.\r\n" + 
				"\r\n" + 
				"    blah blah blah bla blah blah blah bla blah blah blah bla\r\n" + 
				"    blah blah blah bla blah blah blah bla blah blah blah bla\r\n" + 
				"    blah blah blah bla blah blah blah bla blah blah blah blah.\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"*/\r\n" + 
				"\r\n" + 
				"#include \"..\\..\\context_uids.rh\"\r\n" + 
				"#include <sdk_version.mmp>\r\n" + 
				"#include \"..\\..\\context.mmp\"\r\n" + 
				"\r\n" + 
				"TARGET            BlackBoardData.dll\r\n" + 
				"TARGETTYPE        dll\r\n" + 
				"UID               0x1000008d CONTEXT_UID_BLACKBOARDDATA\r\n" + 
				"START ARMI\r\n" + 
				"TARGETPATH	  \\system\\libs\r\n" + 
				"END\r\n" + 
				"START THUMB\r\n" + 
				"TARGETPATH	  \\system\\libs\r\n" + 
				"END\r\n" + 
				"\r\n" + 
				"DEFFILENAME(BlackBoardData)\r\n" + 
				"\r\n" + 
				"SOURCEPATH        ..\\src\r\n" + 
				"SOURCE		concretedata.cpp\r\n" + 
				"SOURCE		bbdata.cpp\r\n" + 
				"SOURCE		bbutil.cpp\r\n" + 
				"SOURCE		bbxml.cpp\r\n" + 
				"SOURCE		bbdll.cpp\r\n" + 
				"SOURCE		bblist.cpp\r\n" + 
				"SOURCE		bberrorinfo.cpp\r\n" + 
				"SOURCE		bb_incoming.cpp\r\n" + 
				"SOURCE		bbtuple.cpp\r\n" + 
				"SOURCE		csd_md5hash.cpp\r\n" + 
				"SOURCE		csd_uuid.cpp\r\n" + 
				"\r\n" + 
				"USERINCLUDE ..\\inc ..\\..\\ContextCompat\\inc\r\n" + 
				"USERINCLUDE ..\\..\\contextcommon\\inc ..\\..\\expat\\lib ..\\..\r\n" + 
				"\r\n" + 
				"SYSTEMINCLUDE     \\epoc32\\include \\epoc32\\include\\libc\r\n" + 
				"\r\n" + 
				"LIBRARY           euser.lib contextcommon.lib estor.lib\r\n" + 
				"LIBRARY		  contextexpat.lib bafl.lib\r\n" + 
				"LIBRARY		  efsrv.lib\r\n" + 
				"";
		
		String mmp = "#include <sdk_version.mmp>\r\n" + 
				"#include \"..\\..\\context.mmp\"\r\n" + 
				"DEFFILENAME(BlackBoardData)\r\n" + 
				"#include \"..\\common\\BlackBoard_common.mmp\"\r\n" + 
				"";

		parserConfig.getFilesystem().put("sdk_version.mmp", sdk_version);
		parserConfig.getFilesystem().put("context.mmp", context);
		parserConfig.getFilesystem().put("Blackboard_common.mmp", bb_common);
		parserConfig.getFilesystem().put("context_uids.rh", context_uids);
		makeModel(mmp);
		IMMPView view = getView(mmpConfig);
		checkNoProblems(view);
		
		commitTest(view, mmp);
		
	}

	public void testMacroMungingBug3700() {
		String header = "#define MW_LAYER_SYSTEMINCLUDE SYSTEMINCLUDE \\\r\n" +
				" \\epoc32\\include \\\r\n" + 
				" \\epoc32\\include\\oem \\\r\n" + 
				" \\epoc32\\include\\middleware \\\r\n" + 
				" \\epoc32\\include\\domain\\middleware \\\r\n" + 
				" \\epoc32\\include\\osextensions \\\r\n" + 
				" \\epoc32\\include\\domain\\osextensions\r\n";
		
		String text = "#include <data_caging_paths.hrh>\r\n" + 
				"USERINCLUDE       ..\\FeedsServer\\Client\\inc\r\n" + 
				"USERINCLUDE       ..\\FeedsServer\\Common\\inc\r\n" + 
				"USERINCLUDE       ..\\..\\inc\r\n" + 
				"\r\n" + 
				"MW_LAYER_SYSTEMINCLUDE\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"// Libraries\r\n" + 
				"LIBRARY           euser.lib\r\n" + 
				"DEBUGLIBRARY           flogger.lib\r\n" + 
				"LIBRARY           efsrv.lib\r\n" + 
				"LIBRARY           estor.lib\r\n" + 
				"LIBRARY           esock.lib\r\n" + 
				"";

		String text1 = "#include <data_caging_paths.hrh>\r\n" + 
				"USERINCLUDE       ..\\FeedsServer\\Client\\inc\r\n" + 
				"USERINCLUDE       ..\\FeedsServer\\Common\\inc\r\n" + 
				"USERINCLUDE       ..\\..\\inc\r\n" + 
				"\r\n" + 
				"#if 0\r\n" + 
				"MW_LAYER_SYSTEMINCLUDE\r\n" + 
				"#endif\r\n" + 
				"SYSTEMINCLUDE \\epoc32\\include\\oem \\epoc32\\include \\epoc32\\include\\middleware \\epoc32\\include\\domain\\middleware \\epoc32\\include\\osextensions \\epoc32\\include\\domain\\osextensions\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"// Libraries\r\n" + 
				"LIBRARY           euser.lib\r\n" + 
				"DEBUGLIBRARY           flogger.lib\r\n" + 
				"LIBRARY           efsrv.lib\r\n" + 
				"LIBRARY           estor.lib\r\n" + 
				"LIBRARY           esock.lib\r\n" + 
				"";

		String text2 = "#include <data_caging_paths.hrh>\r\n" + 
				"USERINCLUDE       ..\\FeedsServer\\Client\\inc\r\n" + 
				"USERINCLUDE       ..\\FeedsServer\\Common\\inc\r\n" + 
				"USERINCLUDE       ..\\..\\inc\r\n" + 
				"\r\n" + 
				"#if 0\r\n" + 
				"MW_LAYER_SYSTEMINCLUDE\r\n" + 
				"#endif\r\n" + 
				"SYSTEMINCLUDE \\epoc32\\include\\oem \\epoc32\\include\\middleware \\epoc32\\include\\domain\\middleware \\epoc32\\include\\osextensions \\epoc32\\include\\domain\\osextensions\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"// Libraries\r\n" + 
				"LIBRARY           euser.lib\r\n" + 
				"DEBUGLIBRARY           flogger.lib\r\n" + 
				"LIBRARY           efsrv.lib\r\n" + 
				"LIBRARY           estor.lib\r\n" + 
				"LIBRARY           esock.lib\r\n" + 
				"";
		
		String text3 = "#include <data_caging_paths.hrh>\r\n" + 
		"USERINCLUDE       ..\\FeedsServer\\Client\\inc\r\n" + 
		"USERINCLUDE       ..\\FeedsServer\\Common\\inc\r\n" + 
		"USERINCLUDE       ..\\..\\inc\r\n" + 
		"\r\n" + 
		"#if 0\r\n"+
		"MW_LAYER_SYSTEMINCLUDE\r\n" + 
		"#endif\r\n"+
		"SYSTEMINCLUDE \\epoc32\\include \\epoc32\\include\\oem \\epoc32\\include\\middleware \\epoc32\\include\\osextensions \\epoc32\\include\\domain\\osextensions\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"// Libraries\r\n" + 
		"LIBRARY           euser.lib\r\n" + 
		"DEBUGLIBRARY           flogger.lib\r\n" + 
		"LIBRARY           efsrv.lib\r\n" + 
		"LIBRARY           estor.lib\r\n" +
		"LIBRARY           esock.lib\r\n" +
		"";
		String text4 = "#include <data_caging_paths.hrh>\r\n" + 
		"USERINCLUDE       ..\\FeedsServer\\Client\\inc\r\n" + 
		"USERINCLUDE       ..\\FeedsServer\\Common\\inc\r\n" + 
		"USERINCLUDE       ..\\..\\inc\r\n" + 
		"\r\n" + 
		"#if 0\r\n"+
		"MW_LAYER_SYSTEMINCLUDE\r\n" + 
		"#endif\r\n"+
		"SYSTEMINCLUDE \\epoc32\\include \\epoc32\\include\\oem \\epoc32\\include\\middleware \\epoc32\\include\\domain\\middleware \\epoc32\\include\\osextensions\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"// Libraries\r\n" + 
		"LIBRARY           euser.lib\r\n" + 
		"DEBUGLIBRARY           flogger.lib\r\n" + 
		"LIBRARY           efsrv.lib\r\n" + 
		"LIBRARY           estor.lib\r\n" +
		"LIBRARY           esock.lib\r\n" +
		"";
		String text5 = "#include <data_caging_paths.hrh>\r\n" + 
		"USERINCLUDE       ..\\FeedsServer\\Client\\inc\r\n" + 
		"USERINCLUDE       ..\\FeedsServer\\Common\\inc\r\n" + 
		"USERINCLUDE       ..\\..\\inc\r\n" + 
		"\r\n" + 
		"#if 0\r\n"+
		"MW_LAYER_SYSTEMINCLUDE\r\n" + 
		"#endif\r\n"+
		"SYSTEMINCLUDE \\epoc32\\include \\epoc32\\foo \\epoc32\\include\\middleware \\epoc32\\include\\domain\\middleware \\epoc32\\include\\osextensions \\epoc32\\include\\domain\\osextensions\r\n" + 
		"\r\n" + 
		"\r\n" + 
		"// Libraries\r\n" + 
		"LIBRARY           euser.lib\r\n" + 
		"DEBUGLIBRARY           flogger.lib\r\n" + 
		"LIBRARY           efsrv.lib\r\n" + 
		"LIBRARY           estor.lib\r\n" +
		"LIBRARY           esock.lib\r\n" +
		"";

		parserConfig.getFilesystem().put("data_caging_paths.hrh", 
				header);

		makeModel(text);
		IMMPView view = getView(mmpConfig);
		commitTest(view, text);

		// move entry
		makeModel(text);
		view = getView(mmpConfig);
		IPath p = view.getSystemIncludes().remove(1);
		view.getSystemIncludes().add(0, p);
		commitTest(view, text1);

		// remove from start
		makeModel(text);
		view = getView(mmpConfig);
		view.getSystemIncludes().remove(0);
		commitTest(view, text2);

		// remove from middle
		makeModel(text);
		view = getView(mmpConfig);
		
		view.getSystemIncludes().remove(3);
		commitTest(view, text3);

		// remove from end
		makeModel(text);
		view = getView(mmpConfig);
		
		view.getSystemIncludes().remove(5);
		commitTest(view, text4);

		// change in middle
		makeModel(text);
		view = getView(mmpConfig);
		
		view.getSystemIncludes().set(1, new Path("/epoc32/foo"));
		commitTest(view, text5);
		
	}

	public void testMacroMungingBug3700_2() {
		String header = "#define MY_SYSTEM_INCLUDE \\epoc32\\foo\n";
		
		String text = "#include <data_caging_paths.hrh>\r\n" + 
				"SYSTEMINCLUDE \\epoc32\\include MY_SYSTEM_INCLUDE ..\\src\r\n" + 
				"";

		// can't avoid the #if 0 yet
		
		String text2 = "#include <data_caging_paths.hrh>\r\n" + 
		"#if 0\r\n"+
		"SYSTEMINCLUDE \\epoc32\\include MY_SYSTEM_INCLUDE ..\\src\r\n" +
		"#endif\r\n"+
		"SYSTEMINCLUDE \\epoc32\\include MY_SYSTEM_INCLUDE ..\\src ..\\inc\r\n" + 
		"";
		
		String text3 = "#include <data_caging_paths.hrh>\r\n" + 
		"#if 0\r\n"+
		"SYSTEMINCLUDE \\epoc32\\include MY_SYSTEM_INCLUDE ..\\src\r\n" +
		"#endif\r\n"+
		"SYSTEMINCLUDE MY_SYSTEM_INCLUDE ..\\src\r\n" + 
		"";
		String text4 = "#include <data_caging_paths.hrh>\r\n" +
		"#if 0\r\n"+
		"SYSTEMINCLUDE \\epoc32\\include MY_SYSTEM_INCLUDE ..\\src\r\n" +
		"#endif\r\n"+
		"SYSTEMINCLUDE \\epoc32\\include \\epoc32\\arglebargle ..\\src\r\n" + 
		"";

		parserConfig.getFilesystem().put("data_caging_paths.hrh", 
				header);

		makeModel(text);
		IMMPView view = getView(mmpConfig);
		commitTest(view, text);
		
		// add to end
		view.getSystemIncludes().add(new Path("inc"));
		commitTest(view, text2);

		// remove from start
		makeModel(text);
		view = getView(mmpConfig);
		
		view.getSystemIncludes().remove(0);
		commitTest(view, text3);

		// change
		makeModel(text);
		view = getView(mmpConfig);
		
		view.getSystemIncludes().set(1, new Path("/epoc32/arglebargle"));
		commitTest(view, text4);
		
	}

	public void testMacroMungingBug3700_3() {
		String header = "#define MY_SYSTEM_INCLUDE SYSTEMINCLUDE \\epoc32\\foo\n";
		
		String text = "#include <data_caging_paths.hrh>\r\n" + 
				"MY_SYSTEM_INCLUDE ..\\src\r\n" + 
				"";

		// can't avoid the #if 0 yet
		
		// don't try to preserve MY_SYSTEM_INCLUDE yet... too weird when it spans one child and part of parent
		String text2 = "#include <data_caging_paths.hrh>\r\n" + 
		"#if 0\r\n"+
		"MY_SYSTEM_INCLUDE ..\\src\r\n" + 
		"#endif\r\n"+
		"SYSTEMINCLUDE \\epoc32\\foo ..\\src ..\\inc\r\n" + 
		"";
		
		String text3 = "#include <data_caging_paths.hrh>\r\n" + 
		"#if 0\r\n"+
		"MY_SYSTEM_INCLUDE ..\\src\r\n" + 
		"#endif\r\n"+
		"SYSTEMINCLUDE ..\\src\r\n" + 
		"";
		String text4 = "#include <data_caging_paths.hrh>\r\n" +
		"#if 0\r\n"+
		"MY_SYSTEM_INCLUDE ..\\src\r\n" + 
		"#endif\r\n"+
		"SYSTEMINCLUDE \\epoc32\\foo \\epoc32\\arglebargle\r\n" + 
		"";

		parserConfig.getFilesystem().put("data_caging_paths.hrh", 
				header);

		makeModel(text);
		IMMPView view = getView(mmpConfig);
		commitTest(view, text);
		
		// add to end
		view.getSystemIncludes().add(new Path("inc"));
		commitTest(view, text2);

		// remove from start
		makeModel(text);
		view = getView(mmpConfig);
		
		view.getSystemIncludes().remove(0);
		commitTest(view, text3);

		// change
		makeModel(text);
		view = getView(mmpConfig);
		
		view.getSystemIncludes().set(1, new Path("/epoc32/arglebargle"));
		commitTest(view, text4);
		
	}

	public void testBug3765() {
		String text = 
			"UID 0x2 0x3\n"
			;
		
		makeModel(text);
		IMMPView view = getView(mmpConfig);
		checkNoProblems(view);
		
		view.setUid2(null);
		
		String text2= 
			"UID 0 0x3\n"
			;
		commitTest(view, text2);
	}
	
	public void testReorderInList() {
		String text = 
			"SOURCE a.cpp b.cpp c.cpp\n"
			;
		
		makeModel(text);
		IMMPView view = getView(mmpConfig);
		checkNoProblems(view);
		
		IPath first = view.getSources().remove(0);
		view.getSources().add(first);
		
		String text2= 
			"SOURCE b.cpp c.cpp a.cpp\n";
			;
		commitTest(view, text2);
		
	}

	public void testBug4822() {
			String text = 
				"#define concat2(a,b) concatx(a,b,,,,,,)\r\n" + 
				"#define concat3(a,b,c) concatx(a,b,c,,,,,)\r\n" + 
				"#define concat4(a,b,c,d) concatx(a,b,c,d,,,,)\r\n" + 
				"#define concat5(a,b,c,d,e) concatx(a,b,c,d,e,,,)\r\n" + 
				"#define concat6(a,b,c,d,e,f) concatx(a,b,c,d,e,f,,)\r\n" + 
				"#define concat7(a,b,c,d,e,f,g) concatx(a,b,c,d,e,f,g,)\r\n" + 
				"#define concat8(a,b,c,d,e,f,g,h) concatx(a,b,c,d,e,f,g,h)\r\n" + 
				"#define concatx(x1,x2,x3,x4,x5,x6,x7,x8) x1 ## x2 ## x3 ## x4 ## x5 ## x6 ## x7 ## x8 \r\n" + 
				"#define AbsPathUnderCedarGenericBase(x)         \\src\\cedar\\generic\\base ## x\r\n" + 
				"SYSTEMINCLUDE AbsPathUnderCedarGenericBase(\\e32\\drivers\\pbus\\mmc\\sdcard\\sdcard3c)\n";

			makeModel(text);
			IMMPView view = getView(mmpConfig);
			checkNoProblems(view);
			
			IPath path = view.getSystemIncludes().get(0);
			assertEquals(new Path("/src/cedar/generic/base/e32/drivers/pbus/mmc/sdcard/sdcard3c"), 
					path);
			
			
	}
}
