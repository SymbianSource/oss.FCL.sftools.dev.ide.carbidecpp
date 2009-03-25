#ifndef SETTINGS_LIST_3_0SETTINGITEMLISTVIEW_H
#define SETTINGS_LIST_3_0SETTINGITEMLISTVIEW_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <aknview.h>
#include "Settings_list_3_0SettingItemListSettings.h"
// ]]] end generated region [Generated Includes]


// [[[ begin [Event Handler Includes]
// ]]] end [Event Handler Includes]

// [[[ begin generated region: do not modify [Generated Constants]
// ]]] end generated region [Generated Constants]

// [[[ begin generated region: do not modify [Generated Forward Declarations]
class CSettings_list_3_0SettingItemList;
// ]]] end generated region [Generated Forward Declarations]

/**
 * Avkon view class for Settings_list_3_0SettingItemListView. It is register with the view server
 * by the AppUi. It owns the container control.
 * @class	CSettings_list_3_0SettingItemListView Settings_list_3_0SettingItemListView.h
 */						
			
class CSettings_list_3_0SettingItemListView : public CAknView
	{
	
	
	// [[[ begin [Public Section]
public:
	// constructors and destructor
	CSettings_list_3_0SettingItemListView();
	static CSettings_list_3_0SettingItemListView* NewL();
	static CSettings_list_3_0SettingItemListView* NewLC();        
	void ConstructL();
	virtual ~CSettings_list_3_0SettingItemListView();
						
	// from base class CAknView
	TUid Id() const;
	void HandleCommandL( TInt aCommand );
	
	// [[[ begin generated region: do not modify [Generated Methods]
	// ]]] end generated region [Generated Methods]
	
	// ]]] end [Public Section]
	
	
	// [[[ begin [Protected Section]
protected:
	// from base class CAknView
	void DoActivateL(
		const TVwsViewId& aPrevViewId,
		TUid aCustomMessageId,
		const TDesC8& aCustomMessage );
	void DoDeactivate();
	void HandleStatusPaneSizeChange();
	
	// [[[ begin generated region: do not modify [Overridden Methods]
	// ]]] end generated region [Overridden Methods]
	
	
	// [[[ begin [User Handlers]
	TBool HandleChangeSelectedSettingItemL( TInt aCommand );
	// ]]] end [User Handlers]
	
	// ]]] end [Protected Section]
	
	
	// [[[ begin [Private Section]
private:
	void SetupStatusPaneL();
	void CleanupStatusPane();
	
	// [[[ begin generated region: do not modify [Generated Instance Variables]
	CSettings_list_3_0SettingItemList* iSettings_list_3_0SettingItemList;
	TSettings_list_3_0SettingItemListSettings* iSettings;
	// ]]] end generated region [Generated Instance Variables]
	
	// [[[ begin generated region: do not modify [Generated Methods]
	// ]]] end generated region [Generated Methods]
	
	// ]]] end [Private Section]
	
	};

#endif // SETTINGS_LIST_3_0SETTINGITEMLISTVIEW_H
