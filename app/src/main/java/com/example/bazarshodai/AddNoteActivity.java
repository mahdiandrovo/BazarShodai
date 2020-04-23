package com.example.bazarshodai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNoteActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_TITLE = "androidx.appcompat.app.AppCompatActivity.EXTRA_TITLE";
    public static final String EXTRA_AMOUNT = "androidx.appcompat.app.AppCompatActivity.EXTRA_AMOUNT";
    public static final String EXTRA_AMOUNT_TYPE = "androidx.appcompat.app.AppCompatActivity.EXTRA_AMOUNT_TYPE";
    public static final String EXTRA_DESCRIPTION = "androidx.appcompat.app.AppCompatActivity.EXTRA_DESCRIPTION";

    private EditText editText_Title;
    private EditText editText_Amount;
    private EditText editText_AmountType;
    private EditText editText_Description;
    private Button button_SaveNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editText_Title = (EditText) findViewById(R.id.title_EditText);
        editText_Amount = (EditText) findViewById(R.id.amount_EditText);
        editText_AmountType = (EditText) findViewById(R.id.amountType_EditText);
        editText_Description = (EditText) findViewById(R.id.description_EditText);
        button_SaveNote = (Button) findViewById(R.id.save_Button);
        button_SaveNote.setOnClickListener(this);

    }

    private void saveNote(){
        String title = editText_Title.getText().toString().trim();
        String amount_str = editText_Amount.getText().toString().trim();
        String amount_type = editText_AmountType.getText().toString().trim();
        String description = editText_Description.getText().toString().trim();

        if (title == null || title.isEmpty()){
            editText_Title.setError("Enter Title");
            editText_Title.requestFocus();
            return;
        }
        if (amount_str == null || amount_str.isEmpty()){
            editText_Amount.setError("Enter Valid Amount");
            editText_Amount.requestFocus();
            return;
        }
        if (amount_type == null || amount_type.isEmpty()){
            editText_AmountType.setError("Enter Title");
            editText_AmountType.requestFocus();
            return;
        }
        if (description == null || description.isEmpty()){
            editText_Description.setError("Enter Description");
            editText_Description.requestFocus();
            return;
        }

        int amount = Integer.parseInt(amount_str);

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_AMOUNT,amount);
        data.putExtra(EXTRA_AMOUNT_TYPE,amount_type);
        data.putExtra(EXTRA_DESCRIPTION,description);
        setResult(RESULT_OK,data);
        finish();

    }

    @Override
    public void onClick(View view) {
        if (view == button_SaveNote){
            saveNote();
        }
    }
}
