from graph_tool.all import *
import time

def bfs(g):
	dist = g.new_vertex_property("int")
	pred = g.new_vertex_property("int")
	#toughness = float(0)
	bfs_search(g, g.vertex(0), Visitor(pred, dist))
	#print "toughness approximation finally = ", g.graph_properties["tough"]

class Visitor(BFSVisitor):

	staticVariable = 0
	def __init__(self, pred, dist):
		self.pred = pred
		self.dist = dist

	def examine_vertex(self, u):
		self.staticVariable += 1 / (u.out_degree() * 1.0)
		#print("toughness approximation = ", 1 / (self.staticVariable * 1.0))
		# TODO THIS CAN BE IMPROVED BY ASSIGNING ONLY IN THE LAST VERTEX EXAMINATION
		g.graph_properties["tough"] = 1 / (self.staticVariable * 1.0)

	def tree_edge(self, e):
		self.pred[e.target()] = int(e.source())
		self.dist[e.target()] = self.dist[e.source()] + 1	
	

if __name__ == '__main__':
	g = load_graph("./data/small-cases/graph-4.xml.gz")
	g.set_directed(False)
	toughness = g.new_graph_property("float")
	g.graph_properties["tough"] = toughness
	g.graph_properties["tough"] = float("inf")
	
	#calculate toughness
	begin_time = time.clock()
	bfs(g)
	end_time = time.clock()
	print "toughness = %f" % g.graph_properties["tough"]
	print "Total running time : ", end_time - begin_time

	#graph_draw(g, vertex_text= g.vertex_index, vertex_font_size=12, vertex_shape="double_circle", vertex_fill_color="#729fcf", vertex_pen_width=3, output="search_example.pdf")
