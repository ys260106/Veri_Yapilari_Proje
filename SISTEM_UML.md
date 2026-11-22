# VY-007 | Kütüphane Ödünç ve İade Takip Sistemi — Sistem UML

> VS Code'da **Ctrl+Shift+V** ile önizlemeyi aç → kareler ve oklar görünür.
> (Önizleme için "Markdown Preview Mermaid Support" eklentisi gerekli.)

---

## 1. Ortak Çekirdek Sınıflar

Sistemde **tek** Kitap ve **tek** Üye olmalı. Şu an herkes kendininkini yazmış.

```mermaid
classDiagram
    class Kitap {
        -String kayitNumarasi
        -String ad
        -String yazar
        -int yayinYili
        -boolean oduncVerildi
        +getAd() String
        +getYazar() String
        +getYayinYili() int
        +isOduncVerildi() boolean
        +setOduncVerildi(boolean) void
        +toString() String
    }

    class Uye {
        -int uyeNo
        -String adSoyad
        -int acikOduncSayisi
        +Uye sonraki
        +Uye onceki
        +getUyeNo() int
        +getAdSoyad() String
        +oduncArtir() void
        +oduncAzalt() void
        +silinebilirMi() boolean
    }

    class Odunc {
        -int oduncId
        -int kopyaNo
        -int uyeNo
        -int kitapNo
        -LocalDate beklenenIade
        -boolean iadeEdildi
        +getUyeNo() int
        +getKitapNo() int
        +iadeEt() void
    }

    class BeklemeTalebi {
        -int uyeNo
        -int kopyaNo
        -BeklemeTalebi sonraki
    }
```

---

## 2. Her Modülün Sınıfı

```mermaid
classDiagram
    class SorguSistemi {
        <<Yusuf - Sorgulama>>
        -KendiHashMap kitapAdinaGore
        -KendiHashMap yazaraGore
        -KendiHashMap uyeNoGore
        +kitapAra(String) Kitap
        +yazarAra(String) List
        +uyeAra(int) Uye
        +gecikenleriListele() List
    }

    class UyeListesi {
        <<Muhammed K - Kayit/Silme>>
        -Uye bas
        -Uye son
        -int boyut
        +uyeBul(int) Uye
        +uyeKaydet(int, String) boolean
        +uyeSil(int) boolean
        +listele() void
    }

    class OduncServisi {
        <<Muhammed F - Odunc/Iade>>
        -KendiHashMap kuyruklar
        -int sonOduncId
        +oduncVer(int, int, int) Odunc
        +iadeAl(int) Odunc
        +beklemeyeAl(int, int) void
    }

    class Kuyruk {
        -BeklemeTalebi bas
        -BeklemeTalebi son
        -int boyut
        +kuyrugaEkle(BeklemeTalebi) void
        +kuyruktanCikar() BeklemeTalebi
        +bosMu() boolean
    }

    class KutupHaneSiralama {
        <<Omer - Siralama>>
        +basligaGoreSirala(List) void
        +yilaGoreSirala(List) void
        +tariheGoreSirala(List) void
    }
```

---

## 3. Modüller Arası Bağlantı (Kim Kime Bağlı?)

```mermaid
graph TD
    A["SorguSistemi<br/>Yusuf<br/>HashMap + ArrayList"] -->|arar| K[["Kitap"]]
    B["UyeListesi<br/>Muhammed K<br/>Cift Yonlu Bagli Liste"] -->|tutar| U[["Uye"]]
    C["OduncServisi<br/>Muhammed F<br/>Kuyruk + HashMap"] -->|tutar| O[["Odunc"]]
    C --> Q[Kuyruk]
    Q --> BT[BeklemeTalebi]
    D["KutupHaneSiralama<br/>Omer<br/>Siralama Algoritmalari"] -.->|sirala| K
    D -.->|sirala| O
    A -.->|uyeNo ile eris| U
    C -.->|uyeNo ile eris| U

    style A fill:#aed6f1
    style B fill:#a9dfbf
    style C fill:#f9e79f
    style D fill:#f5b7b1
    style K fill:#d7bde2
    style U fill:#d7bde2
    style O fill:#d7bde2
```

---

## 4. Sıralı Akış — Örnek: Kitap Sorgulama

```mermaid
sequenceDiagram
    actor Kullanici
    participant S as SorguSistemi
    participant H as KendiHashMap

    Kullanici->>S: kitapAra("Suc ve Ceza")
    S->>H: get("Suc ve Ceza")
    H->>H: hash(key) -> index
    H-->>S: Kitap nesnesi
    S-->>Kullanici: Kitap bilgisi
```

---

## 5. Sıralı Akış — Örnek: İade + Kuyruk

```mermaid
sequenceDiagram
    actor Uye1
    participant C as OduncServisi
    participant Q as Kuyruk
    participant Uye2

    Uye2->>C: beklemeTalebi(KitapX)
    C->>Q: kuyrugaEkle(Uye2)
    Uye1->>C: iadeAl(KitapX)
    C->>Q: kuyruktanCikar()
    Q-->>C: Uye2 talebi
    C-->>Uye2: Kitap X sana ayrildi
```

---

## 6. Veri Yapıları Özet Tablo

| Modül | Geliştirici | Veri Yapısı | Ne için |
|-------|-------------|-------------|---------|
| Sorgulama | Yusuf Şahin | HashMap + ArrayList | O(1) arama, çoklu sonuç |
| Kayıt/Silme | Muhammed Y. Karataş | Çift Yönlü Bağlı Liste | Dinamik ekle/sil |
| Ödünç/İade | Muhammed F. Akkösem | Kuyruk (FIFO) + HashMap | Adil sıra, hızlı erişim |
| Sıralama | Ömer F. Afşar | Bağlı Liste üstünde sort | Alfabetik / kronolojik |

---

## 7. Tespit Edilen Problemler (Ekip Toplantısı İçin)

1. **Ortak tip yok:** Her modül kendi Kitap/Uye'sini yazmış → birbirini çağıramıyor.
2. **Paket tutarsız:** 3 modül `src/kutuphane/` paketinde, 2 modül kök dizinde.
3. **Hazır koleksiyon:** `SorguSistemi` şu an `java.util.HashMap` kullanıyor →
   ödev kuralı gereği sıfırdan yazılmalı (Yusuf'un kararı).
4. **Derleme hataları:** `sorgulama.java` (`boolen`), `KutupHaneSiralama.java` (syntax).
5. **Alan tutarsızlığı:** Ömer'in kodu `getBaslik()`, `getYayinYili()` bekliyor ama
   Yusuf'un Kitap'ında bu metotlar yok.

---

## 8. Çözüm Önerisi — Ortak Sözleşme

```
src/kutuphane/
├── Kitap.java          <- ORTAK model, herkes kullanir
├── Uye.java            <- Muhammed K
├── Odunc.java          <- Muhammed F
├── BeklemeTalebi.java  <- Muhammed F
├── KendiHashMap.java   <- Yusuf (sifirdan hash tablosu)
├── UyeListesi.java     <- Muhammed K
├── Kuyruk.java         <- Muhammed F
├── SorguSistemi.java   <- Yusuf
└── KutupHaneSiralama.java <- Omer
```
