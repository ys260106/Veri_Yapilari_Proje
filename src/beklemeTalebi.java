//burası kütüphanede bulunmayan kitaplara bekleme talebi olusturarak sıraya girmesini saglar
public class beklemeTalebi {
    private int talepId;
    private int kopyaNo;
    private int uyeNo;

    public beklemeTalebi(int talepId,int kopyaNo,int uyeNo){
      this.talepId=talepId++;
      this.kopyaNo=kopyaNo;
      this.uyeNo=uyeNo;
   }
}
