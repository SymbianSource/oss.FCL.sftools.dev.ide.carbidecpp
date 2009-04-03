#ifndef EMPTY_CONTAINER_3_0DOCUMENT_H
#define EMPTY_CONTAINER_3_0DOCUMENT_H

#include <akndoc.h>
		
class CEikAppUi;

/**
* @class	Cempty_container_3_0Document empty_container_3_0Document.h
* @brief	A CAknDocument-derived class is required by the S60 application 
*           framework. It is responsible for creating the AppUi object. 
*/
class Cempty_container_3_0Document : public CAknDocument
	{
public: 
	// constructor
	static Cempty_container_3_0Document* NewL( CEikApplication& aApp );

private: 
	// constructors
	Cempty_container_3_0Document( CEikApplication& aApp );
	void ConstructL();
	
public: 
	// from base class CEikDocument
	CEikAppUi* CreateAppUiL();
	};
#endif // EMPTY_CONTAINER_3_0DOCUMENT_H
