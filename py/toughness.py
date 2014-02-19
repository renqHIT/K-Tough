from graph_tool.all import *
from numpy.random import random
from numpy.random import poisson
from copy import deepcopy
import sys
import pdb

#f_handler = open('debug.log','w')
#sys.stdout = f_handler

def toughness(g):
	toughness = float("inf")
	n = g.num_vertices()
	for i in range(1, n/2):
		print '--------------------------------listing all %d combinations---------------------------' % i
		combine = combinations(range(n), i)
		for c in combine:
			residual = deepcopy(g)
			for x in c:
				# x is the vertex index of |S|
				print '|S| = {%d}' % x
				# remove the vertex combination from g
				residual.remove_vertex(residual.vertex(x))
				print 'residual graph : ', residual
				# w is the number of connected components of G-S
				w = num_cc2(residual)
				print 'w(G-S) = %d' % w
				# k is the temp value of toughness
				k = i / (w * 1.0)
				print 'k = %f' % k
				if k < toughness:
					toughness = k
				print '-------------------------------------------------------------------------------------'
	return toughness	


def combinations(iterable, r):
    # combinations('ABCD', 2) --> AB AC AD BC BD CD
    # combinations(range(4), 3) --> 012 013 023 123
    pool = tuple(iterable)
    n = len(pool)
    if r > n:
        return
    indices = range(r)
    yield tuple(pool[i] for i in indices)
    while True:
        for i in reversed(range(r)):
            if indices[i] != i + n - r:
                break
        else:
            return
        indices[i] += 1
        for j in range(i+1, r):
            indices[j] = indices[j-1] + 1
        yield tuple(pool[i] for i in indices)


#visited = {}	

def explore(g, v, visited):
	visited[v] = True
	for u in v.all_neighbours():
		#pdb.set_trace()
		if not visited[u]:
			explore(g, u, visited)


def num_cc(g):
	cc = 0
	visited = {}	
	for v in g.vertices():
		print 'v = ', v
		visited[v] = False

	for v in g.vertices():
		if not visited[v]:
			cc = cc + 1
			explore(g, v, visited)

	return cc


def num_cc2(g):
	cc = 1
	comp, hist = label_components(g)
	print 'comp.a = ', comp.a
	print 'max of comp.a = ', max(comp.a)
	print 'hist = ', hist
	print 'len(hist)', len(hist)
	return cc + max(comp.a)


def num_cc3(g):
	seen = set()
	def component(node):
		nodes = set([node])
		while nodes:
			node = nodes.pop()
			seen.add(node)
			nodes |= neighbors[node] - seen
			yield node
		for node in neighbors:
			if node not in seen:
				yield component(node)	


if __name__ == '__main__':
	# load graph and initialize
	#g = random_graph(100, lambda: (poisson(2), poisson(2)))
	#g = load_graph("./data/running-examples/running-example-1.xml.gz")
	g = load_graph("./data/running-examples/path.xml.gz")
	#g = load_graph("./data/running-examples/circle.xml.gz")
	#g.set_directed(False)

	# calculate toughness
	t = toughness(g)
	print t

	graph_draw(g, output = "poisson.pdf")
