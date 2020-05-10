package Tools.FileReader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class ImportFiles {
    public static File importFile(){

        //Change the JFileChooser window style
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (Exception e) {
            System.out.println(e);
        }

        JFileChooser selector = new JFileChooser();
        JFrame parentFrame = new JFrame();

        chooserConfiguration(selector, parentFrame);
        System.out.println("Select all .obj files you want to add to the scene");

        File input = askForTheFiles(selector, parentFrame);

        return input;
    }

    /**
     * chooserConfiguration, configure the chooser to allow only photos and videos and multi selection
     * @param selector
     * @param parentFrame
     */
    private static void chooserConfiguration(JFileChooser selector, JFrame parentFrame){
        parentFrame.setAlwaysOnTop(true);
        parentFrame.requestFocus();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("OBJ Files", "obj");
        selector.setFileFilter(filter);
        selector.setMultiSelectionEnabled(true);
    }

    /**
     *askForTheFIle, show the selector and return the Files selected
     * @param selector
     * @param parentFrame
     * @return inputFile[]
     */
    private static File askForTheFiles(JFileChooser selector, JFrame parentFrame)
    {
        int result;

        //loop that ensures the user selects a file
        do{
            System.out.println("Selector is open, please select the OBJ files: ");
            result = selector.showOpenDialog(parentFrame);
        }while(!(result == JFileChooser.APPROVE_OPTION));

        //Destroy the window
        parentFrame.dispose();

        return selector.getSelectedFile();
    }
}
