# Largest Triangle

Sequential and Cluster parallel programs that finds the largest triangle in a group of points. 

In other words, given a group of two-dimensional points, the goal is to find the largest triangle.

The area of a triangle is (s(s − a)(s − b)(s − c))^1/2, where a, b, and c are the lengths of the triangle's sides and s = (a + b + c)/2.

The arguments to the program are a PointSpec object from which the program obtains (x,y) coordinates.


## Output Example

```
$ java pj2 LargestTriangleSeq "RandomPoints(100,100,142857)"
9 95.951 7.8408
12 98.248 97.938
80 2.3838 77.670
4295.3
```
