package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.lang.System.*;

public class AdamAsmaca {


    private final Hile hile = new Hile();
    private int kazanilanOyunSayisi = 0;
    private int kaybedilenOyunSayisi = 0;
    private final List<String> oynananKelimeler = new ArrayList<>();
    private List<String> kelimeListesi = new ArrayList<>();
    private String gecerliVeriYolu;
    private final Scanner scn = new Scanner(in);
    private int kalanHak = 8;
    private String kelime;
    private Character[] bulunanHarfler;
    private int kacHarfBulundu = 0;

    AdamAsmaca(String path) throws IOException {
        out.println("                                                                               HOŞGELDİNİZ");
        out.println("Enter ile Anamenüye devam edebilirsiniz.");
        in.read();
        File kelimeDosyasi = new File(path);
        if (kelimeDosyasi.exists()) {
            Scanner scanner = new Scanner(kelimeDosyasi);
            while (scanner.hasNextLine()) {
                String kelime = scanner.nextLine();
                if (kelime.equals("") | kelime.length() < 51) {
                    kelimeListesi.add(kelime);
                }
            }
            if (kelimeListesi.size() != 0) {
                gecerliVeriYolu = path;
                AnaMenu();
            } else {
                out.print("Bu veri tabanı boş yeni veri eklemek istermisin : ");
                if (Eminmisin()) {
                    VeriTabaninaKelimeEkle();
                } else {
                    out.println("Çıkmak için enter a bas.");
                    in.read();
                }
            }

        } else {
            out.println("Bu adreste veri tabanı yok");
            out.println("Yeni veri tabanı oluşturmak istermisin");
            if (Eminmisin()) {
                YeniVeriTabani();
            } else {
                out.println("Çıkmak için enter a bas.");
                in.read();
            }
        }
    }

