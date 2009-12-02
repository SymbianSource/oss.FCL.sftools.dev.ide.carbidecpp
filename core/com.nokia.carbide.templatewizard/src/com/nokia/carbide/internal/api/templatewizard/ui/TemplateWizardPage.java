/*
* Copyright (c) 2006, 2008 Nokia Corporation and/or its subsidiary(-ies).
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


package com.nokia.carbide.internal.api.templatewizard.ui;

import com.nokia.carbide.internal.template.gen.Template.*;
import com.nokia.carbide.template.engine.ITemplate;
import com.nokia.carbide.templatewizard.Messages;
import com.nokia.carbide.templatewizard.TemplateWizardPlugin;
import com.nokia.cpp.internal.api.utils.core.*;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;

import java.text.MessageFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A wizard page constructed from a wizardPage template element.
 * <p>
 * When made visible, the page will immediately update the associated template's value map
 * from the persisted or default values.  Then, changes made in the UI will be added to the map
 * when the page is left.  The {@link #getPageValues()} implementation ensures that values
 * are initialized properly even if a page has not been navigated to (early Finish). 
 */
public class TemplateWizardPage extends org.eclipse.jface.wizard.WizardPage implements IWizardDataPage {

	/** This class is the model for a field, which holds values independent of the UI */
	static class FieldModel {
		private final BaseFieldType type;
		private final Map<String, Object> values;
		private final IWizardDataPage page;
		
		public FieldModel(BaseFieldType type, IWizardDataPage page, Map<String, Object> pageValues) {
			this.type = type;
			this.page = page;
			this.values = pageValues;
		}
		/** Get the field id */
		String getId() {
			return type.getId();
		}
		
		/** Get the base field type */
		BaseFieldType getType() {
			return type;
		}
		
		/**
		 * Initialize the field from the persisted values or the defaults.
		 */
		public void initialize(ITemplate template) {
			Object currentValue = template.getTemplateValues().get(type.getId());
			if (currentValue != null) {
				// use the persisted or current value
				setValue(currentValue.toString());
			} else {
				String defaultValue = null;
				if (type instanceof TextFieldType)
					defaultValue = ((TextFieldType) type).getDefault();
				else if (type instanceof FilenameFieldType)
					defaultValue = ((FilenameFieldType) type).getDefault();
				else if (type instanceof UidFieldType)
					defaultValue = null;	/* no default in XML; initialized directly in control */
					
				if (defaultValue != null) {
					// localize the string
					String value = template.getLocalizedString(defaultValue);
					
					// if the default value is a variable then try to replace it
					if (containsVariable(value)) {
						value = replaceVariables(value);
					}
					setValue(value);
				}
			}
		}

		/** Get the current value, for use in a text */
		public String getValue() {
			Object obj = values.get(type.getId());
			return (obj != null) ? obj.toString() : ""; //$NON-NLS-1$
		}
		
		/** Set the current value */
		public void setValue(String value) {
			values.put(type.getId(), value);
			
			if (type instanceof UidFieldType) {
				values.put(type.getId(), value.toLowerCase());
				// least hacky way to support this without creating a language out of templates
				values.put(type.getId() + UIDComposite.WITHOUT_0X_PREFIX, 
						UIDComposite.getWithout0x(value).toLowerCase());
			}

		}
		
		/**
		 * Replace variables in a default value setting by querying the variables
		 * available from previous pages.
		 * @param defaultValue
		 * @return
		 */
		protected String replaceVariables(String defaultValue) {
			VariableSubstitutionEngine engine = new VariableSubstitutionEngine(null, null);
			engine.setVariableToken('(');
			engine.allowRecursion(true);
			IVariableLookupCallback variableLookupCallback = new IVariableLookupCallback() {

				public Object getValue(String var) {
					// check all the pages (including the current one) for values
					IWizardPage wizardPage = page;
					while (wizardPage != null) {
						if (wizardPage instanceof IWizardDataPage) {
							Map<String, Object> otherPageValues = ((IWizardDataPage) wizardPage).getPageValues();
							if (otherPageValues.containsKey(var))
								return otherPageValues.get(var);
						}
						wizardPage = wizardPage.getPreviousPage();
					}
					return null;
				}
				
			};
			defaultValue = engine.substitute(variableLookupCallback, defaultValue);
			return defaultValue;
		}
		
