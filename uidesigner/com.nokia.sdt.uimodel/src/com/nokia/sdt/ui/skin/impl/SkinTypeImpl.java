/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.ui.skin.impl;

import com.nokia.sdt.ui.skin.*;
import com.nokia.sdt.uimodel.Messages;
import com.swtdesigner.ResourceManager;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.swt.graphics.*;

import java.io.File;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Iterator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.SkinTypeImpl#getEditorArea <em>Editor Area</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.SkinTypeImpl#getHotZone <em>Hot Zone</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.SkinTypeImpl#getBackgroundColor <em>Background Color</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.SkinTypeImpl#getHeight <em>Height</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.SkinTypeImpl#getImageFilePath <em>Image File Path</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.SkinTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.SkinTypeImpl#getWidth <em>Width</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SkinTypeImpl extends EObjectImpl implements SkinType, ISkin {
	/**
	 * The cached value of the '{@link #getEditorArea() <em>Editor Area</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditorArea()
	 * @generated
	 * @ordered
	 */
	protected EditorAreaType editorArea = null;

	/**
	 * The cached value of the '{@link #getHotZone() <em>Hot Zone</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHotZone()
	 * @generated
	 * @ordered
	 */
	protected EList hotZone = null;

	/**
	 * The cached value of the '{@link #getBackgroundColor() <em>Background Color</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBackgroundColor()
	 * @generated
	 * @ordered
	 */
	protected BackgroundColorType backgroundColor = null;

	/**
	 * The default value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected static final short HEIGHT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected short height = HEIGHT_EDEFAULT;

	/**
	 * This is true if the Height attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean heightESet = false;

	/**
	 * The default value of the '{@link #getImageFilePath() <em>Image File Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImageFilePath()
	 * @generated
	 * @ordered
	 */
	protected static final String IMAGE_FILE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImageFilePath() <em>Image File Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImageFilePath()
	 * @generated
	 * @ordered
	 */
	protected String imageFilePath = IMAGE_FILE_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected static final short WIDTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected short width = WIDTH_EDEFAULT;

	/**
	 * This is true if the Width attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean widthESet = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected SkinTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return SkinPackage.eINSTANCE.getSkinType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EditorAreaType getEditorArea() {
		return editorArea;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEditorArea(EditorAreaType newEditorArea, NotificationChain msgs) {
		EditorAreaType oldEditorArea = editorArea;
		editorArea = newEditorArea;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SkinPackage.SKIN_TYPE__EDITOR_AREA, oldEditorArea, newEditorArea);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEditorArea(EditorAreaType newEditorArea) {
		if (newEditorArea != editorArea) {
			NotificationChain msgs = null;
			if (editorArea != null)
				msgs = ((InternalEObject)editorArea).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SkinPackage.SKIN_TYPE__EDITOR_AREA, null, msgs);
			if (newEditorArea != null)
				msgs = ((InternalEObject)newEditorArea).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SkinPackage.SKIN_TYPE__EDITOR_AREA, null, msgs);
			msgs = basicSetEditorArea(newEditorArea, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SkinPackage.SKIN_TYPE__EDITOR_AREA, newEditorArea, newEditorArea));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getHotZone() {
		if (hotZone == null) {
			hotZone = new EObjectContainmentEList(HotZoneType.class, this, SkinPackage.SKIN_TYPE__HOT_ZONE);
		}
		return hotZone;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BackgroundColorType getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBackgroundColor(BackgroundColorType newBackgroundColor, NotificationChain msgs) {
		BackgroundColorType oldBackgroundColor = backgroundColor;
		backgroundColor = newBackgroundColor;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SkinPackage.SKIN_TYPE__BACKGROUND_COLOR, oldBackgroundColor, newBackgroundColor);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBackgroundColor(BackgroundColorType newBackgroundColor) {
		if (newBackgroundColor != backgroundColor) {
			NotificationChain msgs = null;
			if (backgroundColor != null)
				msgs = ((InternalEObject)backgroundColor).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SkinPackage.SKIN_TYPE__BACKGROUND_COLOR, null, msgs);
			if (newBackgroundColor != null)
				msgs = ((InternalEObject)newBackgroundColor).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SkinPackage.SKIN_TYPE__BACKGROUND_COLOR, null, msgs);
			msgs = basicSetBackgroundColor(newBackgroundColor, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SkinPackage.SKIN_TYPE__BACKGROUND_COLOR, newBackgroundColor, newBackgroundColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImageFilePath(String newImageFilePath) {
		String oldImageFilePath = imageFilePath;
		imageFilePath = newImageFilePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SkinPackage.SKIN_TYPE__IMAGE_FILE_PATH, oldImageFilePath, imageFilePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public short getHeight() {
		return height;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHeight(short newHeight) {
		short oldHeight = height;
		height = newHeight;
		boolean oldHeightESet = heightESet;
		heightESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SkinPackage.SKIN_TYPE__HEIGHT, oldHeight, height, !oldHeightESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetHeight() {
		short oldHeight = height;
		boolean oldHeightESet = heightESet;
		height = HEIGHT_EDEFAULT;
		heightESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, SkinPackage.SKIN_TYPE__HEIGHT, oldHeight, HEIGHT_EDEFAULT, oldHeightESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetHeight() {
		return heightESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImageFilePath() {
		return imageFilePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SkinPackage.SKIN_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public short getWidth() {
		return width;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWidth(short newWidth) {
		short oldWidth = width;
		width = newWidth;
		boolean oldWidthESet = widthESet;
		widthESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SkinPackage.SKIN_TYPE__WIDTH, oldWidth, width, !oldWidthESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetWidth() {
		short oldWidth = width;
		boolean oldWidthESet = widthESet;
		width = WIDTH_EDEFAULT;
		widthESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, SkinPackage.SKIN_TYPE__WIDTH, oldWidth, WIDTH_EDEFAULT, oldWidthESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetWidth() {
		return widthESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case SkinPackage.SKIN_TYPE__EDITOR_AREA:
					return basicSetEditorArea(null, msgs);
				case SkinPackage.SKIN_TYPE__HOT_ZONE:
					return ((InternalEList)getHotZone()).basicRemove(otherEnd, msgs);
				case SkinPackage.SKIN_TYPE__BACKGROUND_COLOR:
					return basicSetBackgroundColor(null, msgs);
				default:
					return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
			}
		}
		return eBasicSetContainer(null, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case SkinPackage.SKIN_TYPE__EDITOR_AREA:
				return getEditorArea();
			case SkinPackage.SKIN_TYPE__HOT_ZONE:
				return getHotZone();
			case SkinPackage.SKIN_TYPE__BACKGROUND_COLOR:
				return getBackgroundColor();
			case SkinPackage.SKIN_TYPE__HEIGHT:
				return new Short(getHeight());
			case SkinPackage.SKIN_TYPE__IMAGE_FILE_PATH:
				return getImageFilePath();
			case SkinPackage.SKIN_TYPE__NAME:
				return getName();
			case SkinPackage.SKIN_TYPE__WIDTH:
				return new Short(getWidth());
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case SkinPackage.SKIN_TYPE__EDITOR_AREA:
				setEditorArea((EditorAreaType)newValue);
				return;
			case SkinPackage.SKIN_TYPE__HOT_ZONE:
				getHotZone().clear();
				getHotZone().addAll((Collection)newValue);
				return;
			case SkinPackage.SKIN_TYPE__BACKGROUND_COLOR:
				setBackgroundColor((BackgroundColorType)newValue);
				return;
			case SkinPackage.SKIN_TYPE__HEIGHT:
				setHeight(((Short)newValue).shortValue());
				return;
			case SkinPackage.SKIN_TYPE__IMAGE_FILE_PATH:
				setImageFilePath((String)newValue);
				return;
			case SkinPackage.SKIN_TYPE__NAME:
				setName((String)newValue);
				return;
			case SkinPackage.SKIN_TYPE__WIDTH:
				setWidth(((Short)newValue).shortValue());
				return;
		}
		eDynamicSet(eFeature, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case SkinPackage.SKIN_TYPE__EDITOR_AREA:
				setEditorArea((EditorAreaType)null);
				return;
			case SkinPackage.SKIN_TYPE__HOT_ZONE:
				getHotZone().clear();
				return;
			case SkinPackage.SKIN_TYPE__BACKGROUND_COLOR:
				setBackgroundColor((BackgroundColorType)null);
				return;
			case SkinPackage.SKIN_TYPE__HEIGHT:
				unsetHeight();
				return;
			case SkinPackage.SKIN_TYPE__IMAGE_FILE_PATH:
				setImageFilePath(IMAGE_FILE_PATH_EDEFAULT);
				return;
			case SkinPackage.SKIN_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case SkinPackage.SKIN_TYPE__WIDTH:
				unsetWidth();
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case SkinPackage.SKIN_TYPE__EDITOR_AREA:
				return editorArea != null;
			case SkinPackage.SKIN_TYPE__HOT_ZONE:
				return hotZone != null && !hotZone.isEmpty();
			case SkinPackage.SKIN_TYPE__BACKGROUND_COLOR:
				return backgroundColor != null;
			case SkinPackage.SKIN_TYPE__HEIGHT:
				return isSetHeight();
			case SkinPackage.SKIN_TYPE__IMAGE_FILE_PATH:
				return IMAGE_FILE_PATH_EDEFAULT == null ? imageFilePath != null : !IMAGE_FILE_PATH_EDEFAULT.equals(imageFilePath);
			case SkinPackage.SKIN_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case SkinPackage.SKIN_TYPE__WIDTH:
				return isSetWidth();
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (height: ");
		if (heightESet) result.append(height); else result.append("<unset>");
		result.append(", imageFilePath: ");
		result.append(imageFilePath);
		result.append(", name: ");
		result.append(name);
		result.append(", width: ");
		if (widthESet) result.append(width); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

	private Point skinSize;
	private Rectangle editorAreaBounds;
	private Image image;
	private String parentPath;
	private String displayName;
	private String id;
	
	public Image getImage() {
		if (image == null) {
			String path = getImageFilePath();
			if (path != null) {
				File imageFile = new File(parentPath, path);
				image = ResourceManager.getImage(imageFile.getPath());
			}
		}
		
		return image;
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public Point getSkinSize() {
		if (skinSize == null) {
			skinSize = new Point(getWidth(), getHeight());
		}
		return skinSize;
	}

	public Rectangle getEditorAreaBounds() {
		if (editorAreaBounds == null) {
			EditorAreaType ea = getEditorArea();
			editorAreaBounds = new Rectangle(ea.getX(), ea.getY(), ea.getWidth(), ea.getHeight());
		}
		
		return editorAreaBounds;
	}

	public Collection getHotzones() {
		return getHotZone();
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public String getID() {
		return id;
	}
	
	public String getDisplayName() {
		if (displayName == null) {
			String fmt = Messages.getString("SkinTypeImpl.1"); //$NON-NLS-1$
			EditorAreaType ea = getEditorArea();
			Object params[] = {getName(), new Integer(ea.getWidth()), new Integer(ea.getHeight())};
			displayName = MessageFormat.format(fmt, params);
		}
		return displayName;
	}
	
	/**
	 * 
	 * @param path the directory in which the image files exist
	 */
	public void setDirectory(String path) {
		parentPath = path;
		for (Iterator hotZones = getHotZone().iterator(); hotZones.hasNext();) {
			HotZoneType hotzone = (HotZoneType) hotZones.next();
			for (Iterator states = hotzone.getState().iterator(); states.hasNext();) {
				StateType state = (StateType) states.next();
				state.setDirectory(parentPath);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.sdt.ui.skin.ISkin#getBackgroundRGB()
	 */
	public RGB getBackgroundRGB() {
		BackgroundColorType colorType = getBackgroundColor();
		if (colorType != null) {
			return new RGB(colorType.getRed(), colorType.getGreen(), colorType.getBlue());
		}
		return null;
	}

} //SkinTypeImpl
