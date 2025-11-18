import javax.swing.table.AbstractTableModel;
import java.util.List;

class KsiazkiTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID", "Tytuł", "Autor", "Rok Wydania"};
    private List<Ksiazka> data;

    public KsiazkiTableModel(List<Ksiazka> data) {
        this.data = data;
    }

    public void setData(List<Ksiazka> data) {
        this.data = data;
        fireTableDataChanged();
    }


    
    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }


    public Ksiazka getKsiazkaAt(int rowIndex) {
        return data.get(rowIndex);
    }

    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Ksiazka ksiazka = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> ksiazka.getId();
            case 1 -> ksiazka.getTytul();
            case 2 -> ksiazka.getAutor();
            case 3 -> ksiazka.getRokWydania();
            default -> null;
        };
    }
}
