package com.company;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class AdamAsmaca {
    List<String> kelimeListesi = new ArrayList<>();
    File kelimeDosyasi;
    Scanner scn = new Scanner(System.in);
    private int kalanHak = 8;
    private String kelime;
    private Character[] bulunanHarfler;
    private int kacHarfBulundu = 0;

    AdamAsmaca(String path) throws IOException {
        kelimeDosyasi = new File(path);
        if (kelimeDosyasi.exists()) {
            Scanner scanner = new Scanner(kelimeDosyasi);
            while (scanner.hasNextLine()) {
                kelimeListesi.add(scanner.nextLine());
            }
            if (kelimeListesi.size() != 0) {
                AnaMenu();
            } else {
                System.out.println("Belirtilen dosya boş");
            }
        }
    }

    public void Sor() throws IOException {
        BoslukSpam();
        AdamCiz(kalanHak);
        System.out.println("Kalan Hak : " + kalanHak);
        for (int i = 0; i < bulunanHarfler.length; i++) {
            if (bulunanHarfler[i] != null) {
                System.out.print(bulunanHarfler[i]);
            } else System.out.print(" ");
        }
        System.out.println();
        for (int i = 0; i < kelime.length(); i++) {
            System.out.print("-");
        }
        System.out.println();
        if (kalanHak == 0) {
            System.out.println("Oyun Bitti\nKaynettiniz\nKelime : " + kelime);
            AnaMenuSorgu();
        }
        System.out.print("Harf Gir : ");
        Character girilenHarf = scn.next().toLowerCase().charAt(0);
        if (kelime.toLowerCase().contains(girilenHarf.toString().toLowerCase())) {
            if(!Arrays.asList(bulunanHarfler).contains(girilenHarf)) {
                for (int i = 0; i < kelime.length(); i++) {
                    if (kelime.toLowerCase().charAt(i) == (char) girilenHarf.toString().toLowerCase().charAt(0)) {
                        bulunanHarfler[i] = girilenHarf;
                        kacHarfBulundu++;
                    }
                }
            }
            if (kacHarfBulundu == kelime.length()) {
                System.out.println("Oyun Bitti \n Kazandınız.");
                System.out.println("Yeni Oyuna Başlamak için 'Y' yazıp enterlayın. ");
                if (scn.next().toLowerCase().equals("y")) {
                    System.out.println("Yeni Oyun Başladı");
                    YeniOyun();
                }
                else AnaMenu();
            } else Sor();
        } else {
            kalanHak--;
        }
        Sor();
    }

    private void YeniOyun() throws IOException {
        YeniKelimeCek();
        this.kalanHak = 8;
        kacHarfBulundu =0;
        bulunanHarfler = new Character[kelime.length()];
        Sor();
    }

    private void AnaMenu() throws IOException {
        BoslukSpam();
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
        System.out.println("Yeni Oyun                  ----> 1");
        System.out.println("Çıkış                      ----> 2");
        System.out.println("Kelime Listesi Yolu Ayarla ----> 3");
        System.out.print("-------->  ");
        String x = scn.nextLine();
        if (x.equals("1")) YeniOyun();
        else if (x.equals("2")) System.exit(0);
        else if (x.equals("3")) {
            System.out.println("Yeni kelime listesinin tam yolunu giriniz.");
            String yeniKelimeListesiYolu = scn.next();
            File y = new File(yeniKelimeListesiYolu);
            if (y.exists()) {
                System.out.println("İşlem Başarılı");
            } else System.out.println("İşlem başarısız");
            AnaMenuSorgu();
        } else AnaMenu();
    }

    private void YeniKelimeCek() throws IOException {
        if (kelimeListesi.size() != 0) {
            Random uretici = new Random();
            int sira = uretici.nextInt(kelimeListesi.size());
            String yeniKelime = kelimeListesi.get(sira);
            kelimeListesi.remove(sira);
            kelime = yeniKelime;
            System.out.println(yeniKelime);//guhıjokaefrıhtgofepdagfıhkspadf
        } else {
            System.out.println("Veri tabanında yeni kelime kalmadı");
            System.out.println("Çıkış yapmak için enter a basın");
            System.in.read();
            System.exit(0);
        }
    }

    private void KelimeleriListeyeCek() throws IOException {
        if (kelimeDosyasi.exists()) {
            Scanner f = new Scanner(kelimeDosyasi);
            List<String> yeniListe = new ArrayList<>();
            while (f.hasNextLine()) {
                yeniListe.add(f.nextLine());
            }
            if (yeniListe.size() == 0) {
                System.out.println("Dosya boş");
                AnaMenuSorgu();
            } else {
                while (true) {
                    BoslukSpam();
                    System.out.println("Kelime listesi güncellemek için 'evet' yazıp enterlamanız gerek ");
                    System.out.println("İşlemi iptal etmek için 'iptal' yazıp enterlamanız gerek");
                    String sonuc = scn.next();
                    if (sonuc.toLowerCase().equals("evet")) {
                        AnaMenuSorgu();
                    } else if (sonuc.toLowerCase().equals("iptal")) {
                        AnaMenu();
                    }

                }
            }
        } else {
            System.out.println("Geçersiz dosya yolu");
        }
    }

    private void BoslukSpam() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    private void AnaMenuSorgu() throws IOException {
        System.out.println();
        System.out.println();
        System.out.println("Ana menüye dönmek için enter a basın");
        System.in.read();
        AnaMenu();
    }

    private void AdamCiz(int i) {
        switch (i) {
            case 0: {
                System.out.println("                  _______                  ");
                System.out.println("                  |     |                    ");
                System.out.println("                 \\0/    |                     ");
                System.out.println("                  |     |                   ");
                System.out.println("                 / \\    |                      ");
                System.out.println("                        |                   ");
                System.out.println("                 --------                   ");
                break;
            }
            case 1: {
                System.out.println("                  _______                  ");
                System.out.println("                        |                    ");
                System.out.println("                 \\0/    |                     ");
                System.out.println("                  |     |                   ");
                System.out.println("                 / \\    |                      ");
                System.out.println("                        |                   ");
                System.out.println("                 --------                   ");
                break;
            }
            case 2: {
                System.out.println("                  _______                  ");
                System.out.println("                        |                    ");
                System.out.println("                 \\0/    |                     ");
                System.out.println("                  |     |                   ");
                System.out.println("                        |                      ");
                System.out.println("                        |                   ");
                System.out.println("                 --------                   ");
                break;
            }
            case 3: {
                System.out.println("                  _______                  ");
                System.out.println("                        |                    ");
                System.out.println("                 \\0/    |                     ");
                System.out.println("                        |                   ");
                System.out.println("                        |                      ");
                System.out.println("                        |                   ");
                System.out.println("                 --------                   ");
                break;
            }
            case 4: {
                System.out.println("                  _______                  ");
                System.out.println("                        |                    ");
                System.out.println("                  0     |                     ");
                System.out.println("                        |                   ");
                System.out.println("                        |                      ");
                System.out.println("                        |                   ");
                System.out.println("                 --------                   ");
                break;
            }
            case 5: {
                System.out.println("                  _______                  ");
                System.out.println("                        |                    ");
                System.out.println("                        |                     ");
                System.out.println("                        |                   ");
                System.out.println("                        |                      ");
                System.out.println("                        |                   ");
                System.out.println("                 --------                   ");
                break;
            }
            case 6: {
                System.out.println("                                           ");
                System.out.println("                        |                    ");
                System.out.println("                        |                     ");
                System.out.println("                        |                   ");
                System.out.println("                        |                      ");
                System.out.println("                        |                   ");
                System.out.println("                 --------                   ");
                break;
            }
            case 7: {
                System.out.println("                                           ");
                System.out.println("                                            ");
                System.out.println("                                             ");
                System.out.println("                                           ");
                System.out.println("                                              ");
                System.out.println("                                           ");
                System.out.println("                 --------                   ");
                break;
            }
            case 8: {
                System.out.println("                                           ");
                System.out.println("                                            ");
                System.out.println("                                             ");
                System.out.println("                                           ");
                System.out.println("                                              ");
                System.out.println("                                           ");
                System.out.println("                                            ");
                break;
            }
        }
    }
}
