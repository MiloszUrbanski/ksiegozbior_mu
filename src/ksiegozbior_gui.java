import javax.swing.*;
import java.awt.*;

public class ksiegozbior_gui extends JFrame {

    private ksiazkiDB dao;
    private KsiazkiTableModel tableModel;
    private JTable ksiazkiTable;
    private JTextField tytulField = new JTextField(20);
    private JTextField autorField = new JTextField(20);
    private JTextField rokWydaniaField = new JTextField(10);
    private JButton dodajBtn = new JButton("Dodaj");
    private JButton usunBtn = new JButton("Usuń");
    private JButton aktualizujBtn = new JButton("Aktualizuj");

    public ksiegozbior_gui() {
        super("Księgozbiór - Aplikacja CRUD (JDBC & Swing)");
        dao = new ksiazkiDB();
        tableModel = new KsiazkiTableModel(dao.getAllKsiazki());

        setupGUI();
        setupListeners();

        refreshTable();
    }

    private void setupGUI() {
        setLayout(new BorderLayout(10, 10));

        ksiazkiTable = new JTable(tableModel);
        ksiazkiTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ksiazkiTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && ksiazkiTable.getSelectedRow() != -1) {
                int selectedRow = ksiazkiTable.getSelectedRow();
                Ksiazka selectedBook = tableModel.getKsiazkaAt(selectedRow);
                tytulField.setText(selectedBook.getTytul());
                autorField.setText(selectedBook.getAutor());
                rokWydaniaField.setText(String.valueOf(selectedBook.getRokWydania()));
            }
        });

        add(new JScrollPane(ksiazkiTable), BorderLayout.CENTER);


        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(new JLabel("Tytuł:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; inputPanel.add(tytulField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; inputPanel.add(new JLabel("Autor:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; inputPanel.add(autorField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; inputPanel.add(new JLabel("Rok Wydania:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; inputPanel.add(rokWydaniaField, gbc);

        add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(dodajBtn);
        buttonPanel.add(usunBtn);
        buttonPanel.add(aktualizujBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupListeners() {
        dodajBtn.addActionListener(e -> {
            try {
                String tytul = tytulField.getText();
                String autor = autorField.getText();
                int rokWydania = Integer.parseInt(rokWydaniaField.getText());

                if (tytul.isEmpty() || autor.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Tytuł i Autor nie mogą być puste", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Ksiazka nowaKsiazka = new Ksiazka(tytul, autor, rokWydania);
                if (dao.addKsiazka(nowaKsiazka)) {
                    refreshTable();
                    clearFields();
                    JOptionPane.showMessageDialog(this, "Książka dodana pomyślnie!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Rok Wydania musi być liczbą!", "Błąd Wprowadzania", JOptionPane.ERROR_MESSAGE);
            }
        });

        usunBtn.addActionListener(e -> {
            int selectedRow = ksiazkiTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Wybierz książkę do usunięcia!", "Błąd", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int idToDelete = tableModel.getKsiazkaAt(selectedRow).getId();
            int confirm = JOptionPane.showConfirmDialog(this, "Czy na pewno usunąć: " + tableModel.getKsiazkaAt(selectedRow).getTytul() + "?", "Potwierdź", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (dao.deleteKsiazka(idToDelete)) {
                    refreshTable();
                    clearFields();
                    JOptionPane.showMessageDialog(this, "Książka usunięta pomyślnie!");
                }
            }
        });

        aktualizujBtn.addActionListener(e -> {
            int selectedRow = ksiazkiTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Wybierz książkę do edycji!", "Błąd", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int idToUpdate = tableModel.getKsiazkaAt(selectedRow).getId();
                String tytul = tytulField.getText();
                String autor = autorField.getText();
                int rokWydania = Integer.parseInt(rokWydaniaField.getText());

                Ksiazka updatedBook = new Ksiazka(idToUpdate, tytul, autor, rokWydania);
                if (dao.updateKsiazka(updatedBook)) {
                    refreshTable();
                    clearFields();
                    JOptionPane.showMessageDialog(this, "Książka zaktualizowana pomyślnie!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Rok Wydania musi być liczbą!", "Błąd Wprowadzania", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    private void refreshTable() {
        tableModel.setData(dao.getAllKsiazki());
    }

    private void clearFields() {
        tytulField.setText("");
        autorField.setText("");
        rokWydaniaField.setText("");
        ksiazkiTable.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ksiegozbior_gui());
    }
}