package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class AdamAsmaca {
    List<String> kelimeListesi = new ArrayList<>();
    Scanner scn = new Scanner(System.in);
    private int kalanHak = 8;
    private String kelime;
    private Character[] bulunanHarfler;
    private int kacHarfBulundu = 0;

    AdamAsmaca(String path) throws IOException {
        File kelimeDosyasi = new File(path);
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
            if (!Arrays.asList(bulunanHarfler).contains(girilenHarf)) {
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
                } else AnaMenu();
            } else Sor();
        } else {
            kalanHak--;
        }
        Sor();
    }

    private void YeniOyun() throws IOException {
        YeniKelimeCek();
        this.kalanHak = 8;
        kacHarfBulundu = 0;
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
        System.out.println("Liste Ayarları             ----> 4");
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
        } else if (x.equals("4")) {
            ListeAyarlari();
        } else AnaMenu();
    }

    private void ListeAyarlari() throws IOException {
        System.out.println("1-->    Liste veritabanı yolunu güncelle ");
        System.out.println("2-->    Geçerli veritabanına yeni kelime ekle");
        System.out.println("3-->    Yeni veri tabanı oluştur");
        System.out.println("4-->    Ana menüye Dön");
        System.out.print("----------> ");
        String x = scn.nextLine();
        switch (x) {
            case "1": {
                VeriYoluGuncelle();
                break;
            }
            case "2": {
                VeriTabaninaKelimeEkle();
                break;
            }
            case "3": {
                YeniVeriTabani();
                break;
            }
            case "4": {
                AnaMenu();
                break;
            }
            default: {
                ListeAyarlari();
            }
        }
    }

    private void VeriTabaninaKelimeEkle() {
    }

    private void YeniVeriTabani() throws IOException {
        System.out.println("Yeni Veri tabanı yolu giriniz : ");
        String x = scn.nextLine();
        File f = new File(x);
        if (f.exists()) {
            System.out.println("Belirtilen adreste zaten bir dosya mevcut");
            System.out.println("1-->Dosyanın içeriğini silip yeniden doldur");
            System.out.println("2-->Dosyanın içeriğinin üzerine veri ekle");
            System.out.println("3-->Yeni Dosya yolu gir");
            System.out.println("4-->İptal et ve anamenüye dön");
            System.out.println("----------->");
            String a = scn.nextLine();
            switch (a) {
                case "1": {
                    FileWriter fw = new FileWriter(f, false);
                    BufferedWriter bw = new BufferedWriter(fw);
                    while (true) {
                        BoslukSpam();
                        System.out.println(" Eklemek istediğiniz kelimeyi yazıp enter a basmanız yeterli.\n Ekleme işlemini sonlanırmak için İşlemi_Sonlandır yazıp enterlamanız yeterli");
                        String yeniKelime = scn.nextLine();
                        if (yeniKelime.equals("İşlemi_Sonlandır")) {
                            bw.close();
                            break;
                        }
                        bw.write(yeniKelime);
                        bw.newLine();
                    }
                    break;
                }
                case "2": {
                    FileWriter fw = new FileWriter(f, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    while (true) {
                        BoslukSpam();
                        System.out.println(" Eklemek istediğiniz kelimeyi yazıp enter a basmanız yeterli.\n Ekleme işlemini sonlanırmak için İşlemi_Sonlandır yazıp enterlamanız yeterli");
                        String yeniKelime = scn.nextLine();
                        if (yeniKelime.equals("İşlemi_Sonlandır")) {
                            bw.close();
                            break;
                        }
                        bw.write(yeniKelime);
                        bw.newLine();
                    }
                    break;
                }
                case "3": {
                    YeniVeriTabani();
                    break;
                }
                case "4": {
                    AnaMenuSorgu();
                    break;
                }
            }
            AnaMenuSorgu();
        }
    }

    private void VeriYoluGuncelle() throws IOException {
        System.out.print("Yeni veritabanı yolunu giriniz : ");
        String yeniVeriYolu = scn.nextLine();
        File yeniVeriTabani = new File(yeniVeriYolu);
        if (!yeniVeriTabani.exists()) {
            System.out.println("Belirtilen konumda böyle bir dosya yok");
        } else {
            List<String> yeniKelimeListesi = new ArrayList<>();
            Scanner kelimeCekici = new Scanner(yeniVeriTabani);
            while (kelimeCekici.hasNextLine()) {
                yeniKelimeListesi.add(kelimeCekici.nextLine());
            }
            if (kelimeListesi.size() == 0) {
                System.out.println("Geçerli veritabanı dosyası içerisinde herhangi bir kelime yok");
            } else {
                System.out.println("Veritabanı bulundu.\nKelime Adedi : " + yeniKelimeListesi.size());
                if (Eminmisin()) {
                    kelimeListesi = yeniKelimeListesi;
                } else {
                    AnaMenuSorgu();
                }
            }
        }
    }

    private void YeniKelimeCek() throws IOException {
        if (kelimeListesi.size() != 0) {
            Random uretici = new Random();
            int sira = uretici.nextInt(kelimeListesi.size());
            String yeniKelime = kelimeListesi.get(sira);
            kelimeListesi.remove(sira);
            kelime = yeniKelime;
        } else {
            System.out.println("Veri tabanında yeni kelime kalmadı");
            System.out.println("Çıkış yapmak için enter a basın");
            System.in.read();
            System.exit(0);
        }
    }


    private boolean Eminmisin() {
        Boolean sonuc = null;
        System.out.println("Bu işlemi gerçekleştirmek istediğine eminmisin(EVET/HAYIR giriniz)");
        String x = scn.nextLine().toUpperCase();
        if (x.equals("EVET")) {
            sonuc = true;
        } else if (x.equals("HAYIR")) {
            sonuc = false;
        } else Eminmisin();
        return sonuc;
    }

    private void BoslukSpam() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    private void AnaMenuSorgu() {
        System.out.println();
        System.out.println();
        System.out.println("Ana menüye dönmek için enter a basın");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            AnaMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void AdamCiz(int i) {
        switch (i) {
            case 0: {
                System.out.println("  __________________________________________");
                System.out.println("  |               _______                  |");
                System.out.println("  |               |     |                  |");
                System.out.println("  |              \\0/    |                  |");
                System.out.println("  |               |     |                  |");
                System.out.println("  |              / \\    |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |              --------                  |");
                System.out.println("  |________________________________________|");
                break;
            }
            case 1: {

                System.out.println("  __________________________________________");
                System.out.println("  |               _______                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |              \\0/    |                  |");
                System.out.println("  |               |     |                  |");
                System.out.println("  |              / \\    |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |              --------                  |");
                System.out.println("  |________________________________________|");
                break;
            }
            case 2: {

                System.out.println("  __________________________________________");
                System.out.println("  |               _______                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |              \\0/    |                  |");
                System.out.println("  |               |     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |              --------                  |");
                System.out.println("  |________________________________________|");
                break;
            }
            case 3: {
                System.out.println("  __________________________________________");
                System.out.println("  |               _______                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |              \\0/    |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |              --------                  |");
                System.out.println("  |________________________________________|");
                break;
            }
            case 4: {
                System.out.println("  __________________________________________");
                System.out.println("  |               _______                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |               0     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |              --------                  |");
                System.out.println("  |________________________________________|");
                break;
            }
            case 5: {
                System.out.println("  __________________________________________");
                System.out.println("  |               _______                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |              --------                  |");
                System.out.println("  |________________________________________|");
                break;
            }
            case 6: {

                System.out.println("  __________________________________________");
                System.out.println("  |                                        |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |              --------                  |");
                System.out.println("  |________________________________________|");
                break;
            }
            case 7: {
                System.out.println("  __________________________________________");
                System.out.println("  |                                        |");
                System.out.println("  |                                        |");
                System.out.println("  |                                        |");
                System.out.println("  |                                        |");
                System.out.println("  |                                        |");
                System.out.println("  |                                        |");
                System.out.println("  |              --------                  |");
                System.out.println("  |________________________________________|");
                break;
            }
            case 8: {
                System.out.println("  __________________________________________");
                System.out.println("  |                                        |");
                System.out.println("  |                                        |");
                System.out.println("  |                                        |");
                System.out.println("  |                                        |");
                System.out.println("  |                                        |");
                System.out.println("  |                                        |");
                System.out.println("  |                                        |");
                System.out.println("  |________________________________________|");
                break;
            }
        }
    }
}
