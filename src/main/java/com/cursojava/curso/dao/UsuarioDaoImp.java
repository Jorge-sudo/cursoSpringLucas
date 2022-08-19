package com.cursojava.curso.dao;

import com.cursojava.curso.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository //funcionalidad de que podra acceder a la base de datos
@Transactional //forma con la cual tratara las consultas SQL en forma transaccion
public class UsuarioDaoImp implements UsuarioDao{

    @PersistenceContext
    private EntityManager entityManager ; //nos sirve para la conexion a la BD

    @Override
    public List<Usuario> getUsuarios() {
        String query = "SELECT u FROM Usuario u";
        List<Usuario> usuarios= entityManager.createQuery(query).getResultList();
        return usuarios;
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        entityManager.remove(usuario);
    }

    @Override
    public void registrar(Usuario usuario) {
        //agregamos
        entityManager.persist(usuario);
    }

    @Override
    public Usuario obtenerUsuarioPorCredenciales(Usuario usuario) {
        //En este query no podemos concatenar el usuario y password por que es vulnerable a hacker y daria true y tendria acceso
        //por los tanto utilizamos :email y :password
        String query = "SELECT u FROM Usuario u WHERE u.email = :email";
        List<Usuario> lista =  entityManager.createQuery(query).
                setParameter("email", usuario.getEmail())
                .getResultList();
        //si la lista esta vacia
        if (lista.isEmpty()){
            return null;
        }

        //obtenemos el primer elemento de la lista (0) y sacamos el password
        String passwordHashed = lista.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        //comparamos los passwords encriptados
        if(argon2.verify(passwordHashed, usuario.getPassword())){//retorna un boolean
            return lista.get(0);
        }
        return null;
    }
}
