package Lessons.JAXB;



import Lessons.Message;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        Message m1 = new Message("wqeqwe", 2 );
        Message m2 = new Message("hello", 12 );
        Message m3 = new Message("rrrrrr", 90 );
        Message m4 = new Message("mmmmm", 5 );

        Product pr1 = new Product("Phone", 120000, "rrrr", m1);
        Product pr2 = new Product("Tyyre", 120000, "one", m2);
        Product pr3 = new Product("kkkkk", 120000, "two", m3);
        Product pr4 = new Product("nnnnn", 120000, "five", m4);

        Catalog catalog = new Catalog();
        catalog.add(pr1);
        catalog.add(pr2);
        catalog.add(pr3);
        catalog.add(pr4);

        File file = new File("D:\\Files\\Documents\\Java\\Work\\my.xml");




        convertObjectToXml(catalog, file);



        Catalog unmarshalOrganization = fromXmlToObject(file);
        if (unmarshalOrganization != null) {
            System.out.println(unmarshalOrganization.toString());
        }


    }
    public static void convertObjectToXml(Catalog cat, File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(Catalog.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(cat, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }


    private static Catalog fromXmlToObject(File file) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
            Unmarshaller un = jaxbContext.createUnmarshaller();

            return (Catalog) un.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
