/*
============================================================================
 Name		: $(baseName).h
 Author	  : $(author)
 Copyright   : $(copyright)
 Description : $(baseName).h - C$(baseName) class header
============================================================================
*/

// This file defines the API for $(baseName).dll

#ifndef __$(baseNameUpper)_H__
#define __$(baseNameUpper)_H__


//  Include Files

#include <e32base.h>	// CBase
#include <e32std.h>	 // TBuf


//  Constants

const TInt K$(baseName)BufferLength = 15;
typedef TBuf<K$(baseName)BufferLength> T$(baseName)ExampleString;


//  Class Definitions

class C$(baseName) : public CBase
	{
	public:	 // new functions
		IMPORT_C static C$(baseName)* NewL();
		IMPORT_C static C$(baseName)* NewLC();
		IMPORT_C ~C$(baseName)();

	public:	 // new functions, example API
		IMPORT_C TVersion Version() const;
		IMPORT_C void ExampleFuncAddCharL(const TChar& aChar);
		IMPORT_C void ExampleFuncRemoveLast();
		IMPORT_C const TPtrC ExampleFuncString() const;

	private:	// new functions
		C$(baseName)();
		void ConstructL();

	private:	// data
		T$(baseName)ExampleString* iString;
	};


#endif  // __$(baseNameUpper)_H__


