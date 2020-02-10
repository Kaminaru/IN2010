import java.util.List;
import java.util.LinkedList;

class Node {
	private int label;
	private boolean visited = false;
	private List<Node> neighbors = new LinkedList<Node>();

	public Node(int label) {
		this.label = label;
	}

	public int getLabel() {
		return label;
	}

	public List<Node> getNeighbors() {
		return neighbors;
	}

	public boolean isVisited() {
		return visited;
	}

	public void visit() {
		visited = true;
	}

	public void unvist(){
		visited = false;
	}


	public void addNeighbor(Node n) {
		// legger til en uretta kant fra this til n
		if (!neighbors.contains(n)) {
			neighbors.add(n);
			n.addNeighbor(this);
		}
	}

	public void addSuccessor(Node n) {
		// legger til en retta kant fra this til n
		if (!neighbors.contains(n)) {
			neighbors.add(n);
		}
	}

	public String toString() {
		return Integer.toString(label);
	}
}







class Graph {
	private Node[] nodes;

	public Graph(Node[] nodes) {
		this.nodes = nodes;
	}

	public Node[] returnNodes(){
		return nodes;
	}

	public void printNeighbors() {
		for (Node n1 : nodes) {
			String s = n1.toString() + ": ";
			for (Node n2 : n1.getNeighbors()) {
				s += n2.toString() + " ";
			}
			System.out.println(s.substring(0, s.length() - 1)); // ??????
		}
	}

	public void resetAllVisited(){
		for(Node n : nodes){
			n.unvist();
		}
	}

	private static Graph buildExampleGraph() {
	  // ukeoppgave
		Node[] nodes = new Node[7];
		for (int i = 0; i < 7; i++) {
			nodes[i] = new Node(i);
		}
		nodes[0].addNeighbor(nodes[1]);
		nodes[0].addNeighbor(nodes[2]);
		nodes[1].addNeighbor(nodes[2]);
		nodes[2].addNeighbor(nodes[3]);
		nodes[2].addNeighbor(nodes[5]);
		nodes[3].addNeighbor(nodes[4]);
		nodes[4].addNeighbor(nodes[5]);
		nodes[5].addNeighbor(nodes[6]);
		return new Graph(nodes);
	}

	private static Graph extraTest(){
		// test for 1 --> 2 <-- 3  0 4;

		Node[] nodes = new Node[5];
		for (int i = 0; i < 5; i++) {
			nodes[i] = new Node(i);
		}
		nodes[1].addSuccessor(nodes[2]);
		nodes[3].addSuccessor(nodes[2]);
		return new Graph(nodes);
	}


	private static Graph buildRandomSparseGraph(int numberofV, long seed) {
		// seed brukes av java.util.Random for å generere samme sekvens for samme frø
		// (seed) og numberofV
		java.util.Random tilf = new java.util.Random(seed);
		int tilfeldig1 = 0, tilfeldig2 = 0;
		Node[] nodes = new Node[numberofV];

		for (int i = 0; i < numberofV; i++) {
			nodes[i] = new Node(i);
		}

		for (int i = 0; i < numberofV; i++) {
			tilfeldig1 = tilf.nextInt(numberofV);
			tilfeldig2 = tilf.nextInt(numberofV);
			if (tilfeldig1 != tilfeldig2)
				nodes[tilfeldig1].addNeighbor(nodes[tilfeldig2]);
		}
		return new Graph(nodes);
	}

	private static Graph buildRandomDenseGraph(int numberofV, long seed) {
		java.util.Random tilf = new java.util.Random(seed);
		int tilfeldig1 = 0, tilfeldig2 = 0;
		Node[] nodes = new Node[numberofV];

		for (int i = 0; i < numberofV; i++) {
			nodes[i] = new Node(i);
		}

		for (int i = 0; i < numberofV * numberofV; i++) {
			tilfeldig1 = tilf.nextInt(numberofV);
			tilfeldig2 = tilf.nextInt(numberofV);
			if (tilfeldig1 != tilfeldig2)
				nodes[tilfeldig1].addNeighbor(nodes[tilfeldig2]);
		}
		return new Graph(nodes);
	}

	private static Graph buildRandomDirGraph(int numberofV, long seed) {
		java.util.Random tilf = new java.util.Random(seed);
		int tilfeldig1 = 0, tilfeldig2 = 0;
		Node[] nodes = new Node[numberofV];

		for (int i = 0; i < numberofV; i++) {
			nodes[i] = new Node(i);
		}

		for (int i = 0; i < 2 * numberofV; i++) {
			tilfeldig1 = tilf.nextInt(numberofV);
			tilfeldig2 = tilf.nextInt(numberofV);
			if (tilfeldig1 != tilfeldig2)
				nodes[tilfeldig1].addSuccessor(nodes[tilfeldig2]);
		}
		return new Graph(nodes);
	}

	public void DFS(Node s) {
    s.visit();
    for(Node n : s.getNeighbors()){
      if(!n.isVisited()){ // == false
        DFS(n); // rekusjon
      }
    }
	}

	public void DFSFull() {
    for(Node n : nodes){
      if(!n.isVisited()){ // == false
        DFS(n);
      }
    }
	}

	// Returns number of components in the graph
  public int numberOfComponents() {
	  // Oppgave 1A
    int samKomp = 0;
		for(Node n : this.nodes){
			if(!n.isVisited()){ // if not visited
				samKomp++;
				DFS(n);
			}
		}
	  return samKomp;

	}

