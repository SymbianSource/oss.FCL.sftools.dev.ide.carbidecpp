<!-- START_USECASES: CU13 END_USECASES -->

<!-- BEGIN EVENT listBoxEventItemTapped -->
<templateGroup id="GenerateListBoxItemTappedEvent" ifEvents="listBoxEventItemTapped">
	<template id="ItemTappedEventCase" phase="InitializeControlHandlers">
<![CDATA[
AddListBoxEventHandlerL( 
		${this.getInstanceMemberName(instance)}, 
		EEventItemTapped, 
		&${className}::${event.handlerName} );]]>
	</template>
	<expandMacro name="GenerateUserEventHandlerFunction"
		FunctionLocationId="ListBoxItemTappedEventHandler"
		DeclPhase="UserHandlers"
		DefnLocation="SOURCE_FILE"
		FunctionArgs="CQikListBox* /*aListBox*/, TQikListBoxEvent /*aEventType*/, TInt /*aItemIndex*/, TInt /*aSlotId*/"/>
</templateGroup>
<!-- END EVENT listBoxEventItemTapped -->

<!-- BEGIN EVENT listBoxEventItemHighlighted -->
<templateGroup id="GenerateListBoxItemHighlightedEvent" ifEvents="listBoxEventItemHighlighted">
	<template id="ItemHighlightedEventCase" phase="InitializeControlHandlers">
<![CDATA[
AddListBoxEventHandlerL( 
		${this.getInstanceMemberName(instance)}, 
		EEventItemHighlighted, 
		&${className}::${event.handlerName} );]]>
	</template>
	<expandMacro name="GenerateUserEventHandlerFunction"
		FunctionLocationId="ListBoxItemHighlightedEventHandler"
		DeclPhase="UserHandlers"
		DefnLocation="SOURCE_FILE"
		FunctionArgs="CQikListBox* /*aListBox*/, TQikListBoxEvent /*aEventType*/, TInt /*aItemIndex*/, TInt /*aSlotId*/"/>
</templateGroup>
<!-- END EVENT listBoxEventItemHighlighted -->

<!-- BEGIN EVENT listBoxEventItemConfirmed -->
<templateGroup id="GenerateListBoxItemConfirmedEvent" ifEvents="listBoxEventItemConfirmed">
	<template id="ItemConfirmedEventCase" phase="InitializeControlHandlers">
<![CDATA[
AddListBoxEventHandlerL( 
		${this.getInstanceMemberName(instance)}, 
		EEventItemConfirmed, 
		&${className}::${event.handlerName} );]]>
	</template>
	<expandMacro name="GenerateUserEventHandlerFunction"
		FunctionLocationId="ListBoxItemConfirmedEventHandler"
		DeclPhase="UserHandlers"
		DefnLocation="SOURCE_FILE"
		FunctionArgs="CQikListBox* /*aListBox*/, TQikListBoxEvent /*aEventType*/, TInt /*aItemIndex*/, TInt /*aSlotId*/"/>
</templateGroup>
<!-- END EVENT listBoxEventItemConfirmed -->

<!-- BEGIN EVENT listBoxEventSelectionChanged -->
<templateGroup id="GenerateListBoxSelectionChangedEvent" ifEvents="listBoxEventSelectionChanged">
	<template id="SelectionChangedEventCase" phase="InitializeControlHandlers">
<![CDATA[
AddListBoxEventHandlerL( 
		${this.getInstanceMemberName(instance)}, 
		EEventSelectionChanged, 
		&${className}::${event.handlerName} );]]>
	</template>
	<expandMacro name="GenerateUserEventHandlerFunction"
		FunctionLocationId="ListBoxSelectionChangedEventHandler"
		DeclPhase="UserHandlers"
		DefnLocation="SOURCE_FILE"
		FunctionArgs="CQikListBox* /*aListBox*/, TQikListBoxEvent /*aEventType*/, TInt /*aItemIndex*/, TInt /*aSlotId*/"/>
</templateGroup>
<!-- END EVENT listBoxEventSelectionChanged -->

