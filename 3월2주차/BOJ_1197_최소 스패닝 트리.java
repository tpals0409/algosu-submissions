import java.util.*;
import java.io.*;

class Edge implements Comparable<Edge>{
	int start, end, cost;
	public Edge(int start, int end, int cost) {
		this.start = start;
		this.end = end;
		this.cost = cost;
	}
	@Override
	public int compareTo(Edge o) {
	    return this.cost - o.cost; 
	}
}

public class Main {
	static int[] parent;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int V = Integer.parseInt(st.nextToken());
		int E = Integer.parseInt(st.nextToken());
		
		parent = new int[V + 1];
        for (int i = 1; i <= V; i++) {
            parent[i] = i;
        }
        
        List<Edge> edgeList = new ArrayList<>();
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            edgeList.add(new Edge(start, end, cost));
        }
        
        Collections.sort(edgeList);
        
        int totalCost = 0;
        int count = 0;
        
        for (Edge edge : edgeList) {
            if (find(edge.start) != find(edge.end)) {
                union(edge.start, edge.end);
                totalCost += edge.cost;
                count++;
                
                if (count == V - 1) break;
            }
        }

        System.out.println(totalCost);
	}
	
	
	static int find(int a) {
		if(parent[a] == a) return a;
		return parent[a] = find(parent[a]);
	}
	
	static void union(int a, int b) {
		int rootA = find(a);
		int rootB = find(b);
		if(rootA != rootB) {
			parent[rootB] = rootA;
		}
	}
}