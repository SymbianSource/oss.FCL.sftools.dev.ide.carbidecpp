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

C$(classname)::C$(classname)()
{
	// No implementation required
}


C$(classname)::~C$(classname)()
{
}

C$(classname)* C$(classname)::NewLC()
{
	C$(classname)* self = new (ELeave)C$(classname)();
	CleanupStack::PushL(self);
	self->ConstructL();
	return self;
}

C$(classname)* C$(classname)::NewL()
{
	C$(classname)* self=C$(classname)::NewLC();
	CleanupStack::Pop(); // self;
	return self;
}

void C$(classname)::ConstructL()
{

}
