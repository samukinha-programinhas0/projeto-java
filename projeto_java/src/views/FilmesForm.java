package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import models.Filmes;

public class FilmesForm extends JDialog {
    private JTextField nomeField;
    private JTextField generoField;
    private JTextField diretorField;
    private JSpinner dataSpinner;
    private JRadioButton disponivelSim;
    private JRadioButton disponivelNao;
    private ButtonGroup disponivelGroup;
    private JRadioButton faixaEtariaLivre;
    private JRadioButton faixaEtaria12;
    private JRadioButton faixaEtaria18;
    private ButtonGroup faixaEtariaGroup;
    private JButton salvarButton;
    private JButton cancelarButton;

    private Filmes Filme;
    private boolean isEditMode;

    public FilmesForm(Frame parent, String title) {
        super(parent, title, true);
        this.isEditMode = false;
        initializeComponents();
    }

    public FilmesForm(Frame parent, String title, Filmes Filme) {
        super(parent, title, true);
        this.Filme = Filme;
        this.isEditMode = true;
        initializeComponents();
        preencherCampos();
    }

    private void initializeComponents() {
        nomeField = new JTextField(20);
        generoField = new JTextField(20);
        diretorField = new JTextField(20);

        // Configuração para data
        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH);
        dataSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dataSpinner, "yyyy-MM-dd");
        dataSpinner.setEditor(dateEditor);

        // Botões de rádio para "Disponível"
        disponivelSim = new JRadioButton("Sim");
        disponivelNao = new JRadioButton("Não");
        disponivelGroup = new ButtonGroup();
        disponivelGroup.add(disponivelSim);
        disponivelGroup.add(disponivelNao);

        // Botões de rádio para "faixa etaria"
        faixaEtariaLivre = new JRadioButton("Livre");
        faixaEtaria12 = new JRadioButton("+12");
        faixaEtaria18 = new JRadioButton("+18");
        faixaEtariaGroup = new ButtonGroup();
        faixaEtariaGroup.add(faixaEtariaLivre);
        faixaEtariaGroup.add(faixaEtaria12);
        faixaEtariaGroup.add(faixaEtaria18);

        salvarButton = new JButton("Salvar");
        cancelarButton = new JButton("Cancelar");

        // Painel principal com BoxLayout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margem

        // Adicionando os componentes ao painel
        panel.add(createRow("Nome:", nomeField));
        panel.add(createRow("Gênero:", generoField));
        panel.add(createRow("Diretor:", diretorField));
        panel.add(createRow("Data:", dataSpinner));
        panel.add(createRow("Disponível:", disponivelSim, disponivelNao));
        panel.add(createRow("Faixa Etária:", faixaEtariaLivre, faixaEtaria12, faixaEtaria18));

        // Adicionando os botões em uma linha
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(salvarButton);
        buttonPanel.add(cancelarButton);
        panel.add(buttonPanel);

        // Configuração da janela
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(getParent());

        salvarButton.addActionListener((ActionEvent e) -> {
            if (validarCampos()) {
                if (isEditMode) {
                    atualizarFilme();
                } else {
                    adicionarFilme();
                }
                dispose();
            }
        });

        cancelarButton.addActionListener(e -> dispose());
    }

    private JPanel createRow(String labelText, JComponent... components) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        row.add(new JLabel(labelText));
        for (JComponent component : components) {
            row.add(component);
        }
        return row;
    }

    private void preencherCampos() {
        if (Filme != null) {
            nomeField.setText(Filme.getNome());
            generoField.setText(Filme.getGenero());
            diretorField.setText(Filme.getDiretor());
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = formatter.parse(Filme.getData());
                dataSpinner.setValue(date);
            } catch (Exception e) {
                dataSpinner.setValue(new Date()); 
            }
            if ("Sim".equalsIgnoreCase(Filme.getDisponivel())) {
                disponivelSim.setSelected(true);
            } else {
                disponivelNao.setSelected(true);
            }

            // Preencher faixa etária com base no valor definido
            String faixaEtaria = Filme.getFaixaEtaria();
            if ("Livre".equalsIgnoreCase(faixaEtaria)) {
                faixaEtariaLivre.setSelected(true);
            } else if ("+12".equalsIgnoreCase(faixaEtaria)) {
                faixaEtaria12.setSelected(true);
            } else if ("+18".equalsIgnoreCase(faixaEtaria)) {
                faixaEtaria18.setSelected(true);
            }
        }
    }

    private boolean validarCampos() {
        if (nomeField.getText().trim().isEmpty() || (!disponivelSim.isSelected() && !disponivelNao.isSelected())) {
            JOptionPane.showMessageDialog(
                this,
                "Nome e disponibilidade são obrigatórios.",
                "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!faixaEtariaLivre.isSelected() && !faixaEtaria12.isSelected() && !faixaEtaria18.isSelected()) {
            JOptionPane.showMessageDialog(
                this,
                "Faixa etária é obrigatória.",
                "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void adicionarFilme() {
        String disponivel = disponivelSim.isSelected() ? "Sim" : "Não";
        String faixaEtaria = faixaEtariaLivre.isSelected() ? "Livre" :
                             (faixaEtaria12.isSelected() ? "+12" : "+18");

        // Obtendo a data para o SQL
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dataFormatada = formatter.format(dataSpinner.getValue());

        Filme = new Filmes(
            nomeField.getText().trim(),
            generoField.getText().trim(),
            diretorField.getText().trim(),
            faixaEtaria,
            dataFormatada,
            disponivel
        );
    }

    private void atualizarFilme() {
        if (Filme != null) {
            Filme.setNome(nomeField.getText().trim());
            Filme.setGenero(generoField.getText().trim());
            Filme.setDiretor(diretorField.getText().trim());

            // Obtendo a faixa etária selecionada
            String faixaEtaria = faixaEtariaLivre.isSelected() ? "Livre" :
                                 (faixaEtaria12.isSelected() ? "+12" : "+18");
            Filme.setFaixaEtaria(faixaEtaria);

            // Obtendo a data no formato adequado para o SQL
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dataFormatada = formatter.format(dataSpinner.getValue());
            Filme.setData(dataFormatada);

            Filme.setDisponivel(disponivelSim.isSelected() ? "Sim" : "Não");
        }
    }

    public Filmes getFilme() {
        return Filme;
    }
}
