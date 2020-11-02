package userinterface;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class creates a new custom theme
 * @author Håkon
 */
public class CustomTheme {

    private String backgroundColor;
    private String textColor;
    private String buttonColor;
    private String borderColor;

    public CustomTheme(String backgroundColor, String textColor, String buttonColor, String borderColor){
        this.backgroundColor = backgroundColor;
        this.textColor       = textColor;
        this.buttonColor     = buttonColor;
        this.borderColor     = borderColor;
    }

    /**
     * This method writes the values of the theme to a .css file
     */
    public void saveTheme(){
        final String FILENAME = "src/userinterface/style/themes/custom.css";

        String css =
                  ".parent                { -fx-background-color: " + backgroundColor + ";}\n"
                + ".infolabel, .button    { -fx-text-fill: "        + textColor       + ";}\n"
                + ".button, .color-picker { -fx-background-color: " + buttonColor     + ";}\n"
                + ".progess-indicator     { -fx-progress-color: "   + buttonColor     + ";}\n"
                + ".text-field, .text-area, .check-box .box, .button { -fx-border-color: " + borderColor + ";}\n";

        try (FileWriter fileWriter = new FileWriter(FILENAME);
             BufferedWriter writer = new BufferedWriter(fileWriter)){

            writer.write(css);

        } catch (IOException e){
            System.out.println("Could not write custom theme");
            e.printStackTrace();
        }
    }
}