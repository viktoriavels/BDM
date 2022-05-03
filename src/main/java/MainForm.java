import dto.EnsembleDto;
import dto.MusicDto;
import dto.MusicianDto;
import forms.EditEnsembleForm;
import forms.EditMusicForm;
import forms.EditMusicianForm;
import sql.EnsembleSql;
import sql.MusicSql;
import sql.MusicianSql;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

public class MainForm {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JTable ensembleTable;
    private JButton ensembleSearchButton;
    private JTextField ensembleNameField;
    private JTextField ensembleTypeField;
    private JButton deleteEnsembleButton;
    private JButton modifyEnsembleButton;
    private JButton addEnsembleButton;
    private JScrollPane ensembleJScrollPane;
    private JButton musicSearchButton;
    private JTextField musicNameText;
    private JTextField musicExecutorText;
    private JButton musicDeleteButton;
    private JButton musicAddButton;
    private JButton musicUpdateButton;
    private JButton musicianSearchButton;
    private JTextField musicianNameText;
    private JButton musicianDeleteButton;
    private JButton musicianAddButton;
    private JButton musicianUpdateButton;
    private JTextField musicianGenreText;
    private JTextField musicianInstrumentText;
    private JTable musicianTable;
    private JTable musicTable;
    private static final int HEIGHT = 700;
    private static final int WIDTH = 850;

    public MainForm() {
        JFrame frame = new JFrame();
        frame.setContentPane(panel1);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        ensembleSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = ensembleNameField.getText();
                String type = ensembleTypeField.getText();
                List<EnsembleDto> search = EnsembleSql.search(name, type);
                DefaultTableModel defaultTableModel = new DefaultTableModel();
                defaultTableModel.addColumn("Id");
                defaultTableModel.addColumn("Название");
                defaultTableModel.addColumn("Жанр");
                if (search != null) {
                    for (EnsembleDto ensembleDto : search) {
                        defaultTableModel.addRow(new Object[]{ensembleDto.getId(), ensembleDto.getName(), ensembleDto.getType()});
                    }
                    ensembleTable.setModel(defaultTableModel);
                }
            }
        });
        addEnsembleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jFrame = new JFrame();
                jFrame.setContentPane(new EditEnsembleForm(jFrame, null).panel);
                jFrame.setSize(400, 300);
                jFrame.setVisible(true);

            }
        });
        deleteEnsembleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int i = ensembleTable.getSelectedRow();
                if (i >= 0) {
                    int id = (int) ensembleTable.getModel().getValueAt(i, 0);
                    EnsembleSql.delete(id);
                }
            }
        });
        modifyEnsembleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = ensembleTable.getSelectedRow();
                if (i >= 0) {
                    int id = (int) ensembleTable.getModel().getValueAt(i, 0);
                    JFrame jFrame = new JFrame();
                    jFrame.setContentPane(new EditEnsembleForm(jFrame, id).panel);
                    jFrame.setSize(400, 300);
                    jFrame.setVisible(true);

                }
            }
        });
        musicianSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = musicianNameText.getText();
                String genre = musicianGenreText.getText();
                String instrument = musicianInstrumentText.getText();
                List<MusicianDto> search = MusicianSql.search(name, genre, instrument);
                DefaultTableModel defaultTableModel = new DefaultTableModel();
                defaultTableModel.addColumn("Id");
                defaultTableModel.addColumn("Имя");
                defaultTableModel.addColumn("Жанр");
                defaultTableModel.addColumn("Инструмент");
                defaultTableModel.addColumn("Группа");
                defaultTableModel.addColumn("Роль");
                if (search != null) {
                    for (MusicianDto musicianDto : search) {
                        String ensembleName = "";
                        if (musicianDto.getEnsembleDto() != null) {
                            ensembleName = musicianDto.getEnsembleDto().getName();
                        }
                        defaultTableModel.addRow(new Object[]{musicianDto.getId(),
                                musicianDto.getName(), musicianDto.getGenre(),
                                musicianDto.getInstrument(), ensembleName, musicianDto.getRole()});
                    }
                    musicianTable.setModel(defaultTableModel);

                }
            }
        });
        musicianDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = musicianTable.getSelectedRow();
                if (i >= 0) {
                    int id = (int) musicianTable.getModel().getValueAt(i, 0);
                    MusicianSql.delete(id);
                }
            }
        });
        musicianAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jFrame = new JFrame();
                jFrame.setContentPane(new EditMusicianForm(jFrame, null).panel);
                jFrame.setSize(400, 300);
                jFrame.setVisible(true);
            }
        });
        musicianUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = musicianTable.getSelectedRow();
                if (i >= 0) {
                    int id = (int) musicianTable.getModel().getValueAt(i, 0);
                    JFrame jFrame = new JFrame();
                    jFrame.setContentPane(new EditMusicianForm(jFrame, id).panel);
                    jFrame.setSize(400, 300);
                    jFrame.setVisible(true);

                }
            }

        });

        musicDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = musicTable.getSelectedRow();
                if (i >= 0) {
                    int id = (int) musicTable.getModel().getValueAt(i, 0);
                    MusicSql.delete(id);
                }
            }
        });
        musicAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame jFrame = new JFrame();
                jFrame.setContentPane(new EditMusicForm(jFrame, null).panel);
                jFrame.setSize(400, 300);
                jFrame.setVisible(true);
            }
        });
        musicUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = musicTable.getSelectedRow();
                if (i >= 0) {
                    int id = (int) musicTable.getModel().getValueAt(i, 0);
                    JFrame jFrame = new JFrame();
                    jFrame.setContentPane(new EditMusicForm(jFrame, id).panel);
                    jFrame.setSize(400, 300);
                    jFrame.setVisible(true);

                }

            }
        });
        musicSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = musicNameText.getText();
                String executorName = musicExecutorText.getText();

                List<MusicDto> search = MusicSql.search(name, executorName);
                DefaultTableModel defaultTableModel = new DefaultTableModel();
                defaultTableModel.addColumn("Id");
                defaultTableModel.addColumn("Название");
                defaultTableModel.addColumn("Исполнитель");
                defaultTableModel.addColumn("Исполнение");

                if (search != null) {
                    for (MusicDto musicDto : search) {
                        String exrName = "";
                        if (musicDto.getMusicianDto() != null) {
                            exrName = musicDto.getMusicianDto().getName();
                        }
                        defaultTableModel.addRow(new Object[]{musicDto.getId(),
                                musicDto.getName(), exrName, musicDto.getPerformance()});
                    }
                }
                musicTable.setModel(defaultTableModel);


            }
        });
    }

    private void createUIComponents() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Id");
        columnNames.add("Название");
        columnNames.add("Жанр");
        ensembleTable = new JTable(new Vector<>(), columnNames);

        Vector<String> musicianColumns = new Vector<>();
        musicianColumns.add("Id");
        musicianColumns.add("Имя");
        musicianColumns.add("Жанр");
        musicianColumns.add("Инструмент");
        musicianColumns.add("Группа");
        musicianColumns.add("Роль");
        musicianTable = new JTable(new Vector<>(), musicianColumns);

        Vector<String> musicColumns = new Vector<>();
        musicColumns.add("id");
        musicColumns.add("Название");
        musicColumns.add("Исполнитель");
        musicColumns.add("Исполнение");
        musicTable = new JTable(new Vector<>(), musicColumns);
    }
}
