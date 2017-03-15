package rappay.com.rohit.rapidgrid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class YearlyVendors extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yearly_vendors);

        BarChart bc=(BarChart)findViewById(R.id.barchart12);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(30f, 0));
        entries.add(new BarEntry(25f, 1));
        entries.add(new BarEntry(13f, 2));
        entries.add(new BarEntry(45f, 3));
        entries.add(new BarEntry(15f, 4));


        BarDataSet dataset = new BarDataSet(entries, " spent in thousands");
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("LifeStyle");
        labels.add("Vijaya diagnosis center");
        labels.add("KFC");
        labels.add("Big Bazaar");
        labels.add("Apolla pharms");



        BarData data = new BarData(labels, dataset);
        dataset.setColors(ColorTemplate.VORDIPLOM_COLORS);
        bc.setData(data);
        bc.setDescription("# Most visited Vendors");
    }
}
