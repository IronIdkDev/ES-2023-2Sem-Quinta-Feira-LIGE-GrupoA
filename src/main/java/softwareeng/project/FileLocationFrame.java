package softwareeng.project;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.net.MalformedURLException;
import java.net.URL;


public class FileLocationFrame extends JFrame {
    private JLabel locationLabel;
    private JTextField locationTextField;
    private JButton browseButton;
    private JButton okButton;
    private static final Logger LOGGER = Logger.getLogger("FileLocationFrame");


    public FileLocationFrame() {
        super("Selecionar localização do ficheiro");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 2));
        initComponents();
        layoutComponents();
        addListeners();
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        locationLabel = new JLabel("Localização do ficheiro:");
        locationTextField = new JTextField(20);
        browseButton = new JButton("Procurar ficheiro localmente");
        okButton = new JButton("OK");
    }

    private void layoutComponents() {
        add(locationLabel);
        add(locationTextField);
        add(browseButton);
        add(okButton);
    }

    private void addListeners() {
        browseButton.addActionListener(e -> browseButtonClicked());
        okButton.addActionListener(e -> okButtonClicked());
    }


    private void browseButtonClicked() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getPath();
            locationTextField.setText(path);
        }
    }

    private void okButtonClicked() {
        String location = locationTextField.getText();
        // faça algo com a localização do arquivo aqui
        JOptionPane.showMessageDialog(this, "URL of target schedule: " + location);
        //Verifica se a String com o caminho do ficheiro inserido terminar em .csv, então converte-o em JSon
        if(location.endsWith(".csv")) {
            CSVToJSon csv = new CSVToJSon();
            csv.convertCSVToJson(location);
        }else if(location.endsWith(".json")) {
            try {
                JSonToCSV json = new JSonToCSV(location);
                json.convertFile();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao converter o arquivo JSON para CSV: " + e.getMessage());
            }
        }else if(location.startsWith("webcal")) {
        	Web web = new Web();
            // Makes it possible to have an .ics file
        	String s = location.replace("webcal", "https");
        	
				try {
					URL url = new URL(s);
					web.ReadWeb(url);
					web.icsToJson(url);
				} catch (MalformedURLException e) {
					LOGGER.log(Level.SEVERE, "Exception occurred", e);
				} catch (IOException e) {
					LOGGER.log(Level.SEVERE, "Exception occurred", e);
				}
        }else if(location.startsWith("https")){
        	Web web = new Web();
        	
				try {
					URL url = new URL(location);
					web.ReadWeb(url);
					web.URLToCSV(url);
				} catch (MalformedURLException e) {
					LOGGER.log(Level.SEVERE, "Exception occurred", e);
				} catch (IOException e) {
					LOGGER.log(Level.SEVERE, "Exception occurred", e);
				}
        }
        dispose();
    }

    // Métodos para testar

    public JTextField getLocationTextField() {
        return locationTextField;
    }

    public JButton getBrowseButton() {
        return browseButton;
    }

    public JButton getOkButton() {
        return okButton;
    }

    public void setLocationTextField(String location) {
        locationTextField.setText(location);
    }

    public void clickBrowseButton() {
        browseButton.doClick();
    }


}