/**
 * Usuario.js
 *
 * @description :: A model definition represents a database table/collection.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {

    cedula:{ //nombre del atributo
      type:'String',
      required:true,    
      unique:true,
      minLength:10,
      maxLength:15,
    },
    nombre:{
      type:'String',
      required:true,            
      minLength:3
    },
    apellido:{
      type:'String',
      required:true,            
      minLength:3
    },
    telefono:{
      type:'String',
      required:true,
      minLength:3
    },
    correo:{
      type:'String',
      isEmail:true
    },
    experiencia:{
      type:'String',                  
      minLength:3
    },
    contrasenia:{
      type:'String',
      required:true,
      regex:/^[a-zA-Z]\w{3,14}$/
    },
    usuario:{
      type:'String',                
      minLength:3
    },
    rol:{
      type:'String',
      isIn:['Veterinario','Cliente','Administrador'],
      defaultsTo:'Soltero'
    },

    Mascota:{//one to many (plural)
      collection: "mascota", //referenci al modelo
      via:"usuario" //Nombre fk en pokemon
    }

  },
};

