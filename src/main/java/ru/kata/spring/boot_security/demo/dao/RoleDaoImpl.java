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
    public Set<Role> getRolesConvert(String roleAdmin) {
        Set<Role> roles = new HashSet<>();
        if (roleAdmin.equals("ADMIN") || roleAdmin.matches(".*\\bADMIN\\b.*")) {
            roles.add(getRoleByName("ADMIN"));
        }
        if (roleAdmin.equals("USER") || roleAdmin.matches(".*\\bUSER\\b.*")) {
            roles.add(getRoleByName("USER"));
        }
        return roles;
    }
}