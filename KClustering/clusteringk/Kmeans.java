package clusteringk;
//Darius Bowers


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Kmeans {

	public static void main(String[] args) {
		String filename = args[0];
		int numberofPoints = 0;
		String iterations = args[1];
		int numberofIterations = Integer.parseInt(iterations);
		String converge = args[2];
		String runs = args[3];
		Double convergenceThreshold = Double.parseDouble(converge);
		int numberofRuns = Integer.parseInt(runs);
		int dimensionality = 0;
		int totalPoints = 0;
		File file = new File(filename);
		int bestRun = 0;
		Double bestSSE = Double.MAX_VALUE;
		Double bestRand = Double.MIN_VALUE;
		Double bestJaccard = Double.MIN_VALUE;
		Double bestFM = Double.MIN_VALUE;
		ArrayList<Cluster> bestClusters = new ArrayList<>();
      ArrayList<Double> dataList = new ArrayList<>();
      int numberofClusters = 0;
      ArrayList<Integer> externalClusterNumber = new ArrayList<>();
      try {
          Scanner sc = new Scanner(file);
          String num = sc.next();
          numberofPoints = Integer.parseInt(num);
          String dim = sc.next();
          dimensionality = Integer.parseInt(dim);
          String clusters = sc.next();
          numberofClusters = Integer.parseInt(clusters);
          int point = 0;
          totalPoints = numberofPoints * dimensionality;
          dimensionality = dimensionality - 1;
          int pointNum = 1;
          
          while (point < totalPoints) {
        	  String d = sc.next();
        	  if(pointNum%(dimensionality+1) == 0) {
        		  int data = Integer.parseInt(d);
        		  externalClusterNumber.add(data);
        	  }
        	  else {
        		  Double dataP = Double.parseDouble(d);
                  dataList.add(dataP);
        	  }
              point++;
              pointNum++;
          }
          sc.close();
      } 
      catch (FileNotFoundException e) {
          e.printStackTrace();
      }
      
      ArrayList<DataPoint> arrayList = new ArrayList<>();
      for(int i = 0; i < numberofRuns; i++) {
      	System.out.println("Run " + (i+1));
      	System.out.println("-----");
	        arrayList = setArrayLists(dataList, dimensionality);
	        ArrayList<DataPoint> newList = new ArrayList<>();
	        
	        newList = minMaxNormalization(arrayList, dimensionality);
	        arrayList = newList;
	        
	        setAssignedClusterNumber(arrayList, externalClusterNumber);
	        //trade off to switch between initialization methods
	        //ArrayList<Cluster> clusters = randomPartition(arrayList, numberofClusters, dimensionality);
	        ArrayList<Centroid> list = setCentroidPoints(numberofClusters, arrayList);
	        
	        int j = 1;
	        int a = 0;
	        Double sse = 0.0;
	        Double pastSSE = Double.MAX_VALUE;
	        ArrayList<Cluster> clusters = new ArrayList<>();
	        while(a < numberofIterations) {
	        	//put into random clusters
	        	//calculate centroid of clusters
	        	//assign points to centroid
	        	//calculate sse
	        	//reassign centroids
	        	
	        	//trade off to switch between initialization methods
	        	//assignCentroids(arrayList, list, numberofClusters);
	        	//ArrayList<Centroid> list = reassignCentroids(clusters, numberofClusters, dimensionality);
	        	assignCentroids(arrayList, list, numberofClusters);
	        	
		        sse = generateSSE(arrayList);
		        System.out.println("Iteration " + j + ": SSE = " + sse);
		        j++;
		        if((pastSSE - sse) / (pastSSE) < convergenceThreshold) {
		        	break;
		        }
		        pastSSE = sse;
		        
		        //ArrayList <Cluster>
		        clusters = setClusters(arrayList, numberofClusters, list);
		        list = reassignCentroids(clusters, numberofClusters, dimensionality);
		        a++;
	        }
	        if(sse < bestSSE) {
	        	bestSSE = sse;
	        	bestClusters = clusters;
	        	bestRun = i+1;
	        }
	        System.out.println();
	        
	        Double rand = rand(arrayList);
	        Double jaccard = jaccard(arrayList);
	        Double fm = fmMeasure(arrayList);
	        
	        if(bestRand < rand) {
	        	bestRand = rand;
	        }
	        
	        if(bestJaccard < jaccard) {
	        	bestJaccard = jaccard;
	        }
	        
	        if(bestFM < fm) {
	        	bestFM = fm;
	        }
    		}
      System.out.println("Best Rand: " + bestRand);
      System.out.println("Best Jaccard: " + bestJaccard);
      System.out.println("Best FM: " + bestFM);
      
      System.out.println();
      System.out.println("Best Run: " + bestRun + ": SSE = " + bestSSE);
	}
	
	public static ArrayList<DataPoint> minMaxNormalization(ArrayList<DataPoint> arrayList, int dimensionality) {
		int newMax = 1;
		int newMin = 0;
		ArrayList<DataPoint> newList = new ArrayList<>();
		DataPoint dataPoint = new DataPoint();
		ArrayList<Double> tempList = new ArrayList<>();
		ArrayList<Double> tempList2;
		ArrayList<Double> maxList = new ArrayList<>();
		ArrayList<Double> minList = new ArrayList<>();
		
		Double min = 0.0;
		Double max = 0.0;
		for(int i = 0; i < dimensionality; i++) {
			tempList2 = new ArrayList<>();
			for(int j = 0; j < arrayList.size(); j++) {
				dataPoint = arrayList.get(j);
				tempList = dataPoint.getDataPoints();
				tempList2.add(tempList.get(i));
			}
			max = findMax(tempList2);
			min = findMin(tempList2);
			
			maxList.add(max);
			minList.add(min);
		}
		
		DataPoint tempPoint = new DataPoint();
		DataPoint newDataPoint;
		ArrayList<Double> list = new ArrayList<>();
		ArrayList<Double> tempArrayList;
		Double num = 0.0;
		for(int i = 0; i < arrayList.size(); i++) {
			tempArrayList = new ArrayList<>();
			tempPoint = arrayList.get(i);
			list = tempPoint.getDataPoints();
			for(int j = 0; j < dimensionality; j++) {
				num = list.get(j);

				num = ((num - minList.get(j)) / (maxList.get(j) - minList.get(j)) * (newMax-newMin) + (newMin));
				
				tempArrayList.add(num);
			}
			
			newDataPoint = new DataPoint();
			newDataPoint.setDataPoints(tempArrayList);
			newList.add(newDataPoint);
		}
		
		return newList;
	}
	public static Double findMin(ArrayList<Double> tempList2) {
		Double min = Double.MAX_VALUE;
		for(int i = 0; i < tempList2.size(); i++) {
			if(tempList2.get(i) < min) {
				min = tempList2.get(i);
			}
		}
		return min;
	}
	public static Double findMax(ArrayList<Double> tempList2) {
		Double max = Double.MIN_VALUE;
		for(int i = 0; i < tempList2.size(); i++) {
			if(tempList2.get(i) > max) {
				max = tempList2.get(i);
			}
		}
		return max;
	}
	
	public static ArrayList<DataPoint> setArrayLists(ArrayList<Double> dataList, int dimensionality) {
	  ArrayList<DataPoint> pointList = new ArrayList<>();
      ArrayList<Double> dataPoints = new ArrayList<>();
      for(int i = 0; i < dataList.size(); i++) {
  	   if(i % dimensionality == 0 && i != 0) {
  		   DataPoint dataPoint = new DataPoint();
  		   dataPoint.setDataPoints(dataPoints);
  		   pointList.add(dataPoint);
  		   dataPoints = new ArrayList<>();
  		   dataPoints.add(dataList.get(i));
  	   }
  	   else {
  		   dataPoints.add(dataList.get(i));
  		   if(i == dataList.size()-1) {
  			   DataPoint dataPoint = new DataPoint();
      		   dataPoint.setDataPoints(dataPoints);
      		   pointList.add(dataPoint);
  		   }
  	   }
     }
      
      return pointList;
	}
	
	
	public static int generateRandomNum(int upperbound) {
		Random rand = new Random();
	    int int_random = rand.nextInt(upperbound); 
		return int_random;
	}
	
	
	public static ArrayList<Centroid> setCentroidPoints(int numberofClusters, ArrayList<DataPoint> arrayList) {
		ArrayList<Centroid> centroids = new ArrayList<>();
		int randomNum = 0;
		for(int i = 0; i < numberofClusters; i++) {
			randomNum = generateRandomNum(arrayList.size());
			Centroid centroid = new Centroid();
			DataPoint dataPoint = new DataPoint();
			dataPoint = arrayList.get(randomNum);
			centroid.setCentroidPoint(dataPoint.getDataPoints());
			centroids.add(centroid);
		}
		return centroids;
	}
	
	public static void assignCentroids(ArrayList<DataPoint> arrayList, ArrayList<Centroid> centroids, int numberofClusters) {
		for(int i = 0; i < arrayList.size(); i++) {
			int index = 0;
			DataPoint dataPoint = new DataPoint();
			dataPoint = arrayList.get(i);
			Double min = Double.MAX_VALUE;
			Double distance = 0.0;
			for(int j = 0; j < numberofClusters; j++) {
				Centroid centroid = new Centroid();
				centroid = centroids.get(j);
				distance = calculateDistance(centroid, dataPoint);
				if(min > distance) {
					min = distance;
					index = j;
				}
			}
			dataPoint.setCentroidAssigned(centroids.get(index));
			dataPoint.setDistanceFromCentroid(min);
			dataPoint.setIndex(index);
		}
	}
	
	public static void setAssignedClusterNumber(ArrayList<DataPoint> arrayList, ArrayList<Integer> externalClusterNumber) {
		DataPoint dataPoint = new DataPoint();
		for(int i = 0; i < arrayList.size(); i++) {
			dataPoint = arrayList.get(i);
			dataPoint.setExternalClusterNum(externalClusterNumber.get(i));
		}
	}
	public static Double calculateDistance(Centroid centroid, DataPoint dataPoint) {
		Double distance = 0.0;
		ArrayList<Double> centroidPoints = centroid.getCentroidPoint();
		ArrayList<Double> dataPoints = dataPoint.getDataPoints();
		for(int i = 0; i < dataPoints.size(); i++) {
			distance += (dataPoints.get(i) - centroidPoints.get(i)) * (dataPoints.get(i) - centroidPoints.get(i));
		}
		return distance;
		
	}
	
	
	public static Double generateSSE(ArrayList<DataPoint> arrayList) {
		Double sse = 0.0;
		DataPoint dataPoint = new DataPoint();
		for(int i = 0; i < arrayList.size(); i++) {
			dataPoint = arrayList.get(i);
			sse += dataPoint.getDistanceFromCentroid();
		}
		return sse;
	}
	
	public static ArrayList<Cluster> setClusters(ArrayList<DataPoint> arrayList, int numberofClusters, ArrayList<Centroid> list) {
		ArrayList<Cluster> clusters = new ArrayList<>();
		Cluster cluster = new Cluster();
		DataPoint dataPoint = new DataPoint();
		ArrayList<DataPoint> clusterPoints = new ArrayList<>();
		for(int i = 0; i < numberofClusters; i++) {
			for(int j = 0; j < arrayList.size(); j++) {
				dataPoint = arrayList.get(j);
				if(dataPoint.getIndex() == i) {
					clusterPoints.add(dataPoint);
				}
			}
			cluster.setCluster(clusterPoints);
			clusters.add(cluster);
			clusterPoints = new ArrayList<>();
			cluster = new Cluster();
		}
		return clusters;
	}
	
	public static ArrayList<Centroid> reassignCentroids(ArrayList<Cluster> clusters, int numberofClusters, int dimensionality) {
		Cluster cluster = new Cluster();
		ArrayList<DataPoint> arrayList = new ArrayList<>();
		ArrayList<Centroid> centroids = new ArrayList<>();
		for(int i = 0; i < numberofClusters; i++) {
			ArrayList<Double> newPoints = new ArrayList<>();
			cluster = clusters.get(i);
			arrayList = cluster.getCluster();
			for(int s = 0; s < dimensionality; s++) {
				Double sum = 0.0;
				for(int j = 0; j < arrayList.size(); j++) {
					DataPoint dataPoint = new DataPoint();
					dataPoint = arrayList.get(j);
					ArrayList<Double> dataPoints = new ArrayList<>();
					dataPoints = dataPoint.getDataPoints();
					sum += dataPoints.get(s);
				}
				sum = sum / arrayList.size();
				newPoints.add(sum);
				}
			Centroid centroid = new Centroid();
			centroid.setCentroidPoint(newPoints);
			centroids.add(centroid);
			}
		return centroids;
	}
	
	/*public static ArrayList<Cluster> randomPartition(ArrayList<DataPoint> arrayList, int numberofClusters, int dimensionality) {
		ArrayList<Cluster> randomClusters = new ArrayList<>();
		ArrayList<DataPoint> tempList = arrayList;
		ArrayList<DataPoint> clusterPoints = new ArrayList<>();
		DataPoint dataPoint;
		for(int i = 0; i < tempList.size(); i++) {
			dataPoint = new DataPoint();
			int rand = generateRandomNum(numberofClusters);
			dataPoint = tempList.get(i);
			dataPoint.setRandomCluster(rand);
			clusterPoints.add(dataPoint);
		}
		
		Cluster cluster;
		DataPoint tempPoint = new DataPoint();
		ArrayList<DataPoint> tempCluster;
		for(int i = 0; i < numberofClusters; i++) {
			cluster = new Cluster();
			tempCluster = new ArrayList<>();
			for(int j = 0; j < clusterPoints.size(); j++) {
				tempPoint = clusterPoints.get(j);
				if(tempPoint.getRandomCluster() == i) {
					tempCluster.add(tempPoint);
				}
			}
			cluster.setCluster(tempCluster);
			randomClusters.add(cluster);
		}
		
		Cluster clusters = new Cluster();
		ArrayList<DataPoint> list = new ArrayList<>();
		for(int i = 0; i < numberofClusters; i++) {
			//System.out.println("Cluster " + i);
			clusters = randomClusters.get(i);
			list = clusters.getCluster();
			//System.out.println("Size of Cluster: " + list.size());
			DataPoint dataPoints = new DataPoint();
			for(int j = 0; j < list.size(); j++) {
				dataPoints = list.get(j);
				ArrayList<Double> points = dataPoints.getDataPoints();
				for(int s = 0; s < dimensionality; s++) {
					//System.out.print(points.get(s) + " ");
				}
				
			}
			//System.out.println();
			
		}
		return randomClusters;
		
	}*/
	
	
	public static ArrayList<Double> countPositiveNegatives(ArrayList<DataPoint> arrayList) {
		DataPoint dataPoint = new DataPoint();
		DataPoint dataPoint2 = new DataPoint();
		Double truePositive = 0.0;
		Double falsePositive = 0.0;
		Double trueNegative = 0.0;
		Double falseNegative = 0.0;
		ArrayList<Double> positiveNegatives = new ArrayList<>();
		int a = 0;
		for(int i = 0; i < arrayList.size(); i++) {
			dataPoint = arrayList.get(i);
			a++;
			for(int j = a; j < arrayList.size(); j++) {
				dataPoint2 = arrayList.get(j);
				if((dataPoint.getIndex() == dataPoint2.getIndex()) && (dataPoint.getExternalClusterNum() == dataPoint2.getExternalClusterNum())) {
					truePositive++;
				}
				
				else if((dataPoint.getIndex() == dataPoint2.getIndex()) && (dataPoint.getExternalClusterNum() != dataPoint2.getExternalClusterNum())) {
					falsePositive++;
				}
				else if((dataPoint.getIndex() != dataPoint2.getIndex())
						&& (dataPoint.getExternalClusterNum() == dataPoint2.getExternalClusterNum())) {
					trueNegative++;
				}
				else if((dataPoint.getIndex() == dataPoint2.getIndex())
						&& (dataPoint.getExternalClusterNum() != dataPoint2.getExternalClusterNum())) {
					trueNegative++;
				}
				else if((dataPoint.getExternalClusterNum() != dataPoint2.getExternalClusterNum())
						&& (dataPoint.getIndex() != dataPoint2.getIndex())) {
					trueNegative++;
				}
			}
		}
		positiveNegatives.add(truePositive);
		positiveNegatives.add(trueNegative);
		positiveNegatives.add(falsePositive);
		positiveNegatives.add(falseNegative);
		
		return positiveNegatives;
	}
	
	public static Double rand(ArrayList<DataPoint> arrayList) {
		ArrayList<Double> positiveNegatives = countPositiveNegatives(arrayList);
		Double truePositive = positiveNegatives.get(0);
		Double trueNegative = positiveNegatives.get(1);
		Double falsePositive = positiveNegatives.get(2);
		Double falseNegative = positiveNegatives.get(3);
		
		Double n = truePositive + trueNegative + falsePositive + falseNegative;
		return (double) ((truePositive + trueNegative) / n);
	}
	
	//TP FN FP
	
	public static Double jaccard(ArrayList<DataPoint> arrayList) {
		ArrayList<Double> positiveNegatives = countPositiveNegatives(arrayList);
		Double truePositive = positiveNegatives.get(0);
		Double trueNegative = positiveNegatives.get(1);
		Double falsePositive = positiveNegatives.get(2);
		Double falseNegative = positiveNegatives.get(3);
		
		Double numerator = truePositive;
		Double denominator = falseNegative + truePositive + falsePositive;
		return numerator / denominator;
		
	}
	
	public static Double fmMeasure(ArrayList<DataPoint> arrayList) {
		ArrayList<Double> positiveNegatives = countPositiveNegatives(arrayList);
		Double truePositive = positiveNegatives.get(0);
		Double trueNegative = positiveNegatives.get(1);
		Double falsePositive = positiveNegatives.get(2);
		Double falseNegative = positiveNegatives.get(3);
		
		Double prec = truePositive / (truePositive + falsePositive);
		Double recall = truePositive / (truePositive + falseNegative);
		
		return Math.sqrt(prec * recall);
	}
}

