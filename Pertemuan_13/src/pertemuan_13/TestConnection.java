/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pertemuan_13;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Lenovo IP 330-14IKB
 */
public class TestConnection {
        public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/DB_PBO_Login_13_111125";
        String user = "postgres";
        String password = "ZayZiya03";
        
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Koneksi PostgreSQL berhasil!");
            conn.close();
        } catch (Exception e) {
            System.out.println("❌ Koneksi PostgreSQL gagal: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
