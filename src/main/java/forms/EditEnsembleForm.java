package forms;

import dto.EnsembleDto;
import sql.EnsembleSql;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditEnsembleForm {
    private JTextField nameText;
    private JTextField typeText;
    private JButton saveButton;
    private JButton cancelButton;
    public JPanel panel;

    public EditEnsembleForm(JFrame jFrame, Integer id) {
        if (id != null) {
            EnsembleDto ensembleDto = EnsembleSql.get(id);
            if (ensembleDto != null) {
                nameText.setText(ensembleDto.getName());
                typeText.setText(ensembleDto.getType());
            }
        }

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (id != null) {
                    EnsembleDto ensembleDto = new EnsembleDto();
                    ensembleDto.setId(id);
                    ensembleDto.setType(typeText.getText());
                    ensembleDto.setName(nameText.getText());
                    EnsembleSql.update(ensembleDto);
                    jFrame.dispose();
                } else {
                    EnsembleDto ensembleDto = new EnsembleDto();
                    ensembleDto.setType(typeText.getText());
                    ensembleDto.setName(nameText.getText());
                    EnsembleSql.insert(ensembleDto);
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
}
