===========PRACTICA GLOBAL PARA LA 2ª EVALUACIÓN===========
Esta practica presenta complejidad, por lo que se ha usado la IA para el desarrollo y la compresion de algunas partes.

Esta practica pide hacer una aplicacion para gestionar conciertos, artistas y entradas utilizado las bases de datos de Oracle y las
clases de java. Para ello primero tenermos que tener las tablas Artista Concierto y Entrada dentro de la base de datos, y por supuest
tener hecha la conexion usando JDBC. 

Despues vamos a diferencias dos tipos de clases basadas sobre estas tablas, primero las clases modelo (artista, concierto o entrada)
que solo son el molde de los datos de la informacion.Los atributos de dentro de las clases son privados para el encapsulamiento, para que 
nadie los pueda modificar directamente desde fuera de la clase.  Definen que es cada cosa, sus atrivutos y los getters y setter. Y las clases DAO
que son las que verdaderamente tratan la gestion de la base de datos. Dentro de estas clases de crean diferentes metodos para insetar, 
eliminar o listar y estos metodos son llamados desde la clase Main para ejecutarles. Dao significa Data Access Object.

La clase main es donde se muestra el menu de las opciones, es la clase que une todo, que recoge lo que se escribe por consola. Crea objetos
con esos datos y llama a las clases dao con estos datos para que los usen en sus sentencias sql. Para insetar se piden todos los datos de 
la tabla que se va a rellenar, para eliminar se pide el id y al lista simplemente se muestra la informacion de los datos antes insertados. 
Si alguna parte del programa no funcionase correctamente habria un catch capaz de manejar la excepcion generada.


