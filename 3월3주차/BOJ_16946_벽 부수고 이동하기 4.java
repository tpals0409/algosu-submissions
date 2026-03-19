import java.util.*;
import java.io.*;

public class Main {
    static int N, M, parent[], size[];
    static char[][] map;
    static int[] dr = {-1, 1, 0, 0}, dc = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); M = Integer.parseInt(st.nextToken());

        map = new char[N][];
        parent = new int[N * M]; size = new int[N * M];
        for (int i = 0; i < N * M; i++) { parent[i] = i; size[i] = 1; }

        for (int i = 0; i < N; i++) map[i] = br.readLine().toCharArray();

        // 1. Union: 인접한 0끼리 합치기 (오른쪽, 아래만 체크해도 충분)
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (map[r][c] == '0') {
                    for (int i = 0; i < 4; i++) {
                        int nr = r + dr[i], nc = c + dc[i];
                        if (nr >= 0 && nr < N && nc >= 0 && nc < M && map[nr][nc] == '0')
                            union(r * M + c, nr * M + nc);
                    }
                }
            }
        }

        // 2. Result: 벽(1) 주변 루트의 size 합산
        StringBuilder sb = new StringBuilder();
        List<Integer> near = new ArrayList<>(); // 중복 방지용 임시 리스트
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (map[r][c] == '1') {
                    int sum = 1; near.clear();
                    for (int i = 0; i < 4; i++) {
                        int nr = r + dr[i], nc = c + dc[i];
                        if (nr >= 0 && nr < N && nc >= 0 && nc < M && map[nr][nc] == '0') {
                            int root = find(nr * M + nc);
                            if (!near.contains(root)) { // 중복 루트 체크
                                sum += size[root];
                                near.add(root);
                            }
                        }
                    }
                    sb.append(sum % 10);
                } else sb.append('0');
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }

    static int find(int x) {
        return (parent[x] == x) ? x : (parent[x] = find(parent[x]));
    }

    static void union(int x, int y) {
        x = find(x); y = find(y);
        if (x != y) { parent[y] = x; size[x] += size[y]; }
    }
}