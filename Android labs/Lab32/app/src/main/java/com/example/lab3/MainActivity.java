package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button button1;
    Button button2;
    Button button3;
    TextView textView;
    String columns[] = {DBHelper.getKeyID(), DBHelper.getKeySurname(),DBHelper.getKeyName(),
            DBHelper.getKeyMiddleName(),DBHelper.getKeyDate()};
    ContentValues contentValues = new ContentValues();
    Integer lastID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.btn1);
        button2 = (Button) findViewById(R.id.btn2);
        button3 = (Button) findViewById(R.id.btn3);
        textView = (TextView) findViewById(R.id.textView);
        getDatabase().delete(DBHelper.getTableName(), null, null);
        for(int i = 0; i < 5; i++){
            insertRandomData();
        }


    }

    public void onClickBtn1(View view) {
        textView.setText("");
        Cursor cursor = getDatabase().query(DBHelper.getTableName(), columns, null,
                null, null, null, null);

        int IDColumnIndex = cursor.getColumnIndex(DBHelper.getKeyID());
        int surnameColumnIndex = cursor.getColumnIndex(DBHelper.getKeySurname());
        int nameColumnIndex = cursor.getColumnIndex(DBHelper.getKeyName());
        int midNameColumnIndex = cursor.getColumnIndex(DBHelper.getKeyMiddleName());
        int dateColumnIndex = cursor.getColumnIndex(DBHelper.getKeyDate());
        int currentID = 0;
        String currentSurname;
        String currentName;
        String currentMidName;
        String currentDate;
        while (cursor.moveToNext()) {
            currentID = cursor.getInt(IDColumnIndex);
            currentSurname = cursor.getString(surnameColumnIndex);
            currentName = cursor.getString(nameColumnIndex);
            currentMidName = cursor.getString(midNameColumnIndex);
            currentDate = cursor.getString(dateColumnIndex);
            textView.append(("\n" + currentID + " - " +
                    currentSurname + " - " +
                    currentName + " - " +
                    currentMidName + " - " +
                    currentDate));
        }
        lastID = currentID;
    }

    public void onClickBtn2(View view) {
        insertRandomData();

    }

    public void onClickBtn3(View view) {
        contentValues.put("Фамилия", "Иванов");
        contentValues.put("Имя", "Иван");
        contentValues.put("Отчество", "Иванович");
        getDatabase().update(DBHelper.getTableName(),contentValues,DBHelper.getKeyID() +
                "= ?",new String[]{lastID.toString()});

    }

    public void insertRandomData() {
        String fNames[] = {"Игорь", "Станислав", "Андрей", "Пётр", "Александр"};
        String lNames[] = {"Горький", "Шлеменко", "Боварский", "Герасименко", "Торчков"};
        String mNames[] = {"Янович", "Альбертович", "Глебыч", "Александрович", "Никитич"};
        String dates[] = {"2017-08-31", "2014-03-22", "2008-12-17", "2019-06-06", "2018-09-15"};
        int indexFNames = (int) (Math.random() * fNames.length);
        int indexLNames = (int) (Math.random() * lNames.length);
        int indexMNames = (int) (Math.random() * mNames.length);
        int indexDates = (int) (Math.random() * dates.length);
        String name = fNames[indexFNames];
        String surname = lNames[indexLNames];
        String midName = mNames[indexMNames];
        contentValues.put("Фамилия", surname);
        contentValues.put("Имя", name);
        contentValues.put("Отчество", midName);
        contentValues.put("DATE", dates[indexDates]);
        getDatabase().insert(DBHelper.getTableName(), null, contentValues);
    }

    public SQLiteDatabase getDatabase(){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        return database;
    }


}