<!-- BEGIN EVENT listBoxEventMatchBufferChanged -->
<templateGroup id="GenerateListBoxMatchBufferChangedEvent" ifEvents="listBoxEventMatchBufferChanged">
	<template id="MatchBufferChangedEventCase" phase="InitializeControlHandlers">
<![CDATA[
AddListBoxEventHandlerL( 
		${this.getInstanceMemberName(instance)}, 
		EEventMatchBufferChanged, 
		&${className}::${event.handlerName} );]]>
	</template>
	<expandMacro name="GenerateUserEventHandlerFunction"
		FunctionLocationId="ListBoxMatchBufferChangedEventHandler"
		DeclPhase="UserHandlers"
		DefnLocation="SOURCE_FILE"
		FunctionArgs="CQikListBox* /*aListBox*/, TQikListBoxEvent /*aEventType*/, TInt /*aItemIndex*/, TInt /*aSlotId*/"/>
</templateGroup>
<!-- END EVENT listBoxEventMatchBufferChanged -->

<!-- BEGIN EVENT listBoxEventTopReached -->
<templateGroup id="GenerateListBoxTopReachedEvent" ifEvents="listBoxEventTopReached">
	<template id="TopReachedEventCase" phase="InitializeControlHandlers">
<![CDATA[
AddListBoxEventHandlerL( 
		${this.getInstanceMemberName(instance)}, 
		EEventTopReached, 
		&${className}::${event.handlerName} );]]>
	</template>
	<expandMacro name="GenerateUserEventHandlerFunction"
		FunctionLocationId="ListBoxTopReachedEventHandler"
		DeclPhase="UserHandlers"
		DefnLocation="SOURCE_FILE"
		FunctionArgs="CQikListBox* /*aListBox*/, TQikListBoxEvent /*aEventType*/, TInt /*aItemIndex*/, TInt /*aSlotId*/"/>
</templateGroup>
<!-- END EVENT listBoxEventTopReached -->

<!-- BEGIN EVENT listBoxEventBottomReached -->
<templateGroup id="GenerateListBoxBottomReachedEvent" ifEvents="listBoxEventBottomReached">
	<template id="BottomReachedEventCase" phase="InitializeControlHandlers">
<![CDATA[
AddListBoxEventHandlerL( 
		${this.getInstanceMemberName(instance)}, 
		EEventBottomReached, 
		&${className}::${event.handlerName} );]]>
	</template>
	<expandMacro name="GenerateUserEventHandlerFunction"
		FunctionLocationId="ListBoxBottomReachedEventHandler"
		DeclPhase="UserHandlers"
		DefnLocation="SOURCE_FILE"
		FunctionArgs="CQikListBox* /*aListBox*/, TQikListBoxEvent /*aEventType*/, TInt /*aItemIndex*/, TInt /*aSlotId*/"/>
</templateGroup>
<!-- END EVENT listBoxEventBottomReached -->

<!-- BEGIN EVENT listBoxEventEmptyListBoxActioned -->
<templateGroup id="GenerateListBoxEmptyListBoxActionedEvent" ifEvents="listBoxEventEmptyListBoxActioned">
	<template id="EmptyListBoxActionedEventCase" phase="InitializeControlHandlers">
<![CDATA[
AddListBoxEventHandlerL( 
		${this.getInstanceMemberName(instance)}, 
		EEventEmptyListBoxActioned, 
		&${className}::${event.handlerName} );]]>
	</template>
	<expandMacro name="GenerateUserEventHandlerFunction"
		FunctionLocationId="ListBoxEmptyListBoxActionedEventHandler"
		DeclPhase="UserHandlers"
		DefnLocation="SOURCE_FILE"
		FunctionArgs="CQikListBox* /*aListBox*/, TQikListBoxEvent /*aEventType*/, TInt /*aItemIndex*/, TInt /*aSlotId*/"/>
</templateGroup>
<!-- END EVENT listBoxEventEmptyListBoxActioned -->

