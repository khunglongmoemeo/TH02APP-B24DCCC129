package com.example.personalexpensemanagement2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Expense> expenseList;
    private ExpenseAdapter adapter;
    private ListView lvExpenses;
    private TextView tvTotalAmount;
    private Button btnAddExpense;
    private DecimalFormat formatter = new DecimalFormat("#,###");

    private static final int REQUEST_ADD = 100;
    private static final int REQUEST_DETAIL = 200;
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvExpenses = findViewById(R.id.lvExpenses);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        btnAddExpense = findViewById(R.id.btnAddExpense);

        // Khởi tạo dữ liệu mẫu ban đầu
        expenseList = new ArrayList<>();
        expenseList.add(new Expense("1", "Ăn sáng", 30000, "Ăn uống", "12/10/2026", "Phở bò"));
        expenseList.add(new Expense("2", "Đổ xăng", 50000, "Đi lại", "12/10/2026", "Đổ xăng xe máy"));
        expenseList.add(new Expense("3", "Mua sách", 120000, "Học tập", "13/10/2026", "Sách Lập trình Android"));

        adapter = new ExpenseAdapter(this, expenseList);
        lvExpenses.setAdapter(adapter);
        updateTotalAmount();

        // Xử lý nút thêm khoản chi
        btnAddExpense.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
            startActivityForResult(intent, REQUEST_ADD);
        });

        // Xử lý nhấn vào dòng để xem chi tiết
        lvExpenses.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            Expense expense = expenseList.get(position);
            Intent intent = new Intent(MainActivity.this, ExpenseDetailActivity.class);
            intent.putExtra("expense", expense);
            startActivityForResult(intent, REQUEST_DETAIL);
        });
    }

    private void updateTotalAmount() {
        double total = 0;
        for (Expense e : expenseList) {
            total += e.getAmount();
        }
        tvTotalAmount.setText(formatter.format(total) + " đ");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_ADD) {
                // Nhận khoản chi mới được thêm
                Expense newExpense = (Expense) data.getSerializableExtra("new_expense");
                if (newExpense != null) {
                    expenseList.add(newExpense);
                    adapter.notifyDataSetChanged();
                    updateTotalAmount();
                }
            } else if (requestCode == REQUEST_DETAIL) {
                // Xử lý xóa khoản chi từ màn hình chi tiết
                boolean isDeleted = data.getBooleanExtra("is_deleted", false);
                if (isDeleted && selectedPosition != -1) {
                    expenseList.remove(selectedPosition);
                    adapter.notifyDataSetChanged();
                    updateTotalAmount();
                    selectedPosition = -1;
                }
            }
        }
    }
}