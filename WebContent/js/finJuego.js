$( document ).click(function() {
  $( "#mensaje" ).effect( "pulsate", {times:500}, 30000 );

  $("#otraVez").on("click touchstart", function(){
	  window.location="./";
  });
});