package clusteringk;

import java.util.ArrayList;


public class DataPoint {
	
	private ArrayList<Double> dataPoints;
	private Centroid centroidAssigned;
	private Double distanceFromCentroid;
	private int index;
	private int randomCluster;
	private Double cohesionScore;
	private Double separationScore;
	private ArrayList<Double> separations;
	private Double swScore;
	private int externalClusterNum;
	public int getRandomCluster() {
		return randomCluster;
	}

	public void setRandomCluster(int randomCluster) {
		this.randomCluster = randomCluster;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Double getDistanceFromCentroid() {
		return distanceFromCentroid;
	}

	public void setDistanceFromCentroid(Double distanceFromCentroid) {
		this.distanceFromCentroid = distanceFromCentroid;
	}

	public ArrayList<Double> getDataPoints() {
		return dataPoints;
	}

	public void setDataPoints(ArrayList<Double> dataPoints) {
		this.dataPoints = dataPoints;
	}

	public Centroid getCentroidAssigned() {
		return centroidAssigned;
	}

	public void setCentroidAssigned(Centroid centroidAssigned) {
		this.centroidAssigned = centroidAssigned;
	}

	
	public DataPoint() {
		
	}

	public Double getCohesionScore() {
		return cohesionScore;
	}

	public void setCohesionScore(Double cohesionScore) {
		this.cohesionScore = cohesionScore;
	}

	public Double getSeparationScore() {
		return separationScore;
	}

	public void setSeparationScore(Double separationScore) {
		this.separationScore = separationScore;
	}

	public ArrayList<Double> getSeparations() {
		return separations;
	}

	public void setSeparations(ArrayList<Double> separations) {
		this.separations = separations;
	}

	public Double getSwScore() {
		return swScore;
	}

	public void setSwScore(Double swScore) {
		this.swScore = swScore;
	}

	public int getExternalClusterNum() {
		return externalClusterNum;
	}

	public void setExternalClusterNum(int externalClusterNum) {
		this.externalClusterNum = externalClusterNum;
	}
}

