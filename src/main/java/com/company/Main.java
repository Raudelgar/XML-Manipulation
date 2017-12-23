package com.company;

public class Main {

    public static void main(String[] args) {

        Path path = new Path();
        XMLStructure xmlStructure = new XMLStructure(path.getPathValue("demo1Path"));
        System.out.print(xmlStructure.getXMLElement("questionname"));
        System.out.print(xmlStructure.getXMLElement("answers"));
    }
}