    AdamAsmaca() throws IOException {

        String baslangicAdresi = getProperty("user.dir");
        File file = new File(baslangicAdresi + "\\kelime_veri_tabani.txt");
        String[] liste = {"araba", "zırh", "sofistik", "kelime", "pasaklı", "faktöriyel", "fişek", "pena", "kaval"};
        gecerliVeriYolu = file.getAbsolutePath();
        if (!file.exists()) {
            BoslukSpam();
            out.println("Fonksiyona veritabanı dizini göndermediğiniz için ben sizin yerinize bir tane oluşturuyorum..");
            out.println("Yeni veri tabanını şu klasörün altına kaydediyorum : " + file.getAbsolutePath() + "(\nProje klasörünün altında kelime_listesi adında oluşan dosya senin veri tabanının olacak oradan ekleme silme yapabilirsin.\nEğer gözükmüyorsa sağ tık menüsünden refresle)");//Bu yorum satırdan hatalar gelebilir ama şuan uğraşamam.
            file.createNewFile();
            FileWriter fw = new FileWriter(file, false);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < liste.length - 1; i++) {
                bw.write(liste[i]);
                bw.newLine();
            }
            bw.write(liste[liste.length - 1]);
            bw.close();
            VeriTabanindanKelimeListesiniDoldur(file.getAbsolutePath());
            AnaMenuSorgu();
        } else {
            gecerliVeriYolu = file.getAbsolutePath();
            VeriTabanindanKelimeListesiniDoldur(gecerliVeriYolu);
        }
    }

    private void VeriTabanindanKelimeListesiniDoldur(String path) throws IOException {
        File fl = new File(path);
        Scanner kelimeCekici = new Scanner(fl);
        kelimeListesi.clear();
        while (kelimeCekici.hasNextLine()) {
            kelimeListesi.add(kelimeCekici.nextLine());
        }
        kelimeCekici.close();
    }

    private void Sor() throws IOException {
        BoslukSpam();
        AdamCiz(kalanHak);
        out.println("                                                                               Kalan Hak : " + kalanHak);
        out.print("                                                                    ");
        for (Character character : bulunanHarfler) {

            if (character != null) {
                out.print(" " + character);
            } else out.print("  ");
        }
        out.println();
        out.print("                                                                    ");
        for (int i = 0; i < kelime.length(); i++) {
            out.print(" ¯");
        }
        out.println();
        if (kalanHak == 0) {
            if (!hile.herZamanKazan) {
                out.println("Oyun Bitti\nKaybettiniz\nKelime : " + kelime);
                OynananlaraYeniSatirEkle(kelime, false, 0);
                out.println("                                                                               Yeni Oyuna Başlamak için 'Y'  \n                                                                               Anamenü için 'A'yazıp enterlayın.");
                if (scn.next().equalsIgnoreCase("y")) {
                    out.println("                                                                               Yeni Oyun Başladı");
                    YeniOyun();
                } else AnaMenu();

            } else {
                OynananlaraYeniSatirEkle(kelime, true, 100);
            }
            AnaMenuSorgu();
        }
        out.print("                                                                               Harf Gir : ");
        String girilenMetin = scn.next();
        char girilenHarf = girilenMetin.toLowerCase().charAt(0);
        if (girilenMetin.equals("oyunu_kazan")) {
            hile.OyunuKazan();
        } else {
            if (girilenMetin.equals("reset")) {
                hile.Reset();
            } else if (girilenMetin.equals("kelimeyi_göster")) {
                hile.KelimeyiGoster();
            } else if (girilenMetin.toLowerCase().equals(kelime)) {
                OynananlaraYeniSatirEkle(kelime, true, (100 / kelime.length()) * (kelime.length() - kacHarfBulundu));
            } else if (girilenMetin.length() == 1) {
                if (kelime.toLowerCase().contains(Character.toString(girilenHarf).toLowerCase())) {
                    if (!Arrays.asList(bulunanHarfler).contains(girilenHarf)) {
                        for (int i = 0; i < kelime.length(); i++) {
                            if (kelime.toLowerCase().charAt(i) == Character.toString(girilenHarf).toLowerCase().charAt(0)) {
                                bulunanHarfler[i] = girilenHarf;
                                kacHarfBulundu++;
                            }
                        }
                    }
                    if (kacHarfBulundu == kelime.length()) {
                        out.println("                                                                               Oyun Bitti \n                                                                                Kazandınız.");
                        OynananlaraYeniSatirEkle(kelime, true, 10);
                        AdamCiz(9);
                        out.println("                                                                               Yeni Oyuna Başlamak için 'Y'  \n                                                                               Anamenü için 'A'yazıp enterlayın.");
                        if (scn.next().equalsIgnoreCase("y")) {
                            out.println("                                                                               Yeni Oyun Başladı");
                            YeniOyun();
                        } else AnaMenu();
                    } else Sor();
                } else {
                    kalanHak--;
                }
            }
            Sor();
        }
    }//Neredeyse tüm iş buradan dönüyor

    private void OynananlaraYeniSatirEkle(String kelime, Boolean kazandimi, int skor) {
        String metin = "Kelime : " + kelime + " ; ";
        if (kazandimi) {
            kazanilanOyunSayisi++;
            metin += "  Puan : " + skor + " ; Kazandın ";
        } else {
            kaybedilenOyunSayisi++;
            metin += " Kaybettin";
        }
        oynananKelimeler.add(metin);
    }

    private void OynananKelimeleriListele() throws IOException {
        BoslukSpam();
        if (oynananKelimeler.size() != 0) {
            out.println("                                                                   ----------------------Kelimeler---------------------");
            for (String s : oynananKelimeler) {
                out.println("                                                                           " + s);
            }
            out.println("                                                                   ----------------------------------------------------");
            out.println("                                                                               Kazanılan Oyun Sayısı : " + kazanilanOyunSayisi);
            out.println("                                                                               Kaybedilen Oyun Sayısı : " + kaybedilenOyunSayisi);
        } else {
            out.println("                                                                               Daha önce oyun oynamadın neyin peşindesin");
            AnaMenuSorgu();
        }
        AnaMenuSorgu();
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
        out.println("                                                                        <-------------ANA MENÜ----------->");
        out.println("                                                                        Yeni Oyun                  ----> 1");
        out.println("                                                                        Veritabanı Ayarları        ----> 2");
        out.println("                                                                        Oynanan Kelimeleri Listele ----> 3");
        out.println("                                                                        Çıkış                      ----> 4");
        CizgiSpam();
        String x = scn.nextLine();
        switch (x) {
            case "1" -> YeniOyun();
            case "2" -> ListeAyarlari();
            case "3" -> OynananKelimeleriListele();
            case "4" -> {
                out.println("                                                                        Oyundan çıkmak için 'C' yazın  (Ana menü için enter a bas)");
                String sonuc = scn.nextLine();
                if (sonuc.equalsIgnoreCase("c")) exit(0);
                else {
                    AnaMenu();
                }
            }
            case "sapıt" -> hile.Sapit();
            case "herzaman_kazan : açık" -> {
                hile.herZamanKazan = true;
                out.println("Her oyunu kazan : Açık");
                out.println("Enter ile devam et");
                in.read();
                AnaMenu();
            }
            case "herzaman_kazan : kapalı" -> {
                hile.herZamanKazan = false;
                out.println("Her oyunu kazan : Kapalı");
                out.println("Enter ile devam et");
                in.read();
                AnaMenu();
            }
            default -> AnaMenu();
        }
    }

    private void ListeAyarlari() throws IOException {
        BoslukSpam();
        out.println("                                                               1-->    Kelime veritabanı yolunu güncelle ");
        out.println("                                                               2-->    Geçerli veritabanına yeni kelime ekle");
        out.println("                                                               3-->    Yeni veri tabanı oluştur");
        out.println("                                                               4-->    Geçerli veritabanındaki kelimeleri listele");
        out.println("                                                               5-->    Ana menüye Dön");
        CizgiSpam();
        String x = scn.nextLine();
        switch (x) {
            case "1" -> VeriYoluGuncelle();
            case "2" -> VeriTabaninaKelimeEkle();
            case "3" -> YeniVeriTabani();
            case "4" -> KelimeleriListele();
            case "5" -> AnaMenu();

            default -> ListeAyarlari();
        }
    }

    private void KelimeleriListele() throws IOException {
        BoslukSpam();
        File file = new File(gecerliVeriYolu);
        var scanner = new Scanner(file);
        out.println("                                                                          -----------KELİMELER------------");
        String x;
        while (scanner.hasNextLine()) {
            x = scanner.next();
            int a = (30 - x.length()) / 2;
            int b;
            if (x.length() % 2 == 0) {
                b = a;
            } else b = a + 1;

            out.println("                                                                          " + "|" + " ".repeat(a) + x + " ".repeat(b) + "|");
        }
        scanner.close();
        out.println("                                                                          " + "--------------------------------");
        out.println("                                                                          " + "Toplam Kelime Sayısı : " + kelimeListesi.size());
        out.println("                                                                          " + "--------------------------------");
        out.println("                                                                          " + "Veri tabanı konumu : " + gecerliVeriYolu);
        AnaMenuSorgu();
    }

    private void CizgiSpam() {
        out.println("                                                                                     |     ");
        out.println("                                                                                     |     ");
        out.println("                                                                                     |     ");
        out.print("                                                                                     |-->");
    }

    private void VeriTabaninaKelimeEkle() throws IOException {

        VeriTabaninaYaz(gecerliVeriYolu, true);
        AnaMenuSorgu();
    }

    private void VeriTabaninaYaz(String dosyaYolu, Boolean AppendMode) throws IOException {
        BoslukSpam();
        File fl = new File(dosyaYolu);
        FileWriter fw = new FileWriter(fl, AppendMode);
        BufferedWriter bw = new BufferedWriter(fw);
        List<String> veriTabanindakiKelimeler = new ArrayList<>();
        Scanner kelimeCekici = new Scanner(fl);
        while (kelimeCekici.hasNextLine()) {
            veriTabanindakiKelimeler.add(kelimeCekici.nextLine());
        }
        kelimeCekici.close();
        while (true) {
            BoslukSpam();
            out.println("'İşlemi_Sonlandır' yazarak işlemi sonlandırabilirsin.");
            out.print("Yeni kelime : ");
            String yeniKelime = scn.next();
            if (yeniKelime.equals("İşlemi_Sonlandır")) {
                bw.close();
                break;
            } else {
                if (!veriTabanindakiKelimeler.contains(yeniKelime.toLowerCase())) {
                    if (AppendMode) {
                        bw.newLine();
                        bw.write(yeniKelime);
                    } else {
                        bw.write(yeniKelime);
                        bw.newLine();
                    }
                    veriTabanindakiKelimeler.add(yeniKelime);
                }
            }
        }
        kelimeListesi = veriTabanindakiKelimeler;
        out.println("İşlem tamamlandı");
    }

    private void YeniVeriTabani() throws IOException {
        out.print("Oluşturulacak Veritabnının ismi ne olsun : ");
        String dosyaAdi = scn.nextLine();
        out.print("Veri tabanını nereye kaydetmek istiyorsunuz (Örnek : C:/users/xxxx/desktop) : ");
        String y = scn.nextLine();
        String x = y + dosyaAdi + ".txt";
        File f = new File(x);
        if (f.exists()) {
            out.println("Belirtilen adreste zaten bir dosya mevcut");
            out.println("1-->Dosyanın içeriğini silip yeniden doldur");
            out.println("2-->Dosyanın içeriğinin üzerine veri ekle");
            out.println("3-->Yeni Dosya yolu gir");
            out.println("4-->İptal et ve anamenüye dön");
            out.println("----------->");
            String a = scn.nextLine();
            switch (a) {
                case "1" -> VeriTabaninaYaz(x, false);
                case "2" -> VeriTabaninaYaz(x, true);
                case "3" -> YeniVeriTabani();
                case "4" -> AnaMenuSorgu();
            }
            AnaMenuSorgu();
        } else {
            f.createNewFile();
            VeriTabaninaYaz(x, false);

            AnaMenuSorgu();
        }
    }

    private void VeriYoluGuncelle() throws IOException {
        out.print("Yeni veritabanı yolunu giriniz : ");
        String yeniVeriYolu = scn.nextLine();
        File yeniVeriTabani = new File(yeniVeriYolu);
        if (!yeniVeriTabani.exists()) {
            out.println("Belirtilen konumda böyle bir dosya yok");
        } else {
            List<String> yeniKelimeListesi = new ArrayList<>();
            Scanner kelimeCekici = new Scanner(yeniVeriTabani);
            while (kelimeCekici.hasNextLine()) {
                yeniKelimeListesi.add(kelimeCekici.nextLine());
            }
            if (kelimeListesi.size() == 0) {
                out.println("Geçerli veritabanı dosyası içerisinde herhangi bir kelime yok");
            } else {
                out.println("Veritabanı bulundu.\nKelime Adedi : " + yeniKelimeListesi.size());
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
            BoslukSpam();
            out.println("Veri tabanında yeni kelime kalmadı");
            out.println("Çıkış yapmak için ç yazın");
            out.println("Yeni kelime eklemek için y yazın");
            String sonuc = scn.next();
            if (sonuc.equalsIgnoreCase("y")) {
                VeriTabaninaKelimeEkle();
            } else if (sonuc.equalsIgnoreCase("ç")) exit(0);
            else YeniKelimeCek();
        }
    }//Kelime listesinden daha önce oynanmamış bir kelime çeker.

    private boolean Eminmisin() {
        Boolean sonuc = null;

        while (true) {
            out.println("(EVET/HAYIR giriniz)");
            String x = scn.nextLine().toUpperCase();
            if (x.equals("EVET")) {
                sonuc = true;
                break;
            } else if (x.equals("HAYIR")) {
                sonuc = false;
                break;
            }
            BoslukSpam();
            out.println("Gerçekten ne yazman gerektiğini çözemedinmi ? (๑•̀ㅂ•́)و✧");
        }
        return sonuc;
    }

    private void BoslukSpam() {
        out.println("                                                                                |                 |                                                                                ");
        out.println("                                                                                |                 |                                                                                ");
        out.println("                                                                                |                 |                                                                                ");
        out.println("                                                                                |                 |                                                                                ");
        out.println("                                                                                |                 |                                                                                ");
        out.println("                                                                                |                 |                                                                                ");
        out.println("                                                                                |                 |                                                                                ");
        out.println("                                                                                |                 |                                                                                ");
        out.println("                                                                                |                 |                                                                                ");
        out.println("                                                                                |                 |                                                                                ");
        out.println("                                                                                |                 |                                                                                ");
        out.println("                                                                                |                 |                                                                                ");
        out.println("                                                                                |                 |-------------------------------------------------------------------------------|");
        out.println("                                                                                |                                                                                                 |");
        out.println("                                                                                |                                                                                                 |");
        out.println("                                                                                |                                                                                                 |");
        out.println("                                                                                |                                                                                                 |");
        out.println("                                                                                |--------------------------------------------------------------------------------|                |");
        out.println("                                                                                                                                                                 |                |");
        out.println("                                                                                                                                                                 |                |");
        out.println("                                                                                                                                                                 |                |");
        out.println("                                                                                                                                                                 |                |");
        out.println("                                                                                                                                                                 |                |");
        out.println("                                                                                                                                                                 |                |");
        out.println("                                                                                                                                                                 |                |");
        out.println("                                                                                                                                                                 |                |");
        out.println("                                                                                                                                                                 |                |");
        out.println("                                                                                                                                                                 |                |");
        out.println("|----------------------------------------------------------------------------------------------------------------------------------------------------------------|                |");
        out.println("|                                                                                                                                                                                 |");
        out.println("|                                                                                                                                                                                 |");
        out.println("|                                                                                                                                                                                 |");
        out.println("|                                                                                                                                                                                 |");
        out.println("|               |-----------------------------------------------------------------------------------------------------------------------------------------------------------------|");
        out.println("|               |                                                                                                                                                                  ");
        out.println("|               |                                                                                                                                                                  ");
        out.println("|               |                                                                                                                                                                  ");
        out.println("|               |                                                                                                                                                                  ");
        out.println("|               |                                                                                                                                                                  ");
        out.println("|               |                                                                                                                                                                  ");
        out.println("|               |                                                                                                                                                                  ");
        out.println("|               |                                                                                                                                                                  ");
        out.println("|               |                                                                                                                                                                  ");
        out.println("|               |----------------------------------------------------------------------------------                                                                                ");
        out.println("|                                                                                                 |                                                                                ");
        out.println("|                                                                                                 |                                                                                ");
        out.println("|                                                                                                 |                                                                                ");
        out.println("|                                                                                                 |                                                                                ");
        out.println("|---------------------------------------------------------------------------------                |                                                                                ");
        out.println("                                                                                |                 |                                                                                ");
        out.println("                                                                                |                 |                                                                                ");
        out.println("                                                                                |                 |                                                                                ");
        out.println("                                                                                |                 |                                                                                ");
        out.println("                                                                                \\                /                                                                                ");
        out.println("                                                                                 \\              /                                                                                 ");
        out.println("                                                                                  \\            /                                                                                  ");
        out.println("                                                                                   \\          /                                                                                   ");
        out.println("                                                                                    \\        /                                                                                    ");
        out.println("                                                                                     \\      /                                                                                     ");
        out.println("                                                                                      \\    /                                                                                      ");
        out.println("                                                                                       \\  /                                                                                       ");
        out.println("                                                                                        \\/                                                                                        ");
        out.println("                                                                                                                                                                                   ");
        out.println("                                                                                                                                                                                   ");
        out.println("                                                                                                                                                                                   ");
        out.println("                                                                                                                                                                                   ");
        out.println("                                                                                                                                                                                   ");
    }

    private void AnaMenuSorgu() throws IOException {
        out.println();
        out.println();
        out.println("                                                                          " + "Ana menüye gitmek için enter a basın");
        in.read();
        AnaMenu();
    }

    private void AdamCiz(int i) {
        switch (i) {
            case 0 -> {
                out.println("                                                                __________________________________________");
                out.println("                                                                |               _______                  |");
                out.println("                                                                |               |     |                  |");
                out.println("                                                                |              \\0/    |                  |");
                out.println("                                                                |               |     |                  |");
                out.println("                                                                |              / \\    |                  |");
                out.println("                                                                |                     |                  |");
                out.println("                                                                |              --------                  |");
                out.println("                                                                |________________________________________|");
            }
            case 1 -> {

                out.println("                                                                __________________________________________");
                out.println("                                                                |               _______                  |");
                out.println("                                                                |                     |                  |");
                out.println("                                                                |              \\0/    |                  |");
                out.println("                                                                |               |     |                  |");
                out.println("                                                                |              / \\    |                  |");
                out.println("                                                                |                     |                  |");
                out.println("                                                                |              --------                  |");
                out.println("                                                                |________________________________________|");
            }
            case 2 -> {

                out.println("                                                                __________________________________________");
                out.println("                                                                |               _______                  |");
                out.println("                                                                |                     |                  |");
                out.println("                                                                |              \\0/    |                  |");
                out.println("                                                                |               |     |                  |");
                out.println("                                                                |                     |                  |");
                out.println("                                                                |                     |                  |");
                out.println("                                                                |              --------                  |");
                out.println("                                                                |________________________________________|");
            }
            case 3 -> {
                out.println("                                                                __________________________________________");
                out.println("                                                                |               _______                  |");
                out.println("                                                                |                     |                  |");
                out.println("                                                                |              \\0/    |                  |");
                out.println("                                                                |                     |                  |");
                out.println("                                                                |                     |                  |");
                out.println("                                                                |                     |                  |");
                out.println("                                                                |              --------                  |");
                out.println("                                                                |________________________________________|");
            }
            case 4 -> {
                out.println("                                                                __________________________________________");
                out.println("                                                                |               _______                  |");
                out.println("                                                                |                     |                  |");
                out.println("                                                                |               0     |                  |");
                out.println("                                                                |                     |                  |");
                out.println("                                                                |                     |                  |");
                out.println("                                                                |                     |                  |");
                out.println("                                                                |              --------                  |");
                out.println("                                                                |________________________________________|");
            }
            case 5 -> {
                out.println("                                                                __________________________________________");
                out.println("                                                                |               _______                  |");
                out.println("                                                                |                     |                  |");
                out.println("                                                                |                     |                  |");
                out.println("                                                                |                     |                  |");
                out.println("                                                                |                     |                  |");
                out.println("                                                                |                     |                  |");
                out.println("                                                                |              --------                  |");
                out.println("                                                                |________________________________________|");
            }
            case 6 -> {

                out.println("                                                                __________________________________________");
                out.println("                                                                |                                        |");
                out.println("                                                                |                     |                  |");
                out.println("                                                                |                     |                  |");
                out.println("                                                                |                     |                  |");
                out.println("                                                                |                     |                  |");
                out.println("                                                                |                     |                  |");
                out.println("                                                                |              --------                  |");
                out.println("                                                                |________________________________________|");
            }
            case 7 -> {
                out.println("                                                                __________________________________________");
                out.println("                                                                |                                        |");
                out.println("                                                                |                                        |");
                out.println("                                                                |                                        |");
                out.println("                                                                |                                        |");
                out.println("                                                                |                                        |");
                out.println("                                                                |                                        |");
                out.println("                                                                |              --------                  |");
                out.println("                                                                |________________________________________|");
            }
            case 8 -> {
                out.println("                                                                __________________________________________");
                out.println("                                                                |                                        |");
                out.println("                                                                |                                        |");
                out.println("                                                                |                                        |");
                out.println("                                                                |                                        |");
                out.println("                                                                |                                        |");
                out.println("                                                                |                                        |");
                out.println("                                                                |                                        |");
                out.println("                                                                |________________________________________|");
            }
            case 9 -> {
                out.println("                                                                __________________________________________");
                out.println("                                                                |                                        |");
                out.println("                                                                |                                        |");
                out.println("                                                                |          KAZANDIN            O    /    |");
                out.println("                                                                |   --------------------->    /|\\ /      |");
                out.println("                                                                |   --------------------->   / |         |");
                out.println("                                                                |   --------------------->    / \\        |");
                out.println("                                                                |                             |  |       |");
                out.println("                                                                |________________________________________|");
            }
        }
    }

    class Hile {
        public boolean herZamanKazan = false;

        public void Sapit() {
            while (true) {
                Random x = new Random();
                AdamCiz(x.nextInt(9));
                for (int i = 0; i < x.nextInt(5); i++) {
                    BoslukSpam();
                }
                for (int i = 0; i < x.nextInt(5); i++) {
                    CizgiSpam();
                }
            }
        }

        public void OyunuKazan() throws IOException {
            OynananlaraYeniSatirEkle(kelime, true, 100);
            out.println("Cheat activated");
            AnaMenuSorgu();
        }

        public void Reset() {
            kalanHak = 8;
        }

        public void KelimeyiGoster() throws IOException {
            out.println();
            out.println();
            out.println();
            out.println("                                                                           Şuan Oynanan Kelime : " + kelime);
            out.println("                                                                           Hadi kimse görmeden enter a bas. ");
            in.read();
        }
    }
}
