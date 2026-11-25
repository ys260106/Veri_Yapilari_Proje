package kutuphane;

public class KutuphaneSiralama {

    private int metinKarsilastir(String a, String b) {
        if (a == null && b == null) {
            return 0;
        }
        if (a == null) {
            return -1;
        }
        if (b == null) {
            return 1;
        }
        int minUzunluk = a.length() < b.length() ? a.length() : b.length();
        for (int i = 0; i < minUzunluk; i++) {
            char c1 = a.charAt(i);
            char c2 = b.charAt(i);
            if (c1 != c2) {
                return c1 - c2;
            }
        }
        return a.length() - b.length();
    }

    // 1. Kabarcık Sıralama (Bubble Sort) - Kitapları adına göre sıralar
    public void kitaplariAdaGoreSirala(Kitap[] d) {
        if (d == null) {
            return;
        }
        int n = d.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                String ad1 = d[j].getAd();
                String ad2 = d[j + 1].getAd();
                if (metinKarsilastir(ad1, ad2) > 0) {
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
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (d[j].getYayinYili() < d[minIndex].getYayinYili()) {
                    minIndex = j;
                }
            }
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
        for (int i = 1; i < n; i++) {
            Odunc anahtar = d[i];
            int j = i - 1;

            while (j >= 0 && d[j].getBeklenenIade().isAfter(anahtar.getBeklenenIade())) {
                d[j + 1] = d[j];
                j--;
            }
            d[j + 1] = anahtar;
        }
    }
}