/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pertemuan_13;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Lenovo IP 330-14IKB
 */
public class TestJPA {
    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try {
            System.out.println("Mencoba koneksi ke database...");
            emf = Persistence.createEntityManagerFactory("Pertemuan_13PU");
            em = emf.createEntityManager();
            
            System.out.println("✅ Koneksi JPA dengan Hibernate berhasil!");
            
            // Test menggunakan named query dari entity class
            try {
                // Test named query "Login.findAll"
                long userCount = em.createNamedQuery("Login.findAll", Login.class)
                                  .getResultList()
                                  .size();
                System.out.println("Jumlah user dalam database: " + userCount);
                
                // Jika tidak ada data, buat sample data
                if (userCount == 0) {
                    System.out.println("Membuat sample user...");
                    em.getTransaction().begin();
                    
                    Login sampleUser = new Login("admin", "admin123");
                    em.persist(sampleUser);
                    
                    em.getTransaction().commit();
                    System.out.println("Sample user 'admin' berhasil dibuat!");
                } else {
                    // Tampilkan semua user
                    System.out.println("\nDaftar user:");
                    em.createNamedQuery("Login.findAll", Login.class)
                      .getResultList()
                      .forEach(user -> {
                          System.out.println("Username: " + user.getUsername() + ", Password: " + user.getPw());
                      });
                }
                
            } catch (Exception queryError) {
                System.out.println("Error saat query: " + queryError.getMessage());
                queryError.printStackTrace();
            }
            
        } catch (Exception e) {
            System.out.println("❌ Koneksi JPA gagal: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        }
    }
}
