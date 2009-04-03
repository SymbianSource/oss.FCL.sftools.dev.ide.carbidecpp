
#ifndef LISTS_3_0_PAN_H
#define LISTS_3_0_PAN_H

/** lists_3_0 application panic codes */
enum Tlists_3_0Panics
	{
	Elists_3_0Ui = 1
	// add further panics here
	};

inline void Panic(Tlists_3_0Panics aReason)
	{
	_LIT(applicationName,"lists_3_0");
	User::Panic(applicationName, aReason);
	}

#endif // LISTS_3_0_PAN_H
