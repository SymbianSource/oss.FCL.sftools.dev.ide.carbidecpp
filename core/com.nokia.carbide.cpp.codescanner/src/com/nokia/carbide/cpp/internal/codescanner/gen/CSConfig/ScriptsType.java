/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
* All rights reserved.
* This component and the accompanying materials are made available
* under the terms of the License "Eclipse Public License v1.0"
* which accompanies this distribution, and is available
* at the URL "http://www.eclipse.org/legal/epl-v10.html".
*
* Initial Contributors:
* Nokia Corporation - initial contribution.
*
* Contributors:
*
* Description: 
*
*/

package com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Scripts Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getAccessArrayElementWithoutCheck <em>Access Array Element Without Check</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getAccessArrayElementWithoutCheck2 <em>Access Array Element Without Check2</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getActivestart <em>Activestart</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getActivestop <em>Activestop</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getArraypassing <em>Arraypassing</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getArrayptrcleanup <em>Arrayptrcleanup</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getAssertdebuginvariant <em>Assertdebuginvariant</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getBaddefines <em>Baddefines</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getBaseconstruct <em>Baseconstruct</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getCallActiveObjectWithoutCheckingOrStopping <em>Call Active Object Without Checking Or Stopping</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getChangenotification <em>Changenotification</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getCleanup <em>Cleanup</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getCommentcode <em>Commentcode</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getConnect <em>Connect</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getConnectAndDontCloseMemberVariable <em>Connect And Dont Close Member Variable</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getConstnames <em>Constnames</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getConsttdescptr <em>Consttdescptr</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getControlornull <em>Controlornull</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getCrepository <em>Crepository</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getCtltargettype <em>Ctltargettype</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getCustomizableicons <em>Customizableicons</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getDebugrom <em>Debugrom</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getDeclarename <em>Declarename</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getDeleteMemberVariable <em>Delete Member Variable</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getDestructor <em>Destructor</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getDoubleSemiColon <em>Double Semi Colon</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getDriveletters <em>Driveletters</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getEikbuttons <em>Eikbuttons</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getEikonenvstatic <em>Eikonenvstatic</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getEnummembers <em>Enummembers</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getEnumnames <em>Enumnames</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getExportinline <em>Exportinline</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getExportpurevirtual <em>Exportpurevirtual</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getExternaldriveletters <em>Externaldriveletters</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getFlags <em>Flags</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getFoff <em>Foff</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getForbiddenwords <em>Forbiddenwords</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getForgottoputptroncleanupstack <em>Forgottoputptroncleanupstack</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getFriend <em>Friend</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getGoto <em>Goto</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getIfassignments <em>Ifassignments</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getIfpreprocessor <em>Ifpreprocessor</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getInheritanceorder <em>Inheritanceorder</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getIntleaves <em>Intleaves</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getJmp <em>Jmp</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getLeave <em>Leave</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getLeaveNoError <em>Leave No Error</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getLeavingoperators <em>Leavingoperators</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getLFunctionCantLeave <em>LFunction Cant Leave</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getLonglines <em>Longlines</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getMagicnumbers <em>Magicnumbers</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getMclassdestructor <em>Mclassdestructor</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getMemberlc <em>Memberlc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getMembervariablecallld <em>Membervariablecallld</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getMissingcancel <em>Missingcancel</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getMissingcclass <em>Missingcclass</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getMmpsourcepath <em>Mmpsourcepath</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getMultilangrsc <em>Multilangrsc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getMultipledeclarations <em>Multipledeclarations</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getMultipleinheritance <em>Multipleinheritance</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getMydocs <em>Mydocs</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getNewlreferences <em>Newlreferences</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getNoleavetrap <em>Noleavetrap</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getNonconsthbufc <em>Nonconsthbufc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getNonconsttdesc <em>Nonconsttdesc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getNonleavenew <em>Nonleavenew</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getNonunicodeskins <em>Nonunicodeskins</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getNull <em>Null</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getOpen <em>Open</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getPointertoarrays <em>Pointertoarrays</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getPragmadisable <em>Pragmadisable</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getPragmamessage <em>Pragmamessage</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getPragmaother <em>Pragmaother</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getPrivateinheritance <em>Privateinheritance</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getPushaddrvar <em>Pushaddrvar</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getPushmember <em>Pushmember</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getReadresource <em>Readresource</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getResourcenotoncleanupstack <em>Resourcenotoncleanupstack</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getResourcesonheap <em>Resourcesonheap</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getReturndescriptoroutofscope <em>Returndescriptoroutofscope</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getRfs <em>Rfs</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getRssnames <em>Rssnames</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getStringliterals <em>Stringliterals</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getStringsinresourcefiles <em>Stringsinresourcefiles</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getStruct <em>Struct</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getTcclasses <em>Tcclasses</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getTclassdestructor <em>Tclassdestructor</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getTodocomments <em>Todocomments</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getTrapcleanup <em>Trapcleanup</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getTrapeleave <em>Trapeleave</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getTraprunl <em>Traprunl</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getTrspassing <em>Trspassing</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getUids <em>Uids</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getUncompressedaif <em>Uncompressedaif</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getUncompressedbmp <em>Uncompressedbmp</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getUnicodesource <em>Unicodesource</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getUserafter <em>Userafter</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getUserfree <em>Userfree</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getUserWaitForRequest <em>User Wait For Request</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getVariablenames <em>Variablenames</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getVoidparameter <em>Voidparameter</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getWorryingcomments <em>Worryingcomments</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType()
 * @model extendedMetaData="name='scripts_._type' kind='mixed'"
 * @generated
 */
public interface ScriptsType extends EObject {
	/**
	 * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mixed</em>' attribute list.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

	/**
	 * Returns the value of the '<em><b>Access Array Element Without Check</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Access Array Element Without Check</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Access Array Element Without Check</em>' containment reference.
	 * @see #setAccessArrayElementWithoutCheck(AccessArrayElementWithoutCheckType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_AccessArrayElementWithoutCheck()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='accessArrayElementWithoutCheck' namespace='##targetNamespace'"
	 * @generated
	 */
	AccessArrayElementWithoutCheckType getAccessArrayElementWithoutCheck();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getAccessArrayElementWithoutCheck <em>Access Array Element Without Check</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Access Array Element Without Check</em>' containment reference.
	 * @see #getAccessArrayElementWithoutCheck()
	 * @generated
	 */
	void setAccessArrayElementWithoutCheck(AccessArrayElementWithoutCheckType value);

	/**
	 * Returns the value of the '<em><b>Access Array Element Without Check2</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Access Array Element Without Check2</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Access Array Element Without Check2</em>' containment reference.
	 * @see #setAccessArrayElementWithoutCheck2(AccessArrayElementWithoutCheck2Type)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_AccessArrayElementWithoutCheck2()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='accessArrayElementWithoutCheck2' namespace='##targetNamespace'"
	 * @generated
	 */
	AccessArrayElementWithoutCheck2Type getAccessArrayElementWithoutCheck2();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getAccessArrayElementWithoutCheck2 <em>Access Array Element Without Check2</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Access Array Element Without Check2</em>' containment reference.
	 * @see #getAccessArrayElementWithoutCheck2()
	 * @generated
	 */
	void setAccessArrayElementWithoutCheck2(AccessArrayElementWithoutCheck2Type value);

	/**
	 * Returns the value of the '<em><b>Activestart</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Activestart</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Activestart</em>' containment reference.
	 * @see #setActivestart(ActivestartType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Activestart()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='activestart' namespace='##targetNamespace'"
	 * @generated
	 */
	ActivestartType getActivestart();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getActivestart <em>Activestart</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Activestart</em>' containment reference.
	 * @see #getActivestart()
	 * @generated
	 */
	void setActivestart(ActivestartType value);

