from graph_tool.all import *
from numpy.random import *


def path_graph(n):
	g = Graph()
	g.add_vertex(n)
	
	for i in range(n-1):
		g.add_edge(g.vertex(i), g.vertex(i+1))
	
	return g


def circle_graph(n):
	g = Graph()
	g.add_vertex(n)
	
	for i in range(n-1):
		g.add_edge(g.vertex(i), g.vertex(i+1))
	g.add_edge(g.vertex(n-1),g.vertex(0))
	
	return g


def generate_graph(vertices):
	# We start with an empty graph	
	g = Graph()
	# We want also to keep the age information for each vertex and edge. For that
	# let's create some property maps
	v_age = g.new_vertex_property("int")
	e_age = g.new_edge_property("int")

	# The final size of the network
	N = vertices

	# We have to start with one vertex
	v = g.add_vertex()
	v_age[v] = 0

	# we will keep a list of the vertices. The number of times a vertex is in this
	# list will give the probability of it being selected.
	vlist = [v]

	# let's now add the new edges and vertices
	for i in range(1, N):
   		# create our new vertex
		v = g.add_vertex()
		v_age[v] = i

		# we need to sample a new vertex to be the target, based on its in-degree +
		# 1. For that, we simply randomly sample it from vlist.
		i = randint(0, len(vlist))
		target = vlist[i]

		# add edge
		e = g.add_edge(v, target)
		e_age[e] = i

		# put v and target in the list
		vlist.append(target)
		vlist.append(v)

	# now we have a graph!
	return g


def txt2xml(txt_path, num_nodes, num_edges):
	g = Graph(directed = False)
	g.add_vertex(num_nodes)
	maxa = 0
	maxb = 0
	with open(txt_path, 'r') as f:
		for line in f:
			a, b = line.split()
			#if int(a) > maxa:	maxa = int(a)
			#if int(b) > maxb:	maxb = int(b)
			g.add_edge(int(a), int(b))
	#print 'maxa = %d' %maxa
	print g
	return g

if __name__ == '__main__':
	#g = generate_graph(6)
	#graph_draw(g, vertex_text=g.vertex_index, vertex_font_size=18, output_size=(500, 500), output="./data/example-1.png")

	#path = path_graph(8)
	#graph_draw(path, vertex_font_size=18, output_size=(200,200), output="./data/path.pdf")
	#path.save("./data/path.xml.gz")

	#circle = circle_graph(8)
	#graph_draw(circle, vertex_font_size=18, output_size=(200,200), output="./data/circle.pdf")
	#path.save("./data/circle.xml.gz")

	ca_road = txt2xml("./data/SNAP/roadNet-CA.txt", 1971281, 2766607)
	ca_road.save("./data/ca_road.xml.gz")

	com_dblp = txt2xml("./data/SNAP/com-dblp.txt", 425957, 1049866)
	com_dblp.save("./data/com_dblp.xml.gz")
