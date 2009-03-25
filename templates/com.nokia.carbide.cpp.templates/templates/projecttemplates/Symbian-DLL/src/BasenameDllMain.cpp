/*
============================================================================
 Name		: $(baseName)Dll.cpp 
 Author	  : $(author)
 Copyright   : $(copyright)
 Description : $(baseName)Dll.cpp - main DLL source
============================================================================
*/

//  Include Files  

#include <e32std.h>		 // GLDEF_C
#include "$(baseName).pan"		// panic codes


//  Global Functions

GLDEF_C void Panic(T$(baseName)Panic aPanic)
// Panics the thread with given panic code
	{
	User::Panic(_L("$(baseName)"), aPanic);
	}


//  Exported Functions

#ifndef EKA2 // for EKA1 only
EXPORT_C TInt E32Dll(TDllReason /*aReason*/)
// Called when the DLL is loaded and unloaded. Note: have to define
// epoccalldllentrypoints in MMP file to get this called in THUMB.
	{
	return KErrNone;
	}
#endif