	/**
	 * Returns the value of the '<em><b>Activestop</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Activestop</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Activestop</em>' containment reference.
	 * @see #setActivestop(ActivestopType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Activestop()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='activestop' namespace='##targetNamespace'"
	 * @generated
	 */
	ActivestopType getActivestop();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getActivestop <em>Activestop</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Activestop</em>' containment reference.
	 * @see #getActivestop()
	 * @generated
	 */
	void setActivestop(ActivestopType value);

	/**
	 * Returns the value of the '<em><b>Arraypassing</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Arraypassing</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arraypassing</em>' containment reference.
	 * @see #setArraypassing(ArraypassingType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Arraypassing()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='arraypassing' namespace='##targetNamespace'"
	 * @generated
	 */
	ArraypassingType getArraypassing();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getArraypassing <em>Arraypassing</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Arraypassing</em>' containment reference.
	 * @see #getArraypassing()
	 * @generated
	 */
	void setArraypassing(ArraypassingType value);

	/**
	 * Returns the value of the '<em><b>Arrayptrcleanup</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Arrayptrcleanup</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arrayptrcleanup</em>' containment reference.
	 * @see #setArrayptrcleanup(ArrayptrcleanupType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Arrayptrcleanup()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='arrayptrcleanup' namespace='##targetNamespace'"
	 * @generated
	 */
	ArrayptrcleanupType getArrayptrcleanup();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getArrayptrcleanup <em>Arrayptrcleanup</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Arrayptrcleanup</em>' containment reference.
	 * @see #getArrayptrcleanup()
	 * @generated
	 */
	void setArrayptrcleanup(ArrayptrcleanupType value);

	/**
	 * Returns the value of the '<em><b>Assertdebuginvariant</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assertdebuginvariant</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assertdebuginvariant</em>' containment reference.
	 * @see #setAssertdebuginvariant(AssertdebuginvariantType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Assertdebuginvariant()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='assertdebuginvariant' namespace='##targetNamespace'"
	 * @generated
	 */
	AssertdebuginvariantType getAssertdebuginvariant();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getAssertdebuginvariant <em>Assertdebuginvariant</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Assertdebuginvariant</em>' containment reference.
	 * @see #getAssertdebuginvariant()
	 * @generated
	 */
	void setAssertdebuginvariant(AssertdebuginvariantType value);

	/**
	 * Returns the value of the '<em><b>Baddefines</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Baddefines</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Baddefines</em>' containment reference.
	 * @see #setBaddefines(BaddefinesType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Baddefines()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='baddefines' namespace='##targetNamespace'"
	 * @generated
	 */
	BaddefinesType getBaddefines();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getBaddefines <em>Baddefines</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Baddefines</em>' containment reference.
	 * @see #getBaddefines()
	 * @generated
	 */
	void setBaddefines(BaddefinesType value);

	/**
	 * Returns the value of the '<em><b>Baseconstruct</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Baseconstruct</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Baseconstruct</em>' containment reference.
	 * @see #setBaseconstruct(BaseconstructType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Baseconstruct()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='baseconstruct' namespace='##targetNamespace'"
	 * @generated
	 */
	BaseconstructType getBaseconstruct();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getBaseconstruct <em>Baseconstruct</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Baseconstruct</em>' containment reference.
	 * @see #getBaseconstruct()
	 * @generated
	 */
	void setBaseconstruct(BaseconstructType value);

	/**
	 * Returns the value of the '<em><b>Call Active Object Without Checking Or Stopping</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Call Active Object Without Checking Or Stopping</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Call Active Object Without Checking Or Stopping</em>' containment reference.
	 * @see #setCallActiveObjectWithoutCheckingOrStopping(CallActiveObjectWithoutCheckingOrStoppingType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_CallActiveObjectWithoutCheckingOrStopping()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='callActiveObjectWithoutCheckingOrStopping' namespace='##targetNamespace'"
	 * @generated
	 */
	CallActiveObjectWithoutCheckingOrStoppingType getCallActiveObjectWithoutCheckingOrStopping();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getCallActiveObjectWithoutCheckingOrStopping <em>Call Active Object Without Checking Or Stopping</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Call Active Object Without Checking Or Stopping</em>' containment reference.
	 * @see #getCallActiveObjectWithoutCheckingOrStopping()
	 * @generated
	 */
	void setCallActiveObjectWithoutCheckingOrStopping(CallActiveObjectWithoutCheckingOrStoppingType value);

	/**
	 * Returns the value of the '<em><b>Changenotification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Changenotification</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Changenotification</em>' containment reference.
	 * @see #setChangenotification(ChangenotificationType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Changenotification()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='changenotification' namespace='##targetNamespace'"
	 * @generated
	 */
	ChangenotificationType getChangenotification();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getChangenotification <em>Changenotification</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Changenotification</em>' containment reference.
	 * @see #getChangenotification()
	 * @generated
	 */
	void setChangenotification(ChangenotificationType value);

	/**
	 * Returns the value of the '<em><b>Cleanup</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cleanup</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cleanup</em>' containment reference.
	 * @see #setCleanup(CleanupType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Cleanup()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='cleanup' namespace='##targetNamespace'"
	 * @generated
	 */
	CleanupType getCleanup();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getCleanup <em>Cleanup</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cleanup</em>' containment reference.
	 * @see #getCleanup()
	 * @generated
	 */
	void setCleanup(CleanupType value);

	/**
	 * Returns the value of the '<em><b>Commentcode</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Commentcode</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Commentcode</em>' containment reference.
	 * @see #setCommentcode(CommentcodeType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Commentcode()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='commentcode' namespace='##targetNamespace'"
	 * @generated
	 */
	CommentcodeType getCommentcode();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getCommentcode <em>Commentcode</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Commentcode</em>' containment reference.
	 * @see #getCommentcode()
	 * @generated
	 */
	void setCommentcode(CommentcodeType value);

	/**
	 * Returns the value of the '<em><b>Connect</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Connect</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Connect</em>' containment reference.
	 * @see #setConnect(ConnectType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Connect()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='connect' namespace='##targetNamespace'"
	 * @generated
	 */
	ConnectType getConnect();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getConnect <em>Connect</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Connect</em>' containment reference.
	 * @see #getConnect()
	 * @generated
	 */
	void setConnect(ConnectType value);

	/**
	 * Returns the value of the '<em><b>Connect And Dont Close Member Variable</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Connect And Dont Close Member Variable</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Connect And Dont Close Member Variable</em>' containment reference.
	 * @see #setConnectAndDontCloseMemberVariable(ConnectAndDontCloseMemberVariableType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_ConnectAndDontCloseMemberVariable()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ConnectAndDontCloseMemberVariable' namespace='##targetNamespace'"
	 * @generated
	 */
	ConnectAndDontCloseMemberVariableType getConnectAndDontCloseMemberVariable();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getConnectAndDontCloseMemberVariable <em>Connect And Dont Close Member Variable</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Connect And Dont Close Member Variable</em>' containment reference.
	 * @see #getConnectAndDontCloseMemberVariable()
	 * @generated
	 */
	void setConnectAndDontCloseMemberVariable(ConnectAndDontCloseMemberVariableType value);

	/**
	 * Returns the value of the '<em><b>Constnames</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constnames</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constnames</em>' containment reference.
	 * @see #setConstnames(ConstnamesType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Constnames()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='constnames' namespace='##targetNamespace'"
	 * @generated
	 */
	ConstnamesType getConstnames();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getConstnames <em>Constnames</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Constnames</em>' containment reference.
	 * @see #getConstnames()
	 * @generated
	 */
	void setConstnames(ConstnamesType value);

