package clusteringk;
import java.util.ArrayList;


public class Cluster {

	private ArrayList<DataPoint> cluster;
	private int indexOfClosestCluster;
	private Centroid centroidPoint;
	private int centroidIndex;
	
	public ArrayList<DataPoint> getCluster() {
		return cluster;
	}

	public void setCluster(ArrayList<DataPoint> cluster) {
		this.cluster = cluster;
	}

	public int getIndexOfClosestCluster() {
		return indexOfClosestCluster;
	}

	public void setIndexOfClosestCluster(int indexOfClosestCluster) {
		this.indexOfClosestCluster = indexOfClosestCluster;
	}

	public Centroid getCentroidPoint() {
		return centroidPoint;
	}

	public void setCentroidPoint(Centroid centroidPoint) {
		this.centroidPoint = centroidPoint;
	}

	public int getCentroidIndex() {
		return centroidIndex;
	}

	public void setCentroidIndex(int centroidIndex) {
		this.centroidIndex = centroidIndex;
	}
}
