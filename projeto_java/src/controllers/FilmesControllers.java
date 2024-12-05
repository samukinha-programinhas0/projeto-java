package controllers;

import models.Filmes;
import repository.FilmesRepository;
import views.FilmesForm;
import views.FilmesTableView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FilmesControllers {
    private FilmesRepository repository;
    private FilmesTableView tableView;
    
    public FilmesControllers() {
        repository = new FilmesRepository();
        tableView = new FilmesTableView();
        inicializar();
    }

private void inicializar() {
        // Atualizar a tabela com os contatos existentes
        atualizarTabela();

        // Criar a barra de ferramentas (toolbar) com botões
        JToolBar toolBar = new JToolBar();
        JButton adicionarButton = new JButton("Adicionar");
        JButton editarButton = new JButton("Editar");
        JButton deletarButton = new JButton("Deletar");
        toolBar.add(adicionarButton);
        toolBar.add(editarButton);
        toolBar.add(deletarButton);

        tableView.add(toolBar, java.awt.BorderLayout.NORTH);

        // Ações dos botões
        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarFilme();
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarFilme();
            }
        });

        deletarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletarFilme();
            }
        });

        tableView.setVisible(true);
    }

private void atualizarTabela() {
        List<Filmes> Filmes = repository.obterTodosFilmes();
        tableView.atualizarTabela(Filmes);
    }

    private void adicionarFilme() {
        FilmesForm form = new FilmesForm (tableView, "Adicionar Filme");
        form.setVisible(true);
        Filmes novoFilme = form.getFilme();
        if (novoFilme != null) {
            repository.adicionarFilme (novoFilme);
            atualizarTabela();
        }
    }

    private void editarFilme() {
            int selectedId = tableView.getSelectedFilmeId();
            if (selectedId != -1) {
                Filmes Filme = repository.obterFilmePorId (selectedId);
                if (Filme != null) {
                    FilmesForm form = new FilmesForm (tableView, 
                        "Editar filme", Filme);
                    form.setVisible(true);
                    Filmes FilmeAtualizado = form.getFilme();
                    if (FilmeAtualizado != null) {
                        FilmeAtualizado = new Filmes(
                            selectedId,
                            FilmeAtualizado.getNome(),
                            FilmeAtualizado.getGenero(),
                            FilmeAtualizado.getDiretor(),
                            FilmeAtualizado.getFaixaEtaria(),
                            FilmeAtualizado.getData(),
                            FilmeAtualizado.getDisponivel()
                        );
                        repository.atualizarFilme (FilmeAtualizado);
                        atualizarTabela();
                    }
                } else {
                    JOptionPane.showMessageDialog(tableView,
                        "Filme não encontrado.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(tableView,
                    "Selecione um filme para editar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }

private void deletarFilme() {
        int selectedId = tableView.getSelectedFilmeId();
        if (selectedId != -1) {
            int confirm = JOptionPane.showConfirmDialog(
                tableView,
                "Tem certeza que deseja deletar este filme?",
                "Confirmar Deleção",
                JOptionPane. YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                repository.deletarFilme (selectedId);
                atualizarTabela();
            }
        } else {
            JOptionPane.showMessageDialog(
                tableView,
                "Selecione um filme para deletar.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    public void iniciar() {
        // Ações já são inicializadas no construtor
    }
}