	/**
	 * Returns the value of the '<em><b>Consttdescptr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Consttdescptr</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Consttdescptr</em>' containment reference.
	 * @see #setConsttdescptr(ConsttdescptrType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Consttdescptr()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='consttdescptr' namespace='##targetNamespace'"
	 * @generated
	 */
	ConsttdescptrType getConsttdescptr();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getConsttdescptr <em>Consttdescptr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Consttdescptr</em>' containment reference.
	 * @see #getConsttdescptr()
	 * @generated
	 */
	void setConsttdescptr(ConsttdescptrType value);

	/**
	 * Returns the value of the '<em><b>Controlornull</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Controlornull</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Controlornull</em>' containment reference.
	 * @see #setControlornull(ControlornullType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Controlornull()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='controlornull' namespace='##targetNamespace'"
	 * @generated
	 */
	ControlornullType getControlornull();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getControlornull <em>Controlornull</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Controlornull</em>' containment reference.
	 * @see #getControlornull()
	 * @generated
	 */
	void setControlornull(ControlornullType value);

	/**
	 * Returns the value of the '<em><b>Crepository</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Crepository</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Crepository</em>' containment reference.
	 * @see #setCrepository(CrepositoryType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Crepository()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='crepository' namespace='##targetNamespace'"
	 * @generated
	 */
	CrepositoryType getCrepository();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getCrepository <em>Crepository</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Crepository</em>' containment reference.
	 * @see #getCrepository()
	 * @generated
	 */
	void setCrepository(CrepositoryType value);

	/**
	 * Returns the value of the '<em><b>Ctltargettype</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ctltargettype</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ctltargettype</em>' containment reference.
	 * @see #setCtltargettype(CtltargettypeType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Ctltargettype()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ctltargettype' namespace='##targetNamespace'"
	 * @generated
	 */
	CtltargettypeType getCtltargettype();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getCtltargettype <em>Ctltargettype</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ctltargettype</em>' containment reference.
	 * @see #getCtltargettype()
	 * @generated
	 */
	void setCtltargettype(CtltargettypeType value);

	/**
	 * Returns the value of the '<em><b>Customizableicons</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Customizableicons</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Customizableicons</em>' containment reference.
	 * @see #setCustomizableicons(CustomizableiconsType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Customizableicons()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='customizableicons' namespace='##targetNamespace'"
	 * @generated
	 */
	CustomizableiconsType getCustomizableicons();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getCustomizableicons <em>Customizableicons</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Customizableicons</em>' containment reference.
	 * @see #getCustomizableicons()
	 * @generated
	 */
	void setCustomizableicons(CustomizableiconsType value);

	/**
	 * Returns the value of the '<em><b>Debugrom</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Debugrom</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Debugrom</em>' containment reference.
	 * @see #setDebugrom(DebugromType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Debugrom()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='debugrom' namespace='##targetNamespace'"
	 * @generated
	 */
	DebugromType getDebugrom();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getDebugrom <em>Debugrom</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Debugrom</em>' containment reference.
	 * @see #getDebugrom()
	 * @generated
	 */
	void setDebugrom(DebugromType value);

	/**
	 * Returns the value of the '<em><b>Declarename</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Declarename</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declarename</em>' containment reference.
	 * @see #setDeclarename(DeclarenameType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Declarename()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='declarename' namespace='##targetNamespace'"
	 * @generated
	 */
	DeclarenameType getDeclarename();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getDeclarename <em>Declarename</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declarename</em>' containment reference.
	 * @see #getDeclarename()
	 * @generated
	 */
	void setDeclarename(DeclarenameType value);

	/**
	 * Returns the value of the '<em><b>Delete Member Variable</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delete Member Variable</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delete Member Variable</em>' containment reference.
	 * @see #setDeleteMemberVariable(DeleteMemberVariableType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_DeleteMemberVariable()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='deleteMemberVariable' namespace='##targetNamespace'"
	 * @generated
	 */
	DeleteMemberVariableType getDeleteMemberVariable();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getDeleteMemberVariable <em>Delete Member Variable</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delete Member Variable</em>' containment reference.
	 * @see #getDeleteMemberVariable()
	 * @generated
	 */
	void setDeleteMemberVariable(DeleteMemberVariableType value);

	/**
	 * Returns the value of the '<em><b>Destructor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Destructor</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Destructor</em>' containment reference.
	 * @see #setDestructor(DestructorType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Destructor()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='destructor' namespace='##targetNamespace'"
	 * @generated
	 */
	DestructorType getDestructor();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getDestructor <em>Destructor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Destructor</em>' containment reference.
	 * @see #getDestructor()
	 * @generated
	 */
	void setDestructor(DestructorType value);

	/**
	 * Returns the value of the '<em><b>Double Semi Colon</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Double Semi Colon</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Double Semi Colon</em>' containment reference.
	 * @see #setDoubleSemiColon(DoubleSemiColonType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_DoubleSemiColon()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='doubleSemiColon' namespace='##targetNamespace'"
	 * @generated
	 */
	DoubleSemiColonType getDoubleSemiColon();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getDoubleSemiColon <em>Double Semi Colon</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Double Semi Colon</em>' containment reference.
	 * @see #getDoubleSemiColon()
	 * @generated
	 */
	void setDoubleSemiColon(DoubleSemiColonType value);

	/**
	 * Returns the value of the '<em><b>Driveletters</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Driveletters</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Driveletters</em>' containment reference.
	 * @see #setDriveletters(DrivelettersType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Driveletters()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='driveletters' namespace='##targetNamespace'"
	 * @generated
	 */
	DrivelettersType getDriveletters();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getDriveletters <em>Driveletters</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Driveletters</em>' containment reference.
	 * @see #getDriveletters()
	 * @generated
	 */
	void setDriveletters(DrivelettersType value);

	/**
	 * Returns the value of the '<em><b>Eikbuttons</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Eikbuttons</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Eikbuttons</em>' containment reference.
	 * @see #setEikbuttons(EikbuttonsType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Eikbuttons()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='eikbuttons' namespace='##targetNamespace'"
	 * @generated
	 */
	EikbuttonsType getEikbuttons();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getEikbuttons <em>Eikbuttons</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Eikbuttons</em>' containment reference.
	 * @see #getEikbuttons()
	 * @generated
	 */
	void setEikbuttons(EikbuttonsType value);

	/**
	 * Returns the value of the '<em><b>Eikonenvstatic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Eikonenvstatic</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Eikonenvstatic</em>' containment reference.
	 * @see #setEikonenvstatic(EikonenvstaticType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Eikonenvstatic()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='eikonenvstatic' namespace='##targetNamespace'"
	 * @generated
	 */
	EikonenvstaticType getEikonenvstatic();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getEikonenvstatic <em>Eikonenvstatic</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Eikonenvstatic</em>' containment reference.
	 * @see #getEikonenvstatic()
	 * @generated
	 */
	void setEikonenvstatic(EikonenvstaticType value);

	/**
	 * Returns the value of the '<em><b>Enummembers</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enummembers</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enummembers</em>' containment reference.
	 * @see #setEnummembers(EnummembersType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Enummembers()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='enummembers' namespace='##targetNamespace'"
	 * @generated
	 */
	EnummembersType getEnummembers();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getEnummembers <em>Enummembers</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enummembers</em>' containment reference.
	 * @see #getEnummembers()
	 * @generated
	 */
	void setEnummembers(EnummembersType value);

	/**
	 * Returns the value of the '<em><b>Enumnames</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enumnames</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enumnames</em>' containment reference.
	 * @see #setEnumnames(EnumnamesType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Enumnames()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='enumnames' namespace='##targetNamespace'"
	 * @generated
	 */
	EnumnamesType getEnumnames();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getEnumnames <em>Enumnames</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enumnames</em>' containment reference.
	 * @see #getEnumnames()
	 * @generated
	 */
	void setEnumnames(EnumnamesType value);

