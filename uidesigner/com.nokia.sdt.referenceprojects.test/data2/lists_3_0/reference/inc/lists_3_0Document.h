#ifndef LISTS_3_0DOCUMENT_H
#define LISTS_3_0DOCUMENT_H

#include <akndoc.h>
		
class CEikAppUi;

/**
* @class	Clists_3_0Document lists_3_0Document.h
* @brief	A CAknDocument-derived class is required by the S60 application 
*           framework. It is responsible for creating the AppUi object. 
*/
class Clists_3_0Document : public CAknDocument
	{
public: 
	// constructor
	static Clists_3_0Document* NewL( CEikApplication& aApp );

private: 
	// constructors
	Clists_3_0Document( CEikApplication& aApp );
	void ConstructL();
	
public: 
	// from base class CEikDocument
	CEikAppUi* CreateAppUiL();
	};
#endif // LISTS_3_0DOCUMENT_H
