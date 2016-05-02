/**
 * 
 */


/* La pagina debe contener un div con id dialog y un link con id opener*/
$(function() {
    $( "#dialog" ).dialog({
      autoOpen: false,
      modal: true,
      show: {
          effect: "fade",
          duration: 500
        },
        hide: {
          effect: "fade",
          duration: 500
        },
      buttons: {
        Ok: function() {
          $( this ).dialog( "close" );
        	}
    	}
    });
 
    $( "#preference" ).click(function() {
      $( "#dialog" ).dialog( "open" );
    });
    
   
  });


$(function() {
    $( "#dialog-data" ).dialog({
      autoOpen: false,
      modal: true,
      show: {
          effect: "fade",
          duration: 500
        },
        hide: {
          effect: "fade",
          duration: 500
        },
      buttons: {
        Ok: function() {
          $( this ).dialog( "close" );
        	}
    	}
    });
    
    $( "#data" ).click(function() {
        $( "#dialog-data" ).dialog( "open" );
      });    
   
  });



$(function() {
    $( "#dialog-confirm" ).dialog({
      autoOpen: false,
      modal: true,
      show: {
          effect: "fade",
          duration: 500
        },
        hide: {
          effect: "fade",
          duration: 500
        },
      buttons: {
    	"Terminar y Avanzar": function() {
    		$( this ).dialog( "close" );
    		window.location.href="trainer?mode=continue";            
            },
        Terminar: function() {
            $( this ).dialog( "close" );
            window.location.href="trainer?mode=finish";
            
            },
        Cancelar: function() {
          $( this ).dialog( "close" );
        }
      }
    });
    
    $( "#closer" ).click(function() {
        $( "#dialog-confirm" ).dialog( "open" );
      });
    
  });

$(function() {
    $( "#fecha" ).datepicker({
    	  dateFormat: "yy-mm-dd"
    	});
  });

