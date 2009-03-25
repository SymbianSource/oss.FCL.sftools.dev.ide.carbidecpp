/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.emf.component;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Designer Images Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.sdt.emf.component.DesignerImagesType#getLargeIconFile <em>Large Icon File</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DesignerImagesType#getLayoutImageFile <em>Layout Image File</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DesignerImagesType#getSmallIconFile <em>Small Icon File</em>}</li>
 *   <li>{@link com.nokia.sdt.emf.component.DesignerImagesType#getThumbnailFile <em>Thumbnail File</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.sdt.emf.component.ComponentPackage#getDesignerImagesType()
 * @model extendedMetaData="name='designerImages_._type' kind='empty'"
 * @generated
 */
public interface DesignerImagesType extends EObject{
	/**
	 * Returns the value of the '<em><b>Large Icon File</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Large Icon File</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Large Icon File</em>' attribute.
	 * @see #setLargeIconFile(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDesignerImagesType_LargeIconFile()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='largeIconFile'"
	 * @generated
	 */
    String getLargeIconFile();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DesignerImagesType#getLargeIconFile <em>Large Icon File</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Large Icon File</em>' attribute.
	 * @see #getLargeIconFile()
	 * @generated
	 */
    void setLargeIconFile(String value);

	/**
	 * Returns the value of the '<em><b>Layout Image File</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Layout Image File</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Layout Image File</em>' attribute.
	 * @see #setLayoutImageFile(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDesignerImagesType_LayoutImageFile()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='layoutImageFile'"
	 * @generated
	 */
    String getLayoutImageFile();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DesignerImagesType#getLayoutImageFile <em>Layout Image File</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Layout Image File</em>' attribute.
	 * @see #getLayoutImageFile()
	 * @generated
	 */
    void setLayoutImageFile(String value);

	/**
	 * Returns the value of the '<em><b>Small Icon File</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Small Icon File</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A relative path to an Eclipse-supported image type, such as PNG, GIF, or BMP. This image is displayed in the outline.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Small Icon File</em>' attribute.
	 * @see #setSmallIconFile(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDesignerImagesType_SmallIconFile()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='smallIconFile'"
	 * @generated
	 */
    String getSmallIconFile();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DesignerImagesType#getSmallIconFile <em>Small Icon File</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Small Icon File</em>' attribute.
	 * @see #getSmallIconFile()
	 * @generated
	 */
    void setSmallIconFile(String value);

	/**
	 * Returns the value of the '<em><b>Thumbnail File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Thumbnail File</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A relative path to an Eclipse-supported image type, such as PNG, GIF, or BMP. This image is displayed in UI designer's graphical
	 * 				layout area if the component does not provide rendering code.
	 * 				
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Thumbnail File</em>' attribute.
	 * @see #setThumbnailFile(String)
	 * @see com.nokia.sdt.emf.component.ComponentPackage#getDesignerImagesType_ThumbnailFile()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='thumbnailFile'"
	 * @generated
	 */
	String getThumbnailFile();

	/**
	 * Sets the value of the '{@link com.nokia.sdt.emf.component.DesignerImagesType#getThumbnailFile <em>Thumbnail File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Thumbnail File</em>' attribute.
	 * @see #getThumbnailFile()
	 * @generated
	 */
	void setThumbnailFile(String value);

} // DesignerImagesType
