/*
============================================================================
 Name		: C$(baseName)Globals from $(baseName)Globals.h
 Author	  : $(author)
 Version	 :
 Copyright   : $(copyright)
 Description : Declares main application globals class.
============================================================================
*/

#ifndef $(baseNameUpper)GLOBALS_H
#define $(baseNameUpper)GLOBALS_H

/**
Identifies the application’s UID3.

@since UIQ 3.0
*/
const TUid KUid$(baseName)App = {$(uid3)};

/**
Identifies the $(baseName) application’s view.
Each view need an unique UID in the application.

@since UIQ 3.0
*/
const TUid KUid$(baseName)View = {0x00000001};

#endif // $(baseNameUpper)GLOBALS_H

