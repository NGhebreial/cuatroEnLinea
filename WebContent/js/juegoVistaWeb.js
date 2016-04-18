/**
 * 
 */
$(document).ready(function(){
	$("#peticion").val("cargaTablero");
	//Hace la carga de la vista tablero
	$("#cargaTablero").load("./controlador?peticion=cargaTablero");
});