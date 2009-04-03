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

package com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.util;

import com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.*;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage
 * @generated
 */
public class CSConfigSwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static CSConfigPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSConfigSwitch() {
		if (modelPackage == null) {
			modelPackage = CSConfigPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case CSConfigPackage.ACCESS_ARRAY_ELEMENT_WITHOUT_CHECK2_TYPE: {
				AccessArrayElementWithoutCheck2Type accessArrayElementWithoutCheck2Type = (AccessArrayElementWithoutCheck2Type)theEObject;
				T result = caseAccessArrayElementWithoutCheck2Type(accessArrayElementWithoutCheck2Type);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.ACCESS_ARRAY_ELEMENT_WITHOUT_CHECK_TYPE: {
				AccessArrayElementWithoutCheckType accessArrayElementWithoutCheckType = (AccessArrayElementWithoutCheckType)theEObject;
				T result = caseAccessArrayElementWithoutCheckType(accessArrayElementWithoutCheckType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.ACTIVESTART_TYPE: {
				ActivestartType activestartType = (ActivestartType)theEObject;
				T result = caseActivestartType(activestartType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.ACTIVESTOP_TYPE: {
				ActivestopType activestopType = (ActivestopType)theEObject;
				T result = caseActivestopType(activestopType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.ARGUMENTS_TYPE: {
				ArgumentsType argumentsType = (ArgumentsType)theEObject;
				T result = caseArgumentsType(argumentsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.ARRAYPASSING_TYPE: {
				ArraypassingType arraypassingType = (ArraypassingType)theEObject;
				T result = caseArraypassingType(arraypassingType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.ARRAYPTRCLEANUP_TYPE: {
				ArrayptrcleanupType arrayptrcleanupType = (ArrayptrcleanupType)theEObject;
				T result = caseArrayptrcleanupType(arrayptrcleanupType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.ASSERTDEBUGINVARIANT_TYPE: {
				AssertdebuginvariantType assertdebuginvariantType = (AssertdebuginvariantType)theEObject;
				T result = caseAssertdebuginvariantType(assertdebuginvariantType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.BADDEFINES_TYPE: {
				BaddefinesType baddefinesType = (BaddefinesType)theEObject;
				T result = caseBaddefinesType(baddefinesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.BASECONSTRUCT_TYPE: {
				BaseconstructType baseconstructType = (BaseconstructType)theEObject;
				T result = caseBaseconstructType(baseconstructType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.CALL_ACTIVE_OBJECT_WITHOUT_CHECKING_OR_STOPPING_TYPE: {
				CallActiveObjectWithoutCheckingOrStoppingType callActiveObjectWithoutCheckingOrStoppingType = (CallActiveObjectWithoutCheckingOrStoppingType)theEObject;
				T result = caseCallActiveObjectWithoutCheckingOrStoppingType(callActiveObjectWithoutCheckingOrStoppingType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.CANPANIC_TYPE: {
				CanpanicType canpanicType = (CanpanicType)theEObject;
				T result = caseCanpanicType(canpanicType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.CATEGORIES_TYPE: {
				CategoriesType categoriesType = (CategoriesType)theEObject;
				T result = caseCategoriesType(categoriesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.CHANGENOTIFICATION_TYPE: {
				ChangenotificationType changenotificationType = (ChangenotificationType)theEObject;
				T result = caseChangenotificationType(changenotificationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.CLEANUP_TYPE: {
				CleanupType cleanupType = (CleanupType)theEObject;
				T result = caseCleanupType(cleanupType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.CODEREVIEW_TYPE: {
				CodereviewType codereviewType = (CodereviewType)theEObject;
				T result = caseCodereviewType(codereviewType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE: {
				CodescannerConfigType codescannerConfigType = (CodescannerConfigType)theEObject;
				T result = caseCodescannerConfigType(codescannerConfigType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.CODINGSTANDARDS_TYPE: {
				CodingstandardsType codingstandardsType = (CodingstandardsType)theEObject;
				T result = caseCodingstandardsType(codingstandardsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.COMMENTCODE_TYPE: {
				CommentcodeType commentcodeType = (CommentcodeType)theEObject;
				T result = caseCommentcodeType(commentcodeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.CONNECT_AND_DONT_CLOSE_MEMBER_VARIABLE_TYPE: {
				ConnectAndDontCloseMemberVariableType connectAndDontCloseMemberVariableType = (ConnectAndDontCloseMemberVariableType)theEObject;
				T result = caseConnectAndDontCloseMemberVariableType(connectAndDontCloseMemberVariableType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.CONNECT_TYPE: {
				ConnectType connectType = (ConnectType)theEObject;
				T result = caseConnectType(connectType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.CONSTNAMES_TYPE: {
				ConstnamesType constnamesType = (ConstnamesType)theEObject;
				T result = caseConstnamesType(constnamesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.CONSTTDESCPTR_TYPE: {
				ConsttdescptrType consttdescptrType = (ConsttdescptrType)theEObject;
				T result = caseConsttdescptrType(consttdescptrType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.CONTROLORNULL_TYPE: {
				ControlornullType controlornullType = (ControlornullType)theEObject;
				T result = caseControlornullType(controlornullType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.CREPOSITORY_TYPE: {
				CrepositoryType crepositoryType = (CrepositoryType)theEObject;
				T result = caseCrepositoryType(crepositoryType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.CTLTARGETTYPE_TYPE: {
				CtltargettypeType ctltargettypeType = (CtltargettypeType)theEObject;
				T result = caseCtltargettypeType(ctltargettypeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.CUSTOMIZABLEICONS_TYPE: {
				CustomizableiconsType customizableiconsType = (CustomizableiconsType)theEObject;
				T result = caseCustomizableiconsType(customizableiconsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.CUSTOMRULES_TYPE: {
				CustomrulesType customrulesType = (CustomrulesType)theEObject;
				T result = caseCustomrulesType(customrulesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.CUSTOMRULE_TYPE: {
				CustomruleType customruleType = (CustomruleType)theEObject;
				T result = caseCustomruleType(customruleType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.DEBUGROM_TYPE: {
				DebugromType debugromType = (DebugromType)theEObject;
				T result = caseDebugromType(debugromType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.DECLARENAME_TYPE: {
				DeclarenameType declarenameType = (DeclarenameType)theEObject;
				T result = caseDeclarenameType(declarenameType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.DELETE_MEMBER_VARIABLE_TYPE: {
				DeleteMemberVariableType deleteMemberVariableType = (DeleteMemberVariableType)theEObject;
				T result = caseDeleteMemberVariableType(deleteMemberVariableType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.DESTRUCTOR_TYPE: {
				DestructorType destructorType = (DestructorType)theEObject;
				T result = caseDestructorType(destructorType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.DOCUMENTATION_TYPE: {
				DocumentationType documentationType = (DocumentationType)theEObject;
				T result = caseDocumentationType(documentationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.DOCUMENT_ROOT: {
				DocumentRoot documentRoot = (DocumentRoot)theEObject;
				T result = caseDocumentRoot(documentRoot);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.DOUBLE_SEMI_COLON_TYPE: {
				DoubleSemiColonType doubleSemiColonType = (DoubleSemiColonType)theEObject;
				T result = caseDoubleSemiColonType(doubleSemiColonType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.DRIVELETTERS_TYPE: {
				DrivelettersType drivelettersType = (DrivelettersType)theEObject;
				T result = caseDrivelettersType(drivelettersType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.EIKBUTTONS_TYPE: {
				EikbuttonsType eikbuttonsType = (EikbuttonsType)theEObject;
				T result = caseEikbuttonsType(eikbuttonsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.EIKONENVSTATIC_TYPE: {
				EikonenvstaticType eikonenvstaticType = (EikonenvstaticType)theEObject;
				T result = caseEikonenvstaticType(eikonenvstaticType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.ENUMMEMBERS_TYPE: {
				EnummembersType enummembersType = (EnummembersType)theEObject;
				T result = caseEnummembersType(enummembersType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.ENUMNAMES_TYPE: {
				EnumnamesType enumnamesType = (EnumnamesType)theEObject;
				T result = caseEnumnamesType(enumnamesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.EXPORTINLINE_TYPE: {
				ExportinlineType exportinlineType = (ExportinlineType)theEObject;
				T result = caseExportinlineType(exportinlineType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.EXPORTPUREVIRTUAL_TYPE: {
				ExportpurevirtualType exportpurevirtualType = (ExportpurevirtualType)theEObject;
				T result = caseExportpurevirtualType(exportpurevirtualType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.EXTERNALDRIVELETTERS_TYPE: {
				ExternaldrivelettersType externaldrivelettersType = (ExternaldrivelettersType)theEObject;
				T result = caseExternaldrivelettersType(externaldrivelettersType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.FLAGS_TYPE: {
				FlagsType flagsType = (FlagsType)theEObject;
				T result = caseFlagsType(flagsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.FOFF_TYPE: {
				FoffType foffType = (FoffType)theEObject;
				T result = caseFoffType(foffType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.FORBIDDENWORDS_TYPE: {
				ForbiddenwordsType forbiddenwordsType = (ForbiddenwordsType)theEObject;
				T result = caseForbiddenwordsType(forbiddenwordsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.FORGOTTOPUTPTRONCLEANUPSTACK_TYPE: {
				ForgottoputptroncleanupstackType forgottoputptroncleanupstackType = (ForgottoputptroncleanupstackType)theEObject;
				T result = caseForgottoputptroncleanupstackType(forgottoputptroncleanupstackType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.FRIEND_TYPE: {
				FriendType friendType = (FriendType)theEObject;
				T result = caseFriendType(friendType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.FUNCTIONALITY_TYPE: {
				FunctionalityType functionalityType = (FunctionalityType)theEObject;
				T result = caseFunctionalityType(functionalityType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.GOTO_TYPE: {
				GotoType gotoType = (GotoType)theEObject;
				T result = caseGotoType(gotoType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.HIGH_TYPE: {
				HighType highType = (HighType)theEObject;
				T result = caseHighType(highType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.IFASSIGNMENTS_TYPE: {
				IfassignmentsType ifassignmentsType = (IfassignmentsType)theEObject;
				T result = caseIfassignmentsType(ifassignmentsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.IFPREPROCESSOR_TYPE: {
				IfpreprocessorType ifpreprocessorType = (IfpreprocessorType)theEObject;
				T result = caseIfpreprocessorType(ifpreprocessorType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.INHERITANCEORDER_TYPE: {
				InheritanceorderType inheritanceorderType = (InheritanceorderType)theEObject;
				T result = caseInheritanceorderType(inheritanceorderType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.INTLEAVES_TYPE: {
				IntleavesType intleavesType = (IntleavesType)theEObject;
				T result = caseIntleavesType(intleavesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.JMP_TYPE: {
				JmpType jmpType = (JmpType)theEObject;
				T result = caseJmpType(jmpType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.KEYWORD_TYPE: {
				KeywordType keywordType = (KeywordType)theEObject;
				T result = caseKeywordType(keywordType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.LEAVE_NO_ERROR_TYPE: {
				LeaveNoErrorType leaveNoErrorType = (LeaveNoErrorType)theEObject;
				T result = caseLeaveNoErrorType(leaveNoErrorType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.LEAVE_TYPE: {
				LeaveType leaveType = (LeaveType)theEObject;
				T result = caseLeaveType(leaveType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.LEAVINGOPERATORS_TYPE: {
				LeavingoperatorsType leavingoperatorsType = (LeavingoperatorsType)theEObject;
				T result = caseLeavingoperatorsType(leavingoperatorsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.LEGAL_TYPE: {
				LegalType legalType = (LegalType)theEObject;
				T result = caseLegalType(legalType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.LFUNCTION_CANT_LEAVE_TYPE: {
				LFunctionCantLeaveType lFunctionCantLeaveType = (LFunctionCantLeaveType)theEObject;
				T result = caseLFunctionCantLeaveType(lFunctionCantLeaveType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.LOCALISATION_TYPE: {
				LocalisationType localisationType = (LocalisationType)theEObject;
				T result = caseLocalisationType(localisationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.LONGLINES_TYPE: {
				LonglinesType longlinesType = (LonglinesType)theEObject;
				T result = caseLonglinesType(longlinesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.LOW_TYPE: {
				LowType lowType = (LowType)theEObject;
				T result = caseLowType(lowType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.MAGICNUMBERS_TYPE: {
				MagicnumbersType magicnumbersType = (MagicnumbersType)theEObject;
				T result = caseMagicnumbersType(magicnumbersType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.MCLASSDESTRUCTOR_TYPE: {
				MclassdestructorType mclassdestructorType = (MclassdestructorType)theEObject;
				T result = caseMclassdestructorType(mclassdestructorType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.MEDIUM_TYPE: {
				MediumType mediumType = (MediumType)theEObject;
				T result = caseMediumType(mediumType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.MEMBERLC_TYPE: {
				MemberlcType memberlcType = (MemberlcType)theEObject;
				T result = caseMemberlcType(memberlcType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.MEMBERVARIABLECALLLD_TYPE: {
				MembervariablecallldType membervariablecallldType = (MembervariablecallldType)theEObject;
				T result = caseMembervariablecallldType(membervariablecallldType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.MISSINGCANCEL_TYPE: {
				MissingcancelType missingcancelType = (MissingcancelType)theEObject;
				T result = caseMissingcancelType(missingcancelType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.MISSINGCCLASS_TYPE: {
				MissingcclassType missingcclassType = (MissingcclassType)theEObject;
				T result = caseMissingcclassType(missingcclassType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.MMPSOURCEPATH_TYPE: {
				MmpsourcepathType mmpsourcepathType = (MmpsourcepathType)theEObject;
				T result = caseMmpsourcepathType(mmpsourcepathType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.MULTILANGRSC_TYPE: {
				MultilangrscType multilangrscType = (MultilangrscType)theEObject;
				T result = caseMultilangrscType(multilangrscType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.MULTIPLEDECLARATIONS_TYPE: {
				MultipledeclarationsType multipledeclarationsType = (MultipledeclarationsType)theEObject;
				T result = caseMultipledeclarationsType(multipledeclarationsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.MULTIPLEINHERITANCE_TYPE: {
				MultipleinheritanceType multipleinheritanceType = (MultipleinheritanceType)theEObject;
				T result = caseMultipleinheritanceType(multipleinheritanceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.MYDOCS_TYPE: {
				MydocsType mydocsType = (MydocsType)theEObject;
				T result = caseMydocsType(mydocsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.NAMESPACE_TYPE: {
				NamespaceType namespaceType = (NamespaceType)theEObject;
				T result = caseNamespaceType(namespaceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.NEWLREFERENCES_TYPE: {
				NewlreferencesType newlreferencesType = (NewlreferencesType)theEObject;
				T result = caseNewlreferencesType(newlreferencesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.NOLEAVETRAP_TYPE: {
				NoleavetrapType noleavetrapType = (NoleavetrapType)theEObject;
				T result = caseNoleavetrapType(noleavetrapType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.NONCONSTHBUFC_TYPE: {
				NonconsthbufcType nonconsthbufcType = (NonconsthbufcType)theEObject;
				T result = caseNonconsthbufcType(nonconsthbufcType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.NONCONSTTDESC_TYPE: {
				NonconsttdescType nonconsttdescType = (NonconsttdescType)theEObject;
				T result = caseNonconsttdescType(nonconsttdescType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.NONLEAVENEW_TYPE: {
				NonleavenewType nonleavenewType = (NonleavenewType)theEObject;
				T result = caseNonleavenewType(nonleavenewType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.NONUNICODESKINS_TYPE: {
				NonunicodeskinsType nonunicodeskinsType = (NonunicodeskinsType)theEObject;
				T result = caseNonunicodeskinsType(nonunicodeskinsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.NULL_TYPE: {
				NullType nullType = (NullType)theEObject;
				T result = caseNullType(nullType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.OPEN_TYPE: {
				OpenType openType = (OpenType)theEObject;
				T result = caseOpenType(openType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.OTHER_TYPE: {
				OtherType otherType = (OtherType)theEObject;
				T result = caseOtherType(otherType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.PANIC_TYPE: {
				PanicType panicType = (PanicType)theEObject;
				T result = casePanicType(panicType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.PERFORMANCE_TYPE: {
				PerformanceType performanceType = (PerformanceType)theEObject;
				T result = casePerformanceType(performanceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.POINTERTOARRAYS_TYPE: {
				PointertoarraysType pointertoarraysType = (PointertoarraysType)theEObject;
				T result = casePointertoarraysType(pointertoarraysType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.PRAGMADISABLE_TYPE: {
				PragmadisableType pragmadisableType = (PragmadisableType)theEObject;
				T result = casePragmadisableType(pragmadisableType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.PRAGMAMESSAGE_TYPE: {
				PragmamessageType pragmamessageType = (PragmamessageType)theEObject;
				T result = casePragmamessageType(pragmamessageType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.PRAGMAOTHER_TYPE: {
				PragmaotherType pragmaotherType = (PragmaotherType)theEObject;
				T result = casePragmaotherType(pragmaotherType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.PRIVATEINHERITANCE_TYPE: {
				PrivateinheritanceType privateinheritanceType = (PrivateinheritanceType)theEObject;
				T result = casePrivateinheritanceType(privateinheritanceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.PUSHADDRVAR_TYPE: {
				PushaddrvarType pushaddrvarType = (PushaddrvarType)theEObject;
				T result = casePushaddrvarType(pushaddrvarType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.PUSHMEMBER_TYPE: {
				PushmemberType pushmemberType = (PushmemberType)theEObject;
				T result = casePushmemberType(pushmemberType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.READRESOURCE_TYPE: {
				ReadresourceType readresourceType = (ReadresourceType)theEObject;
				T result = caseReadresourceType(readresourceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.RESOURCENOTONCLEANUPSTACK_TYPE: {
				ResourcenotoncleanupstackType resourcenotoncleanupstackType = (ResourcenotoncleanupstackType)theEObject;
				T result = caseResourcenotoncleanupstackType(resourcenotoncleanupstackType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.RESOURCESONHEAP_TYPE: {
				ResourcesonheapType resourcesonheapType = (ResourcesonheapType)theEObject;
				T result = caseResourcesonheapType(resourcesonheapType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.RETURNDESCRIPTOROUTOFSCOPE_TYPE: {
				ReturndescriptoroutofscopeType returndescriptoroutofscopeType = (ReturndescriptoroutofscopeType)theEObject;
				T result = caseReturndescriptoroutofscopeType(returndescriptoroutofscopeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.RFS_TYPE: {
				RfsType rfsType = (RfsType)theEObject;
				T result = caseRfsType(rfsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.RSSNAMES_TYPE: {
				RssnamesType rssnamesType = (RssnamesType)theEObject;
				T result = caseRssnamesType(rssnamesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.SCRIPTS_TYPE: {
				ScriptsType scriptsType = (ScriptsType)theEObject;
				T result = caseScriptsType(scriptsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.SEVERITIES_TYPE: {
				SeveritiesType severitiesType = (SeveritiesType)theEObject;
				T result = caseSeveritiesType(severitiesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.SOURCES_TYPE: {
				SourcesType sourcesType = (SourcesType)theEObject;
				T result = caseSourcesType(sourcesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.STRINGLITERALS_TYPE: {
				StringliteralsType stringliteralsType = (StringliteralsType)theEObject;
				T result = caseStringliteralsType(stringliteralsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.STRINGSINRESOURCEFILES_TYPE: {
				StringsinresourcefilesType stringsinresourcefilesType = (StringsinresourcefilesType)theEObject;
				T result = caseStringsinresourcefilesType(stringsinresourcefilesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.STRUCT_TYPE: {
				StructType structType = (StructType)theEObject;
				T result = caseStructType(structType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.TCCLASSES_TYPE: {
				TcclassesType tcclassesType = (TcclassesType)theEObject;
				T result = caseTcclassesType(tcclassesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.TCLASSDESTRUCTOR_TYPE: {
				TclassdestructorType tclassdestructorType = (TclassdestructorType)theEObject;
				T result = caseTclassdestructorType(tclassdestructorType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.TODOCOMMENTS_TYPE: {
				TodocommentsType todocommentsType = (TodocommentsType)theEObject;
				T result = caseTodocommentsType(todocommentsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.TRAPCLEANUP_TYPE: {
				TrapcleanupType trapcleanupType = (TrapcleanupType)theEObject;
				T result = caseTrapcleanupType(trapcleanupType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.TRAPELEAVE_TYPE: {
				TrapeleaveType trapeleaveType = (TrapeleaveType)theEObject;
				T result = caseTrapeleaveType(trapeleaveType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.TRAPRUNL_TYPE: {
				TraprunlType traprunlType = (TraprunlType)theEObject;
				T result = caseTraprunlType(traprunlType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.TRSPASSING_TYPE: {
				TrspassingType trspassingType = (TrspassingType)theEObject;
				T result = caseTrspassingType(trspassingType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.UIDS_TYPE: {
				UidsType uidsType = (UidsType)theEObject;
				T result = caseUidsType(uidsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.UNCOMPRESSEDAIF_TYPE: {
				UncompressedaifType uncompressedaifType = (UncompressedaifType)theEObject;
				T result = caseUncompressedaifType(uncompressedaifType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.UNCOMPRESSEDBMP_TYPE: {
				UncompressedbmpType uncompressedbmpType = (UncompressedbmpType)theEObject;
				T result = caseUncompressedbmpType(uncompressedbmpType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.UNICODESOURCE_TYPE: {
				UnicodesourceType unicodesourceType = (UnicodesourceType)theEObject;
				T result = caseUnicodesourceType(unicodesourceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.USERAFTER_TYPE: {
				UserafterType userafterType = (UserafterType)theEObject;
				T result = caseUserafterType(userafterType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.USERFREE_TYPE: {
				UserfreeType userfreeType = (UserfreeType)theEObject;
				T result = caseUserfreeType(userfreeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.USER_WAIT_FOR_REQUEST_TYPE: {
				UserWaitForRequestType userWaitForRequestType = (UserWaitForRequestType)theEObject;
				T result = caseUserWaitForRequestType(userWaitForRequestType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.VARIABLENAMES_TYPE: {
				VariablenamesType variablenamesType = (VariablenamesType)theEObject;
				T result = caseVariablenamesType(variablenamesType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.VOIDPARAMETER_TYPE: {
				VoidparameterType voidparameterType = (VoidparameterType)theEObject;
				T result = caseVoidparameterType(voidparameterType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case CSConfigPackage.WORRYINGCOMMENTS_TYPE: {
				WorryingcommentsType worryingcommentsType = (WorryingcommentsType)theEObject;
				T result = caseWorryingcommentsType(worryingcommentsType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Access Array Element Without Check2 Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Access Array Element Without Check2 Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAccessArrayElementWithoutCheck2Type(AccessArrayElementWithoutCheck2Type object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Access Array Element Without Check Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Access Array Element Without Check Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAccessArrayElementWithoutCheckType(AccessArrayElementWithoutCheckType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Activestart Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Activestart Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseActivestartType(ActivestartType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Activestop Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Activestop Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseActivestopType(ActivestopType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Arguments Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Arguments Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArgumentsType(ArgumentsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Arraypassing Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Arraypassing Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArraypassingType(ArraypassingType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Arrayptrcleanup Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Arrayptrcleanup Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArrayptrcleanupType(ArrayptrcleanupType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Assertdebuginvariant Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Assertdebuginvariant Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAssertdebuginvariantType(AssertdebuginvariantType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Baddefines Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Baddefines Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBaddefinesType(BaddefinesType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Baseconstruct Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Baseconstruct Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBaseconstructType(BaseconstructType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Call Active Object Without Checking Or Stopping Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Call Active Object Without Checking Or Stopping Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCallActiveObjectWithoutCheckingOrStoppingType(CallActiveObjectWithoutCheckingOrStoppingType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Canpanic Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Canpanic Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCanpanicType(CanpanicType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Categories Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Categories Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCategoriesType(CategoriesType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Changenotification Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Changenotification Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChangenotificationType(ChangenotificationType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cleanup Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cleanup Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCleanupType(CleanupType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Codereview Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Codereview Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCodereviewType(CodereviewType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Codescanner Config Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Codescanner Config Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCodescannerConfigType(CodescannerConfigType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Codingstandards Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Codingstandards Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCodingstandardsType(CodingstandardsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Commentcode Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Commentcode Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCommentcodeType(CommentcodeType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Connect And Dont Close Member Variable Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Connect And Dont Close Member Variable Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConnectAndDontCloseMemberVariableType(ConnectAndDontCloseMemberVariableType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Connect Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Connect Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConnectType(ConnectType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Constnames Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Constnames Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConstnamesType(ConstnamesType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Consttdescptr Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Consttdescptr Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConsttdescptrType(ConsttdescptrType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Controlornull Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Controlornull Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseControlornullType(ControlornullType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Crepository Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Crepository Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCrepositoryType(CrepositoryType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ctltargettype Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ctltargettype Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCtltargettypeType(CtltargettypeType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Customizableicons Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Customizableicons Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCustomizableiconsType(CustomizableiconsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Customrules Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Customrules Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCustomrulesType(CustomrulesType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Customrule Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Customrule Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCustomruleType(CustomruleType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Debugrom Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Debugrom Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDebugromType(DebugromType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Declarename Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Declarename Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDeclarenameType(DeclarenameType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Delete Member Variable Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Delete Member Variable Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDeleteMemberVariableType(DeleteMemberVariableType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Destructor Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Destructor Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDestructorType(DestructorType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Documentation Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Documentation Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDocumentationType(DocumentationType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Document Root</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Document Root</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDocumentRoot(DocumentRoot object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Double Semi Colon Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Double Semi Colon Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDoubleSemiColonType(DoubleSemiColonType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Driveletters Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Driveletters Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDrivelettersType(DrivelettersType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Eikbuttons Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Eikbuttons Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEikbuttonsType(EikbuttonsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Eikonenvstatic Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Eikonenvstatic Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEikonenvstaticType(EikonenvstaticType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enummembers Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enummembers Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEnummembersType(EnummembersType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Enumnames Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Enumnames Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEnumnamesType(EnumnamesType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Exportinline Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Exportinline Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExportinlineType(ExportinlineType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Exportpurevirtual Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Exportpurevirtual Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExportpurevirtualType(ExportpurevirtualType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Externaldriveletters Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Externaldriveletters Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExternaldrivelettersType(ExternaldrivelettersType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Flags Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Flags Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFlagsType(FlagsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Foff Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Foff Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFoffType(FoffType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Forbiddenwords Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Forbiddenwords Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseForbiddenwordsType(ForbiddenwordsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Forgottoputptroncleanupstack Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Forgottoputptroncleanupstack Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseForgottoputptroncleanupstackType(ForgottoputptroncleanupstackType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Friend Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Friend Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFriendType(FriendType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Functionality Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Functionality Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFunctionalityType(FunctionalityType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Goto Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Goto Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGotoType(GotoType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>High Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>High Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHighType(HighType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ifassignments Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ifassignments Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIfassignmentsType(IfassignmentsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ifpreprocessor Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ifpreprocessor Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIfpreprocessorType(IfpreprocessorType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Inheritanceorder Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Inheritanceorder Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInheritanceorderType(InheritanceorderType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Intleaves Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Intleaves Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIntleavesType(IntleavesType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Jmp Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Jmp Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseJmpType(JmpType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Keyword Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Keyword Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseKeywordType(KeywordType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Leave No Error Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Leave No Error Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLeaveNoErrorType(LeaveNoErrorType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Leave Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Leave Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLeaveType(LeaveType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Leavingoperators Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Leavingoperators Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLeavingoperatorsType(LeavingoperatorsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Legal Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Legal Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLegalType(LegalType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>LFunction Cant Leave Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>LFunction Cant Leave Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLFunctionCantLeaveType(LFunctionCantLeaveType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Localisation Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Localisation Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLocalisationType(LocalisationType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Longlines Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Longlines Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLonglinesType(LonglinesType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Low Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Low Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLowType(LowType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Magicnumbers Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Magicnumbers Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMagicnumbersType(MagicnumbersType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mclassdestructor Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mclassdestructor Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMclassdestructorType(MclassdestructorType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Medium Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Medium Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMediumType(MediumType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Memberlc Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Memberlc Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMemberlcType(MemberlcType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Membervariablecallld Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Membervariablecallld Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMembervariablecallldType(MembervariablecallldType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Missingcancel Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Missingcancel Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMissingcancelType(MissingcancelType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Missingcclass Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Missingcclass Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMissingcclassType(MissingcclassType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mmpsourcepath Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mmpsourcepath Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMmpsourcepathType(MmpsourcepathType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Multilangrsc Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Multilangrsc Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMultilangrscType(MultilangrscType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Multipledeclarations Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Multipledeclarations Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMultipledeclarationsType(MultipledeclarationsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Multipleinheritance Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Multipleinheritance Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMultipleinheritanceType(MultipleinheritanceType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mydocs Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mydocs Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMydocsType(MydocsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Namespace Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Namespace Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNamespaceType(NamespaceType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Newlreferences Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Newlreferences Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNewlreferencesType(NewlreferencesType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Noleavetrap Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Noleavetrap Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNoleavetrapType(NoleavetrapType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Nonconsthbufc Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Nonconsthbufc Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNonconsthbufcType(NonconsthbufcType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Nonconsttdesc Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Nonconsttdesc Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNonconsttdescType(NonconsttdescType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Nonleavenew Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Nonleavenew Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNonleavenewType(NonleavenewType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Nonunicodeskins Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Nonunicodeskins Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNonunicodeskinsType(NonunicodeskinsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Null Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Null Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNullType(NullType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Open Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Open Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOpenType(OpenType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Other Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Other Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOtherType(OtherType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Panic Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Panic Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePanicType(PanicType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Performance Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Performance Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePerformanceType(PerformanceType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pointertoarrays Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pointertoarrays Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePointertoarraysType(PointertoarraysType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pragmadisable Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pragmadisable Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePragmadisableType(PragmadisableType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pragmamessage Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pragmamessage Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePragmamessageType(PragmamessageType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pragmaother Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pragmaother Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePragmaotherType(PragmaotherType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Privateinheritance Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Privateinheritance Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePrivateinheritanceType(PrivateinheritanceType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pushaddrvar Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pushaddrvar Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePushaddrvarType(PushaddrvarType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pushmember Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pushmember Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePushmemberType(PushmemberType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Readresource Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Readresource Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReadresourceType(ReadresourceType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Resourcenotoncleanupstack Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Resourcenotoncleanupstack Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseResourcenotoncleanupstackType(ResourcenotoncleanupstackType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Resourcesonheap Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Resourcesonheap Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseResourcesonheapType(ResourcesonheapType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Returndescriptoroutofscope Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Returndescriptoroutofscope Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReturndescriptoroutofscopeType(ReturndescriptoroutofscopeType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rfs Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rfs Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRfsType(RfsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rssnames Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rssnames Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRssnamesType(RssnamesType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Scripts Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Scripts Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseScriptsType(ScriptsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Severities Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Severities Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSeveritiesType(SeveritiesType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Sources Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sources Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSourcesType(SourcesType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Stringliterals Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Stringliterals Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringliteralsType(StringliteralsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Stringsinresourcefiles Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Stringsinresourcefiles Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStringsinresourcefilesType(StringsinresourcefilesType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Struct Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Struct Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStructType(StructType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tcclasses Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tcclasses Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTcclassesType(TcclassesType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Tclassdestructor Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Tclassdestructor Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTclassdestructorType(TclassdestructorType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Todocomments Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Todocomments Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTodocommentsType(TodocommentsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Trapcleanup Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Trapcleanup Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTrapcleanupType(TrapcleanupType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Trapeleave Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Trapeleave Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTrapeleaveType(TrapeleaveType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Traprunl Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Traprunl Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTraprunlType(TraprunlType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Trspassing Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Trspassing Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTrspassingType(TrspassingType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Uids Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Uids Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUidsType(UidsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Uncompressedaif Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Uncompressedaif Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUncompressedaifType(UncompressedaifType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Uncompressedbmp Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Uncompressedbmp Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUncompressedbmpType(UncompressedbmpType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Unicodesource Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Unicodesource Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUnicodesourceType(UnicodesourceType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Userafter Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Userafter Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUserafterType(UserafterType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Userfree Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Userfree Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUserfreeType(UserfreeType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>User Wait For Request Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>User Wait For Request Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUserWaitForRequestType(UserWaitForRequestType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Variablenames Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Variablenames Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVariablenamesType(VariablenamesType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Voidparameter Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Voidparameter Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVoidparameterType(VoidparameterType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Worryingcomments Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Worryingcomments Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWorryingcommentsType(WorryingcommentsType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object) {
		return null;
	}

} //CSConfigSwitch
