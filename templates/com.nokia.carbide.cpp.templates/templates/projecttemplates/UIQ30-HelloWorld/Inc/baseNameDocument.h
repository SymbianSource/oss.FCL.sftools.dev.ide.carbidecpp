/*
============================================================================
 Name		: C$(baseName)Document from $(baseName)Document.h
 Author	  : $(author)
 Version	 :
 Copyright   : $(copyright)
 Description : Declares main application document class.
============================================================================
*/


#ifndef $(baseNameUpper)DOCUMENT_H
#define $(baseNameUpper)DOCUMENT_H

#include <QikDocument.h>

/**
This class represents the document in $(baseName) application,
For an UIQ application to work properly the document class need to be
derived from CQikDocument.

@since UIQ 3.0
*/
class C$(baseName)Document : public CQikDocument
	{
public:
	static C$(baseName)Document* NewL(CQikApplication& aApp);

private:
	// from CQikDocument
	CEikAppUi* CreateAppUiL();

	C$(baseName)Document(CQikApplication& aApp);
	void ConstructL();
	};
#endif // $(baseNameUpper)DOCUMENT_H
