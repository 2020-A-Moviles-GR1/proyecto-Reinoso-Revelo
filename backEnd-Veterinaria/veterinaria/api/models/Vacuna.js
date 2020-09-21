/**
 * Vacuna.js
 *
 * @description :: A model definition represents a database table/collection.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {

    fechaVacuna:{ //nombre del atributo
      type:'String',
      minLength:3,
    },
    tipoVacuna:{
      type:'String',      
      minLength:3
    },
    numDosisVacuna:{
      type:'String',
      minLength:3
    },

    cita:{             //Many to One en Pokemon.
      model:"cita",    //referencia a usuario
      required:true       //opcional y depende de la logica de negocio
    },



  },
};

