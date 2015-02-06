package hashchecker.mainwindow;

import java.awt.Component;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Oleksandr Bogovyk <obogovyk@gmail.com>
 */
public class MainWindow extends javax.swing.JFrame {
	
	private final static int MAX_LABEL_LENGTH = 50;

	private boolean isCalculating = false;
	private HashCalculator hashCalculator;
	HashInfo hashInfo = null;
    Component frame;


    /**
     * Creates new form NewJFrame
     */
    public MainWindow() {
        initComponents();
		
		List<Algorithm> list = Algorithm.getList();
		for(Algorithm a : list) {
			cmbAlgorithm.addItem(a);
		}
		
		clearData();
    }

	void calculateHash() {
		
		String filename = fieldFileName.getText();
		File file = new File( filename );
		if( !file.exists() ) {
			JOptionPane.showMessageDialog(frame, "Отсутствует файл " + filename, 
					"Ошибка", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		isCalculating = true;
		clearData();
		
		buttonBuffer.setEnabled(false);
		buttonCalculate.setEnabled(false);
		buttonClear.setEnabled(false);
		menuItemExport.setEnabled(false);
		menuItemOpenFile.setEnabled(false);
		
        Algorithm algorithm = (Algorithm)cmbAlgorithm.getSelectedItem();
		hashCalculator = new HashCalculator();
		hashCalculator.setMainWindow(this);
		hashCalculator.setFileName( filename );
		hashCalculator.setAlgorithm(algorithm);
		hashCalculator.addPropertyChangeListener( new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				String propertyName = evt.getPropertyName();
				if( propertyName.equals(HashCalculator.PROPERTY_PROGRESS_STRING) ) {
					String progressString = (String)evt.getNewValue();
					progressBar.setString( progressString );
				} else if( propertyName.equals(HashCalculator.PROPERTY_PROGRESS_VALUE) ) {
					int progress = (Integer)evt.getNewValue();
					progressBar.setValue(progress);
				}
			}
		});
		
		hashCalculator.execute();	
	}
	void hashCalculated() {
		try {
			hashInfo = hashCalculator.get();
			fieldHash.setText( hashInfo.getHash() );
			
			setLabelText(labelInfoFilename, hashInfo.getFilename());
			setLabelText(labelInfoFilesize, hashInfo.getFilesize());
			setLabelText(labelInfoAlgorithm, hashInfo.getAlgorithm());
			setLabelText(labelInfoHash, hashInfo.getHash());
		} catch (InterruptedException | ExecutionException ex) {
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(frame, "Возникла ошибка при расчете хэш-суммы ", 
					"Ошибка", JOptionPane.ERROR_MESSAGE);
			clearData();
		}
		buttonBuffer.setEnabled(true);
		buttonCalculate.setEnabled(true);
		buttonClear.setEnabled(true);
		menuItemExport.setEnabled(true);
		menuItemOpenFile.setEnabled(true);
		isCalculating = false;
		progressBar.setValue(0);
		progressBar.setString( "" );
	}
	
    private void openDirectoryFile() {
		if( isCalculating )
			return;
        int foresult = hashFileChooserOpen.showOpenDialog(this);
        if (foresult == JFileChooser.APPROVE_OPTION) {
			File file = hashFileChooserOpen.getSelectedFile();
			fieldFileName.setText(file.getAbsolutePath());
			fieldFileName.setCaretPosition(0);
		} else {
        System.out.println("File access cancelled by user.");
        }
    }
    
	private void clearData() {
        //fieldFileName.setText("");
		hashInfo = null;
        fieldHash.setText("");
		labelInfoAlgorithm.setText("");
		labelInfoFilename.setText("");
		labelInfoFilesize.setText("");
		labelInfoHash.setText("");
	}

