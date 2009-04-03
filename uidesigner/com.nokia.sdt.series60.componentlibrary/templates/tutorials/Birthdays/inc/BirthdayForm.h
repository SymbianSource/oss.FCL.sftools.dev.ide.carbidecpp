/*
========================================================================
 Name		: BirthdayForm.h

 Description: 

 Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies).
 All rights reserved.
 This component and the accompanying materials are made available
 under the terms of the License "Eclipse Public License v1.0"
 which accompanies this distribution, and is available
 at the URL "http://www.eclipse.org/legal/epl-v10.html".

 Contributors:
 Nokia Corporation - initial contribution.
========================================================================
*/
#ifndef BIRTHDAYFORM_H
#define BIRTHDAYFORM_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <aknform.h>
// ]]] end generated region [Generated Includes]


// [[[ begin [Event Handler Includes]
// ]]] end [Event Handler Includes]

// [[[ begin generated region: do not modify [Generated Forward Declarations]
class MEikCommandObserver;		
class CEikEdwin;
class CEikDateEditor;
// ]]] end generated region [Generated Forward Declarations]

struct TBirthday;

/**
 * @class	CBirthdayForm BirthdayForm.h
 * @brief	
 * @version	[version] TODO
 */
class CBirthdayForm : public CAknForm
	{
public: // constructors and destructor

	CBirthdayForm( MEikCommandObserver* aCommandObserver );
	static CBirthdayForm* NewL( MEikCommandObserver* aCommandObserver );
	static CBirthdayForm* NewLC( MEikCommandObserver* aCommandObserver );
	virtual ~CBirthdayForm();
	
	// From CEikDialog
	virtual TKeyResponse OfferKeyEventL( const TKeyEvent& aKeyEvent, TEventCode aType );
	
protected: 

	virtual void PreLayoutDynInitL();
	virtual TBool OkToExitL( TInt aButtonId );

protected: 

	// From CAknDialog	

	// from CAknForm
	void DynInitMenuPaneL( TInt aResourceId, CEikMenuPane* aMenuPane );
	TBool QuerySaveChangesL();
	TBool SaveFormDataL(); 
	void DoNotSaveFormDataL();

private:
	void LoadFromDataL();
	MEikCommandObserver* iCommandObserver;
	// [[[ begin generated region: do not modify [Generated Methods]
public: 
	// ]]] end generated region [Generated Methods]
	
	// [[[ begin generated region: do not modify [Generated Type Declarations]
public: 
	// ]]] end generated region [Generated Type Declarations]
	
	// [[[ begin generated region: do not modify [Generated Instance Variables]
private: 
	CEikEdwin* iName;
	CEikDateEditor* iDate;
	CEikEdwin* iTodo;
	// ]]] end generated region [Generated Instance Variables]
	
	
	// [[[ begin [Overridden Methods]
protected: 
	// ]]] end [Overridden Methods]
	
	
	// [[[ begin [User Handlers]
protected: 
	// ]]] end [User Handlers]
	
public:
	void SetBirthday( TBirthday* aBirthday, TBool aEmpty = EFalse )
		{
		iBirthday = aBirthday;
		iBirthdayChanged = EFalse;
		iBirthdayEmpty = aEmpty;
		}

	/**
	 * Tell whether the birthday was changed.
	 */
	TBool IsBirthdayChanged() 
		{
		return iBirthdayChanged;
		}
		
private:
	TBirthday* iBirthday;
	TBool iBirthdayChanged;
	TBool iBirthdayEmpty;
	};

#endif // BIRTHDAYFORM_H
