package com.bigdatafield;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");

		InputFileData input = new InputFileData();
		input.setCount(2);
		input.setId("run1");
		input.setTimeStamp(Calendar.getInstance().getTime());
		input.setStatus("READY");

		OutputFileData output1 = new OutputFileData();
		output1.setId("run1");
		OutputFileData output2 = new OutputFileData();
		output2.setId("run1");

		SparkConf conf = new SparkConf();
		conf.setMaster("local").setAppName("AppCountComparison");

		JavaSparkContext sc = new JavaSparkContext(conf);

		JavaRDD<InputFileData> filterRDD = inputRDD(input, sc);

		System.out.println(filterRDD.count());
		
		ArrayList<OutputFileData> outputFileDataList = new ArrayList<OutputFileData>();
		outputFileDataList.add(output1);
		outputFileDataList.add(output2);
		
		JavaRDD<OutputFileData> outputRDD = sc.parallelize(outputFileDataList);
		
		outputRDD.filter(new Function<OutputFileData, Boolean>() {
			@Override
			public Boolean call(OutputFileData arg0) throws Exception {
				
				return null;
			}
		});
		
	}

	private static JavaRDD<InputFileData> inputRDD(InputFileData input, JavaSparkContext sc) {
		ArrayList<InputFileData> fileDataList = new ArrayList<InputFileData>();
		fileDataList.add(input);

		JavaRDD<InputFileData> inputRDD = sc.parallelize(fileDataList);

		JavaRDD<InputFileData> filterRDD = inputRDD.filter(new Function<InputFileData, Boolean>() {
			private static final long serialVersionUID = 3794780603743272886L;

			@Override
			public Boolean call(InputFileData input) throws Exception {
				// Date currentDate = Calendar.getInstance().getTime();
				// if(input.getTimeStamp().compareTo(currentDate) > 0) {
				// return true;
				// }
				if (input.getStatus().equalsIgnoreCase("READY")) {
					return true;
				}
				return false;
			}
		});
		return filterRDD;
	}
}
