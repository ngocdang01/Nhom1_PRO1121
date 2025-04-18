package dunghtph30405.example.nhom1_pro1121.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import dunghtph30405.example.nhom1_pro1121.database.DbHelper;
import dunghtph30405.example.nhom1_pro1121.designPantter.AccountSingle;
import dunghtph30405.example.nhom1_pro1121.model.giohang;
import dunghtph30405.example.nhom1_pro1121.model.sanpham;

public class GioHangDAO {
    DbHelper dbHelper;
    public GioHangDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    public ArrayList<giohang> listGH() {
        ArrayList<giohang> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM GIOHANG", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                if (cursor.getInt(4) == AccountSingle.getInstance().getAccount().getId()){
                    list.add(new giohang(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getInt(3),
                            cursor.getInt(5)));
                }
            } while (cursor.moveToNext());
        }
        return list;
    }

    public void addToCart(sanpham sanPham) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tensp", sanPham.getTensp());
        values.put("gia", sanPham.getGia());
        values.put("id_ac", AccountSingle.getInstance().getAccount().getId());
        sqLiteDatabase.insert("GIOHANG", null, values);
        sqLiteDatabase.close();
    }




    public void updateQuantity(int ID, int newQuantity) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("soluong", newQuantity);
        sqLiteDatabase.update("GIOHANG", values, "id=?", new String[]{String.valueOf(ID)});
        sqLiteDatabase.close();
    }

    public void deleteFromCartUseOBJ(giohang giohang) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();


        int magh = giohang.getId();
        if (magh >= 0) {
            sqLiteDatabase.delete("GIOHANG", "id=?", new String[]{String.valueOf(magh)});
        }
    }









    public void addHoaDon(giohang hd, String hoTen, String ngayMua) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tensp", hd.getTensp());
        values.put("soluong", hd.getSoluong());
        values.put("gia", hd.getGia());
        values.put("hoten", hoTen.trim());
        values.put("ngaymua", ngayMua.trim());
        values.put("id_ac_hd", AccountSingle.getInstance().getAccount().getId());
        sqLiteDatabase.insert("HOADON", null, values);
        sqLiteDatabase.close();
    }



}
