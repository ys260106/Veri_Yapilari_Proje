# VY-007 | Kütüphane Ödünç ve İade Takip Sistemi — Ortak Plan ve UML

> VS Code'da **Ctrl+Shift+V** ile önizlemeyi aç → kareler ve oklar görünür.
> (Önizleme için "Markdown Preview Mermaid Support" eklentisi gerekli. GitHub'da kendiliğinden çizilir.)

**Renkler:** 🟩 Muhammed Yusuf Karataş · 🟧 Muhammed Faruk Akkösem · 🟦 Yusuf Şahin · 🟪 Ömer Faruk Afşar

Amaç: 4 modülü tek projede derlenir hale getirmek. Herkes **sadece kendi dosyalarına** dokunur, isimler aşağıdaki sözleşmeye göre yazılır.

---

## 1. Genel Mimari (Kim Kime Bağlı?)

Paneller sadece `Kutuphane` nesnesini alır; `Kutuphane` listeleri ve servisleri tutar; servisler kendi yazdığımız veri yapılarını kullanır.

```mermaid
flowchart TB
    subgraph GUI["Arayüz (Swing sekmeleri)"]
        AP[AnaPencere]
        UP[UyePanel]
        KP[KitapPanel]
        OP[OduncPanel]
        SP[SorguPanel]
        SRP[SiralamaPanel]
    end
    subgraph MERKEZ["Merkez"]
        MAIN[Main]
        KUT[Kutuphane]
        DOS[DosyaIslemleri]
    end
    subgraph SERVIS["Servisler"]
        SIR[KutuphaneSiralama]
        ODS[OduncServisi]
        SOR[SorguSistemi]
    end
    subgraph VY["Kendi Veri Yapılarımız"]
        KL[KitapListesi<br/>çift yönlü liste]
        UL[UyeListesi<br/>çift yönlü liste]
        OL[OduncListesi<br/>bağlı liste]
        Q[Kuyruk<br/>FIFO]
        HM[KendiHashMap<br/>hash tablosu]
        DD[DinamikDizi<br/>büyüyen dizi]
    end
    subgraph MODEL["Modeller"]
        KT[Kitap]
        KK[KitapKopyasi]
        U[Uye]
        O[Odunc]
        BT[BeklemeTalebi]
    end
    MAIN -->|oluşturur| KUT
    MAIN -->|açar| AP
    DOS -->|kaydet / yükle| KUT
    GUI -->|"new XPanel(kutuphane)"| KUT
    KUT -->|tutar| SIR
    KUT -->|tutar| ODS
    KUT -->|tutar| SOR
    SIR -.->|diziyeCevir| KL
    SIR -.->|diziyeCevir| OL
    ODS --> OL
    ODS -->|kopya başına 1| Q
    ODS -->|kuyruklar| HM
    ODS -.->|bulur, doğrular| UL
    ODS -.->|bulur, doğrular| KL
    SOR -->|indeks| HM
    SOR -->|sonuç| DD
    SOR -.->|okur| KL
    SOR -.->|okur| UL
    SOR -.->|okur| OL
    KL --> KT
    KT -->|1..* kopya| KK
    UL --> U
    OL --> O
    Q --> BT

    classDef mk fill:#d5f2ec,stroke:#0e9c86,stroke-width:2px,color:#10223a
    classDef f fill:#ffe6d3,stroke:#f06d12,stroke-width:2px,color:#10223a
    classDef y fill:#dcebfa,stroke:#2b7fd0,stroke-width:2px,color:#10223a
    classDef o fill:#f5dcec,stroke:#b0408a,stroke-width:2px,color:#10223a
    class UP,KP,KL,UL,KT,KK,U mk
    class OP,ODS,OL,Q,O,BT f
    class SP,SOR,HM,DD y
    class AP,SRP,MAIN,KUT,DOS,SIR o
```

---

## 2. Kim Hangi Dosyayı Açacak?

```mermaid
flowchart LR
    subgraph O["🟪 Ömer F. Afşar — siralama-modulu"]
        direction TB
        o1[KutuphaneSiralama]
        o2[DosyaIslemleri]
        o3[Kutuphane]
        o4[Main]
        o5[AnaPencere]
        o6[SiralamaPanel]
        o7[SiralamaTest]
    end
    subgraph Y["🟦 Yusuf Şahin — sorgulama-modulu"]
        direction TB
        y1[KendiHashMap]
        y2[HashDugumu]
        y3[DinamikDizi]
        y4[SorguSistemi]
        y5[SorguPanel]
        y6[SorguTest]
    end
    subgraph F["🟧 M. Faruk Akkösem — odunc-sistemi"]
        direction TB
        f1[Odunc]
        f2[BeklemeTalebi]
        f3[Kuyruk]
        f4[OduncListesi]
        f5[OduncServisi]
        f6[OduncPanel]
        f7[OduncTest]
    end
    subgraph MK["🟩 M. Yusuf Karataş — kayit-ve-silme"]
        direction TB
        mk1[Kitap]
        mk2[KitapKopyasi]
        mk3[KitapListesi]
        mk4["Uye ✔ hazır"]
        mk5[UyeListesi]
        mk6[UyePanel]
        mk7[KitapPanel]
        mk8[UyeKitapTest]
    end

    classDef mk fill:#d5f2ec,stroke:#0e9c86,stroke-width:2px,color:#10223a
    classDef f fill:#ffe6d3,stroke:#f06d12,stroke-width:2px,color:#10223a
    classDef y fill:#dcebfa,stroke:#2b7fd0,stroke-width:2px,color:#10223a
    classDef o fill:#f5dcec,stroke:#b0408a,stroke-width:2px,color:#10223a
    class mk1,mk2,mk3,mk4,mk5,mk6,mk7,mk8 mk
    class f1,f2,f3,f4,f5,f6,f7 f
    class y1,y2,y3,y4,y5,y6 y
    class o1,o2,o3,o4,o5,o6,o7 o
```

| Kişi | Kendi yazdığı veri yapısı | Asıl mantık işi | Arayüz | Yapılacak dosya |
|---|---|---|---|---|
| 🟩 M. Yusuf K. | Çift yönlü bağlı liste (Üye, Kitap) + kopya listesi | Kayıt, silme ve silme kuralları | UyePanel, KitapPanel | 7 (Uye hazır) |
| 🟧 Faruk A. | Kuyruk + ödünç listesi | Ödünç / iade / bekleme kuralları | OduncPanel | 7 |
| 🟦 Yusuf Ş. | Hash tablosu + dinamik dizi | 6 arama türü + indeks | SorguPanel | 6 |
| 🟪 Ömer A. | 3 sıralama algoritması | txt kaydet/yükle + sistemi bağlama | AnaPencere, SiralamaPanel | 7 |

Değişen iki şey: bekleme talepleri tamamen ödünç modülünde (README'de iki kişide yazıyordu); Ömer'e sıralamanın yanında dosya kaydetme ve ana pencere eklendi.

---

## 3. Ortak Model Sınıfları

Sistemde **tek** Kitap, **tek** Üye var. Kimse kendi dosyasının içine ikinci bir Kitap yazmaz.

```mermaid
classDiagram
    class Kitap {
        - kitapNo : int
        - ad : String
        - yazar : String
        - yayinYili : int
        - ilkKopya : KitapKopyasi
        - kopyaSayisi : int
        + sonraki, onceki : Kitap
        + Kitap(int kitapNo, String ad, String yazar, int yayinYili)
        + getKitapNo() int
        + getAd() String
        + getYazar() String
        + getYayinYili() int
        + kopyaEkle(KitapKopyasi k) void
        + kopyaBul(int kopyaNo) KitapKopyasi
        + getIlkKopya() KitapKopyasi
        + toString() String
    }
    class KitapKopyasi {
        + RAFTA, ODUNCTE, AYRILDI : String
        - kopyaNo : int
        - kitapNo : int
        - durum : String
        - ayrilanUyeNo : int
        + sonraki : KitapKopyasi
        + KitapKopyasi(int kopyaNo, int kitapNo)
        + getKopyaNo() int
        + getKitapNo() int
        + getDurum() String
        + setDurum(String d) void
        + getAyrilanUyeNo() int
        + setAyrilanUyeNo(int uyeNo) void
    }
    class Uye {
        - uyeNo : int
        - adSoyad : String
        - acikOduncSayisi : int
        + sonraki, onceki : Uye
        + Uye(int uyeNo, String adSoyad)
        + getUyeNo() int
        + getAdSoyad() String
        + getAcikOduncSayisi() int
        + oduncArtir() void
        + oduncAzalt() void
        + silinebilirMi() boolean
    }
    class Odunc {
        - oduncId, kopyaNo, uyeNo, kitapNo : int
        - beklenenIade : LocalDate
        - iadeEdildi : boolean
        + sonraki, onceki : Odunc
        + Odunc(int oduncId, int kopyaNo, int uyeNo, int kitapNo, LocalDate beklenenIade)
        + getter'lar (mevcut)
        + iadeEt() void
        + gecikmisMi(LocalDate bugun) boolean
    }
    class BeklemeTalebi {
        - sayac : int
        - talepId, uyeNo, kitapNo, kopyaNo : int
        + sonraki : BeklemeTalebi
        + BeklemeTalebi(int uyeNo, int kitapNo, int kopyaNo)
        + BeklemeTalebi(int talepId, int uyeNo, int kitapNo, int kopyaNo)
        + getter'lar (mevcut)
    }
    Kitap "1" *-- "1..*" KitapKopyasi : kopyalar
    KitapKopyasi "1" <.. "0..*" Odunc : kopyaNo
    Uye "1" <.. "0..*" Odunc : uyeNo
    KitapKopyasi "1" <.. "0..*" BeklemeTalebi : kopyaNo
    Uye "1" <.. "0..*" BeklemeTalebi : uyeNo
```

---

## 4. Kendi Veri Yapılarımız

`java.util` koleksiyonları kullanılmaz; hepsi sıfırdan yazılır.

```mermaid
classDiagram
    class KitapListesi {
        - bas, son : Kitap
        - boyut : int
        + kitapEkle(int kitapNo, String ad, String yazar, int yil) boolean
        + kitapBul(int kitapNo) Kitap
        + kitapSil(int kitapNo) boolean
        + kopyaEkle(int kitapNo, int kopyaNo) boolean
        + kopyaBul(int kopyaNo) KitapKopyasi
        + kopyaSil(int kopyaNo) boolean
        + getBas() Kitap
        + getBoyut() int
        + diziyeCevir() Kitap[]
    }
    class UyeListesi {
        - bas, son : Uye
        - boyut : int
        + uyeKaydet(int uyeNo, String adSoyad) boolean
        + uyeBul(int uyeNo) Uye
        + uyeSil(int uyeNo) boolean
        + getBas() Uye
        + getBoyut() int
        + listele() void
        + diziyeCevir() Uye[]
    }
    class OduncListesi {
        - bas, son : Odunc
        - boyut : int
        + ekle(Odunc o) void
        + aktifOduncBul(int kopyaNo) Odunc
        + getBas() Odunc
        + getBoyut() int
        + diziyeCevir() Odunc[]
    }
    class Kuyruk {
        - bas, son : BeklemeTalebi
        - boyut : int
        + kuyrugaEkle(BeklemeTalebi t) void
        + kuyruktanCikar() BeklemeTalebi
        + ilkiGoster() BeklemeTalebi
        + kuyruktaUyeVarMi(int uyeNo) boolean
        + talepSil(int talepId) boolean
        + bosMu() boolean
        + getBoyut() int
        + listele() void
    }
    class KendiHashMap~K,V~ {
        - kutular : HashDugumu~K,V~[]
        - boyut : int
        + put(K anahtar, V deger) void
        + get(K anahtar) V
        + containsKey(K anahtar) boolean
        + remove(K anahtar) V
        + getBoyut() int
        + temizle() void
        - indexBul(K anahtar) int
    }
    class HashDugumu~K,V~ {
        + anahtar : K
        + deger : V
        + sonraki : HashDugumu~K,V~
        + HashDugumu(K anahtar, V deger)
    }
    class DinamikDizi~T~ {
        - elemanlar : Object[]
        - boyut : int
        + ekle(T eleman) void
        + get(int i) T
        + getBoyut() int
        - buyut() void
    }
    KitapListesi "1" *-- "0..*" Kitap : bas/son
    UyeListesi "1" *-- "0..*" Uye : bas/son
    OduncListesi "1" *-- "0..*" Odunc : bas/son
    Kuyruk "1" *-- "0..*" BeklemeTalebi : bas/son
    KendiHashMap~K,V~ "1" *-- "0..*" HashDugumu~K,V~ : kutular
```

---

## 5. Servisler ve Merkez

```mermaid
classDiagram
    class OduncServisi {
        - uyeler : UyeListesi
        - kitaplar : KitapListesi
        - oduncler : OduncListesi
        - kuyruklar : KendiHashMap~Integer, Kuyruk~
        - sonOduncId : int
        + OduncServisi(UyeListesi u, KitapListesi k, OduncListesi o)
        + oduncVer(int uyeNo, int kopyaNo, LocalDate sonTarih) Odunc
        + iadeAl(int kopyaNo) Odunc
        + beklemeTalebiOlustur(int uyeNo, int kopyaNo) BeklemeTalebi
        + beklemeTalebiSil(int kopyaNo, int talepId) boolean
        + ayirmaIptal(int kopyaNo) void
        + kuyrukGetir(int kopyaNo) Kuyruk
        + dosyadanOduncEkle(Odunc o) void
        + dosyadanTalepEkle(BeklemeTalebi t) void
    }
    class SorguSistemi {
        - kitaplar : KitapListesi
        - uyeler : UyeListesi
        - oduncler : OduncListesi
        - adaGore : KendiHashMap~String, DinamikDizi~
        - yazaraGore : KendiHashMap~String, DinamikDizi~
        - uyeNoyaGore : KendiHashMap~Integer, Uye~
        + SorguSistemi(KitapListesi k, UyeListesi u, OduncListesi o)
        + indeksleriYenile() void
        + kitapAdiylaAra(String ad) DinamikDizi~Kitap~
        + yazaraGoreAra(String yazar) DinamikDizi~Kitap~
        + uyeNoIleAra(int uyeNo) Uye
        + uyeninOduncleri(int uyeNo) DinamikDizi~Odunc~
        + durumaGoreAra(boolean iadeEdildi) DinamikDizi~Odunc~
        + gecikenler(LocalDate bugun) DinamikDizi~Odunc~
    }
    class KutuphaneSiralama {
        + kitaplariAdaGoreSirala(Kitap[] d) void
        + kitaplariYilaGoreSirala(Kitap[] d) void
        + odunclariIadeTarihineGoreSirala(Odunc[] d) void
        - metinKarsilastir(String a, String b) int
    }
    class Kutuphane {
        - uyeler : UyeListesi
        - kitaplar : KitapListesi
        - oduncler : OduncListesi
        - oduncServisi : OduncServisi
        - sorgu : SorguSistemi
        - siralama : KutuphaneSiralama
        + Kutuphane()
        + getUyeler() / getKitaplar() / getOduncler()
        + getOduncServisi() / getSorgu() / getSiralama()
    }
    class DosyaIslemleri {
        + kaydet(Kutuphane k) void
        + yukle(Kutuphane k) void
    }
    class Main {
        + main(String[] args) void
    }
    class AnaPencere {
        - sekmeler : JTabbedPane
        + AnaPencere(Kutuphane k)
    }
    Kutuphane *-- OduncServisi
    Kutuphane *-- SorguSistemi
    Kutuphane *-- KutuphaneSiralama
    OduncServisi --> KendiHashMap~K,V~ : kuyruklar
    OduncServisi --> Kuyruk
    SorguSistemi --> KendiHashMap~K,V~ : indeks
    SorguSistemi ..> DinamikDizi~T~ : sonuç
    DosyaIslemleri ..> Kutuphane : kaydet/yukle
    Main ..> Kutuphane : oluşturur
    Main ..> AnaPencere : açar
    AnaPencere ..> Kutuphane
```

---

## 6. Kitap Kopyasının Durumları

`durum` alanını sadece `OduncServisi` değiştirir. Bu diyagram "iadede ilk bekleyene ayrılan kitap başkasına verilmez" kuralını gösterir.

```mermaid
stateDiagram-v2
    [*] --> RAFTA : kopyaEkle()
    RAFTA --> ODUNCTE : oduncVer()
    ODUNCTE --> RAFTA : iadeAl() ve kuyruk boş
    ODUNCTE --> AYRILDI : iadeAl() ve kuyrukta üye var
    AYRILDI --> ODUNCTE : oduncVer() sadece ayrılan üye
    AYRILDI --> AYRILDI : ayirmaIptal() sıradakine geçer
    AYRILDI --> RAFTA : ayirmaIptal() ve kuyruk boş
    RAFTA --> [*] : kopyaSil()
    note right of AYRILDI
        Başka üye oduncVer() derse
        IllegalArgumentException
    end note
```

---

## 7. Sıralı Akış — Ödünç + Bekleme + İade

```mermaid
sequenceDiagram
    actor U1 as Üye 101
    actor U2 as Üye 102
    participant P as OduncPanel
    participant S as OduncServisi
    participant K as KitapListesi
    participant Q as Kuyruk (kopya 500)

    U1->>P: kopya 500'ü ödünç al
    P->>S: oduncVer(101, 500, sonTarih)
    S->>K: kopyaBul(500)
    K-->>S: KitapKopyasi (RAFTA)
    S-->>P: Odunc no 1, kopya ODUNCTE

    U2->>P: kopya 500 için sıraya gir
    P->>S: beklemeTalebiOlustur(102, 500)
    S->>Q: kuyruktaUyeVarMi(102)
    Q-->>S: false
    S->>Q: kuyrugaEkle(talep)

    U1->>P: iade et
    P->>S: iadeAl(500)
    S->>Q: kuyruktanCikar()
    Q-->>S: talep (üye 102)
    S-->>P: kopya AYRILDI → üye 102
    Note over S: Üye 103 isterse hata mesajı döner

    U2->>P: ödünç al
    P->>S: oduncVer(102, 500, sonTarih)
    S-->>P: Odunc no 2, kopya ODUNCTE
```

---

## 8. Sıralı Akış — Yazara Göre Sorgulama

```mermaid
sequenceDiagram
    actor Kullanici
    participant P as SorguPanel
    participant S as SorguSistemi
    participant H as KendiHashMap

    Kullanici->>P: yazar = "Orhan Pamuk"
    P->>S: yazaraGoreAra("Orhan Pamuk")
    S->>S: anahtarı küçük harfe çevir, trim
    S->>H: get("orhan pamuk")
    H->>H: indexBul(anahtar) → kutu → zincirde ara
    H-->>S: DinamikDizi<Kitap>
    S-->>P: sonuçlar
    P-->>Kullanici: tabloda listele
```

---

## 9. Birleştirme Sırası

Kimse doğrudan `main`'e push etmez. Her parça kendi dalından **Pull Request** ile gelir; sıra, bir dosyanın kullandığı sınıfın main'de önceden var olmasına göre belirlendi.

```mermaid
gitGraph
    commit id: "README"
    branch kayit-ve-silme
    commit id: "Kitap, Uye, listeler"
    checkout main
    branch sorgulama-modulu
    commit id: "KendiHashMap, DinamikDizi"
    checkout main
    branch odunc-sistemi
    commit id: "Odunc, Kuyruk, OduncServisi"
    checkout main
    branch siralama-modulu
    commit id: "Siralama, Dosya, Kutuphane, Main"
    checkout main
    merge kayit-ve-silme id: "1. PR"
    merge sorgulama-modulu id: "2. PR"
    checkout odunc-sistemi
    merge main id: "Update from main (F)"
    checkout main
    merge odunc-sistemi id: "3. PR"
    checkout sorgulama-modulu
    merge main id: "Update from main (Y)"
    commit id: "SorguSistemi"
    checkout main
    merge sorgulama-modulu id: "4. PR"
    checkout siralama-modulu
    merge main id: "Update from main (O)"
    checkout main
    merge siralama-modulu id: "5. PR"
    commit id: "6. Paneller + sekmeler"
```

1. **Temizlik:** Yusuf `main`'deki kök `sorgulama.java`'yı siler. Herkes dosyalarını `src/kutuphane/`'ye taşır, `package kutuphane;` ekler.
2. **PR sırası:** `kayit-ve-silme` → `sorgulama-modulu` (KendiHashMap, HashDugumu, DinamikDizi) → `odunc-sistemi` → `sorgulama-modulu` (SorguSistemi) → `siralama-modulu` → paneller. İlk PR'lar iskelet olabilir (alanlar + imzalar, gövde boş / `return null;`).
3. **Her PR'dan önce:** GitHub Desktop → kendi dalın → *Branch → Update from main* → derle → *Push origin* → *Create Pull Request*.
   ```
   git checkout <kendi-dalın>
   git pull origin main
   javac -d out src/kutuphane/*.java
   git push origin <kendi-dalın>
   ```
4. **Başkasının dosyasına dokunma:** metot lazımsa sahibine yazılır, sahibi ekler.
5. **Son test (main'de):** üye 101, 102 ekle → kitap 1 + kopya 500 → 101'e ödünç → 102 sıraya → 101 iade → kopya `AYRILDI` → 103 alamaz → 102 alır → kapat/aç, veriler txt'den gelir.

---

## 10. Ortak İsimler (Sözleşme)

| Konu | Kural |
|---|---|
| Paket | Her dosyada `package kutuphane;`, hepsi `src/kutuphane/` altında |
| Tek model | `Kitap`, `KitapKopyasi`, `Uye`, `Odunc`, `BeklemeTalebi` projede birer kez |
| Numaralar | `uyeNo`, `kitapNo`, `kopyaNo`, `oduncId`, `talepId` hep `int`; `kopyaNo` sistemde tek → metotlar sadece `kopyaNo` alır |
| Getter adları | `getAd()`, `getYazar()`, `getYayinYili()`, `getKitapNo()`, `getKopyaNo()`, `getUyeNo()`, `getBeklenenIade()` — `getBaslik()` yok |
| Kopya durumu | `KitapKopyasi.RAFTA`, `ODUNCTE`, `AYRILDI` |
| Liste deseni | Model sınıfında `public sonraki/onceki`; listede `bas`, `son`, `boyut`, `getBas()`, `diziyeCevir()` |
| Koleksiyon | `java.util` (List, ArrayList, HashMap, LinkedList, Queue) yok; tarih için `java.time.LocalDate` |
| Hata | Kurala aykırı işlemde `IllegalArgumentException("Türkçe mesaj")`, panel `JOptionPane` ile gösterir |
| Arayüz | Swing; `XPanel extends JPanel`, kurucu `XPanel(Kutuphane k)`; sekmeleri sadece `AnaPencere` ekler |
| main | Uygulamanın tek `main`'i `Main.java`'da; testler `UyeKitapTest`, `OduncTest`, `SorguTest`, `SiralamaTest` |
| Dosya biçimi | Her satır bir kayıt, alanlar `;` ile ayrılır (ör. `uyeler.txt`: `101;Ali Yılmaz`) |
| README | Herkes sadece kendi bölümünü düzenler |

---

## 11. Dosya Listesi

`hazır` = dalda var · `güncelle` = küçük ekleme · `düzelt` = derleme hatası var · `yeni` = açılacak

### 🟩 Muhammed Yusuf Karataş · Kitap & Üye kayıt/silme (`kayit-ve-silme`)

| Dosya | Durum | Not |
|---|---|---|
| `Kitap.java` | yeni | Hem kitap bilgisi hem KitapListesi düğümü (Uye ile aynı desen). |
| `KitapKopyasi.java` | yeni | Durumu yalnızca OduncServisi değiştirir. AYRILDI = iadeden sonra sıradaki üyeye ayrıldı. |
| `KitapListesi.java` | yeni | Çift yönlü bağlı liste. Silme kuralı: kopyaların hepsi RAFTA değilse silinmez. |
| `Uye.java` | hazır | kayit-ve-silme dalında hazır, değişmiyor. |
| `UyeListesi.java` | güncelle | Sadece diziyeCevir() eklenecek (tablo ve sıralama için). |
| `UyePanel.java` | yeni | Üye ekle / sil / listele sekmesi. |
| `KitapPanel.java` | yeni | Kitap + kopya ekle / sil sekmesi. |
| `UyeKitapTest.java` | yeni | Kendi modülünün konsol testi. |

### 🟧 Muhammed Faruk Akkösem · Ödünç / İade / Bekleme (`odunc-sistemi`)

| Dosya | Durum | Not |
|---|---|---|
| `Odunc.java` | güncelle | OduncListesi düğümü olacağı için sonraki/onceki eklenir. |
| `BeklemeTalebi.java` | güncelle | İkinci kurucu sayacı en büyük talepId + 1'e çeker. |
| `Kuyruk.java` | güncelle | talepSil: üye sıradan vazgeçince ortadan düğüm koparır. |
| `OduncListesi.java` | yeni | Tüm ödünç kayıtları (iade edilenler de) burada kalır. |
| `OduncServisi.java` | güncelle | Bütün kurallar burada: kopya RAFTA değilse veya başka üyeye AYRILDI ise ödünç verilmez; aynı üye aynı kopyada iki kez bekleyemez; iadede kuyruktan çıkan üye silinmişse atlanır; ayrılan üye vazgeçerse kopya sıradakine geçer. |
| `OduncPanel.java` | yeni | Ödünç ver / iade al / sıraya gir sekmesi. |
| `OduncTest.java` | güncelle | Şimdiki Maintest.java'nın yeni adı. |

### 🟦 Yusuf Şahin · Sorgulama (`sorgulama-modulu`)

| Dosya | Durum | Not |
|---|---|---|
| `KendiHashMap.java` | güncelle | Şu an sadece String→Kitap; generic yapılınca Faruk da kopyaNo→Kuyruk için kullanır. |
| `HashDugumu.java` | güncelle | Zincirleme (chaining) düğümü. |
| `DinamikDizi.java` | yeni | README'deki ArrayList'in yerine; arama sonuçlarını döndürür. |
| `SorguSistemi.java` | güncelle | Anahtarlar küçük harfe çevrilip trim'lenir. Ekleme/silmeden sonra indeksleriYenile() çağrılır. |
| `SorguPanel.java` | yeni | Arama sekmesi. |
| `SorguTest.java` | yeni | Kendi modülünün konsol testi. |

### 🟪 Ömer Faruk Afşar · Sıralama + Dosya + Ana pencere (`siralama-modulu`)

| Dosya | Durum | Not |
|---|---|---|
| `KutuphaneSiralama.java` | düzelt | List<Kitap> yerine dizi alır; diziyi listelerin diziyeCevir() metodu verir, asıl liste bozulmaz. |
| `DosyaIslemleri.java` | yeni | uyeler.txt · kitaplar.txt · kopyalar.txt · oduncler.txt · talepler.txt — alanlar ; ile ayrılır. |
| `Kutuphane.java` | yeni | Tek merkez nesne. Paneller bunu alır, başka hiçbir şeyi new'lemez. |
| `Main.java` | yeni | Kutuphane oluştur → yukle → AnaPencere göster; kapanırken kaydet. Projedeki tek uygulama main'i. |
| `AnaPencere.java` | yeni | Panel birleşince sekme satırını Ömer ekler. |
| `SiralamaPanel.java` | yeni | Sıralama sekmesi. |
| `SiralamaTest.java` | yeni | Kendi modülünün konsol testi. |

---

## 12. Dallarda Şu An Düzeltilecekler

| Dal | Sorun | Kim |
|---|---|---|
| `main` | Kökte `sorgulama.java`: `java.util` import, `boolen` hatası, derlenmiyor | 🟦 Yusuf |
| `sorgulama-modulu` | İçinde ayrı `Kitap` sınıfı, `package` yok, kökte; `KendiHashMap`'te `get` yok, sadece String→Kitap | 🟦 Yusuf |
| `siralama-modulu` | `import java.util.List`; `for(int i=1 i<n; i++);{` derleme hatası; `getBaslik()` → `getAd()`; metot adlarında "ö"; `KutupHaneSiralama` → `KutuphaneSiralama`; kökte | 🟪 Ömer |
| `odunc-sistemi` | `OduncServisi` sadece println; `Kuyruk`'ta `talepSil` yok; `Maintest` → `OduncTest`; `oduncVer` sadece `kopyaNo` almalı | 🟧 Faruk |
| `kayit-ve-silme` | Kitap/kopya tarafı yok; README başlığındaki "Bekleme Talepleri" çıkmalı; `UyeListesi.diziyeCevir()` | 🟩 M. Yusuf |
| `README` | Ömer'in bölümü dizi üzerinde sıralamaya; Faruk'un bölümü `KendiHashMap`'e güncellenmeli | 🟪 Ömer, 🟧 Faruk |

> Renkli tek sayfalık sürüm: `docs/ortak_plan.html` dosyasını indirip tarayıcıda açın.