	/**
	 * Returns the value of the '<em><b>Exportinline</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exportinline</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exportinline</em>' containment reference.
	 * @see #setExportinline(ExportinlineType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Exportinline()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='exportinline' namespace='##targetNamespace'"
	 * @generated
	 */
	ExportinlineType getExportinline();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getExportinline <em>Exportinline</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exportinline</em>' containment reference.
	 * @see #getExportinline()
	 * @generated
	 */
	void setExportinline(ExportinlineType value);

	/**
	 * Returns the value of the '<em><b>Exportpurevirtual</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exportpurevirtual</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exportpurevirtual</em>' containment reference.
	 * @see #setExportpurevirtual(ExportpurevirtualType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Exportpurevirtual()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='exportpurevirtual' namespace='##targetNamespace'"
	 * @generated
	 */
	ExportpurevirtualType getExportpurevirtual();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getExportpurevirtual <em>Exportpurevirtual</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exportpurevirtual</em>' containment reference.
	 * @see #getExportpurevirtual()
	 * @generated
	 */
	void setExportpurevirtual(ExportpurevirtualType value);

	/**
	 * Returns the value of the '<em><b>Externaldriveletters</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Externaldriveletters</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Externaldriveletters</em>' containment reference.
	 * @see #setExternaldriveletters(ExternaldrivelettersType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Externaldriveletters()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='externaldriveletters' namespace='##targetNamespace'"
	 * @generated
	 */
	ExternaldrivelettersType getExternaldriveletters();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getExternaldriveletters <em>Externaldriveletters</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Externaldriveletters</em>' containment reference.
	 * @see #getExternaldriveletters()
	 * @generated
	 */
	void setExternaldriveletters(ExternaldrivelettersType value);

	/**
	 * Returns the value of the '<em><b>Flags</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Flags</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Flags</em>' containment reference.
	 * @see #setFlags(FlagsType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Flags()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='flags' namespace='##targetNamespace'"
	 * @generated
	 */
	FlagsType getFlags();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getFlags <em>Flags</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Flags</em>' containment reference.
	 * @see #getFlags()
	 * @generated
	 */
	void setFlags(FlagsType value);

	/**
	 * Returns the value of the '<em><b>Foff</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Foff</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Foff</em>' containment reference.
	 * @see #setFoff(FoffType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Foff()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='foff' namespace='##targetNamespace'"
	 * @generated
	 */
	FoffType getFoff();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getFoff <em>Foff</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Foff</em>' containment reference.
	 * @see #getFoff()
	 * @generated
	 */
	void setFoff(FoffType value);

	/**
	 * Returns the value of the '<em><b>Forbiddenwords</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Forbiddenwords</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Forbiddenwords</em>' containment reference.
	 * @see #setForbiddenwords(ForbiddenwordsType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Forbiddenwords()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='forbiddenwords' namespace='##targetNamespace'"
	 * @generated
	 */
	ForbiddenwordsType getForbiddenwords();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getForbiddenwords <em>Forbiddenwords</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Forbiddenwords</em>' containment reference.
	 * @see #getForbiddenwords()
	 * @generated
	 */
	void setForbiddenwords(ForbiddenwordsType value);

	/**
	 * Returns the value of the '<em><b>Forgottoputptroncleanupstack</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Forgottoputptroncleanupstack</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Forgottoputptroncleanupstack</em>' containment reference.
	 * @see #setForgottoputptroncleanupstack(ForgottoputptroncleanupstackType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Forgottoputptroncleanupstack()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='forgottoputptroncleanupstack' namespace='##targetNamespace'"
	 * @generated
	 */
	ForgottoputptroncleanupstackType getForgottoputptroncleanupstack();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getForgottoputptroncleanupstack <em>Forgottoputptroncleanupstack</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Forgottoputptroncleanupstack</em>' containment reference.
	 * @see #getForgottoputptroncleanupstack()
	 * @generated
	 */
	void setForgottoputptroncleanupstack(ForgottoputptroncleanupstackType value);

	/**
	 * Returns the value of the '<em><b>Friend</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Friend</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Friend</em>' containment reference.
	 * @see #setFriend(FriendType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Friend()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='friend' namespace='##targetNamespace'"
	 * @generated
	 */
	FriendType getFriend();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getFriend <em>Friend</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Friend</em>' containment reference.
	 * @see #getFriend()
	 * @generated
	 */
	void setFriend(FriendType value);

	/**
	 * Returns the value of the '<em><b>Goto</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Goto</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Goto</em>' containment reference.
	 * @see #setGoto(GotoType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Goto()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='goto' namespace='##targetNamespace'"
	 * @generated
	 */
	GotoType getGoto();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getGoto <em>Goto</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Goto</em>' containment reference.
	 * @see #getGoto()
	 * @generated
	 */
	void setGoto(GotoType value);

	/**
	 * Returns the value of the '<em><b>Ifassignments</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ifassignments</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ifassignments</em>' containment reference.
	 * @see #setIfassignments(IfassignmentsType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Ifassignments()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ifassignments' namespace='##targetNamespace'"
	 * @generated
	 */
	IfassignmentsType getIfassignments();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getIfassignments <em>Ifassignments</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ifassignments</em>' containment reference.
	 * @see #getIfassignments()
	 * @generated
	 */
	void setIfassignments(IfassignmentsType value);

	/**
	 * Returns the value of the '<em><b>Ifpreprocessor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ifpreprocessor</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ifpreprocessor</em>' containment reference.
	 * @see #setIfpreprocessor(IfpreprocessorType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Ifpreprocessor()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ifpreprocessor' namespace='##targetNamespace'"
	 * @generated
	 */
	IfpreprocessorType getIfpreprocessor();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getIfpreprocessor <em>Ifpreprocessor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ifpreprocessor</em>' containment reference.
	 * @see #getIfpreprocessor()
	 * @generated
	 */
	void setIfpreprocessor(IfpreprocessorType value);

	/**
	 * Returns the value of the '<em><b>Inheritanceorder</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inheritanceorder</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inheritanceorder</em>' containment reference.
	 * @see #setInheritanceorder(InheritanceorderType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Inheritanceorder()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='inheritanceorder' namespace='##targetNamespace'"
	 * @generated
	 */
	InheritanceorderType getInheritanceorder();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getInheritanceorder <em>Inheritanceorder</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inheritanceorder</em>' containment reference.
	 * @see #getInheritanceorder()
	 * @generated
	 */
	void setInheritanceorder(InheritanceorderType value);

	/**
	 * Returns the value of the '<em><b>Intleaves</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Intleaves</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Intleaves</em>' containment reference.
	 * @see #setIntleaves(IntleavesType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Intleaves()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='intleaves' namespace='##targetNamespace'"
	 * @generated
	 */
	IntleavesType getIntleaves();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getIntleaves <em>Intleaves</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Intleaves</em>' containment reference.
	 * @see #getIntleaves()
	 * @generated
	 */
	void setIntleaves(IntleavesType value);

	/**
	 * Returns the value of the '<em><b>Jmp</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Jmp</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jmp</em>' containment reference.
	 * @see #setJmp(JmpType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Jmp()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='jmp' namespace='##targetNamespace'"
	 * @generated
	 */
	JmpType getJmp();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getJmp <em>Jmp</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Jmp</em>' containment reference.
	 * @see #getJmp()
	 * @generated
	 */
	void setJmp(JmpType value);

	/**
	 * Returns the value of the '<em><b>Leave</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Leave</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Leave</em>' containment reference.
	 * @see #setLeave(LeaveType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Leave()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='leave' namespace='##targetNamespace'"
	 * @generated
	 */
	LeaveType getLeave();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getLeave <em>Leave</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Leave</em>' containment reference.
	 * @see #getLeave()
	 * @generated
	 */
	void setLeave(LeaveType value);

