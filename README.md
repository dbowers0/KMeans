# KMeans
This repository contains an implementation of the kmeans clustering algorithm in Java.

How to run
Clone this repository:
Copy Code: git clone https://github.com/your-username/kmeans.git

Navigate to the root directory of the repository:

Copy code: cd kmeans

Compile the Java files:

Copy code: javac *.java

Run the Kmeans class, passing in the path to the data file, max number  of iterations, convergence threshold, and max number of runs as arguments:

Copy code: java Kmeans path/to/data/file "max number of iterations" "Convergence Threshold" "max number of runs"

Example: java Kmeans path/to/data/file 100 .001 100

File format
The data file should be in the following format:

The first line should contain three integers, separated by spaces:

The first integer is the number of points in the data set.

The second integer is the number of attributes for each point (including the true cluster label).

The third integer is the number of true clusters in the data set.

Each subsequent line should contain the attribute values for a single point, followed by the true cluster label for that point. The attribute values and true cluster label should be separated by spaces.

For example, the first four lines of the file iris_bezdek might look like this:

Copy code

150 5 3

5.1 3.5 1.4 0.2 0

4.9 3.0 1.4 0.2 0

4.7 3.2 1.3 0.2 0

This indicates that the data set contains 150 points, each point has 5 attributes (4 dimensions and a true cluster label),

and there are 3 true clusters (0, 1, or 2). In this example, the first three points all belong to cluster 0.

Example data files are located in phase5.zip
