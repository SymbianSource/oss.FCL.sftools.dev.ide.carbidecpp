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

// INCLUDES
#include <e32std.h>
#include <e32base.h>

// CLASS DECLARATION

/**
*  C$(classname)
* 
*/
class C$(classname) : public CBase
{
public: // Constructors and destructor

	/**
		* Destructor.
		*/
	~C$(classname)();

		/**
		* Two-phased constructor.
		*/
	static C$(classname)* NewL();

		/**
		* Two-phased constructor.
		*/
	static C$(classname)* NewLC();

private:

	/**
		* Constructor for performing 1st stage construction
		*/
	C$(classname)();

	/**
		* EPOC default constructor for performing 2nd stage construction
		*/
	void ConstructL();

};

#endif // $(classname$upper)_H

