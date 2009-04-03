
#ifndef FORM_ALL_3_0_PAN_H
#define FORM_ALL_3_0_PAN_H

/** form_all_3_0 application panic codes */
enum Tform_all_3_0Panics
	{
	Eform_all_3_0Ui = 1
	// add further panics here
	};

inline void Panic(Tform_all_3_0Panics aReason)
	{
	_LIT(applicationName,"form_all_3_0");
	User::Panic(applicationName, aReason);
	}

#endif // FORM_ALL_3_0_PAN_H
