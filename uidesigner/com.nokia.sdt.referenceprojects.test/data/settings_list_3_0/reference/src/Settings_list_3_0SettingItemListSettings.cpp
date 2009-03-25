/**
 *	Generated helper class which manages the settings contained 
 *	in 'Settings_list_3_0SettingItemList'.  Each CAknSettingItem maintains
 *	a reference to data in this class so that changes in the setting
 *	item list can be synchronized with this storage.
 */
 
// [[[ begin generated region: do not modify [Generated Includes]
#include <e32base.h>
#include <stringloader.h>
#include <barsread.h>
#include <settings_list_3_0.rsg>
#include "Settings_list_3_0SettingItemListSettings.h"
// ]]] end generated region [Generated Includes]

/**
 * C/C++ constructor for settings data, cannot throw
 */
TSettings_list_3_0SettingItemListSettings::TSettings_list_3_0SettingItemListSettings()
	{
	}

/**
 * Two-phase constructor for settings data
 */
TSettings_list_3_0SettingItemListSettings* TSettings_list_3_0SettingItemListSettings::NewL()
	{
	TSettings_list_3_0SettingItemListSettings* data = new( ELeave ) TSettings_list_3_0SettingItemListSettings;
	CleanupStack::PushL( data );
	data->ConstructL();
	CleanupStack::Pop( data );
	return data;
	}
	
/**
 *	Second phase for initializing settings data
 */
void TSettings_list_3_0SettingItemListSettings::ConstructL()
	{
	// [[[ begin generated region: do not modify [Generated Initializers]
		{
		HBufC* text = StringLoader::LoadLC( R_SETTINGS_LIST_3_0_SETTING_ITEM_LIST_EDIT1 );
		SetEdit1( text->Des() );
		CleanupStack::PopAndDestroy( text );
		}
	SetEnumeratedTextPopup1( 1 );
	SetTimeEditor1( TTime( TDateTime( 0, EJanuary, 0, 0, 0, 0, 0 ) ) );
	// ]]] end generated region [Generated Initializers]
	
	}
	
// [[[ begin generated region: do not modify [Generated Contents]
TDes& TSettings_list_3_0SettingItemListSettings::Edit1()
	{
	return iEdit1;
	}

void TSettings_list_3_0SettingItemListSettings::SetEdit1(const TDesC& aValue)
	{
	if ( aValue.Length() < KEdit1MaxLength)
		iEdit1.Copy( aValue );
	else
		iEdit1.Copy( aValue.Ptr(), KEdit1MaxLength);
	}

TDes& TSettings_list_3_0SettingItemListSettings::Secret1()
	{
	return iSecret1;
	}

void TSettings_list_3_0SettingItemListSettings::SetSecret1(const TDesC& aValue)
	{
	if ( aValue.Length() < KSecret1MaxLength)
		iSecret1.Copy( aValue );
	else
		iSecret1.Copy( aValue.Ptr(), KSecret1MaxLength);
	}

TInt& TSettings_list_3_0SettingItemListSettings::EnumeratedTextPopup1()
	{
	return iEnumeratedTextPopup1;
	}

void TSettings_list_3_0SettingItemListSettings::SetEnumeratedTextPopup1(const TInt& aValue)
	{
	iEnumeratedTextPopup1 = aValue;
	}

TTime& TSettings_list_3_0SettingItemListSettings::TimeEditor1()
	{
	return iTimeEditor1;
	}

void TSettings_list_3_0SettingItemListSettings::SetTimeEditor1(const TTime& aValue)
	{
	iTimeEditor1 = aValue;
	}

// ]]] end generated region [Generated Contents]

