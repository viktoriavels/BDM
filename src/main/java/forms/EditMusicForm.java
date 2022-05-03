package forms;

import dto.MusicDto;
import dto.MusicianDto;
import sql.MusicSql;
import sql.MusicianSql;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EditMusicForm {
    public JPanel panel;
    private JTextField nameText;
    private JTextField performanceText;
    private JComboBox<MusicianDto> executorComboBox;
    private JButton saveButton;
    private JButton cancelButton;

    public EditMusicForm(JFrame jFrame, Integer id) {

        executorComboBox.setRenderer(new ItemRenderer());
        List<MusicianDto> musicianDtos = MusicianSql.search(null, null, null);
        if (musicianDtos != null) {
            for (MusicianDto n : musicianDtos) {
                executorComboBox.addItem(n);
            }
        }
        if (id != null) {
            MusicDto musicDto = MusicSql.get(id);
            if (musicDto != null) {
                nameText.setText(musicDto.getName());
                executorComboBox.setSelectedItem(musicDto.getMusicianDto());
                performanceText.setText(musicDto.getPerformance());
            }
        }

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (id != null) {
                    MusicDto musicDto = new MusicDto();
                    musicDto.setId(id);
                    musicDto.setName(nameText.getText());
                    musicDto.setMusicianDto((MusicianDto) executorComboBox.getSelectedItem());
                    musicDto.setPerformance(performanceText.getText());
                    MusicSql.update(musicDto);
                    jFrame.dispose();
                } else {
                    MusicDto musicDto = new MusicDto();
                    musicDto.setName(nameText.getText());
                    musicDto.setMusicianDto((MusicianDto) executorComboBox.getSelectedItem());
                    musicDto.setPerformance(performanceText.getText());
                    MusicSql.insert(musicDto);
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

    public static class ItemRenderer extends BasicComboBoxRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value != null) {
                MusicianDto item = (MusicianDto) value;
                setText(item.getName());
            }
            if (index == -1) {
                MusicianDto item = (MusicianDto) value;
                setText("" + item.getName());
            }
            return this;
        }
    }
}
