
#ifndef SETTINGS_LIST_3_0_PAN_H
#define SETTINGS_LIST_3_0_PAN_H

/** settings_list_3_0 application panic codes */
enum Tsettings_list_3_0Panics
	{
	Esettings_list_3_0Ui = 1
	// add further panics here
	};

inline void Panic(Tsettings_list_3_0Panics aReason)
	{
	_LIT(applicationName,"settings_list_3_0");
	User::Panic(applicationName, aReason);
	}

#endif // SETTINGS_LIST_3_0_PAN_H