	/**
	 * Returns the value of the '<em><b>Leave No Error</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Leave No Error</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Leave No Error</em>' containment reference.
	 * @see #setLeaveNoError(LeaveNoErrorType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_LeaveNoError()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='LeaveNoError' namespace='##targetNamespace'"
	 * @generated
	 */
	LeaveNoErrorType getLeaveNoError();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getLeaveNoError <em>Leave No Error</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Leave No Error</em>' containment reference.
	 * @see #getLeaveNoError()
	 * @generated
	 */
	void setLeaveNoError(LeaveNoErrorType value);

	/**
	 * Returns the value of the '<em><b>Leavingoperators</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Leavingoperators</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Leavingoperators</em>' containment reference.
	 * @see #setLeavingoperators(LeavingoperatorsType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Leavingoperators()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='leavingoperators' namespace='##targetNamespace'"
	 * @generated
	 */
	LeavingoperatorsType getLeavingoperators();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getLeavingoperators <em>Leavingoperators</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Leavingoperators</em>' containment reference.
	 * @see #getLeavingoperators()
	 * @generated
	 */
	void setLeavingoperators(LeavingoperatorsType value);

	/**
	 * Returns the value of the '<em><b>LFunction Cant Leave</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>LFunction Cant Leave</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>LFunction Cant Leave</em>' containment reference.
	 * @see #setLFunctionCantLeave(LFunctionCantLeaveType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_LFunctionCantLeave()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='LFunctionCantLeave' namespace='##targetNamespace'"
	 * @generated
	 */
	LFunctionCantLeaveType getLFunctionCantLeave();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getLFunctionCantLeave <em>LFunction Cant Leave</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>LFunction Cant Leave</em>' containment reference.
	 * @see #getLFunctionCantLeave()
	 * @generated
	 */
	void setLFunctionCantLeave(LFunctionCantLeaveType value);

	/**
	 * Returns the value of the '<em><b>Longlines</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Longlines</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Longlines</em>' containment reference.
	 * @see #setLonglines(LonglinesType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Longlines()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='longlines' namespace='##targetNamespace'"
	 * @generated
	 */
	LonglinesType getLonglines();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getLonglines <em>Longlines</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Longlines</em>' containment reference.
	 * @see #getLonglines()
	 * @generated
	 */
	void setLonglines(LonglinesType value);

	/**
	 * Returns the value of the '<em><b>Magicnumbers</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Magicnumbers</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Magicnumbers</em>' containment reference.
	 * @see #setMagicnumbers(MagicnumbersType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Magicnumbers()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='magicnumbers' namespace='##targetNamespace'"
	 * @generated
	 */
	MagicnumbersType getMagicnumbers();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getMagicnumbers <em>Magicnumbers</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Magicnumbers</em>' containment reference.
	 * @see #getMagicnumbers()
	 * @generated
	 */
	void setMagicnumbers(MagicnumbersType value);

	/**
	 * Returns the value of the '<em><b>Mclassdestructor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mclassdestructor</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mclassdestructor</em>' containment reference.
	 * @see #setMclassdestructor(MclassdestructorType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Mclassdestructor()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mclassdestructor' namespace='##targetNamespace'"
	 * @generated
	 */
	MclassdestructorType getMclassdestructor();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getMclassdestructor <em>Mclassdestructor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mclassdestructor</em>' containment reference.
	 * @see #getMclassdestructor()
	 * @generated
	 */
	void setMclassdestructor(MclassdestructorType value);

	/**
	 * Returns the value of the '<em><b>Memberlc</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Memberlc</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Memberlc</em>' containment reference.
	 * @see #setMemberlc(MemberlcType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Memberlc()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='memberlc' namespace='##targetNamespace'"
	 * @generated
	 */
	MemberlcType getMemberlc();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getMemberlc <em>Memberlc</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Memberlc</em>' containment reference.
	 * @see #getMemberlc()
	 * @generated
	 */
	void setMemberlc(MemberlcType value);

	/**
	 * Returns the value of the '<em><b>Membervariablecallld</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Membervariablecallld</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Membervariablecallld</em>' containment reference.
	 * @see #setMembervariablecallld(MembervariablecallldType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Membervariablecallld()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='membervariablecallld' namespace='##targetNamespace'"
	 * @generated
	 */
	MembervariablecallldType getMembervariablecallld();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getMembervariablecallld <em>Membervariablecallld</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Membervariablecallld</em>' containment reference.
	 * @see #getMembervariablecallld()
	 * @generated
	 */
	void setMembervariablecallld(MembervariablecallldType value);

	/**
	 * Returns the value of the '<em><b>Missingcancel</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missingcancel</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Missingcancel</em>' containment reference.
	 * @see #setMissingcancel(MissingcancelType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Missingcancel()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='missingcancel' namespace='##targetNamespace'"
	 * @generated
	 */
	MissingcancelType getMissingcancel();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getMissingcancel <em>Missingcancel</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Missingcancel</em>' containment reference.
	 * @see #getMissingcancel()
	 * @generated
	 */
	void setMissingcancel(MissingcancelType value);

	/**
	 * Returns the value of the '<em><b>Missingcclass</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Missingcclass</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Missingcclass</em>' containment reference.
	 * @see #setMissingcclass(MissingcclassType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Missingcclass()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='missingcclass' namespace='##targetNamespace'"
	 * @generated
	 */
	MissingcclassType getMissingcclass();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getMissingcclass <em>Missingcclass</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Missingcclass</em>' containment reference.
	 * @see #getMissingcclass()
	 * @generated
	 */
	void setMissingcclass(MissingcclassType value);

	/**
	 * Returns the value of the '<em><b>Mmpsourcepath</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mmpsourcepath</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mmpsourcepath</em>' containment reference.
	 * @see #setMmpsourcepath(MmpsourcepathType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Mmpsourcepath()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mmpsourcepath' namespace='##targetNamespace'"
	 * @generated
	 */
	MmpsourcepathType getMmpsourcepath();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getMmpsourcepath <em>Mmpsourcepath</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mmpsourcepath</em>' containment reference.
	 * @see #getMmpsourcepath()
	 * @generated
	 */
	void setMmpsourcepath(MmpsourcepathType value);

	/**
	 * Returns the value of the '<em><b>Multilangrsc</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multilangrsc</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multilangrsc</em>' containment reference.
	 * @see #setMultilangrsc(MultilangrscType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Multilangrsc()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='multilangrsc' namespace='##targetNamespace'"
	 * @generated
	 */
	MultilangrscType getMultilangrsc();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getMultilangrsc <em>Multilangrsc</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Multilangrsc</em>' containment reference.
	 * @see #getMultilangrsc()
	 * @generated
	 */
	void setMultilangrsc(MultilangrscType value);

	/**
	 * Returns the value of the '<em><b>Multipledeclarations</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multipledeclarations</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multipledeclarations</em>' containment reference.
	 * @see #setMultipledeclarations(MultipledeclarationsType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Multipledeclarations()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='multipledeclarations' namespace='##targetNamespace'"
	 * @generated
	 */
	MultipledeclarationsType getMultipledeclarations();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getMultipledeclarations <em>Multipledeclarations</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Multipledeclarations</em>' containment reference.
	 * @see #getMultipledeclarations()
	 * @generated
	 */
	void setMultipledeclarations(MultipledeclarationsType value);

