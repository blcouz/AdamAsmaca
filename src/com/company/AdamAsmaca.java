package com.company;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("ALL")
public class AdamAsmaca {

    private Hile hile = new Hile();
    private String baslangicAdresi = System.getProperty("user.dir");
    private int kazanilanOyunSayisi = 0;
    private int kaybedilenOyunSayisi = 0;
    private List<String> oynananKelimeler = new ArrayList<>();
    private List<String> kelimeListesi = new ArrayList<>();
    private String gecerliVeriYolu;
    private final Scanner scn = new Scanner(System.in);
    private int kalanHak = 8;
    private String kelime;
    private Character[] bulunanHarfler;
    private int kacHarfBulundu = 0;

    AdamAsmaca(String path) throws IOException {
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
                System.out.print("Bu veri tabanı boş yeni veri eklemek istermisin : ");
                if (Eminmisin()) {
                    VeriTabaninaKelimeEkle();
                } else {
                    System.out.println("Çıkmak için enter a bas.");
                    System.in.read();
                }
            }

        } else {
            System.out.println("Bu adreste veri tabanı yok");
            System.out.println("Yeni veri tabanı oluşturmak istermisin");
            if (Eminmisin()) {
                YeniVeriTabani();
            } else {
                System.out.println("Çıkmak için enter a bas.");
                System.in.read();
            }
        }
    }

    AdamAsmaca() throws IOException {

        File file = new File(baslangicAdresi + "\\kelime_veri_tabani.txt");
        String[] liste = {"araba", "zırh", "sofistik", "kelime", "pasaklı", "faktöriyel", "fişek", "pena", "kaval"};
        gecerliVeriYolu = baslangicAdresi;
        if (!file.exists()) {
            System.out.println("Fonksiyona veritabanı dizini göndermediğiniz için ben sizin yerinize bir tane oluşturuyorum..");
            System.out.println("Yeni veri tabanını şu klasörün altına kaydediyorum : " + file.getAbsolutePath() + "(\nEğer ide üzerinden çalıştırıyorsan ki eminim oradan çalıştırıyorsun proje klasörünün altında kelime_listesi (sola bak. ekranın solu) adında oluşan dosya senin veri tabanının olacak oradan ekleme silme yapabilirsin.)");//Bu yorum satırdan hatalar gelebilir ama şuan uğraşamam.
            file.createNewFile();
            FileWriter fw = new FileWriter(file, false);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < liste.length - 1; i++) {
                bw.write(liste[i]);
                bw.newLine();
            }
            bw.write(liste[liste.length - 1]);
            bw.close();
            VeriTabanındanKelimeListesiniDoldur(file.getAbsolutePath());
            AnaMenuSorgu();
        } else {
            gecerliVeriYolu = file.getAbsolutePath();
            VeriTabanındanKelimeListesiniDoldur(gecerliVeriYolu);
        }
    }

    private void VeriTabanındanKelimeListesiniDoldur(String path) throws IOException {
        File fl = new File(path);
        Scanner kelimeCekici = new Scanner(fl);
        kelimeListesi.clear();
        while (kelimeCekici.hasNextLine()) {
            kelimeListesi.add(kelimeCekici.nextLine());
        }
        kelimeCekici.close();
        AnaMenu();
    }

    private void Sor() throws IOException {
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
            if (!hile.herZamanKazan) {
                System.out.println("Oyun Bitti\nKaybettiniz\nKelime : " + kelime);
                OynananlaraYeniSatirEkle(kelime, false);

            } else {
                OynananlaraYeniSatirEkle(kelime, true);
            }
            AnaMenuSorgu();
        }
        System.out.print("Harf Gir : ");
        String girilenMetin = scn.next();
        Character girilenHarf = girilenMetin.toLowerCase().charAt(0);
        if (girilenMetin.equals("oyunu_kazan")) {
            hile.OyunuKazan();
        } else {
            if (girilenMetin.equals("reset")) {
                hile.Reset();
            } else if (girilenMetin.equals("kelimeyi_göster")) {
                hile.KelimeyiGoster();
            } else if (kelime.toLowerCase().contains(girilenHarf.toString().toLowerCase())) {
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
                    OynananlaraYeniSatirEkle(kelime, true);
                    BoslukSpam();
                    AdamCiz(9);
                    System.out.println("Yeni Oyuna Başlamak için 'Y'  \nAnamenü için 'A'yazıp enterlayın.");
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
    }//Neredeyse tüm iş buradan dönüyor

    private void OynananlaraYeniSatirEkle(String kelime, Boolean kazandimi) {
        String metin = "Kelime : " + kelime + " : ";
        if (kazandimi) {
            kazanilanOyunSayisi++;
            metin += "Kazandın";
        } else {
            kaybedilenOyunSayisi++;
            metin += "Kaybettin";
        }
        oynananKelimeler.add(metin);
    }

    private void OynananKelimeleriListele() throws IOException {
        BoslukSpam();
        if (oynananKelimeler.size() != 0) {
            System.out.println("----------Kelimeler---------");
            for (int i = 0; i < oynananKelimeler.size(); i++) {
                System.out.println("     " + oynananKelimeler.get(i));
            }
            System.out.println("----------------------------");
            System.out.println("Kazanılan Oyun Sayısı : " + kazanilanOyunSayisi);
            System.out.println("Kaybedilen Oyun Sayısı : " + kaybedilenOyunSayisi);
        } else {
            System.out.println("Daha önce oyun oynamadın neyin peşindesin");
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
        System.out.println("<-------------ANA MENÜ----------->");
        System.out.println("Yeni Oyun                  ----> 1");
        System.out.println("Veritabanı Ayarları        ----> 2");
        System.out.println("Oynanan Kelimeleri Listele ----> 3");
        System.out.println("Çıkış                      ----> 4");
        CizgiSpam();
        String x = scn.nextLine();
        switch (x) {
            case "1" -> {
                YeniOyun();
            }
            case "2" -> {
                ListeAyarlari();
            }
            case "3" -> {
                OynananKelimeleriListele();
            }
            case "4" -> {
                System.out.println("Oyundan çıkmak için 'C' yazın c harici (Ana menü için enter a bas)");
                String sonuc = scn.nextLine();
                if (sonuc.toLowerCase().equals("c")) {
                    System.exit(0);
                } else {
                    AnaMenu();
                }
            }
            case "sapıt" -> {
                hile.Sapit();
            }
            case "herzaman_kazan : açık" -> {
                hile.herZamanKazan = true;
                System.out.println("Her oyunu kazan : Açık");
                System.out.println("Enter ile devam et");
                System.in.read();
                AnaMenu();
            }
            case "herzaman_kazan : kapalı" -> {
                hile.herZamanKazan = false;
                System.out.println("Her oyunu kazan : Kapalı");
                System.out.println("Enter ile devam et");
                System.in.read();
                AnaMenu();
            }
            default -> {
                AnaMenu();
            }
        }
    }

    private void ListeAyarlari() throws IOException {
        BoslukSpam();
        System.out.println("1-->    Kelime veritabanı yolunu güncelle ");
        System.out.println("2-->    Geçerli veritabanına yeni kelime ekle");
        System.out.println("3-->    Yeni veri tabanı oluştur");
        System.out.println("4-->    Geçerli veritabanındaki kelimeleri listele");
        System.out.println("5-->    Ana menüye Dön");
        CizgiSpam();
        String x = scn.nextLine();
        switch (x) {
            case "1" -> {
                VeriYoluGuncelle();
            }
            case "2" -> {
                VeriTabaninaKelimeEkle();
            }
            case "3" -> {
                YeniVeriTabani();
            }
            case "4" -> {
                KelimeleriListele();
            }
            case "5" -> {
                AnaMenu();
            }

            default -> {
                ListeAyarlari();
            }
        }
    }

    private void KelimeleriListele() throws IOException {
        BoslukSpam();
        File file = new File(gecerliVeriYolu);
        Scanner scanner = new Scanner(file);
        System.out.println("-----------KELİMELER----------");
        for (int i = 0; i < kelimeListesi.size(); i++) {
            System.out.println("|             " + kelimeListesi.get(i));
        }
        System.out.println("------------------------------");
        System.out.println("Toplam Kelime Sayısı : " + kelimeListesi.size());
        System.out.println("------------------------------");
        System.out.println("Veri tabanı konumu : " + gecerliVeriYolu);
        AnaMenuSorgu();
    }

    private void CizgiSpam() {
        for (int i = 0; i < 5; i++) {
            System.out.println("|");
        }
        System.out.print("---------------> ");
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
            System.out.println("'İşlemi_Sonlandır' yazarak işlemi sonlandırabilirsin.");
            System.out.print("Yeni kelime : ");
            String yeniKelime = scn.nextLine();
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
        System.out.println("İşlem tamamlandı");
    }

    private void YeniVeriTabani() throws IOException {
        System.out.print("Oluşturulacak Veritabnının ismi ne olsun : ");
        String dosyaAdi = scn.nextLine();
        System.out.print("Veri tabanını nereye kaydetmek istiyorsunuz (Örnek : C:/users/xxxx/desktop) : ");
        String y = scn.nextLine();
        String x = y + dosyaAdi + ".txt";
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
                    VeriTabaninaYaz(x, false);
                    break;
                }
                case "2": {
                    VeriTabaninaYaz(x, true);
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
        } else {
            f.createNewFile();
            VeriTabaninaYaz(x, false);

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
    }//Kelime listesinden daha önce oynanmamış bir kelime çeker.

    private boolean Eminmisin() {
        Boolean sonuc = null;

        while (true) {
            System.out.println("Bu işlemi gerçekleştirmek istediğine eminmisin(EVET/HAYIR giriniz)");
            String x = scn.nextLine().toUpperCase();
            if (x.equals("EVET")) {
                sonuc = true;
                break;
            } else if (x.equals("HAYIR")) {
                sonuc = false;
                break;
            }
            System.out.println("Gerçekten ne yazman gerektiğini çözemedinmi ?");
            BoslukSpam();
        }
        return sonuc;
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
            case 0 -> {
                System.out.println("  __________________________________________");
                System.out.println("  |               _______                  |");
                System.out.println("  |               |     |                  |");
                System.out.println("  |              \\0/    |                  |");
                System.out.println("  |               |     |                  |");
                System.out.println("  |              / \\    |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |              --------                  |");
                System.out.println("  |________________________________________|");
            }
            case 1 -> {

                System.out.println("  __________________________________________");
                System.out.println("  |               _______                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |              \\0/    |                  |");
                System.out.println("  |               |     |                  |");
                System.out.println("  |              / \\    |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |              --------                  |");
                System.out.println("  |________________________________________|");
            }
            case 2 -> {

                System.out.println("  __________________________________________");
                System.out.println("  |               _______                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |              \\0/    |                  |");
                System.out.println("  |               |     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |              --------                  |");
                System.out.println("  |________________________________________|");
            }
            case 3 -> {
                System.out.println("  __________________________________________");
                System.out.println("  |               _______                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |              \\0/    |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |              --------                  |");
                System.out.println("  |________________________________________|");
            }
            case 4 -> {
                System.out.println("  __________________________________________");
                System.out.println("  |               _______                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |               0     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |              --------                  |");
                System.out.println("  |________________________________________|");
            }
            case 5 -> {
                System.out.println("  __________________________________________");
                System.out.println("  |               _______                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |              --------                  |");
                System.out.println("  |________________________________________|");
            }
            case 6 -> {

                System.out.println("  __________________________________________");
                System.out.println("  |                                        |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |                     |                  |");
                System.out.println("  |              --------                  |");
                System.out.println("  |________________________________________|");
            }
            case 7 -> {
                System.out.println("  __________________________________________");
                System.out.println("  |                                        |");
                System.out.println("  |                                        |");
                System.out.println("  |                                        |");
                System.out.println("  |                                        |");
                System.out.println("  |                                        |");
                System.out.println("  |                                        |");
                System.out.println("  |              --------                  |");
                System.out.println("  |________________________________________|");
            }
            case 8 -> {
                System.out.println("  __________________________________________");
                System.out.println("  |                                        |");
                System.out.println("  |                                        |");
                System.out.println("  |                                        |");
                System.out.println("  |                                        |");
                System.out.println("  |                                        |");
                System.out.println("  |                                        |");
                System.out.println("  |                                        |");
                System.out.println("  |________________________________________|");
            }
            case 9 -> {
                System.out.println("  __________________________________________");
                System.out.println("  |                                        |");
                System.out.println("  |                                        |");
                System.out.println("  |      KAZANDIN                O         |");
                System.out.println("  |   ---------------------->   /|\\        |");
                System.out.println("  |   ---------------------->    |         |");
                System.out.println("  |   ---------------------->   / \\        |");
                System.out.println("  |                                        |");
                System.out.println("  |________________________________________|");
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
            OynananlaraYeniSatirEkle(kelime, true);
            System.out.println("Cheat activated");
            AnaMenuSorgu();
        }

        public void Reset() {
            kalanHak = 8;
        }

        public void KelimeyiGoster() throws IOException {
            System.out.println("Şuan Oynanan Kelime : " +  kelime);
            System.out.println("Hadi kimse görmeden enter a bas. ");
            System.in.read();
        }
    }
}
