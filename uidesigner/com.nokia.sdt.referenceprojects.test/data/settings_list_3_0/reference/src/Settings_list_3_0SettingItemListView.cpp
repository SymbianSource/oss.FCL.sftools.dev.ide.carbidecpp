// [[[ begin generated region: do not modify [Generated System Includes]
#include <aknviewappui.h>
#include <eikmenub.h>
#include <avkon.hrh>
#include <akncontext.h>
#include <akntitle.h>
#include <stringloader.h>
#include <barsread.h>
#include <eikbtgpc.h>
#include <settings_list_3_0.rsg>
// ]]] end generated region [Generated System Includes]

// [[[ begin generated region: do not modify [Generated User Includes]

#include "settings_list_3_0.hrh"
#include "Settings_list_3_0SettingItemListView.h"
#include "Settings_list_3_0SettingItemList.hrh"
#include "Settings_list_3_0SettingItemList.h"
// ]]] end generated region [Generated User Includes]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

/**
 * First phase of Symbian two-phase construction. Should not contain any
 * code that could leave.
 */
CSettings_list_3_0SettingItemListView::CSettings_list_3_0SettingItemListView()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	
	}

/** 
 * The view's destructor removes the container from the control
 * stack and destroys it.
 */
CSettings_list_3_0SettingItemListView::~CSettings_list_3_0SettingItemListView()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	// ]]] end generated region [Generated Contents]
	
	}

/**
 * Symbian two-phase constructor.
 * This creates an instance then calls the second-phase constructor
 * without leaving the instance on the cleanup stack.
 * @return new instance of CSettings_list_3_0SettingItemListView
 */
CSettings_list_3_0SettingItemListView* CSettings_list_3_0SettingItemListView::NewL()
	{
	CSettings_list_3_0SettingItemListView* self = CSettings_list_3_0SettingItemListView::NewLC();
	CleanupStack::Pop( self );
	return self;
	}

/**
 * Symbian two-phase constructor.
 * This creates an instance, pushes it on the cleanup stack,
 * then calls the second-phase constructor.
 * @return new instance of CSettings_list_3_0SettingItemListView
 */
CSettings_list_3_0SettingItemListView* CSettings_list_3_0SettingItemListView::NewLC()
	{
	CSettings_list_3_0SettingItemListView* self = new ( ELeave ) CSettings_list_3_0SettingItemListView();
	CleanupStack::PushL( self );
	self->ConstructL();
	return self;
	}


/**
 * Second-phase constructor for view.  
 * Initialize contents from resource.
 */ 
void CSettings_list_3_0SettingItemListView::ConstructL()
	{
	// [[[ begin generated region: do not modify [Generated Code]
	BaseConstructL( R_SETTINGS_LIST_3_0_SETTING_ITEM_LIST_SETTINGS_LIST_3_0_SETTING_ITEM_LIST_VIEW );
				
	// ]]] end generated region [Generated Code]
	
	// add your own initialization code here
	
	}

/**
 * @return The UID for this view
 */
TUid CSettings_list_3_0SettingItemListView::Id() const
	{
	return TUid::Uid( ESettings_list_3_0SettingItemListViewId );
	}

/**
 * Handle a command for this view (override)
 * @param aCommand command id to be handled
 */
void CSettings_list_3_0SettingItemListView::HandleCommandL( TInt aCommand )
	{
	// [[[ begin generated region: do not modify [Generated Code]
	TBool commandHandled = EFalse;
	switch ( aCommand )
		{	// code to dispatch to the AknView's menu and CBA commands is generated here
		case ESettings_list_3_0SettingItemListViewMenuItem1Command:
			commandHandled = HandleChangeSelectedSettingItemL( aCommand );
			break;
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
void CSettings_list_3_0SettingItemListView::DoActivateL( 
		const TVwsViewId& /*aPrevViewId*/,
		TUid /*aCustomMessageId*/,
		const TDesC8& /*aCustomMessage*/ )
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	SetupStatusPaneL();
	
				
	if ( iSettings_list_3_0SettingItemList == NULL )
		{
		iSettings = TSettings_list_3_0SettingItemListSettings::NewL();
		iSettings_list_3_0SettingItemList = new ( ELeave ) CSettings_list_3_0SettingItemList( *iSettings, this );
		iSettings_list_3_0SettingItemList->SetMopParent( this );
		iSettings_list_3_0SettingItemList->ConstructFromResourceL( R_SETTINGS_LIST_3_0_SETTING_ITEM_LIST_SETTINGS_LIST_3_0_SETTING_ITEM_LIST );
		iSettings_list_3_0SettingItemList->ActivateL();
		iSettings_list_3_0SettingItemList->LoadSettingValuesL();
		iSettings_list_3_0SettingItemList->LoadSettingsL();
		AppUi()->AddToStackL( *this, iSettings_list_3_0SettingItemList );
		} 
	// ]]] end generated region [Generated Contents]
	
	}

/**
 */
void CSettings_list_3_0SettingItemListView::DoDeactivate()
	{
	// [[[ begin generated region: do not modify [Generated Contents]
	CleanupStatusPane();
	
	if ( iSettings_list_3_0SettingItemList != NULL )
		{
		AppUi()->RemoveFromStack( iSettings_list_3_0SettingItemList );
		delete iSettings_list_3_0SettingItemList;
		iSettings_list_3_0SettingItemList = NULL;
		delete iSettings;
		iSettings = NULL;
		}
	// ]]] end generated region [Generated Contents]
	
	}

/** 
 * Handle status pane size change for this view (override)
 */
void CSettings_list_3_0SettingItemListView::HandleStatusPaneSizeChange()
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
void CSettings_list_3_0SettingItemListView::SetupStatusPaneL()
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
		iEikonEnv->CreateResourceReaderLC( reader, R_SETTINGS_LIST_3_0_SETTING_ITEM_LIST_TITLE_RESOURCE );
		title->SetFromResourceL( reader );
		CleanupStack::PopAndDestroy(); // reader internal state
		}
				
	}

// ]]] end generated function

// [[[ begin generated function: do not modify
void CSettings_list_3_0SettingItemListView::CleanupStatusPane()
	{
	}

// ]]] end generated function

/** 
 * Handle the selected event.
 * @param aCommand the command id invoked
 * @return ETrue if the command was handled, EFalse if not
 */
TBool CSettings_list_3_0SettingItemListView::HandleChangeSelectedSettingItemL( TInt aCommand )
	{
	// TODO: implement selected event handler
	return ETrue;
	}
				

