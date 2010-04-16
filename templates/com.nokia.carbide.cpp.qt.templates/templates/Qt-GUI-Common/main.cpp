/*
============================================================================
 Name		: $(baseName).cpp
 Author	  : $(author)
 Copyright   : $(copyright)
 Description : Main GUI Application
============================================================================
*/

#include "$(className).h"

#include <QtGui>
#include <QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    $(className) w;
    w.showMaximized();
    return a.exec();
}
