package com.pavitharan.tha_204151x.activities;

import static com.pavitharan.tha_204151x.utils.Validator.DecimalPointValidator;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.pavitharan.tha_204151x.R;
import com.pavitharan.tha_204151x.utils.Validator;

public class FormActivity extends AppCompatActivity {
    EditText item_name;
    EditText item_description;
    EditText item_price;
    Button edit_item;
    Button delete_item;
    Button add_item;
    AlertDialog.Builder prompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        item_name = findViewById(R.id.et_form_name);
        item_description = findViewById(R.id.et_form_description);
        item_price = findViewById(R.id.et_form_price);
        edit_item = findViewById(R.id.btn_form_update);
        delete_item = findViewById(R.id.btn_form_delete);
        add_item = findViewById(R.id.btn_form_add);

        item_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = item_price.getText().toString();
                if (str.isEmpty()) return;
                String str2 = DecimalPointValidator(str, 2);

                if (!str2.equals(str)) {
                    item_price.setText(str2);
                    item_price.setSelection(str2.length());
                }
            }
        });

        Intent i = getIntent();
        if (i.getExtras().getInt("type") == 1) {
            edit_item.setVisibility(View.GONE);
            delete_item.setVisibility(View.GONE);
            add_item.setVisibility(View.VISIBLE);
            add_item.setOnClickListener(v -> {
                try {
                    Validator.InputsValidator(item_name.getText().toString(), item_description.getText().toString(), item_price.getText().toString());
                    Intent intent = new Intent();
                    intent.putExtra("Title", item_name.getText().toString());
                    intent.putExtra("Description", item_description.getText().toString());
                    intent.putExtra("Price", Double.valueOf(item_price.getText().toString()));
                    setResult(1, intent);
                    finish();
                } catch (RuntimeException e) {
                    prompt = new AlertDialog.Builder(this);
                    prompt.setTitle("Information");
                    prompt.setMessage(e.getMessage());
                    prompt.setPositiveButton("OK", (dialog, which) -> {
                        dialog.dismiss();
                    });
                    prompt.show();
                }
            });
        } else {
            item_name.setText(i.getExtras().getString("Title"));
            item_description.setText(i.getExtras().getString("Description"));
            item_price.setText(String.valueOf(i.getExtras().getDouble("Price")));
            edit_item.setOnClickListener(v -> {
                try{
                    Validator.InputsValidator(item_name.getText().toString(), item_description.getText().toString(), item_price.getText().toString());
                    Intent intent = new Intent();
                    intent.putExtra("Title", item_name.getText().toString());
                    intent.putExtra("Description", item_description.getText().toString());
                    intent.putExtra("Price", Double.valueOf(item_price.getText().toString()));
                    intent.putExtra("Position", getIntent().getIntExtra("Position", 0));
                    intent.putExtra("ID", getIntent().getIntExtra("ID", 0));
                    setResult(2, intent);
                    finish();
                }catch (Exception e){
                    prompt = new AlertDialog.Builder(this);
                    prompt.setTitle("Information");
                    prompt.setMessage(e.getMessage());
                    prompt.setPositiveButton("OK", (dialog, which) -> {
                        dialog.dismiss();
                    });
                    prompt.show();
                }
            });

            delete_item.setOnClickListener(v -> {
                prompt = new AlertDialog.Builder(this);
                prompt.setTitle("Warning");
                prompt.setMessage("Do you want delete this item? This action cannot be undone");
                prompt.setPositiveButton("Yes", (dialog, which) -> {
                    Intent intent = new Intent();
                    intent.putExtra("Position", getIntent().getIntExtra("Position", 0));
                    setResult(3, intent);
                    finish();
                    dialog.dismiss();
                });
                prompt.setNegativeButton("No", (dialog, which) -> {
                    finish();
                    dialog.dismiss();
                });
                prompt.show();
            });
        }
    }
}
