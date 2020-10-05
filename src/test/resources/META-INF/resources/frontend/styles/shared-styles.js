// eagerly import theme styles so as we can override them
import '@vaadin/vaadin-lumo-styles/all-imports';

const $_documentContainer = document.createElement('template');

$_documentContainer.innerHTML = `

<dom-module id="wizard" theme-for="vaadin-timeu-wizard">
  <template>
    <style>      
      :host(.wizardsConfig) timeu-wizard {
	    --timeu-wizard-active-color: red;
      }
      :host(.wizardsConfig) timeu-wizard {
        --timeu-wizard-step-font-family: vaadin;
      }
    </style>
  </template>
</dom-module>
`;

document.head.appendChild($_documentContainer.content);
