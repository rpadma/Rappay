package rappay.com.rohit.rapidgrid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class YearlySavings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yearly_savings);

        PieChart pieChart = (PieChart) findViewById(R.id.chart);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(50f, 0));
        entries.add(new Entry(10f, 1));
        entries.add(new Entry(60f, 2));
        entries.add(new Entry(15f, 5));
        entries.add(new Entry(25f, 3));
        entries.add(new Entry(40f, 4));
        entries.add(new Entry(10f,6));

        PieDataSet dataset = new PieDataSet(entries, "spent in Thousands");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Food");
        labels.add("Travel");
        labels.add("Entertainment");
        labels.add("Education and Insurance policy");
        labels.add("Savings");
        labels.add("Paid Bills");
        labels.add("others");


        PieData data = new PieData(labels, dataset); // initialize Piedata
        pieChart.setData(data);
        dataset.setColors(ColorTemplate.PASTEL_COLORS);

        pieChart.setDescription("Categories");

    }
}
