package com.shdd.threeinone.HandleTools.XmlMessageHandle;

import com.shdd.cfs.ColdFusionStorageNexus;
import jdk.internal.org.xml.sax.XMLReader;
import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.boot.SpringApplication;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.swing.text.Document;
import javax.swing.text.Element;
import java.awt.print.Book;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.lune.bean.Book;


public class Xmlhandle {
        public static String fromUrlGetxml(String url) throws IOException {
                URLConnection uc = new URL(url).openConnection();
                uc.setConnectTimeout(10000);
                uc.setDoOutput(true);
                InputStream in = new BufferedInputStream(uc.getInputStream());
                StringBuffer temp = new StringBuffer();
                Reader rd = new InputStreamReader(in,"UTF-8");
                int c = 0;
                while ((c = rd.read()) != -1) {
                        temp.append((char) c);
                }
                in.close();
                return temp.toString();
        }


        public class ReadXMLByDom4j {

                private List<Book> bookList = null;
                private Book book = null;

                public List<Book> getBooks(File file){

                        SAXReader reader = new SAXReader();
                        try {
                                Document document = (Document) reader.read(file);
                                Element bookstore = document.getRootElement();
                                Iterator storeit = bookstore.elementIterator();

                                bookList = new ArrayList<Book>();
                                while(storeit.hasNext()){

                                        book = new Book();
                                        Element bookElement = (Element) storeit.next();
                                        //遍历bookElement的属性
                                        List<Attribute> attributes = bookElement.attributes();
                                        for(Attribute attribute : attributes){
                                                if(attribute.getName().equals(“id”)){
                                                        String id = attribute.getValue();//System.out.println(id);
                                                        book.setId(Integer.parseInt(id));
                                                }
                                        }

                                        Iterator bookit = bookElement.elementIterator();
                                        while(bookit.hasNext()){
                                                Element child = (Element) bookit.next();
                                                String nodeName = child.getName();
                                                if(nodeName.equals(“name”)){
                                                        //System.out.println(child.getStringValue());
                                                        String name = child.getStringValue();
                                                        book.setName(name);
                                                }else if(nodeName.equals(“author”)){
                                                        String author = child.getStringValue();
                                                        book.setAuthor(author);
                                                }else if(nodeName.equals(“year”)){
                                                        String year = child.getStringValue();
                                                        book.setYear(Integer.parseInt(year));
                                                }else if(nodeName.equals(“price”)){
                                                        String price = child.getStringValue();
                                                        book.setPrice(Double.parseDouble(price));
                                                }
                                        }
                                        bookList.add(book);
                                        book = null;

                                }
                        } catch (DocumentException e) {

                                e.printStackTrace();
                        } catch (DocumentException e) {
                                e.printStackTrace();
                        }


                        return bookList;

                }


                /**
                 * @param args
                 */
                public static void main(String[] args) {
                        // TODO Auto-generated method stub
                        File file = new File(“src/res/books.xml”);
                        List<Book> bookList = new ReadXMLByDom4j().getBooks(file);
                        for(Book book : bookList){
                                System.out.println(book);
                        }
                }

        }


        public static void main(String[] args) throws IOException {
            System.out.println(fromUrlGetxml("http://www.w3school.com.cn/example/xmle/note.xml"));
        }

}
