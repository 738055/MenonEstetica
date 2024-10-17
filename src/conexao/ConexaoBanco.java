package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


public class ConexaoBanco {   
    private final String servidor;
    private final String banco;
    private final String usuario;
    private final String senha;
    private Connection conexao; 


    public ConexaoBanco() {
        this.servidor = "200.195.171.124";
        this.banco = "grupo02_ThomasWilliam_Lista";
        this.usuario = "grupo02";
        this.senha = "Y4NFvVi62nD30d0d";
    }

    
    public boolean conectar() {
        try {
            this.conexao = DriverManager.getConnection("jdbc:mysql://" + this.servidor + "/" + this.banco, this.usuario, this.senha);
            return true;
        } catch (SQLException ex) {
            System.err.println ("Erro: "+ex);
            return false;
        }
    }

   
    public Connection getConnection() {
        return conexao;
    }

    
    public static void closeConnection(Connection con) { 
        if (con != null) { 
            try {
                con.close();
            } catch (SQLException ex) {
                  System.err.println ("Erro: "+ex);
            }
        }
    }

    
    public static void closeConnection(Connection con, PreparedStatement stmt) { 
        if (stmt != null) { 
            try {
                stmt.close();
            } catch (SQLException ex) {
                  System.err.println("Erro: " + ex);
            }
        }
        closeConnection(con);
    }
    
    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) { 
        if (rs != null) { 
            try {
                rs.close();
            } catch (SQLException ex) {
                 System.err.println("Erro: " + ex);
            }
        }
        closeConnection(con, stmt);
    }
}
