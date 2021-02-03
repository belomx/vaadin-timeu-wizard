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
                left=[[left]]
                right=[[right]]
                clickablestep=[[clickablestep]]
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
                reflectToAttribute: true,
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
            },
            left: {
                type: Boolean,
                reflectToAttribute: true,
                value: false
            },
            right: {
                type: Boolean,
                reflectToAttribute: true,
                value: false
            },
            clickablestep: {
                type: Boolean,
                reflectToAttribute: true,
                value: false
            }
        }
    }

    constructor() {
        super();        
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

        let wizard = this.shadowRoot.querySelector('timeu-wizard');
        wizard.addEventListener('timeu-wizard-item-tap', this._handleItemTap);
    }

    _onChangeStep (newValue, oldValue) {
	    if (this.$server !== undefined && this.$server.onValueChanged !== undefined)
	        this.$server.onValueChanged();
    }

    /**
     * Increment the current step
     *
     * Use this function for moving to the next step in the wizard
     */
    increment() {
      this.shadowRoot.querySelector('timeu-wizard').increment();
    }

    /**
     * Decrement the current step
     *
     * Use this function for moving to the previous step in the wizard
     */
    decrement() {
      this.shadowRoot.querySelector('timeu-wizard').decrement();
    }

    updateConfig() {
    }

    _handleItemTap(e) {	
      this.dispatchEvent(new CustomEvent('vaadin-timeu-wizard-item-tap', { bubbles: true, composed: true, detail: {model: e.detail.model, target:e.detail.target} }));
    }

    ready() {
        super.ready();
    }    

}

customElements.define(VaadinTimeuWizard.is, VaadinTimeuWizard);
export {VaadinTimeuWizard};