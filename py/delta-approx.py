from graph_tool.all import *
from numpy.random import random
from itertools import combinations
import time
import sys


def delta_approx(g, delta):
	n = g.num_vertices()
	cut_set_size = int(n * delta)

	for i in range(1, cut_set_size):
		combine = combinations(range(n), i)

		for c in combine:
            # filter the vertex combination from g
			residual = GraphView(g, vfilt = lambda v: g.vertex_index[v] not in c)
			for x in c:
                # w is the number of connected components of G-S
				w = num_cc(residual)
                # k is the temp value of toughness
				k = i / (w * 1.0)
				if k < g.graph_properties["tough"]:
					g.graph_properties["tough"] = k
					#print 'k = %f' % k


def num_cc(g):
    cc = 1
    comp, hist = label_components(g)
    return cc + max(comp.a)


if __name__ == '__main__':
	g = load_graph("./data/small-cases/graph-4.xml.gz")
	g.set_directed(False)
	toughness = g.new_graph_property("float")
	g.graph_properties["tough"] = toughness
	g.graph_properties["tough"] = float("inf")

	delta = 0.2

    # calculate toughness
	begin_time = time.clock()
	delta_approx(g, delta)
	end_time = time.clock()
	print 'toughness = %f' % g.graph_properties["tough"]
	print 'Total running time : ', end_time - begin_time
