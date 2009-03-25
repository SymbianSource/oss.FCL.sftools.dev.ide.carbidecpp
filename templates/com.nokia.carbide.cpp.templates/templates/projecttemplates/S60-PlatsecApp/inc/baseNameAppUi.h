/*
============================================================================
 Name		: $(baseName)AppUi.h
 Author	  : $(author)
 Copyright   : $(copyright)
 Description : Declares UI class for application.
============================================================================
*/

#ifndef __$(baseNameUpper)APPUI_h__
#define __$(baseNameUpper)APPUI_h__

// INCLUDES
#include <aknappui.h>


// FORWARD DECLARATIONS
class C$(baseName)AppView;


// CLASS DECLARATION
/**
* C$(baseName)AppUi application UI class.
* Interacts with the user through the UI and request message processing
* from the handler class
*/
class C$(baseName)AppUi : public CAknAppUi
	{
	public: // Constructors and destructor

		/**
		* ConstructL.
		* 2nd phase constructor.
		*/
		void ConstructL();

		/**
		* C$(baseName)AppUi.
		* C++ default constructor. This needs to be public due to
		* the way the framework constructs the AppUi
		*/
		C$(baseName)AppUi();

		/**
		* ~C$(baseName)AppUi.
		* Virtual Destructor.
		*/
		virtual ~C$(baseName)AppUi();

	private:  // Functions from base classes

		/**
		* From CEikAppUi, HandleCommandL.
		* Takes care of command handling.
		* @param aCommand Command to be handled.
		*/
		void HandleCommandL( TInt aCommand );

		/**
		*  HandleStatusPaneSizeChange.
		*  Called by the framework when the application status pane
 		*  size is changed.
		*/
		void HandleStatusPaneSizeChange();
		
		/**
		*  From CCoeAppUi, HelpContextL.
		*  Provides help context for the application.
 		*  size is changed.
		*/
	 	CArrayFix<TCoeHelpContext>* HelpContextL() const;

	private: // Data

		/**
		* The application view
		* Owned by C$(baseName)AppUi
		*/
		C$(baseName)AppView* iAppView;
		
		
	};

#endif // __$(baseNameUpper)APPUI_h__

// End of File