		protected boolean containsVariable(String text) {
			return text != null && VARIABLE_PATTERN.matcher(text).find();
		}
		

	}
	
	
	
	class FieldValidator {
		
		protected BaseFieldType baseFieldType;
		protected Text text;
		
		public FieldValidator(BaseFieldType baseFieldType, Text text) {
			this.baseFieldType = baseFieldType;
			this.text = text;
			text.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					validatePage();
				}
			});
		}
		
		
		public String getValue() {
			Check.checkState(text != null);
			return text.getText();
		}
		
		public String getId() {
			Check.checkState(baseFieldType != null);
			return baseFieldType.getId();
		}
		
		/**
		 * @return error string, or null if validates
		 */
		public String validate() {
			Check.checkState(baseFieldType != null);
			if (baseFieldType.isMandatory()) {
				String value = getValue();
				if (value.length() == 0) {
					return MessageFormat.format(Messages.getString("TemplateWizardPage.MandatoryValueError"),  //$NON-NLS-1$
							new Object[] { template.getLocalizedString(baseFieldType.getLabel()) });
				}
			}
			
			return null;
		}
		
		/**
		 * @return warning string, or null if no warning
		 */
		public String warn() {
			return null;
		}
	}
	
	class PatternFieldValidator extends FieldValidator {
		private Pattern pattern;
		private String regex;
		
		public PatternFieldValidator(TextFieldType textFieldType, Text text, String regex) {
			super(textFieldType, text);
			this.regex = regex;
			if (regex != null)
				this.pattern = Pattern.compile(regex, 
						textFieldType.isMultiline() ? Pattern.MULTILINE : 0);
		}
		
		@Override
		public String validate() {
			String error = super.validate();
			if (error == null) {
				String value = getValue();
				if (value.length() > 0 && pattern != null) {
			        Matcher m = pattern.matcher(value);
					if (!m.matches()) {
						return MessageFormat.format(Messages.getString("TemplateWizardPage.PatternMatchError"),  //$NON-NLS-1$
													new Object[] { value, regex });
					}
				}
			}
			
			return error;
		}
	}
	
	class FilenameFieldValidator extends FieldValidator {
		
		public FilenameFieldValidator(FilenameFieldType filenameFieldType, Text text) {
			super(filenameFieldType, text);
		}

		@Override
		public String getValue() {
			return super.getValue().trim();
		}
		
		@Override
		public String validate() {
			String error = super.validate();
			if (error == null) {
				String value = getValue();
				IPath path = new Path(value);
				for (String segment : path.segments()) {
					if (!FileUtils.isValidCarbideProjectPathSegment(segment)) {
						return MessageFormat.format(Messages.getString("TemplateWizardPage.FilenameError"),  //$NON-NLS-1$
								new Object[] { segment });
					}
				}
			}
			
			return error;
		}
	}
	
	class UIDFieldValdator extends FieldValidator {
		
		private UIDComposite composite;

		public UIDFieldValdator(UidFieldType uidFieldType, Text text, UIDComposite composite) {
			super(uidFieldType, text);
			this.composite = composite;
		}
		
		@Override
		public String getValue() {
			String value = super.getValue().trim();
			if (!UIDComposite.isValidHexString(value))
				return value;
			return UIDComposite.makeCanonicalHexString(value);
		}
		
		@Override
		public String validate() {
			String error = super.validate();
			if (error == null) {
				String value = getValue();
				if (!UIDComposite.isValidHexString(value))
					return MessageFormat.format(Messages.getString("TemplateWizardPage.UIDHexError"),  //$NON-NLS-1$
							new Object[] { value });
			}
			
			return error;
		}
		
		@Override
		public String warn() {
			String value = getValue();
			String minText = composite.getMinText();
			String maxText = composite.getMaxText();
			if (value.length() > 0 && !composite.validateAppUIDText()) {
				return MessageFormat.format(Messages.getString("TemplateWizardPage.UIDWarning"),  //$NON-NLS-1$
											new Object[] { value, minText, maxText });
			}
			
			return super.warn();
		}
	}
	
	public static final String NAME_KEY = ".uid"; //$NON-NLS-1$
	private static final int SINGLELINE_STYLE = SWT.BORDER;
	private static final int MULTILINE_STYLE = SWT.WRAP | SWT.MULTI | SWT.V_SCROLL | SWT.BORDER;

	private WizardPageType wizardPageType;
	private ITemplate template;
	private List<FieldValidator> fieldValidators;
	private boolean pageHasBeenShown = false;
	private Map<String, FieldModel> modelMap;
	private Map<String, Text> controlMap;
	private ArrayList<FieldModel> fieldModels;
	private Map<String, Object> pageValues;
	
	private static final Pattern VARIABLE_PATTERN = Pattern.compile("(\\$\\(([^)]+)\\))"); //$NON-NLS-1$

	
	public TemplateWizardPage(IWizard wizard, ITemplate template, WizardPageType wizardPageType) {
		this("TemplateWizardPage"); //$NON-NLS-1$
		this.template = template;
		this.wizardPageType = wizardPageType;
		setTitle(template.getLocalizedString(wizardPageType.getLabel()));
		setDescription(template.getLocalizedString(
				TextUtils.catenateBrokenLines(wizardPageType.getDescription())));
		setWizard(wizard);
		
		pageValues = new HashMap<String, Object>();
		fieldModels = new ArrayList<FieldModel>();
		modelMap = new HashMap<String, FieldModel>();
		
		for (EObject obj : wizardPageType.eContents()) {
    		if (obj instanceof BaseFieldType) {
    			BaseFieldType type = (BaseFieldType) obj;
				FieldModel model = new FieldModel(type, this, pageValues);
				fieldModels.add(model);
    			modelMap.put(type.getId(), model); 
    		}
		}
		
		fieldValidators = new ArrayList<FieldValidator>();
		
		controlMap = new HashMap<String, Text>();
	}

	protected TemplateWizardPage(String pageName) {
		super(pageName);
	}

	/* (non-Javadoc)
	 * @see com.nokia.carbide.internal.api.templatewizard.ui.IWizardDataPage#getPageValues()
	 */
	public Map<String, Object> getPageValues() {
		// This method is typically called when finishing the wizard.
		// The template's value map already contains values established when
		// leaving other pages, but if pages were skipped entirely, this
		// handles them as well.
		
		if (!pageHasBeenShown) {
			pageHasBeenShown = true;
			initializeFieldValues();
		}
		
		finalizeFieldValues();

		return pageValues;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		Composite mainPageComposite = new Composite(parent, SWT.NULL);
		final GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.verticalSpacing = 10;
		mainPageComposite.setLayout(gridLayout);
    	for (FieldModel model : fieldModels) {
    		if (model.getType() instanceof TextFieldType) {
    			TextFieldType textField = (TextFieldType) model.getType();
   				createTextControl(mainPageComposite, textField, model);
    		}
    		else if (model.getType() instanceof UidFieldType) {
    			UidFieldType uidField = (UidFieldType) model.getType();
    			createUIDControl(mainPageComposite, uidField, model);
    		}
    		else if (model.getType() instanceof FilenameFieldType) {
    			FilenameFieldType filenameField = (FilenameFieldType) model.getType();
    			createFileControl(mainPageComposite, filenameField, model);
    		}
    		else
    			throw new IllegalStateException();
		}

    	mainPageComposite.setData(NAME_KEY, wizardPageType.getId());
        setControl(mainPageComposite);
        initializeFieldControls();
        validatePage();

		getControl().setData("WizardPage", this); //$NON-NLS-1$
        
		String helpContextId = TemplateWizardPlugin.ID + "." + wizardPageType.getId(); //$NON-NLS-1$
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), helpContextId);
	}
	
	private void createLabel(Composite container, BaseFieldType baseFieldType) {
		Label label = new Label(container, SWT.NONE);
		label.setText(template.getLocalizedString(baseFieldType.getLabel()));
	}
	
	private void setToolTipText(Control control, BaseFieldType baseFieldType) {
		String description = TextUtils.catenateBrokenLines(baseFieldType.getDescription());
		control.setToolTipText(template.getLocalizedString(description));
	}
	
	private void createTextControl(Composite container, TextFieldType textField, final FieldModel model) {
		createLabel(container, textField);

		int style = textField.isMultiline() ? MULTILINE_STYLE : SINGLELINE_STYLE;
		final Text text = new Text(container, style);
		GridData gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		text.setLayoutData(gridData);
		if (textField.isMultiline()) {
			gridData.heightHint = text.getLineHeight() * 4;
		}
		
		setToolTipText(text, textField);
		String id = textField.getId();
		text.setData(NAME_KEY, id);
		String pattern = textField.getPattern();
		fieldValidators.add(new PatternFieldValidator(textField, text, pattern));
		
		text.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				model.setValue(text.getText());
			}
			
		});
		controlMap.put(id, text);
	}
	
	private void createUIDControl(Composite container, UidFieldType uidField, final FieldModel model) {
		createLabel(container, uidField);
		long minValue = createLongVal(uidField.getMin());
		long maxValue = createLongVal(uidField.getMax());
		UIDComposite composite = new UIDComposite(container, model, minValue, maxValue);
		composite.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		setToolTipText(composite, uidField);
		String id = uidField.getId();
		final Text text = composite.getTextControl();
		text.setData(NAME_KEY, id);
		fieldValidators.add(new UIDFieldValdator(uidField, text, composite));
		
		text.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				model.setValue(text.getText());
			}
			
		});
		
		controlMap.put(id, text);
	}
	
	private static long createLongVal(String valStr) {
		long val = 0;
		Check.checkContract(valStr.startsWith(UIDComposite.HEX_PREFIX));
		try {
			val = Long.parseLong(valStr.substring(UIDComposite.HEX_PREFIX.length()), 16);
		}
		catch (NumberFormatException e) {
			Check.failedArg(e);
		}
		return val;
	}

	private void createFileControl(Composite container, FilenameFieldType filenameField, final FieldModel model) {
		createLabel(container, filenameField);

		final Text text = new Text(container, SINGLELINE_STYLE);
		text.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		
		setToolTipText(text, filenameField);
		String id = filenameField.getId();
		text.setData(NAME_KEY, id);
		fieldValidators.add(new FilenameFieldValidator(filenameField, text));
		
		text.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				model.setValue(text.getText());
			}
			
		});
		
		controlMap.put(id, text);
	}

	@Override
	public void setPageComplete(boolean complete) {
		super.setPageComplete(complete);
		if (complete)
			setErrorMessage(null);
	}

	@Override
	public void setWizard(IWizard newWizard) {
		super.setWizard(newWizard);
		setImageDescriptor(ImageDescriptor.createFromImage(newWizard.getDefaultPageImage()));
	}

	public void validatePage() {
		setErrorMessage(null);
		setMessage(null);
		setPageComplete(true);
		
		if (!pageHasBeenShown) {
			// don't validate data before it's been initialized, but don't initialize it here
			// yet, either
			return;
		}
		
		for (Iterator<FieldValidator> iter = fieldValidators.iterator(); iter.hasNext();) {
			FieldValidator checker = iter.next();
			String error = checker.validate();
			if (error != null) {
				setPageComplete(false);
				setErrorMessage(error);
				return;
			}
		}
		for (Iterator<FieldValidator> iter = fieldValidators.iterator(); iter.hasNext();) {
			FieldValidator checker = iter.next();
			String warning = checker.warn();
			if (warning != null) {
				setMessage(warning, IMessageProvider.WARNING);
				return;
			}
		}
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		
		if (!pageHasBeenShown && visible) {
			// replace and template variables the first time the page is displayed
			pageHasBeenShown = true;
			initializeFieldValues();
		}
	}

	/**
	 * On entering a page, set up the controls with default or persisted values
	 */
	protected void initializeFieldValues() {
		for (FieldModel model : fieldModels) {
			model.initialize(template);
		}
		
		// the page may or may not already have UI
		initializeFieldControls();
	}
	
	/**
	 * When the controls are first created, copy the current page values into them.
	 */
	protected void initializeFieldControls() {
		for (FieldModel model : fieldModels) {
			Text text = controlMap.get(model.getId());
			if (text != null) {
				String value = model.getValue();
				String current = text.getText();
				if (!current.equals(value)) {
					text.setText(value);
				}
			}
		}
	}
	
	/**
	 * On exiting a page, store the data into the template store.
	 */
	protected void finalizeFieldValues() {
		for (FieldModel model : fieldModels) {
			Text text = controlMap.get(model.getId());
			if (text != null) {
				String current = text.getText();
				model.setValue(current);
			}
		}
	}
	
	
}
