/*
============================================================================
 Name		: $(baseName)Document.h
 Author	  : $(author)
 Copyright   : $(copyright)
 Description : Declares document class for application.
============================================================================
*/

#ifndef __$(baseNameUpper)DOCUMENT_h__
#define __$(baseNameUpper)DOCUMENT_h__

// INCLUDES
#include <akndoc.h>

// FORWARD DECLARATIONS
class C$(baseName)AppUi;
class CEikApplication;


// CLASS DECLARATION

/**
* C$(baseName)Document application class.
* An instance of class C$(baseName)Document is the Document part of the
* AVKON application framework for the $(baseName) example application.
*/
class C$(baseName)Document : public CAknDocument
	{
	public: // Constructors and destructor

		/**
		* NewL.
		* Two-phased constructor.
		* Construct a C$(baseName)Document for the AVKON application aApp
		* using two phase construction, and return a pointer
		* to the created object.
		* @param aApp Application creating this document.
		* @return A pointer to the created instance of C$(baseName)Document.
		*/
		static C$(baseName)Document* NewL( CEikApplication& aApp );

		/**
		* NewLC.
		* Two-phased constructor.
		* Construct a C$(baseName)Document for the AVKON application aApp
		* using two phase construction, and return a pointer
		* to the created object.
		* @param aApp Application creating this document.
		* @return A pointer to the created instance of C$(baseName)Document.
		*/
		static C$(baseName)Document* NewLC( CEikApplication& aApp );

		/**
		* ~C$(baseName)Document
		* Virtual Destructor.
		*/
		virtual ~C$(baseName)Document();

	public: // Functions from base classes

		/**
		* CreateAppUiL
		* From CEikDocument, CreateAppUiL.
		* Create a C$(baseName)AppUi object and return a pointer to it.
		* The object returned is owned by the Uikon framework.
		* @return Pointer to created instance of AppUi.
		*/
		CEikAppUi* CreateAppUiL();

	private: // Constructors

		/**
		* ConstructL
		* 2nd phase constructor.
		*/
		void ConstructL();

		/**
		* C$(baseName)Document.
		* C++ default constructor.
		* @param aApp Application creating this document.
		*/
		C$(baseName)Document( CEikApplication& aApp );

	};

#endif // __$(baseNameUpper)DOCUMENT_h__

// End of File
