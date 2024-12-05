package repository;

import config.DbConection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Filmes;

public class FilmesRepository {

    // Criar um novo contato
    public void adicionarFilme (Filmes Filme) {
        String sql = "INSERT INTO filmes (Nome, Genero, Diretor, faixaEtaria, Data, Disponivel) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConection.getConnection();
            PreparedStatement stmt = conn.prepareStatement (sql)) {

            stmt.setString(1, Filme.getNome());
            stmt.setString(2, Filme.getGenero());
            stmt.setString(3, Filme.getDiretor());
            stmt.setString(4, Filme.getFaixaEtaria());
            stmt.setString(5, Filme.getData());
            stmt.setString(6, Filme.getDisponivel());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("filme adicionado com sucesso!");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao adicionar filme.");
            e.printStackTrace();
        }
    }

// Obter todos os contatos
    public List<Filmes> obterTodosFilmes() {
        List<Filmes> Filmes = new ArrayList<>();
        String sql = "SELECT * FROM filmes";
        
        try (Connection conn = DbConection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery (sql)) {
    
                while (rs.next()) {
                    Filmes Filme = new Filmes(
                        rs.getInt("ID"),
                        rs.getString("Nome"),
                        rs.getString("Genero"),
                        rs.getString("Diretor"),
                        rs.getString("faixaEtaria"),
                        rs.getString("Data"),
                        rs.getString("Disponivel")
                    );
                    Filmes.add(Filme);
                }

            } catch (SQLException e) {
                System.out.println("Erro ao obter filmes.");
                e.printStackTrace();
            }

            return Filmes;
        }
// Obter contato por ID
    public Filmes obterFilmePorId(int id) {
        String sql = "SELECT * FROM Filmes WHERE ID ?";
        Filmes Filme = null;

        try (Connection conn = DbConection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                Filme = new Filmes (
                    rs.getInt("ID"),
                    rs.getString("Nome"),
                    rs.getString("Genero"),
                    rs.getString("Diretor"),
                    rs.getString("faixaEtaria"),
                    rs.getString("Data"),
                    rs.getString("Disponivel")
                );
            }

        } catch (SQLException e) {
            System.out.println("Erro ao obter contato por ID.");
            e.printStackTrace();
        }

        return Filme;
    }

// Atualizar um contato
    public void atualizarFilme (Filmes Filme) {
        String sql =
            "UPDATE filmes SET Nome = ?, Genero = ?, Diretor = ? , faixaEtaria = ?, Data = ?, Disponivel = ? WHERE ID = ?";
    
        try (Connection conn = DbConection.getConnection();
            PreparedStatement stmt = conn.prepareStatement (sql)) {
    
            stmt.setString(1, Filme.getNome());
            stmt.setString(2, Filme.getGenero());
            stmt.setString(3, Filme.getDiretor());
            stmt.setString(4, Filme.getFaixaEtaria());
            stmt.setString(5, Filme.getData());
            stmt.setString(6, Filme.getDisponivel());
            stmt.setInt(7, Filme.getId());
    
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("filme atualizado com sucesso!");
            } else {
                System.out.println("filme não encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar filme.");
            e.printStackTrace();
        }
    }

// Deletar um contato
    public void deletarFilme (int id) {
        String sql = "DELETE FROM Filmes WHERE ID = ?";
    
        try (Connection conn = DbConection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, id);
            
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Filme deletado com sucesso!");
            } else {
                 System.out.println("Filme não encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao deletar Filme.");
            e.printStackTrace();
        }
    }
}