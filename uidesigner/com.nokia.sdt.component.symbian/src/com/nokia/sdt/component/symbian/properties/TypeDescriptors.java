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
package com.nokia.sdt.component.symbian.properties;

import com.nokia.sdt.component.IComponentSet;
import com.nokia.sdt.component.ITypeDescriptor;
import com.nokia.sdt.component.property.IPropertyValueSource;
import com.nokia.sdt.component.symbian.ComponentProvider;
import com.nokia.sdt.component.symbian.ComponentSystemPlugin;
import com.nokia.sdt.component.symbian.Messages;
import com.nokia.sdt.emf.component.EnumElementType;
import com.nokia.sdt.emf.component.PropertyDataType;
import com.nokia.sdt.emf.component.SourceTypeMappingType;
import com.nokia.cpp.internal.api.utils.core.Check;
import com.nokia.cpp.internal.api.utils.core.ILocalizedStrings;
import com.nokia.cpp.internal.api.utils.core.TextUtils;
import com.nokia.cpp.internal.api.utils.ui.ModelObjectComboBoxCellEditor;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.osgi.framework.Bundle;

import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TypeDescriptors {
	
	public static final String BOOLEAN_IMAGE = "boolean"; //$NON-NLS-1$
	public static final String INTEGER_IMAGE = "integer"; //$NON-NLS-1$
	public static final String FLOAT_IMAGE = "float"; //$NON-NLS-1$
	public static final String TEXT_IMAGE = "text"; //$NON-NLS-1$
	public static final String UNIQUE_NAME_IMAGE = TEXT_IMAGE;
	public static final String ENUM_IMAGE = "enumeration"; //$NON-NLS-1$
	public static final String GENERIC_IMAGE = "generic"; //$NON-NLS-1$
	public static final String COMPONENT_REFERENCE_IMAGE = GENERIC_IMAGE;
	public static final char QUOTE_CHAR = '"';
	
	public static void registerDefaultTypeHandlers(ComponentProvider provider) {
		loadImages();
		provider.registerTypeDescriptor(ITypeDescriptor.BOOLEAN_TYPE,
				new BooleanTypeDescriptor());
		
		provider.registerTypeDescriptor(ITypeDescriptor.INTEGER_TYPE,
				new IntegerTypeDescriptor());

		provider.registerTypeDescriptor(ITypeDescriptor.FLOAT_TYPE,
				new FloatTypeDescriptor());
		
		provider.registerTypeDescriptor(ITypeDescriptor.STRING_TYPE,
				new StringTypeDescriptor());
		
		provider.registerTypeDescriptor(ITypeDescriptor.LOCALIZED_STRING_TYPE,
				new LocalizedStringTypeDescriptor());
	
		provider.registerTypeDescriptor(ITypeDescriptor.VOID_TYPE,
				new VoidTypeDescriptor());
		
		provider.registerTypeDescriptor(ITypeDescriptor.UNIQUE_NAME_TYPE,
				new UniqueNameTypeDescriptor());

        provider.registerTypeDescriptor(ITypeDescriptor.COMPONENT_REFERENCE_TYPE,
                new ComponentReferenceTypeDescriptor());

    }
	
	public static ITypeDescriptor lookupPrimitiveDescriptor(ComponentProvider provider, PropertyDataType propertyType, ITypeDescriptor extendedDescriptor) {
		ITypeDescriptor result = null;
		String typeID = null;
		switch (propertyType.getValue()) {
		case PropertyDataType.VOID:
			typeID = ITypeDescriptor.VOID_TYPE;
			break;
		case PropertyDataType.BOOLEAN:
			typeID = ITypeDescriptor.BOOLEAN_TYPE;
			break;
		case PropertyDataType.INTEGER:
			typeID = ITypeDescriptor.INTEGER_TYPE;
			break;
		case PropertyDataType.FLOAT:
			typeID = ITypeDescriptor.FLOAT_TYPE;
			break;
		case PropertyDataType.STRING:
			typeID = ITypeDescriptor.STRING_TYPE;
			break;
		case PropertyDataType.UNIQUE_NAME:
			typeID = ITypeDescriptor.UNIQUE_NAME_TYPE;
			break;
		case PropertyDataType.LOCALIZED_STRING:
			typeID = ITypeDescriptor.LOCALIZED_STRING_TYPE;
			break;
		}
		
		if (typeID != null) {
			result = provider.lookupTypeDescriptor(typeID);
			
			// see if this is extended with an enum
			if (extendedDescriptor != null && result != null) {
				result = getExtendedPropertyDescriptor(result, extendedDescriptor);
			}
		}
		return result;
	}
	
	/**
	 * Take a type descriptor and return an extended descriptor that
	 * allows display and selection of enums in addition to the 
	 * values provided by the type.
	 * @param typeDescriptor original type descriptor
	 * @param extendedDescriptor enum type descriptor
	 * @return new type descriptor
	 */
	public static ITypeDescriptor getExtendedPropertyDescriptor(
			ITypeDescriptor typeDescriptor, ITypeDescriptor extendedDescriptor) {
		if (!(extendedDescriptor instanceof EnumPropertyTypeDescriptor)) {
			ComponentSystemPlugin.log(new IllegalArgumentException("Cannot extend " + typeDescriptor + " with " + extendedDescriptor));
			return typeDescriptor;
		}
		
		EnumPropertyTypeDescriptor enumPropertyTypeDescriptor = (EnumPropertyTypeDescriptor) extendedDescriptor;
		return new ExtendedWithEnumTypeDescriptor(typeDescriptor, enumPropertyTypeDescriptor);
	}

	private static HashMap imageMap;

	private static void loadImages() {
		Bundle emfEditPlugin = Platform.getBundle("org.eclipse.emf.edit"); //$NON-NLS-1$
		if (emfEditPlugin != null) {
			imageMap = new HashMap();
			imageMap.put(BOOLEAN_IMAGE, loadImage(emfEditPlugin, "BooleanValue.gif")); //$NON-NLS-1$
			imageMap.put(INTEGER_IMAGE, loadImage(emfEditPlugin, "IntegralValue.gif")); //$NON-NLS-1$
			imageMap.put(FLOAT_IMAGE, loadImage(emfEditPlugin, "RealValue.gif")); //$NON-NLS-1$
			imageMap.put(TEXT_IMAGE, loadImage(emfEditPlugin, "TextValue.gif")); //$NON-NLS-1$
			imageMap.put(ENUM_IMAGE, loadImage(emfEditPlugin, "GenericValue.gif")); //$NON-NLS-1$
			imageMap.put(GENERIC_IMAGE, loadImage(emfEditPlugin, "GenericValue.gif")); //$NON-NLS-1$
			imageMap.put(UNIQUE_NAME_IMAGE, loadImage(emfEditPlugin, "TextValue.gif")); //$NON-NLS-1$
		}
	}
	
	private static Image loadImage(Bundle emfEditPlugin, String fileName) {
		Image result = null;
		Path path = new Path("/icons/full/obj16/" + fileName); //$NON-NLS-1$
		URL url = FileLocator.find(emfEditPlugin, path, null); 
		ImageDescriptor id = ImageDescriptor.createFromURL(url);
		result = id.createImage();
		return result;
	}
	
	public static Image getImage(String key) {
		Image result = null;
		if (imageMap != null) {
			result = (Image) imageMap.get(key);
			if (result == null) {
				result = (Image) imageMap.get(GENERIC_IMAGE);
			}
		}
		return result;
	}
	
	public static abstract class AbstractTypeDescriptor implements ITypeDescriptor, Cloneable {
	
		private IComponentSet componentSet;
		private String imageKey;
		private Object defaultValue;
		
		protected AbstractTypeDescriptor(String imageKey, Object defaultValue) {
			this.imageKey = imageKey;
			this.defaultValue = defaultValue;
		}
		
		public String getImageKey() {
			return imageKey;
		}
		
		public Object getDefaultValue() {
			return defaultValue;
		}
		
		public Object clone() {
			AbstractTypeDescriptor copy = null;
			try {
				copy = (AbstractTypeDescriptor) super.clone();
				copy.componentSet = null;
			}
			catch (CloneNotSupportedException x) {
				// should not happen since this class supports cloning
			}
			return copy;
		}
		
		public ICellEditorValidator getValidator() {
			return null;
		}

		public Image getImage() {
			return TypeDescriptors.getImage(imageKey);
		}

		public IComponentSet getComponentSet() {
			return componentSet;
		}
		public void setComponentSet(IComponentSet componentSet) {
			this.componentSet = componentSet;
		}
		public Object defaultValue(IPropertyValueSource valueSource, Object propertyId) {
			return defaultValue;
		}
		
		public Collection getChoiceOfValues() {
			return null;
		}

		public boolean isLocalizable() {
			return false;
		}

		public boolean isReference() {
			return false;
		}

		public Object editableValueToPropertyValue(Object editableValue, IPropertyValueSource parentValueSource) {
			return editableValue;
		}

		public String getEditorFactoryClass() {
			return null;
		}
		
		public ILocalizedStrings getStrings() {
			return null;
		}
	}
	
	static class ExtendedWithEnumTypeDescriptor implements ITypeDescriptor {
		
		private final EnumPropertyTypeDescriptor enumPropertyTypeDescriptor;
		private final ITypeDescriptor typeDescriptor;
		private Collection choiceOfValues;
		private Map<String, String> internalToExternalEnumMap;
		private Map<String, String> externalToInternalEnumMap;
		
		public ExtendedWithEnumTypeDescriptor(ITypeDescriptor typeDescriptor,
				EnumPropertyTypeDescriptor enumPropertyTypeDescriptor) {
			Check.checkArg(enumPropertyTypeDescriptor);
			this.typeDescriptor = typeDescriptor;
			this.enumPropertyTypeDescriptor = enumPropertyTypeDescriptor;
		}
		
		@Override
		public Object clone() {
			Object cl = new ExtendedWithEnumTypeDescriptor(typeDescriptor, enumPropertyTypeDescriptor);
			return cl;
		}
		
		public CellEditor createPropertyEditor(Composite parent, int style,
				EObject object, String propertyPath) {
			CellEditor editor = new ExtendedWithEnumCellEditor(parent, typeDescriptor, this);
			if (editor == null)
				return null;
			editor.setValidator(getValidator());
			return editor;
		}

		public String toDisplayString(Object value) {
			// the enum is substituted
			if (value == null)
				return null;
			
			String valueStr = value.toString();
			
			ensureEnumMappings();

			String display = internalToExternalEnumMap.get(valueStr);
			if (display != null)
				return display;

			return typeDescriptor.toDisplayString(value);
		}
		
		public String toStorageString(Object value) {
			// extended enums may represent values not representable in the underlying type
			ensureEnumMappings();
			if (internalToExternalEnumMap.containsKey(value))
				return value.toString();

			// the internal value only
			return typeDescriptor.toStorageString(value);
		}
		
		public Object valueOf(String text) {
			ensureEnumMappings();
			String internal = externalToInternalEnumMap.get(text);
			if (internal != null)
				return internal;
			
			Object value = typeDescriptor.valueOf(text);
			if (value != null)
				return value.toString();
			
			return null;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof ExtendedWithEnumTypeDescriptor))
				return false;
			ExtendedWithEnumTypeDescriptor eetd = (ExtendedWithEnumTypeDescriptor) obj;
			return super.equals(eetd)
			&& eetd.typeDescriptor.equals(typeDescriptor)
			&& eetd.enumPropertyTypeDescriptor.equals(enumPropertyTypeDescriptor);
		}
		
		public Collection getChoiceOfValues() {
			if (choiceOfValues == null) {
				choiceOfValues = typeDescriptor.getChoiceOfValues();
				if (choiceOfValues == null)
					choiceOfValues = new ArrayList();
				ensureEnumMappings();
				choiceOfValues.addAll(externalToInternalEnumMap.values());
			}
			return choiceOfValues;
		}
		
		protected void ensureEnumMappings() {
			if (internalToExternalEnumMap == null) {
				internalToExternalEnumMap = new HashMap<String, String>();
				for (Object obj : enumPropertyTypeDescriptor.getEnumPropertyDeclarationType().getEnumElement()) {
					if (!(obj instanceof EnumElementType))
						continue;
					EnumElementType enumElement = (EnumElementType) obj;
					internalToExternalEnumMap.put(enumElement.getValue().toString(), 
							enumDisplayValue(enumElement));
				}
			}
			if (externalToInternalEnumMap == null) {
				externalToInternalEnumMap = new HashMap<String, String>();
				for (Object obj : enumPropertyTypeDescriptor.getEnumPropertyDeclarationType().getEnumElement()) {
					if (!(obj instanceof EnumElementType))
						continue;
					EnumElementType enumElement = (EnumElementType) obj;
					externalToInternalEnumMap.put(
							enumDisplayValue(enumElement), 
							enumElement.getValue().toString());
				}
			}
		}
		
		private String enumDisplayValue(EnumElementType elem) {
			ILocalizedStrings localizedStrings = enumPropertyTypeDescriptor.getStrings();
			String result = null;
			Object displayValue = elem.getDisplayValue();
			if (displayValue instanceof String) {
				if (localizedStrings != null)
					result = (localizedStrings.checkPercentKey((String)displayValue));
				else
					result = (String) displayValue;
			}
			// allow enum value to be a resource key
			if (result == null) {
				Object value = elem.getValue();
				if (value instanceof String 
						&& localizedStrings != null
						&& localizedStrings.hasString(((String)value))) {
					result = localizedStrings.getString((String) value);
				}
			}
			return result;
		}

		/** Get the list of enum strings selectable for the property. */
		protected Collection<String> getExtendedEnumStrings() {
			ensureEnumMappings();
			return internalToExternalEnumMap.values();
		}

		public Object defaultValue(IPropertyValueSource valueSource,
				Object propertyId) {
			return typeDescriptor.defaultValue(valueSource, propertyId);
		}
		
		public Object editableValueToPropertyValue(Object editableValue,
				IPropertyValueSource parentValueSource) {
			return typeDescriptor.editableValueToPropertyValue(editableValue, parentValueSource);
		}
		
		public IComponentSet getComponentSet() {
			return typeDescriptor.getComponentSet();
		}
		
		public String getEditorFactoryClass() {
			return typeDescriptor.getEditorFactoryClass();
		}
		
		public Image getImage() {
			return typeDescriptor.getImage();
		}
		
		public ICellEditorValidator getValidator() {
			final ICellEditorValidator baseValidator = typeDescriptor.getValidator();
			return new ICellEditorValidator() {

				public String isValid(Object value) {
					String valueStr = value != null ? value.toString() : "";
					ensureEnumMappings();
					
					if (externalToInternalEnumMap.values().contains(valueStr))
						return null;
					return baseValidator != null ? baseValidator.isValid(value) : null; 
				}
				
			};
		}
		
		public boolean isLocalizable() {
			return typeDescriptor.isLocalizable();
		}
		
		public boolean isReference() {
			return typeDescriptor.isReference();
		}
		
		public void setComponentSet(IComponentSet componentSet) {
			typeDescriptor.setComponentSet(componentSet);
		}
		
		public ILocalizedStrings getStrings() {
			return typeDescriptor.getStrings();
		}
	}

	public static class BooleanTypeDescriptor extends AbstractTypeDescriptor {

		static Collection choiceOfValues;
		
		BooleanTypeDescriptor() {
			super(BOOLEAN_IMAGE, Boolean.FALSE);
		}
		public Object valueOf(String text) {
			if (text.equalsIgnoreCase(Boolean.FALSE.toString()))
				return Boolean.FALSE;
			if (text.equalsIgnoreCase(Boolean.TRUE.toString()))
				return Boolean.TRUE;
			return null;
		}

		public String toDisplayString(Object value) {
            String result = null;
            if (value instanceof Boolean)
                result = value.toString();
            else if (value instanceof Number)
                result = Boolean.toString(((Number) value).intValue() != 0);
			return result;
		}
		
		public String toStorageString(Object value) {
			return toDisplayString(value);
		}

		@Override
		public ICellEditorValidator getValidator() {
			return new ICellEditorValidator() {
	           public String isValid(Object object) {
	        	   if (object instanceof Boolean)
	        		   return null;
	        	   else if (object instanceof String && 
	        			   (object.equals("false") || object.equals("true")))
	        		   return null;

	        	   return Messages.getString("TypeDescriptors.15"); //$NON-NLS-1$
	           }
			};
		}
		
		@Override
		public Collection getChoiceOfValues() {
			if (choiceOfValues == null) {
				choiceOfValues = new ArrayList(2);
				choiceOfValues.add(Boolean.FALSE);
				choiceOfValues.add(Boolean.TRUE);
			}
			return choiceOfValues;
		}
		
		public CellEditor createPropertyEditor(Composite parent, int style, EObject object, String propertyPath) {
			return new BooleanCellEditor(parent);
		}
	}

	public static class StringTypeDescriptor extends AbstractTypeDescriptor {
		public StringTypeDescriptor() {
			super(TEXT_IMAGE, ""); //$NON-NLS-1$
		}
		
		public StringTypeDescriptor(String imageKey, String defaultValue) {
			super(imageKey, defaultValue);
		}

		public Object valueOf(String text) {
			return text;
		}

		public String toDisplayString(Object value) {
			String result = null;
			if (value != null) {
				result = TextUtils.escape(value.toString(), QUOTE_CHAR);
			}
			return result;
		}
		
		public String toStorageString(Object value) {
			String result = null;
			if (value != null) {
				result = value.toString();
			}
			return result;
		}

		public String isValid(Object value) {
			if (value instanceof String)
				return null;
			return Messages.getString("TypeDescriptors.17");  //$NON-NLS-1$
		}

		public CellEditor createPropertyEditor(Composite parent, int style, EObject object, String propertyPath) {
			return new StringCellEditor(parent, style);
		}

		public boolean isLocalizable() {
			return false;
		}
	}
	
	public static class LocalizedStringTypeDescriptor extends StringTypeDescriptor {
		public boolean isLocalizable() {
			return true;
		}

	}
	
	public static class UniqueNameTypeDescriptor extends StringTypeDescriptor {
		public UniqueNameTypeDescriptor() {
			super(UNIQUE_NAME_IMAGE, ""); //$NON-NLS-1$
		}
	}

	public static class FloatTypeDescriptor extends AbstractTypeDescriptor {
		
		private double minValue = Double.MIN_VALUE;
		private double maxValue = Double.MAX_VALUE;
		
		public FloatTypeDescriptor() {
			super(FLOAT_IMAGE, new Double(0.0));
		}
		
		public void setMinValue(float minValue) {
			this.minValue = minValue;
		}
		
		public void setMaxValue(float maxValue) {
			this.maxValue = maxValue;
		}

		public Object valueOf(String text) {
            try {
                return Double.valueOf(text);
            } catch (NumberFormatException e) {
                return null;
            }                
		}

		// We have to ensure that we always get an actual string representing a value
		// Inihibit [-]Inifinity and NaN
		public String toDisplayString(Object value) {
			String result = null;
			if (value instanceof Double) {
				Double dbl = (Double) value;
				boolean isNotRepresentable = dbl.isInfinite() || dbl.isNaN();
				if (isNotRepresentable || (dbl.doubleValue() >= Double.MAX_VALUE))
					result = String.valueOf(Double.MAX_VALUE);
				else if (dbl.doubleValue() <= -Double.MAX_VALUE)
					result = String.valueOf(-Double.MAX_VALUE);
			}
			if ((result == null) && (value instanceof Number))
				result = value.toString();
			return result;
		}
		
		public String toStorageString(Object value) {
			return toDisplayString(value);
		}

		public String isValid(Object value) {
			String result = null;
			if (value instanceof Number) {
				Number val = (Number) value;
				if (val.doubleValue() < minValue ||
					val.doubleValue() > maxValue) {
					String fmt;
					if (minValue > Double.MIN_VALUE) {
						if (maxValue < Double.MAX_VALUE) {
							fmt = Messages.getString("TypeDescriptors.14"); //$NON-NLS-1$
						}
						else {
							fmt = Messages.getString("TypeDescriptors.13"); //$NON-NLS-1$
						}
					}
					else {
						fmt = Messages.getString("TypeDescriptors.12"); //$NON-NLS-1$
					}
					Object params[] = {Double.valueOf(minValue).toString(), 
									   Double.valueOf(maxValue).toString()};
					result = String.format(fmt, params);
				}
			}
			else {
				result = Messages.getString("TypeDescriptors.20"); //$NON-NLS-1$
			}
			return result;
		}
	
		public CellEditor createPropertyEditor(Composite parent, int style, EObject object, String propertyPath) {
			return new FloatCellEditor(parent, this, style);
		}
		
		@Override
		public ICellEditorValidator getValidator() {
			return new ICellEditorValidator() {
	           public String isValid(Object object)
	           {
	        	   String result = null;
	        	   Double doubleVal = null;
				   if (object instanceof Double) {
					   doubleVal = (Double) object;
				   }
				   else if (object != null){
					   try
					   {
						   doubleVal = Double.valueOf(object.toString());
					   }
					   catch (NumberFormatException exception)
					   {
					   }
				   }	
				   if (doubleVal != null) {
					   result = FloatTypeDescriptor.this.isValid(doubleVal);
				   }
				   else {
					   result = MessageFormat.format(Messages.getString("TypeDescriptors.BadFloatNumber"), //$NON-NLS-1$
					   							new Object[] { object });
				   }
				   return result;
	            }
			};
		}
	}

	public static class IntegerTypeDescriptor extends AbstractTypeDescriptor {
	
		private int minValue = Integer.MIN_VALUE;
		private int maxValue = Integer.MAX_VALUE;
		
		public IntegerTypeDescriptor() {
			super(INTEGER_IMAGE, new Integer(0));
		}
		
		public void setMinValue(int minValue) {
			this.minValue = minValue;
		}
		
		public void setMaxValue(int maxValue) {
			this.maxValue = maxValue;
		}
		
		public Object valueOf(String text) {
            try {
                return Integer.valueOf(text);
            } catch (NumberFormatException e) {
                return null;
            }
		}

		public String toDisplayString(Object value) {
			String result = null;
			if (value instanceof Integer)
				result = value.toString();
            else if (value instanceof Number)
                result = Integer.toString(((Number)value).intValue());
            else 
            	result = value.toString();
			return result;
		}
		
		public String toStorageString(Object value) {
			return toDisplayString(value);
		}

		public String isValid(Object value) {
			String result = null;
			if (value instanceof Integer) {
				Integer val = (Integer) value;
				if (val.intValue() < minValue ||
					val.intValue() > maxValue) {
					String fmt;
					if (minValue > Integer.MIN_VALUE) {
						if (maxValue < Integer.MAX_VALUE) {
							fmt = Messages.getString("TypeDescriptors.14"); //$NON-NLS-1$
						}
						else {
							fmt = Messages.getString("TypeDescriptors.13"); //$NON-NLS-1$
						}
					}
					else {
						fmt = Messages.getString("TypeDescriptors.12"); //$NON-NLS-1$
					}
					Object params[] = {Integer.valueOf(minValue).toString(),
									   Integer.valueOf(maxValue).toString()};
					result = MessageFormat.format(fmt, params);
				}
			}
			else {
				result = Messages.getString("TypeDescriptors.21"); //$NON-NLS-1$
			}
			return result;
		}

		public CellEditor createPropertyEditor(Composite parent, int style, EObject object, String propertyPath) {
			return new IntegerCellEditor(parent, this, style);
		}
		
		@Override
		public ICellEditorValidator getValidator() {
			return new ICellEditorValidator() {
	           public String isValid(Object object)
	           {
	        	   String result = null;
	        	   Integer intVal = null;
				   if (object instanceof Integer) {
					   intVal = (Integer) object;
				   }
				   else if (object != null){
					   try
					   {
						   // Javascript usually passes Doubles so start there and convert down
						   Double doubleVal = Double.valueOf(object.toString());
						   intVal = doubleVal.intValue();
						   //intVal = Integer.valueOf(object.toString());
					   }
					   catch (NumberFormatException exception)
					   {
					   }
				   }	
				   if (intVal != null) {
					   result = IntegerTypeDescriptor.this.isValid(intVal);
				   }
				   else {
					   result = MessageFormat.format(Messages.getString("TypeDescriptors.BadNumber"), //$NON-NLS-1$
							   				new Object[] { object });
				   }
				   return result;
	            }
	         };
		}
	}

	public static class VoidTypeDescriptor extends AbstractTypeDescriptor {
		VoidTypeDescriptor() {
			super(null, null);
		}
		public Object valueOf(String text) {
			return text;
		}

		public String toDisplayString(Object value) {
			return value != null? value.toString() : null;
		}

		public String toStorageString(Object value) {
			return toDisplayString(value);
		}

		public String isValid(Object value) {
			return null;
		}

		public CellEditor createPropertyEditor(Composite parent, int style, EObject object, String propertyPath) {
			return null;
		}
		public Image getImage() {
			return null;
		}
	}
	
	public static class ExtendedWithEnumCellEditor extends CellEditor {

		private final Collection builtins;
		private final ITypeDescriptor extendingTypeDescriptor;

		public ExtendedWithEnumCellEditor(Composite composite, ITypeDescriptor baseTypeDescriptor, ITypeDescriptor extendingTypeDescriptor) {
			this(composite, (Collection)null, extendingTypeDescriptor);
		}

		public ExtendedWithEnumCellEditor(Composite composite, Collection builtins, ITypeDescriptor extendingTypeDescriptor) {
			super(composite);
			this.builtins = builtins;
			this.extendingTypeDescriptor = extendingTypeDescriptor;
			if (extendingTypeDescriptor != null) {
				setValidator(extendingTypeDescriptor.getValidator());
			}
		}

		private CCombo comboBox;
		private String comboBoxItems[];
		
		public void dispose() {
			super.dispose();
		}
		
		protected Control createControl(Composite cell) {
			comboBox = new CCombo(cell, getStyle());
			comboBox.setFont(cell.getFont());

			comboBox.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					keyReleaseOccured(e);
				}
			});

			comboBox.addSelectionListener(new SelectionAdapter() {
				public void widgetDefaultSelected(SelectionEvent event) {
					applyEditorValueAndDeactivate();
				}
			});

			comboBox.addTraverseListener(new TraverseListener() {
				public void keyTraversed(TraverseEvent e) {
					if (e.detail == SWT.TRAVERSE_ESCAPE
							|| e.detail == SWT.TRAVERSE_RETURN) {
						e.doit = false;
					}
				}
			});

			comboBox.addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent e) {
					// If the dialog button is the new focus then don't trigger
					// the whole process
					// for applying the current editor value, as the cell editor
					// is still active.
					// Needed because UI Designer's PropertySheetEntry override
					// disposes of the active cell editor
					// whenever a value is applied.
					// if (!dialogButton.isFocusControl()) {
					// RGBCellEditor.this.focusLost();
					// }
				}
			});

			comboBox.addModifyListener(new ModifyListener() {

				public void modifyText(ModifyEvent e) {
					String newText = currentComboBoxText();
		        	boolean wasValid = isValueValid();
		        	Object newValue = null;
		        	if (extendingTypeDescriptor != null)
		        		newValue = extendingTypeDescriptor.valueOf(newText);
		        	// check the incoming text if not convertible so meaningful error results
		        	if (newValue == null)
		        		newValue = newText;
					boolean isValid = isCorrect(newValue);
					if (!isValid) {
			            // try to insert the current value into the error message.
			            setErrorMessage(MessageFormat.format(getErrorMessage(),
			                    new Object[] { newText }));
			        }
					valueChanged(wasValid, isValid);
				}
				
			});
			return comboBox;
		}
		
		@Override
		protected void doSetFocus() {
			// override this because otherwise,
			// editor tries to set the value and keeps combo from activating!
			comboBox.setFocus();
		}

		private void populateComboBoxItems() {
			Collection values = builtins;
			
			if (values != null) {
				values = new ArrayList(values);
			} else {
				values = new ArrayList();
			}
			if (extendingTypeDescriptor != null) {
				values.addAll(extendingTypeDescriptor.getChoiceOfValues());
			}
			
			comboBoxItems = new String[values.size()];
			int i = 0;
			for (Object obj : values) {
				comboBoxItems[i] = extendingTypeDescriptor.toDisplayString(obj);
				comboBox.add(comboBoxItems[i]);
				i++;
			}
	    }
		
		protected void updateContents(Object object) {
			if (comboBoxItems == null) {
				populateComboBoxItems();
			}

			String text = null;
			if (extendingTypeDescriptor != null) {
				text = extendingTypeDescriptor.toDisplayString(object);
			}
			if (text == null && object != null) {
				text = object.toString();
			}
			if (text == null) {
				text = "";
			}
			comboBox.setText(text);
		}
		
		String comboBoxSelectionToText(int selectionIndex) {
			String result = null;
			if (selectionIndex >= 0 && selectionIndex < comboBoxItems.length) {
				result = comboBoxItems[selectionIndex];
			}
			return result;
		}
		
		String currentComboBoxText() {
			String result = null;
			result = comboBoxSelectionToText(comboBox.getSelectionIndex());
			if (result == null) {
				result = comboBox.getText();
			}
			return result;
		}
		
		void applyEditorValueAndDeactivate() {
			String newText = currentComboBoxText();
			Object newValue = extendingTypeDescriptor.valueOf(newText);
	
			doSetValue(newValue);
			markDirty();
			boolean isValid = isCorrect(newValue);
			setValueValid(isValid);
			fireApplyEditorValue();
			deactivate();
		}

	    /*
	     *  (non-Javadoc)
	     * @see org.eclipse.jface.viewers.CellEditor#focusLost()
	     */
	    protected void focusLost() {
	        if (isActivated()) {
	            applyEditorValueAndDeactivate();
	        }
	    }

	    /*
	     *  (non-Javadoc)
	     * @see org.eclipse.jface.viewers.CellEditor#keyReleaseOccured(org.eclipse.swt.events.KeyEvent)
	     */
	    protected void keyReleaseOccured(KeyEvent keyEvent) {
	        if (keyEvent.character == '\u001b') { // Escape character
	            fireCancelEditor();
	        } else if (keyEvent.character == '\t') { // tab key
	            applyEditorValueAndDeactivate();
	        }
	    }

		@Override
		protected Object doGetValue() {
			Object value;
			if (extendingTypeDescriptor != null)
				value = extendingTypeDescriptor.valueOf(currentComboBoxText());
			else
				value = currentComboBoxText();
			return value;
		}

		@Override
		protected void doSetValue(Object value) {
			updateContents(value);
		}
	}

	public static class BooleanCellEditor extends ModelObjectComboBoxCellEditor {

		private static List valueList;
		
		public BooleanCellEditor(Composite composite) {
			super(composite, getValueList(), getLabelProvider());
			//super(composite, new String[] { Boolean.FALSE.toString(), Boolean.TRUE.toString() });
			/* turn off code to swap values on click, since it is
			 * not good UI (we don't know if the user clicked to
			 * focus or clicked to swap the value)
			this.getControl().addMouseListener(new MouseAdapter() {
				public void mouseDoubleClick(MouseEvent e) {
					Object value = getValue();
					Object newValue;
					if (Boolean.TRUE.equals(value)) 
						newValue = Boolean.FALSE;
					else 
						newValue = Boolean.TRUE;
					setValue(newValue);
				}
			});
			*/
		}

		private static List getValueList() {
			if (valueList == null) {
				valueList = new ArrayList();
				valueList.add(Boolean.FALSE);
				valueList.add(Boolean.TRUE);
			}
			return valueList;
		}
		
		private static ILabelProvider getLabelProvider() {
			ILabelProvider result = new LabelProvider() {
				
			};
			return result;
		}
	}
	
	/**
	 * Cell editor for editing string properties that escapes text
	 * for editing.
	 */
	public static class StringCellEditor extends TextCellEditor
	{
		public StringCellEditor(Composite composite, int style)
		{
			super(composite, style);
		}
		
		public Object doGetValue() {
			String result = (String) super.doGetValue();
			if (result != null) {
				result = TextUtils.unescape(result);
			}
			return result;
		}
		
		public void doSetValue(Object value) {
			if (value instanceof String) {
				value = TextUtils.escape(value.toString(), QUOTE_CHAR);
			}
			super.doSetValue(value);
		}
	}

	public static class IntegerCellEditor extends TextCellEditor
	  {
		public IntegerCellEditor(Composite composite,
				IntegerTypeDescriptor typeDescriptor, int style) {
			super(composite, style);
			setValidator(typeDescriptor.getValidator());
		}

		public Object doGetValue() {
			try {
				return new Integer(Integer.parseInt((String) super.doGetValue()));
			} catch (NumberFormatException e) {
			}
			return new Integer(Integer.MAX_VALUE);
		}

		public void doSetValue(Object value) {
			super.doSetValue(value != null ? value.toString() : "");
		}
	}

	public static class FloatCellEditor extends TextCellEditor
	  {
		private final FloatTypeDescriptor typeDescriptor;

		public FloatCellEditor(Composite composite,
				FloatTypeDescriptor typeDescriptor, int style) {
			super(composite, style);
			this.typeDescriptor = typeDescriptor;
			setValidator(typeDescriptor.getValidator());
		}

		public Object doGetValue() {
			return new Double(Double.parseDouble((String) super.doGetValue()));
		}

		public void doSetValue(Object value) {
			super.doSetValue(typeDescriptor.toDisplayString(value));
		}
	}
	
	/**
	 * Get the type descriptor underlying a property descriptor.
	 * 
	 * @param propertyDescriptor
	 * @return ITypeDescriptor if known, else null
	 */
	public static ITypeDescriptor getTypeDescriptorForPropertyDescriptor(IPropertyDescriptor propertyDescriptor) {
    	if (propertyDescriptor instanceof AbstractPropertyDescriptor) {
    		return ((AbstractPropertyDescriptor) propertyDescriptor).getTypeDescriptor();
    	} else if (propertyDescriptor instanceof ArrayPropertySource.ElementPropertyDescriptor) {
    		return ((ArrayPropertySource.ElementPropertyDescriptor) propertyDescriptor).getTypeDescriptor();
    	} else {
    		return null;
    	}
	}
	
	public static Collection<String> getEnumStrings(
			EnumPropertyTypeDescriptor extendedEnums) {
		Collection choiceOfValues = extendedEnums.getChoiceOfValues();
		Collection<String> strings = new ArrayList<String>(choiceOfValues.size());
		for (Object obj : choiceOfValues) {
			strings.add(obj.toString());
		}
		return strings;
	}

	/**
	 * Get the qualified name of the type.
	 * @param typeDescriptor the type descriptor
	 * @return qualified name or null for unknown or fundamental type
	 */
	public static String getTypeDescriptorId(ITypeDescriptor typeDescriptor) {
		if (typeDescriptor instanceof CompoundPropertyTypeDescriptor) {
			return ((CompoundPropertyTypeDescriptor) typeDescriptor).getCompoundPropertyDeclarationType().getQualifiedName();
		} else if (typeDescriptor instanceof EnumPropertyTypeDescriptor) {
			return ((EnumPropertyTypeDescriptor) typeDescriptor).getEnumPropertyDeclarationType().getQualifiedName();
		} else {
			return null;
		}
	}
	
	/**
	 * Get the &lt;sourceTypeMapping&gt; associated with a type descriptor.
	 * @param typeDescriptor type descriptor for compound or enum property
	 * @return {@link SourceTypeMappingType} or null if unknown or not compound or enum
	 */
	public static SourceTypeMappingType getSourceTypeMappingTypeFromTypeDescriptor(
			ITypeDescriptor typeDescriptor) {
		SourceTypeMappingType sourceTypeMappingType;
		if (typeDescriptor instanceof CompoundPropertyTypeDescriptor) {
			sourceTypeMappingType = ((CompoundPropertyTypeDescriptor) typeDescriptor).
				getCompoundPropertyDeclarationType().getSourceTypeMapping();
		} else if (typeDescriptor instanceof EnumPropertyTypeDescriptor) {
			sourceTypeMappingType = ((EnumPropertyTypeDescriptor) typeDescriptor).
				getEnumPropertyDeclarationType().getSourceTypeMapping();
		} else {
			sourceTypeMappingType = null;
		}
		return sourceTypeMappingType;
	}

}
