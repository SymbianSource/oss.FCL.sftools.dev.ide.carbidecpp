/*
============================================================================
 Name		: $(baseName).cpp
 Author	  : $(author)
 Copyright   : $(copyright)
 Description : Declares Class
============================================================================
*/

#ifndef $(className$upper)_H
#define $(className$upper)_H

#include <QtGui/$(parentClass)>
#include "ui_$(baseName).h"

class $(className) : public $(parentClass)
{
    Q_OBJECT

public:
	$(className)(QWidget *parent = 0);
    ~$(className)();

private:
    Ui::$(className) ui;
};

#endif // $(className$upper)_H
