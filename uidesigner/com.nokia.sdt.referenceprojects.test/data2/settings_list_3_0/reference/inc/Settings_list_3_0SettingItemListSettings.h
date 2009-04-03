#ifndef SETTINGS_LIST_3_0SETTINGITEMLISTSETTINGS_H
#define SETTINGS_LIST_3_0SETTINGITEMLISTSETTINGS_H
			
// [[[ begin generated region: do not modify [Generated Includes]
#include <e32std.h>
// ]]] end generated region [Generated Includes]

// [[[ begin generated region: do not modify [Generated Constants]
const int KEdit1MaxLength = 255;
const int KSecret1MaxLength = 8;
// ]]] end generated region [Generated Constants]

/**
 * @class	TSettings_list_3_0SettingItemListSettings Settings_list_3_0SettingItemListSettings.h
 */
class TSettings_list_3_0SettingItemListSettings
	{
public:
	// construct and destroy
	static TSettings_list_3_0SettingItemListSettings* NewL();
	void ConstructL();
		
private:
	// constructor
	TSettings_list_3_0SettingItemListSettings();
	// [[[ begin generated region: do not modify [Generated Accessors]
public:
	TDes& Edit1();
	void SetEdit1(const TDesC& aValue);
	TDes& Secret1();
	void SetSecret1(const TDesC& aValue);
	TInt& EnumeratedTextPopup1();
	void SetEnumeratedTextPopup1(const TInt& aValue);
	TTime& TimeEditor1();
	void SetTimeEditor1(const TTime& aValue);
	// ]]] end generated region [Generated Accessors]
	
	// [[[ begin generated region: do not modify [Generated Members]
protected:
	TBuf<KEdit1MaxLength> iEdit1;
	TBuf<KSecret1MaxLength> iSecret1;
	TInt iEnumeratedTextPopup1;
	TTime iTimeEditor1;
	// ]]] end generated region [Generated Members]
	
	};
#endif // SETTINGS_LIST_3_0SETTINGITEMLISTSETTINGS_H
