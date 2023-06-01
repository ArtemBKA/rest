package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Role role) {
        entityManager.persist(role);
    }

    @Override
    public List<Role> roleList() {
        return entityManager.createQuery("from Role", Role.class).getResultList();
    }

    @Override
    public Role getRoleById(long id) {
        TypedQuery<Role> query = entityManager.createQuery("from Role where id =:id", Role.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public Role getRoleByName(String name) {
        TypedQuery<Role> query = entityManager.createQuery("from Role where name = :name", Role.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public Set<Role> getRoles(String role) {
        Set<Role> roles = new HashSet<>();
        if (role.matches(".*\\bROLE_ADMIN\\b.*")) {
            roles.add(getRoleByName("ROLE_ADMIN"));
        }
        if (role.matches(".*\\bROLE_USER\\b.*")) {
            roles.add(getRoleByName("ROLE_USER"));
        }
        return roles;
    }
}