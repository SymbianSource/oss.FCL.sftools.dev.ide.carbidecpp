/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
* All rights reserved.
* This component and the accompanying materials are made available
* under the terms of the License "Eclipse Public License v1.0"
* which accompanies this distribution, and is available
* at the URL "http://www.eclipse.org/legal/epl-v10.html".
*
* Initial Contributors:
* Nokia Corporation - initial contribution.
*
* Contributors:
*
* Description: 
*
*/


#ifndef $(baseNameUpper)DOCUMENT_H
#define $(baseNameUpper)DOCUMENT_H

// INCLUDES
#include <akndoc.h>
   
// CONSTANTS

// FORWARD DECLARATIONS
class  CEikAppUi;

// CLASS DECLARATION

/**
*  C$(baseName)Document application class.
*/
class C$(baseName)Document : public CAknDocument
    {
    public: // Constructors and destructor
        /**
        * Two-phased constructor.
        */
        static C$(baseName)Document* NewL(CEikApplication& aApp);

        /**
        * Destructor.
        */
        virtual ~C$(baseName)Document();

    public: // New functions

    public: // Functions from base classes
    protected:  // New functions

    protected:  // Functions from base classes

    private:

        /**
        * EPOC default constructor.
        */
        C$(baseName)Document(CEikApplication& aApp);
        void ConstructL();

    private:

        /**
        * From CEikDocument, create C$(baseName)AppUi "App UI" object.
        */
        CEikAppUi* CreateAppUiL();
    };

#endif // $(baseNameUpper)DOCUMENT_H



