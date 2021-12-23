package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        AdamAsmaca adamAsmaca = new AdamAsmaca("C:\\Users\\oguzh\\Desktop\\kelimeler.txt");
        File a = new File("C:\\Users\\oguzh\\Desktop\\kelimeler.txt");
        FileWriter fw = new FileWriter(a, false);
        BufferedWriter bf = new BufferedWriter(fw);
        bf.write("dsfdfsdf");
        bf.newLine();
        bf.write("adsfasf");
        bf.newLine();
        bf.write("dsfdfdsgdfsdf");
        bf.newLine();
        bf.write("regw");
        bf.newLine();
        bf.close();

    }
}
