#ifndef LISTS_3_0APPUI_H
#define LISTS_3_0APPUI_H

// [[[ begin generated region: do not modify [Generated Includes]
#include <aknviewappui.h>
// ]]] end generated region [Generated Includes]

// [[[ begin generated region: do not modify [Generated Forward Declarations]
class Clists_3_0ListBoxView;
class Clists_3_0ListBox2View;
// ]]] end generated region [Generated Forward Declarations]

/**
 * @class	Clists_3_0AppUi lists_3_0AppUi.h
 * @brief The AppUi class handles application-wide aspects of the user interface, including
 *        view management and the default menu, control pane, and status pane.
 */
class Clists_3_0AppUi : public CAknViewAppUi
	{
public: 
	// constructor and destructor
	Clists_3_0AppUi();
	virtual ~Clists_3_0AppUi();
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
	Clists_3_0ListBoxView* iLists_3_0ListBoxView;
	Clists_3_0ListBox2View* iLists_3_0ListBox2View;
	// ]]] end generated region [Generated Instance Variables]
	
	
	// [[[ begin [User Handlers]
protected: 
	// ]]] end [User Handlers]
	
	};

#endif // LISTS_3_0APPUI_H			
