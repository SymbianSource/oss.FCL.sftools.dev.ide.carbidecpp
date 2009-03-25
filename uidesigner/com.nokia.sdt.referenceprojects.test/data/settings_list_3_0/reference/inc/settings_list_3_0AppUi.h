#ifndef SETTINGS_LIST_3_0APPUI_H
#define SETTINGS_LIST_3_0APPUI_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <aknviewappui.h>
// ]]] end generated region [Generated Includes]

// [[[ begin generated region: do not modify [Generated Forward Declarations]
class CSettings_list_3_0SettingItemListView;
// ]]] end generated region [Generated Forward Declarations]

/**
 * @class	Csettings_list_3_0AppUi settings_list_3_0AppUi.h
 * @brief The AppUi class handles application-wide aspects of the user interface, including
 *        view management and the default menu, control pane, and status pane.
 */
class Csettings_list_3_0AppUi : public CAknViewAppUi
	{
public: 
	// constructor and destructor
	Csettings_list_3_0AppUi();
	virtual ~Csettings_list_3_0AppUi();
	void ConstructL();

public:
	// from CCoeAppUi
	TKeyResponse HandleKeyEventL(
				const TKeyEvent& aKeyEvent,
				TEventCode aType );

	// from CEikAppUi
	void HandleCommandL( TInt aCommand );
	void HandleResourceChangeL( TInt aType );

	// from CAknAppUi
	void HandleViewDeactivation( 
			const TVwsViewId& aViewIdToBeDeactivated, 
			const TVwsViewId& aNewlyActivatedViewId );

private:
	void InitializeContainersL();
	// [[[ begin generated region: do not modify [Generated Methods]
public: 
	// ]]] end generated region [Generated Methods]
	
	// [[[ begin generated region: do not modify [Generated Instance Variables]
private: 
	CSettings_list_3_0SettingItemListView* iSettings_list_3_0SettingItemListView;
	// ]]] end generated region [Generated Instance Variables]
	
	
	// [[[ begin [User Handlers]
protected: 
	// ]]] end [User Handlers]
	
	};

#endif // SETTINGS_LIST_3_0APPUI_H			
