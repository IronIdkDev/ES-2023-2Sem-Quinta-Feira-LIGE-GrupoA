package softwareeng.project;

import com.opencsv.exceptions.CsvValidationException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JScrollPane;

/**
 SelectUcs class that allows the user to create their schedule by selecting the curricular units they want.
 */
public class SelectUcs extends JFrame{
	
	private JButton backButton;
	private JButton finishButton;

	private JButton selectFileButton;

	private JButton showScheduleButton;

	private JPanel checkBoxPanel;

	private JScrollPane scrollPane;
	private Horario h;

	private List<String> selectedUCs;


    private static final Logger LOGGER = Logger.getLogger("SelectUcs");

	private static final String ERROR_MESSAGE = "Error";

	/**
	 *Constructor of the SelectUcs class
	 * Sets the size of the JFrame and initializes the other components.
	 */
	public SelectUcs() {
    	super("Select Ucs");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
		ImageIcon icon = new ImageIcon("icons/week.png");
		Image scaledImage = icon.getImage().getScaledInstance(2000, 2000, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		setIconImage(scaledIcon.getImage());
        // Set the look and feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
            UnsupportedLookAndFeelException ex) {
            LOGGER.log(Level.SEVERE, "Error starting program", ex);
         }

          // Set the size of the JFrame
          setSize(350, 150);

          // Set the layout and add components
          setLayout(new GridLayout(3, 1));
          initComponents();
          layoutComponents();
          addListeners();
          setLocationRelativeTo(null);
          
        }

	/**
	 *Method responsible for initializing the buttons and positioning them.
	 */
	private void initComponents() {
		int buttonSize = 48;
		selectFileButton = new JButton("Convert your File");
		selectFileButton.setBorderPainted(false);
		selectFileButton.setFocusPainted(false);
		selectFileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		selectFileButton.setIcon(new ImageIcon(new ImageIcon("icons/convertHorario.png")
				.getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));

		showScheduleButton= new JButton("Show your Schedule");
		showScheduleButton.setBorderPainted(false);
		showScheduleButton.setFocusPainted(false);
		showScheduleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		showScheduleButton.setIcon(new ImageIcon(new ImageIcon("icons/schedule.png")
				.getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));

		showScheduleButton.setVisible(false);
		showScheduleButton.addActionListener(e -> showScheduleButton());

		 backButton = new JButton();
		    backButton.setBorderPainted(false);
		    backButton.setFocusPainted(false);
		    backButton.setBackground(new Color(0, 0, 0, 0)); // Set transparent background
		    backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		    buttonSize = 48;
		    backButton.setIcon(new ImageIcon(new ImageIcon("icons/back.png")
		            .getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));
		    JPanel buttonPanel = new JPanel(new GridBagLayout()); // Create panel for button
		    GridBagConstraints gbc = new GridBagConstraints();
		    gbc.gridx = 0;
		    gbc.gridy = 0;
		    gbc.weightx = 1.0;
		    gbc.fill = GridBagConstraints.HORIZONTAL;
		    buttonPanel.add(backButton, gbc);
		    add(buttonPanel);



	}

