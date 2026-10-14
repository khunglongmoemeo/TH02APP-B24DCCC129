package com.example.personalexpensemanagement2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ExpenseAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Expense> expenseList;
    private DecimalFormat formatter = new DecimalFormat("#,###");

    public ExpenseAdapter(Context context, ArrayList<Expense> expenseList) {
        this.context = context;
        this.expenseList = expenseList;
    }

    @Override
    public int getCount() { return expenseList.size(); }

    @Override
    public Object getItem(int position) { return expenseList.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_expense, parent, false);
        }

        Expense expense = expenseList.get(position);

        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvAmount = convertView.findViewById(R.id.tvAmount);
        TextView tvCategory = convertView.findViewById(R.id.tvCategory);
        TextView tvDate = convertView.findViewById(R.id.tvDate);

        tvTitle.setText(expense.getTitle());
        tvAmount.setText(formatter.format(expense.getAmount()) + " đ");
        tvCategory.setText(expense.getCategory());
        tvDate.setText(expense.getDate());

        return convertView;
    }
}