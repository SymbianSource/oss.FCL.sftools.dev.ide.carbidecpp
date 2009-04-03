/*
============================================================================
 Name		: C$(baseName)Application from $(baseName)Application.h
 Author	  : $(author)
 Version	 :
 Copyright   : $(copyright)
 Description : Declares main application class.
============================================================================
*/

#ifndef $(baseNameUpper)APPAPPLICATION_H
#define $(baseNameUpper)APPAPPLICATION_H

#include <QikApplication.h>

/**
This class is the entry point to the application.

@since UIQ 3.0
*/
class C$(baseName)Application : public CQikApplication
	{
private:
	CApaDocument* CreateDocumentL();
	TUid AppDllUid() const;
	CApaApplication* NewApplication();
	};

#endif // $(baseNameUpper)APPAPPLICATION_H