	/**
	 Adds the previously created and processed buttons to JPanels and finally adds them to the JFrame.
	 */
	private void layoutComponents() {
	    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
	    JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(Box.createHorizontalGlue());
        //topPanel.add(checkBox);
		topPanel.add(selectFileButton);
        topPanel.add(Box.createRigidArea(new Dimension(10, 0)));

		topPanel.add(showScheduleButton);
		topPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        
        topPanel.add(Box.createHorizontalGlue());
        add(topPanel);

	    // Add the button panel to the second panel
	    JPanel middlePanel = new JPanel();
	    middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.X_AXIS));
	    middlePanel.add(Box.createHorizontalGlue());
	    middlePanel.add(backButton);
	    //middlePanel.add(Box.createHorizontalGlue());
	    add(middlePanel);
		
		
	}

	/**
	 Adds Listeners to the backButton and selectFileButton buttons.
	 */
	private void addListeners() {
		backButton.addActionListener(e -> backButtonClicked());
		selectFileButton.addActionListener(e -> {
			try {
				selectFileButtonClicked();
			} catch (CsvValidationException | IOException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	/**
	 This method allows the user to select a file from their computer, reads its content and shows all the curricular units of that file with a checkbox each.
	 At this stage, the checkBoxPanel JPanel has a Listener associated with it so that it always detects when a checkbox is selected.
	 When a checkbox is selected, the finixhButton button is displayed on the screen and it is then possible to finish the choices.
	 @throws CsvValidationException when the csv file is invalid
	 @throws IOException when it is not possible to read the selected file.
	 */
	private void selectFileButtonClicked() throws CsvValidationException, IOException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("CSV files (*.csv)", "csv");
		fileChooser.setFileFilter(csvFilter);

		FileNameExtensionFilter jsonFilter = new FileNameExtensionFilter("JSON files (*.json)", "json");
		fileChooser.setFileFilter(jsonFilter);



		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			String path = fileChooser.getSelectedFile().getPath();
			File selectedFile = fileChooser.getSelectedFile();
			if (selectedFile.getName().endsWith(".csv") || selectedFile.getName().endsWith(".json")) {
				selectFileButton.setVisible(false);
				h = new Horario(selectedFile.getName());
				List<String> ucs = h.getUCsFromHorario();
				int cols = 1;
				checkBoxPanel = new JPanel(new GridLayout(0, cols));
				for (String uc : ucs) {
					JCheckBox checkBox = new JCheckBox(uc);
					checkBox.addItemListener(e -> {
						boolean atLeastOneSelected = false;
						selectedUCs = new ArrayList<>();
						for (Component comp : checkBoxPanel.getComponents()) {
							if (comp instanceof JCheckBox) {
								JCheckBox cb = (JCheckBox) comp;
								if (cb.isSelected()) {
									atLeastOneSelected = true;
									selectedUCs.add(cb.getText());
								}
							}
						}
						finishButton.setVisible(atLeastOneSelected);
					});
					checkBoxPanel.add(checkBox);
				}
				scrollPane = new JScrollPane(checkBoxPanel);
				scrollPane.setViewportView(checkBoxPanel);
				add(scrollPane, BorderLayout.CENTER);
				pack();
				createFinishButton();
				add(finishButton, BorderLayout.NORTH);
				pack();
			} else {
				JOptionPane.showMessageDialog(this, "The selected file is not a CSV file or Json File.", ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
				selectFileButtonClicked();
			}
		}
	}

	/**
	 Method responsible for performing actions when the finishButton is clicked.

	 */
	private void finishButtonClicked(){
		h.horarioFile(selectedUCs);
		for (Component comp : checkBoxPanel.getComponents()) {
			if (comp instanceof JCheckBox) {
				JCheckBox cb = (JCheckBox) comp;
				cb.setVisible(false);
			}
		}
		finishButton.setVisible(false);
		scrollPane.setVisible(false);
		showScheduleButton.setVisible(true);
		setSize(350, 150);
	}


	private void showScheduleButton(){
		if(this.isVisible()){
			dispose();
			OpenSchedule openSchedule = new OpenSchedule();
			openSchedule.setVisible(true);
		}

	}

	/**
Creates button FinishButton
	 */
	private void createFinishButton() {
		int buttonSize = 48;
		finishButton = new JButton("Confirm Choices");
		finishButton.setBorderPainted(false);
		finishButton.setFocusPainted(false);
		finishButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		finishButton.setIcon(new ImageIcon(new ImageIcon("icons/confirm.png")
				.getImage().getScaledInstance(buttonSize, buttonSize, java.awt.Image.SCALE_SMOOTH)));

		finishButton.setVisible(false);
		finishButton.addActionListener(e -> finishButtonClicked());
	}


	/**
	 * Performs the actions when the backButton is clicked
	 */
	private void backButtonClicked() {
		if (this.isVisible()) {
			dispose();
			MainMenu mainMenu = new MainMenu();
			mainMenu.setVisible(true);
			File file = new File("horarioPessoal.json");
			if(file.exists() && !showScheduleButton.isVisible()){
				boolean fileDeleted = file.delete();
				if (fileDeleted) {
					System.out.println("File deleted successfully");
				} else {
					System.out.println("Failed to delete file");
				}
			}
		}
	}
}

