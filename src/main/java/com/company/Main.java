package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Path path = new Path();
        XMLStructure xmlStructure = new XMLStructure(path.getPathValue("demo1Path"));
        xmlStructure.getXMLElement("questionname");
        xmlStructure.getXMLElement("answers");
        xmlStructure.updateXmlValue("answers","id",117);

        Person person = new Person();

        Scanner scanner = new Scanner(System.in);



    }
}
