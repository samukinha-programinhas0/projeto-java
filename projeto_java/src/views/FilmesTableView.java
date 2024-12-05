package views;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.Filmes;

public class FilmesTableView extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public FilmesTableView() {
        super("Gerenciamento de filmes");
        initializeComponents();
    }

private void initializeComponents() {
        String[] columnNames = {"ID", "Nome", "genero", "diretor", "faixa Etaria", "data de lançamento", "disponivel"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable (tableModel);
        JScrollPane scrollPane = new JScrollPane (table);
    
        scrollPane.setBorder(
         BorderFactory.createEmptyBorder (10, 10, 10, 10));
    
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
    
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

public void atualizarTabela (List<Filmes> Filmes) {
        tableModel.setRowCount(0); // Limpa a tabela
        for (Filmes Filme : Filmes) {
            Object[] row = {
                Filme.getId(),
                Filme.getNome(),
                Filme.getGenero(),
                Filme.getDiretor(),
                Filme.getFaixaEtaria(),
                Filme.getData(),
                Filme.getDisponivel()
            };
            tableModel.addRow(row);
        }
    }

public int getSelectedFilmeId() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            return (int) tableModel.getValueAt(selectedRow, 0);
        }
         return -1;
    }
}