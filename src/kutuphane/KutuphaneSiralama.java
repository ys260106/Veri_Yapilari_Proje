package kutuphane;

public class KutuphaneSiralama {
    // İki String'i alfabetik karşılaştırır
    // Negatif: a önce, 0: eşit, Pozitif: a sonra

    private int metinKarsilastir(String a, String b) {
        // Null kontrolleri: null olan küçük sayılır, hata vermez
        if (a == null && b == null) {
            return 0;
        }
        if (a == null) {
            return -1;
        }
        if (b == null) {
            return 1;
        }
         // Kısa olan kelimenin uzunluğu kadar karşılaştır (taşmayı önler)
        int minUzunluk = a.length() < b.length() ? a.length() : b.length();
        for (int i = 0; i < minUzunluk; i++) {
            char c1 = a.charAt(i);
            char c2 = b.charAt(i);
            // İlk farklı harfte karar ver (karakter kodu farkı)
            if (c1 != c2) {
                return c1 - c2;
            }
        }
         // Harfler aynıysa kısa olan önce gelir
        return a.length() - b.length();
    }

    // 1. Kabarcık Sıralama (Bubble Sort) - Kitapları adına göre sıralar
    public void kitaplariAdaGoreSirala(Kitap[] d) {
        if (d == null) { // dizi yoksa çık
            return;
        }
        int n = d.length;
         // Dış döngü: tur sayısı (n-1 tur yeter)
        for (int i = 0; i < n - 1; i++) {
             // İç döngü: komşuları karşılaştır
            // Sondaki i eleman zaten yerinde, o yüzden n-i-1
            for (int j = 0; j < n - i - 1; j++) {
                String ad1 = d[j].getAd();
                String ad2 = d[j + 1].getAd();
                // Soldaki sağdakinden büyükse yanlış sıradadır
                if (metinKarsilastir(ad1, ad2) > 0) {
                    // Swap: temp ile yer değiştir
                    Kitap temp = d[j];
                    d[j] = d[j + 1];
                    d[j + 1] = temp;
                }
            }
        }
    }

    // 2. Seçmeli Sıralama (Selection Sort) - Kitapları yayın yılına göre sıralar
    public void kitaplariYilaGoreSirala(Kitap[] d) {
        if (d == null) {
            return;
        }
        int n = d.length;
        // i: şu an doldurulacak pozisyon
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;  // şimdilik en küçük i'dir
            // Geri kalan kısımda en küçük yılı ara
            for (int j = i + 1; j < n; j++) {
                if (d[j].getYayinYili() < d[minIndex].getYayinYili()) {
                    minIndex = j; // daha küçük bulundu
                }
            }
             // En küçük zaten yerindeyse gereksiz swap yapma
            if (minIndex != i) {
                Kitap temp = d[i];
                d[i] = d[minIndex];
                d[minIndex] = temp;
            }
        }
    }

    // 3. Eklemeli Sıralama (Insertion Sort) - Ödünçleri beklenen iade tarihine göre sıralar
    public void odunclariIadeTarihineGoreSirala(Odunc[] d) {
        if (d == null) {
            return;
        }
        int n = d.length;
        // 1'den başlar: d[0] tek başına sıralı kabul edilir
        for (int i = 1; i < n; i++) {
            Odunc anahtar = d[i]; // yerleştirilecek eleman (saklıyoruz)
            int j = i - 1;        // sıralı kısmın son elemanı

             // Tarihi anahtardan sonra olanları bir sağa kaydır
            // (tarih nesnesi olduğu için > yerine isAfter kullanılır)
            while (j >= 0 && d[j].getBeklenenIade().isAfter(anahtar.getBeklenenIade())) {
                d[j + 1] = d[j];
                j--;
            }
            d[j + 1] = anahtar;  // anahtarı doğru boşluğa koy
        }
    }
}