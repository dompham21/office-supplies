package com.luv2code.shopme.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.luv2code.shopme.App;
import com.luv2code.shopme.R;
import com.luv2code.shopme.Utils.CustomToast;
import com.luv2code.shopme.ViewModel.ReportViewModel;

import java.util.ArrayList;
import java.util.List;

public class BuyHistoryWeekFragment extends Fragment {
    private View view;
    private ReportViewModel reportViewModel;
    AnyChartView chartBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_buy_history_year, container, false);
        
        setControl();
        setEvent();
        reportViewModel.getBuyHistory(App.getAuthorization(), 2);

        return view;
    }

    private void setControl() {
        reportViewModel = new ViewModelProvider(this).get(ReportViewModel.class);
        chartBar = view.findViewById(R.id.chart_bar);

    }

    private void setEvent() {
        reportViewModel.getBuyHistoryData().observe(getViewLifecycleOwner(), object -> {
            if (object == null) {
                CustomToast.makeText(getContext(), "Server error and please try after sometime!", CustomToast.LENGTH_SHORT, CustomToast.ERROR).show();
                return;
            }

            int result = object.getResult();
            String message = object.getMsg();

            if (result == 1 && object.getData() != null) {
                List<Double> listData = object.getData();
                List<String> label = object.getLabel();

                chartBar.setProgressBar(view.findViewById(R.id.progress_bar_chart_bar));

                List<DataEntry> data = new ArrayList<>();

                for (int i=0; i < listData.size(); i++) {
                    data.add(new ValueDataEntry(label.get(i), listData.get(i)));
                }
                Cartesian cartesian = AnyChart.column();

                Column column = cartesian.column(data);

                column.tooltip()
                        .titleFormat("{%X}")
                        .position(Position.CENTER_BOTTOM)
                        .anchor(Anchor.CENTER_BOTTOM)
                        .offsetX(0d)
                        .offsetY(5d)
                        .format("${%Value}{groupsSeparator: }");

                cartesian.animation(true);
                cartesian.title("Thống kê chi tiêu 7 ngày gần nhất");

                cartesian.yScale().minimum(0d);

                cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

                cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
                cartesian.interactivity().hoverMode(HoverMode.BY_X);


                chartBar.setChart(cartesian);




            } else if(result == 0 && message != null) {
                CustomToast.makeText(getContext(), message, CustomToast.LENGTH_LONG, CustomToast.ERROR).show();
            }
        });
    }
}