	private void exportHashInfo() {
		if( hashInfo == null ) {
			JOptionPane.showMessageDialog(frame, "Отсутствует рассчитанная хэш-сумма ", 
					"Ошибка", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int response = hashFileChooserSave.showOpenDialog(this);
		if( response != JFileChooser.APPROVE_OPTION )
			return;
		File file = hashFileChooserSave.getSelectedFile();
		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write( hashInfo.createFileContent().getBytes() );
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(frame, "Возникла ошибка при экспорте информации ", 
					"Ошибка", JOptionPane.ERROR_MESSAGE);
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private void copyToBuffer() {
		fieldHash.selectAll();
		fieldHash.copy();
		fieldHash.select(0, 0);
	}
	
	private void setLabelText(JLabel label, String text) {
		if( text.length() > MAX_LABEL_LENGTH )
			text = text.substring(0, MAX_LABEL_LENGTH) + "...";
		label.setText(text);
	}
	
	// Netbeans generated methods
	
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        hashFileChooserOpen = new javax.swing.JFileChooser();
        hashFileChooserSave = new javax.swing.JFileChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        fieldFileName = new javax.swing.JTextField();
        cmbAlgorithm = new javax.swing.JComboBox<Algorithm>();
        fieldHash = new javax.swing.JTextField();
        buttonCalculate = new javax.swing.JButton();
        labelAlgorithm = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        labelOpen = new javax.swing.JLabel();
        buttonBuffer = new javax.swing.JButton();
        buttonClear = new javax.swing.JButton();
        progressBar = new javax.swing.JProgressBar();
        labelInfoFilesize = new javax.swing.JLabel();
        labelInfoAlgorithm = new javax.swing.JLabel();
        labelInfoHash = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        labelInfoFilename = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuItemOpenFile = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        menuItemExport = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menuItemExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

        hashFileChooserOpen.setApproveButtonText("Открыть");
        hashFileChooserOpen.setApproveButtonToolTipText("Открыть файл");
        hashFileChooserOpen.setDialogTitle("Открыть файл");
        hashFileChooserOpen.setToolTipText("");

        hashFileChooserSave.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Текстовый файл", "txt");
        hashFileChooserSave.addChoosableFileFilter(filter);
        hashFileChooserSave.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        hashFileChooserSave.setApproveButtonText("Сохранить");
        hashFileChooserSave.setApproveButtonToolTipText("Сохранить файл");
        hashFileChooserSave.setDialogTitle("Сохранить файл");
        hashFileChooserSave.setToolTipText("");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hash-checker Utility");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("hash-logo.png")));
        setResizable(false);

        jLabel1.setText("Файл:");

        jLabel2.setText("Алгоритм:");

        jLabel3.setText("Хэш-сумма:");

        fieldFileName.setToolTipText("");

        cmbAlgorithm.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbAlgorithmItemStateChanged(evt);
            }
        });

        fieldHash.setText("73f48840b60ab6da68b03acd322445ee");

