/**
 * Mascota.js
 *
 * @description :: A model definition represents a database table/collection.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {

    nombreMascota:{ //nombre del atributo
      type:'String',
      required:true,
      minLength:5,      
    },
    pesoMascota:{
      type:'String',
      required:true,            
      minLength:3
    },
    edadMascota:{
      type:'String',      
      minLength:3
    },
    razaMascota:{
      type:'String',                  
      minLength:3
    },
    especieMascota:{
      type:'String',
      required:true,            
      minLength:3
    },
    fechaNacimientoMascota:{
      type:'String',                  
      minLength:3
    },

    usuario:{             //Many to One en Pokemon.
      model:"usuario",    //referencia a usuario
      required:true       //opcional y depende de la logica de negocio
    },

    cita:{//one to many (plural)
      collection: "cita", //referenci al modelo
      via:"mascota" //Nombre fk en pokemon
    }


  },

};

