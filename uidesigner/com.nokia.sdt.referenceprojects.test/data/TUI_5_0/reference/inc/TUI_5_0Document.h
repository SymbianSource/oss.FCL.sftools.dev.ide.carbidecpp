#ifndef TUI_5_0DOCUMENT_H
#define TUI_5_0DOCUMENT_H

#include <akndoc.h>
		
class CEikAppUi;

/**
* @class	CTUI_5_0Document TUI_5_0Document.h
* @brief	A CAknDocument-derived class is required by the S60 application 
*           framework. It is responsible for creating the AppUi object. 
*/
class CTUI_5_0Document : public CAknDocument
	{
public: 
	// constructor
	static CTUI_5_0Document* NewL( CEikApplication& aApp );

private: 
	// constructors
	CTUI_5_0Document( CEikApplication& aApp );
	void ConstructL();
	
public: 
	// from base class CEikDocument
	CEikAppUi* CreateAppUiL();
	};
#endif // TUI_5_0DOCUMENT_H
