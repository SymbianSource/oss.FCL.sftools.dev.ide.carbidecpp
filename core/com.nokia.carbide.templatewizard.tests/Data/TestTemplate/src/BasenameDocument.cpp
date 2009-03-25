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
#include "$(baseName)Document.h"
#include "$(baseName)Appui.h"

// ================= MEMBER FUNCTIONS =======================

// constructor
C$(baseName)Document::C$(baseName)Document(CEikApplication& aApp)
: CAknDocument(aApp)    
    {
    }

// destructor
C$(baseName)Document::~C$(baseName)Document()
    {
    }

// EPOC default constructor can leave.
void C$(baseName)Document::ConstructL()
    {
    }

// Two-phased constructor.
C$(baseName)Document* C$(baseName)Document::NewL(
        CEikApplication& aApp)     // C$(baseName)App reference
    {
    C$(baseName)Document* self = new (ELeave) C$(baseName)Document( aApp );
    CleanupStack::PushL( self );
    self->ConstructL();
    CleanupStack::Pop();

    return self;
    }
    
// ----------------------------------------------------
// C$(baseName)Document::CreateAppUiL()
// constructs C$(baseName)AppUi
// ----------------------------------------------------
//
CEikAppUi* C$(baseName)Document::CreateAppUiL()
    {
    return new (ELeave) C$(baseName)AppUi;
    }

  
