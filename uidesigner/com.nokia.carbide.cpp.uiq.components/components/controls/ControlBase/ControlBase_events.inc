			
			<!-- MCoeControlObserver events -->
			<!-- Control -->
			<event name="EEventRequestExit" category="Control"
				group="Control"
			    handlerNameTemplate="Handle{title(control->name)}EEventRequestExitL"
				displayName="%requestExit"
			    descriptionKey="requestExitDescription"
			/>
			<event name="EEventRequestCancel" category="Control"
				group="Control"
			    handlerNameTemplate="Handle{title(control->name)}EEventRequestCancelL"
				displayName="%requestCancel"
			    descriptionKey="requestCancelDescription"
			/>
			<event name="EEventRequestFocus" category="Control"
				group="Control"
			    handlerNameTemplate="Handle{title(control->name)}EEventRequestFocusL"
				displayName="%requestFocus"
			    descriptionKey="requestFocusDescription"
			/>
			<event name="EEventPrepareFocusTransition" category="Control"
				group="Control"
			    handlerNameTemplate="Handle{title(control->name)}EEventPrepareFocusTransitionL"
				displayName="%prepareFocusTransition"
			    descriptionKey="prepareFocusTransitionDescription"
			/>
			<event name="EEventStateChanged" category="Control"
				group="Control"
			    handlerNameTemplate="Handle{title(control->name)}EEventStateChangedL"
				displayName="%stateChanged"
			    descriptionKey="stateChangedDescription"
			/>
			<event name="EEventInteractionRefused" category="Control"
				group="Control"
			    handlerNameTemplate="Handle{title(control->name)}EEventInteractionRefusedL"
				displayName="%interactionRefused"
			    descriptionKey="interactionRefusedDescription"
			/>

			<!-- Container -->
			<event name="EEventRequestExit" category="Control" 
				group="Container"
			    handlerNameTemplate="Handle{title(name)}EEventRequestExitL"
				displayName="%requestExit"
			    descriptionKey="requestExitDescription"
			/>			
			<event name="EEventRequestCancel" category="Control" 
				group="Container"
			    handlerNameTemplate="Handle{title(name)}EEventRequestCancelL"
				displayName="%requestCancel"
			    descriptionKey="requestCancelDescription"
			/>			
			<event name="EEventRequestFocus" category="Control" 
				group="Container"
			    handlerNameTemplate="Handle{title(name)}EEventRequestFocusL"
				displayName="%requestFocus"
			    descriptionKey="requestFocusDescription"
			/>			
			<event name="EEventPrepareFocusTransition" category="Control" 
				group="Container"
			    handlerNameTemplate="Handle{title(name)}EEventPrepareFocusTransitionL"
				displayName="%prepareFocusTransition"
			    descriptionKey="prepareFocusTransitionDescription"
			/>			
			<event name="EEventStateChanged" category="Control" 
				group="Container"
			    handlerNameTemplate="Handle{title(name)}EEventStateChangedL"
				displayName="%stateChanged"
			    descriptionKey="stateChangedDescription"
			/>	
			<event name="EEventInteractionRefused" category="Control" 
				group="Container"
			    handlerNameTemplate="Handle{title(name)}EEventInteractionRefusedL"
				displayName="%interactionRefused"
			    descriptionKey="interactionRefusedDescription"
			/>			
			