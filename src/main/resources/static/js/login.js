// esto es una funcion de javaScrpt que tiene de seleccionar una tabla y agregarle paginacion y mas
$(document).ready(function() {
    // on ready
});

async function iniciarSession() {
    let datos = {};
    datos.email = document.getElementById('txtEmail').value ;
    datos.password = document.getElementById('txtPassword').value ;

    //con await espera el resultado, asi que se le agrega a la funcion que es asincronica async
    const request = await fetch('api/login', {
        method: 'POST', //metodo GET
        headers: {//tipos que se aceptara
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body:await JSON.stringify(datos) //Esto lo que hace es convertir objeto de JavaScript y lo convierte a JSON
    });
    //la respuesta se esta convirtiendo en JSON
    const respuesta = await request.text();
    if(respuesta != 'FAIL'){
        localStorage.token = respuesta;
        localStorage.email = datos.email;
        window.location.href = 'usuarios.html';
    }else{
        alert('Las credenciales son incorrectas. Por Favor intente Nuevamente.');
    }

}