<!-- BEGIN EVENT listBoxEventHighlightMoved -->
<templateGroup id="GenerateListBoxHighlightMovedEvent" ifEvents="listBoxEventHighlightMoved">
	<template id="HighlightMovedEventCase" phase="InitializeControlHandlers">
<![CDATA[
AddListBoxEventHandlerL( 
		${this.getInstanceMemberName(instance)}, 
		EEventHighlightMoved, 
		&${className}::${event.handlerName} );]]>
	</template>
	<expandMacro name="GenerateUserEventHandlerFunction"
		FunctionLocationId="ListBoxHighlightMovedEventHandler"
		DeclPhase="UserHandlers"
		DefnLocation="SOURCE_FILE"
		FunctionArgs="CQikListBox* /*aListBox*/, TQikListBoxEvent /*aEventType*/, TInt /*aItemIndex*/, TInt /*aSlotId*/"/>
</templateGroup>
<!-- END EVENT listBoxEventHighlightMoved -->

<!-- BEGIN EVENT listBoxEventSlotIndexChanged -->
<templateGroup id="GenerateListBoxSlotIndexChangedEvent" ifEvents="listBoxEventSlotIndexChanged">
	<template id="SlotIndexChangedEventCase" phase="InitializeControlHandlers">
<![CDATA[
AddListBoxEventHandlerL( 
		${this.getInstanceMemberName(instance)}, 
		EEventSlotIndexChanged, 
		&${className}::${event.handlerName} );]]>
	</template>
	<expandMacro name="GenerateUserEventHandlerFunction"
		FunctionLocationId="ListBoxSlotIndexChangedEventHandler"
		DeclPhase="UserHandlers"
		DefnLocation="SOURCE_FILE"
		FunctionArgs="CQikListBox* /*aListBox*/, TQikListBoxEvent /*aEventType*/, TInt /*aItemIndex*/, TInt /*aSlotId*/"/>
</templateGroup>
<!-- END EVENT listBoxEventSlotIndexChanged -->

<!-- BEGIN EVENT listBoxEventDimmedItemConfirmedAttempt -->
<templateGroup id="GenerateListBoxDimmedItemConfirmedAttemptEvent" ifEvents="listBoxEventDimmedItemConfirmedAttempt">
	<template id="DimmedItemConfirmedAttemptEventCase" phase="InitializeControlHandlers">
<![CDATA[
AddListBoxEventHandlerL( 
		${this.getInstanceMemberName(instance)}, 
		EEventDimmedItemConfirmedAttempt, 
		&${className}::${event.handlerName} );]]>
	</template>
	<expandMacro name="GenerateUserEventHandlerFunction"
		FunctionLocationId="ListBoxDimmedItemConfirmedAttemptEventHandler"
		DeclPhase="UserHandlers"
		DefnLocation="SOURCE_FILE"
		FunctionArgs="CQikListBox* /*aListBox*/, TQikListBoxEvent /*aEventType*/, TInt /*aItemIndex*/, TInt /*aSlotId*/"/>
</templateGroup>
<!-- END EVENT listBoxEventDimmedItemConfirmedAttempt -->

<!-- BEGIN EVENT listBoxEventMatchBufferFull -->
<templateGroup id="GenerateListBoxMatchBufferFullEvent" ifEvents="listBoxEventMatchBufferFull">
	<template id="MatchBufferFullEventCase" phase="InitializeControlHandlers">
<![CDATA[
AddListBoxEventHandlerL( 
		${this.getInstanceMemberName(instance)}, 
		EEventMatchBufferFull, 
		&${className}::${event.handlerName} );]]>
	</template>
	<expandMacro name="GenerateUserEventHandlerFunction"
		FunctionLocationId="ListBoxMatchBufferFullEventHandler"
		DeclPhase="UserHandlers"
		DefnLocation="SOURCE_FILE"
		FunctionArgs="CQikListBox* /*aListBox*/, TQikListBoxEvent /*aEventType*/, TInt /*aItemIndex*/, TInt /*aSlotId*/"/>
</templateGroup>
<!-- END EVENT listBoxEventMatchBufferFull -->
