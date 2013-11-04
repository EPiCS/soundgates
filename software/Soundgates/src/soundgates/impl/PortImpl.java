/**
 */
package soundgates.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.emf.ecore.util.InternalEList;
import soundgates.AtomicSoundComponent;
import soundgates.Connection;
import soundgates.DataType;
import soundgates.Direction;
import soundgates.Port;
import soundgates.SoundComponent;
import soundgates.SoundgatesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Port</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link soundgates.impl.PortImpl#getOutgoingConnection <em>Outgoing Connection</em>}</li>
 *   <li>{@link soundgates.impl.PortImpl#getIncomingConnection <em>Incoming Connection</em>}</li>
 *   <li>{@link soundgates.impl.PortImpl#getComponent <em>Component</em>}</li>
 *   <li>{@link soundgates.impl.PortImpl#getDirection <em>Direction</em>}</li>
 *   <li>{@link soundgates.impl.PortImpl#getDataType <em>Data Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PortImpl extends NamedElementImpl implements Port {
	/**
	 * The cached value of the '{@link #getOutgoingConnection() <em>Outgoing Connection</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutgoingConnection()
	 * @generated
	 * @ordered
	 */
	protected EList<Connection> outgoingConnection;

	/**
	 * The cached value of the '{@link #getIncomingConnection() <em>Incoming Connection</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncomingConnection()
	 * @generated
	 * @ordered
	 */
	protected Connection incomingConnection;

	/**
	 * The default value of the '{@link #getDirection() <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirection()
	 * @generated
	 * @ordered
	 */
	protected static final Direction DIRECTION_EDEFAULT = Direction.IN;

	/**
	 * The cached value of the '{@link #getDirection() <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirection()
	 * @generated
	 * @ordered
	 */
	protected Direction direction = DIRECTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getDataType() <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataType()
	 * @generated
	 * @ordered
	 */
	protected static final DataType DATA_TYPE_EDEFAULT = DataType.FLOAT;

	/**
	 * The cached value of the '{@link #getDataType() <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataType()
	 * @generated
	 * @ordered
	 */
	protected DataType dataType = DATA_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PortImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SoundgatesPackage.Literals.PORT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Connection> getOutgoingConnection() {
		if (outgoingConnection == null) {
			outgoingConnection = new EObjectWithInverseResolvingEList<Connection>(Connection.class, this, SoundgatesPackage.PORT__OUTGOING_CONNECTION, SoundgatesPackage.CONNECTION__SOURCE);
		}
		return outgoingConnection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Connection getIncomingConnection() {
		if (incomingConnection != null && incomingConnection.eIsProxy()) {
			InternalEObject oldIncomingConnection = (InternalEObject)incomingConnection;
			incomingConnection = (Connection)eResolveProxy(oldIncomingConnection);
			if (incomingConnection != oldIncomingConnection) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SoundgatesPackage.PORT__INCOMING_CONNECTION, oldIncomingConnection, incomingConnection));
			}
		}
		return incomingConnection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Connection basicGetIncomingConnection() {
		return incomingConnection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIncomingConnection(Connection newIncomingConnection, NotificationChain msgs) {
		Connection oldIncomingConnection = incomingConnection;
		incomingConnection = newIncomingConnection;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SoundgatesPackage.PORT__INCOMING_CONNECTION, oldIncomingConnection, newIncomingConnection);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIncomingConnection(Connection newIncomingConnection) {
		if (newIncomingConnection != incomingConnection) {
			NotificationChain msgs = null;
			if (incomingConnection != null)
				msgs = ((InternalEObject)incomingConnection).eInverseRemove(this, SoundgatesPackage.CONNECTION__TARGET, Connection.class, msgs);
			if (newIncomingConnection != null)
				msgs = ((InternalEObject)newIncomingConnection).eInverseAdd(this, SoundgatesPackage.CONNECTION__TARGET, Connection.class, msgs);
			msgs = basicSetIncomingConnection(newIncomingConnection, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SoundgatesPackage.PORT__INCOMING_CONNECTION, newIncomingConnection, newIncomingConnection));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SoundComponent getComponent() {
		if (eContainerFeatureID() != SoundgatesPackage.PORT__COMPONENT) return null;
		return (SoundComponent)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetComponent(SoundComponent newComponent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newComponent, SoundgatesPackage.PORT__COMPONENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponent(SoundComponent newComponent) {
		if (newComponent != eInternalContainer() || (eContainerFeatureID() != SoundgatesPackage.PORT__COMPONENT && newComponent != null)) {
			if (EcoreUtil.isAncestor(this, newComponent))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newComponent != null)
				msgs = ((InternalEObject)newComponent).eInverseAdd(this, SoundgatesPackage.SOUND_COMPONENT__PORTS, SoundComponent.class, msgs);
			msgs = basicSetComponent(newComponent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SoundgatesPackage.PORT__COMPONENT, newComponent, newComponent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDirection(Direction newDirection) {
		Direction oldDirection = direction;
		direction = newDirection == null ? DIRECTION_EDEFAULT : newDirection;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SoundgatesPackage.PORT__DIRECTION, oldDirection, direction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataType getDataType() {
		return dataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDataType(DataType newDataType) {
		DataType oldDataType = dataType;
		dataType = newDataType == null ? DATA_TYPE_EDEFAULT : newDataType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SoundgatesPackage.PORT__DATA_TYPE, oldDataType, dataType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SoundgatesPackage.PORT__OUTGOING_CONNECTION:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutgoingConnection()).basicAdd(otherEnd, msgs);
			case SoundgatesPackage.PORT__INCOMING_CONNECTION:
				if (incomingConnection != null)
					msgs = ((InternalEObject)incomingConnection).eInverseRemove(this, SoundgatesPackage.CONNECTION__TARGET, Connection.class, msgs);
				return basicSetIncomingConnection((Connection)otherEnd, msgs);
			case SoundgatesPackage.PORT__COMPONENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetComponent((SoundComponent)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SoundgatesPackage.PORT__OUTGOING_CONNECTION:
				return ((InternalEList<?>)getOutgoingConnection()).basicRemove(otherEnd, msgs);
			case SoundgatesPackage.PORT__INCOMING_CONNECTION:
				return basicSetIncomingConnection(null, msgs);
			case SoundgatesPackage.PORT__COMPONENT:
				return basicSetComponent(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case SoundgatesPackage.PORT__COMPONENT:
				return eInternalContainer().eInverseRemove(this, SoundgatesPackage.SOUND_COMPONENT__PORTS, SoundComponent.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SoundgatesPackage.PORT__OUTGOING_CONNECTION:
				return getOutgoingConnection();
			case SoundgatesPackage.PORT__INCOMING_CONNECTION:
				if (resolve) return getIncomingConnection();
				return basicGetIncomingConnection();
			case SoundgatesPackage.PORT__COMPONENT:
				return getComponent();
			case SoundgatesPackage.PORT__DIRECTION:
				return getDirection();
			case SoundgatesPackage.PORT__DATA_TYPE:
				return getDataType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SoundgatesPackage.PORT__OUTGOING_CONNECTION:
				getOutgoingConnection().clear();
				getOutgoingConnection().addAll((Collection<? extends Connection>)newValue);
				return;
			case SoundgatesPackage.PORT__INCOMING_CONNECTION:
				setIncomingConnection((Connection)newValue);
				return;
			case SoundgatesPackage.PORT__COMPONENT:
				setComponent((SoundComponent)newValue);
				return;
			case SoundgatesPackage.PORT__DIRECTION:
				setDirection((Direction)newValue);
				return;
			case SoundgatesPackage.PORT__DATA_TYPE:
				setDataType((DataType)newValue);
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
			case SoundgatesPackage.PORT__OUTGOING_CONNECTION:
				getOutgoingConnection().clear();
				return;
			case SoundgatesPackage.PORT__INCOMING_CONNECTION:
				setIncomingConnection((Connection)null);
				return;
			case SoundgatesPackage.PORT__COMPONENT:
				setComponent((SoundComponent)null);
				return;
			case SoundgatesPackage.PORT__DIRECTION:
				setDirection(DIRECTION_EDEFAULT);
				return;
			case SoundgatesPackage.PORT__DATA_TYPE:
				setDataType(DATA_TYPE_EDEFAULT);
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
			case SoundgatesPackage.PORT__OUTGOING_CONNECTION:
				return outgoingConnection != null && !outgoingConnection.isEmpty();
			case SoundgatesPackage.PORT__INCOMING_CONNECTION:
				return incomingConnection != null;
			case SoundgatesPackage.PORT__COMPONENT:
				return getComponent() != null;
			case SoundgatesPackage.PORT__DIRECTION:
				return direction != DIRECTION_EDEFAULT;
			case SoundgatesPackage.PORT__DATA_TYPE:
				return dataType != DATA_TYPE_EDEFAULT;
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
		result.append(" (direction: ");
		result.append(direction);
		result.append(", dataType: ");
		result.append(dataType);
		result.append(')');
		return result.toString();
	}
	
	public void setName(String newName) {
		if(!(this.eContainer() instanceof AtomicSoundComponent)){
			String oldName = name;
			name = newName;
			if (eNotificationRequired())
				eNotify(new ENotificationImpl(this, Notification.SET, SoundgatesPackage.NAMED_ELEMENT__NAME, oldName, name));
			
		}
	}

} //PortImpl