	/**
	 * Returns the value of the '<em><b>Multipleinheritance</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multipleinheritance</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multipleinheritance</em>' containment reference.
	 * @see #setMultipleinheritance(MultipleinheritanceType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Multipleinheritance()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='multipleinheritance' namespace='##targetNamespace'"
	 * @generated
	 */
	MultipleinheritanceType getMultipleinheritance();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getMultipleinheritance <em>Multipleinheritance</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Multipleinheritance</em>' containment reference.
	 * @see #getMultipleinheritance()
	 * @generated
	 */
	void setMultipleinheritance(MultipleinheritanceType value);

	/**
	 * Returns the value of the '<em><b>Mydocs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mydocs</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mydocs</em>' containment reference.
	 * @see #setMydocs(MydocsType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Mydocs()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='mydocs' namespace='##targetNamespace'"
	 * @generated
	 */
	MydocsType getMydocs();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getMydocs <em>Mydocs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mydocs</em>' containment reference.
	 * @see #getMydocs()
	 * @generated
	 */
	void setMydocs(MydocsType value);

	/**
	 * Returns the value of the '<em><b>Namespace</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Namespace</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Namespace</em>' containment reference.
	 * @see #setNamespace(NamespaceType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Namespace()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='namespace' namespace='##targetNamespace'"
	 * @generated
	 */
	NamespaceType getNamespace();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getNamespace <em>Namespace</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Namespace</em>' containment reference.
	 * @see #getNamespace()
	 * @generated
	 */
	void setNamespace(NamespaceType value);

	/**
	 * Returns the value of the '<em><b>Newlreferences</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Newlreferences</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Newlreferences</em>' containment reference.
	 * @see #setNewlreferences(NewlreferencesType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Newlreferences()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='newlreferences' namespace='##targetNamespace'"
	 * @generated
	 */
	NewlreferencesType getNewlreferences();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getNewlreferences <em>Newlreferences</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Newlreferences</em>' containment reference.
	 * @see #getNewlreferences()
	 * @generated
	 */
	void setNewlreferences(NewlreferencesType value);

	/**
	 * Returns the value of the '<em><b>Noleavetrap</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Noleavetrap</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Noleavetrap</em>' containment reference.
	 * @see #setNoleavetrap(NoleavetrapType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Noleavetrap()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='noleavetrap' namespace='##targetNamespace'"
	 * @generated
	 */
	NoleavetrapType getNoleavetrap();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getNoleavetrap <em>Noleavetrap</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Noleavetrap</em>' containment reference.
	 * @see #getNoleavetrap()
	 * @generated
	 */
	void setNoleavetrap(NoleavetrapType value);

	/**
	 * Returns the value of the '<em><b>Nonconsthbufc</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nonconsthbufc</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nonconsthbufc</em>' containment reference.
	 * @see #setNonconsthbufc(NonconsthbufcType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Nonconsthbufc()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='nonconsthbufc' namespace='##targetNamespace'"
	 * @generated
	 */
	NonconsthbufcType getNonconsthbufc();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getNonconsthbufc <em>Nonconsthbufc</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nonconsthbufc</em>' containment reference.
	 * @see #getNonconsthbufc()
	 * @generated
	 */
	void setNonconsthbufc(NonconsthbufcType value);

	/**
	 * Returns the value of the '<em><b>Nonconsttdesc</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nonconsttdesc</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nonconsttdesc</em>' containment reference.
	 * @see #setNonconsttdesc(NonconsttdescType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Nonconsttdesc()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='nonconsttdesc' namespace='##targetNamespace'"
	 * @generated
	 */
	NonconsttdescType getNonconsttdesc();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getNonconsttdesc <em>Nonconsttdesc</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nonconsttdesc</em>' containment reference.
	 * @see #getNonconsttdesc()
	 * @generated
	 */
	void setNonconsttdesc(NonconsttdescType value);

	/**
	 * Returns the value of the '<em><b>Nonleavenew</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nonleavenew</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nonleavenew</em>' containment reference.
	 * @see #setNonleavenew(NonleavenewType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Nonleavenew()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='nonleavenew' namespace='##targetNamespace'"
	 * @generated
	 */
	NonleavenewType getNonleavenew();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getNonleavenew <em>Nonleavenew</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nonleavenew</em>' containment reference.
	 * @see #getNonleavenew()
	 * @generated
	 */
	void setNonleavenew(NonleavenewType value);

	/**
	 * Returns the value of the '<em><b>Nonunicodeskins</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Nonunicodeskins</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nonunicodeskins</em>' containment reference.
	 * @see #setNonunicodeskins(NonunicodeskinsType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Nonunicodeskins()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='nonunicodeskins' namespace='##targetNamespace'"
	 * @generated
	 */
	NonunicodeskinsType getNonunicodeskins();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getNonunicodeskins <em>Nonunicodeskins</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nonunicodeskins</em>' containment reference.
	 * @see #getNonunicodeskins()
	 * @generated
	 */
	void setNonunicodeskins(NonunicodeskinsType value);

	/**
	 * Returns the value of the '<em><b>Null</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Null</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Null</em>' containment reference.
	 * @see #setNull(NullType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Null()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='null' namespace='##targetNamespace'"
	 * @generated
	 */
	NullType getNull();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getNull <em>Null</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Null</em>' containment reference.
	 * @see #getNull()
	 * @generated
	 */
	void setNull(NullType value);

	/**
	 * Returns the value of the '<em><b>Open</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Open</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Open</em>' containment reference.
	 * @see #setOpen(OpenType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Open()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='open' namespace='##targetNamespace'"
	 * @generated
	 */
	OpenType getOpen();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getOpen <em>Open</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Open</em>' containment reference.
	 * @see #getOpen()
	 * @generated
	 */
	void setOpen(OpenType value);

	/**
	 * Returns the value of the '<em><b>Pointertoarrays</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pointertoarrays</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pointertoarrays</em>' containment reference.
	 * @see #setPointertoarrays(PointertoarraysType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Pointertoarrays()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='pointertoarrays' namespace='##targetNamespace'"
	 * @generated
	 */
	PointertoarraysType getPointertoarrays();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getPointertoarrays <em>Pointertoarrays</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pointertoarrays</em>' containment reference.
	 * @see #getPointertoarrays()
	 * @generated
	 */
	void setPointertoarrays(PointertoarraysType value);

	/**
	 * Returns the value of the '<em><b>Pragmadisable</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pragmadisable</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pragmadisable</em>' containment reference.
	 * @see #setPragmadisable(PragmadisableType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Pragmadisable()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='pragmadisable' namespace='##targetNamespace'"
	 * @generated
	 */
	PragmadisableType getPragmadisable();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getPragmadisable <em>Pragmadisable</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pragmadisable</em>' containment reference.
	 * @see #getPragmadisable()
	 * @generated
	 */
	void setPragmadisable(PragmadisableType value);

	/**
	 * Returns the value of the '<em><b>Pragmamessage</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pragmamessage</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pragmamessage</em>' containment reference.
	 * @see #setPragmamessage(PragmamessageType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Pragmamessage()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='pragmamessage' namespace='##targetNamespace'"
	 * @generated
	 */
	PragmamessageType getPragmamessage();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getPragmamessage <em>Pragmamessage</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pragmamessage</em>' containment reference.
	 * @see #getPragmamessage()
	 * @generated
	 */
	void setPragmamessage(PragmamessageType value);

	/**
	 * Returns the value of the '<em><b>Pragmaother</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pragmaother</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pragmaother</em>' containment reference.
	 * @see #setPragmaother(PragmaotherType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Pragmaother()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='pragmaother' namespace='##targetNamespace'"
	 * @generated
	 */
	PragmaotherType getPragmaother();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getPragmaother <em>Pragmaother</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pragmaother</em>' containment reference.
	 * @see #getPragmaother()
	 * @generated
	 */
	void setPragmaother(PragmaotherType value);

