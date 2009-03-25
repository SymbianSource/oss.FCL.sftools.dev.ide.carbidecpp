/*
============================================================================
 Name		: $(classname).cpp
 Author	  : $(author)
 Version	 : $(version)
 Copyright   : $(copyright)
 Description : C$(classname) implementation
============================================================================
*/

#include "$(incFileName)"

C$(classname)::C$(classname)() : CActive( EPriorityStandard )	// Standard priority
{
}

C$(classname)* C$(classname)::NewLC()
{
	C$(classname)* self = new ( ELeave ) C$(classname)();
	CleanupStack::PushL( self );
	self->ConstructL();
	return self;
}

C$(classname)* C$(classname)::NewL()
{
	C$(classname)* self = C$(classname)::NewLC();
	CleanupStack::Pop(); // self;
	return self;
}

void C$(classname)::ConstructL()
{
	User::LeaveIfError( iTimer.CreateLocal() );	// Initialize timer
	CActiveScheduler::Add( this );				// Add to scheduler
}

C$(classname)::~C$(classname)()
{
	Cancel(); // Cancel any request, if outstanding
	iTimer.Close(); // Destroy the RTimer object
	// Delete instance variables if any
}

void C$(classname)::DoCancel()
{
	iTimer.Cancel();
}

void C$(classname)::StartL( TTimeIntervalMicroSeconds32 aDelay )
{
	Cancel();							// Cancel any request, just to be sure
	iState = EUninitialized;
	iTimer.After( iStatus, aDelay );	// Set for later
	SetActive();						// Tell scheduler a request is active
}

void C$(classname)::RunL()
{
	if ( iState == EUninitialized ) 
	{
		// Do something the first time RunL() is called
		iState = EInitialized;
	} 
	else if ( iState != EError ) 
	{
		// Do something
	}
	iTimer.After( iStatus, 1000000 );	// Set for 1 sec later
	SetActive();					// Tell scheduler a request is active
}

TInt C$(classname)::RunError( TInt aError )
{
	return aError;
}
