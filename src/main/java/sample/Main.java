package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.commons.math3.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.*;

import static sample.Data.TRAINING_DATA;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
        Platform.setImplicitExit(true);
        XYChart.Series<Number,Number>series1=new XYChart.Series<>();
        XYChart.Series<Number,Number>series2=new XYChart.Series<>();
        IntStream.range(0,Data.TRAINING_DATA.length).forEach(i->
                series1.getData().add(new XYChart.Data<Number,Number>(Data.TRAINING_DATA[i][0][1],Data.TRAINING_DATA[i][1][0])));
        IntStream.range(0,Data.TRAINING_DATA.length).forEach(i->
                series1.getData().add(new XYChart.Data<Number,Number>(Data.TRAINING_DATA[i][0][1],linearRegression.getEstimate().getEntry(i,0))));
        NumberAxis xAxis=new NumberAxis(0,50,1);
        xAxis.setLabel("Amount in Ksh");
        NumberAxis yAxis=new NumberAxis(0,175,5);
        yAxis.setLabel("Bundles in mbs");
        ScatterChart<Number,Number>scatterChart=new ScatterChart<Number, Number>(xAxis,yAxis);
        scatterChart.getData().add(series1);
        LineChart<Number,Number>lineChart=new LineChart<>(xAxis,yAxis);
        lineChart.getData().add(series2);
        lineChart.setOpacity(.4);
        Pane pane=new Pane();
        pane.getChildren().addAll(scatterChart,lineChart);
        primaryStage.setScene(new Scene(pane,580,370));
        primaryStage.setOnHidden(event -> {
            try {
                handleCommandline();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Close display window to procceed to commandline funtionality");
        primaryStage.setTitle("Linear regression on safaricom no Expiry mbs");
        primaryStage.show();


    }
//     static double [][][]TRAINING_DATA={
//            {{1.0,5},{10}},
//            {{1.0,10},{20}},
//            {{1.0,15},{52}},
//            {{1.0,20},{70}},
//            {{1.0,25},{87}},
//            {{1.0,30},{105}},
//            {{1.0,35},{122}},
//             {{1.0,40},{140}},
//             {{1.0,45},{157}},
//             {{1.0,50},{175}},
//             {{1.0,55},{275}},
//             {{1.0,60},{300}},
//             {{1.0,65},{325}},
//             {{1.0,70},{350}},
//             {{1.0,75},{375}},
//             {{1.0,80},{400}},
//             {{1.0,85},{425}},
//             {{1.0,90},{450}},
//             {{1.0,95},{475}},
//             {{1.0,100},{500}},
//
//
//
//
//    };
static LinearRegression linearRegression;
    public static void main(String[] args) throws Exception {

        double[][]xArray=new double[TRAINING_DATA.length][TRAINING_DATA[0][0].length];
        double[][]yArray=new double[TRAINING_DATA.length][1];
       IntStream.range(0,TRAINING_DATA.length).forEach(i->{
           IntStream.range(0,TRAINING_DATA[0][0].length).forEach(j->xArray[i][j]=TRAINING_DATA[i][0][j]);
           yArray[i][0]=TRAINING_DATA[i][1][0];
       });
       linearRegression=new LinearRegression(xArray,yArray);
        launch(args);
    }
    static void handleCommandline() throws IOException {
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
        while (true)
        {
            System.out.println("To estimate bundles enter the your amount in Ksh or exit");
            try {
                String entry=bufferedReader.readLine();
                if (!entry.equals("exit"))
                    System.out.println("Estimated bundles  is "+linearRegression.estimatedBundles(entry));
                else System.exit(0);
            }
            catch (Exception e){
                System.out.println("invalid input");
            }
        }
    }
}