	/**
	 * Returns the value of the '<em><b>Privateinheritance</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Privateinheritance</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Privateinheritance</em>' containment reference.
	 * @see #setPrivateinheritance(PrivateinheritanceType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Privateinheritance()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='privateinheritance' namespace='##targetNamespace'"
	 * @generated
	 */
	PrivateinheritanceType getPrivateinheritance();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getPrivateinheritance <em>Privateinheritance</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Privateinheritance</em>' containment reference.
	 * @see #getPrivateinheritance()
	 * @generated
	 */
	void setPrivateinheritance(PrivateinheritanceType value);

	/**
	 * Returns the value of the '<em><b>Pushaddrvar</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pushaddrvar</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pushaddrvar</em>' containment reference.
	 * @see #setPushaddrvar(PushaddrvarType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Pushaddrvar()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='pushaddrvar' namespace='##targetNamespace'"
	 * @generated
	 */
	PushaddrvarType getPushaddrvar();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getPushaddrvar <em>Pushaddrvar</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pushaddrvar</em>' containment reference.
	 * @see #getPushaddrvar()
	 * @generated
	 */
	void setPushaddrvar(PushaddrvarType value);

	/**
	 * Returns the value of the '<em><b>Pushmember</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pushmember</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pushmember</em>' containment reference.
	 * @see #setPushmember(PushmemberType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Pushmember()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='pushmember' namespace='##targetNamespace'"
	 * @generated
	 */
	PushmemberType getPushmember();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getPushmember <em>Pushmember</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pushmember</em>' containment reference.
	 * @see #getPushmember()
	 * @generated
	 */
	void setPushmember(PushmemberType value);

	/**
	 * Returns the value of the '<em><b>Readresource</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Readresource</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Readresource</em>' containment reference.
	 * @see #setReadresource(ReadresourceType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Readresource()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='readresource' namespace='##targetNamespace'"
	 * @generated
	 */
	ReadresourceType getReadresource();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getReadresource <em>Readresource</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Readresource</em>' containment reference.
	 * @see #getReadresource()
	 * @generated
	 */
	void setReadresource(ReadresourceType value);

	/**
	 * Returns the value of the '<em><b>Resourcenotoncleanupstack</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resourcenotoncleanupstack</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resourcenotoncleanupstack</em>' containment reference.
	 * @see #setResourcenotoncleanupstack(ResourcenotoncleanupstackType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Resourcenotoncleanupstack()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='resourcenotoncleanupstack' namespace='##targetNamespace'"
	 * @generated
	 */
	ResourcenotoncleanupstackType getResourcenotoncleanupstack();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getResourcenotoncleanupstack <em>Resourcenotoncleanupstack</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resourcenotoncleanupstack</em>' containment reference.
	 * @see #getResourcenotoncleanupstack()
	 * @generated
	 */
	void setResourcenotoncleanupstack(ResourcenotoncleanupstackType value);

	/**
	 * Returns the value of the '<em><b>Resourcesonheap</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resourcesonheap</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resourcesonheap</em>' containment reference.
	 * @see #setResourcesonheap(ResourcesonheapType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Resourcesonheap()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='resourcesonheap' namespace='##targetNamespace'"
	 * @generated
	 */
	ResourcesonheapType getResourcesonheap();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getResourcesonheap <em>Resourcesonheap</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resourcesonheap</em>' containment reference.
	 * @see #getResourcesonheap()
	 * @generated
	 */
	void setResourcesonheap(ResourcesonheapType value);

	/**
	 * Returns the value of the '<em><b>Returndescriptoroutofscope</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Returndescriptoroutofscope</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Returndescriptoroutofscope</em>' containment reference.
	 * @see #setReturndescriptoroutofscope(ReturndescriptoroutofscopeType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Returndescriptoroutofscope()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='returndescriptoroutofscope' namespace='##targetNamespace'"
	 * @generated
	 */
	ReturndescriptoroutofscopeType getReturndescriptoroutofscope();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getReturndescriptoroutofscope <em>Returndescriptoroutofscope</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Returndescriptoroutofscope</em>' containment reference.
	 * @see #getReturndescriptoroutofscope()
	 * @generated
	 */
	void setReturndescriptoroutofscope(ReturndescriptoroutofscopeType value);

	/**
	 * Returns the value of the '<em><b>Rfs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rfs</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rfs</em>' containment reference.
	 * @see #setRfs(RfsType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Rfs()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='rfs' namespace='##targetNamespace'"
	 * @generated
	 */
	RfsType getRfs();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getRfs <em>Rfs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rfs</em>' containment reference.
	 * @see #getRfs()
	 * @generated
	 */
	void setRfs(RfsType value);

	/**
	 * Returns the value of the '<em><b>Rssnames</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rssnames</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rssnames</em>' containment reference.
	 * @see #setRssnames(RssnamesType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Rssnames()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='rssnames' namespace='##targetNamespace'"
	 * @generated
	 */
	RssnamesType getRssnames();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getRssnames <em>Rssnames</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rssnames</em>' containment reference.
	 * @see #getRssnames()
	 * @generated
	 */
	void setRssnames(RssnamesType value);

	/**
	 * Returns the value of the '<em><b>Stringliterals</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Stringliterals</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stringliterals</em>' containment reference.
	 * @see #setStringliterals(StringliteralsType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Stringliterals()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='stringliterals' namespace='##targetNamespace'"
	 * @generated
	 */
	StringliteralsType getStringliterals();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getStringliterals <em>Stringliterals</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stringliterals</em>' containment reference.
	 * @see #getStringliterals()
	 * @generated
	 */
	void setStringliterals(StringliteralsType value);

	/**
	 * Returns the value of the '<em><b>Stringsinresourcefiles</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Stringsinresourcefiles</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stringsinresourcefiles</em>' containment reference.
	 * @see #setStringsinresourcefiles(StringsinresourcefilesType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Stringsinresourcefiles()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='stringsinresourcefiles' namespace='##targetNamespace'"
	 * @generated
	 */
	StringsinresourcefilesType getStringsinresourcefiles();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getStringsinresourcefiles <em>Stringsinresourcefiles</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stringsinresourcefiles</em>' containment reference.
	 * @see #getStringsinresourcefiles()
	 * @generated
	 */
	void setStringsinresourcefiles(StringsinresourcefilesType value);

	/**
	 * Returns the value of the '<em><b>Struct</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Struct</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Struct</em>' containment reference.
	 * @see #setStruct(StructType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Struct()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='struct' namespace='##targetNamespace'"
	 * @generated
	 */
	StructType getStruct();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getStruct <em>Struct</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Struct</em>' containment reference.
	 * @see #getStruct()
	 * @generated
	 */
	void setStruct(StructType value);

	/**
	 * Returns the value of the '<em><b>Tcclasses</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tcclasses</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tcclasses</em>' containment reference.
	 * @see #setTcclasses(TcclassesType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Tcclasses()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='tcclasses' namespace='##targetNamespace'"
	 * @generated
	 */
	TcclassesType getTcclasses();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getTcclasses <em>Tcclasses</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tcclasses</em>' containment reference.
	 * @see #getTcclasses()
	 * @generated
	 */
	void setTcclasses(TcclassesType value);

	/**
	 * Returns the value of the '<em><b>Tclassdestructor</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tclassdestructor</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tclassdestructor</em>' containment reference.
	 * @see #setTclassdestructor(TclassdestructorType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Tclassdestructor()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='tclassdestructor' namespace='##targetNamespace'"
	 * @generated
	 */
	TclassdestructorType getTclassdestructor();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getTclassdestructor <em>Tclassdestructor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tclassdestructor</em>' containment reference.
	 * @see #getTclassdestructor()
	 * @generated
	 */
	void setTclassdestructor(TclassdestructorType value);

