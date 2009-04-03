
#ifndef $(baseNameUpper)_PAN_H
#define $(baseNameUpper)_PAN_H

/** $(baseName) application panic codes */
enum T$(baseName)Panics
    {
    E$(baseName)Ui = 1
    // add further panics here
    };

inline void Panic(T$(baseName)Panics aReason)
    {
    _LIT(applicationName,"$(baseName)");
    User::Panic(applicationName, aReason);
    }

#endif // $(baseNameUpper)_PAN_H
