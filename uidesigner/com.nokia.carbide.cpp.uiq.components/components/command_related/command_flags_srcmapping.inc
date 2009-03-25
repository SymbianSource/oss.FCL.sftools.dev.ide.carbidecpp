<!--START_USECASES: CU11 END_USECASES-->

		<!-- This maps the common state and cpf flags for command base and command components -->

				<mapSimpleMember property="text" member="text"></mapSimpleMember>						
				<mapSimpleMember property="shortText" member="shortText"></mapSimpleMember>						
				<mapSimpleMember property="priority" member="priority"></mapSimpleMember>						
				<mapMemberFromType property="type" member="type"/>

				<mapBitmaskMember property="stateFlags" member="stateFlags">
					<mapBitmaskValue properties="EQikCmdFlagDimmed" value="EQikCmdFlagDimmed"/>
					<mapBitmaskValue properties="EQikCmdFlagCheckBox" value="EQikCmdFlagCheckBox"/>
					<mapBitmaskValue properties="EQikCmdFlagRadioStart" value="EQikCmdFlagRadioStart"/>
					<mapBitmaskValue properties="EQikCmdFlagRadioMiddle" value="EQikCmdFlagRadioMiddle"/> 
					<mapBitmaskValue properties="EQikCmdFlagRadioEnd" value="EQikCmdFlagRadioEnd"/>
					<mapBitmaskValue properties="EQikCmdFlagSymbolOn" value="EQikCmdFlagSymbolOn"/>
					<mapBitmaskValue properties="EQikCmdFlagInlinePane" value="EQikCmdFlagInlinePane"/>
					<mapBitmaskValue properties="EQikCmdFlagSortAlphabetic" value="EQikCmdFlagSortAlphabetic"/>
					<mapBitmaskValue properties="EQikCmdFlagUnavailable" value="EQikCmdFlagUnavailable"/>
					<mapBitmaskValue properties="EQikCmdFlagInvisible" value="EQikCmdFlagInvisible"/>
					<mapBitmaskValue properties="EQikCmdFlagDebugOnly" value="EQikCmdFlagDebugOnly"/>
				</mapBitmaskMember>				

				<mapBitmaskMember property="cpfFlags" member="cpfFlags">
					<mapBitmaskValue properties="EQikCpfFlagReplaceContainerPopoutDone" value="EQikCpfFlagReplaceContainerPopoutDone"/>
					<mapBitmaskValue properties="EQikCpfFlagPreferToBePlacedInButtonbar" value="EQikCpfFlagPreferToBePlacedInButtonbar"/>
					<mapBitmaskValue properties="EQikCpfFlagDuplicateInMenuPane" value="EQikCpfFlagDuplicateInMenuPane"/>
					<mapBitmaskValue properties="EQikCpfFlagOkToExclude" value="EQikCpfFlagOkToExclude"/> 
					<mapBitmaskValue properties="EQikCpfFlagIsDefault" value="EQikCpfFlagIsDefault"/>
					<mapBitmaskValue properties="EQikCpfFlagDoneCommandPreferToBePlacedOnPositiveSoftkey" value="EQikCpfFlagDoneCommandPreferToBePlacedOnPositiveSoftkey"/>
					<mapBitmaskValue properties="EQikCpfFlagHardwarekeyOnly" value="EQikCpfFlagHardwarekeyOnly"/>
					<mapBitmaskValue properties="EQikCpfFlagTouchscreenOnly" value="EQikCpfFlagTouchscreenOnly"/>
					<mapBitmaskValue properties="EQikCpfFlagNoTouchscreenOnly" value="EQikCpfFlagNoTouchscreenOnly"/>
					<mapBitmaskValue properties="EQikCpfFlagInteractionMenubarOnly" value="EQikCpfFlagInteractionMenubarOnly"/>
					<mapBitmaskValue properties="EQikCpfFlagInteractionSoftkeysOnly" value="EQikCpfFlagInteractionSoftkeysOnly"/>
					<mapBitmaskValue properties="EQikCpfFlagPortraitOnly" value="EQikCpfFlagPortraitOnly"/>
					<mapBitmaskValue properties="EQikCpfFlagLandscapeOnly" value="EQikCpfFlagLandscapeOnly"/>
					<mapBitmaskValue properties="EQikCpfFlagExecuteRepeat" value="EQikCpfFlagExecuteRepeat"/>
					<mapBitmaskValue properties="EQikCommandGroupIdBeforeCategoryCommands" value="EQikCommandGroupIdBeforeCategoryCommands"/>
					<mapBitmaskValue properties="EQikCommandGroupIdCategoryCommands" value="EQikCommandGroupIdCategoryCommands"/>
					<mapBitmaskValue properties="EQikCommandGroupIdAfterCategoryCommands" value="EQikCommandGroupIdAfterCategoryCommands"/>
				</mapBitmaskMember>				

				<select property="icon.bmpfile">
					<choice value="">
					</choice>
					<choice>
						<mapResourceMember property="." member="icon" struct="QIK_CONTENT_MBM" headers="qikon.rh">
							<mapSimpleMember property="icon.bmpfile" member="bmpfile"/>	
				            <select property="icon.bmpid">
								<choice value="">
								</choice>
					            <choice>	
									<mapEnumMember property="icon.bmpid" member="bmpid" validate="false"/>				
								</choice>
							</select>
							<select property="icon.bmpmask">
								<choice value="">
								</choice>
					            <choice>	
									<mapEnumMember property="icon.bmpmask" member="bmpmask" validate="false"/>						
								</choice>
							</select>					
						</mapResourceMember>
					</choice>
				</select>
				
				<select property="anonymousGroup">
					<choice value="">
					</choice>
					<choice>
						<mapEnumMember property="anonymousGroup->"
							member="groupId"
							nameAlgorithm="com.nokia.carbide.uiq.NAME_ALG_GROUP_ID"
							uniqueValue="*"/>
					</choice>
				</select>
				
				<select property="namedGroup">
					<choice value="">
					</choice>
					<choice>
						<mapEnumMember property="namedGroup->"
							member="namedGroupId"
							nameAlgorithm="com.nokia.carbide.uiq.NAME_ALG_NAMEDGROUP_ID"
							uniqueValue="*"/>
					</choice>
				</select>
