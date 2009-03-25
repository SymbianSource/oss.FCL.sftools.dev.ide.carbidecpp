// [[[ begin generated region: do not modify [Generated System Includes]
#include <aknviewappui.h>
#include <eikmenub.h>
#include <avkon.hrh>
#include <barsread.h>
#include <stringloader.h>
#include <eiklabel.h>
#include <eikenv.h>
#include <akncontext.h>
#include <akntitle.h>
#include <eikbtgpc.h>
#include <aknpreviewpopupcontroller.h>
#include <barsread.h>
#include <stringloader.h>
#include "PreviewPopUp1.h"
#include <TUI_5_0.rsg>
// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated User Includes]

#include "TUI_5_0.hrh"
#include "TUI_5_0ContainerView.h"
#include "TUI_5_0Container.hrh"
#include "TUI_5_0Container.h"
// ]]] end generated region [Generated User Includes]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

/**
 * First phase of Symbian two-phase construction. Should not contain any
 * code that could leave.
 */
CTUI_5_0ContainerView::CTUI_5_0ContainerView()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	iTUI_5_0Container = NULL;
	iPreviewPopUp1Controller = NULL;
	iPreviewPopUp1Content = NULL;
	// ]]] end generated region [Generated Contents]
	
	}

/** 
 * The view's destructor removes the container from the control
 * stack and destroys it.
 */
CTUI_5_0ContainerView::~CTUI_5_0ContainerView()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	delete iTUI_5_0Container;
	iTUI_5_0Container = NULL;
	delete iPreviewPopUp1Controller;
	iPreviewPopUp1Controller = NULL;
	delete iPreviewPopUp1Content;
	iPreviewPopUp1Content = NULL;
	// ]]] end generated region [Generated Contents]
	
	}

/**
 * Symbian two-phase constructor.
 * This creates an instance then calls the second-phase constructor
 * without leaving the instance on the cleanup stack.
 * @return new instance of CTUI_5_0ContainerView
 */
CTUI_5_0ContainerView* CTUI_5_0ContainerView::NewL()
	{
	CTUI_5_0ContainerView* self = CTUI_5_0ContainerView::NewLC();
	CleanupStack::Pop( self );
	return self;
	}

/**
 * Symbian two-phase constructor.
 * This creates an instance, pushes it on the cleanup stack,
 * then calls the second-phase constructor.
 * @return new instance of CTUI_5_0ContainerView
 */
CTUI_5_0ContainerView* CTUI_5_0ContainerView::NewLC()
	{
	CTUI_5_0ContainerView* self = new ( ELeave ) CTUI_5_0ContainerView();
	CleanupStack::PushL( self );
	self->ConstructL();
	return self;
	}


/**
 * Second-phase constructor for view.  
 * Initialize contents from resource.
 */ 
void CTUI_5_0ContainerView::ConstructL()
	{
	// [[[ begin generated region: do not modify [Generated Code]
	BaseConstructL( R_TUI_5_0_CONTAINER_TUI_5_0_CONTAINER_VIEW );
				
	if( AknLayoutUtils::PenEnabled() && Toolbar() ) 
		{
		//By default keep toolbar invisible        
		ShowToolbarOnViewActivation( ETrue ); 
		Toolbar()->SetToolbarObserver( this );
		}		
	iPreviewPopUp1Content = CPreviewPopUp1::NewL( this );
	iPreviewPopUp1Controller = CAknPreviewPopUpController::NewL(
			*iPreviewPopUp1Content,
			*iPreviewPopUp1Content );
	iPreviewPopUp1Content->SetPopUpController( iPreviewPopUp1Controller );
		{
		HBufC* headingText = StringLoader::LoadLC( R_TUI_5_0_CONTAINER_PREVIEW_POP_UP1 );
		iPreviewPopUp1Controller->SetHeadingTextL( *headingText );
		CleanupStack::PopAndDestroy( headingText );
		}
	
	
	// ]]] end generated region [Generated Code]
	
	// add your own initialization code here
	
	}

/**
 * @return The UID for this view
 */
TUid CTUI_5_0ContainerView::Id() const
	{
	return TUid::Uid( ETUI_5_0ContainerViewId );
	}

/**
 * Handle a command for this view (override)
 * @param aCommand command id to be handled
 */
void CTUI_5_0ContainerView::HandleCommandL( TInt aCommand )
	{
	// [[[ begin generated region: do not modify [Generated Code]
	TBool commandHandled = EFalse;
	switch ( aCommand )
		{	// code to dispatch to the AknView's menu and CBA commands is generated here
		default:
			break;
		}
	
		
	if ( !commandHandled ) 
		{
	
		if ( aCommand == EAknSoftkeyExit )
			{
			AppUi()->HandleCommandL( EEikCmdExit );
			}
	
		}
	// ]]] end generated region [Generated Code]
	
	}

/**
 *	Handles user actions during activation of the view, 
 *	such as initializing the content.
 */
void CTUI_5_0ContainerView::DoActivateL( 
		const TVwsViewId& /*aPrevViewId*/,
		TUid /*aCustomMessageId*/,
		const TDesC8& /*aCustomMessage*/ )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	SetupStatusPaneL();
	
				
				
	
	if ( iTUI_5_0Container == NULL )
		{
		iTUI_5_0Container = CreateContainerL();
		iTUI_5_0Container->SetMopParent( this );
		AppUi()->AddToStackL( *this, iTUI_5_0Container );
		} 
	// ]]] end generated region [Generated Contents]
	
	}

/**
 */
