K-Tough
=======
This is a trial for calculating the TOUGHNESS for any given graph.

#Usage 
* To compile and package the project,
	sbt package

* To submit package to spark in local mode
	spark-submit --class "Toughness" --master local[4] target/scala-2.10/your-project_2.10-1.0.jar

* To submit package to spark in stand alone cluster mode
	spark-submit --class "Toughness" --master spark://cluster07:7077 your-project_2.10-1.0.jar

