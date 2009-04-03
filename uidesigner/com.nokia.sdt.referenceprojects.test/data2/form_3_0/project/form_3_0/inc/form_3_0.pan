
#ifndef FORM_3_0_PAN_H
#define FORM_3_0_PAN_H

/** form_3_0 application panic codes */
enum Tform_3_0Panics
	{
	Eform_3_0Ui = 1
	// add further panics here
	};

inline void Panic(Tform_3_0Panics aReason)
	{
	_LIT(applicationName,"form_3_0");
	User::Panic(applicationName, aReason);
	}

#endif // FORM_3_0_PAN_H
