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


// INCLUDE FILES
#include    "$(baseName)App.h"
#include    "$(baseName)Document.h"

// ================= MEMBER FUNCTIONS =======================

// ---------------------------------------------------------
// C$(baseName)App::AppDllUid()
// Returns application UID
// ---------------------------------------------------------
//
TUid C$(baseName)App::AppDllUid() const
    {
    return KUid$(baseName);
    }

   
// ---------------------------------------------------------
// C$(baseName)App::CreateDocumentL()
// Creates C$(baseName)Document object
// ---------------------------------------------------------
//
CApaDocument* C$(baseName)App::CreateDocumentL()
    {
    return C$(baseName)Document::NewL( *this );
    }

// ================= OTHER EXPORTED FUNCTIONS ==============
//
// ---------------------------------------------------------
// NewApplication() 
// Constructs C$(baseName)App
// Returns: created application object
// ---------------------------------------------------------
//
EXPORT_C CApaApplication* NewApplication()
    {
    return new C$(baseName)App;
    }

// ---------------------------------------------------------
// E32Dll(TDllReason) 
// Entry point function for EPOC Apps
// Returns: KErrNone: No error
// ---------------------------------------------------------
//
GLDEF_C TInt E32Dll( TDllReason )
    {
    return KErrNone;
    }

  

