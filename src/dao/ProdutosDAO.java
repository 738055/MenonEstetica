/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import conexao.ConexaoBanco;
import java.util.ArrayList;
import java.util.List;
import model.CadastroProdutos;

public class ProdutosDAO {

    private Connection con = null;

    public ProdutosDAO() {
         ConexaoBanco conexaoBanco = new ConexaoBanco();
        if (conexaoBanco.conectar()) {
            con = conexaoBanco.getConnection();
            System.out.println("Conexão ok");
        } else {
            System.err.println("erro");
        }
    }

    public boolean inserir(CadastroProdutos produto) {
        

          
        String sql = "INSERT INTO CadastroProdutos (nome, preco, quantidade, descricao) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setFloat(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setString(4, produto.getDescricao());

            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro ao inserir: " + ex);
            return false;
        } finally {
            ConexaoBanco.closeConnection(con, stmt);
        }
    }

    public boolean atualizar(CadastroProdutos produto) {
        String sql = "UPDATE CadastroProdutos SET nome = ?, preco = ?, quantidade = ?, descricao = ? WHERE id = ?";
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setFloat(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setString(4, produto.getDescricao());
            stmt.setInt(5, produto.getId());

            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro ao atualizar: " + ex);
            return false;
        } finally {
            ConexaoBanco.closeConnection(con, stmt);
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM CadastroProdutos WHERE id = ?";
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("Erro ao deletar: " + ex);
            return false;
        } finally {
            ConexaoBanco.closeConnection(con, stmt);
        }
    }

    public List<CadastroProdutos> consultar() {
        String sql = "SELECT * FROM CadastroProdutos";

        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<CadastroProdutos> produtos = new ArrayList<>();

        try {
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                CadastroProdutos produto = new CadastroProdutos();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco(rs.getFloat("preco"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setDescricao(rs.getString("descricao"));
                produtos.add(produto);
            }

        } catch (SQLException ex) {

            System.err.println("Erro:" + ex);
        } finally {
            ConexaoBanco.closeConnection(con, stmt, rs);
        }

        return produtos;
    }
}
