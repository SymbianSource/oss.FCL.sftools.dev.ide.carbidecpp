/*
============================================================================
 Name		: $(baseName).pan
 Author	  : $(author)
 Copyright   : $(copyright)
 Description : This file contains panic codes.
============================================================================
*/

#ifndef __$(baseNameUpper)_PAN__
#define __$(baseNameUpper)_PAN__

/** $(baseName) application panic codes */
enum T$(baseName)Panics
	{
	E$(baseName)Ui = 1
	// add further panics here
	};

inline void Panic(T$(baseName)Panics aReason)
	{
	_LIT(applicationName,"$(baseName)");
	User::Panic(applicationName, aReason);
	}

#endif // __$(baseNameUpper)_PAN__
