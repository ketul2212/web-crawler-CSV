package com.example.demo.service;

import com.opencsv.CSVWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResultService {

    private static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";

    private static List<String> links = new ArrayList<String>();

    public ModelAndView searchResult(String searchTerm) throws IOException {

       if(links.size() != 0)
           links = new ArrayList<String>();

        ModelAndView mv = new ModelAndView("index.jsp");

        String searchURL = GOOGLE_SEARCH_URL + "?q="+searchTerm;

        Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();

        Elements results = doc.select(".kCrYT > a");

        for (Element result : results) {
            String linkHref = result.getElementsByTag("a").attr("href");
            String[] x = linkHref.split("&");
            links.add(x[0].substring(7));
        }

        mv.addObject("links", links);

        return mv;
    }

    public void getCSV(HttpServletResponse response) throws IOException {

        File file = new File("downloads.csv");
        try {
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);

            String[] header = new String[links.size()];
            for(int i = 0; i < header.length; i++)
                header[i] = "link" + (i + 1);
            writer.writeNext(header);

            for(int i = 0; i < header.length; i++)
                header[i] = links.get(i);
            writer.writeNext(header);
            writer.close();

            String userDirectory = FileSystems.getDefault()
                    .getPath("")
                    .toAbsolutePath()
                    .toString();

            response.setContentType("text/csv");
            response.setHeader("Content-disposition","attachment; filename=downloads.csv");

            OutputStream output = response.getOutputStream();
            FileInputStream in = new FileInputStream(userDirectory + "/downloads.csv");
            byte[] buffer = new byte[4096];
            int length;
            while ((length = in.read(buffer)) > 0){
                output.write(buffer, 0, length);
            }
            in.close();
            output.flush();
            output.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
