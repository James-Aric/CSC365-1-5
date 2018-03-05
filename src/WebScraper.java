import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author James
 */
public class WebScraper {
    //Jsoup document array
    Document[] docs;

    //method to find the num of lines in a file in order to creat docs array
    void fileLineNum(){
        try{
            //BufferedReader reader = new BufferedReader(new FileReader("sites.txt"));
            BufferedReader reader = new BufferedReader(new FileReader("sites.txt"));
            int lines = 0;
            while (reader.readLine() != null) lines++;
            reader.close();
            docs = new Document[lines];
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
    //load documents from sites.txt file located in project directory.
    void loadDocs() {
        fileLineNum();
        try {
            //create a scanner to go through file to pull urls
            Scanner scan = new Scanner(new File("sites.txt"));
            File f = new File("sites.txt");
            for(int i = 0; scan.hasNextLine();i++){
                //convert the urls to Documents via Jsoup, and add them to docs array
                docs[i] = Jsoup.connect(scan.nextLine()).get();
            }
            //CURRENT sites.txt HOLDS 138,964 words
        }
        //catch errors and print out exception toString
        catch (Exception e) {
            System.out.println("ERROR: CODE BELOW  WEBSCRAPER LOADDOCS");
            System.out.println(e.toString());
        }
    }

    //read text from Documents, remove special characters in order to split properly for hashing
    String[] parseDocs(){
        //call loadDocs just to be safe, better slightly slower then sorry
        loadDocs();
        //create an array of size docs.length to hold the new cleaned up string
        String text[] = new String[docs.length];

        //add string to array, and clean up special chars
        for(int i = 0; i < docs.length; i++){
            String temp = docs[i].text();
            //System.out.println(temp);
            temp = temp.replaceAll("[^A-Za-z0-9]", " ");
            temp = temp.replaceAll("  ", " ");
            text[i] = temp;
        }
        //return array
        return text;
    }

    String parseCompare(String url) throws IOException{
        String temp;
        Document tempDoc = Jsoup.connect(url).get();
        temp = tempDoc.text();
        temp = temp.replaceAll("[^A-Za-z0-9]", " ");
        temp = temp.replaceAll("  ", " ");
        return temp;
    }


}
