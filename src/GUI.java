import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class GUI {


    //define the JavaFX objects
    public TextField input;
    public Label result;
    public Label closest;

    //String for the url inputted by the user
    private String inputURL;

    //creating the webscraper to go through the hardcoded URL's, and the inputURL
    WebScraper ws = new WebScraper();

    //create the string list from the hardcoded urls
    String[] urlText = ws.parseDocs();

    //variables to keep track of the best result, and the best results index
    double compareResult = 0;
    int compareIndex = 0;

    //method called when pressing the compare button, compares the user input to
    public void compare(ActionEvent actionEvent) throws IOException{
        //try catch in order to catch illegal url input
        try{
            //get the user input from the input box
            inputURL = input.getText();
            //create two frequency tables
            FreqTable f1, f2;
            //create another string object that stores the parsed url from the user input
            String compare = ws.parseCompare(input.getText());

            //iterate through the hardcoded parsed urls to find the best match
            for(int i = 0; i < urlText.length; i++){
                //instantiate the two tables created above
                f1 = new FreqTable();
                f2 = new FreqTable();
                //map the first one with the user inputted parsed page
                f1.mapPage1(compare);
                //map the second one with the i'th element
                f2.mapPage2(urlText[i]);
                //create a new cosineCalc object to do the cosine similarity calculations
                CosineCalcs cc = new CosineCalcs();
                //store the results of the comparison in a variable to compare against previous results
                double tempResults = cc.compare(f1,f2);
                //if the most recently compared data is better then previously calculated data
                if(tempResults > compareResult){
                    //the most recent comparison becomes the compareResult
                    compareResult = tempResults;
                    //stores the index of the best fit
                    compareIndex = i;
                }
            }

            //sets the result text box to the similarity value
            result.setText(""+compareResult);
            //sets the other text box to the website url
            closest.setText(getURLName(compareIndex));
            //just to be safe, reset the value of compare result
            compareResult = 0;
        }
        catch(IllegalArgumentException e){
            //sets one of the boxes to the following output to show that an improper url was input
            result.setText("Invalid input");
        }
    }

    //hacked together way of getting the url
    String getURLName(int num)throws FileNotFoundException{
        Scanner scan = new Scanner(new File("sites.txt"));
        String name = "";
        for(int i = 0; i < num+1; i++){
            name = scan.nextLine();
        }
        return name;
    }
}