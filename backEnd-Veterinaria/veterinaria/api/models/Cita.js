/**
 * Cita.js
 *
 * @description :: A model definition represents a database table/collection.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {

    fechaAtencionCita:{ //nombre del atributo
      type:'String',
      required:true,
      minLength:3,      
    },
    HoraAtencionCita:{
      type:'String',
      required:true,
      minLength:3
    },
    fechaProximaAtencionCita:{
      type:'String',      
      minLength:3
    },
    estadoAtencionCita:{
      type:'String',
      required:true,      
      isIn:['Atendido','Por Atender'],
    },

    mascota:{             //Many to One en Pokemon.
      model:"mascota",    //referencia a usuario
      required:true       //opcional y depende de la logica de negocio
    },

    vacuna:{
      collection: "vacuna", //referenci al modelo
      via:"cita" //Nombre fk en pokemon
    },

    diagnostico:{
      collection: "diagnostico", //referenci al modelo
      via:"cita" //Nombre fk en pokemon
    }

  },

};

