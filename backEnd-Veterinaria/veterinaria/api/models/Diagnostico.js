/**
 * Diagnostico.js
 *
 * @description :: A model definition represents a database table/collection.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {

    fechaAtencionDiagnostico:{ //nombre del atributo
      type:'String',
      required:true,
      minLength:3,      
    },
    motivoConsultaDiagnostico:{
      type:'String',
      required:true,
      minLength:3
    },
    diagnosticoAtencion:{
      type:'String',      
      minLength:3
    },

    cita:{             //Many to One en Pokemon.
      model:"cita",    //referencia a usuario
      required:true       //opcional y depende de la logica de negocio
    },

  },
};

