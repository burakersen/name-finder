import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        if(args.length<1){
            System.out.println("Please Enter An Url And Try Again");
            System.exit(0);
        }
        String text = "";
        try{
            Document doc = Jsoup.connect(args[0]).get();
            text = doc.html();
        }catch (Exception e){
            System.out.println("Please Try Again With A New Url!");
            System.exit(0);
        }

        String changeOne = text.replaceAll(",", " ,");
        String changeTwo = changeOne.trim().replaceAll("<", " <");
        String[] lines = changeTwo.trim().split("\\s+");

        InputStream inputStream = new FileInputStream("src/main/resources/en-ner-person.bin");
        TokenNameFinderModel model = new TokenNameFinderModel(inputStream);
        NameFinderME nameFinder = new NameFinderME(model);

        Span nameSpans[] = nameFinder.find(lines);

        for(Span s: nameSpans){
            StringBuilder builder = new StringBuilder();
            for (int i = s.getStart(); i < s.getEnd(); i++) {
                builder.append(lines[i]).append(" ");
            }

            System.out.println(builder.toString());
        }
    }
}
