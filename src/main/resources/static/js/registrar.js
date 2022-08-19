// esto es una funcion de javaScrpt que tiene de seleccionar una tabla y agregarle paginacion y mas
$(document).ready(function() {
    // on ready
});

async function registrarUsuarios() {
    let datos = {};
    datos.nombre = document.getElementById('txtNombre').value ;
    datos.apellido = document.getElementById('txtApellido').value ;
    datos.email = document.getElementById('txtEmail').value ;
    datos.password = document.getElementById('txtPassword').value ;

    let repetirPassword = document.getElementById('txtRepetirPassword').value ;

    if(repetirPassword != datos.password){
        alert('La contraseña que escribiste es diferente.');
        return;
    }
    //con await espera el resultado, asi que se le agrega a la funcion que es asincronica async
    const request = await fetch('api/usuarios', {
        method: 'POST', //metodo GET
        headers: {//tipos que se aceptara
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body:await JSON.stringify(datos) //Esto lo que hace es convertir objeto de JavaScript y lo convierte a JSON
    });

    alert("La cuenta fue creada con Exito.");
    window.location.href = 'login.html';

}
