from graph_tool.all import *
from numpy.random import random
from numpy.random import poisson
from itertools import combinations
import time
import sys

#f_handler = open('debug.log','w')
#sys.stdout = f_handler

def toughness(g):
	toughness = float("inf")
	n = g.num_vertices()
	for i in range(1, n/2):
		#print '--------------------------------listing all %d combinations---------------------------' % i
		combine = combinations(range(n), i)
		for c in combine:
			# filter the vertex combination from g
			residual = GraphView(g, vfilt = lambda v: g.vertex_index[v] not in c)
			#print 'residual graph : ', residual
			for x in c:
				# x is the vertex index of |S|
				# print '|S| = {%d}' % x
				# w is the number of connected components of G-S
				w = num_cc(residual)
				# print 'w(G-S) = %d' % w
				# k is the temp value of toughness
				k = i / (w * 1.0)
				if k < toughness:
					toughness = k
					print 'k = %f' % k
				#print '-------------------------------------------------------------------------------------'
	return toughness	


def num_cc(g):
	cc = 1
	comp, hist = label_components(g)
	return cc + max(comp.a)


if __name__ == '__main__':
	begin_time = time.clock()
	# load graph and initialize
	#g = random_graph(100, lambda: (poisson(2), poisson(2)))
	#g = load_graph("./data/running-examples/running-example-1.xml.gz")
	#g = load_graph("./data/running-examples/running-example-2.xml.gz")
	g = load_graph("./data/small-cases/graph-4.xml.gz")
	#g = load_graph("./data/running-examples/path.xml.gz")
	#g = load_graph("./data/running-examples/circle.xml.gz")
	g.set_directed(False)

	# calculate toughness
	t = toughness(g)
	print 'toughness = %f' % t
	end_time = time.clock()
	print 'Total running time : ', end_time - begin_time 
