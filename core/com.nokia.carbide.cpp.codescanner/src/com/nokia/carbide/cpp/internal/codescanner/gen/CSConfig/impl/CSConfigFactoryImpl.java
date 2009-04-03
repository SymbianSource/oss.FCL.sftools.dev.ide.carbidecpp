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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CSConfigFactoryImpl extends EFactoryImpl implements CSConfigFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CSConfigFactory init() {
		try {
			CSConfigFactory theCSConfigFactory = (CSConfigFactory)EPackage.Registry.INSTANCE.getEFactory("platform:/resource/com.nokia.carbide.cpp.codescanner/schema/CSConfig.xsd"); 
			if (theCSConfigFactory != null) {
				return theCSConfigFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CSConfigFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSConfigFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case CSConfigPackage.ACCESS_ARRAY_ELEMENT_WITHOUT_CHECK2_TYPE: return createAccessArrayElementWithoutCheck2Type();
			case CSConfigPackage.ACCESS_ARRAY_ELEMENT_WITHOUT_CHECK_TYPE: return createAccessArrayElementWithoutCheckType();
			case CSConfigPackage.ACTIVESTART_TYPE: return createActivestartType();
			case CSConfigPackage.ACTIVESTOP_TYPE: return createActivestopType();
			case CSConfigPackage.ARGUMENTS_TYPE: return createArgumentsType();
			case CSConfigPackage.ARRAYPASSING_TYPE: return createArraypassingType();
			case CSConfigPackage.ARRAYPTRCLEANUP_TYPE: return createArrayptrcleanupType();
			case CSConfigPackage.ASSERTDEBUGINVARIANT_TYPE: return createAssertdebuginvariantType();
			case CSConfigPackage.BADDEFINES_TYPE: return createBaddefinesType();
			case CSConfigPackage.BASECONSTRUCT_TYPE: return createBaseconstructType();
			case CSConfigPackage.CALL_ACTIVE_OBJECT_WITHOUT_CHECKING_OR_STOPPING_TYPE: return createCallActiveObjectWithoutCheckingOrStoppingType();
			case CSConfigPackage.CANPANIC_TYPE: return createCanpanicType();
			case CSConfigPackage.CATEGORIES_TYPE: return createCategoriesType();
			case CSConfigPackage.CHANGENOTIFICATION_TYPE: return createChangenotificationType();
			case CSConfigPackage.CLEANUP_TYPE: return createCleanupType();
			case CSConfigPackage.CODEREVIEW_TYPE: return createCodereviewType();
			case CSConfigPackage.CODESCANNER_CONFIG_TYPE: return createCodescannerConfigType();
			case CSConfigPackage.CODINGSTANDARDS_TYPE: return createCodingstandardsType();
			case CSConfigPackage.COMMENTCODE_TYPE: return createCommentcodeType();
			case CSConfigPackage.CONNECT_AND_DONT_CLOSE_MEMBER_VARIABLE_TYPE: return createConnectAndDontCloseMemberVariableType();
			case CSConfigPackage.CONNECT_TYPE: return createConnectType();
			case CSConfigPackage.CONSTNAMES_TYPE: return createConstnamesType();
			case CSConfigPackage.CONSTTDESCPTR_TYPE: return createConsttdescptrType();
			case CSConfigPackage.CONTROLORNULL_TYPE: return createControlornullType();
			case CSConfigPackage.CREPOSITORY_TYPE: return createCrepositoryType();
			case CSConfigPackage.CTLTARGETTYPE_TYPE: return createCtltargettypeType();
			case CSConfigPackage.CUSTOMIZABLEICONS_TYPE: return createCustomizableiconsType();
			case CSConfigPackage.CUSTOMRULES_TYPE: return createCustomrulesType();
			case CSConfigPackage.CUSTOMRULE_TYPE: return createCustomruleType();
			case CSConfigPackage.DEBUGROM_TYPE: return createDebugromType();
			case CSConfigPackage.DECLARENAME_TYPE: return createDeclarenameType();
			case CSConfigPackage.DELETE_MEMBER_VARIABLE_TYPE: return createDeleteMemberVariableType();
			case CSConfigPackage.DESTRUCTOR_TYPE: return createDestructorType();
			case CSConfigPackage.DOCUMENTATION_TYPE: return createDocumentationType();
			case CSConfigPackage.DOCUMENT_ROOT: return createDocumentRoot();
			case CSConfigPackage.DOUBLE_SEMI_COLON_TYPE: return createDoubleSemiColonType();
			case CSConfigPackage.DRIVELETTERS_TYPE: return createDrivelettersType();
			case CSConfigPackage.EIKBUTTONS_TYPE: return createEikbuttonsType();
			case CSConfigPackage.EIKONENVSTATIC_TYPE: return createEikonenvstaticType();
			case CSConfigPackage.ENUMMEMBERS_TYPE: return createEnummembersType();
			case CSConfigPackage.ENUMNAMES_TYPE: return createEnumnamesType();
			case CSConfigPackage.EXPORTINLINE_TYPE: return createExportinlineType();
			case CSConfigPackage.EXPORTPUREVIRTUAL_TYPE: return createExportpurevirtualType();
			case CSConfigPackage.EXTERNALDRIVELETTERS_TYPE: return createExternaldrivelettersType();
			case CSConfigPackage.FLAGS_TYPE: return createFlagsType();
			case CSConfigPackage.FOFF_TYPE: return createFoffType();
			case CSConfigPackage.FORBIDDENWORDS_TYPE: return createForbiddenwordsType();
			case CSConfigPackage.FORGOTTOPUTPTRONCLEANUPSTACK_TYPE: return createForgottoputptroncleanupstackType();
			case CSConfigPackage.FRIEND_TYPE: return createFriendType();
			case CSConfigPackage.FUNCTIONALITY_TYPE: return createFunctionalityType();
			case CSConfigPackage.GOTO_TYPE: return createGotoType();
			case CSConfigPackage.HIGH_TYPE: return createHighType();
			case CSConfigPackage.IFASSIGNMENTS_TYPE: return createIfassignmentsType();
			case CSConfigPackage.IFPREPROCESSOR_TYPE: return createIfpreprocessorType();
			case CSConfigPackage.INHERITANCEORDER_TYPE: return createInheritanceorderType();
			case CSConfigPackage.INTLEAVES_TYPE: return createIntleavesType();
			case CSConfigPackage.JMP_TYPE: return createJmpType();
			case CSConfigPackage.KEYWORD_TYPE: return createKeywordType();
			case CSConfigPackage.LEAVE_NO_ERROR_TYPE: return createLeaveNoErrorType();
			case CSConfigPackage.LEAVE_TYPE: return createLeaveType();
			case CSConfigPackage.LEAVINGOPERATORS_TYPE: return createLeavingoperatorsType();
			case CSConfigPackage.LEGAL_TYPE: return createLegalType();
			case CSConfigPackage.LFUNCTION_CANT_LEAVE_TYPE: return createLFunctionCantLeaveType();
			case CSConfigPackage.LOCALISATION_TYPE: return createLocalisationType();
			case CSConfigPackage.LONGLINES_TYPE: return createLonglinesType();
			case CSConfigPackage.LOW_TYPE: return createLowType();
			case CSConfigPackage.MAGICNUMBERS_TYPE: return createMagicnumbersType();
			case CSConfigPackage.MCLASSDESTRUCTOR_TYPE: return createMclassdestructorType();
			case CSConfigPackage.MEDIUM_TYPE: return createMediumType();
			case CSConfigPackage.MEMBERLC_TYPE: return createMemberlcType();
			case CSConfigPackage.MEMBERVARIABLECALLLD_TYPE: return createMembervariablecallldType();
			case CSConfigPackage.MISSINGCANCEL_TYPE: return createMissingcancelType();
			case CSConfigPackage.MISSINGCCLASS_TYPE: return createMissingcclassType();
			case CSConfigPackage.MMPSOURCEPATH_TYPE: return createMmpsourcepathType();
			case CSConfigPackage.MULTILANGRSC_TYPE: return createMultilangrscType();
			case CSConfigPackage.MULTIPLEDECLARATIONS_TYPE: return createMultipledeclarationsType();
			case CSConfigPackage.MULTIPLEINHERITANCE_TYPE: return createMultipleinheritanceType();
			case CSConfigPackage.MYDOCS_TYPE: return createMydocsType();
			case CSConfigPackage.NAMESPACE_TYPE: return createNamespaceType();
			case CSConfigPackage.NEWLREFERENCES_TYPE: return createNewlreferencesType();
			case CSConfigPackage.NOLEAVETRAP_TYPE: return createNoleavetrapType();
			case CSConfigPackage.NONCONSTHBUFC_TYPE: return createNonconsthbufcType();
			case CSConfigPackage.NONCONSTTDESC_TYPE: return createNonconsttdescType();
			case CSConfigPackage.NONLEAVENEW_TYPE: return createNonleavenewType();
			case CSConfigPackage.NONUNICODESKINS_TYPE: return createNonunicodeskinsType();
			case CSConfigPackage.NULL_TYPE: return createNullType();
			case CSConfigPackage.OPEN_TYPE: return createOpenType();
			case CSConfigPackage.OTHER_TYPE: return createOtherType();
			case CSConfigPackage.PANIC_TYPE: return createPanicType();
			case CSConfigPackage.PERFORMANCE_TYPE: return createPerformanceType();
			case CSConfigPackage.POINTERTOARRAYS_TYPE: return createPointertoarraysType();
			case CSConfigPackage.PRAGMADISABLE_TYPE: return createPragmadisableType();
			case CSConfigPackage.PRAGMAMESSAGE_TYPE: return createPragmamessageType();
			case CSConfigPackage.PRAGMAOTHER_TYPE: return createPragmaotherType();
			case CSConfigPackage.PRIVATEINHERITANCE_TYPE: return createPrivateinheritanceType();
			case CSConfigPackage.PUSHADDRVAR_TYPE: return createPushaddrvarType();
			case CSConfigPackage.PUSHMEMBER_TYPE: return createPushmemberType();
			case CSConfigPackage.READRESOURCE_TYPE: return createReadresourceType();
			case CSConfigPackage.RESOURCENOTONCLEANUPSTACK_TYPE: return createResourcenotoncleanupstackType();
			case CSConfigPackage.RESOURCESONHEAP_TYPE: return createResourcesonheapType();
			case CSConfigPackage.RETURNDESCRIPTOROUTOFSCOPE_TYPE: return createReturndescriptoroutofscopeType();
			case CSConfigPackage.RFS_TYPE: return createRfsType();
			case CSConfigPackage.RSSNAMES_TYPE: return createRssnamesType();
			case CSConfigPackage.SCRIPTS_TYPE: return createScriptsType();
			case CSConfigPackage.SEVERITIES_TYPE: return createSeveritiesType();
			case CSConfigPackage.SOURCES_TYPE: return createSourcesType();
			case CSConfigPackage.STRINGLITERALS_TYPE: return createStringliteralsType();
			case CSConfigPackage.STRINGSINRESOURCEFILES_TYPE: return createStringsinresourcefilesType();
			case CSConfigPackage.STRUCT_TYPE: return createStructType();
			case CSConfigPackage.TCCLASSES_TYPE: return createTcclassesType();
			case CSConfigPackage.TCLASSDESTRUCTOR_TYPE: return createTclassdestructorType();
			case CSConfigPackage.TODOCOMMENTS_TYPE: return createTodocommentsType();
			case CSConfigPackage.TRAPCLEANUP_TYPE: return createTrapcleanupType();
			case CSConfigPackage.TRAPELEAVE_TYPE: return createTrapeleaveType();
			case CSConfigPackage.TRAPRUNL_TYPE: return createTraprunlType();
			case CSConfigPackage.TRSPASSING_TYPE: return createTrspassingType();
			case CSConfigPackage.UIDS_TYPE: return createUidsType();
			case CSConfigPackage.UNCOMPRESSEDAIF_TYPE: return createUncompressedaifType();
			case CSConfigPackage.UNCOMPRESSEDBMP_TYPE: return createUncompressedbmpType();
			case CSConfigPackage.UNICODESOURCE_TYPE: return createUnicodesourceType();
			case CSConfigPackage.USERAFTER_TYPE: return createUserafterType();
			case CSConfigPackage.USERFREE_TYPE: return createUserfreeType();
			case CSConfigPackage.USER_WAIT_FOR_REQUEST_TYPE: return createUserWaitForRequestType();
			case CSConfigPackage.VARIABLENAMES_TYPE: return createVariablenamesType();
			case CSConfigPackage.VOIDPARAMETER_TYPE: return createVoidparameterType();
			case CSConfigPackage.WORRYINGCOMMENTS_TYPE: return createWorryingcommentsType();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case CSConfigPackage.CATEGORY_TYPE:
				return createCategoryTypeFromString(eDataType, initialValue);
			case CSConfigPackage.SEVERITY_TYPE:
				return createSeverityTypeFromString(eDataType, initialValue);
			case CSConfigPackage.CATEGORY_TYPE_OBJECT:
				return createCategoryTypeObjectFromString(eDataType, initialValue);
			case CSConfigPackage.SEVERITY_TYPE_OBJECT:
				return createSeverityTypeObjectFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case CSConfigPackage.CATEGORY_TYPE:
				return convertCategoryTypeToString(eDataType, instanceValue);
			case CSConfigPackage.SEVERITY_TYPE:
				return convertSeverityTypeToString(eDataType, instanceValue);
			case CSConfigPackage.CATEGORY_TYPE_OBJECT:
				return convertCategoryTypeObjectToString(eDataType, instanceValue);
			case CSConfigPackage.SEVERITY_TYPE_OBJECT:
				return convertSeverityTypeObjectToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AccessArrayElementWithoutCheck2Type createAccessArrayElementWithoutCheck2Type() {
		AccessArrayElementWithoutCheck2TypeImpl accessArrayElementWithoutCheck2Type = new AccessArrayElementWithoutCheck2TypeImpl();
		return accessArrayElementWithoutCheck2Type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AccessArrayElementWithoutCheckType createAccessArrayElementWithoutCheckType() {
		AccessArrayElementWithoutCheckTypeImpl accessArrayElementWithoutCheckType = new AccessArrayElementWithoutCheckTypeImpl();
		return accessArrayElementWithoutCheckType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActivestartType createActivestartType() {
		ActivestartTypeImpl activestartType = new ActivestartTypeImpl();
		return activestartType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActivestopType createActivestopType() {
		ActivestopTypeImpl activestopType = new ActivestopTypeImpl();
		return activestopType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArgumentsType createArgumentsType() {
		ArgumentsTypeImpl argumentsType = new ArgumentsTypeImpl();
		return argumentsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArraypassingType createArraypassingType() {
		ArraypassingTypeImpl arraypassingType = new ArraypassingTypeImpl();
		return arraypassingType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArrayptrcleanupType createArrayptrcleanupType() {
		ArrayptrcleanupTypeImpl arrayptrcleanupType = new ArrayptrcleanupTypeImpl();
		return arrayptrcleanupType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AssertdebuginvariantType createAssertdebuginvariantType() {
		AssertdebuginvariantTypeImpl assertdebuginvariantType = new AssertdebuginvariantTypeImpl();
		return assertdebuginvariantType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BaddefinesType createBaddefinesType() {
		BaddefinesTypeImpl baddefinesType = new BaddefinesTypeImpl();
		return baddefinesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BaseconstructType createBaseconstructType() {
		BaseconstructTypeImpl baseconstructType = new BaseconstructTypeImpl();
		return baseconstructType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CallActiveObjectWithoutCheckingOrStoppingType createCallActiveObjectWithoutCheckingOrStoppingType() {
		CallActiveObjectWithoutCheckingOrStoppingTypeImpl callActiveObjectWithoutCheckingOrStoppingType = new CallActiveObjectWithoutCheckingOrStoppingTypeImpl();
		return callActiveObjectWithoutCheckingOrStoppingType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CanpanicType createCanpanicType() {
		CanpanicTypeImpl canpanicType = new CanpanicTypeImpl();
		return canpanicType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CategoriesType createCategoriesType() {
		CategoriesTypeImpl categoriesType = new CategoriesTypeImpl();
		return categoriesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChangenotificationType createChangenotificationType() {
		ChangenotificationTypeImpl changenotificationType = new ChangenotificationTypeImpl();
		return changenotificationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CleanupType createCleanupType() {
		CleanupTypeImpl cleanupType = new CleanupTypeImpl();
		return cleanupType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CodereviewType createCodereviewType() {
		CodereviewTypeImpl codereviewType = new CodereviewTypeImpl();
		return codereviewType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CodescannerConfigType createCodescannerConfigType() {
		CodescannerConfigTypeImpl codescannerConfigType = new CodescannerConfigTypeImpl();
		return codescannerConfigType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CodingstandardsType createCodingstandardsType() {
		CodingstandardsTypeImpl codingstandardsType = new CodingstandardsTypeImpl();
		return codingstandardsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CommentcodeType createCommentcodeType() {
		CommentcodeTypeImpl commentcodeType = new CommentcodeTypeImpl();
		return commentcodeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectAndDontCloseMemberVariableType createConnectAndDontCloseMemberVariableType() {
		ConnectAndDontCloseMemberVariableTypeImpl connectAndDontCloseMemberVariableType = new ConnectAndDontCloseMemberVariableTypeImpl();
		return connectAndDontCloseMemberVariableType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectType createConnectType() {
		ConnectTypeImpl connectType = new ConnectTypeImpl();
		return connectType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConstnamesType createConstnamesType() {
		ConstnamesTypeImpl constnamesType = new ConstnamesTypeImpl();
		return constnamesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConsttdescptrType createConsttdescptrType() {
		ConsttdescptrTypeImpl consttdescptrType = new ConsttdescptrTypeImpl();
		return consttdescptrType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ControlornullType createControlornullType() {
		ControlornullTypeImpl controlornullType = new ControlornullTypeImpl();
		return controlornullType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CrepositoryType createCrepositoryType() {
		CrepositoryTypeImpl crepositoryType = new CrepositoryTypeImpl();
		return crepositoryType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CtltargettypeType createCtltargettypeType() {
		CtltargettypeTypeImpl ctltargettypeType = new CtltargettypeTypeImpl();
		return ctltargettypeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CustomizableiconsType createCustomizableiconsType() {
		CustomizableiconsTypeImpl customizableiconsType = new CustomizableiconsTypeImpl();
		return customizableiconsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CustomrulesType createCustomrulesType() {
		CustomrulesTypeImpl customrulesType = new CustomrulesTypeImpl();
		return customrulesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CustomruleType createCustomruleType() {
		CustomruleTypeImpl customruleType = new CustomruleTypeImpl();
		return customruleType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DebugromType createDebugromType() {
		DebugromTypeImpl debugromType = new DebugromTypeImpl();
		return debugromType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeclarenameType createDeclarenameType() {
		DeclarenameTypeImpl declarenameType = new DeclarenameTypeImpl();
		return declarenameType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeleteMemberVariableType createDeleteMemberVariableType() {
		DeleteMemberVariableTypeImpl deleteMemberVariableType = new DeleteMemberVariableTypeImpl();
		return deleteMemberVariableType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DestructorType createDestructorType() {
		DestructorTypeImpl destructorType = new DestructorTypeImpl();
		return destructorType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentationType createDocumentationType() {
		DocumentationTypeImpl documentationType = new DocumentationTypeImpl();
		return documentationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DoubleSemiColonType createDoubleSemiColonType() {
		DoubleSemiColonTypeImpl doubleSemiColonType = new DoubleSemiColonTypeImpl();
		return doubleSemiColonType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DrivelettersType createDrivelettersType() {
		DrivelettersTypeImpl drivelettersType = new DrivelettersTypeImpl();
		return drivelettersType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EikbuttonsType createEikbuttonsType() {
		EikbuttonsTypeImpl eikbuttonsType = new EikbuttonsTypeImpl();
		return eikbuttonsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EikonenvstaticType createEikonenvstaticType() {
		EikonenvstaticTypeImpl eikonenvstaticType = new EikonenvstaticTypeImpl();
		return eikonenvstaticType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnummembersType createEnummembersType() {
		EnummembersTypeImpl enummembersType = new EnummembersTypeImpl();
		return enummembersType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumnamesType createEnumnamesType() {
		EnumnamesTypeImpl enumnamesType = new EnumnamesTypeImpl();
		return enumnamesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExportinlineType createExportinlineType() {
		ExportinlineTypeImpl exportinlineType = new ExportinlineTypeImpl();
		return exportinlineType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExportpurevirtualType createExportpurevirtualType() {
		ExportpurevirtualTypeImpl exportpurevirtualType = new ExportpurevirtualTypeImpl();
		return exportpurevirtualType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExternaldrivelettersType createExternaldrivelettersType() {
		ExternaldrivelettersTypeImpl externaldrivelettersType = new ExternaldrivelettersTypeImpl();
		return externaldrivelettersType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FlagsType createFlagsType() {
		FlagsTypeImpl flagsType = new FlagsTypeImpl();
		return flagsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FoffType createFoffType() {
		FoffTypeImpl foffType = new FoffTypeImpl();
		return foffType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ForbiddenwordsType createForbiddenwordsType() {
		ForbiddenwordsTypeImpl forbiddenwordsType = new ForbiddenwordsTypeImpl();
		return forbiddenwordsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ForgottoputptroncleanupstackType createForgottoputptroncleanupstackType() {
		ForgottoputptroncleanupstackTypeImpl forgottoputptroncleanupstackType = new ForgottoputptroncleanupstackTypeImpl();
		return forgottoputptroncleanupstackType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FriendType createFriendType() {
		FriendTypeImpl friendType = new FriendTypeImpl();
		return friendType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionalityType createFunctionalityType() {
		FunctionalityTypeImpl functionalityType = new FunctionalityTypeImpl();
		return functionalityType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GotoType createGotoType() {
		GotoTypeImpl gotoType = new GotoTypeImpl();
		return gotoType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HighType createHighType() {
		HighTypeImpl highType = new HighTypeImpl();
		return highType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IfassignmentsType createIfassignmentsType() {
		IfassignmentsTypeImpl ifassignmentsType = new IfassignmentsTypeImpl();
		return ifassignmentsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IfpreprocessorType createIfpreprocessorType() {
		IfpreprocessorTypeImpl ifpreprocessorType = new IfpreprocessorTypeImpl();
		return ifpreprocessorType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InheritanceorderType createInheritanceorderType() {
		InheritanceorderTypeImpl inheritanceorderType = new InheritanceorderTypeImpl();
		return inheritanceorderType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntleavesType createIntleavesType() {
		IntleavesTypeImpl intleavesType = new IntleavesTypeImpl();
		return intleavesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JmpType createJmpType() {
		JmpTypeImpl jmpType = new JmpTypeImpl();
		return jmpType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KeywordType createKeywordType() {
		KeywordTypeImpl keywordType = new KeywordTypeImpl();
		return keywordType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LeaveNoErrorType createLeaveNoErrorType() {
		LeaveNoErrorTypeImpl leaveNoErrorType = new LeaveNoErrorTypeImpl();
		return leaveNoErrorType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LeaveType createLeaveType() {
		LeaveTypeImpl leaveType = new LeaveTypeImpl();
		return leaveType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LeavingoperatorsType createLeavingoperatorsType() {
		LeavingoperatorsTypeImpl leavingoperatorsType = new LeavingoperatorsTypeImpl();
		return leavingoperatorsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LegalType createLegalType() {
		LegalTypeImpl legalType = new LegalTypeImpl();
		return legalType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LFunctionCantLeaveType createLFunctionCantLeaveType() {
		LFunctionCantLeaveTypeImpl lFunctionCantLeaveType = new LFunctionCantLeaveTypeImpl();
		return lFunctionCantLeaveType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalisationType createLocalisationType() {
		LocalisationTypeImpl localisationType = new LocalisationTypeImpl();
		return localisationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LonglinesType createLonglinesType() {
		LonglinesTypeImpl longlinesType = new LonglinesTypeImpl();
		return longlinesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LowType createLowType() {
		LowTypeImpl lowType = new LowTypeImpl();
		return lowType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MagicnumbersType createMagicnumbersType() {
		MagicnumbersTypeImpl magicnumbersType = new MagicnumbersTypeImpl();
		return magicnumbersType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MclassdestructorType createMclassdestructorType() {
		MclassdestructorTypeImpl mclassdestructorType = new MclassdestructorTypeImpl();
		return mclassdestructorType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MediumType createMediumType() {
		MediumTypeImpl mediumType = new MediumTypeImpl();
		return mediumType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MemberlcType createMemberlcType() {
		MemberlcTypeImpl memberlcType = new MemberlcTypeImpl();
		return memberlcType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MembervariablecallldType createMembervariablecallldType() {
		MembervariablecallldTypeImpl membervariablecallldType = new MembervariablecallldTypeImpl();
		return membervariablecallldType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MissingcancelType createMissingcancelType() {
		MissingcancelTypeImpl missingcancelType = new MissingcancelTypeImpl();
		return missingcancelType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MissingcclassType createMissingcclassType() {
		MissingcclassTypeImpl missingcclassType = new MissingcclassTypeImpl();
		return missingcclassType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmpsourcepathType createMmpsourcepathType() {
		MmpsourcepathTypeImpl mmpsourcepathType = new MmpsourcepathTypeImpl();
		return mmpsourcepathType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MultilangrscType createMultilangrscType() {
		MultilangrscTypeImpl multilangrscType = new MultilangrscTypeImpl();
		return multilangrscType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MultipledeclarationsType createMultipledeclarationsType() {
		MultipledeclarationsTypeImpl multipledeclarationsType = new MultipledeclarationsTypeImpl();
		return multipledeclarationsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MultipleinheritanceType createMultipleinheritanceType() {
		MultipleinheritanceTypeImpl multipleinheritanceType = new MultipleinheritanceTypeImpl();
		return multipleinheritanceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MydocsType createMydocsType() {
		MydocsTypeImpl mydocsType = new MydocsTypeImpl();
		return mydocsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NamespaceType createNamespaceType() {
		NamespaceTypeImpl namespaceType = new NamespaceTypeImpl();
		return namespaceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NewlreferencesType createNewlreferencesType() {
		NewlreferencesTypeImpl newlreferencesType = new NewlreferencesTypeImpl();
		return newlreferencesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NoleavetrapType createNoleavetrapType() {
		NoleavetrapTypeImpl noleavetrapType = new NoleavetrapTypeImpl();
		return noleavetrapType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonconsthbufcType createNonconsthbufcType() {
		NonconsthbufcTypeImpl nonconsthbufcType = new NonconsthbufcTypeImpl();
		return nonconsthbufcType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonconsttdescType createNonconsttdescType() {
		NonconsttdescTypeImpl nonconsttdescType = new NonconsttdescTypeImpl();
		return nonconsttdescType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonleavenewType createNonleavenewType() {
		NonleavenewTypeImpl nonleavenewType = new NonleavenewTypeImpl();
		return nonleavenewType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonunicodeskinsType createNonunicodeskinsType() {
		NonunicodeskinsTypeImpl nonunicodeskinsType = new NonunicodeskinsTypeImpl();
		return nonunicodeskinsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NullType createNullType() {
		NullTypeImpl nullType = new NullTypeImpl();
		return nullType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OpenType createOpenType() {
		OpenTypeImpl openType = new OpenTypeImpl();
		return openType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OtherType createOtherType() {
		OtherTypeImpl otherType = new OtherTypeImpl();
		return otherType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PanicType createPanicType() {
		PanicTypeImpl panicType = new PanicTypeImpl();
		return panicType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PerformanceType createPerformanceType() {
		PerformanceTypeImpl performanceType = new PerformanceTypeImpl();
		return performanceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PointertoarraysType createPointertoarraysType() {
		PointertoarraysTypeImpl pointertoarraysType = new PointertoarraysTypeImpl();
		return pointertoarraysType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PragmadisableType createPragmadisableType() {
		PragmadisableTypeImpl pragmadisableType = new PragmadisableTypeImpl();
		return pragmadisableType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PragmamessageType createPragmamessageType() {
		PragmamessageTypeImpl pragmamessageType = new PragmamessageTypeImpl();
		return pragmamessageType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PragmaotherType createPragmaotherType() {
		PragmaotherTypeImpl pragmaotherType = new PragmaotherTypeImpl();
		return pragmaotherType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PrivateinheritanceType createPrivateinheritanceType() {
		PrivateinheritanceTypeImpl privateinheritanceType = new PrivateinheritanceTypeImpl();
		return privateinheritanceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PushaddrvarType createPushaddrvarType() {
		PushaddrvarTypeImpl pushaddrvarType = new PushaddrvarTypeImpl();
		return pushaddrvarType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PushmemberType createPushmemberType() {
		PushmemberTypeImpl pushmemberType = new PushmemberTypeImpl();
		return pushmemberType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReadresourceType createReadresourceType() {
		ReadresourceTypeImpl readresourceType = new ReadresourceTypeImpl();
		return readresourceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourcenotoncleanupstackType createResourcenotoncleanupstackType() {
		ResourcenotoncleanupstackTypeImpl resourcenotoncleanupstackType = new ResourcenotoncleanupstackTypeImpl();
		return resourcenotoncleanupstackType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourcesonheapType createResourcesonheapType() {
		ResourcesonheapTypeImpl resourcesonheapType = new ResourcesonheapTypeImpl();
		return resourcesonheapType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReturndescriptoroutofscopeType createReturndescriptoroutofscopeType() {
		ReturndescriptoroutofscopeTypeImpl returndescriptoroutofscopeType = new ReturndescriptoroutofscopeTypeImpl();
		return returndescriptoroutofscopeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RfsType createRfsType() {
		RfsTypeImpl rfsType = new RfsTypeImpl();
		return rfsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RssnamesType createRssnamesType() {
		RssnamesTypeImpl rssnamesType = new RssnamesTypeImpl();
		return rssnamesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScriptsType createScriptsType() {
		ScriptsTypeImpl scriptsType = new ScriptsTypeImpl();
		return scriptsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SeveritiesType createSeveritiesType() {
		SeveritiesTypeImpl severitiesType = new SeveritiesTypeImpl();
		return severitiesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SourcesType createSourcesType() {
		SourcesTypeImpl sourcesType = new SourcesTypeImpl();
		return sourcesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringliteralsType createStringliteralsType() {
		StringliteralsTypeImpl stringliteralsType = new StringliteralsTypeImpl();
		return stringliteralsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringsinresourcefilesType createStringsinresourcefilesType() {
		StringsinresourcefilesTypeImpl stringsinresourcefilesType = new StringsinresourcefilesTypeImpl();
		return stringsinresourcefilesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StructType createStructType() {
		StructTypeImpl structType = new StructTypeImpl();
		return structType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TcclassesType createTcclassesType() {
		TcclassesTypeImpl tcclassesType = new TcclassesTypeImpl();
		return tcclassesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TclassdestructorType createTclassdestructorType() {
		TclassdestructorTypeImpl tclassdestructorType = new TclassdestructorTypeImpl();
		return tclassdestructorType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TodocommentsType createTodocommentsType() {
		TodocommentsTypeImpl todocommentsType = new TodocommentsTypeImpl();
		return todocommentsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TrapcleanupType createTrapcleanupType() {
		TrapcleanupTypeImpl trapcleanupType = new TrapcleanupTypeImpl();
		return trapcleanupType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TrapeleaveType createTrapeleaveType() {
		TrapeleaveTypeImpl trapeleaveType = new TrapeleaveTypeImpl();
		return trapeleaveType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TraprunlType createTraprunlType() {
		TraprunlTypeImpl traprunlType = new TraprunlTypeImpl();
		return traprunlType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TrspassingType createTrspassingType() {
		TrspassingTypeImpl trspassingType = new TrspassingTypeImpl();
		return trspassingType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UidsType createUidsType() {
		UidsTypeImpl uidsType = new UidsTypeImpl();
		return uidsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UncompressedaifType createUncompressedaifType() {
		UncompressedaifTypeImpl uncompressedaifType = new UncompressedaifTypeImpl();
		return uncompressedaifType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UncompressedbmpType createUncompressedbmpType() {
		UncompressedbmpTypeImpl uncompressedbmpType = new UncompressedbmpTypeImpl();
		return uncompressedbmpType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnicodesourceType createUnicodesourceType() {
		UnicodesourceTypeImpl unicodesourceType = new UnicodesourceTypeImpl();
		return unicodesourceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserafterType createUserafterType() {
		UserafterTypeImpl userafterType = new UserafterTypeImpl();
		return userafterType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserfreeType createUserfreeType() {
		UserfreeTypeImpl userfreeType = new UserfreeTypeImpl();
		return userfreeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserWaitForRequestType createUserWaitForRequestType() {
		UserWaitForRequestTypeImpl userWaitForRequestType = new UserWaitForRequestTypeImpl();
		return userWaitForRequestType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VariablenamesType createVariablenamesType() {
		VariablenamesTypeImpl variablenamesType = new VariablenamesTypeImpl();
		return variablenamesType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VoidparameterType createVoidparameterType() {
		VoidparameterTypeImpl voidparameterType = new VoidparameterTypeImpl();
		return voidparameterType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WorryingcommentsType createWorryingcommentsType() {
		WorryingcommentsTypeImpl worryingcommentsType = new WorryingcommentsTypeImpl();
		return worryingcommentsType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CategoryType createCategoryTypeFromString(EDataType eDataType, String initialValue) {
		CategoryType result = CategoryType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCategoryTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SeverityType createSeverityTypeFromString(EDataType eDataType, String initialValue) {
		SeverityType result = SeverityType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSeverityTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CategoryType createCategoryTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createCategoryTypeFromString(CSConfigPackage.eINSTANCE.getCategoryType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCategoryTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertCategoryTypeToString(CSConfigPackage.eINSTANCE.getCategoryType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SeverityType createSeverityTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createSeverityTypeFromString(CSConfigPackage.eINSTANCE.getSeverityType(), initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSeverityTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertSeverityTypeToString(CSConfigPackage.eINSTANCE.getSeverityType(), instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSConfigPackage getCSConfigPackage() {
		return (CSConfigPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CSConfigPackage getPackage() {
		return CSConfigPackage.eINSTANCE;
	}

} //CSConfigFactoryImpl
