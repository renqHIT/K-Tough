from graph_tool.all import *
from random import *
from matplotlib import *

#g, pos = triangulation(random(1000,2))
#pos = arf_layout(g)

#graph_draw(g, pos=pos, output="rewire_orig.pdf", output_size=(300,300))

def sample_k(max):
	accept = False
	while not accept:
		k = randint(1, max+1)
		accept = random() < 1.0/k
	return k


def deg_sample():
	if random() > 0.5:
		return poisson(4), poisson(4)
	else:
		return poisson(20), poisson(20)


g = random_graph(1000, lambda: sample_k(40), model="probabilistic", vertex_corr=lambda i, k: 1.0 / (1 + abs(i - k)), 
				directed=False, n_iter=100)

print scalar_assortativity(g, "out")

pos = arf_layout(g)
graph_draw(g, pos=pos, output="r.pdf", output_size=(1000,1000))
g.save("r.xml.gz")

#hist = combined_corr_hist(g, "in", "out")
#clf()
#imshow(hist[0].T, interpolation="nearest", origin="lower")
#colorbar()
#xlabel("in-degree")
#ylabel("out-degree")
#savefig("combined-deg-hist.pdf")
