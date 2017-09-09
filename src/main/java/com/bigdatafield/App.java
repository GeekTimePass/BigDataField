package com.bigdatafield;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.broadcast.Broadcast;

import scala.Tuple2;

/**
 *
 */
public class App {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf();
		conf.setMaster("local").setAppName("ReconApp");

		JavaSparkContext sc = new JavaSparkContext(conf);

		JavaRDD<InputFileData> inputFileRDD1 = inputRDD(sc);
		
		JavaPairRDD<String, BigInteger> mapToPair = inputFileRDD1.mapToPair(new PairFunction<InputFileData, String, BigInteger>() {

			private static final long serialVersionUID = -7082896884660349716L;

			@Override
			public Tuple2<String, BigInteger> call(InputFileData arg0) throws Exception {
				return new Tuple2<String, BigInteger>(arg0.getRunId(), arg0.getCount());
			}
		});
		
		JavaRDD<OutputFileData> outputFileRDD1 = getOutputRDD(sc);
		
		for (Entry<String, BigInteger> entry : mapToPair.collectAsMap().entrySet())
		{
		    BigInteger count = entry.getValue();
			String key = entry.getKey();
			System.out.println(key + "/" + count);
		    
			JavaRDD<OutputFileData> filterByParentRdd = outputFileRDD1
					.filter(new Function<OutputFileData, Boolean>() {
						private static final long serialVersionUID = 8316558109766688821L;

						@Override
						public Boolean call(OutputFileData arg0) throws Exception {
							if (arg0.getRunId().equals(key)) {
								return true;
							}
							return false;
						}
					});
			System.out.println(count +":"+ filterByParentRdd.count());
		}
	}

	private static JavaRDD<OutputFileData> getOutputRDD(JavaSparkContext sc) {
		OutputFileData output1 = new OutputFileData();
		output1.setRunId("run1");

		OutputFileData output3 = new OutputFileData();
		output3.setRunId("run2");
		OutputFileData output4 = new OutputFileData();
		output4.setRunId("run2");

		ArrayList<OutputFileData> outputFileDataList = new ArrayList<OutputFileData>();
		outputFileDataList.add(output1);
		outputFileDataList.add(output3);
		outputFileDataList.add(output4);

		return sc.parallelize(outputFileDataList);
	}

	private static JavaRDD<InputFileData> inputRDD(JavaSparkContext sc) {
		InputFileData input1 = new InputFileData();
		input1.setCount(BigInteger.valueOf(1));
		input1.setRunId("run1");
		input1.setTimeStamp(Calendar.getInstance().getTime());
		input1.setStatus("READY");

		InputFileData input2 = new InputFileData();
		input2.setCount(BigInteger.valueOf(2));
		input2.setRunId("run2");
		input2.setTimeStamp(Calendar.getInstance().getTime());
		input2.setStatus("READY");

		ArrayList<InputFileData> fileDataList = new ArrayList<InputFileData>();
		fileDataList.add(input1);
		fileDataList.add(input2);

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
