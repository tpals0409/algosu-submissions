import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1. 빠른 입출력을 위한 설정
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[][] board = new int[N][M];
        int[][] dp = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 2. 첫 번째 행 초기화
        // 첫 행은 무조건 왼쪽에서 오른쪽으로만 갈 수 있음 (위나 오른쪽에서 올 수 없음)
        dp[0][0] = board[0][0];
        for (int j = 1; j < M; j++) {
            dp[0][j] = dp[0][j - 1] + board[0][j];
        }

        // 3. 두 번째 행부터 DP 진행
        // 한 행을 처리할 때 임시 배열 2개를 사용하여 '방향성'을 분리함
        int[] LtoR = new int[M];
        int[] RtoL = new int[M];

        for (int i = 1; i < N; i++) {
            // (1) 왼쪽 -> 오른쪽 진행 시 시나리오
            // 해당 칸의 최선 = Max(위에서 내려옴, 왼쪽에서 옴) + 현재 칸 값
            LtoR[0] = dp[i - 1][0] + board[i][0]; // 맨 왼쪽은 위에서만 내려올 수 있음
            for (int j = 1; j < M; j++) {
                LtoR[j] = Math.max(dp[i - 1][j], LtoR[j - 1]) + board[i][j];
            }

            // (2) 오른쪽 -> 왼쪽 진행 시 시나리오
            // 해당 칸의 최선 = Max(위에서 내려옴, 오른쪽에서 옴) + 현재 칸 값
            RtoL[M - 1] = dp[i - 1][M - 1] + board[i][M - 1]; // 맨 오른쪽은 위에서만 내려옴
            for (int j = M - 2; j >= 0; j--) {
                RtoL[j] = Math.max(dp[i - 1][j], RtoL[j + 1]) + board[i][j];
            }

            // (3) 두 시나리오 중 더 큰 값을 진짜 DP 테이블에 기록
            for (int j = 0; j < M; j++) {
                dp[i][j] = Math.max(LtoR[j], RtoL[j]);
            }
        }

        // 4. 최종 결과 출력 (N, M 지점)
        System.out.println(dp[N - 1][M - 1]);
    }
}