package com.cursojava.curso.controllers;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    //Esto es la injeccion de dependencias hace que se cree un objeto y se guarde en esta variable
    @Autowired
    private UsuarioDao usuarioDao; //si esta variable se crea en otra clase se utilizara el mismo objeto
    //entonces quiere decir que se compartira el mismo objeto
    
    @Autowired //es necesario
    private JWTUtil jwtUtil;

    @RequestMapping(value="api/login", method = RequestMethod.POST)
    public String login(@RequestBody Usuario usuario){//con requestBody convierte el JSON que recibe a un usuario
        Usuario usuarioLogeado = usuarioDao.obtenerUsuarioPorCredenciales(usuario);
        if (usuarioLogeado != null){
            //create(idUsuario, email)
            String tokenJwt = jwtUtil.create(String.valueOf(usuarioLogeado.getId()), usuarioLogeado.getEmail());
            return tokenJwt;
        }
        return "FAIL";
    }

}
