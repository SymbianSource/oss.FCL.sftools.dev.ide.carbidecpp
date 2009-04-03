/*
============================================================================
 Name		: $(baseName)Application.h
 Author	  : $(author)
 Copyright   : $(copyright)
 Description : Declares main application class.
============================================================================
*/

#ifndef __$(baseNameUpper)APPLICATION_H__
#define __$(baseNameUpper)APPLICATION_H__

// INCLUDES
#include <aknapp.h>
#include "$(baseName).hrh"


// UID for the application;
// this should correspond to the uid defined in the mmp file
const TUid KUid$(baseName)App = { _UID3 };


// CLASS DECLARATION

/**
* C$(baseName)Application application class.
* Provides factory to create concrete document object.
* An instance of C$(baseName)Application is the application part of the
* AVKON application framework for the $(baseName) example application.
*/
class C$(baseName)Application : public CAknApplication
	{
	public: // Functions from base classes

		/**
		* From CApaApplication, AppDllUid.
		* @return Application's UID (KUid$(baseName)App).
		*/
		TUid AppDllUid() const;

	protected: // Functions from base classes

		/**
		* From CApaApplication, CreateDocumentL.
		* Creates C$(baseName)Document document object. The returned
		* pointer in not owned by the C$(baseName)Application object.
		* @return A pointer to the created document object.
		*/
		CApaDocument* CreateDocumentL();
	};

#endif // __$(baseNameUpper)APPLICATION_H__

// End of File
