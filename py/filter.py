from graph_tool.all import *
from numpy.random import random

def g_filter():
	g = load_graph("./data/running-examples/delaunay.xml.gz")
	gv = GraphView(g, vfilt = lambda v: g.vertex_index[v] != 2)	


if __name__ == '__main__':
	g_filter()
