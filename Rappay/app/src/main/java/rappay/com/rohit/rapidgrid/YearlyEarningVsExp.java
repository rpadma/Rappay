package rappay.com.rohit.rapidgrid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class YearlyEarningVsExp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yearly_earning_vs_exp);


        PieChart pieChart1 = (PieChart) findViewById(R.id.chartee1);

        ArrayList<Entry> entries1 = new ArrayList<>();
        entries1.add(new Entry(500f, 0));
        entries1.add(new Entry(400f, 1));
        PieDataSet dataset1 = new PieDataSet(entries1, "spent in Thousands");
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Earning");
        labels.add("Expenditure");
        PieData data1 = new PieData(labels, dataset1); // initialize Piedata
        pieChart1.setData(data1);
        dataset1.setColors(ColorTemplate.LIBERTY_COLORS);
        pieChart1.setDescription("In 2013");


        PieChart pieChart2 = (PieChart) findViewById(R.id.chartee2);

        ArrayList<Entry> entries2 = new ArrayList<>();
        entries2.add(new Entry(500f, 0));
        entries2.add(new Entry(450f, 1));
        PieDataSet dataset2 = new PieDataSet(entries2, "spent in Thousands");
        PieData data2 = new PieData(labels, dataset2); // initialize Piedata
        pieChart2.setData(data2);
        dataset2.setColors(ColorTemplate.PASTEL_COLORS);
        pieChart2.setDescription("In 2014");



        PieChart pieChart3 = (PieChart) findViewById(R.id.chartee3);

        ArrayList<Entry> entries3 = new ArrayList<>();
        entries3.add(new Entry(500f, 0));
        entries3.add(new Entry(550f, 1));
        PieDataSet dataset3 = new PieDataSet(entries3, "spent in Thousands");
        PieData data3 = new PieData(labels, dataset3); // initialize Piedata
        pieChart3.setData(data3);
        dataset3.setColors(ColorTemplate.JOYFUL_COLORS);
        pieChart3.setDescription("In 2015");



    }
}
