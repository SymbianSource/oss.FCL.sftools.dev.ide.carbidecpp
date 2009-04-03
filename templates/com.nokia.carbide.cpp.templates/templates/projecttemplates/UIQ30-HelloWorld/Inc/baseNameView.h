/*
============================================================================
 Name		: C$(baseName)View from $(baseName)View.h
 Author	  : $(author)
 Version	 :
 Copyright   : $(copyright)
 Description : Declares main application view class.
============================================================================
*/

#ifndef $(baseNameUpper)VIEW_H
#define $(baseNameUpper)VIEW_H

#include <QikViewBase.h>

/**
A very simple view that displays the text "$(baseName)", drawn using the default title
font supplied by UIQ. It also consist of three commands that will bring up infoprints.

@since UIQ 3.0
*/
class C$(baseName)View : public CQikViewBase
	{
public:
	static C$(baseName)View* NewLC(CQikAppUi& aAppUi);
	~C$(baseName)View();

	// from CQikViewBase
	TVwsViewId ViewId()const;
	void HandleCommandL(CQikCommand& aCommand);

protected:
	// from CQikViewBase
	void ViewConstructL();

private:
	C$(baseName)View(CQikAppUi& aAppUi);
	void ConstructL();
	};

#endif // $(baseNameUpper)VIEW_H
