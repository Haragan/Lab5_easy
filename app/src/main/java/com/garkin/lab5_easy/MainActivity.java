package com.garkin.lab5_easy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextFirstName, editTextMiddleName, editTextLastName;
    private TextView tvIdPerson;
    private PersonDbHelper personDbHelper;

    private Long idPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        personDbHelper = new PersonDbHelper(this);

        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextMiddleName = (EditText) findViewById(R.id.editTextMiddleName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);

        tvIdPerson = (TextView) findViewById(R.id.tvIdPerson);
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_LONG).show();
    }

    private void clearText() {
        tvIdPerson.setText("");
        editTextFirstName.getText().clear();
        editTextMiddleName.getText().clear();
        editTextLastName.getText().clear();
    }

    private void createPerson(){
        Person person = new Person(
                editTextFirstName.getText().toString(),
                editTextMiddleName.getText().toString(),
                editTextLastName.getText().toString()
        );
        idPerson = personDbHelper.createPerson(person);

        if (idPerson > 0) {
            tvIdPerson.setText(String.format("ID заявки - %d", idPerson));
            Toast.makeText(getApplicationContext(), "Человек успешно создан",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Не все поля заполнены",Toast.LENGTH_LONG).show();
        }
    }

    private void updatePerson(){
        Person person = new Person(
                idPerson,
                editTextFirstName.getText().toString(),
                editTextMiddleName.getText().toString(),
                editTextLastName.getText().toString()
        );
        int result = personDbHelper.updatePerson(person);
        String message;
        if (result == 1) {
            message = "Заявка успешно обновлена";
        } else {
            message = "Заявка не обновлена!";
        }
        showToast(message);
    }

    private void deletePerson(){
        int result = personDbHelper.deletePerson(idPerson);

        if (result == 1) {
            Toast.makeText(getApplicationContext(), "Заявка успешно удалена!",Toast.LENGTH_LONG).show();
            clearText();
        } else {
            Toast.makeText(getApplicationContext(), "Заполните все поля, заявка не создана!",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_new_person:
                clearText();
                break;
            case R.id.action_create:
                createPerson();
                break;
            case R.id.action_edit:
                updatePerson();
                break;
            case R.id.action_delete:
                deletePerson();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
