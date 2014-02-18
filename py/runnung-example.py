from graph_tool.all import *

a = Graph()
b = Graph()

a.add_vertex(6)
b.add_vertex(6)

a.add_edge(a.vertex(1), a.vertex(0))
a.add_edge(a.vertex(2), a.vertex(0))
a.add_edge(a.vertex(3), a.vertex(0))
a.add_edge(a.vertex(4), a.vertex(0))
a.add_edge(a.vertex(5), a.vertex(1))

a.save("example-1.xml.gz")

b.add_edge(b.vertex(3), b.vertex(5))
b.add_edge(b.vertex(2), b.vertex(0))
b.add_edge(b.vertex(4), b.vertex(2))
b.add_edge(b.vertex(3), b.vertex(0))
b.add_edge(b.vertex(1), b.vertex(4))
b.add_edge(b.vertex(4), b.vertex(0))

b.save("example-2.xml.gz")

graph_draw(a, vertex_text=a.vertex_index, vertex_font_size=18, output_size=(500, 500), output="two-nodes-1.png")

graph_draw(b, vertex_text=b.vertex_index, vertex_font_size=18, output_size=(500, 500), output="two-nodes-2.png")
