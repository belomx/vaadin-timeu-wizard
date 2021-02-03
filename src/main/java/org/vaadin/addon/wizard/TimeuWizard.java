package org.vaadin.addon.wizard;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.EventData;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;

@Tag("vaadin-timeu-wizard")
@JsModule("./addon/wizard/timeu-wizard.js")
@NpmPackage(value = "@belomx/timeu-wizard", version = "^1.7.14")
public class TimeuWizard extends PolymerTemplate<TimeuWizard.WizardModel> implements HasStyle, HasValue<AbstractField.ComponentValueChangeEvent<TimeuWizard, Integer>, Integer>, HasSize {

    private static final long serialVersionUID = 1L;
    
    private final List<ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<TimeuWizard, Integer>>> listeners = new ArrayList<>();
    private int oldValue;
    
    /**
     * @param steps the list of the wizard
     * @param step the initial position of the wizard  
     * @param vertical true to make the wizard vertical and false to set it horizontal
     * @param labelDirection the label direction for vertical wizard
     * @param clickableStep, make the cursor pointer
     * @param ItemTapListener
     */
    public TimeuWizard (List<String> steps, int step, Boolean vertical, VerticalLabelDirection labelDirection, Boolean clickablestep, ComponentEventListener<WizardItemTapEvent> itemTapListener) {
        setSteps(steps);
        setValue(step);
        getModel().setVertical(vertical);
        if (Boolean.TRUE.equals(vertical))
        {
            if (VerticalLabelDirection.LEFT == labelDirection) {
                getModel().setLeft(Boolean.TRUE);
            }
            else if (VerticalLabelDirection.RIGHT == labelDirection) {
                getModel().setRight(Boolean.TRUE);
            }            
        }
        getModel().setClickablestep(clickablestep);
        if (itemTapListener != null) 
        {
            this.addItemTapListener(itemTapListener);
        }
        this.oldValue = step;
    }
    
    /**
     * @param steps the list of the wizard
     * @param step the initial position of the wizard  
     * @param clickableStep  make the cursor pointer
     * @param itemTapListener
     */
    public TimeuWizard (List<String> steps, int step, Boolean clickableStep, ComponentEventListener<WizardItemTapEvent> itemTapListener) {
        this(steps, step, Boolean.FALSE, VerticalLabelDirection.BOTTOM, clickableStep, itemTapListener);
    }
    
    /**
     * @param steps the list of the wizard
     * @param step the initial position of the wizard  
     * @param labelDirection the label direction for vertical wizard
     */
    public TimeuWizard(List<String> steps, int step, VerticalLabelDirection labelDirection) {
        this(steps, step, Boolean.TRUE, labelDirection, Boolean.FALSE, null);
    }

    /**
     * @param steps the list of the wizard
     * @param step the initial position of the wizard  
     * @param vertical true to make the wizard vertical and false to set it horizontal
     */
    public TimeuWizard(List<String> steps, int step, Boolean vertical) {
        this(steps, step, vertical, VerticalLabelDirection.BOTTOM, Boolean.FALSE, null);
    }
    
    /**
     * @param steps the list of the wizard
     */
    public TimeuWizard(List<String> steps) {
        this(steps, 1, Boolean.FALSE);
    }
    
    /**
     * @param steps the list of the wizard
     * @param step the initial position of the wizard  
     */
    public TimeuWizard(List<String> steps, int step) {
        this(steps, step, Boolean.FALSE);
    }
    
    /**
     * @param steps the list of the wizard  
     * @param vertical true to make the wizard vertical and false to set it horizontal
     */
    public TimeuWizard(List<String> steps, Boolean vertical) {
        this(steps, 1, vertical);
    }
    
    public boolean next ()
    {
        if (getValue() >= getSteps().size())
            return false;
        
        setValue(getValue()+1);
        return true;
    }
    
    public boolean previous ()
    {
        if (getValue() <= 1)
            return false;
        
        setValue(getValue()-1);
        return true;
    }

    @Override
    public Integer getValue() {
        return getModel().getStep();
    }

    @Override
    public void setValue(Integer value) {
        final WizardModel model = getModel();

        if (value > model.getSteps().size()) {
            throw new IllegalArgumentException("Step cannot be greater than the number of steps");
        }

        if (value <= 0) {
            throw new IllegalArgumentException("Step must be greater than 0");
        }

        model.setStep(value);
    }

    public int getStep() {
        return getValue();
    }    

    public void setStep(int step) {        
        setValue(step);
    }

    public List<String> getSteps() {
        return getModel().getSteps();
    }

    public void setSteps(List<String> steps) {
        getModel().setSteps(steps);
        getModel().setStep(1);
    }        

    @Override
    public Registration addValueChangeListener(ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<TimeuWizard, Integer>> listener) {
        requireNonNull(listener);

        listeners.add(listener);
        return () -> listeners.remove(listener);
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isRequiredIndicatorVisible() {
        return false;
    }

    @Override
    public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
        throw new UnsupportedOperationException();
    }
    
    public void addItemTapListener (ComponentEventListener<WizardItemTapEvent> consumerEvent) {
        addListener(WizardItemTapEvent.class, consumerEvent);
    }        

    @ClientCallable
    public void onValueChanged() {
        AbstractField.ComponentValueChangeEvent<TimeuWizard, Integer> event = new AbstractField.ComponentValueChangeEvent<>(this, this, oldValue, true);

        listeners.forEach(l -> l.valueChanged(event));

        this.oldValue = getValue();
    }
    
    @SuppressWarnings("serial")
    @com.vaadin.flow.component.DomEvent("vaadin-timeu-wizard-item-tap")
    public static class WizardItemTapEvent extends ComponentEvent<TimeuWizard> {

        /**
         * Creates a new event using the given source and indicator whether the
         * event originated from the client side or the server side.
         *
         * @param source     the source component
         * @param fromClient <code>true</code> if the event originated from the client
         * @param index      the index of the opened panel or null if the accordion is
         */
        private Integer itemsIndex;
        public WizardItemTapEvent(TimeuWizard source, boolean fromClient,
                                     @EventData("event.detail.model.__data.itemsIndex") Integer itemsIndex) {            
            super(source, fromClient);
            this.itemsIndex = itemsIndex + 1;
        }
        
        public Integer getItemsIndex () {
            return itemsIndex;
        }
        
    }
    
    public enum VerticalLabelDirection {
        BOTTOM, LEFT, RIGHT;
    }

    public interface WizardModel extends TemplateModel {
        
        int getStep();        
        void setStep(int step);
        
        List<String> getSteps ();
        void setSteps (List<String> steps);
        
        Boolean getVertical ();
        void setVertical (Boolean vertical);
        
        Boolean getLeft ();
        void setLeft (Boolean left);
        
        Boolean getRight ();
        void setRight (Boolean right);
        
        Boolean getClickablestep ();
        void setClickablestep (Boolean clickablestep);

    }
}
