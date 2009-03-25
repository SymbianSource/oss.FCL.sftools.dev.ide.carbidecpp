		<!-- This component must have children of type CBA and StatusPane -->
		
  		  <mapResource struct="EIK_APP_INFO" headers="uikon.rh avkon.rsg">
  		    <select property="[com.nokia.sdt.series60.CBA]">
   		        <choice value="">
  		          <!-- no CBA -->
  		        </choice>
  		        <choice>
		 		  <select property="[com.nokia.sdt.series60.CBA].info">
				    <choice value="com.nokia.sdt.series60.CBA.Type.CUSTOM">
				    	<!-- emit a reference to the resource generated -->
				        <mapReferenceMember property="[com.nokia.sdt.series60.CBA]" member="cba"/>
				    </choice>
				    <choice>
						<!-- else, emit the builtin -->
						<mapIdentifierMember property="[com.nokia.sdt.series60.CBA].info" member="cba" />
				    </choice> 
				  </select>
			    </choice>
			</select>
 		    <mapReferenceMember property="[com.nokia.sdt.series60.StatusPane]" member="status_pane" />
 		    <mapReferenceMember property="optionsMenu" member="menubar" />
		  </mapResource>
