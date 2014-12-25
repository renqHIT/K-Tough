from graph_tool.all import *
from numpy.random import random
import time
import sys


def brute_force(g):
    toughness = float("inf")
    n = g.num_vertices()
    for i in range(1, n/2):
        #print '--------------------------------listing all %d combinations---------------------------' % i
        combine = combinations(range(n), i)
        for c in combine:
            # filter the vertex combination from g
            residual = GraphView(g, vfilt = lambda v: g.vertex_index[v] not in c)
            for x in c:
                # w is the number of connected components of G-S
                w = num_cc(residual)
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
    g = load_graph("./data/small-cases/graph-4.xml.gz")
    g.set_directed(False)

    # calculate toughness
    begin_time = time.clock()
    t = brute_force(g)
    end_time = time.clock()
    print 'toughness = %f' % t
    print 'Total running time : ', end_time - begin_time

