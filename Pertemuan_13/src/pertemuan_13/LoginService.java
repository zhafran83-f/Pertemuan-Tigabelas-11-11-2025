/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pertemuan_13;

import javax.persistence.*;
import java.util.List;

/**
 *
 * @author Lenovo IP 330-14IKB
 */
public class LoginService {
    private EntityManagerFactory emf;

    public LoginService() {
        emf = Persistence.createEntityManagerFactory("Pertemuan_13PU");
    }

    // Cek apakah username sudah ada
    public boolean isUsernameExists(String username) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Login> users = em.createNamedQuery("Login.findByUsername", Login.class)
                                 .setParameter("username", username)
                                 .getResultList();
            return !users.isEmpty();
        } catch (Exception e) {
            System.out.println("Error checking username: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    // Buat user baru
    public boolean createUser(String username, String password) {
        if (isUsernameExists(username)) {
            System.out.println("Username '" + username + "' sudah ada!");
            return false;
        }

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        try {
            transaction.begin();
            Login newUser = new Login(username, password);
            em.persist(newUser);
            transaction.commit();
            System.out.println("User '" + username + "' berhasil dibuat!");
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error creating user: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    // Login user
    public boolean loginUser(String username, String password) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Login> users = em.createNamedQuery("Login.findByUsername", Login.class)
                                 .setParameter("username", username)
                                 .getResultList();
            
            if (!users.isEmpty()) {
                Login user = users.get(0);
                return password.equals(user.getPw());
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    // Update password
    public boolean updatePassword(String username, String newPassword) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        
        try {
            // Cari user terlebih dahulu
            List<Login> users = em.createNamedQuery("Login.findByUsername", Login.class)
                                 .setParameter("username", username)
                                 .getResultList();
            
            if (users.isEmpty()) {
                System.out.println("User '" + username + "' tidak ditemukan!");
                return false;
            }
            
            transaction.begin();
            Login user = users.get(0);
            user.setPw(newPassword);
            em.merge(user);
            transaction.commit();
            System.out.println("Password untuk user '" + username + "' berhasil diupdate!");
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error updating password: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    // Cari user by username - untuk fitur "CARI" di Ubah.java
    public Login findUserByUsername(String username) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Login> users = em.createNamedQuery("Login.findByUsername", Login.class)
                                 .setParameter("username", username)
                                 .getResultList();
            return users.isEmpty() ? null : users.get(0);
        } catch (Exception e) {
            System.out.println("Error finding user: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
