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

package com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.util;

import com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPackage
 * @generated
 */
public class KbdataAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static KbdataPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KbdataAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = KbdataPackage.eINSTANCE;
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
	protected KbdataSwitch<Adapter> modelSwitch =
		new KbdataSwitch<Adapter>() {
			@Override
			public Adapter caseAbstractType(AbstractType object) {
				return createAbstractTypeAdapter();
			}
			@Override
			public Adapter caseAltType(AltType object) {
				return createAltTypeAdapter();
			}
			@Override
			public Adapter caseAny(Any object) {
				return createAnyAdapter();
			}
			@Override
			public Adapter caseApinameType(ApinameType object) {
				return createApinameTypeAdapter();
			}
			@Override
			public Adapter caseAreaType(AreaType object) {
				return createAreaTypeAdapter();
			}
			@Override
			public Adapter caseAudienceType(AudienceType object) {
				return createAudienceTypeAdapter();
			}
			@Override
			public Adapter caseAuthorType(AuthorType object) {
				return createAuthorTypeAdapter();
			}
			@Override
			public Adapter caseBodyType(BodyType object) {
				return createBodyTypeAdapter();
			}
			@Override
			public Adapter caseBooleanType(BooleanType object) {
				return createBooleanTypeAdapter();
			}
			@Override
			public Adapter caseBrandType(BrandType object) {
				return createBrandTypeAdapter();
			}
			@Override
			public Adapter caseBType(BType object) {
				return createBTypeAdapter();
			}
			@Override
			public Adapter caseCallType(CallType object) {
				return createCallTypeAdapter();
			}
			@Override
			public Adapter caseCategoryType(CategoryType object) {
				return createCategoryTypeAdapter();
			}
			@Override
			public Adapter caseCiteType(CiteType object) {
				return createCiteTypeAdapter();
			}
			@Override
			public Adapter caseClassType(ClassType object) {
				return createClassTypeAdapter();
			}
			@Override
			public Adapter caseCmdnameType(CmdnameType object) {
				return createCmdnameTypeAdapter();
			}
			@Override
			public Adapter caseCodeblockType(CodeblockType object) {
				return createCodeblockTypeAdapter();
			}
			@Override
			public Adapter caseCodephType(CodephType object) {
				return createCodephTypeAdapter();
			}
			@Override
			public Adapter caseColspecType(ColspecType object) {
				return createColspecTypeAdapter();
			}
			@Override
			public Adapter caseCommentType(CommentType object) {
				return createCommentTypeAdapter();
			}
			@Override
			public Adapter caseComponentType(ComponentType object) {
				return createComponentTypeAdapter();
			}
			@Override
			public Adapter caseCoordsType(CoordsType object) {
				return createCoordsTypeAdapter();
			}
			@Override
			public Adapter caseCopyrholderType(CopyrholderType object) {
				return createCopyrholderTypeAdapter();
			}
			@Override
			public Adapter caseCopyrightType(CopyrightType object) {
				return createCopyrightTypeAdapter();
			}
			@Override
			public Adapter caseCopyryearType(CopyryearType object) {
				return createCopyryearTypeAdapter();
			}
			@Override
			public Adapter caseCreatedType(CreatedType object) {
				return createCreatedTypeAdapter();
			}
			@Override
			public Adapter caseCritdatesType(CritdatesType object) {
				return createCritdatesTypeAdapter();
			}
			@Override
			public Adapter caseDataAboutType(DataAboutType object) {
				return createDataAboutTypeAdapter();
			}
			@Override
			public Adapter caseDataType(DataType object) {
				return createDataTypeAdapter();
			}
			@Override
			public Adapter caseDdhdType(DdhdType object) {
				return createDdhdTypeAdapter();
			}
			@Override
			public Adapter caseDdType(DdType object) {
				return createDdTypeAdapter();
			}
			@Override
			public Adapter caseDelimType(DelimType object) {
				return createDelimTypeAdapter();
			}
			@Override
			public Adapter caseDescType(DescType object) {
				return createDescTypeAdapter();
			}
			@Override
			public Adapter caseDeviceType(DeviceType object) {
				return createDeviceTypeAdapter();
			}
			@Override
			public Adapter caseDlentryType(DlentryType object) {
				return createDlentryTypeAdapter();
			}
			@Override
			public Adapter caseDlheadType(DlheadType object) {
				return createDlheadTypeAdapter();
			}
			@Override
			public Adapter caseDlType(DlType object) {
				return createDlTypeAdapter();
			}
			@Override
			public Adapter caseDocumentRoot(DocumentRoot object) {
				return createDocumentRootAdapter();
			}
			@Override
			public Adapter caseDraftCommentType(DraftCommentType object) {
				return createDraftCommentTypeAdapter();
			}
			@Override
			public Adapter caseDthdType(DthdType object) {
				return createDthdTypeAdapter();
			}
			@Override
			public Adapter caseDtType(DtType object) {
				return createDtTypeAdapter();
			}
			@Override
			public Adapter caseEntryType(EntryType object) {
				return createEntryTypeAdapter();
			}
			@Override
			public Adapter caseExampleType(ExampleType object) {
				return createExampleTypeAdapter();
			}
			@Override
			public Adapter caseFeatnumType(FeatnumType object) {
				return createFeatnumTypeAdapter();
			}
			@Override
			public Adapter caseFiggroupType(FiggroupType object) {
				return createFiggroupTypeAdapter();
			}
			@Override
			public Adapter caseFigType(FigType object) {
				return createFigTypeAdapter();
			}
			@Override
			public Adapter caseFilepathType(FilepathType object) {
				return createFilepathTypeAdapter();
			}
			@Override
			public Adapter caseFiletypeType(FiletypeType object) {
				return createFiletypeTypeAdapter();
			}
			@Override
			public Adapter caseFnType(FnType object) {
				return createFnTypeAdapter();
			}
			@Override
			public Adapter caseForeignType(ForeignType object) {
				return createForeignTypeAdapter();
			}
			@Override
			public Adapter caseFragmentType(FragmentType object) {
				return createFragmentTypeAdapter();
			}
			@Override
			public Adapter caseFragrefType(FragrefType object) {
				return createFragrefTypeAdapter();
			}
			@Override
			public Adapter caseGroupchoiceType(GroupchoiceType object) {
				return createGroupchoiceTypeAdapter();
			}
			@Override
			public Adapter caseGroupcompType(GroupcompType object) {
				return createGroupcompTypeAdapter();
			}
			@Override
			public Adapter caseGroupseqType(GroupseqType object) {
				return createGroupseqTypeAdapter();
			}
			@Override
			public Adapter caseImagemapType(ImagemapType object) {
				return createImagemapTypeAdapter();
			}
			@Override
			public Adapter caseImageType(ImageType object) {
				return createImageTypeAdapter();
			}
			@Override
			public Adapter caseIndexBaseType(IndexBaseType object) {
				return createIndexBaseTypeAdapter();
			}
			@Override
			public Adapter caseIndexSeeAlsoType(IndexSeeAlsoType object) {
				return createIndexSeeAlsoTypeAdapter();
			}
			@Override
			public Adapter caseIndexSeeType(IndexSeeType object) {
				return createIndexSeeTypeAdapter();
			}
			@Override
			public Adapter caseIndexSortAsType(IndexSortAsType object) {
				return createIndexSortAsTypeAdapter();
			}
			@Override
			public Adapter caseIndextermrefType(IndextermrefType object) {
				return createIndextermrefTypeAdapter();
			}
			@Override
			public Adapter caseIndextermType(IndextermType object) {
				return createIndextermTypeAdapter();
			}
			@Override
			public Adapter caseInheritanceType(InheritanceType object) {
				return createInheritanceTypeAdapter();
			}
			@Override
			public Adapter caseItemgroupType(ItemgroupType object) {
				return createItemgroupTypeAdapter();
			}
			@Override
			public Adapter caseIType(IType object) {
				return createITypeAdapter();
			}
			@Override
			public Adapter caseKbdataBodyType(KbdataBodyType object) {
				return createKbdataBodyTypeAdapter();
			}
			@Override
			public Adapter caseKbdataCategoryType(KbdataCategoryType object) {
				return createKbdataCategoryTypeAdapter();
			}
			@Override
			public Adapter caseKbdataIdType(KbdataIdType object) {
				return createKbdataIdTypeAdapter();
			}
			@Override
			public Adapter caseKbdataKeywords(KbdataKeywords object) {
				return createKbdataKeywordsAdapter();
			}
			@Override
			public Adapter caseKbdataKeywordsType(KbdataKeywordsType object) {
				return createKbdataKeywordsTypeAdapter();
			}
			@Override
			public Adapter caseKbdataKeywordType(KbdataKeywordType object) {
				return createKbdataKeywordTypeAdapter();
			}
			@Override
			public Adapter caseKbdataMetadataType(KbdataMetadataType object) {
				return createKbdataMetadataTypeAdapter();
			}
			@Override
			public Adapter caseKbdataPlatformType(KbdataPlatformType object) {
				return createKbdataPlatformTypeAdapter();
			}
			@Override
			public Adapter caseKbdataRefType(KbdataRefType object) {
				return createKbdataRefTypeAdapter();
			}
			@Override
			public Adapter caseKbdataType(KbdataType object) {
				return createKbdataTypeAdapter();
			}
			@Override
			public Adapter caseKeywordsType(KeywordsType object) {
				return createKeywordsTypeAdapter();
			}
			@Override
			public Adapter caseKeywordType(KeywordType object) {
				return createKeywordTypeAdapter();
			}
			@Override
			public Adapter caseKwdType(KwdType object) {
				return createKwdTypeAdapter();
			}
			@Override
			public Adapter caseLinesType(LinesType object) {
				return createLinesTypeAdapter();
			}
			@Override
			public Adapter caseLinkinfoType(LinkinfoType object) {
				return createLinkinfoTypeAdapter();
			}
			@Override
			public Adapter caseLinklistType(LinklistType object) {
				return createLinklistTypeAdapter();
			}
			@Override
			public Adapter caseLinkpoolType(LinkpoolType object) {
				return createLinkpoolTypeAdapter();
			}
			@Override
			public Adapter caseLinktextType(LinktextType object) {
				return createLinktextTypeAdapter();
			}
			@Override
			public Adapter caseLinkType(LinkType object) {
				return createLinkTypeAdapter();
			}
			@Override
			public Adapter caseLiType(LiType object) {
				return createLiTypeAdapter();
			}
			@Override
			public Adapter caseLocalType(LocalType object) {
				return createLocalTypeAdapter();
			}
			@Override
			public Adapter caseLqType(LqType object) {
				return createLqTypeAdapter();
			}
			@Override
			public Adapter caseMacroType(MacroType object) {
				return createMacroTypeAdapter();
			}
			@Override
			public Adapter caseMemberType(MemberType object) {
				return createMemberTypeAdapter();
			}
			@Override
			public Adapter caseMenucascadeType(MenucascadeType object) {
				return createMenucascadeTypeAdapter();
			}
			@Override
			public Adapter caseMessagesType(MessagesType object) {
				return createMessagesTypeAdapter();
			}
			@Override
			public Adapter caseMetadataType(MetadataType object) {
				return createMetadataTypeAdapter();
			}
			@Override
			public Adapter caseMethodType(MethodType object) {
				return createMethodTypeAdapter();
			}
			@Override
			public Adapter caseMsgblockType(MsgblockType object) {
				return createMsgblockTypeAdapter();
			}
			@Override
			public Adapter caseMsgnumType(MsgnumType object) {
				return createMsgnumTypeAdapter();
			}
			@Override
			public Adapter caseMsgphType(MsgphType object) {
				return createMsgphTypeAdapter();
			}
			@Override
			public Adapter caseNavtitleType(NavtitleType object) {
				return createNavtitleTypeAdapter();
			}
			@Override
			public Adapter caseNoteType(NoteType object) {
				return createNoteTypeAdapter();
			}
			@Override
			public Adapter caseNoTopicNestingType(NoTopicNestingType object) {
				return createNoTopicNestingTypeAdapter();
			}
			@Override
			public Adapter caseObjectType(ObjectType object) {
				return createObjectTypeAdapter();
			}
			@Override
			public Adapter caseOlType(OlType object) {
				return createOlTypeAdapter();
			}
			@Override
			public Adapter caseOperType(OperType object) {
				return createOperTypeAdapter();
			}
			@Override
			public Adapter caseOptionType(OptionType object) {
				return createOptionTypeAdapter();
			}
			@Override
			public Adapter caseOthermetaType(OthermetaType object) {
				return createOthermetaTypeAdapter();
			}
			@Override
			public Adapter caseParameterType(ParameterType object) {
				return createParameterTypeAdapter();
			}
			@Override
			public Adapter caseParamType(ParamType object) {
				return createParamTypeAdapter();
			}
			@Override
			public Adapter caseParmlType(ParmlType object) {
				return createParmlTypeAdapter();
			}
			@Override
			public Adapter caseParmnameType(ParmnameType object) {
				return createParmnameTypeAdapter();
			}
			@Override
			public Adapter casePdType(PdType object) {
				return createPdTypeAdapter();
			}
			@Override
			public Adapter casePermissionsType(PermissionsType object) {
				return createPermissionsTypeAdapter();
			}
			@Override
			public Adapter casePhType(PhType object) {
				return createPhTypeAdapter();
			}
			@Override
			public Adapter casePlatformType(PlatformType object) {
				return createPlatformTypeAdapter();
			}
			@Override
			public Adapter casePlentryType(PlentryType object) {
				return createPlentryTypeAdapter();
			}
			@Override
			public Adapter casePreType(PreType object) {
				return createPreTypeAdapter();
			}
			@Override
			public Adapter caseProdinfoType(ProdinfoType object) {
				return createProdinfoTypeAdapter();
			}
			@Override
			public Adapter caseProdnameType(ProdnameType object) {
				return createProdnameTypeAdapter();
			}
			@Override
			public Adapter casePrognumType(PrognumType object) {
				return createPrognumTypeAdapter();
			}
			@Override
			public Adapter casePrologType(PrologType object) {
				return createPrologTypeAdapter();
			}
			@Override
			public Adapter casePropdeschdType(PropdeschdType object) {
				return createPropdeschdTypeAdapter();
			}
			@Override
			public Adapter casePropdescType(PropdescType object) {
				return createPropdescTypeAdapter();
			}
			@Override
			public Adapter casePropertiesType(PropertiesType object) {
				return createPropertiesTypeAdapter();
			}
			@Override
			public Adapter casePropertyType(PropertyType object) {
				return createPropertyTypeAdapter();
			}
			@Override
			public Adapter casePropheadType(PropheadType object) {
				return createPropheadTypeAdapter();
			}
			@Override
			public Adapter caseProptypehdType(ProptypehdType object) {
				return createProptypehdTypeAdapter();
			}
			@Override
			public Adapter caseProptypeType(ProptypeType object) {
				return createProptypeTypeAdapter();
			}
			@Override
			public Adapter casePropvaluehdType(PropvaluehdType object) {
				return createPropvaluehdTypeAdapter();
			}
			@Override
			public Adapter casePropvalueType(PropvalueType object) {
				return createPropvalueTypeAdapter();
			}
			@Override
			public Adapter casePtType(PtType object) {
				return createPtTypeAdapter();
			}
			@Override
			public Adapter casePType(PType object) {
				return createPTypeAdapter();
			}
			@Override
			public Adapter casePublisherType(PublisherType object) {
				return createPublisherTypeAdapter();
			}
			@Override
			public Adapter caseQType(QType object) {
				return createQTypeAdapter();
			}
			@Override
			public Adapter caseRefbodyType(RefbodyType object) {
				return createRefbodyTypeAdapter();
			}
			@Override
			public Adapter caseReferenceType(ReferenceType object) {
				return createReferenceTypeAdapter();
			}
			@Override
			public Adapter caseRefsynType(RefsynType object) {
				return createRefsynTypeAdapter();
			}
			@Override
			public Adapter caseRelatedLinksType(RelatedLinksType object) {
				return createRelatedLinksTypeAdapter();
			}
			@Override
			public Adapter caseRepsepType(RepsepType object) {
				return createRepsepTypeAdapter();
			}
			@Override
			public Adapter caseRequiredCleanupType(RequiredCleanupType object) {
				return createRequiredCleanupTypeAdapter();
			}
			@Override
			public Adapter caseResourceidType(ResourceidType object) {
				return createResourceidTypeAdapter();
			}
			@Override
			public Adapter caseRevisedType(RevisedType object) {
				return createRevisedTypeAdapter();
			}
			@Override
			public Adapter caseRowType(RowType object) {
				return createRowTypeAdapter();
			}
			@Override
			public Adapter caseScreenType(ScreenType object) {
				return createScreenTypeAdapter();
			}
			@Override
			public Adapter caseSearchtitleType(SearchtitleType object) {
				return createSearchtitleTypeAdapter();
			}
			@Override
			public Adapter caseSectionType(SectionType object) {
				return createSectionTypeAdapter();
			}
			@Override
			public Adapter caseSepType(SepType object) {
				return createSepTypeAdapter();
			}
			@Override
			public Adapter caseSeriesType(SeriesType object) {
				return createSeriesTypeAdapter();
			}
			@Override
			public Adapter caseShapeType(ShapeType object) {
				return createShapeTypeAdapter();
			}
			@Override
			public Adapter caseShortcutType(ShortcutType object) {
				return createShortcutTypeAdapter();
			}
			@Override
			public Adapter caseShortdescType(ShortdescType object) {
				return createShortdescTypeAdapter();
			}
			@Override
			public Adapter caseSimpletableType(SimpletableType object) {
				return createSimpletableTypeAdapter();
			}
			@Override
			public Adapter caseSliType(SliType object) {
				return createSliTypeAdapter();
			}
			@Override
			public Adapter caseSlType(SlType object) {
				return createSlTypeAdapter();
			}
			@Override
			public Adapter caseSolutionType(SolutionType object) {
				return createSolutionTypeAdapter();
			}
			@Override
			public Adapter caseSourceType(SourceType object) {
				return createSourceTypeAdapter();
			}
			@Override
			public Adapter caseStateType(StateType object) {
				return createStateTypeAdapter();
			}
			@Override
			public Adapter caseStentryType(StentryType object) {
				return createStentryTypeAdapter();
			}
			@Override
			public Adapter caseStheadType(StheadType object) {
				return createStheadTypeAdapter();
			}
			@Override
			public Adapter caseStrowType(StrowType object) {
				return createStrowTypeAdapter();
			}
			@Override
			public Adapter caseSubType(SubType object) {
				return createSubTypeAdapter();
			}
			@Override
			public Adapter caseSupType(SupType object) {
				return createSupTypeAdapter();
			}
			@Override
			public Adapter caseSymptomType(SymptomType object) {
				return createSymptomTypeAdapter();
			}
			@Override
			public Adapter caseSynblkType(SynblkType object) {
				return createSynblkTypeAdapter();
			}
			@Override
			public Adapter caseSynnoterefType(SynnoterefType object) {
				return createSynnoterefTypeAdapter();
			}
			@Override
			public Adapter caseSynnoteType(SynnoteType object) {
				return createSynnoteTypeAdapter();
			}
			@Override
			public Adapter caseSynphType(SynphType object) {
				return createSynphTypeAdapter();
			}
			@Override
			public Adapter caseSyntaxdiagramType(SyntaxdiagramType object) {
				return createSyntaxdiagramTypeAdapter();
			}
			@Override
			public Adapter caseSystemoutputType(SystemoutputType object) {
				return createSystemoutputTypeAdapter();
			}
			@Override
			public Adapter caseTableType(TableType object) {
				return createTableTypeAdapter();
			}
			@Override
			public Adapter caseTbodyType(TbodyType object) {
				return createTbodyTypeAdapter();
			}
			@Override
			public Adapter caseTermType(TermType object) {
				return createTermTypeAdapter();
			}
			@Override
			public Adapter caseTgroupType(TgroupType object) {
				return createTgroupTypeAdapter();
			}
			@Override
			public Adapter caseTheadType(TheadType object) {
				return createTheadTypeAdapter();
			}
			@Override
			public Adapter caseTitlealtsType(TitlealtsType object) {
				return createTitlealtsTypeAdapter();
			}
			@Override
			public Adapter caseTitleType(TitleType object) {
				return createTitleTypeAdapter();
			}
			@Override
			public Adapter caseTmType(TmType object) {
				return createTmTypeAdapter();
			}
			@Override
			public Adapter caseTopicType(TopicType object) {
				return createTopicTypeAdapter();
			}
			@Override
			public Adapter caseTtType(TtType object) {
				return createTtTypeAdapter();
			}
			@Override
			public Adapter caseUicontrolType(UicontrolType object) {
				return createUicontrolTypeAdapter();
			}
			@Override
			public Adapter caseUlType(UlType object) {
				return createUlTypeAdapter();
			}
			@Override
			public Adapter caseUnknownType(UnknownType object) {
				return createUnknownTypeAdapter();
			}
			@Override
			public Adapter caseUserinputType(UserinputType object) {
				return createUserinputTypeAdapter();
			}
			@Override
			public Adapter caseUType(UType object) {
				return createUTypeAdapter();
			}
			@Override
			public Adapter caseVarnameType(VarnameType object) {
				return createVarnameTypeAdapter();
			}
			@Override
			public Adapter caseVarType(VarType object) {
				return createVarTypeAdapter();
			}
			@Override
			public Adapter caseVrmlistType(VrmlistType object) {
				return createVrmlistTypeAdapter();
			}
			@Override
			public Adapter caseVrmType(VrmType object) {
				return createVrmTypeAdapter();
			}
			@Override
			public Adapter caseWintitleType(WintitleType object) {
				return createWintitleTypeAdapter();
			}
			@Override
			public Adapter caseXrefType(XrefType object) {
				return createXrefTypeAdapter();
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
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.AbstractType <em>Abstract Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.AbstractType
	 * @generated
	 */
	public Adapter createAbstractTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.AltType <em>Alt Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.AltType
	 * @generated
	 */
	public Adapter createAltTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.Any <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.Any
	 * @generated
	 */
	public Adapter createAnyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ApinameType <em>Apiname Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ApinameType
	 * @generated
	 */
	public Adapter createApinameTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.AreaType <em>Area Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.AreaType
	 * @generated
	 */
	public Adapter createAreaTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.AudienceType <em>Audience Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.AudienceType
	 * @generated
	 */
	public Adapter createAudienceTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.AuthorType <em>Author Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.AuthorType
	 * @generated
	 */
	public Adapter createAuthorTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.BodyType <em>Body Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.BodyType
	 * @generated
	 */
	public Adapter createBodyTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.BooleanType <em>Boolean Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.BooleanType
	 * @generated
	 */
	public Adapter createBooleanTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.BrandType <em>Brand Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.BrandType
	 * @generated
	 */
	public Adapter createBrandTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.BType <em>BType</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.BType
	 * @generated
	 */
	public Adapter createBTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CallType <em>Call Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CallType
	 * @generated
	 */
	public Adapter createCallTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CategoryType <em>Category Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CategoryType
	 * @generated
	 */
	public Adapter createCategoryTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CiteType <em>Cite Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CiteType
	 * @generated
	 */
	public Adapter createCiteTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ClassType <em>Class Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ClassType
	 * @generated
	 */
	public Adapter createClassTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CmdnameType <em>Cmdname Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CmdnameType
	 * @generated
	 */
	public Adapter createCmdnameTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CodeblockType <em>Codeblock Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CodeblockType
	 * @generated
	 */
	public Adapter createCodeblockTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CodephType <em>Codeph Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CodephType
	 * @generated
	 */
	public Adapter createCodephTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType <em>Colspec Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ColspecType
	 * @generated
	 */
	public Adapter createColspecTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CommentType <em>Comment Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CommentType
	 * @generated
	 */
	public Adapter createCommentTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ComponentType <em>Component Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ComponentType
	 * @generated
	 */
	public Adapter createComponentTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CoordsType <em>Coords Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CoordsType
	 * @generated
	 */
	public Adapter createCoordsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CopyrholderType <em>Copyrholder Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CopyrholderType
	 * @generated
	 */
	public Adapter createCopyrholderTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CopyrightType <em>Copyright Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CopyrightType
	 * @generated
	 */
	public Adapter createCopyrightTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CopyryearType <em>Copyryear Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CopyryearType
	 * @generated
	 */
	public Adapter createCopyryearTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CreatedType <em>Created Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CreatedType
	 * @generated
	 */
	public Adapter createCreatedTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CritdatesType <em>Critdates Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.CritdatesType
	 * @generated
	 */
	public Adapter createCritdatesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataAboutType <em>Data About Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataAboutType
	 * @generated
	 */
	public Adapter createDataAboutTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DataType
	 * @generated
	 */
	public Adapter createDataTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DdhdType <em>Ddhd Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DdhdType
	 * @generated
	 */
	public Adapter createDdhdTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DdType <em>Dd Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DdType
	 * @generated
	 */
	public Adapter createDdTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DelimType <em>Delim Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DelimType
	 * @generated
	 */
	public Adapter createDelimTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DescType <em>Desc Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DescType
	 * @generated
	 */
	public Adapter createDescTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DeviceType <em>Device Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DeviceType
	 * @generated
	 */
	public Adapter createDeviceTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DlentryType <em>Dlentry Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DlentryType
	 * @generated
	 */
	public Adapter createDlentryTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DlheadType <em>Dlhead Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DlheadType
	 * @generated
	 */
	public Adapter createDlheadTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DlType <em>Dl Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DlType
	 * @generated
	 */
	public Adapter createDlTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DocumentRoot
	 * @generated
	 */
	public Adapter createDocumentRootAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DraftCommentType <em>Draft Comment Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DraftCommentType
	 * @generated
	 */
	public Adapter createDraftCommentTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DthdType <em>Dthd Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DthdType
	 * @generated
	 */
	public Adapter createDthdTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DtType <em>Dt Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.DtType
	 * @generated
	 */
	public Adapter createDtTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.EntryType <em>Entry Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.EntryType
	 * @generated
	 */
	public Adapter createEntryTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ExampleType <em>Example Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ExampleType
	 * @generated
	 */
	public Adapter createExampleTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FeatnumType <em>Featnum Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FeatnumType
	 * @generated
	 */
	public Adapter createFeatnumTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FiggroupType <em>Figgroup Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FiggroupType
	 * @generated
	 */
	public Adapter createFiggroupTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FigType <em>Fig Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FigType
	 * @generated
	 */
	public Adapter createFigTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FilepathType <em>Filepath Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FilepathType
	 * @generated
	 */
	public Adapter createFilepathTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FiletypeType <em>Filetype Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FiletypeType
	 * @generated
	 */
	public Adapter createFiletypeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FnType <em>Fn Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FnType
	 * @generated
	 */
	public Adapter createFnTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ForeignType <em>Foreign Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ForeignType
	 * @generated
	 */
	public Adapter createForeignTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FragmentType <em>Fragment Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FragmentType
	 * @generated
	 */
	public Adapter createFragmentTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FragrefType <em>Fragref Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.FragrefType
	 * @generated
	 */
	public Adapter createFragrefTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.GroupchoiceType <em>Groupchoice Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.GroupchoiceType
	 * @generated
	 */
	public Adapter createGroupchoiceTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.GroupcompType <em>Groupcomp Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.GroupcompType
	 * @generated
	 */
	public Adapter createGroupcompTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.GroupseqType <em>Groupseq Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.GroupseqType
	 * @generated
	 */
	public Adapter createGroupseqTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImagemapType <em>Imagemap Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImagemapType
	 * @generated
	 */
	public Adapter createImagemapTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImageType <em>Image Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ImageType
	 * @generated
	 */
	public Adapter createImageTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.IndexBaseType <em>Index Base Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.IndexBaseType
	 * @generated
	 */
	public Adapter createIndexBaseTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.IndexSeeAlsoType <em>Index See Also Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.IndexSeeAlsoType
	 * @generated
	 */
	public Adapter createIndexSeeAlsoTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.IndexSeeType <em>Index See Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.IndexSeeType
	 * @generated
	 */
	public Adapter createIndexSeeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.IndexSortAsType <em>Index Sort As Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.IndexSortAsType
	 * @generated
	 */
	public Adapter createIndexSortAsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.IndextermrefType <em>Indextermref Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.IndextermrefType
	 * @generated
	 */
	public Adapter createIndextermrefTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.IndextermType <em>Indexterm Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.IndextermType
	 * @generated
	 */
	public Adapter createIndextermTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.InheritanceType <em>Inheritance Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.InheritanceType
	 * @generated
	 */
	public Adapter createInheritanceTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ItemgroupType <em>Itemgroup Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ItemgroupType
	 * @generated
	 */
	public Adapter createItemgroupTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.IType <em>IType</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.IType
	 * @generated
	 */
	public Adapter createITypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataBodyType <em>Body Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataBodyType
	 * @generated
	 */
	public Adapter createKbdataBodyTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataCategoryType <em>Category Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataCategoryType
	 * @generated
	 */
	public Adapter createKbdataCategoryTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataIdType <em>Id Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataIdType
	 * @generated
	 */
	public Adapter createKbdataIdTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataKeywords <em>Keywords</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataKeywords
	 * @generated
	 */
	public Adapter createKbdataKeywordsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataKeywordsType <em>Keywords Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataKeywordsType
	 * @generated
	 */
	public Adapter createKbdataKeywordsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataKeywordType <em>Keyword Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataKeywordType
	 * @generated
	 */
	public Adapter createKbdataKeywordTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataMetadataType <em>Metadata Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataMetadataType
	 * @generated
	 */
	public Adapter createKbdataMetadataTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPlatformType <em>Platform Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataPlatformType
	 * @generated
	 */
	public Adapter createKbdataPlatformTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataRefType <em>Ref Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataRefType
	 * @generated
	 */
	public Adapter createKbdataRefTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KbdataType
	 * @generated
	 */
	public Adapter createKbdataTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KeywordsType <em>Keywords Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KeywordsType
	 * @generated
	 */
	public Adapter createKeywordsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KeywordType <em>Keyword Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KeywordType
	 * @generated
	 */
	public Adapter createKeywordTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KwdType <em>Kwd Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.KwdType
	 * @generated
	 */
	public Adapter createKwdTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.LinesType <em>Lines Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.LinesType
	 * @generated
	 */
	public Adapter createLinesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.LinkinfoType <em>Linkinfo Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.LinkinfoType
	 * @generated
	 */
	public Adapter createLinkinfoTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.LinklistType <em>Linklist Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.LinklistType
	 * @generated
	 */
	public Adapter createLinklistTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.LinkpoolType <em>Linkpool Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.LinkpoolType
	 * @generated
	 */
	public Adapter createLinkpoolTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.LinktextType <em>Linktext Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.LinktextType
	 * @generated
	 */
	public Adapter createLinktextTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.LinkType <em>Link Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.LinkType
	 * @generated
	 */
	public Adapter createLinkTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.LiType <em>Li Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.LiType
	 * @generated
	 */
	public Adapter createLiTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.LocalType <em>Local Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.LocalType
	 * @generated
	 */
	public Adapter createLocalTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.LqType <em>Lq Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.LqType
	 * @generated
	 */
	public Adapter createLqTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MacroType <em>Macro Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MacroType
	 * @generated
	 */
	public Adapter createMacroTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MemberType <em>Member Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MemberType
	 * @generated
	 */
	public Adapter createMemberTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MenucascadeType <em>Menucascade Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MenucascadeType
	 * @generated
	 */
	public Adapter createMenucascadeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MessagesType <em>Messages Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MessagesType
	 * @generated
	 */
	public Adapter createMessagesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MetadataType <em>Metadata Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MetadataType
	 * @generated
	 */
	public Adapter createMetadataTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MethodType <em>Method Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MethodType
	 * @generated
	 */
	public Adapter createMethodTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MsgblockType <em>Msgblock Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MsgblockType
	 * @generated
	 */
	public Adapter createMsgblockTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MsgnumType <em>Msgnum Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MsgnumType
	 * @generated
	 */
	public Adapter createMsgnumTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MsgphType <em>Msgph Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.MsgphType
	 * @generated
	 */
	public Adapter createMsgphTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.NavtitleType <em>Navtitle Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.NavtitleType
	 * @generated
	 */
	public Adapter createNavtitleTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.NoteType <em>Note Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.NoteType
	 * @generated
	 */
	public Adapter createNoteTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.NoTopicNestingType <em>No Topic Nesting Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.NoTopicNestingType
	 * @generated
	 */
	public Adapter createNoTopicNestingTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ObjectType <em>Object Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ObjectType
	 * @generated
	 */
	public Adapter createObjectTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.OlType <em>Ol Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.OlType
	 * @generated
	 */
	public Adapter createOlTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.OperType <em>Oper Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.OperType
	 * @generated
	 */
	public Adapter createOperTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.OptionType <em>Option Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.OptionType
	 * @generated
	 */
	public Adapter createOptionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.OthermetaType <em>Othermeta Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.OthermetaType
	 * @generated
	 */
	public Adapter createOthermetaTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ParameterType <em>Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ParameterType
	 * @generated
	 */
	public Adapter createParameterTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ParamType <em>Param Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ParamType
	 * @generated
	 */
	public Adapter createParamTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ParmlType <em>Parml Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ParmlType
	 * @generated
	 */
	public Adapter createParmlTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ParmnameType <em>Parmname Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ParmnameType
	 * @generated
	 */
	public Adapter createParmnameTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PdType <em>Pd Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PdType
	 * @generated
	 */
	public Adapter createPdTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PermissionsType <em>Permissions Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PermissionsType
	 * @generated
	 */
	public Adapter createPermissionsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PhType <em>Ph Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PhType
	 * @generated
	 */
	public Adapter createPhTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PlatformType <em>Platform Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PlatformType
	 * @generated
	 */
	public Adapter createPlatformTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PlentryType <em>Plentry Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PlentryType
	 * @generated
	 */
	public Adapter createPlentryTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PreType <em>Pre Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PreType
	 * @generated
	 */
	public Adapter createPreTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType <em>Prodinfo Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdinfoType
	 * @generated
	 */
	public Adapter createProdinfoTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdnameType <em>Prodname Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProdnameType
	 * @generated
	 */
	public Adapter createProdnameTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PrognumType <em>Prognum Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PrognumType
	 * @generated
	 */
	public Adapter createPrognumTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PrologType <em>Prolog Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PrologType
	 * @generated
	 */
	public Adapter createPrologTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PropdeschdType <em>Propdeschd Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PropdeschdType
	 * @generated
	 */
	public Adapter createPropdeschdTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PropdescType <em>Propdesc Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PropdescType
	 * @generated
	 */
	public Adapter createPropdescTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PropertiesType <em>Properties Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PropertiesType
	 * @generated
	 */
	public Adapter createPropertiesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PropertyType <em>Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PropertyType
	 * @generated
	 */
	public Adapter createPropertyTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PropheadType <em>Prophead Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PropheadType
	 * @generated
	 */
	public Adapter createPropheadTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProptypehdType <em>Proptypehd Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProptypehdType
	 * @generated
	 */
	public Adapter createProptypehdTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProptypeType <em>Proptype Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ProptypeType
	 * @generated
	 */
	public Adapter createProptypeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PropvaluehdType <em>Propvaluehd Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PropvaluehdType
	 * @generated
	 */
	public Adapter createPropvaluehdTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PropvalueType <em>Propvalue Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PropvalueType
	 * @generated
	 */
	public Adapter createPropvalueTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PtType <em>Pt Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PtType
	 * @generated
	 */
	public Adapter createPtTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PType <em>PType</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PType
	 * @generated
	 */
	public Adapter createPTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PublisherType <em>Publisher Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.PublisherType
	 * @generated
	 */
	public Adapter createPublisherTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.QType <em>QType</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.QType
	 * @generated
	 */
	public Adapter createQTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.RefbodyType <em>Refbody Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.RefbodyType
	 * @generated
	 */
	public Adapter createRefbodyTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ReferenceType <em>Reference Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ReferenceType
	 * @generated
	 */
	public Adapter createReferenceTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.RefsynType <em>Refsyn Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.RefsynType
	 * @generated
	 */
	public Adapter createRefsynTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.RelatedLinksType <em>Related Links Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.RelatedLinksType
	 * @generated
	 */
	public Adapter createRelatedLinksTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.RepsepType <em>Repsep Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.RepsepType
	 * @generated
	 */
	public Adapter createRepsepTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.RequiredCleanupType <em>Required Cleanup Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.RequiredCleanupType
	 * @generated
	 */
	public Adapter createRequiredCleanupTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ResourceidType <em>Resourceid Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ResourceidType
	 * @generated
	 */
	public Adapter createResourceidTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.RevisedType <em>Revised Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.RevisedType
	 * @generated
	 */
	public Adapter createRevisedTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.RowType <em>Row Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.RowType
	 * @generated
	 */
	public Adapter createRowTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ScreenType <em>Screen Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ScreenType
	 * @generated
	 */
	public Adapter createScreenTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SearchtitleType <em>Searchtitle Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SearchtitleType
	 * @generated
	 */
	public Adapter createSearchtitleTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SectionType <em>Section Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SectionType
	 * @generated
	 */
	public Adapter createSectionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SepType <em>Sep Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SepType
	 * @generated
	 */
	public Adapter createSepTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SeriesType <em>Series Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SeriesType
	 * @generated
	 */
	public Adapter createSeriesTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType <em>Shape Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShapeType
	 * @generated
	 */
	public Adapter createShapeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShortcutType <em>Shortcut Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShortcutType
	 * @generated
	 */
	public Adapter createShortcutTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShortdescType <em>Shortdesc Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.ShortdescType
	 * @generated
	 */
	public Adapter createShortdescTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SimpletableType <em>Simpletable Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SimpletableType
	 * @generated
	 */
	public Adapter createSimpletableTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SliType <em>Sli Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SliType
	 * @generated
	 */
	public Adapter createSliTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SlType <em>Sl Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SlType
	 * @generated
	 */
	public Adapter createSlTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SolutionType <em>Solution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SolutionType
	 * @generated
	 */
	public Adapter createSolutionTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SourceType <em>Source Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SourceType
	 * @generated
	 */
	public Adapter createSourceTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StateType <em>State Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StateType
	 * @generated
	 */
	public Adapter createStateTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StentryType <em>Stentry Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StentryType
	 * @generated
	 */
	public Adapter createStentryTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StheadType <em>Sthead Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StheadType
	 * @generated
	 */
	public Adapter createStheadTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StrowType <em>Strow Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.StrowType
	 * @generated
	 */
	public Adapter createStrowTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SubType <em>Sub Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SubType
	 * @generated
	 */
	public Adapter createSubTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SupType <em>Sup Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SupType
	 * @generated
	 */
	public Adapter createSupTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SymptomType <em>Symptom Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SymptomType
	 * @generated
	 */
	public Adapter createSymptomTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SynblkType <em>Synblk Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SynblkType
	 * @generated
	 */
	public Adapter createSynblkTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SynnoterefType <em>Synnoteref Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SynnoterefType
	 * @generated
	 */
	public Adapter createSynnoterefTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SynnoteType <em>Synnote Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SynnoteType
	 * @generated
	 */
	public Adapter createSynnoteTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SynphType <em>Synph Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SynphType
	 * @generated
	 */
	public Adapter createSynphTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SyntaxdiagramType <em>Syntaxdiagram Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SyntaxdiagramType
	 * @generated
	 */
	public Adapter createSyntaxdiagramTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SystemoutputType <em>Systemoutput Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.SystemoutputType
	 * @generated
	 */
	public Adapter createSystemoutputTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TableType <em>Table Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TableType
	 * @generated
	 */
	public Adapter createTableTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TbodyType <em>Tbody Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TbodyType
	 * @generated
	 */
	public Adapter createTbodyTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TermType <em>Term Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TermType
	 * @generated
	 */
	public Adapter createTermTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TgroupType <em>Tgroup Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TgroupType
	 * @generated
	 */
	public Adapter createTgroupTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TheadType <em>Thead Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TheadType
	 * @generated
	 */
	public Adapter createTheadTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TitlealtsType <em>Titlealts Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TitlealtsType
	 * @generated
	 */
	public Adapter createTitlealtsTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TitleType <em>Title Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TitleType
	 * @generated
	 */
	public Adapter createTitleTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TmType <em>Tm Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TmType
	 * @generated
	 */
	public Adapter createTmTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType <em>Topic Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TopicType
	 * @generated
	 */
	public Adapter createTopicTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TtType <em>Tt Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.TtType
	 * @generated
	 */
	public Adapter createTtTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.UicontrolType <em>Uicontrol Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.UicontrolType
	 * @generated
	 */
	public Adapter createUicontrolTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.UlType <em>Ul Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.UlType
	 * @generated
	 */
	public Adapter createUlTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.UnknownType <em>Unknown Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.UnknownType
	 * @generated
	 */
	public Adapter createUnknownTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.UserinputType <em>Userinput Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.UserinputType
	 * @generated
	 */
	public Adapter createUserinputTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.UType <em>UType</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.UType
	 * @generated
	 */
	public Adapter createUTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.VarnameType <em>Varname Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.VarnameType
	 * @generated
	 */
	public Adapter createVarnameTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.VarType <em>Var Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.VarType
	 * @generated
	 */
	public Adapter createVarTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.VrmlistType <em>Vrmlist Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.VrmlistType
	 * @generated
	 */
	public Adapter createVrmlistTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.VrmType <em>Vrm Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.VrmType
	 * @generated
	 */
	public Adapter createVrmTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.WintitleType <em>Wintitle Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.WintitleType
	 * @generated
	 */
	public Adapter createWintitleTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.XrefType <em>Xref Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.nokia.carbide.cpp.internal.codescanner.gen.Kbdata.XrefType
	 * @generated
	 */
	public Adapter createXrefTypeAdapter() {
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

} //KbdataAdapterFactory
