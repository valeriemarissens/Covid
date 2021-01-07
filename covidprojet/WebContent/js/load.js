/**
 * Created by TABBOU Nadir on 29/06/2017.
 */
$('#mdp1').keyup(function(){


 if($('#mdp').val()!==$('#mdp1').val()){
 $('#mdp1').css({
 'border-color':'red'
 });
 }

 else{
 $('#mdp1').css({
 'border-color':'green'
 })
 }

 })
$('#prenom').keyup(function(){


    if($('#prenom').val().length >= 5 ){
        $(this).css({
            'border-color':'green'
        })

    }else{
        $(this).css({
            'border-color':'red'
        })
    }

})
$('#nom').keyup(function(){


    if($('#nom').val().length >= 5 ){
        $(this).css({
            'border-color':'green'
        })

    }else{
        $(this).css({
            'border-color':'red'
        })
    }

})



$('#mdp').keyup(function(){


    var url='http://localhost/messagerie1/loginverif.php';
    var data={
        'val':$('#login').val()
    };


    //alert($(this).val());

    $.ajax({
        url:url,
        dataType:'text',
        type:'post',
        data:data,


        success:function(da){


            $('#ajx').html(da);
        },


//        error:function(){
//            alert('error');
//        }

    })



})