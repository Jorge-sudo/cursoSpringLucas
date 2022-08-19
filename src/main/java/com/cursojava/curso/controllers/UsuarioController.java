package com.cursojava.curso.controllers;

//Los controllers simplemente es manejar nuestrar URL o Path

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    //Esto es la injeccion de dependencias hace que se cree un objeto y se guarde en esta variable
    @Autowired
    private UsuarioDao usuarioDao; //si esta variable se crea en otra clase se utilizara el mismo objeto
    //entonces quiere decir que se compartira el mismo objeto

    @Autowired
    private JWTUtil jwtUtil;


    @RequestMapping(value="api/usuarios/{id}", method = RequestMethod.GET)
    public Usuario getUsuario(@PathVariable Long id){
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre("Jorge");
        usuario.setApellido("Arone");
        usuario.setEmail("jorgearone567@gmail");
        usuario.setTelefono("63090689");
        return usuario ;
    }


    @RequestMapping(value="api/usuarios", method = RequestMethod.GET) //recibimos el token
    public List<Usuario> getUsuarios(@RequestHeader(value = "Authorization")String token){
        if(!validarToken(token)){
            return null;
        }
        return usuarioDao.getUsuarios();
    }

    private boolean validarToken (String token){
        String usuarioId = jwtUtil.getKey(token);
        return usuarioId != null;
    }

    @RequestMapping(value="api/usuarios", method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody Usuario usuario){//con requestBody convierte el JSON que recibe a un usuario
        // Utilizaremos una libreria para encriptar el password y asi guardarlo a la base de datos
        //A esto se le llama HASH
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        //Convertimos la contraseña a argon
        //hash(cantidadIteraciones,memoria, hilos de proceso, contraseña) mientras mas iteraciones mas seguro pero lento es
        String hash = argon2.hash(1, 1024, 1, usuario.getPassword());
        usuario.setPassword(hash);
        usuarioDao.registrar(usuario);
    }

    @RequestMapping(value="usuario1")
    public Usuario editar(){
        Usuario usuario = new Usuario();
        usuario.setNombre("Jorge");
        usuario.setApellido("Arone");
        usuario.setEmail("jorgearone567@gmail");
        usuario.setTelefono("63090689");
        return usuario ;
    }

    @RequestMapping(value="api/usuarios/{id}", method = RequestMethod.DELETE)
    public void eliminar(@RequestHeader(value = "Authorization")String token, @PathVariable Long id){
        if(!validarToken(token)){
            return ;
        }
        usuarioDao.eliminar(id);
    }
}
