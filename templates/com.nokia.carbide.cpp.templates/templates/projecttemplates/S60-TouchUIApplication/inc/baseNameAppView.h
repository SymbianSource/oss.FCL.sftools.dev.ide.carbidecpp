/*
============================================================================
 Name		: $(baseName)AppView.h
 Author	  : $(author)
 Copyright   : $(copyright)
 Description : Declares view class for application.
============================================================================
*/

#ifndef __$(baseNameUpper)APPVIEW_h__
#define __$(baseNameUpper)APPVIEW_h__

// INCLUDES
#include <coecntrl.h>

// CLASS DECLARATION
class C$(baseName)AppView : public CCoeControl
	{
	public: // New methods

		/**
		* NewL.
		* Two-phased constructor.
		* Create a C$(baseName)AppView object, which will draw itself to aRect.
		* @param aRect The rectangle this view will be drawn to.
		* @return a pointer to the created instance of C$(baseName)AppView.
		*/
		static C$(baseName)AppView* NewL( const TRect& aRect );

		/**
		* NewLC.
		* Two-phased constructor.
		* Create a C$(baseName)AppView object, which will draw itself
		* to aRect.
		* @param aRect Rectangle this view will be drawn to.
		* @return A pointer to the created instance of C$(baseName)AppView.
		*/
		static C$(baseName)AppView* NewLC( const TRect& aRect );

		/**
		* ~C$(baseName)AppView
		* Virtual Destructor.
		*/
		virtual ~C$(baseName)AppView();

	public:  // Functions from base classes

		/**
		* From CCoeControl, Draw
		* Draw this C$(baseName)AppView to the screen.
		* @param aRect the rectangle of this view that needs updating
		*/
		void Draw( const TRect& aRect ) const;

		/**
		* From CoeControl, SizeChanged.
		* Called by framework when the view size is changed.
		*/
		virtual void SizeChanged();

		/**
		* From CoeControl, HandlePointerEventL.
		* Called by framework when a pointer touch event occurs.
		* Note: although this method is compatible with earlier SDKs, 
		* it will not be called in SDKs without Touch support.
		* @param aPointerEvent the information about this event
		*/
		virtual void HandlePointerEventL( const TPointerEvent& aPointerEvent );

	private: // Constructors

		/**
		* ConstructL
		* 2nd phase constructor.
		* Perform the second phase construction of a
		* C$(baseName)AppView object.
		* @param aRect The rectangle this view will be drawn to.
		*/
		void ConstructL(const TRect& aRect);

		/**
		* C$(baseName)AppView.
		* C++ default constructor.
		*/
		C$(baseName)AppView();

	};

#endif // __$(baseNameUpper)APPVIEW_h__

// End of File
