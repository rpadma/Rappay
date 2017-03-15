package rappay.com.rohit.rapidgrid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MontlySaving extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_montly_saving);

        BarChart bc=(BarChart)findViewById(R.id.barchart1);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1f, 0));
        entries.add(new BarEntry(2f, 1));
        entries.add(new BarEntry(3f, 2));
        entries.add(new BarEntry(6f, 3));
        entries.add(new BarEntry(10f, 4));
        entries.add(new BarEntry(10f, 5));
        entries.add(new BarEntry(9f, 6));
        entries.add(new BarEntry(9f, 7));
        entries.add(new BarEntry(6f, 8));
        entries.add(new BarEntry(4f, 9));
        entries.add(new BarEntry(3f, 10));
        entries.add(new BarEntry(1f, 11));

        BarDataSet dataset = new BarDataSet(entries, " Savings in Thousands");
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        labels.add("August");
        labels.add("September");
        labels.add("October");
        labels.add("November");
        labels.add("December");


        BarData data = new BarData(labels, dataset);
        dataset.setColors(ColorTemplate.VORDIPLOM_COLORS);
        bc.setData(data);
        bc.setDescription("# Saving made in 2015");

    }
}
