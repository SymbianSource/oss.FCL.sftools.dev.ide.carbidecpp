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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CSConfigPackage
 * @generated
 */
public class CSConfigAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static CSConfigPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CSConfigAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = CSConfigPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CSConfigSwitch<Adapter> modelSwitch =
		new CSConfigSwitch<Adapter>() {
			@Override
			public Adapter caseAccessArrayElementWithoutCheck2Type(AccessArrayElementWithoutCheck2Type object) {
				return createAccessArrayElementWithoutCheck2TypeAdapter();
			}
			@Override
			public Adapter caseAccessArrayElementWithoutCheckType(AccessArrayElementWithoutCheckType object) {
				return createAccessArrayElementWithoutCheckTypeAdapter();
			}
			@Override
			public Adapter caseActivestartType(ActivestartType object) {
				return createActivestartTypeAdapter();
			}
			@Override
			public Adapter caseActivestopType(ActivestopType object) {
				return createActivestopTypeAdapter();
			}
			@Override
			public Adapter caseArgumentsType(ArgumentsType object) {
				return createArgumentsTypeAdapter();
			}
			@Override
			public Adapter caseArraypassingType(ArraypassingType object) {
				return createArraypassingTypeAdapter();
			}
			@Override
			public Adapter caseArrayptrcleanupType(ArrayptrcleanupType object) {
				return createArrayptrcleanupTypeAdapter();
			}
			@Override
			public Adapter caseAssertdebuginvariantType(AssertdebuginvariantType object) {
				return createAssertdebuginvariantTypeAdapter();
			}
			@Override
			public Adapter caseBaddefinesType(BaddefinesType object) {
				return createBaddefinesTypeAdapter();
			}
			@Override
			public Adapter caseBaseconstructType(BaseconstructType object) {
				return createBaseconstructTypeAdapter();
			}
			@Override
			public Adapter caseCallActiveObjectWithoutCheckingOrStoppingType(CallActiveObjectWithoutCheckingOrStoppingType object) {
				return createCallActiveObjectWithoutCheckingOrStoppingTypeAdapter();
			}
			@Override
			public Adapter caseCanpanicType(CanpanicType object) {
				return createCanpanicTypeAdapter();
			}
			@Override
			public Adapter caseCategoriesType(CategoriesType object) {
				return createCategoriesTypeAdapter();
			}
			@Override
			public Adapter caseChangenotificationType(ChangenotificationType object) {
				return createChangenotificationTypeAdapter();
			}
			@Override
			public Adapter caseCleanupType(CleanupType object) {
				return createCleanupTypeAdapter();
			}
			@Override
			public Adapter caseCodereviewType(CodereviewType object) {
				return createCodereviewTypeAdapter();
			}
			@Override
			public Adapter caseCodescannerConfigType(CodescannerConfigType object) {
				return createCodescannerConfigTypeAdapter();
			}
			@Override
			public Adapter caseCodingstandardsType(CodingstandardsType object) {
				return createCodingstandardsTypeAdapter();
			}
			@Override
			public Adapter caseCommentcodeType(CommentcodeType object) {
				return createCommentcodeTypeAdapter();
			}
			@Override
			public Adapter caseConnectAndDontCloseMemberVariableType(ConnectAndDontCloseMemberVariableType object) {
				return createConnectAndDontCloseMemberVariableTypeAdapter();
			}
			@Override
			public Adapter caseConnectType(ConnectType object) {
				return createConnectTypeAdapter();
			}
			@Override
			public Adapter caseConstnamesType(ConstnamesType object) {
				return createConstnamesTypeAdapter();
			}
			@Override
			public Adapter caseConsttdescptrType(ConsttdescptrType object) {
				return createConsttdescptrTypeAdapter();
			}
			@Override
			public Adapter caseControlornullType(ControlornullType object) {
				return createControlornullTypeAdapter();
			}
			@Override
			public Adapter caseCrepositoryType(CrepositoryType object) {
				return createCrepositoryTypeAdapter();
			}
			@Override
			public Adapter caseCtltargettypeType(CtltargettypeType object) {
				return createCtltargettypeTypeAdapter();
			}
			@Override
			public Adapter caseCustomizableiconsType(CustomizableiconsType object) {
				return createCustomizableiconsTypeAdapter();
			}
			@Override
			public Adapter caseCustomrulesType(CustomrulesType object) {
				return createCustomrulesTypeAdapter();
			}
			@Override
			public Adapter caseCustomruleType(CustomruleType object) {
				return createCustomruleTypeAdapter();
			}
			@Override
			public Adapter caseDebugromType(DebugromType object) {
				return createDebugromTypeAdapter();
			}
			@Override
			public Adapter caseDeclarenameType(DeclarenameType object) {
				return createDeclarenameTypeAdapter();
			}
			@Override
			public Adapter caseDeleteMemberVariableType(DeleteMemberVariableType object) {
				return createDeleteMemberVariableTypeAdapter();
			}
			@Override
			public Adapter caseDestructorType(DestructorType object) {
				return createDestructorTypeAdapter();
			}
			@Override
			public Adapter caseDocumentationType(DocumentationType object) {
				return createDocumentationTypeAdapter();
			}
			@Override
			public Adapter caseDocumentRoot(DocumentRoot object) {
				return createDocumentRootAdapter();
			}
			@Override
			public Adapter caseDoubleSemiColonType(DoubleSemiColonType object) {
				return createDoubleSemiColonTypeAdapter();
			}
			@Override
			public Adapter caseDrivelettersType(DrivelettersType object) {
				return createDrivelettersTypeAdapter();
			}
			@Override
			public Adapter caseEikbuttonsType(EikbuttonsType object) {
				return createEikbuttonsTypeAdapter();
			}
			@Override
			public Adapter caseEikonenvstaticType(EikonenvstaticType object) {
				return createEikonenvstaticTypeAdapter();
			}
			@Override
			public Adapter caseEnummembersType(EnummembersType object) {
				return createEnummembersTypeAdapter();
			}
			@Override
			public Adapter caseEnumnamesType(EnumnamesType object) {
				return createEnumnamesTypeAdapter();
			}
			@Override
			public Adapter caseExportinlineType(ExportinlineType object) {
				return createExportinlineTypeAdapter();
			}
			@Override
			public Adapter caseExportpurevirtualType(ExportpurevirtualType object) {
				return createExportpurevirtualTypeAdapter();
			}
			@Override
			public Adapter caseExternaldrivelettersType(ExternaldrivelettersType object) {
				return createExternaldrivelettersTypeAdapter();
			}
			@Override
			public Adapter caseFlagsType(FlagsType object) {
				return createFlagsTypeAdapter();
			}
			@Override
			public Adapter caseFoffType(FoffType object) {
				return createFoffTypeAdapter();
			}
			@Override
			public Adapter caseForbiddenwordsType(ForbiddenwordsType object) {
				return createForbiddenwordsTypeAdapter();
			}
			@Override
			public Adapter caseForgottoputptroncleanupstackType(ForgottoputptroncleanupstackType object) {
				return createForgottoputptroncleanupstackTypeAdapter();
			}
			@Override
			public Adapter caseFriendType(FriendType object) {
				return createFriendTypeAdapter();
			}
			@Override
			public Adapter caseFunctionalityType(FunctionalityType object) {
				return createFunctionalityTypeAdapter();
			}
			@Override
			public Adapter caseGotoType(GotoType object) {
				return createGotoTypeAdapter();
			}
			@Override
			public Adapter caseHighType(HighType object) {
				return createHighTypeAdapter();
			}
			@Override
			public Adapter caseIfassignmentsType(IfassignmentsType object) {
				return createIfassignmentsTypeAdapter();
			}
			@Override
			public Adapter caseIfpreprocessorType(IfpreprocessorType object) {
				return createIfpreprocessorTypeAdapter();
			}
			@Override
			public Adapter caseInheritanceorderType(InheritanceorderType object) {
				return createInheritanceorderTypeAdapter();
			}
			@Override
			public Adapter caseIntleavesType(IntleavesType object) {
				return createIntleavesTypeAdapter();
			}
			@Override
			public Adapter caseJmpType(JmpType object) {
				return createJmpTypeAdapter();
			}
			@Override
			public Adapter caseKeywordType(KeywordType object) {
				return createKeywordTypeAdapter();
			}
			@Override
			public Adapter caseLeaveNoErrorType(LeaveNoErrorType object) {
				return createLeaveNoErrorTypeAdapter();
			}
			@Override
			public Adapter caseLeaveType(LeaveType object) {
				return createLeaveTypeAdapter();
			}
			@Override
			public Adapter caseLeavingoperatorsType(LeavingoperatorsType object) {
				return createLeavingoperatorsTypeAdapter();
			}
			@Override
			public Adapter caseLegalType(LegalType object) {
				return createLegalTypeAdapter();
			}
			@Override
			public Adapter caseLFunctionCantLeaveType(LFunctionCantLeaveType object) {
				return createLFunctionCantLeaveTypeAdapter();
			}
			@Override
			public Adapter caseLocalisationType(LocalisationType object) {
				return createLocalisationTypeAdapter();
			}
			@Override
			public Adapter caseLonglinesType(LonglinesType object) {
				return createLonglinesTypeAdapter();
			}
			@Override
			public Adapter caseLowType(LowType object) {
				return createLowTypeAdapter();
			}
			@Override
			public Adapter caseMagicnumbersType(MagicnumbersType object) {
				return createMagicnumbersTypeAdapter();
			}
			@Override
			public Adapter caseMclassdestructorType(MclassdestructorType object) {
				return createMclassdestructorTypeAdapter();
			}
			@Override
			public Adapter caseMediumType(MediumType object) {
				return createMediumTypeAdapter();
			}
			@Override
			public Adapter caseMemberlcType(MemberlcType object) {
				return createMemberlcTypeAdapter();
			}
			@Override
			public Adapter caseMembervariablecallldType(MembervariablecallldType object) {
				return createMembervariablecallldTypeAdapter();
			}
			@Override
			public Adapter caseMissingcancelType(MissingcancelType object) {
				return createMissingcancelTypeAdapter();
			}
			@Override
			public Adapter caseMissingcclassType(MissingcclassType object) {
				return createMissingcclassTypeAdapter();
			}
			@Override
			public Adapter caseMmpsourcepathType(MmpsourcepathType object) {
				return createMmpsourcepathTypeAdapter();
			}
			@Override
			public Adapter caseMultilangrscType(MultilangrscType object) {
				return createMultilangrscTypeAdapter();
			}
			@Override
			public Adapter caseMultipledeclarationsType(MultipledeclarationsType object) {
				return createMultipledeclarationsTypeAdapter();
			}
			@Override
			public Adapter caseMultipleinheritanceType(MultipleinheritanceType object) {
				return createMultipleinheritanceTypeAdapter();
			}
			@Override
			public Adapter caseMydocsType(MydocsType object) {
				return createMydocsTypeAdapter();
			}
			@Override
			public Adapter caseNamespaceType(NamespaceType object) {
				return createNamespaceTypeAdapter();
			}
			@Override
			public Adapter caseNewlreferencesType(NewlreferencesType object) {
				return createNewlreferencesTypeAdapter();
			}
			@Override
			public Adapter caseNoleavetrapType(NoleavetrapType object) {
				return createNoleavetrapTypeAdapter();
			}
			@Override
			public Adapter caseNonconsthbufcType(NonconsthbufcType object) {
				return createNonconsthbufcTypeAdapter();
			}
			@Override
			public Adapter caseNonconsttdescType(NonconsttdescType object) {
				return createNonconsttdescTypeAdapter();
			}
			@Override
			public Adapter caseNonleavenewType(NonleavenewType object) {
				return createNonleavenewTypeAdapter();
			}
			@Override
			public Adapter caseNonunicodeskinsType(NonunicodeskinsType object) {
				return createNonunicodeskinsTypeAdapter();
			}
			@Override
			public Adapter caseNullType(NullType object) {
				return createNullTypeAdapter();
			}
			@Override
			public Adapter caseOpenType(OpenType object) {
				return createOpenTypeAdapter();
			}
			@Override
			public Adapter caseOtherType(OtherType object) {
				return createOtherTypeAdapter();
			}
			@Override
			public Adapter casePanicType(PanicType object) {
				return createPanicTypeAdapter();
			}
			@Override
			public Adapter casePerformanceType(PerformanceType object) {
				return createPerformanceTypeAdapter();
			}
			@Override
			public Adapter casePointertoarraysType(PointertoarraysType object) {
				return createPointertoarraysTypeAdapter();
			}
			@Override
			public Adapter casePragmadisableType(PragmadisableType object) {
				return createPragmadisableTypeAdapter();
			}
			@Override
			public Adapter casePragmamessageType(PragmamessageType object) {
				return createPragmamessageTypeAdapter();
			}
			@Override
			public Adapter casePragmaotherType(PragmaotherType object) {
				return createPragmaotherTypeAdapter();
			}
			@Override
			public Adapter casePrivateinheritanceType(PrivateinheritanceType object) {
				return createPrivateinheritanceTypeAdapter();
			}
			@Override
			public Adapter casePushaddrvarType(PushaddrvarType object) {
				return createPushaddrvarTypeAdapter();
			}
			@Override
			public Adapter casePushmemberType(PushmemberType object) {
				return createPushmemberTypeAdapter();
			}
			@Override
			public Adapter caseReadresourceType(ReadresourceType object) {
				return createReadresourceTypeAdapter();
			}
			@Override
			public Adapter caseResourcenotoncleanupstackType(ResourcenotoncleanupstackType object) {
				return createResourcenotoncleanupstackTypeAdapter();
			}
			@Override
			public Adapter caseResourcesonheapType(ResourcesonheapType object) {
				return createResourcesonheapTypeAdapter();
			}
			@Override
			public Adapter caseReturndescriptoroutofscopeType(ReturndescriptoroutofscopeType object) {
				return createReturndescriptoroutofscopeTypeAdapter();
			}
			@Override
			public Adapter caseRfsType(RfsType object) {
				return createRfsTypeAdapter();
			}
			@Override
			public Adapter caseRssnamesType(RssnamesType object) {
				return createRssnamesTypeAdapter();
			}
			@Override
			public Adapter caseScriptsType(ScriptsType object) {
				return createScriptsTypeAdapter();
			}
			@Override
			public Adapter caseSeveritiesType(SeveritiesType object) {
				return createSeveritiesTypeAdapter();
			}
			@Override
			public Adapter caseSourcesType(SourcesType object) {
				return createSourcesTypeAdapter();
			}
			@Override
			public Adapter caseStringliteralsType(StringliteralsType object) {
				return createStringliteralsTypeAdapter();
			}
			@Override
			public Adapter caseStringsinresourcefilesType(StringsinresourcefilesType object) {
				return createStringsinresourcefilesTypeAdapter();
			}
			@Override
			public Adapter caseStructType(StructType object) {
				return createStructTypeAdapter();
			}
			@Override
			public Adapter caseTcclassesType(TcclassesType object) {
				return createTcclassesTypeAdapter();
			}
			@Override
			public Adapter caseTclassdestructorType(TclassdestructorType object) {
				return createTclassdestructorTypeAdapter();
			}
			@Override
			public Adapter caseTodocommentsType(TodocommentsType object) {
				return createTodocommentsTypeAdapter();
			}
			@Override
			public Adapter caseTrapcleanupType(TrapcleanupType object) {
				return createTrapcleanupTypeAdapter();
			}
			@Override
			public Adapter caseTrapeleaveType(TrapeleaveType object) {
				return createTrapeleaveTypeAdapter();
			}
			@Override
			public Adapter caseTraprunlType(TraprunlType object) {
				return createTraprunlTypeAdapter();
			}
			@Override
			public Adapter caseTrspassingType(TrspassingType object) {
				return createTrspassingTypeAdapter();
			}
			@Override
			public Adapter caseUidsType(UidsType object) {
				return createUidsTypeAdapter();
			}
			@Override
			public Adapter caseUncompressedaifType(UncompressedaifType object) {
				return createUncompressedaifTypeAdapter();
			}
			@Override
			public Adapter caseUncompressedbmpType(UncompressedbmpType object) {
				return createUncompressedbmpTypeAdapter();
			}
			@Override
			public Adapter caseUnicodesourceType(UnicodesourceType object) {
				return createUnicodesourceTypeAdapter();
			}
			@Override
			public Adapter caseUserafterType(UserafterType object) {
				return createUserafterTypeAdapter();
			}
			@Override
			public Adapter caseUserfreeType(UserfreeType object) {
				return createUserfreeTypeAdapter();
			}
			@Override
			public Adapter caseUserWaitForRequestType(UserWaitForRequestType object) {
				return createUserWaitForRequestTypeAdapter();
			}
			@Override
			public Adapter caseVariablenamesType(VariablenamesType object) {
				return createVariablenamesTypeAdapter();
			}
			@Override
			public Adapter caseVoidparameterType(VoidparameterType object) {
				return createVoidparameterTypeAdapter();
			}
			@Override
			public Adapter caseWorryingcommentsType(WorryingcommentsType object) {
				return createWorryingcommentsTypeAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.AccessArrayElementWithoutCheck2Type <em>Access Array Element Without Check2 Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.AccessArrayElementWithoutCheck2Type
	 * @generated
	 */
	public Adapter createAccessArrayElementWithoutCheck2TypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.AccessArrayElementWithoutCheckType <em>Access Array Element Without Check Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.AccessArrayElementWithoutCheckType
	 * @generated
	 */
	public Adapter createAccessArrayElementWithoutCheckTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ActivestartType <em>Activestart Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ActivestartType
	 * @generated
	 */
	public Adapter createActivestartTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ActivestopType <em>Activestop Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ActivestopType
	 * @generated
	 */
	public Adapter createActivestopTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ArgumentsType <em>Arguments Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ArgumentsType
	 * @generated
	 */
	public Adapter createArgumentsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ArraypassingType <em>Arraypassing Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ArraypassingType
	 * @generated
	 */
	public Adapter createArraypassingTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ArrayptrcleanupType <em>Arrayptrcleanup Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ArrayptrcleanupType
	 * @generated
	 */
	public Adapter createArrayptrcleanupTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.AssertdebuginvariantType <em>Assertdebuginvariant Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.AssertdebuginvariantType
	 * @generated
	 */
	public Adapter createAssertdebuginvariantTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.BaddefinesType <em>Baddefines Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.BaddefinesType
	 * @generated
	 */
	public Adapter createBaddefinesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.BaseconstructType <em>Baseconstruct Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.BaseconstructType
	 * @generated
	 */
	public Adapter createBaseconstructTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CallActiveObjectWithoutCheckingOrStoppingType <em>Call Active Object Without Checking Or Stopping Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CallActiveObjectWithoutCheckingOrStoppingType
	 * @generated
	 */
	public Adapter createCallActiveObjectWithoutCheckingOrStoppingTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CanpanicType <em>Canpanic Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CanpanicType
	 * @generated
	 */
	public Adapter createCanpanicTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoriesType <em>Categories Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CategoriesType
	 * @generated
	 */
	public Adapter createCategoriesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ChangenotificationType <em>Changenotification Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ChangenotificationType
	 * @generated
	 */
	public Adapter createChangenotificationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CleanupType <em>Cleanup Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CleanupType
	 * @generated
	 */
	public Adapter createCleanupTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CodereviewType <em>Codereview Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CodereviewType
	 * @generated
	 */
	public Adapter createCodereviewTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CodescannerConfigType <em>Codescanner Config Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CodescannerConfigType
	 * @generated
	 */
	public Adapter createCodescannerConfigTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CodingstandardsType <em>Codingstandards Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CodingstandardsType
	 * @generated
	 */
	public Adapter createCodingstandardsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CommentcodeType <em>Commentcode Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CommentcodeType
	 * @generated
	 */
	public Adapter createCommentcodeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ConnectAndDontCloseMemberVariableType <em>Connect And Dont Close Member Variable Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ConnectAndDontCloseMemberVariableType
	 * @generated
	 */
	public Adapter createConnectAndDontCloseMemberVariableTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ConnectType <em>Connect Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ConnectType
	 * @generated
	 */
	public Adapter createConnectTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ConstnamesType <em>Constnames Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ConstnamesType
	 * @generated
	 */
	public Adapter createConstnamesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ConsttdescptrType <em>Consttdescptr Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ConsttdescptrType
	 * @generated
	 */
	public Adapter createConsttdescptrTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ControlornullType <em>Controlornull Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ControlornullType
	 * @generated
	 */
	public Adapter createControlornullTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CrepositoryType <em>Crepository Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CrepositoryType
	 * @generated
	 */
	public Adapter createCrepositoryTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CtltargettypeType <em>Ctltargettype Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CtltargettypeType
	 * @generated
	 */
	public Adapter createCtltargettypeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CustomizableiconsType <em>Customizableicons Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CustomizableiconsType
	 * @generated
	 */
	public Adapter createCustomizableiconsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CustomrulesType <em>Customrules Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CustomrulesType
	 * @generated
	 */
	public Adapter createCustomrulesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CustomruleType <em>Customrule Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.CustomruleType
	 * @generated
	 */
	public Adapter createCustomruleTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.DebugromType <em>Debugrom Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.DebugromType
	 * @generated
	 */
	public Adapter createDebugromTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.DeclarenameType <em>Declarename Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.DeclarenameType
	 * @generated
	 */
	public Adapter createDeclarenameTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.DeleteMemberVariableType <em>Delete Member Variable Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.DeleteMemberVariableType
	 * @generated
	 */
	public Adapter createDeleteMemberVariableTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.DestructorType <em>Destructor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.DestructorType
	 * @generated
	 */
	public Adapter createDestructorTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.DocumentationType <em>Documentation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.DocumentationType
	 * @generated
	 */
	public Adapter createDocumentationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.DocumentRoot
	 * @generated
	 */
	public Adapter createDocumentRootAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.DoubleSemiColonType <em>Double Semi Colon Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.DoubleSemiColonType
	 * @generated
	 */
	public Adapter createDoubleSemiColonTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.DrivelettersType <em>Driveletters Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.DrivelettersType
	 * @generated
	 */
	public Adapter createDrivelettersTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.EikbuttonsType <em>Eikbuttons Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.EikbuttonsType
	 * @generated
	 */
	public Adapter createEikbuttonsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.EikonenvstaticType <em>Eikonenvstatic Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.EikonenvstaticType
	 * @generated
	 */
	public Adapter createEikonenvstaticTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.EnummembersType <em>Enummembers Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.EnummembersType
	 * @generated
	 */
	public Adapter createEnummembersTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.EnumnamesType <em>Enumnames Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.EnumnamesType
	 * @generated
	 */
	public Adapter createEnumnamesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ExportinlineType <em>Exportinline Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ExportinlineType
	 * @generated
	 */
	public Adapter createExportinlineTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ExportpurevirtualType <em>Exportpurevirtual Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ExportpurevirtualType
	 * @generated
	 */
	public Adapter createExportpurevirtualTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ExternaldrivelettersType <em>Externaldriveletters Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ExternaldrivelettersType
	 * @generated
	 */
	public Adapter createExternaldrivelettersTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.FlagsType <em>Flags Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.FlagsType
	 * @generated
	 */
	public Adapter createFlagsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.FoffType <em>Foff Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.FoffType
	 * @generated
	 */
	public Adapter createFoffTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ForbiddenwordsType <em>Forbiddenwords Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ForbiddenwordsType
	 * @generated
	 */
	public Adapter createForbiddenwordsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ForgottoputptroncleanupstackType <em>Forgottoputptroncleanupstack Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ForgottoputptroncleanupstackType
	 * @generated
	 */
	public Adapter createForgottoputptroncleanupstackTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.FriendType <em>Friend Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.FriendType
	 * @generated
	 */
	public Adapter createFriendTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.FunctionalityType <em>Functionality Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.FunctionalityType
	 * @generated
	 */
	public Adapter createFunctionalityTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.GotoType <em>Goto Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.GotoType
	 * @generated
	 */
	public Adapter createGotoTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.HighType <em>High Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.HighType
	 * @generated
	 */
	public Adapter createHighTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.IfassignmentsType <em>Ifassignments Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.IfassignmentsType
	 * @generated
	 */
	public Adapter createIfassignmentsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.IfpreprocessorType <em>Ifpreprocessor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.IfpreprocessorType
	 * @generated
	 */
	public Adapter createIfpreprocessorTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.InheritanceorderType <em>Inheritanceorder Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.InheritanceorderType
	 * @generated
	 */
	public Adapter createInheritanceorderTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.IntleavesType <em>Intleaves Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.IntleavesType
	 * @generated
	 */
	public Adapter createIntleavesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.JmpType <em>Jmp Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.JmpType
	 * @generated
	 */
	public Adapter createJmpTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.KeywordType <em>Keyword Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.KeywordType
	 * @generated
	 */
	public Adapter createKeywordTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.LeaveNoErrorType <em>Leave No Error Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.LeaveNoErrorType
	 * @generated
	 */
	public Adapter createLeaveNoErrorTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.LeaveType <em>Leave Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.LeaveType
	 * @generated
	 */
	public Adapter createLeaveTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.LeavingoperatorsType <em>Leavingoperators Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.LeavingoperatorsType
	 * @generated
	 */
	public Adapter createLeavingoperatorsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.LegalType <em>Legal Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.LegalType
	 * @generated
	 */
	public Adapter createLegalTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.LFunctionCantLeaveType <em>LFunction Cant Leave Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.LFunctionCantLeaveType
	 * @generated
	 */
	public Adapter createLFunctionCantLeaveTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.LocalisationType <em>Localisation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.LocalisationType
	 * @generated
	 */
	public Adapter createLocalisationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.LonglinesType <em>Longlines Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.LonglinesType
	 * @generated
	 */
	public Adapter createLonglinesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.LowType <em>Low Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.LowType
	 * @generated
	 */
	public Adapter createLowTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.MagicnumbersType <em>Magicnumbers Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.MagicnumbersType
	 * @generated
	 */
	public Adapter createMagicnumbersTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.MclassdestructorType <em>Mclassdestructor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.MclassdestructorType
	 * @generated
	 */
	public Adapter createMclassdestructorTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.MediumType <em>Medium Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.MediumType
	 * @generated
	 */
	public Adapter createMediumTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.MemberlcType <em>Memberlc Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.MemberlcType
	 * @generated
	 */
	public Adapter createMemberlcTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.MembervariablecallldType <em>Membervariablecallld Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.MembervariablecallldType
	 * @generated
	 */
	public Adapter createMembervariablecallldTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.MissingcancelType <em>Missingcancel Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.MissingcancelType
	 * @generated
	 */
	public Adapter createMissingcancelTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.MissingcclassType <em>Missingcclass Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.MissingcclassType
	 * @generated
	 */
	public Adapter createMissingcclassTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.MmpsourcepathType <em>Mmpsourcepath Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.MmpsourcepathType
	 * @generated
	 */
	public Adapter createMmpsourcepathTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.MultilangrscType <em>Multilangrsc Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.MultilangrscType
	 * @generated
	 */
	public Adapter createMultilangrscTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.MultipledeclarationsType <em>Multipledeclarations Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.MultipledeclarationsType
	 * @generated
	 */
	public Adapter createMultipledeclarationsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.MultipleinheritanceType <em>Multipleinheritance Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.MultipleinheritanceType
	 * @generated
	 */
	public Adapter createMultipleinheritanceTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.MydocsType <em>Mydocs Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.MydocsType
	 * @generated
	 */
	public Adapter createMydocsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.NamespaceType <em>Namespace Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.NamespaceType
	 * @generated
	 */
	public Adapter createNamespaceTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.NewlreferencesType <em>Newlreferences Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.NewlreferencesType
	 * @generated
	 */
	public Adapter createNewlreferencesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.NoleavetrapType <em>Noleavetrap Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.NoleavetrapType
	 * @generated
	 */
	public Adapter createNoleavetrapTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.NonconsthbufcType <em>Nonconsthbufc Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.NonconsthbufcType
	 * @generated
	 */
	public Adapter createNonconsthbufcTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.NonconsttdescType <em>Nonconsttdesc Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.NonconsttdescType
	 * @generated
	 */
	public Adapter createNonconsttdescTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.NonleavenewType <em>Nonleavenew Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.NonleavenewType
	 * @generated
	 */
	public Adapter createNonleavenewTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.NonunicodeskinsType <em>Nonunicodeskins Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.NonunicodeskinsType
	 * @generated
	 */
	public Adapter createNonunicodeskinsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.NullType <em>Null Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.NullType
	 * @generated
	 */
	public Adapter createNullTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.OpenType <em>Open Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.OpenType
	 * @generated
	 */
	public Adapter createOpenTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.OtherType <em>Other Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.OtherType
	 * @generated
	 */
	public Adapter createOtherTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.PanicType <em>Panic Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.PanicType
	 * @generated
	 */
	public Adapter createPanicTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.PerformanceType <em>Performance Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.PerformanceType
	 * @generated
	 */
	public Adapter createPerformanceTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.PointertoarraysType <em>Pointertoarrays Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.PointertoarraysType
	 * @generated
	 */
	public Adapter createPointertoarraysTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.PragmadisableType <em>Pragmadisable Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.PragmadisableType
	 * @generated
	 */
	public Adapter createPragmadisableTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.PragmamessageType <em>Pragmamessage Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.PragmamessageType
	 * @generated
	 */
	public Adapter createPragmamessageTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.PragmaotherType <em>Pragmaother Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.PragmaotherType
	 * @generated
	 */
	public Adapter createPragmaotherTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.PrivateinheritanceType <em>Privateinheritance Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.PrivateinheritanceType
	 * @generated
	 */
	public Adapter createPrivateinheritanceTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.PushaddrvarType <em>Pushaddrvar Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.PushaddrvarType
	 * @generated
	 */
	public Adapter createPushaddrvarTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.PushmemberType <em>Pushmember Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.PushmemberType
	 * @generated
	 */
	public Adapter createPushmemberTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ReadresourceType <em>Readresource Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ReadresourceType
	 * @generated
	 */
	public Adapter createReadresourceTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ResourcenotoncleanupstackType <em>Resourcenotoncleanupstack Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ResourcenotoncleanupstackType
	 * @generated
	 */
	public Adapter createResourcenotoncleanupstackTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ResourcesonheapType <em>Resourcesonheap Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ResourcesonheapType
	 * @generated
	 */
	public Adapter createResourcesonheapTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ReturndescriptoroutofscopeType <em>Returndescriptoroutofscope Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ReturndescriptoroutofscopeType
	 * @generated
	 */
	public Adapter createReturndescriptoroutofscopeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.RfsType <em>Rfs Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.RfsType
	 * @generated
	 */
	public Adapter createRfsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.RssnamesType <em>Rssnames Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.RssnamesType
	 * @generated
	 */
	public Adapter createRssnamesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType <em>Scripts Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.ScriptsType
	 * @generated
	 */
	public Adapter createScriptsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.SeveritiesType <em>Severities Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.SeveritiesType
	 * @generated
	 */
	public Adapter createSeveritiesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.SourcesType <em>Sources Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.SourcesType
	 * @generated
	 */
	public Adapter createSourcesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.StringliteralsType <em>Stringliterals Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.StringliteralsType
	 * @generated
	 */
	public Adapter createStringliteralsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.StringsinresourcefilesType <em>Stringsinresourcefiles Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.StringsinresourcefilesType
	 * @generated
	 */
	public Adapter createStringsinresourcefilesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.StructType <em>Struct Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.StructType
	 * @generated
	 */
	public Adapter createStructTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.TcclassesType <em>Tcclasses Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.TcclassesType
	 * @generated
	 */
	public Adapter createTcclassesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.TclassdestructorType <em>Tclassdestructor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.TclassdestructorType
	 * @generated
	 */
	public Adapter createTclassdestructorTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.TodocommentsType <em>Todocomments Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.TodocommentsType
	 * @generated
	 */
	public Adapter createTodocommentsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.TrapcleanupType <em>Trapcleanup Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.TrapcleanupType
	 * @generated
	 */
	public Adapter createTrapcleanupTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.TrapeleaveType <em>Trapeleave Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.TrapeleaveType
	 * @generated
	 */
	public Adapter createTrapeleaveTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.TraprunlType <em>Traprunl Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.TraprunlType
	 * @generated
	 */
	public Adapter createTraprunlTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.TrspassingType <em>Trspassing Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.TrspassingType
	 * @generated
	 */
	public Adapter createTrspassingTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.UidsType <em>Uids Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.UidsType
	 * @generated
	 */
	public Adapter createUidsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.UncompressedaifType <em>Uncompressedaif Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.UncompressedaifType
	 * @generated
	 */
	public Adapter createUncompressedaifTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.UncompressedbmpType <em>Uncompressedbmp Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.UncompressedbmpType
	 * @generated
	 */
	public Adapter createUncompressedbmpTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.UnicodesourceType <em>Unicodesource Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.UnicodesourceType
	 * @generated
	 */
	public Adapter createUnicodesourceTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.UserafterType <em>Userafter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.UserafterType
	 * @generated
	 */
	public Adapter createUserafterTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.UserfreeType <em>Userfree Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.UserfreeType
	 * @generated
	 */
	public Adapter createUserfreeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.UserWaitForRequestType <em>User Wait For Request Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.UserWaitForRequestType
	 * @generated
	 */
	public Adapter createUserWaitForRequestTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.VariablenamesType <em>Variablenames Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.VariablenamesType
	 * @generated
	 */
	public Adapter createVariablenamesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.VoidparameterType <em>Voidparameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.VoidparameterType
	 * @generated
	 */
	public Adapter createVoidparameterTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.WorryingcommentsType <em>Worryingcomments Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.CSConfig.WorryingcommentsType
	 * @generated
	 */
	public Adapter createWorryingcommentsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //CSConfigAdapterFactory