        buttonCalculate.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        buttonCalculate.setText("Расчитать");
        buttonCalculate.setToolTipText("Расчитать значение");
        buttonCalculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCalculateActionPerformed(evt);
            }
        });

        labelAlgorithm.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelAlgorithm.setText("Message Digest 5");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Детально:");

        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("Размер файла:");

        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Алгоритм:");

        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Хэш-сумма:");

        labelOpen.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelOpen.setForeground(java.awt.Color.blue);
        labelOpen.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelOpen.setText("<html><u>Открыть...</u></html>");
        labelOpen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelOpen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelOpenMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labelOpenMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labelOpenMouseExited(evt);
            }
        });

        buttonBuffer.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        buttonBuffer.setText("Буфер");
        buttonBuffer.setToolTipText("Скопировать в буфер обмена");
        buttonBuffer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBufferActionPerformed(evt);
            }
        });

        buttonClear.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        buttonClear.setText("Очистить");
        buttonClear.setToolTipText("Очистить данные");
        buttonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonClearActionPerformed(evt);
            }
        });

        progressBar.setForeground(new java.awt.Color(51, 153, 0));
        progressBar.setToolTipText("");
        progressBar.setString("");
        progressBar.setStringPainted(true);
        progressBar.setVerifyInputWhenFocusTarget(false);

        labelInfoFilesize.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelInfoFilesize.setForeground(new java.awt.Color(102, 102, 102));
        labelInfoFilesize.setText("132,12 Mb");

        labelInfoAlgorithm.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelInfoAlgorithm.setForeground(new java.awt.Color(102, 102, 102));
        labelInfoAlgorithm.setText("MD5 (Message Digest 4)");

        labelInfoHash.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        labelInfoHash.setForeground(new java.awt.Color(102, 102, 102));
        labelInfoHash.setText("73f48840b60ab6da68b03acd322445ee");

        jLabel14.setForeground(new java.awt.Color(102, 102, 102));
        jLabel14.setText("Имя файла:");

        labelInfoFilename.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelInfoFilename.setForeground(new java.awt.Color(102, 102, 102));
        labelInfoFilename.setText("testfile.txt");

        jMenu1.setText("Файл");

        menuItemOpenFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        menuItemOpenFile.setText("Открыть");
        menuItemOpenFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemOpenFileActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemOpenFile);

        jMenu3.setText("Экспорт");

        menuItemExport.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuItemExport.setText("Текстовый файл (*.txt)");
        menuItemExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemExportActionPerformed(evt);
            }
        });
        jMenu3.add(menuItemExport);

        jMenu1.add(jMenu3);
        jMenu1.add(jSeparator3);

        menuItemExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        menuItemExit.setText("Выход");
        menuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemExitActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemExit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Справка");

        jMenuItem4.setText("Лицензия");
        jMenu2.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem5.setText("О программе");
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fieldHash, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(cmbAlgorithm, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelAlgorithm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(fieldFileName)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(labelOpen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonClear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonBuffer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonCalculate))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(jLabel14)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelInfoHash, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelInfoFilename, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelInfoFilesize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelInfoAlgorithm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 154, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {buttonBuffer, buttonCalculate, buttonClear});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(fieldFileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelOpen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbAlgorithm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelAlgorithm))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(fieldHash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCalculate)
                    .addComponent(buttonBuffer)
                    .addComponent(buttonClear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(labelInfoFilename))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(labelInfoFilesize))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(labelInfoAlgorithm))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(labelInfoHash))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {fieldHash, progressBar});

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

		
    private void buttonCalculateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCalculateActionPerformed
		calculateHash();
    }//GEN-LAST:event_buttonCalculateActionPerformed

    private void buttonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonClearActionPerformed
		clearData();
		fieldFileName.setText("");
    }//GEN-LAST:event_buttonClearActionPerformed

    private void labelOpenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelOpenMouseClicked
        openDirectoryFile();
    }//GEN-LAST:event_labelOpenMouseClicked

    private void menuItemOpenFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemOpenFileActionPerformed
        openDirectoryFile();
    }//GEN-LAST:event_menuItemOpenFileActionPerformed

    private void menuItemExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemExportActionPerformed
        exportHashInfo();
    }//GEN-LAST:event_menuItemExportActionPerformed

    private void labelOpenMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelOpenMouseEntered
        labelOpen.setText("<html>Открыть...</html>");
    }//GEN-LAST:event_labelOpenMouseEntered

    private void labelOpenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelOpenMouseExited
        labelOpen.setText("<html><u>Открыть...</u></html>");
    }//GEN-LAST:event_labelOpenMouseExited

    private void cmbAlgorithmItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbAlgorithmItemStateChanged
        Algorithm algorithm = (Algorithm)cmbAlgorithm.getSelectedItem();
		if( algorithm == null ) {
			labelAlgorithm.setText("");
		} else {
			labelAlgorithm.setText(algorithm.getDescription());
		}
    }//GEN-LAST:event_cmbAlgorithmItemStateChanged

    private void menuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemExitActionPerformed
        Object[] options = {"Да", "Отмена"};
        int status = JOptionPane.showOptionDialog(frame, 
                "Действительно выйти из программы?",
                "Выход",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
                );
        System.out.println(status);
        if (JOptionPane.NO_OPTION != status) {
            System.exit(0);
        }
    }//GEN-LAST:event_menuItemExitActionPerformed

    private void buttonBufferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBufferActionPerformed
        copyToBuffer();
    }//GEN-LAST:event_buttonBufferActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonBuffer;
    private javax.swing.JButton buttonCalculate;
    private javax.swing.JButton buttonClear;
    private javax.swing.JComboBox<Algorithm> cmbAlgorithm;
    private javax.swing.JTextField fieldFileName;
    private javax.swing.JTextField fieldHash;
    private javax.swing.JFileChooser hashFileChooserOpen;
    private javax.swing.JFileChooser hashFileChooserSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JLabel labelAlgorithm;
    private javax.swing.JLabel labelInfoAlgorithm;
    private javax.swing.JLabel labelInfoFilename;
    private javax.swing.JLabel labelInfoFilesize;
    private javax.swing.JLabel labelInfoHash;
    private javax.swing.JLabel labelOpen;
    private javax.swing.JMenuItem menuItemExit;
    private javax.swing.JMenuItem menuItemExport;
    private javax.swing.JMenuItem menuItemOpenFile;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables
}
