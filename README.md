K-Tough
=======
This is a trial for calculating the TOUGHNESS for any given graph.

Let G(V,E) be an undirected graph, t(G) denotes the toughness of G defined as:

	t(G) = min{|S|/w(G-S)}
	
where S is any vertex seperator for G, |S| is the size of vertex seperator S,
w(G) is the number of connected components of G.

##Usage 
* To compile and package the project,
```
sbt package
```

* To submit package to spark in local mode
```
spark-submit --class "Toughness" --master local[4] target/scala-2.10/your-project_2.10-1.0.jar
```
* To submit package to spark in stand alone cluster mode
```
spark-submit --class "Toughness" --master spark://cluster07:7077 your-project_2.10-1.0.jar
```

##Visualize
We use a Python module: graph-tool to visualize graphs (<50 nodes for simplicity).
http://graph-tool.skewed.de/
* To draw a graph to a png/pdf file, use graph_draw function:
```
graph_draw(g, vertex_text=g.vertex_index, vertex_font_size=18, output_size=(500,500), output="example-2.png")
```
