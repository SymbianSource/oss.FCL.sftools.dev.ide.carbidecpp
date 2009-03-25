/*
============================================================================
 Name		: $(classname).h
 Author	  : $(author)
 Version	 : $(version)
 Copyright   : $(copyright)
 Description : C$(classname) declaration
============================================================================
*/

#ifndef $(classname$upper)_H
#define $(classname$upper)_H

#include <e32base.h>	// For CActive, link against: euser.lib
#include <e32std.h>		// For RTimer, link against: euser.lib

class C$(classname) : public CActive
{
public:
	// Cancel and destroy
	~C$(classname)();

	// Two-phased constructor.
	static C$(classname)* NewL();

	// Two-phased constructor.
	static C$(classname)* NewLC();

public: // New functions
	// Function for making the initial request
	void StartL( TTimeIntervalMicroSeconds32 aDelay );

private:
	// C++ constructor
	C$(classname)();
	
	// Second-phase constructor
	void ConstructL();
	
private: // From CActive
	// Handle completion
	void RunL();
	
	// How to cancel me
	void DoCancel();
	
	// Override to handle leaves from RunL(). Default implementation causes
	// the active scheduler to panic.
	TInt RunError( TInt aError );

private:
	enum T$(classname)State
	{
		EUninitialized,	// Uninitialized
		EInitialized,	// Initalized
		EError			// Error condition
	};

private:
	TInt	iState;		// State of the active object
	RTimer	iTimer;		// Provides async timing service

};

#endif // $(classname$upper)_H
