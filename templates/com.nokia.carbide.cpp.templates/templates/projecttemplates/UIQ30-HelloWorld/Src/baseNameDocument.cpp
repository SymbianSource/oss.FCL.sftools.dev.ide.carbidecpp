/*
============================================================================
 Name		: C$(baseName)Document from $(baseName)Document.h
 Author	  : $(author)
 Version	 :
 Copyright   : $(copyright)
 Description : C$(baseName)Document implementation
============================================================================
*/

#include <s32strm.h>
#include <QikApplication.h>

#include "$(baseName)Document.h"
#include "$(baseName)AppUi.h"
#include "$(baseName)Globals.h"

/**
Creates and constructs the document. This is called by
C$(baseName)Application::CreateDocumentL() which in turn is called by the
UI framework.
*/
C$(baseName)Document* C$(baseName)Document::NewL(CQikApplication& aApp)
	{
	C$(baseName)Document* self = new (ELeave) C$(baseName)Document(aApp);
	CleanupStack::PushL(self);
	self->ConstructL();
	CleanupStack::Pop(self);
	return self;
	}

/**
The constructor of the document class just passes the
supplied reference to the constructor initialization list.
*/
C$(baseName)Document::C$(baseName)Document(CQikApplication& aApp)
	: CQikDocument(aApp)
	{
	}

/**
2nd stage construction of the model.
All code that shall be called in initializing phase and might leave shall be
added here.
*/
void C$(baseName)Document::ConstructL()
	{
	}

/**
This is called by the UI framework as soon as the document has been created.
It creates an instance of the ApplicationUI. The Application UI class is an
instance of a CEikAppUi derived class.
*/
CEikAppUi* C$(baseName)Document::CreateAppUiL()
	{
	return new (ELeave) C$(baseName)AppUi;
	}

