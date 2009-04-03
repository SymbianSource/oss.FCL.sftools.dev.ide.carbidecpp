/*
============================================================================
 Name		: $(baseName).cpp
 Author	  : $(author)
 Copyright   : $(copyright)
 Description : C$(baseName) DLL source
============================================================================
*/

//  Include Files  

#include "$(baseName).h"	// C$(baseName)
#include "$(baseName).pan"	  	// panic codes


//  Member Functions

EXPORT_C C$(baseName)* C$(baseName)::NewLC()
	{
	C$(baseName)* self = new (ELeave) C$(baseName);
	CleanupStack::PushL(self);
	self->ConstructL();
	return self;
	}


EXPORT_C C$(baseName)* C$(baseName)::NewL()
	{
	C$(baseName)* self = C$(baseName)::NewLC();
	CleanupStack::Pop(self);
	return self;
	}


C$(baseName)::C$(baseName)()
// note, CBase initialises all member variables to zero
	{
	}


void C$(baseName)::ConstructL()
	{
	// second phase constructor, anything that may leave must be constructed here
	iString = new (ELeave) T$(baseName)ExampleString;
	}


EXPORT_C C$(baseName)::~C$(baseName)()
	{
	delete iString;
	}


EXPORT_C TVersion C$(baseName)::Version() const
	{
	// Version number of example API
	const TInt KMajor = 1;
	const TInt KMinor = 0;
	const TInt KBuild = 1;
	return TVersion(KMajor, KMinor, KBuild);
	}


EXPORT_C void C$(baseName)::ExampleFuncAddCharL(const TChar& aChar)
	{
	__ASSERT_ALWAYS(iString != NULL, Panic(E$(baseName)NullPointer));

	if (iString->Length() >= K$(baseName)BufferLength)
		{
		User::Leave(KErrTooBig);
		}

	iString->Append(aChar);
	}


EXPORT_C void C$(baseName)::ExampleFuncRemoveLast()
	{
	__ASSERT_ALWAYS(iString != NULL, Panic(E$(baseName)NullPointer));

	if (iString->Length() > 0)
		{
		iString->SetLength(iString->Length() - 1);
		}
	}


EXPORT_C const TPtrC C$(baseName)::ExampleFuncString() const
	{
	__ASSERT_ALWAYS(iString != NULL, Panic(E$(baseName)NullPointer));
	return *iString;
	}



