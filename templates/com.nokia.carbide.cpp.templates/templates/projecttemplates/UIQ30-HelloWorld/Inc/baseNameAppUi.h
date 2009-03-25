/*
============================================================================
 Name		: C$(baseName)AppUi from $(baseName)AppUi.h
 Author	  : $(author)
 Version	 :
 Copyright   : $(copyright)
 Description : Declares main application UI class.
============================================================================
*/

#ifndef $(baseNameUpper)APPUI_H
#define $(baseNameUpper)APPUI_H

#include <QikAppUi.h>

/**
This class represents the application UI in $(baseName) application,
It has responsibility to create the view.

@since UIQ 3.0
*/
class C$(baseName)AppUi : public CQikAppUi
	{
public:
	// from CQikAppUi
	void ConstructL();
	};
#endif // $(baseNameUpper)APPUI_H
