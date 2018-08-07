import edu.duke.*;
import java.io.File;

public class PerimeterAssignmentRunner {
    public double getPerimeter (Shape s) {
        // Start with totalPerim = 0
        double totalPerim = 0.0;
        // Start wth prevPt = the last point 
        Point prevPt = s.getLastPoint();
        // For each point currPt in the shape,
        for (Point currPt : s.getPoints()) {
            // Find distance from prevPt point to currPt 
            double currDist = prevPt.distance(currPt);
            // Update totalPerim by currDist
            totalPerim = totalPerim + currDist;
            // Update prevPt to be currPt
            prevPt = currPt;
        }
        // totalPerim is the answer
        return totalPerim;
    }

    public int getNumPoints (Shape s) {
        // Task 1
        int totalPts = 0;
        for (Point pt : s.getPoints())
            totalPts += 1;
        return totalPts;
    }

    public double getAverageLength(Shape s) {
        // Task 3
        return getPerimeter(s) / getNumPoints(s);
    }

    public double getLargestSide(Shape s) {
        // Task 5
        // Start with max = 0
        double max = 0.0;
        // Start wth prevPt = the last point
        Point prevPt = s.getLastPoint();
        // For each point currPt in the shape,
        for (Point currPt : s.getPoints()) {
            // Find distance from prevPt point to currPt
            double currDist = prevPt.distance(currPt);
            // If currDist is greater then max, then assign max to currDist
            if (currDist > max)
                max = currDist;
            // Update prevPt to be currPt
            prevPt = currPt;
        }
        // max is the answer
        return max;
    }

    public double getLargestX(Shape s) {
        // Task 7
        // Start with max = 0
        double max = 0.0;
        // For each point currPt in the shape,
        for (Point currPt : s.getPoints()) {
            // If x coordinate of currPt is greater than max, then assign max to currPt
            double x = currPt.getX();
            if (x > max)
                max = x;
        }
        // max is the answer
        return max;
    }

    public double getLargestPerimeterMultipleFiles() {
        // A 2 Task 1
        double maxParam = 0.0;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            Shape s = new Shape(fr);
            double perimeter = getPerimeter(s);
            if (perimeter > maxParam)
                maxParam = perimeter;
        }
        return maxParam;
    }

    public String getFileWithLargestPerimeter() {
        // A 2 Task 3
        double maxParam = 0.0;
        String fileName = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            Shape s = new Shape(fr);
            double perimeter = getPerimeter(s);
            if (perimeter > maxParam) {
                maxParam = perimeter;
                fileName = f.getName();
            }
        }
        return fileName;
    }

    public void testPerimeter () {
        FileResource fr = new FileResource();
        Shape s = new Shape(fr);
        double length = getPerimeter(s);
        System.out.println("perimeter = " + length);
        // Task 2
        System.out.println("Total points in the shapes are " + getNumPoints(s));
        // Task 4
        System.out.println("Average length is " + getAverageLength(s));
        // Task 6
        System.out.println("The largest side is " + getLargestSide(s));
        // Task 8
        System.out.println("The largest x value is " + getLargestX(s));
    }
    
    public void testPerimeterMultipleFiles() {
        // A 2 Task 2
        System.out.println("The maximum perimeter is " + getLargestPerimeterMultipleFiles());
     }

    public void testFileWithLargestPerimeter() {
        // A 2 Task 4
        System.out.println("The file with largest perimeter is " + getFileWithLargestPerimeter());
    }

    // This method creates a triangle that you can use to test your other methods
    public void triangle(){
        Shape triangle = new Shape();
        triangle.addPoint(new Point(0,0));
        triangle.addPoint(new Point(6,0));
        triangle.addPoint(new Point(3,6));
        for (Point p : triangle.getPoints()){
            System.out.println(p);
        }
        double peri = getPerimeter(triangle);
        System.out.println("perimeter = "+peri);
    }

    // This method prints names of all files in a chosen folder that you can use to test your other methods
    public void printFileNames() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            System.out.println(f);
        }
    }

    public static void main (String[] args) {
        PerimeterAssignmentRunner pr = new PerimeterAssignmentRunner();
        //pr.testPerimeter();
        //pr.testPerimeterMultipleFiles();
        pr.testFileWithLargestPerimeter();

    }
}
