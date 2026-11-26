package kutuphane;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class DosyaIslemleri {
    private static final String UYELER_DOSYA = "uyeler.txt";
    private static final String KITAPLAR_DOSYA= "kitaplar.txt";
    private static final String KOPYALAR_DOSYA = "kopyalar.txt";
    private static final String ODUNCLER_DOSYA = "oduncler.txt";
    private static final String TALEPLER_DOSYA = "talepler.txt";
    
    public void kaydet(Kutuphane k){
        if(k==null){
            return;
        }
        uyeleriKaydet(k.getUyeler());
        kitaplariVeKopyalariKaydet(k.getKitaplar());
        oduncleriKaydet(k.getOduncler());
        talepleriKaydet(k);
    }
    public void yukle(Kutuphane k){
        if(k==null){
            return;
        }
        uyeleriYukle(k.getUyeler());
        kitaplariYukle(k.getKitaplar());
        kopyalariYukle(k.getKitaplar());
        oduncleriYukle(k.getOduncServisi());
        talepleriYukle(k.getOduncServisi());
        k.getSorgu().indeksleriYenile();
    }
    private void uyeleriKaydet(UyeListesi uyeler){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(UYELER_DOSYA))){
            Uye mevcut = uyeler.getBas();
            while (mevcut != null){
                bw.write(mevcut.getUyeNo() + ";" + mevcut.getAdSoyad());
                bw.newLine();
                mevcut = mevcut.sonraki;
            }
        } catch (IOException e){
            System.err.println("Üyeler kaydedilemedi: " + e.getMessage());
        }
    }
    private void kitaplariVeKopyalariKaydet(KitapListesi kitaplar){
        try (BufferedWriter bwKitap = new BufferedWriter(new FileWriter(KITAPLAR_DOSYA));
             BufferedWriter bwKopya = new BufferedWriter(new FileWriter(KOPYALAR_DOSYA))) {

            Kitap k = kitaplar.getBas();
            while (k != null){
                bwKitap.write(k.getKitapNo() + ";" + k.getAd() + ";" + k.getYazar() + ";" + k.getYayinYili());
                bwKitap.newLine();

                KitapKopyasi kopya = k.getIlkKopya();
                while (kopya != null) {
                    bwKopya.write(kopya.getKopyaNo() + ";" + kopya.getKitapNo() + ";"
                            + kopya.getDurum() + ";" + kopya.getAyrilanUyeNo());
                    bwKopya.newLine();
                    kopya = kopya.sonraki;
                }
                k = k.sonraki;
            }
        } catch (IOException e){
            System.err.println("Kitaplar ve kopyalar kaydedilemedi: " + e.getMessage());
        }
    }
    private void oduncleriKaydet(OduncListesi oduncler) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ODUNCLER_DOSYA))) {
            Odunc o = oduncler.getBas();
            while (o != null) {
                bw.write(o.getOduncId() + ";" + o.getKopyaNo() + ";" + o.getUyeNo() + ";"
                        + o.getKitapNo() + ";" + o.getBeklenenIade() + ";" + o.isIadeEdildi());
                bw.newLine();
                o = o.sonraki;
            }
        } catch (IOException e) {
            System.err.println("Ödünçler kaydedilemedi: " + e.getMessage());
        }
    }

    private void talepleriKaydet(Kutuphane k) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TALEPLER_DOSYA))) {
            Kitap kitap = k.getKitaplar().getBas();
            while (kitap != null) {
                KitapKopyasi kopya = kitap.getIlkKopya();
                while (kopya != null) {
                    Kuyruk kuyruk = k.getOduncServisi().kuyrukGetir(kopya.getKopyaNo());
                    if (kuyruk != null) {
                        BeklemeTalebi talep = kuyruk.ilkiGoster();
                        while (talep != null) {
                            bw.write(talep.getTalepId() + ";" + talep.getUyeNo() + ";"
                                    + talep.getKitapNo() + ";" + talep.getKopyaNo());
                            bw.newLine();
                            talep = talep.sonraki;
                        }
                    }
                    kopya = kopya.sonraki;
                }
                kitap = kitap.sonraki;
            }
        } catch (IOException e) {
            System.err.println("Talepler kaydedilemedi: " + e.getMessage());
        }
    }
    private void uyeleriYukle(UyeListesi uyeler){
        File dosya = new File(UYELER_DOSYA);
        if(!dosya.exists()){
            return; 
        }
        try (BufferedReader br = new BufferedReader(new FileReader(dosya))){
            String satir;
            while((satir=br.readLine()) != null){
                if(satir.trim().isEmpty()){
                    continue;
                }
                String[] parca= satir.split(";");
                int uyeNo = Integer.parseInt(parca[0]);
                String adSoyad = parca[1];
                uyeler.uyeKaydet(uyeNo,adSoyad);
            }
        }catch (IOException e){
            System.err.println("Üyeler yüklenmedi: "+e.getMessage());
        }
    }
    private void kitaplariYukle(KitapListesi kitaplar) {
        File dosya = new File(KITAPLAR_DOSYA);
        if(!dosya.exists()){
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(dosya))){
            String satir;
            while((satir=br.readLine())!=null){
                if(satir.trim().isEmpty()){
                    continue;
                }
                String[] parca = satir.split(";");
                int kitapNo = Integer.parseInt(parca[0]);
                String ad = parca[1];
                String yazar = parca[2];
                int yayinYili = Integer.parseInt(parca[3]);
                kitaplar.kitapEkle(kitapNo,ad,yazar,yayinYili);
            }
        } catch (IOException e){
            System.err.println("Kitaplar yüklenmedi: "+e.getMessage());
        }
    }
    private void kopyalariYukle(KitapListesi kitaplar){
        File dosya = new File(KOPYALAR_DOSYA);
        if(!dosya.exists()){
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(dosya))){
            String satir;
            while((satir = br.readLine())!=null){
                if(satir.trim().isEmpty()){
                    continue;
                }
                String[] parca = satir.split(";");
                int kopyaNo = Integer.parseInt(parca[0]);
                int kitapNo = Integer.parseInt(parca[1]);
                String durum = parca[2];
                int ayrilanUyeNo = Integer.parseInt(parca[3]);
                
                kitaplar.kopyaEkle(kitapNo, kopyaNo);
                KitapKopyasi kopya = kitaplar.kopyaBul(kopyaNo);
                if (kopya != null) {
                    kopya.setDurum(durum);
                    kopya.setAyrilanUyeNo(ayrilanUyeNo);
            }
        }
    }catch(IOException e){
        System.err.println("Kopyalar yüklenmedi: "+e.getMessage());
    }
}
    private void oduncleriYukle(OduncServisi oduncServisi) {
        File dosya = new File(ODUNCLER_DOSYA);
        if (!dosya.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(dosya))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                if (satir.trim().isEmpty()) {
                    continue;
                }
                String[] parca = satir.split(";");
                int oduncId = Integer.parseInt(parca[0]);
                int kopyaNo = Integer.parseInt(parca[1]);
                int uyeNo = Integer.parseInt(parca[2]);
                int kitapNo = Integer.parseInt(parca[3]);
                LocalDate beklenenIade = LocalDate.parse(parca[4]);
                boolean iadeEdildi = Boolean.parseBoolean(parca[5]);

                Odunc o = new Odunc(oduncId, kopyaNo, uyeNo, kitapNo, beklenenIade);
                if (iadeEdildi) {
                    o.iadeEt();
                }
                oduncServisi.dosyadanOduncEkle(o);
            }
        } catch (IOException e) {
            System.err.println("Ödünçler yüklenemedi: " + e.getMessage());
        }
    }

    private void talepleriYukle(OduncServisi oduncServisi) {
        File dosya = new File(TALEPLER_DOSYA);
        if (!dosya.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(dosya))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                if (satir.trim().isEmpty()) {
                    continue;
                }
                String[] parca = satir.split(";");
                int talepId = Integer.parseInt(parca[0]);
                int uyeNo = Integer.parseInt(parca[1]);
                int kitapNo = Integer.parseInt(parca[2]);
                int kopyaNo = Integer.parseInt(parca[3]);

                BeklemeTalebi talep = new BeklemeTalebi(talepId, uyeNo, kitapNo, kopyaNo);
                oduncServisi.dosyadanTalepEkle(talep);
            }
        } catch (IOException e) {
            System.err.println("Talepler yüklenemedi: " + e.getMessage());
        }
    }
}
