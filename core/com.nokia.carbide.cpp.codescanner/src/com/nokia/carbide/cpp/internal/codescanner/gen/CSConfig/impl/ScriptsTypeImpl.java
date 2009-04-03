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

package com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl;

import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.*;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Scripts Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getAccessArrayElementWithoutCheck <em>Access Array Element Without Check</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getAccessArrayElementWithoutCheck2 <em>Access Array Element Without Check2</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getActivestart <em>Activestart</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getActivestop <em>Activestop</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getArraypassing <em>Arraypassing</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getArrayptrcleanup <em>Arrayptrcleanup</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getAssertdebuginvariant <em>Assertdebuginvariant</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getBaddefines <em>Baddefines</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getBaseconstruct <em>Baseconstruct</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getCallActiveObjectWithoutCheckingOrStopping <em>Call Active Object Without Checking Or Stopping</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getChangenotification <em>Changenotification</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getCleanup <em>Cleanup</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getCommentcode <em>Commentcode</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getConnect <em>Connect</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getConnectAndDontCloseMemberVariable <em>Connect And Dont Close Member Variable</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getConstnames <em>Constnames</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getConsttdescptr <em>Consttdescptr</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getControlornull <em>Controlornull</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getCtltargettype <em>Ctltargettype</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getDebugrom <em>Debugrom</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getDeclarename <em>Declarename</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getDeleteMemberVariable <em>Delete Member Variable</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getDestructor <em>Destructor</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getDoubleSemiColon <em>Double Semi Colon</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getDriveletters <em>Driveletters</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getEikbuttons <em>Eikbuttons</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getEikonenvstatic <em>Eikonenvstatic</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getEnummembers <em>Enummembers</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getEnumnames <em>Enumnames</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getExportinline <em>Exportinline</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getExportpurevirtual <em>Exportpurevirtual</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getExternaldriveletters <em>Externaldriveletters</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getFoff <em>Foff</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getForbiddenwords <em>Forbiddenwords</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getForgottoputptroncleanupstack <em>Forgottoputptroncleanupstack</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getFriend <em>Friend</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getGoto <em>Goto</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getIfassignments <em>Ifassignments</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getIfpreprocessor <em>Ifpreprocessor</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getInheritanceorder <em>Inheritanceorder</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getIntleaves <em>Intleaves</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getJmp <em>Jmp</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getLeave <em>Leave</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getLeaveNoError <em>Leave No Error</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getLeavingoperators <em>Leavingoperators</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getLFunctionCantLeave <em>LFunction Cant Leave</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getLonglines <em>Longlines</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getMagicnumbers <em>Magicnumbers</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getMclassdestructor <em>Mclassdestructor</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getMemberlc <em>Memberlc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getMembervariablecallld <em>Membervariablecallld</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getMissingcancel <em>Missingcancel</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getMissingcclass <em>Missingcclass</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getMmpsourcepath <em>Mmpsourcepath</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getMultilangrsc <em>Multilangrsc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getMultipledeclarations <em>Multipledeclarations</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getMultipleinheritance <em>Multipleinheritance</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getMydocs <em>Mydocs</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getNewlreferences <em>Newlreferences</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getNoleavetrap <em>Noleavetrap</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getNonconsthbufc <em>Nonconsthbufc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getNonconsttdesc <em>Nonconsttdesc</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getNonleavenew <em>Nonleavenew</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getNonunicodeskins <em>Nonunicodeskins</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getNull <em>Null</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getOpen <em>Open</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getPointertoarrays <em>Pointertoarrays</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getPragmadisable <em>Pragmadisable</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getPragmamessage <em>Pragmamessage</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getPragmaother <em>Pragmaother</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getPrivateinheritance <em>Privateinheritance</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getPushaddrvar <em>Pushaddrvar</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getPushmember <em>Pushmember</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getReadresource <em>Readresource</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getResourcenotoncleanupstack <em>Resourcenotoncleanupstack</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getResourcesonheap <em>Resourcesonheap</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getReturndescriptoroutofscope <em>Returndescriptoroutofscope</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getRfs <em>Rfs</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getRssnames <em>Rssnames</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getStringliterals <em>Stringliterals</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getStringsinresourcefiles <em>Stringsinresourcefiles</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getStruct <em>Struct</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getTcclasses <em>Tcclasses</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getTclassdestructor <em>Tclassdestructor</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getTodocomments <em>Todocomments</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getTrapcleanup <em>Trapcleanup</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getTrapeleave <em>Trapeleave</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getTraprunl <em>Traprunl</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getTrspassing <em>Trspassing</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getUids <em>Uids</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getUncompressedaif <em>Uncompressedaif</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getUncompressedbmp <em>Uncompressedbmp</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getUnicodesource <em>Unicodesource</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getUserafter <em>Userafter</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getUserfree <em>Userfree</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getUserWaitForRequest <em>User Wait For Request</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getVariablenames <em>Variablenames</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getVoidparameter <em>Voidparameter</em>}</li>
 *   <li>{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.impl.ScriptsTypeImpl#getWorryingcomments <em>Worryingcomments</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ScriptsTypeImpl extends EObjectImpl implements ScriptsType {
	/**
	 * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMixed()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap mixed;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ScriptsTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CSConfigPackage.eINSTANCE.getScriptsType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, CSConfigPackage.SCRIPTS_TYPE__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AccessArrayElementWithoutCheckType getAccessArrayElementWithoutCheck() {
		return (AccessArrayElementWithoutCheckType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_AccessArrayElementWithoutCheck(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAccessArrayElementWithoutCheck(AccessArrayElementWithoutCheckType newAccessArrayElementWithoutCheck, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_AccessArrayElementWithoutCheck(), newAccessArrayElementWithoutCheck, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAccessArrayElementWithoutCheck(AccessArrayElementWithoutCheckType newAccessArrayElementWithoutCheck) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_AccessArrayElementWithoutCheck(), newAccessArrayElementWithoutCheck);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AccessArrayElementWithoutCheck2Type getAccessArrayElementWithoutCheck2() {
		return (AccessArrayElementWithoutCheck2Type)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_AccessArrayElementWithoutCheck2(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAccessArrayElementWithoutCheck2(AccessArrayElementWithoutCheck2Type newAccessArrayElementWithoutCheck2, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_AccessArrayElementWithoutCheck2(), newAccessArrayElementWithoutCheck2, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAccessArrayElementWithoutCheck2(AccessArrayElementWithoutCheck2Type newAccessArrayElementWithoutCheck2) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_AccessArrayElementWithoutCheck2(), newAccessArrayElementWithoutCheck2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActivestartType getActivestart() {
		return (ActivestartType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Activestart(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetActivestart(ActivestartType newActivestart, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Activestart(), newActivestart, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActivestart(ActivestartType newActivestart) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Activestart(), newActivestart);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActivestopType getActivestop() {
		return (ActivestopType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Activestop(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetActivestop(ActivestopType newActivestop, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Activestop(), newActivestop, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActivestop(ActivestopType newActivestop) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Activestop(), newActivestop);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArraypassingType getArraypassing() {
		return (ArraypassingType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Arraypassing(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetArraypassing(ArraypassingType newArraypassing, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Arraypassing(), newArraypassing, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArraypassing(ArraypassingType newArraypassing) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Arraypassing(), newArraypassing);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayptrcleanupType getArrayptrcleanup() {
		return (ArrayptrcleanupType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Arrayptrcleanup(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetArrayptrcleanup(ArrayptrcleanupType newArrayptrcleanup, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Arrayptrcleanup(), newArrayptrcleanup, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArrayptrcleanup(ArrayptrcleanupType newArrayptrcleanup) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Arrayptrcleanup(), newArrayptrcleanup);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AssertdebuginvariantType getAssertdebuginvariant() {
		return (AssertdebuginvariantType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Assertdebuginvariant(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAssertdebuginvariant(AssertdebuginvariantType newAssertdebuginvariant, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Assertdebuginvariant(), newAssertdebuginvariant, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAssertdebuginvariant(AssertdebuginvariantType newAssertdebuginvariant) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Assertdebuginvariant(), newAssertdebuginvariant);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BaddefinesType getBaddefines() {
		return (BaddefinesType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Baddefines(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBaddefines(BaddefinesType newBaddefines, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Baddefines(), newBaddefines, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBaddefines(BaddefinesType newBaddefines) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Baddefines(), newBaddefines);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BaseconstructType getBaseconstruct() {
		return (BaseconstructType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Baseconstruct(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBaseconstruct(BaseconstructType newBaseconstruct, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Baseconstruct(), newBaseconstruct, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBaseconstruct(BaseconstructType newBaseconstruct) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Baseconstruct(), newBaseconstruct);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CallActiveObjectWithoutCheckingOrStoppingType getCallActiveObjectWithoutCheckingOrStopping() {
		return (CallActiveObjectWithoutCheckingOrStoppingType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_CallActiveObjectWithoutCheckingOrStopping(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCallActiveObjectWithoutCheckingOrStopping(CallActiveObjectWithoutCheckingOrStoppingType newCallActiveObjectWithoutCheckingOrStopping, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_CallActiveObjectWithoutCheckingOrStopping(), newCallActiveObjectWithoutCheckingOrStopping, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCallActiveObjectWithoutCheckingOrStopping(CallActiveObjectWithoutCheckingOrStoppingType newCallActiveObjectWithoutCheckingOrStopping) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_CallActiveObjectWithoutCheckingOrStopping(), newCallActiveObjectWithoutCheckingOrStopping);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChangenotificationType getChangenotification() {
		return (ChangenotificationType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Changenotification(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetChangenotification(ChangenotificationType newChangenotification, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Changenotification(), newChangenotification, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setChangenotification(ChangenotificationType newChangenotification) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Changenotification(), newChangenotification);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CleanupType getCleanup() {
		return (CleanupType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Cleanup(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCleanup(CleanupType newCleanup, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Cleanup(), newCleanup, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCleanup(CleanupType newCleanup) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Cleanup(), newCleanup);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CommentcodeType getCommentcode() {
		return (CommentcodeType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Commentcode(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCommentcode(CommentcodeType newCommentcode, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Commentcode(), newCommentcode, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCommentcode(CommentcodeType newCommentcode) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Commentcode(), newCommentcode);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectType getConnect() {
		return (ConnectType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Connect(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConnect(ConnectType newConnect, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Connect(), newConnect, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConnect(ConnectType newConnect) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Connect(), newConnect);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectAndDontCloseMemberVariableType getConnectAndDontCloseMemberVariable() {
		return (ConnectAndDontCloseMemberVariableType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_ConnectAndDontCloseMemberVariable(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConnectAndDontCloseMemberVariable(ConnectAndDontCloseMemberVariableType newConnectAndDontCloseMemberVariable, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_ConnectAndDontCloseMemberVariable(), newConnectAndDontCloseMemberVariable, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConnectAndDontCloseMemberVariable(ConnectAndDontCloseMemberVariableType newConnectAndDontCloseMemberVariable) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_ConnectAndDontCloseMemberVariable(), newConnectAndDontCloseMemberVariable);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConstnamesType getConstnames() {
		return (ConstnamesType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Constnames(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConstnames(ConstnamesType newConstnames, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Constnames(), newConstnames, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConstnames(ConstnamesType newConstnames) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Constnames(), newConstnames);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConsttdescptrType getConsttdescptr() {
		return (ConsttdescptrType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Consttdescptr(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConsttdescptr(ConsttdescptrType newConsttdescptr, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Consttdescptr(), newConsttdescptr, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConsttdescptr(ConsttdescptrType newConsttdescptr) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Consttdescptr(), newConsttdescptr);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ControlornullType getControlornull() {
		return (ControlornullType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Controlornull(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetControlornull(ControlornullType newControlornull, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Controlornull(), newControlornull, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setControlornull(ControlornullType newControlornull) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Controlornull(), newControlornull);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CtltargettypeType getCtltargettype() {
		return (CtltargettypeType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Ctltargettype(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCtltargettype(CtltargettypeType newCtltargettype, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Ctltargettype(), newCtltargettype, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCtltargettype(CtltargettypeType newCtltargettype) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Ctltargettype(), newCtltargettype);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DebugromType getDebugrom() {
		return (DebugromType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Debugrom(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDebugrom(DebugromType newDebugrom, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Debugrom(), newDebugrom, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDebugrom(DebugromType newDebugrom) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Debugrom(), newDebugrom);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeclarenameType getDeclarename() {
		return (DeclarenameType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Declarename(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeclarename(DeclarenameType newDeclarename, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Declarename(), newDeclarename, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeclarename(DeclarenameType newDeclarename) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Declarename(), newDeclarename);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeleteMemberVariableType getDeleteMemberVariable() {
		return (DeleteMemberVariableType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_DeleteMemberVariable(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeleteMemberVariable(DeleteMemberVariableType newDeleteMemberVariable, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_DeleteMemberVariable(), newDeleteMemberVariable, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeleteMemberVariable(DeleteMemberVariableType newDeleteMemberVariable) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_DeleteMemberVariable(), newDeleteMemberVariable);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DestructorType getDestructor() {
		return (DestructorType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Destructor(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDestructor(DestructorType newDestructor, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Destructor(), newDestructor, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDestructor(DestructorType newDestructor) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Destructor(), newDestructor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DoubleSemiColonType getDoubleSemiColon() {
		return (DoubleSemiColonType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_DoubleSemiColon(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDoubleSemiColon(DoubleSemiColonType newDoubleSemiColon, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_DoubleSemiColon(), newDoubleSemiColon, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDoubleSemiColon(DoubleSemiColonType newDoubleSemiColon) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_DoubleSemiColon(), newDoubleSemiColon);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DrivelettersType getDriveletters() {
		return (DrivelettersType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Driveletters(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDriveletters(DrivelettersType newDriveletters, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Driveletters(), newDriveletters, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDriveletters(DrivelettersType newDriveletters) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Driveletters(), newDriveletters);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EikbuttonsType getEikbuttons() {
		return (EikbuttonsType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Eikbuttons(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEikbuttons(EikbuttonsType newEikbuttons, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Eikbuttons(), newEikbuttons, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEikbuttons(EikbuttonsType newEikbuttons) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Eikbuttons(), newEikbuttons);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EikonenvstaticType getEikonenvstatic() {
		return (EikonenvstaticType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Eikonenvstatic(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEikonenvstatic(EikonenvstaticType newEikonenvstatic, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Eikonenvstatic(), newEikonenvstatic, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEikonenvstatic(EikonenvstaticType newEikonenvstatic) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Eikonenvstatic(), newEikonenvstatic);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnummembersType getEnummembers() {
		return (EnummembersType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Enummembers(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnummembers(EnummembersType newEnummembers, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Enummembers(), newEnummembers, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnummembers(EnummembersType newEnummembers) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Enummembers(), newEnummembers);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumnamesType getEnumnames() {
		return (EnumnamesType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Enumnames(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnumnames(EnumnamesType newEnumnames, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Enumnames(), newEnumnames, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnumnames(EnumnamesType newEnumnames) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Enumnames(), newEnumnames);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExportinlineType getExportinline() {
		return (ExportinlineType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Exportinline(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExportinline(ExportinlineType newExportinline, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Exportinline(), newExportinline, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExportinline(ExportinlineType newExportinline) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Exportinline(), newExportinline);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExportpurevirtualType getExportpurevirtual() {
		return (ExportpurevirtualType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Exportpurevirtual(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExportpurevirtual(ExportpurevirtualType newExportpurevirtual, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Exportpurevirtual(), newExportpurevirtual, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExportpurevirtual(ExportpurevirtualType newExportpurevirtual) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Exportpurevirtual(), newExportpurevirtual);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExternaldrivelettersType getExternaldriveletters() {
		return (ExternaldrivelettersType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Externaldriveletters(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExternaldriveletters(ExternaldrivelettersType newExternaldriveletters, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Externaldriveletters(), newExternaldriveletters, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExternaldriveletters(ExternaldrivelettersType newExternaldriveletters) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Externaldriveletters(), newExternaldriveletters);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FoffType getFoff() {
		return (FoffType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Foff(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFoff(FoffType newFoff, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Foff(), newFoff, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFoff(FoffType newFoff) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Foff(), newFoff);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ForbiddenwordsType getForbiddenwords() {
		return (ForbiddenwordsType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Forbiddenwords(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetForbiddenwords(ForbiddenwordsType newForbiddenwords, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Forbiddenwords(), newForbiddenwords, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setForbiddenwords(ForbiddenwordsType newForbiddenwords) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Forbiddenwords(), newForbiddenwords);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ForgottoputptroncleanupstackType getForgottoputptroncleanupstack() {
		return (ForgottoputptroncleanupstackType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Forgottoputptroncleanupstack(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetForgottoputptroncleanupstack(ForgottoputptroncleanupstackType newForgottoputptroncleanupstack, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Forgottoputptroncleanupstack(), newForgottoputptroncleanupstack, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setForgottoputptroncleanupstack(ForgottoputptroncleanupstackType newForgottoputptroncleanupstack) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Forgottoputptroncleanupstack(), newForgottoputptroncleanupstack);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FriendType getFriend() {
		return (FriendType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Friend(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFriend(FriendType newFriend, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Friend(), newFriend, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFriend(FriendType newFriend) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Friend(), newFriend);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GotoType getGoto() {
		return (GotoType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Goto(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGoto(GotoType newGoto, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Goto(), newGoto, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGoto(GotoType newGoto) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Goto(), newGoto);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IfassignmentsType getIfassignments() {
		return (IfassignmentsType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Ifassignments(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIfassignments(IfassignmentsType newIfassignments, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Ifassignments(), newIfassignments, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIfassignments(IfassignmentsType newIfassignments) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Ifassignments(), newIfassignments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IfpreprocessorType getIfpreprocessor() {
		return (IfpreprocessorType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Ifpreprocessor(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIfpreprocessor(IfpreprocessorType newIfpreprocessor, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Ifpreprocessor(), newIfpreprocessor, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIfpreprocessor(IfpreprocessorType newIfpreprocessor) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Ifpreprocessor(), newIfpreprocessor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InheritanceorderType getInheritanceorder() {
		return (InheritanceorderType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Inheritanceorder(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInheritanceorder(InheritanceorderType newInheritanceorder, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Inheritanceorder(), newInheritanceorder, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInheritanceorder(InheritanceorderType newInheritanceorder) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Inheritanceorder(), newInheritanceorder);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntleavesType getIntleaves() {
		return (IntleavesType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Intleaves(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIntleaves(IntleavesType newIntleaves, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Intleaves(), newIntleaves, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIntleaves(IntleavesType newIntleaves) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Intleaves(), newIntleaves);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JmpType getJmp() {
		return (JmpType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Jmp(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJmp(JmpType newJmp, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Jmp(), newJmp, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJmp(JmpType newJmp) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Jmp(), newJmp);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LeaveType getLeave() {
		return (LeaveType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Leave(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLeave(LeaveType newLeave, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Leave(), newLeave, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLeave(LeaveType newLeave) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Leave(), newLeave);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LeaveNoErrorType getLeaveNoError() {
		return (LeaveNoErrorType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_LeaveNoError(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLeaveNoError(LeaveNoErrorType newLeaveNoError, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_LeaveNoError(), newLeaveNoError, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLeaveNoError(LeaveNoErrorType newLeaveNoError) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_LeaveNoError(), newLeaveNoError);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LeavingoperatorsType getLeavingoperators() {
		return (LeavingoperatorsType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Leavingoperators(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLeavingoperators(LeavingoperatorsType newLeavingoperators, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Leavingoperators(), newLeavingoperators, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLeavingoperators(LeavingoperatorsType newLeavingoperators) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Leavingoperators(), newLeavingoperators);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LFunctionCantLeaveType getLFunctionCantLeave() {
		return (LFunctionCantLeaveType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_LFunctionCantLeave(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLFunctionCantLeave(LFunctionCantLeaveType newLFunctionCantLeave, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_LFunctionCantLeave(), newLFunctionCantLeave, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLFunctionCantLeave(LFunctionCantLeaveType newLFunctionCantLeave) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_LFunctionCantLeave(), newLFunctionCantLeave);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LonglinesType getLonglines() {
		return (LonglinesType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Longlines(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLonglines(LonglinesType newLonglines, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Longlines(), newLonglines, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLonglines(LonglinesType newLonglines) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Longlines(), newLonglines);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MagicnumbersType getMagicnumbers() {
		return (MagicnumbersType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Magicnumbers(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMagicnumbers(MagicnumbersType newMagicnumbers, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Magicnumbers(), newMagicnumbers, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMagicnumbers(MagicnumbersType newMagicnumbers) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Magicnumbers(), newMagicnumbers);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MclassdestructorType getMclassdestructor() {
		return (MclassdestructorType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Mclassdestructor(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMclassdestructor(MclassdestructorType newMclassdestructor, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Mclassdestructor(), newMclassdestructor, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMclassdestructor(MclassdestructorType newMclassdestructor) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Mclassdestructor(), newMclassdestructor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MemberlcType getMemberlc() {
		return (MemberlcType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Memberlc(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMemberlc(MemberlcType newMemberlc, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Memberlc(), newMemberlc, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMemberlc(MemberlcType newMemberlc) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Memberlc(), newMemberlc);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MembervariablecallldType getMembervariablecallld() {
		return (MembervariablecallldType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Membervariablecallld(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMembervariablecallld(MembervariablecallldType newMembervariablecallld, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Membervariablecallld(), newMembervariablecallld, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMembervariablecallld(MembervariablecallldType newMembervariablecallld) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Membervariablecallld(), newMembervariablecallld);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MissingcancelType getMissingcancel() {
		return (MissingcancelType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Missingcancel(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMissingcancel(MissingcancelType newMissingcancel, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Missingcancel(), newMissingcancel, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMissingcancel(MissingcancelType newMissingcancel) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Missingcancel(), newMissingcancel);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MissingcclassType getMissingcclass() {
		return (MissingcclassType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Missingcclass(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMissingcclass(MissingcclassType newMissingcclass, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Missingcclass(), newMissingcclass, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMissingcclass(MissingcclassType newMissingcclass) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Missingcclass(), newMissingcclass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmpsourcepathType getMmpsourcepath() {
		return (MmpsourcepathType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Mmpsourcepath(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMmpsourcepath(MmpsourcepathType newMmpsourcepath, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Mmpsourcepath(), newMmpsourcepath, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMmpsourcepath(MmpsourcepathType newMmpsourcepath) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Mmpsourcepath(), newMmpsourcepath);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MultilangrscType getMultilangrsc() {
		return (MultilangrscType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Multilangrsc(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMultilangrsc(MultilangrscType newMultilangrsc, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Multilangrsc(), newMultilangrsc, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMultilangrsc(MultilangrscType newMultilangrsc) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Multilangrsc(), newMultilangrsc);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MultipledeclarationsType getMultipledeclarations() {
		return (MultipledeclarationsType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Multipledeclarations(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMultipledeclarations(MultipledeclarationsType newMultipledeclarations, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Multipledeclarations(), newMultipledeclarations, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMultipledeclarations(MultipledeclarationsType newMultipledeclarations) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Multipledeclarations(), newMultipledeclarations);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MultipleinheritanceType getMultipleinheritance() {
		return (MultipleinheritanceType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Multipleinheritance(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMultipleinheritance(MultipleinheritanceType newMultipleinheritance, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Multipleinheritance(), newMultipleinheritance, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMultipleinheritance(MultipleinheritanceType newMultipleinheritance) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Multipleinheritance(), newMultipleinheritance);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MydocsType getMydocs() {
		return (MydocsType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Mydocs(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMydocs(MydocsType newMydocs, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Mydocs(), newMydocs, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMydocs(MydocsType newMydocs) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Mydocs(), newMydocs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamespaceType getNamespace() {
		return (NamespaceType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Namespace(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNamespace(NamespaceType newNamespace, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Namespace(), newNamespace, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNamespace(NamespaceType newNamespace) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Namespace(), newNamespace);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NewlreferencesType getNewlreferences() {
		return (NewlreferencesType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Newlreferences(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNewlreferences(NewlreferencesType newNewlreferences, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Newlreferences(), newNewlreferences, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNewlreferences(NewlreferencesType newNewlreferences) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Newlreferences(), newNewlreferences);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NoleavetrapType getNoleavetrap() {
		return (NoleavetrapType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Noleavetrap(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNoleavetrap(NoleavetrapType newNoleavetrap, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Noleavetrap(), newNoleavetrap, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNoleavetrap(NoleavetrapType newNoleavetrap) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Noleavetrap(), newNoleavetrap);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonconsthbufcType getNonconsthbufc() {
		return (NonconsthbufcType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Nonconsthbufc(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNonconsthbufc(NonconsthbufcType newNonconsthbufc, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Nonconsthbufc(), newNonconsthbufc, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNonconsthbufc(NonconsthbufcType newNonconsthbufc) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Nonconsthbufc(), newNonconsthbufc);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonconsttdescType getNonconsttdesc() {
		return (NonconsttdescType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Nonconsttdesc(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNonconsttdesc(NonconsttdescType newNonconsttdesc, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Nonconsttdesc(), newNonconsttdesc, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNonconsttdesc(NonconsttdescType newNonconsttdesc) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Nonconsttdesc(), newNonconsttdesc);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonleavenewType getNonleavenew() {
		return (NonleavenewType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Nonleavenew(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNonleavenew(NonleavenewType newNonleavenew, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Nonleavenew(), newNonleavenew, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNonleavenew(NonleavenewType newNonleavenew) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Nonleavenew(), newNonleavenew);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonunicodeskinsType getNonunicodeskins() {
		return (NonunicodeskinsType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Nonunicodeskins(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNonunicodeskins(NonunicodeskinsType newNonunicodeskins, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Nonunicodeskins(), newNonunicodeskins, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNonunicodeskins(NonunicodeskinsType newNonunicodeskins) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Nonunicodeskins(), newNonunicodeskins);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NullType getNull() {
		return (NullType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Null(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNull(NullType newNull, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Null(), newNull, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNull(NullType newNull) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Null(), newNull);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OpenType getOpen() {
		return (OpenType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Open(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOpen(OpenType newOpen, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Open(), newOpen, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOpen(OpenType newOpen) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Open(), newOpen);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PointertoarraysType getPointertoarrays() {
		return (PointertoarraysType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Pointertoarrays(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPointertoarrays(PointertoarraysType newPointertoarrays, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Pointertoarrays(), newPointertoarrays, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPointertoarrays(PointertoarraysType newPointertoarrays) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Pointertoarrays(), newPointertoarrays);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PragmadisableType getPragmadisable() {
		return (PragmadisableType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Pragmadisable(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPragmadisable(PragmadisableType newPragmadisable, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Pragmadisable(), newPragmadisable, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPragmadisable(PragmadisableType newPragmadisable) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Pragmadisable(), newPragmadisable);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PragmamessageType getPragmamessage() {
		return (PragmamessageType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Pragmamessage(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPragmamessage(PragmamessageType newPragmamessage, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Pragmamessage(), newPragmamessage, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPragmamessage(PragmamessageType newPragmamessage) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Pragmamessage(), newPragmamessage);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PragmaotherType getPragmaother() {
		return (PragmaotherType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Pragmaother(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPragmaother(PragmaotherType newPragmaother, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Pragmaother(), newPragmaother, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPragmaother(PragmaotherType newPragmaother) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Pragmaother(), newPragmaother);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PrivateinheritanceType getPrivateinheritance() {
		return (PrivateinheritanceType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Privateinheritance(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPrivateinheritance(PrivateinheritanceType newPrivateinheritance, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Privateinheritance(), newPrivateinheritance, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrivateinheritance(PrivateinheritanceType newPrivateinheritance) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Privateinheritance(), newPrivateinheritance);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PushaddrvarType getPushaddrvar() {
		return (PushaddrvarType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Pushaddrvar(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPushaddrvar(PushaddrvarType newPushaddrvar, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Pushaddrvar(), newPushaddrvar, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPushaddrvar(PushaddrvarType newPushaddrvar) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Pushaddrvar(), newPushaddrvar);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PushmemberType getPushmember() {
		return (PushmemberType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Pushmember(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPushmember(PushmemberType newPushmember, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Pushmember(), newPushmember, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPushmember(PushmemberType newPushmember) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Pushmember(), newPushmember);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReadresourceType getReadresource() {
		return (ReadresourceType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Readresource(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReadresource(ReadresourceType newReadresource, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Readresource(), newReadresource, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReadresource(ReadresourceType newReadresource) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Readresource(), newReadresource);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourcenotoncleanupstackType getResourcenotoncleanupstack() {
		return (ResourcenotoncleanupstackType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Resourcenotoncleanupstack(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetResourcenotoncleanupstack(ResourcenotoncleanupstackType newResourcenotoncleanupstack, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Resourcenotoncleanupstack(), newResourcenotoncleanupstack, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResourcenotoncleanupstack(ResourcenotoncleanupstackType newResourcenotoncleanupstack) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Resourcenotoncleanupstack(), newResourcenotoncleanupstack);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourcesonheapType getResourcesonheap() {
		return (ResourcesonheapType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Resourcesonheap(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetResourcesonheap(ResourcesonheapType newResourcesonheap, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Resourcesonheap(), newResourcesonheap, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResourcesonheap(ResourcesonheapType newResourcesonheap) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Resourcesonheap(), newResourcesonheap);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReturndescriptoroutofscopeType getReturndescriptoroutofscope() {
		return (ReturndescriptoroutofscopeType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Returndescriptoroutofscope(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReturndescriptoroutofscope(ReturndescriptoroutofscopeType newReturndescriptoroutofscope, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Returndescriptoroutofscope(), newReturndescriptoroutofscope, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReturndescriptoroutofscope(ReturndescriptoroutofscopeType newReturndescriptoroutofscope) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Returndescriptoroutofscope(), newReturndescriptoroutofscope);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RfsType getRfs() {
		return (RfsType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Rfs(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRfs(RfsType newRfs, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Rfs(), newRfs, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRfs(RfsType newRfs) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Rfs(), newRfs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RssnamesType getRssnames() {
		return (RssnamesType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Rssnames(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRssnames(RssnamesType newRssnames, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Rssnames(), newRssnames, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRssnames(RssnamesType newRssnames) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Rssnames(), newRssnames);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringliteralsType getStringliterals() {
		return (StringliteralsType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Stringliterals(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStringliterals(StringliteralsType newStringliterals, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Stringliterals(), newStringliterals, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStringliterals(StringliteralsType newStringliterals) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Stringliterals(), newStringliterals);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringsinresourcefilesType getStringsinresourcefiles() {
		return (StringsinresourcefilesType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Stringsinresourcefiles(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStringsinresourcefiles(StringsinresourcefilesType newStringsinresourcefiles, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Stringsinresourcefiles(), newStringsinresourcefiles, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStringsinresourcefiles(StringsinresourcefilesType newStringsinresourcefiles) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Stringsinresourcefiles(), newStringsinresourcefiles);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StructType getStruct() {
		return (StructType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Struct(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStruct(StructType newStruct, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Struct(), newStruct, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStruct(StructType newStruct) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Struct(), newStruct);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TcclassesType getTcclasses() {
		return (TcclassesType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Tcclasses(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTcclasses(TcclassesType newTcclasses, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Tcclasses(), newTcclasses, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTcclasses(TcclassesType newTcclasses) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Tcclasses(), newTcclasses);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TclassdestructorType getTclassdestructor() {
		return (TclassdestructorType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Tclassdestructor(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTclassdestructor(TclassdestructorType newTclassdestructor, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Tclassdestructor(), newTclassdestructor, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTclassdestructor(TclassdestructorType newTclassdestructor) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Tclassdestructor(), newTclassdestructor);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TodocommentsType getTodocomments() {
		return (TodocommentsType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Todocomments(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTodocomments(TodocommentsType newTodocomments, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Todocomments(), newTodocomments, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTodocomments(TodocommentsType newTodocomments) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Todocomments(), newTodocomments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TrapcleanupType getTrapcleanup() {
		return (TrapcleanupType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Trapcleanup(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTrapcleanup(TrapcleanupType newTrapcleanup, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Trapcleanup(), newTrapcleanup, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTrapcleanup(TrapcleanupType newTrapcleanup) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Trapcleanup(), newTrapcleanup);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TrapeleaveType getTrapeleave() {
		return (TrapeleaveType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Trapeleave(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTrapeleave(TrapeleaveType newTrapeleave, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Trapeleave(), newTrapeleave, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTrapeleave(TrapeleaveType newTrapeleave) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Trapeleave(), newTrapeleave);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TraprunlType getTraprunl() {
		return (TraprunlType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Traprunl(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTraprunl(TraprunlType newTraprunl, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Traprunl(), newTraprunl, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTraprunl(TraprunlType newTraprunl) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Traprunl(), newTraprunl);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TrspassingType getTrspassing() {
		return (TrspassingType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Trspassing(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTrspassing(TrspassingType newTrspassing, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Trspassing(), newTrspassing, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTrspassing(TrspassingType newTrspassing) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Trspassing(), newTrspassing);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UidsType getUids() {
		return (UidsType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Uids(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUids(UidsType newUids, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Uids(), newUids, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUids(UidsType newUids) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Uids(), newUids);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UncompressedaifType getUncompressedaif() {
		return (UncompressedaifType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Uncompressedaif(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUncompressedaif(UncompressedaifType newUncompressedaif, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Uncompressedaif(), newUncompressedaif, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUncompressedaif(UncompressedaifType newUncompressedaif) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Uncompressedaif(), newUncompressedaif);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UncompressedbmpType getUncompressedbmp() {
		return (UncompressedbmpType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Uncompressedbmp(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUncompressedbmp(UncompressedbmpType newUncompressedbmp, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Uncompressedbmp(), newUncompressedbmp, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUncompressedbmp(UncompressedbmpType newUncompressedbmp) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Uncompressedbmp(), newUncompressedbmp);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnicodesourceType getUnicodesource() {
		return (UnicodesourceType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Unicodesource(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUnicodesource(UnicodesourceType newUnicodesource, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Unicodesource(), newUnicodesource, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUnicodesource(UnicodesourceType newUnicodesource) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Unicodesource(), newUnicodesource);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserafterType getUserafter() {
		return (UserafterType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Userafter(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUserafter(UserafterType newUserafter, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Userafter(), newUserafter, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserafter(UserafterType newUserafter) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Userafter(), newUserafter);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserfreeType getUserfree() {
		return (UserfreeType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Userfree(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUserfree(UserfreeType newUserfree, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Userfree(), newUserfree, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserfree(UserfreeType newUserfree) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Userfree(), newUserfree);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserWaitForRequestType getUserWaitForRequest() {
		return (UserWaitForRequestType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_UserWaitForRequest(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUserWaitForRequest(UserWaitForRequestType newUserWaitForRequest, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_UserWaitForRequest(), newUserWaitForRequest, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserWaitForRequest(UserWaitForRequestType newUserWaitForRequest) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_UserWaitForRequest(), newUserWaitForRequest);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VariablenamesType getVariablenames() {
		return (VariablenamesType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Variablenames(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVariablenames(VariablenamesType newVariablenames, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Variablenames(), newVariablenames, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVariablenames(VariablenamesType newVariablenames) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Variablenames(), newVariablenames);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VoidparameterType getVoidparameter() {
		return (VoidparameterType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Voidparameter(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVoidparameter(VoidparameterType newVoidparameter, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Voidparameter(), newVoidparameter, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVoidparameter(VoidparameterType newVoidparameter) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Voidparameter(), newVoidparameter);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WorryingcommentsType getWorryingcomments() {
		return (WorryingcommentsType)getMixed().get(CSConfigPackage.eINSTANCE.getScriptsType_Worryingcomments(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetWorryingcomments(WorryingcommentsType newWorryingcomments, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CSConfigPackage.eINSTANCE.getScriptsType_Worryingcomments(), newWorryingcomments, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWorryingcomments(WorryingcommentsType newWorryingcomments) {
		((FeatureMap.Internal)getMixed()).set(CSConfigPackage.eINSTANCE.getScriptsType_Worryingcomments(), newWorryingcomments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CSConfigPackage.SCRIPTS_TYPE__MIXED:
				return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__ACCESS_ARRAY_ELEMENT_WITHOUT_CHECK:
				return basicSetAccessArrayElementWithoutCheck(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__ACCESS_ARRAY_ELEMENT_WITHOUT_CHECK2:
				return basicSetAccessArrayElementWithoutCheck2(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__ACTIVESTART:
				return basicSetActivestart(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__ACTIVESTOP:
				return basicSetActivestop(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__ARRAYPASSING:
				return basicSetArraypassing(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__ARRAYPTRCLEANUP:
				return basicSetArrayptrcleanup(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__ASSERTDEBUGINVARIANT:
				return basicSetAssertdebuginvariant(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__BADDEFINES:
				return basicSetBaddefines(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__BASECONSTRUCT:
				return basicSetBaseconstruct(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__CALL_ACTIVE_OBJECT_WITHOUT_CHECKING_OR_STOPPING:
				return basicSetCallActiveObjectWithoutCheckingOrStopping(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__CHANGENOTIFICATION:
				return basicSetChangenotification(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__CLEANUP:
				return basicSetCleanup(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__COMMENTCODE:
				return basicSetCommentcode(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__CONNECT:
				return basicSetConnect(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__CONNECT_AND_DONT_CLOSE_MEMBER_VARIABLE:
				return basicSetConnectAndDontCloseMemberVariable(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__CONSTNAMES:
				return basicSetConstnames(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__CONSTTDESCPTR:
				return basicSetConsttdescptr(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__CONTROLORNULL:
				return basicSetControlornull(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__CTLTARGETTYPE:
				return basicSetCtltargettype(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__DEBUGROM:
				return basicSetDebugrom(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__DECLARENAME:
				return basicSetDeclarename(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__DELETE_MEMBER_VARIABLE:
				return basicSetDeleteMemberVariable(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__DESTRUCTOR:
				return basicSetDestructor(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__DOUBLE_SEMI_COLON:
				return basicSetDoubleSemiColon(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__DRIVELETTERS:
				return basicSetDriveletters(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__EIKBUTTONS:
				return basicSetEikbuttons(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__EIKONENVSTATIC:
				return basicSetEikonenvstatic(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__ENUMMEMBERS:
				return basicSetEnummembers(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__ENUMNAMES:
				return basicSetEnumnames(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__EXPORTINLINE:
				return basicSetExportinline(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__EXPORTPUREVIRTUAL:
				return basicSetExportpurevirtual(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__EXTERNALDRIVELETTERS:
				return basicSetExternaldriveletters(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__FOFF:
				return basicSetFoff(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__FORBIDDENWORDS:
				return basicSetForbiddenwords(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__FORGOTTOPUTPTRONCLEANUPSTACK:
				return basicSetForgottoputptroncleanupstack(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__FRIEND:
				return basicSetFriend(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__GOTO:
				return basicSetGoto(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__IFASSIGNMENTS:
				return basicSetIfassignments(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__IFPREPROCESSOR:
				return basicSetIfpreprocessor(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__INHERITANCEORDER:
				return basicSetInheritanceorder(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__INTLEAVES:
				return basicSetIntleaves(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__JMP:
				return basicSetJmp(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__LEAVE:
				return basicSetLeave(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__LEAVE_NO_ERROR:
				return basicSetLeaveNoError(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__LEAVINGOPERATORS:
				return basicSetLeavingoperators(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__LFUNCTION_CANT_LEAVE:
				return basicSetLFunctionCantLeave(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__LONGLINES:
				return basicSetLonglines(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__MAGICNUMBERS:
				return basicSetMagicnumbers(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__MCLASSDESTRUCTOR:
				return basicSetMclassdestructor(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__MEMBERLC:
				return basicSetMemberlc(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__MEMBERVARIABLECALLLD:
				return basicSetMembervariablecallld(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__MISSINGCANCEL:
				return basicSetMissingcancel(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__MISSINGCCLASS:
				return basicSetMissingcclass(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__MMPSOURCEPATH:
				return basicSetMmpsourcepath(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__MULTILANGRSC:
				return basicSetMultilangrsc(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__MULTIPLEDECLARATIONS:
				return basicSetMultipledeclarations(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__MULTIPLEINHERITANCE:
				return basicSetMultipleinheritance(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__MYDOCS:
				return basicSetMydocs(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__NAMESPACE:
				return basicSetNamespace(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__NEWLREFERENCES:
				return basicSetNewlreferences(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__NOLEAVETRAP:
				return basicSetNoleavetrap(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__NONCONSTHBUFC:
				return basicSetNonconsthbufc(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__NONCONSTTDESC:
				return basicSetNonconsttdesc(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__NONLEAVENEW:
				return basicSetNonleavenew(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__NONUNICODESKINS:
				return basicSetNonunicodeskins(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__NULL:
				return basicSetNull(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__OPEN:
				return basicSetOpen(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__POINTERTOARRAYS:
				return basicSetPointertoarrays(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__PRAGMADISABLE:
				return basicSetPragmadisable(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__PRAGMAMESSAGE:
				return basicSetPragmamessage(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__PRAGMAOTHER:
				return basicSetPragmaother(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__PRIVATEINHERITANCE:
				return basicSetPrivateinheritance(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__PUSHADDRVAR:
				return basicSetPushaddrvar(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__PUSHMEMBER:
				return basicSetPushmember(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__READRESOURCE:
				return basicSetReadresource(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__RESOURCENOTONCLEANUPSTACK:
				return basicSetResourcenotoncleanupstack(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__RESOURCESONHEAP:
				return basicSetResourcesonheap(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__RETURNDESCRIPTOROUTOFSCOPE:
				return basicSetReturndescriptoroutofscope(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__RFS:
				return basicSetRfs(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__RSSNAMES:
				return basicSetRssnames(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__STRINGLITERALS:
				return basicSetStringliterals(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__STRINGSINRESOURCEFILES:
				return basicSetStringsinresourcefiles(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__STRUCT:
				return basicSetStruct(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__TCCLASSES:
				return basicSetTcclasses(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__TCLASSDESTRUCTOR:
				return basicSetTclassdestructor(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__TODOCOMMENTS:
				return basicSetTodocomments(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__TRAPCLEANUP:
				return basicSetTrapcleanup(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__TRAPELEAVE:
				return basicSetTrapeleave(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__TRAPRUNL:
				return basicSetTraprunl(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__TRSPASSING:
				return basicSetTrspassing(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__UIDS:
				return basicSetUids(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__UNCOMPRESSEDAIF:
				return basicSetUncompressedaif(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__UNCOMPRESSEDBMP:
				return basicSetUncompressedbmp(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__UNICODESOURCE:
				return basicSetUnicodesource(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__USERAFTER:
				return basicSetUserafter(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__USERFREE:
				return basicSetUserfree(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__USER_WAIT_FOR_REQUEST:
				return basicSetUserWaitForRequest(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__VARIABLENAMES:
				return basicSetVariablenames(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__VOIDPARAMETER:
				return basicSetVoidparameter(null, msgs);
			case CSConfigPackage.SCRIPTS_TYPE__WORRYINGCOMMENTS:
				return basicSetWorryingcomments(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CSConfigPackage.SCRIPTS_TYPE__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case CSConfigPackage.SCRIPTS_TYPE__ACCESS_ARRAY_ELEMENT_WITHOUT_CHECK:
				return getAccessArrayElementWithoutCheck();
			case CSConfigPackage.SCRIPTS_TYPE__ACCESS_ARRAY_ELEMENT_WITHOUT_CHECK2:
				return getAccessArrayElementWithoutCheck2();
			case CSConfigPackage.SCRIPTS_TYPE__ACTIVESTART:
				return getActivestart();
			case CSConfigPackage.SCRIPTS_TYPE__ACTIVESTOP:
				return getActivestop();
			case CSConfigPackage.SCRIPTS_TYPE__ARRAYPASSING:
				return getArraypassing();
			case CSConfigPackage.SCRIPTS_TYPE__ARRAYPTRCLEANUP:
				return getArrayptrcleanup();
			case CSConfigPackage.SCRIPTS_TYPE__ASSERTDEBUGINVARIANT:
				return getAssertdebuginvariant();
			case CSConfigPackage.SCRIPTS_TYPE__BADDEFINES:
				return getBaddefines();
			case CSConfigPackage.SCRIPTS_TYPE__BASECONSTRUCT:
				return getBaseconstruct();
			case CSConfigPackage.SCRIPTS_TYPE__CALL_ACTIVE_OBJECT_WITHOUT_CHECKING_OR_STOPPING:
				return getCallActiveObjectWithoutCheckingOrStopping();
			case CSConfigPackage.SCRIPTS_TYPE__CHANGENOTIFICATION:
				return getChangenotification();
			case CSConfigPackage.SCRIPTS_TYPE__CLEANUP:
				return getCleanup();
			case CSConfigPackage.SCRIPTS_TYPE__COMMENTCODE:
				return getCommentcode();
			case CSConfigPackage.SCRIPTS_TYPE__CONNECT:
				return getConnect();
			case CSConfigPackage.SCRIPTS_TYPE__CONNECT_AND_DONT_CLOSE_MEMBER_VARIABLE:
				return getConnectAndDontCloseMemberVariable();
			case CSConfigPackage.SCRIPTS_TYPE__CONSTNAMES:
				return getConstnames();
			case CSConfigPackage.SCRIPTS_TYPE__CONSTTDESCPTR:
				return getConsttdescptr();
			case CSConfigPackage.SCRIPTS_TYPE__CONTROLORNULL:
				return getControlornull();
			case CSConfigPackage.SCRIPTS_TYPE__CTLTARGETTYPE:
				return getCtltargettype();
			case CSConfigPackage.SCRIPTS_TYPE__DEBUGROM:
				return getDebugrom();
			case CSConfigPackage.SCRIPTS_TYPE__DECLARENAME:
				return getDeclarename();
			case CSConfigPackage.SCRIPTS_TYPE__DELETE_MEMBER_VARIABLE:
				return getDeleteMemberVariable();
			case CSConfigPackage.SCRIPTS_TYPE__DESTRUCTOR:
				return getDestructor();
			case CSConfigPackage.SCRIPTS_TYPE__DOUBLE_SEMI_COLON:
				return getDoubleSemiColon();
			case CSConfigPackage.SCRIPTS_TYPE__DRIVELETTERS:
				return getDriveletters();
			case CSConfigPackage.SCRIPTS_TYPE__EIKBUTTONS:
				return getEikbuttons();
			case CSConfigPackage.SCRIPTS_TYPE__EIKONENVSTATIC:
				return getEikonenvstatic();
			case CSConfigPackage.SCRIPTS_TYPE__ENUMMEMBERS:
				return getEnummembers();
			case CSConfigPackage.SCRIPTS_TYPE__ENUMNAMES:
				return getEnumnames();
			case CSConfigPackage.SCRIPTS_TYPE__EXPORTINLINE:
				return getExportinline();
			case CSConfigPackage.SCRIPTS_TYPE__EXPORTPUREVIRTUAL:
				return getExportpurevirtual();
			case CSConfigPackage.SCRIPTS_TYPE__EXTERNALDRIVELETTERS:
				return getExternaldriveletters();
			case CSConfigPackage.SCRIPTS_TYPE__FOFF:
				return getFoff();
			case CSConfigPackage.SCRIPTS_TYPE__FORBIDDENWORDS:
				return getForbiddenwords();
			case CSConfigPackage.SCRIPTS_TYPE__FORGOTTOPUTPTRONCLEANUPSTACK:
				return getForgottoputptroncleanupstack();
			case CSConfigPackage.SCRIPTS_TYPE__FRIEND:
				return getFriend();
			case CSConfigPackage.SCRIPTS_TYPE__GOTO:
				return getGoto();
			case CSConfigPackage.SCRIPTS_TYPE__IFASSIGNMENTS:
				return getIfassignments();
			case CSConfigPackage.SCRIPTS_TYPE__IFPREPROCESSOR:
				return getIfpreprocessor();
			case CSConfigPackage.SCRIPTS_TYPE__INHERITANCEORDER:
				return getInheritanceorder();
			case CSConfigPackage.SCRIPTS_TYPE__INTLEAVES:
				return getIntleaves();
			case CSConfigPackage.SCRIPTS_TYPE__JMP:
				return getJmp();
			case CSConfigPackage.SCRIPTS_TYPE__LEAVE:
				return getLeave();
			case CSConfigPackage.SCRIPTS_TYPE__LEAVE_NO_ERROR:
				return getLeaveNoError();
			case CSConfigPackage.SCRIPTS_TYPE__LEAVINGOPERATORS:
				return getLeavingoperators();
			case CSConfigPackage.SCRIPTS_TYPE__LFUNCTION_CANT_LEAVE:
				return getLFunctionCantLeave();
			case CSConfigPackage.SCRIPTS_TYPE__LONGLINES:
				return getLonglines();
			case CSConfigPackage.SCRIPTS_TYPE__MAGICNUMBERS:
				return getMagicnumbers();
			case CSConfigPackage.SCRIPTS_TYPE__MCLASSDESTRUCTOR:
				return getMclassdestructor();
			case CSConfigPackage.SCRIPTS_TYPE__MEMBERLC:
				return getMemberlc();
			case CSConfigPackage.SCRIPTS_TYPE__MEMBERVARIABLECALLLD:
				return getMembervariablecallld();
			case CSConfigPackage.SCRIPTS_TYPE__MISSINGCANCEL:
				return getMissingcancel();
			case CSConfigPackage.SCRIPTS_TYPE__MISSINGCCLASS:
				return getMissingcclass();
			case CSConfigPackage.SCRIPTS_TYPE__MMPSOURCEPATH:
				return getMmpsourcepath();
			case CSConfigPackage.SCRIPTS_TYPE__MULTILANGRSC:
				return getMultilangrsc();
			case CSConfigPackage.SCRIPTS_TYPE__MULTIPLEDECLARATIONS:
				return getMultipledeclarations();
			case CSConfigPackage.SCRIPTS_TYPE__MULTIPLEINHERITANCE:
				return getMultipleinheritance();
			case CSConfigPackage.SCRIPTS_TYPE__MYDOCS:
				return getMydocs();
			case CSConfigPackage.SCRIPTS_TYPE__NAMESPACE:
				return getNamespace();
			case CSConfigPackage.SCRIPTS_TYPE__NEWLREFERENCES:
				return getNewlreferences();
			case CSConfigPackage.SCRIPTS_TYPE__NOLEAVETRAP:
				return getNoleavetrap();
			case CSConfigPackage.SCRIPTS_TYPE__NONCONSTHBUFC:
				return getNonconsthbufc();
			case CSConfigPackage.SCRIPTS_TYPE__NONCONSTTDESC:
				return getNonconsttdesc();
			case CSConfigPackage.SCRIPTS_TYPE__NONLEAVENEW:
				return getNonleavenew();
			case CSConfigPackage.SCRIPTS_TYPE__NONUNICODESKINS:
				return getNonunicodeskins();
			case CSConfigPackage.SCRIPTS_TYPE__NULL:
				return getNull();
			case CSConfigPackage.SCRIPTS_TYPE__OPEN:
				return getOpen();
			case CSConfigPackage.SCRIPTS_TYPE__POINTERTOARRAYS:
				return getPointertoarrays();
			case CSConfigPackage.SCRIPTS_TYPE__PRAGMADISABLE:
				return getPragmadisable();
			case CSConfigPackage.SCRIPTS_TYPE__PRAGMAMESSAGE:
				return getPragmamessage();
			case CSConfigPackage.SCRIPTS_TYPE__PRAGMAOTHER:
				return getPragmaother();
			case CSConfigPackage.SCRIPTS_TYPE__PRIVATEINHERITANCE:
				return getPrivateinheritance();
			case CSConfigPackage.SCRIPTS_TYPE__PUSHADDRVAR:
				return getPushaddrvar();
			case CSConfigPackage.SCRIPTS_TYPE__PUSHMEMBER:
				return getPushmember();
			case CSConfigPackage.SCRIPTS_TYPE__READRESOURCE:
				return getReadresource();
			case CSConfigPackage.SCRIPTS_TYPE__RESOURCENOTONCLEANUPSTACK:
				return getResourcenotoncleanupstack();
			case CSConfigPackage.SCRIPTS_TYPE__RESOURCESONHEAP:
				return getResourcesonheap();
			case CSConfigPackage.SCRIPTS_TYPE__RETURNDESCRIPTOROUTOFSCOPE:
				return getReturndescriptoroutofscope();
			case CSConfigPackage.SCRIPTS_TYPE__RFS:
				return getRfs();
			case CSConfigPackage.SCRIPTS_TYPE__RSSNAMES:
				return getRssnames();
			case CSConfigPackage.SCRIPTS_TYPE__STRINGLITERALS:
				return getStringliterals();
			case CSConfigPackage.SCRIPTS_TYPE__STRINGSINRESOURCEFILES:
				return getStringsinresourcefiles();
			case CSConfigPackage.SCRIPTS_TYPE__STRUCT:
				return getStruct();
			case CSConfigPackage.SCRIPTS_TYPE__TCCLASSES:
				return getTcclasses();
			case CSConfigPackage.SCRIPTS_TYPE__TCLASSDESTRUCTOR:
				return getTclassdestructor();
			case CSConfigPackage.SCRIPTS_TYPE__TODOCOMMENTS:
				return getTodocomments();
			case CSConfigPackage.SCRIPTS_TYPE__TRAPCLEANUP:
				return getTrapcleanup();
			case CSConfigPackage.SCRIPTS_TYPE__TRAPELEAVE:
				return getTrapeleave();
			case CSConfigPackage.SCRIPTS_TYPE__TRAPRUNL:
				return getTraprunl();
			case CSConfigPackage.SCRIPTS_TYPE__TRSPASSING:
				return getTrspassing();
			case CSConfigPackage.SCRIPTS_TYPE__UIDS:
				return getUids();
			case CSConfigPackage.SCRIPTS_TYPE__UNCOMPRESSEDAIF:
				return getUncompressedaif();
			case CSConfigPackage.SCRIPTS_TYPE__UNCOMPRESSEDBMP:
				return getUncompressedbmp();
			case CSConfigPackage.SCRIPTS_TYPE__UNICODESOURCE:
				return getUnicodesource();
			case CSConfigPackage.SCRIPTS_TYPE__USERAFTER:
				return getUserafter();
			case CSConfigPackage.SCRIPTS_TYPE__USERFREE:
				return getUserfree();
			case CSConfigPackage.SCRIPTS_TYPE__USER_WAIT_FOR_REQUEST:
				return getUserWaitForRequest();
			case CSConfigPackage.SCRIPTS_TYPE__VARIABLENAMES:
				return getVariablenames();
			case CSConfigPackage.SCRIPTS_TYPE__VOIDPARAMETER:
				return getVoidparameter();
			case CSConfigPackage.SCRIPTS_TYPE__WORRYINGCOMMENTS:
				return getWorryingcomments();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CSConfigPackage.SCRIPTS_TYPE__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__ACCESS_ARRAY_ELEMENT_WITHOUT_CHECK:
				setAccessArrayElementWithoutCheck((AccessArrayElementWithoutCheckType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__ACCESS_ARRAY_ELEMENT_WITHOUT_CHECK2:
				setAccessArrayElementWithoutCheck2((AccessArrayElementWithoutCheck2Type)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__ACTIVESTART:
				setActivestart((ActivestartType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__ACTIVESTOP:
				setActivestop((ActivestopType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__ARRAYPASSING:
				setArraypassing((ArraypassingType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__ARRAYPTRCLEANUP:
				setArrayptrcleanup((ArrayptrcleanupType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__ASSERTDEBUGINVARIANT:
				setAssertdebuginvariant((AssertdebuginvariantType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__BADDEFINES:
				setBaddefines((BaddefinesType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__BASECONSTRUCT:
				setBaseconstruct((BaseconstructType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__CALL_ACTIVE_OBJECT_WITHOUT_CHECKING_OR_STOPPING:
				setCallActiveObjectWithoutCheckingOrStopping((CallActiveObjectWithoutCheckingOrStoppingType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__CHANGENOTIFICATION:
				setChangenotification((ChangenotificationType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__CLEANUP:
				setCleanup((CleanupType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__COMMENTCODE:
				setCommentcode((CommentcodeType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__CONNECT:
				setConnect((ConnectType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__CONNECT_AND_DONT_CLOSE_MEMBER_VARIABLE:
				setConnectAndDontCloseMemberVariable((ConnectAndDontCloseMemberVariableType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__CONSTNAMES:
				setConstnames((ConstnamesType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__CONSTTDESCPTR:
				setConsttdescptr((ConsttdescptrType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__CONTROLORNULL:
				setControlornull((ControlornullType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__CTLTARGETTYPE:
				setCtltargettype((CtltargettypeType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__DEBUGROM:
				setDebugrom((DebugromType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__DECLARENAME:
				setDeclarename((DeclarenameType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__DELETE_MEMBER_VARIABLE:
				setDeleteMemberVariable((DeleteMemberVariableType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__DESTRUCTOR:
				setDestructor((DestructorType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__DOUBLE_SEMI_COLON:
				setDoubleSemiColon((DoubleSemiColonType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__DRIVELETTERS:
				setDriveletters((DrivelettersType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__EIKBUTTONS:
				setEikbuttons((EikbuttonsType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__EIKONENVSTATIC:
				setEikonenvstatic((EikonenvstaticType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__ENUMMEMBERS:
				setEnummembers((EnummembersType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__ENUMNAMES:
				setEnumnames((EnumnamesType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__EXPORTINLINE:
				setExportinline((ExportinlineType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__EXPORTPUREVIRTUAL:
				setExportpurevirtual((ExportpurevirtualType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__EXTERNALDRIVELETTERS:
				setExternaldriveletters((ExternaldrivelettersType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__FOFF:
				setFoff((FoffType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__FORBIDDENWORDS:
				setForbiddenwords((ForbiddenwordsType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__FORGOTTOPUTPTRONCLEANUPSTACK:
				setForgottoputptroncleanupstack((ForgottoputptroncleanupstackType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__FRIEND:
				setFriend((FriendType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__GOTO:
				setGoto((GotoType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__IFASSIGNMENTS:
				setIfassignments((IfassignmentsType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__IFPREPROCESSOR:
				setIfpreprocessor((IfpreprocessorType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__INHERITANCEORDER:
				setInheritanceorder((InheritanceorderType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__INTLEAVES:
				setIntleaves((IntleavesType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__JMP:
				setJmp((JmpType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__LEAVE:
				setLeave((LeaveType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__LEAVE_NO_ERROR:
				setLeaveNoError((LeaveNoErrorType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__LEAVINGOPERATORS:
				setLeavingoperators((LeavingoperatorsType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__LFUNCTION_CANT_LEAVE:
				setLFunctionCantLeave((LFunctionCantLeaveType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__LONGLINES:
				setLonglines((LonglinesType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__MAGICNUMBERS:
				setMagicnumbers((MagicnumbersType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__MCLASSDESTRUCTOR:
				setMclassdestructor((MclassdestructorType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__MEMBERLC:
				setMemberlc((MemberlcType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__MEMBERVARIABLECALLLD:
				setMembervariablecallld((MembervariablecallldType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__MISSINGCANCEL:
				setMissingcancel((MissingcancelType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__MISSINGCCLASS:
				setMissingcclass((MissingcclassType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__MMPSOURCEPATH:
				setMmpsourcepath((MmpsourcepathType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__MULTILANGRSC:
				setMultilangrsc((MultilangrscType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__MULTIPLEDECLARATIONS:
				setMultipledeclarations((MultipledeclarationsType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__MULTIPLEINHERITANCE:
				setMultipleinheritance((MultipleinheritanceType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__MYDOCS:
				setMydocs((MydocsType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__NAMESPACE:
				setNamespace((NamespaceType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__NEWLREFERENCES:
				setNewlreferences((NewlreferencesType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__NOLEAVETRAP:
				setNoleavetrap((NoleavetrapType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__NONCONSTHBUFC:
				setNonconsthbufc((NonconsthbufcType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__NONCONSTTDESC:
				setNonconsttdesc((NonconsttdescType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__NONLEAVENEW:
				setNonleavenew((NonleavenewType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__NONUNICODESKINS:
				setNonunicodeskins((NonunicodeskinsType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__NULL:
				setNull((NullType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__OPEN:
				setOpen((OpenType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__POINTERTOARRAYS:
				setPointertoarrays((PointertoarraysType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__PRAGMADISABLE:
				setPragmadisable((PragmadisableType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__PRAGMAMESSAGE:
				setPragmamessage((PragmamessageType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__PRAGMAOTHER:
				setPragmaother((PragmaotherType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__PRIVATEINHERITANCE:
				setPrivateinheritance((PrivateinheritanceType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__PUSHADDRVAR:
				setPushaddrvar((PushaddrvarType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__PUSHMEMBER:
				setPushmember((PushmemberType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__READRESOURCE:
				setReadresource((ReadresourceType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__RESOURCENOTONCLEANUPSTACK:
				setResourcenotoncleanupstack((ResourcenotoncleanupstackType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__RESOURCESONHEAP:
				setResourcesonheap((ResourcesonheapType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__RETURNDESCRIPTOROUTOFSCOPE:
				setReturndescriptoroutofscope((ReturndescriptoroutofscopeType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__RFS:
				setRfs((RfsType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__RSSNAMES:
				setRssnames((RssnamesType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__STRINGLITERALS:
				setStringliterals((StringliteralsType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__STRINGSINRESOURCEFILES:
				setStringsinresourcefiles((StringsinresourcefilesType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__STRUCT:
				setStruct((StructType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__TCCLASSES:
				setTcclasses((TcclassesType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__TCLASSDESTRUCTOR:
				setTclassdestructor((TclassdestructorType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__TODOCOMMENTS:
				setTodocomments((TodocommentsType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__TRAPCLEANUP:
				setTrapcleanup((TrapcleanupType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__TRAPELEAVE:
				setTrapeleave((TrapeleaveType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__TRAPRUNL:
				setTraprunl((TraprunlType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__TRSPASSING:
				setTrspassing((TrspassingType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__UIDS:
				setUids((UidsType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__UNCOMPRESSEDAIF:
				setUncompressedaif((UncompressedaifType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__UNCOMPRESSEDBMP:
				setUncompressedbmp((UncompressedbmpType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__UNICODESOURCE:
				setUnicodesource((UnicodesourceType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__USERAFTER:
				setUserafter((UserafterType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__USERFREE:
				setUserfree((UserfreeType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__USER_WAIT_FOR_REQUEST:
				setUserWaitForRequest((UserWaitForRequestType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__VARIABLENAMES:
				setVariablenames((VariablenamesType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__VOIDPARAMETER:
				setVoidparameter((VoidparameterType)newValue);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__WORRYINGCOMMENTS:
				setWorryingcomments((WorryingcommentsType)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case CSConfigPackage.SCRIPTS_TYPE__MIXED:
				getMixed().clear();
				return;
			case CSConfigPackage.SCRIPTS_TYPE__ACCESS_ARRAY_ELEMENT_WITHOUT_CHECK:
				setAccessArrayElementWithoutCheck((AccessArrayElementWithoutCheckType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__ACCESS_ARRAY_ELEMENT_WITHOUT_CHECK2:
				setAccessArrayElementWithoutCheck2((AccessArrayElementWithoutCheck2Type)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__ACTIVESTART:
				setActivestart((ActivestartType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__ACTIVESTOP:
				setActivestop((ActivestopType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__ARRAYPASSING:
				setArraypassing((ArraypassingType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__ARRAYPTRCLEANUP:
				setArrayptrcleanup((ArrayptrcleanupType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__ASSERTDEBUGINVARIANT:
				setAssertdebuginvariant((AssertdebuginvariantType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__BADDEFINES:
				setBaddefines((BaddefinesType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__BASECONSTRUCT:
				setBaseconstruct((BaseconstructType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__CALL_ACTIVE_OBJECT_WITHOUT_CHECKING_OR_STOPPING:
				setCallActiveObjectWithoutCheckingOrStopping((CallActiveObjectWithoutCheckingOrStoppingType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__CHANGENOTIFICATION:
				setChangenotification((ChangenotificationType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__CLEANUP:
				setCleanup((CleanupType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__COMMENTCODE:
				setCommentcode((CommentcodeType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__CONNECT:
				setConnect((ConnectType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__CONNECT_AND_DONT_CLOSE_MEMBER_VARIABLE:
				setConnectAndDontCloseMemberVariable((ConnectAndDontCloseMemberVariableType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__CONSTNAMES:
				setConstnames((ConstnamesType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__CONSTTDESCPTR:
				setConsttdescptr((ConsttdescptrType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__CONTROLORNULL:
				setControlornull((ControlornullType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__CTLTARGETTYPE:
				setCtltargettype((CtltargettypeType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__DEBUGROM:
				setDebugrom((DebugromType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__DECLARENAME:
				setDeclarename((DeclarenameType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__DELETE_MEMBER_VARIABLE:
				setDeleteMemberVariable((DeleteMemberVariableType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__DESTRUCTOR:
				setDestructor((DestructorType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__DOUBLE_SEMI_COLON:
				setDoubleSemiColon((DoubleSemiColonType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__DRIVELETTERS:
				setDriveletters((DrivelettersType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__EIKBUTTONS:
				setEikbuttons((EikbuttonsType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__EIKONENVSTATIC:
				setEikonenvstatic((EikonenvstaticType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__ENUMMEMBERS:
				setEnummembers((EnummembersType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__ENUMNAMES:
				setEnumnames((EnumnamesType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__EXPORTINLINE:
				setExportinline((ExportinlineType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__EXPORTPUREVIRTUAL:
				setExportpurevirtual((ExportpurevirtualType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__EXTERNALDRIVELETTERS:
				setExternaldriveletters((ExternaldrivelettersType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__FOFF:
				setFoff((FoffType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__FORBIDDENWORDS:
				setForbiddenwords((ForbiddenwordsType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__FORGOTTOPUTPTRONCLEANUPSTACK:
				setForgottoputptroncleanupstack((ForgottoputptroncleanupstackType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__FRIEND:
				setFriend((FriendType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__GOTO:
				setGoto((GotoType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__IFASSIGNMENTS:
				setIfassignments((IfassignmentsType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__IFPREPROCESSOR:
				setIfpreprocessor((IfpreprocessorType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__INHERITANCEORDER:
				setInheritanceorder((InheritanceorderType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__INTLEAVES:
				setIntleaves((IntleavesType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__JMP:
				setJmp((JmpType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__LEAVE:
				setLeave((LeaveType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__LEAVE_NO_ERROR:
				setLeaveNoError((LeaveNoErrorType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__LEAVINGOPERATORS:
				setLeavingoperators((LeavingoperatorsType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__LFUNCTION_CANT_LEAVE:
				setLFunctionCantLeave((LFunctionCantLeaveType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__LONGLINES:
				setLonglines((LonglinesType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__MAGICNUMBERS:
				setMagicnumbers((MagicnumbersType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__MCLASSDESTRUCTOR:
				setMclassdestructor((MclassdestructorType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__MEMBERLC:
				setMemberlc((MemberlcType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__MEMBERVARIABLECALLLD:
				setMembervariablecallld((MembervariablecallldType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__MISSINGCANCEL:
				setMissingcancel((MissingcancelType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__MISSINGCCLASS:
				setMissingcclass((MissingcclassType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__MMPSOURCEPATH:
				setMmpsourcepath((MmpsourcepathType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__MULTILANGRSC:
				setMultilangrsc((MultilangrscType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__MULTIPLEDECLARATIONS:
				setMultipledeclarations((MultipledeclarationsType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__MULTIPLEINHERITANCE:
				setMultipleinheritance((MultipleinheritanceType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__MYDOCS:
				setMydocs((MydocsType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__NAMESPACE:
				setNamespace((NamespaceType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__NEWLREFERENCES:
				setNewlreferences((NewlreferencesType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__NOLEAVETRAP:
				setNoleavetrap((NoleavetrapType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__NONCONSTHBUFC:
				setNonconsthbufc((NonconsthbufcType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__NONCONSTTDESC:
				setNonconsttdesc((NonconsttdescType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__NONLEAVENEW:
				setNonleavenew((NonleavenewType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__NONUNICODESKINS:
				setNonunicodeskins((NonunicodeskinsType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__NULL:
				setNull((NullType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__OPEN:
				setOpen((OpenType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__POINTERTOARRAYS:
				setPointertoarrays((PointertoarraysType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__PRAGMADISABLE:
				setPragmadisable((PragmadisableType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__PRAGMAMESSAGE:
				setPragmamessage((PragmamessageType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__PRAGMAOTHER:
				setPragmaother((PragmaotherType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__PRIVATEINHERITANCE:
				setPrivateinheritance((PrivateinheritanceType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__PUSHADDRVAR:
				setPushaddrvar((PushaddrvarType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__PUSHMEMBER:
				setPushmember((PushmemberType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__READRESOURCE:
				setReadresource((ReadresourceType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__RESOURCENOTONCLEANUPSTACK:
				setResourcenotoncleanupstack((ResourcenotoncleanupstackType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__RESOURCESONHEAP:
				setResourcesonheap((ResourcesonheapType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__RETURNDESCRIPTOROUTOFSCOPE:
				setReturndescriptoroutofscope((ReturndescriptoroutofscopeType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__RFS:
				setRfs((RfsType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__RSSNAMES:
				setRssnames((RssnamesType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__STRINGLITERALS:
				setStringliterals((StringliteralsType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__STRINGSINRESOURCEFILES:
				setStringsinresourcefiles((StringsinresourcefilesType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__STRUCT:
				setStruct((StructType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__TCCLASSES:
				setTcclasses((TcclassesType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__TCLASSDESTRUCTOR:
				setTclassdestructor((TclassdestructorType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__TODOCOMMENTS:
				setTodocomments((TodocommentsType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__TRAPCLEANUP:
				setTrapcleanup((TrapcleanupType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__TRAPELEAVE:
				setTrapeleave((TrapeleaveType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__TRAPRUNL:
				setTraprunl((TraprunlType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__TRSPASSING:
				setTrspassing((TrspassingType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__UIDS:
				setUids((UidsType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__UNCOMPRESSEDAIF:
				setUncompressedaif((UncompressedaifType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__UNCOMPRESSEDBMP:
				setUncompressedbmp((UncompressedbmpType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__UNICODESOURCE:
				setUnicodesource((UnicodesourceType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__USERAFTER:
				setUserafter((UserafterType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__USERFREE:
				setUserfree((UserfreeType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__USER_WAIT_FOR_REQUEST:
				setUserWaitForRequest((UserWaitForRequestType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__VARIABLENAMES:
				setVariablenames((VariablenamesType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__VOIDPARAMETER:
				setVoidparameter((VoidparameterType)null);
				return;
			case CSConfigPackage.SCRIPTS_TYPE__WORRYINGCOMMENTS:
				setWorryingcomments((WorryingcommentsType)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case CSConfigPackage.SCRIPTS_TYPE__MIXED:
				return mixed != null && !mixed.isEmpty();
			case CSConfigPackage.SCRIPTS_TYPE__ACCESS_ARRAY_ELEMENT_WITHOUT_CHECK:
				return getAccessArrayElementWithoutCheck() != null;
			case CSConfigPackage.SCRIPTS_TYPE__ACCESS_ARRAY_ELEMENT_WITHOUT_CHECK2:
				return getAccessArrayElementWithoutCheck2() != null;
			case CSConfigPackage.SCRIPTS_TYPE__ACTIVESTART:
				return getActivestart() != null;
			case CSConfigPackage.SCRIPTS_TYPE__ACTIVESTOP:
				return getActivestop() != null;
			case CSConfigPackage.SCRIPTS_TYPE__ARRAYPASSING:
				return getArraypassing() != null;
			case CSConfigPackage.SCRIPTS_TYPE__ARRAYPTRCLEANUP:
				return getArrayptrcleanup() != null;
			case CSConfigPackage.SCRIPTS_TYPE__ASSERTDEBUGINVARIANT:
				return getAssertdebuginvariant() != null;
			case CSConfigPackage.SCRIPTS_TYPE__BADDEFINES:
				return getBaddefines() != null;
			case CSConfigPackage.SCRIPTS_TYPE__BASECONSTRUCT:
				return getBaseconstruct() != null;
			case CSConfigPackage.SCRIPTS_TYPE__CALL_ACTIVE_OBJECT_WITHOUT_CHECKING_OR_STOPPING:
				return getCallActiveObjectWithoutCheckingOrStopping() != null;
			case CSConfigPackage.SCRIPTS_TYPE__CHANGENOTIFICATION:
				return getChangenotification() != null;
			case CSConfigPackage.SCRIPTS_TYPE__CLEANUP:
				return getCleanup() != null;
			case CSConfigPackage.SCRIPTS_TYPE__COMMENTCODE:
				return getCommentcode() != null;
			case CSConfigPackage.SCRIPTS_TYPE__CONNECT:
				return getConnect() != null;
			case CSConfigPackage.SCRIPTS_TYPE__CONNECT_AND_DONT_CLOSE_MEMBER_VARIABLE:
				return getConnectAndDontCloseMemberVariable() != null;
			case CSConfigPackage.SCRIPTS_TYPE__CONSTNAMES:
				return getConstnames() != null;
			case CSConfigPackage.SCRIPTS_TYPE__CONSTTDESCPTR:
				return getConsttdescptr() != null;
			case CSConfigPackage.SCRIPTS_TYPE__CONTROLORNULL:
				return getControlornull() != null;
			case CSConfigPackage.SCRIPTS_TYPE__CTLTARGETTYPE:
				return getCtltargettype() != null;
			case CSConfigPackage.SCRIPTS_TYPE__DEBUGROM:
				return getDebugrom() != null;
			case CSConfigPackage.SCRIPTS_TYPE__DECLARENAME:
				return getDeclarename() != null;
			case CSConfigPackage.SCRIPTS_TYPE__DELETE_MEMBER_VARIABLE:
				return getDeleteMemberVariable() != null;
			case CSConfigPackage.SCRIPTS_TYPE__DESTRUCTOR:
				return getDestructor() != null;
			case CSConfigPackage.SCRIPTS_TYPE__DOUBLE_SEMI_COLON:
				return getDoubleSemiColon() != null;
			case CSConfigPackage.SCRIPTS_TYPE__DRIVELETTERS:
				return getDriveletters() != null;
			case CSConfigPackage.SCRIPTS_TYPE__EIKBUTTONS:
				return getEikbuttons() != null;
			case CSConfigPackage.SCRIPTS_TYPE__EIKONENVSTATIC:
				return getEikonenvstatic() != null;
			case CSConfigPackage.SCRIPTS_TYPE__ENUMMEMBERS:
				return getEnummembers() != null;
			case CSConfigPackage.SCRIPTS_TYPE__ENUMNAMES:
				return getEnumnames() != null;
			case CSConfigPackage.SCRIPTS_TYPE__EXPORTINLINE:
				return getExportinline() != null;
			case CSConfigPackage.SCRIPTS_TYPE__EXPORTPUREVIRTUAL:
				return getExportpurevirtual() != null;
			case CSConfigPackage.SCRIPTS_TYPE__EXTERNALDRIVELETTERS:
				return getExternaldriveletters() != null;
			case CSConfigPackage.SCRIPTS_TYPE__FOFF:
				return getFoff() != null;
			case CSConfigPackage.SCRIPTS_TYPE__FORBIDDENWORDS:
				return getForbiddenwords() != null;
			case CSConfigPackage.SCRIPTS_TYPE__FORGOTTOPUTPTRONCLEANUPSTACK:
				return getForgottoputptroncleanupstack() != null;
			case CSConfigPackage.SCRIPTS_TYPE__FRIEND:
				return getFriend() != null;
			case CSConfigPackage.SCRIPTS_TYPE__GOTO:
				return getGoto() != null;
			case CSConfigPackage.SCRIPTS_TYPE__IFASSIGNMENTS:
				return getIfassignments() != null;
			case CSConfigPackage.SCRIPTS_TYPE__IFPREPROCESSOR:
				return getIfpreprocessor() != null;
			case CSConfigPackage.SCRIPTS_TYPE__INHERITANCEORDER:
				return getInheritanceorder() != null;
			case CSConfigPackage.SCRIPTS_TYPE__INTLEAVES:
				return getIntleaves() != null;
			case CSConfigPackage.SCRIPTS_TYPE__JMP:
				return getJmp() != null;
			case CSConfigPackage.SCRIPTS_TYPE__LEAVE:
				return getLeave() != null;
			case CSConfigPackage.SCRIPTS_TYPE__LEAVE_NO_ERROR:
				return getLeaveNoError() != null;
			case CSConfigPackage.SCRIPTS_TYPE__LEAVINGOPERATORS:
				return getLeavingoperators() != null;
			case CSConfigPackage.SCRIPTS_TYPE__LFUNCTION_CANT_LEAVE:
				return getLFunctionCantLeave() != null;
			case CSConfigPackage.SCRIPTS_TYPE__LONGLINES:
				return getLonglines() != null;
			case CSConfigPackage.SCRIPTS_TYPE__MAGICNUMBERS:
				return getMagicnumbers() != null;
			case CSConfigPackage.SCRIPTS_TYPE__MCLASSDESTRUCTOR:
				return getMclassdestructor() != null;
			case CSConfigPackage.SCRIPTS_TYPE__MEMBERLC:
				return getMemberlc() != null;
			case CSConfigPackage.SCRIPTS_TYPE__MEMBERVARIABLECALLLD:
				return getMembervariablecallld() != null;
			case CSConfigPackage.SCRIPTS_TYPE__MISSINGCANCEL:
				return getMissingcancel() != null;
			case CSConfigPackage.SCRIPTS_TYPE__MISSINGCCLASS:
				return getMissingcclass() != null;
			case CSConfigPackage.SCRIPTS_TYPE__MMPSOURCEPATH:
				return getMmpsourcepath() != null;
			case CSConfigPackage.SCRIPTS_TYPE__MULTILANGRSC:
				return getMultilangrsc() != null;
			case CSConfigPackage.SCRIPTS_TYPE__MULTIPLEDECLARATIONS:
				return getMultipledeclarations() != null;
			case CSConfigPackage.SCRIPTS_TYPE__MULTIPLEINHERITANCE:
				return getMultipleinheritance() != null;
			case CSConfigPackage.SCRIPTS_TYPE__MYDOCS:
				return getMydocs() != null;
			case CSConfigPackage.SCRIPTS_TYPE__NAMESPACE:
				return getNamespace() != null;
			case CSConfigPackage.SCRIPTS_TYPE__NEWLREFERENCES:
				return getNewlreferences() != null;
			case CSConfigPackage.SCRIPTS_TYPE__NOLEAVETRAP:
				return getNoleavetrap() != null;
			case CSConfigPackage.SCRIPTS_TYPE__NONCONSTHBUFC:
				return getNonconsthbufc() != null;
			case CSConfigPackage.SCRIPTS_TYPE__NONCONSTTDESC:
				return getNonconsttdesc() != null;
			case CSConfigPackage.SCRIPTS_TYPE__NONLEAVENEW:
				return getNonleavenew() != null;
			case CSConfigPackage.SCRIPTS_TYPE__NONUNICODESKINS:
				return getNonunicodeskins() != null;
			case CSConfigPackage.SCRIPTS_TYPE__NULL:
				return getNull() != null;
			case CSConfigPackage.SCRIPTS_TYPE__OPEN:
				return getOpen() != null;
			case CSConfigPackage.SCRIPTS_TYPE__POINTERTOARRAYS:
				return getPointertoarrays() != null;
			case CSConfigPackage.SCRIPTS_TYPE__PRAGMADISABLE:
				return getPragmadisable() != null;
			case CSConfigPackage.SCRIPTS_TYPE__PRAGMAMESSAGE:
				return getPragmamessage() != null;
			case CSConfigPackage.SCRIPTS_TYPE__PRAGMAOTHER:
				return getPragmaother() != null;
			case CSConfigPackage.SCRIPTS_TYPE__PRIVATEINHERITANCE:
				return getPrivateinheritance() != null;
			case CSConfigPackage.SCRIPTS_TYPE__PUSHADDRVAR:
				return getPushaddrvar() != null;
			case CSConfigPackage.SCRIPTS_TYPE__PUSHMEMBER:
				return getPushmember() != null;
			case CSConfigPackage.SCRIPTS_TYPE__READRESOURCE:
				return getReadresource() != null;
			case CSConfigPackage.SCRIPTS_TYPE__RESOURCENOTONCLEANUPSTACK:
				return getResourcenotoncleanupstack() != null;
			case CSConfigPackage.SCRIPTS_TYPE__RESOURCESONHEAP:
				return getResourcesonheap() != null;
			case CSConfigPackage.SCRIPTS_TYPE__RETURNDESCRIPTOROUTOFSCOPE:
				return getReturndescriptoroutofscope() != null;
			case CSConfigPackage.SCRIPTS_TYPE__RFS:
				return getRfs() != null;
			case CSConfigPackage.SCRIPTS_TYPE__RSSNAMES:
				return getRssnames() != null;
			case CSConfigPackage.SCRIPTS_TYPE__STRINGLITERALS:
				return getStringliterals() != null;
			case CSConfigPackage.SCRIPTS_TYPE__STRINGSINRESOURCEFILES:
				return getStringsinresourcefiles() != null;
			case CSConfigPackage.SCRIPTS_TYPE__STRUCT:
				return getStruct() != null;
			case CSConfigPackage.SCRIPTS_TYPE__TCCLASSES:
				return getTcclasses() != null;
			case CSConfigPackage.SCRIPTS_TYPE__TCLASSDESTRUCTOR:
				return getTclassdestructor() != null;
			case CSConfigPackage.SCRIPTS_TYPE__TODOCOMMENTS:
				return getTodocomments() != null;
			case CSConfigPackage.SCRIPTS_TYPE__TRAPCLEANUP:
				return getTrapcleanup() != null;
			case CSConfigPackage.SCRIPTS_TYPE__TRAPELEAVE:
				return getTrapeleave() != null;
			case CSConfigPackage.SCRIPTS_TYPE__TRAPRUNL:
				return getTraprunl() != null;
			case CSConfigPackage.SCRIPTS_TYPE__TRSPASSING:
				return getTrspassing() != null;
			case CSConfigPackage.SCRIPTS_TYPE__UIDS:
				return getUids() != null;
			case CSConfigPackage.SCRIPTS_TYPE__UNCOMPRESSEDAIF:
				return getUncompressedaif() != null;
			case CSConfigPackage.SCRIPTS_TYPE__UNCOMPRESSEDBMP:
				return getUncompressedbmp() != null;
			case CSConfigPackage.SCRIPTS_TYPE__UNICODESOURCE:
				return getUnicodesource() != null;
			case CSConfigPackage.SCRIPTS_TYPE__USERAFTER:
				return getUserafter() != null;
			case CSConfigPackage.SCRIPTS_TYPE__USERFREE:
				return getUserfree() != null;
			case CSConfigPackage.SCRIPTS_TYPE__USER_WAIT_FOR_REQUEST:
				return getUserWaitForRequest() != null;
			case CSConfigPackage.SCRIPTS_TYPE__VARIABLENAMES:
				return getVariablenames() != null;
			case CSConfigPackage.SCRIPTS_TYPE__VOIDPARAMETER:
				return getVoidparameter() != null;
			case CSConfigPackage.SCRIPTS_TYPE__WORRYINGCOMMENTS:
				return getWorryingcomments() != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (mixed: ");
		result.append(mixed);
		result.append(')');
		return result.toString();
	}

} //ScriptsTypeImpl