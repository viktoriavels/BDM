package forms;

import dto.EnsembleDto;
import dto.MusicianDto;
import sql.EnsembleSql;
import sql.MusicianSql;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EditMusicianForm {
    public JPanel panel;
    private JComboBox<EnsembleDto> ensembleText;
    private JTextField nameText;
    private JTextField instrumentText;
    private JTextField genreText;
    private JTextField roleText;
    private JButton saveButton;
    private JButton cancelButton;

    public EditMusicianForm(JFrame jFrame, Integer id) {
        ensembleText.setRenderer(new ItemRenderer());
        List<EnsembleDto> arrayList = EnsembleSql.search(null, null);
        if (arrayList != null) {
            for (EnsembleDto e : arrayList) {
                ensembleText.addItem(e);
            }
        }
        if (id != null) {
            MusicianDto musicianDto = MusicianSql.get(id);
            if (musicianDto != null) {
                nameText.setText(musicianDto.getName());
                genreText.setText(musicianDto.getGenre());
                roleText.setText(musicianDto.getRole());
                instrumentText.setText(musicianDto.getInstrument());
                ensembleText.setSelectedItem(musicianDto.getEnsembleDto());
            }
        }

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (id != null) {
                    MusicianDto musicianDto = new MusicianDto();
                    musicianDto.setEnsembleDto((EnsembleDto) ensembleText.getSelectedItem());
                    musicianDto.setRole(roleText.getText());
                    musicianDto.setName(nameText.getText());
                    musicianDto.setGenre(genreText.getText());
                    musicianDto.setInstrument(instrumentText.getText());
                    musicianDto.setId(id);
                    MusicianSql.update(musicianDto);
                    jFrame.dispose();
                } else {
                    MusicianDto musicianDto = new MusicianDto();
                    musicianDto.setEnsembleDto((EnsembleDto) ensembleText.getSelectedItem());
                    musicianDto.setRole(roleText.getText());
                    musicianDto.setName(nameText.getText());
                    musicianDto.setGenre(genreText.getText());
                    musicianDto.setInstrument(instrumentText.getText());
                    MusicianSql.insert(musicianDto);
                    jFrame.dispose();
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
            }
        });
    }

    public class ItemRenderer extends BasicComboBoxRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value != null) {
                EnsembleDto item = (EnsembleDto) value;
                setText(item.getName());
            }
            if (index == -1) {
                EnsembleDto item = (EnsembleDto) value;
                setText("" + item.getId());
            }
            return this;
        }
    }
}

