from graph_tool.all import *
from numpy.random import random
from numpy.random import poisson

def toughness(g):
	toughness = float("inf")
	n = g.num_vertices()
	for i in range(1, n/2):
		print 'listing all %d combinations' % i
		combine = combinations(range(n), i)
		for c in combine:
			# c is the vertex index of |S|
			print c
			# w is the number of connected components of G-S
			w = num_cc(g)
			# k is the temp value of toughness
			k = i / w
			if k < toughness:
				toughness = k
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


def num_cc(g):
	num_cc = 1
	comp, hist = label_components(g)
	max_index = 0
	for v in g.vertices():
		if max(comp.a) > max_index:
			max_index = max(comp.a)

	return num_cc + max_index


if __name__ == '__main__':
	# load graph and initialize
	#g = random_graph(100, lambda: (poisson(2), poisson(2)))
	g = load_graph("./data/running-examples/running-example-1.xml.gz")
	g.set_directed(False)

	# calculate toughness
	t = toughness(g)
	print t

	graph_draw(g, output = "poisson.pdf")
