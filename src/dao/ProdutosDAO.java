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
import Model.CadastroProdutos;

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
               
        String sql = "INSERT INTO CadastroProdutos (NomeProduto,MarcaProduto,IdFornecedor, IdUnidadeMedida, ValorCompraProduto,ValorVendaProduto,IdTipoProduto,QuantidadeEstoque,DescriçaoProduto)"
                + "VALUES (?, ?, (select IdFornecedor from Fornecedor where NomeFornecedor = ?), (select IdUnidadeMedida from UnidadeMedida where DescricaoMedida=?), ?,?,(select IdTipoProduto from TipoProduto where DescricaoTipoProduto =?,?,?)";
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, produto.getNomeProduto());
            stmt.setString(2, produto.getMarcaProduto());
            stmt.setInt(3, produto.getIdFornecedor());
            stmt.setInt(4, produto.getIdUnidadeMedida());
            stmt.setFloat(5,produto.getValorCompra());
            stmt.setFloat(6,produto.getValorVenda());
            stmt.setInt(7,produto.getIdTipoProduto());
            stmt.setFloat(8,produto.getQuantidadeDisponivel());
            stmt.setString(9,produto.getDescricaoProduto());
            

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
        String sql = "UPDATE CadastroProdutos SET NomeProduto = ?, MarcaProduto = ?, IdFornecedor = ?, IdUnidadeMedida = ?, ValorCompraProduto=?, ValorVendaProduto = ?, IdTipoProduto = ?, QuantidadeEstoque = ?, DescriçaoProduto = ? WHERE IdProduto = ?";
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, produto.getNomeProduto());
            stmt.setString(2, produto.getMarcaProduto());
            stmt.setInt(3, produto.getIdFornecedor());
            stmt.setInt(4, produto.getIdUnidadeMedida());
            stmt.setFloat(5,produto.getValorCompra());
            stmt.setFloat(6,produto.getValorVenda());
            stmt.setInt(7,produto.getIdTipoProduto());
            stmt.setFloat(8,produto.getQuantidadeDisponivel());
            stmt.setString(9,produto.getDescricaoProduto());

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
        String sql = "DELETE FROM CadastroProdutos WHERE IdProduto = ?";
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
                produto.setIdProduto(rs.getInt("IdProduto"));
                produto.setNomeProduto(rs.getString("NomeProduto"));
                produto.setMarcaProduto(rs.getString("MarcaProduto"));
                produto.setIdFornecedor(rs.getInt("IdFornecedor"));
                produto.setIdUnidadeMedida (rs.getInt("IdUnidadeMedida"));
                produto.setValorCompra(rs.getInt("ValorCompraProduto"));
                produto.setValorVenda(rs.getInt("ValorVendaProduto"));
                produto.setQuantidadeDisponivel(rs.getInt("QuantidadeEstoque"));
                produto.setDescricaoProduto(rs.getString("DescriçaoProduto"));
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
