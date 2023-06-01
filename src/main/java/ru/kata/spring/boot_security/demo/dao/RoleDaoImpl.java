package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao {
    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

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
        return entityManager.createQuery("from Role where id =:id", Role.class).setParameter("id", id).getSingleResult();
    }

    @Override
    public Role getRoleByName(String name) {
        return entityManager.createQuery("from Role where name =:name", Role.class).setParameter("name", name).getSingleResult();
    }

    @Override
    public Set<Role> getRoles(String role) {
        Set<Role> roles = new HashSet<>();
        if (role.equals("ROLE_ADMIN") || role.matches(".*\\bROLE_ADMIN\\b.*")) {
            roles.add(getRoleByName("ROLE_ADMIN"));
        }
        if (role.equals("ROLE_USER") || role.matches(".*\\bROLE_USER\\b.*")) {
            roles.add(getRoleByName("ROLE_USER"));
        }
        return roles;
    }
}