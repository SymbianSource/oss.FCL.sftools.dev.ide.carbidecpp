
#ifndef TUI_5_0_PAN_H
#define TUI_5_0_PAN_H

/** TUI_5_0 application panic codes */
enum TTUI_5_0Panics
	{
	ETUI_5_0Ui = 1
	// add further panics here
	};

inline void Panic(TTUI_5_0Panics aReason)
	{
	_LIT(applicationName,"TUI_5_0");
	User::Panic(applicationName, aReason);
	}

#endif // TUI_5_0_PAN_H