  public Graph transformDirToUndir() {
	  // Oppgave 1B

		Node[] n = this.nodes; // array of old nodes list
		Node[] urettNodes = new Node[n.length];
		// makes new urettNode list with labes in each node
		for(int i = 0; i < n.length; i++){ // n is a node in a old node list
			urettNodes[i] = new Node(n[i].getLabel());
		}

		for(int i = 0; i < n.length; i++){
			for(Node neigh : n[i].getNeighbors()){
				urettNodes[i].addNeighbor(urettNodes[neigh.getLabel()]); // label here used as index
			}
		}
		return new Graph(urettNodes); // returner en NY graf


		// This one is much shorter but i need the second one, because second one
		// makes new graph, so original wont be changed, i need that to make 1D part work normaly
		// (For problem like 1 --> 2 <-- 3   4  5)

		//    ||  ||  ||  ||  ||  ||
		//    V   V   V   V   V   V

		// for(Node n : this.nodes){
		// 	for(Node neighbor : n.getNeighbors()){
		// 		n.addNeighbor(neighbor);
		// 		neighbor.addNeighbor(n);
		// 	}
		// }
		// return this;
	}


	// sjekker om graph er svak sammenhengende eller nei
  public boolean isConnected(){
	  // Oppgave 1C
	  return this.transformDirToUndir().numberOfComponents() == 1; // true om den er svak sammenhengende
	}


  public Graph biggestComponent() {
		// Oppgave 1D
		// Kode er for versjon 0.9 ... Fant ut bare paa slutt at det er bare for uretta graph.
		// MEN MIN KODE fungerer for retta ogsaa.
		int numbComp = numberOfComponents();
		if(numbComp == 1 || numbComp == 0) return this; // if there is only one element

		//---------------------------------------
		// For promlems like 1 --> 2 <-- 3   4  5
		// (Where program can't see that 1 2 3 is same comp)
		Graph extraGraph = this.transformDirToUndir(); // extra undirect graph
		Node[] nodesList = extraGraph.returnNodes();
		//--------------------------------------

		// if there is more than one component in the graph
		int biggestNum = 0;
		Node[] biggestComp = null;
		// n^4 in worst case ... (changes needed)
		for(Node n : nodesList){
			extraGraph.resetAllVisited();
			DFS(n); // few nodes gets visited.
			// Those that was visisted is in same components (not all the time because of Dir. and Undir parts)

			int newNum = 0; // number of nodes in component
			for(Node k : nodesList){
				if(k.isVisited()){ // if true
					newNum++;
				}
			}

			if(newNum > biggestNum){ // if new component bigger than last one
				biggestNum = newNum;
				biggestComp = new Node[newNum];

				// adds direction to every node
				int j = 0; // because old graph and new graph with only one component doesnt have same size
				for(int i = 0; i < nodesList.length; i++){ // going throw not original graph
					if(nodesList[i].isVisited()){
						biggestComp[j] = this.nodes[i]; // using nodes from original graph
						j++;
					}
				}
			}
		}
		Graph nyGraph = new Graph(biggestComp);
		return nyGraph; // returns new graph with only one element
	}

	public int[][] buildAdjacencyMatrix() {
		// Oppgave 1E                          (rows)           (columns)
		int[][] neighborMatrix = new int[this.nodes.length][this.nodes.length]; // every index have 0 as data
    for(Node n : this.nodes){
			for(Node nabo : n.getNeighbors()){
				neighborMatrix[n.getLabel()][nabo.getLabel()] = 1;
			}
		}
	  return neighborMatrix;
	}



	// just a testing of program
	public static void main(String[] args) {
		 Graph graph = buildExampleGraph();
		graph = buildRandomSparseGraph(110, 201909202359L);
		graph.printNeighbors();
		System.out.println("");
		graph = buildRandomDenseGraph(15, 201909202359L);
		graph.printNeighbors();

		//test 1A
		System.out.println("\n-----------------------------------");
		graph = buildExampleGraph();
		System.out.println(graph.numberOfComponents()); // skriver

		//test 1B
		System.out.println("\n TEST 1B\n");
		graph = buildRandomDirGraph(11, 201909202359L);
		graph.printNeighbors();
		System.out.println("\n");
		graph = graph.transformDirToUndir();
		graph.printNeighbors();

		//test 1C
		System.out.println("\n");
		graph = buildRandomDirGraph(11, 201909202359L);
		graph.printNeighbors();
		System.out.println(graph.isConnected());

		//test 1D
		System.out.println("\n TEST 1D\n");
		Graph graphSparse = buildRandomSparseGraph(15, 201909202359L);
		graphSparse.printNeighbors();
		System.out.println("\nBIGGEST comp 2\n");
		graphSparse.biggestComponent().printNeighbors();

		// test 1D
		System.out.println("\n Matrix test\n");
		int[][] matrixTest = graphSparse.buildAdjacencyMatrix();
		// paints neighbor matrix
		System.out.print("     ");
		for(int i = 0; i < matrixTest[0].length; i++){
			if(i>9){
				i-=10;
				System.out.print(i+" ");
				i+=10;
			}else{
				System.out.print(i+" ");
			}
		}
		System.out.println("");
		System.out.print("     ");
		for(int i = 0; i < matrixTest[0].length; i++){
			System.out.print("- ");
		}

		for(int y = 0; y < matrixTest.length; y++){
			System.out.println("");
			if (y < 10) {
				System.out.print("  " + y + ": ");
			}else if(y < 100){
				System.out.print(" " + y + ": ");
			}else{ // y>100
				System.out.println(y + ": ");
			}
			for(int x = 0; x < matrixTest[0].length; x++){ // I know, matrix is (n x n) but anyways...
				System.out.print(matrixTest[y][x]+" ");
			}
		}
		System.out.println("\n");
		System.out.println("EKSTRA test for 1D\n");
		graph = extraTest();
		graph.biggestComponent().printNeighbors();


		// NOT PROG TEST
		System.out.println("\n");
		graph = buildRandomSparseGraph(11, 201909202359L);
		graph.printNeighbors();

	}
}
