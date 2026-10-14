package com.example.personalexpensemanagement2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddExpenseActivity extends AppCompatActivity {
    private EditText edtTitle, edtAmount, edtNote;
    private Spinner spinnerCategory;
    private DatePicker datePicker;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        edtTitle = findViewById(R.id.edtTitle);
        edtAmount = findViewById(R.id.edtAmount);
        edtNote = findViewById(R.id.edtNote);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        datePicker = findViewById(R.id.datePicker);
        btnSave = findViewById(R.id.btnSave);

        // Thiết lập Spinner cho danh mục
        String[] categories = {"Ăn uống", "Đi lại", "Học tập", "Giải trí", "Khác"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerCategory.setAdapter(adapter);

        btnSave.setOnClickListener(v -> {
            String title = edtTitle.getText().toString().trim();
            String amountStr = edtAmount.getText().toString().trim();
            String category = spinnerCategory.getSelectedItem().toString();
            String note = edtNote.getText().toString().trim();

            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1;
            int year = datePicker.getYear();
            String date = String.format("%02d/%02d/%d", day, month, year);

            // Kiểm tra ràng buộc
            if (title.isEmpty()) {
                edtTitle.setError("Tên khoản chi không được để trống");
                return;
            }

            if (amountStr.isEmpty()) {
                edtAmount.setError("Vui lòng nhập số tiền");
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    edtAmount.setError("Số tiền phải lớn hơn 0");
                    return;
                }
            } catch (NumberFormatException e) {
                edtAmount.setError("Số tiền không hợp lệ");
                return;
            }

            // Tạo đối tượng Expense mới và trả về
            Expense newExpense = new Expense(String.valueOf(System.currentTimeMillis()), title, amount, category, date, note);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("new_expense", newExpense);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}