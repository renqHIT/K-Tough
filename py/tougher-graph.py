from graph_tool.all import *
from random import randint

def tougher_graph(vertices, edges):
	g = Graph()
	g.add_vertex(vertices)

	for i in range(edges):
		a = randint(0, vertices-1)
		b = randint(0, vertices-1)
		if a != b:
			print a, b	
			g.add_edge(g.vertex(a), g.vertex(b))

	return g


if __name__ == '__main__':
	g = tougher_graph(6, 8)
	g.save("running-example-2.xml.gz")
	graph_draw(g, vertex_text=g.vertex_index, vertex_font_size=18, output_size=(500,500), output="example-2.png")

