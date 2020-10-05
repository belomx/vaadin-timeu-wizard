package org.vaadin.addon.wizard.ui;

import java.util.List;

import org.vaadin.addon.wizard.TimeuWizard;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@SuppressWarnings("serial")
@Route("")
@JsModule("./styles/shared-styles.js")
public class DemoView extends VerticalLayout
{
    private TimeuWizard wizard = null;
    private TimeuWizard wizard2 = null;
    private Label wizardValue = null;

    public DemoView()
    {
        Anchor sourceLink = new Anchor("https://github.com/belomx/vaadin-timeu-wizard", " (source code on GitHub)");
        sourceLink.setTarget("_blank");

        HorizontalLayout titleBox = new HorizontalLayout();
        titleBox.setAlignItems(Alignment.BASELINE);
        H3 title = new H3("Vaadin timeu wizard of Vaadin v17");
        titleBox.add(title, sourceLink);
        add(titleBox);

        wizardValue = new Label("Wizard value");

        Label label = new Label("");
        label.setWidth("25px");

        HorizontalLayout wizardLine = new HorizontalLayout();
        wizard = new TimeuWizard(List.of("Step 1","Step 2","Step3","Step4","Step5"), 1);
        wizard.addClassName("wizardsConfig");
        wizard.addValueChangeListener(e -> {
            Integer value = e.getValue();
            wizardValue.setText("Wizard value: "+value);
            });
        HorizontalLayout buttonsLine = new HorizontalLayout();
        Button previousButton = new Button("Previous");        
        previousButton.addClickListener(e -> wizard.previous());        
        Button nextButton = new Button("Next");
        nextButton.addClickListener(e -> wizard.next());
        buttonsLine.add(previousButton, nextButton);
        wizardLine.add(wizard, wizardValue);
        add(wizardLine);

        Label whiteline = new Label("");
        whiteline.setHeight("50px");
        add(whiteline, buttonsLine);
        
        wizard2 = new TimeuWizard(List.of("{\"label\":\"FIRST STEP\",\"content\":\"A\"}","{\"label\":\"CHOOSE INTEREST\",\"content\":\"B\"}","{\"label\":\"ADD FRIENDS\",\"content\":\"C\"}","{\"label\":\"VIEW MAP\",\"content\":\"D\"}"), 2, Boolean.TRUE);
        HorizontalLayout buttonsLine2 = new HorizontalLayout();
        Button previousButton2 = new Button("Previous");        
        previousButton2.addClickListener(e -> wizard2.previous());        
        Button nextButton2 = new Button("Next");
        nextButton2.addClickListener(e -> wizard2.next());
        buttonsLine2.add(previousButton2, nextButton2);
        add(wizard2, buttonsLine2);
    }
}
