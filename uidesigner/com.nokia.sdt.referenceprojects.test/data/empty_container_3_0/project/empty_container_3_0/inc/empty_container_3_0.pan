
#ifndef EMPTY_CONTAINER_3_0_PAN_H
#define EMPTY_CONTAINER_3_0_PAN_H

/** empty_container_3_0 application panic codes */
enum Tempty_container_3_0Panics
	{
	Eempty_container_3_0Ui = 1
	// add further panics here
	};

inline void Panic(Tempty_container_3_0Panics aReason)
	{
	_LIT(applicationName,"empty_container_3_0");
	User::Panic(applicationName, aReason);
	}

#endif // EMPTY_CONTAINER_3_0_PAN_H
