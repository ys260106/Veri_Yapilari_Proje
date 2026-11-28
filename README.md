# VY-007 | Kütüphane Ödünç ve İade Takip Sistemi

## Proje Tanımı
Bu proje, kitapların, kütüphane üyelerinin ve ödünç işlemlerinin yönetildiği kapsamlı bir takip sistemidir. Sistem; kullanımda olan kitaplara gelen talepleri beklemeye alır, iadeleri ve geciken kitapları takip eder. Bir kitap iade edildiğinde kuyruktaki ilk üyeye ayrılır ve bekleme süreci adil bir şekilde yönetilir. Tüm veriler harici bir veritabanı kullanılmadan, RAM üzerinde yapılandırılmış dinamik veri yapılarıyla tutulmaktadır.

## Kullanılacak Teknolojiler
- **Programlama Dili:** Java
- **Kullanıcı Arayüzü (GUI):** Java FX / Swing (İlerleyen aşamalarda eklenecektir)

---

## Ekip Üyeleri, İş Bölümü ve Veri Yapısı Tercihleri

### 1. Sorgulama Modülü (Arama İşlemleri)
**Geliştirici:** Yusuf Şahin  
**Numarası:** 02250201089  
**Seçilen Veri Yapısı:** Karma Tablosu (HashMap) ve Dinamik Dizi (ArrayList)  

**1. Temel İşlevler ve Çalışma Mantığı**
- **Hızlı Sorgulama:** Kitap adı, yazar veya üye numarası girilerek sistemdeki ilgili kayıtların anında ekrana getirilmesi.
- **Çoklu Sonuç ve Durum Sorgulama:** Yazar aramalarında o yazara ait tüm kitapların, ayrıca ödünç durumu ve iade tarihi geçmiş (geciken) işlemlerin gruplanarak listelenmesi.

**2. Neden Karma Tablosu (HashMap) ve ArrayList Tercih Edildi?**
- **O(1) Arama Performansı:** Kütüphane sisteminde en sık başvurulan işlem aramadır. Binlerce kayıt arasında lineer arama döngüsü kurup O(N) sürede aramak yerine, Anahtar-Değer (Key-Value) yapısıyla çalışan HashMap tercih edilmiştir. Bu sayede arama karmaşıklığı kayıt sayısından bağımsız olarak O(1) (sabit zaman) seviyesine inmiştir. 
- **Çoklu Sonuç Yönetimi:** Bir yazarın birden fazla kitabı olabileceği için HashMap değerleri `ArrayList` olarak tutulmuş, böylece arama motorunun esnek ve dinamik bir şekilde birden fazla sonuç döndürmesi sağlanmıştır.

---

### 2. Kitap Yönetimi, Üye Yönetimi, Bekleme Talepleri
**Geliştirici:** Muhammed Yusuf Karataş  
**Numarası:** 02250201028  
**Seçilen Veri Yapısı:** Çift Yönlü Bağlı Liste (Doubly Linked List)  

**1. Temel İşlevler ve Çalışma Mantığı**
- **Kaydetme / Ekleme İşlemleri:** Yeni kütüphane üyelerinin sisteme dinamik olarak kaydedilmesi ve üye bilgilerinin bellekte tutulması. Stoğu tükenen veya ödünçte olan kitaplar için üyeler adına bekleme taleplerinin oluşturulması ve ilgili kitap/üye düğümlerine bağlanması.
- **Silme İşlemleri ve İlişkisel Temizleme:** Üyenin vazgeçtiği veya talebinin süresi dolduğu durumlarda bekleme talebi kaydının silinmesi. Silinmek istenen kitap kopyası veya üyenin açık/aktif bir ödünç ilişkisinin bulunmadığı öncelikle kontrol edilir. Herhangi bir aktif ödünç bağı yoksa; nesneye ait tüm referanslar temizlenerek düğüm çift yönlü listeden güvenle koparılır ve bellek sızıntısı (memory leak) önlenir.

**2. Neden Çift Yönlü Bağlı Liste (Doubly Linked List) Tercih Edildi?**
- **Dinamik Bellek ve İlişki Yönetimi:** Harici bir SQL veritabanı kullanılmadığı ve tüm verilerin RAM üzerinde tutulduğu bu mimaride, ekleme ve silme sıklığı oldukça yüksektir. Çift yönlü bağlı liste, sabit boyutlu dizilerin dezavantajlarını ortadan kaldırarak bellek üzerinde esnek bir büyüme ve küçülme sağlar.
- **Güvenli Düğüm Kaldırma:** Hem ileri (next) hem geri (prev) yönlü işaretçi barındırdığı için; listeden bir üye veya kitap kopyası silinirken, bağlı olduğu bekleme talebi ilişkilerinin koparılması ve işaretçilerin güncellenmesi tek yönlü listelere kıyasla çok daha hatasız ve yüksek performansla gerçekleştirilmektedir.

