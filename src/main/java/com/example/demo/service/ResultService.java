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
import java.util.HashSet;
import java.util.Set;

@Service
public class ResultService {

    private static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";

    private static Set<String> links = new HashSet<String>();

    public ModelAndView searchResult(String searchTerm) throws IOException {

       if(links.size() != 0)
           links = new HashSet<String>();

        ModelAndView mv = new ModelAndView("index.jsp");

        String[] terms = searchTerm.split(" ");

        if(terms.length > 1) {
            for (int i = 0; i < terms.length; i += 5) {

                int finalI = i;
                Thread thread1 = new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    addResults(terms[finalI]);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch(IndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
                thread1.start();

                Thread thread2 = new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    addResults(terms[finalI + 1]);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch(IndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
                thread2.start();

                Thread thread3 = new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    addResults(terms[finalI + 2]);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch(IndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
                thread3.start();

                Thread thread4 = new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    addResults(terms[finalI + 3]);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch(IndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
                thread4.start();

                Thread thread5 = new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    addResults(terms[finalI + 4]);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch(IndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
                thread5.start();
            }

        }

        addResults(searchTerm);

        mv.addObject("links", links);
        return mv;
    }


    public void addResults(String searchTerm) throws IOException {
        int page = 0;
        Document document;
        Elements elements = null;
        for(int i = 0; i < 10; i++) {
            StringBuilder searchURLs = new StringBuilder(GOOGLE_SEARCH_URL + "?q=" + searchTerm + "&start=" + page);

            document = Jsoup.connect(searchURLs.toString()).timeout(100000).userAgent("Chrome 70.0.3538.77").get();

            elements = document.select(".kCrYT > a");

            for (Element element : elements) {
                String linkHref = element.getElementsByTag("a").attr("href");
                String[] x = linkHref.split("&");
                links.add(x[0].substring(7));
                System.out.println(linkHref);
            }

            page += 10;
        }
    }

/*
    public void getCSV(HttpServletResponse response) {

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
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
*/

    public void getCSV2(HttpServletResponse response) {

        File file = new File("downloads.csv");
        try {
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);


            writer.writeNext(new String[]{"links"});

            for (String s : links)
                writer.writeNext(new String[]{s});

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
            while ((length = in.read(buffer)) > 0)
                output.write(buffer, 0, length);

            in.close();
            output.flush();
            output.close();
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
