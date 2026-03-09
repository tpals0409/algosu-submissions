import java.util.*;
import java.io.*;

public class Main {
    static int N, M;
    static int[][] map;
    static boolean[][] visited;
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    static int maxH = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];

        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = line.charAt(j) - '0';
                maxH = Math.max(maxH, map[i][j]);
            }
        }

        int totalWater = 0;

        // 높이 2부터 최대 높이까지 반복 (높이 1은 물이 고일 수 없음)
        for (int h = 2; h <= maxH; h++) {
            visited = new boolean[N][M];
            for (int i = 1; i < N - 1; i++) {
                for (int j = 1; j < M - 1; j++) {
                    // 현재 높이보다 낮고 방문하지 않은 곳 탐색
                    if (map[i][j] < h && !visited[i][j]) {
                        totalWater += bfs(i, j, h);
                    }
                }
            }
        }

        System.out.println(totalWater);
    }

    static int bfs(int x, int y, int h) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x, y});
        visited[x][y] = true;

        int count = 1;
        boolean isTrapped = true;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();

            for (int i = 0; i < 4; i++) {
                int nx = curr[0] + dx[i];
                int ny = curr[1] + dy[i];

                // 경계를 벗어나면 물이 새어 나가는 것임
                if (nx < 0 || nx >= N || ny < 0 || ny >= M) {
                    isTrapped = false;
                    continue;
                }

                // 현재 체크하는 높이(h)보다 낮고 아직 방문 안했다면
                if (!visited[nx][ny] && map[nx][ny] < h) {
                    visited[nx][ny] = true;
                    queue.add(new int[]{nx, ny});
                    count++;
                }
            }
        }

        // 갇혀있는 공간일 때만 칸 수를 반환, 아니면 0
        return isTrapped ? count : 0;
    }
}