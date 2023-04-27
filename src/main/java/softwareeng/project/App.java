package softwareeng.project;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        try {
            LOGGER.log(Level.CONFIG, "Program started.");
            MainMenu mainMenu = new MainMenu();
            mainMenu.setVisible(true);
            mainMenu.getOpenSchedulesButton().addActionListener(e -> {});
            mainMenu.getConvertSchedulesButton().addActionListener(e -> {
                String location = "webcal://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=rageo@iscte.pt&password=zLgFKoKyGjZf1Ago80qtjmy8f0eS5uDCJQZSq2MNDGbZlTcMLw7pXDjThCYU52bDlIZsBYjNgXsIGLGUYPs8HHDfk9YnHQIZtkZXHgyBlk1nvaoTbqw4S2BG4V70CcTl";

                    Web web = new Web();
                        String s = location.replace("webcal", "https");

                        try {
                            URL url = new URL(s);
                            web.ReadWeb(url);
                            web.URLToCSV(url);
                            web.URLToJson(url);
                        } catch (IOException ex) {
                            LOGGER.log(Level.SEVERE, "Exception occurred", ex);
                        }
                    }
            );


            mainMenu.getLoadSchedulesButton().addActionListener(e -> {
                mainMenu.setVisible(false);
                LoadSchedules loadSchedules = new LoadSchedules();
                loadSchedules.setVisible(true);
            });
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error starting program", e);
        }
    }
}
