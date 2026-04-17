# 해당 사람(노드)이 속한 그룹의 '대표(루트)'를 찾는 함수
def find(x):
    # 자기 자신이 대표라면(아직 다른 그룹에 속하지 않았다면) 자기 자신을 반환
    if friends[x] == x:
        return x
    
    # 경로 압축(Path Compression): 
    # 대표를 찾기 위해 거쳐가는 모든 사람들의 부모를 '최상위 대표'로 직접 연결합니다.
    # 이렇게 하면 다음번에 대표를 찾을 때 훨씬 빠르게 찾을 수 있습니다.
    friends[x] = find(friends[x])
    return friends[x]


# 두 사람이 속한 그룹을 하나로 합치는 함수
def union(x, y):
    # 각 사람이 속한 그룹의 대표를 찾음
    root_x = find(x)
    root_y = find(y)

    # 두 사람의 대표가 다르다면 (즉, 아직 서로 다른 그룹이라면)
    if root_x != root_y:
        # y의 그룹 대표를 x의 그룹 대표로 변경하여 두 그룹을 합침
        friends[root_y] = root_x
        # x 그룹의 크기에 합쳐진 y 그룹의 크기를 더해줌
        network_size[root_x] += network_size[root_y]
        
    # 합쳐진 후(또는 이미 같은 그룹일 경우)의 현재 네트워크 크기를 반환
    return network_size[root_x]


T = int(input()) # 테스트 케이스의 개수 입력
for tc in range(T):
    line = int(input()) # 친구 관계(간선)의 개수 입력
    
    # 매 테스트 케이스마다 딕셔너리 초기화
    friends = dict()      # 누가 누구와 연결되어 있는지(부모 노드)를 저장
    network_size = dict() # 각 그룹의 친구 수(네트워크 크기)를 저장

    for _ in range(line):
        first, second = input().split() # 두 사람의 이름을 입력받음

        # 처음 입력된 이름(first)이라면 초기 설정
        if first not in friends:
            friends[first] = first # 처음엔 자기 자신이 대표
            network_size[first] = 1 # 처음 그룹의 크기는 본인 1명
            
        # 두 번째 이름(second)도 처음 등장했다면 초기 설정
        if second not in friends:
            friends[second] = second
            network_size[second] = 1

        # 두 사람의 그룹을 하나로 합치고, 합쳐진 그룹의 총 친구 수를 출력
        print(union(first, second))