void CTUI_5_0ContainerView::DoDeactivate()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	CleanupStatusPane();
	
		
	if ( iTUI_5_0Container != NULL )
		{
		AppUi()->RemoveFromViewStack( *this, iTUI_5_0Container );
		delete iTUI_5_0Container;
		iTUI_5_0Container = NULL;
		}
	// ]]] end generated region [Generated Contents]
	
	}

/** 
 * Handle status pane size change for this view (override)
 */
void CTUI_5_0ContainerView::HandleStatusPaneSizeChange()
	{
	CAknView::HandleStatusPaneSizeChange();
	
	// this may fail, but we're not able to propagate exceptions here
	TVwsViewId view;
	AppUi()->GetActiveViewId( view );
	if ( view.iViewUid == Id() )
		{
		TInt result;
		TRAP( result, SetupStatusPaneL() );
		}
	
	// [[[ begin generated region: do not modify [Generated Code]
	// ]]] end generated region [Generated Code]
	
	}

// [[[ begin generated function: do not modify
void CTUI_5_0ContainerView::SetupStatusPaneL()
	{
	// reset the context pane
	TUid contextPaneUid = TUid::Uid( EEikStatusPaneUidContext );
	CEikStatusPaneBase::TPaneCapabilities subPaneContext = 
		StatusPane()->PaneCapabilities( contextPaneUid );
	if ( subPaneContext.IsPresent() && subPaneContext.IsAppOwned() )
		{
		CAknContextPane* context = static_cast< CAknContextPane* > ( 
			StatusPane()->ControlL( contextPaneUid ) );
		context->SetPictureToDefaultL();
		}
	
	// setup the title pane
	TUid titlePaneUid = TUid::Uid( EEikStatusPaneUidTitle );
	CEikStatusPaneBase::TPaneCapabilities subPaneTitle = 
		StatusPane()->PaneCapabilities( titlePaneUid );
	if ( subPaneTitle.IsPresent() && subPaneTitle.IsAppOwned() )
		{
		CAknTitlePane* title = static_cast< CAknTitlePane* >( 
			StatusPane()->ControlL( titlePaneUid ) );
		TResourceReader reader;
		iEikonEnv->CreateResourceReaderLC( reader, R_TUI_5_0_CONTAINER_TITLE_RESOURCE );
		title->SetFromResourceL( reader );
		CleanupStack::PopAndDestroy(); // reader internal state
		}
				
	}

// ]]] end generated function

// [[[ begin generated function: do not modify
void CTUI_5_0ContainerView::CleanupStatusPane()
	{
	}

// ]]] end generated function

/**
 *	Creates the top-level container for the view.  You may modify this method's
 *	contents and the CTUI_5_0Container::NewL() signature as needed to initialize the
 *	container, but the signature for this method is fixed.
 *	@return new initialized instance of CTUI_5_0Container
 */
CTUI_5_0Container* CTUI_5_0ContainerView::CreateContainerL()
	{
	return CTUI_5_0Container::NewL( ClientRect(), NULL, this );
	}
/**
 * Should be used to set the properties of some toolbar components 
 * before it is drawn.
 * @param aResourceId The resource ID for particular toolbar
 * @param aToolbar The toolbar object pointer
 */
void CTUI_5_0ContainerView::DynInitToolbarL( 
		TInt aResourceId,
		CAknToolbar* aToolBar )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
		
	
	// ]]] end generated region [Generated Contents]
	
	}

/**
 * Handles toolbar events for a certain toolbar item.
 * @param aCommand The command ID of some toolbar item.
 */
void CTUI_5_0ContainerView::OfferToolbarEventL( TInt aCommandId )
	{
	HandleCommandL( aCommandId );
	}

// [[[ begin generated function: do not modify
/**
 * Show the preview pop-up for previewPopUp1.
 * Sets the position of the preview pop-up so that it is aligned with the highlight rect.
 * This is intented to be used in conjunction with lists and grids if the 
 * application wishes to implement a popup that follows lists/grids item highlight.
 * If fixed mode is used then the value of the highlight rect has no effect.
 * @param aHighlightRect Screen-relative rectangle used to calculate pop-up's position.
 * @param aOverrideText optional override header text
 */
void CTUI_5_0ContainerView::ShowPreviewPopUp1L( 
		const TRect& aHighlightRect,
		const TDesC* aOverrideHeadingText )
	{
	if ( aOverrideHeadingText != NULL )
		{
		iPreviewPopUp1Controller->SetHeadingTextL( *aOverrideHeadingText );
		}
	iPreviewPopUp1Controller->SetPositionByHighlight( aHighlightRect );
	iPreviewPopUp1Controller->ShowPopUp();
	}

// ]]] end generated function

// [[[ begin generated function: do not modify
/**
 * Show the preview pop-up for previewPopUp1.
 * Sets the position of preview popup left and down from the given point. 
 * If there is not enough space, the pop-up is opened up and left.
 * If fixed mode is used then the value of the point has no effect.
 * @param aPoint Popup's position.
 * @param aOverrideText optional override header text
 */
void CTUI_5_0ContainerView::ShowPreviewPopUp1L( 
		const TPoint& aPoint,
		const TDesC* aOverrideHeadingText )
	{
	if ( aOverrideHeadingText != NULL )
		{
		iPreviewPopUp1Controller->SetHeadingTextL( *aOverrideHeadingText );
		}
	iPreviewPopUp1Controller->SetPosition( aPoint );
	iPreviewPopUp1Controller->ShowPopUp();
	}

// ]]] end generated function


