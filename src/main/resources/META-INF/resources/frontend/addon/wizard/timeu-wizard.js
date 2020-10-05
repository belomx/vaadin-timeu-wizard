import '@polymer/polymer/polymer-element.js';
import '@belomx/timeu-wizard/timeu-wizard.js';
import '@vaadin/vaadin-icons'; 
import {html} from '@polymer/polymer/lib/utils/html-tag.js';
import {PolymerElement} from '@polymer/polymer';
import { ThemableMixin } from '@vaadin/vaadin-themable-mixin/vaadin-themable-mixin.js';
import { ElementMixin } from '@vaadin/vaadin-element-mixin/vaadin-element-mixin.js';

class VaadinTimeuWizard extends 
  ElementMixin(
        ThemableMixin(PolymerElement)) { 

    static get template() {
        return html`        
        <style include="timeu-wizard-shared-styles">
        /* polymer-cli linter breaks with empty line */          
        </style>
        <timeu-wizard
                class="wizard"                
                on-change="onValueChanged"
                steps=[[steps]]
                step={{step}}
                vertical=[[vertical]]
                >
        </paper-range-slider>     
    `;
    }

    // ::slotted(map)

    static get is() {
        return 'vaadin-timeu-wizard';
    }

    static get properties() {

        console.log("**** - At properties()");

        return {
            steps: {
                type: Array,
                value: function() {
                  return [];
                }
            },
            step: {
                type: Number,
                reflectToAttribute: true,
                notify: true,
                value: 1,
                observer: '_onChangeStep'
            },
            vertical: {
                type: Boolean,
                reflectToAttribute: true,
                value: false
            }
        }
    }

    connectedCallback () {
	    super.connectedCallback();
	    let i;
        let parsedSteps = [];
        let newValue = this.steps;
	    for (i = 0; i < newValue.length; i++)
        {
	        let jsonStep;
	        try {
                jsonStep = JSON.parse(newValue[i]);
            } catch (e) {
                jsonStep = newValue[i];
            }
            
            parsedSteps.push(jsonStep);
        }
		    
        this.steps = parsedSteps;
    }

    _onChangeStep (newValue, oldValue) {
	    this.$server.onValueChanged();
    }

    updateConfig() {
    }

    ready() {
        super.ready();
    }    

}

customElements.define(VaadinTimeuWizard.is, VaadinTimeuWizard);
export {VaadinTimeuWizard};