	/**
	 * Returns the value of the '<em><b>Todocomments</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Todocomments</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Todocomments</em>' containment reference.
	 * @see #setTodocomments(TodocommentsType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Todocomments()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='todocomments' namespace='##targetNamespace'"
	 * @generated
	 */
	TodocommentsType getTodocomments();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getTodocomments <em>Todocomments</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Todocomments</em>' containment reference.
	 * @see #getTodocomments()
	 * @generated
	 */
	void setTodocomments(TodocommentsType value);

	/**
	 * Returns the value of the '<em><b>Trapcleanup</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trapcleanup</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trapcleanup</em>' containment reference.
	 * @see #setTrapcleanup(TrapcleanupType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Trapcleanup()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='trapcleanup' namespace='##targetNamespace'"
	 * @generated
	 */
	TrapcleanupType getTrapcleanup();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getTrapcleanup <em>Trapcleanup</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Trapcleanup</em>' containment reference.
	 * @see #getTrapcleanup()
	 * @generated
	 */
	void setTrapcleanup(TrapcleanupType value);

	/**
	 * Returns the value of the '<em><b>Trapeleave</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trapeleave</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trapeleave</em>' containment reference.
	 * @see #setTrapeleave(TrapeleaveType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Trapeleave()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='trapeleave' namespace='##targetNamespace'"
	 * @generated
	 */
	TrapeleaveType getTrapeleave();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getTrapeleave <em>Trapeleave</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Trapeleave</em>' containment reference.
	 * @see #getTrapeleave()
	 * @generated
	 */
	void setTrapeleave(TrapeleaveType value);

	/**
	 * Returns the value of the '<em><b>Traprunl</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Traprunl</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Traprunl</em>' containment reference.
	 * @see #setTraprunl(TraprunlType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Traprunl()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='traprunl' namespace='##targetNamespace'"
	 * @generated
	 */
	TraprunlType getTraprunl();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getTraprunl <em>Traprunl</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Traprunl</em>' containment reference.
	 * @see #getTraprunl()
	 * @generated
	 */
	void setTraprunl(TraprunlType value);

	/**
	 * Returns the value of the '<em><b>Trspassing</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trspassing</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trspassing</em>' containment reference.
	 * @see #setTrspassing(TrspassingType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Trspassing()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='trspassing' namespace='##targetNamespace'"
	 * @generated
	 */
	TrspassingType getTrspassing();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getTrspassing <em>Trspassing</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Trspassing</em>' containment reference.
	 * @see #getTrspassing()
	 * @generated
	 */
	void setTrspassing(TrspassingType value);

	/**
	 * Returns the value of the '<em><b>Uids</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uids</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uids</em>' containment reference.
	 * @see #setUids(UidsType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Uids()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='uids' namespace='##targetNamespace'"
	 * @generated
	 */
	UidsType getUids();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getUids <em>Uids</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uids</em>' containment reference.
	 * @see #getUids()
	 * @generated
	 */
	void setUids(UidsType value);

	/**
	 * Returns the value of the '<em><b>Uncompressedaif</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uncompressedaif</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uncompressedaif</em>' containment reference.
	 * @see #setUncompressedaif(UncompressedaifType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Uncompressedaif()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='uncompressedaif' namespace='##targetNamespace'"
	 * @generated
	 */
	UncompressedaifType getUncompressedaif();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getUncompressedaif <em>Uncompressedaif</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uncompressedaif</em>' containment reference.
	 * @see #getUncompressedaif()
	 * @generated
	 */
	void setUncompressedaif(UncompressedaifType value);

	/**
	 * Returns the value of the '<em><b>Uncompressedbmp</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uncompressedbmp</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uncompressedbmp</em>' containment reference.
	 * @see #setUncompressedbmp(UncompressedbmpType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Uncompressedbmp()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='uncompressedbmp' namespace='##targetNamespace'"
	 * @generated
	 */
	UncompressedbmpType getUncompressedbmp();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getUncompressedbmp <em>Uncompressedbmp</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uncompressedbmp</em>' containment reference.
	 * @see #getUncompressedbmp()
	 * @generated
	 */
	void setUncompressedbmp(UncompressedbmpType value);

	/**
	 * Returns the value of the '<em><b>Unicodesource</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unicodesource</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unicodesource</em>' containment reference.
	 * @see #setUnicodesource(UnicodesourceType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Unicodesource()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='unicodesource' namespace='##targetNamespace'"
	 * @generated
	 */
	UnicodesourceType getUnicodesource();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getUnicodesource <em>Unicodesource</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unicodesource</em>' containment reference.
	 * @see #getUnicodesource()
	 * @generated
	 */
	void setUnicodesource(UnicodesourceType value);

	/**
	 * Returns the value of the '<em><b>Userafter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Userafter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Userafter</em>' containment reference.
	 * @see #setUserafter(UserafterType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Userafter()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='userafter' namespace='##targetNamespace'"
	 * @generated
	 */
	UserafterType getUserafter();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getUserafter <em>Userafter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Userafter</em>' containment reference.
	 * @see #getUserafter()
	 * @generated
	 */
	void setUserafter(UserafterType value);

	/**
	 * Returns the value of the '<em><b>Userfree</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Userfree</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Userfree</em>' containment reference.
	 * @see #setUserfree(UserfreeType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Userfree()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='userfree' namespace='##targetNamespace'"
	 * @generated
	 */
	UserfreeType getUserfree();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getUserfree <em>Userfree</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Userfree</em>' containment reference.
	 * @see #getUserfree()
	 * @generated
	 */
	void setUserfree(UserfreeType value);

	/**
	 * Returns the value of the '<em><b>User Wait For Request</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Wait For Request</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Wait For Request</em>' containment reference.
	 * @see #setUserWaitForRequest(UserWaitForRequestType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_UserWaitForRequest()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='userWaitForRequest' namespace='##targetNamespace'"
	 * @generated
	 */
	UserWaitForRequestType getUserWaitForRequest();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getUserWaitForRequest <em>User Wait For Request</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User Wait For Request</em>' containment reference.
	 * @see #getUserWaitForRequest()
	 * @generated
	 */
	void setUserWaitForRequest(UserWaitForRequestType value);

	/**
	 * Returns the value of the '<em><b>Variablenames</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variablenames</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variablenames</em>' containment reference.
	 * @see #setVariablenames(VariablenamesType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Variablenames()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='variablenames' namespace='##targetNamespace'"
	 * @generated
	 */
	VariablenamesType getVariablenames();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getVariablenames <em>Variablenames</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Variablenames</em>' containment reference.
	 * @see #getVariablenames()
	 * @generated
	 */
	void setVariablenames(VariablenamesType value);

	/**
	 * Returns the value of the '<em><b>Voidparameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Voidparameter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Voidparameter</em>' containment reference.
	 * @see #setVoidparameter(VoidparameterType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Voidparameter()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='voidparameter' namespace='##targetNamespace'"
	 * @generated
	 */
	VoidparameterType getVoidparameter();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getVoidparameter <em>Voidparameter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Voidparameter</em>' containment reference.
	 * @see #getVoidparameter()
	 * @generated
	 */
	void setVoidparameter(VoidparameterType value);

	/**
	 * Returns the value of the '<em><b>Worryingcomments</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Worryingcomments</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Worryingcomments</em>' containment reference.
	 * @see #setWorryingcomments(WorryingcommentsType)
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage#getScriptsType_Worryingcomments()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='worryingcomments' namespace='##targetNamespace'"
	 * @generated
	 */
	WorryingcommentsType getWorryingcomments();

	/**
	 * Sets the value of the '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType#getWorryingcomments <em>Worryingcomments</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Worryingcomments</em>' containment reference.
	 * @see #getWorryingcomments()
	 * @generated
	 */
	void setWorryingcomments(WorryingcommentsType value);

} // ScriptsType