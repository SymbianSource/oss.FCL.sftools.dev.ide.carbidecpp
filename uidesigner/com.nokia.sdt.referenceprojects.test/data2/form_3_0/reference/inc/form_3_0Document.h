#ifndef FORM_3_0DOCUMENT_H
#define FORM_3_0DOCUMENT_H

#include <akndoc.h>
		
class CEikAppUi;

/**
* @class	Cform_3_0Document form_3_0Document.h
* @brief	A CAknDocument-derived class is required by the S60 application 
*           framework. It is responsible for creating the AppUi object. 
*/
class Cform_3_0Document : public CAknDocument
	{
public: 
	// constructor
	static Cform_3_0Document* NewL( CEikApplication& aApp );

private: 
	// constructors
	Cform_3_0Document( CEikApplication& aApp );
	void ConstructL();
	
public: 
	// from base class CEikDocument
	CEikAppUi* CreateAppUiL();
	};
#endif // FORM_3_0DOCUMENT_H
