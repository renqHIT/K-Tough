from graph_tool.all import *
from numpy.random import *

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


if __name__ == '__main__':
	g = generate_graph(10)
	graph_draw(g, output_size=(1000,1000), output="graph-4.png" )
	g.save("graph-4.xml.gz")
	print g
