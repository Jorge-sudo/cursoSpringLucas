// esto es una funcion de javaScrpt que tiene de seleccionar una tabla y agregarle paginacion y mas
$(document).ready(function() {
  //Cuando la pagina se inicia esto es lo primero que se carga
  cargarUsuarios();
  //Actualiza el Email del usuario
  $('#usuarios').DataTable();
    actualizarEmailDelUsuario();

});

function actualizarEmailDelUsuario(){
    document.getElementById('txt-email-usuario').outerHTML = localStorage.email;
}

async function cargarUsuarios() {
    //con await espera el resultado, asi que se le agrega a la funcion que es asincronica async
    const request = await fetch('api/usuarios', {
        method: 'GET', //metodo GET
        headers: getHeaders()
    });
    //la respuesta se esta convirtiendo en JSON
    const usuarios = await request.json();

    let listadoHtml = '';
    for(let usuario of usuarios){
        let botonEliminar = '<a href="#" onclick="eliminarUsuario('+usuario.id+')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';

        //Estamos consultando esta condicional donde preguntamos si es nulo entonces se agregara - sino el telefono
        let telefono = usuario.telefono == null ? '-' : usuario.telefono;

        let usuarioHtml = '<tr>\n' +
            '                  <td>'+usuario.id+'</td>\n' +
            '                  <td>'+usuario.nombre+' '+usuario.apellido+'</td>\n' +
            '                  <td>'+usuario.email+'</td>\n' +
            '                  <td>'+telefono+'</td>\n' +
            '                  <td>'+botonEliminar+'</td>\n' +
            '             </tr>';
        listadoHtml += usuarioHtml;
    }

    console.log(usuarios);

    document.querySelector('#usuarios tbody').outerHTML = listadoHtml;
}

function  getHeaders(){
    return {//tipos que se aceptara
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        //Enviamos el token
        'Authorization': localStorage.token
    };
}

async function eliminarUsuario(id) {
    //con confirm mostramos un cartel de pregunta si le click a si devuelve "true" sino "false"
    if(!confirm('¿Desea eliminar este usuario?')){
        return;
    }
    //con await espera el resultado, asi que se le agrega a la funcion que es asincronica async
    const request = await fetch('api/usuarios/' + id, {
        method: 'DELETE', //metodo DELETE
        headers: getHeaders()
    });
    //actualiza para que se visualice los cambios
    location.reload();
}
