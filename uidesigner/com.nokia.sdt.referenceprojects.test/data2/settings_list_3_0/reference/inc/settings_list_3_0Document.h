#ifndef SETTINGS_LIST_3_0DOCUMENT_H
#define SETTINGS_LIST_3_0DOCUMENT_H

#include <akndoc.h>
		
class CEikAppUi;

/**
* @class	Csettings_list_3_0Document settings_list_3_0Document.h
* @brief	A CAknDocument-derived class is required by the S60 application 
*           framework. It is responsible for creating the AppUi object. 
*/
class Csettings_list_3_0Document : public CAknDocument
	{
public: 
	// constructor
	static Csettings_list_3_0Document* NewL( CEikApplication& aApp );

private: 
	// constructors
	Csettings_list_3_0Document( CEikApplication& aApp );
	void ConstructL();
	
public: 
	// from base class CEikDocument
	CEikAppUi* CreateAppUiL();
	};
#endif // SETTINGS_LIST_3_0DOCUMENT_H
