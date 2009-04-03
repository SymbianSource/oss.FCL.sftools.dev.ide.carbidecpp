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


#ifndef $(baseNameUpper)APP_H
#define $(baseNameUpper)APP_H

// INCLUDES
#include <aknapp.h>

// CONSTANTS
// UID of the application
const TUid KUid$(baseName) = { $(uid3) };

// CLASS DECLARATION

/**
* C$(baseName)App application class.
* Provides factory to create concrete document object.
* 
*/
class C$(baseName)App : public CAknApplication
    {
    
    public: // Functions from base classes
    private:

        /**
        * From CApaApplication, creates C$(baseName)Document document object.
        * @return A pointer to the created document object.
        */
        CApaDocument* CreateDocumentL();
        
        /**
        * From CApaApplication, returns application's UID (KUid$(baseName)).
        * @return The value of KUid$(baseName).
        */
        TUid AppDllUid() const;
    };

#endif  // $(baseNameUpper)APP_H




