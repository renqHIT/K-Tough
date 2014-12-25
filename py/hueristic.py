from graph_tool.all import *
from numpy.random import random
import time
import sys


# TODO Implement hueristic function

def hueristic(g):
	toughness = float("inf")
	n = g.num_vertices()
	start_v = random_v()
	for v in two_hop_neighbour(start_v):
		pass
		
	return toughness


def num_cc(g):
	cc = 1
	comp, hist = label_components(g)
	return cc + max(comp.a)


if __name__ == '__main__':
	g = load_graph("./data/small-cases/graph-4.xml.gz")
	g.set_directed(False)

    # calculate toughness
	begin_time = time.clock()
	t = hueristic(g)
	end_time = time.clock()
	print 'toughness = %f' % t
	print 'Total running time : ', end_time - begin_time
