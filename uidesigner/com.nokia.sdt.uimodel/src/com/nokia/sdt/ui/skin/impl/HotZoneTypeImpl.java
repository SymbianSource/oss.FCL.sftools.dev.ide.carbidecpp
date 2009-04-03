/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.nokia.sdt.ui.skin.impl;

import com.nokia.sdt.ui.skin.*;
import com.nokia.sdt.ui.skin.ISkin.Hotzone;

import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.swt.graphics.Rectangle;

import java.util.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Hot Zone Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.HotZoneTypeImpl#getEvent <em>Event</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.HotZoneTypeImpl#getState <em>State</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.HotZoneTypeImpl#getHeight <em>Height</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.HotZoneTypeImpl#isSticky <em>Sticky</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.HotZoneTypeImpl#getWidth <em>Width</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.HotZoneTypeImpl#getX <em>X</em>}</li>
 *   <li>{@link com.nokia.sdt.ui.skin.impl.HotZoneTypeImpl#getY <em>Y</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class HotZoneTypeImpl extends EObjectImpl implements HotZoneType, Hotzone, MouseListener {
	/**
	 * The cached value of the '{@link #getEvent() <em>Event</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEvent()
	 * @generated
	 * @ordered
	 */
	protected EventType event = null;

	/**
	 * The cached value of the '{@link #getState() <em>State</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected EList state = null;

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
	 * The default value of the '{@link #isSticky() <em>Sticky</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSticky()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STICKY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSticky() <em>Sticky</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSticky()
	 * @generated
	 * @ordered
	 */
	protected boolean sticky = STICKY_EDEFAULT;

	/**
	 * This is true if the Sticky attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean stickyESet = false;

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
	 * The default value of the '{@link #getX() <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getX()
	 * @generated
	 * @ordered
	 */
	protected static final short X_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getX() <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getX()
	 * @generated
	 * @ordered
	 */
	protected short x = X_EDEFAULT;

	/**
	 * This is true if the X attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean xESet = false;

	/**
	 * The default value of the '{@link #getY() <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getY()
	 * @generated
	 * @ordered
	 */
	protected static final short Y_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getY() <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getY()
	 * @generated
	 * @ordered
	 */
	protected short y = Y_EDEFAULT;

	/**
	 * This is true if the Y attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean yESet = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected HotZoneTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return SkinPackage.eINSTANCE.getHotZoneType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventType getEvent() {
		return event;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEvent(EventType newEvent, NotificationChain msgs) {
		EventType oldEvent = event;
		event = newEvent;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SkinPackage.HOT_ZONE_TYPE__EVENT, oldEvent, newEvent);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEvent(EventType newEvent) {
		if (newEvent != event) {
			NotificationChain msgs = null;
			if (event != null)
				msgs = ((InternalEObject)event).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - SkinPackage.HOT_ZONE_TYPE__EVENT, null, msgs);
			if (newEvent != null)
				msgs = ((InternalEObject)newEvent).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - SkinPackage.HOT_ZONE_TYPE__EVENT, null, msgs);
			msgs = basicSetEvent(newEvent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SkinPackage.HOT_ZONE_TYPE__EVENT, newEvent, newEvent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getState() {
		if (state == null) {
			state = new EObjectContainmentEList(StateType.class, this, SkinPackage.HOT_ZONE_TYPE__STATE);
		}
		return state;
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
			eNotify(new ENotificationImpl(this, Notification.SET, SkinPackage.HOT_ZONE_TYPE__HEIGHT, oldHeight, height, !oldHeightESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, SkinPackage.HOT_ZONE_TYPE__HEIGHT, oldHeight, HEIGHT_EDEFAULT, oldHeightESet));
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
	public boolean isSticky() {
		return sticky;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSticky(boolean newSticky) {
		boolean oldSticky = sticky;
		sticky = newSticky;
		boolean oldStickyESet = stickyESet;
		stickyESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SkinPackage.HOT_ZONE_TYPE__STICKY, oldSticky, sticky, !oldStickyESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetSticky() {
		boolean oldSticky = sticky;
		boolean oldStickyESet = stickyESet;
		sticky = STICKY_EDEFAULT;
		stickyESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, SkinPackage.HOT_ZONE_TYPE__STICKY, oldSticky, STICKY_EDEFAULT, oldStickyESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetSticky() {
		return stickyESet;
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
			eNotify(new ENotificationImpl(this, Notification.SET, SkinPackage.HOT_ZONE_TYPE__WIDTH, oldWidth, width, !oldWidthESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, SkinPackage.HOT_ZONE_TYPE__WIDTH, oldWidth, WIDTH_EDEFAULT, oldWidthESet));
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
	public short getX() {
		return x;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setX(short newX) {
		short oldX = x;
		x = newX;
		boolean oldXESet = xESet;
		xESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SkinPackage.HOT_ZONE_TYPE__X, oldX, x, !oldXESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetX() {
		short oldX = x;
		boolean oldXESet = xESet;
		x = X_EDEFAULT;
		xESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, SkinPackage.HOT_ZONE_TYPE__X, oldX, X_EDEFAULT, oldXESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetX() {
		return xESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public short getY() {
		return y;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setY(short newY) {
		short oldY = y;
		y = newY;
		boolean oldYESet = yESet;
		yESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SkinPackage.HOT_ZONE_TYPE__Y, oldY, y, !oldYESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetY() {
		short oldY = y;
		boolean oldYESet = yESet;
		y = Y_EDEFAULT;
		yESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, SkinPackage.HOT_ZONE_TYPE__Y, oldY, Y_EDEFAULT, oldYESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetY() {
		return yESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case SkinPackage.HOT_ZONE_TYPE__EVENT:
					return basicSetEvent(null, msgs);
				case SkinPackage.HOT_ZONE_TYPE__STATE:
					return ((InternalEList)getState()).basicRemove(otherEnd, msgs);
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
			case SkinPackage.HOT_ZONE_TYPE__EVENT:
				return getEvent();
			case SkinPackage.HOT_ZONE_TYPE__STATE:
				return getState();
			case SkinPackage.HOT_ZONE_TYPE__HEIGHT:
				return new Short(getHeight());
			case SkinPackage.HOT_ZONE_TYPE__STICKY:
				return isSticky() ? Boolean.TRUE : Boolean.FALSE;
			case SkinPackage.HOT_ZONE_TYPE__WIDTH:
				return new Short(getWidth());
			case SkinPackage.HOT_ZONE_TYPE__X:
				return new Short(getX());
			case SkinPackage.HOT_ZONE_TYPE__Y:
				return new Short(getY());
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
			case SkinPackage.HOT_ZONE_TYPE__EVENT:
				setEvent((EventType)newValue);
				return;
			case SkinPackage.HOT_ZONE_TYPE__STATE:
				getState().clear();
				getState().addAll((Collection)newValue);
				return;
			case SkinPackage.HOT_ZONE_TYPE__HEIGHT:
				setHeight(((Short)newValue).shortValue());
				return;
			case SkinPackage.HOT_ZONE_TYPE__STICKY:
				setSticky(((Boolean)newValue).booleanValue());
				return;
			case SkinPackage.HOT_ZONE_TYPE__WIDTH:
				setWidth(((Short)newValue).shortValue());
				return;
			case SkinPackage.HOT_ZONE_TYPE__X:
				setX(((Short)newValue).shortValue());
				return;
			case SkinPackage.HOT_ZONE_TYPE__Y:
				setY(((Short)newValue).shortValue());
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
			case SkinPackage.HOT_ZONE_TYPE__EVENT:
				setEvent((EventType)null);
				return;
			case SkinPackage.HOT_ZONE_TYPE__STATE:
				getState().clear();
				return;
			case SkinPackage.HOT_ZONE_TYPE__HEIGHT:
				unsetHeight();
				return;
			case SkinPackage.HOT_ZONE_TYPE__STICKY:
				unsetSticky();
				return;
			case SkinPackage.HOT_ZONE_TYPE__WIDTH:
				unsetWidth();
				return;
			case SkinPackage.HOT_ZONE_TYPE__X:
				unsetX();
				return;
			case SkinPackage.HOT_ZONE_TYPE__Y:
				unsetY();
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
			case SkinPackage.HOT_ZONE_TYPE__EVENT:
				return event != null;
			case SkinPackage.HOT_ZONE_TYPE__STATE:
				return state != null && !state.isEmpty();
			case SkinPackage.HOT_ZONE_TYPE__HEIGHT:
				return isSetHeight();
			case SkinPackage.HOT_ZONE_TYPE__STICKY:
				return isSetSticky();
			case SkinPackage.HOT_ZONE_TYPE__WIDTH:
				return isSetWidth();
			case SkinPackage.HOT_ZONE_TYPE__X:
				return isSetX();
			case SkinPackage.HOT_ZONE_TYPE__Y:
				return isSetY();
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
		result.append(", sticky: ");
		if (stickyESet) result.append(sticky); else result.append("<unset>");
		result.append(", width: ");
		if (widthESet) result.append(width); else result.append("<unset>");
		result.append(", x: ");
		if (xESet) result.append(x); else result.append("<unset>");
		result.append(", y: ");
		if (yESet) result.append(y); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

	private Rectangle bounds;
	private StateTypeImpl currentState;
	private List listeners;

	public Rectangle getBounds() {
		if (bounds == null) {
			bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
		}
		
		return bounds;
	}

	public Object getID() {
		return getEvent().getId();
	}

	public State getCurrentState() {
		if (currentState == null)
			setNextState();
		return currentState;
	}

	public Collection getStates() {
		return getState();
	}

	public void addListener(SkinHotzoneListener listener) {
		if (listeners == null)
			listeners = new ArrayList();

		listeners.add(listener);
	}

	public void removeListener(SkinHotzoneListener listener) {
		if (listeners != null)
			listeners.remove(listener);
	}
	
	protected void fireHotzoneEvent() {
		if (listeners == null)
			return;
		
		for (Iterator iter = listeners.iterator(); iter.hasNext();) {
			((SkinHotzoneListener) iter.next()).pressed(this);
		}
	}
	
	public void setNextState() {
		Collection states = getStates();
		for (Iterator iter = states.iterator(); iter.hasNext();) {
			if ((currentState == null) || (iter.next().equals(currentState))) {
				currentState = (StateTypeImpl) (iter.hasNext() ? iter.next() : states.iterator().next());
				break;
			}
		}
	}

	public void mousePressed(MouseEvent me) {
		IFigure figure = (IFigure) me.getSource();
		Point location = me.getLocation();
		Point figureLoc = figure.getBounds().getLocation();
		org.eclipse.draw2d.geometry.Rectangle hotzoneBounds = 
				new org.eclipse.draw2d.geometry.Rectangle(
				getX() + figureLoc.x, getY() + figureLoc.y, 
				getWidth(), getHeight());
		if (hotzoneBounds.contains(location)) {
			if (sticky) {
				setNextState();
				fireHotzoneEvent();
			}
		}
	}

	public void mouseReleased(MouseEvent me) {}

	public void mouseDoubleClicked(MouseEvent me) {}

} //HotZoneTypeImpl
