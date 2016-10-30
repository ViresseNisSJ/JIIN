package com.triples.bf_skku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;

public class StartActivity extends AppCompatActivity {

    private static String HasVisited = "hasVisited";
    SharedPreferences sp;

    private NotesDbAdapter dbAdapter;
    private static final String TAG = "NotesDbAdapter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        getPreferences();

        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    // 값 불러오기
    public boolean getPreferences(){
        sp = getSharedPreferences("pref", MODE_PRIVATE);

        boolean hasVisited = sp.getBoolean(HasVisited, false);
        if(!hasVisited){
            SharedPreferences.Editor e = sp.edit();
            e.putBoolean(HasVisited, true);
            e.commit();
            this.dbAdapter = new NotesDbAdapter(this);
            copyExcelDataToDatabase();
            //copyExcelDataToDatabase2();
            return false;
        }
        return true;

    }

    private void copyExcelDataToDatabase() {
        Log.w("ExcelToDatabase", "copyExcelDataToDatabase()");

        Workbook workbook = null;
        Sheet sheet = null;
        Sheet sheet2 = null;

        try {
            InputStream is = getBaseContext().getResources().getAssets().open("notes.xls");
            workbook = Workbook.getWorkbook(is);

            if (workbook != null) {
                sheet = workbook.getSheet(0);

                if (sheet != null) {

                    int nMaxColumn = 3;
                    int nRowStartIndex = 0;
                    int nRowEndIndex = sheet.getColumn(nMaxColumn - 1).length - 1;
                    int nColumnStartIndex = 0;
                    int nColumnEndIndex = sheet.getRow(0).length - 1;

                    dbAdapter.open();
                    for (int nRow = nRowStartIndex; nRow <= nRowEndIndex; nRow++) {
                        String building_eng = sheet.getCell(nColumnStartIndex, nRow).getContents();
                        String building_name = sheet.getCell(nColumnStartIndex + 1, nRow).getContents();
                        String toilet = sheet.getCell(nColumnStartIndex +2, nRow).getContents();
                        dbAdapter.createNote(building_eng, building_name, toilet);
                    }
                    dbAdapter.close();
                } else {
                    System.out.println("Sheet is null!!");
                }

                sheet2 = workbook.getSheet(1);
                if (sheet2 != null) {

                    int nMaxColumn = 4;
                    int nRowStartIndex = 0;
                    int nRowEndIndex = sheet2.getColumn(nMaxColumn - 1).length - 1;
                    int nColumnStartIndex = 0;
                    int nColumnEndIndex = sheet2.getRow(0).length - 1;

                    dbAdapter.open();
                    for (int nRow = nRowStartIndex; nRow <= nRowEndIndex; nRow++) {
                        String building_eng = sheet2.getCell(nColumnStartIndex, nRow).getContents();
                        String class_num = sheet2.getCell(nColumnStartIndex + 1, nRow).getContents();
                        String class_type = sheet2.getCell(nColumnStartIndex +2, nRow).getContents();
                        String type_option = sheet2.getCell(nColumnStartIndex +3, nRow).getContents();
                        dbAdapter.createNote2(building_eng, class_num, class_type, type_option);
                    }
                    dbAdapter.close();
                } else {
                    System.out.println("Sheet is null!!");
                }
            } else {
                System.out.println("WorkBook is null!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }


}
