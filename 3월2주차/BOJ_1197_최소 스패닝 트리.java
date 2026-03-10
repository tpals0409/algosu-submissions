import java.util.*;
import java.io.*;

// 간선 정보를 저장하기 위한 클래스입니다.
class Edge implements Comparable<Edge>{
	int start, end, cost;
	
	public Edge(int start, int end, int cost) {
		this.start = start;
		this.end = end;
		this.cost = cost;
	}
	
	@Override
	public int compareTo(Edge o) {
	    // 비용이 작은 순서대로(오름차순) 정렬하기 위한 기준을 정의합니다.
	    return this.cost - o.cost; 
	}
}

public class Main {
	// 각 노드의 부모 노드를 저장하여 집합을 관리할 배열입니다.
	static int[] parent;
	
	public static void main(String[] args) throws IOException{
		// 입력을 빠르게 받기 위한 BufferedReader와 StringTokenizer 설정입니다.
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int V = Integer.parseInt(st.nextToken()); // 정점(Vertex)의 개수
		int E = Integer.parseInt(st.nextToken()); // 간선(Edge)의 개수
		
		// 1. Union-Find를 위한 부모 테이블 초기화
		parent = new int[V + 1];
        for (int i = 1; i <= V; i++) {
            // 처음에는 모든 노드가 자기 자신을 부모로 가집니다 (독립된 집합).
            parent[i] = i;
        }
        
        // 2. 간선 정보를 입력받아 리스트에 담습니다.
        List<Edge> edgeList = new ArrayList<>();
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            edgeList.add(new Edge(start, end, cost));
        }
        
        // 3. 비용을 기준으로 간선들을 오름차순 정렬합니다.
        Collections.sort(edgeList);
        
        int totalCost = 0; // 최소 신장 트리의 총 비용
        int count = 0;     // 연결된 간선의 개수
        
        // 4. 정렬된 간선들을 하나씩 확인하며 트리를 만들어갑니다.
        for (Edge edge : edgeList) {
            // 시작점과 끝점의 최상위 부모(Root)가 다르다면? -> 사이클이 발생하지 않는다는 뜻!
            if (find(edge.start) != find(edge.end)) {
                // 두 노드를 하나의 집합으로 합치고(Union), 결과에 포함시킵니다.
                union(edge.start, edge.end);
                totalCost += edge.cost;
                count++;
                
                // 간선의 개수가 (정점 개수 - 1)개가 되면 MST가 완성된 것이므로 종료합니다.
                if (count == V - 1) break;
            }
        }

        // 완성된 최소 신장 트리의 총 비용을 출력합니다.
        System.out.println(totalCost);
	}
	
	 // 특정 노드가 속한 집합의 대표자(Root)를 찾는 메서드입니다.
	static int find(int a) {
		// 노드 자기 자신이 부모라면 그 노드가 루트입니다.
		if(parent[a] == a) return a;
		
		//  경로 압축(Path Compression)
		return parent[a] = find(parent[a]);
	}
	
	 // 두 노드가 속한 집합을 하나로 합치는 메서드입니다.
	static void union(int a, int b) {
		int rootA = find(a);
		int rootB = find(b);
		
		// 두 노드의 루트가 다르다면 하나로 합쳐줍니다.
		if(rootA != rootB) {
			parent[rootB] = rootA;
		}
	}
}