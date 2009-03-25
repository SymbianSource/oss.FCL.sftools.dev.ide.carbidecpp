/*
============================================================================
 Name		: $(baseName).pan
 Author	  : $(author)
 Copyright   : $(copyright)
 Description : Panic codes
============================================================================
*/

#ifndef __$(baseNameUpper)_PAN__
#define __$(baseNameUpper)_PAN__


//  Data Types

enum T$(baseName)Panic
	{
	E$(baseName)NullPointer
	};


//  Function Prototypes

GLREF_C void Panic(T$(baseName)Panic aPanic);


#endif  // __$(baseNameUpper)_PAN__


