$(document).ready(function() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/cakes",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data) {
            var table='<table style="width:available"><tr><th>Name</th><th>Description</th><th>Image</th></tr>';

            $.each( data, function( index, item){
                table+='<tr><td>'+item.title+'</td><td>'+item.desc+'</td><td><img src=\"'+item.image+'\" width=\"250\"></td></tr>';
            });
            table+='</table>';

            $("body").append( table );
        },
        error: function (xhr, textStatus, errorThrown) {
            console.log(textStatus);
        }
    });

});