---

### 3. Ödünç Alma, İade İşlemleri ve Bekleme Sırası Yönetimi
**Geliştirici:** Muhammed Faruk Akkösem  
**Numarası:** 02250201080  
**Seçilen Veri Yapısı:** Kuyruk (Queue / FIFO) ve Hash Tablosu (HashMap)  

**1. Temel İşlevler ve Çalışma Mantığı**
- **Ödünç Verme ve Kuyruk Kontrolü:** Bir kitap kopyası ödünç verilmeden önce sistemde kopyaya ait aktif bir rezervasyon bulunup bulunmadığı kontrol edilir. Kopya boştaysa veya işlem yapan kişi sırada bekleyen hak sahibi üye ise ödünç kaydı oluşturulup aktif listeye eklenir. Dışarıda/ödünçte olan bir kopya için talep geldiğinde, ilgili kopyanın kuyruğuna yeni bir bekleme düğümü eklenir.
- **İade Alma ve Hak Koruması:** Kitap kopyası iade edildiğinde mevcut ödünç kaydının durumu güncellenir. Ardından kopyanın kuyruğuna bakılır; bekleyen üye varsa en baştaki talep kuyruktan çekilerek kitap doğrudan o üyeye ayrılır.
- **Kural Güvencesi:** İade sonrasında kuyruktaki ilk üyeye ayrılan kitap, o üye gelip teslim alana kadar başka hiçbir üyeye ödünç verilemez. Sıradaki üyenin talebini iptal etmesi durumunda ilgili kayıt kuyruktan temizlenir.

**2. Neden Kuyruk (Queue) ve HashMap Tercih Edildi?**
- **FIFO (İlk Gelen İlk Çıkar) ile Adil Sıralama:** Proje şartnamesindeki *"İadede ilk bekleyen üyeye ayrılan kitap, başka üyeye verilmez"* kuralını en doğru sağlayan yapı Queue (Kuyruk) modelidir. Talepler kronolojik sıraya göre dizilir; ilk başvuran üye her zaman ilk sırada çıkarak hak kaybı önlenir.
- **Hızlı ve Doğrudan Erişim:** Bir kitabın bekleme sırasını veya kime ayrıldığını öğrenmek için bütün listeyi baştan sona tek tek aramakla vakit kaybetmek yerine; HashMap sayesinde kitap numarasını verdiğimiz anda aradığımız bilgiye beklemeden, tek hamlede doğrudan ulaşılır.

---

### 4. Sıralama Modülü (Kitap ve Ödünç Sıralama İşlemleri)
**Geliştirici:** Ömer Faruk Afşar  
**Numarası:** 02250201083  
**Seçilen Yöntem ve Veri Yapısı:** Çift Yönlü Bağlı Liste Üzerinde Sıralama Algoritmaları (Bubble Sort / Karşılaştırma Mantığı)  

**1. Temel İşlevler ve Çalışma Mantığı**
- **Kitapları Ada Göre Sıralama:** Sistemdeki kitap koleksiyonunun başlıkları (String değerler), alfabetik sıraya göre (`compareTo` metodu yardımıyla) karşılaştırılarak yeniden düzenlenir.
- **Kitapları Yayın Yılına Göre Sıralama:** Kitapların yayın yılı verileri (int değerler) sayısal büyüklüklerine göre kıyaslanarak kronolojik düzene sokulur.
- **Ödünçleri İade Tarihine Göre Sıralama:** Ödünç işlemlerine ait iade tarihleri taranarak sıralama yapılır; böylece yaklaşan veya geciken iadelerin takibi optimize edilir.

**2. Neden Bu Yöntem Tercih Edildi?**
- **Bellek Üzerinde Dinamik Uyum:** Harici bir SQL veritabanı olmaksızın RAM üzerinde çalışan Çift Yönlü Bağlı Liste (Doubly Linked List) yapısıyla tam uyumlu çalışacak şekilde tasarlanmıştır. İleri ve geri yönlü işaretçiler (`next` ve `prev`) sayesinde veri düğümleri arasında gezinmek ve sıralama algoritmalarını (yer değiştirme) uygulamak son derece pratiktir.

---

## Temel Sınıf ve Düğüm Yapıları (Taslak)

Veri yapılarımızın kod iskeleti ve temel sınıfların yapısı, her üyenin kendi kod katkısında (commit) detaylı olarak yer almaktadır.
