/**
 * 
 */
$(document).ready(function(){
	$("#peticion").val("cargaTablero");
	//Cada vez que se pulse en una celda
	$(".celda").on("click touchstart", function(){
		//Recojo el valor del id
		var id = $(this).attr("id");
		id = id.split("-")[1];
		$("#cargaTablero").load("./controlador?peticion=recargaTablero&columna="+id);
	});
});