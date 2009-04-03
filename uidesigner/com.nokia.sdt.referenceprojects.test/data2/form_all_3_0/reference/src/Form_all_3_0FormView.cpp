// [[[ begin generated region: do not modify [Generated System Includes]
#include <aknviewappui.h>
#include <eikmenub.h>
#include <avkon.hrh>
#include <akncontext.h>
#include <akntitle.h>
#include <stringloader.h>
#include <barsread.h>
#include <form_all_3_0.rsg>
// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated User Includes]

#include "form_all_3_0.hrh"
#include "form_all_3_0FormView.h"
#include "form_all_3_0Form.hrh"
#include "form_all_3_0Form.h"
// ]]] end generated region [Generated User Includes]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

/**
 * First phase of Symbian two-phase construction. Should not contain any
 * code that could leave.
 */
Cform_all_3_0FormView::Cform_all_3_0FormView()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	
	}

/** 
 * The view's destructor removes the container from the control
 * stack and destroys it.
 */
Cform_all_3_0FormView::~Cform_all_3_0FormView()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	
	}

/**
 * Symbian two-phase constructor.
 * This creates an instance then calls the second-phase constructor
 * without leaving the instance on the cleanup stack.
 * @return new instance of Cform_all_3_0FormView
 */
Cform_all_3_0FormView* Cform_all_3_0FormView::NewL()
	{
	Cform_all_3_0FormView* self = Cform_all_3_0FormView::NewLC();
	CleanupStack::Pop( self );
	return self;
	}

/**
 * Symbian two-phase constructor.
 * This creates an instance, pushes it on the cleanup stack,
 * then calls the second-phase constructor.
 * @return new instance of Cform_all_3_0FormView
 */
Cform_all_3_0FormView* Cform_all_3_0FormView::NewLC()
	{
	Cform_all_3_0FormView* self = new ( ELeave ) Cform_all_3_0FormView();
	CleanupStack::PushL( self );
	self->ConstructL();
	return self;
	}


/**
 * Second-phase constructor for view.  
 * Initialize contents from resource.
 */ 
void Cform_all_3_0FormView::ConstructL()
	{
	// [[[ begin generated region: do not modify [Generated Code]
	BaseConstructL( R_FORM_ALL_3_0_FORM_FORM_ALL_3_0_FORM_VIEW );
				
	// ]]] end generated region [Generated Code]
	
	// add your own initialization code here
	
	}

/**
 * @return The UID for this view
 */
TUid Cform_all_3_0FormView::Id() const
	{
	return TUid::Uid( EForm_all_3_0FormViewId );
	}

/**
 * Handle a command for this view (override)
 * @param aCommand command id to be handled
 */
void Cform_all_3_0FormView::HandleCommandL( TInt aCommand )
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
		// dispatch to handleCommand handler
		commandHandled = HandleForm_all_3_0FormViewCommandL( aCommand );
	
		}
	// ]]] end generated region [Generated Code]
	
	}

/**
 *	Handles user actions during activation of the view, 
 *	such as initializing the content.
 */
void Cform_all_3_0FormView::DoActivateL( 
		const TVwsViewId& /*aPrevViewId*/,
		TUid /*aCustomMessageId*/,
		const TDesC8& /*aCustomMessage*/ )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	SetupStatusPaneL();
	
				
	if ( iForm_all_3_0Form == NULL )
		{
		CForm_all_3_0Form* form = CForm_all_3_0Form::NewL( this );
		form->SetMopParent( this ); 
		form->ExecuteLD( R_FORM_ALL_3_0_FORM_FORM_ALL_3_0_FORM );
		AppUi()->AddToStackL( *this, form );
		iForm_all_3_0Form = form;
		} 
	HandleForm_all_3_0FormViewActivatedL();
	// ]]] end generated region [Generated Contents]
	
	}

/**
 */
void Cform_all_3_0FormView::DoDeactivate()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	HandleForm_all_3_0FormViewDeactivated();
	CleanupStatusPane();
	
	if ( iForm_all_3_0Form != NULL )
		{
		AppUi()->RemoveFromStack( iForm_all_3_0Form );
		delete iForm_all_3_0Form;
		iForm_all_3_0Form = NULL;
		}
	// ]]] end generated region [Generated Contents]
	
	}

/**
 *	Handle the deactivated event
 */
void Cform_all_3_0FormView::HandleForm_all_3_0FormViewDeactivated()
	{
	// TODO: implement deactivated event handler		
	}

/** 
 * Handle status pane size change for this view (override)
 */
void Cform_all_3_0FormView::HandleStatusPaneSizeChange()
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
	HandleForm_all_3_0FormViewStatusPaneSizeChange();
	// ]]] end generated region [Generated Code]
	
	}

/** 
 * Handle the handleCommand event.
 * @return ETrue if event was handled, EFalse if not
 */
TBool Cform_all_3_0FormView::HandleForm_all_3_0FormViewCommandL( TInt aCommand )
	{
	// TODO: implement handleCommand event handler		
	return EFalse;
	}

/**
 *	Handle the handleStatusPaneSizeChange event
 */
void Cform_all_3_0FormView::HandleForm_all_3_0FormViewStatusPaneSizeChange()
	{
	// TODO: implement handleStatusPaneSizeChange event handler		
	}

// [[[ begin generated function: do not modify
void Cform_all_3_0FormView::SetupStatusPaneL()
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
		iEikonEnv->CreateResourceReaderLC( reader, R_FORM_ALL_3_0_FORM_TITLE_RESOURCE );
		title->SetFromResourceL( reader );
		CleanupStack::PopAndDestroy(); // reader internal state
		}
				
	}

// ]]] end generated function

// [[[ begin generated function: do not modify
void Cform_all_3_0FormView::CleanupStatusPane()
	{
	}

// ]]] end generated function

/**
 *	Handle the activated event
 */
void Cform_all_3_0FormView::HandleForm_all_3_0FormViewActivatedL()
	{
	// TODO: implement activated event handler		
	}


